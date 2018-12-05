/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.sys.service;

import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.common.utils.CacheUtils;
import com.hzkans.crm.modules.sys.dao.DictDao;
import com.hzkans.crm.modules.sys.entity.Dict;
import com.hzkans.crm.modules.sys.utils.DictUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 字典Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class DictService extends CrudService<DictDao, Dict> {
	
	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new Dict());
	}

	@Transactional(readOnly = false)
	public void save(Dict dict) {
		super.save(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	@Transactional(readOnly = false)
	public void delete(Dict dict) {
		super.delete(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}


	/**
	 * 根据字典type查询
	 * @param type
	 * @return
	 */
	public List<Dict> findListByType(String type){
		Dict dict = new Dict();
		dict.setType(type);
		List<Dict> dictList = findList(dict);
		return dictList;

	}



}
