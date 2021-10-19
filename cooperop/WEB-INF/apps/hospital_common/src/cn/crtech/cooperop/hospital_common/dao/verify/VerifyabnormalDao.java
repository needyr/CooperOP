package cn.crtech.cooperop.hospital_common.dao.verify;

import java.util.Date;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;

public class VerifyabnormalDao extends BaseDao{
	public void insert(Map<String, Object> params) throws Exception {
		executeInsert("verify_abnormal", params);
	}
	
	public void update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		if("1".equals(params.get("is_deal"))) {
			params.put("deal_time", CommonFun.formatDate(new Date(), "YYYY-MM-dd HH:mm:ss"));
		}
		executeUpdate("verify_abnormal", params, r);
	}
}
