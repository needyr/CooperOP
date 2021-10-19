package cn.crtech.cooperop.hospital_common.action.tpn;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.tpn.XiangmuwhService;
import cn.crtech.precheck.server.OptionIFSServlet;
import cn.crtech.ylz.YlzPost;

public class XiangmuwhAction extends BaseAction {
	
	public Map<String,List<Record>> edit(Map<String, Object> params) throws Throwable{
		
		List<Record> list = new XiangmuwhService().edit(params);
		HashMap<String,List<Record>> map = new HashMap<String,List<Record>>();
		map.put("list", list);
		return map;
	}
	
	public Result getXm(Map<String, Object> params) throws Throwable{
		return new XiangmuwhService().getXm(params);
	}
	
	public Result getXmValue(Map<String, Object> params) throws Throwable{
		return new XiangmuwhService().getXmValue(params);
	}
	
	/**
	 * 删除项目
	 */
	public Integer deleteXm(Map<String, Object> params) throws Throwable{
		Integer i = new XiangmuwhService().deleteXm(params);
		return i;
	}
	/**
	 * 删除项目值
	 */
	public Integer deleteXmValue(Map<String, Object> params) throws Throwable{
		Integer i = new XiangmuwhService().deleteXmValue(params);
		return i;
	}
	/**
	 * 新增项目值
	 */
	public Integer addXmValue(Map<String, Object> params) throws Throwable{
		return new XiangmuwhService().addXmValue(params);
	}
	/**
	 * 新增项目
	 */
	public Integer addXm(Map<String, Object> params) throws Throwable{
		return new XiangmuwhService().addXm(params);
	}
	/**
	 * 新增类别
	 */
	public Integer addClass(Map<String, Object> params) throws Throwable{
		return new XiangmuwhService().addClass(params);
	}
	/**
	 * 删除类别
	 */
	public Integer deleteClass(Map<String, Object> params) throws Throwable{
		return new XiangmuwhService().deleteClass(params);
	}

	public void init_tpn(Map<String, Object> params) throws Throwable{
		if(OptionIFSServlet.final_control == 4) {
			String YC_AUDIT_ENGINE = SystemConfig.getSystemConfigValue("hospital_common", "yc.audit.engine.initmemorycache", "http://127.0.0.1:9272/initmemorycache");
			try {
				YlzPost.post(YC_AUDIT_ENGINE, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			Method m = Class.forName("cn.crtech.precheck.ipc.audit_def.MemoryCache").getMethod("init");
			if(m!=null) {
				m.invoke(null);
			}
		}
	}
}
