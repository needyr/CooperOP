package cn.crtech.ylz;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**  
* <p>统一控制打印日志，输入控制台，
* 禁止在程序其他位置使用log.debug</p>
* @author ygz 2019-07-25
*/
public class ylz {
	/** INFO 正常的输出信息  */
	private static final int INFO = 1;
	
	/** WARN 警示信息  */
	private static final int WARN = 2;
	
	/** DEBUG 用于帮助调试的信息  */
	private static final int DEBUG = 3;
	
	/** ERROR 严重错误  */
	private static final int ERROR = 4;
	
	/** 需要打印的等级  */
	private static final int PRINT_LEVEL = 1;
	
	/** 需要记录日志的等级  */
	private static final int LOG_LEVEL = 4;
	
	/** 输出红色的日志等级  */
	private static final int RED_LEVEL = 2;
	
	/** 是否打印/记录作者  */
	private static final boolean AUTHOR_SHOW = true;
	
	private static Map<String, StringBuffer> rMap = new ConcurrentHashMap<String, StringBuffer>();
	
	private static Map<String, Long> timeMap = new ConcurrentHashMap<String, Long>();
	
	//private static boolean is_rMsg = Boolean.getBoolean(GlobalVar.getSystemProperty("clog.record", "true"));
	private static boolean is_rMsg = true;
	// 显示调用者
	private static boolean SHOWCALLER = false;
	
		/**
	    * @Method p
		* @param description 描述
	    * @Description 打印日志[默认为info的等级]
		* @author ygz  2019-07-25
	    */
	public static void p(String description) {
		if(SHOWCALLER) {
			String callclass = new Exception().getStackTrace()[1].getClassName();
			String callmethod = new Exception().getStackTrace()[1].getMethodName();
			p(callclass + ".." + callmethod, INFO, description);
		}else {
			p(null, INFO, description);
		}	
	}
	
		/**
	    * @Method p
		* @param author 作者
		* @param description 描述
	    * @Description 打印日志[作者，描述，默认为info等级]
		* @author yanguozhi  2019-07-25
	    */
	public static void p(String author, String description) {
		p(author, INFO , description);
	}
	
	/**
	    * @Method p
		* @param description 描述
		* @param level 等级
	    * @Description 打印日志[作者，等级]
		* @author yanguozhi  2019-07-25
	    */
	public static void p(int level, String description) {
		if(SHOWCALLER) {
			String callclass = new Exception().getStackTrace()[1].getClassName();
			String callmethod = new Exception().getStackTrace()[1].getMethodName();
			p(callclass + ".." + callmethod, level, description);
		}else {
			p(null, level, description);
		}	
	}

		/**
	    * @Method p
		* @param author 作者
		* @param description  描述
		* @param level 信息级别
	    * @Description 打印日志[作者，描述，日志等级]
		* @author yanguozhi  2019-07-25
	    */
	public static void p(String author, int level, String description) {
		if(AUTHOR_SHOW && author !=null) {
			description = author + "[" + description + "]";
		}
		StringBuffer sb = new StringBuffer();
		switch(level) {
			case INFO:
				sb.append(" --- info: ").append(description).append(" ---");break;
			case WARN: 
				sb.append(" === warn: ").append(description).append(" ===");break;
			case DEBUG: 
				sb.append(" --- debug ---\n");
				sb.append(description);
				sb.append("\n === debug ===");break;
			case ERROR:
				sb.append(" === error ===\n");
				sb.append(description);
				sb.append("\n === error ===");break;
			default: 
				sb.append(" === 日志级别设置错误,当前配置 ===");
				sb.append("\n * 日志级别[info:1, warn:2, debug:3, error:4]");
				sb.append("\n * 达到[" + PRINT_LEVEL +"]的日志将会被输出到控制台");
				sb.append("\n * 达到[" + RED_LEVEL +"]的日志将会输出为红色日志");
				sb.append("\n * 达到[" + LOG_LEVEL +"]的日志将会被记录到日志文件,可能耗时约300ms");
				sb.append("\n * 是否输出日志作者：[" + AUTHOR_SHOW +"]");
				sb.append("\n ====================");
				break;
		}
		if(level >= PRINT_LEVEL) {
			if(level >= RED_LEVEL) {
				System.err.println(sb.toString());
			}else {
				System.out.println(sb.toString());
			}
		}
		if(level >= LOG_LEVEL && level <= ERROR) {
			//log.error(sb.toString());
		}
	}
	
	// msg_head 是否为消息串头（消息列的开头）
	public static void recordMsg(boolean msg_head, String id, String msg) {
		if(is_rMsg && msg_head) {
			timeMap.put(id, System.currentTimeMillis());
			rMap.put(id, new StringBuffer().append(msg));
		}
	}
	
	// 消息体拼接
	public static void recordMsg(String id, String msg) {
		if(rMap.containsKey(id)) {
			rMap.put(id, rMap.get(id).append(";\n^" + msg + " [" + (System.currentTimeMillis() - timeMap.get(id)) + "]ms"));
		}
	}
	
	// 获取消息
	public static String getMsg(String id) {
		if(rMap.containsKey(id)) {
			return rMap.get(id).toString() + ";\n[内存中有" + rMap.size() + "个" +"clog,"+ timeMap.size() +"个clog_time]";
		}else {
			return "no record !";
		}
	}
	
	//将消息移出内存
	public static String removeMsg(String id) {
		String clog = "";
		if(rMap.containsKey(id)) {
			timeMap.remove(id);
			clog = rMap.remove(id).toString() + ";\n[内存中还有" + rMap.size() + "个" +"clog,"+ timeMap.size() +"个clog_time]";
			//防止内存无限增长，溢出
			if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == 23 && rMap.size() > 50) {
				rMap.clear();
				timeMap.clear();
			}
		}else {
			clog = "no record !";
		}
		return clog;
	}
	
	public static void main(String[] args) {
		//long s = System.currentTimeMillis();
		// 描述
		//ylz.p("默认info日志");
		// 作者+描述
		//ylz.p("yan", "添加了作者的info日志");
		// 等级 + 描述
		//ylz.p(2, "标识了的等级的warn日志");
		// 作者 + 等级 + 描述
		//ylz.p("yan", 4, "标识了作者的error日志");
		//ylz.p("yan", 4, "超出了设置等级");
		//log.debug(System.currentTimeMillis() - s);
	}
	
	/** 将内容写入文件 filename = path + filename */
	public static int writeFile(String content, String filename) throws Exception {
		int success = 1;
		try {
			StringBuffer sb=new StringBuffer();
			String path=null;
			sb.append(content);
			FileOutputStream out = null;
			try {
				path = filename;
				File file=new File(path);
		         if(!file.exists()) {
		        	 file.createNewFile();
		        	 out =new FileOutputStream(file,true);        
			         out.write(sb.toString().getBytes("utf-8"));
		         }
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(out != null) {
					out.close();
				}
			}
			ylz.p("请求参数已写入目录:" + path);
		} catch (Exception e) {
			success = 0;
			e.printStackTrace();
		}
		return success;
	}
}
