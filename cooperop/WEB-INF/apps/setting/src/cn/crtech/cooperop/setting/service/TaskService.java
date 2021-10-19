package cn.crtech.cooperop.setting.service;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.application.dao.SystemMessageDao;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.setting.dao.TaskDao;


public class TaskService extends BaseService {
	public Result queryTasking(Map<String, Object> params) throws Exception {
		try {
			connect();
			TaskDao td = new TaskDao();
			return td.queryTasking(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public void urgep(Map<String, Object> params) throws Exception {
		try {
			connect();
			start();
			SystemMessageDao smd = new SystemMessageDao();
			if(!CommonFun.isNe(params.get("operator"))){
				String[] operators = params.get("operator").toString().split(",");
				for(String operator : operators){
					
					Map<String, Object> p = new HashMap<String, Object>();
					p.put("content", "<div style=\"font-family: 微软雅黑; font-size: 26px; color: rgb(0, 0, 0); "
							+"font-weight: normal; font-style: normal; text-decoration: none; line-height: 30px;\">"
							+"您有一条【"+params.get("process_name")+"】急需处理,业务号为：【"+params.get("djbh")+"】,当前处理环节【"+params.get("node_name")+"】</div>");
					p.put("type", "3");
					p.put("state", 0);
					p.put("send_from", "");
					p.put("system_product_code", "cooperop");
					p.put("send_to", operator);
					smd.insert(p);
				}
			}
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	
}
