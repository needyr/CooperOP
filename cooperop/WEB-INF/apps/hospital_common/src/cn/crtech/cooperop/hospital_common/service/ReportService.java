package cn.crtech.cooperop.hospital_common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.ReportDao;

public class ReportService extends BaseService{

	public Result queryKjyOutpUse(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.putAll(CommonFun.json2Object((String)params.remove("json"), Map.class));
			if(!CommonFun.isNe(params.get("layui_page"))) {
				int parseInt = Integer.parseInt((String)params.get("layui_page"));
				int limit = Integer.parseInt((String)params.get("limit"));
				params.put("start", limit*(parseInt-1) + 1);
			}
			Object object = params.get("incision_type");
			if(!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof String[]) {
				params.put("incision_type", getString((String[]) params.get("incision_type")));
			}else if (!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof ArrayList) {
				params.put("incision_type", listToString((ArrayList) params.get("incision_type")));
			}
			Result queryKjyOutpUse = new ReportDao().queryKjyOutpUse(params);
			List<Record> resultset = queryKjyOutpUse.getResultset();
			List<Record> list = new ArrayList<Record>();
			for (Record record2 : resultset) {
				Object cf = record2.get("cf");
				if(CommonFun.isNe(cf)) {
					list.add(record2);
				}else {
					String[] orders_list = ((String) cf).split("\n");
					for (int j = 0; j < orders_list.length; j++) {
						if(!CommonFun.isNe(orders_list[j])) {
							String[] order = ((String) orders_list[j]).split("~");
							if(order.length > 0) {
								record2.put("drug_name", order[0]);
								record2.put("druggg", order[1]);
								record2.put("jixing", order[2]);
								record2.put("dosages", order[3]);
								record2.put("administration", order[4]);
								record2.put("frequency", order[5]);
								record2.put("start_date_time", order[6]);
								record2.put("stop_date_time", order[7]);
								record2.put("yyts", order[8]);
								record2.put("purpose", order[9]);
							}
						}
						list.add(record2);
					}
				}

			}
			queryKjyOutpUse.setResultset(list);
			return queryKjyOutpUse;
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}

	public Result querykjyoper(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof String[]) {
				params.put("incision_type", getString((String[]) params.get("incision_type")));
			}else if (!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof ArrayList) {
				params.put("incision_type", listToString((ArrayList) params.get("incision_type")));
			}
			return new ReportDao().querykjyoper(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}

	public Result querykjyoper_dept(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof String[]) {
				params.put("incision_type", getString((String[]) params.get("incision_type")));
			}else if (!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof ArrayList) {
				params.put("incision_type", listToString((ArrayList) params.get("incision_type")));
			}
			return new ReportDao().querykjyoper_dept(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}

	public Result querykjyoper_doctor(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof String[]) {
				params.put("incision_type", getString((String[]) params.get("incision_type")));
			}else if (!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof ArrayList) {
				params.put("incision_type", listToString((ArrayList) params.get("incision_type")));
			}
			return new ReportDao().querykjyoper_doctor(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}

	public Result querykjymj(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.putAll(CommonFun.json2Object((String)params.remove("json"), Map.class));
			if(!CommonFun.isNe(params.get("layui_page"))) {
				int parseInt = Integer.parseInt((String)params.get("layui_page"));
				int limit = Integer.parseInt((String)params.get("limit"));
				params.put("start", limit*(parseInt-1) + 1);
			}
			if(!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof String[]) {
				params.put("incision_type", getString((String[]) params.get("incision_type")));
			}else if (!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof ArrayList) {
				params.put("incision_type", listToString((ArrayList) params.get("incision_type")));
			}
			Result querykjymj = new ReportDao().querykjymj(params);
			List<Record> resultset = querykjymj.getResultset();
			List<Record> list = new ArrayList<Record>();
			for (Record record2 : resultset) {
				Object cf = record2.get("cf");
				if(CommonFun.isNe(cf)) {
					list.add(record2);
				}else {
					String[] orders_list = ((String) cf).split("\n");
					for (int j = 0; j < orders_list.length; j++) {
						if(!CommonFun.isNe(orders_list[j])) {
							String[] order = ((String) orders_list[j]).split("~");
							if(order.length > 0) {
								record2.put("drug_name", order[0]);
								record2.put("druggg", order[1]);
								record2.put("jixing", order[2]);
								record2.put("shl", order[3]);
								//record2.put("order_money", 0);
								record2.put("dosages", order[4]);
								record2.put("administration", order[5]);
								record2.put("frequency", order[6]);
								record2.put("purpose", order[7]);
							}
						}
						list.add(record2);
					}
				}

			}
			querykjymj.setResultset(list);
			return querykjymj;
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}

	public Result querykjyoutpuse_dept(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof String[]) {
				params.put("incision_type", getString((String[]) params.get("incision_type")));
			}else if (!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof ArrayList) {
				params.put("incision_type", listToString((ArrayList) params.get("incision_type")));
			}
			return new ReportDao().querykjyoutpuse_dept(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}

	public Result querykjymj_dept(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof String[]) {
				params.put("incision_type", getString((String[]) params.get("incision_type")));
			}else if (!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof ArrayList) {
				params.put("incision_type", listToString((ArrayList) params.get("incision_type")));
			}
			return new ReportDao().querykjymj_dept(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}

	public Result querykjyoutpuse_doctor(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof String[]) {
				params.put("incision_type", getString((String[]) params.get("incision_type")));
			}else if (!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof ArrayList) {
				params.put("incision_type", listToString((ArrayList) params.get("incision_type")));
			}
			return new ReportDao().querykjyoutpuse_doctor(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}

	public Result querykjyomj_doctor(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof String[]) {
				params.put("incision_type", getString((String[]) params.get("incision_type")));
			}else if (!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof ArrayList) {
				params.put("incision_type", listToString((ArrayList) params.get("incision_type")));
			}
			return new ReportDao().querykjyomj_doctor(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}

	public Result query_dui(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof String[]) {
				params.put("incision_type", getString((String[]) params.get("incision_type")));
			}else if (!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof ArrayList) {
				params.put("incision_type", listToString((ArrayList) params.get("incision_type")));
			}
			if(!CommonFun.isNe(params.get("drug_type")) && params.get("drug_type") instanceof String[]) {
				params.put("drug_type", getString((String[]) params.get("drug_type")));
			}else if (!CommonFun.isNe(params.get("drug_type")) && params.get("drug_type") instanceof ArrayList) {
				params.put("drug_type", listToString((ArrayList) params.get("drug_type")));
			}
			if(!CommonFun.isNe(params.get("kjy_drug_level")) && params.get("kjy_drug_level") instanceof String[]) {
				params.put("kjy_drug_level", getString((String[]) params.get("kjy_drug_level")));
			}else if (!CommonFun.isNe(params.get("kjy_drug_level")) && params.get("kjy_drug_level") instanceof ArrayList) {
				params.put("kjy_drug_level", listToString((ArrayList) params.get("kjy_drug_level")));
			}
			return new ReportDao().query_dui(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}

	public Result query_dti(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof String[]) {
				params.put("incision_type", getString((String[]) params.get("incision_type")));
			}else if (!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof ArrayList) {
				params.put("incision_type", listToString((ArrayList) params.get("incision_type")));
			}
			if(!CommonFun.isNe(params.get("drug_type")) && params.get("drug_type") instanceof String[]) {
				params.put("drug_type", getString((String[]) params.get("drug_type")));
			}else if (!CommonFun.isNe(params.get("drug_type")) && params.get("drug_type") instanceof ArrayList) {
				params.put("drug_type", listToString((ArrayList) params.get("drug_type")));
			}
			if(!CommonFun.isNe(params.get("kjy_drug_level")) && params.get("kjy_drug_level") instanceof String[]) {
				params.put("kjy_drug_level", getString((String[]) params.get("kjy_drug_level")));
			}else if (!CommonFun.isNe(params.get("kjy_drug_level")) && params.get("kjy_drug_level") instanceof ArrayList) {
				params.put("kjy_drug_level", listToString((ArrayList) params.get("kjy_drug_level")));
			}
			return new ReportDao().query_dti(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}

	public String getString(String[] str) {
		String new_str = "";
		for (int i=0;i<str.length;i++) {
			if(i == 0) {
				new_str = str[i];
			}else {
				new_str = new_str + "," + str[i];
			}
		}
		return new_str;
	}

	public String listToString(ArrayList str) {
		String new_str = "";
		for (int i=0;i<str.size();i++) {
			if(i == 0) {
				new_str = (String)str.get(i);
			}else {
				new_str = new_str + "," + str.get(i);
			}
		}
		return new_str;
	}
}
