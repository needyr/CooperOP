package cn.crtech.cooperop.crdc.service;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.crdc.dao.PopedomDao;

public class PopedomService extends BaseService {
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			PopedomDao dd = new PopedomDao();
			return dd.query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result queryByRole(Map<String, Object> params) throws Exception {
		try {
			connect();
			PopedomDao dd = new PopedomDao();
			return dd.queryByRole(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect();
			PopedomDao dd = new PopedomDao();
			return dd.get(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int save(Map<String, Object> params) throws Exception {
		try {
			connect();
			PopedomDao dd = new PopedomDao();
			if(CommonFun.isNe(params.get("plugin"))){
				Map<String, Object> q = new HashMap<String, Object>();
				q.put("id", params.get("system_popedom_id_parent"));
				Record precord = dd.get(q);
				params.put("plugin", precord.get("plugin"));
			}
			if("1".equals(params.get("is_cs_page"))){
				if("bill".equals(params.get("page_type"))){
					params.put("code", params.get("plugin")+"."+params.get("page_type")+"."+params.get("page_bs")+"."+params.get("page_lx"));
				}else if("query".equals(params.get("page_type"))){
					params.put("code", params.get("plugin")+"."+params.get("page_type")+".q."+params.get("page_lx"));
				}else if("materials".equals(params.get("page_type"))){
					params.put("code", params.get("plugin")+"."+params.get("page_type")+".m."+params.get("page_lx"));
				}else if("audit".equals(params.get("page_type"))){
					params.put("code", params.get("plugin")+"."+params.get("page_type")+".a."+params.get("page_lx"));
				}
			}else{
				params.remove("page_type");
				params.remove("page_lx");
				params.remove("page_bs");
			}
			params.put("custom_popedom", "1");
			int i = dd.insert(params);
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect();
			PopedomDao dd = new PopedomDao();
			if(CommonFun.isNe(params.get("plugin"))){
				Map<String, Object> q = new HashMap<String, Object>();
				q.put("id", params.get("system_popedom_id_parent"));
				Record precord = dd.get(q);
				params.put("plugin", precord.get("plugin"));
			}
			if("1".equals(params.get("is_cs_page"))){
				if("bill".equals(params.get("page_type"))){
					params.put("code", params.get("plugin")+"."+params.get("page_type")+"."+params.get("page_bs")+"."+params.get("page_lx"));
				}else if("query".equals(params.get("page_type"))){
					params.put("code", params.get("plugin")+"."+params.get("page_type")+".q."+params.get("page_lx"));
				}else if("materials".equals(params.get("page_type"))){
					params.put("code", params.get("plugin")+"."+params.get("page_type")+".m."+params.get("page_lx"));
				}else if("audit".equals(params.get("page_type"))){
					params.put("code", params.get("plugin")+"."+params.get("page_type")+".a."+params.get("page_lx"));
				}
			}else{
				params.remove("page_type");
				params.remove("page_lx");
				params.remove("page_bs");
			}
			int i = dd.update(params);
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect();
			PopedomDao dd = new PopedomDao();
			int i = dd.delete(params);
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record getMaxId(Map<String, Object> params) throws Exception {
		try {
			connect();
			PopedomDao dd = new PopedomDao();
			Record pa = new Record();
			pa.put("id", params.get("system_popedom_id_parent"));
			Record curpopedom = dd.get(pa);
			Record child = dd.getMaxId(params);
			String 	maxid = CommonFun.isNe(child.getString("id"))?pa.getString("id"):child.getString("id");
			String a = "",b = "",c = "",d = "";
			if("1".equals(curpopedom.getString("level"))){
				 a = maxid.substring(0, 2);
				 b = maxid.substring(2, 4);
				 c = maxid.substring(4);
				 d = getAddOne(b);
			}else if("2".equals(curpopedom.getString("level"))){
				 a = maxid.substring(0, 4);
				 b = maxid.substring(4, 6);
				 c = maxid.substring(6);
				 d = getAddOne(b);
			}else if("3".equals(curpopedom.getString("level"))){
				 a = maxid.substring(0, 6);
				 b = maxid.substring(6, 8);
				 c = maxid.substring(8);
				 d = getAddOne(b);
			}else if("4".equals(curpopedom.getString("level"))){
				 a = maxid.substring(0, 8);
				 b = maxid.substring(8);
				 c = "";
				 d = getAddOne(b);
			}
			child.put("id", a + d + c);
			child.put("order", Integer.parseInt(d));
			
			return child;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public String getAddOne(String t){
		String a = 1+t;
		String b = String.valueOf(Integer.parseInt(a)+1);
		
		return b.substring(1);
		
		
	}
}
