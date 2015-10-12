package cn.zmdx.draft.actions;

import java.io.File;
import java.io.FileInputStream;
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
import cn.zmdx.draft.util.UploadPhoto;
import cn.zmdx.draft.util.picCloud.PicCloud;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.qcloud.UploadResult;
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
	// 上传文件域
	private File bgImage;
	// 上传文件类型
	private String bgImageContentType;
	// 封装上传文件名 
	private String bgImageFileName;
	// 上传文件域
	private File detailImage;
	// 上传文件类型
	private String detailImageContentType;
	// 封装上传文件名 
	private String detailImageFileName;
	// 上传文件域
	private File image;
	// 上传文件类型
	private String imageContentType;
	// 封装上传文件名 
	private String imageFileName;
	// 上传文件域
	private File insideDetailImage;
	// 上传文件类型
	private String insideDetailImageContentType;
	// 封装上传文件名 
	private String insideDetailImageFileName;
	// 上传文件域
	private File webDetailImage;
	// 上传文件类型
	private String webDetailImageContentType;
	// 封装上传文件名 
	private String webDetailImageFileName;
	private File webTitleImage;
	// 上传文件类型
	private String webTitleImageContentType;
	// 封装上传文件名 
	private String webTitleImageFileName;
	public static final int APP_ID_V2 = 10002468;
	public static final String SECRET_ID_V2 = "AKIDo26nbKDLWZA6xpPXzRUaYVPgf5wqqlp6";
	public static final String SECRET_KEY_V2 = "upfmsUJgzOitvj0pCzSy4tV9ihdGeZMV";
	public static final String BUCKET = "themepic"; // 空间名
	
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
	public File getImage() {
		return image;
	}
	public void setImage(File image) {
		this.image = image;
	}
	public String getImageContentType() {
		return imageContentType;
	}
	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
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
	public File getBgImage() {
		return bgImage;
	}
	public void setBgImage(File bgImage) {
		this.bgImage = bgImage;
	}
	public String getBgImageContentType() {
		return bgImageContentType;
	}
	public void setBgImageContentType(String bgImageContentType) {
		this.bgImageContentType = bgImageContentType;
	}
	public String getBgImageFileName() {
		return bgImageFileName;
	}
	public void setBgImageFileName(String bgImageFileName) {
		this.bgImageFileName = bgImageFileName;
	}
	public File getDetailImage() {
		return detailImage;
	}
	public void setDetailImage(File detailImage) {
		this.detailImage = detailImage;
	}
	public String getDetailImageContentType() {
		return detailImageContentType;
	}
	public void setDetailImageContentType(String detailImageContentType) {
		this.detailImageContentType = detailImageContentType;
	}
	public String getDetailImageFileName() {
		return detailImageFileName;
	}
	public void setDetailImageFileName(String detailImageFileName) {
		this.detailImageFileName = detailImageFileName;
	}
	public File getInsideDetailImage() {
		return insideDetailImage;
	}
	public void setInsideDetailImage(File insideDetailImage) {
		this.insideDetailImage = insideDetailImage;
	}
	public String getInsideDetailImageContentType() {
		return insideDetailImageContentType;
	}
	public void setInsideDetailImageContentType(String insideDetailImageContentType) {
		this.insideDetailImageContentType = insideDetailImageContentType;
	}
	public String getInsideDetailImageFileName() {
		return insideDetailImageFileName;
	}
	public void setInsideDetailImageFileName(String insideDetailImageFileName) {
		this.insideDetailImageFileName = insideDetailImageFileName;
	}
	public File getWebDetailImage() {
		return webDetailImage;
	}
	public void setWebDetailImage(File webDetailImage) {
		this.webDetailImage = webDetailImage;
	}
	public String getWebDetailImageContentType() {
		return webDetailImageContentType;
	}
	public void setWebDetailImageContentType(String webDetailImageContentType) {
		this.webDetailImageContentType = webDetailImageContentType;
	}
	public String getWebDetailImageFileName() {
		return webDetailImageFileName;
	}
	public void setWebDetailImageFileName(String webDetailImageFileName) {
		this.webDetailImageFileName = webDetailImageFileName;
	}
	public File getWebTitleImage() {
		return webTitleImage;
	}
	public void setWebTitleImage(File webTitleImage) {
		this.webTitleImage = webTitleImage;
	}
	public String getWebTitleImageContentType() {
		return webTitleImageContentType;
	}
	public void setWebTitleImageContentType(String webTitleImageContentType) {
		this.webTitleImageContentType = webTitleImageContentType;
	}
	public String getWebTitleImageFileName() {
		return webTitleImageFileName;
	}
	public void setWebTitleImageFileName(String webTitleImageFileName) {
		this.webTitleImageFileName = webTitleImageFileName;
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
			String themeTitle = request.getParameter("themeTitle");
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String status = request.getParameter("status");
			Map<String, String> filterMap = getPagerMap();//id,theme_title,starttime,endtime,status,bg_url,descs,tag_url,detail_image_url
			String[] viewArray = { "ID", "theme_title", "starttime", "endtime", "status:[{'0':'已结束','1':'进行中','2':'未开始'}]", "bg_url","descs","tag_url","detail_image_url","inside_detail_image_url","vote_start_time","vote_end_time","web_detail_url","web_title_url" };
			if (themeTitle != null && !"".equals(themeTitle)) {
				filterMap.put("themeTitle", themeTitle);
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
			PicCloud pc = new PicCloud(APP_ID_V2, SECRET_ID_V2, SECRET_KEY_V2,
					BUCKET);
			if (0 == cycle.getId()) {
				cycle.setStatus("0");
				if(getBgImage()!=null&&getBgImage().length()>0){
					UploadResult result = new UploadResult();
					int ret = pc.Upload(getBgImage(), result);
					if (ret != 0) {
						
					}else{
						cycle.setBgUrl(result.download_url);
					}
				}
				if(getDetailImage()!=null&&getDetailImage().length()>0){
					UploadResult result = new UploadResult();
					int ret = pc.Upload(getDetailImage(), result);
					if (ret != 0) {
						
					}else{
						cycle.setDetailImageUrl(result.download_url);
					}
				}
				if(getInsideDetailImage()!=null&&getInsideDetailImage().length()>0){
					UploadResult result = new UploadResult();
					int ret = pc.Upload(getInsideDetailImage(), result);
					if (ret != 0) {
						
					}else{
						cycle.setInsideDetailImageUrl(result.download_url);
					}
				}
				if(getWebDetailImage()!=null&&getWebDetailImage().length()>0){
					UploadResult result = new UploadResult();
					int ret = pc.Upload(getWebDetailImage(), result);
					if (ret != 0) {
						
					}else{
						cycle.setWebDetailUrl(result.download_url);
					}
				}
				if(getWebTitleImage()!=null&&getWebTitleImage().length()>0){
					UploadResult result = new UploadResult();
					int ret = pc.Upload(getWebTitleImage(), result);
					if (ret != 0) {
						
					}else{
						cycle.setWebTitleUrl(result.download_url);
					}
				}
				cycleService.saveEntity(cycle);
			} else {
				Cycle entity = (Cycle)cycleService.getEntity(Cycle.class,cycle.getId());
				entity.setId(cycle.getId());
				entity.setThemeTitle(cycle.getThemeTitle());
				entity.setStarttime(cycle.getStarttime());
				entity.setVoteStartTime(cycle.getVoteStartTime());
				entity.setVoteEndTime(cycle.getVoteEndTime());
				entity.setEndtime(cycle.getEndtime());
				entity.setStatus(cycle.getStatus());
				entity.setDescs(cycle.getDescs());
				if(getBgImage()!=null&&getBgImage().length()>0){
					UploadResult result = new UploadResult();
					int ret = pc.Upload(getBgImage(), result);
					if (ret != 0) {
						
					}else{
						entity.setBgUrl(result.download_url);
					}
				}
				if(getDetailImage()!=null&&getDetailImage().length()>0){
					UploadResult result = new UploadResult();
					int ret = pc.Upload(getDetailImage(), result);
					if (ret != 0) {
						
					}else{
						entity.setDetailImageUrl(result.download_url);
					}
				}
				if(getInsideDetailImage()!=null&&getInsideDetailImage().length()>0){
					UploadResult result = new UploadResult();
					int ret = pc.Upload(getInsideDetailImage(), result);
					if (ret != 0) {
						
					}else{
						entity.setInsideDetailImageUrl(result.download_url);
					}
				}
				if(getWebDetailImage()!=null&&getWebDetailImage().length()>0){
					UploadResult result = new UploadResult();
					int ret = pc.Upload(getWebDetailImage(), result);
					if (ret != 0) {
						
					}else{
						entity.setWebDetailUrl(result.download_url);
					}
				}
				if(getWebTitleImage()!=null&&getWebTitleImage().length()>0){
					UploadResult result = new UploadResult();
					int ret = pc.Upload(getWebTitleImage(), result);
					if (ret != 0) {
						
					}else{
						entity.setWebTitleUrl(result.download_url);
					}
				}
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
