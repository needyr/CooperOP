package cn.crtech.cooperop.application.dao;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DataTableDao extends BaseDao {
	public final static String TABLE_NAME = "customset_datatable";
	public Record getSetting(Record req) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append(" where system_user_id = :system_user_id ");
		sql.append("   and pageid = :pageid ");
		sql.append("   and tableid = :tableid ");
/*		if (CommonFun.isNe(req.get("company_id"))) {
			sql.append("   and company_id is null ");
		} else {
			sql.append("   and company_id = :company_id ");
		}*/
		return executeQuerySingleRecord(sql.toString(), req);
	}

	public int insertSetting(Record req) throws Exception {
		return executeInsert(TABLE_NAME, req);
	}

	public int updateSetting(Record req) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update " + TABLE_NAME);
		sql.append("   set setting = :setting");
		sql.append(" where system_user_id = :system_user_id ");
		sql.append("   and pageid = :pageid ");
		sql.append("   and tableid = :tableid ");
	/*	if (CommonFun.isNe(req.get("company_id"))) {
			sql.append("   and company_id is null ");
		} else {
			sql.append("   and company_id = :company_id ");
		}*/
		return executeUpdate(sql.toString(), req);
	}

}
