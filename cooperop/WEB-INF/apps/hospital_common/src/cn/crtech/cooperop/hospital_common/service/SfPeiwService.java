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
import cn.crtech.cooperop.hospital_common.dao.SfPeiwDao;

public class SfPeiwService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SfPeiwDao().query(params);
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
			temp.put("zdy_cz", "新增配伍问题");
			temp.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			temp.put("admin",user().getName());
			temp.put("admin_no",user().getNo());
			temp.put("zdy_type", "配伍问题");
			SfPeiwDao sfPeiwDao = new SfPeiwDao();
			int res=sfPeiwDao.insert(params);
			temp.put("parent_id", sfPeiwDao.getSeqVal("shengfangzl_peiw"));
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
			if(CommonFun.isNe(params.get("is_lhsc"))) {
				params.put("is_lhsc", "否");
			}
			if(CommonFun.isNe(params.get("is_qb"))) {
				params.put("is_qb", "否");
			}
			int check = 0 ;
			String isolate = SystemConfig.getSystemConfigValue("hospital_common", "zdy_cz_change_isolate", "【改为】");
			Map<String, Object> temp = (Map<String, Object>)new SfPeiwDao().getSingle(params);
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
				map.put("zdy_cz", "修改配伍问题");
				map.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				map.put("admin",user().getName());
				map.put("admin_no",user().getNo());
				map.put("zdy_type", "配伍问题");
				new DictdrugDao().insertZdyLog(map);
			}
			int res=new SfPeiwDao().update(params);
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
			Record temp = new SfPeiwDao().getSingle(params);
			temp.remove("rowno");
			temp.put("parent_id", temp.remove("id"));
			temp.put("zdy_cz", "删除配伍问题");
			temp.put("create_time",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			temp.put("admin",user().getName());
			temp.put("admin_no",user().getNo());
			temp.put("zdy_type", "配伍问题");
			new DictdrugDao().insertZdyLog(temp);
			int res=new SfPeiwDao().delete(params);
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
			return new SfPeiwDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
