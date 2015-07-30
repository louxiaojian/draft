package cn.zmdx.draft.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.zmdx.draft.dao.interfaces.PhotoDao;
import cn.zmdx.draft.entity.PageResult;
import cn.zmdx.draft.entity.PictureSet;
import cn.zmdx.draft.entity.ReviewRecords;
import cn.zmdx.draft.entity.User;
import cn.zmdx.draft.service.interfaces.PhotoService;

public class PhotoServiceImpl implements PhotoService {
	private PhotoDao photoDao;
	public PhotoServiceImpl(PhotoDao photoDao){
		this.photoDao=photoDao;
	}

	@Override
	public Object getEntity(Class entityClass, Serializable id) {
		return this.photoDao.getEntity(entityClass, id);
	}

	@Override
	public void saveEntity(Object obj) {
		this.photoDao.saveEntity(obj);
	}

	@Override
	public void updateEntity(Object obj) {
		this.photoDao.updateEntity(photoDao);
	}

	@Override
	public void deletePhoto(Object obj) {
		this.photoDao.deleteEntity(obj);
	}

	@Override
	public PageResult queryPhotos(Map<String, String> filterMap) {
		return this.photoDao.queryPhotos(filterMap);
	}
	
	@Override
	public void auditing(String ids, ReviewRecords rr) {
		String [] id=ids.split(",");
		PictureSet ps;
		User user;
		for (int i = 0; i < id.length; i++) {
			ReviewRecords rrecord=new ReviewRecords();
			rrecord.setDatetime(rr.getDatetime());
			rrecord.setDescs(rr.getDescs());
			rrecord.setStatus(rr.getStatus());
			if(rr.getType()==0){//审核图集
				ps=(PictureSet)this.getEntity(PictureSet.class, Integer.parseInt(id[i]));
				ps.setAuditingDate(rr.getDatetime());
				ps.setStatus(rr.getStatus());
				this.photoDao.updateEntity(ps);
				rrecord.setPhotoSetId(ps.getId());
			}else if(rr.getType()==1){//审核真人验证
				user=(User)this.getEntity(User.class, Integer.parseInt(id[i]));
				user.setIsvalidate(rr.getStatus());
				user.setValidateDate(new Date());
				this.photoDao.updateEntity(user);
				rrecord.setUserId(user.getId());
			}
			rrecord.setType(rr.getType());
			rrecord.setOperatorId(rr.getOperatorId());
			this.photoDao.saveEntity(rrecord);
		}
	}

	@Override
	public void deletePhotoByIds(String ids) {
		this.photoDao.executeSql("delete from photo where id in("+ids+")");
		this.photoDao.executeSql("delete from review_records where photo_id in ("+ids+")");
	}

	@Override
	public PageResult queryPhotoByPictureSetId(Map<String, String> filterMap) {
		return this.photoDao.queryPhotoByPictureSetId(filterMap);
	}
	@Override
	public List queryPhotoByPictureSetId(String pictureSetId) {
		return this.photoDao.queryPhotoByPictureSetId(pictureSetId);
	}
	@Override
	public List queryVerificationPhotoByUserId(String userId) {
		return this.photoDao.queryVerificationPhotoByUserId(userId);
	}

}
