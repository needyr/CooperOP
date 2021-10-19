package cn.crtech.cooperop.hospital_common.dao.dict;

import java.util.HashMap;
import java.util.Map;
import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

/**
 * @className: SysSpcommentDao   
 * @description: 专项点评规则
 * @author: 魏圣峰 
 * @date: 2019年3月1日 上午11:40:14
 */
public class SysSpcommentDao extends BaseDao{
	/** 专项点评字典 */
	private final static String TABLE_NAME = "dict_sys_spcomment";
	/** 药品标签 */
	private final static String DICT_SYS_DRUG_TAG = "DICT_SYS_DRUG_TAG";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" select a.*,");
		sql.append("stuff(");
		sql.append("( select ','+drugtagname");
		sql.append(" from "+DICT_SYS_DRUG_TAG+" b(nolock) ");
		sql.append(" where charindex(','+b.drugtagbh+',',','+a.drug_tags+',')>0 and isnull(b.is_tag,'') = '1' ");
		sql.append(" for xml path('')");
		sql.append(")");
		sql.append(",1,1,''");
		sql.append(")as ordertag");
		sql.append(" from "+TABLE_NAME+" a (nolock)");
		sql.append(" where 1=1");
		if(!CommonFun.isNe(params.get("filter"))) {
			sql.append(" and (spcomment_code like '%"+params.get("filter")+"%'");
			sql.append(" or spcomment_name like '%"+params.get("filter")+"%'");
			sql.append(" or pym like '%"+params.get("filter")+"%')");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.*,b.drugtagid,b.drugtagbh,drugtagname,drugtag_show");
		sql.append(" from "+TABLE_NAME+" a (nolock)");
		sql.append(" left join "+DICT_SYS_DRUG_TAG+" b (nolock) on charindex(','+b.drugtagbh+',',','+a.drug_tags+',')>0 ");
		sql.append(" where (isnull(b.is_tag,'') = '1' or isnull(a.drug_tags,'')='') ");
		sql.append(" and a.id = :id");
		return executeQuery(sql.toString(), params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record id=new Record();
		id.put("id", params.remove("id"));
		executeUpdate(TABLE_NAME, params, id);
		return 1;
	}
	
	/**
	 * @author: 魏圣峰
	 * @description: 插入专项点评和明细表
	 * @param: params Map
	 * @return: int      
	 * @throws: Exception
	 */
	public int insert(Map<String, Object> params) throws Exception {
		params.put("spcomment_code", CommonFun.getSSID());
		executeInsert(TABLE_NAME, params);
		return 1;
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		Map<String,Object> id = new HashMap<String,Object>();
		id.put("id", params.get("id"));
		return executeDelete(TABLE_NAME, id);//删除点评表
	}
	
	/**
	 * @author: 魏圣峰
	 * @description: 查询药品标签
	 * @param: params Map 
	 * @return: Result      
	 * @throws: Exception
	 */
	public Result queryTags(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select drugtagid,drugtagbh,drugtagname,drugtag_show,drugtag_shuom "); 
		sql.append(" from "+DICT_SYS_DRUG_TAG+" (nolock) where beactive = '是' "); 
		params.put("sort", "drugtagbh");
		return executeQuery(sql.toString(), params);
	}
	
	public Record getByCode(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * ");
		sql.append(" from "+TABLE_NAME+" (nolock) ");
		sql.append(" where 1=1");
		sql.append(" and spcomment_code = :spcomment_code");
		return executeQuerySingleRecord(sql.toString(), params);
	}
}
