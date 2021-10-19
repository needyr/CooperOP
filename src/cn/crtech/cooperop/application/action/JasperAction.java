package cn.crtech.cooperop.application.action;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.service.BillService;
import cn.crtech.cooperop.application.service.ChartService;
import cn.crtech.cooperop.application.service.CommonService;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.rm.ResourceManager;
import cn.crtech.cooperop.bus.rm.core.service.ResourceIndexService;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.HttpClient;
import cn.crtech.cooperop.bus.util.MD5;

@DisValidPermission
@DisLoggedIn
public class JasperAction extends BaseAction {

	public Map<String, Object> initJasper(Map<String, Object> req) throws Exception {
		String pageid = (String) req.get("pageid");
		String djbh = (String) req.get("djbh");
		if(!CommonFun.isNe(req.get("tablekey_"))){
			djbh = (String)req.get((String)req.get("tablekey_"));
		}
		Map<String, String> pageinfo = splitPage(pageid);
		String module = pageinfo.get("system_product_code");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("no", req.get("no"));
		m.put("pageid", req.get("pageid"));
		m.put("system_product_code", pageinfo.get("system_product_code"));
		m.put("type", pageinfo.get("type"));
		m.put("flag", pageinfo.get("flag"));
		m.put("id", pageinfo.get("id"));
		
		Map<String, Object> re = new CommonService().getJasperTemp(m);
		Result r = (Result) re.get("temp");
		File template = null;
		if(r!= null){
			String tid = r.getResultset(0).getString("attach");
			if(CommonFun.isNe(GlobalVar.getSystemProperty("rm_url"))){
				ResourceIndexService service = new ResourceIndexService();
				Record rtn = service.getResource(module, tid);
				
				if (rtn == null) {
					throw new Exception("没找到对应的打印方案");
				}
				template = ResourceManager.getFile(false, rtn);
			}else{
				Map<String, Object> file = new HashMap<String, Object>();
				try {
					Record data = new Record();
					String uid = user().getId();
					String SSO_KEY = GlobalVar.getSystemProperty("sso.key", "!~CROP@CRTECH~!");
					long ts = System.currentTimeMillis();
					String vs = MD5.md5(uid + SSO_KEY + ts);
					data.put("ts", ts);
					data.put("vs", vs);
					data.put("file_id", tid);
					file = CommonFun.json2Object(HttpClient.post(GlobalVar.getSystemProperty("rm_url")+"/cert", data), Map.class);
					if(CommonFun.isNe(file.get("file"))){
						throw new Exception("没找到对应的打印方案");
					}else{
						template = (File) file.get("file");
					}
					
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
		
		BillService bs = new BillService();
		Map<String, Object> hz = null;
		if("Y".equals(req.get("save_befroe_print"))){
			hz = bs.init(pageid, djbh);
		}else{
			hz = req;
		}
		
		Result mxs = (Result) re.get("mx");
		if(mxs != null){
			for(Record mx : mxs.getResultset()){
				mx.put("pageid", pageid);
				mx.put("gzid", req.get("gzid"));
				hz.put(mx.getString("tableid"), bs.queryTable(mx));
			}
		}
		hz.put("jasper_template_", template);
		hz.put("module_", module);
		return hz;
	}
	private Map<String, String> splitPage(String pageid) {
		String[] t = pageid.split("\\.");
		Map<String, String> rtn = new HashMap<String, String>();
		rtn.put("system_product_code", t[0]);
		rtn.put("type", t[1]);
		rtn.put("flag", t[2]);
		rtn.put("id", t[3]);
		return rtn;
	}
}
