package cn.crtech.cooperop.hospital_common.service.syscustomreview;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.syscustomreview.SysSfjjzDao;

public class SysSfjjzService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysSfjjzDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result query_mx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysSfjjzDao().query_mx(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.put("apa_check_sorts_id", "43");
			int res=new SysSfjjzDao().insert(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void save_mx_all(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			SysSfjjzDao dao = new SysSfjjzDao();
			if (params.get("fdname").equals("diagnosis_CODE")) {
				params.remove("value");
				String[] codes = ((String)params.remove("valuefifter")).split(",");
				for (int i = 0; i < codes.length; i++) {
					if (params.get("formul").equals("like") || params.get("formul").equals("not like")) {
						params.put("value", "%"+codes[i]+"%");
					}else {
						params.put("value", codes[i]);
					}
					Record dia = dao.getDia(codes[i]);
					params.put("diagnosis_name", dia.get("diagnosis_name"));
					params.put("diagnosis_code", dia.get("diagnosis_code"));
					dao.insert_mx(params);
				}
			}else if (params.get("fdname").equals("diagnosis_NAME")) {
				params.remove("value");
				String[] codes = ((String)params.remove("valuefifter")).split(",");
				for (int i = 0; i < codes.length; i++) {
					Record dia = dao.getDia(codes[i]);
					params.put("diagnosis_name", dia.get("diagnosis_name"));
					params.put("diagnosis_code", dia.get("diagnosis_code"));
					if (params.get("formul").equals("like") || params.get("formul").equals("not like")) {
						params.put("value", "%"+dia.get("diagnosis_name")+"%");
					}else {
						params.put("value", dia.get("diagnosis_name"));
					}
					dao.insert_mx(params);
				}
			}
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void saveAll(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			SysSfjjzDao sf = new SysSfjjzDao();
			Map<String, Object> map = new HashMap<String, Object>();
			Object object = params.get("codes");
			if (object instanceof String[]) {
				String[] codes = (String[]) params.get("codes");
				for (int i = 0; i < codes.length; i++) {
					map.put("fdname", "diagnosis_name");
					map.put("formul", "=");
					map.put("xmid", "048");
					map.put("xmbh", "048");
					map.put("xmmch", "诊断代码");
					map.put("value", codes[i]);
					map.put("tiaojian", "or");
					map.put("parent_id", params.get("parent_id"));
					Record dia = sf.getDia(codes[i]);
					map.put("diagnosis_name", dia.get("diagnosis_name"));
					map.put("diagnosis_code", dia.get("diagnosis_code"));
					map.put("beizhu", "批量添加");
					sf.insert_mx(map);
				}
			}else {
				if (!CommonFun.isNe(params.get("codes"))) {
					String codes = (String) params.get("codes");
					map.put("fdname", "diagnosis_name");
					map.put("formul", "=");
					map.put("xmid", "048");
					map.put("xmbh", "048");
					map.put("xmmch", "诊断代码");
					map.put("value", codes);
					map.put("tiaojian", "or");
					map.put("parent_id", params.get("parent_id"));
					Record dia = sf.getDia(codes);
					map.put("diagnosis_name", dia.get("diagnosis_name"));
					map.put("diagnosis_code", dia.get("diagnosis_code"));
					map.put("beizhu", "批量添加");
					sf.insert_mx(map);
				}
			}
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int res=new SysSfjjzDao().update(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	
	public int insert_mx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int res=new SysSfjjzDao().insert_mx(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int update_mx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int res=new SysSfjjzDao().update_mx(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Record record = new Record();
			record.put("parent_id", params.get("id"));
			int res=new SysSfjjzDao().delete(params);
			int mx = new SysSfjjzDao().delete_mx(record);
			return res+mx;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int delete_mx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int mx = new SysSfjjzDao().delete_mx(params);
			return mx;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysSfjjzDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get_mx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysSfjjzDao().get_mx(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	
	public Record queryJjzZd(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Record result = new Record();
			result.put("diagnos", new SysSfjjzDao().queryJjzZd(params).getResultset());
			return result;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
