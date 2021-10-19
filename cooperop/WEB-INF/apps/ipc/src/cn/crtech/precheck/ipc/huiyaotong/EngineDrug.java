package cn.crtech.precheck.ipc.huiyaotong;



import javax.servlet.ServletException;
import javax.xml.namespace.QName;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;

public class EngineDrug{
	private static Client client = null;

	@SuppressWarnings("resource")
	public static void init() throws ServletException {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        /*client = dcf
                .createClient("http://"+GlobalVar.getSystemProperty("hyt_url")+"/CommonService.svc?wsdl");*/
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
		QName opName = new QName(endpoint.getService().getName().getNamespaceURI(), "GetInstructionByHisCode");
		BindingInfo bindingInfo = endpoint.getEndpointInfo().getBinding();
		BindingOperationInfo op = bindingInfo.getOperation(opName);
		if (op.isUnwrappedCapable()) {
			op = op.getUnwrappedOperation();
		}

		
		Object[] invokeargs = new Object[2];
		invokeargs[0] = request;
		invokeargs[1] = "1";
		log.info(request);
		try {
			Object[] res = client.invoke(op, invokeargs);
			if (res.length > 0) {
				return "http://"+SystemConfig.getSystemConfigValue("hospital_common", "url_ipc")+res[0];
			}
			return null;
		} catch (Throwable ex) {
			if (ex.getCause() != null) throw new Exception(ex.getCause());
			throw ex;
		}
	}

	public String execMethod(String request) throws Exception {
		return (String) invokeMethod(request);
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
