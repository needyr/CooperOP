package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.ProductmanagetService;

public class ChangeqdetailDao extends BaseDao{
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer count = new StringBuffer();
		sql.append(" 	select                                                                                                                                                         ");
		sql.append(" 	dbo.get_patient_xx(a.patient_id,a.visit_id) as patient_name,                                                                                                   ");
		sql.append(" 	a1.dept_name+' '+                                                                                                                                              ");
		sql.append(" 	case a.d_type when '1' then '[住院]' when '2' then '[门诊]' when '3' then '[急诊]' end as dept_name,                                                           ");
		sql.append(" 	d.level_name as check_level_name,                                                                                                                    ");
		sql.append(" 	c.sort_name,a.ordermessage, a.ordermessage_code,a.audit_source_type,                                                                                                                ");
		sql.append(" 	a.description,a.reference,a.check_datetime,a.doctor_advice,                                                                                                    ");
		sql.append(" 	a.auto_audit_sort_id,a.auto_audit_id,a.auto_audit_level,a.related_drugs_pkey,                                                                                  ");
		sql.append(" 	a.patient_id,a.visit_id,a.order_no,a.order_sub_no,a.order_code,                                                                                                ");
		sql.append(" 	a.yxk_advice as yaoshi_advice,a.yaoshi_name,a.yaoshi_audit_time,                                                                                               ");
		sql.append(" 	case when e.sort_id is not null then '已经调整' else '结果调整' end as is_shenc_pass,                                                                          ");
		sql.append(" 	case when e.is_add_advice is not null then '已经调整' else '添加意见' end as is_add_advice,                                                                          ");
		sql.append("         a.check_result_info_id,                                                                                                                                   ");
		sql.append(" 	f.level_name as shenc_change_level_name,                                                                                                             ");
		sql.append(" 	e.shenc_pass_ren,                                                                                                                                              ");
		sql.append(" 	e.shenc_pass_pharmacist_advice,                                                                                                                                ");
		sql.append(" 	e.shenc_pass_time,a.drug_p_key_1                                                                                                                                          ");
		sql.append(" 	from ywlsb_audit_orders_check_result a(nolock)                                                                                                                 ");
		sql.append(" 	inner join ywlsb_orders_drugname a1(nolock) on a.patient_id=a1.patient_id and a.visit_id=a1.visit_id and a.order_no=a1.order_no                                ");
		sql.append(" INNER JOIN  map_common_regulation b ( nolock ) on a.auto_audit_sort_id= b.thirdt_code AND a.auto_audit_type= b.check_type ");
		sql.append(" and b.product_code = '"+ProductmanagetService.IPC+"'");
		sql.append(" INNER JOIN sys_common_regulation c ( nolock ) on b.sys_p_key= c.p_key ");
		sql.append(" INNER JOIN sys_check_level d ( nolock ) on a.auto_audit_level= d.level_code ");
		sql.append(" and d.product_code = '"+ProductmanagetService.IPC+"'");
		sql.append(" 	left join (select sort_id,sort_name,description,min(shenc_change_level) as shenc_change_level,min(shenc_pass_ren) as shenc_pass_ren,                           ");
		sql.append(" 	min(shenc_pass_pharmacist_advice) as shenc_pass_pharmacist_advice,min(shenc_pass_time) as shenc_pass_time,min(pharmacist_todoctor_advice) as is_add_advice                                                      ");
		sql.append(" 	from pharmacist_check_pass (nolock) where beactive ='是' group by sort_name,sort_id,description) e                                                             ");
		sql.append(" 	on a.sort_id=e.sort_id and replace(replace(replace(replace(replace(replace(replace(a.description,CHAR(13),''), CHAR (10),''),'[',''),'<p>',''),'</p>',''),'<br>',''),' ','') like replace(replace(replace(replace(replace(replace(replace(e.description,CHAR(13),''), CHAR (10),''),'[',''),'<p>',''),'</p>',''),'<br>',''),' ','')+'%' ");
		sql.append(" 	left join sys_check_level f(nolock) on e.shenc_change_level=f.level_code                                                                              ");
		sql.append(" and f.product_code = '"+ProductmanagetService.IPC+"'");
		sql.append(" 	where                                                                                                                                                          ");
		sql.append("    a.check_datetime between :s_time and :e_time                                                                                           ");
		if(!"全部".equals(params.get("dept_name"))) {
			sql.append(" 	and a1.dept_name like :dept_name+'%'  ");
		}
		sql.append(" 	and isnull(c.sort_name,'') !='' and isnull(c.sort_name,'') = case when rtrim(:sort_name) !='全部' and rtrim(:sort_name) !='' then rtrim(:sort_name) else isnull(c.sort_name,'') end ");
		sql.append(" 	and a.p_type like case when :p_type='0' then a.p_type else :p_type end                                                                                         ");
		sql.append(" 	and a.d_type like case when :d_type='0' then a.d_type else :d_type end                                                                                         ");
		//sql.append(" and a.description not like '%药师建议：%' ");
		count.append(" SELECT COUNT(1) count                                                                                                                             ");
		count.append(" FROM                                                                                                                                         ");
		count.append(" ywlsb_audit_orders_check_result a ( nolock )                                                                                                 ");
		count.append(" INNER JOIN ywlsb_orders_drugname a1 ( nolock ) ON a.patient_id= a1.patient_id AND a.visit_id= a1.visit_id AND a.order_no= a1.order_no        ");
		count.append(" INNER JOIN  map_common_regulation b ( nolock ) on a.auto_audit_sort_id= b.thirdt_code AND a.auto_audit_type= b.check_type ");
		count.append(" and b.product_code = '"+ProductmanagetService.IPC+"'");
		count.append(" INNER JOIN sys_common_regulation c ( nolock ) on b.sys_p_key= c.p_key ");
		count.append(" INNER JOIN sys_check_level d ( nolock ) on a.auto_audit_level= d.level_code ");
		count.append(" and d.product_code = '"+ProductmanagetService.IPC+"'");
		count.append(" WHERE                                                                                                                                        ");
		count.append(" 	a.check_datetime between '"+params.get("s_time")+"' and '"+params.get("e_time")+"'                                                                                           ");
		count.append(" 	and isnull(c.sort_name,'') !='' and isnull(c.sort_name,'') = case when rtrim('"+params.get("sort_name")+"') !='全部' and rtrim('"+params.get("sort_name")+"') !='' then rtrim('"+params.get("sort_name")+"') else isnull(c.sort_name,'') end ");
		count.append(" 	and a.p_type like case when "+params.get("p_type")+"='0' then a.p_type else "+params.get("p_type")+" end                                                                                         ");
		count.append(" 	and a.d_type like case when "+params.get("d_type")+"='0' then a.d_type else "+params.get("d_type")+" end                                                                                         ");
		//count.append(" and a.description not like '%药师建议：%' ");
		if(!"全部".equals(params.get("dept_name"))) {
			count.append(" 	and a1.dept_name like '"+params.get("dept_name")+"'+'%'  ");
		}
		if(!CommonFun.isNe(params.get("patient"))) {
			params.put("patient", "%"+params.get("patient")+"%");
			sql.append(" 	and (a.patient_id like :patient or dbo.get_patient_xx(a.patient_id,a.visit_id) like :patient)  ");
			count.append(" 	and (a.patient_id like :patient or dbo.get_patient_xx(a.patient_id,a.visit_id) like :patient)  ");
		}
		if(!CommonFun.isNe(params.get("level"))) {
			//String [] s= (String[]) params.get("level");
			sql.append(" and a.auto_audit_level in (:level) ");
			count.append(" and a.auto_audit_level in ");
			if (params.get("level") instanceof String) {
				count.append("("+params.get("level")+")");
			}else {
				String[] pa = (String[]) params.get("level");
				count.append("(");
				int j = 0;
				for (int i=0;i<pa.length;i++) {
					if (j==0) {
						j=1;
						count.append("'"+pa[i]+"'");
					}else {
						count.append(",'"+pa[i]+"'");
					}
				}
				count.append(")");
			}
		}
		
		if(!CommonFun.isNe(params.get("drug_name_w"))) {
			params.put("drug_name_w", "%" + params.get("drug_name_w") + "%");
			sql.append(" and a.related_drugs_show  like :drug_name_w ");
			count.append(" and a.related_drugs_show  like '"+params.get("drug_name_w")+"' ");
		}
		
		if(!CommonFun.isNe(params.get("is_tiaoz_audit"))) {
			if(params.get("is_tiaoz_audit").equals("0")) {
				sql.append(" and e.sort_id is null ");
				//sql.append(" and e.sort_id is null ");
			}else if(params.get("is_tiaoz_audit").equals("1")) {
				sql.append(" and e.sort_id is not null ");
				//sql.append(" and e.sort_id is not null ");
			}
		}
		//return executeQueryLimit(sql.toString(), params);
		return executeQueryLimit(sql.toString(), params, count.toString());                                                                                                                          
	}
	
	public Result query_init_excel(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,case when a.d_type=1 and a.p_type=1 then '住院医嘱' when a.d_type=1 and a.p_type=2 then '住院处方'  else '门诊处方 '  end as kdlx,  ");
		sql.append("stuff((select ','+DIAGNOSIS_DESC from hospital_common..his_in_diagnosis(nolock) ");
		sql.append("where a.patient_id=patient_id and a.visit_id=visit_id  ");
		sql.append("group by DIAGNOSIS_DESC for xml path('')   ");
		sql.append("),1,1,'') as diagnosis_name,     ");
		sql.append("vhipa.patient_name as patient_name_d,vhipa.sex,vhipa.age ");
		sql.append("from TMP_audit_result_change(nolock) a ");
		sql.append("left join v_his_in_patientvisit_all (nolock) vhipa ");
		sql.append("on a.patient_id=vhipa.patient_id and a.visit_id=vhipa.visit_id ");
		sql.append("where 1=1 ");
		if(!CommonFun.isNe(params.get("level"))) {
			sql.append(" and auto_audit_level in (:level) ");
		}
		if(!CommonFun.isNe(params.get("drug_name_w"))) {
			params.put("drug_name_w", "%" + params.get("drug_name_w") + "%");
			sql.append(" and related_drugs_show  like :drug_name_w ");
		}
		if(!"全部".equals(params.get("dept_name"))) {
			sql.append(" 	and dept_name like '"+params.get("dept_name")+"'+'%'  ");
		}
		sql.append(" and check_datetime between :s_time and :e_time  ");
		//sql.append(" and isnull(sort_name,'') !='' and isnull(sort_name,'') = case when rtrim(:sort_name) !='全部' and rtrim(:sort_name) !='' then rtrim(:sort_name) else isnull(sort_name,'') end ");
		if(!CommonFun.isNe(params.get("sort_name")) && !"全部".equals(params.get("sort_name"))) {
			sql.append(" and rtrim(sort_name)=rtrim(:sort_name) ");
		}
		if(!"0".equals(params.get("p_type"))) {
			sql.append(" and p_type= :p_type ");
		}
		if (!CommonFun.isNe(params.get("d_type"))) {
			if (params.get("d_type") instanceof String) {
				sql.append(" and d_type=:d_type ");
			}else {
				sql.append(" and d_type in (:d_type) ");
			}
		}
		sql.append(" and create_user_no= :user_no ");
		if(!CommonFun.isNe(params.get("is_tiaoz_audit"))) {
			if(params.get("is_tiaoz_audit").equals("0")) {
				sql.append(" and change_sort_id is null ");
				//sql.append(" and e.sort_id is null ");
			}else if(params.get("is_tiaoz_audit").equals("1")) {
				sql.append(" and change_sort_id is not null ");
				//sql.append(" and e.sort_id is not null ");
			}
		}
		return executeQueryLimit(sql.toString(), params);                                                                                                                      
	}

	public void init(Map<String, Object> params) throws Exception {
		execute("exec audit_result_change :s_time,:e_time,:user_no", params);
	}

	public Result query_init(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select *,  ");
		sql.append("stuff((select ','+DIAGNOSIS_DESC from hospital_common..his_in_diagnosis(nolock) ");
		sql.append("where a.patient_id=patient_id and a.visit_id=visit_id  ");
		sql.append("group by DIAGNOSIS_DESC for xml path('')   ");
		sql.append("),1,1,'') as diagnosis_name     ");
		sql.append("from TMP_audit_result_change(nolock) a ");
		sql.append(" where 1=1 ");
		if(!CommonFun.isNe(params.get("level"))) {
			sql.append(" and auto_audit_level in (:level) ");
		}
		if(!CommonFun.isNe(params.get("drug_name_w"))) {
			params.put("drug_name_w", "%" + params.get("drug_name_w") + "%");
			sql.append(" and related_drugs_show  like :drug_name_w ");
		}
		if(!"全部".equals(params.get("dept_name"))) {
			sql.append(" 	and dept_name like '"+params.get("dept_name")+"'+'%'  ");
		}
		sql.append(" and check_datetime between :s_time and :e_time  ");
		//sql.append(" and isnull(sort_name,'') !='' and isnull(sort_name,'') = case when rtrim(:sort_name) !='全部' and rtrim(:sort_name) !='' then rtrim(:sort_name) else isnull(sort_name,'') end ");
		if(!CommonFun.isNe(params.get("sort_name")) && !"全部".equals(params.get("sort_name"))) {
			sql.append(" and rtrim(sort_name)=rtrim(:sort_name) ");
		}
		if(!"0".equals(params.get("p_type"))) {
			sql.append(" and p_type= :p_type ");
		}
		if (!CommonFun.isNe(params.get("d_type"))) {
			if (params.get("d_type") instanceof String) {
				sql.append(" and d_type=:d_type ");
			}else {
				sql.append(" and d_type in (:d_type) ");
			}
		}
		if (!CommonFun.isNe(params.get("doctor"))) {
			params.put("doctor",((String)params.get("doctor")).split(","));
			sql.append(" and doctor in (:doctor) ");
		}
		sql.append(" and create_user_no= :user_no ");
		if(!CommonFun.isNe(params.get("is_tiaoz_audit"))) {
			if(params.get("is_tiaoz_audit").equals("0")) {
				sql.append(" and change_sort_id is null ");
				//sql.append(" and e.sort_id is null ");
			}else if(params.get("is_tiaoz_audit").equals("1")) {
				sql.append(" and change_sort_id is not null ");
				//sql.append(" and e.sort_id is not null ");
			}
		}
		return executeQueryLimit(sql.toString(), params);
	}

	public void updateTmpIsAdvice(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" update a                                                        ");
		sql.append(" set is_add_advice='已经调整' ,a.change_sort_id=b.sort_id, ");
		sql.append(" a.shenc_change_level_name=c.level_name,a.shenc_pass_ren=b.shenc_pass_ren, ");
		sql.append(" a.shenc_pass_pharmacist_advice=b.shenc_pass_pharmacist_advice, ");
		sql.append(" a.shenc_pass_time=b.shenc_pass_time ");
		sql.append(" from TMP_audit_result_change a");
		sql.append(" left join hospital_common..pharmacist_check_pass b on");
		sql.append(" REPLACE(REPLACE (REPLACE (REPLACE (REPLACE (                                          ");
		sql.append(" REPLACE ( REPLACE (a.description, CHAR ( 13 ), '' ), CHAR ( 10 ), '' ),                 ");
		sql.append(" '[','' ),'<p>',''),'</p>','' ),'<br>',''),' ','')                                     ");
		sql.append(" LIKE REPLACE (REPLACE (REPLACE (REPLACE (REPLACE(REPLACE(REPLACE(                     ");
		sql.append(" b.description, CHAR (13),''),CHAR(10),''),'[','' ),'<p>','' ),'</p>','' ),'<br>',''),' ','' ) + '%'");
		sql.append(" left join hospital_common..sys_check_level c on b.shenc_change_level=c.level_code and product_code='"+ProductmanagetService.IPC+"'");
		sql.append(" where                                                                                 ");
		sql.append(" REPLACE(REPLACE (REPLACE (REPLACE (REPLACE (                                          ");
		sql.append(" REPLACE ( REPLACE (a.description, CHAR ( 13 ), '' ), CHAR ( 10 ), '' ),                 ");
		sql.append(" '[','' ),'<p>',''),'</p>','' ),'<br>',''),' ','')                                     ");
		sql.append(" LIKE REPLACE (REPLACE (REPLACE (REPLACE (REPLACE(REPLACE(REPLACE(                     ");
		sql.append(" (select top 1 description                                                             ");
		sql.append(" from TMP_audit_result_change(nolock)                                                  ");
		sql.append(" where auto_audit_id= :auto_audit_id                               ");
		sql.append(" and check_result_info_id= :check_result_info_id GROUP BY description)     ");
		sql.append(" , CHAR (13),''),CHAR(10),''),'[','' ),'<p>','' ),'</p>','' ),'<br>',''),' ','' ) + '%'");
		execute(sql.toString(), params);
	}

	public void updateTmpIsPass(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" update a                                                        ");
		sql.append(" set is_shenc_pass='已经调整' ,a.change_sort_id=b.sort_id, ");
		sql.append(" a.shenc_change_level_name=c.level_name,a.shenc_pass_ren=b.shenc_pass_ren, ");
		sql.append(" a.shenc_pass_pharmacist_advice=b.shenc_pass_pharmacist_advice, ");
		sql.append(" a.shenc_pass_time=b.shenc_pass_time ");
		sql.append(" from TMP_audit_result_change a");
		sql.append(" left join hospital_common..pharmacist_check_pass b on");
		sql.append(" REPLACE(REPLACE (REPLACE (REPLACE (REPLACE (                                          ");
		sql.append(" REPLACE ( REPLACE (a.description, CHAR ( 13 ), '' ), CHAR ( 10 ), '' ),                 ");
		sql.append(" '[','' ),'<p>',''),'</p>','' ),'<br>',''),' ','')                                     ");
		sql.append(" LIKE REPLACE (REPLACE (REPLACE (REPLACE (REPLACE(REPLACE(REPLACE(                     ");
		sql.append(" b.description, CHAR (13),''),CHAR(10),''),'[','' ),'<p>','' ),'</p>','' ),'<br>',''),' ','' ) + '%'");
		sql.append(" left join hospital_common..sys_check_level c on b.shenc_change_level=c.level_code and product_code='"+ProductmanagetService.IPC+"'");
		sql.append(" where                                                                                 ");
		sql.append(" REPLACE(REPLACE (REPLACE (REPLACE (REPLACE (                                          ");
		sql.append(" REPLACE ( REPLACE (a.description, CHAR ( 13 ), '' ), CHAR ( 10 ), '' ),                 ");
		sql.append(" '[','' ),'<p>',''),'</p>','' ),'<br>',''),' ','')                                     ");
		sql.append(" LIKE REPLACE (REPLACE (REPLACE (REPLACE (REPLACE(REPLACE(REPLACE(                     ");
		sql.append(" (select top 1 description                                                             ");
		sql.append(" from TMP_audit_result_change(nolock)                                                  ");
		sql.append(" where auto_audit_id= :auto_audit_id                               ");
		sql.append(" and check_result_info_id= :check_result_info_id GROUP BY description)     ");
		sql.append(" , CHAR (13),''),CHAR(10),''),'[','' ),'<p>','' ),'</p>','' ),'<br>',''),' ','' ) + '%'");
		execute(sql.toString(), params);
	}

	public void updateTmpAll(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" update a                                                        ");
		sql.append(" set is_shenc_pass='已经调整', is_add_advice='已经调整' ,a.change_sort_id=b.sort_id, ");
		sql.append(" a.shenc_change_level_name=c.level_name,a.shenc_pass_ren=b.shenc_pass_ren, ");
		sql.append(" a.shenc_pass_pharmacist_advice=b.shenc_pass_pharmacist_advice, ");
		sql.append(" a.shenc_pass_time=b.shenc_pass_time ");
		sql.append(" from TMP_audit_result_change a");
		sql.append(" left join hospital_common..pharmacist_check_pass b on");
		sql.append(" REPLACE(REPLACE (REPLACE (REPLACE (REPLACE (                                          ");
		sql.append(" REPLACE ( REPLACE (a.description, CHAR ( 13 ), '' ), CHAR ( 10 ), '' ),                 ");
		sql.append(" '[','' ),'<p>',''),'</p>','' ),'<br>',''),' ','')                                     ");
		sql.append(" LIKE REPLACE (REPLACE (REPLACE (REPLACE (REPLACE(REPLACE(REPLACE(                     ");
		sql.append(" b.description, CHAR (13),''),CHAR(10),''),'[','' ),'<p>','' ),'</p>','' ),'<br>',''),' ','' ) + '%'");
		sql.append(" left join hospital_common..sys_check_level c on b.shenc_change_level=c.level_code and product_code='"+ProductmanagetService.IPC+"'");
		sql.append(" where                                                                                 ");
		sql.append(" REPLACE(REPLACE (REPLACE (REPLACE (REPLACE (                                          ");
		sql.append(" REPLACE ( REPLACE (a.description, CHAR ( 13 ), '' ), CHAR ( 10 ), '' ),                 ");
		sql.append(" '[','' ),'<p>',''),'</p>','' ),'<br>',''),' ','')                                     ");
		sql.append(" LIKE REPLACE (REPLACE (REPLACE (REPLACE (REPLACE(REPLACE(REPLACE(                     ");
		sql.append(" (select top 1 description                                                             ");
		sql.append(" from TMP_audit_result_change(nolock)                                                  ");
		sql.append(" where auto_audit_id= :auto_audit_id                               ");
		sql.append(" and check_result_info_id= :check_result_info_id GROUP BY description)     ");
		sql.append(" , CHAR (13),''),CHAR(10),''),'[','' ),'<p>','' ),'</p>','' ),'<br>',''),' ','' ) + '%'");
		execute(sql.toString(), params);
	}
}
