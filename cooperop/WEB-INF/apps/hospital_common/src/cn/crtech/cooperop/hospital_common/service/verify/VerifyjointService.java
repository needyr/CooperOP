package cn.crtech.cooperop.hospital_common.service.verify;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerifyjointService extends BaseService{
	
	public void jointCheck_dataexec(Record record,String log_bh,int limit) throws Exception {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			String field = (String)record.get("field");
			String[] tpfields = field.split(","); //切割分离出需要校验的字段
			VerifyabnormalService verifyabnormalService = new VerifyabnormalService(); 
			Map<String, Object> verify_abnormal = new HashMap<String, Object>();
			VerifyTableService tableService = new VerifyTableService();
		    Result result = new VerifyitemchildService().querybypid(record);
		    ArrayList<Record> childRecords = (ArrayList<Record>) result.getResultset();
		    for (Record childRecord : childRecords) {
		    	params.clear();
		    	params.put("table_name", (String)record.get("table_name"));
		    	params.put("product", (String)record.get("product"));
		    	ArrayList<String> fields = new ArrayList<String>();
				for (int i = 0; i < tpfields.length; i++) {
					fields.add(tpfields[i]);
				}
				params.put("fields", fields);
				params.put("ctable_name", childRecord.get("table_name"));
				params.put("cproduct", childRecord.get("product"));
				String childField = (String)childRecord.get("field");
				String[] childFields = childField.split(",");
				ArrayList<String> childfields = new ArrayList<String>();
				for (int i = 0; i < childFields.length; i++) {
					childfields.add(childFields[i]);
				}
				params.put("childfields", childfields);
				params.put("size", fields.size());
				Record result1 = tableService.uniteNullValue(params);
	            if (!CommonFun.isNe(result1) && !CommonFun.isNe(result1.get("count")) && result1.getInt("count") > 0 ) {
	            	Map<String, Object> log = new HashMap<String, Object>();
					log.put("log_bh", log_bh);
					log.put("is_abnormal", "1");
					new VerifylogService().update(log);
	            	verify_abnormal.clear();
					verify_abnormal.put("log_bh", log_bh);
					verify_abnormal.put("table_name", (String)record.get("table_name"));
					verify_abnormal.put("msg", "err! msg：字段联合查询中存在空值！");
					verify_abnormal.put("verify_items_id", record.get("id"));
					verify_abnormal.put("verify_item_child_id", childRecord.get("id"));
					verifyabnormalService.insert(verify_abnormal); //不符合的结果保留
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void jointCheck(Record record,String log_bh,int limit) throws Exception {
		try {
			String field = (String)record.get("field");
			String[] tpfields = field.toLowerCase().split(","); //切割分离出需要校验的字段
			VerifyabnormalService verifyabnormalService = new VerifyabnormalService();
			Map<String, Object> verify_abnormal = new HashMap<String, Object>();
			VerifyTableService tableService = new VerifyTableService();
			Map<String, Object> map_pa = new HashMap<String, Object>();
			map_pa.put("table_name", (String)record.get("table_name"));
			map_pa.put("limit",limit);
			map_pa.put("product",record.get("product"));
			map_pa.put("fields", tpfields);
			long total = tableService.tableTotal(map_pa);
			int start = 1;
			VerifyitemchildService service = new VerifyitemchildService();
			List<Record> childRecords = service.querybypid(record).getResultset();
			while (true) {
				map_pa.put("start", start);
				List<Record> lists = tableService.queryLimit(map_pa).getResultset();
				for (int i=0;i<childRecords.size();i++) {
					Record childRecord = childRecords.get(i);
					for (Record re : lists) {
						Map<String, Object> info = new HashMap<String, Object>();
						info.putAll(re);
						if(!CommonFun.isNe(info.get("rowno"))){
							info.remove("rowno");
						}
						if(!CommonFun.isNe(info.get("rownuma"))){
							info.remove("rownuma");
						}
						re.put("fields",tpfields);
						String[] ctpfields = ((String)childRecord.get("field")).toLowerCase().split(",");
						re.put("cfields",ctpfields);
						re.put("ctable_name", childRecord.get("table_name"));
						re.put("cproduct", childRecord.get("product"));
						Record check = tableService.uniteNullValue_new(re);
						if(CommonFun.isNe(check)){
							Map<String, Object> log = new HashMap<String, Object>();
							log.put("log_bh", log_bh);
							log.put("is_abnormal", "1");
							new VerifylogService().update(log);
							verify_abnormal.clear();
							verify_abnormal.put("log_bh", log_bh);
							verify_abnormal.put("table_name", (String)record.get("table_name"));
							verify_abnormal.put("msg", "【联合校验错误】！主表数据:" + info.toString());
							verify_abnormal.put("verify_items_id", record.get("id"));
							verify_abnormal.put("verify_item_child_id", childRecord.get("id"));
							verifyabnormalService.insert(verify_abnormal); //不符合的结果保留
							childRecords.remove(i);
							break;
						}
					}
				}
				if ((start-1) >= total) {
					break;
				}else {
					start += limit;
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
