package com.bas.admin.dao.impl;

import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.bas.admin.dao.OrgTimeDao;
import com.bas.admin.dao.entity.OrganizationTimeEntity;


/**
 * @author Sid
 *
 */

@Repository("OrgTimeDaoImpl")
public class OrgTimeDaoImpl extends JdbcDaoSupport implements OrgTimeDao {

	@Autowired
	@Qualifier("sdatasource")
	public void setDataSourceInSuper(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	public String editOrgTime(OrganizationTimeEntity organizationTimeEntity) {
		String query = "UPDATE organisation_time SET date=?,intime=?,outtime=?,latein=?,earlyout=? WHERE date=?";
		//super.getJdbcTemplate().update(query,new Object[]{organizationTimeEntity.getFromdate(),organizationTimeEntity.getIntime(),organizationTimeEntity.getOuttime(),organizationTimeEntity.getLatein(),organizationTimeEntity.getEarlyout(),organizationTimeEntity.getFromdate()});
		return "done";	
	}

	@Override
	public String deleteOrgTime(int sno) {
		String query = "DELETE FROM organisation_time WHERE sno=?";
		super.getJdbcTemplate().update(query, sno);
		return "done";
	}

	@Override
	public List<OrganizationTimeEntity> findOrgTimes() {
		String query = "select sno,intime,outtime,latein,earlyout,doe,dom,enteredby,active from organisation_time2";
		Date d= new Date();
		Object[] data = {};
		int[] dataType = {};
		System.out.println(d);	
		System.out.println();	
		String query2 = "update organisation_time2 set active=0";
		super.getJdbcTemplate().update(query2);
		String query1 = "update organisation_time2 set active=1 where dom=?";
		data = new Object[] {d};
		dataType = new int[] { Types.DATE};
		super.getJdbcTemplate().update(query1, data, dataType);
		List<OrganizationTimeEntity> organizationTimeEntitieslist = super.getJdbcTemplate().query(query,new BeanPropertyRowMapper<OrganizationTimeEntity>(OrganizationTimeEntity.class));
		return organizationTimeEntitieslist;
	}

	@Override
	public String addOrgTime(OrganizationTimeEntity organizationTimeEntity) {		
		Calendar cal = Calendar.getInstance();
		//cal.setTime(organizationTimeEntity.getFromdate());
		int fromdate = cal.get(Calendar.MONTH);
		int pkey = returnKey();

		String sql = "INSERT INTO organisation_time VALUES(?,?,?,?,?,?)";		
		//Object[] data = new Object[] {pkey,organizationTimeEntity.getFromdate(),organizationTimeEntity.getIntime(),organizationTimeEntity.getOuttime(),organizationTimeEntity.getLatein(),organizationTimeEntity.getEarlyout()};
		//super.getJdbcTemplate().update(sql, data);
		return "__successfully inserted___";
	}

	@Override
	public OrganizationTimeEntity findOrgTimes(int sno) {
		String query = "SELECT sno,date as fromdate,intime,outtime,latein,earlyout FROM organisation_time WHERE sno="+sno+"";		
		OrganizationTimeEntity organizationTimeEntity = super.getJdbcTemplate().queryForObject(query,new BeanPropertyRowMapper<OrganizationTimeEntity>(OrganizationTimeEntity.class));
		return organizationTimeEntity;
	}

	/**
	 * @return Return the Primary key for organization time.
	 * Set as the count of the number of rows in the table.	 *
	 */

	public int returnKey(){
		String rqry = "SELECT COUNT(*) FROM organisation_time";
		int num = super.getJdbcTemplate().queryForObject(rqry, Integer.class);
		return (num);		

	}

}
