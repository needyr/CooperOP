package cn.crtech.cooperop.hospital_common.dao;

import java.util.Date;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;


public class QscheduleDao extends BaseDao {
	/** 产品定时器任务维护表*/
	private static final String TABLE_NAME = "sys_common_schedule";
	/** 时间表达式字典表 */
	private static final String TIMEEXPRESSION = "sys_time_expression";
	/** 产品定时器任务数据表*/
	private static final String SCHEDULE = "schedule";
	/**产品表*/
	private static final String SYSTEM_PRODUCT = "system_product";
	
	//初始化数据
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*, b.p_key as sys_time_expression_p_key , b.name as sys_time_expression_name , b.code , b.remark as sys_time_expression_remark , c.* from " + TABLE_NAME + " a (nolock) ");
		sql.append(" left join " + TIMEEXPRESSION + " b (nolock)  on a.time_p_key=b.p_key ");
		sql.append(" left join " + SCHEDULE + " c (nolock) on a.schedule_id=c.instance ");
		sql.append(" where 1=1 ");
		sql.append(" and a.product_code = :product_code ");
		if(!CommonFun.isNe(params.get("filter"))) {
			params.put("filter", "%" +params.get("filter")+ "%");
			sql.append(" and c.name like :filter ");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result queryProduct(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + SYSTEM_PRODUCT + "(nolock) ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	
	//手动执行一次---查询单个数据
	public Record getSchedule(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME + "(nolock) ");
		sql.append(" left join " + SCHEDULE + " (nolock) on schedule_id = instance ");
		sql.append(" where 1=1 ");
		sql.append(" and  p_key = :p_key ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	//更新上次手动执行时间
	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("p_key", params.remove("p_key"));
		if (CommonFun.isNe(params.get("time_p_key"))) {
			String formatDate = CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
			params.put("last_use_time", formatDate);
		}
		return executeUpdate(TABLE_NAME, params,r);
	}
	
	//修改IADSCP.dbo.schedule表的定时器
	public int updateByCode(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("instance", params.remove("instance"));
		return executeUpdate(SCHEDULE, params,r);
	}


	//修改定时任务状态
	public int updateState(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("instance", params.remove("instance"));
		return executeUpdate(SCHEDULE, params,r);
	}
	
	//IADSCP.dbo.schedule验重classz
	public Record unique(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + SCHEDULE + " a (nolock) ");
		sql.append(" where  classz = :classz or instance = :scheduleinstance ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	//IADSCP.dbo.schedule添加一条数据
	public int insertSchedule(Map<String, Object> params) throws Exception {

		return executeInsert(SCHEDULE, params);
	}
	
	//sys_common_schedule添加一条数据
	public int insert(Map<String, Object> params) throws Exception {

		return executeInsert(TABLE_NAME, params);
	}
	
	//修改回显查询单条数据
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*, b.p_key as sys_time_expression_p_key , b.name as sys_time_expression_name , b.code , b.remark as sys_time_expression_remark , c.* from " + TABLE_NAME + " a (nolock) ");
		sql.append(" left join " + TIMEEXPRESSION + " b (nolock) on a.time_p_key=b.p_key ");
		sql.append(" left join " + SCHEDULE + " c (nolock) on a.schedule_id=c.instance ");
		sql.append(" where  a.p_key = :p_key ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
}
