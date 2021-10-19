package cn.crtech.precheck.client.ws;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.xml.namespace.QName;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.precheck.client.service.ClientService;

public class Engine extends cn.crtech.precheck.client.Engine{
	public String CLIENT_CODE = "WS";
	private static Client client = null;
	@SuppressWarnings("resource")
	public void init(String c) throws ServletException {
			super.init(c);
			File f = new File("/");
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = null;
			ByteArrayInputStream bais = null;
			try {
				Record webservice = new ClientService().getClient(c);
				f = new File(f, webservice.getString("code") + ".wsdl");
				if (f.exists()) {
					f.delete();
				}
				fos = new FileOutputStream(f);
				bais = new ByteArrayInputStream(webservice.getString("definition").getBytes());
				int readBytes = 0;
				byte buffer[] = new byte[8192];
				while ((readBytes = bais.read(buffer, 0, 8192)) != -1) {
					fos.write(buffer, 0, readBytes);
				}
				fos.flush();

				JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
				client = factory.createClient(f.toURI().toString());
				// +++ exception modify 1
				if (CommonFun.isNe(client)) {
					log.error("WebService 实例化客服端失败");
					throw new Exception("WebService 实例化客服端失败");
				}
				// +++
				Endpoint endpoint = client.getEndpoint();
				// 设置超时单位为毫秒
				HTTPConduit http = (HTTPConduit) client.getConduit();
				HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
				httpClientPolicy.setConnectionTimeout(Long.parseLong(GlobalVar.getSystemProperty("webservice.connection.timeout", "3")) * 1000); // 连接超时
				httpClientPolicy.setAllowChunking(false); // 取消块编码
				httpClientPolicy.setReceiveTimeout(Long.parseLong(GlobalVar.getSystemProperty("webservice.receive.timeout", "60")) * 1000); // 响应超时
				http.setClient(httpClientPolicy);

				BindingInfo bindingInfo = endpoint.getEndpointInfo().getBinding();
				for (BindingOperationInfo op : bindingInfo.getOperations()) {
					log.release("扫描WebService接口：" + op.getName());
				}
				
		} catch (Exception exc) {
			log.error("启动" + CLIENT_CODE + "定时服务异常", exc);
			throw new ServletException(exc);
		}

	}

	
	public void execMethod(String request) throws Exception {
	}
	public void execMethod(String method, String request) throws Exception {
	}
	// +++
	@SuppressWarnings("unchecked")
	public Object invokeMethod(String method, String request) throws Exception {
		// +++ exception modify 5
		if (CommonFun.isNe(client)) {
			log.error("WebService 客服端没有实例化");
			throw new Exception("WebService 客服端没有实例化");
		}
		// +++
		Endpoint endpoint = client.getEndpoint();
		// CXF动态客户端在处理此问题时，会报No operation was found with the name的异常
		QName opName = new QName(endpoint.getService().getName().getNamespaceURI(), "MessageServer");
		BindingInfo bindingInfo = endpoint.getEndpointInfo().getBinding();
		BindingOperationInfo op = bindingInfo.getOperation(opName);
		if (op.isUnwrappedCapable()) {
			op = op.getUnwrappedOperation();
		}

		Object[] invokeargs = new Object[2];
		invokeargs[0] = method;
		invokeargs[1] = request;
		
		log.info(request);
		try {
			Object[] res = client.invoke(op, invokeargs);
			if (res.length > 0) {
				if (res[0] == null) {
					return res[0];
				} else {
					//log.debug(res[0]);
					Map<String, Object> rtn = CommonFun.xml2Object((String)res[0], Map.class);
					return ((Map<String, Object>)rtn.get("Response")).get("Body");
				}
			}
			return null;
		} catch (Throwable ex) {
			if (ex.getCause() != null) throw new Exception(ex.getCause());
			throw ex;
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
	
	@Override
	public void destroy(){
		client.destroy();
	}
}
