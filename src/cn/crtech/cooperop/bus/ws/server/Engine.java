package cn.crtech.cooperop.bus.ws.server;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.jdom.Document;
import org.jdom.Element;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.ws.bean.ClientInfo;
import cn.crtech.cooperop.bus.ws.bean.Message;

@ServerEndpoint(value = "/ws")
public class Engine {
	public static final String TOKEN_KEY = "_cooperopws_token_";
	public static final String APP_KEY = "_cooperopws_app_";
	private static Map<String, String> applications = new HashMap<String, String>();
	private static Map<String, Connection> connections = new HashMap<String, Connection>();

	private Connection connection;

	private ClientInfo parseSession(Session session) {
		Map<String, List<String>> req = session.getRequestParameterMap();
		Record params = new Record();

		Iterator<String> keys = req.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			if (req.get(key).size() == 1) {
				params.put(key, req.get(key).get(0));
			} else if (req.get(key).size() > 1) {
				params.put(key, req.get(key));
			} else {
				params.put(key, null);
			}
		}
		if (params.containsKey(TOKEN_KEY) && params.containsKey(APP_KEY)) {
			ClientInfo rtn = new ClientInfo();
			rtn.setAppid(params.remove(APP_KEY).toString());
			rtn.setToken(params.remove(TOKEN_KEY).toString());

			rtn.setRequest(params);
			return rtn;
		}
		return null;
	}

	public static void init() throws Exception {
		String config = GlobalVar.getSystemProperty("websocket.config");
		if(config == null ){
			return ;
		}
		if (config.charAt(0) != '/' && config.charAt(1) != ':') {
			config = GlobalVar.getWorkPath() + File.separator + config;
		}
		Document doc = CommonFun.loadXMLFile(config);
		Element root = doc.getRootElement();
		List<?> apps = root.getChildren("application");
		for (int i = 0; i < apps.size(); i++) {
			Element app = (Element) apps.get(i);
			applications.put(app.getChildTextTrim("id"), app.getChildTextTrim("driver"));
		}

//		File f = new File(getResourcePath(), "wsconns");
//		if (!f.exists())
//			f.mkdirs();
//		File[] fcs = f.listFiles(new FileFilter() {
//			@Override
//			public boolean accept(File file) {
//				return file.isFile();
//			}
//		});
//		if (fcs != null) {
//			for (File fc : fcs) {
//				loadClientFile(fc);
//			}
//		}
	}

//	@SuppressWarnings("unchecked")
//	private static void loadClientFile(File f) {
//		FileInputStream fis = null;
//		ObjectInputStream ois = null;
//		boolean deletefile = false;
//		try {
//			fis = new FileInputStream(f);
//			ois = new ObjectInputStream(fis);
//			Map<String, Object> session = (Map<String, Object>) ois.readObject();
//			Class<?> c = Engine.class.getClassLoader().loadClass((String) session.get("clazz"));
//			Constructor<?> cs = c.getConstructor(String.class);
//			Connection cc = (Connection) cs.newInstance(f.getName());
//			cc.fill((ClientInfo) session.get("clientInfo"));
//			connections.put(f.getName(), cc);
//		} catch (Exception ex) {
//			log.error("读取WS会话缓存文件失败", ex);
//			deletefile = true;
//		} finally {
//			if (ois != null) {
//				try {
//					ois.close();
//				} catch (IOException e) {
//				}
//			}
//			if (fis != null) {
//				try {
//					fis.close();
//				} catch (IOException e) {
//				}
//			}
//			if (deletefile) {
//				f.delete();
//			}
//		}
//	}
//
//	private static void saveClientFile(Connection c) {
//		File f = new File(getResourcePath(), "wsconns");
//		if (!f.exists())
//			f.mkdirs();
//		File sessionfile = new File(f, c.getToken());
//		if (sessionfile.exists()) {
//			sessionfile.delete();
//		}
//		FileOutputStream fis = null;
//		ObjectOutputStream ois = null;
//		try {
//			fis = new FileOutputStream(sessionfile);
//			ois = new ObjectOutputStream(fis);
//			ois.writeObject(c.serialize());
//		} catch (Exception ex) {
//		} finally {
//			if (ois != null) {
//				try {
//					ois.close();
//				} catch (IOException e) {
//				}
//			}
//			if (fis != null) {
//				try {
//					fis.close();
//				} catch (IOException e) {
//				}
//			}
//		}
//	}
//
//	private static void deleteClientFile(Connection c) {
//		File f = new File(getResourcePath(), "wsconns");
//		if (!f.exists())
//			f.mkdirs();
//		File sessionfile = new File(f, c.getToken());
//		if (sessionfile.exists()) {
//			sessionfile.delete();
//		}
//	}

	public static void kickOutClient(String token) throws Exception {
		disconnect(token, "kick out");
	}

	public static void logoutClient(String token) throws Exception {
		disconnect(token, "logout");
	}

	private void info(String message) {
		log.info("处理线程-" + Thread.currentThread().getId() + "-" + this + message);
	}

	private void error(String message, Throwable e) {
		log.error("处理线程-" + Thread.currentThread().getId() + "-" + this + message, e);
	}

	/**
	 * 建立链接
	 * 
	 * @param session
	 * @throws IOException
	 */
	@OnOpen
	public void onOpen(Session session) throws IOException {
		info("接收到来自" + session.getId() + "的链接: " + session.getRequestURI());
		ClientInfo req = parseSession(session);
		if (req != null && applications.containsKey(req.getAppid())) {
			try {
				//若存在已有连接，则踢掉后重新记录
				disconnect(req.getToken(), "kick out");
				Class<?> c = Engine.class.getClassLoader().loadClass(applications.get(req.getAppid()));
				Constructor<?> cs = c.getConstructor(String.class);
				connection = (Connection) cs.newInstance((String) req.getToken());
				connections.put(connection.getToken(), connection);
				connection.fill(req);
				connection.setSession(session);
				connection.onConnect();
//				saveClientFile(connection);
				info("连接校验成功, id=" + session.getId() + ", connection= " + connection.serialize());
			} catch (Exception e1) {
				info("连接校验失败, id=" + session.getId() + ", " + session.getRequestURI() + ": " + req);
				try {
					session.close(
							new CloseReason(CloseReason.CloseCodes.TLS_HANDSHAKE_FAILURE, "connection valid failed."));
				} catch (IOException e) {
					session.close();
				}
				return;
			}
			try {
				Message rtn = new Message();
				rtn.setType(Message.TYPE_CONNECTBACK);
				rtn.setParams(req.getUserinfo());
				session.getBasicRemote().sendText(CommonFun.object2Json(rtn));
			} catch (Throwable e) {
				error("连接校验成功但返回连接用户信息失败, id=" + session.getId() + ", connection= " + connection.serialize()
						+ ", error: " + e.getMessage(), e);
				try {
					session.close(new CloseReason(CloseReason.CloseCodes.TLS_HANDSHAKE_FAILURE, e.getMessage()));
				} catch (IOException ex) {
					session.close();
				}
			}
		} else {
			info("连接校验失败, id=" + session.getId() + ", " + session.getRequestURI() + ": " + req);
			try {
				session.close(
						new CloseReason(CloseReason.CloseCodes.TLS_HANDSHAKE_FAILURE, "connection valid failed."));
			} catch (IOException e) {
				session.close();
			}
		}
	}

	private StringBuffer tmessage = null;
	private Map<String, Message> messagequenes = new HashMap<String, Message>();
	private Map<String, List<String>> totokens = new HashMap<String, List<String>>();
	private Message tblob = null;

	private void onMessage(String message_id) {
		if (messagequenes.containsKey(message_id)) {
			Message message = messagequenes.get(message_id);
			message.setState(Message.STATE_END);
			try {
				Object rtn = connection.onMessage(message.getParams());
				try {
					message.setParams(rtn);
					sendMessage(connection.getToken(), message);
				} catch (Exception e) {
					error("返回消息失败：" + e.getMessage(), e);
				}
			} catch (Exception e) {
				error("消息处理异常" + e.getMessage(), e);
				Message error = new Message();
				error.setType(Message.TYPE_ERROR);
				error.setId(message_id);
				error.setState(e.getMessage());
				try {
					syncSendText(connection, CommonFun.object2Json(error));
				} catch (Exception ex) {
					error("反馈" + connection.getSession().getId() + "消息处理的异常信息异常" + ex.getMessage(), ex);
				}
			} finally {
				CommonFun.deleteFile(new File(getResourcePath(), message.getId()));
				messagequenes.remove(message_id);
				totokens.remove(message_id);
			}
		}
	}

	/**
	 * 接收消息
	 * 
	 * @param message
	 * @param session
	 */
	@SuppressWarnings("unchecked")
	@OnMessage
	public void onTextMessage(Session session, String message, boolean last) {
		info("接收到来自" + session.getId() + "的文本消息[" + last + "]：" + message);
		if (tmessage == null) {
			tmessage = new StringBuffer();
		}
		tmessage.append(message);
		if (last) {
			message = tmessage.toString();
			tmessage = null;
			Message tmsg = new Message(message);
			if (tmsg != null) {
				if (Message.TYPE_HEARTBEAT.equals(tmsg.getType())) {
					try {
						syncSendText(connection, CommonFun.object2Json(tmsg));
					} catch (Exception ex) {
						error("反馈" + connection.getSession().getId() + "心跳异常" + ex.getMessage(), ex);
					}
				} else if (Message.TYPE_TEXT.equals(tmsg.getType())) {
					tmsg.setFrom(connection.getClientInfo());
					if (!messagequenes.containsKey(tmsg.getId())) {
						messagequenes.put(tmsg.getId(), tmsg);
						totokens.put(tmsg.getId(), connection.list2Tokens(tmsg.getParams()));
					}

					List<String> tokens = totokens.get(tmsg.getId());
					if (tokens != null) {
						for (String token : tokens) {
							Connection c = connections.get(token);
							if (c != null && c.getAppId().equals(connection.getAppId())) {
								try {
									syncSendText(c, CommonFun.object2Json(tmsg));
								} catch (Exception e) {
									error("转发消息失败：" + e.getMessage() + ", message= " + CommonFun.object2Json(tmsg),
											e);
								}
							}
						}
					}
					
					if (!tmsg.isMulti()) {
						onMessage(tmsg.getId());
					} else if (Message.STATE_END.equals(tmsg.getState())) {
						onMessage(tmsg.getId());
					}

				} else if (Message.TYPE_BLOB.equals(tmsg.getType())) {
					if (Message.STATE_BEGIN.equals(tmsg.getState())) {
						tblob = tmsg;
						File f = new File(getResourcePath(), tblob.getMsgid() + File.separator + tblob.getId());
						if (!f.getParentFile().exists()) {
							f.getParentFile().mkdirs();
						}
						if (f.exists()) {
							f.delete();
						}
						tblob.getBlob().setFile(f);
					} else if (Message.STATE_END.equals(tmsg.getState())) {
						if (tblob.getId().equals(tmsg.getId())) {
							if (messagequenes.containsKey(tblob.getMsgid())) {
								Message m = messagequenes.get(tblob.getMsgid());
								Map<String, Object> params = (Map<String, Object>)m.getParams();
								for (int i = 1; i < tblob.getPath().size(); i++) {
									if (i < tblob.getPath().size() - 1) {
										params = (Map<String, Object>) params.get(tblob.getPath().get(i));
									} else {
										params.put(tblob.getPath().get(i), tblob.getBlob());
									}
								}
							}
						}
					}

					if (tblob.getId().equals(tmsg.getId())) {
						if (messagequenes.containsKey(tblob.getMsgid())) {
							List<String> tokens = totokens.get(tblob.getMsgid());
							if (tokens != null) {
								for (String token : tokens) {
									Connection c = connections.get(token);
									if (c != null && c.getAppId().equals(connection.getAppId())) {
										try {
											syncSendText(c, message);
										} catch (Exception e) {
											error("转发消息失败：" + e.getMessage() + ", message= " + CommonFun.object2Json(tmsg),
													e);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 接收消息
	 * 
	 * @param message
	 * @param session
	 */
	@OnMessage
	public void onBinaryMessage(Session session, ByteBuffer message, boolean last) {
		info("接收到来自" + session.getId() + "的二进制消息[" + last + "]：" + message);
		try {
			if (tblob != null) {
				if (messagequenes.containsKey(tblob.getMsgid())) {
					List<String> tokens = totokens.get(tblob.getMsgid());
					if (tokens != null) {
						for (String token : tokens) {
							Connection c = connections.get(token);
							if (c != null && c.getAppId().equals(connection.getAppId())) {
								try {
									c.getSession().getBasicRemote().sendBinary(message);
								} catch (Exception e) {
									error("转发消息失败：" + e.getMessage() + ", tblob= " + tblob + ", binary=" + message, e);
								}
							}
						}
					}
				}
				byte[] dst = new byte[message.capacity()];
				message.get(dst);
				RandomAccessFile oAccessLogFile = new RandomAccessFile(tblob.getBlob().getFile(), "rw");
				oAccessLogFile.seek(oAccessLogFile.length());
				oAccessLogFile.write(dst);
				oAccessLogFile.close();
			}
		} catch (IOException e) {
			error("接收到来自" + session.getId() + "的二进制消息[" + last + "]异常" + e.getMessage(), e);
			if (tblob != null) {
				// 要求重发
				Message error = new Message();
				error.setType(Message.TYPE_ERROR);
				error.setId(tblob.getId());
				error.setMsgid(tblob.getMsgid());
				error.setState(e.getMessage());
				tblob.getBlob().getFile().delete();
				try {
					syncSendText(connection, CommonFun.object2Json(error));
				} catch (Exception ex) {
					error("反馈" + session.getId() + "二进制接收的异常信息异常" + ex.getMessage(), ex);
				}
			}
		}
	}

	/**
	 * 接收消息
	 * 
	 * @param message
	 * @param session
	 */
	@OnMessage
	public void onPongMessage(Session session, PongMessage message) {
		info("接收到来自" + session.getId() + "的PONG消息：" + message);
	}

	/**
	 * 连接断开
	 */
	@OnClose
	public void onClose(Session session, CloseReason reason) {
//		if (reason.getCloseCode() == CloseReason.CloseCodes.TLS_HANDSHAKE_FAILURE) {
//			return;
//		}
		info("和" + session.getId() + "断开");
		if (connection != null) {
			ClientInfo req = parseSession(session);
			if (req != null) {
				connection.onDisconnect(reason);
				connections.remove(connection.getToken());
//				deleteClientFile(connection);
				connection = null;
			} else {
				log.error("websocket disconnect, user=unkown, sessionId=" + session.getId() + ", " + reason);
			}
		} else {
			log.error("websocket disconnect, user=unkown, sessionId=" + session.getId() + ", " + reason);
		}
	}

	/**
	 * 链接异常
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		error("接收到来自" + session.getId() + "的异常：" + error.getMessage(), error);
		if (connection != null) {
			connection.onError(error);
		}
	}

	// TODO: 复杂消息
	public static void sendMessage(String token, Map<String, Object> params) throws Exception {
		Message message = new Message();
		message.setId(CommonFun.getSSID());
		message.setParams(params);
		sendMessage(token, message);
	}

	public static void sendMessage(String token, Message message) throws Exception {
		Connection connection = connections.get(token);
		if (connection == null) {
			return ;
			//throw new Exception("connection not found.");
		}
		if (connection.getSession() == null) {
			return ;
			//throw new Exception("connection not connection.");
		}
		message.setType(Message.TYPE_TEXT);
		message.setState(Message.STATE_END);
		message.setTo(connection.getClientInfo());
		message.setMulti(false);
		log.info("处理线程-" + Thread.currentThread().getId() + "向" + connection.getSession().getId() + "发送消息：" + message);
		syncSendText(connection, CommonFun.object2Json(message));
	}
	
	private static void syncSendText(Connection connection, String message) throws IOException {
		synchronized (connection.getSession()) {
			connection.getSession().getBasicRemote().sendText(message);
		}
	}
	
	public static void disconnect(String token, String reason) throws Exception {
		Connection connection = connections.remove(token);
		if (connection == null) {
			return;
		}
		if (connection.getSession() != null) {
			log.info("处理线程-" + Thread.currentThread().getId() + "主动断开" + connection.getSession().getId() + ", 原因是"
					+ reason);
			connection.getSession().close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE,
					CommonFun.isNe(reason) ? "Server Active Disconnection." : reason));
		}
		connection = null;
	}

	private static String getResourcePath() {
		String rtn = GlobalVar.getSystemProperty("resource.path", "path");
		if (rtn.indexOf('/') == 0 || rtn.indexOf(':') > 0) {

		} else {
			rtn = GlobalVar.getWorkPath() + rtn;
		}
		return rtn;
	}

}
