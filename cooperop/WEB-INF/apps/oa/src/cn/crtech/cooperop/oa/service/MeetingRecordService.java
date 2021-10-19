package cn.crtech.cooperop.oa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.oa.dao.MeetingFacilityDao;
import cn.crtech.cooperop.oa.dao.MeetingRecordDao;
import cn.crtech.cooperop.oa.dao.MeetingRoomDao;

/**
 * @author 作者 wangyu
 * @version 创建时间：2019年9月5日 
 * 类说明 处理来自会议室申请的service
 */
public class MeetingRecordService extends BaseService {
    public Result query(Map<String, Object> params) throws Exception {
	try {
	    connect();
	    MeetingRecordDao dao = new MeetingRecordDao();
	    return dao.query(params);
	} catch (Exception ex) {
	    throw ex;
	} finally {
	    disconnect();
	}
    }

    public Result queryLimitSubscribeList(Map<String, Object> params) throws Exception {
	try {
	    connect();
	    MeetingRecordDao dao = new MeetingRecordDao();
	    return dao.queryTopTwo(params);
	} catch (Exception ex) {
	    throw ex;
	} finally {
	    disconnect();
	}
    }

    // 查询用户
    public Result queryUser(Map<String, Object> params) throws Exception {
	try {
	    connect();
	    MeetingRecordDao dao = new MeetingRecordDao();
	    return dao.queryUser(params);
	} catch (Exception ex) {
	    throw ex;
	} finally {
	    disconnect();
	}
    }

    // 单条记录
    public Record get(Map<String, Object> params) throws Exception {
	try {
	    connect();
	    MeetingRecordDao dao = new MeetingRecordDao();
	    return dao.get(params);
	} catch (Exception ex) {
	    throw ex;
	} finally {
	    disconnect();
	}
    }

    // 新增
    public Record insert(Map<String, Object> params) throws Exception {
	try {
	    Record br = new Record();
	    connect();
	    MeetingRecordDao dao = new MeetingRecordDao();
	    String actor = user().getId();
	    params.remove("id");
	    // 先判断该会议室在该时间段是否在使用
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("meeting_id", params.get("meeting_id"));
	    map.put("start_time", params.get("start_time"));
	    map.put("end_time", params.get("end_time"));
	    // 查询时间是否冲突
	    Record hasmeeting = dao.queryExists(map);
	    if (hasmeeting != null) {
		br.put("result", "error");
		br.put("msg", "选择时间段已被占用,[会议：" + hasmeeting.getString("name") + ", 会议时间："
			+ hasmeeting.getString("start_time") + " 到 " + hasmeeting.getString("end_time") + "]");
		return br;
	    }
	    ;
	    params.put("state", 1);
	    params.put("creator", actor);
	    params.put("create_time", "sysdate");
	    params.put("last_modifier", actor);
	    params.put("last_modify_time", "sysdate");
	    int i = dao.insert(params);
	    if (i > 0) {
		br.put("result", "success");
		br.put("msg", "保存完毕！");
	    } else {
		br.put("result", "error");
		br.put("msg", "保存失败！");
	    }
	    return br;
	} catch (Exception ex) {
	    throw ex;
	} finally {
	    disconnect();
	}
    }

    // 更新
    public Record update(Map<String, Object> params) throws Exception {
	try {
	    Record br = new Record();
	    connect();
	    MeetingRecordDao dao = new MeetingRecordDao();
	    Record conditions = new Record();
	    conditions.put("id", params.remove("id"));
	    params.put("last_modifier", user().getId());
	    params.put("last_modify_time", "sysdate");
	    int i = dao.update(params, conditions);
	    if (i > 0) {
		br.put("result", "success");
		br.put("msg", "更新完毕！");
	    } else {
		br.put("result", "error");
		br.put("msg", "更新失败！");
	    }
	    return br;
	} catch (Exception ex) {
	    throw ex;
	} finally {
	    disconnect();
	}
    }

    // 删除
    public Record delete(Map<String, Object> params) throws Exception {
	try {
	    Record br = new Record();
	    connect();
	    MeetingRecordDao dao = new MeetingRecordDao();
	    int i = dao.delete(params);
	    if (i > 0) {
		br.put("result", "success");
		br.put("msg", "删除完毕！");
	    } else {
		br.put("result", "error");
		br.put("msg", "删除失败！");
	    }
	    return br;
	} catch (Exception ex) {
	    throw ex;
	} finally {
	    disconnect();
	}
    }

    // 查询所有会议室
    public Result queryAllMeetingRoom(Map<String, Object> params) throws Exception {
	try {
	    connect();
	    MeetingRoomDao dao = new MeetingRoomDao();
	    Result rooms = dao.query(params);
	    if (rooms != null && rooms.getCount() > 0) {
		List<Record> allRooms = rooms.getResultset();
		MeetingFacilityDao facilityDao = new MeetingFacilityDao();
		MeetingRecordDao recordDao = new MeetingRecordDao();
		for (Record record : allRooms) {
		    // 获取会议室设备
		    Object id = record.get("id");
		    Map<String, Object> map = new HashMap<>();
		    map.put("meeting_id", id);
		    List<Record> allFacility = facilityDao.query(map).getResultset();
		    record.put("facilities", allFacility);
		    // 获取两条预约会议记录
		    map.put("after", 1);//after标志未开始或者未结束
		    List<Record> meetingRecord = recordDao.queryTopTwo(map).getResultset();
		    record.put("meetingRecord", meetingRecord);
		}
	    }
	    return rooms;
	} catch (Exception ex) {
	    throw ex;
	} finally {
	    disconnect();
	}
    }
}