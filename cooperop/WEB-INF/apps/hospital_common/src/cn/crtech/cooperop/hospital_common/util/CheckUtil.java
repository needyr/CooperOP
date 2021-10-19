package cn.crtech.cooperop.hospital_common.util;

import cn.crtech.cooperop.bus.util.CommonFun;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class CheckUtil {
	
	
	/**
	 * 判断字符串是否是数字
	 * 是，return true
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) { 
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$"); 
        return pattern.matcher(str).matches(); 
    }
	
	/**
	 * 判断字符串是否是浮点型
	 * 是，return true
	 * @param str
	 * @return
	 */
	public static boolean isFloat(String str) {  
	     if (null == str || "".equals(str)) {  
	         return false;  
	     }  
	     Pattern pattern = Pattern.compile("^[-\\+]?\\d*[.]\\d+$");  
	     return pattern.matcher(str).matches();  
	}  
	
	
	/**
	 * 获取浮点型str有几位小数
	 * @param str
	 * @return
	 */
	public static int decimalDigits(String str) {
		String a = str;
		int b = a.indexOf(".");
		int tp = 0;
		if(b>0) {
			tp = (a.substring(b+1)).length();
		}
		return tp;
	}
	
	/**
	 * 判断date是否符合时间格式type
	 * 符合  return true
	 * @param date
	 * @param type
	 * @return
	 */
	public static boolean isDate(String date,String type){
		SimpleDateFormat format = new SimpleDateFormat(type);
		format.setLenient(false);
		int length = format.format(new Date()).length();
		if(CommonFun.isNe(date)){
			return true;
		}
		if(length != date.length()){
			return false;
		}
		boolean result = true;
		try {
			format.parse(date);
		} catch (ParseException e) {
			result = false;
		}
		return result;
	}
}
