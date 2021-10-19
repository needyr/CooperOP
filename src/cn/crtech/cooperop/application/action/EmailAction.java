package cn.crtech.cooperop.application.action;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.service.EmailService;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

@DisValidPermission
public class EmailAction extends BaseAction {
	
	public Record mine(Map<String, Object> req) throws Exception {
		Record rtn = new Record();
		rtn.put("folders", new EmailService().queryMineFolder(false).getResultset());
		return rtn;
	}

	public Result queryMineFolder(Map<String, Object> req) throws Exception {
		return new EmailService().queryMineFolder(true);
	}

	public Result queryMine(Map<String, Object> req) throws Exception {
		return new EmailService().queryMine(req);
	}

	public Record detail(Map<String, Object> req) throws Exception {
		return new EmailService().getMine(req);
	}

	public Record modify(Map<String, Object> req) throws Exception {
		return new EmailService().getMine(req);
	}

	public int emailnum(Map<String, Object> req) throws Exception {
		return new EmailService().queryEmailCount(req);
	}

	public void save(Map<String, Object> req) throws Exception {
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> send_to = CommonFun.json2Object((String)req.remove("send_to"), List.class);
		new EmailService().save(req, send_to);
	}

	public void send(Map<String, Object> req) throws Exception {
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> send_to = CommonFun.json2Object((String)req.remove("send_to"), List.class);
		new EmailService().send(req, send_to);
	}

	public void read(Map<String, Object> req) throws Exception {
		String[] ids = ((String)req.get("ids")).split(",");
		new EmailService().read(ids);
	}

	public void unread(Map<String, Object> req) throws Exception {
		String[] ids = ((String)req.get("ids")).split(",");
		new EmailService().unread(ids);
	}

	public void changeFolder(Map<String, Object> req) throws Exception {
		String[] ids = ((String)req.get("ids")).split(",");
		int new_folder_id = Integer.parseInt((String)req.remove("new_folder_id"));
		new EmailService().changeFolder(ids, new_folder_id, "1".equals(req.get("isfrom")));
	}

	public void recycle(Map<String, Object> req) throws Exception {
		String[] ids = ((String)req.get("ids")).split(",");
		new EmailService().recycle(ids, "1".equals(req.get("isfrom")));
	}

	public void restore(Map<String, Object> req) throws Exception {
		String[] ids = ((String)req.get("ids")).split(",");
		new EmailService().restore(ids, "1".equals(req.get("isfrom")));
	}

	public void delete(Map<String, Object> req) throws Exception {
		String[] ids = ((String)req.get("ids")).split(",");
		new EmailService().delete(ids, "1".equals(req.get("isfrom")));
	}

	public void insertFolder(Map<String, Object> req) throws Exception {
		new EmailService().insertFolder(req);
	}

	public void updateFolder(Map<String, Object> req) throws Exception {
		int id = Integer.parseInt((String)req.remove("id"));
		new EmailService().updateFolder(id, req);
	}

	public void deleteFolder(Map<String, Object> req) throws Exception {
		int id = Integer.parseInt((String)req.remove("id"));
		new EmailService().deleteFolder(id);
	}

	public Result queryMineServer(Map<String, Object> req) throws Exception {
		return new EmailService().queryMineServer();
	}

	public Map<String, Object> modifyServer(Map<String, Object> req) throws Exception {
		if (!CommonFun.isNe(req.get("id"))) {
			int server_id = Integer.parseInt((String)req.remove("id"));
			return new EmailService().getMineServer(server_id);
		}
		return null;
	}

	public void insertServer(Map<String, Object> req) throws Exception {
		new EmailService().insertServer(req);
	}

	public void updateServer(Map<String, Object> req) throws Exception {
		int id = Integer.parseInt((String)req.remove("id"));
		new EmailService().updateServer(id, req);
	}

	public void deleteServer(Map<String, Object> req) throws Exception {
		int id = Integer.parseInt((String)req.remove("id"));
		new EmailService().deleteServer(id);
	}

}
