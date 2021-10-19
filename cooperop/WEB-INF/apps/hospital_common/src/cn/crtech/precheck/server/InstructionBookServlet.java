package cn.crtech.precheck.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.util.DesUtils;
import cn.crtech.precheck.thirdparty.JsonCheck;

public class InstructionBookServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public void init() throws ServletException {
		System.out.println("说明书第三方接口开启成功;");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		//doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		String status = "fail";
		String address = "";
		
		try {
			req.setCharacterEncoding("UTF-8");
			String bodytxt = getBodytxt(req);
			//reqParams.put("req_ip", getIpAddr(req));
			//去除请求串特殊字符，防止类型转换失败
			bodytxt = bodytxt.replaceAll("[\\t\\n\\r]", "");
			bodytxt = bodytxt.replaceAll("\\\\", "/");
			
			Map<String, Object> requestMap = CommonFun.json2Object(bodytxt, Map.class);
			if(!CommonFun.isNe(requestMap.get("drug_code"))) {
				String local_server_address = SystemConfig.getSystemConfigValue("hospital_common", "local_server_address", "http://127.0.0.1:8085");
				DesUtils des = new DesUtils("crtechyckj");
				address = "/w/hospital_common/additional/instruction.html?drug_code="+requestMap.get("drug_code");
				address = local_server_address + "/insbook/" + des.encrypt(address);
				status = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			resp.setContentType("text/html; charset=UTF-8");
			resp.setHeader("Access-Control-Allow-Origin", "*");
			resp.getWriter().write("{\"status\":\""+status+"\",\"address\":\""+("success".equals(status)?address:"")+"\"}");
			resp.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void destroy() {
		super.destroy();
	}
	
	public String getBodytxt(HttpServletRequest request) throws Exception {
		BufferedReader br = null;
		String str, wholeStr = "";
		try {
			br = request.getReader();
			while((str = br.readLine()) != null){
				wholeStr += str;
			}
		} catch (Exception e) {
			throw e;
		}finally {
			if(br != null) {
				br.close();
			}
		}
		return wholeStr;
	}
}
