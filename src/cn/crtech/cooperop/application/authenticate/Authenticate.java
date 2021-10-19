package cn.crtech.cooperop.application.authenticate;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.application.bean.Account;
import cn.crtech.cooperop.application.dao.UserDao;
import cn.crtech.cooperop.bus.license.License;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.session.Session;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.LocalThreadMap;
import cn.crtech.cooperop.bus.util.MD5;

public class Authenticate {

	public static Map<String, Object> getAction(String pageid) {
		try {
			String method = pageid.substring(pageid.lastIndexOf('.') + 1);
			pageid = pageid.substring(0, pageid.lastIndexOf('.'));
			String moduleName = pageid.indexOf('.') < 0 ? pageid : pageid.substring(0, pageid.indexOf('.'));
			pageid = pageid.indexOf('.') < 0 ? pageid : pageid.substring(pageid.indexOf('.') + 1);
			String className = pageid.substring(pageid.lastIndexOf('.') + 1);
			className = CommonFun.capitalize(className);
			String packages = "";
			if (pageid.lastIndexOf('.') > 0) {
				packages = pageid.substring(0, pageid.lastIndexOf('.'));
			}

			String rule = GlobalVar.getSystemProperty("action.access.rule");
			String custom_rule = null;
			String clazz = rule.replace("@[MODULE]", moduleName);
			String custom_clazz = null;
			if ("".equals(packages)) {
				clazz = clazz.replace("@[PACKAGE].", "");
			} else {
				clazz = clazz.replace("@[PACKAGE]", packages);
			}
			clazz = clazz.replace("@[CLASS]", className);
			String custom_id = GlobalVar.getSystemProperty("custom.id");
			if(!CommonFun.isNe(custom_id)){
				custom_rule = GlobalVar.getSystemProperty("action.custom.rule", "");
				custom_rule = custom_rule.replace("@[CUSTOM]", custom_id);
				custom_clazz = custom_rule.replace("@[MODULE]", moduleName);
				if ("".equals(packages)) {
					custom_clazz = custom_clazz.replace("@[PACKAGE].", "");
				} else {
					custom_clazz = custom_clazz.replace("@[PACKAGE]", packages);
				}
				custom_clazz = custom_clazz.replace("@[CLASS]", className);
			}
			Class<?> c = null;
			Method m = null;
			if(CommonFun.isNe(custom_id)){
				try {
					c = Class.forName(clazz);
					m = c.getDeclaredMethod(method, Map.class);
				} catch (Exception ex) {
					log.warning(clazz + "." + method + " not found, use view absolute.");
				}
			}else{
				try {
					c = Class.forName(custom_clazz);
					m = c.getDeclaredMethod(method, Map.class);
				} catch (Exception ex) {
					try {
						c = Class.forName(clazz);
						m = c.getDeclaredMethod(method, Map.class);
					} catch (Exception ex1) {
						log.warning(clazz + "." + method + " not found, use view absolute.");
					}
				}
			}
			if (m != null) {
				Map<String, Object> rtn = new HashMap<String, Object>();
				rtn.put("class", c);
				rtn.put("method", m);
				return rtn;
			} else {
				return null;
			}
		} catch (Exception ex) {
			log.error(ex);
			return null;
		}
	}

	public static boolean checkRequest(String pageid, String key, String sn) {
		String v = MD5.md5(pageid + GlobalVar.getLicense().getPassword() + key);
		return v.equals(sn);
	}

	public static String createURLSN(String pageid, String key) {
		return MD5.md5(pageid + GlobalVar.getLicense().getPassword() + key);
	}

	public static int checkRight(String pageid, Account user) {
		Map<String, Object> action = getAction(pageid);

		// 产品授权过期验证
		if (License.check(pageid) == 0){
			return -2;
		}

		if (action == null) {
			return 1;
		}

		Class<?> c = (Class<?>) action.get("class");
		Method m = (Method) action.get("method");

		// 无需校验是否登录
		DisLoggedIn dli = c.getAnnotation(DisLoggedIn.class);
		if (dli != null) {
			return 1;
		}
		dli = m.getAnnotation(DisLoggedIn.class);
		if (dli != null) {
			return 1;
		}
		if (user == null) {
			return -1;
		}
		// 无需校验是否授权
		DisValidPermission dvp = c.getAnnotation(DisValidPermission.class);
		if (dvp != null && user != null) {
			return 1;
		}
		dvp = m.getAnnotation(DisValidPermission.class);
		if (dvp != null && user != null) {
			return 1;
		}

		return user.checkRight(pageid);
	}

	public static String getRuleDepsString() {
		Session session = Session.getSession();
		Account account = (Account) session.get("userinfo");
		String pageid = (String) LocalThreadMap.get("pageid");

		boolean authenticate = true;

		Map<String, Object> action = getAction(pageid);

		if (action != null) {
			Class<?> c = (Class<?>) action.get("class");
			Method m = (Method) action.get("method");

			// 无需校验是否登录
			DisLoggedIn dli = c.getAnnotation(DisLoggedIn.class);
			if (!authenticate) {
				if (dli != null) {
					authenticate = false;
				}
			}
			if (!authenticate) {
				dli = m.getAnnotation(DisLoggedIn.class);
				if (dli != null) {
					authenticate = false;
				}
			}

			if (!authenticate) {
				DisValidPermission dvp = c.getAnnotation(DisValidPermission.class);
				if (dvp != null && account != null) {
					authenticate = false;
				}
				if (!authenticate) {
					dvp = m.getAnnotation(DisValidPermission.class);
					if (dvp != null && account != null) {
						authenticate = false;
					}
				}
			}
		}

		if (authenticate && account == null) {
			authenticate = false;
		}

		StringBuffer ruleSql = new StringBuffer();
		if (!authenticate) {
			ruleSql.append("(select distinct id as system_department_id from system_department)");
		} else if (account != null) {
			if (Account.TYPE_SUPER.equals(account.getType())) {
				ruleSql.append("(select distinct id as system_department_id from system_department)");
			} else {
				UserDao dao = new UserDao();
				Record re = null;
				try {
					//TODO: getUserPageRuleDeps
					re = dao.getUserPageRuleDeps(account, pageid);
				} catch (Exception e) {
					log.error(e);
				}
				// ruleSql.append("(select distinct sr.base_dep_code as base_dep_code                    ");
				// ruleSql.append("  from system_rule sr                                                 ");
				// ruleSql.append(" where sr.system_user_id = '" + account.getId() + "'                  ");
				// ruleSql.append("   and sr.system_role_id in (" + (re == null ? "''" : re.getString("system_role_id")) + ")) ");
				ruleSql.append("(select column_value as base_dep_code   ");
				ruleSql.append("  from table(split('" + (re == null ? "''" : re.getString("base_dep_code")) + "', ',')))");
			}
		} else {
			ruleSql.append("(select distinct id as system_department_id from system_department where 1 != 1)");
		}
		return ruleSql.toString();
	}

}
