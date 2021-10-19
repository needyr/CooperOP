package cn.crtech.cooperop.application.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.bean.Account;
import cn.crtech.cooperop.application.service.BillService;
import cn.crtech.cooperop.application.service.UserService;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.rm.ResourceManager;
import cn.crtech.cooperop.bus.session.Session;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.ExcelUtil;
@DisValidPermission
public class BillAction extends BaseAction {
	
	public static Map<String, Object> init(String pageid, String djbh) throws Exception {
		return new BillService().init(pageid, djbh);
	}
	
	public static Map<String, Object> initFromCache(String pageid, String gzid) throws Exception {
		return new BillService().initFromCache(pageid, gzid);
	}

	public static Map<String, Object> initFromCacheMX(String p_pageid, String pageid, String tableid
			, String dj_sn, String dj_sort, String gzid) throws Exception {
		return new BillService().initFromCacheMX(p_pageid, pageid, tableid, dj_sn, dj_sort, gzid);
	}
	
	public Result queryTable(Map<String, Object> req) throws Exception {
		req.put("userid", user().getId()); // ygz.2021-07-28 增加userid 字段，防止页面未传递导致sql使用该值时报错
		return new BillService().queryTable(req);
	}

	public void save(Map<String, Object> req) throws Exception {
		Map<String, Object> params = CommonFun.json2Object((String)req.get("data"), Map.class);
		params.put("issave", "true");
		new BillService().save(params);
	}

	public void modifyFunction(Map<String, Object> req) throws Exception {
		new BillService().modifyFunction(req);
	}

	public String submit(Map<String, Object> req) throws Exception {
		Map<String, Object> params = CommonFun.json2Object((String)req.get("data"), Map.class);
		/*params.put("zhiyid", user().getId());
		//params.put("username", user().getNo());
		params.put("lgnname", user().getName());
		//params.put("jigid", CommonFun.isNe(user().getJigid())?"000":user().getJigid());
		params.put("jigname", user().getJigname());*/
		return new BillService().submit(params);
	}
	public String approval(Map<String, Object> req) throws Exception {
		Map<String, Object> params = CommonFun.json2Object((String)req.get("data"), Map.class);
		new BillService().approval(params);
		return "success";
	}
	public String approvalnotsave(Map<String, Object> req) throws Exception {
		Map<String, Object> params = CommonFun.json2Object((String)req.get("data"), Map.class);
		new BillService().approvalnotsave(params);
		return "success";
	}
	public Map<String, Object> addMX(Map<String, Object> req) throws Exception {
		new BillService().addMX(req);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		return map;
	}
	public Map<String, Object> updateMX(Map<String, Object> req) throws Exception {
		Map<String, Object> params = CommonFun.json2Object((String)req.get("data"), Map.class);
		new BillService().updateMX(params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		return map;
	}
	public Map<String, Object> deleteMX(Map<String, Object> req) throws Exception {
		Map<String, Object> params = CommonFun.json2Object((String)req.get("data"), Map.class);
		new BillService().deleteMX(params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		return map;
	}
	public Result querygz(Map<String, Object> req) throws Exception {
		req.put("zhiyid", user().getId());
		return new BillService().querygz(req);
	}
	public String backprocess(Map<String, Object> req) throws Exception {
		Map<String, Object> params = CommonFun.json2Object((String)req.get("data"), Map.class);
		params.put("zhiyid", user().getId());
		//params.put("username", user().getNo());
		params.put("lgnname", user().getName());
		params.put("jigid", CommonFun.isNe(user().getJigid())?"000":user().getJigid());
		params.put("jigname", user().getJigname());
		return new BillService().backprocess(params);
	}
	public String resubmit(Map<String, Object> req) throws Exception {
		Map<String, Object> params = CommonFun.json2Object((String)req.get("data"), Map.class);
		params.put("zhiyid", user().getId());
		//params.put("username", user().getNo());
		params.put("lgnname", user().getName());
		params.put("jigid", CommonFun.isNe(user().getJigid())?"000":user().getJigid());
		params.put("jigname", user().getJigname());
		return new BillService().resubmit(params);
	}
	
	@DisValidPermission
	public Map<String, Object> authorization(Map<String, Object> map) throws Throwable , Exception {
		Record r = new Record();
		if(!CommonFun.isNe(map.get("code"))){
			String[] states = map.get("state").toString().split("_");
			String state = states[0];
			String tourl = states[1];
			String djbh = states[2];
			String order_id = states[3];
			String task_id = states[4];
			String appid = CommonFun.decryptString(SystemConfig.getSystemConfigValue("cooperop", "wx_appid"));
			String secret = CommonFun.decryptString(SystemConfig.getSystemConfigValue("cooperop", "wx_secret"));
			String code = (String) map.get("code");
			String url="https://api.weixin.qq.com/sns/oauth2/access_token?";//url的拼接
			url = url + "appid="+ appid;
			url = url + "&secret="+ secret;
			url = url + "&code="+ code +"&grant_type=authorization_code";
			String wxMpOAuth2AccessToken = getReturnData(url);//进行网络请求
			System.out.println("====wxMpOAuth2AccessToken============"+wxMpOAuth2AccessToken);
			Map<String, Object> param = CommonFun.json2Object(wxMpOAuth2AccessToken, Map.class);
			String openid = CommonFun.encryptString((String)param.get("openid"));
			String unionid = CommonFun.encryptString((String)param.get("unionid"));
			Map<String, Object> m = new HashMap<String, Object>();
			System.out.println("====state============"+map.get("state"));
			Session s = Session.getSession(state);
			if(!CommonFun.isNe(s.get("userinfo"))){
				Account u = (Account) s.get("userinfo");
				if(u != null && unionid.equals(u.getUnionid()) && openid.equals(u.getOpenid())){
					r.put("redirect_url", tourl+"?djbh="+djbh+"&order_id="+order_id+"&task_id="+task_id);
					r.put("wx_auth_url", "Y");
				}
			}
		}
		return r;
	}
	
	
	public String getReturnData(String urlString) throws UnsupportedEncodingException {  
        String res = "";   
        try {   
            URL url = new URL(urlString);  
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();  
            conn.setDoOutput(true);  
            conn.setRequestMethod("POST");  
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"UTF-8"));  
            String line;  
            while ((line = in.readLine()) != null) {  
                res += line;  
            }  
            in.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
//      log.debug(res);  
        return res;  
    }
	@DisValidPermission
	public Map<String, Object> importExcels(Map<String, Object> req) throws Exception {
		String pageid = (String) req.get("pageid");
		Map<String, String> pageinfo = BillService.splitPage(pageid);
		Record file = ResourceManager.getResource(pageinfo.get("system_product_code"), (String) req.get("up_excel"));
		File objFile = ResourceManager.getFile(false, file);
		String name = file.getString("file_name");
		String type = name.substring(name.lastIndexOf(".") + 1);
		List<Result> reqlist = new ArrayList<Result>();
		if (!CommonFun.isNe(type)) {
			if (type.equals("xls"))
				reqlist = ExcelUtil.analysisXLS(new FileInputStream(objFile));
			else if (type.equals("xlsx")) {
				reqlist = ExcelUtil.analysisXLSX(new FileInputStream(objFile));
			}
		}
		int count = reqlist.get(0).getResultset().size();
		System.out.println("count===============" + count);
		req.put("sheet1", reqlist.get(0).getResultset());
		new BillService().importExcels(req);
		return req;
	}
	public Map<String, Object> getPYM(Map<String, Object> req) throws Exception {
		return new BillService().getPYM(req);
	}
	
	public String backprocess_v12(Map<String, Object> req) throws Exception {
		req.put("zhiyid", user().getId());
		//params.put("username", user().getNo());
		req.put("lgnname", user().getName());
		req.put("jigid", CommonFun.isNe(user().getJigid())?"000":user().getJigid());
		req.put("jigname", user().getJigname());
		return new BillService().backprocess(req);
	}
	public String approval_v12(Map<String, Object> req) throws Exception {
		new BillService().approvalnotsave(req);
		return "success";
	}
	public String submit_v12(Map<String, Object> req) throws Exception {
		new BillService().submit_v12(req);
		return "success";
	}
}
