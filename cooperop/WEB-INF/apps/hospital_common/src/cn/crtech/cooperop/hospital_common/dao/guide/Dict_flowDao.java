package cn.crtech.cooperop.hospital_common.dao.guide;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class Dict_flowDao extends BaseDao {
	
	public Result queryBase(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.* from dict_flow (nolock) a where isnull(a.parent_id,'') = '' ");
		sql.append(" and exists (select 1 from iadscp..system_product(nolock) where a.project = code and is_active = 1 )");
		sql.append("order by parent_id,[order]");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryFinishAll(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.* from dict_flow (nolock) a where a.state <> '2' ");
		sql.append(" and exists (select 1 from iadscp..system_product(nolock) where a.project = code and is_active = 1 )");
		return executeQuery(sql.toString(), params);
	}

	public Result getFinishLog(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select convert(varchar(19),a.create_time,21) as create_time,a.info,a.remark,a.flow_id from flow_exec_log (nolock) a where a.flow_id = :id ");
		sql.append(" and info = '完成流程' order by a.create_time desc ");
		return executeQuery(sql.toString(), params);
	}

	public Result querychild(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.* from dict_flow (nolock) a where a.parent_id = :parent_id ");
		sql.append(" and exists (select 1 from iadscp..system_product(nolock) where a.project = code and is_active = 1 )");
		sql.append("order by [order]");
		return executeQuery(sql.toString(), params);
	}

	public Result getByIdPermit(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.* from dict_flow (nolock) a where a.id = :id and a.state in ('1','2') ");
		sql.append(" and exists (select 1 from iadscp..system_product(nolock) where a.project = code and is_active = 1 )");
		return executeQuery(sql.toString(), params);
	}
	
	public Record getById(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.* from dict_flow (nolock) a where a.id = :id ");
		sql.append(" and exists (select 1 from iadscp..system_product(nolock) where a.project = code and is_active = 1 )");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getByIdComplete(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.* from dict_flow (nolock) a where a.id = :id ");
		sql.append("and not exists (select 1 from dict_flow (nolock) where a.[order] > [order] ");
		sql.append("and state <> '2' and isnull(parent_id,'') <> '' ");
		sql.append(" and exists (select 1 from iadscp..system_product(nolock) where project = code and is_active = 1 )");
		sql.append(" ) ");
		sql.append(" and exists (select 1 from iadscp..system_product(nolock) where a.project = code and is_active = 1 )");
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public void updateExecState(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update a set state = 1 from dict_flow a where ");
		sql.append(" exists (select 1 from iadscp..system_product(nolock) where a.project = code and is_active = 1 ) ");
		sql.append("and a.id = :id and a.state = '0' ");
		execute(sql.toString(), params);
	}

	public void updateComplete(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		sql.append("select a.* from dict_flow (nolock) a where a.id = :id ");
		sql.append("and exists (select 1 from dict_flow (nolock) where [order] > a.[order] ");
		sql.append("and (parent_id=:parent_id or id=:id)) ");
		sql.append(" and exists (select 1 from iadscp..system_product(nolock) where a.project = code and is_active = 1 )");
		Result executeQuery = executeQuery(sql.toString(),params);
		if(executeQuery.getCount() > 0) {
			sql2.append("update a set a.state = 2 from dict_flow a where a.id=:id and state not in ('0','2') ");
		}else {
			sql2.append("update a set a.state = 2 from dict_flow a where (a.id=:id or a.id=:parent_id) and state not in ('0','2') ");
		}
		execute(sql2.toString(), params);
	}
	
	public void insertLog(Map<String, Object> params) throws Exception {
		executeInsert("flow_exec_log", params);
	}

	public void insertError(Map<String, Object> params) throws Exception {
		executeInsert("flow_msg_error", params);
	}

	public void initData(Map<String, Object> params) throws Exception {
		//TODO 暂时屏蔽
		//Thread.sleep(3000);
		execute("exec hospital_common..SCR_initialize", params);
	}
	
	public void initUsers(Map<String, Object> params) throws Exception {
		execute("exec hospital_common..SCR_SYN_HIS_DEPARTMENT_USER", params);
	}
	
	public void execOneAfterAudit(Map<String, Object> params) throws Exception {
		if("JCR_after_auto_audit_discharge".equals(params.get("exec_pro"))){
			Result executeQuery2 = executeQuery("select 1 from iadscp..system_product (nolock) where code = 'ipc' and is_active = '1' ");
			if(executeQuery2.getCount() > 0) {
				execute("exec hospital_common..JCR_after_auto_audit_discharge", params);
			}
		}
		if("JCR_after_imic_audit_discharge".equals(params.get("exec_pro"))){
			Result executeQuery = executeQuery("select 1 from iadscp..system_product (nolock) where code = 'hospital_imic' and is_active = '1' ");
			if(executeQuery.getCount() > 0) {
				execute("exec hospital_common..JCR_after_imic_audit_discharge", params);
			}
		}
	}
	
	public Result queryDictPD(Map<String, Object> params) throws Exception {
		execute("exec hospital_common..update_mapnums_proc", params);
		return executeQuery("select * from hospital_common..maptables (nolock) where now_remark <> '已完成配对' ");
	}
	
	public Result queryFlowLog(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select b.name,a.create_time,a.info,a.remark from flow_exec_log(nolock) a ");
		sql.append("inner join dict_flow(nolock) b on a.flow_id=b.id where 1=1 ");
		setParameter(params, "name", "and b.name like '%'+'"+params.get("name")+"'+'%'", sql);
		return executeQueryLimit(sql.toString(),params);
	}
	
	public Result queryFlowErrorLog(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select b.name,a.create_time,a.error_title,a.error_msg from flow_msg_error(nolock) a ");
		sql.append("inner join dict_flow(nolock) b on a.flow_id=b.id where 1=1 ");
		setParameter(params, "name", "and b.name like '%'+'"+params.get("name")+"'+'%'", sql);
		return executeQueryLimit(sql.toString(),params);
	}
	
}
