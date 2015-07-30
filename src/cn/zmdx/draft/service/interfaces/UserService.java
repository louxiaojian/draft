package cn.zmdx.draft.service.interfaces;

import java.util.Map;

import cn.zmdx.draft.entity.PageResult;
import cn.zmdx.draft.entity.User;

public interface UserService {
   int addUser(User user);
   boolean verifyUser(User user);
   User findUsersById(int userid);
   /**
	 * 修改用户信息
	 * @throws Exception
	 */
   void updateUserInfo(User user) throws Exception;
   /**
    * 查询所有用户
    * @author louxiaojian
    * @date： 日期：2015-7-30 时间：上午11:51:18
    * @param filterMap
    * @return
    */
   public PageResult queryUsers(Map<String, String> filterMap);
   
}
