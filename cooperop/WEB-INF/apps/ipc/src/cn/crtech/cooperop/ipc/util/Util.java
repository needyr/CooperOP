package cn.crtech.cooperop.ipc.util;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.crtech.cooperop.bus.rdms.Connection;
import cn.crtech.cooperop.bus.rdms.Record;

public class Util {
	/**
	 * 批处理插入
	 * @param table
	 * @param params
	 * @param conn
	 * @throws Exception
	 */
	public static void executeBatchInsert(String table, List<Record> params, Connection conn) throws Exception {
		//log.debug("-------------------------批处理插入开始--------------------------");
		PreparedStatement pstmt = null;
		try {
			StringBuffer cache_sql_key = new StringBuffer();
			StringBuffer cache_sql_value = new StringBuffer();
			Map<String, Object> map = new HashMap<String, Object>();
			int cache_value = 0;
			Record record = params.get(0);
			Iterator<Entry<String, Object>> iterator = record.entrySet().iterator();
			cache_sql_value.append(" VALUES(");
			cache_sql_key.append(" (");
			while(iterator.hasNext()){
				Entry<String, Object> entry = iterator.next();
				String key = entry.getKey();
				if(!"rowno".equals(key)) {
					cache_value++;
					if(cache_value==1) {
						cache_sql_value.append("?");
						cache_sql_key.append(key);
					}else {
						cache_sql_value.append(",?");
						cache_sql_key.append(","+key);
					}
					map.put(String.valueOf(cache_value), key);
				}
			}
			cache_sql_value.append(")");
			cache_sql_key.append(")");
			pstmt = conn.prepareStatement("insert into "+table+cache_sql_key.toString()+cache_sql_value.toString());
			//conn.setAutoCommit(false);//取消自动提交
			for (Record re : params) {
				for(int i=1;i<=cache_value;i++) {
					pstmt.setObject(i, "".equals(re.get(map.get(String.valueOf(i))))?null:re.get(map.get(String.valueOf(i))));
				}
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			//conn.commit();
		} catch (Exception ex) {
			try{  
		      //conn.rollback();  
		    }catch(Exception e1){  
		       
		    }
			throw ex;
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
					throw e;
				}
			}
			//log.debug("-------------------------批处理插入结束--------------------------");
		}
	}
}
