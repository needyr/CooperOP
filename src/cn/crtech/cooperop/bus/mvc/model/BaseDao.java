package cn.crtech.cooperop.bus.mvc.model;

import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLType;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import java.util.Collection;
import java.util.Comparator;
import java.util.Collections;

import cn.crtech.cooperop.application.authenticate.Authenticate;
import cn.crtech.cooperop.application.bean.Account;
import cn.crtech.cooperop.bus.cache.Dictionary;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Connection;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.session.Session;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.LocalThreadMap;

public class BaseDao {
	protected Connection conn;
	private final static String TABLE_NAME_NO = "system_no_seq";
	private final boolean SQLCOST = "true".equalsIgnoreCase(GlobalVar.getSystemProperty("dao.printsqlcost"));
	/**
	 * 强制打印sql,当sql耗时达到 指定毫秒数时
	 */
	private final short MAXCOSTPRINT = Short.parseShort(GlobalVar.getSystemProperty("dao.printauto_max"));

	public BaseDao() {
		this.conn = (Connection) LocalThreadMap.get("dbconn");
		if (this.conn == null) {
			log.error("get local thread database connection failed.");
		}
	}

	public String getBusinessNo(String no_prix) throws Exception {
		Record conditions = new Record();
		conditions.put("no_prix", no_prix);
		StringBuffer sql = new StringBuffer();
		sql.append(" update " + TABLE_NAME_NO + " set no = no + 1 where prix = :no_prix");
		int i = executeUpdate(sql.toString(), conditions);
		if (i == 0) {
			sql = new StringBuffer();
			sql.append(" insert into " + TABLE_NAME_NO + "(prix, no) values(:no_prix, 1)");
			i = executeUpdate(sql.toString(), conditions);
		}
		sql = new StringBuffer();
		sql.append(
				"select prix + case when isnull(year, 0) > 0 then substring(convert(varchar(4), getdate(), 120), 4 - cast(year as int) + 1, cast(year as int)) else '' end + right(replicate('0', [length]) + cast(no as varchar(max)), [length] - len(prix) - isnull(year, 0)) as no from "
						+ TABLE_NAME_NO);
		sql.append(" where prix = :no_prix");
		return executeQuerySingleRecord(sql.toString(), conditions).getString("no");
	}

	public Integer getSeqVal(String seq) throws Exception {
		String sql;
		if ("MSSQL".equals(conn.getDbType())) {
			try {
				sql = "select ident_current('" + seq + "') as seq";
				Record record = executeQuerySingleRecord(sql, null);
				if (record == null)
					return null;
				return record.getInt("seq");
			} catch (Exception e) {
				sql = "{call seq_currval('" + seq + "', :seq)}";
				Record ins = new Record();
				Map<String, Integer> outs = new HashMap<String, Integer>();
				outs.put("seq", java.sql.Types.BIGINT);
				Record rtn = executeCall(sql.toString(), ins, outs);
				return rtn.getInt("seq");
			}
		} else if ("MySQL".equals(conn.getDbType())) {
			sql = "SELECT @@IDENTITY  seq";
			Map<String, Object> outs = new HashMap<String, Object>();
			Record rtn = executeQuerySingleRecord(sql, outs);
			return rtn.getInt("seq");
		} else {
			sql = "select " + seq + ".currval as seq from dual";
			Record record = executeQuerySingleRecord(sql, null);
			if (record == null)
				return null;
			return record.getInt("seq");
		}
	}

	public String getSeqNextVal(String seq) throws Exception {
		String sql;
		if ("MSSQL".equals(conn.getDbType())) {
			sql = "{call seq_nextval('" + seq + "', :seq)}";
			Record ins = new Record();
			Map<String, Integer> outs = new HashMap<String, Integer>();
			outs.put("seq", java.sql.Types.BIGINT);
			Record rtn = executeCall(sql.toString(), ins, outs);
			return rtn.getString("seq");
		} else if ("MySQL".equals(conn.getDbType())) {
			sql = "{call seq_nextval('" + seq + "', :seq)}";
			Record ins = new Record();
			Map<String, Integer> outs = new HashMap<String, Integer>();
			outs.put("seq", java.sql.Types.BIGINT);
			Record rtn = executeCall(sql.toString(), ins, outs);
			return rtn.getString("seq");
		} else {
			sql = "select " + seq + ".nextval as seq from dual";
			Record record = executeQuerySingleRecord(sql, null);
			if (record == null)
				return null;
			return record.getString("seq");
		}
	}

	public Record executeQuerySingleRecord(String sql, Map<String, Object> parameters) throws Exception {
		Result r = executeQuery(sql, parameters);
		Record record = null;
		if (r.getResultset().size() > 0) {
			record = r.getResultset(0);
		}
		return record;
	}

	public Result executeQuery(String sql) throws Exception {
		return executeQuery(sql, null);
	}

	public Result executeQuery(String sql, Map<String, Object> parameters) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Result result;
		long t1 = System.currentTimeMillis();
		List<?> values = null;
		try {
			Object[] psql = parseSQL(sql, parameters);
			String pstmtsql = (String) psql[0];
			if ("MySQL".equals(conn.getDbType()) && parameters != null && !CommonFun.isNe(parameters.get("sort"))) {
				pstmtsql += " order by " + parameters.get("sort");
			} else if ("MSSQL".equals(conn.getDbType()) && parameters != null && !CommonFun.isNe(parameters.get("sort"))) {
				pstmtsql += " order by " + parameters.get("sort");
			}
			values = (List<?>) psql[1];
			printSql(pstmtsql, values);
			pstmt = conn.prepareStatement(pstmtsql);
			for (int i = 0; i < values.size(); i++) {
				if (values.get(i) == null || values.get(i) == "") {
					pstmt.setNull(i + 1, Types.VARCHAR);
				} else if (values.get(i).getClass().equals(Date.class)) {
					pstmt.setObject(i + 1, new java.sql.Timestamp(((Date) values.get(i)).getTime()));
				} else if (values.get(i).getClass().equals(String.class)) {
					if (values.get(i).toString().length() >= 4000) {
						String s = (String) values.get(i);
						StringReader c = new StringReader(s);
						pstmt.setCharacterStream(i + 1, c, s.length());
					} else {
						pstmt.setString(i + 1, (String) values.get(i));
					}
				} else {
					pstmt.setObject(i + 1, values.get(i));
				}
			}
			rs = pstmt.executeQuery();
			result = new Result(rs);
			rs.close();
			pstmt.close();

			result.setCount(result.getResultset().size());
			result.setStart(1);
			result.setPerpage(0);

			printSqlCost(t1, sql, values);
			return result;
		} catch (Exception ex) {
			printSqlCost(t1, sql, values);
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
			throw ex;
		}
	}
	public Result executeQueryLimit(String sql, Map<String, Object> parameters) throws Exception {
		return executeQueryLimit(sql, parameters, null);
	}
	public Result executeQueryLimit(String sql, Map<String, Object> parameters, String countSql) throws Exception {
		int start = parameters.get("start") == null ? (LocalThreadMap.get("start") == null ? 1 : Integer.parseInt(LocalThreadMap.get("start").toString()))
				: Integer.parseInt(parameters.get("start").toString());
		int limit = parameters.get("limit") == null ? (LocalThreadMap.get("limit") == null ? 25 : Integer.parseInt(LocalThreadMap.get("limit").toString()))
				: Integer.parseInt(parameters.get("limit").toString());
		return executeQueryLimit(sql, parameters, start, limit, countSql);
	}
	
	public Result executeQueryLimit(String sql, Map<String, Object> parameters, long start, int limit) throws Exception {
		return executeQueryLimit(sql, parameters, start, limit, null);
	}
	public Result executeQueryLimit(String sql, Map<String, Object> parameters, long start, int limit, String countSql) throws Exception {
		if (start == 1 && limit <= 0) {
			return executeQuery(sql, parameters);
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Result result;
		long count = 0;
		try {
			long t1 = System.currentTimeMillis();
			Object[] csql;
			if(!CommonFun.isNe(countSql)){
				csql = parseSQL(countSql, parameters);
			}else{
				csql = parseCountSQL(sql, parameters);
			}
			
			String countsql = (String) csql[0];
			List<?> cvalues = (List<?>) csql[1];
			printSql(countsql, cvalues);
			pstmt = conn.prepareStatement(countsql);
			for (int i = 0; i < cvalues.size(); i++) {
				if (cvalues.get(i) == null || cvalues.get(i) == "") {
					pstmt.setNull(i + 1, Types.VARCHAR);
				} else if (cvalues.get(i).getClass().equals(Date.class)) {
					pstmt.setObject(i + 1, new java.sql.Timestamp(((Date) cvalues.get(i)).getTime()));
				} else if (cvalues.get(i).getClass().equals(String.class)) {
					if (cvalues.get(i).toString().length() >= 4000) {
						String s = (String) cvalues.get(i);
						StringReader c = new StringReader(s);
						pstmt.setCharacterStream(i + 1, c, s.length());
					} else {
						pstmt.setString(i + 1, (String) cvalues.get(i));
					}
				} else {
					pstmt.setObject(i + 1, cvalues.get(i));
				}
			}
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getLong(1);
			}
			rs.close();
			pstmt.close();

			printSqlCost(t1, sql, cvalues);
			long t3 = System.currentTimeMillis();

			Object[] psql = parseSQL(sql, parameters);
			String pstmtsql = (String) psql[0];
			if (limit > 0) {

				long end = start + limit > count ? count : start + limit - 1;

				if ("MySQL".equals(conn.getDbType()) && parameters != null && !CommonFun.isNe(parameters.get("sort"))) {
					pstmtsql = pstmtsql + " order by " + parameters.get("sort") + " limit " + (start - 1) + ", " + limit;
				} else if ("MySQL".equals(conn.getDbType())) {
					pstmtsql = pstmtsql + " limit " + (start - 1) + ", " + limit;
				} else if ("MSSQL".equals(conn.getDbType()) && parameters != null && !CommonFun.isNe(parameters.get("sort"))) {
					pstmtsql = "select b.* from (select a.*, row_number() over(order by " + parameters.get("sort") + ") rownumA from (" + pstmtsql + ") a) b where rownumA <= " + end
							+ " and rownumA >= " + start;
				} else if ("MSSQL".equals(conn.getDbType())) {
					pstmtsql = "select b.* from (select a.*, row_number() over(order by getDate()) rownumA from (" + pstmtsql + ") a) b where rownumA <= " + end + " and rownumA >= " + start;
				} else {
					pstmtsql = "select b.* from (select a.*, rownum as rownumA from ( " + pstmtsql + " ) a where rownum <= " + end + ") b where rownumA >= " + start;
				}
			} else if ("MySQL".equals(conn.getDbType()) && parameters != null && !CommonFun.isNe(parameters.get("sort"))) {
				pstmtsql += " order by " + parameters.get("sort");
			} else if ("MSSQL".equals(conn.getDbType()) && parameters != null && !CommonFun.isNe(parameters.get("sort"))) {
				pstmtsql += " order by " + parameters.get("sort");
			}

			List<?> values = (List<?>) psql[1];
			printSql(pstmtsql, values);
			pstmt = conn.prepareStatement(pstmtsql);
			for (int i = 0; i < values.size(); i++) {
				if (values.get(i) == null || values.get(i) == "") {
					pstmt.setNull(i + 1, Types.VARCHAR);
				} else if (values.get(i).getClass().equals(Date.class)) {
					pstmt.setObject(i + 1, new java.sql.Timestamp(((Date) values.get(i)).getTime()));
				} else if (values.get(i).getClass().equals(String.class)) {
					if (values.get(i).toString().length() >= 4000) {
						String s = (String) values.get(i);
						StringReader c = new StringReader(s);
						pstmt.setCharacterStream(i + 1, c, s.length());
					} else {
						pstmt.setString(i + 1, (String) values.get(i));
					}
				} else {
					pstmt.setObject(i + 1, values.get(i));
				}
			}
			rs = pstmt.executeQuery();
			result = new Result(rs);
			rs.close();
			pstmt.close();
			printSqlCost(t3, sql, values);
			// log.debug(t4-t3+"=========sql=="+pstmtsql);
			result.setCount(count);
			result.setStart(start);
			result.setPerpage(limit);

			return result;
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
			throw ex;
		}
	}

	public Result executeQueryTotal(String sql, Map<String, Object> parameters, String totals) throws Exception {
		return executeQueryLimitTotal(sql, parameters, totals, 1, 0);
	}

	/**
	 * @param sql
	 * @param parameters
	 * @param totals
	 *            ["price,sum", "stock_date,max"]
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public Result executeQueryLimitTotal(String sql, Map<String, Object> parameters) throws Exception {
		int start = parameters.get("start") == null ? (LocalThreadMap.get("start") == null ? 1 : Integer.parseInt(LocalThreadMap.get("start").toString()))
				: Integer.parseInt(parameters.get("start").toString());
		int limit = parameters.get("limit") == null ? (LocalThreadMap.get("limit") == null ? 25 : Integer.parseInt(LocalThreadMap.get("limit").toString()))
				: Integer.parseInt(parameters.get("limit").toString());
		return executeQueryLimitTotal(sql, parameters, (String)parameters.get("totals"), start, limit);
	}
	public Result executeQueryLimitTotal(String sql, Map<String, Object> parameters, String totals, long start, int limit) throws Exception {
		return executeQueryLimitTotal(sql, parameters, totals, start, limit, null);
	}
	public Result executeQueryLimitTotal(String sql, Map<String, Object> parameters, String totals, long start, int limit, String csql_) throws Exception {
		if (CommonFun.isNe(totals)) {
			return executeQueryLimit(sql, parameters, start, limit, csql_);
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Result result;
		long count = 0;
		try {
			long t1 = System.currentTimeMillis();
			Object[] csql = null;
			if(csql_ != null && csql_.length() > 0){
				csql = parseSQL(csql_, parameters);
			}else {
				csql = parseTotalSQL(sql, parameters, totals);
			}
			String countsql = (String) csql[0];
			List<?> cvalues = (List<?>) csql[1];
			printSql(countsql, cvalues);
			pstmt = conn.prepareStatement(countsql);
			for (int i = 0; i < cvalues.size(); i++) {
				if (cvalues.get(i) == null || cvalues.get(i) == "") {
					pstmt.setNull(i + 1, Types.VARCHAR);
				} else if (cvalues.get(i).getClass().equals(Date.class)) {
					pstmt.setObject(i + 1, new java.sql.Timestamp(((Date) cvalues.get(i)).getTime()));
				} else if (cvalues.get(i).getClass().equals(String.class)) {
					if (cvalues.get(i).toString().length() >= 4000) {
						String s = (String) cvalues.get(i);
						StringReader c = new StringReader(s);
						pstmt.setCharacterStream(i + 1, c, s.length());
					} else {
						pstmt.setString(i + 1, (String) cvalues.get(i));
					}
				} else {
					pstmt.setObject(i + 1, cvalues.get(i));
				}
			}
			rs = pstmt.executeQuery();

			result = new Result();

			if (start == 1 && limit <= 0) {
				result = executeQuery(sql, parameters);
			}

			if (rs.next()) {
				count = rs.getLong(1);
				result.fillTotal(rs);
			}
			rs.close();
			pstmt.close();

			printSqlCost(t1, countsql, cvalues);

			if (start == 1 && limit <= 0) {
				return result;
			}

			long t3 = System.currentTimeMillis();
			Object[] psql = parseSQL(sql, parameters);
			String pstmtsql = (String) psql[0];
			if (limit > 0) {

				long end = start + limit > count ? count : start + limit - 1;

				if ("MySQL".equals(conn.getDbType()) && parameters != null && !CommonFun.isNe(parameters.get("sort"))) {
					pstmtsql = pstmtsql + " order by " + parameters.get("sort") + " limit " + (start - 1) + ", " + limit;
				} else if ("MySQL".equals(conn.getDbType())) {
					pstmtsql = pstmtsql + " limit " + (start - 1) + ", " + limit;
				} else if ("MSSQL".equals(conn.getDbType()) && parameters != null && !CommonFun.isNe(parameters.get("sort"))) {
					pstmtsql = "select b.* from (select a.*, row_number() over(order by " + parameters.get("sort") + ") rownumA from (" + pstmtsql + ") a) b where rownumA <= " + end
							+ " and rownumA >= " + start;
				} else if ("MSSQL".equals(conn.getDbType())) {
					pstmtsql = "select b.* from (select a.*, row_number() over(order by getDate()) rownumA from (" + pstmtsql + ") a) b where rownumA <= " + end + " and rownumA >= " + start;
				} else {
					pstmtsql = "select b.* from (select a.*, rownum as rownumA from ( " + pstmtsql + " ) a where rownum <= " + end + ") b where rownumA >= " + start;
				}
			} else if ("MySQL".equals(conn.getDbType()) && parameters != null && !CommonFun.isNe(parameters.get("sort"))) {
				pstmtsql += " order by " + parameters.get("sort");
			} else if ("MSSQL".equals(conn.getDbType()) && parameters != null && !CommonFun.isNe(parameters.get("sort"))) {
				pstmtsql += " order by " + parameters.get("sort");
			}

			List<?> values = (List<?>) psql[1];
			printSql(pstmtsql, values);
			pstmt = conn.prepareStatement(pstmtsql);
			for (int i = 0; i < values.size(); i++) {
				if (values.get(i) == null || values.get(i) == "") {
					pstmt.setNull(i + 1, Types.VARCHAR);
				} else if (values.get(i).getClass().equals(Date.class)) {
					pstmt.setObject(i + 1, new java.sql.Timestamp(((Date) values.get(i)).getTime()));
				} else if (values.get(i).getClass().equals(String.class)) {
					if (values.get(i).toString().length() >= 4000) {
						String s = (String) values.get(i);
						StringReader c = new StringReader(s);
						pstmt.setCharacterStream(i + 1, c, s.length());
					} else {
						pstmt.setString(i + 1, (String) values.get(i));
					}
				} else {
					pstmt.setObject(i + 1, values.get(i));
				}
			}
			rs = pstmt.executeQuery();
			result.fill(rs);
			rs.close();
			pstmt.close();

			printSqlCost(t3, pstmtsql, values);
			result.setCount(count);
			result.setStart(start);
			result.setPerpage(limit);

			return result;
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
			throw ex;
		}
	}

	/**
	 * 
	 * @param table
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int executeInsert(String table, Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer fsql = new StringBuffer();
		StringBuffer vsql = new StringBuffer();

		Iterator<String> fkeys = params.keySet().iterator();

		while (fkeys.hasNext()) {
			String key = fkeys.next();
			if ("$request".equals(key)) {
				continue;
			}
			fsql.append(key + ", ");
			vsql.append(":" + key + ", ");
		}

		sql.append("insert into " + table + " (");
		sql.append(fsql.substring(0, fsql.length() - 2) + ") values(");
		sql.append(vsql.substring(0, vsql.length() - 2) + ")");
		return executeUpdate(sql.toString(), params);
	}

	/**
	 * 
	 * @param table
	 * @param params
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public int executeUpdate(String table, Map<String, Object> params, Map<String, Object> conditions) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update " + table + " set ");
		Record p = new Record(params);
		Iterator<String> keys = p.keySet().iterator();

		while (keys.hasNext()) {
			String key = keys.next();
			if ("$request".equals(key)) {
				continue;
			}
			sql.append(key + " = :" + key + ", ");
		}
		sql = new StringBuffer().append(sql.substring(0, sql.length() - 2));
		sql.append(" where 1 = 1");

		keys = conditions.keySet().iterator();
		Record c = new Record();
		while (keys.hasNext()) {
			String key = keys.next();
			if ("$request".equals(key)) {
				continue;
			}
			if (CommonFun.isNe(conditions.get(key))) {
				sql.append("   and " + key + " is null     ");
			} else if (conditions.get(key).getClass().isArray()) {
				sql.append("   and " + key + " in (:_c_" + key + ") ");
			} else {
				sql.append("   and " + key + " = :_c_" + key + " ");
			}
			c.put("_c_" + key, conditions.get(key));
		}
		p.putAll(c);
		return executeUpdate(sql.toString(), p);
	}

	/**
	 * 
	 * @param table
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public int executeDelete(String table, Map<String, Object> conditions) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from " + table + " ");
		sql.append(" where 1 = 1");

		Iterator<String> keys = conditions.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			if ("$request".equals(key)) {
				continue;
			}
			if (CommonFun.isNe(conditions.get(key))) {
				sql.append("   and " + key + " is null     ");
			} else if (conditions.get(key).getClass().isArray()) {
				sql.append("   and " + key + " in (:" + key + ") ");
			} else {
				sql.append("   and " + key + " = :" + key + " ");
			}
		}
		return executeUpdate(sql.toString(), conditions);
	}

	public int executeUpdate(String sql, Map<String, Object> parameters) throws Exception {
		PreparedStatement pstmt = null;
		try {
			long t1 = System.currentTimeMillis();
			Object[] psql = parseSQL(sql, parameters);
			String pstmtsql = (String) psql[0];
			List<?> values = (List<?>) psql[1];
			printSql(pstmtsql, values);
			pstmt = conn.prepareStatement(pstmtsql);
			for (int i = 0; i < values.size(); i++) {
				if (values.get(i) == null || values.get(i) == "") {
					pstmt.setNull(i + 1, Types.VARCHAR);
				} else if (values.get(i).getClass().equals(Date.class)) {
					pstmt.setObject(i + 1, new java.sql.Timestamp(((Date) values.get(i)).getTime()));
				} else if (values.get(i).getClass().equals(String.class)) {
					if (values.get(i).toString().length() >= 4000) {
						String s = (String) values.get(i);
						StringReader c = new StringReader(s);
						pstmt.setCharacterStream(i + 1, c, s.length());
					} else {
						pstmt.setString(i + 1, (String) values.get(i));
					}
				} else if (values.get(i).getClass().equals(BigInteger.class)) {
					pstmt.setBigDecimal(i + 1, new BigDecimal((BigInteger) values.get(i)));
				} else {
					pstmt.setObject(i + 1, values.get(i));
				}
			}
			int i = pstmt.executeUpdate();
			pstmt.close();
			printSqlCost(t1, sql, values);
			return i;
		} catch (Exception ex) {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {

				}
			}
			throw ex;
		}
	}

	public String execute(String sql, Map<String, Object> parameters) throws Exception {
		PreparedStatement pstmt = null;
		try {
			long t1 = System.currentTimeMillis();
			Object[] psql = parseSQL(sql, parameters);
			String pstmtsql = (String) psql[0];
			List<?> values = (List<?>) psql[1];
			printSql(pstmtsql, values);
			pstmt = conn.prepareStatement(pstmtsql);
			for (int i = 0; i < values.size(); i++) {
				if (values.get(i) == null || values.get(i) == "") {
					pstmt.setNull(i + 1, Types.VARCHAR);
				} else if (values.get(i).getClass().equals(Date.class)) {
					pstmt.setObject(i + 1, new java.sql.Timestamp(((Date) values.get(i)).getTime()));
				} else if (values.get(i).getClass().equals(String.class)) {
					if (values.get(i).toString().length() >= 4000) {
						String s = (String) values.get(i);
						StringReader c = new StringReader(s);
						pstmt.setCharacterStream(i + 1, c, s.length());
					} else {
						pstmt.setString(i + 1, (String) values.get(i));
					}
				} else if (values.get(i).getClass().equals(BigInteger.class)) {
					pstmt.setBigDecimal(i + 1, new BigDecimal((BigInteger) values.get(i)));
				} else {
					pstmt.setObject(i + 1, values.get(i));
				}
			}
			pstmt.execute();
			String r = null;
			while (pstmt.getMoreResults() || pstmt.getUpdateCount() > -1) {
				int uc = pstmt.getUpdateCount();
				if (uc > -1) {
					continue;
				}
				ResultSet rs = pstmt.getResultSet();
				if (rs != null && rs.next()) {
					r = rs.getString(1);
				}
			}

			pstmt.close();
			printSqlCost(t1, sql, values);
			return r;
		} catch (Exception ex) {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {

				}
			}
			throw ex;
		}
	}

	public Result executeCallQuery(String sql, Map<String, Object> ins) throws Exception {
		CallableStatement cstmt = null;
		Result result;
		ResultSet rs = null;
		try {
			long t1 = System.currentTimeMillis();
			Object[] psql = parseSQL(sql, ins);
			String pstmtsql = (String) psql[0];
			List<?> values = (List<?>) psql[1];
			printSql(pstmtsql, values);
			cstmt = conn.prepareCall(pstmtsql);
			for (int i = 0; i < values.size(); i++) {
				if (values.get(i) == null || values.get(i) == "") {
					cstmt.setNull(i + 1, Types.VARCHAR);
				} else if (values.get(i).getClass().equals(Date.class)) {
					cstmt.setObject(i + 1, new java.sql.Timestamp(((Date) values.get(i)).getTime()));
				} else if (values.get(i).getClass().equals(String.class)) {
					if (values.get(i).toString().length() >= 4000) {
						String s = (String) values.get(i);
						StringReader c = new StringReader(s);
						cstmt.setCharacterStream(i + 1, c, s.length());
					} else {
						cstmt.setString(i + 1, (String) values.get(i));
					}
				} else if (values.get(i).getClass().equals(BigInteger.class)) {
					cstmt.setBigDecimal(i + 1, new BigDecimal((BigInteger) values.get(i)));
				} else {
					cstmt.setObject(i + 1, values.get(i));
				}
			}

			rs = cstmt.executeQuery();
			result = new Result(rs);
			rs.close();
			cstmt.close();
			printSqlCost(t1, pstmtsql, values);
			return result;
		} catch (Exception ex) {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {

				}
			}
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (Exception e) {

				}
			}
			throw ex;
		}
	}

	public Record executeCall(String sql, Map<String, Object> ins, Map<String, Integer> outs) throws Exception {
		CallableStatement cstmt = null;
		Record rtn = new Record();
		try {
			long t1 = System.currentTimeMillis();
			Object[] psql = parseCallSQL(sql, ins, outs);
			String pstmtsql = (String) psql[0];
			@SuppressWarnings("unchecked")
			List<Parameter> values = (List<Parameter>) psql[1];
			printSql(pstmtsql, values);
			cstmt = conn.prepareCall(pstmtsql);
			for (int i = 0; i < values.size(); i++) {
				if (values.get(i).isout) {
					cstmt.registerOutParameter(i + 1, (Integer) values.get(i).value);
				} else {
					if (values.get(i) == null) {
						cstmt.setNull(i + 1, Types.VARCHAR);
					} else if (values.get(i).getClass().equals(Date.class)) {
						cstmt.setObject(i + 1, new java.sql.Timestamp(((Date) values.get(i).value).getTime()));
					} else if (values.get(i).getClass().equals(String.class)) {
						if (values.get(i).toString().length() >= 4000) {
							String s = (String) values.get(i).value;
							StringReader c = new StringReader(s);
							cstmt.setCharacterStream(i + 1, c, s.length());
						} else {
							cstmt.setString(i + 1, (String) values.get(i).value);
						}
					} else {
						cstmt.setObject(i + 1, values.get(i).value);
					}
				}
			}

			cstmt.execute();

			for (int i = 0; i < values.size(); i++) {
				if (values.get(i).isout) {
					rtn.put(values.get(i).name, cstmt.getObject(i + 1));
				}
			}

			cstmt.close();

			printSqlCost(t1, pstmtsql, values);
			return rtn;

		} catch (Exception ex) {
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (Exception e) {

				}
			}
			throw ex;
		}
	}

	public Object[] parseSQL(String sql, Map<String, Object> parameters) throws Exception {
		if (sql.indexOf(":ruleDeps") > 0) {
			sql = sql.replace(":ruleDeps", Authenticate.getRuleDepsString());
		}

		String psql = sql;
		String tsql = sql;
		List<Parameter> paramlist = new ArrayList<Parameter>();
		if (parameters != null) {
			List<String> keys = new ArrayList<String>(parameters.keySet());
			Collections.sort(keys, new KeyComparator());
			for (int k = 0; k < keys.size(); k++) {
				String key = keys.get(k);
				if ("$request".equals(key)) {
					continue;
				}

				Object value = parameters.get(key);
				while (tsql.indexOf(":" + key) > -1) {
					int index = tsql.indexOf(":" + key);
					StringBuffer tmp = new StringBuffer();
					if (value == null) {
						tmp.append("?");
						paramlist.add(new Parameter(index, value, key));
					} else if (CommonFun.isCollection(value)) {
						Collection<?> col = (Collection<?>) value;
						Object[] arraylist = col.toArray();
						for (int i = 0; i < arraylist.length; i++) {
							if (i > 0) {
								tmp.append(", ");
							}
							tmp.append("?");
							paramlist.add(new Parameter(index, arraylist[i], key));
						}
					} else if (value.getClass().isArray()) {
						if (value.getClass().getComponentType().getName().equals("boolean")) {
							boolean[] arraylist = (boolean[]) value;
							for (int i = 0; i < arraylist.length; i++) {
								if (i > 0) {
									tmp.append(", ");
								}
								tmp.append("?");
								paramlist.add(new Parameter(index, arraylist[i], key));
							}
						} else if (value.getClass().getComponentType().getName().equals("byte")) { // boolean,
																									// byte,
																									// char,
																									// short,
																									// int,
																									// long,
																									// float,
																									// and
																									// double
							byte[] arraylist = (byte[]) value;
							for (int i = 0; i < arraylist.length; i++) {
								if (i > 0) {
									tmp.append(", ");
								}
								tmp.append("?");
								paramlist.add(new Parameter(index, arraylist[i], key));
							}
						} else if (value.getClass().getComponentType().getName().equals("char")) { // boolean,
																									// byte,
																									// char,
																									// short,
																									// int,
																									// long,
																									// float,
																									// and
																									// double
							char[] arraylist = (char[]) value;
							for (int i = 0; i < arraylist.length; i++) {
								if (i > 0) {
									tmp.append(", ");
								}
								tmp.append("?");
								paramlist.add(new Parameter(index, arraylist[i], key));
							}
						} else if (value.getClass().getComponentType().getName().equals("short")) { // boolean,
																									// byte,
																									// char,
																									// short,
																									// int,
																									// long,
																									// float,
																									// and
																									// double
							short[] arraylist = (short[]) value;
							for (int i = 0; i < arraylist.length; i++) {
								if (i > 0) {
									tmp.append(", ");
								}
								tmp.append("?");
								paramlist.add(new Parameter(index, arraylist[i], key));
							}
						} else if (value.getClass().getComponentType().getName().equals("int")) { // boolean,
																									// byte,
																									// char,
																									// short,
																									// int,
																									// long,
																									// float,
																									// and
																									// double
							int[] arraylist = (int[]) value;
							for (int i = 0; i < arraylist.length; i++) {
								if (i > 0) {
									tmp.append(", ");
								}
								tmp.append("?");
								paramlist.add(new Parameter(index, arraylist[i], key));
							}
						} else if (value.getClass().getComponentType().getName().equals("long")) { // boolean,
																									// byte,
																									// char,
																									// short,
																									// int,
																									// long,
																									// float,
																									// and
																									// double
							long[] arraylist = (long[]) value;
							for (int i = 0; i < arraylist.length; i++) {
								if (i > 0) {
									tmp.append(", ");
								}
								tmp.append("?");
								paramlist.add(new Parameter(index, arraylist[i], key));
							}
						} else if (value.getClass().getComponentType().getName().equals("float")) { // boolean,
																									// byte,
																									// char,
																									// short,
																									// int,
																									// long,
																									// float,
																									// and
																									// double
							float[] arraylist = (float[]) value;
							for (int i = 0; i < arraylist.length; i++) {
								if (i > 0) {
									tmp.append(", ");
								}
								tmp.append("?");
								paramlist.add(new Parameter(index, arraylist[i], key));
							}
						} else if (value.getClass().getComponentType().getName().equals("double")) { // boolean,
																										// byte,
																										// char,
																										// short,
																										// int,
																										// long,
																										// float,
																										// and
																										// double
							double[] arraylist = (double[]) value;
							for (int i = 0; i < arraylist.length; i++) {
								if (i > 0) {
									tmp.append(", ");
								}
								tmp.append("?");
								paramlist.add(new Parameter(index, arraylist[i], key));
							}
						} else {
							Object[] arraylist = (Object[]) value;
							for (int i = 0; i < arraylist.length; i++) {
								if (i > 0) {
									tmp.append(", ");
								}
								tmp.append("?");
								paramlist.add(new Parameter(index, arraylist[i], key));
							}
						}
					} else if ("sysdate".equals(value) || "systimestamp".equals(value)) {
						if ("MySQL".equals(conn.getDbType())) {
							value = "now()";
						} else if ("MSSQL".equals(conn.getDbType())) {
							value = "getdate()";
						}
						tmp.append(value);
					} else {
						tmp.append("?");
						paramlist.add(new Parameter(index, value, key));
					}
					psql = psql.replaceFirst(":" + key, tmp.toString());
					tsql = tsql.replaceFirst(":" + key, "!" + key);
				}
			}
		}

		if (!"MSSQL".equals(conn.getDbType())) {
			psql = psql.replaceAll("(?i)dbo.", "");
			psql = psql.replaceAll("(?i)\\(nolock\\)", "");
			psql = psql.replaceAll("(?i)getdate\\(\\)", "sysdate");
			psql = psql.replaceAll("(?i)\\[system_user\\]", "system_user");
		}
		if ("MySQL".equals(conn.getDbType())) {
			psql = psql.replaceAll("(?i)sysdate", "now()");
			psql = psql.replaceAll("(?i)isnull", "ifnull");
		} else if ("MSSQL".equals(conn.getDbType())) {
			psql = psql.replaceAll("(?i)sysdate", "getdate()");
		}
		Collections.sort(paramlist, new ParameterComparator());
		List<Object> valuelist = new ArrayList<Object>();
		for (int i = 0; i < paramlist.size(); i++) {
			valuelist.add((paramlist.get(i)).value);
		}
		return new Object[] { psql, valuelist };
	}
	
	private Object[] parseCallSQL(String sql, Map<String, Object> ins, Map<String, Integer> outs) throws Exception {
		String psql = sql;
		String tsql = sql;
		List<Parameter> paramlist = new ArrayList<Parameter>();
		if (ins != null) {
			for (Iterator<String> e = ins.keySet().iterator(); e.hasNext();) {
				String key = e.next();
				if ("$request".equals(key)) {
					continue;
				}
				Object value = ins.get(key);
				while (tsql.indexOf(":" + key) > -1) {
					int index = tsql.indexOf(":" + key);
					StringBuffer tmp = new StringBuffer();
					if (value == null) {
						tmp.append("?");
						paramlist.add(new Parameter(index, value, key));
					} else if (CommonFun.isCollection(value)) {
						Collection<?> col = (Collection<?>) value;
						Object[] arraylist = col.toArray();
						for (int i = 0; i < arraylist.length; i++) {
							if (i > 0) {
								tmp.append(", ");
							}
							tmp.append("?");
							paramlist.add(new Parameter(index, arraylist[i], key));
						}
					} else if (value.getClass().isArray()) {
						Object[] arraylist = (Object[]) value;
						for (int i = 0; i < arraylist.length; i++) {
							if (i > 0) {
								tmp.append(", ");
							}
							tmp.append("?");
							paramlist.add(new Parameter(index, arraylist[i], key));
						}
					} else {
						tmp.append("?");
						paramlist.add(new Parameter(index, value, key));
					}
					psql = psql.replaceFirst(":" + key, tmp.toString());
					tsql = tsql.replaceFirst(":" + key, "!" + key);
				}
			}
		}
		if (outs != null) {
			for (Iterator<String> e = outs.keySet().iterator(); e.hasNext();) {
				String key = e.next();
				if ("$request".equals(key)) {
					continue;
				}
				Integer value = outs.get(key);
				while (tsql.indexOf(":" + key) > -1) {
					int index = tsql.indexOf(":" + key);
					StringBuffer tmp = new StringBuffer();
					tmp.append("?");
					paramlist.add(new Parameter(index, value, key, true));
					psql = psql.replaceFirst(":" + key, tmp.toString());
					tsql = tsql.replaceFirst(":" + key, "!" + key);
				}
			}
		}

		if (!"MSSQL".equals(conn.getDbType())) {
			psql = psql.replaceAll("dbo.", "");
			psql = psql.replaceAll("\\(nolock\\)", "");
		}
		Collections.sort(paramlist, new ParameterComparator());
		return new Object[] { psql, paramlist };
	}

	private Object[] parseCountSQL(String sql, Map<String, Object> parameters) throws Exception {
		Object[] tmp = parseSQL(sql, parameters);
		tmp[0] = "select count(1) as count from (" + tmp[0] + ") a";
		return tmp;
	}

	private Object[] parseTotalSQL(String sql, Map<String, Object> parameters, String totals) throws Exception {
		Object[] tmp = parseSQL(sql, parameters);
		StringBuffer ts = new StringBuffer();
		ts.append("select count(1) as count");
		String[] t = totals.split(",");
		for (int i = 0; i < t.length; i += 2) {
			ts.append(", " + t[i] + " as " + t[i + 1]);
		}
		ts.append(" from (" + tmp[0] + ") a");
		tmp[0] = ts.toString();
		return tmp;
	}
	
	private class Parameter {
		protected int index;
		protected Object value;
		protected boolean isout;
		protected String name;

		protected Parameter(int index, Object value, String name) {
			this.index = index;
			this.value = value;
			this.name = name;
			this.isout = false;
		}

		protected Parameter(int index, Object value, String name, boolean isout) {
			this.index = index;
			this.value = value;
			this.name = name;
			this.isout = isout;
		}
	}

	private class ParameterComparator implements Comparator<Object> {
		@Override
		public int compare(Object o1, Object o2) throws ClassCastException {
			Parameter p1 = (Parameter) o1;
			Parameter p2 = (Parameter) o2;
			return p1.index - p2.index;
		}
	}

	private class KeyComparator implements Comparator<Object> {
		@Override
		public int compare(Object o1, Object o2) throws ClassCastException {
			String p1 = (String) o1;
			String p2 = (String) o2;
			int l1 = p1 == null ? 0 : p1.length();
			int l2 = p2 == null ? 0 : p2.length();
			return l2 - l1;
		}
	}

	/**
	 * 
	 * 设置sql的where子句，通过判断HashMap中是否包含某键值对，来判断是否拼接sql到查询语句后面，
	 * 
	 * @param HashMap2
	 *            参数存放HashMap
	 * @param key
	 *            HashMap中包含的key，如果存，则将 参数中的sql拼接到 StringBuffer sb后面
	 * @param sql
	 *            要拼接到字符串后面的字符
	 * @param sbf
	 *            用户自己定义的StringBuffer对象
	 */
	protected void setParameter(Map<String, Object> HashMap2, String key, String sql, StringBuffer sbf) {
		if (HashMap2.containsKey(key.trim()) && !CommonFun.isNe(HashMap2.get(key))) {
			sbf.append(" " + sql);
		}
	}

	protected Map<String, Object> setSingleParameter(String key, Object value) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put(key, value);
		return map;
	}

	private void printSql(String psql, List<?> valuelist) {
		if ("true".equalsIgnoreCase(GlobalVar.getSystemProperty("dao.printsql"))) {
			log.info("SQL: " + psql);
			log.info("VAL: " + valuelist);
		}
	}

	private void printSqlCost(long t1, String pstmtsql, List<?> values) {
		long cost = System.currentTimeMillis() - t1;
		if (SQLCOST || cost > MAXCOSTPRINT) {
			log.info("SQLCOST: " + cost + " ms");
			log.info("SQLCOSTSQL: " + pstmtsql);
			log.info("SQLCOSTVAL: " + values);
/*			log.debug("SQLCOST: " + cost + " ms");
			log.debug("SQLCOSTSQL: " + pstmtsql);
			log.debug("SQLCOSTVAL: " + values);*/
		}
	}

	public Result getMetaData(String table) throws Exception {
		ResultSet rs = null;
		try {
			Result result = new Result();
			Record record = null;
			String sql = "select top 1 * from " + table;

			Statement state = conn.createStatement();
			rs = state.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				record = new Record();
				record.put("name", rsmd.getColumnName(i));
				record.put("type_name", rsmd.getColumnTypeName(i));
				record.put("type", rsmd.getColumnType(i));
				record.put("size", rsmd.getColumnDisplaySize(i));
				record.put("precision", rsmd.getPrecision(i));
				record.put("nullable", 0 == rsmd.isNullable(i));
				record.put("label", rsmd.getColumnLabel(i));
				result.addRecord(record);
			}
			return result;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {

				}
			}
		}
	}
	public Result getMetaData(String sql, Map<String, Object> parameters) throws Exception {
		return getMetaData(sql, parameters, null);
	}
	public Result getMetaData(String sql, Map<String, Object> parameters, String countSql) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long count = 0;
		try {
			long t1 = System.currentTimeMillis();
			Object[] csql;
			if(!CommonFun.isNe(countSql)){
				csql = parseSQL(countSql, parameters);
			}else{
				csql = parseCountSQL(sql, parameters);
			}
			String countsql = (String) csql[0];
			List<?> cvalues = (List<?>) csql[1];
			printSql(countsql, cvalues);
			pstmt = conn.prepareStatement(countsql);
			for (int i = 0; i < cvalues.size(); i++) {
				if (cvalues.get(i) == null || cvalues.get(i) == "") {
					pstmt.setNull(i + 1, Types.VARCHAR);
				} else if (cvalues.get(i).getClass().equals(Date.class)) {
					pstmt.setObject(i + 1, new java.sql.Timestamp(((Date) cvalues.get(i)).getTime()));
				} else if (cvalues.get(i).getClass().equals(String.class)) {
					if (cvalues.get(i).toString().length() >= 4000) {
						String s = (String) cvalues.get(i);
						StringReader c = new StringReader(s);
						pstmt.setCharacterStream(i + 1, c, s.length());
					} else {
						pstmt.setString(i + 1, (String) cvalues.get(i));
					}
				} else {
					pstmt.setObject(i + 1, cvalues.get(i));
				}
			}
			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getLong(1);
			}
			rs.close();
			pstmt.close();

			printSqlCost(t1, countsql, cvalues);
			Object[] psql = parseSQL(sql, parameters);
			String pstmtsql = (String) psql[0];
			List<?> values = (List<?>) psql[1];
			printSql(pstmtsql, values);
			pstmt = conn.prepareStatement(pstmtsql);
			for (int i = 0; i < values.size(); i++) {
				if (values.get(i) == null || values.get(i) == "") {
					pstmt.setNull(i + 1, Types.VARCHAR);
				} else if (values.get(i).getClass().equals(Date.class)) {
					pstmt.setObject(i + 1, new java.sql.Timestamp(((Date) values.get(i)).getTime()));
				} else if (values.get(i).getClass().equals(String.class)) {
					if (values.get(i).toString().length() >= 4000) {
						String s = (String) values.get(i);
						StringReader c = new StringReader(s);
						pstmt.setCharacterStream(i + 1, c, s.length());
					} else {
						pstmt.setString(i + 1, (String) values.get(i));
					}
				} else {
					pstmt.setObject(i + 1, values.get(i));
				}
			}
			rs = pstmt.executeQuery();

			Result result = new Result();
			Record record = null;
			ResultSetMetaData metaData = rs.getMetaData();
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				if (Dictionary.getField(metaData.getColumnName(i)) != null) {
					result.addRecord(Dictionary.getField(metaData.getColumnName(i)));
				} else {
					record = new Record();
					record.put("chnname", metaData.getColumnName(i));
					record.put("fdname", metaData.getColumnName(i));
					result.addRecord(record);
				}
			}
			result.setCount(count);
			return result;
		} catch (Exception ex) {
			throw ex;
		} finally {
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
		}
	}

	public Account user() {
		Session session = Session.getSession();
		if (session == null){
			return null;
		}
		Account user = (Account) session.get("userinfo");
		return user;
	}

	public Date getDate() throws Exception {
		String sql = "select sysdate d from dual";
		if ("MySQL".equals(conn.getDbType())) {
			sql = "select now() d from dual";
		} else if ("MSSQL".equals(conn.getDbType())) {
			sql = "select getDate() d ";
		}
		Record r = executeQuerySingleRecord(sql, null);
		return r.getDate("d");
	}
}
