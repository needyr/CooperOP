package cn.crtech.cooperop.oa.action.meeting;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.oa.service.MeetingFacilityService;

/**
 * @author 作者 wangyu
 * @version 创建时间：2019年9月9日 
 * 类说明 操作会议室的设备的action
 */
@DisValidPermission
public class MeetingFacilityAction extends BaseAction {

    public Result query(Map<String, Object> req) throws Exception {
	return new MeetingFacilityService().query(req);
    }

    // 已存在设备
    public Result queryFacilityDistinct(Map<String, Object> req) throws Exception {
	return new MeetingFacilityService().queryFacilityDistinct(req);
    }

    public Map<String, Object> get(Map<String, Object> req) throws Exception {
	return new MeetingFacilityService().get(req);
    }

    public Map<String, Object> add(Map<String, Object> req) throws Exception {
	if (CommonFun.isNe(req.get("id"))) {
	    return req;
	} else {
	    return new MeetingFacilityService().get(req);
	}
    }

    public Record insert(Map<String, Object> req) throws Exception {
	return new MeetingFacilityService().insert(req);
    }

    public Record update(Map<String, Object> req) throws Exception {
	return new MeetingFacilityService().update(req);
    }

    public Record delete(Map<String, Object> req) throws Exception {
	return new MeetingFacilityService().delete(req);
    }

}
