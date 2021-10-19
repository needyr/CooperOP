package cn.crtech.cooperop.crdc.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.bus.engine.window.HtmlWindow;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.mvc.view.ViewCreater;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.rm.ResourceManager;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.crdc.service.DesignerService;

public class DesignerAction extends BaseAction {
	@DisLoggedIn
	@DisValidPermission
	public Map<String, Object> getControlHTML(Map<String, Object> req){
		Map<String, Object> map = req;
		if(!CommonFun.isNe(req.get("schemeid"))){
			
		}else{
			try {
				Object is_mobile = session("is_mobile_login");
				map.put("html", ViewCreater.getControlHtml((String) req.get("jdata"), is_mobile));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	@DisValidPermission
	public Result query(Map<String, Object> req) throws Exception{
		return new DesignerService().query(req);
	}
	@DisValidPermission
	public Result queryHist(Map<String, Object> req) throws Exception{
		return new DesignerService().queryHist(req);
	}
	@DisValidPermission
	public Record get(Map<String, Object> req) throws Exception{
		Record r = new DesignerService().get((String)req.get("type"),(String)req.get("schemeid"),(String)req.get("flag"),(String)req.get("system_product_code"));
		ObjectInputStream ois = null;
		Object definition =  null;
		try {
			ByteArrayInputStream b = new ByteArrayInputStream(r.getBytes("content"));
			ois = new ObjectInputStream(b);
			definition = ois.readObject();
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (ois != null) ois.close();
		}
		
		
		r.put("definition", definition);
		r.remove("rowno");
		return r;
	}
	
	
	@DisValidPermission
	public Map<String, Object> reset(Map<String, Object> req) throws Exception{
		new DesignerService().reset(req);
		return req;
	}
	@DisLoggedIn
	@DisValidPermission
	public Map<String, Object> save(Map<String, Object> req) throws Exception{
		Map<String, Object> map = CommonFun.json2Object((String) req.get("jdata"), Map.class);
		new DesignerService().insert(map);
		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put("result", "success");
		return rtn;
	}
	public Map<String, Object> saveAndDeploy(Map<String, Object> req) throws Exception{
		Map<String, Object> map = CommonFun.json2Object((String) req.get("jdata"), Map.class);
		map.put("deploy", "Y");
		new DesignerService().insert(map);
		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put("result", "success");
		return map;
	}
	public Map<String, Object> deploy(Map<String, Object> req) throws Exception{
		String type = (String)req.get("type");
		String flag = (String)req.get("flag");
		String scheme = (String)req.get("id");
		String system_product_code = (String)req.get("system_product_code");
		ViewCreater.create(type, flag, scheme, system_product_code);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("state", 1);
		new DesignerService().update(type, scheme, flag,system_product_code, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("return", true);
		return map;
	}
	
	public void undeploy(Map<String, Object> req) throws Exception{
		String type = (String)req.get("type");
		String flag = (String)req.get("flag");
		String scheme = (String)req.get("id");
		String system_product_code = (String)req.get("system_product_code");
		
		String pageid = system_product_code + "." + type + "." + flag + "." + scheme;
		String jspFileName = GlobalVar.getWorkPath() + HtmlWindow.getViewPath(pageid);
		File jspFile = new File(jspFileName);
		if (!jspFile.exists()) {
			jspFile.delete();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("state", 0);
		new DesignerService().update(type, scheme, flag,system_product_code, params);
		//删除文件
	}
	
	public Map<String, Object> deployAll(Map<String, Object> req) throws Exception{
		new DesignerService().deployAll(req);;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("return", true);
		return map;
	}
	public Map<String, Object> delete(Map<String, Object> req) throws Exception{
		String type = (String)req.get("type");
		String flag = (String)req.get("flag");
		String scheme = (String)req.get("id");
		String system_product_code = (String)req.get("system_product_code");
		new DesignerService().delete(type, scheme, flag,system_product_code);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("return", true);
		return map;
	}
	public void redeployAll(Map<String, Object> req){
		
	}
	@DisValidPermission
	public Result querySetting(Map<String, Object> req) throws Exception{
		return new DesignerService().querySetting(req);
	}
	@DisValidPermission
	public Result queryJG(Map<String, Object> req) throws Exception{
		return new DesignerService().queryJG(req);
	}
	@DisLoggedIn
	@DisValidPermission
	public void createmxtable(Map<String, Object> req) throws Exception{
		Map<String, Object> map = CommonFun.json2Object((String) req.get("jdata"), Map.class);
		new DesignerService().createmxtable(map);
	}
	@DisLoggedIn
	@DisValidPermission
	public Map<String, Object> querymxtable(Map<String, Object> req) throws Exception{
		Map<String, Object> map = CommonFun.json2Object((String) req.get("jdata"), Map.class);
		return new DesignerService().querymxtable(map);
	}
	@DisLoggedIn
	@DisValidPermission
	public Map<String, Object> queryHZtable(Map<String, Object> req) throws Exception{
		return new DesignerService().queryHZtable(req);
	}
	@DisLoggedIn
	@DisValidPermission
	public void createHZtable(Map<String, Object> req) throws Exception{
		Map<String, Object> map = CommonFun.json2Object((String) req.get("jdata"), Map.class);
		new DesignerService().createHZTable(map);
	}
	
	@DisValidPermission
	public Map<String, Object> initFields(Map<String, Object> req) throws Exception{
		return new DesignerService().initFields(req);
	}
	public Map<String, Object> downFile(Map<String, Object> req) throws Exception{
		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put("fileid", new DesignerService().downFile(req));
		return rtn;
	}
	public Map<String, Object> upFile(Map<String, Object> req) throws Exception{
		Map<String, Object> rtn = new HashMap<String, Object>();
		Record file = ResourceManager.getResource("crdc", (String)req.get("upfile"));
		String forceInsert = (String)req.get("forceInsert");
		File objFile = ResourceManager.getFile(false, file);
		String name = file.getString("file_name");
		if ("xml".equals(name.substring(name.lastIndexOf(".") + 1))) {
			String s =IOUtils.toString(new FileInputStream(objFile), "UTF-8");
			System.out.println(CommonFun.xml2Object(s, Map.class));
			Map<String, Object> m = CommonFun.xml2Object(s, Map.class);
			rtn.put("result", new DesignerService().upFile(m, forceInsert));
		}else{
			rtn.put("result", 2);
		}
		return rtn;
	}
}
