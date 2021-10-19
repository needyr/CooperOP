package cn.crtech.cooperop.ipc.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.ipc.dao.SubmitFailDao;

public class SubmitFailService extends BaseService {

	public Result failList(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			String[] dStrings = ((String)params.get("deptfifter")).split(",");
			params.put("deptfifter", dStrings);
			return new SubmitFailDao().failList(params);
		}finally {
			disconnect();
		}
	}

}
