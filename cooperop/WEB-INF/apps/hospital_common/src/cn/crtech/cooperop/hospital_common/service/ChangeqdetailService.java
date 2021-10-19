package cn.crtech.cooperop.hospital_common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.ChangeqdetailDao;

public class ChangeqdetailService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			if(!CommonFun.isNe(params.get("level"))) {
				if(params.get("level") instanceof java.lang.String) {
					String [] st = new String [] {(String) params.get("level")};
					params.put("level", st);
				}
			}
			return new ChangeqdetailDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result query_init_excel(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			params.putAll(CommonFun.json2Object((String)params.remove("json"), Map.class));
			params.put("user_no", user().getNo());
			if(!CommonFun.isNe(params.get("level"))) {
				if(params.get("level") instanceof java.lang.String) {
					String [] st = new String [] {(String) params.get("level")};
					params.put("level", st);
				}
			}
			if(!CommonFun.isNe(params.get("layui_page"))) {
				int parseInt = Integer.parseInt((String)params.get("layui_page"));
				int limit = Integer.parseInt((String)params.get("limit"));
				params.put("start", limit*(parseInt-1) + 1);
			}
			Result query_init_new = new ChangeqdetailDao().query_init_excel(params);
			List<Record> resultset = query_init_new.getResultset();
			List<Record> list = new ArrayList<Record>();
			for (Record record : resultset) {
				Object ordermessage = record.get("ordermessage");
				if(CommonFun.isNe(ordermessage)) {
					list.add(record);
				}else {
					String[] orders = ((String) ordermessage).split(",");
					for (String order : orders) {
						int indexOf = order.indexOf("[");
						if(indexOf > -1) {
							String substring = order.substring(0, indexOf);
							String[] drug_dd = substring.split(" ");
							String drug_name = "";
							String drug_tl = "";
							int lastIndexOf = order.lastIndexOf(" ");
							if(lastIndexOf > -1) {
								drug_name = order.substring(indexOf, lastIndexOf);
								drug_tl = order.substring(lastIndexOf, order.length());
							}
							record.put("drug_name", drug_name);
							record.put("routename", drug_dd[0]);
							record.put("dosage", drug_tl.replace("\\]", ""));
							record.put("freq", drug_dd[1]);
						}
						list.add(record);
					}
					
				}
			}
			query_init_new.setResultset(list);
			return query_init_new;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public void init(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			params.put("user_no",user().getNo());
			new ChangeqdetailDao().init(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public Result query_init(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			if(!CommonFun.isNe(params.get("level"))) {
				if(params.get("level") instanceof java.lang.String) {
					String [] st = new String [] {(String) params.get("level")};
					params.put("level", st);
				}
			}
			params.put("user_no", user().getNo());
			return new ChangeqdetailDao().query_init(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public void updateTmp(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Object object = params.get("cz");
			if ("1".equals(object)) {
				new ChangeqdetailDao().updateTmpIsAdvice(params);
			}else if("0".equals(object)) {
				new ChangeqdetailDao().updateTmpIsPass(params);
			}else if("2".equals(object)) {
				new ChangeqdetailDao().updateTmpAll(params);
			}
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
