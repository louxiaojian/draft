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
		this.getSession().createSQLQuery("set NAMES utf8mb4").executeUpdate();
		this.template.update(obj);
	}

	@Override
	public void deleteEntity(Object obj) {
		this.template.delete(obj);
	}

	@Override
	public PageResult queryPhotos(Map<String, String> filterMap) {
		StringBuffer sqlCount=new StringBuffer("select count(*) from (select p.id from picture_set p left join users u on p.userid=u.id left join cycle_photo_set cp on p.id =cp.photo_set_id left join theme_cycle tc on cp.theme_cycle_id=tc.id where 1=1 ");
		StringBuffer sql=new StringBuffer("select p.id,p.uploadDate,p.descs,p.type,p.status,p.praise,p.tread,p.auditingDate,p.userid,p.report,p.view,u.loginname,tc.theme_title from picture_set p left join users u on p.userid=u.id left join cycle_photo_set cp on p.id =cp.photo_set_id left join theme_cycle tc on cp.theme_cycle_id=tc.id where 1=1 ");
		if(filterMap!=null&&!filterMap.isEmpty()){
			if(!"".equals(filterMap.get("themeId"))&&filterMap.get("themeId")!=null){
				sql.append(" and tc.id ='"+filterMap.get("themeId")+"'");
				sqlCount.append(" and tc.id ='"+filterMap.get("themeId")+"'");
			}

			if(!"".equals(filterMap.get("cycleId"))&&filterMap.get("cycleId")!=null){
				sql.append(" and cp.theme_cycle_id ='"+filterMap.get("cycleId")+"'");
				sqlCount.append(" and cp.theme_cycle_id ='"+filterMap.get("cycleId")+"'");
			}
			if(!"".equals(filterMap.get("type"))&&filterMap.get("type")!=null){
				sql.append(" and type ='"+filterMap.get("type")+"'");
				sqlCount.append(" and type ='"+filterMap.get("type")+"'");
			}
			if(!"".equals(filterMap.get("status"))&&filterMap.get("status")!=null){
				sql.append(" and p.status ='"+filterMap.get("status")+"'");
				sqlCount.append(" and p.status ='"+filterMap.get("status")+"'");
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

	@Override
	public PageResult viewReviewRecords(Map<String, String> filterMap) {
		StringBuffer sqlCount=new StringBuffer("select count(*) from (SELECT rr.id FROM review_records rr left join users u on u.id=rr.operator_id where 1=1 ");
		StringBuffer sql=new StringBuffer("select id,status,photo_set_id,descs,datetime,operator_id,operator_name,user_id,type from (SELECT rr.id,rr.status,photo_set_id,rr.descs,rr.datetime,rr.operator_id,u.loginname as operator_name,rr.user_id,rr.type FROM review_records rr left join users u on u.id=rr.operator_id where 1=1 ");
		if(filterMap!=null&&!filterMap.isEmpty()){
			if("0".equals(filterMap.get("type"))){
				if(!"".equals(filterMap.get("pictureSetId"))&&filterMap.get("pictureSetId")!=null){
					sql.append(" and photo_set_id ="+filterMap.get("pictureSetId"));
					sqlCount.append(" and photo_set_id ="+filterMap.get("pictureSetId"));
				}
			}else if("1".equals(filterMap.get("type"))){
				if(!"".equals(filterMap.get("userId"))&&filterMap.get("userId")!=null){
					sql.append(" and user_id ="+filterMap.get("userId"));
					sqlCount.append(" and user_id ="+filterMap.get("userId"));
				}
			}
		}
		sql.append(" order by "+filterMap.get("sidx")+" "+filterMap.get("sord"));
		sql.append(") t");
		sqlCount.append(") t");
		return searchBySQL(sqlCount.toString(), sql.toString(), filterMap);
	}
}
