/*package com.bas.admin.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.bas.admin.dao.HolidayEntryDao;
import com.bas.admin.dao.entity.HolidayEntryEntity;

@Repository("HolidayEntryDaoJPAImpl")
@Scope("singleton")
@Transactional(value="jpaTransactionManager",isolation=Isolation.SERIALIZABLE)
public class HolidayEntryDaoJPAImpl  implements
		HolidayEntryDao {
	
	@PersistenceContext
	private EntityManager entityManager; //similar to session in hiberate
	
	@Override
	public String addHolidayEntry(HolidayEntryEntity holidayEntryEntity) {
		entityManager.persist(holidayEntryEntity);
		return "Added";
	}

	@Override
	public String editHolidayEntry(HolidayEntryEntity holidayEntryEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteHolidayEntry(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HolidayEntryEntity> findHolidayEntry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HolidayEntryEntity findHolidayEntryByDate(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HolidayEntryEntity> getHolidayId(int month) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getHoildayImageByDate(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
*/