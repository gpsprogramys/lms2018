package com.bas.employee.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.bas.common.web.controller.form.SubjectArrangementVO;
import com.bas.common.web.controller.form.SubjectNameCodeVO;
import com.bas.employee.dao.FacultySubjectAssignmentDao;
import com.bas.employee.dao.entity.SubjectAssignmentEntity;

@Repository("FacultySubjectAssignmentDaoImpl")
@Scope("singleton")
public class FacultySubjectAssignmentDaoImpl extends JdbcDaoSupport implements FacultySubjectAssignmentDao{

	@Autowired
	@Qualifier("sdatasource")
	public void setDataSourceInSuper(DataSource dataSource) {
		super.setDataSource(dataSource);
	}
	
	
	@Override
	public List<String> findBranchSemSection(String empid){
		String query="select BR_SEM_SEC FROM subject_assignment where USER_ID="+empid;
		List<String> branchSemSecList=super.getJdbcTemplate().queryForList(query,String.class);
		return branchSemSecList;
	}
	
	@Override
	public List<SubjectArrangementVO> findFacultyByBranchPeriod(String branchSemSec,String dayPeriod,int empid) {
		 List<SubjectArrangementVO> arrangementVOs=new ArrayList<SubjectArrangementVO>();
		//String sql="select f.name as name,s.USER_ID as userId,s.DAY_PERIOD as dayPeriods from emp_db as f,subject_assignment as s where f.id=s.USER_ID and s.BR_SEM_SEC=? and id!=?";
		String sql="select f.name as name,s.USER_ID as userId,s.DAY_PERIOD as dayPeriods,s.SUB_CODE as subCode,F_SHORT_SUBJECT_NAME as subjectShortName from emp_db as f,subject_assignment as s where f.id=s.USER_ID and s.BR_SEM_SEC=? and id!=?";
		List<SubjectAssignmentEntity>  subjectAssignmentEntityList=super.getJdbcTemplate().query(sql, new Object[]{branchSemSec,empid}, new BeanPropertyRowMapper(SubjectAssignmentEntity.class));
		Map<String,String> facultyIdNamesMap=new LinkedHashMap<String,String>();
		for(SubjectAssignmentEntity subjectAssignmentEntity:subjectAssignmentEntityList) {
			if(subjectAssignmentEntity.getDayPeriods()!=null && !subjectAssignmentEntity.getDayPeriods().contains(dayPeriod)) {
				facultyIdNamesMap.put(subjectAssignmentEntity.getUserId(), subjectAssignmentEntity.getName());
				SubjectArrangementVO arrangementVO=new SubjectArrangementVO();
				arrangementVO.setFacultyId(subjectAssignmentEntity.getUserId());
				if(arrangementVOs.contains(arrangementVO)){
					arrangementVO=arrangementVOs.get(arrangementVOs.indexOf(arrangementVO));
				}
				arrangementVO.setName(subjectAssignmentEntity.getName());
				SubjectNameCodeVO subjectNameCodeVO=new SubjectNameCodeVO();
				subjectNameCodeVO.setSubCode(subjectAssignmentEntity.getSubCode());
				subjectNameCodeVO.setSubjectShortName(subjectAssignmentEntity.getSubjectShortName());
				arrangementVO.getSubjectsCode().add(subjectNameCodeVO);
				arrangementVOs.add(arrangementVO);
			}
		}
		return arrangementVOs;
	}


	@Override
	public List<SubjectNameCodeVO> findSubjectsByFacultyBranchPeriod(
			String branchSemSec, String dayPeriod, int empid) {
		List<SubjectNameCodeVO> nameCodeVOs=new ArrayList<SubjectNameCodeVO>();
		String sql="select s.DAY_PERIOD as dayPeriods,s.SUB_CODE as subCode,F_SHORT_SUBJECT_NAME as subjectShortName from subject_assignment as s where  s.BR_SEM_SEC=? and s.USER_ID=?";
		List<SubjectAssignmentEntity>  subjectAssignmentEntityList=super.getJdbcTemplate().query(sql, new Object[]{branchSemSec,empid}, new BeanPropertyRowMapper(SubjectAssignmentEntity.class));
		for(SubjectAssignmentEntity subjectAssignmentEntity:subjectAssignmentEntityList) {
			if(subjectAssignmentEntity.getDayPeriods()!=null && !subjectAssignmentEntity.getDayPeriods().contains(dayPeriod)) {
					SubjectNameCodeVO subjectNameCodeVO=new SubjectNameCodeVO();
					subjectNameCodeVO.setSubCode(subjectAssignmentEntity.getSubCode());
					subjectNameCodeVO.setSubjectShortName(subjectAssignmentEntity.getSubjectShortName());
					nameCodeVOs.add(subjectNameCodeVO);
			}		
		}
		return nameCodeVOs;
	}
}
