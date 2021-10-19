package cn.crtech.cooperop.bus.im.test.service;

import java.io.File;
import java.util.Map;

import cn.crtech.cooperop.bus.im.bean.FileItem;
import cn.crtech.cooperop.bus.im.bean.Group;
import cn.crtech.cooperop.bus.im.bean.User;
import cn.crtech.cooperop.bus.im.test.dao.IMDataInterfaceDao;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.rm.ResourceManager;
import cn.crtech.cooperop.bus.util.CommonFun;

public class IMDataInterfaceService extends BaseService {
	@Override
	public void connect() throws Exception {
		connect("base");
	}
	
	public Result listOrgnization(Map<String, Object> params) throws Exception{
		try {
			connect();
			IMDataInterfaceDao dd = new IMDataInterfaceDao();
			return dd.listOrgnization(params);
		} finally {
			disconnect();
		}
	}

	public Result listDepartment(Map<String, Object> params) throws Exception{
		try {
			connect();
			IMDataInterfaceDao dd = new IMDataInterfaceDao();
			return dd.listDepartment(params);
		} finally {
			disconnect();
		}
	}

	public Result listUser(Map<String, Object> params) throws Exception{
		try {
			connect();
			IMDataInterfaceDao dd = new IMDataInterfaceDao();
			Result rs = dd.listUser(params);
			for (Record r : rs.getResultset()) {
				if (!CommonFun.isNe(r.get("avatar"))) {
					Record rr = ResourceManager.getResource("application", r.getString("avatar"));
					r.put("avatar", null);
					if (rr != null) {
						File f = ResourceManager.getFile(false, rr);
						if (f.exists()) {
							r.put("avatar", new FileItem(f, rr.getString("file_name"), rr.getString("mime_type")));
						}
					}
				}
			}
			return rs;
		} finally {
			disconnect();
		}
	}

	public void addOtherAppUser(User user) {
		// TODO Auto-generated method stub

	}

	public void updateOtherAppUser(User user) {
		// TODO Auto-generated method stub

	}

	public void deleteOtherAppUser(User user) {
		// TODO Auto-generated method stub

	}

	public void addGroup(Group group) {
		// TODO Auto-generated method stub

	}

	public void updateGroup(Group group) {
		// TODO Auto-generated method stub

	}

	public void deleteGroup(Group group) {
		// TODO Auto-generated method stub

	}

}
