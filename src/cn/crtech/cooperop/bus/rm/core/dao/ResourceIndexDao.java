package cn.crtech.cooperop.bus.rm.core.dao;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.rm.ResourceManager;
import cn.crtech.cooperop.bus.util.GlobalVar;

public class ResourceIndexDao extends BaseDao {
	public Record getResourceTable(String module, String fileid) throws Exception {
		String sql = "select table_name from document_mapping(nolock) where " + "system_product_code = :module and is_inuse = 1 ";
//				+ "and :fileid between begin_file_id and end_file_id";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("module", module);
		parameters.put("fileid", fileid);
		return executeQuerySingleRecord(sql, parameters);
	}

	public Result listResourceTable() throws Exception {
		String sql = "select distinct table_name from document_mapping(nolock) where is_inuse = 1 ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		return executeQuery(sql, parameters);
	}

	public Record queryStoreTable(String module) throws Exception {
		String sql = "select table_name from document_mapping(nolock) where " 
				+ "system_product_code = :module and is_inuse = 1 " + "and is_current = 1";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("module", module);
		return executeQuerySingleRecord(sql, parameters);
	}

	public int storeResource(String table, Map<String, Object> params) throws Exception {
		String sql = "insert into " + table + " (file_id, version_no, system_product_code, mime_type, file_name, "
				+ "file_size, create_time, create_user, last_modify_time, last_modify_user, description, "
				+ "is_encrypt, is_compress, rm_id, path, is_temp, temp_oper) values (:fileid, "
				+ "1, :module, :mimetype, :filename, :filesize, sysdate, :userid, " + "sysdate, :userid, :description, :encrypt, :compress, "
				+ "'', :path, :istemp, :tempoper)";
		params.put("istemp", ResourceManager.IS_TEMP);
		params.put("tempoper", ResourceManager.TEMP_OPER_INSERT);
		return executeUpdate(sql, params);
	}

	public int commitStore(String table, Map<String, Object> params) throws Exception {
		String sql = "update " + table + " set is_temp = :istemp, temp_oper = :tempoper where file_id = :fileid";
		params.put("istemp", ResourceManager.NOT_TEMP);
		params.put("tempoper", ResourceManager.TEMP_OPER_NORMAL);
		return executeUpdate(sql, params);
	}

	public int commitDelete(String table, Map<String, Object> params) throws Exception {
		String sql = "delete from " + table + " where file_id = :fileid ";
		return executeUpdate(sql, params);
	}

	public Record getResource(String table, String fileid) throws Exception {
		String sql = "select file_id, version_no, system_product_code, mime_type, file_name, "
				+ "file_size, create_time, create_user, last_modify_time, last_modify_user, description, "
				+ "is_encrypt, is_compress, rm_id, path, is_temp, temp_oper from " + table + "(nolock) where file_id = :fileid  ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("fileid", fileid);
		return executeQuerySingleRecord(sql, parameters);
	}

	public int deleteResource(String table, String fileid, boolean deleteflag) throws Exception {
		String sql = "delete from " + table + " where file_id = :fileid  ";
		if (deleteflag) {
			sql = "delete from " + table + " where file_id = :fileid  ";
		} else {
			sql = "update " + table + " set is_temp = " + ResourceManager.IS_TEMP + ", temp_oper = " + ResourceManager.TEMP_OPER_DELETE
					+ " where file_id = :fileid  ";
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("fileid", fileid);
		return executeUpdate(sql, parameters);
	}

	/**
	 * @param string
	 * @param system_user_id
	 * @param fileid
	 * @param description
	 * @return
	 * @throws Exception
	 */
	public int updateDescription(String table, String system_user_id, String fileid, String description) throws Exception {
		String sql = "update " + table
				+ " set description = :description, last_modify_user = :system_user_id, last_modify_time = sysdate where file_id = :fileid";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("description", description);
		parameters.put("system_user_id", system_user_id);
		parameters.put("fileid", fileid);
		return executeUpdate(sql, parameters);
	}
	/**
	 * @param string
	 * @param system_user_id
	 * @param fileid
	 * @param description
	 * @return
	 * @throws Exception
	 */
	public int updateName(String table, String system_user_id, String fileid, String file_name) throws Exception {
		String sql = "update " + table
				+ " set file_name = :file_name, last_modify_user = :system_user_id, last_modify_time = sysdate where file_id = :fileid";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("file_name", file_name);
		parameters.put("system_user_id", system_user_id);
		parameters.put("fileid", fileid);
		return executeUpdate(sql, parameters);
	}
	/**
	 * @param table
	 * @return
	 * @throws Exception
	 */
	public Result getTempResource(String table) throws Exception {
		String sql = "select file_id, version_no, system_product_code, mime_type, file_name, "
				+ "file_size, create_time, create_user, last_modify_time, last_modify_user, description, "
				+ "is_encrypt, is_compress, rm_id, path, is_temp, temp_oper from " + table
				+ "(nolock) where is_temp = :istemp and last_modify_time < sysdate - :gcday";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("istemp", ResourceManager.IS_TEMP);
		parameters.put("gcday", GlobalVar.getSystemProperty("rm.gc.time", "1"));
		return executeQuery(sql, parameters);
	}
	
	/**
	 * @param table
	 * @return
	 * @throws Exception
	 */
	public Result getImageResource(String table) throws Exception {
		String sql = "select file_id, mime_type, file_name, path from " + table
				+ "(nolock) where mime_type in (:mime_type)";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("mime_type", GlobalVar.getSystemProperty("rm.image.mimetype").split(","));
		return executeQuery(sql, parameters);
	}

	/**
	 * 通过资源fileid数组查询资源信息   .
	 * @param table 表名
	 * @param fileidArray file_id数组
	 * @return 结果集
	 * <dt><b>修改历史：</b></dt>
	 * @throws Exception  数据库操作异常
	 */
	public Result getResources(String table, String[] fileidArray) throws Exception {
		String sql = "select file_id, version_no, system_product_code, mime_type, file_name, "
				+ "file_size, create_time, create_user, last_modify_time, last_modify_user, description, "
				+ "is_encrypt, is_compress, rm_id, path, is_temp, temp_oper from " + table + "(nolock) where file_id in (:fileidArray)";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("fileidArray", fileidArray);
		return executeQuery(sql, parameters);
	}

}
