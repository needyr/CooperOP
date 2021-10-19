package cn.crtech.cooperop.hospital_common.action.dictsys;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.dictsys.DgpgjeService;

public class DgpgjeAction extends BaseAction {
	// 查询
	public Result query(Map<String, Object> params) throws Exception {
		return new DgpgjeService().query(params);
	}

	
	public Result queryInterfaceType(Map<String, Object> params) throws Exception {
		return new DgpgjeService().queryInterfaceType(params);
	}

	// 新增
	public int insert(Map<String, Object> params) throws Exception {
		return new DgpgjeService().insert(params);
	}

	// 修改
	public int update(Map<String, Object> params) throws Exception {
		return new DgpgjeService().update(params);
	}

	// 删除
	public int delete(Map<String, Object> params) throws Exception {
		return new DgpgjeService().delete(params);
	}

	// 进入edit时，预加载
	public Record edit(Map<String, Object> params) throws Exception {
		if (CommonFun.isNe(params.get("id"))) {
			return null;
		}
		return new DgpgjeService().get(params);
	}
}
