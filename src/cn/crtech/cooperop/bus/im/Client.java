package cn.crtech.cooperop.bus.im;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.bus.im.transfer.Engine;
import cn.crtech.cooperop.bus.im.bean.Department;
import cn.crtech.cooperop.bus.im.bean.Group;
import cn.crtech.cooperop.bus.im.bean.Orgnization;
import cn.crtech.cooperop.bus.im.bean.User;

public abstract class Client {
	public abstract boolean connectCheck(HttpServletRequest req, HttpServletResponse resp);
	public abstract List<Orgnization> listOrgnization();
	public abstract List<Department> listDepartment();
	public abstract List<User> listUser();
	
	public abstract void addOtherAppUser(User user);
	public abstract void updateOtherAppUser(User user);
	public abstract void deleteOtherAppUser(User user);
	public abstract void addGroup(Group group);
	public abstract void updateGroup(Group group);
	public abstract void deleteGroup(Group group);
	
	public static void addOrgnization(Orgnization orgnization) {
		Engine.addOrgnization(orgnization);
	}
	public static void updateOrgnization(Orgnization orgnization) {
		Engine.updateOrgnization(orgnization);
	}
	public static void deleteOrgnization(Orgnization orgnization) {
		Engine.deleteOrgnization(orgnization);
	}
	public static void addDepartment(Department department) {
		Engine.addDepartment(department);
	}
	public static void updateDepartment(Department department) {
		Engine.updateDepartment(department);
	}
	public static void deleteDepartment(Department department) {
		Engine.deleteDepartment(department);
	}
	public static void addUser(User user) {
		Engine.addUser(user);
	}
	public static void updateUser(User user) {
		Engine.updateUser(user);
	}
	public static void deleteUser(User user) {
		Engine.deleteUser(user);
	}
}
