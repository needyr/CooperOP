package cn.crtech.precheck.server;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.AuditDataReadyService;
import cn.crtech.cooperop.hospital_common.service.AutoCommonService;
import cn.crtech.precheck.EngineInterface;
import cn.crtech.ylz.ylz;
import net.sf.json.JSONObject;

public class TestHYTAudit {
	
	public static void doPost(String xml) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
		Long start = System.currentTimeMillis();
		//根据判断调用servlet接口
		String common_id = "";
		/* 审查串是否为null项目 (空提交) */
		int p_keyNotNull = 1;
		Map<String, Object> resultMap=new HashMap<String, Object>();
		Map<String, Object> checkRtnMap=new HashMap<String, Object>();
		checkRtnMap.put("state", "Y");
		checkRtnMap.put("use_flag", 1);
		checkRtnMap.put("flag", 1);
		checkRtnMap.put("id", "A");
		try {
			xml = xml.replaceAll("[\\t\\n\\r]", "");
			xml = xml.replaceAll("\\\\", "/");
			xml = xml.replace("\uFEFF", "");
			Map<String, Object> p = CommonFun.json2Object(xml, Map.class);
			@SuppressWarnings("unchecked")
			Object audit_source_fk = p.remove("audit_source_fk");
			String his_req = CommonFun.object2Xml(p);
			@SuppressWarnings("unchecked")
			Map<String, Object> params = (Map<String, Object>) p.get("request");
			Object is_after = params.get("is_after");
			Map<String, Object> rows = null;
			if(!CommonFun.isNe(params.get("rows"))) {
				rows = (Map<String, Object>) params.get("rows");
				if(rows.get("row") instanceof java.util.LinkedHashMap) {
					//在青浦医院中的空提交为只有一个row 它的内容为空
					Map<String, Object> row =  (Map<String, Object>) rows.get("row");
					if(CommonFun.isNe(row.get("p_key"))) {
						p_keyNotNull = 0;
						ylz.p("判定为未开具医嘱，空提交，不审查");
					}
				}
			}else {
				if(!"1".equals(is_after)) {
					p_keyNotNull = 0;
					ylz.p("判定为未开具医嘱空提交,不审查");
				}
			}
			
			if("5".equals(params.get("d_type"))) {
				p_keyNotNull = 1;
				ylz.p("判定为：来自医保结算的提交,空提交也审查");
			}
			
			//end
			if(p_keyNotNull == 1) {
				Map<String, Object> info = CommonFun.object2Map(params.get("patient"));
				map.put("patient_id", info.get("id"));
				map.put("visit_id", info.get("visitid"));
				Map<String, Object> doctor_info = CommonFun.object2Map(params.get("doctor"));
				map.put("doctor_no", doctor_info.get("code"));
				//事后审查外键
				map.put("audit_source_fk", audit_source_fk);
				//产生公共主表审查记录
				map.put("his_req", his_req);
				map.put("ip", "test_hyt");
				map.put("d_type", params.get("d_type"));
				map.put("dept_code", info.get("departcode"));
				map.put("is_after", is_after);
				//--------------------------------------
				common_id = new AutoCommonService().insert(map);
				ylz.recordMsg(true, "mmid_" + common_id, "产生common_id, (在此之前消耗 " + (System.currentTimeMillis() - start) + " ms)");
				/*插入诊断信息*/
				/*new InsertDiagnosisService().insert(params);*/
				map.put("common_id", common_id);
				map.put("req", params);
				resultMap.put("id", common_id);
				//调用审查
				map.put("dept_code", info.get("departcode").toString());
				//2019-08-27 wang修改,增加数据准备方法
				new AuditDataReadyService().ready(map);
				ylz.recordMsg("mmid_" + common_id, "存储common患者信息，诊断信息");
				checkRtnMap =  EngineInterface.audit(map);
				map.clear();
				//此id用于demo访问结果页面传入参数 为common_id
				checkRtnMap.put("id", common_id);
				map.put("common_id", common_id);
				map.put("state", checkRtnMap.get("state"));
				map.put("end_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
				map.put("cost_time", System.currentTimeMillis() - start );
				// -- test yan
				map.put("demo_resp", CommonFun.object2Json(checkRtnMap));
				map.put("clog", ylz.removeMsg("mmid_" + common_id));
				// --
				//new AutoCommonService().update(map);
				map.put("his_req", his_req);
				new AutoCommonService().insertMx(map);
			}
		} catch (Throwable e) {
			log.error(e);
		}
	}
	
	 public static Map<String, Object> jsonToMap(String jsonStr) {
        JSONObject jsonObject = JSONObject.fromObject(jsonStr); //首先转换为 JSONObject
        Iterator<String> nameItr = jsonObject.keys();//获取jsonObject的keySet()的迭代器
        String key;//接收key值
        Map<String, Object> outMap = new HashMap<>(); //存放转换结果
        while (nameItr.hasNext()) {
            key = nameItr.next();
            outMap.put(key, jsonObject.get(key));
        }
        return outMap;
    }
}

