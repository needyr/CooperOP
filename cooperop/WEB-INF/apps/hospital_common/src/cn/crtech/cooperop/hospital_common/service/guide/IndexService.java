package cn.crtech.cooperop.hospital_common.service.guide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.MD5;
import cn.crtech.cooperop.hospital_common.dao.guide.Dict_flowDao;
import cn.crtech.cooperop.hospital_common.dao.guide.IndexDao;
import cn.crtech.cooperop.hospital_common.dao.verify.VerifylogDao;
import cn.crtech.cooperop.hospital_common.service.verify.VerifylogService;

public class IndexService extends BaseService {

	public Map<String, Object> index(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			Map<String, Object> map = new HashMap<String, Object>();
			Result query = new Dict_flowDao().queryBase(params);
			map.put("flows",query.getResultset());
			return map;
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}

	public List<Record> querychild(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			Dict_flowDao dict_flowDao = new Dict_flowDao();
			List<Record> resultset = dict_flowDao.querychild(params).getResultset();
			//检查是否将主流程更改为正在执行状态state=1
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", params.get("parent_id"));
			dict_flowDao.updateExecState(map);
			return resultset;
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}

	public String hasPermit(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			long count = new Dict_flowDao().getByIdPermit(params).getCount();
			return count > 0?"pass":"NO";
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}
	
	public boolean is_next(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			Dict_flowDao dict_flowDao = new Dict_flowDao();
			Record re = dict_flowDao.getByIdComplete(params);
			if(CommonFun.isNe(re)) {
				return false;
			}else {
				//可以下一步,更新下一步状态
				dict_flowDao.updateExecState(params);
			}
			return true;
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}

	public Map<String, Object> finish(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			Dict_flowDao dict_flowDao = new Dict_flowDao();
			Record byId = dict_flowDao.getById(params);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("state", true);
			if(!CommonFun.isNe(byId)) {
				boolean check = true;
				if("hospital_common.guide.flow.dict_pd".equals(byId.get("address"))) {
					List<Record> resultset = dict_flowDao.queryDictPD(params).getResultset();
					if(resultset.size() > 0) {
						check = false;
						map.put("state", false);
						map.put("re",resultset);
					}
				}
				if("hospital_common.guide.verify.index".equals(byId.get("address"))) {
					Map<String, Object> ver = new HashMap<String, Object>();
					Map<String, Object> ver2 = new HashMap<String, Object>();
					ver2.put("master_bh", 1);
					ver.put("master_bh", 1);
					ver.put("is_deal", 0);
					Result log = new VerifylogDao().query(ver2);
					if(log.getCount() <= 0) {
						check = false;
						map.put("verify",true);
						map.put("state", false);
					}
					Result queryQuestion = new VerifylogService().queryQuestion(ver);
					if(queryQuestion.getCount() > 0) {
						check = false;
						map.put("verify",true);
						map.put("state", false);
					}
				}
				if("hospital_common.guide.verify.after_index".equals(byId.get("address"))) {
					Map<String, Object> ver = new HashMap<String, Object>();
					Map<String, Object> ver2 = new HashMap<String, Object>();
					ver2.put("master_bh", 2);
					ver.put("master_bh", 2);
					ver.put("is_deal", 0);
					Result log = new VerifylogDao().query(ver2);
					if(log.getCount() <= 0) {
						check = false;
						map.put("verify",true);
						map.put("state", false);
					}
					Result queryQuestion = new VerifylogService().queryQuestion(ver);
					if(queryQuestion.getCount() > 0) {
						check = false;
						map.put("verify",true);
						map.put("state", false);
					}
				}
				if(check) {
					dict_flowDao.updateComplete(params);
				}
			}
			return map;
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}

	public List<Record> getFinishLog(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			return new Dict_flowDao().getFinishLog(params).getResultset();
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}

	public void updateOneActive(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			new Dict_flowDao().updateExecState(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}

	/**
	 * params 中必须有flow_id,info
	 * @param params
	 * @throws Exception
	 */
	public void insertLog(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			new Dict_flowDao().insertLog(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}

	public boolean initData(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			boolean re = false;
			String hospital_name = SystemConfig.getSystemConfigValue("hospital_common", "hospital_name", "");
			String hospital_id = SystemConfig.getSystemConfigValue("hospital_common", "hospital_id", "");
			String pd = MD5.md5("yckj_" + hospital_id + hospital_name);
			if(pd.equals((String)params.get("password"))) {
				new Dict_flowDao().initData(params);
				re = true;
			}else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("flow_id", params.get("flow_id"));
				map.put("error_title", "密码错误!");
				new Dict_flowDao().insertError(map);
				throw new Exception("密码错误!");
			}
			return re;
		} catch (Exception e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("flow_id", params.get("flow_id"));
			map.put("error_title", e.getMessage());
			map.put("error_msg", CommonFun.object2Json(e));
			new Dict_flowDao().insertError(map);
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public boolean initUsers(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			boolean re = false;
			Dict_flowDao dict_flowDao = new Dict_flowDao();
			dict_flowDao.initUsers(params);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("flow_id", params.get("flow_id"));
			map.put("info", "执行了一次【人员组织数据处理】操作");
			dict_flowDao.insertLog(map);
			re = true;
			return re;
		} catch (Exception e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("flow_id", params.get("flow_id"));
			map.put("error_title", e.getMessage());
			map.put("error_msg", CommonFun.object2Json(e));
			new Dict_flowDao().insertError(map);
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public boolean execOneAfterAudit(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			Dict_flowDao dict_flowDao = new Dict_flowDao();
			dict_flowDao.execOneAfterAudit(params);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("flow_id", params.get("flow_id"));
			map.put("info", "执行了一次【事后审查】操作");
			dict_flowDao.insertLog(map);
			return true;
		} catch (Exception e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("flow_id", params.get("flow_id"));
			map.put("error_title", e.getMessage());
			map.put("error_msg", CommonFun.object2Json(e));
			new Dict_flowDao().insertError(map);
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public boolean callRBFexe(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			String rbf_address = SystemConfig.getSystemConfigValue("hospital_common", "rbf_address", "");
			Runtime rt = Runtime.getRuntime();
			rt.exec(rbf_address+"/iNethinkBackup.exe");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("flow_id", params.get("flow_id"));
			map.put("info", "执行了一次【调用睿备份】操作");
			new Dict_flowDao().insertLog(map);
			return true;
		} catch (Exception e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("flow_id", params.get("flow_id"));
			map.put("error_title", e.getMessage());
			map.put("error_msg", CommonFun.object2Json(e));
			new Dict_flowDao().insertError(map);
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public boolean queryFinishAll(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			Result queryFinishAll = new Dict_flowDao().queryFinishAll(params);
			if(queryFinishAll.getCount() > 0) {
				return false;
			}else {
				return true;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Result queryFlowLog(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			return new Dict_flowDao().queryFlowLog(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Result queryFlowErrorLog(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			return new Dict_flowDao().queryFlowErrorLog(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public void reset(Map<String, Object> params) throws Exception {
		try {
			params = new HashMap<String, Object>();
			params.put("state", "0");
			connect("guide");
			IndexDao dao = new IndexDao();
			start();
			dao.resetDictflow(params);
			commit();
		} catch (Exception e) {
			rollback();
			throw e;
		} finally {
			disconnect();
		}
	}
}
