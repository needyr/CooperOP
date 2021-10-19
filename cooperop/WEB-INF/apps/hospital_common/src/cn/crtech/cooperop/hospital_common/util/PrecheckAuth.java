package cn.crtech.cooperop.hospital_common.util;

import java.util.Map;

import cn.crtech.cooperop.application.bean.Account;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.MD5;

public class PrecheckAuth {

	public static Boolean isAuth(Account user, Map<String, Object> params) throws Exception{
		String yycode = GlobalVar.getSystemProperty("yiyuan.code");
		Object ts = params.remove("ts");
		Object vs = params.remove("vs");
		Object uid = params.remove("uid");
		String md5verify = MD5.md5(uid + yycode + ts);
		if (CommonFun.isNe(yycode)|| CommonFun.isNe(uid) || CommonFun.isNe(vs) || CommonFun.isNe(ts) || !vs.equals(md5verify) ) {
			if(CommonFun.isNe(user)){
				throw new Exception("无权访问！");
			}
		}
		return true;
	}
}
