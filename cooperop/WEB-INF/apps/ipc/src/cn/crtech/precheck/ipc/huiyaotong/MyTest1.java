package cn.crtech.precheck.ipc.huiyaotong;


import javax.xml.namespace.QName;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;

import cn.crtech.cooperop.bus.log.log;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;

public class MyTest1 {
	public static void main(String[] args) throws Exception{
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf
                .createClient("http://115.28.223.110:11007/CommonService.svc?wsdl");
		Endpoint endpoint = client.getEndpoint();
		// CXF动态客户端在处理此问题时，会报No operation was found with the name的异常
		QName opName = new QName(endpoint.getService().getName().getNamespaceURI(), "GetAnalysisResult");
		BindingInfo bindingInfo = endpoint.getEndpointInfo().getBinding();
		BindingOperationInfo op = bindingInfo.getOperation(opName);
		if (op.isUnwrappedCapable()) {
			op = op.getUnwrappedOperation();
		}

		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='gbk'?>                 "); 
		sb.append("<PrescriptionContent xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema'>                "); 
		sb.append("<PrescriptionInfoExt>                                         "); 
		sb.append("	<S3></S3>                                                    "); 
		sb.append("	<S4></S4>                                                    "); 
		sb.append("	<S5></S5>                                                    "); 
		sb.append("	<S6></S6>                                                    "); 
		sb.append("	<S7></S7>                                                    "); 
		sb.append("	<S8></S8>                                                    "); 
		sb.append("	<S9></S9>                                                    "); 
		sb.append("	<Birthday>1997-01-01</Birthday>                              "); 
		sb.append("	<Gender>男</Gender>                                          "); 
		sb.append("	<Cost></Cost>                                                "); 
		sb.append("	<StickCount></StickCount>                                    "); 
		sb.append("	<Patientno>0000014060</Patientno>                            "); 
		sb.append("	<Prescriptiontype>2</Prescriptiontype>                       "); 
		sb.append("	<Patienttype>军队医改</Patienttype>                          "); 
		sb.append("	<Registerno>0000014060</Registerno>                          "); 
		sb.append("	<Outdate></Outdate>                                          "); 
		sb.append("	<Height/>                                                    "); 
		sb.append("	<Bednum>9</Bednum>                                           "); 
		sb.append("	<Opsid></Opsid>                                              "); 
		sb.append("	<Officename/>                                                "); 
		sb.append("	<Prescriptionno>0000014060</Prescriptionno>                  "); 
		sb.append("	<Qktype></Qktype>                                            "); 
		sb.append("	<ContactWay/>                                                "); 
		sb.append("	<Doctorno>1733</Doctorno>                                    "); 
		sb.append("	<Weight/>                                                    "); 
		sb.append("	<Opsname></Opsname>                                          "); 
		sb.append("	<Wardcode></Wardcode>                                        "); 
		sb.append("	<Doctorname>艾玉华</Doctorname>                              "); 
		sb.append("	<S10></S10>                                                  "); 
		sb.append("	<Prescriptiondate>2018-01-20 11:02:06</Prescriptiondate>     "); 
		sb.append("	<Patientname>张保银</Patientname>                            "); 
		sb.append("	<Indate></Indate>                                            "); 
		sb.append("</PrescriptionInfoExt>                                        "); 
		sb.append("<MedicineInfoExtS>                                            "); 
		sb.append("	<MedicineInfoExt>                                            "); 
		sb.append("		<Medicinename>葡萄糖酸钙注射液</Medicinename>             "); 
		sb.append("		<Beginusingtime/>                                        "); 
		sb.append("		<S3></S3>                                                "); 
		sb.append("		<Ordertype>2</Ordertype>                                 "); 
		sb.append("		<Medcinedepartment></Medcinedepartment>                  "); 
		sb.append("		<S4></S4>                                                "); 
		sb.append("		<S5></S5>                                                "); 
		sb.append("		<Medicinecode>1000028077</Medicinecode>                  "); 
		sb.append("		<Specification>1</Specification>                                         "); 
		sb.append("		<Frequencyname>2</Frequencyname>                         "); 
		sb.append("		<Count/>                                                 "); 
		sb.append("		<Unit/>                                                  "); 
		sb.append("		<Dosage/>                                                "); 
		sb.append("		<Frequencycode>2</Frequencycode>                         "); 
		sb.append("		<Doseformname>静滴</Doseformname>                        "); 
		sb.append("		<Price></Price>                                          "); 
		sb.append("		<Dosagename>2</Dosagename>                               "); 
		sb.append("		<Groupno>1</Groupno>                                     "); 
		sb.append("		<Precodeitem>1/1/1000028077</Precodeitem>                "); 
		sb.append("		<Endusingtime/>                                          "); 
		sb.append("		<S2></S2>                                                "); 
		sb.append("	</MedicineInfoExt>                                           "); 
		sb.append("	<MedicineInfoExt>                                            "); 
		sb.append("		<Medicinename>地高辛片</Medicinename>             "); 
		sb.append("		<Beginusingtime/>                                        "); 
		sb.append("		<S3></S3>                                                "); 
		sb.append("		<Ordertype>2</Ordertype>                                 "); 
		sb.append("		<Medcinedepartment></Medcinedepartment>                  "); 
		sb.append("		<S4></S4>                                                "); 
		sb.append("		<S5></S5>                                                "); 
		sb.append("		<Medicinecode>1000001685</Medicinecode>                  "); 
		sb.append("		<Specification>1</Specification>                                         "); 
		sb.append("		<Frequencyname>2</Frequencyname>                         "); 
		sb.append("		<Count/>                                                 "); 
		sb.append("		<Unit/>                                                  "); 
		sb.append("		<Dosage/>                                                "); 
		sb.append("		<Frequencycode>2</Frequencycode>                         "); 
		sb.append("		<Doseformname>口服</Doseformname>                        "); 
		sb.append("		<Price></Price>                                          "); 
		sb.append("		<Dosagename>2</Dosagename>                               "); 
		sb.append("		<Groupno>1</Groupno>                                     "); 
		sb.append("		<Precodeitem>1/1/1000001685</Precodeitem>                "); 
		sb.append("		<Endusingtime/>                                          "); 
		sb.append("		<S2></S2>                                                "); 
		sb.append("	</MedicineInfoExt>                                           "); 
		sb.append("	<MedicineInfoExt>                                            "); 
		sb.append("		<Medicinename>盐酸左氧氟沙星片</Medicinename>            "); 
		sb.append("		<Beginusingtime>2016-01-11 08:50:34</Beginusingtime>     "); 
		sb.append("		<S3></S3>                                                "); 
		sb.append("		<Ordertype>0</Ordertype>                                 "); 
		sb.append("		<Medcinedepartment></Medcinedepartment>                  "); 
		sb.append("		<S4></S4>                                                "); 
		sb.append("		<S5></S5>                                                "); 
		sb.append("		<Medicinecode>0202018TB0</Medicinecode>                  "); 
		sb.append("		<Specification>1</Specification>                                         "); 
		sb.append("		<Frequencyname>1/日</Frequencyname>                      "); 
		sb.append("		<Count>0.0000</Count>                                    "); 
		sb.append("		<Unit>*</Unit>                                           "); 
		sb.append("		<Dosage>200.0000</Dosage>                                "); 
		sb.append("		<Frequencycode>1/日</Frequencycode>                      "); 
		sb.append("		<Doseformname>口服</Doseformname>                        "); 
		sb.append("		<Price></Price>                                          "); 
		sb.append("		<Dosagename>mg</Dosagename>                              "); 
		sb.append("		<Groupno>0000014060|4|23</Groupno>                       "); 
		sb.append("		<Precodeitem>0000014060|4|23|1</Precodeitem>             "); 
		sb.append("		<Endusingtime>2016-01-11 08:50:34</Endusingtime>         "); 
		sb.append("		<S2></S2>                                                "); 
		sb.append("	</MedicineInfoExt>                                           "); 
		sb.append("	<MedicineInfoExt>                                            "); 
		sb.append("		<Medicinename>盐酸左氧氟沙星片</Medicinename>            "); 
		sb.append("		<Beginusingtime>2016-01-11 08:12:58</Beginusingtime>     "); 
		sb.append("		<S3></S3>                                                "); 
		sb.append("		<Ordertype>1</Ordertype>                                 "); 
		sb.append("		<Medcinedepartment></Medcinedepartment>                  "); 
		sb.append("		<S4></S4>                                                "); 
		sb.append("		<S5></S5>                                                "); 
		sb.append("		<Medicinecode>0202018TB0</Medicinecode>                  "); 
		sb.append("		<Specification>1</Specification>                                         "); 
		sb.append("		<Frequencyname>3/日</Frequencyname>                      "); 
		sb.append("		<Count>0.0000</Count>                                    "); 
		sb.append("		<Unit>*</Unit>                                           "); 
		sb.append("		<Dosage>200.0000</Dosage>                                "); 
		sb.append("		<Frequencycode>3/日</Frequencycode>                      "); 
		sb.append("		<Doseformname>口服</Doseformname>                        "); 
		sb.append("		<Price></Price>                                          "); 
		sb.append("		<Dosagename>mg</Dosagename>                              "); 
		sb.append("		<Groupno>0000014060|4|24</Groupno>                       "); 
		sb.append("		<Precodeitem>0000014060|4|24|1</Precodeitem>             "); 
		sb.append("		<Endusingtime/>                                          "); 
		sb.append("		<S2></S2>                                                "); 
		sb.append("	</MedicineInfoExt>                                           "); 
		sb.append("</MedicineInfoExtS>                                           "); 
		sb.append("<DiagnoseInfoS>                                               "); 
		sb.append("	<DiagnoseInfo>                                               "); 
		sb.append("		<Diagnosecode>K76.001</Diagnosecode>                     "); 
		sb.append("		<Diagnosename>脂肪肝</Diagnosename>                      "); 
		sb.append("	</DiagnoseInfo>                                              "); 
		sb.append("</DiagnoseInfoS>                                              "); 
		sb.append("<OpsInfoS/>                                                   "); 
		sb.append("<AllergyInfoS/>                                               "); 
		sb.append("<PhysiologyInfoS/>                                            "); 
		sb.append("</PrescriptionContent>                                        "); 
		log.debug("request=jq=："+sb.toString());
		Object[] invokeargs = new Object[1];
		invokeargs[0] = sb.toString();
		
		Object[] res = client.invoke(op, invokeargs);
		//log.debug(res[0]);
			/*Service sv = new Service();  //new 一个服务  
			Call call = (Call) sv.createCall();  //创建一个call对象  
			call.setTargetEndpointAddress(new URL("http://115.28.223.110:11007/CommonService.svc?wsdl"));  //设置要调用的接口地址
			call.setOperationName(new QName("http://tempuri.org/", "GetAnalysisResult"));  //设置要调用的接口方法  
			call.addParameter(new QName("http://tempuri.org/", "PrescriptionContent"), 
				org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);//设置参数名 id  第二个参数表示String类型,第三个参数表示入参  
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//返回参数类型
			call.setUseSOAPAction(true); 
			call.setSOAPActionURI("http://tempuri.org/ICommonService/GetAnalysisResult")*/; 
           /* log.debug("request=jq=："+sb.toString());
            long t1 = System.currentTimeMillis();
            try {
				result = (String) call.invoke(new Object[]{sb.toString()});
			} catch (RemoteException e) {
				e.printStackTrace();
			}
           log.debug("return==jq==(耗时"+(System.currentTimeMillis()-t1)+"毫秒)："+result);*/
//            Document doc = DocumentHelper.parseText(result);
//            Element root = doc.getRootElement();
//            Element ser = root.element("root/serviceResults/resultStatus");
//           log.debug(ser.getName());
           // Map<String, Object> rtn = CommonFun.xml2Object(result, Map.class);
	}
}
