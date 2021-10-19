package cn.crtech.cooperop.hospital_common.action.auditset;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.auditset.CheckLevelService;
import cn.crtech.cooperop.hospital_common.service.auditset.CheckSortService;

public class ChecksortAction extends BaseAction {

	public Result query(Map<String, Object> params) throws Exception {
		return new CheckSortService().query(params);

	}

	public int insert(Map<String, Object> params) throws Exception {
		String user = user().getName();
		params.put("oper_user", user);
		return new CheckSortService().insert(params);

	}

	public int update(Map<String, Object> params) throws Exception {
		String user = user().getName();
		params.put("oper_user", user);
		return new CheckSortService().update(params);

	}

	public int delete(Map<String, Object> params) throws Exception {
		return new CheckSortService().delete(params);

	}

	public Record edit(Map<String, Object> params) throws Exception {
		Record re = new CheckSortService().get(params);
		Result rs = new CheckLevelService().queryAllLevel(params);
		re.put("levelList", rs.getResultset());
		return re;

	}

	public Record ipc_edit(Map<String, Object> params) throws Exception {
		Record re = new CheckSortService().get(params);
		Result rs = new CheckLevelService().queryAllLevel(params);
		re.put("levelList", rs.getResultset());
		return re;

	}

	public Record ipc_list(Map<String, Object> params) throws Exception {
		Record re = new Record();
		params.put("product_code", "ipc");
		Result rs = new CheckLevelService().queryAllLevel(params);
		re.put("levelList", CommonFun.object2Json(rs.getResultset()));
		return re;

	}

	public Record imic_list(Map<String, Object> params) throws Exception {
		Record re = new Record();
		params.put("product_code", "hospital_imic");
		Result rs = new CheckLevelService().queryAllLevel(params);
		re.put("levelList", CommonFun.object2Json(rs.getResultset()));
		return re;

	}

	@DisLoggedIn
	public Result queryListByIpc(Map<String, Object> params) throws Exception {
		return new CheckSortService().queryListByIpc(params);

	}

	public Result queryListByImic(Map<String, Object> params) throws Exception {
		return new CheckSortService().queryListByImic(params);

	}

	/* ========================自定义转自费设置====================== */

	// 进入deptcontrol.jsp页面预加载
	public Record zzfctrl(Map<String, Object> params) throws Exception {
		Record zzfre = new Record();
		List<Record> resultset = new CheckSortService().queryCtrlZzf(params).getResultset();
		if (!CommonFun.isNe(resultset)) {
			for (Record re : resultset) {
				// 1、科室dept，2、人员user，3、费用price
				if (re.get("zzf_type").toString().equals("1")) {
					zzfre.put("zzf_dept", re.get("type"));
				}

				if (re.get("zzf_type").toString().equals("2")) {
					zzfre.put("zzf_user", re.get("type"));
				}

				if (re.get("zzf_type").toString().equals("3")) {
					zzfre.put("zzf_price", re.get("type"));
				}
			}
		}

		return zzfre;
	}

	// 切换时，判断audit_rule_ctrl_zzf表中是否有该规则的数据
	// 有：则修改该audit_rule_ctrl_zzf信息；无则添加
	public int checkCtrlZzf(Map<String, Object> params) throws Exception {
		return new CheckSortService().checkCtrlZzf(params);
	}
	
	public Result queryCtrlZzf(Map<String, Object> params) throws Exception {
		return new CheckSortService().queryCtrlZzf(params);
	}

	public int insertCtrlZzf(Map<String, Object> params) throws Exception {
		return new CheckSortService().insertCtrlZzf(params);
	}

	public int updateCtrlZzf(Map<String, Object> params) throws Exception {
		return new CheckSortService().updateCtrlZzf(params);
	}

	/* ==============1.科室控制转自费================ */
	// 辅助查询字典
	public Result queryDept(Map<String, Object> params) throws Exception {
		return new CheckSortService().queryDept(params);
	}

	// 查询转自费
	public Result queryDeptZzf(Map<String, Object> params) throws Exception {
		return new CheckSortService().queryDeptZzf(params);
	}

	// 插入到转自费
	public int insertDeptCtrl(Map<String, Object> params) throws Exception {
		return new CheckSortService().insertDeptCtrl(params);
	}

	// 删除转自费
	public int delDeptZzf(Map<String, Object> params) throws Exception {
		return new CheckSortService().delDeptZzf(params);
	}

	// 批量添加
	public String insertDeptCtrlBatch(Map<String, Object> params) throws Exception {
		return new CheckSortService().insertDeptCtrlBatch(params);
	}

	/* ==============2.人员控制转自费================ */
	// 辅助查询字典
	public Result queryUser(Map<String, Object> params) throws Exception {
		return new CheckSortService().queryUser(params);
	}
	
	public Result queryDeptAll(Map<String, Object> params) throws Exception {
		return new CheckSortService().queryDeptAll(params);
	}
	// 查询转自费
	public Result queryUserZzf(Map<String, Object> params) throws Exception {
		return new CheckSortService().queryUserZzf(params);
	}

	// 插入到转自费
	public int insertUserCtrl(Map<String, Object> params) throws Exception {
		return new CheckSortService().insertUserCtrl(params);
	}

	// 删除转自费
	public int delUserZzf(Map<String, Object> params) throws Exception {
		return new CheckSortService().delUserZzf(params);
	}

	// 批量添加
	public String insertUserCtrlBatch(Map<String, Object> params) throws Exception {
		return new CheckSortService().insertUserCtrlBatch(params);
	}

	/* ==============3.费用项目控制转自费================ */
	// 辅助查询字典
	public Result queryPrice(Map<String, Object> params) throws Exception {
		return new CheckSortService().queryPrice(params);
	}
	
	public Result queryBillItemClass(Map<String, Object> params) throws Exception {
		return new CheckSortService().queryBillItemClass(params);
	}

	// 查询转自费
	public Result queryPriceZzf(Map<String, Object> params) throws Exception {
		return new CheckSortService().queryPriceZzf(params);
	}

	// 插入到转自费
	public int insertPriceCtrl(Map<String, Object> params) throws Exception {
		return new CheckSortService().insertPriceCtrl(params);
	}

	// 删除转自费
	public int delPriceZzf(Map<String, Object> params) throws Exception {
		return new CheckSortService().delPriceZzf(params);
	}

	// 批量添加
	public String insertPriceCtrlBatch(Map<String, Object> params) throws Exception {
		return new CheckSortService().insertPriceCtrlBatch(params);
	}

	/* ========================自定义转自费设置END====================== */
	

}
