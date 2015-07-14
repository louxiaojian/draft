package cn.zmdx.locker.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import cn.zmdx.locker.entity.Data_img_table;
import cn.zmdx.locker.entity.Data_table;
import cn.zmdx.locker.entity.PageResult;
import cn.zmdx.locker.entity.Tag;
import cn.zmdx.locker.entity.User;
import cn.zmdx.locker.service.impl.LockerServiceImpl;
import cn.zmdx.locker.util.StringUtil;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class LockerAction extends ActionSupport {
	Logger logger = Logger.getLogger(LockerAction.class);
	private Data_table dataTable;
	private LockerServiceImpl lockerService;
	private Data_img_table dataImgTable;
	private String result;
	private String page;
	private String rows;
	private String sidx;
	private String sord;

	public String getResult() {
		return result;
	}

	public void setLockerService(LockerServiceImpl lockerService) {
		this.lockerService = lockerService;
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

	public Data_table getDataTable() {
		return dataTable;
	}

	public void setDataTable(Data_table dataTable) {
		this.dataTable = dataTable;
	}

	public Data_img_table getDataImgTable() {
		return dataImgTable;
	}

	public void setDataImgTable(Data_img_table dataImgTable) {
		this.dataImgTable = dataImgTable;
	}

	public String getColumnJson(PageResult result, String[] viewArray) {
		int rowsInt = Integer.parseInt(rows);
		int totalRows = result.getRowCount(); // 所有的行数
		int totalPages = (totalRows + rowsInt - 1) / rowsInt; // 总页数
		List rowAll = result.getData(); // 查询出的所有记录
		JSONObject obj = new JSONObject();
		obj.put("page", "" + page);
		obj.put("total", totalPages);
		obj.put("records", "" + totalRows);
		JSONArray lineitemArray = new JSONArray();
		Iterator it = rowAll.iterator();
		while (it.hasNext()) {
			Object[] objlist = (Object[]) it.next();
			lineitemArray.add(getColumnValue(objlist, viewArray));
		}
		obj.put("rows", lineitemArray);
		return obj.toString();
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

	public JSONObject getColumnValue(Object[] objlist, String[] viewArray) {
		JSONObject objlineitem = new JSONObject();
		for (int i = 0; i < viewArray.length; i++) {
			if (!StringUtil.isEmpty(viewArray[i])) {
				if (objlist[i] != null) {
					if (viewArray[i].indexOf(":") > 0) {
						String realfieldName = viewArray[i].substring(0,
								viewArray[i].indexOf(":"));
						String jsonStr = viewArray[i].substring(viewArray[i]
								.indexOf(":") + 1);
						JSONArray json = JSONArray.fromObject(jsonStr);
						JSONObject job = json.getJSONObject(0);
						if (job.containsKey(objlist[i].toString())) {
							String realvalue = job.getString(objlist[i]
									.toString());
							if (realvalue != null) {
								objlineitem.put(realfieldName,
										realvalue.toString());
							}
						} else {
							objlineitem.put(realfieldName,
									objlist[i].toString());
						}
					} else {
						if (objlist[i] != null) {
							objlineitem
									.put(viewArray[i], objlist[i].toString());
						} else {
							objlineitem.put(viewArray[i], "");
						}
					}
				} else {
					objlineitem.put(viewArray[i], "");
				}
			}
		}
		return objlineitem;
	}

	/**
	 * 查询非图片数据数据
	 * 
	 * @throws IOException
	 */
	public void queryDataTable() throws IOException {
		try {
			ServletActionContext.getResponse().setContentType(
					"text/json; charset=utf-8");
			String title = ServletActionContext.getRequest().getParameter(
					"data_title");
			String start_date = ServletActionContext.getRequest().getParameter(
					"start_date");
			String end_date = ServletActionContext.getRequest().getParameter(
					"end_date");
			String data_type = ServletActionContext.getRequest().getParameter(
					"data_type");
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			Map<String, String> filterMap = getPagerMap();
			String[] viewArray = { "ID", "title", "url", "data_type" };
			if (title != null && !"".equals(title)) {
				filterMap.put("title", title);
			}
			if (start_date != null && !"".equals(start_date)) {
				filterMap.put("start_date", start_date);
			}
			if (end_date != null && !"".equals(end_date)) {
				filterMap.put("end_date", end_date);
			}
			if (data_type != null && !"".equals(data_type)) {
				filterMap.put("data_type", data_type);
			}
			PageResult result = (PageResult) lockerService
					.queryDataTable(filterMap);
			String returnStr = getColumnJson(result, viewArray);
			out.print(returnStr);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 查询图片数据
	 * 
	 * @throws IOException
	 */
	public void queryDataImgTable() throws IOException {
		try {
			ServletActionContext.getResponse().setContentType(
					"text/html; charset=utf-8");
			String title = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("data_title"));
			String start_date = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("start_date"));
			String end_date = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("end_date"));
			String custom_user = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("custom_user"));
			String type = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("type"));
			String edit_date = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("edit_date"));
			String data_sub = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("data_sub"));
			String collect_website = StringUtil
					.encodingUrl(ServletActionContext.getRequest()
							.getParameter("collect_website"));
			String stick = StringUtil
					.encodingUrl(ServletActionContext.getRequest()
							.getParameter("stick"));
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			Map<String, String> filterMap = getPagerMap();
			String[] viewArray = { "ID", "title", "url", "imgUrl",
					"type:[{'1':'头条','2':'八卦','3':'微精选','4':'美女','5':'搞笑','6':'财经','7':'体育','8':'时尚','9':'潮应用','10':'科技','11':'游戏','12':'创业','13':'旅行'}]",
					"collect_time", "collect_website",
					"data_sub:[{'0':'审核中','1':'审核通过','2':'审核未通过'}]",
					"user_org", "username", "data_view","stick:[{'0':'否','1':'是'}]" };
			if (title != null && !"".equals(title)) {
				filterMap.put("title", title);
			}
			if (start_date != null && !"".equals(start_date)) {
				filterMap.put("start_date", start_date);
			}
			if (end_date != null && !"".equals(end_date)) {
				filterMap.put("end_date", end_date);
			}
			if (custom_user != null && !"".equals(custom_user)) {
				filterMap.put("custom_user", custom_user);
			}
			if (type != null && !"".equals(type)) {
				filterMap.put("type", type);
			}
			if (edit_date != null && !"".equals(edit_date)) {
				filterMap.put("edit_date", edit_date);
			}
			if (data_sub != null && !"".equals(data_sub)) {
				filterMap.put("data_sub", data_sub);
			}
			if (collect_website != null && !"".equals(collect_website)) {
				filterMap.put("collect_website", collect_website);
			}
			if (stick != null && !"".equals(stick)) {
				filterMap.put("stick", stick);
			}
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			String userOrg = session.getAttribute("USER_ORG").toString();
			// String userRole = (String) session.getAttribute("USER_ROLE");
			if (!"".equals(userOrg) && null != userOrg) {
				filterMap.put("userOrg", userOrg);
				filterMap.put("userid", session.getAttribute("USER_ID")
						.toString());
			}
			PageResult result = lockerService.queryDataImgTable(filterMap);
			String returnStr = getColumnJson(result, viewArray);
			out.print(returnStr);
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		}

	}

	/**
	 * 根据ID删除图片数据
	 * 
	 * @throws IOException
	 */
	public void deleteDataImgById() throws IOException {
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			String ids = ServletActionContext.getRequest().getParameter("ids");
			lockerService.deleteDataImgById(ids);
			out.print("{\"result\":\"success\"}");
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"result\":\"error\"}");
		}
	}

	/**
	 * 修改数据
	 * 
	 * @throws IOException
	 */
	public String editDataImg() throws IOException {
		ServletActionContext.getResponse().setContentType(
				"text/html; charset=utf-8");
		String id = ServletActionContext.getRequest().getParameter("id");
		dataImgTable = lockerService.getDataImgById(id);
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		String sql = "select id,tag_name from tag ";
		List<Tag> tagList = (List<Tag>) lockerService.queryAllBySql(sql);
		if (tagList.size() > 0) {
			request.setAttribute("tagList", tagList);
		}
		String sqlId = "select tag_id from data_tag where data_id =" + id + "";
		List<?> tagIdList = lockerService.queryAllBySql(sqlId);
		if (tagIdList.size() > 0) {
			ServletActionContext.getRequest().setAttribute("tagIdList",
					tagIdList);
		}
		String sqlImgs = "select t.imageUrl,t.content,t.id from img t left join data_img di on di.img_id=t.id where di.data_id= "
				+ id;
		List<?> imgList = lockerService.queryAllBySql(sqlImgs);
		ServletActionContext.getRequest().setAttribute("imgList", imgList);
		String userOrg = (String) session.getAttribute("USER_ORG");
		request.setAttribute("userOrg", userOrg);
		return "editDataImg";
	}

	// 保存数据
	public void saveDataImg() throws IOException {
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			if (0 == dataImgTable.getId()) {
				String u = lockerService.saveDataImg(dataImgTable);
			} else {
				Data_img_table entity = lockerService.getDataImgById(String
						.valueOf(dataImgTable.getId()));
				entity.setId(dataImgTable.getId());
				entity.setTitle(dataImgTable.getTitle());
				entity.setUrl(dataImgTable.getUrl());
				entity.setImgUrl(dataImgTable.getImgUrl());
				entity.setCollect_website(dataImgTable.getCollect_website());
				entity.setData_type(dataImgTable.getData_type());
				entity.setCollect_time(dataImgTable.getCollect_time());
				lockerService.updateDataImg(entity);
			}
			out.print("{\"result\":\"success\"}");

		} catch (Exception e) {
			out.print("{\"result\":\"error\"}");
			e.printStackTrace();
		}

	}

	/**
	 * 插入云数据库
	 * 
	 * @throws IOException
	 */
	public void insertDataImg() throws IOException {
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			String ids = ServletActionContext.getRequest().getParameter("ids");
			int bl = lockerService.insertDataImg(ids);
			if (bl > 0)
				out.print("{\"result\":\"success\"}");
			else
				out.print("{\"result\":\"error\"}");
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"result\":\"error\"}");
		}
	}
	
	/**
	 * 批量保存参数
	 * 
	 * @throws IOException
	 */
	public void saveParams() throws IOException {
		ServletActionContext.getResponse().setContentType(
				"text/json; charset=utf-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			String ids = ServletActionContext.getRequest().getParameter("ids");
			String edit_date = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("edit_date"));
			Map filterMap = new HashMap();// 存储参数的map
			if (edit_date != null && !"".equals(edit_date)) {
				filterMap.put("edit_date", edit_date);
			}
			if (ids != null && !"".equals(ids)) {
				filterMap.put("ids", ids);
			}
			if(filterMap.size()>1){
				int count =lockerService.saveParams(filterMap);
				if(count>0){
					out.print("{\"result\":\"success\"}");
				}else{
					out.print("{\"result\":\"empty\"}");
				}
				
			}else{
				out.print("{\"result\":\"empty\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"result\":\"error\"}");
		}
	}
	/**
	 * 加载来源下拉内容
	 * 
	 * @author 张加宁
	 * @throws IOException
	 */
	public void selectInit() throws IOException {
		ServletActionContext.getResponse().setContentType(
				"text/html; charset=utf-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			String orgSql = "select user_org from user where user_org not in ('0','1') ";
			String cusUserSql = "select userid,username from user where user_org = '1' ";
			List<User> orgList = (List<User>) lockerService
					.queryAllBySql(orgSql);
			List<User> cusUserList = (List<User>) lockerService
					.queryAllBySql(cusUserSql);
			if (orgList.size() > 0) {
				out.print("{\"result\":\"success\",\"selectData\":"
						+ JSON.toJSONString(orgList, true)
						+ ",\"cusUserData\":"
						+ JSON.toJSONString(cusUserList, true) + "}");
			} else {
				out.print("{\"result\":\"null\"}");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			out.print("{\"result\":\"error\"}");
		}
	}
	/**
	 * 新闻置顶
	 * @author louxiaojian
	 * @date： 日期：2015-4-20 时间：下午4:20:23
	 * @throws IOException
	 */
	public void stickByIds() throws IOException{
		ServletActionContext.getResponse().setContentType(
				"text/json; charset=utf-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			String ids=ServletActionContext.getRequest().getParameter("ids");
			int count=lockerService.stickByIds(ids);
			if(count>0){
				out.print("{\"state\":\"success\"}");
			}else{
				out.print("{\"state\":\"error\"}");
			}
		} catch (Exception e) {
			out.print("{\"state\":\"error\"}");
			logger.error(e);
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
}
