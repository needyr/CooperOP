package cn.crtech.cooperop.bus.im.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

import cn.crtech.cooperop.bus.im.dao.MessageDao;
import cn.crtech.cooperop.bus.im.resource.ResourceManager;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class MessageService extends IMBaseService {
	public List<Record> send(Map<String, Object> params) throws Exception {
		Date now = new Date();
		try {
			connect();
			MessageDao sud = new MessageDao();
			String fileid = null;
			if (!"T".equals(params.get("type"))) {
				fileid = ResourceManager.storeResource((String) params.get("system_user_id"),
						(FileItem) params.get("content"));
				params.put("content", fileid);
			}
			params.put("send_time", now);
			int id = sud.insert(params);
			if (!"T".equals(params.get("send_type"))) {
				ResourceManager.commit(fileid);
			}
			Record p = new Record();
			p.put("id", id);
			Result rs = sud.queryMessage(p);
			return rs.getResultset();
		} finally {
			disconnect();
		}
	}

	public Result listSessionMessages(Map<String, Object> params) throws Exception {
		try {
			connect();
			MessageDao sud = new MessageDao();
			Result rs = sud.queryMessage(params);
			sud.read(params);
			return rs;
		} finally {
			disconnect();
		}
	}

	public int read(Map<String, Object> params) throws Exception {
		try {
			connect();
			MessageDao sud = new MessageDao();
			return sud.read(params);
		} finally {
			disconnect();
		}
	}

}
