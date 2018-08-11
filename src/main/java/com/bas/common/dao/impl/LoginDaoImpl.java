package com.bas.common.dao.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bas.common.dao.LoginDao;
import com.bas.employee.dao.entity.LoginEntity;


/**
 * 
 * @author nagendra
 * 
 *  This is code responsible for all the logic regarding login  
 *
 */
//@Repository ,@Component,@Controller
@Repository("LoginDaoImpl")
@Transactional
public class LoginDaoImpl extends  JdbcDaoSupport implements LoginDao {
	
	
	@Autowired
	@Qualifier("sdatasource")
	public void initJdbcTemplate(DataSource dataSource){
			super.setDataSource(dataSource);
	}

	@Override
	public LoginEntity validateUser(String username, String password) {
		LoginEntity loginEntity=null;
		try {
			loginEntity=(LoginEntity)super.getJdbcTemplate().queryForObject("select fl.fsn,fl.userid,fl.eid,fl.password,fl.email,fl.locked,fl.role,fl.doe,fl.dom,fd.name,fd.sex as gender from faculity_login_tbl as fl,emp_db as fd where fl.eid=fd.id and  fl.userid=? and fl.password=?",new Object[]{username,password},new BeanPropertyRowMapper<LoginEntity>(LoginEntity.class));
			if(loginEntity!=null){
				long eid=Long.parseLong(loginEntity.getEid());
				String reportingManager=super.getJdbcTemplate().queryForObject("select reporting_manager from emp_db where id="+eid,String.class);
				loginEntity.setReportingManager(reportingManager);
			}
		}catch(Exception ex) {
			 String me=ex.getMessage();
			 System.out.println("me = "+me);
			 return null;
		}
		return loginEntity;
	}
	
	@Override
	public String updatePassword(String email,String password) {
		try {
			super.getJdbcTemplate().update("update faculity_login_tbl set password=? where email=?",new Object[]{password,email});
		}catch(Exception ex) {
			 System.out.println(ex.getMessage());
			 return null;
		}
		return "updated";
	}
	
	
	@Override
	public String changePasswordByUserId(String userid,String newPassword) {
		String email=null;
		try {
			super.getJdbcTemplate().update("update faculity_login_tbl set password=? where userid=?",new Object[]{newPassword,userid});
			 email=super.getJdbcTemplate().queryForObject("select email from faculity_login_tbl where userid=?", new Object[]{userid},String.class);
		}catch(Exception ex) {
			 System.out.println(ex.getMessage());
			 return null;
		}
		return email;
	}

}
