package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class VersionControlClientDao extends BaseDao{
	private final static String TABLE_NAME = "version_control_client";
	private final static String TABLE_NAME_MX = "version_control_client_mx";

	public Result query(Map<String, Object> params) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from "+TABLE_NAME+" (nolock)");
		return executeQuery(sql.toString(), params);
	}
	
	public void insert(Map<String, Object> params) throws Exception{
		executeInsert(TABLE_NAME, params);
	}

	public void updateByIp(Map<String, Object> params) throws Exception{
		Record record = new Record();
		record.put("ip_address", params.remove("ip_address"));
		executeUpdate(TABLE_NAME, params, record);
	}
	
	public Result query_mx(Map<String, Object> params) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from "+TABLE_NAME_MX+" (nolock)");
		return executeQuery(sql.toString(), params);
	}
	
	public void insert_mx(Map<String, Object> params) throws Exception{
		executeInsert(TABLE_NAME_MX, params);
	}

	public void updateByPkey_mx(Map<String, Object> params) throws Exception{
		Record record = new Record();
		record.put("p_key", params.remove("p_key"));
		executeUpdate(TABLE_NAME_MX, params, record);
	}

	public Result queryVersionAll(Map<String, Object> params) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select *,                                           ");
		sql.append("(select max(state) from "+TABLE_NAME+" b(nolock)    "); 
		sql.append("where a.ip_address=b.ip_address) state              ");
		sql.append("from "+TABLE_NAME_MX+" a (nolock)   where 1=1       ");
		setParameter(params, "version_no", " and a.version_no = :version_no ", sql);
		if(!CommonFun.isNe(params.get("d_type_query"))) {
			if(params.get("d_type_query") instanceof String) {
				sql.append(" and a.d_type = :d_type_query ");
			}else{
				sql.append(" and a.d_type in (:d_type_query) ");
			}
		}
		return executeQuery(sql.toString(), params);
	}

	public void capture(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into version_control_client_mx                               ");
		sql.append(" (ip_address,version,d_type,dept_code,dept_NAME,p_key,update_time)   ");
		sql.append(" select a.ip,a.im_version,a.d_type,                                  ");
		sql.append(" max(a.dept_code) dept_code,                                         ");
		sql.append(" (select b.dept_NAME                                                 ");
		sql.append(" from dict_his_deptment b (nolock)                                   ");
		sql.append(" where max(a.dept_code) = b.dept_CODE) dept_NAME,                    ");
		sql.append(" a.ip+'|'+a.d_type as p_key,                                         ");
		sql.append(" CONVERT(varchar,GETDATE(),120) as update_time                       ");
		sql.append(" from hospital_common..v_auto_common_use a (nolock)                                         ");
		sql.append(" where not exists (select 1 from version_control_client_mx c(nolock) ");
		sql.append(" where c.ip_address=a.ip and c.d_type=a.d_type)                      ");
		sql.append(" and isnull(a.d_type, '')<>''                                        ");
		sql.append(" group by a.ip,a.im_version,a.d_type                                 ");
		execute(sql.toString(), params);
		StringBuffer sql2 = new StringBuffer();
		sql2.append(" update b set                                   ");
		sql2.append(" b.version = (select                            ");
		sql2.append(" case when isnull(c.im_version,'')=''           ");
		sql2.append(" then b.version                                 ");
		sql2.append(" else c.im_version end from                     ");
		sql2.append(" (select max(a.im_version) im_version           ");
		sql2.append(" from hospital_common..v_auto_common_use a (nolock)                    ");
		sql2.append(" where b.ip_address=a.ip                        ");
		sql2.append(" and b.d_type=a.d_type                          ");
		sql2.append(" and isnull(a.d_type, '')<>''                   ");
		sql2.append(" group by a.im_version) c                       ");
		sql2.append(" where c.im_version<>b.version),                ");
		sql2.append(" b.update_time = CONVERT(varchar,GETDATE(),120) ");
		sql2.append(" from version_control_client_mx b(nolock)       ");
		sql2.append(" where exists (select                           ");
		sql2.append(" case when isnull(c.im_version,'')=''           ");
		sql2.append(" then b.version                                 ");
		sql2.append(" else c.im_version end from                     ");
		sql2.append(" (select max(a.im_version) im_version           ");
		sql2.append(" from hospital_common..v_auto_common_use a (nolock)                    ");
		sql2.append(" where b.ip_address=a.ip                        ");
		sql2.append(" and b.d_type=a.d_type                          ");
		sql2.append(" and isnull(a.d_type, '')<>''                   ");
		sql2.append(" group by a.im_version) c                       ");
		sql2.append(" where c.im_version<>b.version)                 ");
		execute(sql2.toString(), params);
	}
}
