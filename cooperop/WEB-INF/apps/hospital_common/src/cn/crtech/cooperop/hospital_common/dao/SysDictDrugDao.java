package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.ProductmanagetService;


public class SysDictDrugDao extends BaseDao{

	private final static String TABLE_NAME = "dict_sys_drug";
	
	//设置了自定义审查的药品
	public Result queryzdysc(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select * from "+TABLE_NAME+" (nolock) where 1=1 ");
		sql.append("and P_KEY in ( ");
		if(!CommonFun.isNe(params.get("hasset"))) {
			try {
				String[] hasset  = (String[]) params.get("hasset");
				for (int i = 0; i < hasset.length; i++) {
					if(i == hasset.length -1) {
						sql.append(" select SYS_P_KEY from "+hasset[i]+" (nolock)  ");
					}else {
						sql.append(" select SYS_P_KEY from "+hasset[i]+" (nolock) union ");
					}
				}
			} catch (Exception e) {
				sql.append(" select SYS_P_KEY from " + params.get("hasset")  +" (nolock)  ");
			}
		}else {
			sql.append(" null ");	
		}
		sql.append(")");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
		}
		setParameter(params, "key", " and (drug_name like :key or drug_code like :key or input_code like :key)", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("drug_code", params.remove("drug_code"));
		return executeUpdate(TABLE_NAME, params, r);
	}
	
	
	public Result querynotzdysc(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select * from "+TABLE_NAME+" (nolock) where 1=1 ");
		sql.append("and P_KEY not in ( ");
		sql.append(" select SYS_P_KEY from sys_shengfangzl_pl (nolock) where SYS_P_KEY is not null union ");
		sql.append(" select SYS_P_KEY from sys_shengfangzl_routename (nolock) where SYS_P_KEY is not null union ");
		sql.append(" select SYS_P_KEY from sys_shengfangzl_peiw (nolock) where SYS_P_KEY is not null union ");
		sql.append(" select SYS_P_KEY from sys_shengfangzl_yongl (nolock) where SYS_P_KEY is not null union ");
		sql.append(" select SYS_P_KEY from sys_shengfangzl_rongm (nolock) where SYS_P_KEY is not null union");
		sql.append(" select SYS_P_KEY from dict_sys_drug_mx (nolock) where SYS_P_KEY is not null union");
		sql.append(" select SYS_P_KEY from sys_shengfangzl_jjz (nolock) where SYS_P_KEY is not null union");
		sql.append(" select SYS_P_KEY from sys_shengfangzl_xhzy (nolock) where SYS_P_KEY is not null union");
		sql.append(" select SYS_P_KEY from sys_shengfangzl_syz (nolock) where SYS_P_KEY is not null union");
		sql.append(" select SYS_P_KEY from sys_shengfangzl_ccr (nolock) where SYS_P_KEY is not null ");
		sql.append(")");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("filter", "%"+params.get("filter")+"%");
			sql.append("and (drug_name like :filter or drug_code like :filter )");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	
	//药品信息单条查询
	public Record getforzdy(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select *,dbo.ClearZero(usejlgg) +isnull(use_dw,'') as syjl from dict_sys_drug with(nolock) where 1 = 1");
		if(CommonFun.isNe(params.get("drug_code"))){
			params.put("drug_code", "0");
		}
		sql.append(" and drug_code = :drug_code ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	
	
	public Result queryZdyAll(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		if (!CommonFun.isNe(params.get("filter"))) {
			params.put("key", "%"+params.get("filter")+"%");
		}
		if(!CommonFun.isNe(params.get("hasset"))) {
			try {
				String[] hasset  = (String[]) params.get("hasset");
				for (int i = 0; i < hasset.length; i++) {
					if (hasset[i].equals("syssfpeiw")) {
						sql.append("select sf.id,dsc.level_name sys_check_level_name,acs.sort_name,sf.nwarn_message,");
						sql.append(" nianl_start,nianl_end,nianl_unit,routename,Weight_START,Weight_END,Weight_UNIT,");
						sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+hasset[i]+"' table_name, ");
						sql.append(" drug.shengccj,drug.druggg,drug.drug_code, ");
						sql.append("case when Formul='>' then '大于'              ");
						sql.append("		 when Formul='<' then '小于'          ");
						sql.append("		  when Formul='<>' then '不等于'      ");
						sql.append("			 when Formul='like' then '类似'   ");
						sql.append("		when Formul='not like' then '不类似'  ");
						sql.append("		 when Formul='=' then '等于'          ");
						sql.append("		end as Formul_name                    ");
						sql.append("	 from sys_shengfangzl_peiw (nolock) sf");
						sql.append(" left join dict_sys_drug (nolock) drug on sf.sys_p_key = drug.drug_code ");
						sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
						setParameter(params, "key", "where sf.sys_p_key= :filter or drug.drug_name like :key or drug.input_code like :key", sql);
					}
					if (hasset[i].equals("syssfrongm")) {
						if(i != 0) {
							sql.append(" union ");
						}
						sql.append("select sf.id,dsc.level_name sys_check_level_name,acs.sort_name,sf.nwarn_message,");
						sql.append(" nianl_start,nianl_end,nianl_unit,routename,Weight_START,Weight_END,Weight_UNIT,");
						sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+hasset[i]+"' table_name, ");
						sql.append(" drug.shengccj,drug.druggg,drug.drug_code, ");
						sql.append("case when Formul='>' then '大于'              ");
						sql.append("		 when Formul='<' then '小于'          ");
						sql.append("		  when Formul='<>' then '不等于'      ");
						sql.append("			 when Formul='like' then '类似'   ");
						sql.append("		when Formul='not like' then '不类似'  ");
						sql.append("		 when Formul='=' then '等于'          ");
						sql.append("		end as Formul_name                    ");
						sql.append("	 from sys_shengfangzl_rongm (nolock) sf");
						sql.append(" left join dict_sys_drug (nolock) drug on sf.sys_p_key = drug.drug_code ");
						sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
						setParameter(params, "key", "where sf.sys_p_key= :filter or drug.drug_name like :key or drug.input_code like :key", sql);
					}
					if (hasset[i].equals("syssfyongl")) {
						if(i != 0) {
							sql.append(" union ");
						}
						sql.append("select sf.id,dsc.level_name sys_check_level_name,acs.sort_name,sf.nwarn_message,");
						sql.append(" nianl_start,nianl_end,nianl_unit,routename,Weight_START,Weight_END,Weight_UNIT,");
						sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+hasset[i]+"' table_name, ");
						sql.append(" drug.shengccj,drug.druggg,drug.drug_code, ");
						sql.append("case when Formul='>' then '大于'              ");
						sql.append("		 when Formul='<' then '小于'          ");
						sql.append("		  when Formul='<>' then '不等于'      ");
						sql.append("			 when Formul='like' then '类似'   ");
						sql.append("		when Formul='not like' then '不类似'  ");
						sql.append("		 when Formul='=' then '等于'          ");
						sql.append("		end as Formul_name                    ");
						sql.append("	 from sys_shengfangzl_yongl (nolock) sf");
						sql.append(" left join dict_sys_drug (nolock) drug on sf.sys_p_key = drug.drug_code ");
						sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
						setParameter(params, "key", "where sf.sys_p_key= :filter or drug.drug_name like :key or drug.input_code like :key", sql);
					}
					if (hasset[i].equals("syssfroute")) {
						if(i != 0) {
							sql.append(" union ");
						}
						sql.append("select sf.id,dsc.level_name sys_check_level_name,acs.sort_name,sf.nwarn_message,");
						sql.append(" nianl_start,nianl_end,nianl_unit,routename,Weight_START,Weight_END,Weight_UNIT,");
						sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+hasset[i]+"' table_name, ");
						sql.append(" drug.shengccj,drug.druggg,drug.drug_code, ");
						sql.append("case when Formul='>' then '大于'              ");
						sql.append("		 when Formul='<' then '小于'          ");
						sql.append("		  when Formul='<>' then '不等于'      ");
						sql.append("			 when Formul='like' then '类似'   ");
						sql.append("		when Formul='not like' then '不类似'  ");
						sql.append("		 when Formul='=' then '等于'          ");
						sql.append("		end as Formul_name                    ");
						sql.append("	 from sys_shengfangzl_routename (nolock) sf");
						sql.append(" left join dict_sys_drug (nolock) drug on sf.sys_p_key = drug.drug_code ");
						sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
						setParameter(params, "key", "where sf.sys_p_key= :filter or drug.drug_name like :key or drug.input_code like :key", sql);
					}
					if (hasset[i].equals("syssfpl")) {
						if(i != 0) {
							sql.append(" union ");
						}
						sql.append("select sf.id,dsc.level_name sys_check_level_name,acs.sort_name,sf.nwarn_message,");
						sql.append(" nianl_start,nianl_end,nianl_unit,routename,Weight_START,Weight_END,Weight_UNIT,");
						sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+hasset[i]+"' table_name, ");
						sql.append(" drug.shengccj,drug.druggg,drug.drug_code, ");
						sql.append("case when Formul='>' then '大于'              ");
						sql.append("		 when Formul='<' then '小于'          ");
						sql.append("		  when Formul='<>' then '不等于'      ");
						sql.append("			 when Formul='like' then '类似'   ");
						sql.append("		when Formul='not like' then '不类似'  ");
						sql.append("		 when Formul='=' then '等于'          ");
						sql.append("		end as Formul_name                    ");
						sql.append("	 from sys_shengfangzl_PL (nolock) sf");
						sql.append(" left join dict_sys_drug (nolock) drug on sf.sys_p_key = drug.drug_code ");
						sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
						setParameter(params, "key", "where sf.sys_p_key= :filter or drug.drug_name like :key or drug.input_code like :key", sql);
					}
					if (hasset[i].equals("syssfccr")) {
						if(i != 0) {
							sql.append(" union ");
						}
						sql.append("select sf.id,dsc.level_name sys_check_level_name,acs.sort_name,sf.nwarn_message,");
						sql.append(" nianl_start,nianl_end,nianl_unit,routename,Weight_START,Weight_END,Weight_UNIT,");
						sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+hasset[i]+"' table_name, ");
						sql.append(" drug.shengccj,drug.druggg,drug.drug_code, ");
						sql.append("case when Formul='>' then '大于'              ");
						sql.append("		 when Formul='<' then '小于'          ");
						sql.append("		  when Formul='<>' then '不等于'      ");
						sql.append("			 when Formul='like' then '类似'   ");
						sql.append("		when Formul='not like' then '不类似'  ");
						sql.append("		 when Formul='=' then '等于'          ");
						sql.append("		end as Formul_name                    ");
						sql.append("	 from sys_shengfangzl_ccr (nolock) sf");
						sql.append(" left join dict_sys_drug (nolock) drug on sf.sys_p_key = drug.drug_code ");
						sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
						setParameter(params, "key", "where sf.sys_p_key= :filter or drug.drug_name like :key or drug.input_code like :key", sql);
					}
					if (hasset[i].equals("syssfjjz")) {
						if(i != 0) {
							sql.append(" union ");
						}
						sql.append("select sf.id,dsc.level_name sys_check_level_name,acs.sort_name,sf.nwarn_message,");
						sql.append(" nianl_start,nianl_end,nianl_unit,'' as routename,Weight_START,Weight_END,Weight_UNIT,");
						sql.append(" sf.tiaojian,sf.xmmch,'null' as value,drug.drug_name,drug.drug_unit,'"+hasset[i]+"' table_name, ");
						sql.append(" drug.shengccj,drug.druggg,drug.drug_code  , 'null' as Formul_name  ");
						sql.append("	 from sys_shengfangzl_jjz (nolock) sf");
						sql.append(" left join dict_sys_drug (nolock) drug on sf.sys_p_key = drug.drug_code ");
						sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
						setParameter(params, "key", "where sf.sys_p_key= :filter or drug.drug_name like :key or drug.input_code like :key", sql);
					}
					if (hasset[i].equals("syssfsyz")) {
						if(i != 0) {
							sql.append(" union ");
						}
						sql.append("select sf.id,dsc.level_name sys_check_level_name,acs.sort_name,sf.nwarn_message,");
						sql.append(" nianl_start,nianl_end,nianl_unit,'' as routename,Weight_START,Weight_END,Weight_UNIT,");
						sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+hasset[i]+"' table_name, ");
						sql.append(" drug.shengccj,drug.druggg,drug.drug_code, ");
						sql.append("case when Formul='>' then '大于'              ");
						sql.append("		 when Formul='<' then '小于'          ");
						sql.append("		  when Formul='<>' then '不等于'      ");
						sql.append("			 when Formul='like' then '类似'   ");
						sql.append("		when Formul='not like' then '不类似'  ");
						sql.append("		 when Formul='=' then '等于'          ");
						sql.append("		end as Formul_name                    ");
						sql.append("	 from sys_shengfangzl_syz (nolock) sf");
						sql.append(" left join dict_sys_drug (nolock) drug on sf.sys_p_key = drug.drug_code ");
						sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
						setParameter(params, "key", "where sf.sys_p_key= :filter or drug.drug_name like :key or drug.input_code like :key", sql);
					}
					if (hasset[i].equals("syssfxhzy")) {
						if(i != 0) {
							sql.append(" union ");
						}
						sql.append("select sf.id,dsc.level_name sys_check_level_name,acs.sort_name,sf.nwarn_message,");
						sql.append(" nianl_start,nianl_end,nianl_unit,routename,Weight_START,Weight_END,Weight_UNIT,");
						sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+hasset[i]+"' table_name, ");
						sql.append(" drug.shengccj,drug.druggg,drug.drug_code, ");
						sql.append("case when Formul='>' then '大于'              ");
						sql.append("		 when Formul='<' then '小于'          ");
						sql.append("		  when Formul='<>' then '不等于'      ");
						sql.append("			 when Formul='like' then '类似'   ");
						sql.append("		when Formul='not like' then '不类似'  ");
						sql.append("		 when Formul='=' then '等于'          ");
						sql.append("		end as Formul_name                    ");
						sql.append("	 from sys_shengfangzl_xhzy (nolock) sf");
						sql.append(" left join dict_sys_drug (nolock) drug on sf.sys_p_key = drug.drug_code ");
						sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
						sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
						setParameter(params, "key", "where sf.sys_p_key= :filter or drug.drug_name like :key or drug.input_code like :key", sql);
					}
				}
			} catch (Exception e) {
				String hasset  = (String) params.get("hasset");
				if (hasset.equals("syssfpeiw")) {
					sql.append("select sf.id,dsc.level_name sys_check_level_name,acs.sort_name,sf.nwarn_message,");
					sql.append(" nianl_start,nianl_end,nianl_unit,routename,Weight_START,Weight_END,Weight_UNIT,");
					sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+hasset+"' table_name, ");
					sql.append(" drug.shengccj,drug.druggg,drug.drug_code, ");
					sql.append("case when Formul='>' then '大于'              ");
					sql.append("		 when Formul='<' then '小于'          ");
					sql.append("		  when Formul='<>' then '不等于'      ");
					sql.append("			 when Formul='like' then '类似'   ");
					sql.append("		when Formul='not like' then '不类似'  ");
					sql.append("		 when Formul='=' then '等于'          ");
					sql.append("		end as Formul_name                    ");
					sql.append("	 from sys_shengfangzl_peiw (nolock) sf");
					sql.append(" left join dict_sys_drug (nolock) drug on sf.sys_p_key = drug.drug_code ");
					sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
					sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
					setParameter(params, "key", "where sf.sys_p_key= :filter or drug.drug_name like :key or drug.input_code like :key", sql);
					return executeQueryLimit(sql.toString(), params);
				}
				if (hasset.equals("syssfrongm")) {
					sql.append("select sf.id,dsc.level_name sys_check_level_name,acs.sort_name,sf.nwarn_message,");
					sql.append(" nianl_start,nianl_end,nianl_unit,routename,Weight_START,Weight_END,Weight_UNIT,");
					sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+hasset+"' table_name, ");
					sql.append(" drug.shengccj,drug.druggg,drug.drug_code, ");
					sql.append("case when Formul='>' then '大于'              ");
					sql.append("		 when Formul='<' then '小于'          ");
					sql.append("		  when Formul='<>' then '不等于'      ");
					sql.append("			 when Formul='like' then '类似'   ");
					sql.append("		when Formul='not like' then '不类似'  ");
					sql.append("		 when Formul='=' then '等于'          ");
					sql.append("		end as Formul_name                    ");
					sql.append("	 from sys_shengfangzl_rongm (nolock) sf");
					sql.append(" left join dict_sys_drug (nolock) drug on sf.sys_p_key = drug.drug_code ");
					sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
					sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
					setParameter(params, "key", "where sf.sys_p_key= :filter or drug.drug_name like :key or drug.input_code like :key", sql);
					return executeQueryLimit(sql.toString(), params);	
				}
				if (hasset.equals("syssfyongl")) {
					sql.append("select sf.id,dsc.level_name sys_check_level_name,acs.sort_name,sf.nwarn_message,");
					sql.append(" nianl_start,nianl_end,nianl_unit,routename,Weight_START,Weight_END,Weight_UNIT,");
					sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+hasset+"' table_name, ");
					sql.append(" drug.shengccj,drug.druggg,drug.drug_code, ");
					sql.append("case when Formul='>' then '大于'              ");
					sql.append("		 when Formul='<' then '小于'          ");
					sql.append("		  when Formul='<>' then '不等于'      ");
					sql.append("			 when Formul='like' then '类似'   ");
					sql.append("		when Formul='not like' then '不类似'  ");
					sql.append("		 when Formul='=' then '等于'          ");
					sql.append("		end as Formul_name                    ");
					sql.append("	 from sys_shengfangzl_yongl (nolock) sf");
					sql.append(" left join dict_sys_drug (nolock) drug on sf.sys_p_key = drug.drug_code ");
					sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
					sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
					setParameter(params, "key", "where sf.sys_p_key= :filter or drug.drug_name like :key or drug.input_code like :key", sql);
					return executeQueryLimit(sql.toString(), params);
				}
				if (hasset.equals("syssfroute")) {
					sql.append("select sf.id,dsc.level_name sys_check_level_name,acs.sort_name,sf.nwarn_message,");
					sql.append(" nianl_start,nianl_end,nianl_unit,routename,Weight_START,Weight_END,Weight_UNIT,");
					sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+hasset+"' table_name, ");
					sql.append(" drug.shengccj,drug.druggg,drug.drug_code, ");
					sql.append("case when Formul='>' then '大于'              ");
					sql.append("		 when Formul='<' then '小于'          ");
					sql.append("		  when Formul='<>' then '不等于'      ");
					sql.append("			 when Formul='like' then '类似'   ");
					sql.append("		when Formul='not like' then '不类似'  ");
					sql.append("		 when Formul='=' then '等于'          ");
					sql.append("		end as Formul_name                    ");
					sql.append("	 from sys_shengfangzl_routename (nolock) sf");
					sql.append(" left join dict_sys_drug (nolock) drug on sf.sys_p_key = drug.drug_code ");
					sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
					sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
					setParameter(params, "key", "where sf.sys_p_key= :filter or drug.drug_name like :key or drug.input_code like :key", sql);
					return executeQueryLimit(sql.toString(), params);
				}
				if (hasset.equals("syssfpl")) {
					sql.append("select sf.id,dsc.level_name sys_check_level_name,acs.sort_name,sf.nwarn_message,");
					sql.append(" nianl_start,nianl_end,nianl_unit,routename,Weight_START,Weight_END,Weight_UNIT,");
					sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+hasset+"' table_name, ");
					sql.append(" drug.shengccj,drug.druggg,drug.drug_code, ");
					sql.append("case when Formul='>' then '大于'              ");
					sql.append("		 when Formul='<' then '小于'          ");
					sql.append("		  when Formul='<>' then '不等于'      ");
					sql.append("			 when Formul='like' then '类似'   ");
					sql.append("		when Formul='not like' then '不类似'  ");
					sql.append("		 when Formul='=' then '等于'          ");
					sql.append("		end as Formul_name                    ");
					sql.append("	 from sys_shengfangzl_PL (nolock) sf");
					sql.append(" left join dict_sys_drug (nolock) drug on sf.sys_p_key = drug.drug_code ");
					sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
					sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
					setParameter(params, "key", "where sf.sys_p_key= :filter or drug.drug_name like :key or drug.input_code like :key", sql);
					return executeQueryLimit(sql.toString(), params);
				}
				if (hasset.equals("syssfccr")) {
					sql.append("select sf.id,dsc.level_name sys_check_level_name,acs.sort_name,sf.nwarn_message,");
					sql.append(" nianl_start,nianl_end,nianl_unit,routename,Weight_START,Weight_END,Weight_UNIT,");
					sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+hasset+"' table_name, ");
					sql.append(" drug.shengccj,drug.druggg,drug.drug_code, ");
					sql.append("case when Formul='>' then '大于'              ");
					sql.append("		 when Formul='<' then '小于'          ");
					sql.append("		  when Formul='<>' then '不等于'      ");
					sql.append("			 when Formul='like' then '类似'   ");
					sql.append("		when Formul='not like' then '不类似'  ");
					sql.append("		 when Formul='=' then '等于'          ");
					sql.append("		end as Formul_name                    ");
					sql.append("	 from sys_shengfangzl_ccr (nolock) sf");
					sql.append(" left join dict_sys_drug (nolock) drug on sf.sys_p_key = drug.drug_code ");
					sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
					sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
					setParameter(params, "key", "where sf.sys_p_key= :filter or drug.drug_name like :key or drug.input_code like :key", sql);
					return executeQueryLimit(sql.toString(), params);
				}
				if (hasset.equals("syssfjjz")) {
					sql.append("select sf.id,dsc.level_name sys_check_level_name,acs.sort_name,sf.nwarn_message,");
					sql.append(" nianl_start,nianl_end,nianl_unit,'' as routename,Weight_START,Weight_END,Weight_UNIT,");
					sql.append(" sf.tiaojian,sf.xmmch,drug.drug_name,drug.drug_unit,'"+hasset+"' table_name, ");
					sql.append(" drug.shengccj,drug.druggg,drug.drug_code");
					sql.append("	 from sys_shengfangzl_jjz (nolock) sf");
					sql.append(" left join dict_sys_drug (nolock) drug on sf.sys_p_key = drug.drug_code ");
					sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
					sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
					setParameter(params, "key", "where sf.sys_p_key= :filter or drug.drug_name like :key or drug.input_code like :key", sql);
					return executeQueryLimit(sql.toString(), params);
				}
				if (hasset.equals("syssfsyz")) {
					sql.append("select sf.id,dsc.level_name sys_check_level_name,acs.sort_name,sf.nwarn_message,");
					sql.append(" nianl_start,nianl_end,nianl_unit,'' as routename,Weight_START,Weight_END,Weight_UNIT,");
					sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+hasset+"' table_name, ");
					sql.append(" drug.shengccj,drug.druggg,drug.drug_code, ");
					sql.append("case when Formul='>' then '大于'              ");
					sql.append("		 when Formul='<' then '小于'          ");
					sql.append("		  when Formul='<>' then '不等于'      ");
					sql.append("			 when Formul='like' then '类似'   ");
					sql.append("		when Formul='not like' then '不类似'  ");
					sql.append("		 when Formul='=' then '等于'          ");
					sql.append("		end as Formul_name                    ");
					sql.append("	 from sys_shengfangzl_syz (nolock) sf");
					sql.append(" left join dict_sys_drug (nolock) drug on sf.sys_p_key = drug.drug_code ");
					sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
					sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
					setParameter(params, "key", "where sf.sys_p_key= :filter or drug.drug_name like :key or drug.input_code like :key", sql);
					return executeQueryLimit(sql.toString(), params);
				}
				if (hasset.equals("syssfxhzy")) {
					sql.append("select sf.id,dsc.level_name sys_check_level_name,acs.sort_name,sf.nwarn_message,");
					sql.append(" nianl_start,nianl_end,nianl_unit,routename,Weight_START,Weight_END,Weight_UNIT,");
					sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+hasset+"' table_name, ");
					sql.append(" drug.shengccj,drug.druggg,drug.drug_code, ");
					sql.append("case when Formul='>' then '大于'              ");
					sql.append("		 when Formul='<' then '小于'          ");
					sql.append("		  when Formul='<>' then '不等于'      ");
					sql.append("			 when Formul='like' then '类似'   ");
					sql.append("		when Formul='not like' then '不类似'  ");
					sql.append("		 when Formul='=' then '等于'          ");
					sql.append("		end as Formul_name                    ");
					sql.append("	 from sys_shengfangzl_xhzy (nolock) sf");
					sql.append(" left join dict_sys_drug (nolock) drug on sf.sys_p_key = drug.drug_code ");
					sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
					sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
					setParameter(params, "key", "where sf.sys_p_key= :filter or drug.drug_name like :key or drug.input_code like :key", sql);
					return executeQueryLimit(sql.toString(), params);
				}
			}
			return executeQueryLimit(sql.toString(), params);
		}else {
			return null;
		}
	}

}
