package cn.crtech.cooperop.crdc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DdictionaryDao extends BaseDao {
	private final static String TABLE_NAME = "[dbo].[table_explain]";
	private final static String TABLE_sub1_NAME = "[dbo].[table_columns_explain]";
	
	public Result query(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("query_databasename"))){
			if("全部".equals(params.get("query_databasename").toString())){
				params.remove("query_databasename");
			}
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select te.* ");
		if(!CommonFun.isNe(params.get("query_databasename"))){ 
			String databasename=params.get("query_databasename").toString();
			sql.append(" from (Select '"+databasename+"' as databasename,t1.table_name,isnull(t3.rows,0) as rows ");
			sql.append(" 			,case when t4.table_name is null then '未知' else t4.table_type end as table_type ");
			sql.append("			,case when t4.table_name is null then '未知' else t4.type_explain end as type_explain");
			sql.append("			,case when t4.table_name is null then 1 else t4.is_del end as is_del");
			sql.append(" 			from "+databasename+".information_schema.tables as t1 ");
			sql.append(" 			left join "+databasename+".dbo.sysobjects as t2 on t2.xtype='U' and t1.table_name=t2.name ");
			sql.append(" 			left join "+databasename+".dbo.sysindexes as t3 on t2.id=t3.id and t3.indid in (0,1) ");
			sql.append("			left join cooperop.dbo.table_explain as t4 on t1.table_name=t4.table_name and t4.databasename='"+databasename+"'");
			sql.append(" 			Where t1.table_type='BASE TABLE' and t1.table_name not in ('table_explain','table_columns_explain')) as te");
		}else{
			sql.append(" from "+TABLE_NAME+"(nolock) te");
		}
		sql.append(" where 1 = 1 ");
		setParameter(params,"query_databasename"," and te.databasename=:query_databasename ",sql);
		setParameter(params,"query_tablename"," and te.table_name=:query_tablename ",sql);
		setParameter(params,"query_tabletype"," and te.table_type like '%'+:query_tabletype+'%' ",sql);
		setParameter(params,"query_typeexplain"," and te.type_explain=:query_typeexplain ",sql);
		setParameter(params,"del_rows"," and isnull(rows,0)>=:del_rows ",sql);
		if(CommonFun.isNe(params.get("sort"))){
			params.put("sort", "databasename,table_name");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	public Result queryColumns(Map<String, Object> params) throws Exception {
		String databasename=params.get("databasename").toString();
		StringBuffer sql = new StringBuffer();
		sql.append(" Select t1.table_name,t3.name as 'columns',t3.colid ");
		sql.append(" ,t4.name+isnull('('+case t3.prec when -1 then 'max' else cast(t3.prec as varchar) end +isnull(','+cast(t3.scale as varchar),'')+')','') as type_explain ");
		sql.append(" ,tce.columns_type,tce.remark ");
		sql.append(" from "+databasename+".information_schema.tables as t1 ");
		sql.append(" left join "+databasename+".dbo.sysobjects as t2 on t2.xtype='U' and t1.table_name=t2.name ");
		sql.append(" left join "+databasename+".dbo.syscolumns as t3 on t2.id=t3.id ");
		sql.append(" left join "+databasename+".dbo.systypes as t4 on t3.xtype=t4.xtype and t4.name!='sysname' ");
		sql.append(" left join table_columns_explain as tce on tce.databasename=:databasename and t1.table_name=tce.table_name and t3.name=tce.columns ");
		sql.append(" Where t1.table_type='BASE TABLE' and t1.table_name=:tablename ");
		sql.append(" and t1.table_name not in ('table_explain','table_columns_explain') ");
		if(CommonFun.isNe(params.get("sort"))){
			params.put("sort", "colid");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result queryDistinct(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		if(!CommonFun.isNe(params.get("databasename"))){
			sql.append(" select distinct databasename from "+TABLE_NAME+"(nolock) where 1=1 ");
			setParameter(params, "filter", " and databasename like '%'+:filter+'%' ", sql);
		}
		if(!CommonFun.isNe(params.get("tablename"))){
			sql.append(" select distinct table_name from "+TABLE_NAME+"(nolock) where 1=1 ");
			setParameter(params, "query_databasename", " and databasename like '%'+:query_databasename+'%' ", sql);
			setParameter(params, "filter", " and table_name like '%'+:filter+'%' ", sql);
		}
		if(!CommonFun.isNe(params.get("tabletype"))){
			sql.append(" select distinct table_type from "+TABLE_NAME+"(nolock) where 1=1 ");
			setParameter(params, "query_databasename", " and databasename like '%'+:query_databasename+'%' ", sql);
			setParameter(params, "filter", " and table_type like '%'+:filter+'%' ", sql);
		}
		if(!CommonFun.isNe(params.get("typeexplain"))){
			sql.append(" select * from (select distinct type_explain from "+TABLE_NAME+"(nolock) te where 1=1 ");
			setParameter(params, "query_databasename", " and te.databasename like '%'+:query_databasename+'%' ", sql);
			sql.append(" union all select '未知') as t where 1=1 ");
			setParameter(params, "filter", " and type_explain like '%'+:filter+'%' ", sql);
		}
		return executeQueryLimit(sql.toString(), params);
	}

	public void synchroOnline(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" truncate table "+TABLE_NAME);
		sql.append(" truncate table "+TABLE_sub1_NAME);
		sql.append(" insert into "+TABLE_NAME);
		sql.append(" select * from opendatasource('sqloledb','server=125.64.92.41,9203;uid=sa;pwd=www.server03.com').common."+TABLE_NAME);
		sql.append(" insert into "+TABLE_sub1_NAME);
		sql.append(" select * from opendatasource('sqloledb','server=125.64.92.41,9203;uid=sa;pwd=www.server03.com').common."+TABLE_sub1_NAME);
		execute(sql.toString(),params);
	}

	public void deleteTableRows(Map<String, Object> params) throws Exception {
		String tablename=params.get("tablename").toString();
		StringBuffer sql = new StringBuffer();
		if("cooperop.dbo.wf_process".equals(tablename)){
			sql.append("delete from wf_process where name!='free'");
		}else if("cooperop.dbo.wf_task".equals(tablename) 
				|| "cooperop.dbo.wf_order".equals(tablename) 
				|| "cooperop.dbo.wf_task_actor".equals(tablename)){
			sql.append(" delete from cooperop.dbo.wf_task_actor delete from cooperop.dbo.wf_task delete from cooperop.dbo.wf_order");
		}else if("cooperop.dbo.wf_hist_task".equals(tablename) 
				|| "cooperop.dbo.wf_hist_task_actor".equals(tablename) 
				|| "cooperop.dbo.wf_hist_order".equals(tablename)){
			sql.append(" delete from cooperop.dbo.wf_hist_task_actor delete from cooperop.dbo.wf_hist_task delete from cooperop.dbo.wf_hist_order");
		}else{
			sql.append(" truncate table ["+tablename+"]");
		}
		execute(sql.toString(),params);
	}
}
