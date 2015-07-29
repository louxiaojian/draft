package cn.zmdx.draft.dao.interfaces;

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
	
}
