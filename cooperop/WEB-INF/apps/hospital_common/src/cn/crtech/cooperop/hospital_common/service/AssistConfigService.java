package cn.crtech.cooperop.hospital_common.service;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.AssistConfigDao;

public class AssistConfigService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new AssistConfigDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	//排序上移
	public int moveUp(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			start();
			Map<String, Object> params2=new HashMap<String, Object>();
			params2.put("id", params.remove("last_id"));
			AssistConfigDao icd=new AssistConfigDao();
			icd.updateSortUp(params);
			int res=icd.updateSortDown(params2);
			commit();
			return res;
		} catch (Exception e) {
			rollback();
			throw e;
		}finally {
			disconnect();
		}
	}
	
	//排序下移
	public int moveDown(Map<String, Object> params) throws Exception {
			try {
				connect("hospital_common");
				start();
				Map<String, Object> params2=new HashMap<String, Object>();
				params2.put("id", params.remove("next_id"));
				AssistConfigDao icd=new AssistConfigDao();
				icd.updateSortDown(params);
				int res=icd.updateSortUp(params2);
				commit();
				return res;
			} catch (Exception e) {
				rollback();
				throw e;
			}finally {
				disconnect();
			}
	}
	
	//修改显示/隐藏
	public int updateShow(Map<String, Object> params) throws Exception {
			try {
				connect("hospital_common");
				new AssistConfigDao().updateShow(params);
				return 0;
			} catch (Exception e) {
				throw e;
			}finally {
				disconnect();
			}
	}

	public int updateUrl(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new AssistConfigDao().updateUrl(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
