package cn.crtech.cooperop.crdc.action.designer;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.mvc.view.ViewCreater;
import cn.crtech.cooperop.bus.util.CommonFun;
import util.ParseXML;

public class DesignerAction extends BaseAction{
		@DisLoggedIn
		@DisValidPermission
		public Map<String, Object> paint(Map<String, Object> req){
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
		@DisLoggedIn
		@DisValidPermission
		public Map<String, Object> createJasper(Map<String, Object> req){
			Map<String, Object> map = CommonFun.json2Object((String) req.get("jdata"), Map.class);
			ParseXML.createXml(map);
			return map;
		}
}
