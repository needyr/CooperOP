package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.ProductmanagetService;


//dict_his_drug his药品表映射
public class DictdrugDao extends BaseDao{

	private final static String TABLE_NAME = "dict_his_drug";
	
	private final static String DICT_SYS_DRUG = "dict_sys_drug";
	
	private final static String HOSPITAL_ID = SystemConfig.getSystemConfigValue("hospital_common", "hospital_id", "syshid");
	
	private final static String SHENGFANGZL_MODIFY_RECORD = "shengfangzl_modify_record";//记录自定义维护操作的表
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		
		sql.append("select a.*,b.drug_name sysname from dict_his_drug (nolock) a left join dict_sys_drug (nolock) b on a.sys_p_key=b.p_key where 1=1");
		if("has".equals(params.get("sxtj"))) {
			sql.append(" and (a.sys_p_key is not null and a.sys_p_key <> '')");
		}else if("not".equals(params.get("sxtj"))){
			sql.append(" and (a.sys_p_key is null or a.sys_p_key = '')");
		}
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
		}
		setParameter(params, "filter", " and (a.drug_name like :key or a.drug_code like :key or a.shengccj like :key or a.input_code like :key or a.pizhwh like :key)", sql);
		if(null!=params.get("shengccj")&&!"".equals(params.get("shengccj"))) {
			sql.append(" and a.shengccj like :shengccj");
			params.put("shengccj", "%"+params.get("shengccj")+"%");
		}
		params.remove("sxtj");
		
		/*筛选药品类别*/
		if(!CommonFun.isNe(params.get("drug_indicator"))){
			if(!"all".equals(params.get("drug_indicator"))) {
				sql.append(" and a.drug_indicator = '"+params.get("drug_indicator")+"' ");
			}
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from dict_sys_drug with(nolock) where 1 = 1");
		setParameter(params, "p_key", " and p_key =:p_key ", sql);
		log.debug(sql.toString());
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	
	//模糊匹配 ：查询中间表信息（匹配top100药品信息）
	public Result querysys(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		if("forsys".equals(params.get("stype"))){
			sql.append(" select * from dict_sys_drug (nolock) s where 1=1 ");
			if(!CommonFun.isNe(params.get("shengccj"))) {
				params.put("shengccj","%"+params.get("shengccj").toString().trim()+"%");
				sql.append(" and s.shengccj like :shengccj ");
			}
			if(!CommonFun.isNe(params.get("drug_name"))) {
				params.put("drug_name","%"+(String)params.get("drug_name").toString().trim()+"%");
				sql.append(" and s.drug_name like :drug_name ");
			}
			if(!CommonFun.isNe(params.get("drug_unit"))) {
				params.put("drug_unit","%"+params.get("drug_unit").toString().trim()+"%");
				sql.append(" and s.drug_unit like :drug_unit ");
			}
			if(!CommonFun.isNe(params.get("druggg"))) {
				params.put("druggg","%"+params.get("druggg").toString().trim()+"%");
				sql.append(" and s.druggg like :druggg ");
			}
			if(!CommonFun.isNe(params.get("pizhwh"))) {
				params.put("pizhwh","%"+params.get("pizhwh").toString().trim()+"%");
				sql.append(" and s.pizhwh like :pizhwh ");
			}
		}else {
			sql.append("select s.pphyt, s.drug_code,p.*, ms.sys_drug_code mscode ");
			sql.append("from dict_his_drug_tmp (nolock) p ");
			sql.append(" inner join dict_sys_drug (nolock) s on p.p_key=s.p_key ");
			sql.append(" inner join spzl_shuoms (nolock) ms on s.drug_code = ms.sys_drug_code ");
			//sql.append(" inner join spzl_shuoms_zdy (nolock) zms on s.drug_code = zms.sys_drug_code ");
			sql.append(" where  p.his_p_key =:p_key ");
			if(!CommonFun.isNe(params.get("shengccj"))) {
				params.put("shengccj","%"+params.get("shengccj").toString().trim()+"%");
				sql.append(" and s.shengccj like :shengccj ");
			}
			String sortstr = "cast(spmch_ppd as dec(14,2)) desc, " +
							 "cast(shengccjname_ppd as dec(14,2)) desc," +
							 "cast(shpgg_ppd as dec(14,2)) desc ," +
							 "cast(dw_ppd as dec(14,2)) desc " ;
			params.put("sort", sortstr);
		}
		params.remove("stype");
		return executeQueryLimit(sql.toString(), params);
	}
	
	
	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("drug_code", params.remove("drug_code"));
		return executeUpdate(TABLE_NAME, params, r);
	}
	
	public int verify(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("p_key", params.remove("p_key"));
		return executeUpdate(TABLE_NAME, params, r);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	//设置了自定义审查的药品
	public Result queryzdysc(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select * from "+TABLE_NAME+" (nolock) where 1=1 ");
		sql.append("and drug_code in ( ");
		if(!CommonFun.isNe(params.get("hasset"))) {
			try {
				String[] hasset  = (String[]) params.get("hasset");
				for (int i = 0; i < hasset.length; i++) {
					if(i == hasset.length -1) {
						if("dict_his_drug_mx".equals(hasset[i])) {
							sql.append(" select drug_code from "+hasset[i]+" (nolock)  ");
						}else {
							sql.append(" select spbh from "+hasset[i]+" (nolock)  ");
						}
					}else {
						if("dict_his_drug_mx".equals(hasset[i])) {
							sql.append(" select drug_code from "+hasset[i]+" (nolock) union ");
						}else {
							sql.append(" select spbh from "+hasset[i]+" (nolock) union ");
						}
					}
				}
			} catch (Exception e) {
				if("dict_his_drug_mx".equals((String)params.get("hasset"))) {
					sql.append(" select drug_code from "+ params.get("hasset") +" (nolock)  ");
				}else {
					sql.append(" select spbh from " + params.get("hasset")  +" (nolock)  ");
				}
			}
		}else {
			sql.append(" null ");	
		}
		sql.append(")");
		/*else {
			sql.append("select * from "+TABLE_NAME+" (nolock) where 1=1 ");
			sql.append("and drug_code in ( ");
			sql.append(" select spbh from shengfangzl_pl (nolock) union ");
			sql.append(" select spbh from shengfangzl_routename (nolock) union ");
			sql.append(" select spbh from shengfangzl_peiw (nolock) union ");
			sql.append(" select spbh from shengfangzl_yongl (nolock) union ");
			sql.append(" select spbh from shengfangzl_rongm (nolock) union");
			sql.append(" select drug_code from dict_his_drug_mx (nolock) union");
			sql.append(" select spbh from shengfangzl_pass (nolock) union");
			sql.append(" select spbh from shengfangzl_ccr (nolock) ");
			sql.append(")");
		}*/
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
		}
		setParameter(params, "key", " and (drug_name like :key or drug_code like :key or input_code like :key)", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	
	//查询没有设置自定义审查的药品
	public Result querynotzdysc(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select * from "+TABLE_NAME+" (nolock) where 1=1 ");
		sql.append("and drug_code not in ( ");
		sql.append(" select spbh from shengfangzl_pl (nolock) where spbh is not null union ");
		sql.append(" select spbh from shengfangzl_routename (nolock) where spbh is not null union ");
		sql.append(" select spbh from shengfangzl_peiw (nolock) where spbh is not null union ");
		sql.append(" select spbh from shengfangzl_yongl (nolock) where spbh is not null union ");
		sql.append(" select spbh from shengfangzl_rongm (nolock) where spbh is not null union");
		sql.append(" select drug_code from dict_his_drug_mx (nolock) where drug_code is not null union");
		sql.append(" select spbh from shengfangzl_pass (nolock) where spbh is not null union");
		sql.append(" select spbh from shengfangzl_jjz (nolock) where spbh is not null union");
		sql.append(" select spbh from shengfangzl_xhzy (nolock) where spbh is not null union");
		sql.append(" select spbh from shengfangzl_syz (nolock) where spbh is not null union");
		sql.append(" select spbh from shengfangzl_ccr (nolock) where spbh is not null ");
		sql.append(")");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("filter", "%"+params.get("filter")+"%");
			sql.append("and (drug_name like :filter or drug_code like :filter )");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	//国药准字匹配
	public String matchgyzz(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		/*sql.append("update a set a.sys_p_key = b.p_key from  dict_his_drug a,dict_sys_drug b where a.pizhwh = b.PIZHWH ");
		sql.append(" AND ISNULL(a.pizhwh,'')<>''             ");
		sql.append(" and Replace(a.pizhwh,' ','')<>'国药准字' ");
		sql.append(" and Replace(a.pizhwh,' ','')<>'暂缺'    ");
		sql.append(" and not EXISTS (select top 1            ");
		sql.append(" 1 from dict_his_drug                    ");
		sql.append(" where isnull(sys_p_key,'')<>'')         ");*/
		//修改为执行存储过程
		sql.append(" exec scr_drug_pd ");
		return execute(sql.toString(),params);
	}
	
	//查询匹配度每个药品匹配度  top100
	public void calltop100(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		//sql.append("execute similar_spzl '"+params.get("pizhwh")+"','"+params.get("drug_name")+"','"+params.get("shengccj")+"','"+params.get("druggg")+"','"+params.get("drug_unit")+"'");
		sql.append("execute similar_spzl_tmp ");
		executeCallQuery(sql.toString(), params);
	}
	
	//自定义审查新增问题的  药品信息单条查询
	public Record getforzdy(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select *,dbo.ClearZero(usejlgg) +isnull(use_dw,'') as syjl from dict_his_drug with(nolock) where 1 = 1");
		if(CommonFun.isNe(params.get("drug_code"))){
			params.put("drug_code", "0");
		}
		sql.append(" and drug_code = :drug_code ");
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public Map<String, Object> importSYS(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into   ");
		sql.append(DICT_SYS_DRUG);
		sql.append(" (                                         ");
		sql.append(" P_KEY,DRUG_CODE,DRUG_name,DRUG_unit,      ");
		sql.append(" PROPERTY_TOXI,DRUG_INDICATOR,             ");
		sql.append(" Druggg,Shengccj,PIZHWH,Jixing,            ");
		sql.append(" Usejlgg,Use_dw,INPUT_CODE                 ");
		sql.append(" )                                         ");
		sql.append(" select   top 1                            ");
		sql.append("'"+HOSPITAL_ID + "|'+"+"P_KEY,"+"'"+HOSPITAL_ID + "|'+"+"DRUG_CODE,DRUG_name,");
		sql.append(" DRUG_unit,PROPERTY_TOXI,DRUG_INDICATOR,   ");
		sql.append(" Druggg,Shengccj,PIZHWH,Jixing,            ");
		sql.append(" Usejlgg,Use_dw,INPUT_CODE                 ");
		sql.append(" from "+ TABLE_NAME +" where p_key  = :p_key ");
		execute(sql.toString(), params);
		params.put("sys_p_key", HOSPITAL_ID + "|" +params.remove("p_key"));
		params.put("drug_code", params.remove("drug_code"));
		return params;
	}

	public Result search(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * ");
		sql.append("FROM dict_his_drug(NOLOCK) ");
		sql.append(" where 1=1 ");
		if (!CommonFun.isNe(params.get("data"))) {
			params.put("filter", "%"+params.get("data")+"%");
			sql.append(" and drug_code = :data or drug_name like :filter or input_code like :filter");
		}
		if(!CommonFun.isNe(params.get("tags"))) {
			sql.append(" and CHARINDEX(','+'"+params.get("tags")+"'+',',','+tags+',') > 0 ");
		}
		if(!CommonFun.isNe(params.get("is_anti_drug"))) {
			sql.append(" and is_anti_drug ='1' ");
		}
		return executeQuery(sql.toString(), params);
	}

	public Result searchCheck(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * ");
		sql.append("FROM dict_his_drug(NOLOCK) ");
		sql.append(" where drug_code in(:code) ");
		if(!CommonFun.isNe(params.get("tags"))) {
			sql.append(" and CHARINDEX(','+'"+params.get("tags")+"'+',',','+tags+',') > 0 ");
		}
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryDrugType(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select drug_indicator from dict_his_drug (NOLOCK) where drug_indicator is not null and drug_indicator != ''  group by drug_indicator");
		return executeQuery(sql.toString(), params);
	}
	
	public void deleteStandard(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from dict_sys_drug ");
		sql.append(" where drug_code = '"+HOSPITAL_ID+"|"+params.get("drug_code")+"'");
		sql.append(" and is_new = '是' ");
		execute(sql.toString(), params);
	}
	
	public Result queryZdyAll(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		if(!CommonFun.isNe(params.get("hasset"))) {
			try {
				String[] hasset  = (String[]) params.get("hasset");
				for (int i = 0; i < hasset.length; i++) {
					if (hasset[i].equals("sfpeiw")) {
						params.put("table_name", "sfpeiw");
						if (i != 0) {
							sql.append(" Union all ");
						}
						sql.append("select sf.id,dsc.level_name,acs.sort_name,sf.nwarn_message,");
						sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+params.get("table_name")+"' table_name, ");
						sql.append(" drug.shengccj,drug.druggg,drug.drug_code,drug.zdy_cz, ");
						sql.append("case when Formul='>' then '大于'              ");
						sql.append("		 when Formul='<' then '小于'          ");
						sql.append("		  when Formul='<>' then '不等于'      ");
						sql.append("			 when Formul='like' then '类似'   ");
						sql.append("		when Formul='not like' then '不类似'  ");
						sql.append("		 when Formul='=' then '等于'          ");
						sql.append("		end as Formul_name                    ");
						sql.append("	 from shengfangzl_peiw (nolock) sf");
						sql.append(" left join dict_his_drug (nolock) drug on sf.spbh = drug.drug_code ");
						sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" where 1 = 1");
						if (!CommonFun.isNe(params.get("filter"))) {
							params.put("key", "%"+params.get("filter")+"%");
							sql.append(" and (sf.spbh= :filter or drug.drug_name like :key ) ");
						}
						if (!CommonFun.isNe(params.get("patient"))) {
							params.put("patient", "%"+params.get("patient")+"%");
							sql.append(" and drug.zdy_cz like :patient ");
						}
						if (!CommonFun.isNe(params.get("mintime"))) {
							sql.append(" and substring(zdy_cz,0,20) >= :mintime ");
						}
						if (!CommonFun.isNe(params.get("maxtime"))) {
							sql.append(" and substring(zdy_cz,0,20) <= :maxtime ");
						}
						
					}
					if (hasset[i].equals("sfrongm")) {
						params.put("table_name", "sfrongm");
						if (i != 0) {
							sql.append(" Union all ");
						}
						sql.append("select sf.id,dsc.level_name,acs.sort_name,sf.nwarn_message,");
						sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+params.get("table_name")+"' table_name, ");
						sql.append(" drug.shengccj,drug.druggg,drug.drug_code,drug.zdy_cz, ");
						sql.append("case when Formul='>' then '大于'              ");
						sql.append("		 when Formul='<' then '小于'          ");
						sql.append("		  when Formul='<>' then '不等于'      ");
						sql.append("			 when Formul='like' then '类似'   ");
						sql.append("		when Formul='not like' then '不类似'  ");
						sql.append("		 when Formul='=' then '等于'          ");
						sql.append("		end as Formul_name                    ");
						sql.append("	 from shengfangzl_rongm (nolock) sf");
						sql.append(" left join dict_his_drug (nolock) drug on sf.spbh = drug.drug_code ");
						sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" where 1 = 1");
						if (!CommonFun.isNe(params.get("filter"))) {
							params.put("key", "%"+params.get("filter")+"%");
							sql.append(" and (sf.spbh= :filter or drug.drug_name like :key ) ");
						}
						if (!CommonFun.isNe(params.get("patient"))) {
							params.put("patient", "%"+params.get("patient")+"%");
							sql.append(" and drug.zdy_cz like :patient ");
						}
						if (!CommonFun.isNe(params.get("mintime"))) {
							sql.append(" and substring(zdy_cz,0,20) >= :mintime ");
						}
						if (!CommonFun.isNe(params.get("maxtime"))) {
							sql.append(" and substring(zdy_cz,0,20) <= :maxtime ");
						}
					}
					if (hasset[i].equals("sfyongl")) {
						params.put("table_name", "sfyongl");
						if (i != 0) {
							sql.append(" Union all ");
						}
						sql.append("select sf.id,dsc.level_name,acs.sort_name,sf.nwarn_message,");
						sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+params.get("table_name")+"' table_name, ");
						sql.append(" drug.shengccj,drug.druggg,drug.drug_code,drug.zdy_cz, ");
						sql.append("case when Formul='>' then '大于'              ");
						sql.append("		 when Formul='<' then '小于'          ");
						sql.append("		  when Formul='<>' then '不等于'      ");
						sql.append("			 when Formul='like' then '类似'   ");
						sql.append("		when Formul='not like' then '不类似'  ");
						sql.append("		 when Formul='=' then '等于'          ");
						sql.append("		end as Formul_name                    ");
						sql.append("	 from shengfangzl_yongl (nolock) sf");
						sql.append(" left join dict_his_drug (nolock) drug on sf.spbh = drug.drug_code ");
						sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" where 1 = 1");
						if (!CommonFun.isNe(params.get("filter"))) {
							params.put("key", "%"+params.get("filter")+"%");
							sql.append(" and (sf.spbh= :filter or drug.drug_name like :key ) ");
						}
						if (!CommonFun.isNe(params.get("patient"))) {
							params.put("patient", "%"+params.get("patient")+"%");
							sql.append(" and drug.zdy_cz like :patient ");
						}
						if (!CommonFun.isNe(params.get("mintime"))) {
							sql.append(" and substring(zdy_cz,0,20) >= :mintime ");
						}
						if (!CommonFun.isNe(params.get("maxtime"))) {
							sql.append(" and substring(zdy_cz,0,20) <= :maxtime ");
						}
					}
					if (hasset[i].equals("sfyonglchild")) {
						params.put("table_name", "sfyonglchild");
						if (i != 0) {
							sql.append(" Union all ");
						}
						sql.append("select sf.id,dsc.level_name,acs.sort_name,sf.nwarn_message,");
						sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+params.get("table_name")+"' table_name, ");
						sql.append(" drug.shengccj,drug.druggg,drug.drug_code,drug.zdy_cz, ");
						sql.append("case when Formul='>' then '大于'              ");
						sql.append("		 when Formul='<' then '小于'          ");
						sql.append("		  when Formul='<>' then '不等于'      ");
						sql.append("			 when Formul='like' then '类似'   ");
						sql.append("		when Formul='not like' then '不类似'  ");
						sql.append("		 when Formul='=' then '等于'          ");
						sql.append("		end as Formul_name                    ");
						sql.append("	 from shengfangzl_yongl_child (nolock) sf");
						sql.append(" left join dict_his_drug (nolock) drug on sf.spbh = drug.drug_code ");
						sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" where 1 = 1");
						if (!CommonFun.isNe(params.get("filter"))) {
							params.put("key", "%"+params.get("filter")+"%");
							sql.append(" and (sf.spbh= :filter or drug.drug_name like :key ) ");
						}
						if (!CommonFun.isNe(params.get("patient"))) {
							params.put("patient", "%"+params.get("patient")+"%");
							sql.append(" and drug.zdy_cz like :patient ");
						}
						if (!CommonFun.isNe(params.get("mintime"))) {
							sql.append(" and substring(zdy_cz,0,20) >= :mintime ");
						}
						if (!CommonFun.isNe(params.get("maxtime"))) {
							sql.append(" and substring(zdy_cz,0,20) <= :maxtime ");
						}
					}
					if (hasset[i].equals("sfroute")) {
						params.put("table_name", "sfroute");
						if (i != 0) {
							sql.append(" Union all ");
						}
						sql.append("select sf.id,dsc.level_name,acs.sort_name,sf.nwarn_message,");
						sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+params.get("table_name")+"' table_name, ");
						sql.append(" drug.shengccj,drug.druggg,drug.drug_code,drug.zdy_cz, ");
						sql.append("case when Formul='>' then '大于'              ");
						sql.append("		 when Formul='<' then '小于'          ");
						sql.append("		  when Formul='<>' then '不等于'      ");
						sql.append("			 when Formul='like' then '类似'   ");
						sql.append("		when Formul='not like' then '不类似'  ");
						sql.append("		 when Formul='=' then '等于'          ");
						sql.append("		end as Formul_name                    ");
						sql.append("	 from shengfangzl_routename (nolock) sf");
						sql.append(" left join dict_his_drug (nolock) drug on sf.spbh = drug.drug_code ");
						sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" where 1 = 1");
						if (!CommonFun.isNe(params.get("filter"))) {
							params.put("key", "%"+params.get("filter")+"%");
							sql.append(" and (sf.spbh= :filter or drug.drug_name like :key ) ");
						}
						if (!CommonFun.isNe(params.get("patient"))) {
							params.put("patient", "%"+params.get("patient")+"%");
							sql.append(" and drug.zdy_cz like :patient ");
						}
						if (!CommonFun.isNe(params.get("mintime"))) {
							sql.append(" and substring(zdy_cz,0,20) >= :mintime ");
						}
						if (!CommonFun.isNe(params.get("maxtime"))) {
							sql.append(" and substring(zdy_cz,0,20) <= :maxtime ");
						}
					}
					if (hasset[i].equals("sfpl")) {
						params.put("table_name", "sfpl");
						if (i != 0) {
							sql.append(" Union all ");
						}
						sql.append("select sf.id,dsc.level_name,acs.sort_name,sf.nwarn_message,");
						sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+params.get("table_name")+"' table_name, ");
						sql.append(" drug.shengccj,drug.druggg,drug.drug_code,drug.zdy_cz, ");
						sql.append("case when Formul='>' then '大于'              ");
						sql.append("		 when Formul='<' then '小于'          ");
						sql.append("		  when Formul='<>' then '不等于'      ");
						sql.append("			 when Formul='like' then '类似'   ");
						sql.append("		when Formul='not like' then '不类似'  ");
						sql.append("		 when Formul='=' then '等于'          ");
						sql.append("		end as Formul_name                    ");
						sql.append("	 from shengfangzl_PL (nolock) sf");
						sql.append(" left join dict_his_drug (nolock) drug on sf.spbh = drug.drug_code ");
						sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" where 1 = 1");
						if (!CommonFun.isNe(params.get("filter"))) {
							params.put("key", "%"+params.get("filter")+"%");
							sql.append(" and (sf.spbh= :filter or drug.drug_name like :key ) ");
						}
						if (!CommonFun.isNe(params.get("patient"))) {
							params.put("patient", "%"+params.get("patient")+"%");
							sql.append(" and drug.zdy_cz like :patient ");
						}
						if (!CommonFun.isNe(params.get("mintime"))) {
							sql.append(" and substring(zdy_cz,0,20) >= :mintime ");
						}
						if (!CommonFun.isNe(params.get("maxtime"))) {
							sql.append(" and substring(zdy_cz,0,20) <= :maxtime ");
						}
					}
					if (hasset[i].equals("sfauditpass")) {
						params.put("table_name", "sfauditpass");
						if (i != 0) {
							sql.append(" Union all ");
						}
						sql.append("select sf.id,dsc.level_name,acs.sort_name,sf.nwarn_message,");
						sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+params.get("table_name")+"' table_name, ");
						sql.append(" drug.shengccj,drug.druggg,drug.drug_code,drug.zdy_cz, ");
						sql.append("case when Formul='>' then '大于'              ");
						sql.append("		 when Formul='<' then '小于'          ");
						sql.append("		  when Formul='<>' then '不等于'      ");
						sql.append("			 when Formul='like' then '类似'   ");
						sql.append("		when Formul='not like' then '不类似'  ");
						sql.append("		 when Formul='=' then '等于'          ");
						sql.append("		end as Formul_name                    ");
						sql.append("	 from shengfangzl_pass (nolock) sf");
						sql.append(" left join dict_his_drug (nolock) drug on sf.spbh = drug.drug_code ");
						sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" where 1 = 1");
						if (!CommonFun.isNe(params.get("filter"))) {
							params.put("key", "%"+params.get("filter")+"%");
							sql.append(" and (sf.spbh= :filter or drug.drug_name like :key ) ");
						}
						if (!CommonFun.isNe(params.get("patient"))) {
							params.put("patient", "%"+params.get("patient")+"%");
							sql.append(" and drug.zdy_cz like :patient ");
						}
						if (!CommonFun.isNe(params.get("mintime"))) {
							sql.append(" and substring(zdy_cz,0,20) >= :mintime ");
						}
						if (!CommonFun.isNe(params.get("maxtime"))) {
							sql.append(" and substring(zdy_cz,0,20) <= :maxtime ");
						}
					}
					if (hasset[i].equals("sfccr")) {
						params.put("table_name", "sfccr");
						if (i != 0) {
							sql.append(" Union all ");
						}
						sql.append("select sf.id,dsc.level_name,acs.sort_name,sf.nwarn_message,");
						sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+params.get("table_name")+"' table_name, ");
						sql.append(" drug.shengccj,drug.druggg,drug.drug_code,drug.zdy_cz, ");
						sql.append("case when Formul='>' then '大于'              ");
						sql.append("		 when Formul='<' then '小于'          ");
						sql.append("		  when Formul='<>' then '不等于'      ");
						sql.append("			 when Formul='like' then '类似'   ");
						sql.append("		when Formul='not like' then '不类似'  ");
						sql.append("		 when Formul='=' then '等于'          ");
						sql.append("		end as Formul_name                    ");
						sql.append("	 from shengfangzl_ccr (nolock) sf");
						sql.append(" left join dict_his_drug (nolock) drug on sf.spbh = drug.drug_code ");
						sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" where 1 = 1");
						if (!CommonFun.isNe(params.get("filter"))) {
							params.put("key", "%"+params.get("filter")+"%");
							sql.append(" and (sf.spbh= :filter or drug.drug_name like :key ) ");
						}
						if (!CommonFun.isNe(params.get("patient"))) {
							params.put("patient", "%"+params.get("patient")+"%");
							sql.append(" and drug.zdy_cz like :patient ");
						}
						if (!CommonFun.isNe(params.get("mintime"))) {
							sql.append(" and substring(zdy_cz,0,20) >= :mintime ");
						}
						if (!CommonFun.isNe(params.get("maxtime"))) {
							sql.append(" and substring(zdy_cz,0,20) <= :maxtime ");
						}
					}
					if (hasset[i].equals("sfjjz")) {
						params.put("table_name", "sfjjz");
						if (i != 0) {
							sql.append(" Union all ");
						}
						sql.append("select sf.id,dsc.level_name,acs.sort_name,sf.nwarn_message,");
						sql.append(" sf.tiaojian,sf.xmmch,'详细请见修改' value,drug.drug_name,drug.drug_unit,'"+params.get("table_name")+"' table_name, ");
						sql.append(" drug.shengccj,drug.druggg,drug.drug_code,drug.zdy_cz, ");
						sql.append("'详细请见修改' as Formul_name           ");
						sql.append("	 from shengfangzl_jjz (nolock) sf");
						sql.append(" left join dict_his_drug (nolock) drug on sf.spbh = drug.drug_code ");
						sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" where 1 = 1");
						if (!CommonFun.isNe(params.get("filter"))) {
							params.put("key", "%"+params.get("filter")+"%");
							sql.append(" and (sf.spbh= :filter or drug.drug_name like :key ) ");
						}
						if (!CommonFun.isNe(params.get("patient"))) {
							params.put("patient", "%"+params.get("patient")+"%");
							sql.append(" and drug.zdy_cz like :patient ");
						}
						if (!CommonFun.isNe(params.get("mintime"))) {
							sql.append(" and substring(zdy_cz,0,20) >= :mintime ");
						}
						if (!CommonFun.isNe(params.get("maxtime"))) {
							sql.append(" and substring(zdy_cz,0,20) <= :maxtime ");
						}
					}
					if (hasset[i].equals("sfsyz")) {
						params.put("table_name", "sfsyz");
						if (i != 0) {
							sql.append(" Union all ");
						}
						sql.append("select sf.id,dsc.level_name,acs.sort_name,sf.nwarn_message,");
						sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+params.get("table_name")+"' table_name, ");
						sql.append(" drug.shengccj,drug.druggg,drug.drug_code,drug.zdy_cz, ");
						sql.append("case when Formul='>' then '大于'              ");
						sql.append("		 when Formul='<' then '小于'          ");
						sql.append("		  when Formul='<>' then '不等于'      ");
						sql.append("			 when Formul='like' then '类似'   ");
						sql.append("		when Formul='not like' then '不类似'  ");
						sql.append("		 when Formul='=' then '等于'          ");
						sql.append("		end as Formul_name                    ");
						sql.append("	 from shengfangzl_syz (nolock) sf");
						sql.append(" left join dict_his_drug (nolock) drug on sf.spbh = drug.drug_code ");
						sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" where 1 = 1");
						if (!CommonFun.isNe(params.get("filter"))) {
							params.put("key", "%"+params.get("filter")+"%");
							sql.append(" and (sf.spbh= :filter or drug.drug_name like :key ) ");
						}
						if (!CommonFun.isNe(params.get("patient"))) {
							params.put("patient", "%"+params.get("patient")+"%");
							sql.append(" and drug.zdy_cz like :patient ");
						}
						if (!CommonFun.isNe(params.get("mintime"))) {
							sql.append(" and substring(zdy_cz,0,20) >= :mintime ");
						}
						if (!CommonFun.isNe(params.get("maxtime"))) {
							sql.append(" and substring(zdy_cz,0,20) <= :maxtime ");
						}
					}
					if (hasset[i].equals("sfxhzy")) {
						params.put("table_name", "sfxhzy");
						if (i != 0) {
							sql.append(" Union all ");
						}
						sql.append("select sf.id,dsc.level_name,acs.sort_name,sf.nwarn_message,");
						sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+params.get("table_name")+"' table_name, ");
						sql.append(" drug.shengccj,drug.druggg,drug.drug_code,drug.zdy_cz, ");
						sql.append("case when Formul='>' then '大于'              ");
						sql.append("		 when Formul='<' then '小于'          ");
						sql.append("		  when Formul='<>' then '不等于'      ");
						sql.append("			 when Formul='like' then '类似'   ");
						sql.append("		when Formul='not like' then '不类似'  ");
						sql.append("		 when Formul='=' then '等于'          ");
						sql.append("		end as Formul_name                    ");
						sql.append("	 from shengfangzl_xhzy (nolock) sf");
						sql.append(" left join dict_his_drug (nolock) drug on sf.spbh = drug.drug_code ");
						sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" where 1 = 1");
						if (!CommonFun.isNe(params.get("filter"))) {
							params.put("key", "%"+params.get("filter")+"%");
							sql.append(" and (sf.spbh= :filter or drug.drug_name like :key ) ");
						}
						if (!CommonFun.isNe(params.get("patient"))) {
							params.put("patient", "%"+params.get("patient")+"%");
							sql.append(" and drug.zdy_cz like :patient ");
						}
						if (!CommonFun.isNe(params.get("mintime"))) {
							sql.append(" and substring(zdy_cz,0,20) >= :mintime ");
						}
						if (!CommonFun.isNe(params.get("maxtime"))) {
							sql.append(" and substring(zdy_cz,0,20) <= :maxtime ");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		params.put("sort", "id asc ");
		return executeQueryLimit(sql.toString(), params);
	}
	/**
	 * @author wangsen
	 * @date 2019年3月19日 下午2:09:00 
	 * @param params
	 * @return
	 * @throws Exception  
	 * @function 插入自定义修改内容
	 */
	public int insertZdyLog(Map<String, Object> params) throws Exception {
		return executeInsert(SHENGFANGZL_MODIFY_RECORD, params);
	}
	
	public Result queryzdycz(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,b.drug_name,b.drug_code,b.druggg,b.drug_unit,b.shengccj from "+ SHENGFANGZL_MODIFY_RECORD + " a");
		sql.append(" left join dict_his_drug b on a.spbh=b.drug_code ");
		sql.append(" where 1=1 ");
		if (!CommonFun.isNe(params.get("drug"))) {
			params.put("filter", "%"+params.get("drug")+"%");
			sql.append(" and (b.drug_code = :drug or b.drug_name like :filter or input_code like :filter) ");
		}
		if (!CommonFun.isNe(params.get("operator"))) {
			params.put("filter_operator", "%"+params.get("operator")+"%");
			sql.append(" and (b.admin_no = :operator or b.admin like :filter_operator) ");
		}
		if (!CommonFun.isNe(params.get("xm"))) {
			params.put("filter_xm", "%"+params.get("xm")+"%");
			sql.append(" and a.zdy_cz like :filter_xm ");
		}
		setParameter(params, "mintime", " and a.create_time >= :mintime ", sql);
		setParameter(params, "maxtime", " and a.create_time <= :maxtime ", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result queryHPDrug(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.*,b.p_key sys_pk,c.sys_drug_code from dict_his_drug (nolock) a  ");
		sql.append(" left join dict_sys_drug (nolock) b on a.sys_p_key = b.p_key       ");
		sql.append(" left join spzl_shuoms c (nolock) on b.drug_code = c.sys_drug_code ");
		sql.append(" where 1 = 1 ");
		if(!CommonFun.isNe(params.get("key"))) {
			params.put("key", "%" + params.get("key") +"%");
			sql.append(" and (a.drug_code like :key or a.drug_name like :key or a.input_code like :key) ");
		}
		if(!CommonFun.isNe(params.get("is_verify")) && !"all".equals(params.get("is_verify"))) {
			sql.append(" and a.is_verify = :is_verify ");
		}
		if(!CommonFun.isNe(params.get("drug_indicator")) && !"all".equals(params.get("drug_indicator"))) {
			sql.append(" and a.drug_indicator = :drug_indicator ");
		}
		if(!CommonFun.isNe(params.get("is_had")) && !"all".equals(params.get("is_had"))) {
			if("1".equals(params.get("is_had"))) {
				sql.append(" and c.sys_drug_code is not null ");
			}else if("0".equals(params.get("is_had"))){
				sql.append(" and c.sys_drug_code is null ");
			}
		}
		if(!CommonFun.isNe(params.get("is_kc")) && !"all".equals(params.get("is_kc"))) {
			if("1".equals(params.get("is_kc"))) {
				sql.append(" and isnull(a.shl,0) > 0 ");
			}else if("0".equals(params.get("is_kc"))){
				sql.append(" and isnull(a.shl,0) <= 0 ");
			}
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record getZdyMx(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,b.drug_name,b.drug_code,b.druggg,b.drug_unit,b.shengccj from "+ SHENGFANGZL_MODIFY_RECORD + " a");
		sql.append(" left join dict_his_drug b on a.spbh=b.drug_code ");
		sql.append(" where 1=1 ");
		setParameter(params, "id", " and a.id = :id ", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getUnit(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.Use_dw ");
		sql.append(" from "+TABLE_NAME+" (nolock) a ");
		sql.append(" where a.drug_code = :spbh ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getDrugByCode(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from " + TABLE_NAME + " (nolock) ");
		sql.append(" where drug_code = :drug_code ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
}
