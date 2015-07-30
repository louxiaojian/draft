package cn.zmdx.draft.service.interfaces;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.zmdx.draft.entity.PageResult;
import cn.zmdx.draft.entity.ReviewRecords;

public interface PhotoService {
	/**
	 * 根据id获取对象
	 * @author louxiaojian
	 * @date： 日期：2015-7-7 时间：上午11:31:10
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public Object getEntity(Class entityClass, Serializable id);
	/**
	 * 保存对象
	 * @author louxiaojian
	 * @date： 日期：2015-7-7 时间：上午11:31:29
	 * @param obj
	 * @return
	 */
	public void saveEntity(Object obj);
	/**
	 * 修改对象
	 * @author louxiaojian
	 * @date： 日期：2015-7-7 时间：上午11:31:41
	 * @param obj
	 * @return
	 */
	public void updateEntity(Object obj);
	/**
	 * 删除对象
	 * @author louxiaojian
	 * @date： 日期：2015-7-7 时间：上午11:32:55
	 * @param obj
	 * @return
	 */
	public void deletePhoto(Object obj);
	/**
	 * 查询所有的照片
	 * @author louxiaojian
	 * @date： 日期：2015-7-13 时间：下午5:07:12
	 * @throws IOException
	 */
	public PageResult queryPhotos(Map<String, String> filterMap);
	/**
	 * 审核图片
	 * @author louxiaojian
	 * @date： 日期：2015-7-15 时间：下午3:45:27
	 * @param ids
	 * @param rr
	 */
	public void auditing(String ids, ReviewRecords rr);
	/**
	 * 根据id删除photo
	 * @author louxiaojian
	 * @date： 日期：2015-7-15 时间：下午4:57:15
	 * @param ids
	 */
	public void deletePhotoByIds(String ids);
	/**
	 * 获取照片集所有照片
	 * @author louxiaojian
	 * @date： 日期：2015-7-29 时间：下午4:18:04
	 * @throws IOException
	 */
	public PageResult queryPhotoByPictureSetId(Map<String, String> filterMap);
	/**
	 * 获取照片集所有照片
	 * @author louxiaojian
	 * @date： 日期：2015-7-29 时间：下午4:18:04
	 * @throws IOException
	 */
	public List queryPhotoByPictureSetId(String pictureSetId);
	/**
	 * 获取用户真人验证照片
	 * @author louxiaojian
	 * @date： 日期：2015-7-30 时间：下午2:30:42
	 * @param userId
	 * @return
	 */
	public List queryVerificationPhotoByUserId(String userId);
}
