package cn.crtech.cooperop.crdc.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.mvc.view.ViewCreater;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.rm.ResourceManager;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.crdc.dao.SystemViewTableDao;
import cn.crtech.cooperop.crdc.dao.DJsetDao;
import cn.crtech.cooperop.crdc.dao.DJsetJGDao;
import cn.crtech.cooperop.crdc.dao.DesignerDao;
import cn.crtech.cooperop.crdc.dao.SavetbDao;
import cn.crtech.cooperop.crdc.dao.SystemChartDao;

public class DesignerService extends BaseService {
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			DesignerDao dd = new DesignerDao();
			return dd.query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result queryHist(Map<String, Object> params) throws Exception {
		try {
			connect();
			DesignerDao dd = new DesignerDao();
			return dd.queryHist(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record get(String type, String id,String flag, String system_product_code) throws Exception {
		try {
			connect();
			DesignerDao dd = new DesignerDao();
			return dd.get(type, id, flag, system_product_code);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void reset(Map<String, Object> params) throws Exception {
		try {
			connect();
			new DesignerDao().reset(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect();
			start();
			DesignerDao dd = new DesignerDao();
			Map<String, Object> attrs = (Map<String, Object>) params.get("attrs");
			int i;
			
			Map<String, Object> djsz = new HashMap<String, Object>();
			djsz.put("system_product_code", attrs.get("system_product_code"));
			djsz.put("type", attrs.get("type"));
			djsz.put("flag", attrs.get("flag"));
			djsz.put("id", attrs.get("schemeid"));
			djsz.put("title", attrs.get("title"));
			djsz.put("description", attrs.get("description"));
			djsz.put("remark", attrs.get("remark"));
			djsz.put("initdjsql", attrs.get("initdjsql"));
			djsz.put("djprintsql", attrs.get("djprintsql"));
			djsz.put("othermxsql", attrs.get("othermxsql"));
			djsz.put("tablename", attrs.get("tablename"));//资料卡片的表名
			djsz.put("tablekey", attrs.get("tablekey"));//资料卡片的表名
			djsz.put("sub", attrs.get("sub"));
			if("Y".equals(params.get("deploy"))){
				djsz.put("state", 1);
				djsz.put("is_use", "1");
				Result r = dd.queryPageCount(params);
				String page_num = "9999";//License.getParams().get("page_num");
				if(CommonFun.isNe(page_num)){
					throw new Exception("没有页面设计权限，请联系管理员购买【页面设计】的权限");
				}
				if(r != null && r.getResultset().size() >= Integer.parseInt(page_num)){
					throw new Exception("您购买的页面数量为："+page_num + "，如果需要添加更多的功能页面，请联系管理员进行购买！");
				}
			}else{
				djsz.put("state", 0);
			}
			djsz.put("last_modify_time", new Date());
			djsz.put("last_modifier", user().getId());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(params);
			djsz.put("content", new ByteArrayInputStream(baos.toByteArray()));
			baos.flush();
			
			//判断是否应该update
			Record r = dd.get((String) attrs.get("type"), (String) attrs.get("schemeid"),(String) attrs.get("flag"),(String)attrs.get("system_product_code"));
			if(r != null && r.get("id") != null){//修改
				//修改之前需要将原数据放到hist表中去
				dd.savehist((String) attrs.get("type"), (String) attrs.get("schemeid"),(String) attrs.get("flag"),(String)attrs.get("system_product_code"));
				i = dd.update((String) attrs.get("type"), (String) attrs.get("schemeid"),(String) attrs.get("flag"),(String)attrs.get("system_product_code"),djsz);
			}else{//新增
				i = dd.insert(djsz);
			}
			if(!CommonFun.isNe(params.get("attr_gj"))){
				DJsetDao djsetDao = new DJsetDao();
				djsetDao.deleteByDJ((String)attrs.get("schemeid"));
				List<Map<String, Object>> gjlist = (List<Map<String, Object>>) params.get("attr_gj");
				for(int a=0; a<gjlist.size(); a++){
					Map<String, Object> p = new HashMap<String, Object>();
					p = gjlist.get(a);
					p.put("viewid", attrs.get("schemeid"));
					p.put("system_product_code",attrs.get("system_product_code"));
					p.put("viewtype", attrs.get("type"));
					djsetDao.insert(p);
				}
			}
			if(!CommonFun.isNe(params.get("attr_gj_jg"))){
				DJsetJGDao djsetjgDao = new DJsetJGDao();
				djsetjgDao.deleteByDJ((String)attrs.get("schemeid"));
				List<Map<String, Object>> gjjglist = (List<Map<String, Object>>) params.get("attr_gj_jg");
				for(int a=0; a<gjjglist.size(); a++){
					Map<String, Object> p = new HashMap<String, Object>();
					p = gjjglist.get(a);
					p.put("viewid", attrs.get("schemeid"));
					p.put("system_product_code",attrs.get("system_product_code"));
					p.put("viewtype", attrs.get("type"));
					djsetjgDao.insert(p);
				}
			}
			if(!CommonFun.isNe(params.get("attr_p"))){
				SavetbDao savetbDao = new SavetbDao();
				savetbDao.deleteByDJ((String)attrs.get("schemeid"),(String) attrs.get("system_product_code"),(String) attrs.get("type"));
				List<Map<String, Object>> plist = (List<Map<String, Object>>) params.get("attr_p");
				for(int a=0; a<plist.size(); a++){
					Map<String, Object> p = new HashMap<String, Object>();
					p = plist.get(a);
					p.put("jigid", (CommonFun.isNe(user().getJigid())?"000":user().getJigid()));
					p.put("viewflagid", ""+attrs.get("schemeid"));
					p.put("system_product_code",attrs.get("system_product_code"));
					p.put("viewtype", attrs.get("type"));
					savetbDao.insert(p);
				}
			}
			SystemViewTableDao cdao = new SystemViewTableDao();
			cdao.deleteByDJ((String)attrs.get("type"), (String)attrs.get("flag"),(String)attrs.get("schemeid"),(String)attrs.get("system_product_code"));
			Result tables = getTable(params);
			Result charts = getChart(params);
			if("chart".equals(attrs.get("type"))){
				SystemChartDao scd = new SystemChartDao();
				scd.deleteSchemeByDJ((String)attrs.get("flag"),(String)attrs.get("schemeid"),(String)attrs.get("system_product_code"));
				scd.deletePlotByDJ((String)attrs.get("flag"),(String)attrs.get("schemeid"),(String)attrs.get("system_product_code"));
				scd.deleteByDJ((String)attrs.get("flag"),(String)attrs.get("schemeid"),(String)attrs.get("system_product_code"));
				for(Record re : tables.getResultset()){
					Map<String,Object> param = new HashMap<String, Object>();
					param.put("flag", attrs.get("flag"));
					param.put("viewid", attrs.get("schemeid"));
					param.put("tableid", re.get("tableid"));
					param.put("fields", re.get("fields"));
					param.put("initsql", re.get("initsql"));
					param.put("countsql", re.get("countsql"));
					param.put("totals", re.get("totals"));
					param.put("system_product_code", attrs.get("system_product_code"));
					param.put("viewtype", attrs.get("type"));
					cdao.insert(param);
					
				}
				for(Record rc : charts.getResultset()){
					Map<String,Object> chart = new HashMap<String, Object>();
					if(!CommonFun.isNe(rc.get("yaxis_sql"))){
						chart.put("tableid", rc.get("tableid"));
						chart.put("yaxis_sql", rc.get("yaxis_sql"));
					}else{
						if(tables!= null && tables.getResultset().size() > 0){
							for(Record re : tables.getResultset()){
								if(rc.get("tableid").equals(re.get("tableid"))){
									chart.put("tableid", re.get("tableid"));
									chart.put("yaxis_sql", re.get("initsql"));
									break;
								}
							}
						}
					}
					chart.put("view_flag", attrs.get("flag"));
					chart.put("view_id", attrs.get("schemeid"));
					chart.put("system_product_code",attrs.get("system_product_code"));
					chart.put("xaxis_sql", rc.get("xaxis_sql"));
					chart.put("group_field", rc.get("group_field"));
					chart.put("g_name", rc.get("g_name"));
					chart.put("title", rc.get("label"));
					chart.put("subtitle", rc.get("sub_label"));
					chart.put("subtitle_href", rc.get("sub_label_href"));
					chart.put("drill_chart_name", rc.get("drill_chart_name"));
					chart.put("drill_tabopen", rc.get("drill_tabopen"));
					chart.put("flag", rc.get("flag"));
					chart.put("drill_chart", rc.get("drill_chart"));
					chart.put("chart_height", rc.get("chart_height"));
					chart.put("welcome_func", rc.get("welcome_func"));
					chart.put("chart_width", rc.get("chart_width"));
					chart.put("stacking", rc.get("stacking"));
					//chart.put("is_yibiao", rc.get("is_yibiao"));//新的仪表7/3
					chart.put("min_num", rc.get("min_num"));//新的仪表7/3
					chart.put("max_num", rc.get("max_num"));
					chart.put("default_num", rc.get("default_num"));
					chart.put("autorefresh", rc.get("autorefresh"));
					chart.put("refresh_time", rc.get("refresh_time"));
					chart.put("num_unit", rc.get("num_unit"));
					
					chart.put("is_span", rc.get("is_span"));//新的span展示
					chart.put("span_head", rc.get("span_head"));
					chart.put("span_content", rc.get("span_content"));
					chart.put("span_footer", rc.get("span_footer"));
					chart.put("color", rc.get("color"));
					chart.put("chart_type", rc.get("chart_type"));
					chart.put("x_name", rc.get("x_name"));
					chart.put("y_name", rc.get("y_name"));
					chart.put("plotline_xname", rc.get("plotline_xname"));
					chart.put("plotline_xvalue", rc.get("plotline_xvalue"));
					chart.put("plotline_yname", rc.get("plotline_yname"));
					chart.put("plotline_yvalue", rc.get("plotline_yvalue"));

					chart.put("dync_sql", rc.get("dync_sql"));
					int chart_id = scd.insert(chart);
					
					List<Map<String, Object>> listplot = (List<Map<String, Object>>) rc.get("plot_bands");
					List<Map<String, Object>> listChartsSchemes = (List<Map<String, Object>>) rc.get("yaxis");
					if(listChartsSchemes != null){
						for(Map<String, Object> m : listChartsSchemes){
							Map<String, Object> chartScheme = new HashMap<String, Object>();
							chartScheme.put("system_charts_id", chart_id);
							chartScheme.put("fieldname", m.get("name"));
							chartScheme.put("fieldname_ch", m.get("label"));
							chartScheme.put("group_field", m.get("g_name"));
							chartScheme.put("chart_type", m.get("chart_type"));
							chartScheme.put("stack", m.get("stack"));
							chartScheme.put("nosql", "1");
							scd.insertScheme(chartScheme);
						}
					}
					if(listplot != null){
						for(Map<String, Object> m : listplot){
							Map<String, Object> plot = new HashMap<String, Object>();
							plot.put("system_charts_id", chart_id);
							plot.put("from_num", m.get("from_num"));
							plot.put("to_num", m.get("to_num"));
							plot.put("color", m.get("color"));
							scd.insertPlot(plot);
						}
					}
				}
			}else{
				for(Record re : tables.getResultset()){
					Map<String,Object> param = new HashMap<String, Object>();
					param.put("flag", attrs.get("flag"));
					param.put("viewid", attrs.get("schemeid"));
					param.put("tableid", re.get("tableid"));
					param.put("fields", re.get("fields"));
					param.put("initsql", re.get("initsql"));
					param.put("countsql", re.get("countsql"));
					param.put("totals", re.get("totals"));
					param.put("system_product_code",attrs.get("system_product_code"));
					param.put("viewtype", attrs.get("type"));
					cdao.insert(param);
				}
			}
			commit();
			if("Y".equals(params.get("deploy"))){
				ViewCreater.create((String)attrs.get("type"), (String)attrs.get("flag"), (String)attrs.get("schemeid"), (String)attrs.get("system_product_code"));
			}
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int update(String type, String id, String flag,String system_product_code, Map<String, Object> params) throws Exception {
		try {
			connect();
			DesignerDao dd = new DesignerDao();
			start();
			if("1".equals(params.get("state")) && !"document".equals(type)){
				params.put("is_use", "1");
				Result r = dd.queryPageCount(params);
				String page_num = "9999";//License.getParams().get("page_num");
				if(CommonFun.isNe(page_num)){
					throw new Exception("没有页面设计权限，请联系管理员购买【页面设计】的权限");
				}
				if(r != null && r.getResultset().size() >= Integer.parseInt(page_num)){
					throw new Exception("您购买的页面数量为："+page_num + "，如果需要添加更多的功能页面，请联系管理员进行购买！");
				}
			}
			int i = dd.update(type, id, flag, system_product_code, params);
			commit();
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int delete(String type, String id, String flag,String system_product_code) throws Exception {
		try {
			connect();
			start();
			int i = new DesignerDao().delete(type, id, flag,system_product_code);
			new SystemViewTableDao().deleteByDJ(type, flag, id, system_product_code);
			if("chart".equals(type)){
				SystemChartDao scd = new SystemChartDao();
				scd.deleteByDJ(flag, id, system_product_code);
				scd.deletePlotByDJ(flag, id, system_product_code);
				scd.deleteSchemeByDJ(flag, id, system_product_code);
			}
			/*Map<String, Object> params = new HashMap<String, Object>();
			params.put("state", -1);
			int i = dd.update(type, id, flag,system_product_code, params);*/
			commit();
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void deployAll(Map<String, Object> req) throws Exception {
		try {
			connect();
			start();
			DesignerDao ds = new DesignerDao();
			Result res = ds.queryAll(req);
			for(Record r : res.getResultset()){
				String type = r.getString("type");
				String flag = r.getString("flag");
				String scheme = r.getString("id");
				String system_product_code = r.getString("system_product_code");
				ViewCreater.create(type, flag, scheme, system_product_code);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("state", 1);
				ds.update(type, scheme, flag,system_product_code, params);
			}
			
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void redeployAll(String productCode) throws Exception {
		ViewCreater.create(productCode);
	}
	public Result querySetting(Map<String, Object> params) throws Exception {
		try {
			connect();
			DJsetDao djsetDao = new DJsetDao();
			return djsetDao.query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result queryJG(Map<String, Object> params) throws Exception {
		try {
			connect();
			DesignerDao jg = new DesignerDao();
			return jg.queryJGlist(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void createmxtable(Map<String, Object> params) throws Exception {
		try {
			connect((String)params.get("system_product_code"));
			start();
			DesignerDao dd = new DesignerDao();
			if (params != null) {
				dd.createmxtable(params);
			}
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record querymxtable(Map<String, Object> params) throws Exception {
		try {
			connect((String)params.get("system_product_code"));
			DesignerDao dd = new DesignerDao();
			return dd.querymxtable(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record queryHZtable(Map<String, Object> params) throws Exception {
		try {
			connect((String)params.get("system_product_code"));
			DesignerDao dd = new DesignerDao();
			return dd.queryHZtable(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result getChart(Map<String, Object> params){
		Result res = new Result();
		if("chart".equals(params.get("type"))){
			Record r = new Record();
			Map<String, Object> attrs = (Map<String, Object>) params.get("attrs");
			r.put("tableid", attrs.get("tableid"));
			r.put("label", attrs.get("label"));
			r.put("sub_label", attrs.get("sub_label"));
			r.put("sub_label_href", attrs.get("sub_label_href"));
			r.put("drill_chart_name", attrs.get("drill_chart_name"));
			r.put("flag", attrs.get("flag"));
			r.put("autoload", attrs.get("autoload"));
			r.put("group_field", attrs.get("group_field"));
			r.put("xaxis_sql", attrs.get("xaxis_sql"));
			r.put("yaxis_sql", attrs.get("yaxis_sql"));
			r.put("yaxis", attrs.get("yaxis"));
			r.put("g_name", attrs.get("g_name"));
			r.put("chart_height", attrs.get("chart_height"));
			r.put("chart_width", attrs.get("cols"));
			r.put("welcome_func", attrs.get("welcome_func"));
			r.put("drill_chart", attrs.get("drill_chart"));
			r.put("drill_tabopen", attrs.get("drill_tabopen"));
			r.put("min_num", attrs.get("min_num"));
			r.put("max_num", attrs.get("max_num"));
			r.put("default_num", attrs.get("default_num"));
			r.put("autorefresh", attrs.get("autorefresh"));
			r.put("refresh_time", attrs.get("refresh_time"));
			r.put("plot_bands", attrs.get("plot_bands"));
			r.put("num_unit", attrs.get("num_unit"));
			r.put("is_yibiao", attrs.get("is_yibiao"));
			
			r.put("is_span", attrs.get("is_span"));//新的span展示
			r.put("span_head", attrs.get("span_head"));
			r.put("span_content", attrs.get("span_content"));
			r.put("span_footer", attrs.get("span_footer"));
			r.put("color", attrs.get("color"));

			r.put("stacking", attrs.get("stacking"));

			r.put("x_name", attrs.get("x_name"));
			r.put("y_name", attrs.get("y_name"));
			r.put("chart_type", attrs.get("chart_type"));

			r.put("plotline_xname", attrs.get("plotline_xname"));
			r.put("plotline_xvalue", attrs.get("plotline_xvalue"));
			r.put("plotline_yname", attrs.get("plotline_yname"));
			r.put("plotline_yvalue", attrs.get("plotline_yvalue"));

			r.put("dync_sql", attrs.get("dync_sql"));
			res.addRecord(r);
		}else{
			List<Map<String, Object>> contents = (List<Map<String, Object>>) params.get("contents");
			for(int i = 0;i<contents.size(); i++){
				List<Record> li = getChart(contents.get(i)).getResultset();
				for(Record r : li){
					res.addRecord(r);
				}
			}
		}
		return res;
	}
	public Result getTable(Map<String, Object> params){
		Result res = new Result();
		if("table".equals(params.get("type"))){
			Record r = new Record();
			Map<String, Object> attrs = (Map<String, Object>) params.get("attrs");
			r.put("tableid", attrs.get("tableid"));
			r.put("initsql", attrs.get("initsql"));
			r.put("countsql", attrs.get("countsql"));
			r.put("totals", attrs.get("totals"));
			r.put("fields", attrs.get("fields"));
			/*r.put("fields", attrs.get("fields"));
			r.put("yaxis", attrs.get("yaxis"));
			r.put("g_name", attrs.get("g_name"));*/
			res.addRecord(r);
		}else{
			List<Map<String, Object>> contents = (List<Map<String, Object>>) params.get("contents");
			for(int i = 0;i<contents.size(); i++){
				List<Record> li = getTable(contents.get(i)).getResultset();
				for(Record r : li){
					res.addRecord(r);
				}
			}
		}
		return res;
	}
	public void createHZTable(Map<String, Object> params) throws Exception{
		Map<String, Object> attrs = (Map<String, Object>) params.get("attrs");
		List<Map<String, Object>> contents = (List<Map<String, Object>>) params.get("contents");
		String tableName = "";
		Map<String, Object> mxtableName = new HashMap<String, Object>();
		if(!CommonFun.isNe(params.get("attr_p"))){
			List<Map<String, Object>> plist = (List<Map<String, Object>>) params.get("attr_p");
			for(int a=0; a<plist.size(); a++){
				if("H".equals(plist.get(a).get("lx"))){
					tableName = plist.get(a).get("tbname").toString();
				}else if("M".equals(plist.get(a).get("lx"))){
					mxtableName.put(plist.get(a).get("tableid").toString(), plist.get(a).get("tbname").toString());
				}
			}
		}
		if(tableName == ""){
			throw new Exception("请在数据存储描述中填写表名！");
		}
		try {
			List<Map<String, Object>> fields = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> tables = new ArrayList<Map<String, Object>>();
			getfields(contents, fields,tables);
			connect("base");
			DesignerDao d = new DesignerDao();
			for(Map<String, Object> m :fields){
				Record r = d.executeQuerySingleRecord("select count(1) s from cr_fldlist where rtrim(fdname)=:fdname", m);
				if(r.getInt("s")==0){
					d.executeUpdate("insert into cr_fldlist (fdname,chnname,fdtype,fdsize) values(:fdname,:chnname,:fdtype,:fdsize)", m);
				}
			}
			for(Map<String, Object> t :tables){
				List<Map<String, Object>> tf = (List<Map<String, Object>>)t.get("fields");
				for(Map<String, Object> f :tf){
					Record r = d.executeQuerySingleRecord("select count(1) s from cr_fldlist where rtrim(fdname)=:fdname", f);
					if(r.getInt("s")==0){
						d.executeUpdate("insert into cr_fldlist (fdname,chnname,fdtype,fdsize) values(:fdname,:chnname,:fdtype,:fdsize)", f);
					}
				}
			}
			disconnect();
			connect((String)attrs.get("system_product_code"));
			start();
			DesignerDao dd = new DesignerDao();
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("tname", tableName);
			Record re = dd.queryHZtable(p);
			if(re.getInt("c")>0){
				Result res = dd.executeQuery("select * from "+tableName);
				if(res!= null && res.getResultset().size()>0){
					throw new Exception("表中已经有数据，不允许删除！");
				}
			}
			if (fields != null) {
				dd.createHZTable(tableName, params, fields);
			}
			if (tables != null) {
				for(int i=0;i<tables.size();i++){
					String xmtname = null;
					if(!CommonFun.isNe(mxtableName.get(tables.get(i).get("tableid")))){
						xmtname = mxtableName.get(tables.get(i).get("tableid")).toString();
					}else{
						xmtname = tableName + "_" + tables.get(i).get("tableid");
					}
					dd.createMXYWTable(xmtname, params,
							(List<Map<String, Object>>)tables.get(i).get("fields"));
				}
			}
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public List<Map<String, Object>> getfields(List<Map<String, Object>> params, List<Map<String, Object>> r, List<Map<String, Object>> tables){
		for(int i =0; i<params.size();i++){
			Map<String, Object> attrs = (Map<String, Object>) params.get(i).get("attrs");
			if(!"table".equals(params.get(i).get("type"))){
				if(!CommonFun.isNe(attrs.get("name"))){
					Map<String, Object> field = new HashMap<String, Object>();
					field.put("name", attrs.get("name"));
					if("字符".equals(attrs.get("fdtype"))){
						field.put("type", " varchar("+attrs.get("maxlength")+") null default '' ");
					}else if("整数".equals(attrs.get("fdtype"))){
						field.put("type", " integer null default 0 ");
					}else if("实数".equals(attrs.get("fdtype"))){
						field.put("type"," decimal("+attrs.get("maxlength")+","+(CommonFun.isNe(attrs.get("precision"))?0:attrs.get("precision"))+") null default 0");
					}else if("位图".equals(attrs.get("fdtype")) || "二进制".equals(attrs.get("fdtype"))){
						field.put("type", " image null");
					}else if("文本".equals(attrs.get("fdtype"))){
						field.put("type", " text null");
					}else{
						field.put("type", " varchar("+attrs.get("maxlength")+")");
					}
					field.put("fdname", attrs.get("name"));
					field.put("fdtype", attrs.get("fdtype"));
					field.put("fdsize", attrs.get("maxlength"));
					field.put("chnname", attrs.get("label"));
					r.add(field);
				}
				List<Map<String, Object>> ch = (List<Map<String, Object>>) params.get(i).get("contents");
				if(ch != null){
					getfields(ch,r,tables);
				}
			}else if("table".equals(params.get(i).get("type"))){
				Map<String, Object> tmap = new HashMap<String, Object>();
				List<Map<String, Object>> tflist = new ArrayList<Map<String, Object>>();
				Map<String, Object> tattrs = (Map<String, Object>) params.get(i).get("attrs");
				List<Map<String, Object>> table = (List<Map<String, Object>>)params.get(i).get("contents");
				tmap.put("tableid", tattrs.get("tableid"));
				for(int t =0; t<table.size();t++){
					if(table.get(t).get("type").equals("tablefields")){
						List<Map<String, Object>> filds = (List<Map<String, Object>>)table.get(t).get("contents");
						for(int f =0; f<filds.size();f++){
							Map<String, Object> fattrs = (Map<String, Object>) filds.get(f).get("attrs");
							if(!CommonFun.isNe(fattrs.get("name"))){
								Map<String, Object> field = new HashMap<String, Object>();
								field.put("name", fattrs.get("name"));
								if("字符".equals(fattrs.get("fdtype"))){
									field.put("type", " varchar("+fattrs.get("size")+") null default '' ");
								}else if("整数".equals(fattrs.get("fdtype"))){
									field.put("type", " integer null default 0 ");
								}else if("实数".equals(fattrs.get("fdtype"))){
									field.put("type"," decimal("+fattrs.get("size")+","+fattrs.get("digitsize")+") null default 0");
								}else if("位图".equals(fattrs.get("fdtype")) || "二进制".equals(fattrs.get("fdtype"))){
									field.put("type", " image null");
								}else if("文本".equals(fattrs.get("fdtype"))){
									field.put("type", " text null");
								}else{
									field.put("type", " varchar("+fattrs.get("size")+")");
								}
								field.put("fdname", fattrs.get("name"));
								field.put("fdtype", fattrs.get("fdtype"));
								field.put("fdsize", fattrs.get("size"));
								field.put("chnname", fattrs.get("label"));
								tflist.add(field);
							}
						}
						
					}
				}
				tmap.put("fields", tflist);
				tables.add(tmap);
			}
		}
		return r;
	}
	
	public Map<String, Object> initFields(Map<String, Object> params) throws Exception{
		try{
			connect((String)params.get("system_product_code"));
			String sql = (String)params.get("sql");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fields", new DesignerDao().getMetaData(sql, params).getResultset());
			return map;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public String downFile(Map<String, Object> params) throws Exception {
		try {
			connect();
			DesignerDao dd = new DesignerDao();
			SystemViewTableDao svtd = new SystemViewTableDao();
			SystemChartDao scd = new SystemChartDao();
			List<Map<String, Object>> list = CommonFun.json2Object((String)params.get("data"), List.class);
			Map<String, Object> file = new HashMap<String, Object>();
			List<Map<String, Object>> pages = new ArrayList<Map<String, Object>>();
			for(int i=0; i< list.size(); i++){
				Map<String,	Object> mp = (Map<String, Object>) list.get(i).get("data");
				Map<String,	Object> m = new HashMap<String, Object>();
				m.put("type", mp.get("type"));
				m.put("viewid", mp.get("id"));
				m.put("view_id", mp.get("id"));
				m.put("flag", mp.get("flag"));
				m.put("system_product_code", mp.get("system_product_code"));
				Record page = dd.get((String)m.get("type"), (String)m.get("viewid"), (String)m.get("flag"), (String)m.get("system_product_code"));
				ObjectInputStream ois = null;
				Object definition =  null;
				try {
					ByteArrayInputStream b = new ByteArrayInputStream(page.getBytes("content"));
					ois = new ObjectInputStream(b);
					definition = ois.readObject();
				} catch (Exception ex) {
					throw ex;
				} finally {
					if (ois != null) ois.close();
				}
				page.put("content", CommonFun.object2Json(definition));
				page.remove("rowno");
				
				Result pageTables = svtd.query(m);
				if(pageTables.getResultset().size() > 0){
					page.put("view_tables", pageTables.getResultset());
				}
				if("chart".equals(m.get("type"))){
					Result charts = scd.query(m);
					for(Record re : charts.getResultset()){
						Map<String, Object> p = new HashMap<String, Object>();
						p.put("system_charts_id", re.getInt("id"));
						Result chartShemes = scd.querySchemes(p);
						Result chartPlots = scd.queryPlots(p);
						if(chartShemes.getResultset().size() > 0){
							re.put("chartShemes", chartShemes.getResultset());
						}
						if(chartPlots.getResultset().size() > 0){
							re.put("chartPlots", chartPlots.getResultset());
						}
					}
					page.put("charts", charts.getResultset());
				}
				pages.add(page);
			}
			file.put("page", pages);
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("file", file);
			String s = CommonFun.object2Xml(p);
			String fileid = ResourceManager.storeResourceFile("crdc", user().getId(), new ByteArrayInputStream(s.getBytes()), "text/xml", ((Map<String, Object>) list.get(0).get("data")).get("description")+".xml", 
					s.getBytes().length+"", "");
			return fileid;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public int upFile(Map<String, Object> params, String forceInsert) throws Exception {
		try {
			List<Map<String, Object>> pages = null;
			if(((Map<String, Object>)params.get("file")).get("page") instanceof Map){
				pages = new ArrayList<Map<String, Object>>();
				pages.add((Map<String, Object>)((Map<String, Object>)params.get("file")).get("page"));
			}else{
				pages = (List<Map<String, Object>>) ((Map<String, Object>)params.get("file")).get("page");
			}
			Map<String, Object> rtn = new HashMap<String, Object>();
			connect();
			start();
			DesignerDao dd = new DesignerDao();
			SystemViewTableDao svtd = new SystemViewTableDao();
			SystemChartDao scd = new SystemChartDao();
			for(Map<String, Object> page : pages){
				page.remove("rowno");
				List<Map<String, Object>> pageTables = null;
				List<Map<String, Object>> charts = null;
				if(!CommonFun.isNe(page.get("view_tables"))){
					if(page.get("view_tables") instanceof Map){
						pageTables = new ArrayList<Map<String, Object>>();
						pageTables.add((Map<String, Object>) page.remove("view_tables"));
					}else{
						pageTables = (List<Map<String, Object>>) page.remove("view_tables");
					}
					
				}
				if(!CommonFun.isNe(page.get("charts"))){
					if(page.get("charts") instanceof Map){
						charts = new ArrayList<Map<String, Object>>();
						charts.add((Map<String, Object>) page.remove("charts"));
					}else{
						charts = (List<Map<String, Object>>) page.remove("charts");
					}
				}
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(CommonFun.json2Object((String)page.get("content"), Map.class));
				page.put("content", new ByteArrayInputStream(baos.toByteArray()));
				baos.flush();
				page.put("last_modify_time", "sysdate");
				page.remove("rownuma");
				if(!"Y".equals(forceInsert) && !CommonFun.isNe(dd.get((String)page.get("type"), (String)page.get("id"), (String)page.get("flag"), (String)page.get("system_product_code")))){
					return 0;
				}else if("Y".equals(forceInsert)){
					dd.delete((String)page.get("type"), (String)page.get("id"), (String)page.get("flag"), (String)page.get("system_product_code"));
				}
				dd.insert(page);
				if(pageTables != null){
					if("Y".equals(forceInsert)){
						svtd.deleteByDJ((String)page.get("type"), (String)page.get("flag"), (String)page.get("id"), (String)page.get("system_product_code"));
					}
					for(Map<String, Object> pageTable : pageTables){
						pageTable.remove("rowno");
						pageTable.remove("rownuma");
						svtd.insert(pageTable);
					}
				}
				if("chart".equals(page.get("type")) && charts != null){
					if("Y".equals(forceInsert)){
						scd.deleteByDJ((String)page.get("flag"), (String)page.get("id"), (String)page.get("system_product_code"));
						scd.deleteSchemeByDJ((String)page.get("flag"), (String)page.get("id"), (String)page.get("system_product_code"));
						scd.deletePlotByDJ((String)page.get("flag"), (String)page.get("id"), (String)page.get("system_product_code"));
					}
					for(Map<String, Object> chart : charts){
						chart.remove("rowno");
						chart.remove("id");
						chart.remove("rownuma");
						List<Map<String, Object>> chartShemes = null;
						List<Map<String, Object>> chartPlots = null;
						if(!CommonFun.isNe(chart.get("chartShemes"))){
							if(chart.get("chartShemes") instanceof Map){
								chartShemes = new ArrayList<Map<String, Object>>();
								chartShemes.add((Map<String, Object>) chart.remove("chartShemes"));
							}else{
								chartShemes = (List<Map<String, Object>>) chart.remove("chartShemes");
							}
						}
						if(!CommonFun.isNe(chart.get("chartPlots"))){
							if(chart.get("chartPlots") instanceof Map){
								chartPlots = new ArrayList<Map<String, Object>>();
								chartPlots.add((Map<String, Object>) chart.remove("chartPlots"));
							}else{
								chartPlots = (List<Map<String, Object>>) chart.remove("chartPlots");
							}
						}
						int chart_id = scd.insert(chart);
						if(chartShemes != null){
							for(Map<String, Object> chartSheme : chartShemes){
								chartSheme.remove("rowno");
								chartSheme.remove("rownuma");
								chartSheme.remove("id");
								chartSheme.remove("system_charts_id");
								chartSheme.put("system_charts_id", chart_id);
								scd.insertScheme(chartSheme);
							}
						}
						if(chartPlots != null){
							for(Map<String, Object> chartPlot : chartPlots){
								chartPlot.remove("rowno");
								chartPlot.remove("rownuma");
								chartPlot.remove("id");
								chartPlot.remove("system_charts_id");
								chartPlot.put("system_charts_id", chart_id);
								scd.insertPlot(chartPlot);
							}
						}
					}
				}
			}
			commit();
			return 1;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
}
