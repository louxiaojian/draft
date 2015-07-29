package cn.zmdx.draft.service.impl;

import java.io.Serializable;
import java.util.Map;

import cn.zmdx.draft.dao.interfaces.PhotoDao;
import cn.zmdx.draft.entity.PageResult;
import cn.zmdx.draft.entity.Photo;
import cn.zmdx.draft.entity.PictureSet;
import cn.zmdx.draft.entity.ReviewRecords;
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
		for (int i = 0; i < id.length; i++) {
			ps=(PictureSet)this.getEntity(PictureSet.class, Integer.parseInt(id[i]));
			ps.setAuditingDate(rr.getDatetime());
			ps.setStatus(rr.getStatus());
			this.photoDao.updateEntity(ps);
			ReviewRecords rrecord=new ReviewRecords();
			rrecord.setDatetime(rr.getDatetime());
			rrecord.setDescs(rr.getDescs());
			rrecord.setStatus(rr.getStatus());
			rrecord.setPhotoSetId(ps.getId());
			this.photoDao.saveEntity(rrecord);
		}
	}

	@Override
	public void deletePhotoByIds(String ids) {
		this.photoDao.executeSql("delete from photo where id in("+ids+")");
		this.photoDao.executeSql("delete from review_records where photo_id in ("+ids+")");
	}

}
