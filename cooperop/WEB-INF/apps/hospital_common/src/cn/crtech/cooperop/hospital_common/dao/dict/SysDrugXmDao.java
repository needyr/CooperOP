package cn.crtech.cooperop.hospital_common.dao.dict;

import java.util.Date;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class SysDrugXmDao extends BaseDao{
	/**药品属性*/
	private static final String TABLENAME = "dict_sys_drug_XM";
	/**项目值*/
	private static final String DICT_SYS_DRUG_XM_VALUE = "DICT_SYS_DRUG_XM_VALUE";
	
	//初始化数据
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLENAME + "(nolock) ");
		sql.append(" where 1=1 ");
		if(!CommonFun.isNe(params.get("filter"))) {
			params.put("filter", "%" +params.get("filter")+ "%");
			sql.append(" and XMMCH like :filter or pym like :filter ");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLENAME, params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("XMID", params.remove("xmid"));
		params.put("username", user().getName());
		params.put("lasttime", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SS"));
		return executeUpdate(TABLENAME, params, r);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(TABLENAME, params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLENAME);
		if(!CommonFun.isNe(params.get("isadd"))) {
			sql.append(" (nolock) where xmbh = :xmbh");
		}else {
			sql.append(" (nolock) where  XMID = :xmid ");
		}
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	
	/**********************************以下为药品属性值的维护**************************************/
	
	/**
	 * @param params
	 * @return
	 * @throws Exception
	 * @function
	 * @author yankangkang 2019年2月19日 下午3:20:53
	 */
	public Result queryXmValue(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + DICT_SYS_DRUG_XM_VALUE + "(nolock) ");
		sql.append(" where 1=1 ");
		if(!CommonFun.isNe(params.get("filter"))) {
			params.put("filter", "%" +params.get("filter")+ "%");
			sql.append(" and value like :filter");
		}
		sql.append(" and XMID = :XMID");
		return executeQueryLimit(sql.toString(), params);
	}
	
	
	public int xmValueInsert(Map<String, Object> params) throws Exception {
		params.remove("oldvalue");
		return executeInsert(DICT_SYS_DRUG_XM_VALUE, params);
	}
	
	public int xmValueUpdate(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("xmid", params.remove("xmid"));
		if(!CommonFun.isNe(params.get("isupd"))) {
			params.remove("isupd");
			r.put("value", params.remove("oldvalue"));
		}
		
		return executeUpdate(DICT_SYS_DRUG_XM_VALUE, params, r);
	}
	
	public int xmValueDelete(Map<String, Object> params) throws Exception {
		return executeDelete(DICT_SYS_DRUG_XM_VALUE, params);
	}
	
	/**
	 * @param params isadd、isupd(新增、修改方法标识)
	 * @return
	 * @throws Exception
	 * @function 查询一条数据，新增修改验重
	 * @author yankangkang 2019年2月25日 下午2:11:39
	 */
	public Record xmValueGet(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + DICT_SYS_DRUG_XM_VALUE);
		if(!CommonFun.isNe(params.get("isadd"))) {
			params.remove("isadd");
			sql.append(" (nolock) where  XMID = :xmid ");
			sql.append(" and  VALUE = :value ");
		}else if(!CommonFun.isNe(params.get("isupd"))) {
			if(params.get("oldvalue").equals(params.get("value"))) {
				return null;	
			}else {
				sql.append(" (nolock) where  XMID = :xmid ");
				sql.append(" and  VALUE = :value ");
			}
		}else {
			sql.append(" (nolock) where  XMID = :xmid ");
			sql.append(" and  VALUE = :value ");
		}
		return executeQuerySingleRecord(sql.toString(), params);
	}
}
