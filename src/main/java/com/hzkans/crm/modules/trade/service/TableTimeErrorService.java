package com.hzkans.crm.modules.trade.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.modules.trade.entity.TableTimeError;
import com.hzkans.crm.modules.trade.dao.TableTimeErrorDao;

/**
 * 错误信息展示Service
 * @author jc
 * @version 2018-12-07
 */
@Service
public class TableTimeErrorService extends CrudService<TableTimeErrorDao, TableTimeError> {

	public TableTimeError get(String id) {
		return super.get(id);
	}
	
	public List<TableTimeError> findList(TableTimeError tableTimeError) {
		return super.findList(tableTimeError);
	}
	
	public Page<TableTimeError> findPage(Page<TableTimeError> page, TableTimeError tableTimeError) {
		return super.findPage(page, tableTimeError);
	}
	
	@Transactional(readOnly = false)
	public void save(TableTimeError tableTimeError) {
		super.save(tableTimeError);
	}
	
	@Transactional(readOnly = false)
	public void delete(TableTimeError tableTimeError) {
		super.delete(tableTimeError);
	}
	
}