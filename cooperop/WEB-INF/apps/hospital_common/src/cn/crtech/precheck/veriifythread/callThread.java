package cn.crtech.precheck.veriifythread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.verify.VerifyfieldtypeService;
import cn.crtech.cooperop.hospital_common.service.verify.VerifyitemsService;
import cn.crtech.cooperop.hospital_common.service.verify.VerifyjointService;
import cn.crtech.cooperop.hospital_common.service.verify.VerifylogService;
import cn.crtech.cooperop.hospital_common.service.verify.VerifyuniquenessService;
import cn.crtech.cooperop.hospital_common.service.verify.VerifywholenessService;

public class callThread {
	
	private Map<String, Object> thread_map = new ConcurrentHashMap<String, Object>();
	
	private volatile static callThread instance = null;
	private callThread(){}
	
	public static callThread getInstance(){
	    if(instance == null){
	        synchronized(callThread.class){
	            if(instance == null){
	                instance = new callThread();
	            }
	        }
	    }
	    return instance;
	}
	
	/**
	  *   校验开始调用
	 * @param params master_bh必须传:标识是那个校验1是数据采集后, 2是事后审查完毕后
	 * @return 
	 */
	public boolean verify(Map<String, Object> params) {
		String m_bh = (String)params.get("master_bh");
		boolean result = false;
		if (check_run(m_bh)) {
			result = true;
			new VerifyThread(m_bh,10000).start();
		}
		return result;
	}
	
	/**
	  *   进度条显示调用
	 * @param params master_bh必须传:标识是那个校验1是数据采集后, 2是事后审查完毕后
	 * @return
	 */
	public Map<String, Object> getFlowsBar(Map<String, Object> params) {
		return thread_map;
	}
	
	public boolean check_run(String id) {
		boolean re = false;
		if(CommonFun.isNe(thread_map.get("id"))) {
			re = true;
		}
		return re;
	}
	
	public class VerifyThread extends Thread{
		String id;
		int state;// 1为正在校验，0位校验结束
		long num = 0; //校验规则总数
		long finish_num = 0; //已经校验的规则总数
		
		int limit = 0;
		
		public VerifyThread(String id, int limit){
			this.id = id;
			this.limit = limit;
		}
		@Override
		public void run() {
			super.run();
			String log_bh = "";
			try {
				thread_map.put("verify_thread"+id, "1");
				if(!CommonFun.isNe(id)) {
					setName("verify_thread" + id);
					try {
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("parent_bh", id);
						Map<String, Object> log = new HashMap<String, Object>();
						log.put("state", 1);
						log.put("master_bh", id);
						state = 1; //更新状态为正在校验
						VerifylogService logService = new VerifylogService();
						log_bh = logService.insert(log); //保留校验记录
						ArrayList<Record> records = (ArrayList<Record>) new VerifyitemsService().query(params);
						num = records.size();
						if(num <= 0) {
							state = 0; //更新状态为结束校验
							log.remove("is_abnormal");
							log.put("state", 0);
							//log.put("log_bh", log_bh);
							logService.update(log);
							thread_map.put("data" + id + "_bar", 100);
						}else {
							for (int i = 0; i < records.size(); i++) {
								Record record = records.get(i);
								thread_map.put("data" + id, record.get("table_name") + ";规则id:" + record.get("id"));
								//字段类型校验,
								//如果是datetime，检查是否符合time_format
								//如果是float，检查小数点后是否是float_num位数
								if (!CommonFun.isNe(record.get("field_type"))) {
									new VerifyfieldtypeService().verifyType(record,log_bh,limit);
								}
								//是否唯一性校验
								if ("1".equals(record.get("is_unique"))) {
									new VerifyuniquenessService().uniquenessCheck(record, log_bh);
								}
								//是否完整性校验
								if ("1".equals(record.get("is_null"))) {
									new VerifywholenessService().wholeness(record, log_bh);
								}
								//是否联合校验
								if ("1".equals(record.get("is_union"))) {
									new VerifyjointService().jointCheck(record, log_bh,limit);
								}
								if(i == records.size() - 1) {
									state = 0; //更新状态为结束校验
									log.remove("is_abnormal");
									log.put("state", 0);
									logService.update(log);
								}
								finish_num++;
								thread_map.put("data" + id + "_bar", num == 0?100:(Math.rint((finish_num+0.00)/num*100)));
								//测试用, 正式确认时删除
								//Thread.sleep(10000);
							}
						}
					} catch (Exception e) {
						throw e;
					}
				}
			} catch (Exception e) {
				//TODO 增加数据库日志插入
				log.error(e);
			} finally {
				try{
					if(!CommonFun.isNe(log_bh)){
						state = 0; //更新状态为结束校验
						Map<String, Object> log = new HashMap<String, Object>();
						log.put("master_bh", id);
						log.put("state", 0);
						log.put("log_bh", log_bh);
						new VerifylogService().update(log);
					}
				}catch (Exception e){}
				thread_map.remove("verify_thread"+id);
				thread_map.remove("data" + id);
				thread_map.remove("data" + id + "_bar");
			}
		}
	}
}
