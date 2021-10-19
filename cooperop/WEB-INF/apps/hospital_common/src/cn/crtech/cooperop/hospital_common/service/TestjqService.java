package cn.crtech.cooperop.hospital_common.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.HttpClient;
import cn.crtech.cooperop.hospital_common.dao.TestjqDao;
import cn.crtech.precheck.EngineInterface;

public class TestjqService extends BaseService {

	public Result query(Map<String, Object> params)  throws Exception {
		try {
			connect("hospital_common");
			return new TestjqDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Map<String, Object> jsexesql(Map<String, Object> params)  throws Exception {
		try {
			connect("hospital_common");
			params.put("data", new TestjqDao().jsexesql(params).getResultset());
			return params;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	} 
	
	public void insert(Map<String, Object> params)  throws Exception {
		try {
			connect("hospital_common");
			new TestjqDao().insert(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	} 
	public void auto_audit(Map<String, Object> params)  throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String bf_num = CommonFun.isNe(params.get("bf_num"))?"10":(String)params.get("bf_num");
		int bf_num1 = Integer.parseInt(bf_num);
		try {
			connect("hospital_common");
			Result result = new TestjqDao().executeQuery((String)params.get("source_sql"));
			TestjqDao testjqDao = new TestjqDao();
			Result result2 = testjqDao.executeQuery("select * from test_orders where order_class='5'");
			disconnect();
			if (result!=null) {
				for (Map<String, Object> resultmap : result.getResultset()) {
					Map<String, Object> json = new HashMap<String, Object>();
					Map<String, Object> request = new HashMap<String, Object>();
					Map<String, Object> patient = new HashMap<String, Object>();
					Map<String, Object> doctor = new HashMap<String, Object>();
					List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
					Map<String, Object> r = new HashMap<String, Object>();
					Map<String, Object> map = new HashMap<String, Object>();
					resultmap.put("doctor", ((String)resultmap.remove("doctor")).trim());
					doctor.put("code", resultmap.get("doctor_no"));
					doctor.put("name", resultmap.get("doctor"));
					doctor.put("duty", "主治医师");
					doctor.put("departcode", "1962");
					doctor.put("departname", "测试科室");
					patient.put("id", resultmap.get("patient_id"));
					patient.put("visitid", resultmap.get("visit_id"));
					patient.put("code", "");
					patient.put("name", "");
					patient.put("chargetype", "");
					patient.put("sex", "");
					patient.put("departcode", "1962");
					patient.put("departname", "测试科室");
					patient.put("admissiondate", "");
					patient.put("idcardno", "");
					patient.put("bedno", "");
					patient.put("type", "");
					patient.put("status", "");
					patient.put("birthday", "");
					patient.put("weight", "");
					if(result2 == null || result2.getResultset().size()==0){
						continue;
					}
					for (Map<String, Object> resultmap2 : result2.getResultset()) {
						if(resultmap2.get("patient_id").equals(resultmap.get("patient_id"))
								&& resultmap2.get("visit_id").equals(resultmap.get("visit_id"))
								&& resultmap2.get("doctor_no").equals(resultmap.get("doctor_no"))){
							Map<String, Object> row = new HashMap<String, Object>();
							row.put("p_key", resultmap2.get("p_key"));
							row.put("repeat_indicator", resultmap2.get("repeat_indicator"));
							row.put("start_date_time", resultmap2.get("start_date_time"));
							row.put("order_class", resultmap2.get("order_class"));
							row.put("order_no", resultmap2.get("order_no"));
							row.put("group_id", resultmap2.get("group_id"));
							row.put("order_code", resultmap2.get("order_code"));
							row.put("order_text", resultmap2.get("order_text"));
							row.put("order_sub_no", resultmap2.get("order_sub_no"));
							row.put("dosage", resultmap2.get("dosage"));
							row.put("dosage_units", resultmap2.get("dosage_units"));
							row.put("administration", resultmap2.get("administration"));
							row.put("frequency", resultmap2.get("frequency"));
							row.put("order_status", "0");
							row.put("stop_date_time", resultmap2.get("stop_date_time"));
							row.put("doctor_no", resultmap.get("doctor_no"));
							row.put("doctor", resultmap.get("doctor"));
							row.put("ordering_dept", resultmap2.get("ordering_dept"));
							row.put("ordering_deptname", "");
							rows.add(row);
						}
					}
					request.put("doctor", doctor);
					request.put("patient", patient);
					r.put("row", rows);
					request.put("rows", r);
					request.put("p_type", "1");
					request.put("d_type", "1");
					json.put("request", request);
					map.put("json", json);
					list.add(map);
				}
				
			}
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
		Thread.sleep(60000);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("result", list);
		m.put("jg_num", params.get("jg_num"));
		for(int a=0 ;a<bf_num1; a++){
			new AuditHLYY("auto_audit", m,"","智能审查","1").start();
		}
	}
	
	public void uploadToYun(Map<String, Object> params)  throws Exception {
		try {
			connect("hospital_common");
			Result result = new TestjqDao().executeQuery((String)params.get("source_sql"));
			int a = Integer.parseInt((String)params.get("bf_num")) ;
			int listnum = result.getResultset().size() / a ;
			List<Record> ll =(List<Record>) result.getResultset();
			List<List<Record>> list = new ArrayList<List<Record>>();
			for (int i = 0; i < a; i++) {
				if(i + 1 == a) {
					list.add(ll.subList(i*listnum, ll.size()));
				}else {
					list.add(ll.subList(i*listnum, (i+1)*listnum));
					//ll.subList(0, listnum).clear();
				}
			}
			
			for (int i = 0; i < a; i++) {
				Map<String, Object> maps = new HashMap<String, Object>();
				maps.put("data",list.get(i));
				maps.put("jg_num", params.get("jg_num"));
				new AuditHLYY("qiangzhiyongyao", maps, (String)params.get("id"), "强制用药", "2").start();
			}
			
			
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	 
	public class AuditHLYY extends Thread{
		String method_;
		String name;
		String type;
		Map<String, Object> map;
		String id;
		public AuditHLYY(String method, Map<String, Object> map, String id, String name, String type){
			this.method_ = method;
			this.map = map;
			this.name = name;
			this.type = type;
			this.id = id;
		}
		@Override
		public void run() {
			super.run();
			if("auto_audit".equals(method_)){
				try {
					List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("result");
					//List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
					TestjqService ts = new TestjqService();
					for (Map<String, Object> map1 : list) {
						sleep(Integer.parseInt((String)map.get("jg_num"))*1000);
						Map<String, Object> p = new HashMap<String, Object>();
						//p.put("test_id", id);
						p.put("type", 1);
						p.put("name", "智能审查测试");
						p.put("node_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
						long stime = System.currentTimeMillis();
						try{
							String result = HttpClient.post("http://"+GlobalVar.getSystemProperty("local_ip_port","8085") + "/optionifs", map1);
							p.put("cost_time", System.currentTimeMillis() - stime);
							p.put("state", 1);
							p.put("test_id", CommonFun.json2Object(result, Map.class).get("id"));
							p.put("result_params", result);
						}catch(Throwable e){
							p.put("cost_time", System.currentTimeMillis() - stime);
							p.put("state", 0);
							p.put("result_content", e.getMessage());
						}
						p.put("request_params", CommonFun.object2Xml(map1));
						//resList.add(p);
						ts.insert(p);
						
					}
					/*connect("hospital_common");
					try {
						TestjqDao td = new TestjqDao();
						for(Map<String, Object> m : resList){
							td.insert(m);
						}
					} catch (Exception e) {
						throw e;
					}finally {
						disconnect();
					}*/
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if("qiangzhiyongyao".equals(method_)){
				List<Map<String, Object>> listre = (List<Map<String, Object>>) map.get("data");
				//int jg =Integer.parseInt((String)map.get("jg_num"));
				int jg = 2;
				Map<String, Object> result = new HashMap<String, Object>();
				for (Map<String, Object> record : listre) {
				//for (int i = 0; i < listre.size(); i++) {
					Map<String, Object> mmp = new HashMap<String, Object>();
					//record.put("id", record.remove("id"));
					//record.put("doctor_no", record.remove("doctor_no"));
					
					mmp.put("name", name);
					mmp.put("type", "2");
					mmp.put("test_id", record.get("id"));
					mmp.put("request_params", record.toString());
					mmp.put("node_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
					 try {
						 sleep(jg * 1000);
						 Long t = System.currentTimeMillis();
						//result = HttpClient.post("http://"+GlobalVar.getSystemProperty("local_ip_port","8085")+"/prescripitonpass",record);
						// result = HttpClient.post("http://"+GlobalVar.getSystemProperty("local_ip_port","8085")+"/prescripitonpass",record);
						result =  EngineInterface.mustDo(record);
						mmp.put("cost_time", System.currentTimeMillis() - t);
						mmp.put("state", 1);
						mmp.put("result_params", CommonFun.object2Json(result));
						new TestjqService().insert(mmp);
					 } catch (Throwable e) {
						//mmp.put("cost_time", System.currentTimeMillis() - t);
						mmp.put("state", 0);
						mmp.put("result_params", result);
						mmp.put("result_content", e.getMessage());
						try {
							new TestjqService().insert(mmp);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						e.printStackTrace();
					}
				}
			}
		}
	}

	public Result queryInfo(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new TestjqDao().queryInfo(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
