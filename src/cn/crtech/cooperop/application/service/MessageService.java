package cn.crtech.cooperop.application.service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import cn.crtech.cooperop.application.dao.MessageDao;
import cn.crtech.cooperop.bus.message.MessageSender;
import cn.crtech.cooperop.bus.message.bean.MessageConfigEmail;
import cn.crtech.cooperop.bus.message.bean.MessageEmail;
import cn.crtech.cooperop.bus.message.sender.EmailSender;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;

public class MessageService extends BaseService {

	public Result session(Map<String, Object> params) throws Exception {
		try {
			connect();
			MessageDao td = new MessageDao();
			return td.session(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Result listnew(Map<String, Object> params) throws Exception {
		try {
			connect();
			MessageDao td = new MessageDao();
			Result rs = td.listnew(params);
			td.read(params);
			return rs;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Record send(Map<String, Object> params) throws Exception {
		try {
			connect("base");
			MessageDao td = new MessageDao();
			start();
			int id = td.insert(params);
			Record rtn = td.get(id);
			commit();
			return rtn;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int messagenum(Map<String, Object> params) throws Exception {
		try {
			connect();
			MessageDao td = new MessageDao();
			return td.messagenum(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Result history(Map<String, Object> params) throws Exception {
		try {
			connect();
			MessageDao td = new MessageDao();
			return td.history(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

}
