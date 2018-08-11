package com.bas.admin.dao;

import java.util.List;

import com.bas.admin.dao.entity.CategoryEntity;

public interface CategoryDao {
	public String addCategory(CategoryEntity categoryEntity);
	public String editCategory(CategoryEntity categoryEntity);
	public String deleteCategory(int departmentId);
	public List<CategoryEntity> findCategory();
	public CategoryEntity findCategoryById(int catId);
	public String updateCategory(CategoryEntity categoryEntity);
}
