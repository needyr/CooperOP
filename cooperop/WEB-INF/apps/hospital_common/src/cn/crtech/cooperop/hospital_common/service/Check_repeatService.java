package cn.crtech.cooperop.hospital_common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Connection;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.Check_repeatDao;
import cn.crtech.cooperop.hospital_common.util.Util;

public class Check_repeatService extends BaseService {
	
	//检查是否已经提交给药师审查
    public Map<String, Object> hadSubmit(Map<String, Object> params) throws Exception {
    	try {
    		Map<String, Object> map = new HashMap<String, Object>();
    		Record re = new Record();
    		re.put("flag","0");
    		
    		connect("ipc");
    		if("Y".equals(SystemConfig.getSystemConfigValue("ipc", "check_repeat_submit", "Y"))) {
        		Check_repeatDao autoDao = new Check_repeatDao();
        		Record repetitionAudit = autoDao.getRepetitionAudit(params);
        		if (!CommonFun.isNe(repetitionAudit)) {
        			Result old_orders = autoDao.queryAutoAuditOrders_old(repetitionAudit);
            		if(!CommonFun.isNe(old_orders) && old_orders.getCount() > 0) {
            			Result new_orders = autoDao.queryAutoAuditOrders(params);
            			if (new_orders.getCount() == old_orders.getCount()) {
            				List<Record> order_list_old = old_orders.getResultset();
            				List<Record> order_list_new = new_orders.getResultset();
            				Map<String, String> message = caseResultMsg((String)repetitionAudit.get("state"));
            				Map<String, Object> order_comparison = order_comparison(order_list_old,order_list_new,message,this.getConnection());
            				if("1".equals(order_comparison.get("is_rep"))) {
            					re.put("flag","1");
            					re.put("old_auto_audit_id",repetitionAudit.get("id"));
            					re.put("state",repetitionAudit.get("state"));
            					re.put("message", message.get("check_message"));
            					//re.put("orders", order_comparison.get("orders"));
            				}
        				}
            		}
    			}
    		}
    		
    		map.put("rtnmap",re);
			return map;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
    }
    
    public Map<String, Object> hadSubmit_deal(Map<String, Object> params) throws Exception {
    	try {
    		connect("ipc");
    		Check_repeatDao autoDao = new Check_repeatDao();
    		
    		return params;
    	} catch (Exception e) {
    		throw e;
    	}finally {
    		disconnect();
    	}
    }
    
    private Map<String, Object> order_comparison(List<Record> order_list_old,List<Record> order_list_new,Map<String, String> message, Connection conn) throws Exception {
    	//map中is_rep=1表示对比结果为[是]重复提交; map.put("is_rep", 1);
    	Map<String, Object> map = new HashMap<String, Object>();
    	List<Map<String, Object>> cr_check_results = new ArrayList<Map<String, Object>>(); 
    	for (Record _old : order_list_old) {
    		Map<String, String> check_map = new HashMap<String, String>();
    		check_map.put(""+_old.get("order_code")
    			+"|"+_old.get("shl")
    			+"|"+_old.get("dosage")
    			+"|"+_old.get("dosage_units")
    			+"|"+_old.get("administration")
    			+"|"+_old.get("frequency")
    			+"|"+_old.get("beizhu")
    			+"|"+_old.get("repeat_indicator")
    			+"|"+_old.get("doctor_no")
    			,_old.getString("group_id"));
			for (Record _new : order_list_new) {
				String sss = ""+_new.get("order_code")
    			+"|"+_new.get("shl")
    			+"|"+_new.get("dosage")
    			+"|"+_new.get("dosage_units")
    			+"|"+_new.get("administration")
    			+"|"+_new.get("frequency")
    			+"|"+_new.get("beizhu")
    			+"|"+_new.get("repeat_indicator")
    			+"|"+_new.get("doctor_no");
				String old_group_id = check_map.get(sss);
				if (!CommonFun.isNe(old_group_id)) {
					Record data = new Record();
					//组织插入cr_check_results数据
					String formatDate = CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
					data.put("p_key", CommonFun.getITEMID());
					data.put("auto_audit_id", _new.get("auto_audit_id"));
					data.put("check_datetime", formatDate);
					data.put("patient_id", _new.get("patient_id"));
					data.put("visit_id", _new.get("visit_id"));
					data.put("order_no", _new.get("order_no"));
					data.put("p_type", _new.get("p_type"));
					data.put("check_results", message.get("check_results"));
					data.put("check_message", message.get("check_message"));
					data.put("use_flag", message.get("use_flag"));
					data.put("group_id", _new.get("group_id"));
					data.put("old_group_id", _old.get("group_id"));
					data.put("old_order_no", _old.get("order_no"));
					data.put("repeat_indicator", _new.get("repeat_indicator"));
					data.put("createtime", formatDate);
					cr_check_results.add(data);
				}
			}
		}
    	Check_repeatDao autoDao = new Check_repeatDao();
    	if(order_list_old.size() == cr_check_results.size()) {
    		map.put("is_rep", "1");
    		//插入重复提交数据
    		for (Map<String, Object> d : cr_check_results) {
    			Record crCheckResultsCount = autoDao.getCrCheckResultsCount(d);
    			if (crCheckResultsCount.getLong("count") == 0) {
    				autoDao.insert_cr(d);
				}
			}
    		//Util.executeBatchInsert("cr_check_results", cr_check_results, conn);
    	}
    	return map;
	}

    private static Map<String, String> caseResultMsg(String re_code) {
    	Map<String, String> map = new HashMap<String, String>();
    	switch (re_code) {
    	case "DQ":
    		map.put("check_message", "[医生强制用药，药师正在审查]");
    		map.put("check_results", "11");
    		map.put("use_flag", "0");
			break;
		case "Y":
			map.put("check_message", "[智能审查不通过,药师通过]");
    		map.put("check_results", "3");
    		map.put("use_flag", "1");
			break;
		case "D":
			map.put("check_message", "[药师返回医生决定]");
    		map.put("check_results", "9");
    		map.put("use_flag", "0");
			break;
		case "DS":
			map.put("check_message", "[药师返回医生决定，医生双签名用药]");
    		map.put("check_results", "5");
    		map.put("use_flag", "1");
			break;
		case "N":
			map.put("check_message", "[智能审查不通过,药师不通过]");
    		map.put("check_results", "4");
    		map.put("use_flag", "0");
			break;
		case "DN":
			map.put("check_message", "[药师返回医生决定，医生取消用药]");
    		map.put("check_results", "6");
    		map.put("use_flag", "0");
			break;
		default:
			break;
		}
    	return map;
	}
    
    public static void main(String[] args) {
		System.out.println(caseResultMsg("DN"));
		System.out.println( CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
	}
}
