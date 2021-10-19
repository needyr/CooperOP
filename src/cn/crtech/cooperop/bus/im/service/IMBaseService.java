package cn.crtech.cooperop.bus.im.service;

import java.io.File;
import java.util.List;

import cn.crtech.cooperop.bus.im.bean.Department;
import cn.crtech.cooperop.bus.im.bean.FileItem;
import cn.crtech.cooperop.bus.im.bean.Orgnization;
import cn.crtech.cooperop.bus.im.bean.User;
import cn.crtech.cooperop.bus.im.dao.DepartmentDao;
import cn.crtech.cooperop.bus.im.dao.OrgnizationDao;
import cn.crtech.cooperop.bus.im.dao.SystemUserDao;
import cn.crtech.cooperop.bus.im.resource.ResourceManager;
import cn.crtech.cooperop.bus.im.transfer.Engine;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;

public class IMBaseService extends BaseService {
	@Override
	public void connect() throws Exception {
		connect("im");
	}

	public int refreshOrgnization(List<Orgnization> orgs) throws Exception {
		int i = 0;
		try {
			connect();
			OrgnizationDao od = new OrgnizationDao();
			start();
			for (Orgnization org : orgs) {
				Record map = new Record(CommonFun.object2Map(org));
				if (od.get(org.getId()) != null) {
					i += od.update(map);
				} else {
					i += od.insert(map);
				}
			}
			commit();
			return i;
		} catch (Exception e) {
			rollback();
			throw e;
		} finally {
			disconnect();
		}
	}
	public void deleteOrgnization(Orgnization org) throws Exception {
		try {
			connect();
			OrgnizationDao od = new OrgnizationDao();
			od.delete(org.getId());
		} catch (Exception e) {
			rollback();
			throw e;
		} finally {
			disconnect();
		}
	}
	public int refreshDepartment(List<Department> deps) throws Exception {
		int i = 0;
		try {
			connect();
			DepartmentDao dd = new DepartmentDao();
			start();
			for (Department dep : deps) {
				Record map = new Record(CommonFun.object2Map(dep));
				if (dd.get(dep.getId()) != null) {
					i += dd.update(map);
				} else {
					i += dd.insert(map);
				}
			}
			commit();
			return i;
		} catch (Exception e) {
			rollback();
			throw e;
		} finally {
			disconnect();
		}
	}
	public void deleteDepartment(Department dep) throws Exception {
		try {
			connect();
			DepartmentDao dd = new DepartmentDao();
			dd.delete(dep.getId());
		} catch (Exception e) {
			rollback();
			throw e;
		} finally {
			disconnect();
		}
	}
	public int refreshUser(List<User> users) throws Exception {
		int i = 0;
		try {
			connect();
			SystemUserDao sud = new SystemUserDao();
			start();
			for (User user : users) {
				FileItem f = user.getAvatar();
				user.setAvatar(null);
				Record map = new Record(CommonFun.object2Map(user));
				map.remove("avatar");
				map.put("birthday", user.getBirthday());
				map.put("online_status", "offline");
				map.put("online_status_change_time", "sysdate");
				Record imu = sud.get(user.getId());
				if (f != null) {
					FileItem imf = null;
					if (imu != null && !CommonFun.isNe(imu.get("avatar"))) {
						Record rr = ResourceManager.getResource(imu.getString("avatar"));
						if (rr != null) {
							File file = ResourceManager.getFile(false, rr);
							if (file.exists()) {
								imf = new FileItem(file, rr.getString("name"), rr.getString("mime_type"));
							}
						}
					}
					if (!f.equals(imf)) {
						File destfile = new File(Engine.getProperty("rm.path"), map.getString("id"));
						CommonFun.copyFile(f.getFile(), destfile, true);
						map.put("avatar", ResourceManager.storeResource("chohoim",
								new FileItem(destfile, f.getName(), f.getContentType())));
					}
				} else {
					map.put("avatar", null);
				}
				if (imu != null) {
					i += sud.update(map);
				} else {
					i += sud.insert(map);
				}
			}
			commit();
			return i;
		} catch (Exception e) {
			rollback();
			throw e;
		} finally {
			disconnect();
		}
	}
	public void deleteUser(User user) throws Exception {
		try {
			connect();
			SystemUserDao sud = new SystemUserDao();
			sud.delete(user.getId());
		} catch (Exception e) {
			rollback();
			throw e;
		} finally {
			disconnect();
		}
	}
}
