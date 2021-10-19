package cn.crtech.cooperop.bus.im.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class ContactorDao extends BaseDao {
	
	public final static String VIEW_NAME = "v_user_contacter";
	public final static String VIEW_NAME_USER = "v_user_contacter_users";

	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		/*
		 * if(CommonFun.isNe(params.get("company_id"))){ params.put("company_id",
		 * user().getCompanyid()); }
		 */
		sql.append(" select * from " + VIEW_NAME + "(nolock) where 1 = 1 ");
		setParameter(params, "system_user_id", " and (isnull(system_user_id, '') = '' or system_user_id = :system_user_id) ", sql);
		setParameter(params, "type", " and type in (:type) ", sql);
		setParameter(params, "company_id", " and company_id = :company_id ", sql);
		setParameter(params, "filter", " and input_code like upper('%' + :filter + '%') ", sql);
		return executeQuery(sql.toString(), params);
	}

	public Result queryUser(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		if(CommonFun.isNe(params.get("company_id"))){
			params.put("company_id", user().getCompanyid());
		}
		sql.append(" select * from " + VIEW_NAME_USER + "(nolock) where 1 = 1 ");
		setParameter(params, "contacter_type", " and contacter_type = :contacter_type ", sql);
		setParameter(params, "company_id", " and company_id = :company_id ", sql);
		setParameter(params, "contacter_id", " and contacter_id = :contacter_id ", sql);
		return executeQuery(sql.toString(), params);
	}
}
