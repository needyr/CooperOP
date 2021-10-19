package cn.crtech.cooperop.bus.interf;

import java.io.InputStreamReader;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.SHA1;

public class MyHttpClient {
	public static Map<String, Object> get(String url) {
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
		HttpClient client = new DefaultHttpClient(params);
        client = WebClientDevWrapper.wrapClient(client);
		HttpGet httpget = new HttpGet(url);  
		Map<String, Object> map= null;  
		try {  
            // 创建httpget.    
            System.out.println("executing request " + httpget.getURI());  
            // 执行get请求. 
            Long startTime = System.currentTimeMillis();  
            HttpResponse response = client.execute(httpget);  
            System.out.println(System.currentTimeMillis() - startTime);  
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                HttpEntity entity = response.getEntity();  
                String charset = EntityUtils.getContentCharSet(entity);  
                if(charset == null){  
                    charset = "utf-8";  
                }  
                JSONObject res = new JSONObject(new JSONTokener(  
                        new InputStreamReader(entity.getContent(), charset)));
                map = CommonFun.json2Object(res.toString(), Map.class);
               // System.out.println(map);
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
        return map;
	}
		public static  Map<String, Object> post(String url, Map<String, Object> param) {
		String json = CommonFun.object2Json(param);
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

        HttpClient client = new DefaultHttpClient(params);  
        client = WebClientDevWrapper.wrapClient(client);  
        HttpPost post = new HttpPost(url);
        Map<String, Object> map= null;  
        try {  
            StringEntity s = new StringEntity(json ,"application/json", "UTF-8");
            post.setEntity(s);
            post.setHeader("Content-Type", "application/json; charset=UTF-8");  
  
            Long startTime = System.currentTimeMillis();  
            HttpResponse res = client.execute(post);  
            System.out.println(System.currentTimeMillis() - startTime);  
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                HttpEntity entity = res.getEntity();  
                String charset = EntityUtils.getContentCharSet(entity);  
                if(charset == null){  
                    charset = "utf-8";  
                }  
                JSONObject response = new JSONObject(new JSONTokener(  
                        new InputStreamReader(entity.getContent(), charset)));
                map = CommonFun.json2Object(response.toString(), Map.class);
               // System.out.println(map);
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
        return map;  
    }  
  
    public static class WebClientDevWrapper {
        public static HttpClient wrapClient(HttpClient base) {  
            try {  
                SSLContext ctx = SSLContext.getInstance("TLS");  
                X509TrustManager tm = new X509TrustManager() {  
                    @Override  
                    public X509Certificate[] getAcceptedIssuers() {  
                        return null;  
                    }  
  
                    @Override  
                    public void checkClientTrusted(  
                            java.security.cert.X509Certificate[] chain,  
                            String authType)  
                            throws java.security.cert.CertificateException {  
                          
                    }  
  
                    @Override  
                    public void checkServerTrusted(  
                            java.security.cert.X509Certificate[] chain,  
                            String authType)  
                            throws java.security.cert.CertificateException {  
                          
                    }  
                };  
                ctx.init(null, new TrustManager[] { tm }, null);  
                SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
                ClientConnectionManager ccm = base.getConnectionManager();  
                SchemeRegistry sr = ccm.getSchemeRegistry();  
                sr.register(new Scheme("https", 443, ssf));  
                return new DefaultHttpClient(ccm, base.getParams());  
            } catch (Exception ex) {  
                ex.printStackTrace();  
                return null;  
            }  
        }  
    }
    
	public static void sendm(Map<String,Object> param) throws Exception {
		//Map<String, Object> m1 = new HashMap<String, Object>();
		//m1.put("title", "你好！我测试一下消息推送功能！！！");
		//m1.put("content", "可以发送了");
		//m1.put("type", 1);
		//m1.put("platform", 0);
		//m1.put("platform", 0);
		//m1.put("userIds", "HRZH0000029_crtech001");
		String json = CommonFun.object2Json(param);
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

        HttpClient client = new DefaultHttpClient(params);  
        client = WebClientDevWrapper.wrapClient(client);  
        HttpPost post = new HttpPost("https://p.apicloud.com/api/push/message");
        Map<String, Object> map= null;  
        try {  
            StringEntity s = new StringEntity(json ,"application/json", "UTF-8");
            post.setEntity(s);
            long t = System.currentTimeMillis();
            String appKey = SHA1.encode("A6904774548350" + "UZ" + "4EC1E2BA-E9AB-154F-FF44-3A9FFAF32FA4" + "UZ" + t) + "." + t;
            post.setHeader("Content-Type", "application/json; charset=UTF-8");  
            post.setHeader("X-APICloud-AppId", "A6904774548350");
            post.setHeader("X-APICloud-AppKey", appKey);
  
            HttpResponse res = client.execute(post);  
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                HttpEntity entity = res.getEntity();  
                String charset = EntityUtils.getContentCharSet(entity);  
                if(charset == null){  
                    charset = "utf-8";  
                }  
                JSONObject response = new JSONObject(new JSONTokener(new InputStreamReader(entity.getContent(), charset)));
                map = CommonFun.json2Object(response.toString(), Map.class);
               System.out.println(map);
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
}
