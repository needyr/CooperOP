package cn.crtech.cooperop.bus.weixin.window;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.session.Session;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.LocalThreadMap;
import cn.crtech.cooperop.bus.weixin.WeiXin;
import cn.crtech.cooperop.bus.weixin.WeiXinCore;
import cn.crtech.cooperop.bus.log.log;

@WebServlet(loadOnStartup=2, urlPatterns={"/wxlogin", "/wxauth"})
public class Window extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9172987862073298043L;

	
	private static Map<String, Record> states = new HashMap<String, Record>();
	
	private static final long state_life = 1000 * 60 * 5;
	
	static {
		new clearMemory().start();
	}

	private static class clearMemory extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					try {
						sleep(60 * 1000);
					} catch (InterruptedException e) {
					}
					
					Iterator<String> keys = states.keySet().iterator();
					
					while (keys.hasNext()) {
						String key = keys.next();
						
						if (System.currentTimeMillis() - states.get(key).getLong("time") >= state_life) {
							keys.remove();
							continue;
						}
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Session session = Session.getSession(req, resp);
		if (req.getRequestURI().equals("/wxauth")) {
			String code = req.getParameter("code");
			String state = req.getParameter("state");
			//由于连续跳转两次， Record sta = states.remove(state);
			Record sta = states.get(state);
			
			if (sta != null) {
				Record userinfo = null;
				try {
					userinfo = WeiXinCore.addUserAccessToken(code);
				} catch (Exception e) {
				}
				session.put("wxinfo", userinfo);
				//尝试登录已绑定用户
				try {
					LocalThreadMap.put("sessionId", session.getId());
					LocalThreadMap.put("pageid", WeiXin.getLoginAction());
					Map<String, Object> map = cn.crtech.cooperop.application.authenticate.Authenticate.getAction(WeiXin.getLoginAction());
					cn.crtech.cooperop.bus.engine.Engine.callAction((Class)map.get("class"), (Method)map.get("method"), new Record(userinfo));
				} catch (Exception e) {
					log.error("Weixin login failed.", e);
				} finally {
					LocalThreadMap.clear();
				}
				resp.sendRedirect(sta.getString("url"));
			} else {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
		} else if (req.getRequestURI().equals("/wxlogin")) {
			if (!WeiXin.checkSession(session)) {
				String url = req.getRequestURL().toString().replaceAll("/wxlogin", "/wxauth");
				// nginx转发的https
				if ("https".equalsIgnoreCase(req.getHeader("scheme")) && url.startsWith("http://")) {
					url = url.replace("http://", "https://");
				}
				Record state = new Record();
				state.put("id", CommonFun.getITEMID());
				state.put("url", req.getParameter("rd"));
				state.put("time", System.currentTimeMillis());
				states.put(state.getString("id"), state);
				
				try {
					WeiXinCore.login(state.getString("id"), url, resp);
				} catch (Exception e) {
					throw new ServletException(e);
				}
			} else {
				resp.sendRedirect(req.getParameter("rd"));
			}
		}
	}
	
	@Override
	public void destroy() {
		WeiXinCore.destory();
		super.destroy();
	}


}
