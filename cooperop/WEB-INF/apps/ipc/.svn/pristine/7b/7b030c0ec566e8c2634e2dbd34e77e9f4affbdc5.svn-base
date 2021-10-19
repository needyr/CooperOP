package cn.crtech.precheck.ipc.ws.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.ipc.service.AutoAuditService;
import cn.crtech.cooperop.ipc.service.SendErrorMsgService;
import cn.crtech.precheck.ipc.service.DataService;
import sun.misc.BASE64Encoder;

public class Client {
	
	public static final String HOSPITAL_ID = SystemConfig.getSystemConfigValue("hospital_common", "hospital_id");//传送医嘱处方给审方平台
	public static final String AUDIT_ACTION = "initiate";//传送医嘱处方给审方平台
	public static final String DOCTOR_DEAL_ACTION = "doctorspass";//传送医生处理结果给审方平台
	public static final String SYNCDATA_ACTION = "uploadDoctors";//同步医院基本信息到平台，医生、单位等，回传数据等
	public static final String SYNC_DOCTOR_ACTION = "syncDoctor";//同步医医生，药师信息（实时同步）
	public static final String SYNC_DATA = "syncComments";//同步处方点评和字典
	public static final String SYNC_USERS_UPDATE = "syncUsersUpdate";//同步修改的用户信息（定时同步）
	public static final String SYNC_SHUOMS_IMG = "syncShuomsImg";//同步说明书图片
	public static final String HLYY_ERROR = "hlyy_error";//同步说明书图片
	public static final String EMAIL_SEND = "sendEmailMessage";//邮件发送
	public static final String PHARMACIST_RESULT = "pharmacistresult";
	public static Map<String, Connection> conns = new HashMap<String, Connection>();
	public static long hlyy_error_date = System.currentTimeMillis();
	public static void init() throws Throwable {
		Connection.init();
		if("Y".equals(SystemConfig.getSystemConfigValue("ipc", "fail2resend", "N"))) {
			RunMessages.init();
			log.debug(" -- loading ipc server 'send failed then resend' ... ");
		}
	}
	private static void send(String doctor_no, String hospital_id, Map<String, Object> params) throws Throwable{
		try {
			Connection c = createConn(doctor_no, hospital_id);
			c.send(params);
		} catch (Throwable e) {
			Client.conns.remove(doctor_no);
			Connection c = createConn(doctor_no, hospital_id);
			c.send(params);
		}
	}
	
	private static Connection createConn(String doctor_no, String hospital_id) throws Throwable{
		if(CommonFun.isNe(conns.get(doctor_no))){
			Connection c = new Connection(doctor_no, hospital_id);
			conns.put(doctor_no, c);
			return c;
		}else{
			return conns.get(doctor_no);
		}
	}
	
	public static boolean sendPrescription(String doctor_no, Map<String, Object> params){
		Map<String, Object> mm = new HashMap<String, Object>();
		mm.put("action", AUDIT_ACTION);
		try {
			if (!CommonFun.isNe(params.get("auto_audit_id"))) {
				mm.put("data", new DataService().queryForYXK(params));
				if (CommonFun.isNe(HOSPITAL_ID)) {
					send((String)doctor_no, (String)params.get("hospital_id"), mm);
				}else {
					send((String)doctor_no, HOSPITAL_ID, mm);
				}
				//new AutoAuditService().updateSendTime(new HashMap<String,Object>());
			}
			return true;
		} catch (Throwable e) {
			log.error(e);
			Connection c = Client.conns.remove(doctor_no);
			if(c != null){
				try {
					c.disconnect();
				} catch (Throwable e1) {
					log.error(e1);
				}
			}
			Message m = new Message();
			m.action = AUDIT_ACTION;
			m.auto_audit_id = (String)params.get("id");
			m.doctor_no = doctor_no;
			m.hospital_id = HOSPITAL_ID;
			m.params = params;
			RunMessages.resend(m);
		}
		return false;
	}
	public static void sendDoctorDeal(String doctor_no, Map<String, Object> params){
		Map<String, Object> mm = new HashMap<String, Object>();
		mm.put("action", DOCTOR_DEAL_ACTION);
		try {
			mm.put("data", params);
			send((String)doctor_no, HOSPITAL_ID, mm);
		} catch (Throwable e) {
			Connection c = Client.conns.remove(doctor_no);
			if(c != null){
				try {
					c.disconnect();
				} catch (Throwable e1) {
					log.error(e1);
				}
			}
			log.error(e);
		}
	}
	
	/**
	 * 合理用药异常消息
	 * @param doctor_no
	 * @param params
	 */
	public synchronized static void sendHLYYError(String doctor_no, Map<String, Object> params){
		long currentTimeMillis = System.currentTimeMillis();
		long d = currentTimeMillis - hlyy_error_date;
		//5分钟发送一次
		if(d > 300000) {
			Map<String, Object> mm = new HashMap<String, Object>();
			mm.put("action", HLYY_ERROR);
			try {
				mm.put("data", params);
				send((String)doctor_no, HOSPITAL_ID, mm);
			} catch (Throwable e) {
				Connection c = Client.conns.remove(doctor_no);
				if(c != null){
					try {
						c.disconnect();
					} catch (Throwable e1) {
						log.error(e1);
					}
				}
				log.error(e);
			}
			hlyy_error_date = currentTimeMillis;
		}
	}
	
	public static void SyncData(String doctor_no, Map<String, Object> params){
		Map<String, Object> mm = new HashMap<String, Object>();
		mm.put("action", SYNC_DATA);
		try {
			mm.put("data", params);
			send((String)doctor_no, HOSPITAL_ID, mm);
		} catch (Throwable e) {
			log.error(e);
		}
	}
	
	/**
	 * 
	 * @param doctor_no 一般为"admin_jq"
	 * @param params
	 * params中的参数</br>
	 *  <b>from</b> 发送人邮箱</br>
	 *  <b>emailAddress</b> 接收人, 多人用英文","隔开</br>
	 *  <b>subject</b> 标题</br>
	 *  <b>emailMsg</b> 消息正文</br>
	 *  <b>address</b> 邮箱服务器地址QQ:smtp.qq.com</br>
	 *  <b>port</b> 邮箱服务器端口:465</br>
	 *  <b>username</b> 邮箱发送方"用户名"</br>
	 *  <b>password</b> 邮箱发送方"授权码"</br>
	 *  <b>isDebug</b> 是否开启debug模式</br>
	 */
	public static void emailSend(String doctor_no, Map<String, Object> params){
		Map<String, Object> mm = new HashMap<String, Object>();
		mm.put("action", EMAIL_SEND);
		try {
			mm.put("data", params);
			send(doctor_no, HOSPITAL_ID, mm);
		} catch (Throwable e) {
			log.error(e);
		}
	}
	
	public static void syncUsersBatch(String doctor_no, Map<String, Object> params){
		try {
			Map<String, Object> mm = new HashMap<String, Object>();
			mm.put("action", SYNC_USERS_UPDATE);
			mm.put("data", params);
			send((String)doctor_no, HOSPITAL_ID, mm);
		} catch (Throwable e) {
			log.error(e);
		}
	}
	
	/**
	 * @param doctor_no 账户 
	 * @param params 医生信息
	 * @author yan
	 * @time 2018年4月28日
	 * @function 同步医生信息到云端
	 */
	public static void syncUser(String doctor_no, Map<String, Object> params){
		Map<String, Object> mm = new HashMap<String, Object>();
		mm.put("action", SYNC_DOCTOR_ACTION);
		try {
			mm.put("data", params);
			send(doctor_no, HOSPITAL_ID, mm);
		} catch (Throwable e) {
			Connection c = Client.conns.remove(doctor_no);
			if(c != null){
				try {
					c.disconnect();
				} catch (Throwable e1) {
					log.error(e1);
				}
			}
			log.error(e);
		}
	}
	
	/**
	 * @param doctor_no 账户 
	 * @param params 图片信息
	 * @author wang
	 * @time 2019年2月13日
	 * @function 同步说明书图片信息到云端
	 */
	public static void syncShuomsImg(){
		//String path = "T:\\crtech\\chaoran-shenfang-gongz\\CooperOP\\cooperop\\WEB-INF\\apps\\hospital_common\\resource\\drugimg";
		URL resource = Thread.currentThread().getContextClassLoader().getResource("/");
		String courseFile = resource.getPath().substring(resource.getPath().lastIndexOf(":")-1, resource.getPath().indexOf("/WEB-INF/")+9).replace("/", "\\");
		String shuoms_img_address = SystemConfig.getSystemConfigValue("ipc", "shuoms_img_address", "apps\\hospital_common\\resource\\drugimg");
		String path = courseFile + shuoms_img_address;
		File file = new File(path);
		List<String> content = new ArrayList<String>();
		if(file.exists()) {
			traverseFile(file,content);//获得所有图片,存入list中
			Map<String, Object> mm = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			mm.put("action", SYNC_SHUOMS_IMG);
			map.put("content", content);
			try {
				mm.put("data", map);
				send("admin_jq", HOSPITAL_ID, mm);
			} catch (Throwable e) {
				Connection c = Client.conns.remove("admin_jq");
				if(c != null){
					try {
						c.disconnect();
					} catch (Throwable e1) {
						log.error(e1);
					}
				}
				log.error(e);
			}
		}
	}
	
	private static class Message {
		protected Map<String, Object> params;
		protected Date time;
		protected String action;
		protected String doctor_no;
		protected String hospital_id;
		protected int times;
		protected String auto_audit_id;
		protected long thread_id = Thread.currentThread().getId();
	}
	
	
	private static class RunMessages extends Thread {
		private static Map<String, Message> msqueue = new ConcurrentHashMap<String, Message>();

		private static boolean running = true;

		private Message message;
		
		public static void init(){
			try {
				//从数据库查询数据放入缓存
				Result list = new SendErrorMsgService().query(new HashMap<String,Object>());
				if(!CommonFun.isNe(list)) {
					for (Record re : list.getResultset()) {
						Message message = new Message();
						message.doctor_no = re.getString("doctor_no");
						message.action = re.getString("action");
						message.auto_audit_id = re.getString("auto_audit_id");
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("auto_audit_id", re.get("auto_audit_id"));
						message.params = map;
						msqueue.put(message.doctor_no+"_"+message.action+"_"+message.auto_audit_id, message);
					}
				}
				new RunMessages().start();
			} catch (Exception e) {
				log.error(e);
			}
		}
		
		protected static void resend(Message message) {
			//synchronized (msqueue) {
			try {
				try {
					sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(msqueue.containsKey((message.doctor_no+"_"+message.action+"_"+message.auto_audit_id))){//重复推送失败
					Message m = msqueue.get(message.doctor_no+"_"+message.action+"_"+message.auto_audit_id);
					m.times = m.times+1;
					log.info("doctor_no:"+message.doctor_no+" action:"+message.action+" auto_audit_id:"+message.auto_audit_id+"推送失败，失败次数："+message.times);
				}else{
					//TODO 第一次推送失败，需要插入数据库
					Map<String, Object> mapin = new HashMap<String, Object>();
					mapin.put("state", 0);
					mapin.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
					mapin.put("action", message.action);
					mapin.put("auto_audit_id", message.auto_audit_id);
					mapin.put("hospital_id", message.hospital_id);
					mapin.put("doctor_no", message.doctor_no);
					try {
						new SendErrorMsgService().insert(mapin);
					} catch (Exception e) {
						log.error(e);
					}
					msqueue.put(message.doctor_no+"_"+message.action+"_"+message.auto_audit_id, message);
				}
			} finally {
				synchronized (msqueue) {
					msqueue.notifyAll();
				}
			}
			//}
		}

		protected static void terminal() {
			running = false;
			synchronized (msqueue) {
				if (msqueue.isEmpty()) {
					msqueue.notifyAll();
				}
			}
		}

		@Override
		public void run() {
			while (running || !msqueue.isEmpty()) {
				//synchronized (msqueue) {
					try {
						if (msqueue.isEmpty()) {
							msqueue.wait();
						}
					} catch (Exception ex) {
						continue;
					}
					message = null;
					if (msqueue.size() > 0) {
						for(String key : msqueue.keySet()){
							Map<String, Object> query_params = new HashMap<String, Object>();
							message = msqueue.get(key);
							if (message == null) {
								continue;
							}
							//根据message中的action发送 选择不同的发送方法
							message.params.put("id", message.params.get("auto_audit_id"));
							message.params.put("hospital_id", message.hospital_id);
							log.debug(" -- 推单失败重发 [" + message.doctor_no + "] 第 " + (message.times + 1) + " 次重发");
							query_params.put("auto_audit_id", message.auto_audit_id);
							query_params.put("doctor_no", message.doctor_no);
							query_params.put("hospital_id", message.hospital_id);
							Record is_send = new Record();
							try {
								is_send = new SendErrorMsgService().getShenFang(query_params);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							if((!CommonFun.isNe(is_send) && is_send.get("state").equals("1") && !CommonFun.isNe(message.auto_audit_id)) || sendPrescription(message.doctor_no, message.params)){
								// 发送成功，更新数据库
								Map<String, Object> mapin = new HashMap<String, Object>();
								mapin.put("state", 1);
								mapin.put("update_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
								mapin.put("action", message.action);
								mapin.put("auto_audit_id", message.auto_audit_id);
								mapin.put("doctor_no", message.doctor_no);
								mapin.put("send_times", message.times + 1);
								try {
									new SendErrorMsgService().update(mapin);
								} catch (Exception e) {
									log.error(e);
								}
								msqueue.remove(key);
							}
						}
					}
			//	}
			}
		}
	}
	
	/**
	 * @Description: 根据File转换为base64编码字符串
	 * @Author: 
	 * @CreateTime: 
	 * @return
	 */
	public static String getImageStrByFile(File imgFile) {
	    InputStream inputStream = null;
	    byte[] data = null;
	    try {
	        inputStream = new FileInputStream(imgFile);
	        data = new byte[inputStream.available()];
	        inputStream.read(data);
	    }catch (IOException e) {
	        e.printStackTrace();
	    }finally {
	    	 try {
	    		 if(inputStream != null) {
	    			 inputStream.close();
	    		 }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
	    // 加密
	    BASE64Encoder encoder = new BASE64Encoder();
	    //去除回车,换行
	    String encode = encoder.encode(data).replaceAll("\r|\n", "");
	    String absolutePath = imgFile.getAbsolutePath();
	    String indexOf = absolutePath.substring(absolutePath.indexOf("resource\\"));
	    return "path:"+indexOf+"," + encode;
	}
	
	/**
	 * 遍历文件
	 * @param imgFile
	 */
	public static void traverseFile(File imgFile, List<String> content) {
		File[] listFiles = imgFile.listFiles();
		for (File file : listFiles) {
			if(file.isDirectory()) {
				traverseFile(file,content);
			}else {
				String imageStr = getImageStrByFile(file);
				content.add(imageStr);
			}
		}
	}
	
	public static void pharmacist_result_return(String doctor_no, Map<String, Object> params) {
		Map<String, Object> mm = new HashMap<String, Object>();
		mm.put("action", PHARMACIST_RESULT);
		try {
			mm.put("data", params);
			send((String)doctor_no, HOSPITAL_ID, mm);
		} catch (Throwable e) {
			Connection c = Client.conns.remove(doctor_no);
			if(c != null){
				try {
					c.disconnect();
				} catch (Throwable e1) {
					log.error(e1);
				}
			}
			log.error(e);
		}
	}
}
