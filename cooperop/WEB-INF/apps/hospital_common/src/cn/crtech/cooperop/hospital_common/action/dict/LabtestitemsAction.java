package cn.crtech.cooperop.hospital_common.action.dict;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.dict.LabtestitemsService;

public class LabtestitemsAction extends BaseDao {

	// 查询
	public Result query(Map<String, Object> params) throws Exception {
		return new LabtestitemsService().query(params);
	}
	
	//自动补全项目信息
	public Result queryLabReportItem(Map<String, Object> params) throws Exception {
		return new LabtestitemsService().queryLabReportItem(params);
	}
	

	// 新增
	public int insert(Map<String, Object> params) throws Exception {
		return new LabtestitemsService().insert(params);
	}

	// 修改
	public int update(Map<String, Object> params) throws Exception {
		return new LabtestitemsService().update(params);
	}

	// 删除
	public int delete(Map<String, Object> params) throws Exception {
		return new LabtestitemsService().delete(params);
	}

	// 进入edit时，预加载
	public Record edit(Map<String, Object> params) throws Exception {
		if (CommonFun.isNe(params.get("pkey_id"))) {
			return null;
		}
		return new LabtestitemsService().get(params);
	}

}
