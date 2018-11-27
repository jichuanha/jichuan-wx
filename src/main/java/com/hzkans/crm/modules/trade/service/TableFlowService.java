
package com.hzkans.crm.modules.trade.service;

import com.hzkans.crm.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.hzkans.crm.modules.trade.entity.TableFlow;
import com.hzkans.crm.modules.trade.dao.TableFlowDao;

/**
 * 表格流程Service
 * @author chuan
 * @version 2018-11-26
 */
@Service
@Transactional
public class TableFlowService extends CrudService<TableFlowDao, TableFlow> {

	/**
	 * 添加表格流程
	 * @param tableFlow
	 */
	public void saveTableFlow(TableFlow tableFlow) {
		save(tableFlow);
	}
	
}