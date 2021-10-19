package cn.crtech.cooperop.oa.service;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.oa.dao.MeetingRoomDao;

/** 
* @author 作者 wangyu 
* @version 创建时间：2019年9月5日
* 类说明 操作MeetingRoomDao的service
*/
public class MeetingRoomService extends BaseService{
	
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			MeetingRoomDao dao = new MeetingRoomDao();
			return dao.query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result queryAddressDistinct(Map<String, Object> params) throws Exception {
		try {
			connect();
			MeetingRoomDao dao = new MeetingRoomDao();
			return dao.queryAddressDistinct(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect();
			MeetingRoomDao dao = new MeetingRoomDao();
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
			MeetingRoomDao dao = new MeetingRoomDao();
			//判断会议室重复
			Map<String, Object> map=new HashMap<>();
			String filter=(String) params.get("name")+(String) params.get("address");
			map.put("filter", filter);
			Record rd=dao.get(map);
			if (rd != null) {
				br.put("result", "error");
				br.put("msg", "该会议室已存在，请勿重复添加");
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
			connect();
			MeetingRoomDao dao = new MeetingRoomDao();
			Record conditions = new Record();
			//判断会议室重复
			Map<String, Object> map=new HashMap<>();
			String filter=(String) params.get("name")+(String) params.get("address");
			map.put("filter", filter);
			Record rd=dao.get(map);
			if (rd != null) {
				br.put("result", "error");
				br.put("msg", "修改失败，不能修改成已有会议室");
				return br;
			}
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
			MeetingRoomDao dao = new MeetingRoomDao();
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