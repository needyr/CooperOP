package cn.crtech.cooperop.crdc.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.crdc.service.DdictionaryService;

public class DdictionaryAction extends BaseAction {

	public Result query(Map<String, Object> req) throws Exception{
		return new DdictionaryService().query(req);
	}
	public Result queryColumns(Map<String, Object> req) throws Exception{
		return new DdictionaryService().queryColumns(req);
	}
	public Result queryDatabase(Map<String, Object> req) throws Exception{
		req.put("databasename", 1);
		return new DdictionaryService().queryDistinct(req);
	}
	public Result queryTable(Map<String, Object> req) throws Exception{
		req.put("tablename", 1);
		return new DdictionaryService().queryDistinct(req);
	}
	public Result queryTableType(Map<String, Object> req) throws Exception{
		req.put("tabletype", 1);
		return new DdictionaryService().queryDistinct(req);
	}
	public Result queryTypeExplain(Map<String, Object> req) throws Exception{
		req.put("typeexplain", 1);
		return new DdictionaryService().queryDistinct(req);
	}
	
	public int synchroOnline(Map<String,Object> req) throws Exception{
		return new DdictionaryService().synchroOnline(req);
	}
	
	public Map<String,Object> tablecolumnsdictionary(Map<String,Object> req) throws Exception{
		return req;
	}
	
	public int deleteTableRows(Map<String,Object> req) throws Exception{
		return new DdictionaryService().deleteTableRows(req);
	}
	
	public int deleteTablesRows(Map<String,Object> req) throws Exception{
		return new DdictionaryService().deleteTablesRows(req);
	}
}
