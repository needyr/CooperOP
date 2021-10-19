package cn.crtech.cooperop.application.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.dao.BillDao;
import cn.crtech.cooperop.application.dao.SchemeDao;
import cn.crtech.cooperop.bus.cache.Dictionary;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.CooperopException;
import cn.crtech.cooperop.bus.util.GlobalVar;

public class SchemeService extends BaseService {
	public Map<String, Object> initQueryDialog(Map<String, Object> params) throws Exception {
		try {
			if(!CommonFun.isNe(params.get("no_need_query"))){
				return params;
			}
			connect();
			SchemeDao sd = new SchemeDao();
			String stype = SchemeDao.TABLE_QUERY_SCHEME;
			String sql = null;
			String sql_mx = null;
			if("dj_select_".equals(params.get("schemetype"))){
				stype = SchemeDao.TABLE_DJSELECT_SCHEME;
			}
			Record scheme = sd.getScheme(params,stype);  
			if (scheme == null) {
				throw new CooperopException(CooperopException.DATA_NOT_FOUND);
			}
			if("dj_select_".equals(params.get("schemetype"))){
				sql = scheme.getString("sql_text");
				sql_mx = scheme.getString("d_sql");
			}else{
				sql = scheme.getString("zdysqls");
			}
			if (CommonFun.isNe(sql)) {
				throw new CooperopException(CooperopException.DATA_NOT_FOUND);
			}
			
			disconnect();

			connect(scheme.getString("system_product_code"));
			sd = new SchemeDao();
			
			Record tp = new Record();
			Iterator<String> it = params.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				tp.put("hz." + key, params.get(key));
			}
			params.putAll(tp);
			
			if(CommonFun.isNe(params.get("jigid"))){
				params.put("jigid", user().getJigid());
			}
			if (user() != null) {
				params.put("userid_fqr", params.get("zhiyid"));
				params.put("userid", user().getId());
				params.put("zhiyid", user().getNo());
				params.put("bmid", user().getBmid());
			}
//			Result filterfields = new Result();
//			if (!CommonFun.isNe(scheme.get("filterflds"))) {
//				String[] fields = scheme.getString("filterflds").split(",");
//				for (String field : fields) {
//					if (Dictionary.getField(field) != null) {
//						filterfields.getResultset().add(Dictionary.getField(field));
//					}
//				}
//				scheme.put("filterfields", filterfields.getResultset());
//			}
			Record rtn = new Record();
			Result meta =  sd.getQuerySchemeMetaData(sql.toLowerCase(), params, scheme);
			if(sql_mx != null){
				Result rr = sd.executeQueryScheme(sql.toLowerCase(), params, scheme);
				if(rr != null && rr.getResultset().size() > 0){
					Result meta1 =  sd.getQuerySchemeMetaData(sql_mx.toLowerCase(), rr.getResultset(0), scheme);
					rtn.put("mxfields", meta1.getResultset());
				}
			}
			/*if(!CommonFun.isNe(scheme.getString("d_sql"))){
				Result mxmeta =  sd.getQuerySchemeMetaData(scheme.getString("d_sql").toLowerCase(), params, scheme);
				rtn.put("mxfields", mxmeta.getResultset());
			}*/
			if(!CommonFun.isNe(scheme.get("app_fields"))){
				rtn.put("appflds", scheme.getString("app_fields").split(","));
			}
			rtn.put("scheme", scheme);
			rtn.put("fields", meta.getResultset());
			rtn.put("count", meta.getCount());
			return rtn;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Result executeQueryScheme(Map<String, Object> params) throws Exception {
		try {
			connect();
			SchemeDao sd = new SchemeDao();
			String stype = SchemeDao.TABLE_QUERY_SCHEME;
			String sql = null;
			if("dj_select_".equals(params.get("schemetype"))){
				stype = SchemeDao.TABLE_DJSELECT_SCHEME;
			}
			Record scheme = sd.getScheme(params,stype);  
			if (scheme == null) {
				return new Result();
			}
			if("dj_select_".equals(params.get("schemetype"))){
				sql = scheme.getString("sql_text");
			}else{
				sql = scheme.getString("zdysqls");
			}
			if (CommonFun.isNe(sql)) {
				throw new CooperopException(CooperopException.DATA_NOT_FOUND);
			}
			
			disconnect();
			
			connect(scheme.getString("system_product_code"));
			sd = new SchemeDao();
			
			if (params == null) {
				params = new Record();
			}
			if(CommonFun.isNe(params.get("jigid"))){
				params.put("jigid", user().getJigid());
			}
			if (user() != null) {
				params.put("userid_fqr", params.get("zhiyid"));
				params.put("userid", user().getId());
				params.put("zhiyid", user().getNo());
				params.put("bmid", user().getBmid());
			}
			
			return sd.executeQueryScheme(sql.toLowerCase(), params, scheme);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result schemelist(Map<String, Object> params) throws Exception {
		try {
			connect();
			if(CommonFun.isNe(params.get("system_product_code"))){
				throw(new Exception("无产品"));
			}
			SchemeDao sd = new SchemeDao();
			return sd.executeQuerySchemeList(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record executeYmUpScheme(Map<String, Object> params) throws Exception {
		Map<String, String> pageinfo = splitPage((String) params.remove("pageid"));
		Object scheme = params.remove("scheme");
		try {
			connect();
			SchemeDao sd = new SchemeDao();
			//执行方案
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("system_product_code", params.get("system_product_code"));
			p.put("fangalx", params.get("djlx"));
			p.put("fangabh", scheme);
			Record schemeRecord = sd.getScheme(p,SchemeDao.TABLE_UPDATE_SCHEME);  
			if (scheme == null) {
				return new Record();
			}
			if (CommonFun.isNe(schemeRecord.getString("zdysqls"))) {
				return new Record();
			}
			disconnect();
			List<Map<String, Object>> tables = (List<Map<String, Object>>) params.remove("tables");
			if(CommonFun.isNe(params.get("jigid"))){
				params.put("jigid", user().getJigid());
			}
			Record rtn = new Record();
			String res = null;
			connect(schemeRecord.getString("system_product_code"));
			sd = new SchemeDao();
			if("Y".equals(schemeRecord.get("batch_exe"))){
				if (user() != null) {
					params.put("userid_fqr", params.get("zhiyid"));
					params.put("userid", user().getId());
					params.put("zhiyid", user().getNo());
					params.put("bmid", user().getBmid());
				}
				if(tables != null){
					for(int i = 0 ; i<tables.size(); i++){
						List<Map<String, Object>> selecteds = (List<Map<String, Object>>) tables.get(i).get("selected");
						StringBuffer sb = new StringBuffer();
						sb.append("<object>");
						for(int j = 0; j< selecteds.size(); j++){
							sb.append("<row ");
							Map<String, Object> re = (Map<String, Object>) selecteds.get(j).get("data");
							for(String k : re.keySet()){
								if(re.get(k) !=null){
									String xv = re.get(k).toString();
									xv = xv.replaceAll("<br/>", "").replaceAll("<br />", "");
									sb.append(" "+k+"=\""+xv+"\" ");
								}
							}
							sb.append(" />");
						}
						sb.append("</object>");
						String keyst = "get"+tables.get(i).get("tableid").toString().toLowerCase()+"xml";
						params.put(keyst,sb.toString());
					}
				}
				res = sd.executeScheme(schemeRecord.getString("zdysqls").toLowerCase(), params);
			}else{
				//删除临时表,插入数据
				sd.updateCacheHZ((String)params.get("gzid"),params);
				
				if (user() != null) {
					params.put("userid_fqr", params.get("zhiyid"));
					params.put("userid", user().getId());
					params.put("zhiyid", user().getNo());
					params.put("bmid", user().getBmid());
				}
				if(tables != null){
					for(int i = 0 ; i<tables.size(); i++){
						//sd.delecteCacheMX((String)pageinfo.get("flag"),(String)pageinfo.get("id"),(String)tables.get(i).get("tableid"),(String)params.get("gzid"));
						Record mammx = sd.queryCacheMX_maxSN((String)pageinfo.get("flag"),(String)pageinfo.get("id"),(String)tables.get(i).get("tableid"),(String)params.get("gzid"));
						int max_dj_sn = mammx.getInt("max_sn");
						int max_dj_sort = mammx.getInt("max_sort");
						if(!CommonFun.isNe(tables.get(i).get("tr"))){
							List<Map<String, Object>> trlist = (List<Map<String, Object>>) tables.get(i).get("tr");
							for(int j = 0 ; j<trlist.size(); j++){
								Map<String, Object> param = trlist.get(j);
								if(param.isEmpty()){
									break;
								}
								param.remove("rownuma");
								param.remove("rowno");
								if(CommonFun.isNe(param.get("dj_sn"))){
									param.put("dj_sn", ++max_dj_sn);
									param.put("dj_sort", ++max_dj_sort);
									param.put("gzid", params.get("gzid"));
									sd.insertCacheMX((String)pageinfo.get("flag"),(String)pageinfo.get("id"),(String)tables.get(i).get("tableid"),param);
								}else{
									sd.updateCacheMX((String)pageinfo.get("flag"),(String)pageinfo.get("id"),(String)tables.get(i).get("tableid"),param);
								}
							}
						}
					}
				}
				res = sd.executeScheme(schemeRecord.getString("zdysqls").toLowerCase(), params);
				
				//数据重新初始化到页面
				rtn = new BillDao().getCacheHZ((String)params.get("gzid"));
			}
			
			
			
			
			rtn.put("schemeOut", res);
			return rtn;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Record executeDjSelectScheme(Map<String, Object> params) throws Exception {
		try {
			connect("base");
			SchemeDao sd = new SchemeDao();
			Record scheme = sd.getScheme(params, SchemeDao.TABLE_DJSELECT_SCHEME);
			BillDao bd = new BillDao();
			Result djmxrs = bd.listDJMX((String)params.get("system_product_code"), "bill", (String)params.get("fangalx"));
			disconnect();
			
			connect((String)params.get("system_product_code"));
			sd = new SchemeDao();
			Result mx = sd.executeQuery((scheme.getString("d_sql")).toLowerCase(), params);
			Record record = sd.executeQuery(scheme.getString("m_sql").toLowerCase(), params).getResultset(0);
			//存入明细到临时表
			if(djmxrs!=null && djmxrs.getResultset().size()>0){
				Record mr = djmxrs.getResultset(0);
				connect((String)params.get("system_product_code"));
				bd = new BillDao();
				Result mxmeta =  bd.getMXMetaData((String)params.get("flag"), (String)params.get("fangalx"), mr.getString("tableid"));
				int j = 0;
				if("是".equals(scheme.get("emptylyb"))){
					bd.deleteCacheHZ((String) params.get("gzid"));
					Record r = new Record();
					r.put("gzid", params.get("gzid"));
					bd.deleteMX(r ,BillDao.TABLE_NAME_TEMP_MX + params.get("flag") + params.get("fangalx") + 
							(CommonFun.isNe(mr.getString("tableid")) ? "" :mr.getString("tableid")));
				}else{
					Record rmx = bd.queryCacheMX_maxSN((String)params.get("flag"), (String)params.get("fangalx"), mr.getString("tableid"), (String) params.get("gzid"), new HashMap<String,Object>());
					if(rmx !=null && rmx.getInt("max_sn") !=0 ){
						j = rmx.getInt("max_sn")+1;
					}
				}
				for (Record r : mx.getResultset()) {
					Record tmp = new Record();
					for (Record field : mxmeta.getResultset()) {
						tmp.put(field.getString("fdname"), r.get(field.getString("fdname")));
					}
					tmp.put("dj_sn", ++j);
					tmp.put("dj_sort", j);
					tmp.put("gzid", params.get("gzid"));
					bd.insertCacheMX((String)params.get("flag"), (String)params.get("fangalx"), mr.getString("tableid"), tmp);
				}
			}
			return record;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record executeDjSelectSchememx(Map<String, Object> params) throws Exception {
		try {
			connect();
			SchemeDao sd = new SchemeDao();
			Record scheme = sd.getScheme(params, SchemeDao.TABLE_DJSELECT_SCHEME);
			BillDao bd = new BillDao();
			Result djmxrs = bd.listDJMX((String)params.get("system_product_code"), "bill", (String)params.get("fangalx"));
			disconnect();
			
			connect((String)params.get("system_product_code"));
			sd = new SchemeDao();
			List<Map<String,Object>> mxlist = (List<Map<String, Object>>) params.remove("tr");
			Record record = sd.executeQuerySingleRecord((scheme.getString("m_sql")).toLowerCase(), params);
			disconnect();
			//存入明细到临时表
			if(djmxrs!=null && djmxrs.getResultset().size()>0){
				Record mr = djmxrs.getResultset(0);
				connect((String)params.get("system_product_code"));
				bd = new BillDao();
				Result mxmeta =  bd.getMXMetaData((String)params.get("flag"), (String)params.get("fangalx"), mr.getString("tableid"));
				/*disconnect();
				connect("base");*/
				int j = 0;
				if("是".equals(scheme.get("emptylyb"))){
					bd.deleteCacheHZ((String) params.get("gzid"));
					bd.deleteCacheMX((String)params.get("flag"), (String)params.get("fangalx"), mr.getString("tableid"), (String) params.get("gzid"), null, null);
				}else{
					Record rmx = bd.queryCacheMX_maxSN((String)params.get("flag"), (String)params.get("fangalx"), mr.getString("tableid"), (String) params.get("gzid"), new HashMap<String,Object>());
					if(rmx !=null && rmx.getInt("max_sn") !=0 ){
						j = rmx.getInt("max_sn")+1;
					}
				}
				for (Map r : mxlist) {
					Map<String, Object> mxm = (Map<String, Object>) r.get("data");
					Record tmp = new Record();
					for (Record field : mxmeta.getResultset()) {
						tmp.put(field.getString("fdname"), mxm.get(field.getString("fdname")));
					}
					tmp.put("dj_sn", ++j);
					tmp.put("dj_sort", j);
					tmp.put("gzid", params.get("gzid"));
					bd.insertCacheMX((String)params.get("flag"), (String)params.get("fangalx"), mr.getString("tableid"), tmp);
				}
			}
			record.put("_emptylyb_",scheme.get("emptylyb"));
			return record;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result queryDjSelectSchememx(Map<String, Object> params) throws Exception {
		try {
			connect();
			SchemeDao sd = new SchemeDao();
			Record scheme = sd.getScheme(params,SchemeDao.TABLE_DJSELECT_SCHEME);  
			if (scheme == null) {
				return new Result();
			}
			
			disconnect();
			
			connect(scheme.getString("system_product_code"));
			sd = new SchemeDao();
			
			if (params == null) {
				params = new Record();
			}
			if(CommonFun.isNe(params.get("jigid"))){
				params.put("jigid", user().getJigid());
			}
			if (user() != null) {
				params.put("zhiyid", user().getNo());
				params.put("bmid", user().getBmid());
			}
			
			return sd.executeQuery((scheme.getString("d_sql")).toLowerCase(), params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
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
