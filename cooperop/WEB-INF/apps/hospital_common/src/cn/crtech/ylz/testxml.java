package cn.crtech.ylz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.crtech.cooperop.bus.util.CommonFun;

public class testxml {
	
	public static void main(String[] args) {
		File file = new File("F:/ddd2.txt");
		String txt2String = txt2String(file);
		String[] split = replaceBlank(txt2String).split("&&");
		int i = 0;
		StringBuffer list = new StringBuffer();
		System.out.println(split[0]);
		for (String string : split) {
			//System.out.println(i++ + "=================" );
			//System.out.println(string);
		  if(!CommonFun.isNe(string)) {
			  Document doc = null; try { doc = DocumentHelper.parseText(string); } catch
			  (DocumentException e) { // TODO Auto-generated catch block
			  e.printStackTrace(); } JSONObject json=new JSONObject();
			  dom4j2Json(doc.getRootElement(), json);
			  
			  
			  String string2 = json.toString();
			  if(!CommonFun.isNe(string2)) {
				  if(i == 0) {
					  i++;
					  list.append("\"{\"\"request\"\":"+string2.replaceAll("%", "%25").replaceAll("\"", "\"\"")+"}\","+i);
				  }else {
					  i++;
					  list.append( "\r\n"+ "\"{\"\"request\"\":"+ string2.replaceAll("%", "%25").replaceAll("\"", "\"\"")+"}\","+i);
				  }
			  }
		  }
		}
		//System.out.println(list.toString());
		File fp=new File("F:\\222.txt");
        PrintWriter pfp = null;
		try {
			pfp = new PrintWriter(fp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        pfp.print(list.toString());
        pfp.close();
	}
	
	
	
	public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
	
	public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
	
	public static boolean isEmpty(String str) {

        if (str == null || str.trim().isEmpty() || "null".equals(str)) {
            return true;
        }
        return false;
    }
	
	public static void dom4j2Json(Element element,JSONObject json){
        //如果是属性
        for(Object o:element.attributes()){
            Attribute attr=(Attribute)o;
            if(!isEmpty(attr.getValue())){
                json.put("@"+attr.getName(), attr.getValue());
            }
        }
        List<Element> chdEl=element.elements();
        if(chdEl.isEmpty()&&!isEmpty(element.getText())){//如果没有子元素,只有一个值
            json.put(element.getName(), element.getText());
        }

        for(Element e:chdEl){//有子元素
            if(!e.elements().isEmpty()){//子元素也有子元素
                JSONObject chdjson=new JSONObject();
                dom4j2Json(e,chdjson);
                Object o=json.get(e.getName());
                if(o!=null){
                    JSONArray jsona=null;
                    if(o instanceof JSONObject){//如果此元素已存在,则转为jsonArray
                        JSONObject jsono=(JSONObject)o;
                        json.remove(e.getName());
                        jsona=new JSONArray();
                        jsona.add(jsono);
                        jsona.add(chdjson);
                    }
                    if(o instanceof JSONArray){
                        jsona=(JSONArray)o;
                        jsona.add(chdjson);
                    }
                    json.put(e.getName(), jsona);
                }else{
                    if(!chdjson.isEmpty()){
                        json.put(e.getName(), chdjson);
                    }
                }


            }else{//子元素没有子元素
                for(Object o:element.attributes()){
                    Attribute attr=(Attribute)o;
                    if(!isEmpty(attr.getValue())){
                        json.put("@"+attr.getName(), attr.getValue());
                    }
                }
                if(!e.getText().isEmpty()){
                    json.put(e.getName(), e.getText());
                }
            }
        }
    }
}
