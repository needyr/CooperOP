package cn.crtech.cooperop.ipc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class PharmacistCommentDao extends BaseDao{
	
	public Result queryByAuditId(Map<String, Object> params) throws Exception {
		Record r = new Record();
		StringBuffer sql = new StringBuffer();
		r.put("auto_audit_id", params.get("auto_audit_id"));
		sql.append("select a.*,b.comment_name from pharmacist_comment(nolock) a ");
		sql.append(" left join dict_sys_comment b (nolock) on a.comment_id = b.system_code ");
		sql.append(" where a.auto_audit_id= :auto_audit_id ");
		return executeQuery(sql.toString(), r);
	}
}
