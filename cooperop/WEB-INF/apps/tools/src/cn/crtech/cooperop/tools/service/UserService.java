package cn.crtech.cooperop.tools.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CRPasswordEncrypt;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.tools.dao.UserDao;

public class UserService extends BaseService{
	public int save(Map<String, Object> params) throws Exception {
		try {
			connect();
			UserDao dd = new UserDao();
			String table_name = (String)params.get("table_name");
			String fromfields = (String) params.get("fromfields");
			String tofields = (String) params.get("tofields");
			String keys = (String) params.get("keys");
			
			Result result = dd.query(params);
			for(int i=0;i<result.getCount();i++){
				String tovalue = result.getResultset(i).getString(tofields);
				String realvalue = CRPasswordEncrypt.Encryptstring(tovalue);
				String keyvalue = result.getResultset(i).getString(keys);
				/*Record val = result.getResultset(i);
				val.remove(fromfields);
				val.remove(tofields);
				val.remove("rowno");
				val.remove("rownuma");*/
				
				Map<String, Object> map = new HashMap<String,Object>();
				map.put(fromfields, realvalue);
				
				Map<String, Object> conditions = new HashMap<String,Object>();
				conditions.put(keys, keyvalue);

				dd.update(table_name, map, conditions);
			}
			return 1;
			
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
