package cn.crtech.cooperop.application.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.action.TaskAction;
import cn.crtech.cooperop.application.dao.BillDao;
import cn.crtech.cooperop.application.dao.TaskDao;
import cn.crtech.cooperop.bus.cache.SystemMessageTemplate;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.message.MessageSender;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.workflow.workflow;

public class BillService extends BaseService {

	public static Map<String, String> splitPage(String pageid) {
		String[] t = pageid.split("\\.");
		Map<String, String> rtn = new HashMap<String, String>();
		rtn.put("system_product_code", t[0]);
		rtn.put("type", t[1]);
		rtn.put("flag", t[2]);
		rtn.put("id", t[3]);
		return rtn;
	}

	public Map<String, Object> init(String pageid, String djbh) throws Exception {
		Map<String, String> pageinfo = splitPage(pageid);
		Record rtn = new Record();
		String gzid = null;
		String bmid = user().getBmid();
		rtn.put("pageid", pageid);
		rtn.put("zhiyid", user().getId());
		rtn.put("username", user().getName());
		rtn.put("lgnname", user().getNo());
		rtn.put("dep_code", user().getBaseDepCode());
		rtn.put("company_id", user().getCompanyid());
		rtn.put("dep_name", user().getBaseDepName());
		rtn.put("jigid", (CommonFun.isNe(user().getJigid())?"000":user().getJigid()));
		rtn.put("jigname", user().getJigname());
		rtn.put("cjigid", (CommonFun.isNe(user().getJigid())?"000":user().getJigid()));
		rtn.put("rq", CommonFun.formatDate(new Date(), "yyyy-MM-dd"));
		try {
			if (pageinfo.get("type").equals("bill") || pageinfo.get("type").equals("document")) {
				connect("base");
				BillDao bd = new BillDao();
				Record dj = bd.getDJ(pageinfo.get("system_product_code"), pageinfo.get("type"), pageinfo.get("id"));
				Result djmxrs = bd.listDJMX(pageinfo.get("system_product_code"), pageinfo.get("type"), pageinfo.get("id"));
				disconnect();
				
				connect(pageinfo.get("system_product_code"));
				bd = new BillDao();
				gzid = bd.getGZID(pageinfo.get("flag"), bmid);
				rtn.put("gzid", gzid);
				rtn.put("djbh", djbh);
				rtn.put("clientid", "");
				Date now = bd.getDate();
				rtn.put("rq", CommonFun.formatDate(now, "yyyy-MM-dd"));
				rtn.put("kaiprq", rtn.get("rq"));
				rtn.put("riqi", rtn.get("rq"));
				rtn.put("ontime", CommonFun.formatDate(now, "HH:mm"));
				rtn.put("jigname", user().getJigname());
				if (!CommonFun.isNe(djbh)) {
					Record params = new Record();
					params.put("djbh", djbh);
					params.put("company_id", user().getCompanyid());
					params.put("userid", user().getId());
					if(CommonFun.isNe(dj.getString("initdjsql"))){
						throw new Exception("单据设计时，没有写单据再现sql，无法提取单据数据！");
					}
					String s = bd.relpaceParams(dj.getString("initdjsql"), params);
					Record hz = bd.executeQuerySingleRecord(s, params);
					if(hz != null){
						hz.remove("rowno");
						hz.remove("pageid");
						rtn.putAll(hz);
					}else{
						hz = new Record();
					}
					rtn.put("hz", hz);
					//bd.insertCacheHZ(hz);
					for (Record djmx : djmxrs.getResultset()) {
						Result mxmeta =  bd.getMXMetaData(pageinfo.get("flag"), pageinfo.get("id"), djmx.getString("tableid"));
						Result mx = bd.executeQuery(bd.relpaceParams(djmx.getString("initsql"), params), rtn);
						int j = 0;
						for (Record r : mx.getResultset()) {
							Record tmp = new Record();
							for (Record field : mxmeta.getResultset()) {
								tmp.put(field.getString("fdname"), r.get(field.getString("fdname")));
							}
							tmp.put("dj_sn", ++j);
							tmp.put("dj_sort", ++j);
							tmp.put("gzid", gzid);
							bd.insertCacheMX(pageinfo.get("flag"), pageinfo.get("id"), djmx.getString("tableid"), tmp);
						}
					}
//					Record thz = bd.getCacheHZ(gzid);
//					rtn.putAll(thz);
//					rtn.put("hz", thz);
					/*for (Record djmx : djmxrs.getResultset()) {
						rtn.put((CommonFun.isNe(djmx.getString("tableid")) ? "mx" : djmx.getString("tableid")),
								bd.queryCacheMX(pageinfo.get("flag"), pageinfo.get("id"), djmx.getString("tableid"), gzid)
										.getResultset());
					}*/
				} else {
					rtn.put("djbh", pageinfo.get("flag") + "草");
					if(!CommonFun.isNe(dj.getString("initdjsql"))){
						Record params = new Record();
						params.put("djbh", pageinfo.get("flag") + "草");
						params.put("userid", user().getId());
						params.put("username", user().getName());
						params.put("userno", user().getNo());
						params.put("dep_code", user().getBaseDepCode());
						params.put("dep_name", user().getBaseDepName());
						params.put("company_id", user().getCompanyid());
						params.put("jigid", (CommonFun.isNe(user().getJigid())?"000":user().getJigid()));
						String s = bd.relpaceParams(dj.getString("initdjsql"), params);
						Record hz = bd.executeQuerySingleRecord(s, params);
						if(hz != null){
							hz.remove("rowno");
							hz.remove("pageid");
							rtn.putAll(hz);
						}else{
							hz = new Record();
						}
					}
					rtn.put("hz", new Record(rtn));
				}
			}else if(pageinfo.get("type").equals("materials")){
				if (!CommonFun.isNe(djbh)) {
					Map<String, Object> m = new HashMap<String, Object>();
					m.putAll(pageinfo);
					connect("base");
					BillDao bd = new BillDao();
					Record r = bd.getMaterialsTable(m);
					disconnect();
					connect(pageinfo.get("system_product_code"));
					bd = new BillDao();
					r.put("id", djbh);
					Record materials = bd.getMaterials(r);
					if(materials !=null){
						rtn.putAll(materials);
					}
				}
				rtn.put("rq", CommonFun.formatDate(new Date(), "yyyy-MM-dd"));
				rtn.put("ontime", CommonFun.formatDate(new Date(), "HH:mm"));
			}else if(pageinfo.get("type").equals("query") || pageinfo.get("type").equals("chart")){
				connect("base");
				BillDao bd = new BillDao();
				Record dj = bd.getDJ(pageinfo.get("system_product_code"), pageinfo.get("type"), pageinfo.get("id"));
				disconnect();
				connect(pageinfo.get("system_product_code"));
				bd = new BillDao();
				rtn.put("rq", CommonFun.formatDate(new Date(), "yyyy-MM-dd"));
				if(!CommonFun.isNe(dj.getString("initdjsql"))){
					Record params = new Record();
					params.put("userid", user().getId());
					params.put("username", user().getName());
					params.put("userno", user().getNo());
					params.put("company_id", user().getCompanyid());
					params.put("dep_code", user().getBaseDepCode());
					params.put("dep_name", user().getBaseDepName());
					params.put("jigid", (CommonFun.isNe(user().getJigid())?"000":user().getJigid()));
					String s = bd.relpaceParams(dj.getString("initdjsql"), params);
					Record hz = bd.executeQuerySingleRecord(s, params);
					if(hz != null){
						hz.remove("rowno");
						hz.remove("pageid");
						rtn.putAll(hz);
					}else{
						hz = new Record();
					}
				}
			}
			rtn.put("djbs", pageinfo.get("flag"));
			rtn.put("djlx", pageinfo.get("id"));
			rtn.put("uname", user().getName());
			rtn.put("userid", user().getId());
			return rtn;
		} finally {
			disconnect();
		}
	}

	public Map<String, Object> initFromCache(String pageid, String gzid) throws Exception {
		Map<String, String> pageinfo = splitPage(pageid);
		Record rtn = new Record();
		try {
//			connect("base");
//			BillDao bd = new BillDao();
//			Date now = bd.getDate();
//			rtn.put("pageid", pageid);
//			rtn.put("gzid", gzid);
//			rtn.put("djbh", "");
//			rtn.put("clientid", "");
//			rtn.put("djbs", pageinfo.get("flag"));
//			rtn.put("djlx", pageinfo.get("id"));
//			rtn.put("zhiyid", user().getId());
//			rtn.put("username", user().getName());
//			rtn.put("lgnname", user().getNo());
//			rtn.put("rq", CommonFun.formatDate(now, "yyyy-MM-dd"));
//			rtn.put("kaiprq", rtn.get("rq"));
//			rtn.put("riqi", rtn.get("rq"));
//			rtn.put("ontime", CommonFun.formatDate(now, "HH:mm"));
//			rtn.put("jigid", user().getJigid());
//			rtn.put("jigname", user().getJigname());
//			
//			Result djmxrs = bd.listDJMX(pageinfo.get("system_product_code"), pageinfo.get("type"), pageinfo.get("id"));
//			disconnect();

			connect(pageinfo.get("system_product_code"));
			BillDao bd = new BillDao();
			if (pageinfo.get("type").equals("bill")|| pageinfo.get("type").equals("document")) {
				Record thz = bd.getCacheHZ(gzid);
				rtn.putAll(thz);
				rtn.put("hz", thz);
//				for (Record djmx : djmxrs.getResultset()) {
//					rtn.put((CommonFun.isNe(djmx.getString("tableid")) ? "mx" : djmx.getString("tableid")),
//							bd.queryCacheMX(pageinfo.get("flag"), pageinfo.get("id"), djmx.getString("tableid"), gzid)
//									.getResultset());
//				}
			}
			rtn.put("uname", user().getName());
			rtn.put("userid", user().getId());
			return rtn;
		} finally {
			disconnect();
		}
	}
	public Map<String, Object> initFromCacheMX(String p_pageid, String pageid, String tableid
			, String dj_sn, String dj_sort, String gzid) throws Exception {
		Map<String, String> pageinfo = splitPage(p_pageid);
		Map<String, String> pageinfo1 = splitPage(pageid);
		Record rtn = new Record();
		try {
			connect(pageinfo.get("system_product_code"));
			BillDao bd = new BillDao();
			rtn = bd.getCacheMX(pageinfo.get("flag"), pageinfo.get("id"), tableid, gzid, dj_sn, dj_sort);
			rtn.put("djbs", pageinfo1.get("flag"));
			rtn.put("djlx", pageinfo1.get("id"));
			return rtn;
		} finally {
			disconnect();
		}
	}
	public Result queryTable(Map<String, Object> req) throws Exception {
		Map<String, String> pageinfo = splitPage((String) req.get("pageid"));
		try {
			if("bill".equals(pageinfo.get("type")) || "document".equals(pageinfo.get("type"))){
				connect(pageinfo.get("system_product_code"));
				return new BillDao().queryCacheMX(pageinfo.get("flag"), pageinfo.get("id"), (String)req.get("tableid"), (String)req.get("gzid"),req);
			}else if("query".equals(pageinfo.get("type")) || "chart".equals(pageinfo.get("type"))){
				connect(pageinfo.get("base"));
				BillDao bd = new BillDao();
				Record r = bd.getDJMX(pageinfo.get("system_product_code"), pageinfo.get("type"), (String)pageinfo.get("id"), (String)req.get("tableid"));
				disconnect();
				connect(pageinfo.get("system_product_code"));
				req.put("zhiyid", user().getId());
				Result rs = new BillDao().executeQueryScheme(bd.relpaceParams(r.getString("initsql"), req), 
						CommonFun.isNe(r.get("countsql")) ? null : bd.relpaceParams(r.getString("countsql"), req), req);
//				Result rs = new BillDao().executeQueryScheme(r.getString("initsql"), req);
				return rs;
			}
			return new Result();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}  finally {
			disconnect();
		}
	}

	public Map<String, Object> save(Map<String, Object> req) throws Exception {
		Map<String, String> pageinfo = splitPage((String) req.remove("pageid"));
		String flag = "Y";
		String djbh = "";
		try {
			if("bill".equals(pageinfo.get("type")) || "document".equals(pageinfo.get("type"))){
				req.put("zhiyid", user().getId());
				req.put("dep_code", user().getBaseDepCode());
				req.put("dep_name", user().getBaseDepName());
				req.put("lgnname", user().getName());
				req.put("jigname", user().getJigname());
				req.put("system_product_code", pageinfo.get("system_product_code"));
				req.put("jigid", CommonFun.isNe(user().getJigid())?"000":user().getJigid());
				req.remove("clientid");
				List<Map<String, Object>> list = (List<Map<String, Object>>) req.remove("tables");
				String gzid = (String)req.get("gzid");
				
				if("true".equals(req.remove("issave"))){
					connect("base");
					BillDao bd1 = new BillDao();
					//插入clientdj表、修改或新增
					Map<String ,Object> params = new HashMap<String ,Object>();
					params.put("gzid", gzid);
					params.put("zhiyid", user().getId());
					Result res = bd1.queryGZByGZID(params);
					params.put("jigid", CommonFun.isNe(user().getJigid())?"000":user().getJigid());
					params.put("clientid", params.get("zhiyid"));
					params.put("djbhbs", pageinfo.get("flag")+pageinfo.get("id"));
					params.put("gzdesc", req.remove("gzdesc"));
					params.put("lastmodi", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
					if(res!= null && res.getResultset().size()>0){
						bd1.updateGZ(params);
					}else{
						bd1.insertGZ(params);
					}
				}
				
				disconnect();
				connect(pageinfo.get("system_product_code"));
				start();
				BillDao bd = new BillDao();
				if(CommonFun.isNe(req.get("task_id"))){
					Record r = bd.getGZField(gzid,"state");
					Record r1 = bd.getGZField(gzid,"djbh");
					if(r!=null&& r1!=null && ("1".equals(r.get("fieldvalue")) || "0".equals(r.get("fieldvalue")))
							&& r1.getString("fieldvalue").indexOf("草")<0){
						flag = "N";
					}
				}
				bd.updateCacheHZ(gzid, req);
				if(list != null){
					for(int i = 0 ; i<list.size(); i++){
						//bd.deleteCacheMX((String)pageinfo.get("flag"),(String)pageinfo.get("id"),(String)list.get(i).get("tableid"),(String)req.get("gzid"),null,null);
						List<Map<String, Object>> trlist = (List<Map<String, Object>>) list.get(i).get("tr");
						for(int j = 0 ; j<trlist.size(); j++){
							Map<String, Object> param = trlist.get(j);
							if(param.isEmpty()){
								break;
							}
							/*param.put("dj_sn", j+1);
							param.put("dj_sort", j);*/
							param.put("gzid", req.get("gzid"));
							bd.updateCacheMX((String)pageinfo.get("flag"),(String)pageinfo.get("id"),(String)list.get(i).get("tableid"),param);
						}
					}
				}
				commit();
				/*if(!CommonFun.isNe(req.get("task_id"))){
					bd.saveBill((String)req.get("gzid"));
				}*/
			}else if("materials".equals(pageinfo.get("type"))){
				/*req.remove("zhiyid");
				req.remove("lgnname");
				req.remove("jigname");
				req.remove("gzid");
				req.remove("djbh");
				req.remove("clientid");*/
				req.remove("djbs");
				req.remove("djlx");
				/*req.remove("rq");
				req.remove("kaiprq");
				req.remove("riqi");
				req.remove("ontime");
				req.remove("riqi");*/
				connect("base");
				BillDao bd = new BillDao();
				List<Map<String, Object>> list = (List<Map<String, Object>>) req.remove("tables");//资料卡片的表格
				Map<String, Object> p = new HashMap<String, Object>();
				p.putAll(pageinfo);
				Record r = bd.getMaterialsTable(p);
				disconnect();
				req.put("tablename", r.get("tablename"));
				Object tablekey = req.remove(r.get("tablekey"));
				connect(pageinfo.get("system_product_code"));
				start();
				bd = new BillDao();
				if(CommonFun.isNe(tablekey)){
					djbh = bd.getMAXBH(r.getString("id"), CommonFun.isNe(user().getJigid())?"000":user().getJigid());
					req.put(r.getString("tablekey"), djbh);
					bd.saveMaterials(req);
				}else{
					djbh = (String)tablekey;
					req.put("id",tablekey);
					req.put("idname",r.get("tablekey"));
					bd.updateMaterials(req);
				}
				commit();
			}
			Map<String, Object> r = new HashMap<String, Object>();
			r.put("flag", flag);
			r.put("djbh", djbh);
			return r;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void modifyFunction(Map<String, Object> req) throws Exception {
		Map<String, String> pageinfo = splitPage((String) req.get("pageid"));
		try {
			connect(pageinfo.get("system_product_code"));
			start();
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public String submit(Map<String, Object> req) throws Exception {
		String pageid = (String) req.get("pageid");
		Map<String, String> pageinfo = splitPage(pageid);		
		String task_id = (String) req.get("task_id");
//		String audited = (String) req.remove("audited");
//		String advice = (String) req.remove("advice");
		String gzid = (String)req.get("gzid");
		req.put("state","0");
		Map<String, Object> m = save(req);
		/*if("N".equals(m.get("flag"))){
			return "-1";
		}*/
		String djbh="";
		try {
			connect(pageinfo.get("system_product_code"));
			if("bill".equals(pageinfo.get("type")) || "document".equals(pageinfo.get("type"))){
				BillDao bd = new BillDao();
				djbh = bd.saveBill(gzid);
			}else if("materials".equals(pageinfo.get("type"))){
				djbh = (String)m.get("djbh");
			}
			//启动流程
			
		} catch (Exception ex) {
			//rollback();
			throw ex;
		} finally {
			disconnect();
		}
		try {
			if (CommonFun.isNe(task_id)) {
				workflow.start(pageinfo.get("system_product_code"), pageid, djbh, (String) user().getBaseDepCode());
				//发送消息, 具体是否发送在方法中有判断
				req.put("djbh", djbh);
				if(SystemMessageTemplate.needSendM(pageinfo.get("system_product_code")+pageid)){
					MessageSender.sendBillMessage(pageinfo.get("system_product_code"), pageid, req);
				}
			} else {
				/*disconnect();
				connect("base");
				TaskDao td = new TaskDao();
				Record r = td.get(task_id);
				Result res = td.getTaskActors(task_id);
				disconnect();
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("message", advice);
				param.put("type", 2);
				param.put("task_id", task_id);
				param.put("message", advice);
				param.put("order_id", r.get("order_id"));
				param.put("hchry", res.getResultset());
				new SuggestionsService().save(param);
				connect(pageinfo.get("system_product_code"));
				workflow.next(task_id, audited, advice);*/
			}
		} catch (Exception ex) {
			throw ex;
		}
		return djbh;
	}
	public String approval(Map<String, Object> req) throws Exception {
		String pageid = (String) req.get("pageid");
		Map<String, String> pageinfo = splitPage(pageid);		
		String task_id = (String) req.get("task_id");
		String audited = (String) req.get("audited");
		String advice = (String) req.remove("advice");

		String gzid = (String)req.get("gzid");
		req.remove("rq");
		req.remove("username");
		req.remove("zhiyid");
		List<Record> routes = null;
		Object nextnode = req.remove("nextnode");
		if(!CommonFun.isNe(nextnode)){
			routes = TaskAction.listTaskRoutes(req);
		}
		save(req);
		Map<String, Object> map = new HashMap<String, Object>();
		String djbh = null;
		try {
			connect("base");
			TaskDao td = new TaskDao();
			Record r = td.get(task_id);
			
			map.put("task_id", task_id);
			if(r != null){
				map.put("order_id", r.get("order_id"));
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("task_id", task_id);
				param.put("order_id", r.get("order_id"));
				if("N".equals(audited)){//流程驳回给所有经办人发送消息
					param.put("message", CommonFun.isNe(advice)?"驳回":advice);
					param.put("type", 3);
					Result res = td.getHistTaskActors(r.getString("order_id"));
					param.put("hchry", res.getResultset());
					new SuggestionsService().save(param);
				}else {//流程通过给发起人发送消息
					/*Record order = td.getOrder(r.getString("order_id"), null);
					param.put("message", CommonFun.isNe(advice)?"通过":advice);
					param.put("type", 2);
					List<Record> list = new ArrayList<Record>();
					Record fqr = new Record();
					fqr.put("actor_id", order.get("creator"));
					list.add(fqr);
					param.put("hchry", list);
					new SuggestionsService().save(param);*/
				}
				
			}
			disconnect();
			
			connect(pageinfo.get("system_product_code"));
			BillDao bd = new BillDao();
			djbh = bd.saveBill(gzid);
			
			map.put("gzid", gzid);
			map.put("djbh", djbh);
			
		} catch (Exception ex) {
			//rollback();
			throw ex;
		} finally {
			disconnect();
		}
		try {
			if(!CommonFun.isNe(nextnode)){
				Record target_node = new Record();
				for(Map<String, Object> route : routes){
					if(nextnode.equals(route.get("tonode"))){
						target_node = (Record) route.get("target_node");
					}
				}
				workflow.next(task_id, audited, advice, (String)nextnode, (String[])target_node.get("processors"), (String[])target_node.get("assistants"), map);
			}else{
				workflow.next(task_id, audited, advice, map);
			}
			return djbh;
		} catch (Exception e) {
			throw e;
		}
	}
	public String resubmit(Map<String, Object> req) throws Exception {
		String pageid = (String) req.get("pageid");
		Map<String, String> pageinfo = splitPage(pageid);		
		String gzid = (String)req.get("gzid");
		req.put("state", "0");
		Map<String, Object> m = save(req);
		/*if("N".equals(m.get("flag"))){
			return "-1";
		}*/
		String djbh="";
		try {
			connect(pageinfo.get("system_product_code"));
			BillDao bd = new BillDao();
			//String djbh = bd.saveBill(gzid);
			if("bill".equals(pageinfo.get("type")) || "document".equals(pageinfo.get("type"))){
				djbh = bd.saveBill(gzid);
			}else if("materials".equals(pageinfo.get("type"))){
				djbh = (String)m.get("djbh");
			}
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
		try {
			//启动流程
			workflow.start(pageinfo.get("system_product_code"), pageid, djbh, user().getBaseDepCode());
			return djbh;
		} catch (Exception e) {
			throw e;
		}
	}
	public String backprocess(Map<String, Object> req) throws Exception {
		String pageid = (String) req.get("pageid");
		Map<String, String> pageinfo = splitPage(pageid);		
		//String advice = (String) req.remove("advice");
		String gzid = (String)req.get("gzid");
		if("tht草".equals(req.get("djbh"))){
			return "0";
		}
		String djbh = (String)req.get("djbh");
		String task_id = null;
		try {
			connect("base");
			Result r = new TaskDao().getTask(djbh);
			if(r!=null){
				task_id = r.getResultset(0).getString("id");
			}
			disconnect();
			
			connect(pageinfo.get("system_product_code"));
			BillDao bd = new BillDao();
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("state", "-9");
			m.put("djbh", djbh);
			m.put("gzid", gzid);
			m.put("jigid", CommonFun.isNe(user().getJigid())?"000":user().getJigid());
			m.put("task_id", task_id);//存入,不然不会转存修改state
			bd.updateCacheHZ(gzid, m);
			
			bd.saveBill(gzid);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
		try {
			//启动流程
			if (!CommonFun.isNe(task_id)) {
				workflow.back(task_id, "用户自行撤销");
				return "1";
			} else {
				return "0";
			}
		} catch (Exception e) {
			throw e;
		}
	}
	public void reaudit(Map<String, Object> req) throws Exception {
		//String pageid = (String) req.get("pageid");
		try {
			connect("base");
			workflow.reaudit((String)req.get("task_id"), "撤回");
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void approvalnotsave(Map<String, Object> req) throws Exception {
		String pageid = (String) req.get("pageid");
		Map<String, String> pageinfo = splitPage(pageid);		
		String task_id = (String) req.get("task_id");
		String audited = (String) req.remove("audited");
		String advice = (String) req.remove("advice");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			connect("base");
			TaskDao td = new TaskDao();
			Record r = td.get(task_id);
			map.put("task_id", task_id);
			if(r != null){
				map.put("order_id", r.get("order_id"));
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("task_id", task_id);
				param.put("order_id", r.get("order_id"));
				if("N".equals(audited)){//流程驳回给所有经办人发送消息
					param.put("message", CommonFun.isNe(advice)?"驳回":advice);
					param.put("type", 3);
					Result res = td.getHistTaskActors(r.getString("order_id"));
					param.put("hchry", res.getResultset());
					new SuggestionsService().save(param);
				}else {//流程通过给发起人发送消息
					/*Record order = td.getOrder(r.getString("order_id"), null);
					param.put("message", CommonFun.isNe(advice)?"通过":advice);
					param.put("type", 2);
					List<Record> list = new ArrayList<Record>();
					Record fqr = new Record();
					fqr.put("actor_id", order.get("creator"));
					list.add(fqr);
					param.put("hchry", list);
					new SuggestionsService().save(param);*/
				}
				
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
		try {
			map.put("djbh", req.get("djbh"));
			map.put("gzid", req.remove("gzid"));
			workflow.next(task_id, audited, advice, map);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void addMX(Map<String, Object> req) throws Exception {
		Map<String, String> pageinfo = splitPage((String) req.remove("pageid"));
		try {
			connect(pageinfo.get("system_product_code"));
			start();
			BillDao bd = new BillDao();
			if(CommonFun.isNe(req.get("dj_sn"))){
				Record r = bd.getMXSnAndSort((String)pageinfo.get("flag"),(String)pageinfo.get("id"),(String)req.get("tableid"),(String)req.get("gzid"));
				req.put("dj_sn", CommonFun.isNe(r.get("dj_sn"))?1:r.get("dj_sn"));
				req.put("dj_sort", CommonFun.isNe(r.get("dj_sort"))?1:r.get("dj_sort"));
				bd.insertCacheMX((String)pageinfo.get("flag"),(String)pageinfo.get("id"),(String)req.remove("tableid"),req);
			}else{
				bd.updateCacheMX((String)pageinfo.get("flag"),(String)pageinfo.get("id"),(String)req.remove("tableid"),req);
			}
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void updateMX(Map<String, Object> req) throws Exception {
		Map<String, String> pageinfo = splitPage((String) req.remove("pageid"));
		try {
			connect(pageinfo.get("system_product_code"));
			start();
			BillDao bd = new BillDao();
			bd.updateCacheMX((String)pageinfo.get("flag"),(String)pageinfo.get("id"),(String)req.remove("tableid"),req);
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void deleteMX(Map<String, Object> req) throws Exception {
		Map<String, String> pageinfo = splitPage((String) req.remove("pageid"));
		try {
			if("bill".equals(pageinfo.get("type"))){
				connect(pageinfo.get("system_product_code"));
				start();
				BillDao bd = new BillDao();
				List<Map<String, Object>> trlist = (List<Map<String, Object>>) req.get("tr");
				for(int j = 0 ; j<trlist.size(); j++){
					Map<String, Object> param = trlist.get(j);
					if(param.isEmpty()){
						break;
					}
					bd.deleteCacheMX((String)pageinfo.get("flag"),(String)pageinfo.get("id"),(String)req.get("tableid"),(String)param.get("gzid"),(String)param.get("dj_sn"),(String)param.get("dj_sort"));
				}
				commit();
			}else if("query".equals(pageinfo.get("type"))){//查询中删除
				if(!CommonFun.isNe(req.get("djselect"))){
					Map<String, String> page = splitPage((String)req.get("djselect"));
					if(page.get("type").equals("materials")){//查询中删除卡片数据
						connect("base");
						BillDao bd = new BillDao();
						Map<String, Object> p = new HashMap<String, Object>();
						p.putAll(page);
						Record r = bd.getMaterialsTable(p);
						String tabname = r.getString("tablename");
						disconnect();
						
						connect(page.get("system_product_code"));
						start();
						
						List<Map<String, Object>> trlist = (List<Map<String, Object>>) req.get("tr");
						bd = new BillDao();
						for(int j = 0 ; j<trlist.size(); j++){
							Map<String, Object> param = trlist.get(j);
							if(param.isEmpty() || CommonFun.isNe(param.get(req.get("tablekey")))){//主键字段值为空不能删除
								break;
							}
							Map<String, Object> m = new HashMap<String, Object>();
							m.put((String)req.get("tablekey"), param.get(req.get("tablekey")));
							//m.put("notdelete", req.get("notdelete"));
							if("Y".equals(req.get("notdelete"))){
								Map<String, Object> p1 = new HashMap<String, Object>();
								p1.put("beactive", "删");
								String tk = (String) param.get(req.get("tablekey"));
								tk = tk.replace(page.get("id"), "DEL");
								p1.put((String)req.get("tablekey"), tk);
								bd.deleteMX1(p1, m, tabname);
							}else{
								bd.deleteMX(m, tabname);
							}
						}
						commit();
					}
				}
			}
			
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Result querygz(Map<String, Object> req) throws Exception {
		try {
			connect();
			return new BillDao().querygz(req);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public void importExcels(Map<String, Object> req) throws Exception {
		try {
			Map<String, String> pageinfo = splitPage((String) req.remove("pageid"));
			List<Record> list = (List<Record>) req.get("sheet1");
			Record fileds = list.get(1);
			if("Y".equals(req.get("is_mx"))){
				connect(pageinfo.get("system_product_code"));
				start();
				BillDao bd = new BillDao();
				Object gzid = req.get("gzid");
				String tableid = (String)req.get("tableid");
				Record r = bd.getMXSnAndSort((String)pageinfo.get("flag"),(String)pageinfo.get("id"), tableid,(String)req.get("gzid"));
				for (int i = 2, dj_sn = r.getInt("dj_sn"), dj_sort = r.getInt("dj_sort"); i < list.size(); i++, dj_sn++,dj_sort++) {
					Map<String, Object> parmas = new HashMap<String, Object>();
					parmas.put("dj_sn", dj_sn);
					parmas.put("dj_sort", dj_sort);
					parmas.put("gzid", gzid);
					Iterator<String> keys = fileds.keySet().iterator();
					while (keys.hasNext()) {
						String key = (String) keys.next();
						if (("rowno".equals(key))) {
							continue;
						}
						if ((fileds.get(key) == null) || ("".equals(fileds.get(key)))) {
							continue;
						}
						parmas.put(fileds.getString(key), list.get(i).get(key));
					}
					bd.insertCacheMX((String)pageinfo.get("flag"),(String)pageinfo.get("id"), tableid, parmas);
				}
				commit();
			}else{
				connect("base");
				BillDao bd = new BillDao();
				Map<String, Object> p = new HashMap<String, Object>();
				p.putAll(pageinfo);
				Record r = bd.getMaterialsTable(p);
				disconnect();
				connect(pageinfo.get("system_product_code"));
				start();
				bd = new BillDao();
				for (int i = 2 ; i < list.size(); i++) {
					Map<String, Object> params = new HashMap<String, Object>();
					String tablekey = bd.getMAXBH(r.getString("id"), CommonFun.isNe(user().getJigid())?"000":user().getJigid());
					params.put(r.getString("tablekey"), tablekey);
					params.put("tablename", r.get("tablename"));
					Iterator<String> keys = fileds.keySet().iterator();
					while (keys.hasNext()) {
						String key = (String) keys.next();
						if (("rowno".equals(key))) {
							continue;
						}
						if ((fileds.get(key) == null) || ("".equals(fileds.get(key)))) {
							continue;
						}
						params.put(fileds.getString(key), list.get(i).get(key));
					}
					bd.saveMaterials(params);
				}
				commit();
			}
			
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> getPYM(Map<String, Object> req) throws Exception {
		try {
			connect();
			req.put("pym", new BillDao().getPYM(req).get("pym"));
			return req;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public String submit_v12(Map<String, Object> req) throws Exception {
		String pageid = (String) req.get("pageid");
		Map<String, String> pageinfo = splitPage(pageid);	
		String djbh = (String) req.get("djbh");
		try {
			workflow.start(pageinfo.get("system_product_code"), pageid, djbh, user().getBaseDepCode());
			return djbh;
		} catch (Exception ex) {
			throw ex;
		}
	}
}
