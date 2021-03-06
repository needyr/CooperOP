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

	/* ========================????????????????????????====================== */

	// ??????deptcontrol.jsp???????????????
	public Record zzfctrl(Map<String, Object> params) throws Exception {
		Record zzfre = new Record();
		List<Record> resultset = new CheckSortService().queryCtrlZzf(params).getResultset();
		if (!CommonFun.isNe(resultset)) {
			for (Record re : resultset) {
				// 1?????????dept???2?????????user???3?????????price
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

	// ??????????????????audit_rule_ctrl_zzf?????????????????????????????????
	// ??????????????????audit_rule_ctrl_zzf?????????????????????
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

	/* ==============1.?????????????????????================ */
	// ??????????????????
	public Result queryDept(Map<String, Object> params) throws Exception {
		return new CheckSortService().queryDept(params);
	}

	// ???????????????
	public Result queryDeptZzf(Map<String, Object> params) throws Exception {
		return new CheckSortService().queryDeptZzf(params);
	}

	// ??????????????????
	public int insertDeptCtrl(Map<String, Object> params) throws Exception {
		return new CheckSortService().insertDeptCtrl(params);
	}

	// ???????????????
	public int delDeptZzf(Map<String, Object> params) throws Exception {
		return new CheckSortService().delDeptZzf(params);
	}

	// ????????????
	public String insertDeptCtrlBatch(Map<String, Object> params) throws Exception {
		return new CheckSortService().insertDeptCtrlBatch(params);
	}

	/* ==============2.?????????????????????================ */
	// ??????????????????
	public Result queryUser(Map<String, Object> params) throws Exception {
		return new CheckSortService().queryUser(params);
	}
	
	public Result queryDeptAll(Map<String, Object> params) throws Exception {
		return new CheckSortService().queryDeptAll(params);
	}
	// ???????????????
	public Result queryUserZzf(Map<String, Object> params) throws Exception {
		return new CheckSortService().queryUserZzf(params);
	}

	// ??????????????????
	public int insertUserCtrl(Map<String, Object> params) throws Exception {
		return new CheckSortService().insertUserCtrl(params);
	}

	// ???????????????
	public int delUserZzf(Map<String, Object> params) throws Exception {
		return new CheckSortService().delUserZzf(params);
	}

	// ????????????
	public String insertUserCtrlBatch(Map<String, Object> params) throws Exception {
		return new CheckSortService().insertUserCtrlBatch(params);
	}

	/* ==============3.???????????????????????????================ */
	// ??????????????????
	public Result queryPrice(Map<String, Object> params) throws Exception {
		return new CheckSortService().queryPrice(params);
	}
	
	public Result queryBillItemClass(Map<String, Object> params) throws Exception {
		return new CheckSortService().queryBillItemClass(params);
	}

	// ???????????????
	public Result queryPriceZzf(Map<String, Object> params) throws Exception {
		return new CheckSortService().queryPriceZzf(params);
	}

	// ??????????????????
	public int insertPriceCtrl(Map<String, Object> params) throws Exception {
		return new CheckSortService().insertPriceCtrl(params);
	}

	// ???????????????
	public int delPriceZzf(Map<String, Object> params) throws Exception {
		return new CheckSortService().delPriceZzf(params);
	}

	// ????????????
	public String insertPriceCtrlBatch(Map<String, Object> params) throws Exception {
		return new CheckSortService().insertPriceCtrlBatch(params);
	}

	/* ========================????????????????????????END====================== */
	

}
