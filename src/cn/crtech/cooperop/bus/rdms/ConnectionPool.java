package cn.crtech.cooperop.bus.rdms;

import java.util.*;

import org.jdom.*;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ConnectionPool {
	private static String defaultdatabase = null;

	private static long usedrelease = 5;

	private static long idlerelease = 5;

	protected static long lifetime = 12;  //小时

	private static long logcycle = 0;

	private static idlecheck idlecheck;

	private static usedcheck usedcheck;

	private static monitor monitor;

	private static logs logs;

	public static Hashtable<String, DataBase> databases = new Hashtable<String, DataBase>();

	public static Hashtable jndi_config = new Hashtable();

	private static java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	public static void init() throws Exception {
		String config = GlobalVar.getSystemProperty("datasource.config");
		if (config.charAt(0) != '/' && config.charAt(1) != ':') {
			config = GlobalVar.getWorkPath() + File.separator + config;
		}


		Document doc = CommonFun.loadXMLFile(config);

		Element root = doc.getRootElement();

		defaultdatabase = root.getAttributeValue("defaultdatabase");
		if (root.getAttributeValue("usedrelease") != null) {
			usedrelease = Long.parseLong(root.getAttributeValue("usedrelease"));
		}
		if (root.getAttributeValue("idlerelease") != null) {
			idlerelease = Long.parseLong(root.getAttributeValue("idlerelease"));
		}
		if (root.getAttributeValue("logcycle") != null) {
			logcycle = Long.parseLong(root.getAttributeValue("logcycle"));
		}
		if (root.getAttributeValue("lifetime") != null) {
			lifetime = Long.parseLong(root.getAttributeValue("lifetime"));
		}

		logs = new logs();
		logs.start();
		if (idlerelease > 0) {
			idlecheck = new idlecheck();
			idlecheck.start();
		}
		if (usedrelease > 0) {
			usedcheck = new usedcheck();
			usedcheck.start();
		}
		if (logcycle > 0) {
			monitor = new monitor();
			monitor.start();
		}

		List<?> jndiconf = root.getChildren("jndi_config");
		for (int i = 0; i < jndiconf.size(); i++) {
			Element jc = (Element) jndiconf.get(i);
			jndi_config.put(jc.getAttributeValue("key"), jc.getAttributeValue("value"));
		}

		List<?> dblist = root.getChildren("database");

		DataBase database;
		for (int i = 0; i < dblist.size(); i++) {
			Element db = (Element) dblist.get(i);

			database = new DataBase();
			database.setId(db.getChildTextTrim("id"));
			database.setSchema(db.getChildTextTrim("schema"));
			database.setType(db.getChildTextTrim("type"));
			database.setDbType(db.getChildTextTrim("dbtype"));
			database.setJndiname(db.getChildTextTrim("jndi-name"));
			database.setUserID(db.getChildTextTrim("userid"));
			database.setPassword(db.getChildTextTrim("password"));
			database.setConnectString(db.getChildTextTrim("connectstring"));
			database.setDriver(db.getChildTextTrim("driver"));
			database.setInitCount(Integer.parseInt(db.getChildTextTrim("initcount") == null ? "0" : db.getChildTextTrim("initcount")));
			database.setMaxCount(Integer.parseInt(db.getChildTextTrim("maxcount") == null ? "0" : db.getChildTextTrim("maxcount")));
			database.setChecksql(db.getChildTextTrim("checksql"));

			Connection[] conn = new Connection[database.getInitCount()];
			for (int j = 0; j < database.getInitCount(); j++) {
				conn[j] = database.getConnection();
			}
			for (int j = 0; j < database.getInitCount(); j++) {
				conn[j].close();
			}

			databases.put(database.getId(), database);
		}
		List<Element> druidlist = root.getChildren("druid");
		DataBase2 database2;
		for (int i = 0; i < druidlist.size(); i++) {
			Element db = (Element) druidlist.get(i);
			database2 = new DataBase2();
			database2.setId(db.getChildTextTrim("id"));
			database2.setSchema(db.getChildTextTrim("schema"));
			database2.setType(db.getChildTextTrim("type"));
			database2.setDbType(db.getChildTextTrim("dbtype"));
			database2.setConnectString(db.getChildTextTrim("url"));
			database2.setUserID(db.getChildTextTrim("username"));
			database2.setPassword(db.getChildTextTrim("password"));
			database2.setChecksql(db.getChildTextTrim("validationQuery"));
			database2.setDriver(db.getChildTextTrim("driverClassName"));
			database2.setDriver(db.getChildTextTrim("driverClassName"));
			if(!CommonFun.isNe(db.getChildTextTrim("filters"))){
				database2.setFilters(db.getChildTextTrim("filters"));
			}
			database2.setInitCount(Integer.parseInt(db.getChildTextTrim("initialSize") == null ? "0" : db.getChildTextTrim("initialSize")));
			database2.setMaxCount(Integer.parseInt(db.getChildTextTrim("maxActive") == null ? "0" : db.getChildTextTrim("maxActive")));
			Properties p = new Properties();
			db.removeChild("id");
			db.removeChild("schema");
			db.removeChild("type");
			db.removeChild("dbtype");
			List<Element> l = db.getChildren();
			for(Element e: l){
				p.setProperty(e.getName(), e.getText());
			}
			database2.setDds(p);
			databases.put(database2.getId(), database2);
		}
		log.release("init connection pool success.");
	}

	public static List<Map<String, Object>> getDataSources() throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (Enumeration<String> e = databases.keys(); e.hasMoreElements();) {
			String key = e.nextElement();
			DataBase db = databases.get(key);
			Map<String, Object> record = new HashMap<String, Object>();
			record.put("id", db.getId());
			record.put("schema", db.getSchema());
			record.put("type", db.getType());
			record.put("dbType", db.getDbType());
			record.put("jndiname", db.getJndiname());
			record.put("userID", db.getUserID());
			record.put("password", db.getPassword());
			record.put("connectString", db.getConnectString());
			record.put("driver", db.getDriver());
			record.put("initCount", db.getInitCount());
			record.put("maxCount", db.getMaxCount());
			record.put("checksql", db.getChecksql());
			record.put("idlesize", db.idles.size());
			record.put("inusesize", db.inuses.size());
			record.put("size", db.idles.size() + db.inuses.size());
			result.add(record);
		}
		return result;
	}

	public static List<Map<String, Object>> getInuseLinks(String databaseid) throws Exception {
		DataBase database = databases.get(databaseid);
		if (database == null) {
			throw new Exception("Unkown DataBase ID:" + databaseid);
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (int j = 0; j < database.inuses.size(); j++) {
			Connection conn = database.inuses.get(j);
			Map<String, Object> record = new HashMap<String, Object>();
			record.put("id", database.getId());
			record.put("lastUsefulTime", format.format(conn.lastUsefulTime));
			record.put("systemTime", format.format(new java.util.Date()));
			record.put("lastCodePoint", conn.lastCodePoint);
			result.add(record);
		}
		return result;
	}

	public static Connection getConnection() throws Exception {
		return getConnection(null);
	}

	public static Connection getConnection(String databaseid) throws Exception {
		if (CommonFun.isNe(databaseid)) {
			databaseid = defaultdatabase;
		}
		DataBase database = databases.get(databaseid);
		if (database == null) {
			database = databases.get(defaultdatabase);
		}
		return database.getConnection();
	}

	public static void terminal() {
		if (monitor != null) {
			monitor.running = false;
		}
		if (idlecheck != null) {
			idlecheck.running = false;
		}
		if (usedcheck != null) {
			usedcheck.running = false;
		}
		if (logs != null) {
			logs.running = false;
		}
		Vector<String> names = new Vector<String>(databases.keySet());
		for (int i = 0; i < names.size(); i++) {
			DataBase database = databases.remove(names.get(i));

			try {
				database.terminal();
			} catch (Exception ex) {

			}
		}
		if (idlecheck != null) {
			idlecheck.interrupt();
		}
		if (usedcheck != null) {
			usedcheck.interrupt();
		}
		if (monitor != null) {
			monitor.interrupt();
		}
		if (logs != null) {
			logs.interrupt();
		}
		databases = new Hashtable<String, DataBase>();
	}

	private static class idlecheck extends Thread {
		protected boolean running = true;

		@Override
		public void run() {
			while (running) {
				try {
					sleep(60000);
				} catch (Exception ex) {

				}
				if (!running) {
					break;
				}
				Vector<String> names = new Vector<String>(databases.keySet());
				for (int i = 0; i < names.size(); i++) {
					DataBase database = databases.get(names.get(i));
					if (database.getType().equals(DataBase.TYPE_JNDI) || database.getType().equals(DataBase.TYPE_DURID)) {
						continue;
					}
					try {
						if (database.idles.size() > database.getInitCount()) {
							for (int j = 0; j < database.idles.size(); j++) {
								Connection conn = database.idles.get(j);
								if (System.currentTimeMillis() - conn.lastUsefulTime.getTime() >= idlerelease * 60 * 1000) {
									conn.terminal();
									j--;
									Logs("DataBase[" + database.getId() + "] Idled Connection Terminal.");
								}

								if (database.idles.size() <= database.getInitCount()) {
									break;
								}
							}
						}
					} catch (Exception ex) {
						Logs("DataBase[" + database.getId() + "] Idled Connection Terminal Failed. cause by : " + ex.getMessage());
					}
				}
			}
		}
	}

	private static class usedcheck extends Thread {
		protected boolean running = true;

		@Override
		public void run() {
			while (running) {
				try {
					sleep(60000);
				} catch (Exception ex) {

				}
				if (!running) {
					break;
				}
				Vector<String> names = new Vector<String>(databases.keySet());
				for (int i = 0; i < names.size(); i++) {
					DataBase database = databases.get(names.get(i));

					try {
						for (int j = 0; j < database.inuses.size(); j++) {
							Connection conn = database.inuses.get(j);
							if (database.getType().equals(DataBase.TYPE_JNDI) || database.getType().equals(DataBase.TYPE_DURID)) {
								continue;
							}
							if (System.currentTimeMillis() - conn.lastUsefulTime.getTime() >= usedrelease * 60 * 1000) {
								conn.terminal();
								j--;
								Logs("DataBase[" + database.getId() + "] Used Connection Terminal: " + conn.lastCodePoint);
							}
						}
					} catch (Exception ex) {
						Logs("DataBase[" + database.getId() + "] Used Connection Terminal Failed. cause by : " + ex.getMessage());
					}
				}
			}
		}
	}

	private static class monitor extends Thread {
		protected boolean running = true;

		@Override
		public void run() {
			while (running) {
				try {
					sleep(logcycle * 60 * 1000);
				} catch (Exception ex) {

				}
				if (!running) {
					break;
				}
				Vector<String> names = new Vector<String>(databases.keySet());
				for (int i = 0; i < names.size(); i++) {
					DataBase database = databases.get(names.get(i));

					Logs("DataBase[" + database.getId() + "]: " + (database.inuses.size() + database.idles.size()) + "/" + database.getMaxCount()
							+ " (" + database.inuses.size() + " inuse, " + database.idles.size() + " idle)");
					if (database.inuses.size() > 0) {
						Logs("-----------------------------------------------");
						Logs("lastUsefulTime\t\tlastCodePoint");
						Logs("-----------------------------------------------");
						for (int j = 0; j < database.inuses.size(); j++) {
							Connection conn = database.inuses.get(j);
							Logs(format.format(conn.lastUsefulTime) + "\t" + conn.lastCodePoint);
						}
						Logs("-----------------------------------------------");
					}
				}
			}
		}
	}

	protected static void Logs(String log) {
		Log l = new Log();
		l.message = log;
		l.time = new Date();

		logs.log(l);
	}

	private static class Log {
		protected Date time;
		protected String message;
	}

	private static class logs extends Thread {
		private static ArrayList<Log> logsqueue = new ArrayList<Log>();

		private boolean running = true;

		private Log log;

		private boolean logtoconsole;

		private String path;

		private final java.text.SimpleDateFormat dateformat = new java.text.SimpleDateFormat("yyyy-MM-dd");

		private final java.text.SimpleDateFormat timeformat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

		private void loadProperties() throws IOException {
			path = GlobalVar.getSystemProperty("log.path", "logs");
			if (path.charAt(1) != ':' && !path.startsWith("/")) {
				path = GlobalVar.getWorkPath() + File.separator + path;
			}
			logtoconsole = "1".equals(GlobalVar.getSystemProperty("log.logtoconsole", "0"));
		}

		protected void log(Log log) {
			synchronized (logsqueue) {
				logsqueue.add(log);
				logsqueue.notifyAll();
			}
		}

		@Override
		public void run() {
			while (running || !logsqueue.isEmpty()) {
				synchronized (logsqueue) {
					try {
						if (logsqueue.isEmpty()) {
							logsqueue.wait();
						}
					} catch (Exception ex) {
						continue;
					}
					log = null;
					if (logsqueue.size() > 0) {
						log = logsqueue.remove(0);
					}
				}
				if (log == null) {
					continue;
				}

				Log();
			}
		}

		private void Log() {
			try {
				loadProperties();
			} catch (Exception t) {
				return;
			}

			String msg = timeformat.format(log.time) + " " + log.message;

			if (logtoconsole) {
				//System.out.println(msg);
			}
			try {
				RandomAccessFile oAccessLogFile = getLogFile();
				oAccessLogFile.seek(oAccessLogFile.length());
				oAccessLogFile.write((msg + "\r\n").getBytes());
				oAccessLogFile.close();
			} catch (Exception ex) {
				System.err.println(msg.toString());
				ex.printStackTrace();
			}
		}

		private RandomAccessFile getLogFile() throws Exception {
			RandomAccessFile oAccessLogFile = null;

			StringBuffer filepath = new StringBuffer();
			filepath.append(path);
			filepath.append(File.separator);
			filepath.append(dateformat.format(new Date()));

			File file = new File(filepath.toString());
			if (!file.exists()) {
				file.mkdirs();
			}
			if (!file.exists()) {
				return null;
			}

			filepath.append(File.separator);
			filepath.append("database.log");
			oAccessLogFile = new RandomAccessFile(filepath.toString(), "rw");
			return oAccessLogFile;
		}
	}

}
