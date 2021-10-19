package cn.crtech.cooperop.hospital_common.dao.print;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;

public class CommPrintDao extends BaseDao{
	
	public static final String TABLE_NAME = "common_print"; 

	public Result queryBypat(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from " +TABLE_NAME + " (nolock) ");
		sql.append(" where patient_id =:patient_id and visit_id =:visit_id and print_id = :print_id ");
		return executeQuery(sql.toString(), params);
	}
	
	public void insert(Map<String, Object> params) throws Exception {
		executeInsert(TABLE_NAME, params);
	}
}
