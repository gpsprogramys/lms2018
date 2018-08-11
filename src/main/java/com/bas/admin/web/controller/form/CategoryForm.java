package com.bas.admin.web.controller.form;

import java.util.Date;

public class CategoryForm {
private int categoryId;
private String categoryName;
private String description;
private Date doe;
private Date dom;
private String entryBy;
public int getCategoryId() {
	return categoryId;
}
public void setCategoryId(int categoryId) {
	this.categoryId = categoryId;
}
public String getCategoryName() {
	return categoryName;
}
public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public Date getDoe() {
	return doe;
}
public void setDoe(Date doe) {
	this.doe = doe;
}
public Date getDom() {
	return dom;
}
public void setDom(Date dom) {
	this.dom = dom;
}
public String getEntryBy() {
	return entryBy;
}
public void setEntryBy(String entryBy) {
	this.entryBy = entryBy;
}
@Override
public String toString() {
	return "CategoryEntity [categoryId=" + categoryId + ", categoryName="
			+ categoryName + ", description=" + description + ", doe=" + doe
			+ ", dom=" + dom + ", entryBy=" + entryBy + "]";
}

}
