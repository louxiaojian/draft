package cn.zmdx.draft.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.zmdx.draft.dao.interfaces.PhotoDao;
import cn.zmdx.draft.entity.PageResult;

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
		StringBuffer sqlCount=new StringBuffer("select count(*) from (select p.id from picture_set p left join users u on p.userid=u.id left join cycle_photo_set cp on p.id =cp.photo_set_id left join cycle c on cp.cycle_id=c.id left join themes th on th.id =c.theme_id where 1=1 ");
		StringBuffer sql=new StringBuffer("select p.id,p.uploadDate,p.descs,p.type,p.status,p.praise,p.tread,p.auditingDate,p.userid,p.report,p.view,u.loginname,c.cycle_no,th.name from picture_set p left join users u on p.userid=u.id left join cycle_photo_set cp on p.id =cp.photo_set_id left join cycle c on cp.cycle_id=c.id left join themes th on th.id =c.theme_id where 1=1 ");
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

	@Override
	public PageResult queryPhotoByPictureSetId(Map<String, String> filterMap) {
		StringBuffer sqlCount=new StringBuffer("select count(*) from (select id,photoUrl,uploadDate,userid,picture_set_id from photo where 1=1 ");
		StringBuffer sql=new StringBuffer("select id,photoUrl,uploadDate,userid,picture_set_id from photo where 1=1 ");
		if(filterMap!=null&&!filterMap.isEmpty()){
			if(!"".equals(filterMap.get("pictureSetId"))&&filterMap.get("pictureSetId")!=null){
				sql.append(" and picture_set_id ="+filterMap.get("pictureSetId"));
				sqlCount.append(" and picture_set_id ="+filterMap.get("pictureSetId"));
			}
		}
		sql.append(" order by "+filterMap.get("sidx")+" "+filterMap.get("sord"));
		sqlCount.append(") t");
		return searchBySQL(sqlCount.toString(), sql.toString(), filterMap);
	}

	@Override
	public List queryPhotoByPictureSetId(String pictureSetId) {
		Query query=getSession().createSQLQuery("select photoUrl from photo where picture_set_id =?");
		query.setInteger(0, Integer.parseInt(pictureSetId));
		return query.list();
	}

	@Override
	public List queryVerificationPhotoByUserId(String userId) {
		Query query=getSession().createSQLQuery("select photoUrl from photo where userid =? and picture_set_id=0");
		query.setInteger(0, Integer.parseInt(userId));
		return query.list();
	}
}
