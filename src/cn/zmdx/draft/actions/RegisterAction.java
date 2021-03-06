package cn.zmdx.draft.actions;

import cn.zmdx.draft.entity.User;
import cn.zmdx.draft.service.impl.UserServiceImpl;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class RegisterAction extends ActionSupport implements ModelDriven<User> {
	private User user = new User();
	private UserServiceImpl userService;
	private String result;

	@Override
	public User getModel() {
		return user;
	}

	public String getResult() {
		return result;
	}

	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	public String execute() {
		userService.addUser(user);
		result = "注册成功";
		return SUCCESS;
	}
}
