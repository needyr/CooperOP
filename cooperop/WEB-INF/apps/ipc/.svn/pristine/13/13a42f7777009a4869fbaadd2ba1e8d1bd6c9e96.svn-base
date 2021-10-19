package cn.crtech.cooperop.ipc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.util.CRPasswordEncrypt;

public class PrecheckDao extends BaseDao {
	public int updatePassword(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		params.put("newpwd", CRPasswordEncrypt.Encryptstring(params.get("newpwd").toString()));
		params.put("oldpwd", CRPasswordEncrypt.Encryptstring(params.get("oldpwd").toString()));
		sql.append("update supper_user set password = :newpwd where no =:no  and password = :oldpwd");
		return executeUpdate(sql.toString(), params);
	}
}
