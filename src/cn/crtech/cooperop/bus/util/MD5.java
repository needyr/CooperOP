package cn.crtech.cooperop.bus.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

public class MD5 {
	private static final String key;
	private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	static {
		key = "aa";
	}

	public final static String md5(String s) {
		if (s == null) return null;
		return md5(s.getBytes());
	}

	public final static String md5(File file) {
		if (file == null) return null;
		if (!file.exists()) return null;
		byte bytes[] = new byte[(int) file.length()];
		FileInputStream fio = null;
		try {
			fio = new FileInputStream(file);
			fio.read(bytes);
			return md5(bytes);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (fio != null) {
				try {
					fio.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public final static String md5(byte[] bytes) {
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(bytes);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	public final static String md5() {
		return md5(key);
	}
}
