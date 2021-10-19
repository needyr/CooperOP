package cn.crtech.cooperop.hospital_common.service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.QscheduleDao;

public class QscheduleService extends BaseService {

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new QscheduleDao().query(params);
		} finally {
			disconnect();
		}
	}
	
	public Result queryProduct(Map<String, Object> params) throws Exception {
		try {
			connect("IADSCP");
			return new QscheduleDao().queryProduct(params);
		} finally {
			disconnect();
		}
	}
	
	
	/**
	 * @param params classz 定时器处理类，继承超类AbsoluteSchedule
	 * @throws Exception
	 * @function 手动执行定时器任务
	 * @author yankangkang 2019年1月11日
	 */
	public void execute(Map<String, Object> params) throws Exception {
		try {
			Class<?> forName = Class.forName(params.get("classz").toString());
			//调用执行方法
			Method method = forName.getMethod("executeOn");
			method.invoke(forName.newInstance());
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new QscheduleDao().update(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	/**
	 * @param params instance schedule表id , state 修改后的数据状态
	 * @return
	 * @throws Exception
	 * @function 修改IADSCP.dbo.schedule表中数据状态
	 * @author yankangkang 2019年1月11日
	 */
	public int updateByState(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new QscheduleDao().updateState(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	
	/**
	 * @param params comm_p_key sys_common_schedule表的id ,
	 * time_expression_p_key sys_time_expressiond表的id , instance schedule表的id
	 * @return
	 * @throws Exception
	 * @function 修改IADSCP.dbo.schedule表的定时器表达式和状态，
	 * sys_common_schedule表中关联sys_time_expression的time_p_key
	 * @author yankangkang 2019年1月11日
	 */
	public int updateByCode(Map<String, Object> params) throws Exception {
		try {
			start();
			//更新sys_common_schedule关联字段
			Map<String, Object> commMap=new HashMap<String, Object>();
			commMap.put("p_key", params.get("comm_p_key"));
			commMap.put("time_p_key", params.get("time_expression_p_key"));
			int update = update(commMap);
			connect("IADSCP");
			//更新IADSCP.dbo.schedule表sron字段
			Map<String, Object> scheduleMap=new HashMap<String, Object>();
			scheduleMap.put("instance", params.get("instance"));
			scheduleMap.put("scron", params.get("scron").toString());
			scheduleMap.put("state", params.get("state"));
			int updateByCode = new QscheduleDao().updateByCode(scheduleMap);
			if (update == 1 && updateByCode == 1) {
				commit();
				return 1;
			}else{
				rollback();
				return 0;
			}
		} catch (Exception e) {
			rollback();
			throw e;
		}finally {
			disconnect();
		}
	}
	
	/**
	 * @param params scheduleinstance schedule表id , time_expression_p_key sys_time_expressiond表的id 
	 * @return
	 * @throws Exception
	 * @function 新增IADSCP.dbo.schedule数据 , 同步sys_common_schedule表中
	 * @author yankangkang 2019年1月11日
	 */
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Record unique = new QscheduleDao().unique(params);
			if (unique==null) {
				start();
				Map<String, Object> commschdule=new HashMap<String, Object>();
				commschdule.put("product_code", params.get("product_code"));
				commschdule.put("url", params.get("classz"));
				commschdule.put("remark", params.get("description"));
				commschdule.put("schedule_id",params.get("scheduleinstance"));
				commschdule.put("time_p_key",params.get("time_expression_p_key"));
				int insert = new QscheduleDao().insert(commschdule);
				Map<String, Object> iadscpschdule=new HashMap<String, Object>();
				iadscpschdule.putAll(params);
				iadscpschdule.put("instance", params.get("scheduleinstance"));
				iadscpschdule.remove("product_code");
				iadscpschdule.remove("comm_p_key");
				iadscpschdule.remove("scheduleinstance");
				iadscpschdule.remove("time_expression_p_key");
				int insertSchedule = new QscheduleDao().insertSchedule(iadscpschdule);
				if (insert == 1 && insertSchedule == 1) {
					commit();
					return 1;
				}else {
					rollback();
					return 0;
				}
			}else {
				return 2;
			}
		} catch (Exception e) {
			rollback();
			throw e;
		}finally {
			disconnect();
		}
	}
	
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new QscheduleDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
