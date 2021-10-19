package cn.crtech.cooperop.bus.license;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class LicenseContent implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 2187448955090567643L;

	private Calendar expire = null;
	private String password = null;
	private String cname = null;
	private String ename = null;
	private List<String> plugins = null;

	public Calendar getExpire() {
		return this.expire;
	}

	public void setExpire(Calendar expire) {
		this.expire = expire;
	}

	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		StringBuffer str = new StringBuffer();
		str.append("Licensed to " + this.ename + ", ");
		str.append("valid until " + format.format(this.expire.getTime()) + ", ");
		str.append("licensed plugins: " + plugins);
		return str.toString();
	}

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getEname() {
		return this.ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getPassword() {
		return this.password;
	}

	public List<String> getPlugins() {
		return plugins;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPlugins(List<String> plugins) {
		this.plugins = plugins;
	}
	
	public boolean containtsPlugin(String plugin) {
		if (this.plugins == null) return false;
		for (int i = 0; i < this.plugins.size(); i ++) {
			if (this.plugins.get(i).equals(plugin)) {
				return true;
			}
		}
		return false;
	}
}
