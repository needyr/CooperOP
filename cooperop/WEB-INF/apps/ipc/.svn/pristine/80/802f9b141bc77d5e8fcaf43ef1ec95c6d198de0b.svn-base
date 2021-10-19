package cn.crtech.cooperop.ipc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.ipc.dao.CheckAndCommentDao;

public class CheckaAndCommentService extends BaseService{

	public Map<String, Object> queryCheckAndCommentInfo(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("checkandcommentdetail", new CheckAndCommentDao().queryCheckAndCommentInfo(params).getResultset());
			return result;
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public Map<String, Object> queryCommentAndYizu(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> result = new HashMap<String, Object>();
			String pkeys = (String)params.remove("p_keys");
			params.put("p_keys", pkeys.split(","));
			/*List<Record> commentadvice = new CheckAndCommentDao().queryCommentAdvice(params).getResultset();
			result.put("commentadvice",commentadvice);*/
			result.put("realyizu", new CheckAndCommentDao().queryRealYizu(params).getResultset());
			return result;
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
}
