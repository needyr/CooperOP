package cn.crtech.cooperop.hospital_common.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class UseReasonDao extends BaseDao{
	private final static String FIRST_TABLE_NAME="dict_sys_usereason_product";
	private final static String SECOND_TABLE_NAME="dict_sys_usereason_type";
	private final static String THIRD_TABLE_NAME="dict_sys_usereason_detail";
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select a.*,b.name from "+FIRST_TABLE_NAME+" (nolock) a inner join system_product (nolock) b on a.sys_product_code = b.code where 1 = 1 ");
		if(!CommonFun.isNe(params.get("sys_product_code"))){
			sql.append(" and sys_product_code=:sys_product_code ");
		}
		Result result=executeQueryLimit(sql.toString(),params);
		return result;
	}
	
	public Result queryChild(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select a.* from "+SECOND_TABLE_NAME+" (nolock) a inner join "+FIRST_TABLE_NAME+" (nolock) b on a.usereason_product_id = b.id where 1=1 ");
		if(!CommonFun.isNe(params.get("sys_product_code"))){
			sql.append(" and b.sys_product_code=:sys_product_code ");
		}
		Result result=executeQueryLimit(sql.toString(),params);
		return result;
	}

	public Result queryCheck(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT * FROM sys_common_regulation (nolock) WHERE 1 = 1 ");
		if(!CommonFun.isNe(params.get("product_code"))){
			sql.append(" and product_code=:product_code ");
		}
		return executeQueryLimit(sql.toString(),params);
	}
	
	/**
	 * @author wangsen
	 * @date 2019年1月3日
	 * @param 
	 * @return 
	 * @function 查询同一理由下未选择的审查分类
	 */
	public Result queryNOSelectCheck(Map<String, Object> params) throws Exception {
		StringBuffer sql1=new StringBuffer();
		sql1.append("SELECT sort_id ,sort_name FROM sys_common_regulation (nolock) WHERE 1 = 1 ");
		if(!CommonFun.isNe(params.get("product_code"))){
			sql1.append(" and product_code=:product_code ");
		}
		StringBuffer sql2=new StringBuffer();
		sql2.append("SELECT apa_check_sorts_id AS sort_id,apa_check_sorts_name AS sort_name FROM dict_sys_usereason_type (nolock) WHERE 1 = 1 ");
		if(!CommonFun.isNe(params.get("usereason_product_id"))){
			sql2.append(" and usereason_product_id=:usereason_product_id ");
		}
		StringBuffer sql3=new StringBuffer();
		sql3.append(sql1.toString()+" EXCEPT "+sql2.toString());
		return executeQueryLimit(sql3.toString(),params);
	}
	
	

	public int insertPro(Map<String, Object> params) throws Exception {
		params.remove("id");
		if (params.get("apa_check_sorts_name")!=null) {
			params.remove("apa_check_sorts_name");
		}
		return executeInsert(FIRST_TABLE_NAME, params);
	}
	
	public int insertType(Map<String, Object> params) throws Exception {
		params.remove("id");
		params.remove("sys_product_code");
		return executeInsert(SECOND_TABLE_NAME, params);
	}
	
	public int updatePro(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("id", params.remove("id"));
		if (params.get("apa_check_sorts_name")!=null) {
			params.remove("apa_check_sorts_name");
		}
		return executeUpdate(FIRST_TABLE_NAME, params,r);
	}
	
	public int updateType(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("id", params.remove("id"));
		params.remove("sys_product_code");
		return executeUpdate(SECOND_TABLE_NAME, params,r);
	}

	public Record getPro(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select a.*,b.name as pro_name from "+FIRST_TABLE_NAME+" (nolock) a ");
		sql.append(" left join system_product (nolock) b on b.code=a.sys_product_code where 1=1 ");
		setParameter(params, "id", " and a.id =:id ", sql);
		Record record = executeQuerySingleRecord(sql.toString(), params);
		return record;
	}
	
	public Record getType(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select a.*,b.usereasonproduct_name,c.sort_name as s_name from "+SECOND_TABLE_NAME);
		sql.append(" (nolock) a INNER JOIN dict_sys_usereason_product (nolock) b ON a.usereason_product_id=b.id ");
		sql.append(" left join sys_common_regulation (nolock) c on a.apa_check_sorts_id=c.sort_id and c.product_code=b.sys_product_code where 1=1 ");
		setParameter(params, "id", " and a.id =:id ", sql);
		Record record = executeQuerySingleRecord(sql.toString(), params);
		return record;
	}

	public int deletePro(Map<String, Object> params) throws Exception {
		params.remove("usereason_product_id");
		return executeDelete(FIRST_TABLE_NAME, params);
	}
	
	public Result queryDetail(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select a.*,b.usereasontype_name from "+THIRD_TABLE_NAME+" (nolock) a ");
		sql.append("INNER JOIN dict_sys_usereason_type (nolock) b ON a.usereason_type_id=b.id where 1=1 ");
		setParameter(params, "parent_id", "and a.usereason_type_id = :parent_id", sql);
		Result result = executeQueryLimit(sql.toString(), params);
		return result;
	}

	public Record getDetail(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select * from "+THIRD_TABLE_NAME+" (nolock) where 1=1");
		setParameter(params, "id", " and id =:id ", sql);
		Record record = executeQuerySingleRecord(sql.toString(), params);
		return record;
	}

	public int insertDetail(Map<String, Object> params) throws Exception {
		params.remove("id");
		return executeInsert(THIRD_TABLE_NAME, params);
	}

	public int updateDetail(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(THIRD_TABLE_NAME, params,r);
	}

	public int deleteDetail(Map<String, Object> params) throws Exception {
		int i = executeDelete(THIRD_TABLE_NAME, params);
		return i;
	}
	
	public List<Record> queryTypeAndDetail(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT b.usereason_type_id FROM dict_sys_usereason_detail (nolock) b INNER JOIN ");
		sql.append("dict_sys_usereason_type (nolock) a ON a.id=b.usereason_type_id WHERE 1=1");
		setParameter(params, "usereason_product_id", " and usereason_product_id = :usereason_product_id GROUP BY b.usereason_type_id", sql);
		Result result = executeQuery(sql.toString(),params);
		List<Record> list = result.getResultset();
		return list;
	}

	public int deleteType(Map<String, Object> params) throws Exception {
		int i = executeDelete(SECOND_TABLE_NAME, params);
		return i;
	}
	
	private static final String PRO_TABLE_NAME = "dict_sys_usereason_product";
	private static final String TYPE_TABLE_NAME = "dict_sys_usereason_type";
	private static final String DETAIL_TABLE_NAME = "dict_sys_usereason_detail";
	private static final String PRODUCT_NAME="审查不合理医嘱";

	public Result queryTypeReason(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	c.id as detail_id, ");
		sql.append("	c.usereasondetail_jiancheng, ");
		sql.append("	c.usereasondetail_name,b.apa_check_sorts_name,b.is_must,d.name,a.sys_product_code ");
		sql.append("FROM ");
		sql.append("	"+PRO_TABLE_NAME+" (nolock) a ");
		sql.append("LEFT JOIN "+TYPE_TABLE_NAME+" (nolock) b ON a.id = b.usereason_product_id ");
		sql.append("LEFT JOIN "+DETAIL_TABLE_NAME+" (nolock) c ON b.id = c.usereason_type_id ");
		sql.append("LEFT JOIN system_product (nolock) d ON d.code = a.sys_product_code ");
		sql.append("WHERE ");
		sql.append(" a.system_product_code='usedrug' ");
		sql.append("AND a.beactive = 1 ");
		sql.append("AND b.beactive = 1 ");
		sql.append("AND (c.beactive = 1 or c.beactive is null) ");
		//setParameter(params, "sort_id", "AND b.apa_check_sorts_id = :sort_id ", sql);
		Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
		String string = "";
		int i = 0;
		while(iterator.hasNext()) {
			String key = iterator.next().getKey();
			String value = (String) params.get(key);
			if(i == 0) {
				i++;
				if(CommonFun.isNe(value)) {
					value = "''" ;
				}
				string = " (b.apa_check_sorts_id in ("+value+") and a.sys_product_code = '"+key+"' ) ";
			}else {
				if(CommonFun.isNe(value)) {
					value = "''" ;
				}
				string += " or (b.apa_check_sorts_id in ("+value+") and a.sys_product_code = '"+key+"' ) ";
			}
		}
		if(i != 0) {
			sql.append(" and (" + string + ")");
		}
		Result result = executeQuery(sql.toString(), params);
		return result;
	}
}
