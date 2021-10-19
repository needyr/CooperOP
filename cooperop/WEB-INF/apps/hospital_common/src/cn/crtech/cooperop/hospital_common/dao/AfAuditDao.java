package cn.crtech.cooperop.hospital_common.dao;

import java.util.Date;
import java.util.Map;
import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

/**
 * @ClassName: AfauditDao
 * @Description: 事后审查dao
 * @author: 魏圣峰
 * @date: 2019年1月9日 下午4:57:57
 */
public class AfAuditDao extends BaseDao {
	/** 事后审查队列 */
	public static final String TABLE_NAME = "audit_source_queue";
	/** 事后审查患者信息 */
	public static final String TABLE_PATIENT = "audit_source_patient";
	/** 事后审查医嘱信息 */
	public static final String TABLE_ROW = "audit_source_row";
	/** 审查记录主表 */
	public static final String TABLE_AUTO_COMMON = "v_auto_common_use";
	/** 审查规则表 */
	public static final String TABLE_SYS_COMMON_REGULATION = "sys_common_regulation";
	/** 产品表 */
	public static final String TABLE_SYS_Products = "system_product";
	/** 合理用药审查主表 */
	public static final String TABLE_AUTO_AUDIT = "auto_audit";
	/** his患者就诊记录 */
	public static final String TABLE_HIS_PATIENTVISIT = "v_his_in_patientvisit_all";
	/** 事后审查患者医嘱 */
	public static final String TABLE_HIS_ORDERS = "v_his_in_orders_aftersc";
	/** 药品code字典 */
	public static final String TABLE_DICT_DRUG_MX = "dict_his_drug_mx";
	/** 药品字典 */
	public static final String TABLE_DICT_DRUG = "v_dict_drug";
	/** 科室字典 */
	public static final String TABLE_DICT_DEPTMENT = "dict_his_deptment";
	/** 费别字典 */
	public static final String TABLE_DICT_FRIBIE = "dict_his_feibie";
	/** 添加事后审查队列存储过程 */
	public static final String PROC_ADD_AUDIT_QUEUE = "proc_selectsave_after_auto_audit";
	
	/**
	 * @author: 魏圣峰
	 * @description: 查询事后审查队列信息
	 * @param: params Map 筛选条件
	 * @return: Result      
	 * @throws: Exception
	 */
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from " + TABLE_NAME);
		sql.append(" (nolock) where  1 = 1 ");
		if(!CommonFun.isNe(params.get("start_time"))) {sql.append("and createtime >= :start_time ");}
		if(!CommonFun.isNe(params.get("end_time"))) {sql.append("and createtime <= :end_time ");}
		return executeQueryLimit(sql.toString(), params);
	}
	
	/**
	 * @author: 魏圣峰
	 * @description: 查询事后审查队列详情信息
	 * @param: params Map audit_queue_id
	 * @return: Result      
	 * @throws Exception
	 */
	public Result queryDetails(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" 	select                            ");
		sql.append(" 	q.id queue_id,                    ");
		sql.append(" 	q.state,                          ");
		sql.append(" 	q.execute_date,                   ");
		sql.append(" 	q.createtime,                     ");
		sql.append(" 	q.audit_times,                    ");
		sql.append(" 	q.audit_end_time,                 ");
		sql.append(" 	p.id source_patient_id,           ");
		sql.append(" 	p.patient_id,                     ");
		sql.append(" 	p.visit_id,                       ");
		sql.append(" 	p.patient_name,                   ");
		sql.append(" 	p.p_type,                         ");
		sql.append(" 	p.d_type,                         ");
		sql.append(" 	p.dept_name,                      ");
		sql.append(" 	p.doctor_no,                      ");
		sql.append(" 	p.doctor_name,                    ");
		sql.append(" 	co.state final_state,             ");
		sql.append(" 	co.id common_id,                  ");
		sql.append(" 	co.cost_time,                     ");
		sql.append(" 	co.end_time common_end_time,      ");
		sql.append(" 	co.create_time common_start_time  ");
		sql.append(" from                                 ");
		sql.append(" 	audit_source_queue q ( nolock )   ");
		sql.append(" 	inner join audit_source_patient p ( nolock ) on q.id = p.audit_queue_id ");
		sql.append(" 	LEFT JOIN IADSCP..system_department sd ON p.dept_code = sd.code");
		sql.append(" 	inner join v_auto_common_use co ( nolock ) on co.audit_source_fk= ( p.id+ '#' + convert ( varchar ( 4 ), ");
		if(CommonFun.isNe(params.get("audit_times"))) {
			sql.append(" q.audit_times ");
		}else {
			sql.append(" "+params.get("audit_times")+" ");
		}
		sql.append(" ) ) "); 
		sql.append(" where q.id = '"+params.get("audit_queue_id")+"' ");
		if(!CommonFun.isNe(params.get("resultSerch"))) {//审查结果筛选
			sql.append(" and co.state in('"+params.get("resultSerch").toString().replaceAll(",", "','")+"')");
		}
		if(!CommonFun.isNe(params.get("patient"))) {//审查结果筛选
			params.put("patient", "%" + params.get("patient") + "%");
			sql.append(" and ( p.patient_id like '"+params.get("patient")+"' or p.patient_name like '"+params.get("patient")+"') ");
		}
		if(!CommonFun.isNe(params.get("dept"))) {//审查结果筛选
			params.put("dept", "%" + params.get("dept") + "%");
			sql.append(" and ( p.dept_code like '"+params.get("dept")+"' or p.dept_name like '"+params.get("dept")+"')");
		}
		if (!CommonFun.isNe(params.get("jigou_id"))){
			sql.append(" and ( sd.jigid = :jigou_id");
			//患者部门代码为空则不进行多机构判断
			sql.append(" or p.dept_code is null or p.dept_code='' )");
		}
		StringBuffer count = new StringBuffer();
		count.append(" 	select count(1)                     ");
		count.append("  from                                 ");
		count.append(" 	audit_source_queue q ( nolock )   ");
		count.append(" 	inner join audit_source_patient p ( nolock ) on q.id = p.audit_queue_id ");
		count.append(" 	LEFT JOIN IADSCP..system_department sd ON p.dept_code = sd.code");
		count.append(" 	inner join v_auto_common_use co ( nolock ) on co.audit_source_fk= ( p.id+ '#' + convert ( varchar ( 4 ), ");
		if(CommonFun.isNe(params.get("audit_times"))) {
			count.append(" q.audit_times ");
		}else {
			count.append(" "+params.get("audit_times")+" ");
		}
		count.append(" ) ) "); 
		count.append(" where q.id = '"+params.get("audit_queue_id")+"' ");
		if(!CommonFun.isNe(params.get("resultSerch"))) {//审查结果筛选
			count.append(" and co.state in('"+params.get("resultSerch").toString().replaceAll(",", "','")+"')");
		}
		if(!CommonFun.isNe(params.get("patient"))) {//审查结果筛选
			count.append(" and ( p.patient_id like '"+params.get("patient")+"' or p.patient_name like '"+params.get("patient")+"') ");
		}
		if(!CommonFun.isNe(params.get("dept"))) {//审查结果筛选
			count.append(" and ( p.dept_code like '"+params.get("dept")+"' or p.dept_name like '"+params.get("dept")+"')");
		}
		if (!CommonFun.isNe(params.get("jigou_id"))){
			count.append(" and (sd.jigid = :jigou_id");
			//患者部门代码为空则不进行多机构判断
			count.append(" or p.dept_code is null or p.dept_code='')");
		}
		return executeQueryLimit(sql.toString(), params, count.toString());
	}
	
	/**
	 * @author: 魏圣峰
	 * @description: 获取统计信息
	 * @param: params Map audit_queue_id
	 * @return: Record      
	 * @throws: Exception
	 */
	public Record getStatistics(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select");
		sql.append(" sum(case when co.state='Y' then 1 else 0 end) sum_y,");// 通过数		
		sql.append(" sum(case when co.state='N' then 1 else 0 end) sum_n,");//驳回数		
		sql.append(" sum(case when co.state='T' then 1 else 0 end) sum_t,");//提示数	
		sql.append(" sum(case when co.state='Q' then 1 else 0 end) sum_q,");//待决策数			
		sql.append(" count(0) data_count,");//总数		
		sql.append(" max(cast(co.cost_time as int)) max_elapsed_time,");//最高审查耗时
		sql.append(" min(cast(co.cost_time as int)) min_elapsed_time,");//最低审查耗时
		sql.append(" avg(cast(co.cost_time as int)) avg_elapsed_time,");//平均审查耗时
		sql.append(" sum(cast(co.cost_time as int)) sum_elapsed_time");//总耗时
		sql.append(" from "+TABLE_NAME+" q(nolock) ");
		sql.append(" inner join "+TABLE_PATIENT+" p(nolock) ");
		sql.append(" on q.id = p.audit_queue_id");
		sql.append(" inner join v_auto_common_use co ( nolock ) on co.audit_source_fk= ( p.id+ '#' + convert ( varchar ( 4 ), ");
		if(CommonFun.isNe(params.get("audit_times"))) {
			sql.append(" q.audit_times ");
		}else {
			sql.append("'" + params.get("audit_times") +"'");
		}
		sql.append(" ) ) "); 
		sql.append(" where q.id = '"+params.get("audit_queue_id")+"' ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	/**
	 * @author: 魏圣峰
	 * @description: 判断当前审查规则是否有修改
	 * @param: params Map      
	 * @return: int result 0未更新 1有更新     
	 * @throws: Exception
	 */
	public int isUpdateRegulation(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select case when");
		sql.append(" (select max(co.create_time)");
		sql.append(" from "+TABLE_NAME+" q(nolock) ");
		sql.append(" left join "+TABLE_PATIENT+" p(nolock) ");
		sql.append(" on q.id = p.audit_queue_id");
		sql.append(" left join "+TABLE_AUTO_COMMON+" co(nolock) ");
		sql.append(" on (p.id+'#'+cast(q.audit_times as varchar(5)))=co.audit_source_fk)");
		sql.append(" >");
		sql.append(" (select max(re.update_time) from "+TABLE_SYS_COMMON_REGULATION+" re(nolock) )");
		sql.append(" then '0'");
		sql.append(" else '1'");
		sql.append(" end result");
		return executeQuerySingleRecord(sql.toString(), params).getInt("result");
	}
	
	/**
	 * @author: 魏圣峰
	 * @description: 改变事后审查队列状态
	 * @param: params Map  state，队列id     
	 * @return: int   
	 * @throws: Exception
	 */
	public int changeState(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.get("id"));
		return executeUpdate(TABLE_NAME,params,r);
	}
	
	/**
	 * @author: 魏圣峰
	 * @description: 获取临时筛选队列
	 * @param: params Map 筛选条件  
	 * @return: Result      
	 * @throws: Exception
	 */
	public Result queryTmpQueue(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct");
		if(!CommonFun.isNe(params.get("qMaxCount"))) {sql.append(" top "+params.get("qMaxCount"));}//队列上限
		sql.append(" a.patient_id,a.visit_id,b.admission_datetime,b.charge_type,b.discharge_datetime,");
		sql.append(" c.feibie_name,");
		sql.append("b.patient_name,b.sex,b.birthday,b.patient_no,");
		//加入case when (isdate(b.birthday) != 0 and isdate(a.admission_datetime) != 0) 解决不规范日期导致的日期转换异常
		sql.append("(case when (isdate(b.birthday) != 0 and isdate(b.admission_datetime) != 0) then dbo.fun_get_age_enddate(b.birthday,b.discharge_datetime) else '未知' end) as age,");
		sql.append("(case when (isdate(b.birthday) != 0 and isdate(b.admission_datetime) != 0) then dbo.fun_get_ts(b.admission_datetime,b.discharge_datetime) else '未知' end) as ts,");
		sql.append("b.d_type,a.p_type,a.doctor doctor_name,a.doctor_no doctor_code,a.dept_code,a.dept_name");
		sql.append(" from "+TABLE_HIS_ORDERS+" a(nolock)");
		sql.append(" inner join "+TABLE_HIS_PATIENTVISIT+" b(nolock) on a.patient_id=b.patient_id and a.visit_id = b.visit_id");
		sql.append(" left join "+TABLE_DICT_FRIBIE+" c(nolock) on c.feibie_code=b.charge_type");
		sql.append(" left join "+TABLE_DICT_DEPTMENT+" d(nolock) on b.dept_in=d.dept_code");
		sql.append(" where 1=1");
		if(!CommonFun.isNe(params.get("patient"))){sql.append(" and a.patient_id = :patient");}//患者
		if(!CommonFun.isNe(params.get("p_sex"))){//患者性别
			if("0".equals(params.get("p_sex"))){sql.append(" and (b.sex != '男' and b.sex != '女')");}//未知
			else{sql.append(" and b.sex = :p_sex");}
		}
		if(!CommonFun.isNe(params.get("p_type"))){sql.append(" and a.p_type = :p_type");}//开药类型
		if(!CommonFun.isNe(params.get("d_type"))){sql.append(" and b.d_type = :d_type");}//就诊类型
        if(!CommonFun.isNe(params.get("doctorfifter"))){//医生
        	sql.append(" and a.doctor_no in(");
        	String s[]=params.get("doctorfifter").toString().split(",");
        	for(int i=0;i<s.length;i++) {sql.append("'"+s[i]+"'");if(i<s.length-1) {sql.append(",");}}
        	sql.append(") ");
        }
        if(!CommonFun.isNe(params.get("deptfifter"))){//科室
        	sql.append(" and a.dept_code in(");
        	String s[]=params.get("deptfifter").toString().split(",");
        	for(int i=0;i<s.length;i++) {sql.append("'"+s[i]+"'");if(i<s.length-1) {sql.append(",");}}
        	sql.append(") ");
        }
        if(!CommonFun.isNe(params.get("drugfifter"))){//药品
        	sql.append(" and a.order_code in(");
        	String s[]=params.get("drugfifter").toString().split(",");
        	for(int i=0;i<s.length;i++) {sql.append("'"+s[i]+"'");if(i<s.length-1) {sql.append(",");}}
        	sql.append(") ");
        }
        if(!CommonFun.isNe(params.get("feibiefifter"))){//费别
        	sql.append(" and b.charge_type in(");
        	String s[]=params.get("feibiefifter").toString().split(",");
        	for(int i=0;i<s.length;i++) {sql.append("'"+s[i]+"'");if(i<s.length-1) {sql.append(",");}}
        	sql.append(") ");
        }
        if(!CommonFun.isNe(params.get("mintime"))&&!CommonFun.isNe(params.get("maxtime")))
        {sql.append(" and (b.admission_datetime >= :mintime"+" and b.admission_datetime <= :maxtime)");}//入院时间
        if(!CommonFun.isNe(params.get("p_min_age"))&&!CommonFun.isNe(params.get("p_max_age"))){//就诊年龄
        	//加入case when (isdate(b.birthday) != 0 and isdate(a.admission_datetime) != 0) 解决不规范日期导致的日期转换异常
        	sql.append(" and (:p_min_age <= (case when (isdate(b.birthday) != 0 and isdate(b.admission_datetime) != 0) then datediff(year,b.birthday,b.admission_datetime) else 0 end)");
            sql.append(" and :p_max_age >= (case when (isdate(b.birthday) != 0 and isdate(b.admission_datetime) != 0) then datediff(year,b.birthday,b.admission_datetime) else 0 end))");
        }
		if(CommonFun.isNe(params.get("isInclude"))) {sql.append(" and (select count(1) as num from "+TABLE_NAME+" qp (nolock) where a.patient_id=qp.patient_id and b.visit_id=qp.visit_id ) = 0");}//是否包含已有队列
		return executeQueryLimit(sql.toString(), params);
	}
	
	/**
	 * @author: 魏圣峰
	 * @description: 获取医嘱信息
	 * @param: params Map patient_id和visit_id
	 * @return: Result      
	 * @throws: Exception
	 */
	public Result queryDrugList(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select");
		sql.append(" ord.*,");
		sql.append("c.drug_message,");
		sql.append("mx.xiangm,mx.value xmz");
		sql.append(" from "+TABLE_HIS_ORDERS+" ord (nolock)");
		sql.append(" left join "+TABLE_DICT_DRUG_MX+" mx (nolock)");
		sql.append(" on mx.drug_code= ord.order_code and xiangm = '高危药品'");
		sql.append(" left join "+TABLE_DICT_DRUG+" c(nolock)");
		sql.append(" on c.drug_code=ord.order_code");
		sql.append(" where 1=1");
		sql.append(" and ord.patient_id= :patient_id and visit_id= :visit_id");
		if(CommonFun.isNe(params.get("order"))){sql.append(" order by ord.order_status, ord.group_id, ord.order_no, ord.order_sub_no ");}
		return executeQuery(sql.toString(), params);
	}
	/**
	 * @author: 魏圣峰
	 * @description: 添加事后审查队列
	 * @param: params Map queue_id 当前队列id、remark 队列描述
	 * @return: int      
	 * @throws: Exception
	 */
	public int insertQueue(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("exec "+PROC_ADD_AUDIT_QUEUE+" '',''");
		sql.append(!CommonFun.isNe(params.get("qMaxCount"))?",'top "+params.get("qMaxCount")+"'":",''");//队列上限
		sql.append(!CommonFun.isNe(params.get("remark"))?",'"+params.get("remark")+"'":",'"+CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss")+"'");//队列描述
		sql.append(",'");//组装where条件sql
		if(!CommonFun.isNe(params.get("patient"))){sql.append(" and a.patient_id = ''"+params.get("patient")+"''");}//患者
		if(!CommonFun.isNe(params.get("p_sex"))){//患者性别
			if("0".equals(params.get("p_sex"))){sql.append(" and (b.sex != ''男'' and b.sex != ''女'')");}//未知
			else{sql.append(" and b.sex = ''"+params.get("p_sex")+"''");}
		}
		if(!CommonFun.isNe(params.get("p_type"))){sql.append(" and a.p_type = ''"+params.get("p_type")+"''");}//开药类型
		if(!CommonFun.isNe(params.get("d_type"))){sql.append(" and b.d_type = ''"+params.get("d_type")+"''");}//就诊类型
        if(!CommonFun.isNe(params.get("doctorfifter"))){//医生
        	sql.append(" and a.doctor_no in(");
        	String s[]=params.get("doctorfifter").toString().split(",");
        	for(int i=0;i<s.length;i++) {sql.append("''"+s[i]+"''");if(i<s.length-1) {sql.append(",");}}
        	sql.append(") ");
        }
        if(!CommonFun.isNe(params.get("deptfifter"))){//科室
        	sql.append(" and a.dept_code in(");
        	String s[]=params.get("deptfifter").toString().split(",");
        	for(int i=0;i<s.length;i++) {sql.append("''"+s[i]+"''");if(i<s.length-1) {sql.append(",");}}
        	sql.append(") ");
        }
        if(!CommonFun.isNe(params.get("drugfifter"))){//药品
        	sql.append(" and a.order_code in(");
        	String s[]=params.get("drugfifter").toString().split(",");
        	for(int i=0;i<s.length;i++) {sql.append("''"+s[i]+"''");if(i<s.length-1) {sql.append(",");}}
        	sql.append(") ");
        }
        if(!CommonFun.isNe(params.get("feibiefifter"))){//费别
        	sql.append(" and b.charge_type in(");
        	String s[]=params.get("feibiefifter").toString().split(",");
        	for(int i=0;i<s.length;i++) {sql.append("''"+s[i]+"''");if(i<s.length-1) {sql.append(",");}}
        	sql.append(") ");
        }
        if(!CommonFun.isNe(params.get("mintime"))&&!CommonFun.isNe(params.get("maxtime")))
        {sql.append(" and (b.admission_datetime >= ''"+params.get("mintime")+"'' and b.admission_datetime <= ''"+params.get("maxtime")+"'')");}//入院时间
        if(!CommonFun.isNe(params.get("p_min_age"))&&!CommonFun.isNe(params.get("p_max_age"))){//就诊年龄
        	//加入case when (isdate(b.birthday) != 0 and isdate(a.admission_datetime) != 0) 解决不规范日期导致的日期转换异常
        	sql.append(" and (''"+params.get("p_min_age")+"'' <= (case when (isdate(b.birthday) != 0 and isdate(b.admission_datetime) != 0) then datediff(year,b.birthday,b.admission_datetime) else 0 end)");
            sql.append(" and ''"+params.get("p_max_age")+"'' >= (case when (isdate(b.birthday) != 0 and isdate(b.admission_datetime) != 0) then datediff(year,b.birthday,b.admission_datetime) else 0 end))");
        }
		if(CommonFun.isNe(params.get("isInclude"))) {sql.append(" and (select count(1) as num from "+TABLE_NAME+" qp (nolock) where a.patient_id=qp.patient_id and b.visit_id=qp.visit_id ) = 0");}//是否包含已有队列
		sql.append("'");
		return executeQuerySingleRecord(sql.toString(),null).getInt("rtncount");
	}

	public int update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME, params, r);
	}

	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME, params);
	}

	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append(" (nolock) where  id = :id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}

}
