package cn.crtech.cooperop.hospital_common.action;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.CommentManageService;

@DisLoggedIn
public class CommentmanageAction extends BaseAction {

	public List<Record> query(Map<String, Object> params) throws Exception {
		return new CommentManageService().query(params).getResultset();
	}

	public int save(Map<String, Object> params) throws Exception {
		return new CommentManageService().insert(params);
	}
	
	public Map<String, Object> growSort(Map<String, Object> params) throws Exception {
		return new CommentManageService().growSort(params);
	}

	public int update(Map<String, Object> params) throws Exception {
		return new CommentManageService().update(params);
	}

	public Result queryProduct(Map<String, Object> params) throws Exception {
		return new CommentManageService().queryProduct(params);
	}

	public Result queryComment(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("id"))){
			Result queryComment = new CommentManageService().queryComment(params);
			return queryComment;
		}else{
			return null;
		}	
	}

	public int delete(Map<String, Object> params) throws Exception {
		return new CommentManageService().delete(params);
	}
	
	public Result queryPid(Map<String, Object> params) throws Exception {
		return new CommentManageService().queryPid(params);
	}
	
	public Record getIs_sys(Map<String, Object> params) throws Exception {
		return new CommentManageService().getIs_sys(params);
	}

	// 单条查询
	public Record add(Map<String, Object> params) throws Exception {
		if (!CommonFun.isNe(params.get("id"))) {
			return new CommentManageService().get(params);
		} else {
			return null;
		}
	}

}
