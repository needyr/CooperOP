package cn.crtech.cooperop.oa.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.oa.service.CalendarEventService;

/**
 * @Description:
 * @author: wangyu
 * @date: 2019年11月8日 
 */

public class CalendarEventAction extends BaseAction {
    @DisValidPermission
    public Result query(Map<String, Object> req) throws Exception {
	return new CalendarEventService().query(req);
    }
    @DisValidPermission
    public Result queryMine(Map<String, Object> req) throws Exception {
    	return new CalendarEventService().queryMine(req);
    }

    public Map<String, Object> get(Map<String, Object> req) throws Exception {
	return new CalendarEventService().get(req);
    }

    public Map<String, Object> add(Map<String, Object> req) throws Exception {
	if (CommonFun.isNe(req.get("id"))) {
	    return req;
	} else {
	    return new CalendarEventService().get(req);
	}
    }

    public Record insert(Map<String, Object> req) throws Exception {
	return new CalendarEventService().insert(req);
    }

    public Record update(Map<String, Object> req) throws Exception {
	return new CalendarEventService().update(req);
    }

    public Record delete(Map<String, Object> req) throws Exception {
	return new CalendarEventService().delete(req);
    }

}
