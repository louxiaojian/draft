package cn.zmdx.draft.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.zmdx.draft.dao.interfaces.CycleDao;
import cn.zmdx.draft.entity.PageResult;
import cn.zmdx.draft.entity.User;

public class CycleDaoImpl extends ParentDAOImpl implements CycleDao {
	public CycleDaoImpl(HibernateTemplate template) {
		super(template);
	}

	@Override
	public Object getEntity(Class entityClass, Serializable id) {
		return this.template.get(entityClass, id);
	}

	@Override
	public void saveEntity(Object obj) {
		this.template.save(obj);
	}

	@Override
	public void updateEntity(Object obj) {
		this.template.update(obj);
	}

	@Override
	public void deleteEntity(Object obj) {
		this.template.delete(obj);
	}
	
	@Override
	public PageResult queryCycles(Map<String, String> filterMap) {
		StringBuffer queryString = new StringBuffer();
		StringBuffer queryCountString = new StringBuffer();
		queryCountString
				.append("select count(*) from (select c.id from cycle c left join themes th on th.id=c.theme_id where 1=1  ");
		queryString
				.append("select c.id,cycle_no,starttime,signup_endtime,endtime,status,theme_id,th.name from cycle c left join themes th on th.id=c.theme_id where 1=1 ");
		if (filterMap != null && !filterMap.isEmpty()) {
			if (null != filterMap.get("cycleNo")
					&& !"".equals(filterMap.get("cycleNo"))) {
				queryCountString.append("and cycle_no like '%"
						+ filterMap.get("cycleNo") + "%' ");
				queryString.append("and cycle_no like '%" + filterMap.get("cycleNo")
						+ "%' ");
			}
			if (null != filterMap.get("starttime")
					&& !"".equals(filterMap.get("starttime"))) {
				queryCountString.append("and starttime >= '"
						+ filterMap.get("endtime") + "' ");
				queryString.append("and starttime >= '"
						+ filterMap.get("endtime") + "' ");
			}
			if (null != filterMap.get("endtime")
					&& !"".equals(filterMap.get("endtime"))) {
				queryCountString.append("and endtime <= '"
						+ filterMap.get("endtime") + "' ");
				queryString.append("and endtime <= '"
						+ filterMap.get("endtime") + "' ");
			}
			if (null != filterMap.get("status")
					&& !"".equals(filterMap.get("status"))) {
				queryCountString.append("and status = '"
						+ filterMap.get("status") + "'  ");
				queryString.append("and status = '"
						+ filterMap.get("status") + "'  ");
			}
			if (null != filterMap.get("themeId")
					&& !"".equals(filterMap.get("themeId"))) {
				queryCountString.append("and theme_id = '"
						+ filterMap.get("themeId") + "'  ");
				queryString.append("and theme_id = '"
						+ filterMap.get("themeId") + "'  ");
			}
		}
		queryString.append(" order by "+filterMap.get("sidx")+" "+filterMap.get("sord"));
		queryCountString.append(") t");
		return searchBySQL(queryCountString.toString(), queryString.toString(),
				filterMap);
	}

	@Override
	public PageResult queryThemes(Map<String, String> filterMap) {
		StringBuffer queryString = new StringBuffer();
		StringBuffer queryCountString = new StringBuffer();
		queryCountString
				.append("select count(*) from (select id from themes  where 1=1  ");
		queryString
				.append("select id,name,descs from themes  where 1=1 ");
		if (filterMap != null && !filterMap.isEmpty()) {
			if (null != filterMap.get("name")
					&& !"".equals(filterMap.get("name"))) {
				queryCountString.append("and name like '%"
						+ filterMap.get("name") + "%' ");
				queryString.append("and name like '%" + filterMap.get("name")
						+ "%' ");
			}
		}
		queryString.append(" order by "+filterMap.get("sidx")+" "+filterMap.get("sord"));
		queryCountString.append(") t");
		return searchBySQL(queryCountString.toString(), queryString.toString(),
				filterMap);
	}

	@Override
	public List queryInitData(String tableName, String columns) {
		Query query= this.getSession().createSQLQuery("select "+columns+" from "+tableName);
		return query.list();
	}

}
