package cn.crtech.cooperop.hospital_common.dao.patientdata;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;

public class BillDao extends BaseDao {

	public String insert(Map<String, Object> params) throws Exception {
		String itemid = CommonFun.getITEMID();
		params.put("id", itemid);
		executeInsert("his_in_bill_detail_showlog", params);
		return itemid;
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("id", params.remove("id"));
		return executeUpdate("his_in_bill_detail_showlog", params, r);
	}
}
