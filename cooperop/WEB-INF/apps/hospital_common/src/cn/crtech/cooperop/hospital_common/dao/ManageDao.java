package cn.crtech.cooperop.hospital_common.dao;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;

public class ManageDao extends BaseDao {
	
	
	/**
	 * 建表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int insert(Map<String,Object> params) throws Exception{
		StringBuffer sql = new StringBuffer();
		//表名
		String name = (String) params.get("name");
		/*//判断是否存在该表 ，存在drop表然后在create
		sql.append("if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].["+name+"]')"
				+ " and OBJECTPROPERTY(id, N'IsUserTable') = 1)");
		sql.append(" drop  table " + name);
		sql.append("     ELSE     ");*/
		//创建表结构
		sql.append(" create table  " + name);
		sql.append("    (     ");
		
		//必须包含id,data_service_quene_id,parent_id三个联合主键
		sql.append("  data_service_log_id  numeric(18, 0) not null,");
		sql.append("  id  numeric(18, 0)  not null,");
		sql.append("  parent_id  numeric(18, 0) default 0  not null  Primary Key (data_service_log_id, id, parent_id),");
		//表结构list
		List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("options");
		List<Map<String,Object>> fields = list;
		//遍历list拼接sql
		for(Map<String,Object> map : fields){
			// +++
			String colname = (String) map.get("colname");
			String alias_name = (String) map.get("alias_name");
			sql.append("  " + colname + " ");
			// +++
//			sql.append("  " + map.get("colname") + " ");
			if(!CommonFun.isNe(map.get("datatype"))){
				sql.append("  " + map.get("datatype") + "  ");
			}
			if(!CommonFun.isNe(map.get("nullable")) && ("true".equals(map.get("nullable")))){
				sql.append("  " + " not null" + " ");
			}
			sql.append(",");
			// +++
			if (!CommonFun.isNe(name) && !CommonFun.isNe(colname) && !CommonFun.isNe(alias_name)) {
				Record r_alias = new Record();
				r_alias.put("table_name", name);
				r_alias.put("field_name", colname);
				r_alias.put("alias_name", alias_name);
				executeInsert("dbo.data_table_alias", r_alias);
			}
			// +++
		}
		sql.append("    );");
		log.debug("*************************"+sql.toString()+"*************************");
		//执行sql
		String s = execute(sql.toString(), null);
		return Integer.parseInt(s == null ? "1":s);
	}
	
	/**
	 * drop表结构
	 */
	public int dropTable(Map<String,Object> params) throws Exception{
		return new WebtabstructDao().dropTable(params);
	}
	/**
	 * 查询表结构
	 */
	public Result query(Map<String,Object> params) throws Exception{
		return getMetaData((String)params.get("tablename"));
	}
	
	// +++
	public String getAlias(String table_name, String field_name) throws Exception {
		return new WebtabstructDao().getAlias(table_name, field_name);
	}
	
	public int modify(Map<String,Object> params) throws Exception {
		String table_name = (String) params.get("name");
		int rtnVal = 0; // 
		
		// 获取原表结构
		Result topFields = getMetaData(table_name);
		// 动作
		StringBuffer addField = new StringBuffer();
		addField.append("ALTER TABLE " + table_name + " ADD :field_name :datatype");
		StringBuffer modifyField = new StringBuffer();
		modifyField.append("ALTER TABLE " + table_name + " ALTER COLUMN :field_name :datatype");
		StringBuffer deleteField = new StringBuffer();
		deleteField.append("ALTER TABLE " + table_name + " DROP COLUMN :field_name");
		
		// 获取最新的字段信息
		List<Map<String,Object>> fields = (List<Map<String, Object>>) params.get("options");
		
		// 字段别名表操作条件
		Record r_alias = new Record();
		r_alias.put("table_name", table_name);
		
		// 增加、修改操作
		for(Map<String,Object> map : fields) {
			String colname = (String) map.get("colname");
			String datatype = (String) map.get("datatype");
			String alias_name = (String) map.get("alias_name");
			
			boolean addMark = true;
			boolean modifyMark = true;
			for (Map<String, Object> mf : topFields.getResultset()) {
				if (mf.get("name").equals(colname)) {
					addMark = false;
					// 这里根据实际表中的类型和当前的类型是否一致，不一致允许改变
					String topDatatype = "";
					if ("numeric".equalsIgnoreCase((String) mf.get("type_name"))) {
						topDatatype = "numeric(" + mf.get("precision") + "," + mf.get("scale") + ")";
					} else {
						topDatatype = "varchar(" + mf.get("precision") + ")";
					}
					if (topDatatype.equalsIgnoreCase(datatype)) {
						modifyMark = false;
					}
					break;
				}
			}
			
			// 当前表操作
			if (addMark) {
				String sql = addField.toString();
				sql = sql.replaceAll(":field_name", colname).replaceAll(":datatype", datatype);
				rtnVal += executeUpdate(sql, null);
			} else if (modifyMark) {
				String sql = modifyField.toString();
				sql = sql.replaceAll(":field_name", colname).replaceAll(":datatype", datatype);
				rtnVal += executeUpdate(sql, null);
			} 

			// 字段别名先删除后增加
			r_alias.put("field_name", colname);
			executeDelete("dbo.data_table_alias", r_alias);
			if (!CommonFun.isNe(table_name) && !CommonFun.isNe(colname) && !CommonFun.isNe(alias_name)) {
				r_alias.put("alias_name", alias_name);
				executeInsert("dbo.data_table_alias", r_alias);
				r_alias.remove("alias_name");
			}
		}
		// 删除操作
		boolean deleteMark = true;
		for (Map<String, Object> mfIn : topFields.getResultset()) {
			if ("data_service_log_id".equals(mfIn.get("name")) || 
					"parent_id".equals(mfIn.get("name")) ||
					"id".equals(mfIn.get("name")))
				continue;
			for(Map<String,Object> mapIn : fields) {
				if (mfIn.get("name").equals(mapIn.get("colname"))) {
						deleteMark = false;
						break;
				}
			}
			if (deleteMark) {
				String sql = deleteField.toString();
				sql = sql.replaceAll(":field_name", mfIn.get("name").toString());
				rtnVal += executeUpdate(sql, null);
				//
				r_alias.put("field_name", mfIn.get("name").toString());
				executeDelete("dbo.data_table_alias", r_alias);
			} else {
				deleteMark = true;
			}
		}
		
		return rtnVal;
	}
	// +++
}
