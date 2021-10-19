package cn.crtech.cooperop.hospital_common.service.patientdata;

import java.util.Date;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import cn.crtech.cooperop.application.bean.Account;
import cn.crtech.cooperop.application.dao.UserDao;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.patientdata.BillDao;

public class BillService extends BaseService{
	
	public Map<String, Object> is_permit(Map<String, Object> params) throws Exception {
		try {
			boolean check = false;
			Record p = new Record(params);
			Map<String, Object> ret = new HashedMap();
			connect("base");
			UserDao ud = new UserDao();
			Record user = ud.login(null, p.getString("userid"), p.getString("password"));
			if(user != null) {
				user.remove("password");
			}
			if(user != null && !user.get("no").equals(user().getNo())) {
				check = true;
				ret.put("user_sub", user);
			}
			ret.put("check", check);
			return ret;
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
	
	public String visit_insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Record map = new Record();
			if(!CommonFun.isNe(params.get("user_sub"))) {
				Map<String, Object> user_sub = CommonFun.json2Object((String)params.get("user_sub"), Map.class);
				map.put("userid_second", user_sub.get("no"));
				map.put("username_second", user_sub.get("name"));
			}
			Account user = user();
			map.put("patient_id", params.get("patient_id"));
			map.put("visit_id", params.get("visit_id"));
			map.put("userid", user.getNo());
			map.put("username", user.getName());
			map.put("show_start_datetime", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			map.put("beizhu", "1".equals(params.get("type"))?"查看患者详情费用信息":"查看医保结算费用信息");
			return new BillDao().insert(map);
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
	
	public int update_log(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.put("show_end_datetime", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			return new BillDao().update(params);
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
}
