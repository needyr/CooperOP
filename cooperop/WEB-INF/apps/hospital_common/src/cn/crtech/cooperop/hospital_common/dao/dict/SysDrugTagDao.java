package cn.crtech.cooperop.hospital_common.dao.dict;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class SysDrugTagDao extends BaseDao{
	
	/**药品标签字典*/
	private static final String TABLENAME = "DICT_SYS_DRUG_TAG";
	
	
	//初始化数据
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLENAME + "(nolock) ");
		sql.append(" where 1=1 ");
		if(!CommonFun.isNe(params.get("filter"))) {
			params.put("filter", "%" +params.get("filter")+ "%");
			sql.append(" and DRUGTAGNAME like :filter");
		}
		if(!CommonFun.isNe(params.get("ordertagid"))) {
			sql.append(" and  drugtagid = :drugtagid ");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result parenttag(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select *,(select top 1 DRUGTAGNAME from "+TABLENAME+" (nolock) where a.parent_id=DRUGTAGID) as parent_name ");
		sql.append(" from " + TABLENAME + "(nolock) a ");
		sql.append(" where 1=1 ");
		if(!CommonFun.isNe(params.get("filter"))) {
			params.put("filter", "%" +params.get("filter")+ "%");
			sql.append(" and (a.DRUGTAGNAME like :filter or a.drugtagbh like :filter)");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		//params.remove("drugtagid");
		return executeInsert(TABLENAME, params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("drugtagid", params.remove("drugtagid"));
		return executeUpdate(TABLENAME, params, r);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		/*sql.append("with cte_child(DRUGTAGNAME,DRUGTAGID,parent_id) ");
		sql.append("as(select DRUGTAGNAME,DRUGTAGID,parent_id     ");
	    sql.append("from DICT_SYS_DRUG_TAG                        ");
	    sql.append("where DRUGTAGID = :drugtagid                  ");
	    sql.append("union all                                     ");
	    sql.append("select a.DRUGTAGNAME,a.DRUGTAGID,a.parent_id  ");
	    sql.append("from DICT_SYS_DRUG_TAG a                      ");
	    sql.append("inner join                                    ");
	    sql.append("cte_child b                                   ");
	    sql.append("on ( a.parent_id=b.DRUGTAGID))                ");
        sql.append("                                              ");
		sql.append("select a.* from DICT_SYS_DRUG_TAG a             ");
		sql.append("where exists (select 1 from cte_child         ");
		sql.append("where a.DRUGTAGID=DRUGTAGID)                  ");*/
		sql.append("exec cr_delete_dict_sys_drug_tag @DRUGTAGID= '"+params.get("drugtagid")+"'");
		execute(sql.toString(), params);
		return 1;
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select *,(select top 1 DRUGTAGNAME from "+TABLENAME+" (nolock) where a.parent_id=DRUGTAGID) as parent_name from " + TABLENAME + "(nolock) a ");
		if(!CommonFun.isNe(params.get("drugtagid"))) {
			sql.append(" where  a.drugtagid = :drugtagid ");
		}else {
			sql.append(" where  a.drugtagbh = :drugtagbh ");
		}
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result queryTree(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		StringBuffer sql3 = new StringBuffer();
		
		if(CommonFun.isNe(params.get("filter")) && CommonFun.isNe(params.get("qx"))) {
			sql.append("select ");
			sql.append(" f.*, (                                                                        ");
			sql.append(" 	SELECT                                                                     ");
			sql.append(" 		COUNT (1)                                                              ");
			sql.append(" 	FROM                                                                       ");
			sql.append(" 		DICT_SYS_DRUG_TAG (nolock)                                             ");
			sql.append(" 	WHERE                                                                      ");
			sql.append(" 		parent_id = f.DRUGTAGID                                        ");
			sql.append(" ) AS childnums                                                                ");
			sql.append("from DICT_SYS_DRUG_TAG (nolock) f ");
			sql.append(" where 1=1 ");
			return executeQuery(sql.toString(), params);
		}else if(!CommonFun.isNe(params.get("qx")) && CommonFun.isNe(params.get("filter"))){
			sql3.append("with CTEGetChild as                             ");
			sql3.append("(                                               ");
			sql3.append("select * from DICT_SYS_DRUG_TAG                 ");
			sql3.append("where                             ");
			sql3.append(" isnull(is_permission,'0')='1' ");
			sql3.append("UNION ALL                                       ");
			sql3.append("SELECT a.* from DICT_SYS_DRUG_TAG               ");
			sql3.append("as a inner join                                 ");
			sql3.append("CTEGetChild as b on a.parent_id=b.DRUGTAGID     ");
			sql3.append(")                                               ");
			sql3.append("select a.*,                       ");
			sql3.append("(SELECT COUNT(1) FROM DICT_SYS_DRUG_TAG (nolock) "); 
			sql3.append("WHERE parent_id = a.DRUGTAGID ) AS childnums     ");
			sql3.append(" from CTEGetChild a; ");
			
			sql2.append("with CTEGetChild as                             ");
			sql2.append("(                                               ");
			sql2.append("select * from DICT_SYS_DRUG_TAG                 ");
			sql2.append("where                             ");
			sql2.append(" isnull(is_permission,'0')='1' ");
			sql2.append("UNION ALL                                       ");
			sql2.append("SELECT a.* from DICT_SYS_DRUG_TAG               ");
			sql2.append("as a inner join                                 ");
			sql2.append("CTEGetChild as b on a.DRUGTAGID=b.parent_id     ");
			sql2.append(")                                               ");
			sql2.append("select a.*,                       ");
			sql2.append("(SELECT COUNT(1) FROM DICT_SYS_DRUG_TAG (nolock) "); 
			sql2.append("WHERE parent_id = a.DRUGTAGID ) AS childnums     ");
			sql2.append(" from CTEGetChild a; ");
			Result executeQuery2 = executeQuery(sql2.toString(), params);
			List<Record> resultset3 = executeQuery(sql3.toString(), params).getResultset();
			List<Record> resultset2 = executeQuery2.getResultset();
			List<Record> re = new ArrayList<Record>();
			for (Record record : resultset3) {
				if(check_cf(re,record,"drugtagid")) {
					re.add(record);
				}
			}
			for (Record record : resultset2) {
				if(check_cf(re,record,"drugtagid")) {
					re.add(record);
				}
			}
			Result que = new Result();
			que.setResultset(re);
			return que;
		}else if (!CommonFun.isNe(params.get("qx")) && !CommonFun.isNe(params.get("filter"))) {
			params.put("filter", "%" +params.get("filter")+ "%");
			sql3.append("with CTEGetChild as                             ");
			sql3.append("(                                               ");
			sql3.append("select * from DICT_SYS_DRUG_TAG                 ");
			sql3.append("where                             ");
			sql3.append(" (DRUGTAGNAME like :filter or DRUGTAGBH like :filter)");
			sql3.append(" and isnull(is_permission,'0')='1' ");
			sql3.append("UNION ALL                                       ");
			sql3.append("SELECT a.* from DICT_SYS_DRUG_TAG               ");
			sql3.append("as a inner join                                 ");
			sql3.append("CTEGetChild as b on a.parent_id=b.DRUGTAGID     ");
			sql3.append(")                                               ");
			sql3.append("select a.*,                       ");
			sql3.append("(SELECT COUNT(1) FROM DICT_SYS_DRUG_TAG (nolock) "); 
			sql3.append("WHERE parent_id = a.DRUGTAGID ) AS childnums     ");
			sql3.append(" from CTEGetChild a; ");
			
			sql2.append("with CTEGetChild as                             ");
			sql2.append("(                                               ");
			sql2.append("select * from DICT_SYS_DRUG_TAG                 ");
			sql2.append("where                             ");
			sql2.append(" (DRUGTAGNAME like :filter or DRUGTAGBH like :filter)");
			sql2.append(" and isnull(is_permission,'0')='1' ");
			sql2.append("UNION ALL                                       ");
			sql2.append("SELECT a.* from DICT_SYS_DRUG_TAG               ");
			sql2.append("as a inner join                                 ");
			sql2.append("CTEGetChild as b on a.DRUGTAGID=b.parent_id     ");
			sql2.append(")                                               ");
			sql2.append("select a.*,                       ");
			sql2.append("(SELECT COUNT(1) FROM DICT_SYS_DRUG_TAG (nolock) "); 
			sql2.append("WHERE parent_id = a.DRUGTAGID ) AS childnums     ");
			sql2.append(" from CTEGetChild a; ");
			Result executeQuery2 = executeQuery(sql2.toString(), params);
			List<Record> resultset3 = executeQuery(sql3.toString(), params).getResultset();
			List<Record> resultset2 = executeQuery2.getResultset();
			List<Record> re = new ArrayList<Record>();
			for (Record record : resultset3) {
				if(check_cf(re,record,"drugtagid")) {
					re.add(record);
				}
			}
			for (Record record : resultset2) {
				if(check_cf(re,record,"drugtagid")) {
					re.add(record);
				}
			}
			Result que = new Result();
			que.setResultset(re);
			return que;
		}else {
			params.put("filter", "%" +params.get("filter")+ "%");
			sql3.append("with CTEGetChild as                             ");
			sql3.append("(                                               ");
			sql3.append("select * from DICT_SYS_DRUG_TAG                 ");
			sql3.append("where                             ");
			sql3.append(" (DRUGTAGNAME like :filter or DRUGTAGBH like :filter)");
			sql3.append("UNION ALL                                       ");
			sql3.append("SELECT a.* from DICT_SYS_DRUG_TAG               ");
			sql3.append("as a inner join                                 ");
			sql3.append("CTEGetChild as b on a.parent_id=b.DRUGTAGID     ");
			sql3.append(")                                               ");
			sql3.append("select a.*,                       ");
			sql3.append("(SELECT COUNT(1) FROM DICT_SYS_DRUG_TAG (nolock) "); 
			sql3.append("WHERE parent_id = a.DRUGTAGID ) AS childnums     ");
			sql3.append(" from CTEGetChild a; ");
			
			sql2.append("with CTEGetChild as                             ");
			sql2.append("(                                               ");
			sql2.append("select * from DICT_SYS_DRUG_TAG                 ");
			sql2.append("where                             ");
			sql2.append(" (DRUGTAGNAME like :filter or DRUGTAGBH like :filter)");
			sql2.append("UNION ALL                                       ");
			sql2.append("SELECT a.* from DICT_SYS_DRUG_TAG               ");
			sql2.append("as a inner join                                 ");
			sql2.append("CTEGetChild as b on a.DRUGTAGID=b.parent_id     ");
			sql2.append(")                                               ");
			sql2.append("select a.*,                       ");
			sql2.append("(SELECT COUNT(1) FROM DICT_SYS_DRUG_TAG (nolock) "); 
			sql2.append("WHERE parent_id = a.DRUGTAGID ) AS childnums     ");
			sql2.append(" from CTEGetChild a; ");
			Result executeQuery2 = executeQuery(sql2.toString(), params);
			List<Record> resultset3 = executeQuery(sql3.toString(), params).getResultset();
			List<Record> resultset2 = executeQuery2.getResultset();
			List<Record> re = new ArrayList<Record>();
			for (Record record : resultset3) {
				if(check_cf(re,record,"drugtagid")) {
					re.add(record);
				}
			}
			for (Record record : resultset2) {
				if(check_cf(re,record,"drugtagid")) {
					re.add(record);
				}
			}
			Result que = new Result();
			que.setResultset(re);
			return que;
		}
	}
	
	public boolean check_cf(List<Record> list, Record re, String id) {
		for (Record record : list) {
			if(record.get(id).equals(re.get(id))) {
				return false;
			}
		}
		return true;
	}
}
