package cn.crtech.cooperop.hospital_common.service.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;

public class VerifywholenessService extends BaseService{
	public void wholeness(Record record,String log_bh) throws Exception {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			String field = (String)record.get("field");
			String[] tpfields = field.toLowerCase().split(","); //切割分离出需要校验的字段
			VerifyabnormalService verifyabnormalService = new VerifyabnormalService(); 
			Map<String, Object> verify_abnormal = new HashMap<String, Object>();
			params.put("table_name", (String)record.get("table_name"));
			params.put("product",record.get("product"));
			VerifyTableService tableService = new VerifyTableService();
			for (int i = 0; i < tpfields.length; i++) {
				params.put("field", tpfields[i]);
				Record records = tableService.incompleteResults(params);
				if (!CommonFun.isNe(records) && !CommonFun.isNe(records.get("count")) && records.getInt("count") > 0 ) {
					verify_abnormal.clear();
				    verify_abnormal.put("log_bh", log_bh);
				    verify_abnormal.put("table_name", (String)record.get("table_name"));
				    verify_abnormal.put("msg", "【完整性校验错误】! 例如：字段"+tpfields[i]+"存在空数据");
				    verify_abnormal.put("verify_items_id", record.get("id"));
				    verifyabnormalService.insert(verify_abnormal); //不符合的结果保留
				    Map<String, Object> log = new HashMap<String, Object>();
					log.put("log_bh", log_bh);
					log.put("is_abnormal", "1");
				    new VerifylogService().update(log);
				    return;
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
