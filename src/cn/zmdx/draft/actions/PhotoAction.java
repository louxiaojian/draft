package cn.zmdx.draft.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.zmdx.draft.entity.PageResult;
import cn.zmdx.draft.entity.ReviewRecords;
import cn.zmdx.draft.service.impl.PhotoServiceImpl;
import cn.zmdx.draft.util.DataUtil;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;

public class PhotoAction extends ActionSupport {
	private PhotoServiceImpl photoService;
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

	public PhotoServiceImpl getPhotoService() {
		return photoService;
	}

	public void setPhotoService(PhotoServiceImpl photoService) {
		this.photoService = photoService;
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
	 * 查询所有的照片集
	 * 
	 * @author louxiaojian
	 * @date： 日期：2015-7-13 时间：下午5:07:12
	 * @throws IOException
	 */
	public void queryPhotos() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		try {
			response.setContentType("text/json; charset=utf-8");
			String type = request.getParameter("type");
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String status = request.getParameter("status");
			Map<String, String> filterMap = getPagerMap();
			String[] viewArray = { "ID", "uploadDate", "descs",
					"type:[{'0':'个人','1':'秀场'}]",
					"status:[{'0':'未审核','1':'审核通过','2':'未通过'}]", "praise",
					"tread", "auditingDate", "userid","report","view", "userName","cycle_no","theme_name" };
			if (type != null && !"".equals(type)) {
				filterMap.put("type", type);
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
			PageResult result = (PageResult) photoService
					.queryPhotos(filterMap);
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
	/**
	 * 获取照片集所有照片
	 * @author louxiaojian
	 * @date： 日期：2015-7-29 时间：下午4:18:04
	 * @throws IOException
	 */
	public void queryPhotoByPictureSetId() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		try {
			response.setContentType("text/json; charset=utf-8");
			String pictureSetId = request.getParameter("pictureSetId");
			String[] viewArray = { "ID","photoUrl", "uploadDate", "userid","pictureSetId"};
			List list=photoService.queryPhotoByPictureSetId(pictureSetId);
			out.print("{\"result\":\"success\",\"list\":"+JSON.toJSONString(list)+"}");
		} catch (Exception e) {
			e.printStackTrace();
			out.print("error");
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 获取用户真人验证照片
	 * @author louxiaojian
	 * @date： 日期：2015-7-30 时间：下午2:30:48
	 * @throws IOException
	 */
	public void queryVerificationPhotoByUserId() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		try {
			response.setContentType("text/json; charset=utf-8");
			String userId = request.getParameter("userId");
			String[] viewArray = { "ID","photoUrl", "uploadDate", "userid","pictureSetId"};
			List list=photoService.queryVerificationPhotoByUserId(userId);
			out.print("{\"result\":\"success\",\"list\":"+JSON.toJSONString(list)+"}");
		} catch (Exception e) {
			e.printStackTrace();
			out.print("error");
		} finally {
			out.flush();
			out.close();
		}
	}
	/**
	 * 审核图集或真人验证
	 * @author louxiaojian
	 * @date： 日期：2015-7-15 时间：下午4:22:37
	 * @throws IOException
	 */
	public void auditing() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		try {
			response.setContentType("text/json; charset=utf-8");
			String ids=request.getParameter("ids");
			String status=request.getParameter("status");
			String descs=request.getParameter("descs");
			String type=request.getParameter("type");
			ReviewRecords rr=new ReviewRecords();
			rr.setDatetime(new Date());
			rr.setDescs(descs);
			rr.setStatus(status);
			rr.setType(Integer.parseInt(type));
			rr.setOperatorId(Integer.parseInt(request.getSession().getAttribute("USER_ID").toString()));
			this.photoService.auditing(ids,rr);
			out.print("{\"result\":\"success\"}");
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"result\":\"error\"}");
		} finally {
			out.flush();
			out.close();
		}
	}
	/**
	 * 删除指定id的photo及审核记录
	 * @author louxiaojian
	 * @date： 日期：2015-7-15 时间：下午5:06:45
	 * @throws IOException
	 */
	public void deletePhotoByIds() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		PrintWriter out= response.getWriter();
		try {
			String ids=request.getParameter("ids");
			this.photoService.deletePhotoByIds(ids);
			out.print("{\"result\":\"success\"}");
		} catch (Exception e) {
			out.print("{\"result\":\"error\"}");
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}
	}
}
