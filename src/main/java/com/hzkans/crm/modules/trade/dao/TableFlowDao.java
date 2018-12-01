
package com.hzkans.crm.modules.trade.dao;


import com.hzkans.crm.common.persistence.CrudDao;
import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.persistence.PagePara;
import com.hzkans.crm.common.persistence.annotation.MyBatisDao;
import com.hzkans.crm.modules.trade.entity.TableFlow;

import java.util.List;

/**
 * 表格流程DAO接口
 * @author chuan
 * @version 2018-11-26
 */
@MyBatisDao
public interface TableFlowDao extends CrudDao<TableFlow> {

    List<TableFlow> listTableFlowPage(PagePara<TableFlow> flowPage);

    int countTableFlowPage(PagePara<TableFlow> flowPage);
}