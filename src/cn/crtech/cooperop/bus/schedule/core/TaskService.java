package cn.crtech.cooperop.bus.schedule.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;


/**
 * 
 * @author FORWAY R&D fuhong.chen
 * @version xx 1.0
 * @createTime 2010-11-1 下午01:38:11
 */
public class TaskService extends BaseService {
	@Override
	public void connect() throws Exception {
		connect("base");
	}
	

	//查询需要执行的任务
	public List<Task> queryTask(String mac) throws Exception{
		ArrayList<Task> list;
		try{
			connect();
			TaskDao dao = new TaskDao();
			Result rs = dao.queryTask(mac);
			list = new ArrayList<Task>();
			for (Record record : rs.getResultset()) {
				Task task = new Task();
				task.setInstanceId((String)record.get("instance"));
				task.setName((String)record.get("name"));
				task.setCron((String)record.get("scron"));
				task.setDescription((String)record.get("description"));
				task.setClassz((String)record.get("classz"));
				task.setMac((String)record.get("mac"));
				task.setState(record.getInt("state"));
				task.setRunable(true);
				task.setStartTime(new Date());
				list.add(task);
				task = null;
			}
			return list;
		} catch(Exception ex){
			throw ex;
		} finally{
			disconnect();
		}
	}
	
	//记录执行日志
	public int writeLog(TaskLog log) throws Exception{
		try{
			connect();
			start();
			TaskDao dao = new TaskDao();
			int i = dao.writeLog(log);
			commit();
			return i;
		} catch (Exception e) {
			rollback();
			throw e;
		} finally{
			disconnect();
		}
	}
}
