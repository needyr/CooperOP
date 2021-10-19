package cn.crtech.precheck.ipc.service;

import java.util.List;
import java.util.Map;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.util.CommonFun;

public class EngineHLYY {
	public static final String HYT_ADDRESS = SystemConfig.getSystemConfigValue("ipc", "hyt_dll_audit_address");
	public static final String HYT_JGDM = SystemConfig.getSystemConfigValue("ipc", "hyt_jgdm");
	public static final String HYT_TIMEOUT = SystemConfig.getSystemConfigValue("ipc", "hyt_timeout");
	//static Pointer session = HYTInterface.instanceDll.HYT_Initialize(HYT_ADDRESS, HYT_JGDM, Integer.valueOf(HYT_TIMEOUT));

	@SuppressWarnings("unchecked")
	public String HYTAudit(Map<String,Object> map) throws Throwable{
		//log.debug(System.getProperty("java.library.path"));
		System.setProperty("jna.encoding", "GB2312");  
		
		// 调取药品说明书
		//int result = MyCLibrary.instanceDll.HYT_GetMedInstruction(session, "Y00000002670", "蒲地蓝消炎口服液", "10ml*6支/盒");
		/*int result = HYTInterface.instanceDll.HYT_PrescriptionInfoExt(session, "3743364JAVA", "3743364JAVA", 
				1, "2019-06-14 16:03:25", 59.6, "", "", "1137", 
				"黄振礼", "消化、肾内、肿瘤内科", "3743364JAVA", "黄亚圈", 
				"新农合", "1970-05-23 00:00:00", "男", 0, 0, null, "", 
				null, "", "25", "", null, "", "", "", "", "", "", "", "");*/
		List<Map<String, Object>> drug_list = null;
		List<Map<String, Object>> diagnoseInfoS = null;
		List<Map<String, Object>> allergyInfo = null;
		List<Map<String, Object>> opsInfo = null;
		List<Map<String, Object>> physiologyInfoS = null;
		Map<String,Object> patient = (Map<String, Object>) map.get("PrescriptionInfoExt");
		if (map.get("medicineinfoext") != null) {
			drug_list = (List<Map<String, Object>>) map.get("medicineinfoext");
		}
		if (map.get("diagnoseinfo") != null) {
			diagnoseInfoS = (List<Map<String, Object>>) map.get("diagnoseinfo");
		}
		if (map.get("allergyinfo") !=null) {
			allergyInfo = (List<Map<String, Object>>) map.get("allergyinfo");
		}
		if (map.get("opsinfo") != null) {
			opsInfo = (List<Map<String, Object>>) map.get("opsinfo");
		}
		if (map.get("physiologyinfo")!= null) {
			physiologyInfoS = (List<Map<String, Object>>) map.get("physiologyinfo");
		}
		Pointer session = HYTInterface.instanceDll.HYT_Initialize(HYT_ADDRESS, HYT_JGDM, Integer.valueOf(HYT_TIMEOUT));
		//Pointer session = null;
		//session = HYTInit.getInstance().getSession();
		// 添加病人信息
		int result = HYTInterface.instanceDll.HYT_PrescriptionInfoExt(session, 
				getString(patient.get("registerno")), 
				getString(patient.get("prescriptionno")),
				Integer.parseInt(patient.get("prescriptiontype").toString()),
				getString(patient.get("prescriptiondate")), 
				CommonFun.isNe(patient.get("cost"))?0:Integer.parseInt(String.valueOf(patient.get("cost"))),
				getString(patient.get("indate")),
				getString(patient.get("outdate")), 
				getString(patient.get("doctorno")), 
				getString(patient.get("doctorname")), 
				getString(patient.get("officename")), 
				getString(patient.get("patientno")), 
				getString(patient.get("patientname")), 
				getString(patient.get("patienttype")), 
				getString(patient.get("birthday")), 
				getString(patient.get("gender")), 
				CommonFun.isNe(patient.get("height"))?0:Integer.parseInt(String.valueOf(patient.get("height"))), 
				CommonFun.isNe(patient.get("weight"))?0:Double.parseDouble(String.valueOf(patient.get("weight"))), 
				getString(patient.get("opsid")), 
				getString(patient.get("opsname")), 
				getString(patient.get("qktype")), 
				getString(patient.get("wardcode")), 
				getString(patient.get("bednum")), 
				getString(patient.get("contactway")), 
				getString(patient.get("stickcount")), "", "", "", "", "", "", "", "");
		// 添加药品信息
		/*result = HYTInterface.instanceDll.HYT_MedicineInfoExt(session, 
		"206792", "注射用奥美拉唑钠", "60mg/支", 1, "静脉输液(第二组起)", 
		"一次性", "一次性", "支", 0, 0, 6000.0, "mg", "33841618|33841618#1", 
		"2019-05-22 11:41:00", "", "", "3523012|1|33841618|33841618#1|2|50602020801", 
		"", "", "", "");*/
		if (drug_list != null) {
			for (Map<String, Object> message : drug_list) {
				result = HYTInterface.instanceDll.HYT_MedicineInfoExt(session, 
						getString(message.get("medicinecode")),
						getString(message.get("medicinename")), 
						getString(message.get("specification")), 
						CommonFun.isNe(message.get("ordertype"))?0:Integer.parseInt(String.valueOf(message.get("ordertype"))),
						getString(message.get("doseformname")), 
						getString(message.get("frequencycode")),
						getString(message.get("frequencyname")), 
						getString(message.get("unit")), 
						CommonFun.isNe(message.get("price"))?0:Double.parseDouble(String.valueOf(message.get("price"))), 
						CommonFun.isNe(message.get("count"))?0:Double.parseDouble(String.valueOf(message.get("count"))), 
						CommonFun.isNe(message.get("dosage"))?0:Double.parseDouble(String.valueOf(message.get("dosage"))), 
						getString(message.get("dosagename")), 
						getString(message.get("groupno")),
						getString(message.get("beginusingtime")), 
						getString(message.get("endusingtime")), 
						getString(message.get("medcinedepartment")), 
						getString(message.get("precodeitem")),
						"", "", "", "");
			}
		}
		// 添加诊断信息
		//result = HYTInterface.instanceDll.HYT_DiagnoseInfo(session, "C22.900", "肝恶性肿瘤");
		if(diagnoseInfoS != null) {
			for (Map<String, Object> diagnose : diagnoseInfoS) {
				result = HYTInterface.instanceDll.HYT_DiagnoseInfo(session, 
						String.valueOf(diagnose.get("diagnosecode")), String.valueOf(diagnose.get("diagnosename")));
			}
		}
		//过敏信息
		if(allergyInfo != null) {
			for (Map<String, Object> allergy : allergyInfo) {
				result = HYTInterface.instanceDll.HYT_AllergyInfo(session, 
						String.valueOf(allergy.get("allergycode")), String.valueOf(allergy.get("allergyname")));
			}
		}
		//病人手术信息
		if(opsInfo != null) {
			for (Map<String, Object> ops : opsInfo) {
				result = HYTInterface.instanceDll.HYT_OpsInfo(session,
						String.valueOf(ops.get("opsid")),
						String.valueOf(ops.get("opsname")),
						String.valueOf(ops.get("qktype")),
						String.valueOf(ops.get("begintime")),
						String.valueOf(ops.get("endtime")));
			}
		}
		//病人病生理状态
		if (physiologyInfoS != null) {
			for (Map<String, Object> physiology : physiologyInfoS) {
				result = HYTInterface.instanceDll.HYT_PhysiologyInfo(session,
						String.valueOf(physiology.get("physiologycode")), String.valueOf(physiology.get("physiologyname")));
			}
		}
		// 分析处方
		result = HYTInterface.instanceDll.HYT_Analyze(session, 0, 0, "");
		IntByReference type = new IntByReference();
		type.setValue(0);
		IntByReference severity = new IntByReference();
		severity.setValue(0);
		IntByReference holdup = new IntByReference();
		holdup.setValue(0);
		IntByReference msgtype = new IntByReference();
		msgtype.setValue(0);
		int size = 2048;
		Pointer message = new Memory(size);
		Pointer summary = new Memory(size);
		Pointer reference = new Memory(size);
		Pointer precodeitem1 = new Memory(size);
		Pointer precodeitem2 = new Memory(size);
		int nTmp = 0;
		int retmessage = 0;
		StringBuffer _r = new StringBuffer();
		_r.append("<result>");
		do {
			nTmp = HYTInterface.instanceDll.HYT_GetAnalyzeResultExByLevel(session, type, severity, holdup,msgtype, message, summary, reference,precodeitem1,precodeitem2);
			//log.debug(nTmp);
			if(nTmp >0 ) {
				int value = type.getValue();
				String ref = replaceSpecialCharacter(reference.getString(0));
				_r.append("<RetContent>");
				_r.append("<Type>"+type.getValue()+"</Type>");
				_r.append("<Severity>"+severity.getValue()+"</Severity>");
				_r.append("<Holdup>"+holdup.getValue()+"</Holdup>");
				_r.append("<MsgType>"+msgtype.getValue()+"</MsgType>");
				_r.append("<Message>"+replaceSpecialCharacter(message.getString(0))+"</Message>");
				_r.append("<Summary>"+replaceSpecialCharacter(summary.getString(0))+"</Summary>");
				if (CommonFun.isNe(ref)) {
					if (value>1 && value<=5) {
						_r.append("<Reference>处方管理办法</Reference>");
					}else if(value>5){
						_r.append("<Reference>药品说明书</Reference>");
					}
				}else {
					_r.append("<Reference>"+replaceSpecialCharacter(reference.getString(0))+"</Reference>");
				}
				_r.append("<Precodeitem1>"+replaceSpecialCharacter(precodeitem1.getString(0))+"</Precodeitem1>");
				_r.append("<Precodeitem2>"+replaceSpecialCharacter(precodeitem2.getString(0))+"</Precodeitem2>");
				_r.append("</RetContent>");
				/*ssString+= "type="+type.getValue()+";severity="+severity.getValue()+";holdup="+holdup.getValue()+
						";msgtype="+msgtype.getValue()+";message="+message.getString(0)+";summary="+summary.getString(0)+
						";reference="+reference.getString(0)+";precodeitem1="+precodeitem1.getString(0)+
						";precodeitem2="+precodeitem2.getString(0)+"\n\r";*/
			}
			if(nTmp>retmessage) {
				retmessage = nTmp;
			}
			if (nTmp<0) {
				retmessage = nTmp;
				break;
			}
		}while(nTmp>0);
		//log.debug(ssString);
		_r.append("<RetMessage>"+retmessage+"</RetMessage>");
		_r.append("</result>");
		//_r.append("</AnalyzeResultEx>");
		/*message.clear(size);
		summary.clear(size);
		reference.clear(size);
		precodeitem1.clear(size);
		precodeitem2.clear(size);
		type.getPointer().clear(0);
		severity.getPointer().clear(0);
		holdup.getPointer().clear(0);*/
		
		Native.free(Pointer.nativeValue(message));//手动释放内存
		Pointer.nativeValue(message, 0);//避免Memory对象被GC时重复执行Nativ.free()方法
		Native.free(Pointer.nativeValue(summary));
		Pointer.nativeValue(summary, 0);
		Native.free(Pointer.nativeValue(reference));
		Pointer.nativeValue(reference, 0);
		Native.free(Pointer.nativeValue(precodeitem1));
		Pointer.nativeValue(precodeitem1, 0);
		Native.free(Pointer.nativeValue(precodeitem2));
		Pointer.nativeValue(precodeitem2, 0);
		
		type.setValue(0);
		severity.setValue(0);
		holdup.setValue(0);
		msgtype.setValue(0);
		HYTInterface.instanceDll.HYT_UnInitialize(session);
		/*Native.free(Pointer.nativeValue(type.getPointer()));
		Pointer.nativeValue(type.getPointer(), 0);
		Native.free(Pointer.nativeValue(severity.getPointer()));
		Pointer.nativeValue(severity.getPointer(), 0);
		Native.free(Pointer.nativeValue(holdup.getPointer()));
		Pointer.nativeValue(holdup.getPointer(), 0);*/
		/*if(session != null) {
			HYTInterface.instanceDll.HYT_UnInitialize(session);
		}*/
		return _r.toString();
	}
	
	public String getString(Object string) {
		if(string == null) {
			return "";
		}
		return string.toString();
	}
	
	public static String replaceSpecialCharacter(String string) {
		return string.replaceAll("<", "&lt;").
				replaceAll(">", "&gt;").
				replaceAll("&", "&amp;").
				replaceAll("'", "&apos;").
				replaceAll("\"", "&quot;");
	}
}
