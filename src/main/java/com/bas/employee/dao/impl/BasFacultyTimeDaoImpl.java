package com.bas.employee.dao.impl;

import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bas.employee.dao.BasFacultyTimeDao;
import com.bas.employee.dao.entity.FaculityTimeEntity;

@Repository("BasFacultyTimeDaoImpl")
@Transactional
public class BasFacultyTimeDaoImpl extends JdbcDaoSupport implements
		BasFacultyTimeDao {

	@Autowired
	@Qualifier("sdatasource")
	public void setDataSourceInSuper(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	private int findNextNum() {
		int num = getJdbcTemplate().queryForInt(
				"select max(fid) from faculity_time_table");
		num = num + 1;
		return num;
	}

	@Override
	public String addFacultyTime(FaculityTimeEntity faculityTimeEntity) {

		LobHandler lobHandler = new DefaultLobHandler();
		SqlLobValue cimage = new SqlLobValue(faculityTimeEntity.getImage(),
				lobHandler);

		Object data[] = new Object[] { findNextNum(),
				faculityTimeEntity.getSubjectCode(),
				faculityTimeEntity.getSubjectName(),
				faculityTimeEntity.getBranchSemSec(),
				faculityTimeEntity.getSubjectType(),
				faculityTimeEntity.getShortSubjectName(),
				faculityTimeEntity.getDayName(),
				faculityTimeEntity.getPeriod(),
				faculityTimeEntity.getClassRoom(),
				faculityTimeEntity.getStartTime(), faculityTimeEntity.getDoe(),
				faculityTimeEntity.getDom(), faculityTimeEntity.getEndTime(),
				faculityTimeEntity.getDescription(), cimage };
		String query = "insert into faculity_time_table values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int dataTye[] = new int[] { Types.INTEGER, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.DATE, Types.DATE, Types.VARCHAR, Types.VARCHAR,
				Types.BLOB };
		super.getJdbcTemplate().update(query, data, dataTye);
		return "ahahha";
	}

	@Override
	public String editFacultyTime(FaculityTimeEntity faculityTimeEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteFacultyTime(int fid) {
		String query = "delete from faculity_time_table where fid=?";
		super.getJdbcTemplate().update(query, fid);
		return "deleted";
	}

	@Override
	public List<FaculityTimeEntity> findFacultyTime() {
		List<FaculityTimeEntity> faculityTimeEntities = super.getJdbcTemplate()
				.query("select * from faculity_time_table",
						new BeanPropertyRowMapper(FaculityTimeEntity.class));
		return faculityTimeEntities;
	}

	@Override
	public FaculityTimeEntity findFacultyTimeById(int facId) {
		String query = "select * from faculity_time_table where fid=" + facId;
		FaculityTimeEntity faculityTimeEntity = (FaculityTimeEntity) super.getJdbcTemplate()
				.queryForObject(query,
						new BeanPropertyRowMapper(FaculityTimeEntity.class));
		return faculityTimeEntity;
	}

	@Override
	public String updateFacultyTime(FaculityTimeEntity faculityTimeEntity) {
		String query = "update faculity_time_table set shortSubjectName=?,subjectName=?,subjectType=?,period=?,startTime=?,doe=?,dom=?,endtime=?,description=?"
				+ " where fid=? AND subjectCode=? AND branchSemSec=? AND dayName=? AND classRoom=? ";
		
		StringBuilder builder=new StringBuilder();
		for(String per:faculityTimeEntity.getPeriod()){
			builder.append(per+",");
		}
		super.getJdbcTemplate().update(
				query,
				new Object[] { faculityTimeEntity.getShortSubjectName(),
						faculityTimeEntity.getSubjectName(),
						faculityTimeEntity.getSubjectType(),
						builder.toString(),
						faculityTimeEntity.getStartTime(),
						faculityTimeEntity.getDoe(),
						faculityTimeEntity.getDom(),
						faculityTimeEntity.getEndTime(),
						faculityTimeEntity.getDescription(),
						faculityTimeEntity.getFid(),
						faculityTimeEntity.getSubjectCode(),
						faculityTimeEntity.getBranchSemSec(),
						faculityTimeEntity.getDayName(),
						faculityTimeEntity.getClassRoom() });
	
		System.out.println("DAOIMPL: " + faculityTimeEntity);
		//System.out.println(query);
		return "success";
	}

	@Override
	public FaculityTimeEntity findFacultyTimeByParams(int facId,
			String dayName, String classRoom, String subjectCode) {

		String query = "select * from faculity_time_table where fid=" + facId
				+ " AND dayName='" + dayName + "' AND  classRoom='" + classRoom
				+ "' AND subjectCode='" + subjectCode + "'";
		FaculityTimeEntity faculityTimeEntity = (FaculityTimeEntity) super.getJdbcTemplate()
				.queryForObject(query,
						new BeanPropertyRowMapper(FaculityTimeEntity.class));

		return faculityTimeEntity;
	}

}
