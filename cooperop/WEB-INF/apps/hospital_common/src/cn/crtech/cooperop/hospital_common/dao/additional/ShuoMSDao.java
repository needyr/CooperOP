package cn.crtech.cooperop.hospital_common.dao.additional;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class ShuoMSDao extends BaseDao{

	public static final String SPZL_SHUOMS = "spzl_shuoms";
	
	public static final String SPZL_SHUOMS_ZDY = "spzl_shuoms_zdy";
	
	public Result queryDrug(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		if(!CommonFun.isNe(params.get("filter"))) {
			params.put("pp", "%" + params.get("filter") + "%");
		}
		sql.append(" select dhg.drug_code,dhg.druggg, dhg.shengccj,dhg.drug_unit, ss.name_cn,dhg.drug_name,dhg.input_code his_input from spzl_shuoms (nolock) ss            ");
		sql.append(" inner join dict_sys_drug (nolock) dsg on dsg.drug_code = ss.sys_drug_code ");
		sql.append(" inner join dict_his_drug (nolock) dhg on dhg.sys_p_key = dsg.p_key        ");
		setParameter(params, "pp", "where dhg.drug_code like :pp or ss.name_cn like :pp or dhg.drug_name like :pp or dhg.input_code like :pp", sql);
		sql.append(" union                                                                    ");
		sql.append(" select dhg.drug_code,dhg.druggg,dhg.shengccj,dhg.drug_unit, ss.name_cn,dhg.drug_name,dhg.input_code his_input from spzl_shuoms_zdy (nolock) ss        ");
		sql.append(" inner join dict_sys_drug (nolock) dsg on dsg.drug_code = ss.sys_drug_code ");
		sql.append(" inner join dict_his_drug (nolock) dhg on dhg.sys_p_key = dsg.p_key        ");
		setParameter(params, "pp", "where dhg.drug_code like :pp or ss.name_cn like :pp or dhg.drug_name like :pp or dhg.input_code like :pp", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	
	/**
	 * @param params
	 * @return
	 * @throws Exception
	 * @function 医策标准 --- 已有说明书药品
	 * @author yankangkang 2019年3月17日
	 */
	public Result querySysDrug(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		if(!CommonFun.isNe(params.get("filter"))) {
			params.put("pp", "%" + params.get("filter") + "%");
		}
		sql.append(" select dsg.drug_code,dsg.druggg, dsg.shengccj,dsg.drug_unit, ss.name_cn,dsg.drug_name,dsg.input_code sys_input from spzl_shuoms (nolock) ss            ");
		sql.append(" inner join dict_sys_drug (nolock) dsg on dsg.drug_code = ss.sys_drug_code ");
		setParameter(params, "pp", "where dsg.drug_code like :pp or ss.name_cn like :pp or dsg.drug_name like :pp or dsg.input_code like :pp", sql);
		sql.append(" union                                                                    ");
		sql.append(" select dsg.drug_code,dsg.druggg,dsg.shengccj,dsg.drug_unit, ss.name_cn,dsg.drug_name,dsg.input_code sys_input from spzl_shuoms_zdy (nolock) ss        ");
		sql.append(" inner join dict_sys_drug (nolock) dsg on dsg.drug_code = ss.sys_drug_code ");
		setParameter(params, "pp", "where dsg.drug_code like :pp or ss.name_cn like :pp or dsg.drug_name like :pp or dsg.input_code like :pp", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(SPZL_SHUOMS_ZDY, params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(SPZL_SHUOMS_ZDY, params, r);
	}
	
	
	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(SPZL_SHUOMS_ZDY, params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + SPZL_SHUOMS_ZDY);
		sql.append(" (nolock) where  sys_drug_code = :sys_drug_code ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getByHiscode_zdy(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ss.*,dhg.drug_code,dhg.drug_name,dhg.input_code his_input,dsg.shuoms_file,dhg.zdy_shuoms_msg from "+ SPZL_SHUOMS_ZDY +" (nolock) ss                                      ");
		sql.append(" left join dict_sys_drug (nolock) dsg on dsg.drug_code = ss.SYS_DRUG_CODE      ");
		sql.append(" left join dict_his_drug (nolock) dhg on dhg.sys_p_key = dsg.p_key             ");
		sql.append(" where dhg.drug_code = :drug_code                                         ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getByHiscode(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ss.*,dhg.drug_code,dhg.drug_name,dhg.input_code his_input,dsg.shuoms_file,dhg.zdy_shuoms_msg from "+ SPZL_SHUOMS +" (nolock) ss  ");
		sql.append(" left join dict_sys_drug dsg (nolock) on dsg.drug_code = ss.SYS_DRUG_CODE      ");
		sql.append(" left join dict_his_drug dhg (nolock) on dhg.sys_p_key = dsg.p_key             ");
		sql.append(" where dhg.drug_code = :drug_code                                         ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result queryDrugByHisCode(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ss.*,dsd.shuoms_file from dict_his_drug (nolock) ss        ");
		sql.append("left join dict_sys_drug (nolock) dsd on dsd.p_key=ss.sys_p_key ");
		sql.append(" where ss.drug_code like '"+params.get("drug_code")+"'+'%'");
		return executeQuery(sql.toString(), params);
	}
	
	public Record getBySyscode_zdy(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ss.*,dsg.drug_code,dsg.drug_name,dsg.input_code sys_input,dsg.shuoms_file,dhg.zdy_shuoms_msg from "+ SPZL_SHUOMS_ZDY +" (nolock) ss  ");
		sql.append(" left join dict_sys_drug (nolock) dsg on dsg.drug_code = ss.SYS_DRUG_CODE      ");
		sql.append(" left join dict_his_drug (nolock) dhg on dhg.sys_p_key = dsg.p_key             ");
		sql.append(" where dsg.drug_code = :drug_code                                         ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getBySyscode(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ss.*,dsg.drug_code,dsg.drug_name,dsg.input_code sys_input,dsg.shuoms_file,dhg.zdy_shuoms_msg from "+ SPZL_SHUOMS +" (nolock) ss                                      ");
		sql.append(" left join dict_sys_drug dsg (nolock) on dsg.drug_code = ss.SYS_DRUG_CODE      ");
		sql.append(" left join dict_his_drug (nolock) dhg on dhg.sys_p_key = dsg.p_key             ");
		sql.append(" where dsg.drug_code = :drug_code                                         ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getByCode(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select drug_code from dict_his_drug (nolock) where drug_code = :drug_code ");
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public Result querySmsDesc(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select column_name,column_name_cn from system_instruction_config (nolock) " );
		sql.append(" where sms_is_show = '1'  order by sms_sort_id ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryClinicYBfo(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" exec syc_get_clinic_insur_xx :patient_id , :visit_id, :drug_code" );
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryDrugYBfo(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" exec syc_get_drug_insur_xx :patient_id , :visit_id, :drug_code" );
		return executeQuery(sql.toString(), params);
	}
	
	public Record shuoms_img(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select  a.shuoms_file          ");
		sql.append("from dict_sys_drug a           ");    
		sql.append("where exists (select 1         ");         
		sql.append("from dict_his_drug (nolock) b  ");         
		sql.append("where a.p_key=b.sys_p_key      ");         
		sql.append("and b.drug_code=:drug_code)    ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
}
