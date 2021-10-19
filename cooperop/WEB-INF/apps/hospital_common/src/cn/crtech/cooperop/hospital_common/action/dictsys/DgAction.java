package cn.crtech.cooperop.hospital_common.action.dictsys;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.dictsys.DgService;

@DisLoggedIn
public class DgAction extends BaseAction {
	// 查询
	public Result query(Map<String, Object> params) throws Exception {
		return new DgService().query(params);
	}
	
	//根据dg_icd和dg_remark查询
	public Result queryByIcdAndRemark(Map<String, Object> params) throws Exception {
		return new DgService().queryByIcdAndRemark(params);
	}
	
	public Result queryDictHisDiagnosis(Map<String, Object> params) throws Exception {
		return new DgService().queryDictHisDiagnosis(params);
	}
	// 新增
	public int insert(Map<String, Object> params) throws Exception {
		return new DgService().insert(params);
	}

	// 修改
	public int update(Map<String, Object> params) throws Exception {
		return new DgService().update(params);
	}

	// 删除
	public int delete(Map<String, Object> params) throws Exception {
		return new DgService().delete(params);
	}

	// 进入edit时，预加载
	public Record edit(Map<String, Object> params) throws Exception {
		if (CommonFun.isNe(params.get("id"))) {
			return null;
		}
		return new DgService().get(params);
	}
}
