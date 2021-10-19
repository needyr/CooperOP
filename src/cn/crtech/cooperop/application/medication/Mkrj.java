package cn.crtech.cooperop.application.medication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.servlet.ServletException;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rm.ResourceManager;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;

import org.apache.commons.fileupload.FileItem;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class Mkrj {
	private static final String KEY_STORE_TYPE_JKS = "jks";
	private static final String KEY_STORE_TYPE_P12 = "PKCS12";
	private static final String SCHEME_HTTPS = "https";
	private static final int HTTPS_PORT = 7443;
	private static final String KEY_STORE_PASSWORD = "3V*JT&g1aQMd!U!kS";
	private static final String KEY_STORE_TRUST_PASSWORD = "3V8JT7glaQMd1U1kS";
	private static final String MK_REG_CODE = "dkoCgpu23tBNGWvMm66D4H5wOHW3fK";
	private static final String DEFAULT_USER = "crtech";
	private static final String DEFAULT_USER_TYPE = "医生";

	public static final String HTTPS_URL = "https://service.mkrj.com:7443/passshell/ws/passweb/querylist";

	static boolean inited = false;
	static KeyStore keyStore = null;
	static KeyStore trustStore = null;

	public static void init() {
		if (inited)
			return;
		try {
			keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
			trustStore = KeyStore.getInstance(KEY_STORE_TYPE_JKS);
			String config = GlobalVar.getSystemProperty("mkrj.keystore");
			if (config.charAt(0) != '/' && config.charAt(1) != ':') {
				config = GlobalVar.getWorkPath() + File.separator + config;
			}
			InputStream ksIn = new FileInputStream(config);
			config = GlobalVar.getSystemProperty("mkrj.truststore");
			if (config.charAt(0) != '/' && config.charAt(1) != ':') {
				config = GlobalVar.getWorkPath() + File.separator + config;
			}
			InputStream tsIn = new FileInputStream(config);
			try {
				keyStore.load(ksIn, KEY_STORE_PASSWORD.toCharArray());
				trustStore.load(tsIn, KEY_STORE_TRUST_PASSWORD.toCharArray());
				inited = true;
			} finally {
				try {
					ksIn.close();
				} catch (Exception ignore) {
				}
				try {
					tsIn.close();
				} catch (Exception ignore) {
				}
			}
		} catch (Exception ex) {
			log.error("初始化美康Https配置失败", ex);
		}
	}

	// String userid, String usertype,
	public static String getYpsmsURL(String ypname) throws Exception {
		Record t = new Record();
		Record t2 = new Record();
		t2.put("regs_code", MK_REG_CODE);
		t2.put("user_term", DEFAULT_USER); // userid);
		t2.put("userterm_type", DEFAULT_USER_TYPE); // usertype);
		t.put("PassClient", t2);
		t2 = new Record();
		t2.put("ReferenceCode", ypname);
		t2.put("ReferenceType", 11);
		t.put("ReferenceParam", t2);
		return sslPost(HTTPS_URL, CommonFun.object2Json(t));
	}

	public static String storeYpsms(String ypname, String url) throws Exception {
		HttpClient httpClient = null;
		BlobItem bi = null;
		InputStream is = null;
		try {
			httpClient = new DefaultHttpClient();
			String tmp = url.substring(0, url.lastIndexOf("/") + 1);
			url = tmp + URLEncoder.encode(url.substring(url.lastIndexOf("/") + 1), "UTF-8");
			HttpGet httpget = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpget);
			int state = httpResponse.getStatusLine().getStatusCode();
			if (state == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				Header header = httpResponse.getFirstHeader("Content-Encoding");
				Header content_type = httpResponse.getFirstHeader("Content-Type");
				// 判断服务端否用了gzip
				if (header != null && "gzip".equals(header.getValue().toLowerCase())) {
					log.debug(header.getName() + ":" + header.getValue() + " 开启了gzip");
					is = new GZIPInputStream(httpEntity.getContent());
				} else {
					is = httpEntity.getContent();
				}
				bi = new BlobItem(ypname + "-用药说明.jpg", content_type.getValue(), is);
				return ResourceManager.storeResource("yunclinic", "system", bi, "", 99999999, 99999999);
			} else {
				throw new Exception("获取药品说明书失败!代码:" + state);
			}
		} catch (Throwable ex) {
			if (ex.getCause() != null)
				throw new Exception(ex.getCause());
			throw ex;
		} finally {
			if (bi != null) {
				bi.delete();
			}
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
	}

	public static String sslPost(String httpstr, String jsonstr) throws Exception {
		init();
		HttpClient httpClient = null;
		String result = null;
		try {
			httpClient = new DefaultHttpClient();
			SSLSocketFactory socketFactory = new SSLSocketFactory(keyStore, KEY_STORE_PASSWORD, trustStore);
			Scheme sch = new Scheme(SCHEME_HTTPS, HTTPS_PORT, socketFactory);
			httpClient.getConnectionManager().getSchemeRegistry().register(sch);
			HttpPost httppost = new HttpPost(httpstr);
			StringEntity stringEntity = new StringEntity(jsonstr, HTTP.UTF_8);
			stringEntity.setContentType("application/json");
			// 启用gzip
			httppost.setHeader("Accept-Encoding", "gzip, deflate");
			httppost.setEntity(stringEntity);
			HttpResponse httpResponse = httpClient.execute(httppost);
			int state = httpResponse.getStatusLine().getStatusCode();
			if (state == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				Header header = httpResponse.getFirstHeader("Content-Encoding");
				// 判断服务端否用了gzip
				if (header != null && "gzip".equals(header.getValue().toLowerCase())) {
					log.debug(header.getName() + ":" + header.getValue() + " 开启了gzip");
					GZIPInputStream gzin = new GZIPInputStream(httpEntity.getContent());
					InputStreamReader isr = new InputStreamReader(gzin, HTTP.UTF_8);
					BufferedReader bufferedReader = new BufferedReader(isr);
					String text;
					while ((text = bufferedReader.readLine()) != null) {
						result += text;
					}
				} else {
					result = EntityUtils.toString(httpEntity);// 取出应答字符串
				}
			} else {
				throw new Exception("查询美康药品说明书失败!代码:" + state);
			}
			if (result.length() > 5) {
				@SuppressWarnings("unchecked")
				Map<String, Object> rtn = CommonFun.json2Object(result, Map.class);
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> results = (List<Map<String, Object>>) (rtn.get("ReferenceResults"));
				if (results != null && results.size() > 0) {
					return (String) results.get(0).get("BriefItems");
				} else {
					throw new Exception("美康未找到药品说明书");
				}
			} else if ("-10".equals(result)) {
				throw new Exception("美康客户端证书校验失败");
			} else if ("-11".equals(result)) {
				throw new Exception("美康注册码校验失败");
			} else if ("-12".equals(result)) {
				throw new Exception("美康机构名称校验失败");
			} else if ("-13".equals(result)) {
				throw new Exception("美康用户无效");
			} else if ("-14".equals(result)) {
				throw new Exception("美康客户端证书到期");
			} else if ("-15".equals(result)) {
				throw new Exception("美康客户端接入IP错误");
			} else if ("-16".equals(result)) {
				throw new Exception("美康终端用户名或用户类型为空");
			} else if ("-19".equals(result)) {
				throw new Exception("美康用户信息获取异常");
			} else {
				throw new Exception("美康异常：" + result);
			}
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

	private static class BlobItem implements FileItem {
		private static final long serialVersionUID = 1L;

		private File file = null;
		private String fileName = null;
		private String contentType = null;

		public BlobItem(String filename, String contenttype, InputStream io) throws Exception {
			super();
			this.fileName = filename;
			this.contentType = contenttype;
			if (CommonFun.isNe(this.contentType)) {
				this.contentType = URLConnection.guessContentTypeFromName(filename);
			}
			if (CommonFun.isNe(this.contentType)) {
				this.contentType = "image/jpeg";
			}
			FileOutputStream fos = null;
			File f = new File(GlobalVar.getSystemProperty("rm.tempfilepath"));
			if (!f.exists()) {
				f.mkdirs();
			}
			f = new File(GlobalVar.getSystemProperty("rm.tempfilepath"), filename);
			if (f.exists()) {
				f.delete();
			}
			try {
				fos = new FileOutputStream(f);
				int readBytes = 0;
				byte buffer[] = new byte[8192];
				while ((readBytes = io.read(buffer, 0, 8192)) != -1) {
					fos.write(buffer, 0, readBytes);
				}
				fos.flush();
			} catch (Throwable ex) {
				throw new ServletException(ex);
			} finally {
				if (fos != null) {
					fos.close();
				}
				if (io != null) {
					io.close();
				}
			}
			this.file = f;
		}

		@Override
		public void delete() {
			file.delete();
		}

		@Override
		public byte[] get() {
			byte buffer[] = new byte[(int) file.length()];
			FileInputStream fio = null;
			try {
				fio = new FileInputStream(file);
				fio.read(buffer);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fio != null) {
					try {
						fio.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return buffer;
		}

		@Override
		public String getContentType() {
			return this.contentType;
		}

		@Override
		public String getFieldName() {
			return "blob";
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return new FileInputStream(file);
		}

		@Override
		public String getName() {
			return this.fileName;
		}

		@Override
		public OutputStream getOutputStream() throws IOException {
			return null;
		}

		@Override
		public long getSize() {
			return this.file.length();
		}

		@Override
		public String getString() {
			return this.fileName + "[" + this.contentType + "]: " + this.file.getAbsolutePath();
		}

		@Override
		public String getString(String encoding) throws UnsupportedEncodingException {
			return new String((this.fileName + "[" + this.contentType + "]: " + this.file.getAbsolutePath()).getBytes(), encoding);
		}

		@Override
		public boolean isFormField() {
			return false;
		}

		@Override
		public boolean isInMemory() {
			return false;
		}

		@Override
		public void setFieldName(String name) {
		}

		@Override
		public void setFormField(boolean state) {
		}

		@Override
		public void write(File file) throws Exception {
		}

	}
}
