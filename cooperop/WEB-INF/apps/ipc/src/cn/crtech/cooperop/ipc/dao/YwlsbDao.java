package cn.crtech.cooperop.ipc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;

public class YwlsbDao extends BaseDao{

	public Result queryOrders(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct *,                                                                             ");
		sql.append(" dbo.get_inorders_GROUPTAG_YWLSB(yas.patient_id,yas.visit_id,yas.group_id,yas.order_no,yas.order_sub_no) zu ");
		sql.append(" from YWLSB_AUDIT_ORDERS yas  (nolock)                                                                  ");
		sql.append(" where yas.doctor_no  = '2195'                                                                  ");
		sql.append(" order by yas.order_no                                                                          ");
		return executeQueryLimit(sql.toString(), params);
	}
}
