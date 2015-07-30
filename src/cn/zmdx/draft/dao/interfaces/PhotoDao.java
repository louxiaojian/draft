package cn.zmdx.draft.dao.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.zmdx.draft.entity.PageResult;
import cn.zmdx.draft.entity.User;

public interface PhotoDao extends ParentDAO {

	/**
	 * 查询所有的照片
	 * @author louxiaojian
	 * @date： 日期：2015-7-13 时间：下午5:07:12
	 * @throws IOException
	 */
	public PageResult queryPhotos(Map<String, String> filterMap);

	/**
	 * 执行sql语句
	 * @author louxiaojian
	 * @date： 日期：2015-7-15 时间：下午4:59:43
	 * @param sql
	 */
	public void executeSql(String sql);

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
	 * @date： 日期：2015-7-30 时间：下午2:31:13
	 * @param userId
	 * @return
	 */
	public List queryVerificationPhotoByUserId(String userId);
}
