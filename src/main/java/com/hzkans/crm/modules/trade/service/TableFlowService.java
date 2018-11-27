
package com.hzkans.crm.modules.trade.service;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.common.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.hzkans.crm.modules.trade.entity.TableFlow;
import com.hzkans.crm.modules.trade.dao.TableFlowDao;

import java.util.List;

/**
 * 表格流程Service
 * @author chuan
 * @version 2018-11-26
 */
@Service
@Transactional
public class TableFlowService extends CrudService<TableFlowDao, TableFlow> {

	@Autowired
	private TableFlowDao tableFlowDao;

	/**
	 * 添加表格流程
	 * @param tableFlow
	 */
	public void saveTableFlow(TableFlow tableFlow) {
		save(tableFlow);
	}

	public TableFlow getTable(TableFlow table) throws Exception {
		TableFlow tableFlow = null;
		try {
			tableFlow = get(table);
		} catch (Exception e) {
			logger.error("getTable error",e);
			throw new Exception(ResponseEnum.S_E_SERVICE_ERROR.getMsg());
		}
		return tableFlow;
	}

	public List<TableFlow> getTables(TableFlow table) throws Exception {
		List<TableFlow> tableFlow = null;
		try {
			tableFlow = findList(table);
		} catch (Exception e) {
			logger.error("getTable error",e);
			throw new Exception(ResponseEnum.S_E_SERVICE_ERROR.getMsg());
		}
		return tableFlow;
	}
	
}