package com.bas.admin.dao.impl;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.bas.admin.dao.LeaveTypeDao;
import com.bas.admin.dao.entity.LeaveTypeEntity;

@Repository("LeaveTypeDaoImpl")
@Scope("singleton")
public class LeaveTypeDaoImpl extends JdbcDaoSupport implements LeaveTypeDao{
	
	@Autowired
	@Qualifier("sdatasource")
	public void setDataSourceInSuper(DataSource dataSource) {
		super.setDataSource(dataSource);
	}
	private int findNextNum() {
		int num = getJdbcTemplate().queryForInt(
				"select max(leaveTypeId) from leave_type_tbl");
		num = num + 1;
		return num;
	}
		@Override
	public String addLeaveType(LeaveTypeEntity leaveTypeEntity) {
		String sql = "insert into leave_type_tbl values(?,?,?,?,?,?)";
		Object[] data = new Object[] { findNextNum(),
				leaveTypeEntity.getLeaveType(),
				leaveTypeEntity.getDescription(), new Date(), new Date(),
				"Rama" };
		super.getJdbcTemplate().update(sql, data);
		String sqlmax = "select max(leaveTypeId) from leave_type_tbl";
		int max=super.getJdbcTemplate().queryForInt(sqlmax);
		return max+"";
	}

	@Override
	public String editLeaveType(LeaveTypeEntity leaveTypeEntity) {
		return null;
	}

	@Override
	public String deleteLeaveType(int leaveTypeId) {
		String query = "delete from leave_type_tbl where leaveTypeId=?";
		super.getJdbcTemplate().update(query, leaveTypeId);
		return "deleted";
	}

	@Override
	public List<LeaveTypeEntity> findLeaveType() {
		String query = "select leaveTypeId,leaveType,description,doe,dom,entryBy from leave_type_tbl";
		List<LeaveTypeEntity> leaveTypeEntities = super.getJdbcTemplate()
				.query(query,
						new BeanPropertyRowMapper(LeaveTypeEntity.class));

		return leaveTypeEntities;
	}

	@Override
	public LeaveTypeEntity findLeaveTypeById(int lveTpId) {
		String query = "select * from leave_type_tbl where leaveTypeId="
				+ lveTpId;
		LeaveTypeEntity leaveTypeEntity = (LeaveTypeEntity) super.getJdbcTemplate()
				.queryForObject(query,
						new BeanPropertyRowMapper(LeaveTypeEntity.class));
		return leaveTypeEntity;
	}

	@Override
	public String updateLeaveType(LeaveTypeEntity leaveTypeEntity) {
		String query = "update leave_type_tbl set leaveType=?,description=?,dom=? where leaveTypeId=?";
		super.getJdbcTemplate().update(
				query,
				new Object[] { leaveTypeEntity.getLeaveType(),
						leaveTypeEntity.getDescription(), new Date(),
						leaveTypeEntity.getLeaveTypeId() });
		System.out.println("DAOIMPL: " + leaveTypeEntity);
		System.out.println(query);
		return "success";
	}

}
