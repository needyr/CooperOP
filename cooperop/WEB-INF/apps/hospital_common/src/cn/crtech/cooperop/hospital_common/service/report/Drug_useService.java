package cn.crtech.cooperop.hospital_common.service.report;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.report.Drug_useDao;

public class Drug_useService extends BaseService {
	
	public Result query_stats(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Record query_stats = new Drug_useDao().query_stats(params);
			Result re = new Result();
			
			Record map = new Record();
			float total = CommonFun.isNe(query_stats.get("total"))?0:Float.parseFloat((String)query_stats.get("total"));
			int patient_count = CommonFun.isNe(query_stats.get("patient_count"))?0:Integer.parseInt((String)query_stats.get("patient_count"));
			float total_drug = CommonFun.isNe(query_stats.get("total_drug"))?0:Float.parseFloat((String)query_stats.get("total_drug"));
			float xi_drug = CommonFun.isNe(query_stats.get("xi_drug"))?0:Float.parseFloat((String)query_stats.get("xi_drug"));
			float zongc_drug = CommonFun.isNe(query_stats.get("zongc_drug"))?0:Float.parseFloat((String)query_stats.get("zongc_drug"));
			float zongy_drug = CommonFun.isNe(query_stats.get("zongy_drug"))?0:Float.parseFloat((String)query_stats.get("zongy_drug"));
			float swzj_drug = CommonFun.isNe(query_stats.get("swzj_drug"))?0:Float.parseFloat((String)query_stats.get("swzj_drug"));
			float kj_drug = CommonFun.isNe(query_stats.get("kj_drug"))?0:Float.parseFloat((String)query_stats.get("kj_drug"));
			map.put("total", "总费用(元)");
			map.put("result", total);
			map.put("avg", "总病人数");
			map.put("result2", patient_count);
			re.addRecord(map);
			map = new Record();
			map.put("total", "药品收入(元)");
			map.put("result", total_drug);
			map.put("avg", "人均费用(元)");
			map.put("result2", (patient_count == 0?0:total/patient_count));
			re.addRecord(map);
			map = new Record();
			map.put("total", "药品收入所占比例(%)");
			map.put("result", (total == 0?0:total_drug/total*100));
			map.put("avg", "人均药品费用(元)");
			map.put("result2", (patient_count == 0?0:total_drug/patient_count));
			re.addRecord(map);
			map = new Record();
			map.put("total", "西药收入(元)");
			map.put("result", xi_drug);
			map.put("avg", "人均使用西药费用(元)");
			map.put("result2", (patient_count == 0?0:xi_drug/patient_count));
			re.addRecord(map);
			map = new Record();
			map.put("total", "西药收入占药品收入比例(%)");
			map.put("result", (total == 0?0:xi_drug/total_drug*100));
			map.put("avg", "人均使用中成药费用(元)");
			map.put("result2", (patient_count == 0?0:zongc_drug/patient_count));
			re.addRecord(map);
			map = new Record();
			map.put("total", "中成药(元)");
			map.put("result", zongc_drug);
			map.put("avg", "人均使用中药饮片费用(元)");
			map.put("result2", (patient_count == 0?0:zongy_drug/patient_count));
			re.addRecord(map);
			map = new Record();
			map.put("total", "中成药收入占药品收入比例(%)");
			map.put("result", (total == 0?0:zongc_drug/total_drug*100));
			map.put("avg", "人均使用生物制剂费用(元)");
			map.put("result2", (patient_count == 0?0:swzj_drug/patient_count));
			re.addRecord(map);
			map = new Record();
			map.put("total", "中药饮片(元)");
			map.put("result", zongy_drug);
			map.put("avg", "");
			map.put("result2", "");
			re.addRecord(map);
			map = new Record();
			map.put("total", "中药饮片收入占药品收入比例(%)");
			map.put("result", (total == 0?0:zongy_drug/total_drug*100));
			map.put("avg", "");
			map.put("result2", "");
			re.addRecord(map);
			map = new Record();
			map.put("total", "生物制剂收入(元)");
			map.put("result", swzj_drug);
			map.put("avg", "");
			map.put("result2", "");
			re.addRecord(map);
			map = new Record();
			map.put("total", "生物制剂收入占药品收入比例(%)");
			map.put("result", (total == 0?0:swzj_drug/total_drug*100));
			map.put("avg", "");
			map.put("result2", "");
			re.addRecord(map);
			map = new Record();
			map.put("total", "抗菌药物收入(元)");
			map.put("result", kj_drug);
			map.put("avg", "");
			map.put("result2", "");
			re.addRecord(map);
			map = new Record();
			map.put("total", "抗菌药物收入占药品收入比例(%)");
			map.put("result", (total == 0?0:kj_drug/total_drug*100));
			map.put("avg", "");
			map.put("result2", "");
			re.addRecord(map);
			return re;
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}
	
	public Result query_dept(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new Drug_useDao().query_dept(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}
	
	public Result query_doctor(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new Drug_useDao().query_doctor(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}
}
