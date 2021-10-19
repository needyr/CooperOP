package cn.crtech.cooperop.oa.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.oa.dao.MeetingFacilityDao;

/** 
* @author 作者 wangyu 
* @version 创建时间：2019年9月5日
* 类说明 操作MeetingFacilityDao的service
*/
public class MeetingFacilityService extends BaseService{
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			MeetingFacilityDao dao = new MeetingFacilityDao();
			return dao.query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	//已存在设备
	public Result queryFacilityDistinct(Map<String, Object> params) throws Exception {
		try {
			connect();
			MeetingFacilityDao dao = new MeetingFacilityDao();
			return dao.queryFacilityDistinct(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect();
			MeetingFacilityDao dao = new MeetingFacilityDao();
			return dao.get(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	//新增设备
	public Record insert(Map<String, Object> params) throws Exception {
		try {
			Record br = new Record();
			connect();
			MeetingFacilityDao dao = new MeetingFacilityDao();
			params.remove("id");
			//先判断当前房间是否有该设备
			if(dao.get(params) != null) {
				br.put("result", "error");
				br.put("msg", "该会议室已存在此设备！");
				return br;
			}
			String actor = user().getId();
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
			MeetingFacilityDao dao = new MeetingFacilityDao();
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
			MeetingFacilityDao dao = new MeetingFacilityDao();
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