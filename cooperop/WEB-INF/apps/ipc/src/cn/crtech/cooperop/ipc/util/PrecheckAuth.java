package cn.crtech.cooperop.ipc.util;

import java.util.Map;

import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.MD5;

public class PrecheckAuth {

	public static Boolean isAuth(Map<String, Object> params) throws Exception{
		String yycode = GlobalVar.getSystemProperty("yiyuan.code");
		if(CommonFun.isNe(yycode)){
			throw new Exception("无访问权限！");
		}else{
			Object ts = params.remove("ts");
			Object vs = params.remove("vs");
			Object uid = params.remove("uid");
			String md5verify = MD5.md5(uid + yycode + ts);
			if (CommonFun.isNe(uid) || CommonFun.isNe(vs) || CommonFun.isNe(ts) || !vs.equals(md5verify) ) {
				throw new Exception("无访问权限！");
			}
		}
		return true;
	}
}
