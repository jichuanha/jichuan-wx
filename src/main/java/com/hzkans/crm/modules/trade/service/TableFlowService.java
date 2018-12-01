
package com.hzkans.crm.modules.trade.service;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.persistence.PagePara;
import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.modules.trade.constants.TableFlowStatusEnum;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
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
@Transactional(rollbackFor = Exception.class)
public class TableFlowService extends CrudService<TableFlowDao, TableFlow> {

	@Autowired
	private TableFlowDao tableFlowDao;

	/**
	 * 添加表格流程
	 * @param tableFlow
	 */
	public void saveTableFlow(TableFlow tableFlow) throws ServiceException{
		if(null == tableFlow) {
			logger.error("tableFlow is null");
			throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID);
		}
		TableFlow tableFlow1 = get(tableFlow);
		if(tableFlow1 != null) {
			throw new ServiceException(ResponseEnum.DATE_ALREADY_EXIST);
		}
		try {
			save(tableFlow);
		} catch (Exception e) {
			logger.error("saveTableFlow error",e);
			throw new ServiceException(ResponseEnum.DATEBASE_SAVE_ERROR);
		}
	}

	/**
	 *	获取单个表格信息(通用查)
	 * @param table
	 * @return
	 * @throws ServiceException
	 */
	public TableFlow getTable(TableFlow table) throws ServiceException {
		TableFlow tableFlow = null;
		try {
			tableFlow = get(table);
		} catch (Exception e) {
			logger.error("getTable error",e);
			throw new ServiceException(ResponseEnum.S_E_SERVICE_ERROR);
		}
		return tableFlow;
	}

	/**
	 * 获取多个表格信息(通用查)
	 * @param table
	 * @return
	 * @throws ServiceException
	 */
	public List<TableFlow> getTables(TableFlow table) throws ServiceException {
		TradeUtil.isAllNull(table);
		List<TableFlow> tableFlow = null;
		try {
			tableFlow = findList(table);
		} catch (Exception e) {
			logger.error("getTable error",e);
			throw new ServiceException(ResponseEnum.S_E_SERVICE_ERROR);
		}
		return tableFlow;
	}


	/**
	 * 分页获取表格信息(通用查)
	 * @param flowPage
	 * @return
	 */
	public PagePara<TableFlow> getTablePages(PagePara<TableFlow> flowPage) throws ServiceException{
		TradeUtil.isAllNull(flowPage);
		PagePara<TableFlow> tableFlowPage = null;
		try {
			tableFlowPage = new PagePara<>();
			List<TableFlow> tableFlows = tableFlowDao.listTableFlowPage(flowPage);
			for (TableFlow tableFlow : tableFlows) {
				tableFlow.setStatusStr(TableFlowStatusEnum
						.getTableFlowStatusEnum(tableFlow.getStatus()).getDesc());
				String[] split = tableFlow.getTableName().split("\\.");
				String bizCode = split[0];
				String substring = bizCode.substring(0 ,bizCode.length() - 14);
				tableFlow.setTableName(substring+"."+split[1]);
			}
			int totalCount = tableFlowDao.countTableFlowPage(flowPage);
			tableFlowPage.setList(tableFlows);
			tableFlowPage.setCount(totalCount);
		} catch (Exception e) {
			logger.error("getTablePages service error",e);
			throw new ServiceException(ResponseEnum.DATEBASE_QUERY_ERROR);
		}
		return tableFlowPage;
	}
}