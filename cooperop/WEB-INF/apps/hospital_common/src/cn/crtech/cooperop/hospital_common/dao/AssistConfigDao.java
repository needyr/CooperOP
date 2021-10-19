package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;

public class AssistConfigDao extends BaseDao{

private final static String TABLE_NAME = "system_assist_config";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" select * from "+TABLE_NAME);
		sql.append(" (nolock) order by sort_id ");
		return executeQuery(sql.toString(), params);
	}
	
	
	public int updateSortUp(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("update " + TABLE_NAME + " set sort_id = sort_id - 1");
		sql.append(" where id = :id ");
		return executeUpdate(sql.toString(),params);
	}
	
	public int updateSortDown(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("update " + TABLE_NAME + " set sort_id = sort_id + 1");
		sql.append(" where id = :id ");
		return executeUpdate(sql.toString(),params);
	}
	
	public int updateShow(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("update " + TABLE_NAME + " set is_show = :is_show");
		sql.append(" where id = :id ");
		return executeUpdate(sql.toString(),params);
	}
	
	public int updateUrl(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("update " + TABLE_NAME + " set url = :url");
		sql.append(" where id = :id ");
		return executeUpdate(sql.toString(),params);
	}
}
