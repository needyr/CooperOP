package cn.crtech.cooperop.bus.engine.window;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.bus.engine.Engine;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.util.CommonFun;

public class JsonWindow implements Window {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		Engine.executeCallAction(req, resp);
		Object t = req.getAttribute("$return");
		resp.setContentType("text/html; charset=UTF-8");
		String r = CommonFun.object2Json(t);
		Map<String, Object> m = CommonFun.json2Object(r, Map.class);
		if(m!=null &&"Y".equals(m.get("wx_auth_url"))){
			resp.sendRedirect((String)m.get("redirect_url"));
		}else{
			resp.getWriter().write(r);
			resp.getWriter().flush();
		}
	}

}
