package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.TypeService;
import cn.crtech.precheck.client.Engine;

public class TypeAction extends BaseAction {
	
	public int insert(Map<String, Object> params) throws Exception {
		return new TypeService().insert(params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		stop(params);
		int r = new TypeService().delete(params);
		return r;
	}
	
	public int update(Map<String, Object> params) throws Exception {
		stop(params);
		int r = new TypeService().update(params);
		reload(params);
		return r;
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		Result rs = new TypeService().query(params);
		for (Record re : rs.getResultset()) {
			re.put("running", Engine.engines.containsKey(re.getString("code")));
			// +++
			re.put("exception", Engine.exceptionMark.containsKey("exception#" + re.getString("code")));
			// +++
		}
		return rs;
	}
	
	/*public Result listType(Map<String, Object> params) throws Exception {
		Result rs = new Result();
		File rf = new File(GlobalVar.getWebAppPath(), Engine.CONFIG_PATH);
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
	}*/

	/*public Record type(Map<String, Object> params) throws Exception {
		File rf = new File(GlobalVar.getWebAppPath(), Engine.CONFIG_PATH);
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
	}*/
	
	public Record edit(Map<String, Object> params) throws Exception {
		return new TypeService().get(params);
	}
	
	public void reload(Map<String, Object> params) throws Exception {
		Engine.load((String)params.get("code"));
	}
	
	public void stop(Map<String, Object> params) throws Exception {
		Engine.exceptionMark.remove("exception#" + params.get("code"));
		Engine.remove((String)params.get("code"));
	}
	
}
