package cn.crtech.cooperop.bus.engine.window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.application.authenticate.Authenticate;
import cn.crtech.cooperop.bus.engine.Engine;
import cn.crtech.cooperop.bus.session.Session;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;

public class HtmlWindow implements Window {

	public static String getViewPath(String pageid) throws FileNotFoundException {
		return getViewPath(pageid, false, false);
	}
	public static String getViewPath(String pageid, boolean ismobole, boolean ispad) throws FileNotFoundException {
		String custom_id = GlobalVar.getSystemProperty("custom.id");
		String module = pageid.substring(0, pageid.indexOf('.'));
		pageid = pageid.substring(pageid.indexOf('.') + 1);

		String uri;
		if(ismobole || ispad){
			if(!CommonFun.isNe(custom_id)){
				uri = GlobalVar.getSystemProperty(ismobole?"view_m.html.custom.rule":"view_p.html.custom.rule");
				uri = uri.replace("@[CUSTOM]", custom_id);
				uri = uri.replace("@[MODULE]", module);
				uri = uri.replace("@[PAGE]", pageid.replace('.', '/'));
				if(new File(GlobalVar.getWorkPath() + uri).exists()){
					return uri;
				}else{
					uri = null;
				}
			}
			uri = GlobalVar.getSystemProperty(ismobole?"view_m.html.access.rule":"view_p.html.access.rule");
			uri = uri.replace("@[MODULE]", module);
			uri = uri.replace("@[PAGE]", pageid.replace('.', '/'));
			if(new File(GlobalVar.getWorkPath() + uri).exists()){
				return uri;
			}else{
				uri = null;
			}
		}
		if(!CommonFun.isNe(custom_id)){
			uri = GlobalVar.getSystemProperty("view.html.custom.rule");
			uri = uri.replace("@[CUSTOM]", custom_id);
			uri = uri.replace("@[MODULE]", module);
			uri = uri.replace("@[PAGE]", pageid.replace('.', '/'));	
			if(new File(GlobalVar.getWorkPath() + uri).exists()){
				return uri;
			}else{
				uri = null;
			}
		}
		uri = GlobalVar.getSystemProperty("view.html.access.rule");
		uri = uri.replace("@[MODULE]", module);
		uri = uri.replace("@[PAGE]", pageid.replace('.', '/'));		
		return uri;
	}
	@Override
	public void excute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String cp = req.getContextPath() == null ? "" : req.getContextPath();
		String sp = req.getServletPath();
		String res = req.getRequestURI().substring((cp + sp).length() + 1);
		String pageid = res.substring(0, res.lastIndexOf(".")).replace('/', '.');
		
		Map<String, Object> action = Authenticate.getAction(pageid);
		if (action != null) {
			Engine.executeCallAction(req, resp);
		}
		try {
			String uri;
			if(CommonFun.isMoblie(req)){
				uri = getViewPath(pageid ,true ,false);
			}else if(CommonFun.isPad(req)){
				uri = getViewPath(pageid ,false ,true);
			}else{
				uri = getViewPath(pageid);
			}
			
			if (!new File(req.getSession().getServletContext().getRealPath(uri)).exists()) {
				throw new FileNotFoundException(req.getRequestURI());
			}
			RequestDispatcher rd = req.getRequestDispatcher(uri);
			rd.forward(req, resp);
		} catch (FileNotFoundException ex) {
			Object t = req.getAttribute("$return");
			if (t == null) {
				resp.sendError(404);
			} else {
				resp.setContentType("text/html; charset=UTF-8");
				resp.getWriter().write(object2Html(t));
				resp.getWriter().flush();
			}
		}
	}

	private String object2Html(Object obj) {
		return obj.toString();
	}

}
