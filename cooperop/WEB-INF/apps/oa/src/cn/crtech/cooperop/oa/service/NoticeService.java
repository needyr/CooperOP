package cn.crtech.cooperop.oa.service;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.oa.dao.NoticeDao;
import cn.crtech.cooperop.oa.dao.NoticeReadDao;

public class NoticeService extends BaseService{
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("base");
			NoticeDao dao = new NoticeDao();
			return dao.query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record getMyNoticenum(Map<String, Object> params) throws Exception {
		try {
			connect("base");
			NoticeDao dao = new NoticeDao();
			params.put("system_user_id", user().getId());
			return dao.getMyNoticenum(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("base");
			NoticeDao dao = new NoticeDao();
			//阅读记录
			if(!CommonFun.isNe(params.get("detail"))){
				String userid = user().getId();
				Object id = params.get("id");
				NoticeReadDao nrd = new NoticeReadDao();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("notice_id", id);
				map.put("system_user_id", userid);
				Record ur = nrd.get(map);
				if(ur == null){
					map.clear();
					map.put("notice_id", id);
					map.put("system_user_id", userid);
					map.put("read_time", "sysdate");
					map.put("last_read_time", "sysdate");
					map.put("read_counts", 1);
					nrd.insert(map);
				}else{
					Map<String, Object> condition = new HashMap<String, Object>();
					condition.put("notice_id", id);
					condition.put("system_user_id", userid);
					map.clear();
					map.put("last_read_time", "sysdate");
					map.put("read_counts", ur.getInt("read_counts")+1);
					nrd.update(map, condition);
				}
			}
			return dao.get(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record insert(Map<String, Object> params) throws Exception {
		try {
			Record br = new Record();
			connect("base");
			NoticeDao dao = new NoticeDao();
			String actor = user().getId();
			params.remove("id");
			params.put("creator", actor);
			params.put("create_time", "sysdate");
			params.put("modifier", actor);
			if(CommonFun.isNe(params.get("is_delete"))) {
				params.put("is_delete", 0);
			}
			//是否需要添加发布时间
			if("1".equals(params.get("state").toString())){
				params.put("release_time", "sysdate");
			}
			int i = dao.insert(params);
			if(i > 0){
				br.put("result", "success");
				br.put("msg", "保存完毕！");
			}else{
				br.put("result", "error");
				br.put("msg", "保存失败！");
			}
			return br;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record update(Map<String, Object> params) throws Exception {
		try {
			Record br = new Record();
			connect("base");
			NoticeDao dao = new NoticeDao();
			Record conditions = new Record();
			conditions.put("id", params.remove("id"));
			params.put("last_modifier", user().getId());
			params.put("last_modify_time", "sysdate");
			//是否需要添加发布时间
			if("1".equals(params.get("state").toString())){
				params.put("release_time", "sysdate");
			}
			int i = dao.update(params, conditions);
			if(i > 0){
				br.put("result", "success");
				br.put("msg", "操作完毕！");
			}else{
				br.put("result", "error");
				br.put("msg", "操作失败！");
			}
			return br;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record delete(Map<String, Object> params) throws Exception {
		try {
			Record br = new Record();
			connect("base");
			NoticeDao dao = new NoticeDao();
			int i = dao.delete(params);
			if(i > 0){
				br.put("result", "success");
				br.put("msg", "删除完毕！");
			}else{
				br.put("result", "error");
				br.put("msg", "删除失败！");
			}
			return br;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}