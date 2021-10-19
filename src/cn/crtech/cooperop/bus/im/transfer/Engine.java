package cn.crtech.cooperop.bus.im.transfer;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.bus.Bus;
import cn.crtech.cooperop.bus.im.Client;
import cn.crtech.cooperop.bus.im.JavaScriptServlet;
import cn.crtech.cooperop.bus.im.action.IMAction;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.im.bean.Department;
import cn.crtech.cooperop.bus.im.bean.Group;
import cn.crtech.cooperop.bus.im.bean.Orgnization;
import cn.crtech.cooperop.bus.im.bean.User;
import cn.crtech.cooperop.bus.im.service.IMBaseService;
import cn.crtech.cooperop.bus.log.log;

public class Engine {
	public final static String CLIENT_WEBBROWSER = "Browser";
	public final static String CLIENT_MOBILE = "Phone";
	public final static String CLIENT_PAD = "Pad";
	public final static String CLIENT_TV = "TV";

	private static Properties config = null;

	private static String appid = null;
	private static String appkey = null;
	private static Client busiclass = null;

	private static boolean ready = false;

	public static boolean isReady() {
		return ready;
	}

	public static void init(String appId, String appKey, String busiClass) throws Exception {
		appid = appId;
		appkey = appKey;
		busiclass = (Client) Engine.class.getClassLoader().loadClass(busiClass).newInstance();
		while (config == null) {
			if(Bus.finish_GlobalVar) {
				try {
					config = new Properties();
					config.load(JavaScriptServlet.class.getResourceAsStream("config.properties"));
					String charset = config.getProperty("charset");
					if (charset != null) {
						for (Enumeration<?> e = config.propertyNames(); e.hasMoreElements();) {
							String key = (String) e.nextElement();
							String str = config.getProperty(key);
							if (str != null && !str.equals(new String(str.getBytes(charset), charset))) {
								config.setProperty(key, new String(config.getProperty(key).getBytes("ISO-8859-1"), charset));
							}
						}
					}
					config.put("mac", CommonFun.getMACAddress());
					config.put("app.id", appid);
					config.put("app.key", appkey);
					ready = true;
				} catch (Exception e) {
					e.printStackTrace();
					config = null;
				}
				break;
			}
			Thread.sleep(2000);
		}
		new configRefresher().start();
	}

	public static void destroy() {
	}

	public static String getProperty(String key) {
		return config.getProperty(key);
	}

	public static String generateToken(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (busiclass.connectCheck(req, resp)) {
			String userID = req.getParameter("uid");
			String ClientType = req.getParameter("ct");

			IMAction action = new IMAction();
			return action.login(userID, ClientType);
		} else {
			throw new Exception("get imjsdk user valid failed.");
		}
	}

	public static void refreshOrgnization() {
		List<Orgnization> orgs = busiclass.listOrgnization();
		IMBaseService ibs = new IMBaseService();
		try {
			ibs.refreshOrgnization(orgs);
		} catch (Exception e) {
			log.error("chohoim refresh orgnization list error. " + e.getMessage(), e);
		}
	}

	public static void refreshDepartment() {
		List<Department> deps = busiclass.listDepartment();
		IMBaseService ibs = new IMBaseService();
		try {
			ibs.refreshDepartment(deps);
		} catch (Exception e) {
			log.error("chohoim refresh department list error. " + e.getMessage(), e);
		}
	}

	public static void refreshUser() {
		List<User> users = busiclass.listUser();
		IMBaseService ibs = new IMBaseService();
		try {
			ibs.refreshUser(users);
		} catch (Exception e) {
			log.error("chohoim refresh user list error. " + e.getMessage(), e);
		}
	}

	public static void addOtherAppUser(User user) {
		busiclass.addOtherAppUser(user);
	}

	public static void updateOtherAppUser(User user) {
		busiclass.updateOtherAppUser(user);
	}

	public static void deleteOtherAppUser(User user) {
		busiclass.deleteOtherAppUser(user);
	}

	public static void addGroup(Group group) {
		busiclass.addGroup(group);
	}

	public static void updateGroup(Group group) {
		busiclass.updateGroup(group);
	}

	public static void deleteGroup(Group group) {
		busiclass.deleteGroup(group);
	}

	public static void addOrgnization(Orgnization orgnization) {
		List<Orgnization> orgs = new ArrayList<Orgnization>();
		orgs.add(orgnization);
		IMBaseService ibs = new IMBaseService();;
		try {
			ibs.refreshOrgnization(orgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateOrgnization(Orgnization orgnization) {
		addOrgnization(orgnization);
	}

	public static void deleteOrgnization(Orgnization orgnization) {
		IMBaseService ibs = new IMBaseService();;
		try {
			ibs.deleteOrgnization(orgnization);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addDepartment(Department department) {
		List<Department> deps = new ArrayList<Department>();
		deps.add(department);
		IMBaseService ibs = new IMBaseService();;
		try {
			ibs.refreshDepartment(deps);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateDepartment(Department department) {
		addDepartment(department);

	}

	public static void deleteDepartment(Department department) {
		IMBaseService ibs = new IMBaseService();;
		try {
			ibs.deleteDepartment(department);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void updateUser(User user) {
		addUser(user);

	}

	public static void addUser(User user) {
		List<User> us = new ArrayList<User>();
		us.add(user);
		IMBaseService ibs = new IMBaseService();;
		try {
			ibs.refreshUser(us);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteUser(User user) {
		IMBaseService ibs = new IMBaseService();;
		try {
			ibs.deleteUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static class configRefresher extends Thread {
		@Override
		public void run() {
			//while (config == null) {
				try {
					sleep(5000);
				} catch (InterruptedException e1) {
				}
				try {
					/*loadProperties();
					config.put("app.id", appid);
					config.put("app.key", appkey);*/
					/*while (config == null) {
						if(Bus.finish_GlobalVar) {
							try {
								config = new Properties();
								config.load(JavaScriptServlet.class.getResourceAsStream("config.properties"));
								String charset = config.getProperty("charset");
								if (charset != null) {
									for (Enumeration<?> e = config.propertyNames(); e.hasMoreElements();) {
										String key = (String) e.nextElement();
										String str = config.getProperty(key);
										if (str != null && !str.equals(new String(str.getBytes(charset), charset))) {
											config.setProperty(key, new String(config.getProperty(key).getBytes("ISO-8859-1"), charset));
										}
									}
								}
								config.put("mac", CommonFun.getMACAddress());
								config.put("app.id", appid);
								config.put("app.key", appkey);
							} catch (Exception e) {
								e.printStackTrace();
								config = null;
							}
							break;
						}
						Thread.sleep(2000);
					}*/

					refreshOrgnization();
					refreshDepartment();
					refreshUser();
					
					/*
					 * Part[] parts = new Part[] { new StringPart("cmd",
					 * "register") }; String result = httpPost("auth", parts);
					 * 
					 * @SuppressWarnings("unchecked") Map<String, Object> rtn =
					 * (Map<String, Object>) CommonFun.json2Object(result,
					 * Map.class);
					 * 
					 * Iterator<String> keys = rtn.keySet().iterator(); while
					 * (keys.hasNext()) { String key = keys.next(); if
					 * (!CommonFun.isNe(rtn.get(key))) { config.put(key,
					 * rtn.get(key)); } }
					 */
				} catch (Exception e) {
					e.printStackTrace();
					//config = null;
				}
			//}
			ready = true;
		}

		private void loadProperties() throws Exception {
			config = new Properties();
			config.load(JavaScriptServlet.class.getResourceAsStream("config.properties"));
			String charset = config.getProperty("charset");
			if (charset != null) {
				for (Enumeration<?> e = config.propertyNames(); e.hasMoreElements();) {
					String key = (String) e.nextElement();
					String str = config.getProperty(key);
					if (str != null && !str.equals(new String(str.getBytes(charset), charset))) {
						config.setProperty(key, new String(config.getProperty(key).getBytes("ISO-8859-1"), charset));
					}
				}
			}
			config.put("mac", CommonFun.getMACAddress());
		}

	}

}
