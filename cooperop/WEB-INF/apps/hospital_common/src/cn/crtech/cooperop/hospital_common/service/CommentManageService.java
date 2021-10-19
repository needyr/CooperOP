package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.CommentManageDao;

public class CommentManageService extends BaseService {

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new CommentManageDao().query(params);
		} catch (Exception e) {
			throw e;
		} finally{
			disconnect();
		}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		
		try {
			connect();
			CommentManageDao commentManageDao = new CommentManageDao();
			if(CommonFun.isNe(params.get("parent_id"))){
				params.put("level", 1);
			}else{
				Record queryLevel = commentManageDao.queryLevel(params);
				params.put("level", queryLevel.getInt("level")+1);
			}
			return commentManageDao.insert(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

	public int update(Map<String, Object> params) throws Exception {
		try {
			connect();
			CommentManageDao commentManageDao = new CommentManageDao();
			if(CommonFun.isNe(params.get("parent_id"))){
				params.put("level", 1);
			}else{
				Record queryLevel = commentManageDao.queryLevel(params);
				params.put("level", queryLevel.getInt("level")+1);
			}
			return commentManageDao.update(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

	public Result queryProduct(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new CommentManageDao().queryProduct(params);
		} catch (Exception e) {
			throw e;
		} finally{
			disconnect();
		}
	}

	public Result queryComment(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new CommentManageDao().queryComment(params);	
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Record queryCommentType(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new CommentManageDao().queryCommentType(params);	
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Result queryPid(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new CommentManageDao().queryPid(params);	
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new CommentManageDao().delete(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new CommentManageDao().get(params);	
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> growSort(Map<String, Object> params) throws Exception {
		try {
			connect();
			Record countMaxSort = new CommentManageDao().growSort(params);
			params.clear();
			if(CommonFun.isNe(countMaxSort)) {
				params.put("maxsort", 0);
				params.put("system_code", null);
			}else {
				params.put("maxsort", countMaxSort.get("maxsort"));
				params.put("system_code", countMaxSort.get("system_code"));
			}
			return 	params;
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

	public Record  getIs_sys(Map<String, Object> params) throws Exception {
		try {
			connect();
			Record r=new CommentManageDao().getIs_sys(params);
			return r;
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

}
