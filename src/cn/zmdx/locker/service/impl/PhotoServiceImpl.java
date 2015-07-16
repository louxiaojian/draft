package cn.zmdx.locker.service.impl;

import java.io.Serializable;
import java.util.Map;

import cn.zmdx.locker.dao.interfaces.PhotoDao;
import cn.zmdx.locker.entity.PageResult;
import cn.zmdx.locker.entity.Photo;
import cn.zmdx.locker.entity.ReviewRecords;
import cn.zmdx.locker.service.interfaces.PhotoService;

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
		Photo photo;
		for (int i = 0; i < id.length; i++) {
			photo=(Photo)this.getEntity(Photo.class, Integer.parseInt(id[i]));
			photo.setAuditingDate(rr.getDatetime());
			photo.setStatus(rr.getStatus());
			this.photoDao.updateEntity(photo);
			ReviewRecords rrecord=new ReviewRecords();
			rrecord.setDatetime(rr.getDatetime());
			rrecord.setDescs(rr.getDescs());
			rrecord.setStatus(rr.getStatus());
			rrecord.setPhotoId(photo.getId());
			this.photoDao.saveEntity(rrecord);
		}
	}

	@Override
	public void deletePhotoByIds(String ids) {
		this.photoDao.executeSql("delete from photo where id in("+ids+")");
		this.photoDao.executeSql("delete from review_records where photo_id in ("+ids+")");
	}

}
