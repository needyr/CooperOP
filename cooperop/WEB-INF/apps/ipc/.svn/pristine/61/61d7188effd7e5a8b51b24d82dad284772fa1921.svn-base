package cn.crtech.cooperop.ipc.dao;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DrugSmsDao extends BaseDao {
	
	private final static String TABLE_NAME ="spzl_shuoms";
	private final static String SPZL_SHUOMS_TEMP ="spzl_shuoms_temp";
	
	public Result querySmsDesc(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select column_name,column_name_cn from system_instruction_config (nolock) " );
		sql.append(" where sms_is_show = '1'  order by sms_sort_id ");
		return executeQuery(sql.toString(), params);
	}
	
	public Record getDrugById(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from spzl_shuoms (nolock) ");
		sql.append(" where sys_drug_code = :sys_drug_code");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result querySimpleDesc(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select column_name,column_name_cn,brief_title from system_instruction_config (nolock) " );
		sql.append(" where brief_is_show = '1'  order by brief_sort_id ");
		return executeQuery(sql.toString(), params);
	}
	

	public Record getSimpleDrug(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		//sql.append("select * from spzl_shuoms ");
		//sql.append(" where sys_drug_code = :sys_drug_code");
		sql.append("select sp.* FROM spzl_shuoms (nolock) sp ");
		sql.append(" inner join dict_sys_drug (nolock) ds on sp.sys_drug_code = ds.drug_code ");
		sql.append(" left join dict_his_drug (nolock) dh on dh.sys_p_key = ds.p_key ");
		sql.append(" where dh.drug_code = :drug_code ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result queryAssist(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from system_assist_config (nolock) ");
		sql.append(" where is_show = '1' order by sort_id ");
		return executeQuery(sql.toString(), params);
	}
	
	/**
	 * @param params his库drug_code
	 * @return sys库drug_code
	 * @throws Exception
	 * @author yan
	 * @time 2018年4月11日
	 * @function 将his drugcode转化为标准库drugcode
	 */
	public Record histosys(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select top 1 s.drug_code sys_drug_code,s.haveurl,h.drug_code his_drug_code ");
		sql.append(" from dict_his_drug (nolock) h inner join dict_sys_drug (nolock) s ");
		sql.append(" on h.sys_p_key = s.p_key");
		sql.append(" where h.drug_code = :his_drug_code ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		//params.put("updatetime", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		return executeInsert(TABLE_NAME, params);
	}
	
	public int insert_temp(Map<String, Object> params) throws Exception {
		//params.put("updatetime", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		return executeInsert(SPZL_SHUOMS_TEMP, params);
	}
	
	public int updateSysDict(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		//params.put("updatetime", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		conditions.put("drug_code", params.remove("drug_code"));
		return executeUpdate("dict_sys_drug", params, conditions);
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from hospital_common..v_dict_his_drug_shuoms (nolock) ");
		//sql.append(" where s.pphyt >= 1 ");
		return executeQuery(sql.toString(), params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		params.put("updatetime", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		conditions.put("sys_drug_code", params.remove("sys_drug_code"));
		return executeUpdate(TABLE_NAME, params, conditions);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from "+TABLE_NAME);
		sql.append(" (nolock) where drug_code = :drug_code ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	
	public void deleteAll(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("delete from "+TABLE_NAME);
		sql.append(" where owner_create =  '0' ");
	    execute(sql.toString(),params);
	}
	
	//清空说明书临时表
	public void deleteAllTemp(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("delete from " + SPZL_SHUOMS_TEMP);
	    execute(sql.toString(),params);
	}
	
	
	public int updateHaveUrl(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("update dict_sys_drug set haveurl = null where pphyt >= 1 ");
		return executeUpdate(sql.toString(), params);
	}
	
	public void insertTemptoSms(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" insert into spzl_shuoms select * from spzl_shuoms_temp ");
	    execute(sql.toString(),params);
	}
	
	public void execTmp2Spzl(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		//StringBuffer sql2=new StringBuffer();
		sql.append(" exec cr_spzl_shuoms ");
		//sql.append(" exec hospital_common..cr_spzl_shuoms_zdy ");
	    execute(sql.toString(),params);
	    //execute(sql2.toString(),params);
	}
}
