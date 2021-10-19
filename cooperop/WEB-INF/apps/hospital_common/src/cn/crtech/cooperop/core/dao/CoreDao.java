package cn.crtech.cooperop.core.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

/**
 * 核心接口配置数据处理DAO
 * @author chenjunhong
 * 2021年1月25日
 */
public class CoreDao extends BaseDao {
	
	/**
	 * 加载执行服务接口
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Result loadExecuteService(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select s.*,i.classz ");
		sql.append("from core_service s ");
		sql.append("left join core_interface i on s.run_interface=i.code and i.type=1 and i.state=1 ");
		sql.append("where s.state=1 ");
		setParameter(params, "position", "and s.position=:position ", sql);
		sql.append("order by s.sort asc ");
		return executeQuery(sql.toString(), params);
	}
	
	/**
	 * 加载执行任务接口
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Result loadExecuteTask(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select s.*,i.classz ");
		sql.append("from core_service_config s ");
		sql.append("left join core_interface i on s.run_interface=i.code and i.type=2 and i.state=1 ");
		sql.append("where s.state=1 ");
		setParameter(params, "service_id", "and s.service_id=:service_id ", sql);
		sql.append("order by s.sort asc ");
		return executeQuery(sql.toString(), params);
	}
	
	/**
	 *	查询医生
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Record getDoctor(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select d.*,'' as duty, ");
		sql.append("p.dept_NAME as departName ,d.USER_DEPT as departCode from dict_his_users(nolock) d ");
		sql.append("left join dict_his_deptment(nolock) p on p.dept_CODE=d.USER_DEPT ");
		sql.append(" where 1=1 ");
		setParameter(params, "doctor_no", " and d.user_id=:doctor_no", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	/**
	 * 查询在用的医嘱和处方信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Result queryUseOrders(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.* ");
		sql.append("from V_his_in_orders p (nolock) ");
		sql.append(" where 1=1 ");
		setParameter(params, "patient_id", " and p.PATIENT_ID=:patient_id", sql);
		setParameter(params, "visit_id", " and p.visit_id=:visit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	
	public void updateUser(Map<String, Object> params) throws Exception {
		executeUpdate("system_doctor", params);
	}
	public void insertUser(Map<String, Object> params) throws Exception {
		executeInsert("system_doctor", params);
	}
	
}
