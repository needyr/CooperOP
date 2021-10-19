package cn.crtech.cooperop.bus.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import cn.crtech.cooperop.bus.cache.Dictionary;
import cn.crtech.cooperop.bus.cache.MemoryCache;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.cache.SystemUser;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Connection;
import cn.crtech.cooperop.bus.rdms.ConnectionPool;
import cn.crtech.cooperop.bus.schedule.ScheduleEngine;
import cn.crtech.cooperop.bus.session.Session;
import cn.crtech.cooperop.bus.workflow.core.WorkFlowEngine;
import cn.crtech.cooperop.bus.ws.server.Engine;

public class CRPasswordEncrypt {
	private static String Template = "The treasure we own is noble soul";
	private static String CharTable = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	public static String Encryptstring(String str) {
		byte CharVal;
		short StartKey, MultKey, AddKey;
		String result = "";
		str = str + (char) 1;
		StartKey = (byte) str.charAt(0);
		MultKey = (short) (StartKey * 100);
		AddKey = (short) (StartKey * 512);
		int len = Template.length();
		int strLen = str.length();
		int ctLen = CharTable.length();
		for (int i = 0; i < len; i++) {
			CharVal = (byte) (((byte) str.charAt(((i + 1) % strLen)) ^ (StartKey) >>> 8 ^ (byte) Template.charAt(i)) + strLen);
			int unsignedByte = CharVal >= 0 ? CharVal : CharVal + 256;
			CharVal = (byte) ((unsignedByte % ctLen));
			result = result + CharTable.charAt(CharVal);
			StartKey = (short) (((byte) result.charAt(i) + StartKey) * MultKey + AddKey);
		}
		return result;
	}

	public static void main(String[] args) {
		String workPath = System.getProperty("user.dir") + "/cooperop";
		String configfile = workPath + "/WEB-INF/config/conf.properties";
		try {
			GlobalVar.init(workPath, configfile);
			log.init();
			ConnectionPool.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug(Encryptstring("000000"));

		Connection conn = null;
		PreparedStatement pstmt = null, pstmt2 = null;
		ResultSet rs = null;
		try {
			conn = ConnectionPool.getConnection();

			pstmt = conn.prepareStatement(" select * from chzkzl where isnull(epass, '') > '' ");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				log.debug(rs.getString("chzkid"));
				pstmt2 = conn.prepareStatement(" update chzkzl set epass = ? where chzkid = ? ");
				pstmt2.setString(1, Encryptstring(rs.getString("pass")));
				pstmt2.setString(2, rs.getString("chzkid"));
				pstmt2.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (pstmt2 != null) {
					pstmt2.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

		try {
			ConnectionPool.terminal();
			log.terminal();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
}
