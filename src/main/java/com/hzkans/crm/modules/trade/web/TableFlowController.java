
package com.hzkans.crm.modules.trade.web;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.utils.DateUtils;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.trade.constants.PlatformTypeEnum;
import com.hzkans.crm.modules.trade.constants.TableFlowStatusEnum;
import com.hzkans.crm.modules.trade.constants.TableFlowTypeEnum;
import com.hzkans.crm.modules.trade.entity.TableFlow;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hzkans.crm.modules.trade.service.TableFlowService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;

/**
 * 表格流程Controller
 * @author chuan
 * @version 2018-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/trade/tableFlow")
public class TableFlowController extends BaseController {

	private static final String MAX_SIZE_ERROR = "导入文件大小超过限制100M";
	private static final Long MAX_SIZE =100*1024*1024L ;

	@Autowired
	private TableFlowService tableFlowService;


	@RequestMapping("/tablePage")
	public String tablePage() {
		return "";
	}


	@RequestMapping("/tableImport")
	public String tableImport(HttpServletRequest request, HttpServletResponse response) throws Exception{

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file");
		Integer platformType = RequestUtils.
				getInt(request, "platform_type", "platform_type is null");
		Integer shopNo = RequestUtils.getInt(request, "shop_no", "shop_no is null");
		Integer type = RequestUtils.getInt(request,"table_type","table_type is null");

		String originalFilename = file.getOriginalFilename();
		logger.info("[{}] originalFilename:{}",originalFilename);
		//给名字后加上时间戳
		String[] split = originalFilename.split("\\.");
		originalFilename = split[0] + DateUtils.formatNewDate(new Date())+"."+split[1];
		logger.info("[{}] originalFilename:{}",originalFilename);

		TableFlow tableFlow = new TableFlow();
		tableFlow.setImportDate(new Date());
		tableFlow.setTableName(originalFilename);
		tableFlow.setShopNo(shopNo);
		tableFlow.setPlatformType(platformType);
		tableFlow.setType(type);
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
			tableFlow.setErrorMessage("");
			tableFlowService.saveTableFlow(tableFlow);
		} catch (ServiceException e) {
			return ResponseUtils.getFailApiResponseStr(e.getCode(), e.getServiceMessage());
		}
		return ResponseUtils.getSuccessResponseStr("上传成功");
	}


}