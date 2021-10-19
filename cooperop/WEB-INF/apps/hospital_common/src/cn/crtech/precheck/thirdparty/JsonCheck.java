package cn.crtech.precheck.thirdparty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.util.CommonFun;

public class JsonCheck {
		public static void main(String[] args) throws Exception {
			/*File file = new File("E:\\Study\\Java\\test.txt");// 指定要读取的文件  
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file)); 
			String line = "";// 用来保存每次读取一行的内容  
			StringBuilder tp = new StringBuilder();
		    while ((line = bufferedReader.readLine()) != null) {  
		        tp.append(line);
		    }  
		    bufferedReader.close();// 关闭输入流 
			
			System.out.println(JSON.toJSONString(detection(tp.toString()), true)); */
			
			//System.out.println(isDate1("2019-05-09"));
//			System.out.println(isDouble("222.223"));
			//System.out.println(isDate2("2019-05-09 11:10:00"));
		
		}

	private static final String PROMPT1 = "数据格式不标准,解析错误请检查:";
	private static final String PROMPT2 = "节点不能为空";

	@SuppressWarnings("unchecked")
	public static Map<String, Object> detection(String json) {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean is_err = false;// 默认没有错误
		Map<String, Object> err = new HashMap<String, Object>();
		
		if(CommonFun.isNe(json)) {
			is_err = true;
			err.put("json", "json串不能为空");
			result.put("status", is_err);
			result.put("err", err);
			return err;
		}
		
		Map<String, Object> p = null;
		
		try {
			p = CommonFun.json2Object(json, Map.class);
		} catch (Exception e) {
			is_err = true;
			err.put("json", "json串不标准,解析错误!" + e.getMessage());
			result.put("status", is_err);
			result.put("err", err);
			return err;
		}
		
		if(SystemConfig.getSystemConfigValue("ipc", "ipc.thirdparty.prompt", "0") == "0") {
			result.put("status", is_err);
			result.put("err", err);
			return err;
		}
		
		//p_type
		if (CommonFun.isNe(p.get("p_type"))) {
			is_err = true;
			err.put("p_type","value is required");
		}else if(!"1".equals(p.get("p_type")) && !"2".equals(p.get("p_type"))) {
			is_err = true;
			err.put("p_type","Wrong value!only 1 or 2 !");
		}
		
		//d_type
		if (CommonFun.isNe(p.get("d_type"))) {
			is_err = true;
			err.put("d_type","value is required");
		}else if(!"1".equals(p.get("d_type")) && !"2".equals(p.get("d_type")) && !"3".equals(p.get("d_type"))) {
			is_err = true;
			err.put("d_type","Wrong value!only 1 or 2 or 3 !");
		}
		
		//doctor
//		Map<String, Object> doctor = null;
//		try {
//			doctor = (Map<String, Object>) p.get("doctor");
//		} catch (Exception e) {
//			is_err = true;
//			err.put("doctor", PROMPT1 + e.getMessage());
//		}
		
		//patient
		Map<String, Object> patient = null;
		if(CommonFun.isNe(p.get("patient"))) {
			is_err = true;
			err.put("patient",PROMPT2);
		}else {
			try {
				patient = (Map<String, Object>) p.get("patient");
				
				if(CommonFun.isNe(patient.get("id"))) {
					is_err = true;
					err.put("patient.id",PROMPT2);
				}
				
				if(CommonFun.isNe(patient.get("visitid"))) {
					is_err = true;
					err.put("patient.visitid",PROMPT2);
				}
				
				if(CommonFun.isNe(patient.get("sex"))) {
					is_err = true;
					err.put("patient.sex",PROMPT2);
				}else if(!("男".equals(patient.get("sex"))) &&
						!"女".equals(patient.get("sex")) &&
						!"未知".equals(patient.get("sex"))){
					is_err = true;
					err.put("patient.sex", "Wrong value!only 男 or 女 or 未知");
				}
				
				if(CommonFun.isNe(patient.get("admissiondate"))) {
					is_err = true;
					err.put("patient.admissiondate",PROMPT2);
				}else{
					try {
						String admissiondate = (String)patient.get("admissiondate");
						if (!isDate2(admissiondate)) {
							is_err = true;
							err.put("patient.admissiondate", "格式错误必须为.YYYY-MM-DD HH:mm:ss");
						}
					} catch (Exception e) {
						is_err = true;
						err.put("patient.admissiondate", PROMPT1 + e.getMessage());
					}
				}
				
				if(CommonFun.isNe(patient.get("birthday"))) {
					is_err = true;
					err.put("patient.birthday",PROMPT2);
				}else{
					try {
						String birthday = (String)patient.get("birthday");
						if (!isDate1(birthday)) {
							is_err = true;
							err.put("patient.birthday", "格式错误必须为.YYYY-MM-DD");
						}
					} catch (Exception e) {
						is_err = true;
						err.put("patient.birthday", PROMPT1 + e.getMessage());
					}
				}
				
				if(!CommonFun.isNe(patient.get("weight"))) {
					try {
						if(!isNumeric((String) patient.get("weight"))) {
							is_err = true;
							err.put("patient.weight", "必须为int格式");
						}
					} catch (Exception e) {
						is_err = true;
						err.put("patient.weight", PROMPT1 + e.getMessage());
					}
				}
				
				if(!CommonFun.isNe(patient.get("height"))) {
					try {
						if(!isDouble((String) patient.get("height"))) {
							is_err = true;
							err.put("patient.height", "必须为Double格式");
						}
					} catch (Exception e) {
						is_err = true;
						err.put("patient.height", PROMPT1 + e.getMessage());
					}
				}
			} catch (Exception e) {
				is_err = true;
				err.put("patient", PROMPT1 + e.getMessage());
			}
		}
		
		//orders
		ArrayList<Map<String, Object>> orders = null;
		if (CommonFun.isNe(p.get("orders"))) {
			is_err = true;
			err.put("orders",PROMPT2);
		}else {
			try {
				orders = (ArrayList<Map<String,Object>>)p.get("orders");
				for (Map<String, Object> map : orders) {
					if (CommonFun.isNe(map.get("p_key"))) {
						is_err = true;
						err.put("orders.p_key", PROMPT2);
					}
					
					if (CommonFun.isNe(map.get("repeat_indicator"))) {
						is_err = true;
						err.put("orders.repeat_indicator",PROMPT2);
					}else if(!"0".equals(map.get("repeat_indicator")) && !"1".equals(map.get("repeat_indicator"))) {
						is_err = true;
						err.put("orders.repeat_indicator","Wrong value!only 0 or 1 !");
					}
					
					if (CommonFun.isNe(map.get("start_date_time"))) {
						is_err = true;
						err.put("orders.start_date_time", PROMPT2);
					}else {
						try {
							String start_date_time = (String)map.get("start_date_time");
							if (!isDate2(start_date_time)) {
								is_err = true;
								err.put("orders.start_date_time", "格式错误必须为.YYYY-MM-DD HH:mm:ss");
							}
						} catch (Exception e) {
							is_err = true;
							err.put("orders.start_date_time", PROMPT1 + e.getMessage());
						}
					}
					
					if (!CommonFun.isNe(map.get("stop_date_time"))) {
						try {
							String stop_date_time = (String)map.get("stop_date_time");
							if (!isDate2(stop_date_time)) {
								is_err = true;
								err.put("orders.stop_date_time", "格式错误必须为.YYYY-MM-DD HH:mm:ss");
							}
						} catch (Exception e) {
							is_err = true;
							err.put("orders.stop_date_time", PROMPT1 + e.getMessage());
						}
					}
					
					if (CommonFun.isNe(map.get("order_code"))) {
						is_err = true;
						err.put("orders.order_code", PROMPT2);
					}
					
					if (CommonFun.isNe(map.get("order_text"))) {
						is_err = true;
						err.put("orders.order_text", PROMPT2);
					}
					
					if (CommonFun.isNe(map.get("group_id"))) {
						is_err = true;
						err.put("orders.group_id", PROMPT2);
					}
					
					if (CommonFun.isNe(map.get("order_no"))) {
						is_err = true;
						err.put("orders.order_no", PROMPT2);
					}
					
					if (CommonFun.isNe(map.get("order_sub_no"))) {
						is_err = true;
						err.put("orders.order_sub_no", PROMPT2);
					}
					
					if (CommonFun.isNe(map.get("dosage"))) {
						is_err = true;
						err.put("orders.dosage", PROMPT2);
					}else {
						try {
							if (!isDouble((String)map.get("dosage"))) {
								is_err = true;
								err.put("orders.dosage", "必须为Double格式");
							}
						} catch (Exception e) {
							is_err = true;
							err.put("orders.dosage", PROMPT1 + e.getMessage());
						}
					}
					
					if(CommonFun.isNe(map.get("dosage_units"))) {
						is_err = true;
						err.put("orders.dosage_units", PROMPT2);
					}
					
					
					if (!CommonFun.isNe(map.get("amount"))) {
                        if ("2".equals((String)p.get("d_type"))) {
                        	try {
    							if (!isDouble((String)map.get("amount"))) {
    								is_err = true;
    								err.put("orders.amount","必须为Double格式");
    							}
    						} catch (Exception e) {
    							is_err = true;
    							err.put("orders.amount",PROMPT1 + e.getMessage());
    						}
						}
					}
					
					if (CommonFun.isNe(map.get("administration"))) {
						is_err = true;
						err.put("orders.administration", PROMPT2);
					}
					
					if (CommonFun.isNe(map.get("frequency"))) {
						is_err = true;
						err.put("orders.administration", PROMPT2);
					}
					
				}
			} catch (Exception e) {
				is_err = true;
				err.put("orders", PROMPT1 + e.getMessage());
			}
		}
		
		
		//diagnosis
		ArrayList<Map<String, Object>> diagnosis = null;
		if (CommonFun.isNe(p.get("diagnosis"))) {
			//is_err = true;
			//err.put("diagnosis", PROMPT2);
		}else {
			try {
				diagnosis = (ArrayList<Map<String,Object>>)p.get("diagnosis");
				for (Map<String, Object> map : diagnosis) {
					
					if (CommonFun.isNe(map.get("diagnosecode"))) {
						is_err = true;
						err.put("diagnosis.diagnosecode", PROMPT2);
					}
					
					if (CommonFun.isNe(map.get("diagnosis_date"))) {
						is_err = true;
						err.put("diagnosis.diagnosis_date", PROMPT2);
					}else {
						try {
							String diagnosis_date = (String)map.get("diagnosis_date");
							if (!isDate2(diagnosis_date)) {
								is_err = true;
								err.put("diagnosis.diagnosis_date", "格式错误必须为.YYYY-MM-DD HH:mm:ss");
							}
						} catch (Exception e) {
							is_err = true;
							err.put("diagnosis.diagnosis_date", PROMPT1 + e.getMessage());
						}
					}
					
				}
			} catch (Exception e) {
				is_err = true;
				err.put("diagnosis", PROMPT1 + e.getMessage());
			}
		}
		
		//oper
		ArrayList<Map<String, Object>> oper = null;
		if (CommonFun.isNe(p.get("oper"))) {
			//is_err = true;
			//err.put("oper", PROMPT2);
		}else {
			try {
				oper = (ArrayList<Map<String,Object>>)p.get("oper");
				for (Map<String, Object> map : oper) {
					
					if (CommonFun.isNe(map.get("operid"))) {
						is_err = true;
						err.put("oper.operid", PROMPT2);
					}
					
					if (CommonFun.isNe(map.get("qktype"))) {
						is_err = true;
						err.put("oper.qktype", PROMPT2);
					}else {
						String qktype = (String)map.get("qktype");
						if (!("I".equals(qktype) || "II".equals(qktype) || "III".equals(qktype))) {
							is_err = true;
							err.put("oper.qktype", "Wrong value!only I or II or III");
						}
					}
					
					if (CommonFun.isNe(map.get("begintime"))) {
						is_err = true;
						err.put("oper.begintime", PROMPT2);
					}else {
						try {
							String begintime = (String)map.get("begintime");
							if (!isDate2(begintime)) {
								is_err = true;
								err.put("oper.begintime", "格式错误必须为.YYYY-MM-DD HH:mm:ss");
							}
						} catch (Exception e) {
							is_err = true;
							err.put("oper.begintime", PROMPT1 + e.getMessage());
						}
					}
					
					if (CommonFun.isNe(map.get("endtime"))) {
						is_err = true;
						err.put("oper.endtime", PROMPT2);
					}else {
						try {
							String endtime = (String)map.get("endtime");
							if (!isDate2(endtime)) {
								is_err = true;
								err.put("oper.endtime", "格式错误必须为.YYYY-MM-DD HH:mm:ss");
							}
						} catch (Exception e) {
							is_err = true;
							err.put("oper.endtime", PROMPT1 + e.getMessage());
						}
					}
					
				}
			} catch (Exception e) {
				is_err = true;
				err.put("oper", PROMPT1 + e.getMessage());
			}
		}
		
		//allergy
		ArrayList<Map<String, Object>> allergy = null;
		if (CommonFun.isNe(p.get("allergy"))) {
			//is_err = true;
			//err.put("allergy", PROMPT2);
		}else {
			allergy = (ArrayList<Map<String,Object>>)p.get("allergy");
			for (Map<String, Object> map : allergy) {
				if (CommonFun.isNe(map.get("allergycode"))) {
					is_err = true;
					err.put("allergy.allergycode", PROMPT2);
				}
			}
		}
		
		//physiologyinfo
		ArrayList<Map<String, Object>> physiologyinfo = null;
		if (CommonFun.isNe(p.get("physiologyinfo"))) {
			//is_err = true;
			//err.put("physiologyinfo", PROMPT2);
		}else {
			physiologyinfo = (ArrayList<Map<String,Object>>)p.get("physiologyinfo");
			for (Map<String, Object> map : physiologyinfo) {
				if (CommonFun.isNe(map.get("physiologyinfo_code"))) {
					is_err = true;
					err.put("physiologyinfo.physiologyinfo_code", PROMPT2);
				}
				
				if (CommonFun.isNe(map.get("physiologyinfo_name"))) {
					is_err = true;
					err.put("physiologyinfo.physiologyinfo_name", PROMPT2);
				}
			}
		}
		
		
		if (is_err) {
			result.put("status", true);
			result.put("err", err);
		}else {
			result.put("status", false);
		}
		return result;
	}
	
	public static boolean isDate1(String date)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		boolean result = true;
		try {
			format.parse(date);
		} catch (ParseException e) {
			result = false;
		}
		return result;
	}
	public static boolean isDate2(String date)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setLenient(false);
		boolean result = true;
		try {
			format.parse(date);
		} catch (ParseException e) {
			result = false;
		}
		return result;
	}
	
	public static boolean isNumeric(String str){
	   Pattern pattern = Pattern.compile("[0-9]*");
	   Matcher isNum = pattern.matcher(str);
	   if( !isNum.matches() ){
	       return false;
	   }
	   return true;
	}
	
	public static boolean isDouble(String str){
	     Pattern pattern = Pattern.compile("[0-9]+[.]{0,1}[0-9]*[dD]{0,1}");
	     Matcher isNum = pattern.matcher(str);
	     if( !isNum.matches() ){
	        return false;
	     }
	     return true;
	}
}

