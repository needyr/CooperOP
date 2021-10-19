package cn.crtech.cooperop.bus.rdms;

import java.sql.*;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import cn.crtech.cooperop.bus.mvc.control.BaseService;

public class DataBase2 extends DataBase{
	protected DataBase2() {
	}

	private Object synchronizedobj = new Object();

	protected Connection getConnection() throws Exception {
		if (terminaled)
			return null;
		Connection conn = null;
		synchronized (synchronizedobj) {
			if (terminaled)
				return null;
			conn = new Connection(this);
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




	public DruidDataSource dds;

	public void setDds (Properties properties){
		try {
			dds = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
