package cn.crtech.precheck.ipc.thirdparty;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.ipc.dao.AutoAuditDao;
import cn.crtech.cooperop.ipc.dao.CheckResultInfoDao;
import cn.crtech.precheck.ipc.dao.DataDao;
import cn.crtech.precheck.ipc.dao.ThirdpartyDataDao;

public class AuditResultService extends BaseService {
	public String audit_hlyy(String audit_flag, Map<String, Object> map) throws Exception {
		try {
			connect("autopa_thirdparty");
        	Map<String, Object> idmap = new HashMap<String, Object>();
 	        idmap.put("auto_audit_id", map.get("auto_audit_id"));
			String zdy_result = "HL_Y";//合理用药最终结果
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("id", map.get("auto_audit_id"));
			AutoAuditDao autos = new AutoAuditDao();
			//TODO 注意:需要更改check_result_proc is_new都为1
			autos.execFilterNotmind(idmap);//删除智能审查中被自定义审查设置为合理的问题
			//审查问题集合
			Result result = new CheckResultInfoDao().queryByAutoID(map);
			if(!CommonFun.isNe(result)) {
				List<Record> queryByAutoID = result.getResultset();
				for (Record record : queryByAutoID) {
					String state = (String)record.get("check_result_state");
					if("B".equals(state)) {
						zdy_result="HL_B";
						break;
					}else if("N".equals(state)) {
						zdy_result="HL_N";
					}else if("T".equals(state) && zdy_result.equals("HL_Y")) {
						zdy_result="HL_T";
					}
					
				}
			}
			//audit_flag为Y表示正常审查结束，zdy_result为Y表示审查结果为通过的医嘱/处方
			if("Y".equals(audit_flag) || !"HL_Y".equals(zdy_result)){
				//审查通过，或者审查超时但有一种审查没有超时并有返回不通过意见的情况
				p.put("state", zdy_result);
				p.put("sys_audit_result", zdy_result);
			}else{
				p.put("state", audit_flag);
				p.put("sys_audit_result", audit_flag);
			}
			p.put("auto_audit_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
			autos.update(p);
			return zdy_result;
		}catch(Exception e){
			throw e;
		}finally {
			disconnect();
		}
	}

	public Map<String, Object> orgResultsToMap(Map<String, Object> map) throws Exception {
		try {
			connect("autopa_thirdparty");
        	Map<String, Object> re = new HashMap<String, Object>();
        	ThirdpartyDataDao dao = new ThirdpartyDataDao();
        	List<Record> resultset = dao.orgResultsToMap(map).getResultset();
        	re.put("results", resultset);
        	if(resultset.size() <= 0) {
        		re.put("status", "pass");
        	}else {
        		re.put("status", "success");
        	}
			return re;
		}catch(Exception e){
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public String audit_zdy(Map<String, Object> map, String proc) throws Exception {
		try {
			connect("autopa_thirdparty");
			DataDao  datadao= new DataDao();
			Record done = datadao.audit_zdy_proc(map, proc);
			return "Y";
		}catch(Exception e){
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int audit_sys_procBefore(Map<String, Object> map) throws Exception {
		try {
			connect("autopa_thirdparty");
			return new DataDao().audit_sys_procBefore(map);
		}catch(Exception e){
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int audit_zdy_procBefore(Map<String, Object> map) throws Exception {
		try {
			connect("autopa_thirdparty");
			return new DataDao().audit_zdy_procBefore(map);
		}catch(Exception e){
			throw e;
		}finally {
			disconnect();
		}
	}
}
