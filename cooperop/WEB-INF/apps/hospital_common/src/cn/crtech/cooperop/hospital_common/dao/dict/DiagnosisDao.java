package cn.crtech.cooperop.hospital_common.dao.dict;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DiagnosisDao extends BaseDao{

	private final static String TABLE_NAME ="dict_his_diagnosis";
	
	public Result queryin(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,b.diagnosis_name as sys_p_key_name from "+TABLE_NAME);
		sql.append(" (nolock) a left join dict_sys_diagnosis b on a.sys_p_key=b.p_key");
		sql.append(" where 1=1 ");
		if (!CommonFun.isNe(params.get("sxtj"))) {
			if ("not".equals(params.get("sxtj"))) {
				sql.append("and a.sys_p_key is null or a.sys_p_key=''");
			}else if ("has".equals(params.get("sxtj"))) {
				sql.append("and a.sys_p_key is not null and a.sys_p_key!=''");
			}
		}
		if (!CommonFun.isNe(params.get("filter"))) {
			params.put("filter", "%"+params.get("filter")+"%");
			sql.append(" and a.diagnosis_name like :filter ");
		}
		return executeQueryLimit(sql.toString(), params);
	}

	//查询匹配度每个药品匹配度  top100
	public void calltop100(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("execute similar_diagnisis_tmp ");
		executeCallQuery(sql.toString(), params);
	}

	public Result querysys(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		if (!CommonFun.isNe(params.get("p_key"))) {
			sql.append("select top 100 percent * from dict_his_diagnosis_tmp (nolock) where 1=1 ");
			setParameter(params, "p_key", "and his_p_key= :p_key ", sql);
			params.put("sort"," cast(diagnosis_NAME_ppd as decimal(5,2)) desc ");
		}else{
			if (!CommonFun.isNe(params.get("jb_name_c"))) {
				sql.append("select top 100 percent * from dict_his_diagnosis_tmp (nolock) where 1=1 ");
				if (!CommonFun.isNe(params.get("jb_name_c"))) {
					params.put("jb_name_c", "%"+params.get("jb_name_c")+"%");
					sql.append("and diagnosis_name like :jb_name_c ");
				}
				params.put("sort"," cast(diagnosis_NAME_ppd as decimal(5,2)) desc ");
			}else{
				sql.append("select * from dict_sys_diagnosis where 1=1 ");
				if (!CommonFun.isNe(params.get("jb_name"))) {
					params.put("jb_name", "%"+params.get("jb_name")+"%");
					sql.append("and diagnosis_name like :jb_name ");
				}
			}
		}
		return executeQueryLimit(sql.toString(), params);
	}

	public void updateMapping(Map<String, Object> params) throws Exception {
		Map<String, Object> r = new HashMap<String, Object>();
		r.put("p_key", params.remove("his_p_key"));
		executeUpdate(TABLE_NAME, params, r);
	}

	public void insertSys(Map<String, Object> params) throws Exception {
		executeInsert("dict_sys_diagnosis", params);
	}

	public void updateSureMapping(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update a set a.sys_p_key=b.p_key from dict_his_diagnosis a             ");
		sql.append("left join dict_sys_diagnosis b on a.diagnosis_name = b.diagnosis_name  ");
		execute(sql.toString(),null);
	}

}
