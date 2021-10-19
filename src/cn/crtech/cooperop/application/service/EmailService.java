package cn.crtech.cooperop.application.service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import cn.crtech.cooperop.application.dao.EmailDao;
import cn.crtech.cooperop.bus.message.MessageSender;
import cn.crtech.cooperop.bus.message.bean.MessageConfigEmail;
import cn.crtech.cooperop.bus.message.bean.MessageEmail;
import cn.crtech.cooperop.bus.message.sender.EmailSender;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;

public class EmailService extends BaseService {
	public Result queryMineFolder(boolean withoutsystemfolder) throws Exception {
		try {
			connect();
			EmailDao td = new EmailDao();
			return td.queryMineFolder(withoutsystemfolder);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Result queryMine(Map<String, Object> params) throws Exception {
		try {
			connect();
			EmailDao td = new EmailDao();
			return td.queryMine(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Record getMine(Map<String, Object> params) throws Exception {
		try {
			connect();
			EmailDao td = new EmailDao();
			if (!CommonFun.isNe(params.get("linkid"))) {
				Record t = new Record(params);
				t.put("id", t.get("linkid"));
				Record rtn = td.getMine(t);
				rtn.remove("id");
				return rtn;
			} else if (!CommonFun.isNe(params.get("id"))) {
				Record rtn = td.getMine(params);
				if (rtn.get("read_time") == null) {
					td.saveRead(new String[] {rtn.getString("id")});
				}
				return rtn;
			}
			return null;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int queryEmailCount(Map<String, Object> params) throws Exception {
		try {
			connect();
			EmailDao td = new EmailDao();
			return td.queryEmailCount(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void save(Map<String, Object> params, List<Map<String, Object>> send_to) throws Exception {
		try {
			connect();
			EmailDao td = new EmailDao();
			start();
			int id = 0;
			if (CommonFun.isNe(params.get("id"))) {
				id = td.insert(params);
			} else {
				id = (int) params.get("id");
				td.update(id, params);
			}
			td.updateSendTo(id, send_to);
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void send(Map<String, Object> params, List<Map<String, Object>> send_to) throws Exception {
		try {
			connect();
			EmailDao td = new EmailDao();
			start();
			int id = 0;
			params.put("send_time", "sysdate");
			if (CommonFun.isNe(params.get("id"))) {
				id = td.insert(params);
			} else {
				id = Integer.parseInt((String) params.get("id"));
				td.update(id, params);
			}
			td.updateSendTo(id, send_to);
			td.send(id);
			Record out = td.queryOut(id);
			if (out != null) {
				String config = GlobalVar.getSystemProperty("email.config");
				if (config.charAt(0) != '/' && config.charAt(1) != ':') {
					config = GlobalVar.getWorkPath() + File.separator + config;
				}
				Properties p = CommonFun.loadPropertiesFile(config);
				MessageConfigEmail mce = new MessageConfigEmail();
				mce.setDebug(Long.parseLong(p.getProperty("debug", "0")));
				mce.setHost(CommonFun.isNe(out.getString("smtp_host")) ? p.getProperty("smtp.host")
						: out.getString("smtp_host"));
				mce.setPort(CommonFun.isNe(out.getString("smtp_port")) ? Long.parseLong(p.getProperty("smtp.port", "25"))
						: out.getLong("smtp_port"));
				mce.setUsername(CommonFun.isNe(out.getString("server_user")) ? p.getProperty("username")
						: out.getString("server_user"));
				mce.setPassword(CommonFun.isNe(out.getString("password")) ? p.getProperty("password")
						: out.getString("password"));
				mce.setIsSsl(CommonFun.isNe(out.getString("smtp_ssl")) ? Long.parseLong(p.getProperty("smtp.isssl", "0"))
						: out.getLong("smtp_ssl"));

				MessageEmail email = new MessageEmail();
				email.setProductCode("oa");
				email.setMailFrom(mce.getUsername()); //out.getString("mail_from"));  //501 mail from address must be same as authorization user
				email.setMailTo(out.getString("mail_to"));
				email.setMailCc(out.getString("mail_cc"));
				email.setMailBcc(out.getString("mail_bcc"));
				email.setSubject(out.getString("subject"));
				email.setContent(out.getString("content"));
				email.setAttach(out.getString("attach_files"));
				EmailSender.send(mce, email);
			}
			commit();
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void read(String[] ids) throws Exception {
		try {
			connect();
			EmailDao td = new EmailDao();
			start();
			td.saveRead(ids);
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void unread(String[] ids) throws Exception {
		try {
			connect();
			EmailDao td = new EmailDao();
			start();
			td.unRead(ids);
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void changeFolder(String[] ids, int new_folder_id, boolean isfrom) throws Exception {
		try {
			connect();
			EmailDao td = new EmailDao();
			start();
			td.changeFolder(ids, new_folder_id, isfrom);
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void recycle(String[] ids, boolean isfrom) throws Exception {
		try {
			connect();
			EmailDao td = new EmailDao();
			start();
			td.recycle(ids, isfrom);
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void restore(String[] ids, boolean isfrom) throws Exception {
		try {
			connect();
			EmailDao td = new EmailDao();
			start();
			td.restore(ids, isfrom);
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void delete(String[] ids, boolean isfrom) throws Exception {
		try {
			connect();
			EmailDao td = new EmailDao();
			start();
			td.delete(ids, isfrom);
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void insertFolder(Map<String, Object> params) throws Exception {
		try {
			connect();
			EmailDao td = new EmailDao();
			start();
			td.insertFolder(params);
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void updateFolder(int folder_id, Map<String, Object> params) throws Exception {
		try {
			connect();
			EmailDao td = new EmailDao();
			start();
			td.updateFolder(folder_id, params);
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void deleteFolder(int folder_id) throws Exception {
		try {
			connect();
			EmailDao td = new EmailDao();
			start();
			td.deleteFolder(folder_id);
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Result queryMineServer() throws Exception {
		try {
			connect();
			EmailDao td = new EmailDao();
			return td.queryMineServer();
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Record getMineServer(int server_id) throws Exception {
		try {
			connect();
			EmailDao td = new EmailDao();
			return td.getMineServer(server_id);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void insertServer(Map<String, Object> params) throws Exception {
		try {
			connect();
			EmailDao td = new EmailDao();
			start();
			td.insertServer(params);
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void updateServer(int server_id, Map<String, Object> params) throws Exception {
		try {
			connect();
			EmailDao td = new EmailDao();
			start();
			td.updateServer(server_id, params);
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void deleteServer(int server_id) throws Exception {
		try {
			connect();
			EmailDao td = new EmailDao();
			start();
			td.deleteServer(server_id);
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

}
