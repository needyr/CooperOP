package cn.crtech.cooperop.oa.action.meeting;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.oa.service.MeetingRoomService;

/**
 * @author 作者 wangyu
 * @version 创建时间：2019年9月5日 
 * 类说明 处理会议室信息的action
 */
public class MeetingroomAction extends BaseAction {

    /**
     * 获取所有会议室
     * 
     * @param req
     * @return
     * @throws Exception
     */
    public Result query(Map<String, Object> req) throws Exception {
	return new MeetingRoomService().query(req);
    }

    public Result queryAddressDistinct(Map<String, Object> req) throws Exception {
	return new MeetingRoomService().queryAddressDistinct(req);
    }

    /**
     * 跟据id获取会议室详情
     * 
     * @param req
     * @return
     * @throws Exception
     */
    public Map<String, Object> details(Map<String, Object> req) throws Exception {
	return new MeetingRoomService().get(req);
    }

    /**
     * 打开修改页面
     * 
     * @param req
     * @return
     * @throws Exception
     */
    public Map<String, Object> add(Map<String, Object> req) throws Exception {
	if (!CommonFun.isNe(req.get("id"))) {
	    return new MeetingRoomService().get(req);
	} else {
	    return req;
	}
    }

    /**
     * 新增会议室
     * 
     * @param req
     * @return
     * @throws Exception
     */
    public Record insert(Map<String, Object> req) throws Exception {
	return new MeetingRoomService().insert(req);
    }

    // 更新会议室信息
    public Record update(Map<String, Object> req) throws Exception {
	return new MeetingRoomService().update(req);
    }

    // 删除
    public Record delete(Map<String, Object> req) throws Exception {
	return new MeetingRoomService().delete(req);
    }

}
