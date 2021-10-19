package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class CheckLevelDao_del extends BaseDao{
	private final static String SYS_CHECKLEVEL = "DICT_SYS_CHECKLEVEL";
	private final static String CHECKLEVEL_DUIY = "DICT_CHECKLEVEL_DUIY";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select a.*,b.check_level,b.check_level_name,b.source,c.name from "+SYS_CHECKLEVEL+" (nolock) a inner join "+CHECKLEVEL_DUIY+" (nolock) b ");
		sql.append("on a.sys_check_level = b.sys_check_level inner join system_product (nolock) c on c.code=a.system_product_code");
		Result result = executeQueryLimit(sql.toString(),params);
		return result;
	}

	public int insertSys(Map<String, Object> params) throws Exception {
		String s=(String)params.get("sys_check_level");
		StringBuffer star=new StringBuffer();
		for (int i = 0; i < Integer.parseInt(s); i++) {
			star.append("★");
		}
		params.put("star_level", star.toString());
		int i = executeInsert(SYS_CHECKLEVEL, params);
		return i;
	}

	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(CHECKLEVEL_DUIY, params);
	}

	public int updateSys(Map<String, Object> params) throws Exception {
		String s=(String)params.get("sys_check_level");
		StringBuffer star=new StringBuffer();
		for (int i = 0; i < Integer.parseInt(s); i++) {
			star.append("★");
		}
		params.put("star_level", star.toString());
		Record r=new Record();
		r.put("sys_check_level", params.remove("old_sys_check_level"));
		return executeUpdate(SYS_CHECKLEVEL, params, r);
	}

	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("check_level", params.remove("old_check_level"));
		return executeUpdate(CHECKLEVEL_DUIY, params, r);
	}

	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT a.*,b.check_level,b.check_level_name,b.source,c.name as pro_name FROM "+SYS_CHECKLEVEL+" (nolock) a INNER JOIN "+CHECKLEVEL_DUIY+" (nolock) b ");
		sql.append("on a.SYS_Check_level = b.SYS_Check_level left join system_product (nolock) c on c.code=a.system_product_code where 1=1 ");
		setParameter(params, "sys_check_level", "and a.sys_check_level = :sys_check_level", sql);
		Record record = executeQuerySingleRecord(sql.toString(), params);
		return record;
	}

	public Record isExist(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT a.*,b.check_level,b.check_level_name,b.source,c.name as pro_name FROM "+SYS_CHECKLEVEL+" (nolock) a INNER JOIN "+CHECKLEVEL_DUIY+" (nolock) b ");
		sql.append("on a.SYS_Check_level = b.SYS_Check_level left join system_product (nolock) c on c.code=a.system_product_code where 1=1 ");
		setParameter(params, "sys_check_level", "and a.sys_check_level = :sys_check_level", sql);
		setParameter(params, "system_product_code", "and a.system_product_code = :system_product_code", sql);
		Record record = executeQuerySingleRecord(sql.toString(), params);
		return record;
	}

	public int deleteSys(Map<String, Object> sysMap) throws Exception {
		return executeDelete(SYS_CHECKLEVEL, sysMap);
	}

	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(CHECKLEVEL_DUIY, params);
	}
	
	public Result queryAuto(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT * FROM "+SYS_CHECKLEVEL+" (nolock) ");
		Result result = executeQueryLimit(sql.toString(),params);
		return result;
	}
}
