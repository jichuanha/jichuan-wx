
package com.hzkans.crm.modules.trade.web;

import com.hzkans.crm.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hzkans.crm.modules.trade.service.TableFlowService;

/**
 * 表格流程Controller
 * @author chuan
 * @version 2018-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/trade/tableFlow")
public class TableFlowController extends BaseController {

	@Autowired
	private TableFlowService tableFlowService;


	@RequestMapping("/tableImport")
	public String importTable() {
		return "";
	}



}