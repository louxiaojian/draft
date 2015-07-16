package cn.zmdx.locker.dao.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.zmdx.locker.entity.PageResult;
import cn.zmdx.locker.entity.User;

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
}
