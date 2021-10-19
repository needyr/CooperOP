package cn.crtech.cooperop.hospital_common.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.DictdrugDao;
import cn.crtech.cooperop.hospital_common.dao.SfSyzDao;

public class SfSyzService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SfSyzDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.put("tiaojian", "or");
			params.put("apa_check_sorts_id", "42");
			Map<String,Object> temp=new HashMap<String,Object>();
			temp.putAll(params);
			temp.put("zdy_cz", "新增适应症问题");
			temp.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			temp.put("admin",user().getName());
			temp.put("admin_no",user().getNo());
			temp.put("zdy_type", "适应症问题");
			SfSyzDao sfSyzDao = new SfSyzDao();
			int res=sfSyzDao.insert(params);
			temp.put("parent_id", sfSyzDao.getSeqVal("shengfangzl_syz"));
			new DictdrugDao().insertZdyLog(temp);
			return res;
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
			Map<String, Object> temp = (Map<String, Object>)new SfSyzDao().getSingle(params);
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
				map.put("zdy_cz", "修改适应症问题");
				map.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				map.put("admin",user().getName());
				map.put("admin_no",user().getNo());
				map.put("zdy_type", "适应症问题");
				new DictdrugDao().insertZdyLog(map);
			}
			int res=new SfSyzDao().update(params);
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
			SfSyzDao sfSyzDao = new SfSyzDao();
			DictdrugDao dictdrugDao = new DictdrugDao();
			Record temp = sfSyzDao.getSingle(params);
			Record record = new Record();
			record.put("parent_id", temp.get("id"));
			Result query_mx_byParentId = sfSyzDao.query_mx_byParentId(record);
			for (Record r : query_mx_byParentId.getResultset()) {
				r.remove("rowno");
				r.put("parent_id", r.remove("parent_id"));
				r.put("syz_child_id", r.remove("id"));
				r.put("zdy_cz", "删除适应症问题");
				r.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				r.put("admin",user().getName());
				r.put("admin_no",user().getNo());
				r.put("zdy_type", "适应症问题");
				dictdrugDao.insertZdyLog(r);
			}
			temp.remove("rowno");
			temp.put("parent_id", temp.remove("id"));
			temp.put("zdy_cz", "删除适应症问题");
			temp.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			temp.put("admin",user().getName());
			temp.put("admin_no",user().getNo());
			temp.put("zdy_type", "适应症问题");
			dictdrugDao.insertZdyLog(temp);
			int res=sfSyzDao.delete(params);
			sfSyzDao.delete_mx(record);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SfSyzDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result query_mx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SfSyzDao().query_mx(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int delete_mx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			SfSyzDao sfsyzDao = new SfSyzDao();
			Record temp = sfsyzDao.get_mx_byId(params);
			temp.remove("rowno");
			temp.put("parent_id", temp.remove("parent_id"));
			temp.put("syz_child_id", temp.remove("id"));
			temp.put("zdy_cz", "删除适应症问题");
			temp.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			temp.put("admin",user().getName());
			temp.put("admin_no",user().getNo());
			temp.put("zdy_type", "适应症问题");
			new DictdrugDao().insertZdyLog(temp);
			int mx = sfsyzDao.delete_mx(params);
			return mx;
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
			SfSyzDao sfsyzDao = new SfSyzDao();
			Record shengfangzl_jjz = sfsyzDao.get(map);
			temp.putAll(params);
			temp.put("spbh", shengfangzl_jjz.get("spbh"));
			temp.put("zdy_cz", "新增适应症问题");
			temp.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			temp.put("admin",user().getName());
			temp.put("admin_no",user().getNo());
			temp.put("zdy_type", "适应症问题");
			int res=sfsyzDao.insert_mx(params);
			temp.put("syz_child_id", sfsyzDao.getSeqVal("shengfangzl_syz_diagnosis"));
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
			Map<String, Object> temp = (Map<String, Object>)new SfSyzDao().get_mx_byId(params);
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
				map.put("syz_child_id", temp.remove("id"));
				map.put("spbh", temp.get("spbh"));
				map.put("zdy_cz", "修改适应症问题");
				map.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				map.put("admin",user().getName());
				map.put("admin_no",user().getNo());
				map.put("zdy_type", "适应症问题");
				new DictdrugDao().insertZdyLog(map);
			}
			int res=new SfSyzDao().update_mx(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get_mx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SfSyzDao().get_mx(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result batchQuery(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new SfSyzDao().batchQuery(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int batchInsert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			String str = (String) params.get("data");
			List<Map> list = CommonFun.json2Object(str, List.class);
			SfSyzDao dao = new SfSyzDao();
			int rs=0;
			start();
			SfSyzDao sfsyzDao = new SfSyzDao();
			DictdrugDao dictdrugDao=new DictdrugDao();
			for (Map paramsMap : list) {
				Map<String,Object> temp=new HashMap<String,Object>();
				temp.putAll(paramsMap);
				temp.put("zdy_cz", "新增适应症问题");
				temp.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				temp.put("admin",user().getName());
				temp.put("admin_no",user().getNo());
				temp.put("zdy_type", "适应症问题");
				temp.put("syz_child_id", sfsyzDao.getSeqVal("shengfangzl_syz_diagnosis"));
				rs=dao.batchInsert(paramsMap);
				dictdrugDao.insertZdyLog(temp);
				if(rs==0) {
					return rs;
				}
			}
			commit();
			return rs;
		} catch (Exception e) {
			rollback();
			throw e;
		}finally {
			disconnect();
		}
	}
}
