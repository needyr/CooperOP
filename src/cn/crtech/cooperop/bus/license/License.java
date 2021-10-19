package cn.crtech.cooperop.bus.license;

import java.io.BufferedReader;

import java.io.FileReader;


import cn.crtech.cooperop.bus.cache.MemoryCache;
import cn.crtech.cooperop.bus.rdms.Record;


public class License {
	private static String prix = "license:";

	// 30天: 30 * 24 * 60 * 60 = 2592000
	private static Long warn_ms = 2592000000L;

	public static void init() {
		// check();
		//启动定时器每半个小时重新check一次
		new Thread(()->{
			while(true){
				check();
				checkTime();
				try {
					Thread.sleep(1000*60*30);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

		//checkTime();
	}

	private static String readFile(String fileName) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		StringBuffer strbuff = new StringBuffer();
		String line = null;
		while ((line = reader.readLine()) != null) {
			strbuff.append(line + "\n");
		}
		return strbuff.toString();
	}

	//写到Service，整体事务控制
	private static void check() {
		if(1 == 1){
			MemoryCache.put(prix,"is_dev", "1");
			return;
		}
	}

	public static int check(String pageid){
		if(1 == 1) return 1;
		if("1".equals(MemoryCache.get(prix, "is_dev"))){
			return 2; //ygz.2021.02.20：注释开发者模式
		}
		if(pageid != null && pageid !=""){
			String code = pageid.split("\\.")[0];
			if("1".equals(MemoryCache.get(prix, code))){
				return 1;//正常产品
			}else {
				return 0;//过期产品
			}
		}else{
			return -9;//错误参数
		}
	}

	/*
	ygz.2021-03-17: 检查产品过期时间，过期时间提醒
	* */
	public static Record checkTime(){
		return null;
	}

	/*public static final void validate() throws Exception {
		String config = GlobalVar.getSystemProperty("license.path");
		try {
			if (config == null) {
				throw new Exception("license file not found: null");
			}
			if (config.charAt(0) != '/' && config.charAt(1) != ':') {
				config = GlobalVar.getWorkPath() + File.separator + config;
			}
			// 导入公钥
			String s = null;
			try {
				s = readFile(config);
			} catch (Exception ex) {
				throw new Exception("license file read failed.", ex);
			}
			String[] ss = s.split("\\Q\n\n\\E");

			String spubkey = ss[0].trim();
			String slicense = ss[1].trim();

			BASE64Decoder decoder = new BASE64Decoder();

			ByteArrayInputStream bis = new ByteArrayInputStream(decoder.decodeBuffer(spubkey));
			ObjectInputStream in = new ObjectInputStream(bis);

			PublicKey pubkey = (PublicKey) in.readObject();
			in.close();

			// 导入需要读取的文件
			bis = new ByteArrayInputStream(decoder.decodeBuffer(slicense));
			in = new ObjectInputStream(bis);
			LicenseContent license = (LicenseContent) in.readObject();
			byte[] signed = (byte[]) in.readObject();
			in.close();

			// 验证密钥对
			Signature signCheck = Signature.getInstance("DSA");
			signCheck.initVerify(pubkey);
			signCheck.update(license.toString().getBytes("utf-8"));
			if (signCheck.verify(signed)) {
				if (license.getExpire().getTime().getTime() >= System.currentTimeMillis()) {
					MemoryCache.put("license", license);
				} else {
					throw new Exception("license expired. " + license);
				}
			} else {
				throw new Exception("license invalid. " + license);
			}
		} catch (Exception e) {
			throw e;
		}
	}*/
}
