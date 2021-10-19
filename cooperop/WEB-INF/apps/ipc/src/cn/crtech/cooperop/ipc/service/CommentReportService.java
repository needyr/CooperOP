package cn.crtech.cooperop.ipc.service;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.ipc.dao.CommentReportDao;

public class CommentReportService extends BaseService{

	public Result query(Map<String, Object> map) throws Exception {
		try {
			connect("ipc");
			return new CommentReportDao().query(map);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Map<String, Object> basecomment(Map<String, Object> map) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("drugcountall", new CommentReportDao().getDrugCountAll(map).get("drugcountall"));
			result.put("pre_counts", new CommentReportDao().getDrugAve(map).get("pre_counts"));
			result.put("average_drug_breed_count", new CommentReportDao().getDrugAve(map).get("average_drug_breed_count"));
			result.put("usekjcount", new CommentReportDao().getUseKJCount(map).get("usekjcount"));
			result.put("useallkjcount", new CommentReportDao().getUseKJCount(map).get("useallkjcount"));
			result.put("usezscount", new CommentReportDao().getUseZsCount(map).get("usezscount"));
			result.put("usegeneralcount", new CommentReportDao().getUseGeneralCount(map).get("usegeneralcount"));
			result.put("passcount", new CommentReportDao().getPassCount(map).get("passcount"));
			return result;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

}
