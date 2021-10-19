package cn.crtech.cooperop.hospital_common.action;
import java.io.File;
import java.io.FileFilter;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.hospital_common.service.WebserviceService;
import cn.crtech.precheck.client.Engine;

public class WebserviceAction extends BaseAction {

	public int insert(Map<String, Object> params) throws Exception {
		return new WebserviceService().insert(params);
	}

	public int delete(Map<String, Object> params) throws Exception {
		stop(params);
		int rtn = new WebserviceService().delete(params);
		return rtn;
	}

	public int update(Map<String, Object> params) throws Exception {
		stop(params);
		int r = new WebserviceService().update(params);
		reload(params);
		return r;
	}

	public Result query(Map<String, Object> params) throws Exception {
		Result rs = new WebserviceService().query(params);
		for (Record re : rs.getResultset()) {
			re.put("running", Engine.engines.containsKey(re.getString("code")));
			// +++
			re.put("exception", Engine.exceptionMark.get("exception#" + re.getString("code")));
			// +++
		}
		return rs;
	}

	public Result listType(Map<String, Object> params) throws Exception {
		Result rs = new Result();
		File rf = new File(GlobalVar.getWorkPath(), Engine.CONFIG_PATH);
		File[] cfs = rf.listFiles(new FileFilter() {

			@Override
			public boolean accept(File f) {
				return f.isDirectory();
			}
		});
		if (cfs != null) {
			Record cr;
			File cfc;
			Document document;
			Element root;
			for (File cf : cfs) {
				cr = new Record();
				cr.put("code", cf.getName().toUpperCase());
				cr.put("name", cr.get("code"));
				cfc = new File(cf, "definition.xml");
				if (cfc.exists()) {
					document = CommonFun.loadXMLFile(cfc);
					root = document.getRootElement();
					cr.put("name", root.getChildTextTrim("name"));
					cr.put("description", root.getChildTextTrim("description"));
					cr.put("definition", root.getChildTextTrim("definition"));
					cr.put("initparams", root.getChildTextTrim("initparams"));
					cr.put("initsql", root.getChildTextTrim("initsql"));
					cr.put("remark", root.getChildTextTrim("remark"));
				}
				rs.addRecord(cr);
			}
		}
		return rs;
	}

	public Record type(Map<String, Object> params) throws Exception {
		File rf = new File(GlobalVar.getWorkPath(), Engine.CONFIG_PATH);
		File[] cfs = rf.listFiles(new FileFilter() {

			@Override
			public boolean accept(File f) {
				return f.isDirectory();
			}
		});
		if (cfs != null) {
			Record cr;
			File cfc;
			Document document;
			Element root;
			for (File cf : cfs) {
				if (cf.getName().equalsIgnoreCase((String)params.get("code"))) {
					cr = new Record();
					cr.put("code", cf.getName().toUpperCase());
					cr.put("name", cr.get("code"));
					cfc = new File(cf, "definition.xml");
					if (cfc.exists()) {
						document = CommonFun.loadXMLFile(cfc);
						root = document.getRootElement();
						cr.put("name", root.getChildTextTrim("name"));
						cr.put("definition", root.getChildTextTrim("definition"));
						cr.put("initparams", root.getChildTextTrim("initparams"));
						cr.put("description", root.getChildTextTrim("description"));
						cr.put("initsql", root.getChildTextTrim("initsql"));
						cr.put("remark", root.getChildTextTrim("remark"));
					}
					return cr;
				}
			}
		}
		return null;
	}

	public Record edit(Map<String, Object> params) throws Exception {
		return new WebserviceService().get(params);
	}

	public void reload(Map<String, Object> params) throws Exception {
		params.put("state", "1");
		new WebserviceService().update(params);
		Engine.load((String) params.get("code"));
	}

	public void stop(Map<String, Object> params) throws Exception {
		params.put("state", "0");
		new WebserviceService().update(params);
		Engine.exceptionMark.remove("exception#" + params.get("code"));
		Engine.remove((String) params.get("code"));
	}
}
