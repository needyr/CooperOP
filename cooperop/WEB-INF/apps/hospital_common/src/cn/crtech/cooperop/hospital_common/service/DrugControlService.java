package cn.crtech.cooperop.hospital_common.service;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.DrugControlDao;

public class DrugControlService extends BaseService{
	
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DrugControlDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public Result queryByDrugCode(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DrugControlDao().queryByDrugCode(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	//提示等级
	public Result queryCheckLevel(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DrugControlDao().queryCheckLevel(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	//问题类别
	public Result queryRegulation(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DrugControlDao().queryRegulation(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	
	/**
	 * @param params itemname(权限类别中文名 例：医生、科室、患者、角色) paramcode(值code)
	 * @return
	 * @throws Exception
	 * @function
	 * @author yankangkang 2019年2月25日 下午2:06:06
	 */
	public int insert(Map<String, Object> params) throws Exception {
		try {
			Map<String, Object> levelMap=new HashMap<String, Object>();
			Map<String, Object> drugMap=new HashMap<String, Object>();
			levelMap.put("levelcode", params.get("level"));
			connect("hospital_common");
			DrugControlDao controlDao = new DrugControlDao();
			start();
			//查询LevelName
			Record name = controlDao.getLevelName(levelMap);
			params.put("value_name", params.get("value").toString().replaceAll(" ", ""));
			params.put("spbh", params.get("spbh").toString().replaceAll(" ", ""));
			//condition黑白名单
			if(params.get("condition").equals("1")) {
				params.put("description", "仅("+params.get("itemname")+"："+params.get("value")+")可以使用此药品，否则将触发等级为'"+name.get("level_name")+"'的问题！");
			}else {
				params.put("description", "("+params.get("itemname")+"："+params.get("value")+")使用此药品时，将触发等级为'"+name.get("level_name")+"'的问题，并提示[该"+params.get("itemname")+params.get("message")+"！]");
			}
			params.put("value",params.get("paramcode"));
			params.remove("paramcode");
			//更新操作
			drugMap.put("DRUG_CODE", params.get("spbh"));
			drugMap.put("zdy_cz", user().getName()+"【新增"+params.get("itemname")+"用药规则】");
			controlDao.updateDrug(drugMap);
			params.remove("itemname");
			int insert = controlDao.insert(params);
			commit();
			return insert;
		} catch (Exception e) {
			rollback();
			throw e;
		}finally {
			disconnect();
		}
	}
	
	/**
	 * @param params  paramcode(值code)
	 * @return
	 * @throws Exception
	 * @function
	 * @author yankangkang 2019年2月25日 下午2:06:06
	 */
	public int update(Map<String, Object> params) throws Exception {
		try {
			Map<String, Object> levelMap=new HashMap<String, Object>();
			Map<String, Object> drugMap=new HashMap<String, Object>();
			levelMap.put("levelcode", params.get("level"));
			connect("hospital_common");
			DrugControlDao controlDao = new DrugControlDao();
			start();
			//查询LevelName
			Record name = controlDao.getLevelName(levelMap);
			params.put("value_name", params.get("value").toString().replaceAll(" ", ""));
			params.put("spbh", params.get("spbh").toString().replaceAll(" ", ""));
			if(params.get("condition").equals("1")) {
				params.put("description", "仅 ( "+params.get("itemname")+"："+params.get("value")+" ) 可以使用此药品，否则将触发等级为 ' "+name.get("level_name")+" ' 的问题！");
			}else {
				params.put("description", " ( "+params.get("itemname")+"："+params.get("value")+" ) 使用此药品时，将触发等级为 ' "+name.get("level_name")+" ' 的问题，并提示【该"+params.get("itemname")+params.get("message")+"！】");
			}
			params.put("value",params.get("paramcode"));
			params.remove("paramcode");
			//更新操作
			drugMap.put("DRUG_CODE", params.get("spbh"));
			drugMap.put("zdy_cz", user().getName()+"【修改"+params.get("itemname")+"用药规则】");
			controlDao.updateDrug(drugMap);
			params.remove("itemname");
			int update = controlDao.update(params);
			commit();
			return update;
		} catch (Exception e) {
			rollback();
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DrugControlDao().delete(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DrugControlDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
