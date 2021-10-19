package cn.crtech.cooperop.bus.rm.core.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.rm.ResourceManager;
import cn.crtech.cooperop.bus.rm.core.dao.ResourceIndexDao;
import cn.crtech.cooperop.bus.util.GlobalVar;

public class ResourceIndexService extends BaseService {

	@Override
	public void connect() throws Exception {
		connect(GlobalVar.getSystemProperty("rm.datasource"));
	}

	public int storeResource(String module, Map<String, Object> params) throws Exception {
		try {
			connect();
			start();
			ResourceIndexDao dao = new ResourceIndexDao();

			Map<String, Object> record = dao.queryStoreTable(module);
			if (record == null) {
				log.error("store table not found. " + module);
				return 0;
			}
			int rows = dao.storeResource((String) record.get("table_name"), params);
			commit();
			return rows;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int commit(String module, String[] fileids) throws Exception {
		int i = 0;
		Map<String, Object> params;
		try {
			connect();

			ResourceIndexDao dao = new ResourceIndexDao();

			List<String> deletefiles = new ArrayList<String>();
			start();

			for (String fileid : fileids) {
				params = new HashMap<String, Object>();
				params.put("fileid", fileid);

				Record record = dao.getResourceTable(module, fileid);
				if (record == null) {
					log.error("resource table not found. " + module + ":" + fileid);
					continue;
				}

				Record file = dao.getResource(record.getString("table_name"), fileid);

				if (file != null && file.getInt("is_temp") == ResourceManager.IS_TEMP) {
					if (file.getInt("temp_oper") == ResourceManager.TEMP_OPER_INSERT || file.getInt("temp_oper") == ResourceManager.TEMP_OPER_UPDATE) {
						i += dao.commitStore(record.getString("table_name"), params);
					} else if (file.getInt("temp_oper") == ResourceManager.TEMP_OPER_DELETE) {
						i += dao.commitDelete(record.getString("table_name"), params);
						deletefiles.add(file.getString("path") + "/" + fileid);
					}
				}

			}
			commit();

			for (String f : deletefiles) {
				File folder = new File(GlobalVar.getSystemProperty("rm.path"));
				File file = new File(folder, f);
				if (file.exists()) {
					file.delete();
				}
				file = new File(folder, f + ResourceManager.THUMB_EXT);
				if (file.exists()) {
					file.delete();
				}
			}

			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int rollback(String module, String[] fileids) throws Exception {
		int i = 0;
		Map<String, Object> params;
		try {
			connect();

			ResourceIndexDao dao = new ResourceIndexDao();

			List<String> deletefiles = new ArrayList<String>();
			start();

			for (String fileid : fileids) {
				params = new HashMap<String, Object>();
				params.put("fileid", fileid);

				Record record = dao.getResourceTable(module, fileid);
				if (record == null) {
					log.error("resource table not found. " + module + ":" + fileid);
					continue;
				}

				Record file = dao.getResource(record.getString("table_name"), fileid);

				if (file != null && file.getInt("is_temp") == ResourceManager.IS_TEMP) {
					if (file.getInt("temp_oper") == ResourceManager.TEMP_OPER_INSERT) {
						i += dao.deleteResource(record.getString("table_name"), fileid, true);
						deletefiles.add(file.getString("path") + "/" + fileid);
					} else if (file.getInt("temp_oper") == ResourceManager.TEMP_OPER_UPDATE
							|| file.getInt("temp_oper") == ResourceManager.TEMP_OPER_DELETE) {
						i += dao.commitStore(record.getString("table_name"), params);
					}
				}

			}
			commit();

			for (String f : deletefiles) {
				File folder = new File(GlobalVar.getSystemProperty("rm.path"));
				File file = new File(folder, f);
				if (file.exists()) {
					file.delete();
				}
				file = new File(folder, f + ResourceManager.THUMB_EXT);
				if (file.exists()) {
					file.delete();
				}
			}

			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int gc() throws Exception {
		int i = 0;
		Map<String, Object> params;
		try {
			connect();

			ResourceIndexDao dao = new ResourceIndexDao();

			Result tables = dao.listResourceTable();

			for (Record record : tables.getResultset()) {
				Result files = null;
				try {
					files = dao.getTempResource(record.getString("table_name"));
				} catch (Exception ex) {
					continue;
				}

				for (Record file : files.getResultset()) {
					if (file.getInt("is_temp") == ResourceManager.IS_TEMP) {
						try {
							start();
							if (file.getInt("temp_oper") == ResourceManager.TEMP_OPER_INSERT) {
								dao.deleteResource(record.getString("table_name"), file.getString("file_id"), true);
								File folder = new File(GlobalVar.getSystemProperty("rm.path"));
								File f = new File(folder, file.getString("path") + "/" + file.getString("file_id"));
								if (f.exists()) {
									f.delete();
								}
								f = new File(folder, file.getString("path") + "/" + file.getString("file_id") + ResourceManager.THUMB_EXT);
								if (f.exists()) {
									f.delete();
								}
							} else if (file.getInt("temp_oper") == ResourceManager.TEMP_OPER_UPDATE
									|| file.getInt("temp_oper") == ResourceManager.TEMP_OPER_DELETE) {
								params = new HashMap<String, Object>();
								params.put("fileid", file.getString("file_id"));
								dao.commitStore(record.getString("table_name"), params);
							}
							commit();
							i++;
						} catch (Exception ex) {
							rollback();
							log.error("gc resouce error. ", ex);
						}
					}
				}
			}

			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Record getResource(String module, String fileid) throws Exception {
		try {
			connect();

			ResourceIndexDao dao = new ResourceIndexDao();

			Record record = dao.getResourceTable(module, fileid);
			if (record == null) {
				log.error("resource table not found. " + module + ":" + fileid);
				return null;
			}

			return dao.getResource((String) record.get("table_name"), fileid);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public List<Record> getResource(String module, String[] fileids) throws Exception {
		List<Record> lst = new ArrayList<Record>();

		try {
			connect();
			ResourceIndexDao dao = new ResourceIndexDao();

			for (int i = 0; i < fileids.length; i++) {
				Record record = dao.getResourceTable(module, fileids[i]);
				if (record == null) {
					log.error("resource table not found. " + module + ":" + fileids[i]);
					continue;
				}

				Record res = dao.getResource((String) record.get("table_name"), fileids[i]);
				if (res != null) {
					lst.add(res);
				}
			}

			return lst;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int deleteResource(String module, String[] fileids) throws Exception {
		int rtn = 0;
		try {
			connect();

			ResourceIndexDao dao = new ResourceIndexDao();

			List<String> deletefiles = new ArrayList<String>();
			start();
			for (int i = 0; i < fileids.length; i++) {
				Record record = dao.getResourceTable(module, fileids[i]);
				if (record == null) {
					log.error("resource table not found. " + module + ":" + fileids[i]);
					continue;
				}

				Record f = dao.getResource((String) record.get("table_name"), fileids[i]);
				if (f != null) {
					boolean deleteflag = (f.getInt("is_temp") == ResourceManager.IS_TEMP && f.getInt("temp_oper") == ResourceManager.TEMP_OPER_INSERT);
					rtn += dao.deleteResource((String) record.get("table_name"), fileids[i], deleteflag);
					if (deleteflag) {
						deletefiles.add((String) f.get("path") + "/" + fileids[i]);
					}
				}
			}

			commit();

			for (String f : deletefiles) {
				File folder = new File(GlobalVar.getSystemProperty("rm.path"));
				File file = new File(folder, f);
				if (file.exists()) {
					file.delete();
				}
				file = new File(folder, f + ResourceManager.THUMB_EXT);
				if (file.exists()) {
					file.delete();
				}
			}

			return rtn;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	/**
	 * @param module
	 * @param system_user_id
	 * @param fileid
	 * @param description
	 * @return
	 * @throws Exception
	 */
	public int updateDescription(String module, String system_user_id, String fileid, String description) throws Exception {
		try {
			connect();

			ResourceIndexDao dao = new ResourceIndexDao();

			Record record = dao.getResourceTable(module, fileid);
			if (record == null) {
				log.error("resource table not found. " + module + ":" + fileid);
				return 0;
			}

			return dao.updateDescription(record.getString("table_name"), system_user_id, fileid, description);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	/**
	 * @param module
	 * @param system_user_id
	 * @param fileid
	 * @param description
	 * @return
	 * @throws Exception
	 */
	public int updateName(String module, String system_user_id, String fileid, String file_name) throws Exception {
		try {
			connect();

			ResourceIndexDao dao = new ResourceIndexDao();

			Record record = dao.getResourceTable(module, fileid);
			if (record == null) {
				log.error("resource table not found. " + module + ":" + fileid);
				return 0;
			}

			return dao.updateName(record.getString("table_name"), system_user_id, fileid, file_name);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	/**
	 * 通过资源fileid数组查询资源信息   .
	 * @param module 模块名
	 * @param module 模块，如：assets、shareequ
	 * @return 结果集
	 * <dt><b>修改历史：</b></dt>
	 * @throws Exception  数据库操作异常
	 */
	public Result getResources(String module, String[] fileidArray) throws Exception {
		try {
			connect();
			ResourceIndexDao dao = new ResourceIndexDao();
			Record record = dao.getResourceTable(module, fileidArray[0]);
			if (record == null) {
				log.error("resource table not found. " + module + ":" + fileidArray[0]);
				return null;
			}
			return dao.getResources((String) record.get("table_name"), fileidArray);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}


	public Record getResourceTable(String module) throws Exception {
		try {
			connect();
			ResourceIndexDao dao = new ResourceIndexDao();
			return dao.getResourceTable(module, null);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Result listResourceTable() throws Exception {
		try {
			connect();
			ResourceIndexDao dao = new ResourceIndexDao();
			return dao.listResourceTable();
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Result getImageResource(String resource_table) throws Exception {
		try {
			connect();
			ResourceIndexDao dao = new ResourceIndexDao();
			return dao.getImageResource(resource_table);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

}
