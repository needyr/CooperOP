package cn.crtech.cooperop.bus.weixin.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.protocol.HTTP;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;

@WebServlet(loadOnStartup = 2, urlPatterns = { "/wxlistener" })
public class Listener extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6096879194948982426L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Cache-Control", "max-age=0");
		resp.setContentType("text/html; charset=UTF-8");
		String reqstream = loadRtn(req);
		if (CommonFun.isNe(reqstream)) {
			String signature = req.getParameter("signature");
			String timestamp = req.getParameter("timestamp");
			String nonce = req.getParameter("nonce");
			String echostr = req.getParameter("echostr");
			log.debug("signature:" + signature);
			log.debug("timestamp:" + timestamp);
			log.debug("nonce:" + nonce);
			log.debug("echostr:" + echostr);
			reqstream = echostr;
			resp.getWriter().write(reqstream);
			resp.getWriter().flush();
		} else {
			call(reqstream, resp);
		}
	}

	private static void call(String req, HttpServletResponse resp) throws IOException {
		String rtn = "success";
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> reqmap = CommonFun.xml2Object(req, Map.class);
			@SuppressWarnings("unchecked")
			Record msg = new Record((Map<String, Object>) reqmap.get("xml"));

			String cls = Engine.class.getPackage().getName() + "." + CommonFun.capitalize(msg.getString("MsgType"))
					+ "Engine";

			Engine engine = (Engine) Class.forName(cls).newInstance();

			Record rtnmsg = engine.handle(msg);

			if (rtnmsg != null) {
				rtnmsg.put("FromUserName", msg.getString("ToUserName"));
				rtnmsg.put("ToUserName", msg.getString("FromUserName"));
				rtnmsg.put("CreateTime", System.currentTimeMillis() / 1000);
				Record xml = new Record();
				xml.put("xml", rtnmsg);
				rtn = CommonFun.object2Xml(xml);
			}
		} catch (Exception ex) {
			log.error("call engine failed. ", ex);
		} finally {
			resp.getWriter().write(rtn);
			resp.getWriter().flush();
		}
	}

	private static String loadRtn(HttpServletRequest req) throws IllegalStateException, IOException {
		StringBuffer rtn = new StringBuffer();
		String charset = HTTP.UTF_8;
		if (!CommonFun.isNe(req.getContentType())) {
			String t = req.getContentType();
			String[] ts = t.split(";");
			for (String ti : ts) {
				if (ti.indexOf("charset=") > -1) {
					charset = ti.substring(ti.indexOf("charset=") + "charset=".length()).trim();
					break;
				}
			}
		}
		InputStreamReader isr = new InputStreamReader(req.getInputStream(), charset);
		BufferedReader bufferedReader = new BufferedReader(isr);
		String text;
		while ((text = bufferedReader.readLine()) != null) {
			rtn.append(text + "\r\n");
		}
		return rtn.toString();
	}
}
