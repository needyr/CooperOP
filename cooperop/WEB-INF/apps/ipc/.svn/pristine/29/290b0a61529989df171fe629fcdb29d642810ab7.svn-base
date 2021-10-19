package cn.crtech.precheck.ipc.huiyaotong;

import java.util.Map;

import javax.servlet.ServletException;
import javax.xml.namespace.QName;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.ipc.dao.AutoAuditDao;
import cn.crtech.cooperop.ipc.dao.CheckResultDao;
import cn.crtech.cooperop.ipc.dao.CheckResultInfoDao;
import cn.crtech.cooperop.ipc.service.AuditResultService;

public class EngineHYT{
	private static Client client = null;

	@SuppressWarnings("resource")
	public static void init() throws ServletException {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        client = dcf.createClient("http://"+SystemConfig.getSystemConfigValue("hospital_common", "url_ipc")+"/CommonService.svc?wsdl");
	}

	@SuppressWarnings("unchecked")
	public Object invokeMethod(String request) throws Exception {
		// +++ exception modify 5
		if (CommonFun.isNe(client)) {
			log.error("WebService 客服端没有实例化");
		}
		// +++
		Endpoint endpoint = client.getEndpoint();
		// CXF动态客户端在处理此问题时，会报No operation was found with the name的异常
		QName opName = new QName(endpoint.getService().getName().getNamespaceURI(), "GetAnalysisResult");
		BindingInfo bindingInfo = endpoint.getEndpointInfo().getBinding();
		BindingOperationInfo op = bindingInfo.getOperation(opName);
		if (op.isUnwrappedCapable()) {
			op = op.getUnwrappedOperation();
		}

		
		Object[] invokeargs = new Object[1];
		invokeargs[0] = request;
		
		log.info(request);
		try {
			Object[] res = client.invoke(op, invokeargs);
			if (res.length > 0) {
				return res[0];
			}
			return null;
		} catch (Throwable ex) {
			if (ex.getCause() != null) throw new Exception(ex.getCause());
			throw ex;
		}
	}

	public String execMethod(String request, Map<String, Object> map) throws Exception {
		String result = (String) invokeMethod(request);
		return result;
	}
	public String getRequest(String method, String header, Object... args) {
		return null;
	}
	
	public String getResponse(String method, String header, Object rtn) {
		return null;
	}
	public static void destroy(){
		client.destroy();
	}
}
