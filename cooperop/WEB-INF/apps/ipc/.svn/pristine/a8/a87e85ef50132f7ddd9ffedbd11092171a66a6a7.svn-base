package cn.crtech.precheck.ipc.audit_def;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.ipc.service.Sfzl_tpnService;

public class MemoryCache {
	//tpn规则
	private static Map<String, List<Record>> TPN_RULES = new ConcurrentHashMap<String, List<Record>>();
	//药品属性
	private static Map<String, Object> DRUGMX = new ConcurrentHashMap<String, Object>();
	//TPN计算公式映射
	private static Map<String, String> TPN_FORMUAL_MAPPING = new ConcurrentHashMap<String, String>();
	
	public static void init() {
		Sfzl_tpnService tpn_service = new Sfzl_tpnService();
		try {
			List<Record> loadTpn = tpn_service.query(null).getResultset();
			if(loadTpn.size() > 0) {
				loadTpn(loadTpn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			List<Record> loadTpnDrugMx = tpn_service.queryDrugMx(null).getResultset();
			if(loadTpnDrugMx.size() > 0 ) {
				loadTpnDrugMx(loadTpnDrugMx);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Map<String, String> loadFormul = tpn_service.getformulMapping(null);
			TPN_FORMUAL_MAPPING.clear();
			if(!CommonFun.isNe(TPN_FORMUAL_MAPPING)) {
				TPN_FORMUAL_MAPPING.putAll(loadFormul);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug(TPN_RULES.toString());
		log.debug(DRUGMX.toString());
		log.debug(TPN_FORMUAL_MAPPING.toString());
	}
	
	public static Map<String, List<Record>> getTPN_RULES() {
		return TPN_RULES;
	}
	public static void setTPN_RULES(Map<String, List<Record>> tPN_RULES) {
		TPN_RULES = tPN_RULES;
	}
	public static Map<String, Object> getDRUGMX() {
		return DRUGMX;
	}
	public static void setDRUGMX(Map<String, Object> dRUGMX) {
		DRUGMX = dRUGMX;
	}
	public static Map<String, String> getTPN_FORMUAL_MAPPING() {
		return TPN_FORMUAL_MAPPING;
	}
	public static void setTPN_FORMUAL_MAPPING(Map<String, String> tPN_FORMUAL_MAPPING) {
		TPN_FORMUAL_MAPPING = tPN_FORMUAL_MAPPING;
	}
	
	public static void DRUGMX_removeAll() {
		DRUGMX.clear();
	}
	
	public static void TPN_RULES_removeAll() {
		TPN_RULES.clear();
	}
	
	public static void TPN_FORMUAL_MAPPING_removeAll() {
		TPN_FORMUAL_MAPPING.clear();
	}
	
	public static void loadTpn(List<Record> source) {
		TPN_RULES.clear();
		HashMap<String,List<Record>> map = new HashMap<String,List<Record>>();
		for(int i=0;i<source.size();i++) {
			Record r = source.get(i);
			String tpnzbid = r.getString("shengfang_tpnzbid");
			if(CommonFun.isNe(map.get(tpnzbid))) {
				//如果为空表示为新的规则组
				//则需要新建一个集合保存当前规则
				ArrayList<Record> list = new ArrayList<Record>();
				list.add(r);
				map.put(tpnzbid, list);
			}else {
				//如果不为空表示已经有规则组了
				//只需把这个规则放到器对应的规则组中
				map.get(tpnzbid).add(r);
			}
		}
		TPN_RULES.putAll(map);
		for (String key : map.keySet()) {
			List<Record> list2 =  map.get(key);
			log.debug("================================药品：" + key + "自定义审查规则，有" + map.size() + " 组规则xx");
			for(Record r : list2) {
				log.debug("名字："+r.getString("tpnzb_name"));
			}
		}
	}
	
	public static void loadTpnDrugMx(List<Record> mx) {
		DRUGMX.clear();
		for(Record r : mx) {
			String drug_code = r.getString("drug_code");
			String xiangm = r.getString("xiangm");
			if(!CommonFun.isNe(r.get("value"))) {
				DRUGMX.put(drug_code+"-"+xiangm, r.get("value"));
			}
		}
	}
	
}
