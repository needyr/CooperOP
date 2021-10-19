package cn.crtech.cooperop.ipc.dao;

import java.util.Map;
import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

/**
 * @className: OrderSendErrorDao   
 * @description: 医嘱推送药师端失败dao
 * @author: 魏圣峰 
 * @date: 2019年1月16日 上午9:09:53
 */
public class OrderSendErrorDao extends BaseDao {
	/** 医嘱推送药师端失败信息表 */
	public static final String TABLE_NAME = "system_send_error_message";
	/** 合理用药审查记录主表 */
	public static final String TABLE_AUTO_AUDIT = "auto_audit";
	/** 系统用户表 */
	public static final String TABLE_USER = "system_user_cooperop";

	/**
	 * @author: 魏圣峰
	 * @description: 查询医嘱推送失败信息
	 * @param: params Map
	 * @return: Result      
	 * @throws: Exception
	 */
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select me.*,");
		sql.append("au.state audit_result,au.visit_id,au.patient_id,au.p_type,au.d_type,au.is_after,au.common_id,");
		sql.append("us.name doctor_name,");
		sql.append("dbo.get_patient_xx(au.patient_id,au.visit_id) patient_name");
		sql.append(" from "+TABLE_NAME+" (nolock)me");
		sql.append(" left join "+TABLE_AUTO_AUDIT+" (nolock)au");
		sql.append(" on me.auto_audit_id=au.id");
		sql.append(" left join "+TABLE_USER+" (nolock)us");
		sql.append(" on us.no=me.doctor_no");
		sql.append(" where  1 = 1 ");
		setParameter(params, "state", " and me.state = :state", sql);
		setParameter(params, "start_time", " and me.create_time >= :start_time", sql);
		setParameter(params, "end_time", " and me.create_time <= :end_time", sql);
		if(!CommonFun.isNe(params.get("filter"))) {
			sql.append(" and (me.doctor_no like '%"+params.get("filter")+"%'");
			sql.append(" or us.name like '%"+params.get("filter")+"%')");
		}
		params.put("sort", "update_time desc");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}

	public int update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME, params, r);
	}

	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME, params);
	}

	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append(" (nolock) where  id = :id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}

}
