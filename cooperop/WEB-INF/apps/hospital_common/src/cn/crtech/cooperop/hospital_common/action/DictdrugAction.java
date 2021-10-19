package cn.crtech.cooperop.hospital_common.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.DictdrugService;
@DisLoggedIn
public class DictdrugAction extends BaseAction{
	
	//查询已经映射的
	public Result queryin(Map<String, Object> params) throws Exception {
		return new DictdrugService().query(params);
	}
	
	//查询没有被映射的标准库
	public Result querysys(Map<String, Object> params) throws Exception {
		return new DictdrugService().querysys(params);
	}
	
	//详细信息
	public Record datil(Map<String, Object> params) throws Exception {
		return new DictdrugService().get(params);
	}
	
	/** 匹配标准库药品字典到his药品字典（his药品字典中的sys_p_key代表标准库药品字典）*/
	public int save(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("drug_code"))){
			params.put("pd_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			return new DictdrugService().update(params);
		}else {
			return 0;
		}
	}
	
	public Result queryzdysc(Map<String, Object> params) throws Exception {
		return new DictdrugService().queryzdysc(params);
	}
	
	public Result queryZdyAll(Map<String, Object> params) throws Exception {
		return new DictdrugService().queryZdyAll(params);
	}
	
	public Result querynotzdysc(Map<String, Object> params) throws Exception {
		return new DictdrugService().querynotzdysc(params);
	}
	
	
	public String yjpp(Map<String, Object> params) throws Exception {
		return new DictdrugService().yjpp(params);
	}
	
	public void callinit(Map<String, Object> params) throws Exception{
		new DictdrugService().init();
	}
	
	//详细信息
	public Record getforzdy(Map<String, Object> params) throws Exception {
		return new DictdrugService().getforzdy(params);
	}
	
	//设为标准
	public void importSYS(Map<String, Object> params) throws Exception{
		new DictdrugService().importSYS(params);
	}
	
	//自动以审查操作记录
	public int updateCz(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("drug_code"))){
			params.put("zdy_cz",CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss")+" ["+user().getName()+"] "+params.get("zdy_cz"));
			return new DictdrugService().update(params);
		}else {
			return 0;
		}
	}
	
	//搜索药品
	public Map<String, Object>  search(Map<String, Object> params) throws Exception {
		return new DictdrugService().search(params);
	}
	
	//搜索已选择药品
	public Map<String, Object>  searchCheck(Map<String, Object> params) throws Exception {
		return new DictdrugService().searchCheck(params);
	}
	
	//查询药品类型
	public Map<String, Object> list(Map<String, Object> params) throws Exception {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("types", new DictdrugService().queryDrugType(params).getResultset());
		return rtnMap;
	}
	
	//清除匹配
	public void removeSYS(Map<String, Object> params) throws Exception{
		new DictdrugService().removeSYS(params);
	}
	//查询自定义规则维护记录
	public Result queryzdycz(Map<String, Object> params) throws Exception {
		return new DictdrugService().queryzdycz(params);
	}
	
	//医院药品信息列表
	public Result queryHPDrug(Map<String, Object> params) throws Exception {
		return new DictdrugService().queryHPDrug(params);
	}
	
	//医院药品信息 校验
	public int verify(Map<String, Object> params) throws Exception {
		params.put("verify_user", user().getName()+'|'+CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		return new DictdrugService().verify(params);
	}
}
