package cn.crtech.cooperop.ipc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.JavaScriptEngine;

public class JavaScriptUtil {
	private static JavaScriptEngine engine;
	private static StringBuffer script;
	static {
		
		script = new StringBuffer();
		script.append("function splitBody(){                                 ");         	
		script.append("	  var columns={                                               ");   	
		script.append("	 	    commonName_id : 'name_cn',                            "); 	
		script.append("	 		pinyin_id : 'input_code',                             "); 	
		script.append("	 		character_id : 'durg_state',                          "); 	
		script.append("	 		ESSENTIALCOMPONENT_id : 'ingredients',                "); 	
		script.append("	 		formula_id : 'molecular_formula',                     "); 	
		script.append("	 		molecularWeight_id : 'molecular_weight',              "); 	
		script.append("	 		specification_id : 'spec',                            "); 	
		script.append("	 		indication_id : 'indications',                        "); 	
		script.append("	 		manual_id : 'usage_dosage',                           "); 	
		script.append("	 		adverseReaction_id : 'adverse_reaction',              "); 	
		script.append("	 		contraindication_id : 'taboo',                        "); 	
		script.append("	 		notes_id : 'attentions',                              "); 	
		script.append("	 		FDANotes_id : 'fdanotes_id',                          "); 	
		script.append("	 		pregnantNotes_id : 'drug_pregnant_lactation',         "); 	
		script.append("	 		childrenNote_id : 'drug_children',                    "); 	
		script.append("	 		eldNote_id : 'drug_elderly',                          "); 	
		script.append("	 		medInteraction_id : 'interaction',                    "); 	
		script.append("	 		overdose_id : 'drug_overdose',                        "); 	
		script.append("	 		toxicology_id : 'pharmacology_toxicology',            "); 	
		script.append("	 		pharmacokinetics_id : 'pharmacokinetics',             "); 	
		script.append("	 		keptway_id : 'storage_condition',                     "); 	
		script.append("	 		packageType_id : 'packing',                           "); 	
		script.append("	 		validity_id : 'effective_term',                       "); 	
		script.append("	 		approvalNumber_id : 'approval_number',                "); 	
		script.append("	 		manufactor_id : 'manufacturer',                       "); 	
		script.append("	 		approvalDate_id : 'approval_date'                   "); 	
		script.append("	 };                                                           ");  	
		script.append("	  var $html = $(htmlsplit);                                   ");
		script.append("	 var data = {};                                               ");  	
		script.append("	 for(var k in columns){                                       ");  	
		script.append("	 	data[columns[k]] = $html.find('#'+k).html(); 	  ");
		script.append("	 }                                                            ");
		script.append("	 return data;                                                 ");
		script.append("};   ");
		script.append("redata = splitBody();   ");
	}
	public static Map<String, Object> getFieldsValue(String html){
		try {
			engine = new JavaScriptEngine();
			engine.setParam("htmlsplit", html, true);
			engine.setParam("redata", new HashMap<>(), true);
			engine.excuteString(script.toString());
			Object oo = engine.getParamValue("redata");
			return CommonFun.json2Object(CommonFun.object2Json(oo), Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		System.out.println(getFieldsValue(readToString(null)));
	}
	public static String readToString(String fileName) {
		fileName = "C:\\Users\\lenovo\\Desktop\\drug.txt";
        String encoding = "GBK";  
        File file = new File(fileName);  
        Long filelength = file.length();  
        byte[] filecontent = new byte[filelength.intValue()];  
        try {  
            FileInputStream in = new FileInputStream(file);  
            in.read(filecontent);  
            in.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        try {  
            return new String(filecontent, encoding);  
        } catch (UnsupportedEncodingException e) {  
            System.err.println("The OS does not support " + encoding);  
            e.printStackTrace();  
            return null;  
        }  
    }  
}
