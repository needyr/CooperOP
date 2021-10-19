package cn.crtech.cooperop.hospital_common.service.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.hospital_common.util.CheckUtil;

public class VerifyfieldtypeService extends BaseService{
	
	private int limit;
	
	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void verifyType(Record record,String log_bh,int limitNum) throws Exception {
		try {
			setLimit(limitNum);
			Map<String, Object> params = new HashMap<String, Object>();
			VerifyTableService tableService = new VerifyTableService();
			String field = (String)record.get("field");
			String[] tpfields = field.toLowerCase().split(","); //切割分离出需要校验的字段
			VerifyabnormalService verifyabnormalService = new VerifyabnormalService(); 
			Map<String, Object> verify_abnormal = new HashMap<String, Object>();
			params.put("table_name", (String)record.get("table_name"));
			params.put("limit",limit);
			params.put("product",record.get("product"));
			long total = tableService.tableTotal(params); //获取目标表的总数据行数
			int start = 1; //分页第一页的起始行数
			if ("int".equals((String)record.get("field_type"))) {
				while (true) {
					params.put("start", start);
					params.put("fields", tpfields);
					ArrayList<Record> lists = (ArrayList<Record>)tableService.queryLimit(params).getResultset();
					for (Record ob : lists) {
						for (int i = 0; i < tpfields.length; i++) {
							if (!CheckUtil.isInteger((String)ob.get(tpfields[i]))) {
								Map<String, Object> log = new HashMap<String, Object>();
								log.put("log_bh", log_bh);
								log.put("is_abnormal", "1");
							    new VerifylogService().update(log);
								verify_abnormal.clear();
								verify_abnormal.put("log_bh", log_bh);
								verify_abnormal.put("table_name", (String)record.get("table_name"));
								verify_abnormal.put("msg", "【字段类型校验错误】! 例如：字段"+tpfields[i]+"中的值("+(String)ob.get(tpfields[i])+")不是"+(String)record.get("field_type")+"类型");
								verify_abnormal.put("verify_items_id", record.get("id"));
								verifyabnormalService.insert(verify_abnormal); //不符合的结果保留
								return;
							}
						}
					}
					if ((start-1) >= total) {
						break;
					}else {
						start += limit;
					}
				}
			}else if ("float".equals((String)record.get("field_type"))) {
				while (true) {
					params.put("start", start);
					params.put("fields", tpfields);
					ArrayList<Record> lists = (ArrayList<Record>)tableService.queryLimit(params).getResultset();
					for (Record ob : lists) {
						for (int i = 0; i < tpfields.length; i++) {
							if (!(CheckUtil.isFloat((String)ob.get(tpfields[i])) //判断原数据是否是浮点型
									&& Integer.parseInt((String) record.get("float_num")) == CheckUtil.decimalDigits((String)ob.get(tpfields[i])))) { //判断数据是否拥有指定小数位数
								verify_abnormal.clear();
								verify_abnormal.put("log_bh", log_bh);
								verify_abnormal.put("table_name", (String)record.get("table_name"));
								verify_abnormal.put("msg", "【字段类型校验错误】! 例如：字段"+tpfields[i]+"中的值("+(String)ob.get(tpfields[i])+")不符合float类型的校验规则");
								verify_abnormal.put("verify_items_id", record.get("id"));
								verifyabnormalService.insert(verify_abnormal); //不符合的结果保留
								Map<String, Object> log = new HashMap<String, Object>();
								log.put("log_bh", log_bh);
								log.put("is_abnormal", "1");
							    new VerifylogService().update(log);
								return;
							}
						}
					}
					if ((start-1) >= total) {
						break;
					}else {
						start += limit;
					}
				}
			}else if ("datetime".equals((String)record.get("field_type"))) {
				while (true) {
					params.put("start", start);
					params.put("fields", tpfields);
					ArrayList<Record> lists = (ArrayList<Record>)tableService.queryLimit(params).getResultset();
					for (Record ob : lists) {
						for (int i = 0; i < tpfields.length; i++) {
							if (!CheckUtil.isDate((String)ob.get(tpfields[i]), (String)record.get("time_format"))) { //判断数据是否符合时间格式
								verify_abnormal.clear();
								verify_abnormal.put("log_bh", log_bh);
								verify_abnormal.put("table_name", (String)record.get("table_name"));
								verify_abnormal.put("msg", "【字段类型校验错误】! 例如：字段"+tpfields[i]+"中的值("+(String)ob.get(tpfields[i])+")不是"+(String)record.get("time_format")+"格式");
								verify_abnormal.put("verify_items_id", record.get("id"));
								verifyabnormalService.insert(verify_abnormal); //不符合的结果保留
								Map<String, Object> log = new HashMap<String, Object>();
								log.put("log_bh", log_bh);
								log.put("is_abnormal", "1");
							    new VerifylogService().update(log);
								return;
							}
						}
					}
					if ((start-1) >= total) {
						break;
					}else {
						start += limit;
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} 
	}
}
