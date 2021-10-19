package cn.crtech.cooperop.hospital_common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.HttpClient;
import cn.crtech.cooperop.hospital_common.dao.AfterAutoAuditDao;
import cn.crtech.cooperop.hospital_common.dao.HisInPatientDao;
import cn.crtech.cooperop.hospital_common.schedule.AutoAuditSchedule;
import cn.crtech.precheck.server.PrescripionInitServlet;
import cn.crtech.ylz.ylz;

public class AfterAutoService extends BaseService {

    private static final String URL = GlobalVar.getSystemProperty("local_ip_port", "http://127.0.0.1:8085") + "/optionifs";

    public Result query(Map<String, Object> params) throws Exception {
        try {
            connect("hospital_common");
            return new AfterAutoAuditDao().query(params);
        } catch (Exception e) {
            throw e;
        } finally {
            disconnect();
        }
    }

    public void update(Map<String, Object> params) throws Exception {
        try {
            connect("hospital_common");
            params.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            new AfterAutoAuditDao().update(params);
        } catch (Exception e) {
            throw e;
        } finally {
            disconnect();
        }
    }

    public void exeAudit(Map<String, Object> param) throws Exception {
        Record params = new Record(param);
        Result audits = null;
        Result orders = null;
        int times = Integer.parseInt((String) params.get("audit_times")) + 1;
        try {
            connect("hospital_common");
            Object quene_id = params.get("id");
            AfterAutoAuditDao aad = new AfterAutoAuditDao();
            params.put("state", AutoAuditSchedule.INGEXEC);
            params.put("execute_date", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            params.remove("rowno");
            params.remove("rownuma");
            params.put("audit_times", times);
            aad.update(params);
            params.put("audit_queue_id", quene_id);
            audits = aad.queryAfterAudit(params);
            orders = aad.queryAfterAuditOrders(params);
        } catch (Exception e) {
            throw e;
        } finally {
            disconnect();
        }
        if (audits == null || audits.getResultset().size() < 1) {
            return;
        }
		/*if(orders == null || orders.getResultset().size() < 1){
			return;
		}*/
        log.debug("====jq===" + audits.getResultset().size());
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Record audit : audits.getResultset()) {
            Map<String, Object> json = new HashMap<String, Object>();
            Map<String, Object> request = new HashMap<String, Object>();
            Map<String, Object> patient = new HashMap<String, Object>();
            Map<String, Object> doctor = new HashMap<String, Object>();
            List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
            Map<String, Object> r = new HashMap<String, Object>();
            Map<String, Object> map = new HashMap<String, Object>();
            audit.put("doctor_name", ((String) audit.remove("doctor_name")).trim());
            doctor.put("code", audit.get("doctor_no"));
            doctor.put("name", audit.get("doctor_name"));
            doctor.put("duty", audit.get("doctor_duty"));
            doctor.put("departcode", audit.get("dept_code"));
            doctor.put("departname", audit.get("dept_name"));
            patient.put("id", audit.get("patient_id"));
            patient.put("visitid", audit.get("visit_id"));
            patient.put("code", "");
            patient.put("name", audit.get("patient_name"));
            patient.put("chargetype", audit.get("charge_type"));
            patient.put("sex", audit.get("sex"));
            patient.put("departcode", audit.get("patient_depart_code"));
            patient.put("departname", audit.get("patient_depart_name"));
            patient.put("admissiondate", audit.get("admission_datetime"));
            patient.put("idcardno", audit.get("idcardno"));
            patient.put("bedno", audit.get("bed_no"));
            patient.put("type", audit.get("identity"));
            patient.put("status", audit.get("status"));
            patient.put("birthday", audit.get("birthday"));
            patient.put("weight", audit.get("weight"));
            for (Map<String, Object> order : orders.getResultset()) {
                if (order.get("audit_patient_id").equals(audit.get("id"))) {
                    Map<String, Object> row = new HashMap<String, Object>();
                    row.put("p_key", order.get("p_key"));
                    row.put("repeat_indicator", order.get("repeat_indicator"));
                    row.put("start_date_time", order.get("start_date_time"));
                    row.put("order_class", order.get("order_class"));
                    row.put("order_no", order.get("order_no"));
                    row.put("order_code", order.get("order_code"));
                    row.put("group_id", order.get("group_id"));
                    row.put("order_text", order.get("order_text"));
                    row.put("order_sub_no", order.get("order_sub_no"));
                    row.put("dosage", order.get("dosage"));
                    row.put("dosage_units", order.get("dosage_units"));
                    row.put("administration", order.get("administration"));
                    row.put("frequency", order.get("frequency"));
                    row.put("order_status", order.get("order_status"));
                    row.put("stop_date_time", order.get("stop_date_time"));
                    row.put("enter_date_time", order.get("enter_date_time"));
                    row.put("doctor_no", order.get("doctor_no"));
                    row.put("doctor", order.get("doctor"));
                    row.put("ordering_dept", order.get("ordering_dept"));
                    row.put("ordering_deptname", "");
                    rows.add(row);
                }
            }
            request.put("doctor", doctor);
            request.put("patient", patient);
            r.put("row", rows);
            request.put("rows", r);
            request.put("p_type", audit.get("p_type"));
            request.put("d_type", audit.get("d_type"));
            request.put("is_after", "1");
            if (!CommonFun.isNe(params.get("dda_task_id"))) {
                request.put("dda_task", "1");
            }
            request.put("products", params.get("products"));
            json.put("audit_source_fk", audit.get("id") + "#" + times);//事后审查关联审查主表字段
            json.put("request", request);
            map.put("json", json);
            list.add(map);
        }
        Record con = new Record();
        int tog = params.getInt("tog_num") == 0 ? 5 : params.getInt("tog_num"); // 连续，同时
        int wait = params.getInt("wait_ms") == 0 ? 3000 : params.getInt("wait_ms"); // 间隔
        try {
            connect("hospital_common");
            AfterAutoAuditDao aad = new AfterAutoAuditDao();
            int len = list.size();
            ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
            for (int i = 0; i < len; i++) {
                try {
                    Map<String, Object> data = list.get(i);
                    cachedThreadPool.execute(() ->{
                        try {
                            System.out.println(CommonFun.formatDate(new Date(), "HH:mm:ss") + ": after 发起医保事后审核...");
                            HttpClient.post(URL, data);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (i % tog == 0) {
                    con.put("id", params.get("audit_queue_id"));
                    Thread.sleep(wait);
                    con.put("progress_rate", (i + 1) + "/" + list.size());
                    aad.update(con);
                }
            }
            con.put("id", params.get("audit_queue_id"));
            con.put("progress_rate", list.size() + "/" + list.size());
            con.put("state", AutoAuditSchedule.ENDEXEC);
            con.put("audit_end_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            aad.update(con);
        } catch (Exception e) {
            throw e;
        } finally {
            disconnect();
        }
//        try {
//            List<String> sizeList = new ArrayList<>();//（审查数）检测审查队列是否执行完成
//            Map<String, Object> quene_map = new HashMap<>();
//            quene_map.put("id", params.get("audit_queue_id"));
//            int bf_num = 3;
//            int num = list.size() / bf_num;
//            for (int a = 0; a < bf_num; a++) {//三个线程将数据分三批审查
//                if (a == bf_num - 1) {
//                    List<Map<String, Object>> listPage = list.subList(a * num, list.size());
//                    new AuditHLYY(listPage, sizeList, quene_map, list.size()).start();
//                } else {
//                    List<Map<String, Object>> listPage = list.subList(a * num, (a + 1) * num);
//                    new AuditHLYY(listPage, sizeList, quene_map, list.size()).start();
//                }
//            }
//            Thread.sleep(3000);
//        } catch (Throwable e) {
//            log.error(e);
//        }
    }

    //单个患者的结算审查组织XML，非事后审核，is_after ！= 1
    public Map<String, Object> auditOnePat(Map<String, Object> params) throws Exception {
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        String rtnStr = "1";
        try {
            connect("hospital_common");
            // 没有传递患者信息，从审核中找(未传递患者，但有医生信息，审核该医生最后一次初始化的患者)
            if (CommonFun.isNe(params.get("patient_id")) && !CommonFun.isNe(params.get("doctor_no"))) {
                Record lastPat = (Record) PrescripionInitServlet.DOCTORMPAT.get(params.get("doctor_no"));
                if (!CommonFun.isNe(lastPat)) {
                    params.put("patient_id", lastPat.get("patient_id"));
                    params.put("visit_id", lastPat.get("visit_id"));
                }
            }
            // 先初始化
            HisInPatientDao hip = new HisInPatientDao();
            hip.execPatInit(params);
            Record patinfo = hip.getPatAllInfo(params);
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            Map<String, Object> xmlMap = new HashMap<String, Object>();
            Map<String, Object> req = new HashMap<String, Object>();
            Map<String, Object> doctor = new HashMap<String, Object>();
            Map<String, Object> patient = new HashMap<String, Object>();
            if (!CommonFun.isNe(patinfo)) {
                req.put("d_type", "5");
                req.put("p_type", "4");
                doctor.put("code", params.get("doctor_no"));
                doctor.put("name", params.get("doctor_name"));
                doctor.put("duty", "");
                doctor.put("departcode", patinfo.get("dept_in"));
                doctor.put("departname", "");
                patient.put("id", patinfo.get("patient_id"));
                patient.put("visitid", patinfo.get("visit_id"));
                patient.put("code", patinfo.get("patient_no"));
                patient.put("name", patinfo.get("patient_name"));
                patient.put("chargetype", "");
                patient.put("sex", patinfo.get("sex"));
                patient.put("departcode", patinfo.get("dept_in"));
                patient.put("departname", "");
                patient.put("admissiondate", patinfo.get("admission_datetime"));
                patient.put("idcardno", "");
                patient.put("type", "");
                patient.put("status", "");
                patient.put("birthday", patinfo.get("birthday"));
                patient.put("weight", "0");
                req.put("doctor", doctor);
                req.put("patient", patient);
                if (!CommonFun.isNe(params.get("settlement_last_year"))) {
                    req.put("settlement_last_year", params.get("settlement_last_year"));
                }
                if (!CommonFun.isNe(params.get("bill_start_time"))) {
                    req.put("bill_start_time", params.get("bill_start_time"));
                }
                if (!CommonFun.isNe(params.get("bill_end_time"))) {
                    req.put("bill_end_time", params.get("bill_end_time"));
                }
                xmlMap.put("request", req);
                jsonMap.put("json", xmlMap);
                String rtn = HttpClient.post(URL, jsonMap);
                rtnMap = CommonFun.json2Object(rtn, Map.class);
            } else {
                rtnStr = "系统内无该患者！";
            }
            rtnMap.put("msg", rtnStr);
            rtnMap.put("doctor_no", params.get("doctor_no"));
        } catch (Throwable e) {
            log.error(e);
        }
        return rtnMap;
    }

    public class AuditHLYY extends Thread {
        List<Map<String, Object>> list;//分批待审数据
        List<String> sizeList;//保存审查结果，判断当前队列是否审查完成
        Map<String, Object> quene_map;//队列map
        int num;//当前队列list大小

        public AuditHLYY(List<Map<String, Object>> list, List<String> sizeList, Map<String, Object> quene_map, int num) {
            this.list = list;
            this.sizeList = sizeList;
            this.quene_map = quene_map;
            this.num = num;
        }

        @Override
        public void run() {
            super.run();
            long run_st_time = System.currentTimeMillis();
            try {
                for (Map<String, Object> map : list) {
                    sleep(6000);
                    ylz.p("事后审查线程，本线程距上次发起审查已过 [" + (System.currentTimeMillis() - run_st_time) / 1000 + "] 秒");
                    run_st_time = System.currentTimeMillis();
                    String result = "";
                    try {
                        result = HttpClient.post(URL, map);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error(e);
                        result = "error";
                    }
                    sizeList.add(result);//审查完成，将审查结果插入
                }
                if (sizeList.size() == num) {//队列审查完成
                    quene_map.put("state", AutoAuditSchedule.ENDEXEC);//队列审查执行完成
                    quene_map.put("audit_end_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));//队列审查结束时间
                    new AfterAutoService().updateQueue(quene_map);
                }
            } catch (Throwable e) {
                log.error(e);
            }
        }
    }

    /**
     * @author: 魏圣峰
     * @description: 用于修改队列状态、结束时间
     * @param: params Map 队列id,队列state
     * @throws: Exception
     */
    public void updateQueue(Map<String, Object> params) throws Exception {
        try {
            connect("hospital_common");
            new AfterAutoAuditDao().update(params);
        } catch (Exception e) {
            throw e;
        } finally {
            disconnect();
        }
    }
}
