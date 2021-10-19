package cn.crtech.cooperop.ipc.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.ipc.service.CommentReportService;

public class CommentreportAction extends BaseAction{
	@DisLoggedIn
	public Result query(Map<String, Object> map) throws Exception{
		return new CommentReportService().query(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> basecomments(Map<String, Object> map) throws Exception{
		return new CommentReportService().basecomment(map);
	}
}
