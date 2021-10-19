package cn.crtech.cooperop.hospital_common.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.DictdrugDao;
import cn.crtech.cooperop.hospital_common.dao.SfCCRDao;

public class SfCCRService extends BaseService {
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SfCCRDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String,Object> temp=new HashMap<String,Object>();
			temp.putAll(params);
			temp.put("zdy_cz", "新增肌酐清除率");
			temp.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			temp.put("admin",user().getName());
			temp.put("admin_no",user().getNo());
			temp.put("zdy_type", "肌酐清除率");
			SfCCRDao sfCCRDao = new SfCCRDao();
			int res=sfCCRDao.insert(params);
			temp.put("parent_id", sfCCRDao.getSeqVal("shengfangzl_ccr"));
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
			Map<String, Object> temp = (Map<String, Object>)new SfCCRDao().getSingle(params);
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
				map.put("zdy_cz", "修改肌酐清除率");
				map.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				map.put("admin",user().getName());
				map.put("admin_no",user().getNo());
				map.put("zdy_type", "肌酐清除率");
				new DictdrugDao().insertZdyLog(map);
			}
			int res=new SfCCRDao().update(params);
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
			Record temp = new SfCCRDao().getSingle(params);
			temp.remove("rowno");
			temp.put("parent_id", temp.remove("id"));
			temp.put("zdy_cz", "删除肌酐清除率");
			temp.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			temp.put("admin",user().getName());
			temp.put("admin_no",user().getNo());
			temp.put("zdy_type", "肌酐清除率");
			new DictdrugDao().insertZdyLog(temp);
			int res=new SfCCRDao().delete(params);
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
			return new SfCCRDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
