package cn.crtech.cooperop.oa.service;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.oa.dao.MeetingTagsDao;

/**  
* @Description: 
* @author: wangyu
* @date: 2019年9月29日
*/
public class MeetingTagsService extends BaseService{
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			MeetingTagsDao dao = new MeetingTagsDao();
			return dao.query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result queryDistinct(Map<String, Object> params) throws Exception {
		try {
			connect();
			MeetingTagsDao dao = new MeetingTagsDao();
			return dao.queryDistinct(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect();
			MeetingTagsDao dao = new MeetingTagsDao();
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
			connect();
			start();
			MeetingTagsDao dao = new MeetingTagsDao();
			//判断是否已经存在该标签
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("name", params.get("name"));
			map.put("meeting_room_id", params.get("meeting_room_id"));
			if(dao.get(map) != null) {
				br.put("result", "error");
				br.put("msg", "此会议室已有该标签，请勿重复添加！");
				return br;
			}
			String actor = user().getId();
			params.remove("id");
			params.put("state", 1);
			params.put("creator", actor);
			params.put("create_time", "sysdate");
			params.put("last_modifier", actor);
			params.put("last_modify_time", "sysdate");
			int i = dao.insert(params);
			if(i > 0){
				br.put("result", "success");
				br.put("msg", "保存完毕！");
			}else{
				br.put("result", "error");
				br.put("msg", "保存失败！");
			}
			commit();
			return br;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record update(Map<String, Object> params) throws Exception {
		try {
			Record br = new Record();
			connect();
			MeetingTagsDao dao = new MeetingTagsDao();
			Record conditions = new Record();
			conditions.put("id", params.remove("id"));
			params.put("last_modifier", user().getId());
			params.put("last_modify_time", "sysdate");
			int i = dao.update(params, conditions);
			if(i > 0){
				br.put("result", "success");
				br.put("msg", "更新完毕！");
			}else{
				br.put("result", "error");
				br.put("msg", "更新失败！");
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
			connect();
			MeetingTagsDao dao = new MeetingTagsDao();
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
