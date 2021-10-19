package cn.crtech.cooperop.application.action;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.service.SuggestionsService;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

@DisValidPermission
public class SuggestionsAction extends BaseAction {
	public List<Record> querySuggestions(Map<String, Object> req) throws Exception {
		return new SuggestionsService().querySuggestions(req).getResultset();
	}
	public Result queryActors(Map<String, Object> req) throws Exception {
		return new SuggestionsService().queryActors(req);
	}
	
	public Result queryMine(Map<String, Object> req) throws Exception {
		return new SuggestionsService().queryMine(req);
	}

	public Record suggestions(Map<String, Object> req) throws Exception {
		return new SuggestionsService().get(req);
	}

	public void saveRead(Map<String, Object> req) throws Exception {
		new SuggestionsService().saveRead(req);
	}

	public int suggestionsnum(Map<String, Object> req) throws Exception {
		return new SuggestionsService().querySuggestionsCount(req);
	}
	public Map<String, Object> save(Map<String, Object> req) throws Exception {
		Map<String, Object> map = CommonFun.json2Object((String)req.remove("data"), Map.class);
		map.put("type", 1);
		new SuggestionsService().save(map);
		return req;
	}
}
