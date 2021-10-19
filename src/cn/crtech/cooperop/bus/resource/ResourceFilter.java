package cn.crtech.cooperop.bus.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.bus.theme.ThemeFilter;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.LocalThreadMap;

/**
 * @author Administrator
 *
 */
public class ResourceFilter extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5400824477667029395L;

	@Override
	public void init(ServletConfig config) throws ServletException {
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String cp = req.getContextPath() == null ? "" : req.getContextPath();
		String sp = req.getServletPath();
		String res = req.getRequestURI().substring((cp + sp).length());
		
		String module = res.substring(1, res.indexOf('/', 1));
		res = res.substring(res.indexOf('/', 1) + 1);

		String uri = GlobalVar.getSystemProperty("view.resource.access.rule");

		uri = uri.replace("@[MODULE]", module);
		uri = uri.replace("@[RESOURCE]", res);

		File resource = new File(req.getSession().getServletContext()
				.getRealPath(uri));
		if (resource.exists()) {
			ThemeFilter.setCacheControl(resp);
			RequestDispatcher rd = req.getRequestDispatcher(uri);
			rd.forward(req, resp);
		}
		else {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND,
					req.getRequestURI());
		}
	}

}
