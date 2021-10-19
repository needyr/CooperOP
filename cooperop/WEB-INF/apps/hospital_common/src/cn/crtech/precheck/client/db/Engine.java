package cn.crtech.precheck.client.db;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.precheck.client.service.ClientService;
import cn.crtech.ylz.ylz;

public class Engine extends cn.crtech.precheck.client.Engine{
	
	@Override
	public void init(String c) throws ServletException {
		super.init(c);
	}
	public void execMethod(Map<String, Object> params){
		ClientService cs = new ClientService();
		try {
			Record client = cs.getClientByManual(CLIENT_CODE, (String)params.get("p_type"));
			List<Record> methods = (List<Record>) client.get("methods");
			for(Record method : methods){
				try{
					cs.executeParamProcedure(method, (String)params.get("patient_id"), (String)params.get("visit_id"));
				}catch (Exception e1) {
					//TODO 异常信息处理
					ylz.p(3, method.toString());
					e1.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// +++
	@Override
	public String getRequest(String method, String header, Object... args) {
		StringBuffer xml = new StringBuffer();
		xml.append("<Request>");
		xml.append("\r\n	<Header>");
		xml.append("\r\n		<SourceSystem>" + header + "</SourceSystem> ");
		xml.append("\r\n		<MessageID>" + method + "</MessageID>");
		xml.append("\r\n	</Header>\r\n");
		Record t = new Record();
		t.put("Body", args);
		xml.append(CommonFun.object2Xml(t));
		xml.append("</Request>");
		log.info(xml.toString());
		
		return xml.toString();
	}

	@Override
	public String getResponse(String method, String header, Object rtn) {
		Record body = new Record();
		body.put("Body", rtn);
		StringBuffer xml = new StringBuffer();
		xml.append("<Response>");
		xml.append("\r\n	<Header>");
		xml.append("\r\n		<SourceSystem>" + header + "</SourceSystem> ");
		xml.append("\r\n		<MessageID>" + method + "</MessageID>");
		xml.append("\r\n	</Header>\r\n");
		xml.append(CommonFun.object2Xml(body));
		xml.append("</Response>");
		log.info(xml.toString());
		
		return xml.toString();
	}
}
