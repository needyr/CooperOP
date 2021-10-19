package cn.crtech.cooperop.ipc.service;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.ipc.dao.SearchRResultDao;

public class SearchRResultService extends BaseService {
	
	public String insertAll(Map<String, Object> params) throws Exception {
		String msg = "1";
		try {
			connect("ipc");
			List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("rows");
			SearchRResultDao srr = new SearchRResultDao();
			for (Map<String, Object> map : list) {
				srr.insert(map);
			}
		} catch (Exception ex){
			msg = ex.getMessage();
			ex.printStackTrace();
		}finally{
			disconnect();
		}
		return msg;
	}
	
	public Result queryResult(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new SearchRResultDao().queryResult(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public Result queryQMsg(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new SearchRResultDao().queryQMsg(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public String execComputerProc(Map<String, Object> params) throws Exception {
		String msg = "1";
		try {
			connect("ipc");
			String res = new SearchRResultDao().execComputerProc(params);
		} catch (Exception ex){
			msg = ex.getMessage();
			ex.printStackTrace();
		}finally{
			disconnect();
		}
		return msg;
	}
	
}
