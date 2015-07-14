package cn.zmdx.locker.util;

import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.zmdx.locker.entity.PageResult;

public class DataUtil {
	public static String getColumnJson(PageResult result, String[] viewArray,String rows,String page) {
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
	public static JSONObject getColumnValue(Object[] objlist, String[] viewArray) {
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
}
