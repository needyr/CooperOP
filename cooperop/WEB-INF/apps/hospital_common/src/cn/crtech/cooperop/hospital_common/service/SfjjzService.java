package cn.crtech.cooperop.hospital_common.service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.DictdrugDao;
import cn.crtech.cooperop.hospital_common.dao.SfjjzDao;

public class SfjjzService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SfjjzDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result query_mx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SfjjzDao().query_mx(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.put("apa_check_sorts_id", "43");
			Map<String,Object> temp=new HashMap<String,Object>();
			temp.putAll(params);
			temp.put("zdy_cz", "新增禁忌症问题");
			temp.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			temp.put("admin",user().getName());
			temp.put("admin_no",user().getNo());
			temp.put("zdy_type", "禁忌症问题");
			SfjjzDao SfjjzDao = new SfjjzDao();
			int res=SfjjzDao.insert(params);
			temp.put("parent_id", SfjjzDao.getSeqVal("shengfangzl_jjz"));
			new DictdrugDao().insertZdyLog(temp);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void save_mx_all(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			SfjjzDao dao = new SfjjzDao();
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("id", params.get("parent_id"));
			Record shengfangzl_jjz = new SfjjzDao().getShengfangzl_jjz(map);
			if (params.get("fdname").equals("diagnosis_CODE")) {
				params.remove("value");
				String[] codes = ((String)params.remove("valuefifter")).split(",");
				for (int i = 0; i < codes.length; i++) {
					if (params.get("formul").equals("like") || params.get("formul").equals("not like")) {
						params.put("value", "%"+codes[i]+"%");
					}else {
						params.put("value", codes[i]);
					}
					Record dia = dao.getDia(codes[i]);
					params.put("diagnosis_name", dia.get("diagnosis_name"));
					params.put("diagnosis_code", dia.get("diagnosis_code"));
					Map<String,Object> temp=new HashMap<String,Object>();
					temp.put("spbh", shengfangzl_jjz.get("spbh"));
					temp.putAll(params);
					temp.put("zdy_cz", "新增禁忌症问题");
					temp.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
					temp.put("admin",user().getName());
					temp.put("admin_no",user().getNo());
					temp.put("zdy_type", "禁忌症问题");
					dao.insert_mx(params);
					temp.put("jjz_child_id", dao.getSeqVal("shengfangzl_jjz_diagnosis"));
					new DictdrugDao().insertZdyLog(temp);
				}
			}else if (params.get("fdname").equals("diagnosis_NAME")) {
				params.remove("value");
				String[] codes = ((String)params.remove("valuefifter")).split(",");
				for (int i = 0; i < codes.length; i++) {
					Record dia = dao.getDia(codes[i]);
					params.put("diagnosis_name", dia.get("diagnosis_name"));
					params.put("diagnosis_code", dia.get("diagnosis_code"));
					if (params.get("formul").equals("like") || params.get("formul").equals("not like")) {
						params.put("value", "%"+dia.get("diagnosis_name")+"%");
					}else {
						params.put("value", dia.get("diagnosis_name"));
					}
					Map<String,Object> temp=new HashMap<String,Object>();
					temp.putAll(params);
					temp.put("spbh", shengfangzl_jjz.get("spbh"));
					temp.put("zdy_cz", "新增禁忌症问题");
					temp.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
					temp.put("admin",user().getName());
					temp.put("admin_no",user().getNo());
					temp.put("zdy_type", "禁忌症问题");
					dao.insert_mx(params);
					temp.put("jjz_child_id", dao.getSeqVal("shengfangzl_jjz_diagnosis"));
					new DictdrugDao().insertZdyLog(temp);
				}
			}
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void saveAll(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			SfjjzDao sf = new SfjjzDao();
			Map<String, Object> map = new HashMap<String, Object>();
			Object object = params.get("codes");
			Map<String,Object> map2=new HashMap<String,Object>();
			map2.put("id", params.get("parent_id"));
			Record shengfangzl_jjz = new SfjjzDao().getShengfangzl_jjz(map2);
			if (object instanceof String[]) {
				String[] codes = (String[]) params.get("codes");
				for (int i = 0; i < codes.length; i++) {
					map.put("fdname", "diagnosis_name");
					map.put("formul", "=");
					map.put("xmid", "048");
					map.put("xmbh", "048");
					map.put("xmmch", "诊断代码");
					map.put("value", codes[i]);
					map.put("tiaojian", "or");
					map.put("parent_id", params.get("parent_id"));
					Record dia = sf.getDia(codes[i]);
					map.put("diagnosis_name", dia.get("diagnosis_name"));
					map.put("diagnosis_code", dia.get("diagnosis_code"));
					map.put("beizhu", "批量添加");
					Map<String,Object> temp=new HashMap<String,Object>();
					temp.putAll(map);
					temp.put("spbh", shengfangzl_jjz.get("spbh"));
					temp.put("zdy_cz", "新增禁忌症问题");
					temp.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
					temp.put("admin",user().getName());
					temp.put("admin_no",user().getNo());
					temp.put("zdy_type", "禁忌症问题");
					sf.insert_mx(map);
					temp.put("jjz_child_id", sf.getSeqVal("shengfangzl_jjz_diagnosis"));
					new DictdrugDao().insertZdyLog(temp);
				}
			}else {
				if (!CommonFun.isNe(params.get("codes"))) {
					String codes = (String) params.get("codes");
					map.put("fdname", "diagnosis_name");
					map.put("formul", "=");
					map.put("xmid", "048");
					map.put("xmbh", "048");
					map.put("xmmch", "诊断代码");
					map.put("value", codes);
					map.put("tiaojian", "or");
					map.put("parent_id", params.get("parent_id"));
					Record dia = sf.getDia(codes);
					map.put("diagnosis_name", dia.get("diagnosis_name"));
					map.put("diagnosis_code", dia.get("diagnosis_code"));
					map.put("beizhu", "批量添加");
					Map<String,Object> temp=new HashMap<String,Object>();
					temp.putAll(map);
					temp.put("spbh", shengfangzl_jjz.get("spbh"));
					temp.put("zdy_cz", "新增禁忌症问题");
					temp.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
					temp.put("admin",user().getName());
					temp.put("admin_no",user().getNo());
					temp.put("zdy_type", "禁忌症问题");
					sf.insert_mx(map);
					temp.put("jjz_child_id", sf.getSeqVal("shengfangzl_jjz_diagnosis"));
					new DictdrugDao().insertZdyLog(temp);
				}
			}
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int check = 0 ;
			String isolate = SystemConfig.getSystemConfigValue("hospital_common", "zdy_cz_change_isolate", "【改为】");
			Map<String, Object> temp = (Map<String, Object>)new SfjjzDao().getShengfangzl_jjz(params);
			Map<String, Object> map = new HashMap<String, Object>(); 
			Iterator<Entry<String, Object>> iterator = params.entrySet().iterator(); 
			while (iterator.hasNext()) { 
			  Entry<String, Object> next = iterator.next();
			  String key = next.getKey();
			  Object old_value = temp.get(key);
			  Object new_value = params.get(key);
			  if(!CommonFun.isNe(new_value)) {
				  if(!new_value.equals(old_value)) {
					  map.put(key, (CommonFun.isNe(old_value) ?"不存在内容":old_value) + isolate + new_value);
					  check = 1;
				  }
			  }else {
				  if(!CommonFun.isNe(old_value)) {
					  map.put(key, old_value + isolate + (CommonFun.isNe(new_value) ?"不存在内容":new_value));
					  check = 1;
				  }
			  }
			}
			if (check == 1) {
				map.put("parent_id", temp.get("id"));
				map.put("spbh", temp.get("spbh"));
				map.put("zdy_cz", "修改禁忌症问题");
				map.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				map.put("admin",user().getName());
				map.put("admin_no",user().getNo());
				map.put("zdy_type", "禁忌症问题");
				new DictdrugDao().insertZdyLog(map);
			}
			int res=new SfjjzDao().update(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int insert_mx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String,Object> temp=new HashMap<String,Object>();
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("id", params.get("parent_id"));
			Record shengfangzl_jjz = new SfjjzDao().getShengfangzl_jjz(map);
			temp.putAll(params);
			temp.put("spbh", shengfangzl_jjz.get("spbh"));
			temp.put("zdy_cz", "新增禁忌症问题");
			temp.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			temp.put("admin",user().getName());
			temp.put("admin_no",user().getNo());
			temp.put("zdy_type", "禁忌症问题");
			SfjjzDao SfjjzDao = new SfjjzDao();
			int res=SfjjzDao.insert_mx(params);
			temp.put("jjz_child_id", SfjjzDao.getSeqVal("shengfangzl_jjz_diagnosis"));
			new DictdrugDao().insertZdyLog(temp);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int update_mx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int check = 0 ;
			String isolate = SystemConfig.getSystemConfigValue("hospital_common", "zdy_cz_change_isolate", "【改为】");
			Map<String, Object> temp = (Map<String, Object>)new SfjjzDao().get_mx_byId(params);
			Map<String, Object> map = new HashMap<String, Object>(); 
			Iterator<Entry<String, Object>> iterator = params.entrySet().iterator(); 
			while (iterator.hasNext()) { 
			  Entry<String, Object> next = iterator.next();
			  String key = next.getKey();
			  Object old_value = temp.get(key);
			  Object new_value = params.get(key);
			  if(!CommonFun.isNe(new_value)) {
				  if(!new_value.equals(old_value)) {
					  map.put(key, (CommonFun.isNe(old_value) ?"不存在内容":old_value) + isolate + new_value);
					  check = 1;
				  }
			  }else {
				  if(!CommonFun.isNe(old_value)) {
					  map.put(key, old_value + isolate + (CommonFun.isNe(new_value) ?"不存在内容":new_value));
					  check = 1;
				  }
			  }
			}
			if (check == 1) {
				map.put("parent_id", temp.remove("parent_id"));
				map.put("jjz_child_id", temp.remove("id"));
				map.put("spbh", temp.get("spbh"));
				map.put("zdy_cz", "修改禁忌症问题");
				map.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				map.put("admin",user().getName());
				map.put("admin_no",user().getNo());
				map.put("zdy_type", "禁忌症问题");
				new DictdrugDao().insertZdyLog(map);
			}
			int res=new SfjjzDao().update_mx(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Record record = new Record();
			SfjjzDao sfjjzDao = new SfjjzDao();
			DictdrugDao dictdrugDao = new DictdrugDao();
			record.put("parent_id", params.get("id"));
			Record temp = sfjjzDao.getShengfangzl_jjz(params);
			temp.remove("rowno");
			temp.put("parent_id", temp.remove("id"));
			temp.put("zdy_cz", "删除禁忌症问题");
			temp.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			temp.put("admin",user().getName());
			temp.put("admin_no",user().getNo());
			temp.put("zdy_type", "禁忌症问题");
			dictdrugDao.insertZdyLog(temp);
			int res= sfjjzDao.delete(params);
			List<Record> resultset = sfjjzDao.query_mx_all(record).getResultset();
			for (Record mx : resultset) {
				mx.remove("rowno");
				mx.remove("rownuma");
				mx.put("spbh", temp.get("spbh"));
				mx.put("parent_id", mx.remove("parent_id"));
				mx.put("jjz_child_id", mx.remove("id"));
				mx.put("zdy_cz", "删除禁忌症问题");
				mx.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				mx.put("admin",user().getName());
				mx.put("admin_no",user().getNo());
				mx.put("zdy_type", "禁忌症问题");
				dictdrugDao.insertZdyLog(mx);
			}
			int mx = sfjjzDao.delete_mx(record);
			return res+mx;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int delete_mx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			SfjjzDao sfjjzDao = new SfjjzDao();
			Record temp = sfjjzDao.get_mx_byId(params);
			temp.remove("rowno");
			temp.put("parent_id", temp.remove("parent_id"));
			temp.put("jjz_child_id", temp.remove("id"));
			temp.put("zdy_cz", "删除禁忌症问题");
			temp.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			temp.put("admin",user().getName());
			temp.put("admin_no",user().getNo());
			temp.put("zdy_type", "禁忌症问题");
			new DictdrugDao().insertZdyLog(temp);
			int mx = sfjjzDao.delete_mx(params);
			return mx;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SfjjzDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get_mx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SfjjzDao().get_mx(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Map<String, List<Record>> queryItem(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, List<Record>> map = new HashMap<String, List<Record>>();
			map.put("parent", new SfjjzDao().queryFirstItem(params).getResultset());
			map.put("child", new SfjjzDao().queryChild(params).getResultset());
			return map;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public List<Record> queryOrther(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SfjjzDao().queryOrther(params).getResultset();
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record queryDiagnisis(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Record result = new Record();
			result.put("mx", new SfjjzDao().queryDiagnisis(params).getResultset());
			return result;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record queryJjzZd(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Record result = new Record();
			result.put("diagnos", new SfjjzDao().queryJjzZd(params).getResultset());
			return result;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
