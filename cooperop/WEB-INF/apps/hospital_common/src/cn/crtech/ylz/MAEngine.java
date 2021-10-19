package cn.crtech.ylz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.schedule.MsgSendSchedule;
import cn.crtech.cooperop.hospital_common.service.system.MsgAlertService;


/* 弹窗消息引擎 */
public class MAEngine {

	private static Map<String, Object> msgCont = new ConcurrentHashMap<String, Object>();
	
	private static final String MSGKEY_ = "no_";
	
	// 载入消息
	public static void init() {
		try {
			 List<Record> msglist= new MsgAlertService().queryAllMsg(new HashMap<String, Object>()).getResultset();
			 if(msglist.size() > 0) {
				 for (Record msgr : msglist) {
					 if(CommonFun.isNe(msgCont.get(MSGKEY_ + msgr.getString("send_to_user")))) {
						 List<Record> list = new ArrayList<Record>();
						 list.add(msgr);
						 msgCont.put(MSGKEY_ +  msgr.getString("send_to_user"), list);
					 }else {
						 List<Record> list = (List<Record>) msgCont.get(MSGKEY_ + msgr.getString("send_to_user"));
						 list.add(msgr);
					 }
				}
			 }
			 ylz.p("弹窗消息处理中心初始化：[" + msgCont.size() + "]个用户有新消息");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			MsgSendSchedule.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 获取未读
	public static List<Record> getMsg(String userNo) {
		if(CommonFun.isNe(msgCont.get(MSGKEY_ + userNo))) {
			return new ArrayList<Record>();
		}else {
			return (List<Record>) msgCont.get(MSGKEY_ + userNo);
		}
	}
	
	// 获取所有未读
	public static Map<String, Object> getMsgAll() {
		if(CommonFun.isNe(msgCont)) {
			return null;
		}else {
			return msgCont;
		}
	}
	
	// 已读操作(单条)
	public static int readOne(String userNo, String msgid) {
		List<Record> list = (List<Record>) msgCont.get(MSGKEY_ + userNo);
		if (list != null) {
			for(int i = 0; i < list.size(); i ++){
				Record msg = list.get(i);
				if(msg.get("id").equals(msgid)) {
					list.remove(i);
					break;
				}
			}
		}
		Map<String, Object> tjMap = new HashMap<String, Object>();
		tjMap.put("id", msgid);
		tjMap.put("is_read", 1);
		try {
			new MsgAlertService().updateRead(tjMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	// 新增消息
	public static String addNewMsg(Record params) {
		String msg_id = "";
		try {
			msg_id = new MsgAlertService().insert(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(CommonFun.isNe(msgCont.get(MSGKEY_ + params.get("send_to_user")))) {
			List<Record> list = new ArrayList<Record>();
			list.add(params);
			msgCont.put(MSGKEY_ +  params.getString("send_to_user"), list);
		}else{
			List<Record> list = (List<Record>) msgCont.get(MSGKEY_ + params.getString("send_to_user"));
			list.add(params);
		};
		return msg_id;
	}
}
