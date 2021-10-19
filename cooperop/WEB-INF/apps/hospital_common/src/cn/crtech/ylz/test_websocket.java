package cn.crtech.ylz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import cn.crtech.cooperop.bus.util.CommonFun;

public class test_websocket {
	private static String ws_url = "ws://"+"10.180.7.242:8085"+"/ws?_cooperopws_app_=im&&_cooperopws_token_=";
	
	public static void main(String[] args) throws Throwable {
		int jiange_time = 1000;
		int xunhuan = 1;
		int count = 500;
		int i = 0;
		login_info(500);
		//这里用的binance的socket接口，国内调用需要VPN，使用换成你的就行
    	File file = new File("F:/my_test/cooperopws_token.txt");
		String txt2String = txt2String(file);
		String[] split = txt2String.split("\r\n");
		
		test_tasknum(split,3000,0,200);
		
		if(xunhuan > 0 ) {
			while(i <= xunhuan) {
				i++;
				Thread.sleep(jiange_time);
				for (int j = 0; j < split.length; j++) {
					String jj = split[(int)(Math.random()*split.length)];
					if(j <= count) {
						 new Thread() {
				            public void run() {
				            	start_websocket(jj);
				            }
				        }.start();
					}
				}
			}
		}else {
			while(true) {
				i++;
				Thread.sleep(jiange_time);
				for (int j = 0; j < split.length; j++) {
					String jj = split[(int)(Math.random()*split.length)];
					if(j <= count) {
						 new Thread() {
				            public void run() {
				            	start_websocket(jj);
				            }
				        }.start();
					}
				}
			}
		}
		
    }
	
	public static String txt2String(File file) {
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
	
	public static void test_tasknum(String[] split,int jiange_time,int xunhuan,int count) throws InterruptedException {
		int i = 0;
		if(xunhuan > 0 ) {
			while(i <= xunhuan) {
				Thread.sleep(jiange_time);
				i++;
				new Thread() {
		            public void run() {
		            	for (int j = 0; j < split.length; j++) {
		            		String jj = split[(int)(Math.random()*split.length)];
							if(j <= count) {
								String post2 = "";
			            		try {
			            			post2 = YlzPost.post("http://10.180.7.242:8085/w/application/task/tasknum.json?_CRSID="+jj, new HashedMap());
			            		} catch (Throwable e) {
			            			// TODO Auto-generated catch block
			            			e.printStackTrace();
			            		}
			            		//System.out.println(post2);
							}
						}
		            }
		        }.start();
			}
		}else {
			while(true) {
				Thread.sleep(jiange_time);
				new Thread() {
		            public void run() {
		            	for (int j = 0; j < split.length; j++) {
		            		String jj = split[(int)(Math.random()*split.length)];
							if(j <= count) {
								String post2 = "";
			            		try {
			            			post2 = YlzPost.post("http://10.180.7.242:8085/w/application/task/tasknum.json?_CRSID="+jj, new HashedMap());
			            		} catch (Throwable e) {
			            			// TODO Auto-generated catch block
			            			e.printStackTrace();
			            		}
			            		//System.out.println(post2);
							}
						}
		            }
		        }.start();
			}
		}
		
	}
	
	public static void start_websocket(String _cooperopws_token_) {
		//String post2 = YlzPost.post("http://10.180.7.242:8085/w/application/task/tasknum.json?_CRSID=1FFB793675374D1682963AA6EA12B6AA", m);
		//System.out.println(post2);
		try {
			String url = ws_url+_cooperopws_token_;
			System.out.println(url);
            URI uri = new URI(url);
            WebSocketClient mWs = new WebSocketClient(uri){
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                	System.out.println("onOpen");
                }

                @Override
                public void onMessage(String s) {
                    System.out.println(s);
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                	System.out.println("onClose");
                }

                @Override
                public void onError(Exception e) {
                	System.out.println("onError");
                }
            };
            mWs.connect();
            while (true) {
            	Thread.sleep(3000);
            	mWs.send("111");
			}
		} catch (Exception e) {
			e.printStackTrace();
	    }
	}
	
	public static void login_info(int count) throws Throwable {
		File et_users = new File("F:/my_test/et_user.txt");
		String users = txt2String(et_users);
		String[] split_users = users.split("\r\n");
		StringBuffer list = new StringBuffer();
		int i = 0;
		for (String string : split_users) {
			if(!CommonFun.isNe(string) && i <= count) {
			Map<String, Object> m = new HashedMap();
			m.put("userid", string);
			m.put("password", "000000");
			String post = YlzPost.post("http://10.180.7.242:8085/w/application/auth/login.json", m);
			Map json2Object = CommonFun.json2Object(post, Map.class);
			Object object = json2Object.get("_CRSID");
			if(!CommonFun.isNe(object)) {
				if(i == 0) {
					  i++;
					  list.append(object);
				  }else {
					  i++;
					  list.append("\r\n"+ object);
				  }
			}
			}
		}
		File fp=new File("F:/my_test/cooperopws_token.txt");
		if(fp.exists()) {
			fp.delete();
		}
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
}
