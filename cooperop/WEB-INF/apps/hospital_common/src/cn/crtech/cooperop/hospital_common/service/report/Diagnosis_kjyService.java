package cn.crtech.cooperop.hospital_common.service.report;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.report.Diagnosis_kjyDao;

public class Diagnosis_kjyService extends BaseService {
	
	public Result research_query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new Diagnosis_kjyDao().research_query(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}
	
	public Result cost_comparison_query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new Diagnosis_kjyDao().cost_comparison_query(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}
	
	public Result sum_query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Result re = new Result();
			Record tmp = new Record();
			List<Record> li = new ArrayList<Record>();
			Record record = new Diagnosis_kjyDao().sum_query(params).getResultset(0);
			DecimalFormat df = new DecimalFormat("#.00");
			int zl_patient_num = record.getInt("zl_patient_num");
			int result_zy_num = record.getInt("result_zy_num");
			int ts = record.getInt("ts");
			int kjy_patient_num = record.getInt("kjy_patient_num");
			double all_money = record.getDouble("all_money");
			double drug_money = record.getDouble("drug_money");
			double kjy_money = record.getDouble("kjy_money");
			double kjy_all_money = record.getDouble("kjy_all_money");
			tmp.put("tj1", "A(统计总例数)="+zl_patient_num+"例");
			tmp.put("tj2", "");
			li.add(tmp);
			tmp = new Record();
			tmp.put("tj1", "B(治愈例数)="+result_zy_num+"例");
			tmp.put("tj2", "C(治愈率B/A)100%="+(zl_patient_num == 0? 0 : df.format((result_zy_num/zl_patient_num)))+"%");
			li.add(tmp);
			tmp = new Record();
			tmp.put("tj1", "D(住院总天数)="+ts+"天");
			tmp.put("tj2", "E(人均住院天数D/A)="+(zl_patient_num==0?0:df.format((ts/zl_patient_num)))+"天");
			li.add(tmp);
			tmp = new Record();
			tmp.put("tj1", "F(治疗总金额)="+all_money+"元");
			tmp.put("tj2", "G(人均日治疗金额用F/D)="+(ts==0?0:df.format((all_money/ts)))+"元");
			li.add(tmp);
			tmp = new Record();
			tmp.put("tj1", "H(药品总金额)="+drug_money+"元");
			tmp.put("tj2", "I(药品总金额占治疗总金额的比例H/F)100%="+(all_money==0?0:df.format((drug_money/all_money)))+"%");
			li.add(tmp);
			tmp = new Record();
			tmp.put("tj1", "G(人均药品金额H/A)="+(zl_patient_num==0?0:df.format((drug_money/zl_patient_num)))+"元");
			tmp.put("tj2", "K(人均日药品金额H/D)="+(ts==0?0:df.format((drug_money/ts)))+"元");
			li.add(tmp);
			tmp = new Record();
			tmp.put("tj1", "L(使用抗菌药的例数)="+kjy_patient_num+"例");
			tmp.put("tj2", "M(使用抗菌药物的比例L/A)100%="+(zl_patient_num==0?0:df.format((kjy_patient_num/zl_patient_num)))+"%");
			li.add(tmp);
			tmp = new Record();
			tmp.put("tj1", "N(抗菌药物总金额)="+kjy_money+"元");
			tmp.put("tj2", "O(抗菌药物占药品总金额的比例N/H)100%="+(drug_money==0?0:df.format((kjy_money/drug_money)))+"%");
			li.add(tmp);
			tmp = new Record();
			tmp.put("tj1", "P(使用抗菌药物的总治疗金额)="+kjy_all_money+"元");
			tmp.put("tj2", "Q(使用抗菌药物的人均治疗金额P/L)="+(kjy_patient_num==0?0:df.format((kjy_all_money/kjy_patient_num)))+"元");
			li.add(tmp);
			re.setResultset(li);
			re.setCount(li.size());
			return re;
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}
}
