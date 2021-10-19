package cn.crtech.cooperop.bus.workflow.core.dao;

import java.util.HashMap;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class WorkFlowDao extends BaseDao {
	private final static String TABLE_NAME = "system_product_process";
	private final static String TABLE_NAME_NODE = "system_product_process_node";
	private final static String TABLE_NAME_ROUTE = "system_product_process_route";
	
	/**
	 * 获得审核人 .
	 */
	public Result getActorList(String system_department_id, String system_role_id) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct system_user_id ");
		sql.append("from system_rule(nolock) ");
		sql.append("where system_role_id=:system_role_id ");
		if (!CommonFun.isNe(system_department_id)) {
			sql.append("and system_department_id=:system_department_id ");
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("system_department_id", system_department_id);
		map.put("system_role_id", system_role_id);
		return executeQuery(sql.toString(), map);
	}
	public Result getActorListByPost(String system_post_id) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct system_user_id ");
		sql.append("from system_user_extend(nolock) ");
		sql.append("where post=:system_post_id ");
		HashMap<String, Object> map = new HashMap<>();
		map.put("system_post_id", system_post_id);
		return executeQuery(sql.toString(), map);
	}
	public Record get(String system_product_code, String id) throws Exception {
		Record conditions = new Record();
		conditions.put("system_product_code", system_product_code);
		conditions.put("id", id);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append("(nolock) where system_product_code = :system_product_code and id = :id ");
		return executeQuerySingleRecord(sql.toString(), conditions);
	}	

	public Record getByWFProcessId(String wf_process_id) throws Exception {
		Record conditions = new Record();
		conditions.put("wf_process_id", wf_process_id);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append("(nolock) where wf_process_id = :wf_process_id");
		return executeQuerySingleRecord(sql.toString(), conditions);
	}	

	public Record getByInstanceBill(String system_product_code, String instance_bill) throws Exception {
		Record conditions = new Record();
		conditions.put("system_product_code", system_product_code);
		conditions.put("instance_bill", instance_bill);
		StringBuffer sql = new StringBuffer();
		sql.append("select *, getDate() now from " + TABLE_NAME);
		sql.append("(nolock) where system_product_code = :system_product_code and instance_bill = :instance_bill ");
		return executeQuerySingleRecord(sql.toString(), conditions);
	}	

	public int update(String system_product_code, String id, String wf_process_id) throws Exception {
		Record conditions = new Record();
		conditions.put("system_product_code", system_product_code);
		conditions.put("id", id);
		Record params = new Record();
		params.put("wf_process_id", wf_process_id);
		params.put("state", 1);
		return executeUpdate(TABLE_NAME, params, conditions);
	}	

	public Record getNode(String system_product_code, String system_product_process_id, String id) throws Exception {
		Record conditions = new Record();
		conditions.put("system_product_code", system_product_code);
		conditions.put("system_product_process_id", system_product_process_id);
		conditions.put("id", id);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME_NODE);
		sql.append("(nolock) where system_product_code = :system_product_code and system_product_process_id = :system_product_process_id and id = :id ");
		return executeQuerySingleRecord(sql.toString(), conditions);
	}	

	public Record getRoute(String system_product_code, String system_product_process_id, String fromid, String toid) throws Exception {
		Record conditions = new Record();
		conditions.put("system_product_code", system_product_code);
		conditions.put("system_product_process_id", system_product_process_id);
		conditions.put("fromnode", fromid);
		conditions.put("tonode", toid);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME_ROUTE);
		sql.append("(nolock) where system_product_code = :system_product_code and system_product_process_id = :system_product_process_id and fromnode = :fromnode and tonode = :tonode ");
		return executeQuerySingleRecord(sql.toString(), conditions);
	}
	public Record getTask(String task_id) throws Exception {
		Record conditions = new Record();
		conditions.put("id", task_id);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from wf_task");
		sql.append("(nolock) where id=:id");
		return executeQuerySingleRecord(sql.toString(), conditions);
	}
	public Record getByTaskId(String task_id) throws Exception {
		Record conditions = new Record();
		conditions.put("task_id", task_id);
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM system_product_process(nolock) WHERE wf_process_id ");
		sql.append(" = (SELECT TOP 1 process_id FROM wf_order(nolock) WHERE id =          ");
		sql.append(" (SELECT TOP 1 order_id FROM wf_task(nolock) WHERE id=:task_id))      ");
		return executeQuerySingleRecord(sql.toString(), conditions);
	}
	public Record getNode(String task_id, String nodeid) throws Exception {
		Record conditions = new Record();
		conditions.put("task_id", task_id);
		conditions.put("nodeid", nodeid);
		StringBuffer sql = new StringBuffer();
		sql.append(" select sppn.* from wf_task(nolock) wf                                                                              ");
		sql.append(" left join wf_order(nolock) wo on wo.id=wf.order_id                                                                 ");
		sql.append(" left join wf_process wp(nolock) on wp.id =wo.process_id                                                            ");
		sql.append(" left join system_product_process(nolock) spp on spp.wf_process_id = wp.id                                          ");
		sql.append(" left join system_product_process_node(nolock) sppn  on sppn.system_product_process_id=spp.id and sppn.id=:nodeid   ");
		sql.append(" where wf.id =:task_id                                                                                      ");
		return executeQuerySingleRecord(sql.toString(), conditions);
	}
	public Record checkCountersign(String order_id, String system_product_process_id, String nodeid, String task_id) throws Exception {
		Record conditions = new Record();
		conditions.put("order_id", order_id);
		conditions.put("system_product_process_id", system_product_process_id);
		conditions.put("nodeid", nodeid);
		conditions.put("task_id", task_id);
		StringBuffer sql = new StringBuffer();
		sql.append(" select isnull(finish_num, 0) as s_num,                         ");
		sql.append(" (select count(1)+"+(CommonFun.isNe(task_id)?0:1)+" from wf_task(nolock) where order_id=:order_id) as w_num,                         ");
		sql.append(" (select count(1) num from wf_hist_task(nolock) t                       ");
		sql.append("  where order_id =:order_id            ");
		setParameter(conditions, "task_id", " and t.id != :task_id", sql);
		sql.append(" 	and ( SELECT                                                ");
		sql.append(" 			stringvalue                                         ");
		sql.append(" 			FROM                                                ");
		sql.append(" 			dbo.parsejson(t.variable)                           ");
		sql.append(" 			WHERE                                               ");
		sql.append(" 			name = 'audited'                                    ");
		sql.append(" 			) ='N')                                             ");
		sql.append(" as n_num,                                                      ");
		sql.append(" (select count(1) num from wf_hist_task(nolock) t                       ");
		sql.append("  where order_id =:order_id            ");
		setParameter(conditions, "task_id", " and t.id != :task_id", sql);
		sql.append(" 	and ( SELECT                                                ");
		sql.append(" 			stringvalue                                         ");
		sql.append(" 			FROM                                                ");
		sql.append(" 			dbo.parsejson(t.variable)                           ");
		sql.append(" 			WHERE                                               ");
		sql.append(" 			name = 'audited'                                    ");
		sql.append(" 			) ='Y')                                             ");
		sql.append(" as y_num                                                       ");
		sql.append(" from  system_product_process_node(nolock)  sppn ");
		if(system_product_process_id == null){
			sql.append(" left join system_product_process(nolock) spp on spp.id=sppn.system_product_process_id ");
			sql.append(" left join wf_order(nolock) wo on wo.process_Id=spp.wf_process_id ");
			sql.append(" where wo.id =:order_id   ");
		}else{
			sql.append(" where sppn.system_product_process_id =:system_product_process_id    ");
		}
		sql.append(" and sppn.id=:nodeid   ");
		return executeQuerySingleRecord(sql.toString(), conditions);
	}
}
