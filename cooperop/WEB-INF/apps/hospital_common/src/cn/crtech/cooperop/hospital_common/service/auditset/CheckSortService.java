package cn.crtech.cooperop.hospital_common.service.auditset;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.auditset.CheckSortDao;

public class CheckSortService extends BaseService {

	/**
	 * 
	 * @author wangsen 
	 * @date 2018年12月10日
	 * @param params Map   product_code ？？？ 可有
	 * @return  Result 
	 * @function 查询审查规则list
	 */
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().query(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().insert(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}

	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().update(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	

	/*public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int delete = new CheckSortDao().delete(params);		
			return delete;					
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}*/
	
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			start();
			CheckSortDao sortDao = new CheckSortDao();
			int delete = sortDao.delete(params);
			if (params.get("is_zzf").toString().equals("2")) {
				sortDao.delCtrlZzfBySortId(params);
				sortDao.delDeptZzfBySortId(params);
				sortDao.delUserZzfBySortId(params);
				sortDao.delPriceZzfBySortId(params);
			}
			commit();
			return delete;					
		} catch (Exception e) {
			rollback();
			throw e;
		} finally {
			disconnect();
		}
	}
	
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(CommonFun.isNe(params.get("p_key"))){
				return new CheckSortDao().getSortNumMax(params);
			}
			return new CheckSortDao().get(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}
	
	public Result queryListByIpc(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().queryListByIpc(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Result queryListByImic(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().queryListByImic(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	
	
	/*========================自定义转自费设置======================*/
	
	public Result queryCtrlZzf(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().queryCtrlZzf(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	
	public int checkCtrlZzf(Map<String, Object> params) throws Exception {
		try {
			 connect("hospital_common");
			 CheckSortDao sortDao = new CheckSortDao();
			 Record checkCtrlZzf = sortDao.checkCtrlZzf(params);
			//不为空就修改，为空就添加
			 if (!CommonFun.isNe(checkCtrlZzf)) {
				 Record re=new Record();
				 re.put("id", checkCtrlZzf.get("id").toString());
				 re.put("type", params.get("type"));
				  return sortDao.updateCtrlZzf(re);
			}else{
				 return sortDao.insertCtrlZzf(params);
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
		
	}
	
	/*==============1.科室控制转自费================*/
	public Result queryDept(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().queryDept(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Result queryDeptZzf(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().queryDeptZzf(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public int insertDeptCtrl(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().insertDeptCtrl(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public int delDeptZzf(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().delDeptZzf(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	
	public String insertDeptCtrlBatch(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().insertDeptCtrlBatch(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	
	/*==============2.人员控制转自费================*/
	public Result queryUser(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().queryUser(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	
	public Result queryDeptAll(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().queryDeptAll(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Result queryUserZzf(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().queryUserZzf(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public int insertUserCtrl(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().insertUserCtrl(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public int delUserZzf(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().delUserZzf(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	
	public String insertUserCtrlBatch(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().insertUserCtrlBatch(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	
	
	/*==============3.费用项目控制转自费================*/
	public Result queryPrice(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().queryPrice(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Result queryBillItemClass(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().queryBillItemClass(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Result queryPriceZzf(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().queryPriceZzf(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public int insertPriceCtrl(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().insertPriceCtrl(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public int delPriceZzf(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().delPriceZzf(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	
	public String insertPriceCtrlBatch(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().insertPriceCtrlBatch(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	

	
	
	
	public int insertCtrlZzf(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().insertCtrlZzf(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

	public int updateCtrlZzf(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().updateCtrlZzf(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	
	
	public Record getBySortId(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckSortDao().getBySortId(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
}






