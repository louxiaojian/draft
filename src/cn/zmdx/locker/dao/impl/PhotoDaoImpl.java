package cn.zmdx.locker.dao.impl;

import java.io.Serializable;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.zmdx.locker.dao.interfaces.PhotoDao;
import cn.zmdx.locker.entity.PageResult;

public class PhotoDaoImpl extends ParentDAOImpl implements PhotoDao {
	public PhotoDaoImpl(HibernateTemplate template) {
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
	public PageResult queryPhotos(Map<String, String> filterMap) {
		StringBuffer sqlCount=new StringBuffer("select count(*) from (select p.id from photo p left join users u on p.userid=u.id left join cycle_photo cp on p.id =cp.photo_id left join cycle c on cp.cycle_id=c.id left join themes th on th.id =c.theme_id where 1=1 ");
		StringBuffer sql=new StringBuffer("select p.id,p.photoUrl,p.uploadDate,p.descs,p.type,p.status,p.praise,p.tread,p.auditingDate,p.userid,p.report,u.loginname,c.cycle_no,th.name from photo p " +
				"left join users u on p.userid=u.id left join cycle_photo cp on p.id =cp.photo_id left join cycle c on cp.cycle_id=c.id left join themes th on th.id =c.theme_id where 1=1 ");
		if(filterMap!=null&&!filterMap.isEmpty()){
			if(!"".equals(filterMap.get("type"))&&filterMap.get("type")!=null){
				sql.append(" and type ='"+filterMap.get("type")+"'");
				sqlCount.append(" and type ='"+filterMap.get("type")+"'");
			}
			if(!"".equals(filterMap.get("status"))&&filterMap.get("status")!=null){
				sql.append(" and status ='"+filterMap.get("status")+"'");
				sqlCount.append(" and status ='"+filterMap.get("status")+"'");
			}
			if(!"".equals(filterMap.get("starttime"))&&filterMap.get("starttime")!=null){
				sql.append(" and uploadDate >='"+filterMap.get("starttime")+"'");
				sqlCount.append(" and uploadDate >='"+filterMap.get("starttime")+"'");
			}
			if(!"".equals(filterMap.get("endtime"))&&filterMap.get("endtime")!=null){
				sql.append(" and uploadDate <='"+filterMap.get("endtime")+"'");
				sqlCount.append(" and uploadDate <='"+filterMap.get("endtime")+"'");
			}
		}
		sql.append(" order by "+filterMap.get("sidx")+" "+filterMap.get("sord"));
		sqlCount.append(") t");
		return searchBySQL(sqlCount.toString(), sql.toString(), filterMap);
	}

	@Override
	public void executeSql(String sql){
		Query query=this.getSession().createSQLQuery(sql);
		query.executeUpdate();
	}
}
