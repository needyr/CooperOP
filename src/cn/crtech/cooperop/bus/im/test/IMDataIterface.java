package cn.crtech.cooperop.bus.im.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.application.bean.Account;
import cn.crtech.cooperop.bus.im.bean.Department;
import cn.crtech.cooperop.bus.im.bean.FileItem;
import cn.crtech.cooperop.bus.im.bean.Group;
import cn.crtech.cooperop.bus.im.bean.Orgnization;
import cn.crtech.cooperop.bus.im.bean.User;
import cn.crtech.cooperop.bus.im.test.service.IMDataInterfaceService;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.session.Session;
import cn.crtech.cooperop.bus.util.CommonFun;

public class IMDataIterface extends cn.crtech.cooperop.bus.im.Client {

	@Override
	public boolean connectCheck(HttpServletRequest req, HttpServletResponse resp) {
		String userID = req.getParameter("uid");
		Session s = Session.getSession(req, resp);
		Account user = (Account)s.get("userinfo");
		if (user != null && user.getId().equals(userID)) {
			return true;
		}
		return false;
	}

	@Override
	public List<Orgnization> listOrgnization() {
		List<Orgnization> rtn = new ArrayList<Orgnization>();
		Orgnization bean = null;
		Record params = new Record();
		Result rs;
		try {
			rs = new IMDataInterfaceService().listOrgnization(params);
			for (Record r : rs.getResultset()) {
				bean = CommonFun.map2Object(r, Orgnization.class);
				rtn.add(bean);
			}
		} catch (Exception e) {
			log.error("向IM同步机构数据异常: " + e.getMessage(), e);
		}
		return rtn;
	}

	@Override
	public List<Department> listDepartment() {
		List<Department> rtn = new ArrayList<Department>();
		Department bean = null;
		Record params = new Record();
		Result rs;
		try {
			rs = new IMDataInterfaceService().listDepartment(params);
			for (Record r : rs.getResultset()) {
				bean = CommonFun.map2Object(r, Department.class);
				rtn.add(bean);
			}
		} catch (Exception e) {
			log.error("向IM同步部门数据异常: " + e.getMessage(), e);
		}
		return rtn;
	}

	@Override
	public List<User> listUser() {
		List<User> rtn = new ArrayList<User>();
		User bean = null;
		Record params = new Record();
		Result rs;
		try {
			rs = new IMDataInterfaceService().listUser(params);
			for (Record r : rs.getResultset()) {
				FileItem f = null;
				Object t =  r.remove("avatar");
				if (!CommonFun.isNe(t) && t instanceof FileItem) {
					f = (FileItem) t;
				}
				Date birthday = r.getDate("birthday");
				bean = CommonFun.map2Object(r, User.class);
				bean.setAvatar(f);
				if (birthday != null) {
					bean.setBirthday(birthday);
				}
				rtn.add(bean);
			}
		} catch (Exception e) {
			log.error("向IM同步用户数据异常: " + e.getMessage(), e);
		}
		return rtn;
	}

	@Override
	public void addOtherAppUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateOtherAppUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteOtherAppUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addGroup(Group group) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateGroup(Group group) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteGroup(Group group) {
		// TODO Auto-generated method stub

	}

}
