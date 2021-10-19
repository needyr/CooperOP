package cn.crtech.cooperop.bus.interf.ding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.SmartworkAttendsGetsimplegroupsRequest;
import com.dingtalk.api.request.SmartworkAttendsListscheduleRequest;
import com.dingtalk.api.response.SmartworkAttendsGetsimplegroupsResponse;
import com.dingtalk.api.response.SmartworkAttendsListscheduleResponse;
import com.taobao.api.ApiException;
import com.taobao.api.internal.util.StringUtils;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.interf.MyHttpClient;
import cn.crtech.cooperop.bus.util.CommonFun;

public class Attends {
	private static String corpid;
	private static String corpsecret;
	private static String get_access_token;
	
	static {
		corpid = "ding10a1aa3bd7378032";//SystemConfig.getSystemConfigValue("cooperop", "corpid");
		corpsecret = "MpaSCZo_fSQo471Fwce_0JMdME_GBSjkN4CrLhTNMZzVG0pQzAb3JWRv989bj3Ae";//SystemConfig.getSystemConfigValue("cooperop", "corpsecret");
		get_access_token = "https://oapi.dingtalk.com/gettoken?corpid="+corpid+"&corpsecret="+corpsecret;
	}
	
	public Map<String, Object> getSimplegroups(){
		Map<String, Object> groups = new HashMap<String, Object>();
		Map<String, Object> m = MyHttpClient.get(get_access_token);
		if("0".equals(m.get("errcode").toString())){
			DingTalkClient client = new DefaultDingTalkClient("https://eco.taobao.com/router/rest");
	    	SmartworkAttendsGetsimplegroupsRequest req = new SmartworkAttendsGetsimplegroupsRequest();
	    	req.setOffset(0L);
	    	req.setSize(10L);
	    	SmartworkAttendsGetsimplegroupsResponse rsp;
			try {
				rsp = client.execute(req, m.get("access_token").toString());
				Map<String, Object> map = CommonFun.json2Object(rsp.getBody(), Map.class);
				Map<String, Object> g1 = (Map<String, Object>)map.get("dingtalk_smartwork_attends_getsimplegroups_response");
				if(g1 != null){
					Map<String, Object> g2 = (Map<String, Object>)g1.get("result");
					if(g2 != null){
						Map<String, Object> g3 = (Map<String, Object>)g2.get("result");
						if(g3 != null){
							Map<String, Object> g4 = (Map<String, Object>)g3.get("groups");
							if(g4 != null){
								groups.put("groups", g4.get("at_group_for_top_vo"));
							}
						}
					}
				}
			} catch (ApiException e) {
				e.printStackTrace();
			}
		}else{
			groups.putAll(m);
		}
		
		return groups;
	}
	public Map<String, Object> listschedule(Date date) throws Exception{
		Map<String, Object> schedule = new HashMap<String, Object>();
		Map<String, Object> m = MyHttpClient.get(get_access_token);
		if("0".equals(m.get("errcode").toString())){
			DingTalkClient client = new DefaultDingTalkClient("https://eco.taobao.com/router/rest");
			SmartworkAttendsListscheduleRequest req = new SmartworkAttendsListscheduleRequest();
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			req.setWorkDate(f.parse(f.format(date)));
			req.setOffset(0L);
			req.setSize(200L);
			SmartworkAttendsListscheduleResponse rsp;
			try {
				rsp = client.execute(req, m.get("access_token").toString());
				Map<String, Object> map = CommonFun.json2Object(rsp.getBody(), Map.class);
				Map<String, Object> g1 = (Map<String, Object>)map.get("dingtalk_smartwork_attends_listschedule_response");
				if(g1 != null){
					Map<String, Object> g2 = (Map<String, Object>)g1.get("result");
					if(g2 != null){
						Map<String, Object> g3 = (Map<String, Object>)g2.get("result");
						if(g3 != null){
							Map<String, Object> g4 = (Map<String, Object>)g3.get("schedules");
							if(g4 != null){
								schedule.put("schedule", g4.get("at_schedule_for_top_vo"));
							}
						}
					}
				}
			} catch (ApiException e) {
				e.printStackTrace();
			}
		}else{
			schedule.putAll(m);
		}
		
		return schedule;
	}
	/**
	 * 
	 * @param params
	 * eg:
	 * 	{
	 *	    "userIds": ["001","002"],必须
	 *	    "checkDateFrom": "yyyy-MM-dd hh:mm:ss",必须
	 *    	"checkDateTo": "yyyy-MM-dd hh:mm:ss"必须
	 *	}
	 * @return
	 */
	public Map<String, Object> listAttendsRecord(Map<String, Object> params){
		Map<String, Object> back = new HashMap<String, Object>();
		Map<String, Object> m = MyHttpClient.get(get_access_token);
		if("0".equals(m.get("errcode").toString())){
			String url = "https://oapi.dingtalk.com/attendance/listRecord?access_token="+m.get("access_token");
			back.putAll(MyHttpClient.post(url, params));
		}else{
			back.putAll(m);
		}
		return back;
	}
	
	public static void main(String[] args) {
		System.out.println(new Attends().getSimplegroups());
	}
}
