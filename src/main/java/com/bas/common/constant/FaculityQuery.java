package com.bas.common.constant;

/**
 * 
 * @author astha
 */
public interface FaculityQuery {
  //select * from  faculity_att_tab   order by cdate desc  LIMIT 2 ;  
  public static String SELECT_LAST_TEN_ATTENDANCE ="select cdate,intime,outtime,status from faculity_att_tab where fid=? order by cdate desc  LIMIT 10";
  public static String SELECT_ATTENDANCE_SUMMARY_HEADER ="select id,name,designation,department,image,type from emp_db where id=?";
  public static String SELECT_CURRENT_ORGANIZATION_TIME ="select  *  from organisation_time order by date desc LIMIT 1";
  public static String UPDATE_PASSWORD_BY_EMP_ID="update faculity_login_tbl set password=? where eid=?";
  public static String SELECT_USERID_FROM_FACULITY_LOGIN="select eid from faculity_login_tbl where userid=? and password=?";
  
}
