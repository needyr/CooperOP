package cn.crtech.cooperop.bus.mvc.view;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import cn.crtech.cooperop.application.bean.Account;
import cn.crtech.cooperop.application.service.UserService;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.session.Session;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.LocalThreadMap;
import cn.crtech.cooperop.bus.util.spring.HtmlUtils;
import cn.crtech.cooperop.bus.ws.server.Engine;

public class Page extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1230314682232560430L;

	protected String pageid;

	protected String title;
	protected boolean ismodal = false;
	protected boolean ismobile = false;
	protected boolean disloggedin = false;
	protected boolean dispermission = false;
	protected String schemeid;
	protected String flag;
	protected String type;
	protected String themetype;
	protected int modalwidth = 400;
	protected int modalheight = 300;
	private String rm_url;
	public boolean isIsmobile() {
		return ismobile;
	}

	public void setIsmobile(boolean ismobile) {
		this.ismobile = ismobile;
	}

	public String getThemetype() {
		return themetype;
	}

	public void setThemetype(String themetype) {
		this.themetype = themetype;
	}

	public String getSchemeid() {
		return schemeid;
	}

	public void setSchemeid(String schemeid) {
		this.schemeid = schemeid;
	}

	public String getFlag() {
		return flag;
	}

	public String getType() {
		return type;
	}

	public int getModalwidth() {
		return modalwidth;
	}

	public int getModalheight() {
		return modalheight;
	}

	public void setPageid(String pageid) {
		this.pageid = pageid;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setModalwidth(int modalwidth) {
		this.modalwidth = modalwidth;
	}

	public void setModalheight(int modalheight) {
		this.modalheight = modalheight;
	}

	/**
	 * Prepare for evaluation of the body.
	 * 
	 * @throws JspException
	 *             if an error occurred while processing this tag
	 * @todo Implement this javax.servlet.jsp.tagext.BodyTag method
	 */
	@Override
	public void doInitBody() throws JspException {
		super.doInitBody();
	}

	/**
	 * Process the start tag for this instance.
	 * 
	 * @return EVAL_BODY_INCLUDE if the tag wants to process body, SKIP_BODY if
	 *         it does not want to process it.
	 * @throws JspException
	 *             if an error occurred while processing this tag
	 * @todo Implement this javax.servlet.jsp.tagext.Tag method
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
		HttpServletResponse resp = (HttpServletResponse) pageContext.getResponse();
		
		// 对url参数进行转义 begin
		Map<String, Object> pageParam = requestMap(req);
		Set<String> keys = pageParam.keySet();
		for (String key : keys) {
			if (pageParam.get(key) != null) {
				pageParam.put(key, HtmlUtils.htmlEscape(pageParam.get(key).toString()));
			}
		}
		pageContext.setAttribute("pageParam", pageParam);
		// 对url参数进行转义 end
		ismodal = (!CommonFun.isNe(pageParam.get("ismodal")) ? "true".equals(pageParam.get("ismodal")) : ismodal);

		
		int i = super.doStartTag();


		String res = req.getServletPath();
		String rule = GlobalVar.getSystemProperty("view.html.access.rule");
		res = res.replaceAll(rule.substring(0, rule.indexOf("@[MODULE]")), "");
		res = res.replaceAll(rule.substring(rule.indexOf("@[MODULE]") + "@[MODULE]".length(), rule.indexOf("@[PAGE]")),
				".");
		pageid = res.replaceAll(rule.substring(rule.indexOf("@[PAGE]") + "@[PAGE]".length()), "");
		pageid = pageid.replaceAll("/", ".");
		if(!CommonFun.isNe(GlobalVar.getSystemProperty("custom.id"))){
			pageid = pageid.replace(".custom." + GlobalVar.getSystemProperty("custom.id", ""), "");
		}
		Session session = Session.getSession(req, resp);
		if(ismobile){
			session.put("is_mobile_login", "Y");
		}
		LocalThreadMap.put("sessionId", session.getId());
		LocalThreadMap.put("pageid", pageid);

		Account user = (Account) session.get("userinfo");
		if (!disloggedin) {
			if (user == null) {
				try {
					String query = req.getQueryString();
					StringBuffer url = req.getRequestURL();
					String lastAccessPage = url + "?" + query;
					if (url.toString().endsWith("/e")) {
						session.put("lastAccessPage", lastAccessPage);
					}
					resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
					return (SKIP_PAGE);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (!dispermission) {
				if (user.checkRight(pageid) <= 0) {
					try {
						resp.sendError(HttpServletResponse.SC_FORBIDDEN);
						return (SKIP_PAGE);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		Map<String, Object> a = null;
		Object returnObj = req.getAttribute("$return");
		if (returnObj instanceof Map) {
			a = (Map<String, Object>) returnObj;
		}
		if (a == null) {
			a = new HashMap<String, Object>();
		}

		Calendar date = Calendar.getInstance();
		Map<String, Object> now = new HashMap<String, Object>();
		now.put("date", new SimpleDateFormat("yyyy-MM-dd").format(date.getTime()));
		now.put("year", new SimpleDateFormat("yyyy").format(date.getTime()));
		now.put("month", new SimpleDateFormat("MM").format(date.getTime()));
		now.put("day", new SimpleDateFormat("dd").format(date.getTime()));
		now.put("day4week", date.get(Calendar.DAY_OF_WEEK));
		now.put("time", new SimpleDateFormat("HH:mm:ss").format(date.getTime()));
		now.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date.getTime()));
		now.put("datetimeM", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date.getTime()));
		now.put("millisecond", date.getTime().getTime());
		a.put("now", now);

		a.put("system_title", SystemConfig.getSystemConfigValue("global", "system_title"));
		a.put("hotline_phone", SystemConfig.getSystemConfigValue("global", "hotline_phone"));
		a.put("copyright", SystemConfig.getSystemConfigValue("global", "copyright"));
		if (!CommonFun.isNe(pageid)) {
			String module = pageid.substring(0, pageid.indexOf('.'));
			a.put("module", module);
			a.put("system_title", SystemConfig.getSystemConfigValue(module,
					"system_title", SystemConfig.getSystemConfigValue("global", "system_title")));
			a.put("hotline_phone", SystemConfig.getSystemConfigValue(module, "hotline_phone",
					SystemConfig.getSystemConfigValue("global", "hotline_phone")));
			a.put("copyright", SystemConfig.getSystemConfigValue(module, "copyright",
					SystemConfig.getSystemConfigValue("global", "copyright")));
			a.put("service_time", SystemConfig.getSystemConfigValue(module,
					"service_time", SystemConfig.getSystemConfigValue("global", "service_time")));
			a.put("icp_no", SystemConfig.getSystemConfigValue(module, "icp_no",
					SystemConfig.getSystemConfigValue("global", "icp_no")));
		}
		if(GlobalVar.getSystemProperty("rm_url_port", "").equals(req.getServerPort()+"")){
			rm_url = GlobalVar.getSystemProperty("rm_url", "");
		}else{
			rm_url = GlobalVar.getSystemProperty("rm_url_local", "");
		}
		Enumeration<String> headernames = req.getHeaderNames();
		Map<String, String> header = new HashMap<String, String>();
		while (headernames.hasMoreElements()) {
			String name = headernames.nextElement();
			try {
				header.put(name, URLDecoder.decode(req.getHeader(name), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
			}
		}
		a.put("httpHeader", header);
		a.put("contextpath", req.getContextPath());
		a.put("user", user);
		a.put("ismodal", ismodal);
		a.put("pageid", pageid);
		if (!CommonFun.isNe(pageid)) {
			a.put("module", pageid.substring(0, pageid.indexOf('.')));
		}
		a.put("refreshcycle", GlobalVar.getSystemProperty("task.refresh.cycle", "60000"));
		String welcomepage = GlobalVar.getSystemProperty("login.url", "index.jsp");
		if (user != null) {
			try {
				if (!CommonFun.isNe(user.getWelcomePage())) {
					welcomepage = user.getWelcomePage();
				}
				a.put("userinfo", CommonFun.object2Json(user));
				UserService us = new UserService();
				Result menus = us.getMenuTree(null);
				Result menuinfo = us.getMenu(req.getParameter("_pid_"));
				a.put("menus", menus.getResultset());
				a.put("menuinfo", menuinfo.getResultset());
				content.append(result2xml("menu", menus));
				content.append(result2xml("menuinfo", menuinfo));
			} catch (Exception e) {
				throw new JspException(e);
			}
		}
		if(CommonFun.isMoblie(req)){
			welcomepage = GlobalVar.getSystemProperty("login.app.url", "mobileIndex.jsp");
		}else if(CommonFun.isPad(req)){
			welcomepage = GlobalVar.getSystemProperty("login.pad.url", "padIndex.jsp");
		}
		a.put("welcomepage", welcomepage);
		a.put("userinfo", user == null ? "{}" : CommonFun.object2Json(user));


		Iterator<String> it = a.keySet().iterator();

		while (it.hasNext()) {
			String key = it.next();
			pageContext.setAttribute(key, a.get(key));
		}

		return EVAL_BODY_BUFFERED;
	}

	/**
	 * Process the end tag for this instance.
	 * 
	 * @return indication of whether to continue evaluating the JSP page.
	 * @throws JspException
	 *             if an error occurred while processing this tag
	 * @todo Implement this javax.servlet.jsp.tagext.Tag method
	 */
	@Override
	public int doEndTag() throws JspException {

		int rtn = super.doEndTag();
		if (rtn == SKIP_BODY) {
			return (EVAL_PAGE);
		}
		try {
			// log.debug(content.toString());
			Transform();
			LocalThreadMap.clear();
		} catch (Exception e) {
			throw new JspException(e);
		}

		return rtn;
	}

	/**
	 * Release state.
	 * 
	 * @see Tag#release
	 */

	@Override
	public void release() {
		title = null;
		ismodal = false;
		ismobile = false;
		disloggedin = false;
		dispermission = false;
		id = null;
		flag = null;
		type = null;
		modalwidth = 400;
		modalheight = 300;
		themetype = null;
		super.release();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPageid() {
		return pageid;
	}

	public boolean isDisloggedin() {
		return disloggedin;
	}

	public void setDisloggedin(boolean disloggedin) {
		this.disloggedin = disloggedin;
	}

	public boolean isIsmodal() {
		return ismodal;
	}

	public void setIsmodal(boolean ismodal) {
		this.ismodal = ismodal;
	}

	public boolean isDispermission() {
		return dispermission;
	}

	public void setDispermission(boolean dispermission) {
		this.dispermission = dispermission;
	}

	public static HashMap<String, Object> requestMap(HttpServletRequest request) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String[] values = request.getParameterValues(name);
			if (values != null && values.length == 1) {
				map.put(name, values[0]);
			} else {
				map.put(name, values);
			}
		}
		return map;
	}

	private void Transform() throws Exception {
		ByteArrayInputStream bis = null;
		HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
		Session session = Session.getSession();
		Account user = null;
		if (session != null) {
			user = (Account) session.get("userinfo");
		}
		String module = null;
		String errorpage = (String) req.getAttribute("javax.servlet.error.request_uri");
		if (CommonFun.isNe(errorpage) && !CommonFun.isNe(pageid)) {
			module = pageid.substring(0, pageid.indexOf('.'));
		} else {
			errorpage = errorpage.substring((cn.crtech.cooperop.bus.engine.Engine.getContextPath() + cn.crtech.cooperop.bus.engine.Engine.getServletPath()).length() + 1);
			module = errorpage.substring(0, errorpage.indexOf("/"));
		}
		try {
			String xslFileName = GlobalVar.getWorkPath() + "/" + GlobalVar.getSystemProperty("theme." + module + ".pc.path", GlobalVar.getSystemProperty("theme.pc.path"))
			+ "/views/view.xsl";
			if(CommonFun.isMoblie(req) || "app".equals(getThemetype())){
				//如果 session中有标识是手机登陆在使用  或者  当前page上面有标识是手机展示页面
				xslFileName = GlobalVar.getWorkPath() + "/" + GlobalVar.getSystemProperty("theme." + module + ".app.path", GlobalVar.getSystemProperty("theme.app.path"))
				+ "/views/view.xsl";
			}else if(CommonFun.isPad(req) || "pad".equals(getThemetype())){
				//如果 session中有标识是手机登陆在使用  或者  当前page上面有标识是手机展示页面
				xslFileName = GlobalVar.getWorkPath() + "/" + GlobalVar.getSystemProperty("theme." + module + ".pad.path", GlobalVar.getSystemProperty("theme.pad.path"))
				+ "/views/view.xsl";
			}
			bis = new ByteArrayInputStream(content.toString().getBytes());

			TransformerFactory tFac = TransformerFactory.newInstance();
			Source xslSource = new StreamSource(xslFileName);
			Transformer t = tFac.newTransformer(xslSource);
			t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			t.setOutputProperty(OutputKeys.METHOD, "html");
			t.setParameter("contextpath", req.getContextPath());
			t.setParameter("ismodal", ismodal);
			t.setParameter("rm_url", rm_url);
			t.setParameter("pageid", pageid);
			t.setParameter("system_title", SystemConfig.getSystemConfigValue("global", "system_title"));
			t.setParameter("copyright", SystemConfig.getSystemConfigValue("global", "copyright"));
			if (!CommonFun.isNe(pageid)) {
				t.setParameter("module", pageid.substring(0, pageid.indexOf('.')));
				t.setParameter("system_title",
						SystemConfig.getSystemConfigValue(pageid.substring(0, pageid.indexOf('.')), "system_title",
								SystemConfig.getSystemConfigValue("global", "system_title")));
				t.setParameter("copyright", SystemConfig.getSystemConfigValue(pageid.substring(0, pageid.indexOf('.')),
						"copyright", SystemConfig.getSystemConfigValue("global", "copyright")));
				t.setParameter("hotline_phone", SystemConfig.getSystemConfigValue(pageid.substring(0, pageid.indexOf('.')),
						"hotline_phone", SystemConfig.getSystemConfigValue("global", "hotline_phone")));
				t.setParameter("service_time", SystemConfig.getSystemConfigValue(pageid.substring(0, pageid.indexOf('.')),
						"service_time", SystemConfig.getSystemConfigValue("global", "service_time")));
				t.setParameter("icp_no", SystemConfig.getSystemConfigValue(pageid.substring(0, pageid.indexOf('.')),
						"icp_no", SystemConfig.getSystemConfigValue("global", "icp_no")));
			}
			t.setParameter("refreshcycle", GlobalVar.getSystemProperty("task.refresh.cycle", "60000"));
			//String welcomepage = GlobalVar.getSystemProperty(CommonFun.isMoblie(req) ?"login.app.url":"login.url", CommonFun.isMoblie(req) ? "mobileIndex.jsp" : "index.jsp");
			String welcomepage = GlobalVar.getSystemProperty("login.url", "index.jsp");
			if (user != null) {
				if (!CommonFun.isNe(user.getWelcomePage())) {
					welcomepage = user.getWelcomePage();
				}
				t.setParameter("user_supperUser", user.isSupperUser());
				t.setParameter("user_id", user.getId());
				t.setParameter("user_businessId", user.getBusinessId());
				t.setParameter("user_no", user.getNo());
				t.setParameter("user_name", user.getName());
				t.setParameter("user_passWord", user.getPassWord());
				t.setParameter("user_type", user.getType());
				t.setParameter("user_typeName", user.getTypeName());
				t.setParameter("user_baseDepName", user.getBaseDepName());
				t.setParameter("user_baseDepCode", user.getBaseDepCode());  //cooperop 部门id （账套做为顶级部门）
				t.setParameter("user_gender", user.getGender());
				t.setParameter("user_telephone", user.getTelephone());
				t.setParameter("user_email", user.getEmail());
				t.setParameter("user_mobile", user.getMobile());
				t.setParameter("user_jigid", user.getJigid());  //erp jig id  账套id
				t.setParameter("user_jigname", user.getJigname());  //erp jig name  账套名称
				t.setParameter("user_bmid", user.getBmid());  //erp bm id 单位id
				t.setParameter("user_roleNames", user.getRoleNames());
				t.setParameter("user_avatar", user.getAvatar());
				t.setParameter("user_position", user.getPosition());
				Iterator<String> it = user.getAttendantMap().keySet().iterator();
				while (it.hasNext()) {
					String key = it.next();
					t.setParameter("user_attendant_" + key, user.getAttendantMap().get(key));
				}
			}
			if(CommonFun.isMoblie(req)){
				welcomepage = GlobalVar.getSystemProperty("login.app.url", "mobileIndex.jsp");
			}
			t.setParameter("welcomepage", welcomepage);
			Map<String, Object> p = CommonFun.requestMap(welcomepage);
			Iterator<String> it = p.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				t.setParameter(key, p.get(key));
			}
			t.setParameter("nowyear", new java.text.SimpleDateFormat("yyyy").format(new java.util.Date()));
			t.setParameter("cooperopusername", user == null ? "" : user.getName());
			t.setParameter("userinfo", user == null ? "{}" : CommonFun.object2Json(user));

			Enumeration<String> headernames = req.getHeaderNames();
			while (headernames.hasMoreElements()) {
				String name = headernames.nextElement();
				t.setParameter("httpheader_" + name, URLDecoder.decode(req.getHeader(name), "UTF-8"));
			}
			

			t.setParameter("ws_token_key", Engine.TOKEN_KEY);
			t.setParameter("ws_app_key", Engine.APP_KEY);
			String http_url, ws_url;
			String server = req.getHeader("Host");
			if (!CommonFun.isNe(req.getHeader("Scheme"))) {
				server = req.getHeader("Scheme") + "://" + server;
			} else {
				server = req.getScheme() + "://" + server;
			}
			http_url = server + req.getContextPath();
			if (server.indexOf("https://") == 0) {
				ws_url = server.replaceAll("https://", "wss://");
			} else {
				ws_url = server.replaceAll("http://", "ws://");
			}
			ws_url = ws_url + req.getContextPath() + "/ws";
			t.setParameter("http_url", http_url);
			t.setParameter("ws_url", ws_url);
			
			Source source = new StreamSource(bis);
			javax.xml.transform.Result result = new StreamResult(pageContext.getOut());
			t.transform(source, result);
		} catch (TransformerConfigurationException e) {
			throw e;
		} catch (TransformerException e) {
			throw e;
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
