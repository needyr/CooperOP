package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DictHisDrugDao extends BaseDao {
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select a.*,dsd.shuoms_file,dsy.DRUG_YLFL_NAME, ");
		sql.append("stuff(  ");
		sql.append("(select ','+DRUGTAGBH+'-'+  ");
		sql.append("DRUGTAG_SHUOM+'-'+   ");
		sql.append("DRUGTAG_SHOW    ");
		sql.append("from dict_sys_drug_tag (nolock)   ");
		sql.append("where ");
		sql.append("charindex(','+DRUGTAGBH+',', ','+a.tags+',')>0  ");
		sql.append("and BEACTIVE = '是'            ");
		sql.append("group by DRUGTAGBH,DRUGTAG_SHUOM,  ");
		sql.append("DRUGTAG_SHOW for xml path('')),1,1,'' ");
		sql.append(") drug_tags  ");
		sql.append(" from dict_his_drug (nolock) a ");
		sql.append(" left join dict_sys_drug (nolock) dsd on dsd.p_key=a.sys_p_key ");
		sql.append(" left join dict_sys_ylfl (nolock) dsy on dsy.DRUG_YLFL_CODE=a.drug_ylfl where 1=1 ");
		setParameter(params, "filter", " and (a.drug_code = :filter or a.drug_name like '%'+'"+params.get("filter")+"'+'%' or a.input_code like '%'+'"+params.get("filter")+"'+'%')", sql);
		if(!"all".equals(params.get("drug_indicator"))) {
			sql.append(" and a.drug_indicator = :drug_indicator ");
		}
		if("0".equals(params.get("is_wh_filter"))) {
			sql.append(" and (a.is_wh = 0 or isnull(is_wh,'')='' )");
		}else if("1".equals(params.get("is_wh_filter"))) {
			sql.append(" and a.is_wh = 1");
		}
		if(!CommonFun.isNe(params.get("filter_kjy"))) {
			Object object = params.get("filter_kjy");
			if(object instanceof String) {
				if("1".equals(object)) {
					sql.append(" and isnull(a.IS_ANTI_DRUG,'0')<>0 ");
				}else {
					sql.append(" and isnull(a.IS_ANTI_DRUG,'0')=0 ");
				}
			}
		}
		if(!CommonFun.isNe(params.get("filter_kc"))) {
			Object object = params.get("filter_kc");
			if(object instanceof String) {
				if("1".equals(object)) {
					sql.append(" and a.shl>0 ");
				}else {
					sql.append(" and isnull(a.shl,0)<=0 ");
				}
			}
		}
		if(!CommonFun.isNe(params.get("drug_tag_fifter"))) {
			String drug_tag_fifter = (String)params.get("drug_tag_fifter");
			sql.append(" and exists (select 1 from (select col from split(a.tags,',')) ddd inner join split('"+drug_tag_fifter+"',',') eee on ddd.col=eee.col ) ");
		}
		
		if (!CommonFun.isNe(params.get("is_attr")) && !"-1".equals(params.get("is_attr"))) {
			if ("0".equals(params.get("is_attr"))) {
				sql.append(" and isnull(check_drug_mx,'') = '' ");
			}else {
				sql.append(" and check_drug_mx = '1' ");
			}
		}
		
		if (!CommonFun.isNe(params.get("is_tpn")) && !"-1".equals(params.get("is_tpn"))) {
			if ("0".equals(params.get("is_tpn"))) {
				sql.append("and not exists (                ");
				sql.append("SELECT 1 FROM TPNZL aa          ");
				sql.append("inner join tpnzl_rule bb        ");
				sql.append("on aa.tpnzl_id=bb.tpnzl_id      ");
				sql.append("where aa.state = 1              ");
				sql.append("and bb.fdname ='drug_name'      ");
				sql.append("and bb.formul = 'like'          ");
				sql.append("and a.drug_name like bb.value)  ");
			}else {
				sql.append("and exists (                    ");
				sql.append("SELECT 1 FROM TPNZL aa          ");
				sql.append("inner join tpnzl_rule bb        ");
				sql.append("on aa.tpnzl_id=bb.tpnzl_id      ");
				sql.append("where aa.state = 1              ");
				sql.append("and bb.fdname ='drug_name'      ");
				sql.append("and bb.formul = 'like'          ");
				sql.append("and a.drug_name like bb.value)  ");
			}
		}
		
		params.put("sort", "drug_code");
		return executeQueryLimit(sql.toString(), params);
	}

	public void updateByCode(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("drug_code", params.remove("drug_code"));
		executeUpdate("dict_his_drug", params, r);
	}
	
	public void updateSysByCodeShuoms(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("update a set shuoms_file = :shuoms_file ");
		sql.append("from dict_sys_drug a                    ");
		sql.append("where exists (select 1                  ");
		sql.append("from dict_his_drug (nolock) b           ");
		sql.append("where a.p_key=b.sys_p_key               ");
		sql.append("and b.drug_code=:drug_code)             ");
		execute(sql.toString(), params);
	}
	
	public Record getShuoms_file(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select a.shuoms_file from dict_sys_drug (nolock) a ");
		sql.append("where exists (select 1                  ");
		sql.append("from dict_his_drug (nolock) b           ");
		sql.append("where a.p_key=b.sys_p_key               ");
		sql.append("and b.drug_code=:drug_code)             ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result searchByDrugTag(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from dict_his_drug                 "); 
		sql.append("where CHARINDEX(','+:drugtagbh+',', ','+tags+',')>0 "); 
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryKJYPropertyToxi(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select PROPERTY_TOXI from dict_his_drug "); 
		sql.append("where isnull(IS_ANTI_DRUG,'')<>0 "); 
		sql.append("group by PROPERTY_TOXI "); 
		return executeQuery(sql.toString(), params);
	}
	
	public void updateTag(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update a set a.tags =                ");
		sql.append("(STUFF((select ','+col from          ");
		sql.append("split(isnull(a.tags,'') + ',' + :drugtagbh,',') ");
		sql.append("where isnull(col,'')<>''             ");
		sql.append("and col <>                           ");
		sql.append("(case when exists                    ");
		sql.append("(select 1 from dict_his_drug(nolock) ");
		sql.append("where a.drug_code = drug_code        ");
		sql.append("and drug_code in (:drug_code))       ");
		sql.append("then '' else :drugtagbh end)         ");
		sql.append("group by col for xml path(''))       ");
		sql.append(",1,1,''))                            ");
		sql.append("from dict_his_drug a                 ");
		sql.append("where drug_code in (:drug_code)      ");
		sql.append("or charindex(','+:drugtagbh+',',','+a.tags+',')>0 ");
		execute(sql.toString(), params);
	}

	public Result queryAttrTree(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT (select                            ");
		sql.append(" count(1) from dict_sys_drug_XM (nolock)   ");
		sql.append(" where class_code=a.class_code             ");
		sql.append(" ) as childnums,                           ");
		sql.append(" null XMID,                                ");
		sql.append(" null XMTYPE,null XMBH,                    ");
		sql.append(" class_name XMMCH,null BEACTIVE,           ");
		sql.append(" null PYM,null fdtype,                     ");
		sql.append(" null xmdw,null username,                  ");
		sql.append(" null lasttime,null parent_id,             ");
		sql.append(" class_code,class_name,                    ");
		sql.append(" null as value,                            ");
		sql.append(" null as select_attr_value                 ");
		sql.append(" FROM DICT_SYS_DRUG_XM_CLASS (nolock) a    ");
		sql.append(" union all                                 ");
		sql.append(" SELECT                                    ");
		sql.append(" 0 childnums,                              ");
		sql.append(" a.XMID,a.XMTYPE,a.XMBH,a.XMMCH,           ");
		sql.append(" a.BEACTIVE,a.PYM,a.fdtype,                ");
		sql.append(" a.xmdw,a.username,a.lasttime,             ");
		sql.append(" a.class_code parent_id,                   ");
		sql.append(" a.class_code,                             ");
		sql.append(" null class_name,                          ");
		sql.append(" b.value as value,                         ");
		sql.append(" stuff((                                   ");
		sql.append(" select ','+VALUE from                     ");
		sql.append(" DICT_SYS_DRUG_XM_VALUE(nolock)            ");
		sql.append(" where a.XMID=XMID group by value          ");
		sql.append(" for xml path('')                          ");
		sql.append(" ),1,1,'') as select_attr_value            ");
		sql.append(" FROM dict_sys_drug_XM (nolock) a          ");
		sql.append(" left join DICT_HIS_DRUG_MX (nolock) b     ");
		sql.append(" on a.XMID=b.XMID and DRUG_CODE=:drug_code ");
		sql.append(" where BEACTIVE = '是'                     ");
		return executeQuery(sql.toString(), params);
	}

	public Result queryAttrByDrugCode(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM DICT_HIS_DRUG_MX (nolock) where DRUG_CODE=:drug_code ");
		return executeQuery(sql.toString(), params);
	}
	
	public String deleteAttrByDrugCode(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete FROM DICT_HIS_DRUG_MX where DRUG_CODE=:drug_code ");
		return execute(sql.toString(), params);
	}
}
