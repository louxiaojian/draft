package cn.zmdx.draft.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.zmdx.draft.dao.interfaces.UserDAO;
import cn.zmdx.draft.entity.PageResult;
import cn.zmdx.draft.entity.User;
import cn.zmdx.draft.util.Sha1;

public class UserDAOImpl extends ParentDAOImpl implements UserDAO {

	public UserDAOImpl(HibernateTemplate template) {
		super(template);
		// TODO Auto-generated constructor stub
	}

	public UserDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int save(User user) {
		// TODO Auto-generated method stub
		this.getSession().createSQLQuery("set NAMES utf8mb4").executeUpdate();
		template.save(user);
		return user.getId();
	}

	@Override
	public String getPassword(User user) {
		// TODO Auto-generated method stub
		List<String> password = template
				.find("select password from User where worker_no=?",
						user.getId());
		if (password.size() > 0) {
			return password.get(0);
		}
		return null;
	}

	@Override
	public User get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getMatchUser(String loginname, String password) {
		String sql = "from User where loginname = ? and password =? and orgId=1";
		Query query = getSession().createQuery(String.valueOf(sql));
		query.setString(0, loginname);
		query.setString(1, new Sha1().Digest(password));
		List<User> list = query.list();
		if (list.size() > 0)
			return list.get(0);
		return null;
	}

	// 通过主键id查询用户信息
	public User getUsersById(int userid) {
		User users = (User) super.getHibernateTemplate()
				.get(User.class, userid);
		return users;
	}

	public void update(Object object) {
		this.getSession().createSQLQuery("set NAMES utf8mb4").executeUpdate();
		this.getHibernateTemplate().update(
				this.getHibernateTemplate().merge(object));
	}

	@Override
	public PageResult queryUsers(Map<String, String> filterMap) {
		StringBuffer sqlCount=new StringBuffer("select count(*) from (SELECT id FROM users where 1=1 ");
		StringBuffer sql=new StringBuffer("SELECT id,username,loginname,password,address,telephone,name,flag,isvalidate,headPortrait,age,introduction,registration_date,org_id,gender,validateDate,validateUrl FROM users where 1=1 ");
		if(filterMap!=null&&!filterMap.isEmpty()){
			if(!"".equals(filterMap.get("loginname"))&&filterMap.get("loginname")!=null){//登录名
				sql.append(" and loginname like '%"+filterMap.get("loginname")+"%'");
				sqlCount.append(" and loginname like '%"+filterMap.get("loginname")+"%'");
			}
			if(!"".equals(filterMap.get("flag"))&&filterMap.get("flag")!=null){//用户状态，0:未激活，1:正常，2:冻结
				sql.append(" and flag ='"+filterMap.get("flag")+"'");
				sqlCount.append(" and flag ='"+filterMap.get("flag")+"'");
			}
			if(!"".equals(filterMap.get("orgId"))&&filterMap.get("orgId")!=null){//用户权限，0普通用户，1管理员
				sql.append(" and org_id ="+filterMap.get("orgId"));
				sqlCount.append(" and org_id ="+filterMap.get("orgId"));
			}
			if(!"".equals(filterMap.get("isvalidate"))&&filterMap.get("isvalidate")!=null){//真人验证，0:未验证，1:验证失败，2:验证成功
				sql.append(" and isvalidate ='"+filterMap.get("isvalidate")+"'");
				sqlCount.append(" and isvalidate ='"+filterMap.get("isvalidate")+"'");
			}
			if(!"".equals(filterMap.get("starttime"))&&filterMap.get("starttime")!=null){//真人验证，0:未验证，1:验证失败，2:验证成功
				sql.append(" and registration_date >='"+filterMap.get("starttime")+"'");
				sqlCount.append(" and registration_date >='"+filterMap.get("starttime")+"'");
			}
			if(!"".equals(filterMap.get("endtime"))&&filterMap.get("endtime")!=null){//真人验证，0:未验证，1:验证失败，2:验证成功
				sql.append(" and registration_date <='"+filterMap.get("endtime")+"'");
				sqlCount.append(" and registration_date <='"+filterMap.get("endtime")+"'");
			}
		}
		sql.append(" order by "+filterMap.get("sidx")+" "+filterMap.get("sord"));
		sqlCount.append(") t");
		return searchBySQL(sqlCount.toString(), sql.toString(), filterMap);
	}
}
