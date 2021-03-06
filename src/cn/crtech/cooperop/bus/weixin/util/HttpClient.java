package cn.crtech.cooperop.bus.weixin.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.weixin.WeiXinCore;

public class HttpClient {
	
	public static String get(String url, Map<String, Object> params) throws Throwable {
		org.apache.http.client.HttpClient httpClient = null;
		try {

			StringBuffer p = new StringBuffer();
			Iterator<String> keys = params.keySet().iterator();
			while (keys.hasNext()) {
				String key = keys.next();
				if (p.length() == 0 && url.indexOf('?') == -1)
					p.append("?");
				else
					p.append("&");
				if (CommonFun.isNe(params.get(key))) {
					p.append(key + "=");
				} else if (params.get(key).getClass().isPrimitive() || params.get(key) instanceof String) {
					p.append(key + "=" + URLEncoder.encode(params.get(key).toString(), "UTF-8"));
				} else {
					p.append(key + "=" + URLEncoder.encode(CommonFun.object2Json(params.get(key)), "UTF-8"));
				}
			}
			
			url += p.toString();

			httpClient = createHttpClient(url);
			httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, WeiXinCore.request_timeout);  
			httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, WeiXinCore.connect_timeout);  

			HttpGet httpget = new HttpGet(url);

			// ??????gzip
			httpget.setHeader("User-Agent", "apache httpclient 4.1.2");
			httpget.setHeader("Accept-Encoding", "gzip, deflate");
			HttpResponse httpResponse = httpClient.execute(httpget);
			throwHttpException(httpResponse);
			return loadRtn(httpResponse);
		} catch (Throwable ex) {
			if (ex.getCause() != null)
				throw new Exception(ex.getCause());
			throw ex;
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
	}

	public static String post(String url, Map<String, Object> params) throws Throwable {
		org.apache.http.client.HttpClient httpClient = null;
		try {
			httpClient = createHttpClient(url);
			httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, WeiXinCore.request_timeout);  
			httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, WeiXinCore.connect_timeout);  

			HttpPost httppost = new HttpPost(url);

			httppost.setHeader("Content-Type", "application/json; charset=utf-8");
			httppost.setHeader("Accept", "application/json");
			httppost.setHeader("User-Agent", "apache httpclient 4.1.2");
			// ??????gzip
			httppost.setHeader("Accept-Encoding", "gzip, deflate");
			httppost.getParams().setParameter("http.protocol.content-charset", "UTF-8");

			httppost.setEntity(new StringEntity(CommonFun.object2Json(params), "application/json", "UTF-8"));

			HttpResponse httpResponse = httpClient.execute(httppost);
			
			return loadRtn(httpResponse);
		} catch (Throwable ex) {
			if (ex.getCause() != null)
				throw new Exception(ex.getCause());
			throw ex;
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
	}

	public static String put(String url, Map<String, Object> params) throws Throwable {
		org.apache.http.client.HttpClient httpClient = null;
		try {
			httpClient = createHttpClient(url);
			httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, WeiXinCore.request_timeout);  
			httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, WeiXinCore.connect_timeout);  

			HttpPut httpput = new HttpPut(url);

			MultipartEntity entity = new MultipartEntity();

			Iterator<String> keys = params.keySet().iterator();
			while (keys.hasNext()) {
				String key = keys.next();
				if (CommonFun.isNe(params.get(key))) {
					entity.addPart(key, new StringBody(""));
				} else if (params.get(key) instanceof File) {
					entity.addPart(key, new FileBody((File) params.get(key)));
				} else if (params.get(key).getClass().isPrimitive() || params.get(key) instanceof String) {
					entity.addPart(key, new StringBody(params.get(key).toString()));
				} else {
					entity.addPart(key, new StringBody(CommonFun.object2Json(params.get(key)), "application/json", Charset.forName(HTTP.UTF_8)));
				}
			}

			// ??????gzip
			httpput.setHeader("User-Agent", "apache httpclient 4.1.2");
			httpput.setHeader("Accept-Encoding", "gzip, deflate");
			httpput.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpput);
			throwHttpException(httpResponse);
			return loadRtn(httpResponse);
		} catch (Throwable ex) {
			if (ex.getCause() != null)
				throw new Exception(ex.getCause());
			throw ex;
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
	}

	public static org.apache.http.client.HttpClient createHttpClient(String url) {
		if (url.startsWith("https")) {
			try {
				KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
				trustStore.load(null, null);

				SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
				sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

				HttpParams params = new BasicHttpParams();
				HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
				HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

				SchemeRegistry registry = new SchemeRegistry();
				registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
				registry.register(new Scheme("https", sf, 443));

				ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

				return new DefaultHttpClient(ccm, params);
			} catch (Exception e) {
				return new DefaultHttpClient();
			}
		}
		return new DefaultHttpClient();
	}

	public static String loadRtn(HttpResponse httpResponse) throws IllegalStateException, IOException {
		StringBuffer rtn = new StringBuffer();
		HttpEntity httpEntity = httpResponse.getEntity();
		String charset = HTTP.UTF_8;
		if (httpEntity.getContentType() != null && !CommonFun.isNe(httpEntity.getContentType().getValue())) {
			String t = httpEntity.getContentType().getValue();
			String[] ts = t.split(";");
			for (String ti : ts) {
				if (ti.indexOf("charset=") > -1) {
					charset = ti.substring(ti.indexOf("charset=") + "charset=".length()).trim();
					break;
				}
			}
		}
		// ????????????????????????gzip
		if (httpEntity.getContentEncoding() != null && "gzip".equals(httpEntity.getContentEncoding().getValue().toLowerCase())) {
			System.out.println(httpEntity.getContentEncoding().getName() + ":" + httpEntity.getContentEncoding().getValue() + " ?????????gzip");
			GZIPInputStream gzin = new GZIPInputStream(httpEntity.getContent());
			InputStreamReader isr = new InputStreamReader(gzin, charset);
			BufferedReader bufferedReader = new BufferedReader(isr);
			String text;
			while ((text = bufferedReader.readLine()) != null) {
				rtn.append(text + "\r\n");
			}
		} else {
			InputStreamReader isr = new InputStreamReader(httpEntity.getContent(), charset);
			BufferedReader bufferedReader = new BufferedReader(isr);
			String text;
			while ((text = bufferedReader.readLine()) != null) {
				rtn.append(text + "\r\n");
			}
		}
		return rtn.toString();
	}

	public static void throwHttpException(HttpResponse resp) throws Exception {
		int state = resp.getStatusLine().getStatusCode();
		if (state == 200) {
			return;
		}
		if (state < 500) {
			throw new Exception("Http Server Error - " + state + ": " + resp.getStatusLine().getReasonPhrase().trim());
		} else {
			if (resp.getStatusLine().getReasonPhrase() != null && resp.getStatusLine().getReasonPhrase().trim().length() > 0) {
				throw new Exception("Http Server Error - " + state + ": " + resp.getStatusLine().getReasonPhrase().trim());
			} else {
				String htmlStr = loadRtn(resp);
				String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // ??????script??????????????????
				String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // ??????style??????????????????
				String regEx_html = "<[^>]+>"; // ??????HTML????????????????????????

				Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
				Matcher m_script = p_script.matcher(htmlStr);
				htmlStr = m_script.replaceAll(""); // ??????script??????

				Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
				Matcher m_style = p_style.matcher(htmlStr);
				htmlStr = m_style.replaceAll(""); // ??????style??????

				Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
				Matcher m_html = p_html.matcher(htmlStr);
				htmlStr = m_html.replaceAll(""); // ??????html??????

				throw new Exception("Http Server Error - " + state + ": " + htmlStr);
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
