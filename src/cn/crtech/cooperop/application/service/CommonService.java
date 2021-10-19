package cn.crtech.cooperop.application.service;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.dao.BillDao;
import cn.crtech.cooperop.application.dao.CommonDao;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class CommonService extends BaseService {
	public Result listProducts(Map<String, Object> req) throws Exception {
		try {
			connect();
			return new CommonDao().listAllProducts(req);
		} finally {
			disconnect();
		}
	}
	public Map<String, Object> getFields(Map<String, Object> req) throws Exception {
		try {
			connect();
			Record view = new CommonDao().getSystemView(req);
			if (view == null) {
				throw new Exception("单据未找到");
			}
			
			ObjectInputStream ois = null;
			
			try {
				ois = new ObjectInputStream(new ByteArrayInputStream(view.getBytes("content")));
			} catch (Exception ex) {
				throw ex;
			} finally {
				if (ois != null) ois.close();
			}
			
			@SuppressWarnings("unchecked")
			Map<String, Object> definition =  (Map<String, Object>) ois.readObject();
			List<Map<String, Object>> contents = (List<Map<String, Object>>) definition.get("contents");
			List<Map<String, Object>> fields = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> tables = new ArrayList<Map<String, Object>>();
			getfields(contents, fields, tables);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("hz_fields", fields);
			map.put("tables", tables);
			return map;
		} finally {
			disconnect();
		}
	}
	public Map<String, Object> getJasperTemp(Map<String, Object> req) throws Exception {
		try {
			connect();
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("mx", new BillDao().listDJMX((String)req.get("system_product_code"), (String)req.get("type"), (String)req.get("id")));
			m.put("temp", new CommonDao().getJasperTemp(req));
			return m;
		} finally {
			disconnect();
		}
	}
	
	public List<Map<String, Object>> getfields(List<Map<String, Object>> params, List<Map<String, Object>> r, List<Map<String, Object>> tables){
		for(int i =0; i<params.size();i++){
			Map<String, Object> attrs = (Map<String, Object>) params.get(i).get("attrs");
			if(!"table".equals(params.get(i).get("type"))){
				if(!CommonFun.isNe(attrs.get("name"))){
					Map<String, Object> field = new HashMap<String, Object>();
					field.put("name", attrs.get("name"));
					field.put("fdname", attrs.get("name"));
					field.put("fdtype", attrs.get("fdtype"));
					field.put("fdsize", attrs.get("maxlength"));
					field.put("chnname", attrs.get("label"));
					r.add(field);
				}
				List<Map<String, Object>> ch = (List<Map<String, Object>>) params.get(i).get("contents");
				if(ch != null){
					getfields(ch,r, tables);
				}
			}else if("table".equals(params.get(i).get("type"))){
				Map<String, Object> tmap = new HashMap<String, Object>();
				List<Map<String, Object>> tflist = new ArrayList<Map<String, Object>>();
				Map<String, Object> tattrs = (Map<String, Object>) params.get(i).get("attrs");
				List<Map<String, Object>> table = (List<Map<String, Object>>)params.get(i).get("contents");
				tmap.put("tableid", tattrs.get("tableid"));
				for(int t =0; t<table.size();t++){
					if(table.get(t).get("type").equals("tablefields")){
						List<Map<String, Object>> filds = (List<Map<String, Object>>)table.get(t).get("contents");
						for(int f =0; f<filds.size();f++){
							Map<String, Object> fattrs = (Map<String, Object>) filds.get(f).get("attrs");
							if(!CommonFun.isNe(fattrs.get("name"))){
								Map<String, Object> field = new HashMap<String, Object>();
								field.put("name", fattrs.get("name"));
								field.put("fdname", fattrs.get("name"));
								field.put("fdtype", fattrs.get("fdtype"));
								field.put("fdsize", fattrs.get("size"));
								field.put("chnname", fattrs.get("label"));
								tflist.add(field);
							}
						}
						
					}
				}
				tmap.put("fields", tflist);
				tables.add(tmap);
			}
		}
		return r;
	}
	
	public void loadLicense()throws Exception {
		/*try {
			connect();
			start();
			CommonDao cd = new CommonDao();
			cd.deleteNormalPopedom(new HashMap<String, Object>());
			List<PopedomBean> list = License.getPopedoms();
			for(PopedomBean p : list){
				//BeanMap beanMap = BeanMap.create(p);
				Map<String, Object> map = new HashMap<String, Object>();
				for (Object key : beanMap.keySet()) {
		            map.put(key+"", beanMap.get(key));
		        }
				map.put("id", p.getId());
				map.put("name", p.getName());
				map.put("code", p.getAction());
				map.put("icon", p.getIcons());
				map.put("system_popedom_id_parent", p.getParent_id());
				map.put("description", p.getDescription());
				map.put("order_no", p.getOrder_no());
				map.put("is_menu", p.isMenu()?"1":"0");
				map.put("abstract", "");
				map.put("plugin", p.getPlugin());
				if(CommonFun.json2Object(p.getParams(), Map.class) != null){
					map.put("event", CommonFun.isNe(p.getParams())?""
							:CommonFun.json2Object(p.getParams(), Map.class).get("event"));
				}
				map.put("tel_menu", p.isTel_menu()?"1":"0");
				cd .insertNormalPopedom(map);
			}
			commit();
		} finally {
			disconnect();
		}*/
	}
	
	public Result queryDefaultFields(Map<String, Object> req) throws Exception {
		try {
			connect();
			return new CommonDao().queryDefaultFields(req);
		} catch(Exception e){
			throw e;
		}finally {
			disconnect();
		}
	}
}
