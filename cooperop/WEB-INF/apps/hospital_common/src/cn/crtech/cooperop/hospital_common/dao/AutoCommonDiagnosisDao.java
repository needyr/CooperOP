package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.util.CommonFun;

public class AutoCommonDiagnosisDao extends BaseDao{
	private final static String TABLE_NAME = "auto_common_diagnosis";
	
	public void insert(Map<String, Object> params) throws Exception {
		String id = CommonFun.getITEMID();
		params.put("id", id);
		executeInsert(TABLE_NAME, params);
	}
}
