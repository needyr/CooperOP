package cn.crtech.cooperop.bus.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import cn.crtech.cooperop.bus.cache.MemoryCache;
import cn.crtech.cooperop.bus.license.License;
import cn.crtech.cooperop.bus.license.LicenseContent;
import cn.crtech.cooperop.bus.log.log;

public class GlobalVar {

	private static String workPath;
	private static String configFile;
	private static Properties config;
	private static long lastmodify = 0;
	
	public static void init(String workpath, String configfile) throws IOException {
		workPath = workpath;
		configFile = configfile;
		reload();
		log.release("init global vars success.");
	}

	public static void reload() throws IOException {
		File f = new File(configFile);
		if (lastmodify < f.lastModified()) {
			config = CommonFun.loadPropertiesFile(configFile);
			lastmodify = f.lastModified();
		}
	}

	public static String getWorkPath() {
		return workPath;
	}
	
	public static String getSystemProperty(String string) {
		try {
			reload();
		} catch (IOException e) {
		}
		return config.getProperty(string);
	}

	public static String getSystemProperty(String string, String defaultvalue) {
		try {
			reload();
		} catch (IOException e) {
		}
		return config.getProperty(string, defaultvalue);
	}

	public static String getModuleConfigPath(String module, String file) {
		String uri = workPath + GlobalVar.getSystemProperty("config.folder.access.rule") + "/" + file;

		uri = uri.replace("@[MODULE]", module);

		return uri;

	}

	public static LicenseContent getLicense() {
		return new LicenseContent();
	}
}
