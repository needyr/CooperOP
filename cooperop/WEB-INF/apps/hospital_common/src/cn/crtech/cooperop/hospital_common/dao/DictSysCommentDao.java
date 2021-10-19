package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DictSysCommentDao extends BaseDao{

	/** 专项点评规则 */
	private final static String TABLE_SYS_COMMENT = "dict_sys_comment";
	
	/**
	 * @author: 魏圣峰
	 * @description: 获取点评规则
	 * @param: params Map  
	 * @return: Result      
	 * @throws: Exception
	 */
	public Result search(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select system_code,comment_name,system_product_code");
		sql.append(" from "+TABLE_SYS_COMMENT+"   (nolock)    where 1=1");
		if(!CommonFun.isNe(params.get("data"))){
			params.put("key", "%"+params.get("data")+"%");
			sql.append(" and (comment_name like :key or system_code = :data)");
		}
		sql.append(" and beactive='是'");
		sql.append(" and level='2'");
		sql.append(" order by system_code");
		return executeQuery(sql.toString(), params);
	}
	
	public Result searchCheck(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select system_code,comment_name,system_product_code");
		sql.append(" from "+TABLE_SYS_COMMENT+" (nolock) where 1=1");
		sql.append(" and system_code in(:code) ");
		sql.append(" and beactive='是'");
		sql.append(" and level='2'");
		sql.append(" order by system_code");
		return executeQuery(sql.toString(), params);
	}

	public Result queryCommentByWay(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.* from                             ");
		sql.append("dict_sys_comment (nolock) a                 ");
		sql.append("where EXISTS(                               ");
		sql.append("select 1 from dict_sys_comment where        ");
		sql.append("level=2 and comment_name like               ");
		sql.append("'%'+'"+params.get("comment_way")+"'+'%'     ");
		sql.append("and a.level = 4 and beactive = '是'         ");
		sql.append("and charindex(system_code,a.system_code)>0) ");
		sql.append("and a.beactive = '是' ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result queryComment_special(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.* from ");
		sql.append("dict_sys_comment (nolock) a ");
		sql.append("where EXISTS( ");
		sql.append("select 1 from dict_sys_comment where ");
		sql.append("level=2 "); 
		sql.append("and charindex(system_code+',','"+params.get("comment_way")+"'+',')>0 ");
		sql.append("and a.level in (3,4) and beactive = '是' ");
		sql.append("and charindex(system_code,a.system_code)>0) ");
		sql.append("and a.beactive = '是' ");
		return executeQuery(sql.toString(), params);
	}
	
	
	public Result queryAllByWay(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.* from                             ");
		sql.append("dict_sys_comment (nolock) a                 ");
		sql.append("where EXISTS(                               ");
		sql.append("select 1 from dict_sys_comment where        ");
		sql.append("level=2 and comment_name like               ");
		sql.append("'%'+'"+params.get("comment_way")+"'+'%'     ");
		sql.append("and a.level in (3,4) and beactive = '是'    ");
		sql.append("and charindex(system_code,a.system_code)>0) ");
		sql.append("and a.beactive = '是' ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryAll(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.* from                             ");
		sql.append("dict_sys_comment (nolock) a                 ");
		sql.append("where (a.level='4' or a.level='3' or a.level='2') ");
		sql.append("and a.beactive = '是' ");
		return executeQuery(sql.toString(), params);
	}
}
