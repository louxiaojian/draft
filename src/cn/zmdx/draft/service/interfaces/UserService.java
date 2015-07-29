package cn.zmdx.draft.service.interfaces;

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
   
}
