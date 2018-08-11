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

import com.bas.admin.dao.DesignationDao;
import com.bas.admin.dao.entity.DesignationEntity;

/**
 * 
 * @author Amogh
 * 
 */
@Repository("DesignationDaoImpl")
@Scope("singleton")
public class DesignationDaoImpl extends JdbcDaoSupport implements
		DesignationDao {

	@Autowired
	@Qualifier("sdatasource")
	public void setDataSourceInSuper(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	private int findNextNum() {
		int num = getJdbcTemplate().queryForInt(
				"select max(designationId) from designations_tbl");
		num = num + 1;
		return num;
	}

	@Override
	public String addDesignation(DesignationEntity designationEntity) {
		String sql = "insert into designations_tbl values(?,?,?,?,?,?)";
		Object[] data = new Object[] { findNextNum(),
				designationEntity.getDesignationName(),
				designationEntity.getDescription(), designationEntity.getDoe(), designationEntity.getDom(),
				designationEntity.getEntryBy() };
		super.getJdbcTemplate().update(sql, data);
		String sqlmax = "select max(designationId) from designations_tbl";
		int max=super.getJdbcTemplate().queryForInt(sqlmax);
		return max+"";
	}

	@Override
	public String editDesignation(DesignationEntity designationEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteDesignation(int designationId) {
		String query = "delete from designations_tbl where designationId=?";
		super.getJdbcTemplate().update(query, designationId);
		return "deleted";
	}

	@Override
	public List<DesignationEntity> findDesignations() {
		String query = "select * from designations_tbl";
		List<DesignationEntity> designationEntities = super.getJdbcTemplate()
				.query(query,
						new BeanPropertyRowMapper(DesignationEntity.class));
		return designationEntities;
	}

	@Override
	public DesignationEntity findDesignationById(int desigId) {
		String query = "select * from designations_tbl where designationId="
				+ desigId;
		DesignationEntity designationEntity = (DesignationEntity) super.getJdbcTemplate()
				.queryForObject(query,
						new BeanPropertyRowMapper(DesignationEntity.class));
		return designationEntity;
	}

	@Override
	public String updateDesignation(DesignationEntity designationEntity) {

		String query = "update designations_tbl set designationName=?,description=?,dom=? where designationId=?";
		super.getJdbcTemplate().update(
				query,
				new Object[] { designationEntity.getDesignationName(),
						designationEntity.getDescription(), new Date(),
						designationEntity.getDesignationId() });

		System.out.println("DAOIMPL: " + designationEntity);
		System.out.println(query);

		return "success";
	}

}