package cn.crtech.cooperop.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.core.dao.CoreDao;
import cn.crtech.cooperop.core.domain.pojo.InitRunConfig;
import cn.crtech.cooperop.core.domain.pojo.RunConfig.RunType;

/**
 * 核心业务接口数据处理服务
 * @author chenjunhong
 * 2021年1月25日
 */
public class CoreService extends BaseService{
	
	//core_interface模块或jar包或本地模块中的执行服务默认实现类
	private final String defaultServiceClass = "cn.crtech.cooperop.core.service.BlankService";
	//core_interface模块或jar包或本地模块中的执行任务默认实现类
	private final String defaultTaskClass = "cn.crtech.cooperop.core.service.BlankTask";
	
	public void connect() throws Exception {
		connect("hospital_common");
	}
	
	//加载执行服务
	public List<ExecuteService> loadExecuteService(Map<String, Object> params) {
		List<ExecuteService> services = new ArrayList<ExecuteService>();
		try {
			connect();
			Result result = new CoreDao().loadExecuteService(params);
			if(!CommonFun.isNe(result)&&result.getCount()>0) {
				Map<String, Object> args = new HashMap<String, Object>();
				ExecuteService es = null;
				String classz = null;
				for(Record re:result.getResultset()) {
					classz = re.getString("classz");
					if(CommonFun.isNe(classz)) {
						classz = defaultServiceClass;
					}
					es = getInterfaceImplObj(ExecuteService.class, classz);
					if(es!=null) {
						//1-服务初始化，考虑指定初始化的内容
						es.init(new InitRunConfig(RunType.SERVICE,re));
						args.put("service_id", re.getLong("id"));
						//2-加载服务下配置的执行任务
						es.initTasks(loadExecuteTask(args));
						services.add(es);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return services;
	}
	
	//加载执行任务
	public List<ExecuteTask> loadExecuteTask(Map<String, Object> params) {
		List<ExecuteTask> tasks = new ArrayList<ExecuteTask>();
		try {
			//connect();
			Result result = new CoreDao().loadExecuteTask(params);
			if(!CommonFun.isNe(result)&&result.getCount()>0) {
				ExecuteTask et = null;
				String classz = null;
				for(Record re:result.getResultset()) {
					classz = re.getString("classz");
					if(CommonFun.isNe(classz)) {
						classz = defaultTaskClass;
					}
					et = getInterfaceImplObj(ExecuteTask.class, classz);
					if(et!=null) {
						//1-执行任务的初始化处理
						et.init(new InitRunConfig(RunType.TASK,re));
						tasks.add(et);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//disconnect();
		}
		return tasks;
	}
	
	//反射工具 - 创建实现类
	@SuppressWarnings("unchecked")
	private <T> T getInterfaceImplObj(Class<T> clazz,String className){
		Class<?> t = null;
		Object obj = null;
		if(className!=null) {
			try {
				t = Class.forName(className);
				obj = t.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return (T)obj;
	}
	
}
