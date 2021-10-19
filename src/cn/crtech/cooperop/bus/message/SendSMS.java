package cn.crtech.cooperop.bus.message;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import cn.crtech.cooperop.bus.util.MD5;

public class SendSMS {
	 public static int send(String mobile, String content) throws Exception{
		 URL url = null;
			String CorpID="CRTECH00002";//账户名
			String Pwd="crtech@2017";//密码
			String send_content=URLEncoder.encode(content, "GB2312");//发送内容
			url = new URL("https://mb345.com/ws/BatchSend2.aspx?CorpID="+CorpID+"&Pwd="+Pwd+"&Mobile="+mobile+"&Content="+send_content);
			//url = new URL("https://mb345.com/ws/BatchSend2.aspx?CorpID=CRTECH00002&Pwd=000000&Mobile=18190700525&Content="+send_content);
			BufferedReader in = null;
			int inputLine = 0;
			try {
				System.out.println("开始发送短信手机号码为 ："+mobile);
				in = new BufferedReader(new InputStreamReader(url.openStream()));
				inputLine = new Integer(in.readLine()).intValue();
			} catch (Exception e) {
				System.out.println("网络异常,发送短信失败！");
				inputLine=-2;
			}
			System.out.println("结束发送短信返回值：  "+inputLine);
			return inputLine;
			/*System.setProperty("sun.net.client.defaultConnectTimeout", "30000"); 
		System.setProperty("sun.net.client.defaultReadTimeout", "30000");	

		StringBuffer buffer = new StringBuffer();
		
		
		String encode = "GBK"; 

		//String encode = "UTF-8";
		
		String username = "CRTECH00004";  
		
		String password_md5 = "000000";  
		
		//String mobile = "";  
		
		String apikey = "f7154164fb4778ecc8f856dd51e26b3a";  
		
	   
		String result = "";

		
		try {
			
			
			String contentUrlEncode = URLEncoder.encode(content,encode);  
			
			buffer.append("https://mb345.com/ws/BatchSend2.aspx?CorpID="+username+"&Pwd="+password_md5+"&Mobile="+mobile+"&Content="+contentUrlEncode);
			
			URL url = new URL(buffer.toString());

			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			
			connection.setRequestMethod("POST");

			connection.setRequestProperty("Connection", "Keep-Alive");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			
			 result = reader.readLine();
			
			System.out.println(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;*/
	}
	 
	public static void main(String[] args) {
		try {
			
			SendSMS.send("13880730325,18583649915", "c测试信息");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}