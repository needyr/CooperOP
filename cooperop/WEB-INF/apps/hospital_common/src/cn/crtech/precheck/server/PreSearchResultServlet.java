package cn.crtech.precheck.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.precheck.EngineInterface;

public class PreSearchResultServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public void init() throws ServletException {
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e2) {
			log.error(e2);
		}
		Map<String, Object> xmlMap = new HashMap<String, Object>();
		Map<String, Object> idJson = new HashMap<String, Object>();
		// 初始化值
		xmlMap.put("state", "Y");
		xmlMap.put("msg", "成功");
		xmlMap.put("rows", "");
		idJson.put("id", "AAAAAAAAAAAAAAAA");
		HashMap<String, Object> jsonMap = CommonFun.requestMap(req);
		String req_json = (String)jsonMap.get("json");
		req_json = req_json.replaceAll("[\\t\\n\\r]", "");
		req_json = req_json.replaceAll("\\\\", "/");
		@SuppressWarnings("unchecked")
		Map<String, Object> reqMap = CommonFun.json2Object(req_json, Map.class);
		@SuppressWarnings("unchecked")
		HashMap<String, Object> xml_req = (HashMap<String, Object>) reqMap.get("request");
		@SuppressWarnings("unchecked")
		Map<String, Object> patient = (Map<String, Object>) xml_req.get("patient");
		@SuppressWarnings("unchecked")
		Map<String, Object> doctor = (Map<String, Object>) xml_req.get("doctor");
		idJson.put("patient_id", patient.get("id"));
		idJson.put("visit_id", patient.get("visitid"));
		idJson.put("doctor_no", doctor.get("code"));
		// 业务逻辑
		Map<String, Object> resMap = EngineInterface.searchOCResult(xml_req);
		idJson.put("id", resMap.get("id"));
		xmlMap.put("id", CommonFun.object2Json(idJson));
		xmlMap.put("state", resMap.get("state"));
		xmlMap.put("rows", resMap.get("rows"));
		xmlMap.put("msg", resMap.get("msg"));
		try {
			resp.setContentType("text/html; charset=UTF-8");
			Map<String, Object> checkRtnMap = new HashMap<String, Object>();
			checkRtnMap.put("response", xmlMap);
			String xml = CommonFun.object2Xml(checkRtnMap);
			if(!CommonFun.isNe(jsonMap.get("isbs"))) {
				resp.setHeader("Access-Control-Allow-Origin", "*");
				resp.getWriter().write(CommonFun.object2Json(checkRtnMap));
			}else {
				resp.getWriter().write(xml);
			}
			//resp.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void destroy() {
		super.destroy();
	}
}
