package cn.crtech.cooperop.hospital_common.dao.verify;

import java.util.ArrayList;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class VerifyTableDao extends BaseDao{
	
	public long tableTotal(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(*) as total FROM "+params.get("table_name")+"(nolock) ");
		Record record = executeQuerySingleRecord(sql.toString(), params);
		return Integer.parseInt((String) record.get("total"));
	}
	
	public Result queryLimit(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		String[] fields = (String[]) params.get("fields");
		for (int i = 0; i < fields.length; i++) {
			if (i == 0) {
				sql.append(fields[i].toLowerCase());
			}else {
				sql.append(","+fields[i].toLowerCase());
			}
		}
		sql.append(" FROM "+params.get("table_name")+"(nolock)    ");
		sql.append("WHERE 1=1                            ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record repetitiveness(Map<String, Object> params) throws Exception {
		ArrayList<String> fields = new ArrayList<String>();
		if (!CommonFun.isNe(params.get("fields"))) {
			fields = (ArrayList<String>) params.get("fields");
		}else {
			return new Record();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) as count from (SELECT 1 FROM  ");
		sql.append("(SELECT                                        ");
		for (int i = 0; i < fields.size(); i++) {
			if (i == 0) {
				sql.append(" cast("+fields.get(i)+" as varchar) ");
			}else {
				sql.append(" +cast("+fields.get(i)+" as varchar) ");
			}
		}
		sql.append("as name FROM "+params.get("table_name")+"(nolock)) a  ");
		sql.append("GROUP BY a.name                                ");
		sql.append("HAVING count(a.name) > 1 ) a                      ");
		return executeQuerySingleRecord(sql.toString(),params);
	}
	
	public Record incompleteResults(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) as count                          ");
		sql.append("from "+params.get("table_name")+"(nolock)               ");
		sql.append("where                                                   ");
		sql.append("isnull("+params.get("field")+",'')=''                  ");
		return executeQuerySingleRecord(sql.toString(),params);
	}
	
	public Record uniteNullValue(Map<String, Object> params) throws Exception {
		ArrayList<String> fields = (ArrayList<String>)params.get("fields");
		ArrayList<String> childfields = (ArrayList<String>)params.get("childfields");
		int size = (int)params.get("size");
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(1) as count from                      ");
		sql.append(("ipc".equals(params.get("product"))?"hospital_autopa":params.get("product"))+".."+params.get("table_name")+"(nolock) a where not exists(  ");
		sql.append(" select 1 from "+("ipc".equals(params.get("cproduct"))?"hospital_autopa":params.get("cproduct"))+".."+params.get("ctable_name")+"(nolock) b where ");
		sql.append("1=1                                ");
		for (int i = 0; i < size; i++) {
			sql.append(" and cast(a."+fields.get(i)+" as varchar)=cast(b."+childfields.get(i)+" as varchar)");
		}
		sql.append(" )");
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public Record uniteNullValue_new(Map<String, Object> params) throws Exception {
		String[] fields = (String[])params.get("fields");
		String[] cfields = (String[])params.get("cfields");
		int size = fields.length;
		StringBuffer sql = new StringBuffer();
		sql.append(" select 1 from ");
		sql.append(("ipc".equals(params.get("cproduct"))?"hospital_autopa":params.get("cproduct"))+".."+params.get("ctable_name")+"(nolock) b ");
		sql.append("where 1=1");
		for (int i = 0; i < size; i++) {
			sql.append(" and cast(b."+cfields[i]+" as varchar)='"+params.get(fields[i])+"'");
		}
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public long uniteNullValueTotal(Map<String, Object> params) throws Exception {
		ArrayList<String> fields = (ArrayList<String>)params.get("fields");
		ArrayList<String> childfields = (ArrayList<String>)params.get("childfields");
		int size = (int)params.get("size");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) as total from           ");
		sql.append("(SELECT a."+fields.get(0)+" from                      ");
		sql.append(("ipc".equals(params.get("product"))?"hospital_autopa":params.get("product"))+".."+params.get("table_name")+"(nolock) a LEFT JOIN                 ");
		sql.append(("ipc".equals(params.get("cproduct"))?"hospital_autopa":params.get("cproduct"))+".."+params.get("ctable_name")+"(nolock) b on                        ");
		sql.append("1=1                                ");
		for (int i = 0; i < size; i++) {
			sql.append(" and cast(a."+fields.get(i)+" as varchar)=cast(b."+childfields.get(i)+" as varchar)");
		}
		sql.append("   ) c");
		Record record = executeQuerySingleRecord(sql.toString(), params);
		return Integer.parseInt((String) record.get("total"));
	}
	
	public void empty(Map<String, Object> params) throws Exception {
		execute("DELETE flow_exec_log WHERE info!='empty log record';", null);
		execute("TRUNCATE TABLE flow_msg_error;", null);
		execute("TRUNCATE TABLE verify_abnormal;", null);
		execute("TRUNCATE TABLE verify_log;", null);
	}
}
