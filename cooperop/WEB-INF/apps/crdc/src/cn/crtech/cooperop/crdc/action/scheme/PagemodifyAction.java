package cn.crtech.cooperop.crdc.action.scheme;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.crdc.service.scheme.DataretrievalService;
import cn.crtech.cooperop.crdc.service.scheme.PagemodifyService;
@DisLoggedIn
@DisValidPermission
public class PagemodifyAction {
	public void list(Map<String, Object> req) {

	}
	 public String save(Map<String, Object> req) throws Exception{
		 System.out.println("--------------");
		   InputStream zdysqlsIn=new ByteArrayInputStream(req.get("zdysqls").toString().getBytes("GBK"));
		   req.put("zdysqls", zdysqlsIn);
		   new PagemodifyService().save(req);
		   zdysqlsIn.close();
		   return "Y";
	 }
	 public Map<String, Object> add(Map<String, Object> req) throws Exception{
		   if(req.containsKey("fangabh")){
		       Record r = new PagemodifyService().querySingle(req);
		       return r;
		   }else{
			   return null;
		   }
	   }
	 public String update(Map<String, Object> req) throws Exception{
		 InputStream zdysqlsIn=new ByteArrayInputStream(req.get("zdysqls").toString().getBytes("GBK"));
		   req.put("zdysqls", zdysqlsIn);
		   new PagemodifyService().update(req);
		   zdysqlsIn.close();
		   return "Y";
	 }
	 public Result query(Map<String, Object> req) throws Exception{
		   return new PagemodifyService().query(req);
	   }
	   public String  delete(Map<String, Object> map) throws Exception { 
		   new PagemodifyService().delete(map);
		   return "Y";
	   }
}
