package cn.crtech.cooperop.hospital_common.dao.flow;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DicthisdeptmentDao extends BaseDao{
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT                                   ");
		sql.append("PKEY_ID,                                 ");
		sql.append("P_KEY,                                   ");
		sql.append("dept_CODE,                               ");
		sql.append("dept_NAME,                               ");
		sql.append("dept_ATTR,                               ");
		sql.append("OUTP_OR_INP,                             ");
		sql.append("Jianchen,                                ");
		sql.append("INPUT_CODE,                              ");
		sql.append("IS_Operation,                            ");
		sql.append("bed_count,                               ");
		sql.append("is_wh,                                   ");
		sql.append("is_active,                               ");
		sql.append("area_code,                               ");
		sql.append("area_name                                ");
		sql.append("FROM dict_his_deptment(nolock)           ");
		sql.append("WHERE 1=1                                ");
		if (!CommonFun.isNe(params.get("sousuo"))) {
			params.put("sousuo", "%"+params.get("sousuo")+"%");
			sql.append(" and dept_NAME like :sousuo ");
		}
		return executeQueryLimit(sql.toString(), params);
	}
}
