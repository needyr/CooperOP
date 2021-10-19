package cn.crtech.cooperop.application.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.dao.DingdDao;
import cn.crtech.cooperop.bus.interf.ding.DProcess;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DingdService extends BaseService {
	public void saveDataFromDD(Map<String, Object> req) throws Exception {
		try {
			connect("base");
			DingdDao dd = new DingdDao();
			Result pd = dd.queryProcessDefine(req);
			if(pd == null){
				return;
			}
			Calendar c = Calendar.getInstance();
			Date edate = null;
			c.add(Calendar.MONTH, -1);
			Date sdate = c.getTime();
			DProcess dproc = new DProcess();
			for(Record p : pd.getResultset()){
				Map<String, Object> pp = new HashMap<String, Object>();
				pp.put("table_code_", p.getString("code"));
				Record rr = dd.queryProcessTime(pp);
				if(rr != null && rr.getString("query_time")!=null){
					SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
					sdate = f.parse(f.format(rr.getDate("query_time")));
					//删除查询当天的数据以后的数据
					pp.put("query_time", sdate);
					dd.deleteByTime(pp);
				}
				
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("process_code", p.getString("process_code"));
				//查询 为办结流程最早的一个时间，作为查询时间
				Result pfields = dd.queryProcessFieldDefine(param);
				Map<String, Object> filedMap = new HashMap<String, Object>();
				for(Record ff : pfields.getResultset()){
					filedMap.put(ff.getString("field_name"), ff.get("field_code"));
				}
				List<Map<String, Object>> reList = dproc.getProcess(sdate, edate, p.getString("process_code"));
				for(Map<String, Object> params : reList){
					//approver_userid_list,处理审批人
					Map<String, Object> m1 = (Map<String, Object>)params.remove("approver_userid_list");
					if(m1 != null){
						List<String> approver_userid_list = (List<String>)m1.get("string");
						String approver_userid_string = "";
						for(String s : approver_userid_list){
							approver_userid_string += ","+s;
						}
						if(approver_userid_string != ""){
							params.put("approver_userid_list", approver_userid_string.substring(1));
						}
					}
					//cc_userid_list,处理抄送人
					Map<String, Object> m2 = (Map<String, Object>)params.remove("cc_userid_list");
					if(m2 != null){
						List<String> cc_userid_list = (List<String>)m2.get("string");
						String cc_userid_string = "";
						for(String s : cc_userid_list){
							cc_userid_string += ","+s;
						}
						if(cc_userid_string != ""){
							params.put("approver_userid_list", cc_userid_string.substring(1));
						}
					}
					//form_component_values,处理字段
					Map<String, Object> m3 = (Map<String, Object>)params.remove("form_component_values");
					if(m3 != null){
						List<Map<String, Object>> form_component_value_vo = (List<Map<String, Object>>)m3.get("form_component_value_vo");
						for(Map<String, Object> f : form_component_value_vo){
							if(f.get("name").toString().indexOf(",") > -1){
								String[] names = CommonFun.json2Object((String)f.get("name"), String[].class);
								String[] values = CommonFun.json2Object((String)f.get("value"), String[].class);
								for(int i=0; i<names.length ;i++){
									if(filedMap.get(names[i]).toString() != null){
										params.put(filedMap.get(names[i]).toString(), values[i]);
									}
								}
								//-------------------------------------------------------临时处理(计算值没有对应的name值）
								if(values.length > names.length && filedMap.containsValue((filedMap.get(names[0]).toString()+"_js"))){
									params.put(filedMap.get(names[0]).toString()+"_js", values[values.length-1]);
								}
							}else{
								if(filedMap.get((String)f.get("name")) != null){
									params.put(filedMap.get((String)f.get("name")).toString(), f.get("value"));
								}
							}
								
						}
					}
					params.put("table_code_", p.getString("code"));
//					log.debug(params); 
					dd.insert(params);
				}
			}
		} finally {
			disconnect();
		}
	}
	
}
