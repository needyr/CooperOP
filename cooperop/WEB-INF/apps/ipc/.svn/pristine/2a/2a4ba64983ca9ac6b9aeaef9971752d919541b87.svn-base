package cn.crtech.cooperop.ipc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.crtech.cooperop.application.dao.BillDao;
import cn.crtech.cooperop.application.dao.TaskDao;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.workflow.workflow;
import cn.crtech.cooperop.hospital_common.dao.CommentManageDao;
import cn.crtech.cooperop.ipc.dao.CommentFlowDao;
import cn.crtech.cooperop.ipc.dao.DoctorDao;
import cn.crtech.cooperop.ipc.dao.sample.CommentSamplePatientsDao;
import cn.crtech.cooperop.ipc.dao.sample.OrdersResultsDao;
import cn.crtech.cooperop.ipc.dao.sample.SampleResultDao;

public class CommentFlowService extends BaseService {
	
	public String insert(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new CommentFlowDao().insert(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new CommentFlowDao().update(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	public void delete(Map<String, Object> params) throws Exception {
		try {
			connect();
			new CommentFlowDao().delete(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new CommentFlowDao().get((String)params.get("id"));
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public void submit(Map<String, Object> params) throws Exception{
		List<Record> persons = (List<Record>) params.remove("assign_persons");
		try {
			connect("base");
			List<String> list = new ArrayList<String>();
			for (int i=0;i<persons.size();i++) {
				list.add(new BillDao().getBusinessNo("SHDP"));
			}
			disconnect();
			connect("ipc");
			CommentFlowDao aafd = new CommentFlowDao();
			CommentSamplePatientsDao pa = new CommentSamplePatientsDao();
			Map<String, Object> map = new HashMap<String, Object>();
			new CommentSamplePatientsDao().deleteNotActive(new HashMap<String, Object>());
			new OrdersResultsDao().deleteNotActive(new HashMap<String, Object>());
			map.put("id", params.get("id"));
			map.put("state", 1);
			Map<String, Object> pa_map = new HashMap<String, Object>();
			aafd.update(map);
			for (int j=0;j<list.size();j++) {
				pa_map.put("djbh", list.get(j));
				pa_map.put("id", params.get("id"));
				pa_map.put("handler", persons.get(j).get("handler"));
				pa.updateToDJBH(pa_map);
				workflow.start("ipc", "shdp_dj", list.get(j), null);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	//页面调用  处理
	public void approval(Map<String, Object> params) throws Exception {
		try {
			String task_id = (String) params.remove("task_id");
			String audited = (String) params.remove("audited");
			String advice = (String) params.remove("advice");
			if(CommonFun.isNe(task_id)){
				connect("base");
				Record task = new TaskDao().getTask((String) params.get("djbh")).getResultset(0);
				disconnect();
				task_id=task.getString("id");
			}
			connect("ipc");
			String  finishallnum = (String)new CommentSamplePatientsDao().queryFinishAllNum(params).getResultset(0).get("count");
			if (Long.parseLong(finishallnum) == 0) {
				Map<String, Object> map2 = new HashMap<String, Object>();
				CommentFlowDao aafd = new CommentFlowDao();
				//修改主表state
				map2.put("state", 2);
				map2.put("comment_finish_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				map2.put("djbh", params.get("djbh"));
				aafd.updateByPtientdjbh(map2);
			}
			workflow.next(task_id, audited, advice);//流程下一步
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	/**
	 * 流程转交
	 * @param params
	 * @throws Exception
	 */
	public void reject(Map<String, Object> params) throws Exception {
		try {
			String task_id = (String) params.remove("task_id");
			String audited = (String) params.remove("audited");
			String advice = (String) params.remove("advice");
			String comment_user = (String) params.remove("comment_user");
			connect("base");
			String new_djbh = new BillDao().getBusinessNo("SHDP");
			disconnect();
			if(CommonFun.isNe(task_id)){
				connect("base");
				Record task = new TaskDao().getTask((String) params.get("djbh")).getResultset(0);
				disconnect();
				task_id=task.getString("id");
			}
			connect("ipc");
			//更改人  wrh  转交给具体药师
			Map<String, Object> map2 = new HashMap<String, Object>();
			Map<String, Object> re = new HashMap<String, Object>();
			CommentFlowDao aafd = new CommentFlowDao();
			CommentSamplePatientsDao csp = new CommentSamplePatientsDao();
			map2.put("comment_user", comment_user);
			map2.put("djbh", params.get("djbh"));
			map2.put("new_djbh", new_djbh);
			re.put("new_comment_user", comment_user);
			re.put("old_comment_user", user().getId());
			re.put("sample_id", csp.getSampleId((String)params.get("djbh")).get("sample_id"));
			csp.updateReject(map2);
			aafd.updateCommentUser(re);
			workflow.next(task_id, audited, advice);//流程下一步
			workflow.start("ipc", "shdp_dj", new_djbh, null);//开始新的流程
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	public Result queryPatients(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			params.put("create_user", user().getNo());
			return new CommentSamplePatientsDao().query(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public Result queryNextPat(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new CommentSamplePatientsDao().queryNextPat(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public Result queryFinishNum(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new CommentSamplePatientsDao().queryFinishNum(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public Result queryFinishNumOwn(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new CommentSamplePatientsDao().queryFinishNumOwn(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
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
	
	public Result queryOrders(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new OrdersResultsDao().queryOrders(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public Map<String, Object> getQuestions(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			CommentFlowDao comtdao = new CommentFlowDao();
			List<Record> list = comtdao.getQuestions(params).getResultset();
			if(list.size() > 0) {
				params.put("auto_audit_id", list.get(0).get("auto_audit_id"));
				//rtnMap.put("data", comtdao.getQuestions(params).getResultset());
				rtnMap.put("data", list);
				rtnMap.put("bcomt", comtdao.getBeforeComment(params).getResultset());
			}
			return rtnMap;
		} catch (Exception ex){
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
	
	//查询已有点评信息
	public Map<String, Object> getOrderComment(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> orderMap = new HashMap<String, Object>();
			//查询当次点评的点评信息
			Record record = new OrdersResultsDao().getByNo(params);
			orderMap.put("writer", record);
			if(CommonFun.isNe(record.get("comment_result"))) {
				Map<String, Object> tjMap = new HashMap<String, Object>();
				//查询最后一次点评该组医嘱的信息（包含在线点评）
				Result result = new OrdersResultsDao().queryLastResult(params);
				if(result!=null && result.getResultset().size() > 0) {
					orderMap.put("checker",result.getResultset());
					orderMap.put("writer", result.getResultset(0));
				}
				/*else {
					tjMap.clear();
					tjMap.put("patient_id", record.get("patient_id"));
					tjMap.put("visit_id", record.get("visit_id"));
					tjMap.put("order_no", record.get("order_no"));
					tjMap.put("group_id", record.get("group_id"));
					orderMap.put("checker",new OrdersResultsDao().queryQZResult(tjMap).getResultset());
				}*/
			}else {
				orderMap.put("checker", new SampleResultDao().queryByParentId(params).getResultset());
			}
			return orderMap;
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	public Map<String, Object> queryLastResult(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> orderMap = new HashMap<String, Object>();
			orderMap.put("lastr", new OrdersResultsDao().queryLastResult(params).getResultset());
			return orderMap;
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
	
	public Result sendMsg(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Result result = new DoctorDao().queryBeCommentN(params);
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
	
	public long hasCommentContent(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Result queryComment = new CommentManageDao().queryComment(params);
			return queryComment.getCount();
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
}
