package cn.crtech.cooperop.hospital_common.service.verify;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.verify.VerifylogDao;

public class VerifylogService extends BaseService{
	public String insert(Map<String, Object> params) throws Exception {
		try {
			String log_bh = CommonFun.getITEMID();
			params.put("log_bh", log_bh);
			params.put("is_abnormal", "0"); //默认没有异常
			connect("guide");
			start();
			new VerifylogDao().insert(params);
			commit();
			return log_bh;
		} catch (Exception e) {
			rollback();
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void update(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			start();
			new VerifylogDao().update(params);
			commit();
		} catch (Exception e) {
			rollback();
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result queryQuestion(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			return new VerifylogDao().queryQuestion(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result queryLogMx(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			return new VerifylogDao().queryLogMx(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result queryLog(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			return new VerifylogDao().queryLog(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	/**
	 * 
	 * @param params
	 * @return 0 未使用过校验 1未处理完毕校验不通过问题 2完成,可以进行一切操作;
	 * @throws Exception
	 */
	public int getState(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			int re = 0;
			params.put("limit", "-1");
			Result log = new VerifylogDao().query(params);
			if(log.getCount() > 0) {
				params.put("is_deal", 0);
				Result queryQuestion = new VerifylogDao().queryQuestion(params);
				if(queryQuestion.getCount() > 0) {
					re = 1;
				}else {
					re = 2;
				}
			}
			return re;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
