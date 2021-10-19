package cn.crtech.cooperop.hospital_common.service.version_message;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.version_message.YcmoduledictDao;
import cn.crtech.cooperop.hospital_common.dao.version_message.YcproductdictDao;
import cn.crtech.cooperop.hospital_common.dao.version_message.YcprojectupgradelogDao;
import cn.crtech.cooperop.hospital_common.dao.version_message.YcupgradetypedictDao;

public class YcprojectupgradelogService extends BaseService{
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new YcprojectupgradelogDao().query(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Result queryVersion(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new YcprojectupgradelogDao().queryVersion(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			disconnect();
		}
	}

	
	public void insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new YcprojectupgradelogDao().insert(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> getById(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new YcprojectupgradelogDao().getById(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public void update(Map<String, Object> params) throws Exception {
		try {
			Map<String, Object> tpMap = new HashMap<String, Object>();
			Record record = null;
			connect("hospital_common");
            if (params.get("product_id")==null || ((String)params.get("product_id")).length()==0) {
            	params.remove("product_id");
			}else {
				if (checkname(params.get("product_id"))) {
    				tpMap.put("pro_name", params.get("product_id"));
    				record = new YcproductdictDao().getByName(tpMap);
    				params.put("product_id", record.get("product_id"));
    				tpMap.remove("pro_name");
    			}
			}
//			System.out.println(JSON.toJSONString(params, true));
			new YcprojectupgradelogDao().update(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			disconnect();
		}
	}
	
    public Map<String, Object> getupatemg(Map<String, Object> params) throws Exception{
    	try {
			connect("hospital_common");
			return new YcprojectupgradelogDao().getupatemg(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			disconnect();
		}
	}
	
	//判断字符串是否由汉字组成
	public boolean checkname(Object tp) {
		String name = (String)tp;
		int n = 0;
		for (int i = 0; i < name.length(); i++) {
			n=(int)name.charAt(i);
			if (!(19968 <= n && n < 40869)) {
				return false;
			}
		}
		return true;
	}
	
	
}
