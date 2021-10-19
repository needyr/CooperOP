package cn.crtech.cooperop.application.action;


import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.choho.authresource.log;
import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.bean.Account;
import cn.crtech.cooperop.application.service.UserService;
import cn.crtech.cooperop.bus.cache.MemoryCache;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.license.License;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.session.Session;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.HttpClient;
import cn.crtech.cooperop.bus.util.MD5;

public class AuthAction extends BaseAction {
	private static final String prix = "system.login";
	@DisLoggedIn
	public Map<String, Object> login(Map<String, Object> map) throws Exception {


		//MQEngine.send("hello.hello.hello", "cooperop", map);
		String validcode = (String) map.remove("validcode");
		String svc = (String) sessionRemove("validcode");

		Record res = new Record();
		if (islock((String)map.get("userid"))) {
			res.put("emsg", "用户已锁定。");
			res.put("econtrol", "islock");
			return res;
		}

		if (isneedvalid(map)) {
			if (CommonFun.isNe(validcode)) {
				res.put("emsg", "验证码必须填写。");
				res.put("econtrol", "validcode");
				res.put("isneedvalid", true);
				return res;
			}
			if (!validcode.equals(svc)) {
				res.put("emsg", "验证码错误。");
				res.put("econtrol", "validcode");
				res.put("isneedvalid", true);
				return res;
			}
		}
		Account user = new UserService().login(map);
		String check_deviceId = SystemConfig.getSystemConfigValue("cooperop", "check_deviceId");
		if("Y".equals(check_deviceId) && user != null){
			if(CommonFun.isNe(map.get("deviceid")) && !user.isSupperUser()){
				res.put("error_flag", "A");
				res.put("emsg", "系统开启了安全登陆验证，本次登陆需要手机app进行授权！");//可以跳转到一个二维码的页面，手机app来扫描
				return res;
			}
		}

		if (user == null) {
			if (setValidMap((String) map.get("userid"))) {
				res.put("emsg", "用户已锁定。");
				res.put("econtrol", "islock");
				res.put("isneedvalid", setneedvalid());
			} else {
				res.put("emsg", "用户不存在或密码错误。");
				res.put("econtrol", "userid");
				res.put("isneedvalid", setneedvalid());
			}
		} else {
			if("Y".equals(check_deviceId)){//判断deviceId
				if(!user.isSupperUser() && !map.get("deviceid").equals(user.getDeviceId())){
					res.put("bang_deviceId", "Y");
					return res;
				}
			}

			session("userinfo", user);
			res.put("userinfo", user);
			String redirect_url = GlobalVar.getSystemProperty("login.url", "index.jsp");
			Session s = Session.getSession();
			String _CRSID = s.getId();
			if (!CommonFun.isNe(user.getWelcomePage())) {
				redirect_url = user.getWelcomePage();
			}
			if("Y".equals(map.get("ism"))){
				session("is_mobile_login", "Y");
				s.put("avatar", user.getAvatar());
				redirect_url = GlobalVar.getSystemProperty("login.app.url", "moblieMain.jsp")+"?_CRSID="+_CRSID;
				//看看是否有手机登陆了该用户

			}else if("Y".equals(map.get("ispad"))){
				session("is_pad_login", "Y");
				s.put("avatar", user.getAvatar());
				redirect_url = GlobalVar.getSystemProperty("login.pad.url", "padMain.jsp")+"?_CRSID="+_CRSID;
				//看看是否有手机登陆了该用户

			}else if("Y".equals(map.get("iswx"))){
				session("is_wx_login", "Y");
				s.put("avatar", user.getAvatar());


				redirect_url = GlobalVar.getSystemProperty("login.wx.url", "padMain.jsp");
				//看看是否有手机登陆了该用户

			}else{
				//看看是否有pc登陆了该用户
			}
			res.put("_CRSID", _CRSID);
			res.put("redirect_url", redirect_url);
			removeneedvalid();
			removeValidMap((String) map.get("userid"));
		}
		return res;
	}


	@DisLoggedIn
	public Map<String, Object> login_v12(Map<String, Object> map) throws Exception {

		//MQEngine.send("hello.hello.hello", "cooperop", map);
		String validcode = (String) map.remove("validcode");
		String svc = (String) sessionRemove("validcode");

		Record res = new Record();
		if (islock((String)map.get("userid"))) {
			res.put("emsg", "用户已锁定。");
			res.put("econtrol", "islock");
			return res;
		}

		if (isneedvalid(map)) {
			if (CommonFun.isNe(validcode)) {
				res.put("emsg", "验证码必须填写。");
				res.put("econtrol", "validcode");
				res.put("isneedvalid", true);
				return res;
			}
			if (!validcode.equals(svc)) {
				res.put("emsg", "验证码错误。");
				res.put("econtrol", "validcode");
				res.put("isneedvalid", true);
				return res;
			}
		}
		Record v12_userinfo = new UserService().login_v12(map);
		Account user = (Account)v12_userinfo.remove("account");
		String check_deviceId = SystemConfig.getSystemConfigValue("cooperop", "check_deviceId");
		if("Y".equals(check_deviceId) && user != null){
			if(CommonFun.isNe(map.get("deviceid")) && !user.isSupperUser()){
				res.put("error_flag", "A");
				res.put("emsg", "系统开启了安全登陆验证，本次登陆需要手机app进行授权！");//可以跳转到一个二维码的页面，手机app来扫描
				return res;
			}
		}

		if (user == null) {
			if (setValidMap((String) map.get("userid"))) {
				res.put("emsg", "用户已锁定。");
				res.put("econtrol", "islock");
				res.put("isneedvalid", setneedvalid());
			} else {
				res.put("emsg", "用户不存在或密码错误。");
				res.put("econtrol", "userid");
				res.put("isneedvalid", setneedvalid());
			}
		} else {
			if("Y".equals(check_deviceId)){//判断deviceId
				if(!user.isSupperUser() && !map.get("deviceid").equals(user.getDeviceId())){
					res.put("bang_deviceId", "Y");
					return res;
				}
			}

			session("userinfo", user);
			res.put("userinfo", user);
			String redirect_url = GlobalVar.getSystemProperty("login.url", "index.jsp");
			Session s = Session.getSession();
			String _CRSID = s.getId();
			if (!CommonFun.isNe(user.getWelcomePage())) {
				redirect_url = user.getWelcomePage();
			}
			if("Y".equals(map.get("ism"))){
				session("is_mobile_login", "Y");
				s.put("avatar", user.getAvatar());
				redirect_url = GlobalVar.getSystemProperty("login.app.url", "moblieMain.jsp")+"?_CRSID="+_CRSID;
				//看看是否有手机登陆了该用户

			}else if("Y".equals(map.get("ispad"))){
				session("is_pad_login", "Y");
				s.put("avatar", user.getAvatar());
				redirect_url = GlobalVar.getSystemProperty("login.pad.url", "padMain.jsp")+"?_CRSID="+_CRSID;
				//看看是否有手机登陆了该用户

			}else if("Y".equals(map.get("iswx"))){
				session("is_wx_login", "Y");
				s.put("avatar", user.getAvatar());


				redirect_url = GlobalVar.getSystemProperty("login.wx.url", "padMain.jsp");
				//看看是否有手机登陆了该用户

			}else{
				//看看是否有pc登陆了该用户
			}
			res.put("_CRSID", _CRSID);
			res.put("redirect_url", redirect_url);
			res.put("v12_userinfo", v12_userinfo);
			removeneedvalid();
			removeValidMap((String) map.get("userid"));
		}
		return res;
	}

	@DisLoggedIn
	public Map<String, Object> susso(Map<String, Object> map) throws Exception {
		String SSO_KEY = "!@#$Tech@CRTech.cn$#*@!Shine";
		String uid = (String) map.get("uid");
		int timeOutMinute = Integer.parseInt(GlobalVar.getSystemProperty("sso.timeout", "300"));
		String verify = (String) map.get("vs");
		String timestamp = (String) map.get("ts");
		String md5verify = "";
		md5verify = MD5.md5(uid + SSO_KEY + timestamp);

		Record res = new Record();
		if (CommonFun.isNe(uid) || CommonFun.isNe(verify) || CommonFun.isNe(timestamp) || !verify.equals(md5verify) ) {
			throw new Exception("无效连接串。");
		}
		if (Math.abs(System.currentTimeMillis() - Long.parseLong(timestamp)) > timeOutMinute * 1000) {
			throw new Exception("无效连接串。");
		}

		Account user = new UserService().sso(uid);
		if (user == null) {
			throw new Exception("无效连接串。");
		}
		res.put("sso_key", GlobalVar.getSystemProperty("sso.key", "!~CROP@CRTECH~!"));
		res.put("session_name", Session.SIDNAME);
		return res;
	}

	@DisLoggedIn
	public Map<String, Object> sso(Map<String, Object> map) throws Exception {
		String SSO_KEY = GlobalVar.getSystemProperty("sso.key", "!~CROP@CRTECH~!");
		String uid = (String) map.get("uid");
		int timeOutMinute = Integer.parseInt(GlobalVar.getSystemProperty("sso.timeout", "300"));
		String verify = (String) map.get("vs");
		String timestamp = (String) map.get("ts");
		String md5verify = "";
		md5verify = MD5.md5(uid + SSO_KEY + timestamp);

		Record res = new Record();
		if (CommonFun.isNe(uid) || CommonFun.isNe(verify) || CommonFun.isNe(timestamp) || !verify.equals(md5verify) ) {
			res.put("error", true);
			res.put("emsg", "无效连接串");
			res.put("econtrol", "invalid");
			return res;
		}
		if (Math.abs(System.currentTimeMillis() - Long.parseLong(timestamp)) > timeOutMinute * 1000) {
			res.put("error", true);
			res.put("emsg", "无效连接串");
			res.put("econtrol", "invalid");
			return res;
		}
		Session s = session();
		if (user() != null && user().getId().equals(uid)) {

		} else {
			Account user = new UserService().sso(uid);
			if (user == null) {
				res.put("error", true);
				res.put("emsg", "无效连接串");
				res.put("econtrol", "invalid");
				return res;
			}
			s.put("userinfo", user);
		}
		res.put("userinfo", user());
		res.put("sessionId", s.getId());
		String redirect_url = GlobalVar.getSystemProperty("login.url", "index.jsp");
		if (!CommonFun.isNe(user().getWelcomePage())) {
			redirect_url = user().getWelcomePage();
		}
		if("Y".equals(map.get("ism"))){
			redirect_url = GlobalVar.getSystemProperty("login.app.url", "moblieMain.jsp");
		}
		res.put("redirect_url", redirect_url);
		return res;
	}

	@DisLoggedIn
	public Map<String, Object> wxlogin(Map<String, Object> map) throws Exception {
		Record res = new Record();
		if(!CommonFun.isNe(map.get("code"))){
			log.debug("===login=state============"+map.get("state"));
			String appid = CommonFun.decryptString(SystemConfig.getSystemConfigValue("cooperop", "wx_appid"));
			String secret = CommonFun.decryptString(SystemConfig.getSystemConfigValue("cooperop", "wx_secret"));
			String code = (String) map.get("code");
			String url="https://api.weixin.qq.com/sns/oauth2/access_token?";//url的拼接
			url = url + "appid="+ appid;
			url = url + "&secret="+ secret;
			url = url + "&code="+ code +"&grant_type=authorization_code";
			String wxMpOAuth2AccessToken = getReturnData(url);//进行网络请求
			log.debug("====wxMpOAuth2AccessToken============"+wxMpOAuth2AccessToken);
			Map<String, Object> param = CommonFun.json2Object(wxMpOAuth2AccessToken, Map.class);
			String openid = CommonFun.encryptString((String)param.get("openid"));
			String unionid = CommonFun.encryptString((String)param.get("unionid"));
			Map<String, Object> m = new HashMap<String, Object>();
			log.debug("===login=state============"+map.get("state"));

			m.put("openid", openid);
			m.put("unionid", unionid);
			Account user = new UserService().weixinlogin(m);
			if (user == null) {
				res.put("emsg", "微信尚未绑定用户，请立即绑定。");
				res.put("econtrol", "nobind");
			} else {
				session("userinfo", user);
				res.put("userinfo", user);
				String redirect_url = GlobalVar.getSystemProperty("login.url", "index.jsp");
				if (!CommonFun.isNe(user().getWelcomePage())) {
					redirect_url = user().getWelcomePage();
				}
				if("Y".equals(map.get("ism"))){
					redirect_url = GlobalVar.getSystemProperty("login.app.url", "moblieMain.jsp");
				}
				res.put("redirect_url", redirect_url);
				res.put("wx_auth_url", "Y");
			}
		}



		return res;
	}
	@DisValidPermission
	public Map<String, Object> authorization(Map<String, Object> map) throws Throwable , Exception {
		Record r = new Record();
		if(!CommonFun.isNe(map.get("code"))){
			String appid = CommonFun.decryptString(SystemConfig.getSystemConfigValue("cooperop", "wx_appid"));
			String secret = CommonFun.decryptString(SystemConfig.getSystemConfigValue("cooperop", "wx_secret"));
			String code = (String) map.get("code");
			String url="https://api.weixin.qq.com/sns/oauth2/access_token?";//url的拼接
			url = url + "appid="+ appid;
			url = url + "&secret="+ secret;
			url = url + "&code="+ code +"&grant_type=authorization_code";
			String wxMpOAuth2AccessToken = getReturnData(url);//进行网络请求
			log.debug("====wxMpOAuth2AccessToken============"+wxMpOAuth2AccessToken);
			Map<String, Object> param = CommonFun.json2Object(wxMpOAuth2AccessToken, Map.class);
			String openid = CommonFun.encryptString((String)param.get("openid"));
			String unionid = CommonFun.encryptString((String)param.get("unionid"));
			Map<String, Object> m = new HashMap<String, Object>();
			log.debug("====state============"+map.get("state"));
			Session s = Session.getSession((String)map.get("state"));
			if(!CommonFun.isNe(s.get("userinfo"))){
				Account u = (Account) s.get("userinfo");
				log.debug("====user============" + u.getId());
				log.info("====user============" + u.getId());
				log.info("====openid============" + openid);
				log.info("====user============" + unionid);
				m.put("id", u.getId());
				m.put("openid", openid);
				m.put("unionid", unionid);
				new UserService().updateByVID(m);
			}
			r.put("redirect_url", "/wxlogin.jsp");
			r.put("wx_auth_url", "Y");
		}
		return r;
	}
	@DisLoggedIn
	public boolean isneedvalid(Map<String, Object> req) throws Exception {
		int needvalidcode = Integer.parseInt(GlobalVar.getSystemProperty("login.needvalidcode", "3"));
		int validNums = (sessionContains("validnums") ? (int) session("validnums") : 0);
		return (needvalidcode <= validNums);
	}

	@DisValidPermission
	public Map<String, Object> logout(Map<String, Object> map) throws Exception {
		Record res = new Record();
		sessionClear();
		res.put("redirect_url", GlobalVar.getSystemProperty("logout.url", "login.jsp"));
		return res;
	}

	public static List<Record> getMenuTree(String popedom) throws Exception {
		return new UserService().getMenuTree(popedom).getResultset();
	}

	public static List<Record> getMenu(String popedom) throws Exception {
		return new UserService().getMenu(popedom).getResultset();
	}

	@DisValidPermission
	public Map<String, Object> initPadMenus(Map<String, Object> map) throws Exception {
		map.put("menus", new UserService().getMenuTree(null).getResultset());
		return map;
	}
	@DisValidPermission
	public Map<String, Object> initMenus(Map<String, Object> map) throws Exception {
		map.put("menus", new UserService().getMenus(map).getResultset());
		return map;
	}
	private boolean setneedvalid() throws Exception {
		int needvalidcode = Integer.parseInt(GlobalVar.getSystemProperty("login.needvalidcode", "3"));
		int validNums = (sessionContains("validnums") ? (int) session("validnums") : 0);
		session("validnums", validNums + 1);
		return (needvalidcode <= validNums + 1);
	}

	private void removeneedvalid() throws Exception {
		sessionRemove("validnums");
	}

	private boolean islock(String userid) throws Exception {
		int lockuser = Integer.parseInt(GlobalVar.getSystemProperty("login.lockuser", "5"));
		Object validObj = MemoryCache.get(prix,"validMap");
		Map<String, Integer> validMap = null;
		if (validObj == null) {
			validMap = new HashMap<String, Integer>();
			MemoryCache.put(prix,"validMap", validMap);
		} else {
			validMap = (Map<String, Integer>) validObj;
		}
		int validNums = 0;
		if (validMap.containsKey(userid)) {
			validNums = validMap.get(userid);
		}
		if(lockuser == 0){
			return false;
		}else{
			return (validNums >= lockuser);
		}

	}

	private boolean setValidMap(String userid) {
		int lockuser = Integer.parseInt(GlobalVar.getSystemProperty("login.lockuser", "5"));
		Object validObj = MemoryCache.get(prix,"validMap");
		Map<String, Integer> validMap = null;
		if (validObj == null) {
			validMap = new HashMap<String, Integer>();
			MemoryCache.put(prix,"validMap", validMap);
		} else {
			validMap = (Map<String, Integer>) validObj;
		}
		int validNums = 1;
		if (validMap.containsKey(userid)) {
			validNums += validMap.get(userid);
		}
		validMap.put(userid, validNums);
		if(lockuser == 0){
			return false;
		}else{
			return (validNums >= lockuser);
		}
	}

	public void removeValidMap(String userid) {
		Object validObj = MemoryCache.get(prix,"validMap");
		Map<String, Integer> validMap = null;
		if (validObj == null) {
			validMap = new HashMap<String, Integer>();
			MemoryCache.put(prix,"validMap", validMap);
		} else {
			validMap = (Map<String, Integer>) validObj;
		}
		if (validMap.containsKey(userid)) {
			validMap.remove(userid);
		}
	}
	@DisValidPermission
	public boolean checkpwd(Map<String,Object> req) throws Exception {

		return new UserService().checkpwd(req);
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

	public static void main(String[] args) {
		String s = "{\"access_token\":\"doZp9yXjWhoj6q54boRkFxGW1LZyJQhyV86cGjs2-aZw3ZaxSQGb16_L0awNmbaIo3L50ysBLdCcGDKop8XV-x5tXqhOSzVy32pcx8-EjXs\",\"expires_in\":7200,\"refresh_token\":\"Spsr6T_nEVhtd_bc4BIslQfPZWeVvh0PlTxQ0weFwuVqE_vne_kfvn_f2F7Ge1y7yIyi_KrHufjCcaJNeP9-xsr_KIJdfzlTxr2tN2StCRQ\",\"openid\":\"oZEyDwGUZ5eKxFHBt1a8TycwnAgs\",\"scope\":\"snsapi_login\",\"unionid\":\"ox-lH1qDQ7yekvEWHesm9cE2EOnw\"}";
		Map m = CommonFun.json2Object(s, Map.class);
		log.debug(m.get("openid"));
	}
	@DisValidPermission
	public Map<String, Object> barcodeLogin(Map<String,Object> req) throws Exception {
		Session s = Session.getSession((String)req.get("loginid"));
		s.put("userinfo", session().get("userinfo"));
		req.put("result", "success");
		return req;
	}
	@DisValidPermission
	public Map<String, Object> loginByApp(Map<String,Object> req) throws Exception {
		Account user = (Account) session().get("userinfo");
		Record res = new Record();
		if(user != null){
			res.put("userinfo", user);
			String redirect_url = GlobalVar.getSystemProperty("login.url", "index.jsp");
			if (!CommonFun.isNe(user.getWelcomePage())) {
				redirect_url = user.getWelcomePage();
			}
			res.put("redirect_url", redirect_url);
		}
		return res;
	}


	@DisLoggedIn
	public Map<String, Object> login_ipc(Map<String, Object> map) throws Exception {
		String validcode = (String) map.remove("validcode");
		String svc = (String) sessionRemove("validcode");

		Record res = new Record();
		if (islock((String)map.get("userid"))) {
			res.put("emsg", "用户已锁定。");
			res.put("econtrol", "islock");
			return res;
		}

		if (isneedvalid(map)) {
			if (CommonFun.isNe(validcode)) {
				res.put("emsg", "验证码必须填写。");
				res.put("econtrol", "validcode");
				res.put("isneedvalid", true);
				return res;
			}
			if (!validcode.equals(svc)) {
				res.put("emsg", "验证码错误。");
				res.put("econtrol", "validcode");
				res.put("isneedvalid", true);
				return res;
			}
		}
		Account user = new UserService().login(map);
		if (user == null) {
			if (setValidMap((String) map.get("userid"))) {
				res.put("emsg", "用户已锁定。");
				res.put("econtrol", "islock");
				res.put("isneedvalid", setneedvalid());
			} else {
				res.put("emsg", "用户不存在或密码错误。");
				res.put("econtrol", "userid");
				res.put("isneedvalid", setneedvalid());
			}
			return res;
		}
		session("userinfo", user);
		res.put("uid", user.getNo());
		res.put(Session.SIDNAME, Session.getSession().getId());
		List<Map<String, Object>> list = getMenus(map);
		res.put("menus", list);
		return res;
	}

	public List<Map<String, Object>> getMenus(Map<String, Object> map) throws Exception {
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		Result res = new UserService().queryHomeMenuSetting(map);
		if(res == null || res.getResultset().size()==0){
			return list;
		}
		for (Record record : res.getResultset()) {
			if(record.getInt("child_num")== 0){
				Map<String, Object> mapmenu=new HashMap<String, Object>();
				mapmenu.put("menu_url", record.get("code"));
				mapmenu.put("menu_name", record.get("name"));
				list.add(mapmenu);
			}
		}
		return list;
	}
	@DisValidPermission
	public List<Record> getMenuTree(Map<String, Object> map) throws Exception {
		return new UserService().getMenuTree((String) map.get("rootPopedom")).getResultset();
	}

	@DisValidPermission
	public Map<String, Object> queryOrganization(Map<String, Object> map) throws Exception {
		if(CommonFun.isNe(session().get("curr_jigid"))){
			map.put("curr_jigid", user().getJigid());
		}else{
			map.put("curr_jigid", session().get("curr_jigid"));
		}
		map.put("jigs", new UserService().queryOrganization(map).getResultset());
		map.put("app_info", License.checkTime());
		return map;
	}

	@DisValidPermission
	public Result initProduct(Map<String, Object> map) throws Exception {
		return new UserService().initProduct(map);
	}
	@DisValidPermission
	public Map<String, Object> changeJG(Map<String, Object> map) throws Exception {
		Session session = session();
		session.put("curr_jigid", map.get("jigid"));
		if(session.get("userinfo") != null){
			Account user = (Account) session.get("userinfo");
			user.setJigid((String)map.get("jigid"));
			user.setJigname((String)map.get("jigname"));
			session.put("userinfo", user);
		}
		return map;
	}
	@DisValidPermission
	public Result searchMenu(Map<String, Object> req) throws Exception {
		return new UserService().searchMenu(req);
	}
	@DisValidPermission
	public Map<String, Object> rmcert(Map<String, Object> map) throws Exception {
		Map<String, Object> r = new HashMap<String, Object>();
		try {
			String uid = user().getId();
			String SSO_KEY = GlobalVar.getSystemProperty("sso.key", "!~CROP@CRTECH~!");
			long ts = System.currentTimeMillis();
			String vs = MD5.md5(uid + SSO_KEY + ts);
			map.put("ts", ts);
			map.put("vs", vs);
			map.put("uid", uid);
			r = CommonFun.json2Object(HttpClient.post(GlobalVar.getSystemProperty("rm_url")+"/cert", map), Map.class);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return r;
	}
}
