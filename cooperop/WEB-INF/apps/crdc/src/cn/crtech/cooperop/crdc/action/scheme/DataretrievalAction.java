package cn.crtech.cooperop.crdc.action.scheme;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.crdc.dao.scheme.DataretrievalDao;
import cn.crtech.cooperop.crdc.service.scheme.DataretrievalService;
@DisLoggedIn
@DisValidPermission
public class DataretrievalAction extends BaseAction {
   public String save(Map<String, Object> req) throws Exception{
	   HashMap map=(HashMap) req;
	   InputStream zdysqlsIn=new ByteArrayInputStream(map.get("zdysqls").toString().getBytes("GBK"));
	   map.put("zdysqls", zdysqlsIn);
	   InputStream zyfilter_zxSQLIn=new ByteArrayInputStream(map.get("zyfilter_zxsql").toString().getBytes("GBK"));
	   map.put("zyfilter_zxsql", zyfilter_zxSQLIn);
	   InputStream zyfilter_fxSQLIn=new ByteArrayInputStream(map.get("zyfilter_fxsql").toString().getBytes("GBK"));
	   map.put("zyfilter_fxsql", zyfilter_fxSQLIn);
	   InputStream SubDataSQLIn=new ByteArrayInputStream(map.get("subdatasql").toString().getBytes("GBK"));
	   map.put("subdatasql", SubDataSQLIn);
	   new DataretrievalService().save(map);
	   zdysqlsIn.close();
	   zyfilter_zxSQLIn.close();
	   zyfilter_fxSQLIn.close();
	   SubDataSQLIn.close();
	   return "Y";
   }
   public Map<String, Object> add(Map<String, Object> req) throws Exception{
	   if(req.containsKey("fangabh")){
	       Record r = new DataretrievalService().querySingle(req);
	       return r;
	   }else{
		   return null;
	   }
   }
   public String update(Map<String, Object> req) throws Exception{
	   System.out.println(req.get("zdysqls"));
	   InputStream zdysqlsIn=new ByteArrayInputStream(req.get("zdysqls").toString().getBytes("GBK"));
	   req.put("zdysqls", zdysqlsIn);
	   InputStream zyfilter_zxSQLIn=new ByteArrayInputStream(req.get("zyfilter_zxsql").toString().getBytes("GBK"));
	   req.put("zyfilter_zxsql", zyfilter_zxSQLIn);
	   InputStream zyfilter_fxSQLIn=new ByteArrayInputStream(req.get("zyfilter_fxsql").toString().getBytes("GBK"));
	   req.put("zyfilter_fxsql", zyfilter_fxSQLIn);
	   InputStream SubDataSQLIn=new ByteArrayInputStream(req.get("subdatasql").toString().getBytes("GBK"));
	   req.put("subdatasql", SubDataSQLIn);
	   new DataretrievalService().update(req);
	   zdysqlsIn.close();
	   zyfilter_zxSQLIn.close();
	   zyfilter_fxSQLIn.close();
	   SubDataSQLIn.close();
	   return "Y";
   }
   public void list(Map<String, Object> req){
	   System.out.println(req.get("k")+"--");
	   System.out.println(req.get("k"));
   }
   public Result query(Map<String, Object> req) throws Exception{
	   return new DataretrievalService().query(req);
   }
   public String  delete(Map<String, Object> map) throws Exception { 
	   new DataretrievalService().delete(map);
	   return "Y";
   }
}
