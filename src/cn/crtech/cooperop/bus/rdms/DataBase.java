package cn.crtech.cooperop.bus.rdms;

import java.sql.*;
import java.util.*;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.control.BaseService;

public class DataBase {
	protected DataBase() {
	}

	private Object synchronizedobj = new Object();
	protected boolean terminaled;

	protected Connection getConnection() throws Exception {
		if (terminaled)
			return null;
		Connection conn = null;
		synchronized (synchronizedobj) {
			if (terminaled)
				return null;
			if (TYPE_JDBC.equalsIgnoreCase(type)) {
				if (idles.size() > 0) {
					conn = (Connection) idles.remove(idles.size() - 1);
					if (checksql != null) {
						if (checksql.trim().length() > 0) {
							PreparedStatement pstmt = null;
							ResultSet rs = null;
							try {
								pstmt = conn.prepareStatement(checksql);
								rs = pstmt.executeQuery();
								rs.close();
								pstmt.close();
							} catch (Exception ex) {
								if (rs != null) {
									try {
										rs.close();
									} catch (Exception e) {

									}
								}
								if (pstmt != null) {
									try {
										pstmt.close();
									} catch (Exception e) {

									}
								}
								ConnectionPool.Logs("DataBase[" + id + "] Connection Terminal\r\n\tcause by " + ex.getMessage()
										+ "\r\n\tat " + conn.lastCodePoint);
								try {
									conn.terminal();
								} catch (Exception e) {

								}
								conn = new Connection(this);
							}
						}
					}
				} else {
					if (idles.size() + inuses.size() > maxCount) {
						throw new Exception("DataBase[" + id + "] is full.");
					}
					conn = new Connection(this);
				}
			} else {
				conn = new Connection(this);
			}
			inuses.add(conn);
			conn.lastUsefulTime = new java.util.Date();
			try {
				Throwable tmp = new Throwable();
				for (int i = 0; i < tmp.getStackTrace().length; i++) {
					if (!tmp.getStackTrace()[i].getClassName().startsWith(this.getClass().getPackage().getName())
							&& !tmp.getStackTrace()[i].getClassName()
									.startsWith(BaseService.class.getPackage().getName())) {
						String clasname = tmp.getStackTrace()[i].getClassName();
						String methodname = tmp.getStackTrace()[i].getMethodName();
						String filename = tmp.getStackTrace()[i].getFileName();
						int linenumber = tmp.getStackTrace()[i].getLineNumber();
						boolean isnative = tmp.getStackTrace()[i].isNativeMethod();
						conn.lastCodePoint = clasname + "." + methodname + "(" + filename + ":"
								+ (isnative ? "Native Code" : linenumber) + ")";
						break;
					}
				}
			} catch (Exception ex) {
				conn.lastCodePoint = null;
			}
		}

		return conn;
	}

	protected synchronized void freeConnction(Connection conn) {
		if (terminaled)
			return;
		synchronized (synchronizedobj) {
			if (terminaled)
				return;
			conn.lastUsefulTime = new java.util.Date();
			if (TYPE_JDBC.equalsIgnoreCase(type) && (ConnectionPool.lifetime > 0 && conn.lastUsefulTime.getTime()
					- ConnectionPool.lifetime * 60 * 60 * 1000 < conn.createTime.getTime())) {
				if (inuses.remove(conn)) {
					idles.add(conn);
				}
			} else {
				try {
					ConnectionPool.Logs("DataBase[" + id + "] terminal database Connection.");
					inuses.remove(conn);
					idles.remove(conn);
					if (conn.conn != null && !conn.conn.isClosed())
						conn.conn.close();

					conn.conn = null;
					conn = null;
				} catch (Exception e) {
					ConnectionPool.Logs("DataBase[" + id + "] terminal database Connection error. " + e.getMessage());
					log.error("DataBase[" + id + "] terminal database Connection error. " + e.getMessage(), e);
				}
				if (idles.size() < initCount) {
					try {
						idles.add(new Connection(this));
					} catch (Exception e) {
						ConnectionPool.Logs("DataBase[" + id + "] new init database Connection error. " + e.getMessage());
						log.error("DataBase[" + id + "] new init database Connection error. " + e.getMessage(), e);
					}
				}
			}
		}
	}

	protected synchronized void terminalConnection(Connection conn) throws Exception {
		if (terminaled)
			return;
		synchronized (synchronizedobj) {
			if (terminaled)
				return;
			inuses.remove(conn);
			idles.remove(conn);
			if (conn.conn != null && !conn.conn.isClosed())
				conn.conn.close();

			conn.conn = null;
			conn = null;
		}
	}

	protected synchronized void terminal() throws Exception {
		if (terminaled)
			return;
		synchronized (synchronizedobj) {
			if (terminaled)
				return;
			while (idles.size() > 0) {
				Connection conn = (Connection) idles.remove(0);
				conn.conn.close();
				conn = null;
			}
			while (inuses.size() > 0) {
				Connection conn = (Connection) idles.remove(0);
				conn.conn.close();
				conn = null;
			}
			terminaled = true;
		}
	}

	public String getConnectString() {
		return connectString;
	}

	public String getDriver() {
		return driver;
	}

	public String getId() {
		return id;
	}

	public int getInitCount() {
		return initCount;
	}

	public int getMaxCount() {
		return maxCount;
	}

	protected String getPassword() {
		return password;
	}

	public String getSchema() {
		return schema;
	}

	public String getUserID() {
		return userID;
	}

	public String getChecksql() {
		return checksql;
	}

	protected void setConnectString(String connectString) {
		this.connectString = connectString;
	}

	protected void setDriver(String driver) throws Exception {
		this.driver = driver;
		if (driver != null)
			DriverManager.registerDriver((Driver) Class.forName(driver).newInstance());
	}

	protected void setId(String id) {
		this.id = id;
	}

	protected void setInitCount(int initCount) {
		this.initCount = initCount;
	}

	protected void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	protected void setPassword(String password) {
		this.password = password;
	}

	protected void setSchema(String schema) {
		this.schema = schema;
	}

	protected void setUserID(String userID) {
		this.userID = userID;
	}

	protected void setChecksql(String checksql) {
		this.checksql = checksql;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setJndiname(String jndiname) {
		this.jndiname = jndiname;
	}

	public String getJndiname() {
		return jndiname;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	private String id;
	private String schema;
	private String type;
	private String dbType;
	private String jndiname;
	private String userID;
	private String password;
	private String connectString;
	private String driver;
	private int initCount;
	private int maxCount;
	private String checksql;
	private String filters;

	public static String TYPE_JDBC = "JDBC";
	public static String TYPE_JNDI = "JNDI";
	public static String TYPE_DURID = "DURID";

	protected Vector<Connection> idles = new Vector<Connection>();
	protected Vector<Connection> inuses = new Vector<Connection>();
}
