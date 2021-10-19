package cn.crtech.cooperop.hospital_common.dao.dict;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class SysOrdersTagDao extends BaseDao{
	
	/**医嘱标签字典*/
	private static final String TABLENAME = "DICT_SYS_ORDERS_TAG";
	/**医嘱标签值*/
	private static final String DICT_SYS_ORDERS_TAG_MX = "DICT_SYS_ORDERS_TAG_MX";
	
	
	//初始化数据
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLENAME + "(nolock) ");
		sql.append(" where 1=1 ");
		if(!CommonFun.isNe(params.get("filter"))) {
			params.put("filter", "%" +params.get("filter")+ "%");
			sql.append(" and ORDERTAGNAME like :filter");
		}
		if(!CommonFun.isNe(params.get("ordertagid"))) {
			sql.append(" and  ordertagid = :ordertagid ");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	
	public int insert(Map<String, Object> params) throws Exception {
		params.remove("ordertagid");
		return executeInsert(TABLENAME, params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("ordertagid", params.remove("ordertagid"));
		return executeUpdate(TABLENAME, params, r);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		
		return executeDelete(TABLENAME, params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLENAME);
		if(!CommonFun.isNe(params.get("ordertagid"))) {
			sql.append(" (nolock) where  ordertagid = :ordertagid ");
		}else {
			sql.append(" (nolock) where  ordertagbh = :ordertagbh ");
		}
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	/**********************************以下为医嘱属性值的维护**************************************/
	
	public Result queryOrdersValue(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * ,");
		sql.append("case when tiaojian='>' then '大于'              ");
		sql.append("		 when tiaojian='<' then '小于'          ");
		sql.append("		  when tiaojian='<>' then '不等于'      ");
		sql.append("			 when tiaojian='like' then '类似'   ");
		sql.append("		when tiaojian='not like' then '不类似'  ");
		sql.append("		 when tiaojian='=' then '等于'          ");
		sql.append("		end as tiaojian_name                    ");
		sql.append("	 from "+DICT_SYS_ORDERS_TAG_MX + " (nolock) ");
		sql.append(" where 1 = 1");
		if(!CommonFun.isNe(params.get("filter"))) {
			params.put("filter", "%" +params.get("filter")+ "%");
			sql.append(" and value like :filter or xmmch like :filter");
		}
		sql.append(" and ordertagid = :ordertagid");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public int ordersValueInsert(Map<String, Object> params) throws Exception {
		params.remove("id");
		return executeInsert(DICT_SYS_ORDERS_TAG_MX, params);
	}
	
	public int ordersValueUpdate(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("ID", params.remove("id"));
		return executeUpdate(DICT_SYS_ORDERS_TAG_MX, params, r);
	}
	
	public int ordersValueDelete(Map<String, Object> params) throws Exception {
		return executeDelete(DICT_SYS_ORDERS_TAG_MX, params);
	}
	
	public Record ordersValueGet(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * ,");
		sql.append("case when tiaojian='>' then '大于'              ");
		sql.append("		 when tiaojian='<' then '小于'          ");
		sql.append("		  when tiaojian='<>' then '不等于'      ");
		sql.append("			 when tiaojian='like' then '类似'   ");
		sql.append("		when tiaojian='not like' then '不类似'  ");
		sql.append("		 when tiaojian='=' then '等于'          ");
		sql.append("		end as tiaojian_name                    ");
		sql.append("	 from "+DICT_SYS_ORDERS_TAG_MX + " (nolock) ");
		sql.append(" where 1 = 1");
		sql.append(" and ID = :id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
}
