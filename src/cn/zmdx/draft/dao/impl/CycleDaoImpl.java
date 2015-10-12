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
		this.getSession().createSQLQuery("set NAMES utf8mb4").executeUpdate();
		this.template.save(obj);
	}

	@Override
	public void updateEntity(Object obj) {
		this.getSession().createSQLQuery("set NAMES utf8mb4").executeUpdate();
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
				.append("select count(*) from (SELECT id FROM theme_cycle where 1=1  ");
		queryString
				.append("SELECT id,theme_title,starttime,endtime,status,bg_url,descs,tag_url,detail_image_url,inside_detail_image_url,vote_start_time,vote_end_time,web_detail_url,web_title_url FROM theme_cycle where 1=1 ");
		if (filterMap != null && !filterMap.isEmpty()) {
			if (null != filterMap.get("themeTitle")
					&& !"".equals(filterMap.get("themeTitle"))) {
				queryCountString.append("and theme_title like '%"
						+ filterMap.get("themeTitle") + "%' ");
				queryString.append("and theme_title like '%" + filterMap.get("themeTitle")
						+ "%' ");
			}
			if (null != filterMap.get("starttime")
					&& !"".equals(filterMap.get("starttime"))) {
				queryCountString.append("and starttime >= '"
						+ filterMap.get("starttime") + "' ");
				queryString.append("and starttime >= '"
						+ filterMap.get("starttime") + "' ");
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
	public List queryInitData(String tableName, String columns, String whereCol, String whereVal) {
		StringBuffer sb=new StringBuffer("select "+columns+" from "+tableName);
		if(!"".equals(whereCol)&&whereCol!=null){
			sb.append(" where "+whereCol+"='"+whereVal+"'");
		}
		Query query= this.getSession().createSQLQuery(sb.toString());
		return query.list();
	}

}
