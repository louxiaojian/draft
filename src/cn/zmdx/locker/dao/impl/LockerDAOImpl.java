package cn.zmdx.locker.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.zmdx.locker.dao.interfaces.LockerDAO;
import cn.zmdx.locker.entity.Data_img_table;
import cn.zmdx.locker.entity.PageResult;
import cn.zmdx.locker.util.GenericsUtils;
import cn.zmdx.locker.util.String2list2mapUtil;

public class LockerDAOImpl extends ParentDAOImpl implements LockerDAO {
	public LockerDAOImpl(HibernateTemplate template) {
		super(template);
	}

	public LockerDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	public PageResult searchBySQL(String countsql, String querysql,
			Map filterMap) {
		int page = (Integer) filterMap.get("page");
		int rows = (Integer) filterMap.get("rows");
		int start = rows * (page - 1); // 开始的记录
		// 获取记录总数
		Query query = getSession().createSQLQuery(countsql);
		List<Number> count = query.list();
		int total = Integer.parseInt(count.get(0).toString());

		// 获取当前页的结果集
		query = getSession().createSQLQuery(querysql);
		query.setFirstResult(start);
		query.setMaxResults(rows);
		List<?> datas = query.list();
		PageResult pm = new PageResult();
		pm.setRowCount(total);
		pm.setData(datas);
		return pm;
	}

	@Override
	public PageResult queryDataTable(Map<String, String> filterMap) {
		StringBuffer queryString = new StringBuffer();
		StringBuffer queryCountString = new StringBuffer();
		queryCountString
				.append("select count(*) from (select id,title,url,data_type from data_img_table  where 1=1  ");
		queryString
				.append("select id,title,url,data_type from data_img_table  where 1=1 ");
		if (filterMap != null && !filterMap.isEmpty()) {
			if (null != filterMap.get("title")
					&& !"".equals(filterMap.get("title"))) {
				queryCountString.append("and title like '%"
						+ filterMap.get("title") + "%' ");
				queryString.append("and title like '%" + filterMap.get("title")
						+ "%' ");
			}
			if (null != filterMap.get("data_type")
					&& !"".equals(filterMap.get("data_type"))) {
				queryCountString.append("and data_type = '"
						+ filterMap.get("data_type") + "'  ");
				queryString.append("and data_type = '"
						+ filterMap.get("data_type") + "'  ");
			}
			if (null != filterMap.get("start_date")
					&& !"".equals(filterMap.get("start_date"))) {
				queryCountString.append("and collect_time > '"
						+ filterMap.get("start_date") + "' ");
				queryString.append("and collect_time > '"
						+ filterMap.get("start_date") + "' ");
			}
			if (null != filterMap.get("end_date")
					&& !"".equals(filterMap.get("end_date"))) {
				queryCountString.append("and collect_time < '"
						+ filterMap.get("end_date") + "' ");
				queryString.append("and collect_time < '"
						+ filterMap.get("end_date") + "' ");
			}
			queryCountString.append(")");
		}
		return searchBySQL(queryCountString.toString(), queryString.toString(),
				filterMap);
	}

	@Override
	public PageResult queryDataImgTable(Map<String, String> filterMap) {
		StringBuffer queryString = new StringBuffer();
		StringBuffer queryCountString = new StringBuffer();
		queryCountString
				.append("select count(*) from (select dit.id,dit.title,dit.url,dit.imgUrl,dit.type,dit.collect_time,dit.collect_website,dit.data_sub,u.user_org,u.username,data_view,stick from data_img_table dit left join user u on dit.userid = u.userid where 1=1  ");
		queryString
				.append("select dit.id,dit.title,dit.url,dit.imgUrl,dit.type,dit.collect_time,dit.collect_website,dit.data_sub,u.user_org,u.username,data_view,stick from data_img_table dit left join user u on dit.userid = u.userid  where 1=1 ");
		if (filterMap != null && !filterMap.isEmpty()) {
			if (null != filterMap.get("title")
					&& !"".equals(filterMap.get("title"))) {
				queryCountString.append("and dit.title like '%"
						+ filterMap.get("title") + "%' ");
				queryString.append("and dit.title like '%"
						+ filterMap.get("title") + "%' ");
			}
			if (null != filterMap.get("type")
					&& !"".equals(filterMap.get("type"))) {
				queryCountString.append("and dit.type = '"
						+ filterMap.get("type") + "'  ");
				queryString.append("and dit.type = '" + filterMap.get("type")
						+ "'  ");
			}
			if (null != filterMap.get("start_date")
					&& !"".equals(filterMap.get("start_date"))) {
				queryCountString.append("and dit.collect_time > '"
						+ filterMap.get("start_date") + "' ");
				queryString.append("and dit.collect_time > '"
						+ filterMap.get("start_date") + "' ");
			}
			if (null != filterMap.get("end_date")
					&& !"".equals(filterMap.get("end_date"))) {
				queryCountString.append("and dit.collect_time < '"
						+ filterMap.get("end_date") + "' ");
				queryString.append("and dit.collect_time < '"
						+ filterMap.get("end_date") + "' ");
			}
			if (null != filterMap.get("edit_date")
					&& !"".equals(filterMap.get("edit_date"))) {
				queryCountString.append("and dit.collect_time like '%"
						+ filterMap.get("edit_date") + "%' ");
				queryString.append("and dit.collect_time like '%"
						+ filterMap.get("edit_date") + "%' ");
			}
			if (null != filterMap.get("data_sub")
					&& !"".equals(filterMap.get("data_sub"))) {
				queryCountString.append("and dit.data_sub = "
						+ filterMap.get("data_sub") + " ");
				queryString.append("and dit.data_sub = "
						+ filterMap.get("data_sub") + " ");
			}
			if (null != filterMap.get("custom_user")
					&& !"".equals(filterMap.get("custom_user"))) {
				queryCountString.append("and u.userid = "
						+ filterMap.get("custom_user") + " ");
				queryString.append("and u.userid = "
						+ filterMap.get("custom_user") + " ");
			}
			if (null != filterMap.get("stick")
					&& !"".equals(filterMap.get("stick"))) {
				queryCountString.append("and dit.stick = "
						+ filterMap.get("stick") + " ");
				queryString.append("and dit.stick = "
						+ filterMap.get("stick") + " ");
			}
			if (null != filterMap.get("collect_website")
					&& !"".equals(filterMap.get("collect_website"))) {
				if ("0".equals(filterMap.get("collect_website"))) {
					queryCountString
							.append("and dit.collect_website not in('0') and dit.collect_website in (select user_org from user ) ");
					queryString
							.append("and collect_website not in('0') and collect_website in (select user_org from user ) ");
				} else {
					queryCountString.append("and dit.collect_website = '"
							+ filterMap.get("collect_website") + "' ");
					queryString.append("and dit.collect_website = '"
							+ filterMap.get("collect_website") + "' ");
				}
			}
			if (!"0".equals(filterMap.get("userOrg"))
					&& null != filterMap.get("userOrg")
					&& !"".equals(filterMap.get("userOrg"))) {
				queryCountString.append("and dit.userid = '"
						+ filterMap.get("userid") + "'");
				queryString.append("and dit.userid = '"
						+ filterMap.get("userid") + "'");
			}
		}
		queryCountString.append(") as t");
		queryString.append(" order by "+filterMap.get("sidx")+" "+filterMap.get("sord")+"");
		return searchBySQL(queryCountString.toString(), queryString.toString(),
				filterMap);
	}

	@Override
	public void deleteDataImgById(String ids) {
		String idss[] = ids.split(",");
		for (String id : idss) {
			Data_img_table transientInstance = getDataImgById(id);
			this.getHibernateTemplate().delete(transientInstance);
		}

	}

	@Override
	public String saveDataImg(Data_img_table dit) {
		String id = this.getHibernateTemplate().save(dit).toString();
		return id;

	}

	@Override
	public Data_img_table getDataImgById(String id) {
		Data_img_table dit = (Data_img_table) this.getHibernateTemplate().get(
				Data_img_table.class, Integer.parseInt(id));
		return dit;
	}

	@Override
	public void updateDataImg(Data_img_table dit) {
		this.getHibernateTemplate().update(dit);
	}

	@Override
	public int insertDataImg(String ids) {
		GenericsUtils genericsUtils = new GenericsUtils();
		PreparedStatement pstmt = null;
		Connection dbConn = null;
		String idss[] = ids.split(",");
		Data_img_table dit;
		StringBuffer sql = new StringBuffer();
		sql.append("insert into data_img_table(title,url,imgUrl,data_type,collect_time) values");
		for (String id : idss) {
			dit = getDataImgById(id);
			sql.append("('" + dit.getTitle() + "','" + dit.getUrl() + "','"
					+ dit.getImgUrl() + "','" + dit.getData_type() + "','" + dit.getCollect_time() + "'),");
		}
		try {
			Properties p = genericsUtils.loadProperty("c3p0.properties");
			String url = p.getProperty("c3p0.cloudTestUrl");
			String driverName = p.getProperty("c3p0.cloudTestDriverClassName");
			String userName = p.getProperty("c3p0.cloudTestUsername");
			String password = p.getProperty("c3p0.cloudTestPassword");
			Class.forName(driverName).newInstance();
			dbConn = DriverManager.getConnection(url, userName, password);
			pstmt = (PreparedStatement) dbConn.prepareStatement(String
					.valueOf(sql.substring(0, sql.length() - 1)));
			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (dbConn != null)
					pstmt.close();
				dbConn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	@Override
	public int saveParams(Map<String, String> filterMap) {
		StringBuffer sql = new StringBuffer();
		sql.append("update data_img_table t set t.collect_time = '"
				+ filterMap.get("edit_date") + "' where t.id in ( :idList ) ");
		Query query = getSession().createSQLQuery(String.valueOf(sql));
		query.setParameterList("idList",
				String2list2mapUtil.StringToList(filterMap.get("ids"))
						.toArray());
		int count = query.executeUpdate();
		return count;
	}
	@Override
	public List<?> queryAllBySql(String sql) {
		Query query = getSession().createSQLQuery(sql);
		return query.list();
	}
	@Override
	public int stickByIds(String ids) {
		Query query=this.getSession().createSQLQuery("update data_img_table set stick='1' where id in ("+ids+")");
		return query.executeUpdate();
	}

	@Override
	public int cancelStickByIds(String ids) {
		Query query=this.getSession().createSQLQuery("update data_img_table set stick='0' where id in ("+ids+")");
		return query.executeUpdate();
	}
}
