package cn.crtech.cooperop.setting.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.setting.dao.PostDao;

public class PostService extends BaseService{
	
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			PostDao pd = new PostDao();
			return pd.query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result queryDistinct(Map<String, Object> params) throws Exception {
		try {
			connect();
			PostDao pd = new PostDao();
			return pd.queryDistinct(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect();
			PostDao pd = new PostDao();
			params.put("state", 1);
			params.remove("post_id");
			int i=pd.insert(params);	//插入岗位基本信息
			return i;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect();
			PostDao pd = new PostDao();
			return pd.update(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> get(Map<String, Object> params) throws Exception {
		try {
			connect();
			PostDao pd = new PostDao();
			return pd.get(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect();
			PostDao pd = new PostDao();
			params.put("state",-1);
			int i = pd.delete(params);
			return i;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result queryReq(Map<String, Object> params) throws Exception {
		try {
			connect();
			PostDao pd = new PostDao();
			return pd.queryReq(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public int saveReq(Map<String, Object> params) throws Exception {
		try {
			connect();
			PostDao pd = new PostDao();
			return pd.saveReq(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public int deleteReq(Map<String, Object> params) throws Exception {
		try {
			connect();
			PostDao pd = new PostDao();
			int i = pd.deleteReq(params);
			return i;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
