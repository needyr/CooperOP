package cn.crtech.precheck.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.hospital_common.util.DesUtils;

public class InsBookServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public void init() throws ServletException {
	}
	
	private static String contextPath;
	private static String servletPath;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		contextPath = req.getContextPath() == null ? "" : req.getContextPath();
		servletPath = req.getServletPath();
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		String res = req.getRequestURI().substring((contextPath + servletPath).length() + 1);
		try {
			DesUtils des = new DesUtils("crtechyckj");
			String decrypt = des.decrypt(res);
			req.getRequestDispatcher(decrypt).forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void destroy() {
		super.destroy();
	}
}
