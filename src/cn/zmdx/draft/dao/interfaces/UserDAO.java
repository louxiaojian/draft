package cn.zmdx.draft.dao.interfaces;

import java.util.Map;

import cn.zmdx.draft.entity.PageResult;
import cn.zmdx.draft.entity.User;

public interface UserDAO extends ParentDAO {
	
	User get(Integer id);
	
    int save(User user);

	String getPassword(User user);
	
	/**
	 * 获取匹配用户
	 */
	User getMatchUser(String loginname,String password);
	
	User getUsersById(int userid);
	
    void update(Object object);

    /**
     * 查询所有用户
     * @author louxiaojian
     * @date： 日期：2015-7-30 时间：上午11:51:54
     * @param filterMap
     * @return
     */
	public PageResult queryUsers(Map<String, String> filterMap);
	
}
