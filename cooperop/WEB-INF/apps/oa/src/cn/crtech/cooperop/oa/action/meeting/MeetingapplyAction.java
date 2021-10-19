package cn.crtech.cooperop.oa.action.meeting;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.oa.service.MeetingFacilityService;
import cn.crtech.cooperop.oa.service.MeetingRecordService;

/**
 * @author 作者 wangyu
 * @version 创建时间：2019年9月5日 
 * 类说明 处理会议室申请请求
 */
public class MeetingapplyAction extends BaseAction {

    public Result query(Map<String, Object> req) throws Exception {
	return new MeetingRecordService().query(req);
    }

    public Result queryLimitSubscribeList(Map<String, Object> req) throws Exception {
	return new MeetingRecordService().queryLimitSubscribeList(req);
    }

    /**
     * 展示所有会议室
     * 
     * @param req tags:标签
     * @return
     * @throws Exception
     */
    public Result showList(Map<String, Object> req) throws Exception {
	return new MeetingRecordService().queryAllMeetingRoom(req);
    }

    /**
     * 查询用户，用于选择：会议主持人、纪要人
     * 
     * @param req
     * @return
     * @throws Exception
     */
    @DisValidPermission
    public Result queryUser(Map<String, Object> req) throws Exception {
	return new MeetingRecordService().queryUser(req);
    }

    /**
     * 纪要修改，获取当前会议记录的的会议纪要
     * 
     * @param req
     * @return
     * @throws Exception
     */
    public Map<String, Object> summary(Map<String, Object> req) throws Exception {
	return new MeetingRecordService().get(req);
    }

    /**
     * 跳转修改页面
     * 
     * @param req
     * @return
     * @throws Exception
     */
    public Map<String, Object> add(Map<String, Object> req) throws Exception {
	// 判断是否有设备
	Record rd = new MeetingFacilityService().get(req);
	if (!CommonFun.isNe(req.get("id"))) {
	    Record result = new MeetingRecordService().get(req);
	    if (rd != null) {
		result.put("hasFacility", 1);
	    }
	    return result;
	} else {
	    if (rd != null) {
		req.put("hasFacility", 1);
	    }
	    return req;
	}
    }

    /**
     * 详情展示
     * 
     * @param req
     * @return
     * @throws Exception
     */
    public Map<String, Object> details(Map<String, Object> req) throws Exception {
	return new MeetingRecordService().get(req);
    }

    public Record insert(Map<String, Object> req) throws Exception {
	return new MeetingRecordService().insert(req);
    }

    public Record update(Map<String, Object> req) throws Exception {
	return new MeetingRecordService().update(req);
    }

    public Record delete(Map<String, Object> req) throws Exception {
	return new MeetingRecordService().delete(req);
    }

}
