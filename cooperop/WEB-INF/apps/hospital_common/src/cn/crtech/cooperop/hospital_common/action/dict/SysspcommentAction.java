package cn.crtech.cooperop.hospital_common.action.dict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.hospital_common.service.DictSysYpflService;
import cn.crtech.cooperop.hospital_common.service.dict.SysSpcommentService;

/**
 * @className: SysspcommentAction
 * @description: 专项点评规则
 * @author: 魏圣峰
 * @date: 2019年3月1日 上午10:04:37
 */
public class SysspcommentAction extends BaseAction {

	public Result query(Map<String, Object> params) throws Exception {
		return new SysSpcommentService().query(params);
	}
	public int save(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("type")) && params.get("type") instanceof String[]) {
			params.put("type", getString((String[]) params.get("type")));
		}else if(CommonFun.isNe(params.get("type"))) {
			params.put("type", null );
		}
		if(!CommonFun.isNe(params.get("pr_state")) && params.get("pr_state") instanceof String[]) {
			params.put("pr_state", getString((String[]) params.get("pr_state")));
			String type = (String) params.get("pr_state");
			String types = "";
			if(type.contains("1")) {
				types = types + " 门诊 ";
			}
			if(type.contains("2")) {
				types = types + " 急诊 ";
			}
			if(type.contains("3")) {
				types = types + " 在院 ";
			}
			if(type.contains("4")) {
				types = types + " 出院 ";
			}
		}else if (CommonFun.isNe(params.get("pr_state"))) {
			params.put("pr_state", null);
		}
		if(!CommonFun.isNe(params.get("pr_result")) && params.get("pr_result") instanceof String[]) {
			params.put("pr_result", getString((String[]) params.get("pr_result")));
		}else if(CommonFun.isNe(params.get("pr_result"))) {
			params.put("pr_result", null);
		}
		if(!CommonFun.isNe(params.get("drug_type")) && params.get("drug_type") instanceof String[]) {
			params.put("drug_type", getString((String[]) params.get("drug_type")));
		}else if(CommonFun.isNe(params.get("drug_type"))) {
			params.put("drug_type", null);
		}
		if(!CommonFun.isNe(params.get("is_kangjy")) && params.get("is_kangjy") instanceof String[]) {
			params.put("is_kangjy", getString((String[]) params.get("is_kangjy")));
		}else if(CommonFun.isNe(params.get("is_kangjy"))) {
			params.put("is_kangjy", null);
		}
		if(!CommonFun.isNe(params.get("use_purp")) && params.get("use_purp") instanceof String[]) {
			params.put("use_purp", getString((String[]) params.get("use_purp")));
		}else if(CommonFun.isNe(params.get("use_purp"))) {
			params.put("use_purp", null);
		}
		if(!CommonFun.isNe(params.get("diagno_type")) && params.get("diagno_type") instanceof String[]) {
			params.put("diagno_type", getString((String[]) params.get("diagno_type")));
		}else if(CommonFun.isNe(params.get("diagno_type"))) {
			params.put("diagno_type", null);
		}
		if(!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof String[]) {
			params.put("incision_type", getString((String[]) params.get("incision_type")));
		}else if(CommonFun.isNe(params.get("incision_type"))) {
			params.put("incision_type", null);
		}
		/*if(!CommonFun.isNe(params.get("report_address"))) {
			params.put("report_address", "http://"+SystemConfig.getSystemConfigValue("hospital_common", "url_local", "")+"/w/hospital_oc/chart/chart/"+params.get("report_address")+".html");
		}*/
		params.put("drug_tags", params.remove("drugtagbh").toString());
		return new SysSpcommentService().insert(params);
	}
	public int update(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("id"))) {
			if(!CommonFun.isNe(params.get("type")) && params.get("type") instanceof String[]) {
				params.put("type", getString((String[]) params.get("type")));
			}else if(CommonFun.isNe(params.get("type"))) {
				params.put("type", null );
			}
			if(!CommonFun.isNe(params.get("pr_state")) && params.get("pr_state") instanceof String[]) {
				params.put("pr_state", getString((String[]) params.get("pr_state")));
				String type = (String) params.get("pr_state");
				String types = "";
				if(type.contains("1")) {
					types = types + " 门诊 ";
				}
				if(type.contains("2")) {
					types = types + " 急诊 ";
				}
				if(type.contains("3")) {
					types = types + " 在院 ";
				}
				if(type.contains("4")) {
					types = types + " 出院 ";
				}
			}else if (CommonFun.isNe(params.get("pr_state"))) {
				params.put("pr_state", null);
			}
			if(!CommonFun.isNe(params.get("pr_result")) && params.get("pr_result") instanceof String[]) {
				params.put("pr_result", getString((String[]) params.get("pr_result")));
			}else if(CommonFun.isNe(params.get("pr_result"))) {
				params.put("pr_result", null);
			}
			if(!CommonFun.isNe(params.get("drug_type")) && params.get("drug_type") instanceof String[]) {
				params.put("drug_type", getString((String[]) params.get("drug_type")));
			}else if(CommonFun.isNe(params.get("drug_type"))) {
				params.put("drug_type", null);
			}
			if(!CommonFun.isNe(params.get("is_kangjy")) && params.get("is_kangjy") instanceof String[]) {
				params.put("is_kangjy", getString((String[]) params.get("is_kangjy")));
			}else if(CommonFun.isNe(params.get("is_kangjy"))) {
				params.put("is_kangjy", null);
			}
			if(!CommonFun.isNe(params.get("use_purp")) && params.get("use_purp") instanceof String[]) {
				params.put("use_purp", getString((String[]) params.get("use_purp")));
			}else if(CommonFun.isNe(params.get("use_purp"))) {
				params.put("use_purp", null);
			}
			if(!CommonFun.isNe(params.get("diagno_type")) && params.get("diagno_type") instanceof String[]) {
				params.put("diagno_type", getString((String[]) params.get("diagno_type")));
			}else if(CommonFun.isNe(params.get("diagno_type"))) {
				params.put("diagno_type", null);
			}
			if(!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof String[]) {
				params.put("incision_type", getString((String[]) params.get("incision_type")));
			}else if(CommonFun.isNe(params.get("incision_type"))) {
				params.put("incision_type", null);
			}
			/*if(!CommonFun.isNe(params.get("report_address"))) {
				params.put("report_address", "http://"+SystemConfig.getSystemConfigValue("hospital_common", "url_local", "")+"/w/hospital_oc/chart/chart/"+params.get("report_address")+".html");
			}*/
			params.put("drug_tags", params.remove("drugtagbh").toString());
			return new SysSpcommentService().update(params);
		}else {return -1;}
	}
	public int delete(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("id"))&&!CommonFun.isNe(params.get("mx_id"))) {
			return new SysSpcommentService().delete(params);
		}else {return -1;}
	}
	public Result querytags(Map<String, Object> params) throws Exception{
		return new SysSpcommentService().queryTags(params);
	}
	public Record edit(Map<String, Object> params) throws Exception {
		Record record = new Record();
		if(!CommonFun.isNe(params.get("id"))) {
			Result result=new SysSpcommentService().get(params);
			Record resultset = result.getResultset(0);
			record.putAll(resultset);
			List<String> ordertagid = new ArrayList<String>(),
					ordertagbh = new ArrayList<String>(),
					ordertagname = new ArrayList<String>(),	
					ordertag_show = new ArrayList<String>();
			for(int i=0;i<result.getResultset().size();i++) {
				if(!CommonFun.isNe(result.getResultset(i).get("drug_tags"))) {
					ordertagid.add(result.getResultset(i).get("drugtagid").toString());
					ordertagbh.add(result.getResultset(i).get("drugtagbh").toString());
					ordertagname.add(result.getResultset(i).get("drugtagname").toString());
					ordertag_show.add(result.getResultset(i).get("drugtag_show").toString());
				}
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("drugtagid", ordertagid);
			map.put("drugtagbh", ordertagbh);
			map.put("drugtagname", ordertagname);
			map.put("drugtag_show", ordertag_show);
			record.put("order", CommonFun.object2Json(map));
		}
		record.put("ypfl", new DictSysYpflService().query(params).getResultset());
		return record;
	}
	
	public String getString(String[] str) {
		String new_str = "";
		for (int i=0;i<str.length;i++) {
			if(i == 0) {
				new_str = str[i];
			}else {
				new_str = new_str + "," + str[i];
			}
		}
		return new_str;
	}
}
