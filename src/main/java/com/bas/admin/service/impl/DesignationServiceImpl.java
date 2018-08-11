package com.bas.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bas.admin.dao.DesignationDao;
import com.bas.admin.dao.entity.DesignationEntity;
import com.bas.admin.service.DesignationService;
import com.bas.admin.web.controller.form.DesignationForm;

/**
 * 
 * @author Amogh
 * 
 */
@Service("DesignationServiceImpl")
@Transactional
public class DesignationServiceImpl implements DesignationService {
	@Autowired
	@Qualifier("DesignationDaoImpl")
	private DesignationDao designationDao;

	@Override
	public String addDesignation(DesignationForm designationForm) {
		DesignationEntity designationEntity = new DesignationEntity();
		BeanUtils.copyProperties(designationForm, designationEntity);
		String result = designationDao.addDesignation(designationEntity);
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public String editDesignation(DesignationForm designationForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteDesignation(int designationId) {
		designationDao.deleteDesignation(designationId);
		return "success";
	}

	@Override
	public List<DesignationForm> findDesignations() {
		List<DesignationEntity> designationEntities = designationDao
				.findDesignations();
		List<DesignationForm> designationForms = new ArrayList<DesignationForm>();

		for (DesignationEntity de : designationEntities) {

			DesignationForm df = new DesignationForm();
			BeanUtils.copyProperties(de, df);
			designationForms.add(df);
		}

		return designationForms;
	}

	@Override
	public DesignationForm findDesignationById(int desigId) {
		DesignationEntity designationEntity = designationDao
				.findDesignationById(desigId);
		DesignationForm designationForm = new DesignationForm();
		BeanUtils.copyProperties(designationEntity, designationForm);
		return designationForm;
	}

	@Override
	public String updateDesignation(DesignationForm designationForm) {
		DesignationEntity designationEntity = new DesignationEntity();
		BeanUtils.copyProperties(designationForm, designationEntity);
		String res = designationDao.updateDesignation(designationEntity);
		return res;
	}

}
