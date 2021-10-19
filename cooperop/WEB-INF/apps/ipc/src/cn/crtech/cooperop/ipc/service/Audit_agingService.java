package cn.crtech.cooperop.ipc.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.ipc.dao.Audit_agingDao;

public class Audit_agingService extends BaseService {
	public Result list(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			if(!CommonFun.isNe(params.get("deptfifter"))) {
				String[] dStrings = ((String)params.get("deptfifter")).split(",");
				params.put("deptfifter", dStrings);
			}
			return new Audit_agingDao().list(params);
		}finally {
			disconnect();
		}
	}
}
