
package com.hzkans.crm.modules.trade.web;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.persistence.PagePara;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.utils.*;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.sys.utils.UserUtils;
import com.hzkans.crm.modules.trade.constants.OrderStatusEnum;
import com.hzkans.crm.modules.trade.constants.TableFlowStatusEnum;
import com.hzkans.crm.modules.trade.constants.TableFlowTypeEnum;
import com.hzkans.crm.modules.trade.entity.Order;
import com.hzkans.crm.modules.trade.entity.TableFlow;
import com.hzkans.crm.modules.trade.service.OrderService;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hzkans.crm.modules.trade.service.TableFlowService;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

/**
 * 表格流程Controller
 * @author jc
 * @version 2018-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/trade/tableFlow")
public class TableFlowController extends BaseController {

	private static final String MAX_SIZE_ERROR = "导入文件大小超过限制100M";
	private static final Long MAX_SIZE =100*1024*1024L ;
	private static final String TYPE_XLS = "xls";
	private static final String TYPE_XLSX = "xlsx";

	@Autowired
	private TableFlowService tableFlowService;
	@Autowired
	private OrderService orderService;


	@RequestMapping("/orderImportPage")
	public String tablePage() {
		return "modules/order/order-import";
	}

	@RequestMapping("/orderExtranetPage")
	public String orderExtranetPage() {
		return "modules/order/order-extranet";
	}


	/**
	 * 表格导入
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/tableImport")
	@ResponseBody
	public String tableImport(HttpServletRequest request, HttpServletResponse response) throws Exception{

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file");
		Integer platformType = RequestUtils.
				getInt(request, "platform_type", "platform_type is null");
		Integer shopNo = RequestUtils.getInt(request, "shop_no", "shop_no is null");
		Integer type = RequestUtils.getInt(request,"table_type","table_type is null");
		String message = RequestUtils.getString(request, "message");
		if(null == message){
			message = "";
		}

		String originalFilename = file.getOriginalFilename();
		logger.info("[{}] originalFilename:{}",originalFilename);
		//给名字后加上时间戳
		String[] split = originalFilename.split("\\.");
		//判断格式
		if(!TYPE_XLS.equals(split[1]) && !TYPE_XLSX.equals(split[1])) {
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_TYPE_ERROR);
		}
		//判断内容格式
		Workbook workBook = TradeUtil.getWorkBook(originalFilename, file.getInputStream());
		Sheet sheetAt = workBook.getSheetAt(0);
		Row row = sheetAt.getRow(0);
		Cell cell = row.getCell(0);
		//获取总数据
		long rowNum = (long)(sheetAt.getLastRowNum());
		if(null == cell) {
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_TYPE_DATA_ERROR);
		}
		if(TableFlowTypeEnum.ORDER_INFO.getCode().equals(type)) {
			if(!cell.getStringCellValue().equals("订单ID")) {
				return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR, "请导入正确的订单表");
			}
		}
		if(TableFlowTypeEnum.CUSTOMER.getCode().equals(type)) {
			if(!cell.getStringCellValue().equals("昵称")) {
				return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR, "请导入正确的顾客表");
			}
		}


		originalFilename = split[0] + DateUtils.formatNewDate(new Date())+"."+split[1];
		logger.info("[{}] originalFilename:{}",originalFilename);

		TableFlow tableFlow = new TableFlow();
		tableFlow.setImportDate(new Date());
		tableFlow.setTableName(originalFilename);
		tableFlow.setShopNo(shopNo);
		tableFlow.setPlatformType(platformType);
		tableFlow.setType(type);
		tableFlow.setTotalNum(rowNum);
		long size = file.getSize();
		if(size > MAX_SIZE) {
			logger.info("导入文件过大");
			//保存上传记录
			tableFlow.setStatus(TableFlowStatusEnum.IMPORT_SYSTEM_FAIL.getCode());
			tableFlow.setErrorMessage(MAX_SIZE_ERROR);
			try {
				tableFlowService.saveTableFlow(tableFlow);
			} catch (ServiceException e) {
				return ResponseUtils.getFailApiResponseStr(e.getCode(), e.getServiceMessage());
			}
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR,MAX_SIZE_ERROR);
		}
		try {
			File newFile = new File(TradeUtil.UPLOAD_ADDRESS+originalFilename);
			if(!newFile.exists()) {
                newFile.mkdirs();
            }
			file.transferTo(newFile);

			//保存上传记录
			tableFlow.setStatus(TableFlowStatusEnum.IMPORT_SYSTEM_SUCCESS.getCode());
			tableFlow.setErrorMessage(message);
			tableFlowService.saveTableFlow(tableFlow);
		} catch (ServiceException e) {
			return ResponseUtils.getFailApiResponseStr(e.getCode(), e.getServiceMessage());
		}
		return ResponseUtils.getSuccessResponseStr("上传成功");
	}

	/**
	 *	表格信息展示
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/tableShow")
	@ResponseBody
	public String tableShow(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//必传参数
		Integer currentPage = RequestUtils.getInt(request, "current_page", "current_page is null");
		Integer pageSize = RequestUtils.getInt(request, "page_size", "page_size is null");;
		//非必传参数
		String tableName = RequestUtils.getString(request, "table_name");
		Integer platformType = RequestUtils.getInt(request, "platform_type", "");
		Integer shopNo = RequestUtils.getInt(request, "shop_no", "");
		Integer status = RequestUtils.getInt(request, "status", "");
		String startDate = RequestUtils.getString(request, "start_date" );
		String endDate = RequestUtils.getString(request, "end_date");

		if(currentPage == null || pageSize == null) {
			currentPage = 1;
			pageSize = 10;
		}

		try {
			PagePara<TableFlow> tableFlowPage = new PagePara<>();
			TableFlow tableFlow = new TableFlow();
			tableFlow.setTableName(tableName);
			tableFlow.setPlatformType(platformType);
			tableFlow.setShopNo(shopNo);
			tableFlow.setStatus(status);
			if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
				tableFlow.setStartDate(DateUtil.parse(startDate, DateUtil.NORMAL_DATETIME_PATTERN));
				tableFlow.setEndDate(DateUtil.parse(endDate, DateUtil.NORMAL_DATETIME_PATTERN));
			}

			tableFlowPage.setCurrentPage((currentPage - 1)*pageSize);
			tableFlowPage.setPageSize(pageSize);
			tableFlowPage.setData(tableFlow);
			PagePara<TableFlow> tablePages = tableFlowService.getTablePages(tableFlowPage);

			return ResponseUtils.getSuccessApiResponseStr(tablePages);
		} catch (ServiceException e) {
			logger.error("tableShow error",e);
			return ResponseUtils.getFailApiResponseStr(e.getCode(), e.getServiceMessage());
		}
	}


	/**
	 * 订单管理中的订单发布
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/orderIssue")
	@ResponseBody
	public String orderIssue(HttpServletRequest request, HttpServletResponse response) {
		Long memberId = Long.parseLong(UserUtils.getUser().getNo());
		logger.info("memberId {}", memberId);
		Integer tableId = RequestUtils.getInt(request, "table_id", "table_id is null");
		TableFlow table = null;
		try {
			TableFlow tableFlow = new TableFlow();
			tableFlow.setId(tableId.toString());
			table = tableFlowService.getTable(tableFlow);
		} catch (ServiceException e) {
			logger.error("orderIssue error",e);
			return ResponseUtils.getFailApiResponseStr(e.getCode(), e.getServiceMessage());
		}

		if(null == table) {
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_TABLE_NOT_EXIST);
		}
		Integer status = table.getStatus();
		if(!TableFlowStatusEnum.TIMING_SUCCESS.getCode().equals(status)) {
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_TABLE_STATUS_ERROR);
		}

		try {
			//更新这个表所有订单的状态为审核成功
			Order order = new Order();
			order.setTableId((long)tableId);
			order.setStatus(OrderStatusEnum.ORDER_LIST.getCode());
			orderService.updateOrder(order);
			//更新状态为发布成功..
			TableFlow tableFlow = new TableFlow();
			tableFlow.setId(table.getId());
			tableFlow.setErrorMessage(memberId.toString());
			tableFlow.setTimingDate(new Date());
			tableFlow.setStatus(TableFlowStatusEnum.ENSURE_TABLE_SUCCESS.getCode());
			tableFlowService.updateTableFlow(tableFlow);
			return ResponseUtils.getSuccessApiResponseStr(true);
		} catch (ServiceException e) {
			logger.error("orderIssue error",e);
			return ResponseUtils.getFailApiResponseStr(e.getCode(), e.getServiceMessage());
		}
	}

	/**
	 * 文件下载
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/orderDownLoad")
	@ResponseBody
	public String orderDownLoad(HttpServletRequest request, HttpServletResponse response) {

		Integer tableId = RequestUtils.getInt(request, "table_id", "table_id is null");

		try {
			TableFlow tableFlow = new TableFlow();
			tableFlow.setId(tableId.toString());
			TableFlow table = tableFlowService.getTable(tableFlow);

			if(null == table) {
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_TABLE_NOT_EXIST);
            }
			String tableName = table.getTableName();
			File file = new File(TradeUtil.UPLOAD_ADDRESS+tableName);
			if(!file.exists()) {
				logger.error(ResponseEnum.B_E_FILE_NOT_EXIST.getMsg());
				return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_TABLE_NOT_EXIST);
			}
			InputStream fis = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			response.reset();
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			String fileName = "Excel-"+new String(TradeUtil.getRealName(tableName).getBytes(),"iso-8859-1") + ".xls";
			String headStr = "attachment; filename=\"" + fileName + "\"";
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", headStr);
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
			return ResponseUtils.getSuccessApiResponseStr(true);
		} catch (IOException e) {
			logger.error("orderDownLoad error",e);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_DOWNLOAD_ERROR);
		}
	}

	@RequestMapping("/orderList")
	@ResponseBody
	public String allOrderList(HttpServletRequest request, HttpServletResponse response) {
		//必传参数
		Integer currentPage = RequestUtils.getInt(request, "current_page", "");
		Integer pageSize = RequestUtils.getInt(request, "page_size", "");
		//非必传参数
		Integer platformType = RequestUtils.getInt(request, "platform_type", "");
		Integer shopNo = RequestUtils.getInt(request, "shop_no", "");
		String startDate = RequestUtils.getString(request, "start_date");
		String endDate = RequestUtils.getString(request, "end_date");
		String orderSn = RequestUtils.getString(request, "order_sn");

		try {
			PagePara<Order> pagePara = new PagePara<>();
			Order order = new Order();
			order.setPlatformType(platformType);
			order.setOrderSn(orderSn);
			if(null != shopNo) {
                order.setShopNo(shopNo.toString());
            }
			if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
                order.setStartDate(DateUtil.parse(startDate, DateUtil.NORMAL_DATETIME_PATTERN));
                order.setEndDate(DateUtil.parse(endDate, DateUtil.NORMAL_DATETIME_PATTERN));
            }
			pagePara.setData(order);
			pagePara.setCurrentPage((currentPage-1)*pageSize);
			pagePara.setPageSize(pageSize);
			PagePara<Order> allOrder = orderService.getAllOrder(pagePara);

			return ResponseUtils.getSuccessApiResponseStr(allOrder);
		} catch (ServiceException e) {
			logger.error("allOrderList error",e);
			return ResponseUtils.getFailApiResponseStr(e.getCode(), e.getServiceMessage());
		}
	}

}