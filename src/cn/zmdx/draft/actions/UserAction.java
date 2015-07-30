package cn.zmdx.draft.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.zmdx.draft.entity.PageResult;
import cn.zmdx.draft.service.impl.UserServiceImpl;
import cn.zmdx.draft.util.DataUtil;

import com.opensymphony.xwork2.ActionSupport;

public class UserAction  extends ActionSupport{

	private UserServiceImpl userService;
	private String result;
	private String page;
	private String rows;
	private String sidx;
	private String sord;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	public UserServiceImpl getUserService() {
		return userService;
	}
	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}
	/*
	 * 取得页面传过来的列表参数
	 */
	public Map getPagerMap() {
		if (page == null)
			page = "1";
		if (sidx == null)
			sidx = "id";
		if (rows == null)
			rows = "10";
		if (sord == null)
			sord = "asc";
		Map filterMap = new HashMap();// 存储参数的map
		filterMap.put("page", Integer.parseInt(page));
		filterMap.put("rows", Integer.parseInt(rows));
		filterMap.put("sidx", sidx);
		filterMap.put("sord", sord);
		return filterMap;
	}
	/**
	 * 查询所有的用户
	 * 
	 * @author louxiaojian
	 * @date： 日期：2015-7-13 时间：下午5:07:12
	 * @throws IOException
	 */
	public void queryUsers() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		try {
			response.setContentType("text/json; charset=utf-8");
			String loginname = request.getParameter("loginname");
			String flag = request.getParameter("flag");
			String orgId = request.getParameter("orgId");
			String isvalidate = request.getParameter("isvalidate");
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			Map<String, String> filterMap = getPagerMap();
			//id,username,loginname,password,address,telephone,name,flag,isvalidate,headPortrait,age,introduction,registration_date,org_id
			String[] viewArray = { "ID", "username", "loginname","password","address","telephone","name",
					"flag:[{'0':'未激活','1':'正常','2':'冻结'}]",
					"isvalidate:[{'0':'未验证','1':'验证成功','2':'验证失败'}]", "headPortrait",
					"age", "introduction", "registration_date","org_id:[{'0':'普通用户','1':'管理员'}]","gender:[{'0':'未知','1':'男','2':'女'}]","validateDate" };
			if (loginname != null && !"".equals(loginname)) {
				filterMap.put("loginname", loginname);
			}
			if (flag != null && !"".equals(flag)) {
				filterMap.put("flag", flag);
			}
			if (orgId != null && !"".equals(orgId)) {
				filterMap.put("orgId", orgId);
			}
			if (isvalidate != null && !"".equals(isvalidate)) {
				filterMap.put("isvalidate", isvalidate);
			}
			if (starttime != null && !"".equals(starttime)) {
				filterMap.put("starttime", starttime);
			}
			if (endtime != null && !"".equals(endtime)) {
				filterMap.put("endtime", endtime);
			}
			PageResult result = (PageResult) userService.queryUsers(filterMap);
			String returnStr = DataUtil.getColumnJson(result, viewArray, rows,
					page);
			out.print(returnStr);
		} catch (Exception e) {
			e.printStackTrace();
			out.print("error");
		} finally {
			out.flush();
			out.close();
		}
	}
}
