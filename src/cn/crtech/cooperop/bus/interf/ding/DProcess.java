package cn.crtech.cooperop.bus.interf.ding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.SmartworkBpmsProcessinstanceListRequest;
import com.dingtalk.api.response.SmartworkBpmsProcessinstanceListResponse;
import com.taobao.api.ApiException;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.interf.MyHttpClient;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DProcess {
	private static String corpid;
	private static String corpsecret;
	private static String get_access_token;
	
	static {
		corpid = "ding10a1aa3bd7378032";//SystemConfig.getSystemConfigValue("cooperop", "corpid");
		corpsecret = "MpaSCZo_fSQo471Fwce_0JMdME_GBSjkN4CrLhTNMZzVG0pQzAb3JWRv989bj3Ae";//SystemConfig.getSystemConfigValue("cooperop", "corpsecret");
		get_access_token = "https://oapi.dingtalk.com/gettoken?corpid="+corpid+"&corpsecret="+corpsecret;
	}
	
	public List<Map<String, Object>> getProcess(Date sdate, Date edate, String process_code){
		Map<String, Object> m = MyHttpClient.get(get_access_token);
		if("0".equals(m.get("errcode").toString())){
			return ddProcess(new ArrayList<Map<String, Object>>(), sdate, edate,
					process_code, m.get("access_token").toString(), 0);
		}else{
			return null;
		}
	}
	
	public List<Map<String, Object>> ddProcess(List<Map<String, Object>> resList, Date sdate, Date edate, String process_code, String access_token, long cursor){
		DingTalkClient client = new DefaultDingTalkClient("https://eco.taobao.com/router/rest");
		SmartworkBpmsProcessinstanceListRequest req = new SmartworkBpmsProcessinstanceListRequest();
		req.setProcessCode(process_code);
		req.setStartTime(sdate.getTime());
		if(edate != null){
			req.setEndTime(edate.getTime());
		}
		req.setSize(10L);
		req.setCursor(cursor);
		SmartworkBpmsProcessinstanceListResponse rsp;
		try {
			rsp = client.execute(req, access_token);
			Map<String, Object> map = CommonFun.json2Object(rsp.getBody(), Map.class);
			Map<String, Object> m1 = (Map<String, Object>)map.get("dingtalk_smartwork_bpms_processinstance_list_response");
			Map<String, Object> m2 = (Map<String, Object>)m1.get("result");
			Map<String, Object> m3 = (Map<String, Object>)m2.get("result");
			if(m3 != null){
				Map<String, Object> m4 = (Map<String, Object>)m3.get("list");
				if(m4 != null && m4.size()>0){
					resList.addAll((List<Map<String, Object>>)m4.get("process_instance_top_vo"));
					if(!CommonFun.isNe(m3.get("next_cursor"))){
						ddProcess(resList, sdate, edate, process_code, access_token, ((Integer)m3.get("next_cursor")).longValue());
					}
				}
				
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return resList;
	}
	
	public static void main(String[] args) {
		Map<String, Object> m = MyHttpClient.get(get_access_token);
		if("0".equals(m.get("errcode").toString())){
			DingTalkClient client = new DefaultDingTalkClient("https://eco.taobao.com/router/rest");
			SmartworkBpmsProcessinstanceListRequest req = new SmartworkBpmsProcessinstanceListRequest();
			req.setProcessCode("PROC-FF6YR2IQO2-NP3LJ1J0SO4182NKX26K3-3N23J-PB");
			req.setStartTime(1496678400000L);
			//req.setEndTime(1496678400000L);
			req.setSize(10L);
			req.setCursor(0L);
			SmartworkBpmsProcessinstanceListResponse rsp;
			try {
				rsp = client.execute(req, m.get("access_token").toString());
				System.out.println(rsp.getBody());
			} catch (ApiException e) {
				e.printStackTrace();
			}
			
			
		}
	}
}
