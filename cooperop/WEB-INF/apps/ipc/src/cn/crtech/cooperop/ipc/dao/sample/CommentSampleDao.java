package cn.crtech.cooperop.ipc.dao.sample;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class CommentSampleDao extends BaseDao{
	
	private final static String COMMENT_SAMPLE = "comment_sample";
	private final static String TMP_COMMENT_SAMPLE = "TMP_comment_sample";
	
	public int insert(Map<String, Object> params) throws Exception {
		executeInsert(COMMENT_SAMPLE, params);
		return getSeqVal(COMMENT_SAMPLE);
	}
	
	public void insertTMP(Map<String, Object> params) throws Exception {
		executeInsert(TMP_COMMENT_SAMPLE, params);
	}
	
	public void deleteTMP(Map<String, Object> params) throws Exception {
		executeDelete(TMP_COMMENT_SAMPLE, params);
	}
	
	public int TMPInsertFormality(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into "+COMMENT_SAMPLE+" ");
		sql.append("(create_user,                     ");
		sql.append("create_time,                           ");
		sql.append("dept_code,                              ");
		sql.append("dept_name,                              ");
		sql.append("d_type,                                 ");
		sql.append("sample_start_time,                      ");
		sql.append("sample_end_time,                        ");
		sql.append("sample_num,                             ");
		sql.append("sample_type,                            ");
		sql.append("comment_num,                            ");
		sql.append("comment_flag,                           ");
		sql.append("comment_user,                           ");
		sql.append("djbh,                                   ");
		sql.append("state,                                  ");
		sql.append("sample_patient_idorname,                ");
		sql.append("p_type,                                 ");
		sql.append("feibie,                                 ");
		sql.append("doctor,                                 ");
		sql.append("patient_state,                          ");
		sql.append("num_type,                               ");
		sql.append("patient_num_sample,                     ");
		sql.append("comment_finish_time,                    ");
		sql.append("drug_code,                              ");
		sql.append("cost_time,                              ");
		sql.append("IS_CL,                                  ");
		sql.append("CLTIME,                                 ");
		sql.append("drug_name,                              ");
		sql.append("doctor_name,                            ");
		sql.append("comment_way,                            ");
		sql.append("spcomment_unit,                         ");
		sql.append("drug_type,                              ");
		sql.append("diagnosis_desc,                         ");
		sql.append("special_type,                         ");
		sql.append("assign_type,                             ");
		sql.append("motif,                         ");
		sql.append("spcomment_code   )                      ");	
		sql.append("select b.create_user,                     ");
		sql.append("b.create_time,                           ");
		sql.append("b.dept_code,                              ");
		sql.append("b.dept_name,                              ");
		sql.append("b.d_type,                                 ");
		sql.append("b.sample_start_time,                      ");
		sql.append("b.sample_end_time,                        ");
		sql.append("b.sample_num,                             ");
		sql.append("b.sample_type,                            ");
		sql.append("b.comment_num,                            ");
		sql.append("b.comment_flag,                           ");
		sql.append("b.comment_user,                           ");
		sql.append("b.djbh,                                   ");
		sql.append("b.state,                                  ");
		sql.append("b.sample_patient_idorname,                ");
		sql.append("b.p_type,                                 ");
		sql.append("b.feibie,                                 ");
		sql.append("b.doctor,                                 ");
		sql.append("b.patient_state,                          ");
		sql.append("b.num_type,                               ");
		sql.append("b.patient_num_sample,                     ");
		sql.append("b.comment_finish_time,                    ");
		sql.append("b.drug_code,                              ");
		sql.append("b.cost_time,                              ");
		sql.append("b.IS_CL,                                  ");
		sql.append("b.CLTIME,                                 ");
		sql.append("b.drug_name,                              ");
		sql.append("b.doctor_name,                            ");
		sql.append("b.comment_way,                            ");
		sql.append("b.spcomment_unit,                         ");
		sql.append("b.drug_type,                              ");
		sql.append("b.diagnosis_desc,                         ");
		sql.append("b.special_type,                         ");
		sql.append("b.assign_type,                             ");
		sql.append(" ( ");
		sql.append("CASE  ");
		sql.append("WHEN isnull(c.Spcomment_name,'')='' THEN  ");
		sql.append("'【 常规点评 】 '   "); 
		sql.append("ELSE  ");
		sql.append("'【'+c.Spcomment_name + ' 】 '                     "); 
		sql.append("END)+ ");
		sql.append("(CASE                                                  "); 
		sql.append("WHEN                                                  "); 
		sql.append("	b.d_type = '1' THEN                               "); 
		sql.append("	'住院 '                                           "); 
		sql.append("WHEN b.d_type = '2' THEN                              "); 
		sql.append("'急诊 '                                               "); 
		sql.append("WHEN b.d_type = '3' THEN                              "); 
		sql.append("'门诊 '                                               "); 
		sql.append("WHEN b.d_type = '-1' THEN                             "); 
		sql.append("'门急诊 '                                             "); 
		sql.append("END +  b.sample_start_time +'至'+b.sample_end_time +  "); 
		sql.append("CASE                                                  "); 
		sql.append("	WHEN b.sample_type = '1' THEN                     "); 
		sql.append("	' 手动抽样结果'                                   "); 
		sql.append("	when b.sample_type = '2' then                     "); 
		sql.append("	' 随机抽样'                                       "); 
		sql.append("END) as motif,                   "); 	
		sql.append("b.spcomment_code                             ");
		sql.append(" from "+TMP_COMMENT_SAMPLE+" b ");
		sql.append(" left join hospital_common..DICT_SYS_Spcomment c  ");
		sql.append(" on b.Spcomment_code=c.Spcomment_code ");
		sql.append(" where GZID= :GZID ");
		execute(sql.toString(), params);
		return getSeqVal(COMMENT_SAMPLE);
	}

	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.*, ");
		sql.append(" (select b.name+'('+cast(count(c.HANDLER) as varchar(10))+')'+',' from   ");
		sql.append(" v_system_user b  (nolock)                                                  ");
		sql.append(" left join comment_sample_patients (nolock) c                       ");
		sql.append(" on b.id = c.handler                                                ");
		sql.append(" where                                                              ");
		sql.append(" a.comment_user+','                                                 ");
		sql.append(" like '%'+ b.id +',%'                                               ");
		sql.append(" and c.sample_id = a.id ");
		sql.append(" GROUP BY b.name,c.HANDLER                                               ");
		sql.append(" FOR XML PATH('')                                                   ");
		sql.append(" ) as fp_name                                                       ");
		sql.append(" from "+COMMENT_SAMPLE+"(nolock) a ");
		sql.append(" where a.state!='-9'");
		setParameter(params, "user_no", " and (a.comment_user+',' like '%'+ :user_id + ',%' or a.create_user= :user_no) ", sql);
		setParameter(params, "mintime", " and convert(datetime,a.create_time)>= convert(datetime,:mintime)", sql);
		setParameter(params, "maxtime", " and convert(datetime,a.create_time)<= convert(datetime,:maxtime)", sql);
		if (!CommonFun.isNe(params.get("datasouce")) && !("all".equals(params.get("datasouce")))) {
			sql.append(" and a.d_type = :datasouce ");
		}
		if (!CommonFun.isNe(params.get("p_typeFifter")) && !("all".equals(params.get("p_typeFifter")))) {
			sql.append(" and a.p_type = :p_typeFifter ");
		}
		setParameter(params, "fenpei", " and a.comment_user = :fenpei", sql);
		if (!CommonFun.isNe(params.get("dpzt")) && !("all".equals(params.get("dpzt")))) {
			sql.append(" and a.state = :dpzt ");
		}
		params.put("sort", "state asc,create_time desc");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record queryIsOwn(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) as counts from "+COMMENT_SAMPLE+"(nolock) a");
		//sql.append(" inner join v_system_user vsu on a.comment_user=vsu.id and vsu.no = :user_no ");
		//sql.append(" left join dict_his_drug drug on drug.p_key=a.drug_code ");
		sql.append(" where a.state!='-9' and a.comment_user+',' like '%'+ :user_id + ',%'  ");
		setParameter(params, "mintime", " and convert(datetime,a.create_time)>= convert(datetime,:mintime)", sql);
		setParameter(params, "maxtime", " and convert(datetime,a.create_time)<= convert(datetime,:maxtime)", sql);
		if (!CommonFun.isNe(params.get("datasouce")) && !("all".equals(params.get("datasouce")))) {
			sql.append(" and a.d_type = :datasouce ");
		}
		if (!CommonFun.isNe(params.get("p_typeFifter")) && !("all".equals(params.get("p_typeFifter")))) {
			sql.append(" and a.p_type = :p_typeFifter ");
		}
		setParameter(params, "fenpei", " and a.comment_user = :fenpei", sql);
		if (!CommonFun.isNe(params.get("dpzt")) && !("all".equals(params.get("dpzt")))) {
			sql.append(" and a.state = :dpzt ");
		}
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result queryHistory(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		params.put("sort", "comment_finish_time desc");
		sql.append("select a.*,vsu.name fp_name from "+COMMENT_SAMPLE+"(nolock)");
		sql.append(" a left join v_system_user vsu on a.comment_user=vsu.id ");
		sql.append(" where a.state = '2' ");
		sql.append(" and a.comment_user =:comment_user ");
		return executeQueryLimit(sql.toString(), params);
	}

	public int update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(COMMENT_SAMPLE, params, r);
	}
	
	public int updateBySamId(Map<String, Object> params) throws Exception {
		Record r = new Record();
		Record p = new Record();
		r.put("id", params.get("sample_id"));
		p.put("is_active", params.get("is_active"));
		return executeUpdate(COMMENT_SAMPLE, p, r);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		return executeDelete(COMMENT_SAMPLE, r);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from "+COMMENT_SAMPLE+"(nolock) where id=:id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getAll(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,dhu.user_name, ");
		sql.append(" (select ORDERTAGNAME+',' from DICT_SYS_ORDERS_TAG       ");
		sql.append(" where a.special_type + ',' like '%' + ORDERTAGBH + ',%' ");
		sql.append(" for xml path('')                                        ");
		sql.append(" ) special_types                                         ");
		/*sql.append(" ,stuff((select ','+name from iadscp..v_system_user v ");
		sql.append(" where no in(a.doctor) for XML PATH('')),1,1,''");
		sql.append(" ) as doctor_name ");*/
		sql.append("from "+COMMENT_SAMPLE+"(nolock) a ");
		sql.append(" left join dict_his_users (nolock) dhu on dhu.p_key = a.doctor ");
		sql.append(" where a.id=:id");
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public Result queryAssignUser(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.id comment_user,a.name,a.no,                    ");
		sql.append(" (select count(b.HANDLER) from comment_sample_patients  b ");
		sql.append(" where a.id = b.HANDLER and sample_id=:id) num            ");
		sql.append(" from                                                     ");
		sql.append(" v_system_user a (nolock)                                         ");
		sql.append(" where                                                    ");
		sql.append(" a.id in                                                  ");
		sql.append(" (select col from                                         ");
		sql.append(" [dbo].[split]                                            ");
		sql.append(" ((select comment_user from                               ");
		sql.append(" comment_sample (nolock)                                          ");
		sql.append(" where                                                    ");
		sql.append(" id = :id),','))                                          ");
		return executeQuery(sql.toString(), params);
	}

	public Result querySpecial_tags(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select                                 ");
		sql.append(" ordertagbh,ordertag_shuom,ordertag_show");
		sql.append(" from                                   ");
		sql.append(" DICT_SYS_ORDERS_TAG (nolock)                   ");
		sql.append(" where '"+params.get("special_type")+"' + ','             ");
		sql.append(" like '%' + ordertagbh + ',%'        ");
		sql.append(" and BEACTIVE = '是'                    ");
		return executeQuery(sql.toString(), params);
	}
}
