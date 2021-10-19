package cn.crtech.cooperop.hospital_common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.UseReasonDao;
import cn.crtech.precheck.EngineInterface;

public class UseReasonService extends BaseService{

	public Map<String, List<Record>> query(Map<String, Object> params) throws Exception {
		Map<String, List<Record>> map=new HashMap<String, List<Record>>();
		try {
			connect("hospital_common");
			UseReasonDao usereasondao = new UseReasonDao();
			List<Record> resultset = usereasondao.query(params).getResultset();
			List<Record> resultset2 = usereasondao.queryChild(params).getResultset();
			map.put("parent", resultset);
			map.put("child", resultset2);
			return map;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Result queryCheck(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new UseReasonDao().queryNOSelectCheck(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int insert(Map<String, Object> params) throws Exception {
		int i=0;
		try {
			connect("hospital_common");
			start();
			if (CommonFun.isNe(params.get("usereason_product_id"))) {
				i = new UseReasonDao().insertPro(params);
			}else{
				i = new UseReasonDao().insertType(params);
			}
			commit();
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int update(Map<String, Object> params) throws Exception {
		int i=0;
		try {
			connect("hospital_common");
			start();
			if (CommonFun.isNe(params.get("usereason_product_id"))) {
				i = new UseReasonDao().updatePro(params);
			}else {
				i = new UseReasonDao().updateType(params);
			}
			commit();
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if (CommonFun.isNe(params.get("usereason_product_id"))) {
				return new UseReasonDao().getPro(params);
			}
			return new UseReasonDao().getType(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	@SuppressWarnings("unused")
	public int delete(Map<String, Object> params) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		int res=0;
		try {
			connect("hospital_common");
			start();
			if (!CommonFun.isNe(params.get("usereason_product_id"))) {
				map.put("usereason_type_id", params.get("id"));
				res=new UseReasonDao().deleteType(params);
			}else {
				res=new UseReasonDao().deletePro(params);
			}
			if (!map.isEmpty()) {
				Result detail = new UseReasonDao().queryDetail(map);
				if (detail!=null) {
					int i=new UseReasonDao().deleteDetail(map);
					res+=i;
				}
			}else{
				map.put("usereason_product_id", params.get("id"));
				List<Record> list = new UseReasonDao().queryTypeAndDetail(map);
				if (!list.isEmpty()) {
					for(Record r :list){
						r.remove("rowno");
						int s=new UseReasonDao().deleteDetail(r);
						res+=s;
					}
				}
				int type = new UseReasonDao().deleteType(map);
				res+=type;
				map.remove("usereason_product_id");
			}
			commit();
			return res;
		} catch (Exception e) {
			rollback();
			throw e;
		}finally {
			disconnect();
		}
	}

	public Result queryDetail(Map<String, Object> params) throws Exception {
		try {
			if (!CommonFun.isNe(params.get("parent_id"))) {
				connect("hospital_common");
				Result result=new UseReasonDao().queryDetail(params);
				return result;
			}
			return null;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public Result queryType(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Result result=new UseReasonDao().queryChild(params);
			return result;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public Record getDetail(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new UseReasonDao().getDetail(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int insertDetail(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int i = new UseReasonDao().insertDetail(params);
			return i;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int updateDetail(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int i = new UseReasonDao().updateDetail(params);
			return i;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int deleteDetail(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int res=new UseReasonDao().deleteDetail(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public Result queryPro(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Result result = new UseReasonDao().query(params);
			return result;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public List<Map<String, Object>> queryTypeReason(Map<String, Object> params) throws Exception {
		List<Map<String, Object>> list=(List<Map<String, Object>>) params.get("apa_check_sorts_name");
		List<Map<String, Object>> result=new ArrayList<Map<String, Object>>();
		try {
			connect("hospital_common");
			Map<String, Object> check_product=new HashMap<String, Object>();
			Iterator<String> iter = EngineInterface.interfaces.keySet().iterator();
			while(iter.hasNext()) {
				String product = iter.next();
				if (CommonFun.isNe(check_product.get(product))) {
					int check = 0;
					String sort_id_count = "";
					for(int j = 0; j < list.size(); j++) {
						String name = (String)list.get(j).get("sort_id");
						String rel_product = (String)list.get(j).get("product_code");
						if(product.equals(rel_product)) {
							if(check == 0) {
								sort_id_count = "'"+name+"'";
								check++;
							}else {
								sort_id_count+= ",'"+name+"'";
							}
						}
					}
					check_product.put(product, sort_id_count);
				}
			}
			List<Record> resultset = new UseReasonDao().queryTypeReason(check_product).getResultset();
			result.addAll(resultset);
			return result;
		} finally {
			disconnect();
		}
	}
}
