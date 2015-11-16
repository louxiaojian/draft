package cn.zmdx.draft.dao.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.zmdx.draft.entity.PageResult;
import cn.zmdx.draft.entity.User;

public interface CycleDao extends ParentDAO {

	/**
	 * 查询所有的选秀周期
	 * @author louxiaojian
	 * @date： 日期：2015-7-13 时间：下午5:07:12
	 * @throws IOException
	 */
	public PageResult queryCycles(Map<String, String> filterMap);

	/**
	 * 查询所有的选秀主题
	 * @author louxiaojian
	 * @date： 日期：2015-7-13 时间：下午5:07:12
	 * @throws IOException
	 */
	public PageResult queryThemes(Map<String, String> filterMap);

	/**
	 * 获取相应表中的相应列
	 * @author louxiaojian
	 * @date： 日期：2015-7-14 时间：下午3:58:47
	 * @param tableName
	 * @param columns
	 * @param whereVal 
	 * @param whereCol 
	 * @return
	 */
	public List queryInitData(String tableName, String columns, String whereCol, String whereVal);

	/**
	 * 获取公告栏
	 * @author louxiaojian
	 * @date： 日期：2015-11-16 时间：上午11:37:30
	 * @param filterMap
	 * @return
	 */
	public PageResult queryBulletinBoard(Map<String, String> filterMap);
}
