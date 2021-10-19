package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class InstructionConfigDao extends BaseDao{
	
	private final static String TABLE_NAME = "system_instruction_config";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" select * from "+TABLE_NAME);
		sql.append(" (nolock) order by sms_sort_id ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryBrief(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" select * from "+TABLE_NAME);
		sql.append(" (nolock) order by brief_sort_id ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryHas(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select * from "+TABLE_NAME+" (nolock) where id = :id ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int updateSortUp(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("update " + TABLE_NAME + " set sms_sort_id = sms_sort_id - 1");
		sql.append(" where column_name = :column_name ");
		return executeUpdate(sql.toString(),params);
	}
	
	public int updateSortDown(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("update " + TABLE_NAME + " set sms_sort_id = sms_sort_id + 1");
		sql.append(" where column_name = :column_name ");
		return executeUpdate(sql.toString(),params);
	}
	
	public int updateShow(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("update " + TABLE_NAME + " set sms_is_show = :sms_is_show");
		sql.append(" where column_name = :column_name ");
		return executeUpdate(sql.toString(),params);
	}
	
	public int updateSortUpBrief(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("update " + TABLE_NAME + " set brief_sort_id = brief_sort_id - 1");
		sql.append(" where column_name = :column_name ");
		return executeUpdate(sql.toString(),params);
	}
	
	public int updateSortDownBrief(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("update " + TABLE_NAME + " set brief_sort_id = brief_sort_id + 1");
		sql.append(" where column_name = :column_name ");
		return executeUpdate(sql.toString(),params);
	}
	
	public int updateShowBrief(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("update " + TABLE_NAME + " set brief_is_show = :brief_is_show");
		sql.append(" where column_name = :column_name ");
		return executeUpdate(sql.toString(),params);
	}
	
	public int updateTitle(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("update " + TABLE_NAME + " set brief_title = :brief_title");
		sql.append(" where column_name = :column_name ");
		return executeUpdate(sql.toString(),params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME, params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from "+TABLE_NAME)
		   .append(" (nolock) where id = :id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
}
