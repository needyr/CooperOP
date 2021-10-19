package cn.crtech.cooperop.bus.log;

import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.io.FileFilter;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.io.IOException;

import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;

public class log {
	private static final int SPLITBYSIZE = 1;

	private static final int SPLITBYDATE = 2;

	private static final int SPLITBYHOUR = 3;

	private static final int ERROR = 0;

	private static final int WARN = 1;

	private static final int RUN = 2;

	private static final int RELEASE = 3;

	private static final int INFO = 4;

	private static final int DEBUG = 5;

	private static int maxlevel;

	private static String[] levelname;

	private static String format;

	private static int stacklength;

	private static int splittype;

	private static long splitsize;

	private static int splitbyapp;

	private static boolean logtoconsole;

	private static int clearinterval;
	
	private static int clearcycle;

	private static String path;

	private static java.text.SimpleDateFormat dateformat = new java.text.SimpleDateFormat("yyyy-MM-dd");

	private static java.text.SimpleDateFormat hourformat = new java.text.SimpleDateFormat("hh");

	private static java.text.SimpleDateFormat timeformat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	private static RunLogs logs;

	private static ExceptionLogs exlogs;
	
	private static ClearLogs clears;

	public static void init() throws Exception {
		loadProperties();
		logs = new RunLogs();
		logs.start();
		exlogs = new ExceptionLogs();
		exlogs.start();
		clears = new ClearLogs();
		clears.start();
		log.release("init log engine success. path=" + path);
	}

	private static void loadProperties() throws IOException {
		path = GlobalVar.getSystemProperty("log.path", "logs");
		if (path.charAt(1) != ':' && !path.startsWith("/")) {
			path = GlobalVar.getWorkPath() + File.separator + path;
		}
		maxlevel = Integer.parseInt(GlobalVar.getSystemProperty("log.maxlevel", "5"));
		levelname = GlobalVar.getSystemProperty("log.levelname", "Error,Warning,Run,Release,Info,Debug").split(",");
		stacklength = Integer.parseInt(GlobalVar.getSystemProperty("log.stacklength", "0"));
		format = GlobalVar.getSystemProperty("log.format", "@TIME @MODEL\r\n@LEVEL @LOG");
		logtoconsole = "1".equals(GlobalVar.getSystemProperty("log.logtoconsole", "0"));
		splittype = Integer.parseInt(GlobalVar.getSystemProperty("log.splittype", "2"));
		splitbyapp = Integer.parseInt(GlobalVar.getSystemProperty("log.splitbyapp", "1"));
		splitsize = Long.parseLong(GlobalVar.getSystemProperty("log.splitsize", "10485760"));
		clearinterval = Integer.parseInt(GlobalVar.getSystemProperty("log.clearinterval", "4"));
		clearcycle = Integer.parseInt(GlobalVar.getSystemProperty("log.clearcycle", "7"));
	}

	public static void terminal() {
		RunLogs.terminal();
		if (logs != null) {
			if (logs.isAlive()) {
				try {
					logs.join();
				} catch (Exception ex) {

				}
			}
		}
		ExceptionLogs.terminal();
		if (exlogs != null) {
			if (exlogs.isAlive()) {
				try {
					exlogs.join();
				} catch (Exception ex) {

				}
			}
		}
		ClearLogs.terminal();
		if (clears != null) {
			if (clears.isAlive()) {
				try {
					clears.join();
				} catch (Exception ex) {

				}
			}
		}
	}

	private static void Logs(int level, String message) {
		Log log = new Log();
		log.level = level;
		log.time = new Date();
		log.message = message;

		Throwable tmp = new Throwable();
		log.classname = tmp.getStackTrace()[2].getClassName();
		log.methodname = tmp.getStackTrace()[2].getMethodName();
		log.filename = tmp.getStackTrace()[2].getFileName();
		log.linenumber = tmp.getStackTrace()[2].getLineNumber();

		RunLogs.Logs(log);
	}

	public static void debug(String message) {
		Logs(DEBUG, message);
	}

	public static void info(String message) {
		Logs(INFO, message);
	}

	public static void release(String message) {
		Logs(RELEASE, message);
	}

	public static void run(String message) {
		Logs(RUN, message);
	}

	public static void warning(String message) {
		Logs(WARN, message);
	}

	private static void ExceptionLogs(String message, Throwable ex) {
		Log log = new Log();
		log.level = ERROR;
		log.time = new Date();
		log.message = message;

		Throwable tmp = new Throwable();
		log.classname = tmp.getStackTrace()[2].getClassName();
		log.methodname = tmp.getStackTrace()[2].getMethodName();
		log.filename = tmp.getStackTrace()[2].getFileName();
		log.linenumber = tmp.getStackTrace()[2].getLineNumber();

		RunLogs.Logs(log);

		ExceptionLog exlog = new ExceptionLog();
		exlog.ex = ex;
		exlog.message = log.message;
		exlog.time = log.time;
		exlog.classname = log.classname;
		exlog.methodname = log.methodname;
		exlog.filename = log.filename;
		exlog.linenumber = log.linenumber;

		ExceptionLogs.Logs(exlog);
	}

	public static void error(String message, Throwable ex) {
		ExceptionLogs(message, ex);
	}

	public static void error(String message) {
		ExceptionLogs(message, new Throwable(message));
	}

	public static void error(Throwable ex) {
		ExceptionLogs(ex.getMessage(), ex);
	}

	private static class Log {
		protected int level;
		protected Date time;
		protected String classname;
		protected String methodname;
		protected String filename;
		protected int linenumber;
		protected String message;
		protected long thread_id = Thread.currentThread().getId();
	}

	private static class RunLogs extends Thread {
		private static ArrayList<Log> logsqueue = new ArrayList<Log>();

		private static boolean running = true;

		private Log log;

		protected static void Logs(Log log) {
			synchronized (logsqueue) {
				logsqueue.add(log);
				logsqueue.notifyAll();
			}
		}

		protected static void terminal() {
			running = false;
			synchronized (logsqueue) {
				if (logsqueue.isEmpty()) {
					logsqueue.notifyAll();
				}
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

			if (log.level > maxlevel) {
				return;
			}

			String msg = format;
			msg = CommonFun.replaceOnlyStr(msg, "@THREAD", String.valueOf(log.thread_id));
			msg = CommonFun.replaceOnlyStr(msg, "@TIME", timeformat.format(log.time));
			msg = CommonFun.replaceOnlyStr(msg, "@MODEL", log.classname + "." + log.methodname + "(" + log.filename
					+ ":" + log.linenumber + ")");
			msg = CommonFun.replaceOnlyStr(msg, "@LEVEL", levelname[log.level]);
			msg = CommonFun.replaceOnlyStr(msg, "@LOG", log.message);

			if (logtoconsole) {
				//log.debug(msg);
			}
			try {
				RandomAccessFile oAccessLogFile = null;
				while (oAccessLogFile == null) {
					oAccessLogFile = getLogFile();
				}
				oAccessLogFile.seek(oAccessLogFile.length());
				oAccessLogFile.write((msg + "\r\n").getBytes());
				oAccessLogFile.close();
			} catch (Exception ex) {
				System.err.println(msg.toString());
				ex.printStackTrace();
			}
		}

		private RandomAccessFile getLogFile() throws Exception {
			File file;
			RandomAccessFile oAccessLogFile = null;

			StringBuffer filepath = new StringBuffer();
			filepath.append(path);

			switch (splittype) {
			case SPLITBYDATE: {
				filepath.append(File.separator);
				filepath.append(dateformat.format(log.time));
				if (splitbyapp == 1) {
					String module = log.classname.substring("cn.crtech.cooperop.".length(),
							log.classname.indexOf(".", "cn.crtech.cooperop.".length()));
					filepath.append(File.separator);
					filepath.append(module);
				}
				break;
			}
			case SPLITBYHOUR: {
				filepath.append(File.separator);
				filepath.append(dateformat.format(log.time));
				filepath.append(File.separator);
				filepath.append(hourformat.format(log.time));
				if (splitbyapp == 1) {
					String module = log.classname.substring("cn.crtech.cooperop.".length(),
							log.classname.indexOf(".", "cn.crtech.cooperop.".length()));
					filepath.append(File.separator);
					filepath.append(module);
				}
				break;
			}
			case SPLITBYSIZE: {
				if (splitbyapp == 1) {
					String module = log.classname.substring("cn.crtech.cooperop.".length(),
							log.classname.indexOf(".", "cn.crtech.cooperop.".length()));
					filepath.append(File.separator);
					filepath.append(module);
				}
				file = new File(filepath.toString(), "run.log");
				if (file.exists()) {
					if (file.length() >= splitsize) {
						java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH.mm.ss.SSS");
						StringBuffer bakfilepath = new StringBuffer();
						bakfilepath.append(path);
						bakfilepath.append(File.separator);
						bakfilepath.append("run_");
						bakfilepath.append(format.format(new Date(file.lastModified())));
						bakfilepath.append(".log");
						CommonFun.moveFile(file, new File(bakfilepath.toString()), true);
					}
				}
				file = null;
				break;
			}
			}

			file = new File(filepath.toString());
			if (!file.exists()) {
				file.mkdirs();
			}
			if (!file.exists()) {
				throw new Exception("create log folder failed.");
			}

			filepath.append(File.separator);
			filepath.append("run.log");
			oAccessLogFile = new RandomAccessFile(filepath.toString(), "rw");
			return oAccessLogFile;
		}
	}

	private static class ExceptionLog {
		protected String message;
		protected Date time;
		protected Throwable ex;
		protected String classname;
		protected String methodname;
		protected String filename;
		protected int linenumber;
	}

	private static class ExceptionLogs extends Thread {
		private static ArrayList<ExceptionLog> logsqueue = new ArrayList<ExceptionLog>();

		private static boolean running = true;

		private ExceptionLog log;

		protected static void Logs(ExceptionLog log) {
			synchronized (logsqueue) {
				logsqueue.add(log);
				logsqueue.notifyAll();
			}
		}

		protected static void terminal() {
			running = false;
			synchronized (logsqueue) {
				if (logsqueue.isEmpty()) {
					logsqueue.notifyAll();
				}
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

			StringBuffer msg = new StringBuffer();
			String msg2 = format;
			msg2 = CommonFun.replaceOnlyStr(msg2, "@TIME", timeformat.format(log.time));
			msg2 = CommonFun.replaceOnlyStr(msg2, "@MODEL", log.classname + "." + log.methodname + "(" + log.filename
					+ ":" + log.linenumber + ")");
			msg2 = CommonFun.replaceOnlyStr(msg2, "@LEVEL", levelname[ERROR]);
			msg2 = CommonFun.replaceOnlyStr(msg2, "@LOG", log.message);
			msg.append(msg2);

			Throwable ca = log.ex;
			while (ca != null) {
				msg.append("\r\nCause: ");
				msg.append(ca.toString() + ((ca.getMessage() == null) ? ": null" : ""));
				for (int i = 0; i < ca.getStackTrace().length; i++) {
					msg.append("\r\n\tat ");
					String clasname = ca.getStackTrace()[i].getClassName();
					String methodname = ca.getStackTrace()[i].getMethodName();
					String filename = ca.getStackTrace()[i].getFileName();
					int linenumber = ca.getStackTrace()[i].getLineNumber();
					boolean isnative = ca.getStackTrace()[i].isNativeMethod();
					msg.append(clasname + "." + methodname + "(" + filename + ":"
							+ (isnative ? "Native Code" : linenumber) + ")");
					if (stacklength > 0 && i >= stacklength) {
						msg.append("\r\n\t.....");
						break;
					}
				}
				ca = ca.getCause();
			}

			if (logtoconsole) {
				System.err.println(msg.toString());
			}
			try {
				RandomAccessFile oAccessLogFile = null;
				while (oAccessLogFile == null) {
					oAccessLogFile = getLogFile();
				}
				oAccessLogFile.seek(oAccessLogFile.length());
				oAccessLogFile.write((msg.toString() + "\r\n").getBytes());
				oAccessLogFile.close();
			} catch (Exception ex) {
				System.err.println(msg.toString());
				ex.printStackTrace();
			}
		}

		private RandomAccessFile getLogFile() throws Exception {
			File file;
			RandomAccessFile oAccessLogFile = null;

			StringBuffer filepath = new StringBuffer();
			filepath.append(path);

			switch (splittype) {
			case SPLITBYDATE: {
				filepath.append(File.separator);
				filepath.append(dateformat.format(log.time));
				if (splitbyapp == 1) {
					String module = log.classname.substring("cn.crtech.cooperop.".length(),
							log.classname.indexOf(".", "cn.crtech.cooperop.".length()));
					filepath.append(File.separator);
					filepath.append(module);
				}
				break;
			}
			case SPLITBYHOUR: {
				filepath.append(File.separator);
				filepath.append(dateformat.format(log.time));
				filepath.append(File.separator);
				filepath.append(hourformat.format(log.time));
				if (splitbyapp == 1) {
					String module = log.classname.substring("cn.crtech.cooperop.".length(),
							log.classname.indexOf(".", "cn.crtech.cooperop.".length()));
					filepath.append(File.separator);
					filepath.append(module);
				}
				break;
			}
			case SPLITBYSIZE: {
				if (splitbyapp == 1) {
					String module = log.classname.substring("cn.crtech.cooperop.".length(),
							log.classname.indexOf(".", "cn.crtech.cooperop.".length()));
					filepath.append(File.separator);
					filepath.append(module);
				}
				file = new File(filepath.toString(), "exception.log");
				if (file.exists()) {
					if (file.length() >= splitsize) {
						java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH.mm.ss.SSS");
						StringBuffer bakfilepath = new StringBuffer();
						bakfilepath.append(path);
						bakfilepath.append(File.separator);
						bakfilepath.append("exception_");
						bakfilepath.append(format.format(new Date(file.lastModified())));
						bakfilepath.append(".log");
						CommonFun.moveFile(file, new File(bakfilepath.toString()), true);
					}
				}
				file = null;
				break;
			}
			}

			file = new File(filepath.toString());
			if (!file.exists()) {
				file.mkdirs();
			}
			if (!file.exists()) {
				throw new Exception("create log folder failed.");
			}

			filepath.append(File.separator);
			filepath.append("exception.log");
			oAccessLogFile = new RandomAccessFile(filepath.toString(), "rw");
			return oAccessLogFile;
		}
	}

	private static class ClearLogs extends Thread {
		private static boolean running = true;

		protected static void terminal() {
			running = false;
			if (clears != null) {
				clears.interrupt();
			}
		}

		@Override
		public void run() {
			try {
				loadProperties();
			} catch (IOException e) {
				log.error("start log clear thread load properties error: " + e.getMessage(), e);
			}
			while (running) {
				Date now = new Date();
				
				StringBuffer filepath = new StringBuffer();
				filepath.append(path);

				File logfolder = new File(filepath.toString());
				if (logfolder.exists()) {
					CommonFun.deleteFile(logfolder, new FileFilter() {
						@Override
						public boolean accept(File file) {
							if (file.isDirectory()) return true;
							if (file.lastModified() < now.getTime() - (clearcycle * 24 * 60 * 60 * 1000)) {
								return true;
							}
							return false;
						}
					});
				}
				
				if (running) {
					try {
						loadProperties();
						sleep(clearinterval * 60 * 60 * 1000);
					} catch (Exception ex) {
					}
				}
			}
		}
	}
}
