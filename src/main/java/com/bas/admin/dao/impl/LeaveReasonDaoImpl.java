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

import com.bas.admin.dao.LeaveReasonDao;
import com.bas.admin.dao.entity.LeaveReasonEntity;

@Repository("LeaveReasonDaoImpl")
@Scope("singleton")
public class LeaveReasonDaoImpl extends JdbcDaoSupport implements
		LeaveReasonDao {

	@Autowired
	@Qualifier("sdatasource")
	public void setDataSourceInSuper(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	private int findNextNum() {
		int num = getJdbcTemplate().queryForInt(
				"select max(leaveId) from leave_reason_tbl");
		num = num + 1;
		return num;
	}

	@Override
	public String addLeaveReason(LeaveReasonEntity leaveReasonEntity) {
		String sql = "insert into leave_reason_tbl values(?,?,?,?,?,?)";
		Object[] data = new Object[] { findNextNum(),
				leaveReasonEntity.getLeaveDetail(),
				leaveReasonEntity.getDescription(), new Date(), new Date(),
				leaveReasonEntity.getEntryBy() };
		super.getJdbcTemplate().update(sql, data);
		return "Added";
	}

	@Override
	public String editLeaveReason(LeaveReasonEntity leaveReasonEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteLeaveReason(int leaveId) {
		String query = "delete from leave_reason_tbl where leaveId=?";
		super.getJdbcTemplate().update(query, leaveId);
		return "deleted";
	}

	@Override
	public List<LeaveReasonEntity> findLeaveReason() {
		
		String query = "select * from leave_reason_tbl";
		List<LeaveReasonEntity> leaveReasonEntities = super.getJdbcTemplate()
				.query(query,
						new BeanPropertyRowMapper(LeaveReasonEntity.class));

		return leaveReasonEntities;
	}

	@Override
	public LeaveReasonEntity findLeaveReasonById(int lveRsnId) {
		String query = "select * from leave_reason_tbl where leaveId="
				+ lveRsnId;
		LeaveReasonEntity leaveReasonEntity = (LeaveReasonEntity) super.getJdbcTemplate()
				.queryForObject(query,
						new BeanPropertyRowMapper(LeaveReasonEntity.class));
		return leaveReasonEntity;
	}

	@Override
	public String updateLeaveReason(LeaveReasonEntity leaveReasonEntity) {
		String query = "update leave_reason_tbl set leaveDetail=?,description=?,dom=? where leaveId=?";
		super.getJdbcTemplate().update(
				query,
				new Object[] { leaveReasonEntity.getLeaveDetail(),
						leaveReasonEntity.getDescription(), new Date(),
						leaveReasonEntity.getLeaveId() });
		System.out.println("DAOIMPL: " + leaveReasonEntity);
		System.out.println(query);
		return "success";
	}

}
