package cn.crtech.cooperop.oa.action.meeting;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.oa.service.MeetingTagsService;

/**
 * @Description:
 * @author: wangyu
 * @date: 2019年9月29日
 */
@DisValidPermission
public class MeetingTagsAction extends BaseAction {

    public Result query(Map<String, Object> req) throws Exception {
	return new MeetingTagsService().query(req);
    }

    // 获取已有标签
    public Result queryDistinct(Map<String, Object> req) throws Exception {
	return new MeetingTagsService().queryDistinct(req);
    }

    public Map<String, Object> get(Map<String, Object> req) throws Exception {
	return new MeetingTagsService().get(req);
    }

    public Map<String, Object> add(Map<String, Object> req) throws Exception {
	if (CommonFun.isNe(req.get("id"))) {
	    return req;
	} else {
	    return new MeetingTagsService().get(req);
	}
    }

    public Record insert(Map<String, Object> req) throws Exception {
	return new MeetingTagsService().insert(req);
    }

    public Record update(Map<String, Object> req) throws Exception {
	return new MeetingTagsService().update(req);
    }

    public Record delete(Map<String, Object> req) throws Exception {
	return new MeetingTagsService().delete(req);
    }

}
