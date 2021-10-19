package cn.crtech.cooperop.hospital_common.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.DictdrugDao;
import cn.crtech.cooperop.hospital_common.dao.SfAuditPassDao;
import cn.crtech.cooperop.hospital_common.dao.SfCCRDao;
import cn.crtech.cooperop.hospital_common.dao.SfLabTestDao;
import cn.crtech.cooperop.hospital_common.dao.SfPLDao;
import cn.crtech.cooperop.hospital_common.dao.SfPeiwDao;
import cn.crtech.cooperop.hospital_common.dao.SfRongmDao;
import cn.crtech.cooperop.hospital_common.dao.SfRouteDao;
import cn.crtech.cooperop.hospital_common.dao.SfSyzDao;
import cn.crtech.cooperop.hospital_common.dao.SfXhzyDao;
import cn.crtech.cooperop.hospital_common.dao.SfYonglChildDao;
import cn.crtech.cooperop.hospital_common.dao.SfYonglDao;
import cn.crtech.cooperop.hospital_common.dao.SfjjzDao;
import cn.crtech.cooperop.hospital_common.dao.auditset.CheckLevelDao;

public class DictdrugService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictdrugDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result querysys(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictdrugDao().querysys(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictdrugDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record getDrugByCode(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictdrugDao().getDrugByCode(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int res=new DictdrugDao().insert(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int verify(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int res=new DictdrugDao().verify(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int update(Map<String, Object> params) throws Exception {
	    try {
	    	connect("hospital_common");
			int res=new DictdrugDao().update(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result queryzdysc(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictdrugDao().queryzdysc(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result queryZdyAll(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Result result = new Result();
			if(!CommonFun.isNe(params.get("hasset"))) {
				if (params.get("hasset") instanceof String[]) {
					result = new DictdrugDao().queryZdyAll(params);
				}else {
					String hasset  = (String) params.get("hasset");
					if (hasset.equals("sfpeiw")) {
						params.put("table_name", "sfpeiw");
						return new SfPeiwDao().queryLook(params);
					}
					if (hasset.equals("sfrongm")) {
						params.put("table_name", "sfrongm");
						return new SfRongmDao().queryLook(params);
					}
					if (hasset.equals("sfyongl")) {
						params.put("table_name", "sfyongl");
						return new SfYonglDao().queryLook(params);
					}
					if (hasset.equals("sfyonglchild")) {
						params.put("table_name", "sfyonglchild");
						return new SfYonglChildDao().queryLook(params);
					}
					if (hasset.equals("sfroute")) {
						params.put("table_name", "sfroute");
						return new SfRouteDao().queryLook(params);
					}
					if (hasset.equals("sfpl")) {
						params.put("table_name", "sfpl");
						return new SfPLDao().queryLook(params);
					}
					if (hasset.equals("sfauditpass")) {
						params.put("table_name", "sfauditpass");
						return new SfAuditPassDao().queryLook(params);
					}
					if (hasset.equals("sfccr")) {
						params.put("table_name", "sfccr");
						return new SfCCRDao().queryLook(params);
					}
					if (hasset.equals("sfjjz")) {
						params.put("table_name", "sfjjz");
						List<Record> resultset = new SfjjzDao().queryLook(params).getResultset();
						for (Record record : resultset) {
							result.addRecord(record);
						}
					}
					if (hasset.equals("sfsyz")) {
						params.put("table_name", "sfsyz");
						return new SfSyzDao().queryLook(params);
					}
					if (hasset.equals("sfxhzy")) {
						params.put("table_name", "sfxhzy");
						return new SfXhzyDao().queryLook(params);
					}
					if (hasset.equals("sflabtest")) {
						params.put("table_name", "sflabtest");
						return new SfLabTestDao().queryLook(params);
					}
				}
			}
			return result;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result querynotzdysc(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictdrugDao().querynotzdysc(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	//一键匹配
	public String yjpp(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			//1.进行国药准字匹配
			String res=new DictdrugDao().matchgyzz(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void init() throws Exception {
		try {
			connect("hospital_common");
			//2.查询未匹配药品top100，并写入中间表（存储过程完成）
			new DictdrugDao().calltop100(new HashMap<String, Object>());
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record getforzdy(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictdrugDao().getforzdy(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public String importSYS(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> map = new DictdrugDao().importSYS(params);
			map.put("pd_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			new DictdrugDao().update(map);
			return "1";
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public Map<String, Object> search(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("drug", new DictdrugDao().search(params).getResultset());
			return result;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public Map<String, Object> searchCheck(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("drug", new DictdrugDao().searchCheck(params).getResultset());
			return result;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result queryDrugType(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictdrugDao().queryDrugType(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int removeSYS(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			start();
			Record map = new Record();
			DictdrugDao dictdrugDao = new DictdrugDao();
			map.putAll(params);
			int i = dictdrugDao.update(map);
			dictdrugDao.deleteStandard(params); 
			commit();
			return i;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result queryzdycz(Map<String, Object> params) throws Exception {
		try {
	    	connect("hospital_common");
			return new DictdrugDao().queryzdycz(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record cz_all_mx(Map<String, Object> params) throws Exception {
		try {
	    	connect("hospital_common");
	    	Record zdyMx = new DictdrugDao().getZdyMx(params);
	    	String level=(String) zdyMx.get("sys_check_level");
	    	if(CommonFun.isNe(level)) {
	    		return zdyMx;
	    	}
	    	String isolate = SystemConfig.getSystemConfigValue("hospital_common", "zdy_cz_change_isolate", "【改为】");
	    	String[] strArray=level.split(isolate);
	    	String result="";
	    	int len=strArray.length;
	    	Map<String,Object> map=new HashMap<String, Object>();
	    	CheckLevelDao CheckLevelDao=new CheckLevelDao();
	    	map.put("product_code",ProductmanagetService.IPC);
	    	if(len==1) {
	    		map.put("level_code",strArray[0]);
	    		Record rec=CheckLevelDao.getByCode(map);
	    		if(!CommonFun.isNe(rec)) {
		    		result=(String) rec.get("level_name");
		    	}
	    		zdyMx.put("sys_check_level",result);
	    	}
	    	else if(len==2) {
		    	for(int i=0;i<len;i++) {
		    		Record re=null;
			    	map.put("level_code",strArray[i]);
			    	re=CheckLevelDao.getByCode(map);
			    	if(!CommonFun.isNe(re)) {
			    		result+=(String) re.get("level_name");
			    	}
			    	if(i==0) {
			    		result+=isolate;
			    	}
			    	zdyMx.put("sys_check_level",result);
		    	}
	    	}
	    	else {
	    		return zdyMx;
	    	}
			return zdyMx;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result queryHPDrug(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictdrugDao().queryHPDrug(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record getUnit(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictdrugDao().getUnit(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
