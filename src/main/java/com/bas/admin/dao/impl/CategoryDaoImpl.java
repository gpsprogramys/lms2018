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

import com.bas.admin.dao.CategoryDao;
import com.bas.admin.dao.entity.CategoryEntity;

/**
 * 
 * @author Amogh
 * 
 */
@Repository("CategoryDaoImpl")
@Scope("singleton")
public class CategoryDaoImpl extends JdbcDaoSupport implements CategoryDao {

	@Autowired
	@Qualifier("sdatasource")
	public void setDataSourceInSuper(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	private int findNextNum() {
		int num = getJdbcTemplate().queryForInt(
				"select max(categoryId) from category_tbl");
		num = num + 1;
		return num;
	}

	@Override
	public String addCategory(CategoryEntity categoryEntity) {
		String sql = "insert into category_tbl values(?,?,?,?,?,?)";
		Object[] data = new Object[] { findNextNum(),
				categoryEntity.getCategoryName(),
				categoryEntity.getDescription(), new Date(), new Date(), "dave" };
		super.getJdbcTemplate().update(sql, data);
		return "Added";
	}

	@Override
	public String editCategory(CategoryEntity categoryEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteCategory(int departmentId) {
		String query = "delete from category_tbl where categoryId=?";
		super.getJdbcTemplate().update(query, departmentId);
		return "deleted";
	}

	@Override
	public List<CategoryEntity> findCategory() {
		String query = "select * from category_tbl";
		List<CategoryEntity> categoryEntities = super.getJdbcTemplate().query(
				query, new BeanPropertyRowMapper(CategoryEntity.class));

		return categoryEntities;
	}

	@Override
	public CategoryEntity findCategoryById(int catId) {
		String query = "select * from category_tbl where categoryId=" + catId;
		CategoryEntity categoryEntity = (CategoryEntity) super.getJdbcTemplate().queryForObject(
				query, new BeanPropertyRowMapper(CategoryEntity.class));
		return categoryEntity;
	}

	@Override
	public String updateCategory(CategoryEntity categoryEntity) {
		String query = "update category_tbl set categoryName=?,description=?,dom=? where categoryId=?";
		super.getJdbcTemplate().update(
				query,
				new Object[] { categoryEntity.getCategoryName(),
						categoryEntity.getDescription(), new Date(),
						categoryEntity.getCategoryId() });
		System.out.println("DAOIMPL: " + categoryEntity);
		System.out.println(query);
		return "success";
	}

}
