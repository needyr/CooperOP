package cn.crtech.cooperop.crdc.action.scheme;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.crdc.service.scheme.DataretrievalService;
import cn.crtech.cooperop.crdc.service.scheme.DistillSercice;
import cn.crtech.cooperop.crdc.service.scheme.PagemodifyService;
@DisLoggedIn
@DisValidPermission
public class DistillAction extends BaseAction {
	public void list(Map<String, Object> req) {
	}
	public String save(Map<String, Object> req) throws Exception{
//		   InputStream sql_textIn=new ByteArrayInputStream(req.get("sql_text").toString().getBytes("UTF-8"));
//		   req.put("sql_text", sql_textIn);
//		   InputStream d_sqlIn=new ByteArrayInputStream(req.get("d_sql").toString().getBytes("UTF-8"));
//		   req.put("d_sql", d_sqlIn);
//		   InputStream m_sqlIn=new ByteArrayInputStream(req.get("m_sql").toString().getBytes("UTF-8"));
//		   req.put("m_sql", m_sqlIn);
		   new DistillSercice().save(req);
//		   sql_textIn.close();
//		   d_sqlIn.close();
//		   m_sqlIn.close();
		   return "Y";
	 }
	 public Map<String, Object> add(Map<String, Object> req) throws Exception{
		   if(req.containsKey("fangabh")){
		       Record r = new DistillSercice().querySingle(req);
		       return r;
		   }else{
			   return null;
		   }
	 }
	 public String update(Map<String, Object> req) throws Exception{
//		 InputStream sql_textIn=new ByteArrayInputStream(req.get("sql_text").toString().getBytes("UTF-8"));
//		   req.put("sql_text", sql_textIn);
//		   InputStream d_sqlIn=new ByteArrayInputStream(req.get("d_sql").toString().getBytes("UTF-8"));
//		   req.put("d_sql", d_sqlIn);
//		   InputStream m_sqlIn=new ByteArrayInputStream(req.get("m_sql").toString().getBytes("UTF-8"));
//		   req.put("m_sql", m_sqlIn);
		   new DistillSercice().update(req);
//		   sql_textIn.close();
//		   d_sqlIn.close();
//		   m_sqlIn.close();
		   return "Y";
	 }
	 public Result query(Map<String, Object> req) throws Exception{
		   return new DistillSercice().query(req);
	 }
	 public String  delete(Map<String, Object> map) throws Exception { 
		   new DistillSercice().delete(map);
		   return "Y";
	 }
}
