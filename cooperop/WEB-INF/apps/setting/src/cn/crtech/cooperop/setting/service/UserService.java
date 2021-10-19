package cn.crtech.cooperop.setting.service;

import java.util.List;
import java.util.Map;

import cn.crtech.choho.authresource.bean.License;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CRPasswordEncrypt;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.setting.dao.UserDao;

public class UserService extends BaseService{
	public Result queryByDep(Map<String, Object> params) throws Exception {
		try {
			connect("base");
			UserDao dd = new UserDao();
			if(!CommonFun.isNe(params.get("selected"))){
				List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("selected");
				String[] selectedpersons = new String[list.size()];
				for(int i =0; i<list.size();i++){
					selectedpersons[i] = list.get(i).get("system_user_id").toString();
				}
				params.put("selectedpersons", selectedpersons);
			}
			return dd.queryByDep(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			UserDao dd = new UserDao();
			return dd.query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result queryV(Map<String, Object> params) throws Exception {
		try {
			connect();
			UserDao dd = new UserDao();
			return dd.queryV(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect();
			UserDao dd = new UserDao();
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
			UserDao dd = new UserDao();
			if("1".equals(params.get("state"))){
				Result r = dd.queryUserCount(params);
				String user_num = "9999";//License.getParams().get("user_num");
				if(CommonFun.isNe(user_num)){
					throw new Exception("没有人员添加权限，请联系管理员购买【人员管理】的权限");
				}
				if(r != null && r.getResultset().size() >= Integer.parseInt(user_num)){
					throw new Exception("您购买的人员账号管理数量为："+user_num + "，如果需要管理更多登陆账号，请联系管理员进行购买！");
				}
			}
			/*params.put("state", 1);*/
			if(!CommonFun.isNe(params.get("password"))){
				params.put("password", CRPasswordEncrypt.Encryptstring((String)params.remove("password")));
			}else{
				params.put("password", CRPasswordEncrypt.Encryptstring(SystemConfig.getSystemConfigValue("global", "password", "000000")));
			}
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
			UserDao dd = new UserDao();
			if("1".equals(params.get("state"))){
				Result r = dd.queryUserCount(params);
				String user_num = "999";//License.getParams().get("user_num");
				if(CommonFun.isNe(user_num)){
					throw new Exception("没有人员添加权限，请联系管理员购买【人员管理】的权限");
				}
				if(r != null && r.getResultset().size() >= Integer.parseInt(user_num)){
					throw new Exception("您购买的人员账号管理数量为："+user_num + "，如果需要管理更多登陆账号，请联系管理员进行购买！");
				}
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
			UserDao dd = new UserDao();
			int i = dd.delete(params);
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public int resetpwd(Map<String, Object> params) throws Exception {
		try {
			connect();
			UserDao dd = new UserDao();
			params.put("password", CRPasswordEncrypt.Encryptstring(SystemConfig.getSystemConfigValue("global", "password", "000000")));
			int i = dd.update(params);
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
}
