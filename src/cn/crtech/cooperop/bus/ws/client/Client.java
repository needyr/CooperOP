package cn.crtech.cooperop.bus.ws.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.websocket.ClientEndpoint;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Extension;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.ClientEndpointConfig.Configurator;

import org.apache.http.conn.ssl.SSLSocketFactory;

import com.sun.mail.iap.Protocol;

import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.HttpClient;
import cn.crtech.cooperop.bus.util.HttpClient.MySSLSocketFactory;
import cn.crtech.cooperop.bus.ws.bean.BlobItem;
import cn.crtech.cooperop.bus.ws.bean.Message;

@ClientEndpoint
public class Client {
	
	public static void main(String[] args) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", "root");
		params.put("password", "000000");
		init("https://localhost:8443");
		try {
			send("CRY0000root", "000000", params);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private static String LOGIN_URL;
	private static String LOGOUT_URL;
	private static String WS_URL;

	public static void init(String server_url) {
		LOGIN_URL = server_url + "/w/yaoxunkang/yxkclientlogin.json";
		LOGOUT_URL = server_url + "/w/yaoxunkang/yxkclientlogout.json";
		if (server_url.indexOf("https://") == 0) {
			WS_URL = server_url.replaceAll("https://", "wss://");
		} else {
			WS_URL = server_url.replaceAll("http://", "ws://");
		}
		WS_URL += "/ws";
	}

	public static void send(String userid, String password, Map<String, Object> params) throws Throwable {
		Client client = new Client();
		client.connect(userid, password);
		client.sendMessage(params);
		client.disconnect();
	}

	private Session session;
	private Map<String, Object> logininfo;

	private void connect(String userid, String password) throws Throwable {
		//JSONP登录获取token
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		params.put("password", password);
		logininfo = CommonFun.json2Object(HttpClient.post(LOGIN_URL, params), Map.class);
		
		String url = WS_URL;
		if (url.startsWith("wss")) {
			int port = 443;
			if (url.indexOf(":", "wss://".length() + 1) > 0) {
				String p = url.substring(url.indexOf(":", "wss://".length() + 1) + 1);
				if (p.indexOf("/") > 0) {
					p = p.substring(0, p.indexOf("/"));
				}
				port = Integer.parseInt(p);
			}
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		}
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		session = container.connectToServer(new Endpoint() {
			public void onOpen(Session paramSession, EndpointConfig paramEndpointConfig) {
				// TODO Auto-generated method stub
				System.out.println(paramSession);
				System.out.println(paramEndpointConfig);
			}
		}, new ClientEndpointConfig() {
			
			@Override
			public Map<String, Object> getUserProperties() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<Class<? extends Encoder>> getEncoders() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<Class<? extends Decoder>> getDecoders() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<String> getPreferredSubprotocols() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<Extension> getExtensions() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Configurator getConfigurator() {
				// TODO Auto-generated method stub
				return null;
			}
		}, new URI(WS_URL + "?" + logininfo.get("app_key") + "=" + logininfo.get("app_id") + "&" + logininfo.get("token_key") + "=" + logininfo.get("ws_token")));
	}

	private void disconnect() throws Throwable {
		session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "消息发送完毕，主动断开"));
		Map<String, Object> params = new HashMap<String, Object>();
		HttpClient.get(LOGOUT_URL + "?" + logininfo.get("session_name") + "=" + logininfo.get("session_id"), params);
	}

	/**
	 * 建立链接
	 * 
	 * @param session
	 * @throws IOException
	 */
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("接收到来自" + session.getId() + "的链接: " + session.getBasicRemote());
	}

	/**
	 * 接收消息
	 * 
	 * @param message
	 * @param session
	 */
	@OnMessage
	public void onTextMessage(Session session, String message, boolean last) {
		System.out.println("接收到来自" + session.getId() + "的文本消息[" + last + "]：" + message);
	}

	/**
	 * 接收消息
	 * 
	 * @param message
	 * @param session
	 */
	@OnMessage
	public void onBinaryMessage(Session session, ByteBuffer message, boolean last) {
		System.out.println("接收到来自" + session.getId() + "的二进制消息[" + last + "]：" + message);

	}

	/**
	 * 接收消息
	 * 
	 * @param message
	 * @param session
	 */
	@OnMessage
	public void onPongMessage(Session session, PongMessage message) {
		System.out.println("接收到来自" + session.getId() + "的PONG消息：" + message);
	}

	/**
	 * 连接断开
	 */
	@OnClose
	public void onClose(Session session) {
		System.out.println("和" + session.getId() + "断开");
	}

	/**
	 * 链接异常
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.err.println("接收到来自" + session.getId() + "的异常：" + error.getMessage());
		error.printStackTrace();
	}

	Message message = null;
	List<Message> blobs = new ArrayList<Message>();

	private void sendMessage(Map<String, Object> params) throws IOException {
		// TODO Auto-generated method stub
		message = new Message();
		message.setId(CommonFun.getSSID());
		message.setType(Message.TYPE_TEXT);
		message.setState(Message.STATE_END);
		message.setTo(null);
		message.setMulti(false);
		List<String> path = new ArrayList<String>();
		path.add("params");
		Iterator<String> keys = params.keySet().iterator();
		while (keys.hasNext()) {
			String k = keys.next();
			computeblob(k, params, path);
		}
		message.setParams(params);

		if (blobs.size() == 0) {
			session.getBasicRemote().sendText(CommonFun.object2Json(message));
		} else {
			message.setMulti(true);
			message.setState(Message.STATE_BEGIN);
			session.getBasicRemote().sendText(CommonFun.object2Json(message));

			for (Message bi : blobs) {
				ByteBuffer bb = ByteBuffer.wrap(bi.getBlob().get());
				bi.getBlob().setFile(null);
				bi.setState(Message.STATE_BEGIN);
				Map<String, Object> t = bi.serialize();
				session.getBasicRemote().sendText(CommonFun.object2Json(t));
				session.getBasicRemote().sendBinary(bb);
				t.put("state", Message.STATE_END);
				session.getBasicRemote().sendText(CommonFun.object2Json(t));
			}

			Message end = new Message();
			end.setId(message.getId());
			end.setType(Message.TYPE_TEXT);
			end.setState(Message.STATE_END);
			end.setMulti(message.isMulti());
			session.getBasicRemote().sendText(CommonFun.object2Json(end));
		}

	}

	private void computeblob(String key, Map<String, Object> parent, List<String> path) {
		List<String> np = new ArrayList<String>(path);
		np.add(key);
		if (parent.get(key) instanceof BlobItem) {
			Message blob = new Message();
			blob.setId("_blob_" + CommonFun.getSSID());
			blob.setType(Message.TYPE_BLOB);
			blob.setState(Message.STATE_BEGIN);
			blob.setMsgid(message.getId());
			blob.setPath(np);
			blob.setBlob((BlobItem) parent.get(key));
			blobs.add(blob);
			parent.put(key, blob.getId());
		} else if (parent.get(key) instanceof File) {
			Message blob = new Message();
			blob.setId("_blob_" + CommonFun.getSSID());
			blob.setType(Message.TYPE_BLOB);
			blob.setState(Message.STATE_BEGIN);
			blob.setMsgid(message.getId());
			blob.setPath(np);
			blob.setBlob(new BlobItem((File) parent.get(key)));
			blobs.add(blob);
			parent.put(key, blob.getId());
		} else if (parent.get(key) instanceof Map) {
			Map<String, Object> m = (Map<String, Object>) parent.get(key);
			Iterator<String> keys = m.keySet().iterator();
			while (keys.hasNext()) {
				String k = keys.next();
				computeblob(k, m, np);
			}
		}
	}
	
	public static class MySSLSocketFactory extends SSLSocketFactory {

		SSLContext sslContext = SSLContext.getInstance("TLS");

		/**
		 * 
		 * @param truststore
		 * @throws NoSuchAlgorithmException
		 * @throws KeyManagementException
		 * @throws KeyStoreException
		 * @throws UnrecoverableKeyException
		 */
		public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}

}
