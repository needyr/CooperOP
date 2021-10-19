package cn.crtech.cooperop.hospital_common.dao;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class CommentManageDao extends BaseDao {

	public final static String TABLE_NAME = "dict_sys_comment";

	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT                                                                        ");
		sql.append(" f.*, (                                                                        ");
		sql.append(" 	SELECT                                                                     ");
		sql.append(" 		COUNT (1)                                                              ");
		sql.append(" 	FROM                                                                       ");
		sql.append(" 		" + TABLE_NAME + " (nolock)                                                     ");
		sql.append(" 	WHERE                                                                      ");
		sql.append(" 		parent_id = f.id                                                       ");
		sql.append(" ) AS childnums                                                                ");
		sql.append(" from " + TABLE_NAME + " f order by f.sort");
		return executeQuery(sql.toString(), params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		params.remove("id");
		return executeInsert(TABLE_NAME, params);
	}

	public int update(Map<String, Object> params) throws Exception {
		Record record = new Record();
		record.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME, params, record);
	}

	public Result queryProduct(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from system_product (nolock)");
		return executeQuery(sql.toString(), params);
	}

	public Result queryComment(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from (");
		sql.append(" select d.* from comment_sample a  (nolock)                                                           ");
		sql.append(" left join hospital_common..DICT_SYS_Spcomment b (nolock) on                                           ");
		sql.append(" a.Spcomment_code=b.Spcomment_code                                                            ");
		sql.append(" left join dict_sys_comment c (nolock) on (charindex(                         ");
		sql.append(" substring(c.system_code,1,len(c.system_code)-(charindex('-',reverse(c.system_code))-1)-1)+',',b.sys_comment_Rules_code+',')>0 ");
		sql.append(" and b.sys_comment_Rules_code is not null)                                                    ");
		sql.append(" or (charindex(                                                                 ");
		sql.append(" substring(c.system_code,1,len(c.system_code)-(charindex('-',reverse(c.system_code))-1)-1)+',','(1)-1,')>0 "); 
		sql.append(" and b.sys_comment_Rules_code is null)                                                        ");
		sql.append(" left join dict_sys_comment d (nolock) on c.id=d.parent_id ");
		sql.append(" where                                                                                        ");
		sql.append(" a.id = :sample_id                                                                            ");
		sql.append(" and c.level='3'                                                                              ");
		sql.append(" and c.beactive = '是' ) ss where 1=1 ");
		if(!CommonFun.isNe(params.get("filter"))) {
			params.put("filter", "%" +params.get("filter") + "%");
			sql.append(" and ss.comment_name like :filter ");
		}
		if(!CommonFun.isNe(params.get("data_choose"))) {
			if (params.get("data_choose").equals("0")) {
				sql.append(" and ss.is_hl= '0' ");
			}if (params.get("data_choose").equals("1")) {
				sql.append(" and ss.is_hl= '1' ");
			}
		}
		params.put("sort", "CAST(replace(replace(replace(system_code,'-',''),')',''),'(','') as decimal ),sort");
		return executeQueryLimit(sql.toString(), params);
	}
	
	
	public Record queryCommentType(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		//sql.append(" select substring(comment_name,0,charindex('规则',comment_name)) comment_type_name from dict_sys_comment(nolock) where");
		//sql.append(" select comment_name comment_type_name from dict_sys_comment(nolock) where");
		//sql.append(" system_code = (select comment_way from comment_sample where id = :sample_id)");
		sql.append("select comment_way from comment_sample (nolock) where id = :sample_id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result queryPid(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME +" (nolock) where level <= :level ");
		sql.append(" and id != :id");
		return executeQuery(sql.toString(), params);
	}

	public int delete(Map<String, Object> params) throws Exception {
		Record record = new Record();
		StringBuffer sql = new StringBuffer();
		record.put("id", params.remove("id"));
		sql.append(" delete from  "+TABLE_NAME+"  where id=:id or parent_id=:id");
		execute(sql.toString(), record);
		return 1;
	}

	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,b.comment_jiancheng pjiancheng ");
		sql.append(" from " + TABLE_NAME + " (nolock) a LEFT JOIN " + TABLE_NAME + " (nolock) b ");
		sql.append(" on a.parent_id = b.id ");
		setParameter(params, "id"," where a.id = :id", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public Record getIs_sys(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select is_sys from " + TABLE_NAME + " (nolock) where 1=1");
		setParameter(params, "id"," and id = :id", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public Record queryLevel(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parent_id", params.get("parent_id"));
		StringBuffer sql = new StringBuffer();
		sql.append("select level from " + TABLE_NAME +" (nolock) where id = :parent_id");
		return executeQuerySingleRecord(sql.toString(), map);
		
	}

	public Result queryCommentWay(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select comment_name,system_code from ");
		sql.append(TABLE_NAME+" (nolock) where level = '2' and beactive = '是' ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record growSort(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select max(d2.comment_code) maxsort,max(d1.system_code) system_code from dict_sys_comment (nolock) d1 " );
		sql.append(" left join dict_sys_comment (nolock) d2 on d2.parent_id = d1.id " );
		if(CommonFun.isNe(params.get("parent_id"))) {
			sql.append(" where d1.id is null " );
		}else {
			sql.append(" where d1.id = :parent_id" );
		}
		return executeQuerySingleRecord(sql.toString(), params);
	}

}
