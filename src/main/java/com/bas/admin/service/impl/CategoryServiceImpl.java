package com.bas.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bas.admin.dao.CategoryDao;
import com.bas.admin.dao.entity.CategoryEntity;
import com.bas.admin.service.CategoryService;
import com.bas.admin.web.controller.form.CategoryForm;
/**
 * 
 * @author Amogh
 *
 */
@Service("CategoryServiceImpl")
@Transactional
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	@Qualifier("CategoryDaoImpl")
	private CategoryDao categoryDao;

	@Override
	public String addCategory(CategoryForm categoryForm) {
		CategoryEntity categoryEntity = new CategoryEntity();
		BeanUtils.copyProperties(categoryForm, categoryEntity);
		String result = categoryDao.addCategory(categoryEntity);
		return result;
	}

	@Override
	public String editCategory(CategoryForm categoryForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteCategory(int categoryId) {
		categoryDao.deleteCategory(categoryId);
		return "success";
	}
	
	@Override
	public List<CategoryForm> findCategory() {
		List<CategoryEntity> categoryEntities = categoryDao
				.findCategory();
		List<CategoryForm> categoryForms = new ArrayList<CategoryForm>();

		for (CategoryEntity ce : categoryEntities) {

			CategoryForm cf = new CategoryForm();
			BeanUtils.copyProperties(ce, cf);
			categoryForms.add(cf);
		}

		return categoryForms;
	}

	@Override
	public CategoryForm findCategoryById(int catId) {
		CategoryEntity categoryEntity = categoryDao
				.findCategoryById(catId);
		CategoryForm categoryForm = new CategoryForm();
		BeanUtils.copyProperties(categoryEntity, categoryForm);
		return categoryForm;
	}

	@Override
	public String updateCategory(CategoryForm categoryForm) {
		CategoryEntity categoryEntity = new CategoryEntity();
		BeanUtils.copyProperties(categoryForm, categoryEntity);
		String res = categoryDao.updateCategory(categoryEntity);

		return res;
	}
	

}
