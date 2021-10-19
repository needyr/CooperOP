package cn.crtech.cooperop.application.dao;

import java.sql.Statement;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;

public class DingdingDao extends BaseDao {
	private final static String TABLE_NAME_ATTENDANCE = "cr_bmc.dbo.dd_attendance";
	
	/**
	 * 查询所有人员的ID，连接成一个字符串，逗号隔开
	 * @param params 无
	 * @return {userids:"001,002,..."}
	 * @throws Exception
	 */
	public Record getDDUserids(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select stuff((select ','+id from (SELECT DISTINCT id FROM cr_bmc.dbo.dd_user_tmp) t for xml path('')),1,1,'') as userids ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getAttendanceMaxDate(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select max(workDate) as sdate from "+TABLE_NAME_ATTENDANCE);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public void delAttendance(Long deldate) throws Exception {
		Statement stmt=conn.createStatement();
		StringBuffer sql = new StringBuffer();
		sql.append("delete from "+TABLE_NAME_ATTENDANCE+" where workDate>="+deldate);
		stmt.execute(sql.toString());
	}
	
	public int insertAttendance(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME_ATTENDANCE, params);
	}
	
}
