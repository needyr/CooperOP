package cn.crtech.cooperop.crdc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.mvc.view.ViewCreater;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.crdc.dao.WorkFlowDesignerDao;

public class DocFlowService extends BaseService {
	
	public Result queryDocFlowCount(Map<String, Object> req) throws Exception{
		try{
			connect();
			req.put("type", "document");
			return new WorkFlowDesignerDao().queryFlowCount(req);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
		
	}
	
	public void deploy(Map<String, Object> req) throws Exception{
		try{
			connect();
			
			Map<String, Object> p = CommonFun.json2Object((String)req.get("wfjson"), Map.class);
			new WorkFlowDesignerService().deploy(p);
			DesignerService ds = new DesignerService();
			Map<String, Object> m = CommonFun.json2Object((String) req.get("jdata"), Map.class);
			Map<String, Object> attrs = (Map<String, Object>) m.get("attrs");
			String type = (String)attrs.get("type");
			String flag = (String)attrs.get("flag");
			String scheme = (String)attrs.get("schemeid");
			String system_product_code = (String)attrs.get("system_product_code");
			Map<String, Object> f = new HashMap<String, Object>();
			f.put("doc", "doc");
			ViewCreater.create(type, flag, scheme,system_product_code, f);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("state", 1);
			ds.update(type, scheme, flag,system_product_code, params);
			
			List<Map<String, Object>> nodes = (List<Map<String, Object>>) p.get("nodes");
			for(Map<String, Object> map :nodes){
				if("task".equals(map.get("type"))){
					Map<String, Object> a = new HashMap<String, Object>();
					a.putAll(attrs);
					a.put("schemeid",""+attrs.get("schemeid")+map.get("id"));
					Map<String, Object> pa = new HashMap<String, Object>();
					pa.putAll(m);
					pa.put("attrs", a);
					ds.insert(pa);
					
					Map<String, Object> par = new HashMap<String, Object>();
					par.put("state", -3);
					ds.update(type, ""+attrs.get("schemeid")+map.get("id"), flag,system_product_code, par);
					
					Map<String, Object> param = new HashMap<String, Object>();
					String s ="[";
					if("是".equals(map.get("caozuo1"))){
						s += "1";
					}
					if("是".equals(map.get("caozuo2"))){
						if(!"[".equals(s)){
							s += ",2";
						}else{
							s += "2";
						}
					}
					if("是".equals(map.get("caozuo3"))){
						if(!"[".equals(s)){
							s += ",3";
						}else{
							s += "3";
						}
					}
					if("是".equals(map.get("caozuo4"))){
						if(!"[".equals(s)){
							s += ",4";
						}else{
							s += "4";
						}
					}
					s += "]";
					param.put("caozuo", s);
					param.put("canreadfields", map.get("canreadfields"));
					param.put("canmodifyfields", map.get("canmodifyfields"));
					param.put("requiredfields", map.get("requiredfields"));
					param.put("suggestions", "suggestions");
					ViewCreater.create(type, flag, ""+attrs.get("schemeid")+map.get("id"),system_product_code,param);
				}
			}
			Map<String, Object> a = new HashMap<String, Object>();
			a.putAll(attrs);
			a.put("schemeid", scheme+"suggestions");
			Map<String, Object> pa = new HashMap<String, Object>();
			pa.putAll(m);
			pa.put("attrs", a);
			ds.insert(pa);
			Map<String, Object> par = new HashMap<String, Object>();
			par.put("state", -3);
			ds.update(type, scheme+"suggestions", flag,system_product_code, par);
			Map<String, Object> su = new HashMap<String, Object>();
			su.put("suggestions", "suggestions");
			ViewCreater.create(type, flag, scheme+"suggestions",system_product_code, su);
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
}
