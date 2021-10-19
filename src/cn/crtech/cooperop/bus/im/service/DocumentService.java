package cn.crtech.cooperop.bus.im.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.im.dao.DocumentDao;
import cn.crtech.cooperop.bus.im.resource.ResourceManager;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.GlobalVar;

public class DocumentService extends IMBaseService {

	public int storeResource(Map<String, Object> params) throws Exception {
		try {
			connect();
			start();
			DocumentDao dao = new DocumentDao();
			int rows = dao.storeResource(params);
			commit();
			return rows;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int commit(String[] fileids) throws Exception {
		int i = 0;
		Map<String, Object> params;
		try {
			connect();

			DocumentDao dao = new DocumentDao();

			List<String> deletefiles = new ArrayList<String>();
			start();

			for (String fileid : fileids) {
				params = new HashMap<String, Object>();
				params.put("fileid", fileid);

				Record file = dao.getResource(fileid);

				if (file != null && file.getInt("is_temp") == ResourceManager.IS_TEMP) {
					if (file.getInt("temp_oper") == ResourceManager.TEMP_OPER_INSERT
							|| file.getInt("temp_oper") == ResourceManager.TEMP_OPER_UPDATE) {
						i += dao.commitStore(params);
					} else if (file.getInt("temp_oper") == ResourceManager.TEMP_OPER_DELETE) {
						i += dao.commitDelete(params);
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

	public int rollback(String[] fileids) throws Exception {
		int i = 0;
		Map<String, Object> params;
		try {
			connect();

			DocumentDao dao = new DocumentDao();

			List<String> deletefiles = new ArrayList<String>();
			start();

			for (String fileid : fileids) {
				params = new HashMap<String, Object>();
				params.put("fileid", fileid);

				Record file = dao.getResource(fileid);

				if (file != null && file.getInt("is_temp") == ResourceManager.IS_TEMP) {
					if (file.getInt("temp_oper") == ResourceManager.TEMP_OPER_INSERT) {
						i += dao.deleteResource(fileid, true);
						deletefiles.add(file.getString("path") + "/" + fileid);
					} else if (file.getInt("temp_oper") == ResourceManager.TEMP_OPER_UPDATE
							|| file.getInt("temp_oper") == ResourceManager.TEMP_OPER_DELETE) {
						i += dao.commitStore(params);
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

			DocumentDao dao = new DocumentDao();

			Result files = dao.getTempResource();

			for (Record file : files.getResultset()) {
				if (file.getInt("is_temp") == ResourceManager.IS_TEMP) {
					try {
						start();
						if (file.getInt("temp_oper") == ResourceManager.TEMP_OPER_INSERT) {
							dao.deleteResource(file.getString("file_id"), true);
							File folder = new File(GlobalVar.getSystemProperty("rm.path"));
							File f = new File(folder, file.getString("path") + "/" + file.getString("file_id"));
							if (f.exists()) {
								f.delete();
							}
							f = new File(folder, file.getString("path") + "/" + file.getString("file_id")
									+ ResourceManager.THUMB_EXT);
							if (f.exists()) {
								f.delete();
							}
						} else if (file.getInt("temp_oper") == ResourceManager.TEMP_OPER_UPDATE
								|| file.getInt("temp_oper") == ResourceManager.TEMP_OPER_DELETE) {
							params = new HashMap<String, Object>();
							params.put("fileid", file.getString("file_id"));
							dao.commitStore(params);
						}
						commit();
						i++;
					} catch (Exception ex) {
						rollback();
						log.error("gc resouce error. ", ex);
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

	public Record getResource(String fileid) throws Exception {
		try {
			connect();

			DocumentDao dao = new DocumentDao();

			return dao.getResource(fileid);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public List<Record> getResource(String[] fileids) throws Exception {
		List<Record> lst = new ArrayList<Record>();

		try {
			connect();
			DocumentDao dao = new DocumentDao();

			for (int i = 0; i < fileids.length; i++) {
				Record res = dao.getResource(fileids[i]);
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

	public int deleteResource(String[] fileids) throws Exception {
		int rtn = 0;
		try {
			connect();

			DocumentDao dao = new DocumentDao();

			List<String> deletefiles = new ArrayList<String>();
			start();
			for (int i = 0; i < fileids.length; i++) {
				Record f = dao.getResource(fileids[i]);
				if (f != null) {
					boolean deleteflag = (f.getInt("is_temp") == ResourceManager.IS_TEMP
							&& f.getInt("temp_oper") == ResourceManager.TEMP_OPER_INSERT);
					rtn += dao.deleteResource(fileids[i], deleteflag);
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
	 * 通过资源fileid数组查询资源信息 .
	 * 
	 * @param module
	 *            模块名
	 * @param module
	 *            模块，如：assets、shareequ
	 * @return 结果集
	 *         <dt><b>修改历史：</b></dt>
	 * @throws Exception
	 *             数据库操作异常
	 */
	public Result getResources(String[] fileidArray) throws Exception {
		try {
			connect();
			DocumentDao dao = new DocumentDao();
			return dao.getResources(fileidArray);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}


}
