package cn.crtech.cooperop.bus.im.action;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.im.service.GroupService;
import cn.crtech.cooperop.bus.im.service.MessageService;
import cn.crtech.cooperop.bus.im.service.SystemUserService;
import cn.crtech.cooperop.bus.im.ws.Connection;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.ws.bean.ClientInfo;

public class IMAction extends BaseAction {

	public String login(String userid, String clienttype) throws Exception {
		SystemUserService sus = new SystemUserService();
		Record user = sus.login(userid);
		return Connection.newConnection(user, clienttype);
	}

	public void changeOnlineStatus(ClientInfo client, Map<String, Object> params) throws Exception {
		/*SystemUserService sus = new SystemUserService();
		sus.changeOnlineStatus((String)client.getUserinfo().get("id"), (String)params.get("online_status"));
		Record message = new Record();
		message.put("userid", client.getUserinfo().get("id"));
		message.put("online_status", params.get("online_status"));
		Connection.sendMessage2AllOnline("changeUserOnlineStatus", message);*/
	}
	
	public Map<String, Object> send(ClientInfo client, Map<String, Object> params) throws Exception {
		Record rtn = null;
		MessageService ms = new MessageService();
		params.put("system_user_id", client.getUserinfo().get("id"));
		List<Record> list = ms.send(params);
		for (Record r : list) {
			if (r.getString("read_user_id").equals(client.getUserinfo().get("id")) && r.getString("send_type").equals("from")) {
				rtn = r;
			}
			Connection.send(client, r);
		}
		return rtn;
	}

	public Map<String, Object> loadSessions(ClientInfo client, Map<String, Object> params) throws Exception {
		SystemUserService sus = new SystemUserService();
		params.put("system_user_id", client.getUserinfo().get("id"));
		return sus.loadSessions(params);
	}

	public Map<String, Object> loadContactors(ClientInfo client, Map<String, Object> params) throws Exception {
		SystemUserService sus = new SystemUserService();
		params.put("system_user_id", client.getUserinfo().get("id"));
		return sus.loadContactors(params);
	}
	public Map<String, Object> loadGroups(ClientInfo client, Map<String, Object> params) throws Exception {
		SystemUserService sus = new SystemUserService();
		params.put("system_user_id", client.getUserinfo().get("id"));
		return sus.loadContactors(params);
	}
	public Result listSessionMessages(ClientInfo client, Map<String, Object> params) throws Exception {
		MessageService ms = new MessageService();
		params.put("system_user_id", client.getUserinfo().get("id"));
		return ms.listSessionMessages(params);
	}

	public int messageRead(ClientInfo client, Map<String, Object> params) throws Exception {
		MessageService ms = new MessageService();
		params.put("system_user_id", client.getUserinfo().get("id"));
		return ms.read(params);
	}

	public Map<String, Object> listContractorUsers(ClientInfo client, Map<String, Object> params) throws Exception {
		SystemUserService sus = new SystemUserService();
		return sus.listContractorUsers(params);
	}
	
	public Map<String, Object> saveGroup(ClientInfo client, Map<String, Object> params) throws Exception {
		GroupService sus = new GroupService();
		Map<String, Object> pa = CommonFun.json2Object((String)params.get("jdata"), Map.class);
		pa.put("system_user_id", client.getUserinfo().get("id"));
		return sus.saveGroup(pa);
	}
	
}
