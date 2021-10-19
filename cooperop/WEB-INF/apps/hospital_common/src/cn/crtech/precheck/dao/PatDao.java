package cn.crtech.precheck.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.model.BaseDao;

public class PatDao extends BaseDao{

	public void hisInTmpProc(Map<String, Object> params) throws Exception {
		execute(" exec hospital_common..JCR_his_in_TMP '', '', :patient_id, :visit_id ", params);
		log.debug(" exec hospital_common..JCR_his_in_TMP '', '', :patient_id, :visit_id " + "/ params：" + params.toString());
	}
}
