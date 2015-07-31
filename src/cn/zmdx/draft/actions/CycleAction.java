package cn.zmdx.draft.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.zmdx.draft.entity.Cycle;
import cn.zmdx.draft.entity.PageResult;
import cn.zmdx.draft.entity.Themes;
import cn.zmdx.draft.entity.User;
import cn.zmdx.draft.service.impl.CycleServiceImpl;
import cn.zmdx.draft.util.DataUtil;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 选秀主题、周期相关操作
 * @author louxiaojian
 * @date： 日期：2015-7-13 时间：下午5:02:05
 */
public class CycleAction extends ActionSupport {
	private CycleServiceImpl cycleService;
	private String result;
	private String page;
	private String rows;
	private String sidx;
	private String sord;
	private Cycle cycle;
	private Themes theme;
	
	public void setCycleService(CycleServiceImpl cycleService) {
		this.cycleService = cycleService;
	}
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
	public CycleServiceImpl getCycleService() {
		return cycleService;
	}
	public Cycle getCycle() {
		return cycle;
	}
	public void setCycle(Cycle cycle) {
		this.cycle = cycle;
	}
	public Themes getTheme() {
		return theme;
	}
	public void setTheme(Themes theme) {
		this.theme = theme;
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
	 * 查询所有的选秀周期
	 * @author louxiaojian
	 * @date： 日期：2015-7-13 时间：下午5:07:12
	 * @throws IOException
	 */
	public void queryCycles() throws IOException {
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response= ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		try {
			response.setContentType("text/json; charset=utf-8");
			String cycleNo = request.getParameter("cycleNo");
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String status = request.getParameter("status");
			String themeId = request.getParameter("themeId");
			Map<String, String> filterMap = getPagerMap();
			String[] viewArray = { "ID", "cycle_no", "starttime", "signup_endtime", "endtime", "status:[{'0':'未开始','1':'进行中','2':'已结束'}]", "theme_id","theme_name" };
			if (cycleNo != null && !"".equals(cycleNo)) {
				filterMap.put("cycleNo", cycleNo);
			}
			if (starttime != null && !"".equals(starttime)) {
				filterMap.put("starttime", starttime);
			}
			if (endtime != null && !"".equals(endtime)) {
				filterMap.put("endtime", endtime);
			}
			if (status != null && !"".equals(status)) {
				filterMap.put("status", status);
			}
			if (themeId != null && !"".equals(themeId)) {
				filterMap.put("themeId", themeId);
			}
			PageResult result = (PageResult) cycleService.queryCycles(filterMap);
			String returnStr = DataUtil.getColumnJson(result, viewArray,rows,page);
			out.print(returnStr);
		}catch (Exception e) {
			e.printStackTrace();
			out.print("error");
		} finally{
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 保存、修改选秀周期
	 * @author louxiaojian
	 * @date： 日期：2015-7-13 时间：下午6:19:52
	 * @throws IOException
	 */
	public void saveCycle() throws IOException {
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			if (0 == cycle.getId()) {
				cycle.setStatus("0");
				cycleService.saveEntity(cycle);
			} else {
				Cycle entity = (Cycle)cycleService.getEntity(Cycle.class,cycle.getId());
				entity.setId(cycle.getId());
				entity.setCycleNo(cycle.getCycleNo());
				entity.setStarttime(cycle.getStarttime());
				entity.setSignupEndtime(cycle.getSignupEndtime());
				entity.setEndtime(cycle.getEndtime());
				entity.setStatus(cycle.getStatus());
				entity.setThemeId(cycle.getThemeId());
				cycleService.updateEntity(entity);
			}
			out.print("{\"result\":\"success\"}");
		} catch (Exception e) {
			out.print("{\"result\":\"error\"}");
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 根据id获取Cycle对象并跳转至修改页面
	 * @author louxiaojian
	 * @date： 日期：2015-7-13 时间：下午5:35:10
	 * @return
	 * @throws IOException
	 */
	public String getCycleById() throws IOException {
		ServletActionContext.getResponse().setContentType(
				"text/html; charset=utf-8");
		String id = ServletActionContext.getRequest().getParameter("id");
		String flag = ServletActionContext.getRequest().getParameter("flag");
		Cycle cycle = (Cycle)cycleService.getEntity(Cycle.class, Integer.parseInt(id));
		ServletActionContext.getRequest().setAttribute("cycle", cycle);
		ServletActionContext.getRequest().setAttribute("flag", flag);
		return "editCycle";
	}
	/**
	 * 删除选秀周期
	 * @author louxiaojian
	 * @date： 日期：2015-7-13 时间：下午6:19:45
	 * @throws IOException
	 */
	public void deleteCycle() throws IOException {
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			String ids = ServletActionContext.getRequest().getParameter("ids");
			cycleService.deleteEntity(Cycle.class,ids);
			out.print("{\"result\":\"success\"}");
		} catch (Exception e) {
			out.print("{\"result\":\"error\"}");
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}
	}
	/**
	 * 查询所有的选秀主题
	 * @author louxiaojian
	 * @date： 日期：2015-7-13 时间：下午5:07:12
	 * @throws IOException
	 */
	public void queryThemes() throws IOException {
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response= ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		try {
			response.setContentType("text/json; charset=utf-8");
			String name = request.getParameter("name");
			Map<String, String> filterMap = getPagerMap();
			String[] viewArray = { "ID", "name", "descs"};
			if (name != null && !"".equals(name)) {
				filterMap.put("name", name);
			}
			PageResult result = (PageResult) cycleService.queryThemes(filterMap);
			String returnStr = DataUtil.getColumnJson(result, viewArray,rows,page);
			out.print(returnStr);
		}catch (Exception e) {
			e.printStackTrace();
			out.print("error");
		} finally{
			out.flush();
			out.close();
		}
	}
	/**
	 * 保存、修改选秀主题
	 * @author louxiaojian
	 * @date： 日期：2015-7-13 时间：下午6:19:52
	 * @throws IOException
	 */
	public void saveTheme() throws IOException {
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			if (0 == theme.getId()) {
				cycleService.saveEntity(theme);
			} else {
				Themes entity = (Themes)cycleService.getEntity(Themes.class,theme.getId());
				entity.setId(theme.getId());
				entity.setName(theme.getName());
				entity.setDescs(theme.getDescs());
				cycleService.updateEntity(entity);
			}
			out.print("{\"result\":\"success\"}");
		} catch (Exception e) {
			out.print("{\"result\":\"error\"}");
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 根据id获取Themes对象并跳转至修改页面
	 * @author louxiaojian
	 * @date： 日期：2015-7-13 时间：下午5:35:10
	 * @return
	 * @throws IOException
	 */
	public String getThemeById() throws IOException {
		ServletActionContext.getResponse().setContentType(
				"text/html; charset=utf-8");
		String id = ServletActionContext.getRequest().getParameter("id");
		String flag = ServletActionContext.getRequest().getParameter("flag");
		Themes theme = (Themes)cycleService.getEntity(Themes.class, Integer.parseInt(id));
		ServletActionContext.getRequest().setAttribute("theme", theme);
		ServletActionContext.getRequest().setAttribute("flag", flag);
		return "editTheme";
	}
	/**
	 * 删除选秀主题
	 * @author louxiaojian
	 * @date： 日期：2015-7-13 时间：下午6:19:45
	 * @throws IOException
	 */
	public void deleteTheme() throws IOException {
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			String ids = ServletActionContext.getRequest().getParameter("ids");
			cycleService.deleteEntity(Themes.class,ids);
			out.print("{\"result\":\"success\"}");
		} catch (Exception e) {
			out.print("{\"result\":\"error\"}");
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
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
			String tableName = ServletActionContext.getRequest().getParameter("tableName");
			String columns = ServletActionContext.getRequest().getParameter("columns");
			String whereCol = ServletActionContext.getRequest().getParameter("whereCol");
			String whereVal = ServletActionContext.getRequest().getParameter("whereVal");
			List list= cycleService.queryInitData(tableName,columns,whereCol,whereVal);
			if (list.size() > 0) {
				out.print("{\"result\":\"success\",\"data\":"
						+ JSON.toJSONString(list, true)+"}");
			} else {
				out.print("{\"result\":\"null\"}");
			}
		} catch (Exception e) {
//			logger.error(e);
			e.printStackTrace();
			out.print("{\"result\":\"error\"}");
		}
	}
}
