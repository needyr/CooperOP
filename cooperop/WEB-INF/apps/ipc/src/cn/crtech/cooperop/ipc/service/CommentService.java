package cn.crtech.cooperop.ipc.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.CommentManageDao;
import cn.crtech.cooperop.hospital_common.dao.auditset.CheckLevelDao;
import cn.crtech.cooperop.ipc.dao.AutoAuditOrdersDao;
import cn.crtech.cooperop.ipc.dao.CheckAndCommentDao;
import cn.crtech.cooperop.ipc.dao.CheckResultInfoDao;
import cn.crtech.cooperop.ipc.dao.CommentDao;
import cn.crtech.cooperop.ipc.dao.DoctorDao;
import cn.crtech.cooperop.ipc.dao.sample.CommentSampleDao;
import cn.crtech.cooperop.ipc.dao.sample.CommentSamplePatientsDao;
import cn.crtech.cooperop.ipc.dao.sample.OrdersResultsDao;
import cn.crtech.cooperop.ipc.dao.sample.SampleResultDao;

public class CommentService extends BaseService{
	
	public Result querySample(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new CommentSampleDao().queryHistory(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public Record queryPass(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new CommentDao().queryPass(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}

	public Result queryCRorders(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new OrdersResultsDao().queryCRorders(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public int finishMRHL(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			start();
			Map<String, Object> tjMap = new HashMap<String, Object>();
			tjMap.put("id", params.remove("sample_pid"));
			tjMap.put("state", 1);
			new OrdersResultsDao().updateMRHL(params);
			new CommentSamplePatientsDao().update(tjMap);
			commit();
			return 1;
		} catch (Exception ex){
			rollback();
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	//在完成点评之前，查询工作表中的点评数据
	public Map<String, Object> getCommenting(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> orderMap = new HashMap<String, Object>();
			Record record = new OrdersResultsDao().getByNo(params);
			orderMap.put("writer", record);
			orderMap.put("checker", new SampleResultDao().queryByParentId(params).getResultset());
			return orderMap;
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	//查询历史表中的点评数据
	public Map<String, Object> commentHistory(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> orderMap = new HashMap<String, Object>();
			Result result = new OrdersResultsDao().getHistoryByNo(params);
			if(CommonFun.isNe(result)) {
				orderMap.put("data", null);
			}else{
				orderMap.put("data", result.getResultset());
			}
			return orderMap;
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public Result queryFinishNum(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new CommentSamplePatientsDao().queryFinishNumOwn(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public void approval(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> map2 = new HashMap<String, Object>();
			//修改主表state
			map2.put("state", 2);
			map2.put("comment_finish_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			map2.put("id", params.get("sample_id"));
			new CommentSampleDao().update(map2);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public Result sendMsg(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Result result = new DoctorDao().queryBeCommentNBySampleid(params);
			disconnect();
			 if(result !=null && !CommonFun.isNe(result.getResultset())) {
				connect("iadscp");
				 for (Record rec  : result.getResultset()) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("content", "您有医嘱被点评为：【不合理】，点击查看");
					map.put("subject", "不合理医嘱/处方点评提醒");
					map.put("type", "3");
					map.put("state", "0");
					map.put("send_from", null);
					map.put("send_to", rec.get("id"));
					map.put("system_product_code", "ipc");
					map.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
					map.put("tunnel_id", "4");
					map.put("url_", "ipc.comment.resultfordoc");
					new DoctorDao().insertMsg(map);
				}
			 }
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
		return null;
	}
	
	public Record getPatientInfo(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new CommentSamplePatientsDao().gePatientInfo(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	//保存点评结果
	public void saveCommentR(Map<String, Object> params) throws Exception {
		try {
			connect();
			start();
			Map<String, Object> updatetj = new HashMap<String, Object>();
			updatetj.put("sample_orders_id_set", params.remove("sample_orders_id_set"));
			updatetj.put("sample_patients_id", params.get("sample_patients_id"));
			SampleResultDao sampler = new SampleResultDao();
			sampler.delete(updatetj);
			updatetj.put("comment_content", params.remove("comment_content"));
			updatetj.put("comment_result",params.remove("comment_result"));
			new OrdersResultsDao().updateByOrderNo(updatetj);
			List<String> check_gx= (List<String>) params.remove("check_gx");
			List<String> sample_orders_id_set= (List<String>) updatetj.remove("sample_orders_id_set");
			//List<String> order_no_set= (List<String>) params.remove("order_no_set");
			
			//List<String> p_group_id_set= (List<String>) params.remove("p_group_id_set");
			if(!CommonFun.isNe(check_gx)) {
				for(int i=0; i<sample_orders_id_set.size(); i++) {
					params.put("sample_orders_id", sample_orders_id_set.get(i));
					//params.put("order_no", order_no_set.get(i));
					//params.put("group_id", p_group_id_set.get(i));
					for (String  pharmacist_comment_id: check_gx) {
						params.put("pharmacist_comment_id",pharmacist_comment_id);
						sampler.insert(params);
					}
				}
			}
			commit();
		} catch (Exception ex){
			rollback();
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public int getNullNum(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Record record = new OrdersResultsDao().getNullNum(params);
			if(CommonFun.isNe(record)) {
				return 0;
			}else {
				return record.getInt("num");
			}
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public int updateCommentState(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			new CommentSamplePatientsDao().update(params);
			return 1;
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public Result queryNextPat(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new CommentSamplePatientsDao().queryNextPatOwn(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public Result queryPatients(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new CommentSamplePatientsDao().query(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	
	public Result queryComment(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new CommentManageDao().queryComment(params);	
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

	public Result getDiagnosis(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new AutoAuditOrdersDao().queryDiagnosis(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

	public Map<String, Object> setHL(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> select = new HashMap<String, Object>();
			Map<String, Object> passmap = new HashMap<String, Object>();
			CommentDao commentDao = new CommentDao();
			CheckResultInfoDao info = new CheckResultInfoDao();
			select.put("auto_audit_id", params.get("auto_audit_id"));
			select.put("id", params.get("id"));
			select.put("shenc_change_level", params.get("shenc_change_level"));
			params.put("check_result_info_id", params.get("id"));
			Record queryPass = commentDao.queryPass(params);
			Record record = info.getPassAudit(select).getResultset(0);
			if(queryPass == null || CommonFun.isNe(queryPass.get("id"))) {
				passmap.put("id", CommonFun.getITEMID());
				passmap.put("shenc_pass_source", params.get("shenc_pass_source"));
				passmap.put("check_result_info_id", record.get("check_result_info_id"));
				passmap.put("auto_audit_id", record.get("auto_audit_id"));
				passmap.put("sort_id", record.get("sort_id"));
				passmap.put("sort_name", record.get("sort_name"));
				passmap.put("level", record.get("auto_audit_level"));
				passmap.put("description", record.get("description"));
				passmap.put("reference", record.get("reference"));
				passmap.put("order_p_key", record.get("related_drugs_pkey"));
				passmap.put("is_shenc_pass", 1);
				passmap.put("shenc_pass_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				passmap.put("shenc_pass_ren", user().getName());
				passmap.put("shenc_pass_gnmch", "药师审查");
				passmap.put("pharmacist_todoctor_advice", params.get("pharmacist_todoctor_advice"));
				passmap.put("shenc_pass_pharmacist_advice", params.get("shenc_pass_pharmacist_advice"));
				passmap.put("beactive", "是");
				passmap.put("level_name", record.get("sys_check_level_name"));
				passmap.put("related_drugs_show", record.get("related_drugs_show"));
				passmap.put("create_time", record.get("createtime"));
				passmap.put("shenc_change_level", params.get("shenc_change_level"));
				passmap.put("drug_p_key_1", record.get("drug_p_key_1"));
				passmap.put("drug_p_key", record.get("drug_p_key"));
				start();
				int insertnum = commentDao.insertPharmacistCheckPass(passmap);
				commit();
			}
			Record result = new Record();
			result.put("description", record.get("description"));
			if(!CommonFun.isNe(params.get("pharmacist_todoctor_advice"))) {
				result.put("pharmacist_todoctor_advice", 1);
			}
			return result; 
		} catch (Exception e) {
			rollback();
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public int updateHL(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			start();
			params.put("check_result_info_id", params.remove("id"));
			int result = new CommentDao().updatePharmacistCheckPass(params);
			commit();
			return result;
		} catch (Exception e) {
			rollback();
			throw e;
		} finally {
			disconnect();
		}
	}

	public Result queryCheck(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Result result = new CheckLevelDao().queryAuto(params);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

	public Map<String, Object> queryRealYizu(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("commentadvice", new CheckAndCommentDao().queryCommentAdvice(params).getResultset());
			retMap.put("realyizu", new CheckAndCommentDao().queryRealYizu(params).getResultset());
			return retMap;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
