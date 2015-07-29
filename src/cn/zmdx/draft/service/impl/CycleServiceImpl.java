package cn.zmdx.draft.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.zmdx.draft.dao.interfaces.CycleDao;
import cn.zmdx.draft.entity.Cycle;
import cn.zmdx.draft.entity.PageResult;
import cn.zmdx.draft.entity.User;
import cn.zmdx.draft.service.interfaces.CycleService;

public class CycleServiceImpl implements CycleService {
	private CycleDao cycleDao;

	public CycleServiceImpl(CycleDao cycleDao) {
		this.cycleDao = cycleDao;
	}

	@Override
	public Object getEntity(Class entityClass, Serializable id) {
		return this.cycleDao.getEntity(entityClass, id);
	}

	@Override
	public void saveEntity(Object obj) {
		this.cycleDao.saveEntity(obj);
	}

	@Override
	public void updateEntity(Object obj) {
		this.cycleDao.updateEntity(obj);

	}

	@Override
	public void deleteEntity(Class entityClass,String ids) {
		String [] id=ids.split(",");
		for (int i = 0; i < id.length; i++) {
			this.cycleDao.deleteEntity(this.cycleDao.getEntity(entityClass, Integer.parseInt(id[i])));
		}
	}

	@Override
	public PageResult queryCycles(Map<String, String> filterMap) {
		return this.cycleDao.queryCycles(filterMap);
	}

	@Override
	public PageResult queryThemes(Map<String, String> filterMap) {
		return this.cycleDao.queryThemes(filterMap);
	}

	@Override
	public List queryInitData(String tableName, String columns) {
		return this.cycleDao.queryInitData(tableName, columns);
	}
	

}
