package com.bas.admin.service;

import java.util.List;

import com.bas.admin.web.controller.form.CategoryForm;

public interface CategoryService {
	public String addCategory(CategoryForm categoryForm);
	public String editCategory(CategoryForm categoryForm);
	public String deleteCategory(int categoryId);
	public List<CategoryForm> findCategory();
	public CategoryForm findCategoryById(int catId);
	public String updateCategory(CategoryForm categoryForm);
}


