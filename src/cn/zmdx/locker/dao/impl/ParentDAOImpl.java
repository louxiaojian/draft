package cn.zmdx.locker.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.zmdx.locker.dao.interfaces.ParentDAO;
import cn.zmdx.locker.entity.PageResult;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ParentDAOImpl extends HibernateDaoSupport  implements ParentDAO  {
	HibernateTemplate template;

	public ParentDAOImpl(HibernateTemplate template) {
		this.template = template;
	}
	public ParentDAOImpl() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public Object getEntity(Class entityClass, Serializable id) {
		// TODO Auto-generated method stub
		return template.get(entityClass, id);
	}
	@Override
	public void saveEntity(Object obj) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateEntity(Object obj) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteEntity(Object obj) {
		// TODO Auto-generated method stub
		
	}
	public PageResult searchBySQL(String countsql, String querysql,
			Map filterMap) {
		int page = (Integer) filterMap.get("page");
		int rows = (Integer) filterMap.get("rows");
		int start = rows * (page - 1); // 开始的记录
		// 获取记录总数
		Query query = getSession().createSQLQuery(countsql);
		List<Number> count = query.list();
		int total = Integer.parseInt(count.get(0).toString());

		// 获取当前页的结果集
		query = getSession().createSQLQuery(querysql);
		query.setFirstResult(start);
		query.setMaxResults(rows);
		List<?> datas = query.list();
		PageResult pm = new PageResult();
		pm.setRowCount(total);
		pm.setData(datas);
		return pm;
	}

}
