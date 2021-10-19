package cn.crtech.cooperop.application.service;
import java.util.Map;

import cn.crtech.cooperop.application.dao.MessageTemplateDao;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class MessageTemplateService extends BaseService {
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new MessageTemplateDao().query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result queryTemp(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new MessageTemplateDao().queryTemplate(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void insert(Map<String, Object> params) throws Exception {
		try {
			connect();
			start();
			MessageTemplateDao mtd = new MessageTemplateDao();
			Map<String, Object> sort = (Map<String, Object>)params.remove("sort");
			sort.put("state", "1");
			int id = mtd.insert(sort);
			Map<String, Object> sms = (Map<String, Object>)params.get("sms");
			sms.put("sort_id", id);
			sms.put("type", MessageTemplateDao.temp_type_sms);
			Map<String, Object> email = (Map<String, Object>)params.get("email");
			email.put("sort_id", id);
			email.put("type", MessageTemplateDao.temp_type_email);
			Map<String, Object> wx = (Map<String, Object>)params.get("wx");
			wx.put("sort_id", id);
			wx.put("type", MessageTemplateDao.temp_type_wx);
			Map<String, Object> xt = (Map<String, Object>)params.get("xt");
			xt.put("sort_id", id);
			xt.put("type", MessageTemplateDao.temp_type_im);
			Map<String, Object> sysmes = (Map<String, Object>)params.get("sysmes");
			sysmes.put("sort_id", id);
			sysmes.put("type", MessageTemplateDao.temp_type_sysmes);
			mtd.insertTemplate(sms);
			mtd.insertTemplate(email);
			mtd.insertTemplate(wx);
			mtd.insertTemplate(xt);
			mtd.insertTemplate(sysmes);
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Map<String , Object> getSimple(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new MessageTemplateDao().get(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Map<String , Object> get(Map<String, Object> params) throws Exception {
		try {
			connect();
			MessageTemplateDao mtd = new MessageTemplateDao();
			Record r = new Record();
			r.put("msort", mtd.get(params));
			params.put("sort_id", params.get("id"));
			params.remove("id");
			params.put("type", 3);
			r.put("xt", mtd.queryTemplate(params).getResultset(0));
			params.put("type", 2);
			r.put("email", mtd.queryTemplate(params).getResultset(0));
			params.put("type", 4);
			r.put("wx", mtd.queryTemplate(params).getResultset(0));
			params.put("type", 1);
			r.put("sms", mtd.queryTemplate(params).getResultset(0));
			return r;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void update(Map<String, Object> params) throws Exception {
		try {
			connect();
			start();
			MessageTemplateDao mtd = new MessageTemplateDao();
			mtd.update((Map<String, Object>)params.get("sort"));
			mtd.updateTemplate((Map<String, Object>)params.get("sms"));
			mtd.updateTemplate((Map<String, Object>)params.get("email"));
			mtd.updateTemplate((Map<String, Object>)params.get("wx"));
			mtd.updateTemplate((Map<String, Object>)params.get("xt"));
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void updateSimple(Map<String, Object> params) throws Exception {
		try {
			connect();
			MessageTemplateDao mtd = new MessageTemplateDao();
			mtd.update(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
