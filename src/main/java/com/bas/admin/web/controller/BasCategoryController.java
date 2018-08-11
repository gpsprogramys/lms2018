package com.bas.admin.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bas.admin.service.CategoryService;
import com.bas.admin.web.controller.form.CategoryForm;
import com.bas.common.constant.NavigationConstant;
/**
 * 
 * @author Amogh
 *
 */
@Controller
public class BasCategoryController {

	@Autowired
	@Qualifier("CategoryServiceImpl")
	private CategoryService categoryService;

	@RequestMapping(value = "/addCategory.htm", method = RequestMethod.GET)
	public String showAddCategory(Model model) {
		CategoryForm categoryForm = new CategoryForm();
		model.addAttribute("categoryForm", categoryForm);
		List<CategoryForm> categoryForms = categoryService.findCategory();
		model.addAttribute("buttonLable", "Add Category");
		model.addAttribute("categoryForms", categoryForms);
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.ADD_CATEGORY_PAGE;
	}

	@RequestMapping(value = "/addCategory.htm", method = RequestMethod.POST)
	public String submitAddCategory(
			@ModelAttribute("categoryForm") CategoryForm categoryForm,
			@RequestParam(value = "buttonAction", required = false) String buttonAction,
			Model model) {
		if (buttonAction != null && buttonAction.equals("Update Category")) {

			categoryService.updateCategory(categoryForm);
			System.out.println("Contr : " + categoryForm);
		} else {
			categoryService.addCategory(categoryForm);
		}

		List<CategoryForm> categoryForms = categoryService.findCategory();
		model.addAttribute("buttonLable", "Add Category");

		// We are creating a blank form so that we can show blank value
		CategoryForm ncategoryForm = new CategoryForm();
		model.addAttribute("categoryForm", ncategoryForm);

		model.addAttribute("categoryForms", categoryForms);
		System.out.println(categoryForms);
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.ADD_CATEGORY_PAGE;
	}
	
	@RequestMapping(value = "/editCategory.htm", method = RequestMethod.GET)
	public String editCategory(HttpServletRequest request, Model model) {
		Integer catId = Integer.parseInt(request.getParameter("categoryId"));
		
		CategoryForm categoryForm = categoryService
				.findCategoryById(catId);
		model.addAttribute("categoryForm", categoryForm);
		List<CategoryForm> categoryForms = categoryService
				.findCategory();
		model.addAttribute("buttonLable", "Update Category");
		model.addAttribute("categoryForms", categoryForms);
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.ADD_CATEGORY_PAGE;
	}
	
	@RequestMapping(value = "/deleteCategory.htm", method = RequestMethod.GET)
	public String deleteCategory(HttpServletRequest request, Model model) {
		Integer catId = Integer.parseInt(request.getParameter("categoryId"));
		System.out.println("catId" + catId);
		categoryService.deleteCategory(catId);
		return "redirect:addCategory.htm";
}
}
