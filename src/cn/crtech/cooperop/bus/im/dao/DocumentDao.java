package cn.crtech.cooperop.bus.im.dao;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.im.resource.ResourceManager;
import cn.crtech.cooperop.bus.im.transfer.Engine;
import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class DocumentDao extends BaseDao {
	public final static String TABLE_NAME = "documents";
	public int storeResource(Map<String, Object> params) throws Exception {
		String sql = "insert into " + TABLE_NAME + " (file_id, version_no, mime_type, file_name, "
				+ "file_size, create_time, create_user, last_modify_time, last_modify_user, description, "
				+ "is_encrypt, is_compress, rm_id, path, is_temp, temp_oper) values (:fileid, "
				+ "1, :mimetype, :filename, :filesize, sysdate, :userid, " + "sysdate, :userid, :description, :encrypt, :compress, "
				+ "'', :path, :istemp, :tempoper)";
		params.put("istemp", ResourceManager.IS_TEMP);
		params.put("tempoper", ResourceManager.TEMP_OPER_INSERT);
		return executeUpdate(sql, params);
	}

	public int commitStore(Map<String, Object> params) throws Exception {
		String sql = "update " + TABLE_NAME + " set is_temp = :istemp, temp_oper = :tempoper where file_id = :fileid";
		params.put("istemp", ResourceManager.NOT_TEMP);
		params.put("tempoper", ResourceManager.TEMP_OPER_NORMAL);
		return executeUpdate(sql, params);
	}

	public int commitDelete(Map<String, Object> params) throws Exception {
		String sql = "delete from " + TABLE_NAME + " where file_id = :fileid ";
		return executeUpdate(sql, params);
	}

	public Record getResource(String fileid) throws Exception {
		String sql = "select file_id, version_no, mime_type, file_name, "
				+ "file_size, create_time, create_user, last_modify_time, last_modify_user, description, "
				+ "is_encrypt, is_compress, rm_id, path, is_temp, temp_oper from " + TABLE_NAME + "(nolock) where file_id = :fileid  ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("fileid", fileid);
		return executeQuerySingleRecord(sql, parameters);
	}

	public int deleteResource(String fileid, boolean deleteflag) throws Exception {
		String sql = "delete from " + TABLE_NAME + " where file_id = :fileid  ";
		if (deleteflag) {
			sql = "delete from " + TABLE_NAME + " where file_id = :fileid  ";
		} else {
			sql = "update " + TABLE_NAME + " set is_temp = " + ResourceManager.IS_TEMP + ", temp_oper = " + ResourceManager.TEMP_OPER_DELETE
					+ " where file_id = :fileid  ";
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("fileid", fileid);
		return executeUpdate(sql, parameters);
	}

	/**
	 * 通过资源fileid数组查询资源信息   .
	 * @param table 表名
	 * @param fileidArray file_id数组
	 * @return 结果集
	 * <dt><b>修改历史：</b></dt>
	 * @throws Exception  数据库操作异常
	 */
	public Result getResources(String[] fileidArray) throws Exception {
		String sql = "select file_id, version_no, mime_type, file_name, "
				+ "file_size, create_time, create_user, last_modify_time, last_modify_user, description, "
				+ "is_encrypt, is_compress, rm_id, path, is_temp, temp_oper from " + TABLE_NAME + "(nolock) where file_id in (:fileidArray)";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("fileidArray", fileidArray);
		return executeQuery(sql, parameters);
	}

	/**
	 * @param table
	 * @return
	 * @throws Exception
	 */
	public Result getTempResource() throws Exception {
		String sql = "select file_id, version_no, mime_type, file_name, "
				+ "file_size, create_time, create_user, last_modify_time, last_modify_user, description, "
				+ "is_encrypt, is_compress, rm_id, path, is_temp, temp_oper from " + TABLE_NAME
				+ "(nolock) where is_temp = :istemp and last_modify_time < sysdate - :gcday";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("istemp", ResourceManager.IS_TEMP);
		parameters.put("gcday", Engine.getProperty("rm.gc.time"));
		return executeQuery(sql, parameters);
	}
}
