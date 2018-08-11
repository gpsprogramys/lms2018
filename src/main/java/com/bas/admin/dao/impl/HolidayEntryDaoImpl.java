package com.bas.admin.dao.impl;

import java.sql.Types;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

import com.bas.admin.dao.HolidayEntryDao;
import com.bas.admin.dao.entity.HolidayEntryEntity;
import com.bas.common.constant.BASMessageConstant;
import com.bas.common.util.DateUtils;

@Repository("HolidayEntryDaoImpl")
@Scope("singleton")
public class HolidayEntryDaoImpl extends JdbcDaoSupport implements
		HolidayEntryDao {
	
	@Autowired
	@Qualifier("sdatasource")
	public void setDataSourceInSuper(DataSource dataSource) {
		super.setDataSource(dataSource);
	}
	
	@Override
	public byte[] getHoildayImageByDate(String date){
		String query = "select * from holiday_entry_tbl where cdate =?";
		Date cdate=DateUtils.getDateIntoSQLFormat(date);
		HolidayEntryEntity calenderEntities = (HolidayEntryEntity) super.getJdbcTemplate().queryForObject(query,
				new Object[]{cdate}, new BeanPropertyRowMapper(HolidayEntryEntity.class) );
		return calenderEntities.getImage();
	}


@Override
	public List<HolidayEntryEntity> getHolidayId(int month){
		String query = "select cdate as holidayDate, description, comment,image from holiday_entry_tbl where month(cdate) = ? and weekend=? order by holidayDate desc";
		List<HolidayEntryEntity> calenderEntities = super.getJdbcTemplate().query(query,
				new Object[]{month,"no"}, new BeanPropertyRowMapper<HolidayEntryEntity>(HolidayEntryEntity.class));
		return calenderEntities;
	}

	@Override
	public String addHolidayEntry(HolidayEntryEntity holidayEntryEntity) {
		String sql = "insert into holiday_entry_tbl values(?,?,?,?,?,?,?,?,?)";
		LobHandler lobHandler = new DefaultLobHandler();
		SqlLobValue image = new SqlLobValue(holidayEntryEntity.getImage(),	lobHandler);
		Object[] data = new Object[] { holidayEntryEntity.getHolidayDate(), 
				holidayEntryEntity.getWorking(),
				holidayEntryEntity.getHoliday(),
				holidayEntryEntity.getWeekend(),
				holidayEntryEntity.getDescription(),holidayEntryEntity.getComment(),holidayEntryEntity.getHolidayType(),image,DateUtils.getCurrentTimestatmp()};
		int dataTye[] = new int[] {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
		Types.VARCHAR,Types.BLOB,Types.TIMESTAMP};
		// firing the query  
		try {
		super.getJdbcTemplate().update(sql, data,dataTye);
		}catch(Exception exe){
			 System.out.println(exe.getMessage());
			 return BASMessageConstant.RECORD_ALERADY_EXIST;
		}
		return "Added";
	}

	@Override
	public String editHolidayEntry(HolidayEntryEntity holidayEntryEntity) {
		String query = "update holiday_entry_tbl set working=?,holiday=?,weekend=?, description=? where cdate=?";
		Object[] data = new Object[] {
				holidayEntryEntity.getWorking(),
				holidayEntryEntity.getHoliday(),
				holidayEntryEntity.getWeekend(),
				holidayEntryEntity.getDescription(),
				 holidayEntryEntity.getHolidayDate()};
		super.getJdbcTemplate().update(query, data);
		return "updated";

		
	}

	@Override
	public String deleteHolidayEntry(String date) {
		String query = "delete from holiday_entry_tbl where cdate=?";
		Object data[] = new Object[] { date };
		super.getJdbcTemplate().update(query, data);
		return "deleted";
	}

	@Override
	public List<HolidayEntryEntity> findHolidayEntry() {
		String query = "select cdate as holidayDate,working,holiday,weekend,description,comment,holidayType,image from holiday_entry_tbl where holidayType!='Weekend' order by holidayDate desc";
		List<HolidayEntryEntity> holidayEntryEntities = super.getJdbcTemplate()
				.query(query,new BeanPropertyRowMapper(HolidayEntryEntity.class));
		return holidayEntryEntities;
	}

	@Override
	public HolidayEntryEntity findHolidayEntryByDate(String date) {
		String query = "select cdate as holidayDate, working,holiday,weekend,description from holiday_entry_tbl where cdate="+date;
		HolidayEntryEntity holidayEntryEntity = (HolidayEntryEntity) super.getJdbcTemplate()
				.queryForObject(query,
						new BeanPropertyRowMapper(HolidayEntryEntity.class));
		return holidayEntryEntity;
	}

}
