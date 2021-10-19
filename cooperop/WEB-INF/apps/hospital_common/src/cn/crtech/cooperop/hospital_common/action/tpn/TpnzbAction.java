package cn.crtech.cooperop.hospital_common.action.tpn;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.tpn.TpnzbService;


public class TpnzbAction extends BaseAction{
	//查询TPN指标
	public Result query(Map<String, Object> params) throws Exception {
		return new TpnzbService().query(params);
	}
		
	//新增TPN指标
	public int insert(Map<String, Object> params) throws Exception {
		return new TpnzbService().insert(params);
	}
	
	//插入TPN指标明细
	public int insertZbmx(Map<String, Object> params) throws Exception {
		return new TpnzbService().insertZbmx(params);
	}
	
	//修改TPN指标
	public int update(Map<String, Object> params) throws Exception {
		return new TpnzbService().update(params);
	}
	//修改指标明细
	public int updateZbmx(Map<String, Object> params) throws Exception {
		return new TpnzbService().updateZbmx(params);
	}
	public int delete(Map<String, Object> params) throws Exception {
		return new TpnzbService().delete(params);
	}
	
	public int deleteZbmx(Map<String, Object> params) throws Exception {
		return new TpnzbService().deleteZbmx(params);
	}

	//查询TPN指标明细
	public Result queryTpnzbMX(Map<String, Object> params) throws Exception {
		return new TpnzbService().queryTpnzbMX(params);
	}
	
	//修改TPN指标
	public Record edit(Map<String, Object> params) throws Exception {
		return new TpnzbService().edit(params);	
	}
	
	
	//修改TPN指标明细
		public Record zbmxEdit(Map<String, Object> params) throws Exception {
			return new TpnzbService().zbmxEdit(params);	
		}
	
	
}
