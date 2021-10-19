package cn.crtech.precheck;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.application.bean.Account;
import cn.crtech.cooperop.bus.cache.MemoryCache;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.LocalThreadMap;

public class Session implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -8232391469819633205L;

	public static final String SIDNAME = "_CRSID";
	private static final String prix = "session.";
	private static destorier d = null;
	private String sessionid;
	private Map<String, Object> map;
	private long lastTime;
	private String ip;
	private String brower;
	private String brower_version;
	private int is_mobile;
	private int is_app;

	public static void init() {
		if (d == null) {
			d = new destorier();
			d.start();
		}
		/*File folder = new File(GlobalVar.getSystemProperty("session.filepath"));
		if (!folder.exists()) {
			folder.mkdirs();
		}
		File[] sessionfiles = folder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile();
			}
		});

		if (sessionfiles != null) {
			for (File sessionfile : sessionfiles) {
				Session s = getSession(sessionfile.getName());
				if (s != null) {
					MemoryCache.put(prix, s.sessionid, s);
				}
			}
		}*/
		log.release("init session success.");
	}

	public static Session getSession(String sessionid) {
		if (!MemoryCache.containsKey(prix, sessionid)) {
			return null;
		} else {
			return (Session)MemoryCache.get(prix, sessionid);
		}
		/*File folder = new File(GlobalVar.getSystemProperty("session.filepath"));
		if (!folder.exists()) {
			folder.mkdirs();
		}
		if (CommonFun.isNe(sessionid)) return null;
		File sessionfile = new File(folder ,sessionid);
		if (sessionfile.exists()) {
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			boolean deletefile = false;
			try {
				fis = new FileInputStream(sessionfile);
				ois = new ObjectInputStream(fis);
				Session s = (Session) ois.readObject();
				MemoryCache.put(prix, s.sessionid, s);
				return s;
			} catch (Exception ex) {
				log.error("读取会话缓存文件失败", ex);
				deletefile = true;
			} finally {
				if (ois != null) {
					try {
						ois.close();
					} catch (IOException e) {
					}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
					}
				}
				if (deletefile) {
					log.info("删除读取失败的缓存文件");
					sessionfile.delete();
				}
			}
		}
		return null;*/
	}

	private static void refreshSession(Session session) {/*
		File folder = new File(GlobalVar.getSystemProperty("session.filepath"));
		if (!folder.exists()) {
			folder.mkdirs();
		}
		if (session == null) return;
		File sessionfile = new File(folder ,session.sessionid);
		if (sessionfile.exists()) {
			sessionfile.delete();
		}
		FileOutputStream fis = null;
		ObjectOutputStream ois = null;
		try {
			fis = new FileOutputStream(sessionfile);
			ois = new ObjectOutputStream(fis);
			ois.writeObject(session);
		} catch (Exception ex) {
			//log.error("读取会话缓存文件失败", ex);
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
		}
	*/}

	private static void deleteSession(Session session) {
		File folder = new File(GlobalVar.getSystemProperty("session.filepath"));
		if (!folder.exists()) {
			folder.mkdirs();
		}
		if (session == null) return;
		File sessionfile = new File(folder ,session.sessionid);
		if (sessionfile.exists()) {
			sessionfile.delete();
		}
	}

	public static Session getSession(HttpServletRequest req, HttpServletResponse resp) {
		boolean is_app = !CommonFun.isNe(req.getParameter(GlobalVar.getSystemProperty("app.flag", "_IA_")));
		String sessionid = req.getParameter(SIDNAME);
		if (req.getCookies() != null) {
			for (Cookie cookie : req.getCookies()) {
				if(SIDNAME.equals(cookie.getName())) {
					if (CommonFun.isNe(sessionid)) {
						sessionid = cookie.getValue();
					}
					break;
				}
			}
		}
		if (CommonFun.isNe(sessionid) || "undefined".equals(sessionid)) {
			sessionid = CommonFun.getITEMID();
		}

		if (resp != null) {
			Cookie cookie = new Cookie(SIDNAME, sessionid);
			cookie.setPath("/");
			if (is_app) {
				int session_time = Integer.parseInt(GlobalVar.getSystemProperty("session.maxage", "1800"));
				cookie.setMaxAge(session_time);
			} else {
				cookie.setMaxAge(-1);
			}
			resp.addCookie(cookie);
		}

		//System.out.println("==========================>sessionid: " + sessionid + ", url: "+ req.getRequestURL());

		Session session;
		if (!MemoryCache.containsKey(prix, sessionid)) {
			session = new Session();
			session.sessionid =  sessionid;
			session.map = new HashMap<String, Object>();
		} else {
			session = (Session)MemoryCache.get(prix, sessionid);
		}
		Map<String, String> userAgent = CommonFun.getUserAgent(req);
		session.lastTime = System.currentTimeMillis();
		session.ip = CommonFun.getIp(req);
		session.brower = userAgent.get("type");
		session.brower_version = userAgent.get("version");
		MemoryCache.put(prix, session.sessionid, session);

		refreshSession(session);

		return session;
	}

	public static void destorySession(HttpServletRequest req, HttpServletResponse resp) {
		boolean has_cookie = false;
		String sessionid = req.getParameter(SIDNAME);
		if (req.getCookies() != null) {
			for (Cookie cookie : req.getCookies()) {
				if(SIDNAME.equals(cookie.getName())) {
					if (CommonFun.isNe(sessionid)) {
						sessionid = cookie.getValue();
					}
					break;
				}
			}
		}
		if (!CommonFun.isNe(sessionid)) {
			Session session = (Session)MemoryCache.get(prix, sessionid);
			session.map = new HashMap<String,Object>();
			MemoryCache.put(prix, session.sessionid, session);
		}
	}

	public static Session getSession() {
		String sessionid = (String) LocalThreadMap.get("sessionId");
		if (!MemoryCache.containsKey(prix, sessionid)) {
			return null;
		} else {
			Session session = (Session)MemoryCache.get(prix, sessionid);
			session.lastTime = System.currentTimeMillis();
			MemoryCache.put(prix, session.sessionid, session);
			refreshSession(session);
			return session;
		}
	}

	public String getId() {
		return sessionid;
	}

	@SuppressWarnings("unchecked")
	public boolean containsKey(String key) {
		return map.containsKey(key);
	}

	@SuppressWarnings("unchecked")
	public Object get(String key) {
		return map.get(key);
	}

	@SuppressWarnings("unchecked")
	public void put(String key, Object value) {
		map.put(key, value);
		lastTime = System.currentTimeMillis();
		refreshSession(this);
		MemoryCache.put(prix, sessionid, this);

	}

	@SuppressWarnings("unchecked")
	public Object remove(String key) {
		Object c =map.remove(key);
		lastTime = System.currentTimeMillis();
		refreshSession(this);
		MemoryCache.put(prix, sessionid, this);
		return c;
	}

	@SuppressWarnings("unchecked")
	public void clear() {
		map.clear();
		lastTime = System.currentTimeMillis();
		refreshSession(this);
		MemoryCache.put(prix, sessionid, this);
	}

	public String toString() {
		StringBuffer t = new StringBuffer();
		t.append("{id:" + sessionid + ", ");
		t.append("lastTime:" + lastTime + ", ");
		t.append("map:" + map.toString() + "}");
		return t.toString();
	}

	public String getIP(){
		return this.ip;
	}

	private static class destorier extends Thread {
		private static boolean running = true;

		protected static void terminal() {
			running = false;
		}

		@Override
		public void run() {
			while (running) {
				try {
					sleep(60000);
				} catch (InterruptedException e) {
				}

				if (!running) {
					continue;
				}

				long session_time = Long.parseLong(GlobalVar.getSystemProperty("session.maxage", "1800"));
				if (session_time >= 0) {
					Set<String> sessionids = MemoryCache.keySet(prix);
					for (String sessionid : sessionids) {
						try {
							Session session = (Session) MemoryCache.get(prix, sessionid);
							if (session == null) {
								MemoryCache.remove(prix, sessionid);
								continue;
							}
							if (session.lastTime < System.currentTimeMillis() - session_time * 1000) {
								deleteSession(session);
								session = null;
								MemoryCache.remove(prix, sessionid);
							}
						} catch (Exception e) {
							log.error(e);
						}
					}
				}
			}
		}
	}
}
