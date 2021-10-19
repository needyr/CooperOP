package cn.crtech.cooperop.bus.engine.window;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.bus.engine.Engine;
import cn.crtech.cooperop.bus.util.CommonFun;

public class JsonpWindow implements Window {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		Engine.executeCallAction(req, resp);
		Object t = req.getAttribute("$return");
		resp.setContentType("text/html; charset=UTF-8");
		String callback = req.getParameter("callback");
		String paramter = req.getParameter("paramter");
		if (!CommonFun.isNe(callback)) {
			resp.getWriter().write(callback + "(" + CommonFun.object2Json(t) + ");");
		} else if (!CommonFun.isNe(paramter)) {
			resp.getWriter().write("var " + paramter + " = " + CommonFun.object2Json(t));
		} else {
			resp.getWriter().write("var _jsonp_" + CommonFun.getSSID() + " = " + CommonFun.object2Json(t));
		}
		resp.getWriter().flush();
	}

}
