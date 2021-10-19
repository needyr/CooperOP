package cn.crtech.cooperop.bus.mvc.control;

import java.sql.SQLException;
import java.util.Map;

import cn.crtech.cooperop.application.bean.Account;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Connection;
import cn.crtech.cooperop.bus.rdms.ConnectionPool;
import cn.crtech.cooperop.bus.session.Session;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.LocalThreadMap;

public abstract class BaseService {
	private Connection conn;
	protected boolean hasCreateConnection;
	protected boolean hasStartTransaction;

	public Connection getConnection() {
		return conn;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public void connect() throws Exception {
		String pageid = (String) LocalThreadMap.get("pageid");
		if (!CommonFun.isNe(pageid) && pageid.indexOf('.') >= 0) {
			String moduleName = pageid.substring(0, pageid.indexOf('.'));
			connect(moduleName);
		}
		else {
			connect(null);
		}
	}

	public void connect(String datasource) throws Exception {
		String codepoint = null;
		Throwable tmp = new Throwable();
		for (int i = 0; i < tmp.getStackTrace().length; i++) {
			if (!tmp.getStackTrace()[i].getClassName().startsWith(
					BaseService.class.getPackage().getName())) {
				String clasname = tmp.getStackTrace()[i].getClassName();
				String methodname = tmp.getStackTrace()[i]
						.getMethodName();
				String filename = tmp.getStackTrace()[i].getFileName();
				int linenumber = tmp.getStackTrace()[i].getLineNumber();
				boolean isnative = tmp.getStackTrace()[i]
						.isNativeMethod();
				codepoint = clasname + "." + methodname + "("
						+ filename + ":"
						+ (isnative ? "Native Code" : linenumber) + ")";
				break;
			}
		}
		if (conn == null){
			Connection localConn = (Connection) LocalThreadMap.get("dbconn");
			if(localConn != null && localConn.getDBConfigId().equals(datasource)){
				hasCreateConnection = false;
				this.conn = localConn;
				log.debug("Service[" + codepoint + "]获取数据库连接，使用本地线程池链接：" + conn);
				if (!codepoint.equals(conn.lastCodePoint)) {
					String currentapp = codepoint.substring("cn.crtech.cooperop.".length(), codepoint.indexOf('.', "cn.crtech.cooperop.".length()));
					String connapp = conn.lastCodePoint.substring("cn.crtech.cooperop.".length(), codepoint.indexOf('.', "cn.crtech.cooperop.".length()));
					if (!currentapp.equals(connapp)) {
						log.error("Service[" + codepoint + "]使用了非本应用链接：" + conn);
					}
				}
			}else{
				hasCreateConnection = true;
				conn = ConnectionPool.getConnection(datasource);
				if (conn != null) {
					conn.setAutoCommit(true);
					LocalThreadMap.put("dbconn", conn);
				}
				log.debug("Service[" + codepoint + "]获取数据库连接，新建链接：" + conn);
			}
		} else {
			log.debug("Service[" + codepoint + "]获取数据库连接，使用本类链接：" + conn);
		}
	}

	public void disconnect() {
		if (conn == null) {
			return;
		}
		if(hasCreateConnection){
			try {
				if (!conn.getAutoCommit()) {
					conn.rollback();
					conn.setAutoCommit(true);
				}
			} catch (Exception ex) {
				log.error("disconnect database error. ", ex);
			}finally{
				try {
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			conn = null;
			LocalThreadMap.remove("dbconn");
			String pageid = (String) LocalThreadMap.get("pageid");
			if (CommonFun.isNe(pageid)) {
				pageid = this.getClass().getName();
			}
		}else{
			log.warning("current method not create connection ,so  please not to disconnect");
		}
//		 log.debug("[Thread:" + Thread.currentThread().getId() + "] disconnect to database success. [page=" + pageid + "]");
	}

	public void start() throws Exception {
		if (conn != null) {
			if(conn.getAutoCommit()){
				hasStartTransaction = true;//还得判断，连接本身是否已经开启事务
				conn.setAutoCommit(false);
			}
		}
	}

	public void commit() throws Exception {
		if (conn != null && hasStartTransaction) {
			if (!conn.getAutoCommit()) {
				conn.commit();
				conn.setAutoCommit(true);
			}
		}
	}

	public Account user(){
		Session session = Session.getSession();
		if (session == null) return null;
		Account user = (Account)session.get("userinfo");
		return user;
	}
	
	public void rollback() {
		if (conn != null && hasStartTransaction) {
			try {
				if (!conn.getAutoCommit()) {
					conn.rollback();
					conn.setAutoCommit(true);
				}
			} catch (Exception ex) {
				log.error("rollback database transtion error. ", ex);
			}
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		disconnect();
	}
}
