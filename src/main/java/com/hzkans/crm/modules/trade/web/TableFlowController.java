
package com.hzkans.crm.modules.trade.web;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.utils.DateUtils;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.trade.constants.TableFlowStatusEnum;
import com.hzkans.crm.modules.trade.entity.TableFlow;
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

	/*private static final String UPLOAD_ADDRESS = "/deploy/data/www/static/upload/";*/
	private static final String UPLOAD_ADDRESS = "E:/youxi/";
	private static final String ORDER = "订单";
	private static final String BUYER = "顾客";
	private static final String EVALUATE = "评价";
	private static final String ERROR_MESSAGE = "表格格式有误,上传订单请在表格名称前加 订单 两个字," +
			"上传评价在表格名称前加 评价 两个字,上传顾客信息在表格名称前加 顾客 两个字";
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
		String originalFilename = file.getOriginalFilename();
		logger.info("[{}] originalFilename:{}",originalFilename);
		//给名字后加上时间戳
		String[] split = originalFilename.split("\\.");
		originalFilename = split[0] + DateUtils.formatNewDate(new Date())+"."+split[1];
		logger.info("[{}] originalFilename:{}",originalFilename);
		String substring = originalFilename.substring(0, 2);
		logger.info("[{}] substring:{}",substring);
		TableFlow tableFlow = new TableFlow();
		tableFlow.setImportDate(new Date());
		tableFlow.setTableName(originalFilename);
		if(!substring.equals(ORDER) && !substring.equals(BUYER) && !substring.equals(EVALUATE)) {
			logger.info("导入的订单格式有误");
			//保存上传记录
			tableFlow.setStatus(TableFlowStatusEnum.IMPORT_SYSTEM_FAIL.getCode());
			tableFlow.setErrorMessage(ERROR_MESSAGE);
			tableFlowService.saveTableFlow(tableFlow);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR,ERROR_MESSAGE);
		}
		long size = file.getSize();
		if(size > MAX_SIZE) {
			logger.info("导入文件过大");
			//保存上传记录
			tableFlow.setStatus(TableFlowStatusEnum.IMPORT_SYSTEM_FAIL.getCode());
			tableFlow.setErrorMessage(MAX_SIZE_ERROR);
			tableFlowService.saveTableFlow(tableFlow);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR,MAX_SIZE_ERROR);
		}
		File newFile = new File(UPLOAD_ADDRESS+originalFilename);
		if(!newFile.exists()) {
			newFile.mkdirs();
		}
		file.transferTo(newFile);

		//保存上传记录
		tableFlow.setStatus(TableFlowStatusEnum.IMPORT_SYSTEM_SUCCESS.getCode());
		tableFlow.setErrorMessage("");
		tableFlowService.saveTableFlow(tableFlow);
		return ResponseUtils.getSuccessResponseStr("上传成功");
	}



}