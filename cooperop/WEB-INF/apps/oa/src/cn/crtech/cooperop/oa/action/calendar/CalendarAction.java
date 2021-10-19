package cn.crtech.cooperop.oa.action.calendar;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.oa.service.WorkDayService;

/**
 * @Description: oa日历action
 * @author: wangyu
 * @date: 2019年10月29日
 */
@DisValidPermission
public class CalendarAction extends BaseAction {

    //查询日历
    public Result query(Map<String, Object> req) throws Exception {
	return new WorkDayService().query(req);
    }

    //获取指定id，或者指定日期日历
    public Map<String, Object> get(Map<String, Object> req) throws Exception {
	return new WorkDayService().get(req);
    }

    //打开add页面
    public Map<String, Object> add(Map<String, Object> req) throws Exception {
	return new WorkDayService().get(req);
    }

    public Record insert(Map<String, Object> req) throws Exception {
	return new WorkDayService().insert(req);
    }

    public Record update(Map<String, Object> req) throws Exception {
	return new WorkDayService().update(req);
    }

    public Record delete(Map<String, Object> req) throws Exception {
	return new WorkDayService().delete(req);
    }

}
