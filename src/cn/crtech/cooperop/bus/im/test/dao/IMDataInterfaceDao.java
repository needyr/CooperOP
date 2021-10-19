package cn.crtech.cooperop.bus.im.test.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;

public class IMDataInterfaceDao extends BaseDao {

	public final static String VIEW_NAME_ORG = "v_im_orgnization";

	public Result listOrgnization(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  * FROM " + VIEW_NAME_ORG + "(nolock) WHERE   1 = 1 ");
		return executeQuery(sql.toString(), params);
	}

	public final static String VIEW_NAME_DEP = "v_im_department";

	public Result listDepartment(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  * FROM " + VIEW_NAME_DEP + "(nolock) WHERE   1 = 1 ");
		return executeQuery(sql.toString(), params);
	}

	public final static String VIEW_NAME_USER = "v_im_users";

	public Result listUser(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  * FROM " + VIEW_NAME_USER + "(nolock) WHERE   1 = 1 ");
		return executeQuery(sql.toString(), params);
	}

}
