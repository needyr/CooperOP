package cn.crtech.cooperop.application.action;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.service.TaskService;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.workflow.workflow;

@DisValidPermission
public class TaskAction extends BaseAction {
	
	public Result queryMine(Map<String, Object> req) throws Exception {
		return new TaskService().queryMine(req);
	}

	public Result listProcess(Map<String, Object> req) throws Exception {
		return new TaskService().listProcess(req);
	}

	public Result queryTasking(Map<String, Object> req) throws Exception {
		return new TaskService().queryTasking(req);
	}

	public Result queryHistory(Map<String, Object> req) throws Exception {
		return new TaskService().queryHistory(req);
	}

	public String tasknum(Map<String, Object> req) throws Exception {
		return new TaskService().queryTaskCount(req).getString("task_nums");
	}
	
	public Result queryFreeTask(Map<String, Object> req) throws Exception {
		return new TaskService().queryFreeTask(req);
	}

	public void saveFreeTask(Map<String, Object> req) throws Exception {
		new TaskService().saveFreeTask(req);
	}

	public void startFreeTask(Map<String, Object> req) throws Exception {
		new TaskService().startFreeTask(req);
	}

	public void deleteFreeTask(Map<String, Object> req) throws Exception {
		int id = Integer.parseInt((String)req.remove("id"));
		new TaskService().deleteFreeTask(id);
	}

	public void cancelFreeTask(Map<String, Object> req) throws Exception {
		int id = Integer.parseInt((String)req.remove("id"));
		new TaskService().cancelFreeTask(id);
	}

	public Map<String, Object> freetask(Map<String, Object> req) throws Exception {
		if (!CommonFun.isNe(req.get("id"))) {
			int id = Integer.parseInt((String)req.remove("id"));
			Record rtn = new TaskService().getFreeTask(id);
			return rtn;
		} else {
			return new Record();
		}
	}

	public Map<String, Object> freetaskdetail(Map<String, Object> req) throws Exception {
		Record rtn = new TaskService().getFreeTask(req);
		rtn.put("task_id", req.get("task_id"));
		return rtn;
	}

	public Map<String, Object> freetaskaudit(Map<String, Object> req) throws Exception {
		Record rtn = new TaskService().getFreeTask(req);
		rtn.put("task_id", req.get("task_id"));
		return rtn;
	}

	public void freenext(Map<String, Object> req) throws Exception {
		new TaskService().nextFreeTask(req);
	}

	public void freefinish(Map<String, Object> req) throws Exception {
		new TaskService().finishFreeTask(req);
	}
	
	public Result orderhistory(Map<String, Object> req) throws Exception {
		String djbh = (String)req.remove("djbh");
		return new TaskService().listOrderTaskHistory(djbh);
	}
	
	public static List<Record> listTaskRoutes(Map<String, Object> req) throws Exception {
		String task_id = (String)req.get("task_id");
		return workflow.listTaskRoutes(task_id, "Y", "", true);
	}
	
	public void skimsave(Map<String, Object> req) throws Exception {
		new TaskService().skimsave(req);
	}
}
