package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DictHisDiagnosisDao extends BaseDao {
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select a.* from dict_his_diagnosis (nolock) a where 1=1");
		setParameter(params, "filter", " and (diagnosis_code like '%'+'"+params.get("filter")+"'+'%' or diagnosis_name like '%'+'"+params.get("filter")+"'+'%' or input_code like '%'+'"+params.get("filter")+"'+'%')", sql);
		if("0".equals(params.get("is_wh_filter"))) {
			sql.append(" and (is_wh = 0 or isnull(is_wh,'')='' )");
		}else if("1".equals(params.get("is_wh_filter"))) {
			sql.append(" and is_wh = 1");
		}
		return executeQueryLimit(sql.toString(), params);
	}

	public void updateByCode(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("diagnosis_code", params.remove("diagnosis_code"));
		executeUpdate("dict_his_diagnosis", params, r);
	}
	
	public void pipeiGRLX(Map<String, Object> params) throws Exception {
		execute("execute syc_diagnosis_grlx_pd ", params);
	}
	

	public Result search(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select diagnosis_CODE,diagnosis_NAME,INPUT_CODE        ");
		sql.append(" from dict_his_diagnosis (nolock)                                                 ");
		sql.append(" where 1=1 ");
		if(!CommonFun.isNe(params.get("data"))){
			params.put("key", "%"+params.get("data")+"%");
			sql.append(" and (diagnosis_name like :key or diagnosis_code like :key or INPUT_CODE like :key  ) ");
		}
		sql.append(" order by diagnosis_CODE                       ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result searchCheck(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select diagnosis_CODE,diagnosis_NAME,INPUT_CODE           ");
		sql.append(" from dict_his_diagnosis  (nolock)                                                 ");
		sql.append(" where a.diagnosis_code in (:code)         ");
		sql.append(" order by diagnosis_CODE                       ");
		return executeQuery(sql.toString(), params);
	}
	

	
	public Result queryDiagnosis(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select diagnosis_CODE,diagnosis_NAME,INPUT_CODE        ");
		sql.append(" from dict_his_diagnosis (nolock)                                                 ");
		sql.append(" where 1=1 ");
		if(!CommonFun.isNe(params.get("data"))){
			params.put("key", "%"+params.get("data")+"%");
			sql.append(" and (diagnosis_name like :key or diagnosis_code like :key   or INPUT_CODE like :key ) ");
		}
		sql.append(" order by diagnosis_CODE                       ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryDiagnosisCheck(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select diagnosis_CODE,diagnosis_NAME,INPUT_CODE              ");
		sql.append(" from dict_his_diagnosis  (nolock)                            ");               
		sql.append(" where diagnosis_code in (:code)                              ");
		sql.append(" order by diagnosis_CODE                                      ");
		return executeQuery(sql.toString(), params);
	}
	
	
	
}
