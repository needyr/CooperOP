package cn.crtech.cooperop.ipc.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jsoup.Jsoup;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.AutoCommonDao;
import cn.crtech.cooperop.hospital_common.dao.HisInPatientDao;
import cn.crtech.cooperop.hospital_common.dao.additional.ShuoMSDao;
import cn.crtech.cooperop.ipc.dao.AutoAuditDao;
import cn.crtech.cooperop.ipc.dao.AutoAuditFlowDao;
import cn.crtech.cooperop.ipc.dao.AutoAuditOrdersDao;
import cn.crtech.cooperop.ipc.dao.CheckDataParmsDao;
import cn.crtech.cooperop.ipc.dao.CheckResultDao;
import cn.crtech.cooperop.ipc.dao.CheckResultInfoDao;
import cn.crtech.cooperop.ipc.dao.DrugSmsDao;
import cn.crtech.cooperop.ipc.dao.PharmacistCommentDao;
import cn.crtech.cooperop.ipc.schedule.SyncInstruction;
import cn.crtech.cooperop.ipc.util.JavaScriptUtil;
import cn.crtech.precheck.ipc.dao.DataDao;
import cn.crtech.precheck.ipc.huiyaotong.EngineDrug;
import cn.crtech.precheck.ipc.ws.client.Client;
import cn.crtech.precheck.server.TestHYTAudit;
import cn.crtech.ylz.ylz;

/**审查结果*/
public class AuditResultService extends BaseService {
	
	
	/**
	 * 审查结果页面显示
	 * @param params Map
	 * @return Map
	 * @throws Exception
	 */
	public Map<String, Object> detail(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> ret = new HashMap<String, Object>();
			//根据common_id去查询主表的id
			AutoAuditDao autodao=new AutoAuditDao();
			if(!CommonFun.isNe(params.get("id"))){
				Record record=autodao.getAuto_audit_id_view((String)params.get("id"));
				if(!CommonFun.isNe(record)) {
					String id = (String)record.get("id");
					ret.putAll(autodao.get_view(id));
					params.put("auto_audit_id", id);
					CheckResultInfoDao check = new CheckResultInfoDao();
					ret.put("tnum", new CheckResultDao().queryTypeNum_v(params).getResultset());
					ret.put("item_info", check.queryCheckResultInfo_v(params).getResultset());
					ret.put("orders", new AutoAuditOrdersDao().queryOrders_v(params).getResultset());
					ret.put("ipc_auto_id", id);
					//ret.put("diagnosis", new AutoAuditOrdersDao().queryDiagnosis(params).getResultset());
					ret.put("state", record.get("state"));
				}
			}
			return ret;
		}catch(Exception e){
			throw e;
		} finally {
			disconnect();
		}
	}
	
	
	/**
	 * 审查结果页面显示{视图版本}
	 * @param params Map
	 * @return Map
	 * @throws Exception
	 */
	public Map<String, Object> detail_view(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> ret = new HashMap<String, Object>();
			//根据common_id去查询主表的id
			AutoAuditDao autodao=new AutoAuditDao();
			if(!CommonFun.isNe(params.get("id"))){
				Record record=autodao.getAuto_audit_id_view((String)params.get("id"));
				if(!CommonFun.isNe(record)) {
					String id = (String)record.get("id");
					ret.putAll(autodao.get(id));
					params.put("auto_audit_id", id);
					CheckResultInfoDao check = new CheckResultInfoDao();
					ret.put("ipca", record);
					ret.put("tnum", new CheckResultDao().queryTypeNum_view(params).getResultset());
					ret.put("item_info", check.queryCheckResultInfo_view(params).getResultset());
					ret.put("orders", new AutoAuditOrdersDao().queryOrders_view(params).getResultset());
					ret.put("ipc_auto_id", id);
					//ret.put("diagnosis", new AutoAuditOrdersDao().queryDiagnosis(params).getResultset());
					ret.put("state", record.get("state"));
					ret.put("d_type", record.get("d_type"));
				}
			}
			return ret;
		}catch(Exception e){
			throw e;
		} finally {
			disconnect();
		}
	}
	
	/**
	 * 查找业务流水表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> show_ywlsb(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> ret = new HashMap<String, Object>();
			if(!CommonFun.isNe(params.get("auto_audit_id"))){
				String id = (String)params.get("auto_audit_id");
				Record getpatient = new HisInPatientDao().getpatientByYwlsb(params);
				params.put("patient_id",getpatient.get("patient_id"));
				params.put("visit_id",getpatient.get("visit_id"));
				ret.put("auto",new AutoAuditDao().getYwlsb(id));
				params.put("auto_audit_id", id);
				CheckResultInfoDao check = new CheckResultInfoDao();
				Map<String, Object> idmap = new HashMap<String, Object>();
				idmap.put("auto_audit_id", id);
				ret.put("items", new CheckResultDao().queryTypeNumByYwlsb(params).getResultset());
				ret.put("item_info", check.queryCheckResultInfoByYwlsb(params).getResultset());
				ret.put("orders", new AutoAuditOrdersDao().queryOrdersByYwlsb(params).getResultset());
				ret.put("ipc_auto_id", id);
				ret.put("diagnosis", new AutoAuditOrdersDao().queryDiagnosisAll(params).getResultset());
			}
			Result  commonts = new PharmacistCommentDao().queryByAuditId(params);
			if(!CommonFun.isNe(commonts)){
				ret.put("comments", commonts.getResultset());
			}
			Result ordersgroup = new AutoAuditOrdersDao().queryOrdersGroup(params);
			if(!CommonFun.isNe(ordersgroup)){
				ret.put("ordersgroup", ordersgroup.getResultset());
			}
			Result comment_orders = new AutoAuditDao().getyzsAll(params);
			if(!CommonFun.isNe(comment_orders)){
				ret.put("comment_orders", comment_orders.getResultset());
			}
			return ret;
		}catch(Exception e){
			throw e;
		} finally {
			disconnect();
		}
	}
	
	/**
	 * 审查结果页面显示
	 * @param cmid String
	 * @return Record
	 * @throws Exception
	 */
	public Record getIdByCmid(String cmid) throws Exception {
		try {
			connect("ipc");
			return new AutoAuditDao().getAuto_audit_id(cmid);
		}catch(Exception e){
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> queryComment(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Record r = new Record();
			Map<String, Object> ret = new HashMap<String, Object>();
			r.put("auto_audit_id", params.get("id"));
			Result comment_orders = new AutoAuditDao().getyzsAll(r);
			ret.put("comments", new PharmacistCommentDao().queryByAuditId(r).getResultset());
			ret.put("ordersgroup", new AutoAuditOrdersDao().queryOrdersGroup(r).getResultset());
			ret.put("comment_orders", comment_orders.getResultset());
			return ret;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	
	/*public void saveResult(Map<String, Object> params) throws Exception {
		try {
			
		} catch(Exception e) {
			rollback();
			throw e;
		} 
	}*/
	
	/**
	 * 点赞 踩
	 * @param params Map
	 * @return int
	 * @throws Exception
	 */
	public int goodlike(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			int a = new CheckResultInfoDao().update(params);
			return a;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	/**
	 * 审查结果页面详情
	 * @param params Map
	 * @return Map
	 * @throws Exception
	 */
	public Map<String, Object> particulars(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> ret = new HashMap<String, Object>();
			Map<String, Object> censorship=new HashMap<String, Object>();
			censorship.put("sort_id", params.remove("sort_id"));
			AutoAuditDao autoaudit= new AutoAuditDao();
			ret.put("orders", autoaudit.getyzs(params).getResultset());
			ret.put("drugs", new AutoAuditOrdersDao().queryOrders(params).getResultset());
			ret.put("sort", new CheckResultInfoDao().getsort(params));
			ret.put("patient", autoaudit.getpatient1((String)params.get("auto_audit_id")));
			ret.put("censorship", autoaudit.censorship(censorship));
			//ret.put("queryLevel", new AutoAuditDao().queryLevel().getResultset());
			return ret;
		} finally {
			disconnect();
		}
	}
	
	/**
	 * 审查结果页面详情-----视图版
	 * @param params Map
	 * @return Map
	 * @throws Exception
	 */
	public Map<String, Object> particulars_lishi(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> ret = new HashMap<String, Object>();
			Map<String, Object> censorship=new HashMap<String, Object>();
			censorship.put("sort_id", params.remove("sort_id"));
			AutoAuditDao autoaudit= new AutoAuditDao();
			ret.put("orders", autoaudit.getyzs_view(params).getResultset());
			ret.put("drugs", new AutoAuditOrdersDao().queryOrders_v(params).getResultset());
			ret.put("sort", new CheckResultInfoDao().getsort_view(params));
			ret.put("patient", autoaudit.getpatient1_view((String)params.get("auto_audit_id")));
			ret.put("censorship", autoaudit.censorship(censorship));
			//ret.put("queryLevel", new AutoAuditDao().queryLevel().getResultset());
			return ret;
		} finally {
			disconnect();
		}
	}
	
	/**
	 * 查看医嘱
	 * @param params Map
	 * @return Map
	 * @throws Exception
	 */
	public Map<String, Object> druglist(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> ret = new HashMap<String, Object>();
			AutoAuditDao autoaudit= new AutoAuditDao();
//				ret.put("orders", autoaudit.getyzs(params).getResultset());
				ret.put("orders", new AutoAuditOrdersDao().queryDrugList(params).getResultset());
			return ret;
		} finally {
			disconnect();
		}
	}
	
	public Result druglist2(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new AutoAuditOrdersDao().queryDrugList(params);
		} finally {
			disconnect();
		}
	}
	
	public String auditAndSave_zy(String xmlStr, Map<String, Object> map) throws Exception {
		try {
			connect("ipc");
			Document doc = DocumentHelper.parseText(xmlStr);
	        Element root = doc.getRootElement();
	        List<Element> items = root.element("serviceResults").element("services").elements("item");
	        //TODO 调用自定义审查
	        String zdy_result = "Y";
	        if(items.size() > 0){
	        	Object auto_audit_id = map.get("auto_audit_id");
	        	CheckResultDao crd = new CheckResultDao();
	        	CheckResultInfoDao crid = new CheckResultInfoDao();
	        	for(Element item: items){
	        		Map<String, Object> resultMap = new HashMap<String, Object>();
	        		resultMap.put("auto_audit_id", auto_audit_id);
	        		resultMap.put("keyword", item.elementText("keyword"));
	        		resultMap.put("keyTitle", item.elementText("keyTitle"));
	        		resultMap.put("type", "1");
	        		String rid = crd.insert(resultMap);
	        		List<Element> itemInfo = item.element("results").elements("result");
	        		for(Element info : itemInfo){
	        			Map<String, Object> InfoMap = new HashMap<String, Object>();
	        			InfoMap.put("auto_audit_id", auto_audit_id);
	        			InfoMap.put("parent_id", rid);
	        			InfoMap.put("level", info.elementText("level"));
	        			InfoMap.put("warning", info.elementText("warning"));
	        			InfoMap.put("reference", info.elementText("reference"));
	        			InfoMap.put("description", info.elementText("description"));
	        			List<Element> ords = info.element("relateOrders").elements("item");
	        			String order_ids = "";
	        			for(Element o : ords){
	        				order_ids = order_ids+","+ o.elementText("id");
	        			}
	        			InfoMap.put("order_id", order_ids.substring(1));
	        			crid.insert(InfoMap);
	        		}
	        	}
	        	return "N";
	        }else{
	        	return zdy_result;
	        }
		} finally {
			disconnect();
		}
	}
	
	public int audit_zdy_procBefore(Map<String, Object> map) throws Exception {
		try {
			connect("hospital_common");
			return new DataDao().audit_zdy_procBefore(map);
		}catch(Exception e){
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int audit_sys_procBefore(Map<String, Object> map) throws Exception {
		try {
			connect("hospital_common");
			return new DataDao().audit_sys_procBefore(map);
		}catch(Exception e){
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public String audit_zdy(Map<String, Object> map, String proc) throws Exception {
		try {
			connect("hospital_common");
			DataDao  datadao= new DataDao();
			Record done = datadao.audit_zdy_proc(map, proc);
			return "Y";
		}catch(Exception e){
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public String pr_audit_zdy(Map<String, Object> map, String proc) throws Exception {
		try {
			connect("hospital_common");
			DataDao  datadao= new DataDao();
			Record done = datadao.audit_zdy_proc(map, proc);
			return done.getString("result");
		}catch(Exception e){
			throw e;
		}finally {
			disconnect();
		}
	}
	
	// {已被hytWS接口方法替代}
	@Deprecated
	public String auditAndSave_hyt(String xmlStr, Map<String, Object> map) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("update_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
			Object auto_audit_id = map.get("auto_audit_id");
	        p.put("id", auto_audit_id);
	        p.put("cost_time", (long)map.remove("cost_time"));
	        p.put("thirdt_response", xmlStr);
	        //new CheckDataParmsDao().updateTResp(p);
			Document doc = DocumentHelper.parseText(xmlStr);
	        Element root = doc.getRootElement();
	        if(Integer.parseInt(root.elementText("RetMessage")) < 0){
	        	//Client.sendHLYYError("admin_jq", new HashMap<String, Object>());
	        	return "HL_F";
	        }
	        List<Element> items = root.element("RetContent").elements("RetContent");
	        CheckResultDao crd = new CheckResultDao();
	        if(items.size() > 0){
	        	CheckResultInfoDao crid = new CheckResultInfoDao();
	        	Map<String, String> hassave = new HashMap<String, String>();
	        	for(Element item: items){
	        		String rid;
	        		if(CommonFun.isNe(hassave.get(item.elementText("Type")))){
	        			Map<String, Object> resultMap = new HashMap<String, Object>();
		        		resultMap.put("auto_audit_id", auto_audit_id);
		        		resultMap.put("keyword", item.elementText("Type"));
		        		resultMap.put("keyTitle", item.elementText("keyTitle"));
		        		resultMap.put("type", "1");
		        		rid = crd.insert(resultMap);
		        		hassave.put(item.elementText("Type"), rid);
	        		}else{
	        			rid = hassave.get(item.elementText("Type"));
	        		}
	        		Map<String, Object> InfoMap = new HashMap<String, Object>();
        			InfoMap.put("auto_audit_id", auto_audit_id);
        			InfoMap.put("parent_id", rid);
        			InfoMap.put("level", item.elementText("Severity"));
        			InfoMap.put("warning", item.elementText("Holdup"));
        			InfoMap.put("reference", item.elementText("Reference"));
        			InfoMap.put("description", item.elementText("Message"));
        			String order_ids = item.elementText("Precodeitem1");
        			if(!CommonFun.isNe(item.elementText("Precodeitem2"))){
        				order_ids = order_ids+","+item.elementText("Precodeitem2");
        			}
        			
        			InfoMap.put("order_id", order_ids.replace("~", ","));
        			crid.insert(InfoMap);
	        	}
	        }
	        //saveResult(idmap);
	        return "Y"; 
		}catch(Exception exc) {
			exc.printStackTrace();
			throw exc;
		} finally {
			disconnect();
		}
	}
	
	/**
	 * 
	 * @author yan
	 * @time 2018年4月11日
	 * @function 定时30天执行（获取药品说明书的操作-删除所有药品说明书数据和文件重新获取）
	 */
	/*public  void timerget30() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				sysnDrugDetail();
			}
		},0,1000*60*60*24*30);
	}*/
	
	
	/**
	 * 
	 * @author yan @time:2018年4月8日
	 * @function 根据药品编号获取药品说明书，并存到数据库
	 */
	public void sysnDrugDetail(){
		try {
			connect("ipc");
			//获取his对应的标准库的药品信息Result
			DrugSmsDao drug=new DrugSmsDao();
			List<Record> drugcodes = new ArrayList<Record>();
			try {
				drugcodes=drug.query(new HashMap<String, Object>()).getResultset();
				drug.deleteAllTemp(new HashMap<String, Object>());
				drug.updateHaveUrl(new HashMap<String, Object>());//去除有说明书的标志
			}catch (Exception e) {
				e.getStackTrace();
			} finally {
				disconnect();
			}
			String url = new String();
			//循环药品for(Result)
			for (Record code : drugcodes) {
			try {
				//根据每个药品获取三方的药品说明书地址   new EngineHYT().execMethod("药品code");
				String string_code = code.getString("sys_drug_code");
				string_code = string_code.replaceAll("\\|", "_").replaceAll("\\;", "_")
				.replaceAll("\\/", "_").replaceAll("\\?", "_")
				.replaceAll("\\:", "_").replaceAll("\\@", "_")
				.replaceAll("\\&", "_").replaceAll("\\=", "_")
				.replaceAll("\\+", "_").replaceAll("\\$", "_")
				.replaceAll("\\,", "_").replaceAll("\\#", "_").replaceAll("\\*", "_");
				url=new EngineDrug().execMethod(code.getString("sys_drug_code"));
				//url = "http://"+SystemConfig.getSystemConfigValue("hospital_common", "url_ipc")+"/DrugInstructionFiles/"+string_code+".html";
				//根据地址获取页面内容
				String body=geturlbody(url);
				body=body.replaceAll("<loml", "<loml>");
				body=body.replaceAll("<l", "<l>");
				//log.debug(body);
				//保存页面内容数据
				Map<String,Object> map = JavaScriptUtil.getFieldsValue(body);
				long img_count = 0;
				boolean check = false;
				Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
				while (iterator.hasNext()) {
					if (map.get(iterator.next().getKey()) != null) {
						check = true;
						break;
					}
				}
				if(check) {
					//添加药品说明书图片的解析
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						try {
							String key = entry.getKey();
							Object object = entry.getValue();
							if (!CommonFun.isNe(object)) {
								String value = object.toString();
								String regxpForImgTag = "<\\s*img\\s*([^>]*)\\s*/?\\s*>";
								Pattern p = Pattern.compile(regxpForImgTag, Pattern.MULTILINE);
								Matcher m = p.matcher(value);
								String face = "";
								String add_name ="";
								String houz = "";
								while(m.find()) {
									String data = m.group(0).trim();//img标签<img src="111.jqp" />
									String regxpForImgSrc = "(src|SRC)=(\\\"|\\')(.*?)(\\\"|\\')";
									Pattern pattern = Pattern.compile(regxpForImgSrc, Pattern.MULTILINE);
									Matcher matcher = pattern.matcher(data);
									while (matcher.find()) {
										img_count += 1;
										String[] trim = matcher.group(0).trim().split("\"");
										String src = trim[trim.length-1];
										//src有两种,一种为"111.jpg",一种为"images\drugimages\111.jpg"
										int one = src.lastIndexOf("/");
										if (one >= 0 ) {
											String add_name_all = src.substring(one+1, src.length());
											face = src.substring(0, one+1);//前缀
											int lastIndexOf = add_name_all.lastIndexOf(".");
											add_name = add_name_all.substring(0, lastIndexOf);//文件名
											houz = add_name_all.substring(lastIndexOf+1, add_name_all.length());//后缀
										}else {
											String add_name_all = src;
											int lastIndexOf = add_name_all.lastIndexOf(".");
											add_name = add_name_all.substring(0, lastIndexOf);//文件名
											houz = add_name_all.substring(lastIndexOf+1, add_name_all.length());//后缀
										}
										value = value.replace(src, face+"sys" + string_code + "p" + img_count +"."+houz);
										
										//拷贝文件,更改文件名称:  sys+标准库code(|替换为K)+p+递增数字(从1开始)
										URL resource = Thread.currentThread().getContextClassLoader().getResource("/");
										String courseFile = resource.getPath().substring(resource.getPath().lastIndexOf(":")-1, resource.getPath().indexOf("/WEB-INF/")+9).replace("/", "\\");
										String shuoms_img_address = SystemConfig.getSystemConfigValue("ipc", "shuoms_img_address", "apps\\hospital_common\\resource\\drugimg");
										String file_add = courseFile + shuoms_img_address + "\\";
										File craete_file = new File(file_add+face.replace("/", "\\\\"));
										if(!craete_file.exists()) {
											craete_file.mkdirs();
										}
										//String file_add = SystemConfig.getSystemConfigValue("ipc", "img_add", "X:\\Cooperop\\apache-tomcat-8.0.24\\webapps\\ROOT\\WEB-INF\\apps\\ipc\\resource\\hytimages\\");
										String hty_add = SystemConfig.getSystemConfigValue("ipc", "hty_add", "X:\\hyt1\\慧药通\\分析服务WebService接口\\hytimages\\");
										String files = hty_add + face + add_name + "." + houz;
						                String change = file_add + face + "sys" + string_code + "p" + img_count + "." + houz;
						                copyFile(new File(files.replaceAll("/", "\\\\")), new File(change.replaceAll("/", "\\\\")));
									}
								}
								//重新插入
								map.put(key, value);
							}
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}
					}
					//------------------------
					map.put("sys_drug_code", code.get("sys_drug_code"));
					map.put("owner_create", "0");
					//截取生产厂家
					String address = (String)map.get("manufacturer");
					String sc_name = getAddress(address,"企业名称:");
					String sc_address = getAddress(address,"生产地址:");
					String sc_phone = getAddress(address,"电话号码:");
					if (CommonFun.isNe(sc_name)) {
						sc_name = getAddress(address,"企业名称：");
					}
					if (CommonFun.isNe(sc_address)) {
						sc_address = getAddress(address,"生产地址：");
					}
					if (CommonFun.isNe(sc_phone)) {
						sc_phone = getAddress(address,"电话号码：");
					}
					map.put("manufacturer_short_name", sc_name);
					map.put("manufacturer_address", sc_address);
					map.put("manufacturer_tel", sc_phone);
					
					try {
						connect("ipc");
						DrugSmsDao drugSmsDao = new DrugSmsDao();
						drugSmsDao.insert_temp(map);//插入说明书表 临时表
						Map<String,Object> mParam=new HashMap<String,Object>();
						mParam.put("drug_code", code.get("sys_drug_code"));
						mParam.put("haveurl", "1") ;
						drugSmsDao.updateSysDict(mParam);//TODO :更新标准库药品数据字典
					}catch (Exception e) {
						e.getStackTrace();
						continue;
					}  finally {
						disconnect();
					}
				}
			}catch(Exception es) {
				log.info("获取说明书失败，说明书链接：" + url);
				log.error(es);
				continue;
			}
			}
			/*drug.deleteAll(new HashMap<String, Object>());//清空表数据，需要加条件删除，仅仅删除同步过来的药品说明书
			drug.insertTemptoSms(new HashMap<String, Object>());  可直接删除 ayangz*/
			try {
				connect("ipc");
				new DrugSmsDao().execTmp2Spzl(new HashMap<String, Object>());
			} finally {
				disconnect();
			}
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			//disconnect();
			Client.syncShuomsImg();
			try {
				new SyncInstruction().executeOn();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param gurl  网址
	 * @return getbody 网址链接的body部分源代码
	 * @author yan 
	 * @throws IOException 
	 * @time 2018年4月3日
	 * @function 根据网页链接获取其body部分源码
	 */
	public static String geturlbody(String gurl) throws IOException  {
		StringBuffer sb=null;
		String getbody="";
		InputStream in=null;
		BufferedReader bf=null;
		try {
		    String substring = gurl.substring(gurl.lastIndexOf("/")+1, gurl.length());
		    //log.error(gurl.substring(0, gurl.lastIndexOf("/")+1)+URLEncoder.encode(substring, "UTF-8"));
			URL url = new URL(gurl.substring(0, gurl.lastIndexOf("/")+1)+URLEncoder.encode(substring, "UTF-8"));
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			int responseCode = conn.getResponseCode();
			if(HttpURLConnection.HTTP_OK == responseCode 
					|| responseCode == HttpURLConnection.HTTP_CREATED 
					|| responseCode == HttpURLConnection.HTTP_ACCEPTED ) {
				in=conn.getInputStream();				
			}else {
				in=conn.getErrorStream();
			}
			if(in==null) {
				log.debug("没有抓取到数据！");
			}
			bf=new BufferedReader(new InputStreamReader(in, "utf-8"));
			String line=null;
			sb=new StringBuffer();
			while(null!=(line=bf.readLine())){
				sb.append(line+"\n");
			}
			//log.debug(sb.toString());
			String html=sb.toString();
			Pattern pattern = Pattern.compile("<h5( .*?)?>.*?</h5>");
			Matcher matcher = pattern.matcher(html);
			while (matcher.find()) {
				String trim = matcher.group(0).trim();
				html = html.replace(trim, "");
			}
			getbody= Jsoup.parse(html).getElementsByTag("body").toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (bf != null) {
				bf.close();
			}
			if (in != null) {
				in.close();
			}
		}
		return getbody;
	}
	
	
	/**
	 * 药品说明书直接调用此方法
	 */
	public Map<String, Object> getInstruction(Record drug) throws Exception {
		try {
			connect("ipc");
			if (drug == null || CommonFun.isNe(drug.get("his_drug_code"))) {
				System.out.println("请求参数不能为null：" + drug.toString());
				return null;
			}
			Map<String, Object> info = new HashMap<String, Object>();
			DrugSmsDao dsDao = new DrugSmsDao();
			ShuoMSDao smsDao = new ShuoMSDao();
			drug.put("drug_code",drug.get("his_drug_code"));
			Record zdySms = smsDao.getByHiscode_zdy(drug);
			info.put("smsdrug",dsDao.querySmsDesc(new HashMap<String,Object>()).getResultset()); // 说明书字段排列顺序
			// 自定义说明书
			if(!CommonFun.isNe(zdySms)) {
				info.put("drug", zdySms);
				info.put("is_zdy", "1");
				return info;
			}
			// 系统说明书
			Record sysSms = smsDao.getByHiscode(drug);
			info.put("drug", sysSms);
			return info;
		} catch (Exception e) {
			log.error("参数" + drug.toString(), e);
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Map<String, Object> getbydrugcode_one(Map<String, Object> codemap) throws Exception {
		try {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			codemap.put("drug_code",codemap.get("his_drug_code"));
			Record smsre = new Record();
			connect("ipc");
			smsre = new DrugSmsDao().histosys(codemap);//到dao方法查询需要修改，his和标准库的药品字典表对应
			disconnect();
			if(CommonFun.isNe(smsre)) {
				returnMap = null;
			}else {
				//++++++
				String url = "";
				try {
					//根据每个药品获取三方的药品说明书地址   new EngineHYT().execMethod("药品code");
					String  drug_code = smsre.getString("sys_drug_code");
					drug_code = drug_code.replaceAll("\\|", "_").replaceAll("\\;", "_")
							.replaceAll("\\/", "_").replaceAll("\\?", "_")
							.replaceAll("\\:", "_").replaceAll("\\@", "_")
							.replaceAll("\\&", "_").replaceAll("\\=", "_")
							.replaceAll("\\+", "_").replaceAll("\\$", "_")
							.replaceAll("\\,", "_").replaceAll("\\#", "_").replaceAll("\\*", "_");
					url=new EngineDrug().execMethod(smsre.getString("sys_drug_code"));
					//url = "http://"+SystemConfig.getSystemConfigValue("hospital_common", "url_ipc")+"/DrugInstructionFiles/"+drug_code+".html";
					//根据地址获取页面内容
					String body=geturlbody(url);
					body=body.replaceAll("<loml", "<loml>");
					body=body.replaceAll("<l", "<l>");
					//log.debug(body);
					//保存页面内容数据
					Map<String,Object> map = JavaScriptUtil.getFieldsValue(body);
					long img_count = 0;
					boolean check = false;
					Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
					while (iterator.hasNext()) {
						if (map.get(iterator.next().getKey()) != null) {
							check = true;
							break;
						}
					}
					if(check) {
						//添加药品说明书图片的解析
						for (Map.Entry<String, Object> entry : map.entrySet()) {
							try {
								String key = entry.getKey();
								Object object = entry.getValue();
								if (!CommonFun.isNe(object)) {
									String value = object.toString();
									String regxpForImgTag = "<\\s*img\\s*([^>]*)\\s*/?\\s*>";
									Pattern p = Pattern.compile(regxpForImgTag, Pattern.MULTILINE);
									Matcher m = p.matcher(value);
									String face = "";
									String add_name ="";
									String houz = "";
									while(m.find()) {
										String data = m.group(0).trim();//img标签<img src="111.jqp" />
										String regxpForImgSrc = "(src|SRC)=(\\\"|\\')(.*?)(\\\"|\\')";
										Pattern pattern = Pattern.compile(regxpForImgSrc, Pattern.MULTILINE);
										Matcher matcher = pattern.matcher(data);
										while (matcher.find()) {
											img_count += 1;
											String[] trim = matcher.group(0).trim().split("\"");
											String src = trim[trim.length-1];
											//src有两种,一种为"111.jpg",一种为"images\drugimages\111.jpg"
											int one = src.lastIndexOf("/");
											if (one >= 0 ) {
												String add_name_all = src.substring(one+1, src.length());
												face = src.substring(0, one+1);//前缀
												int lastIndexOf = add_name_all.lastIndexOf(".");
												add_name = add_name_all.substring(0, lastIndexOf);//文件名
												houz = add_name_all.substring(lastIndexOf+1, add_name_all.length());//后缀
											}else {
												String add_name_all = src;
												int lastIndexOf = add_name_all.lastIndexOf(".");
												add_name = add_name_all.substring(0, lastIndexOf);//文件名
												houz = add_name_all.substring(lastIndexOf+1, add_name_all.length());//后缀
											}
											value = value.replace(src, face+"sys" + drug_code + "p" + img_count +"."+houz);
											
											//拷贝文件,更改文件名称:  sys+标准库code(|替换为K)+p+递增数字(从1开始)
											URL resource = Thread.currentThread().getContextClassLoader().getResource("/");
											String courseFile = resource.getPath().substring(resource.getPath().lastIndexOf(":")-1, resource.getPath().indexOf("/WEB-INF/")+9).replace("/", "\\");
											String shuoms_img_address = SystemConfig.getSystemConfigValue("ipc", "shuoms_img_address", "apps\\hospital_common\\resource\\drugimg");
											String file_add = courseFile + shuoms_img_address + "\\";
											File craete_file = new File(file_add+face.replace("/", "\\\\"));
											if(!craete_file.exists()) {
												craete_file.mkdirs();
											}
											//String file_add = SystemConfig.getSystemConfigValue("ipc", "img_add", "X:\\Cooperop\\apache-tomcat-8.0.24\\webapps\\ROOT\\WEB-INF\\apps\\ipc\\resource\\hytimages\\");
											String hty_add = SystemConfig.getSystemConfigValue("ipc", "hty_add", "X:\\hyt1\\慧药通\\分析服务WebService接口\\hytimages\\");
											String files = hty_add + face + add_name + "." + houz;
							                String change = file_add + face + "sys" + drug_code + "p" + img_count + "." + houz;
							                copyFile(new File(files.replaceAll("/", "\\\\")), new File(change.replaceAll("/", "\\\\")));
										}
									}
									//重新插入
									map.put(key, value);
								}
							} catch (Exception e) {
								e.printStackTrace();
								continue;
							}
						}
						//------------------------
						map.put("sys_drug_code", smsre.get("sys_drug_code"));
						map.put("owner_create", "0");
						//截取生产厂家
						String address = (String)map.get("manufacturer");
						String sc_name = getAddress(address,"企业名称:");
						String sc_address = getAddress(address,"生产地址:");
						String sc_phone = getAddress(address,"电话号码:");
						if (CommonFun.isNe(sc_name)) {
							sc_name = getAddress(address,"企业名称：");
						}
						if (CommonFun.isNe(sc_address)) {
							sc_address = getAddress(address,"生产地址：");
						}
						if (CommonFun.isNe(sc_phone)) {
							sc_phone = getAddress(address,"电话号码：");
						}
						map.put("manufacturer_short_name", sc_name);
						map.put("manufacturer_address", sc_address);
						map.put("manufacturer_tel", sc_phone);
						
						try {
							connect("ipc");
							new DrugSmsDao().update(map);//插入说明书表
							returnMap.put("drug", map);
							return returnMap;
						}catch (Exception e) {
							e.getStackTrace();
							return returnMap;
						}  finally {
							disconnect();
						}
					}
				}catch(Exception es) {
					log.info("获取说明书失败，说明书链接：" + url);
					log.error(es);
					return returnMap;
				}
				//++++++
			}
			return returnMap;
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}finally {
			//disconnect();
		}
	}
	
	/**
	 * 获取说明书字符串中的厂家,地址等信息
	 */
	public String getAddress(String string, String t) {
		if(!CommonFun.isNe(string)) {
			String[] split = string.split(t);
			if (!CommonFun.isNe(split)) {
				if (split.length>1) {
					String s = split[1];
					String[] q = s.split(" ");
					String w = new String();
					for (String string2 : q) {
						if (!string2.isEmpty()) {
							w = string2;
							break;
						}
					}
					return w.trim();
				}
			}
		}
		return "";
	}
	
	/**
	 * @param body dict_sys_drugsms中的htmlbody
	 * @param drugcode 药品编号
	 * @throws Exception
	 * @author yan 
	 * @time 2018年4月11日
	 * @function 根据body部分 生成jsp文件
	 */
	/*public void createhtml(String body,String drugcode) throws Exception {
		try {
			StringBuffer sb=new StringBuffer();
			String path=null;
			sb.append("<%@page import=\"java.util.Date\"%>\n");
			sb.append("<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>\n");
			sb.append("<%@taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>\n");
			sb.append("<%@taglib prefix=\"fn\" uri=\"http://java.sun.com/jsp/jstl/functions\"%>\n");
			sb.append("<%@taglib prefix=\"fmt\" uri=\"http://java.sun.com/jsp/jstl/fmt\"%>\n");
			sb.append("<%@taglib prefix=\"s\" uri=\"http://www.crtech.cn/jstl/cooperoptld\" %>\n");
			sb.append("<html>\n");
			sb.append(body);
			sb.append("\n</html>");
			FileOutputStream out = null;
			try {
				path=GlobalVar.getWorkPath()+"\\WEB-INF\\apps\\hospital_common\\view\\drugsms\\drug"+drugcode+".jsp";
				File file=new File(path);
		         if(!file.exists()) {
		        	 file.createNewFile();
		        	 out =new FileOutputStream(file,true);        
			         out.write(sb.toString().getBytes("utf-8"));
		         }
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(out != null) {
					out.close();
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}*/
	
	
    /**
     * @param dir 要清空的文件夹路径
     * @author yan
     * @time 2018年4月11日
     * @function 删除指定目录下的所有文件（不含有子目录）
     */
    /*public static void delfiles(String dir) {
    	File file = new File(dir); 
		if(file.isDirectory()) {
			String[] s=file.list();
			for (int i = 0; i < s.length; i++) {
				new File(dir+"//"+s[i]).delete();
			}
		}
    } */

	
	//审查结果页面显示
		public Record get(Map<String, Object> params) throws Exception {
			try {
				connect("ipc");
				return new AutoAuditDao().get((String)params.get("id"));
			}catch(Exception e){
				throw e;
			} finally {
				disconnect();
			}
		}
		/** 根据各个审查结果和审查规则，分析出审查的综合结果  */
		public String audit_hlyy(String audit_flag, Map<String, Object> map) throws Exception {
			try {
				connect("ipc");
	        	Map<String, Object> idmap = new HashMap<String, Object>();
	 	        idmap.put("auto_audit_id", map.get("auto_audit_id"));
	 	        CheckResultInfoDao check = new CheckResultInfoDao();
	 	        // test yan
	 	        /*ThingHpTimeDao tht = new ThingHpTimeDao();
	 	        tht.insert((String)map.get("common_id"), "调用自定义审查运行环境开始,[合理用药审查开始]", (String)map.remove("tt2"));
	 	        tht.insert((String)map.get("common_id"), "调用自定义审查运行环境结束", (String)map.remove("tt3"));
	 	        tht.insert((String)map.get("common_id"), "合理用药审查结束", (String)map.remove("tt4"));
	 	        tht.insert((String)map.get("common_id"), "标记is_new,标记审查结果YNT开始", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));*/
				// --
	 			//check.updateIsNew(idmap);//给新开医嘱问题标记
	 			//check.updateResultState(idmap);//审查结果标记
	 			//tht.insert((String)map.get("common_id"), "标记is_new,标记审查结果YNT结束,调用check_result_proc开始", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
				String zdy_result = "HL_Y";//合理用药最终结果
				Map<String, Object> p = new HashMap<String, Object>();
				p.put("id", map.get("auto_audit_id"));
				AutoAuditDao autos = new AutoAuditDao();
				long execFilterNotmind_time = System.currentTimeMillis();
				String rtns = autos.execFilterNotmind(idmap);//删除智能审查中被自定义审查设置为合理的问题
				//tht.insert((String)map.get("common_id"), "调用check_result_proc结束,分析ipc审查结果开始", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
				ylz.recordMsg("mmid_" + map.get("common_id"), "ipc调用check_result_proc得出结果时间, (消耗 " + (System.currentTimeMillis() - execFilterNotmind_time) + " ms)");
				//审查问题集合
				long result_time = System.currentTimeMillis();
				Result result = new CheckResultInfoDao().queryByAutoID(map);
				if(!CommonFun.isNe(result)) {
					List<Record> queryByAutoID = result.getResultset();
					for (Record record : queryByAutoID) {
						String state = (String)record.get("check_result_state");
						if("B".equals(state)) {
							zdy_result="HL_B";
							break;
						}else if("N".equals(state)) {
							zdy_result="HL_N";
						}else if("T".equals(state) && zdy_result.equals("HL_Y")) {
							zdy_result="HL_T";
						}
						
					}
				}
				//audit_flag为Y表示正常审查结束，zdy_result为Y表示审查结果为通过的医嘱/处方
				if("Y".equals(audit_flag) || !"HL_Y".equals(zdy_result)){
					//审查通过，或者审查超时但有一种审查没有超时并有返回不通过意见的情况
					p.put("state", zdy_result);
					p.put("sys_audit_result", zdy_result);
				}else{
					p.put("state", audit_flag);
					p.put("sys_audit_result", audit_flag);
				}
				p.put("auto_audit_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
				autos.update(p);
				//tht.insert((String)map.get("common_id"), "分析ipc审查结果结束", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
				ylz.recordMsg("mmid_" + map.get("common_id"), "ipc分析审查结果时间, (消耗 " + (System.currentTimeMillis() - result_time) + " ms)");
				return zdy_result;
			}catch(Exception e){
				throw e;
			}finally {
				disconnect();
			}
		}
		public Map<String, Object> timeOutDetail(Map<String, Object> params) throws Exception {
			try {
				connect("ipc");
				Map<String, Object> ret = new HashMap<String, Object>();
				//根据common_id去查询主表的id
				AutoAuditDao autodao=new AutoAuditDao();
				AutoAuditFlowDao fd=new AutoAuditFlowDao();
				Record flow = fd.query(params).getResultset(0);
				params.put("id", flow.get("common_id"));
				ret.put("flow_id", flow.get("id"));
				ret.put("djbh", flow.get("djbh"));
				Record record=autodao.getAuto_audit_id((String)params.get("id"));
				if(!CommonFun.isNe(params.get("id"))){
					String id = (String)record.get("id");
					Record getpatient = new HisInPatientDao().getpatient1(params);
					params.put("patient_id",getpatient.get("patient_id"));
					params.put("visit_id",getpatient.get("visit_id"));
					ret.put("auto",new AutoAuditDao().get(id));
					params.put("auto_audit_id", id);
					CheckResultInfoDao check = new CheckResultInfoDao();
					Map<String, Object> idmap = new HashMap<String, Object>();
					idmap.put("auto_audit_id", id);
					ret.put("items", new CheckResultDao().queryTypeNum(params).getResultset());
					ret.put("item_info", check.queryCheckResultInfo(params).getResultset());
					ret.put("orders", new AutoAuditOrdersDao().queryOrders(params).getResultset());
					ret.put("ipc_auto_id", id);
					ret.put("msg_alert_id", record.get("msg_alert_id"));
					ret.put("diagnosis", new AutoAuditOrdersDao().queryDiagnosis(params).getResultset());
				}
				Result  commonts = new PharmacistCommentDao().queryByAuditId(params);
				if(!CommonFun.isNe(commonts)){
					ret.put("comments", commonts.getResultset());
				}
				Result ordersgroup = new AutoAuditOrdersDao().queryOrdersGroup(params);
				if(!CommonFun.isNe(ordersgroup)){
					ret.put("ordersgroup", ordersgroup.getResultset());
				}
				Result comment_orders = new AutoAuditDao().getyzsAll(params);
				if(!CommonFun.isNe(comment_orders)){
					ret.put("comment_orders", comment_orders.getResultset());
				}
				ret.put("common_id", flow.get("common_id"));
				try {
					ret.put("userinfo_id", user().getId());
				} catch (Exception e) {}
				return ret;
			}catch(Exception e){
				throw e;
			} finally {
				disconnect();
			}
		}
		
	
	public Map<String, Object> test_getDrugUrl(Map<String, Object> params){
		try{
			String drug_code = (String) params.get("drug_code");
			/*drug_code = drug_code.replaceAll("\\|", "_").replaceAll("\\;", "_")
			.replaceAll("\\/", "_").replaceAll("\\?", "_")
			.replaceAll("\\:", "_").replaceAll("\\@", "_")
			.replaceAll("\\&", "_").replaceAll("\\=", "_")
			.replaceAll("\\+", "_").replaceAll("\\$", "_")
			.replaceAll("\\,", "_").replaceAll("\\#", "_").replaceAll("\\*", "_");*/
			String url = null ;
			if(!CommonFun.isNe(drug_code)) {
				url = new EngineDrug().execMethod(drug_code);
				log.debug("抓取说明书URL:"+url);
				//url = "http://"+SystemConfig.getSystemConfigValue("hospital_common", "url_ipc")+"/DrugInstructionFiles/"+drug_code+".html";
			}
			String body=geturlbody(url);
			body=body.replaceAll("<loml", "<loml>");
			body=body.replaceAll("<l", "<l>");
			params.put("drug_url", url);
			params.put("drug_body", body);
		} catch(Exception e){
			e.printStackTrace();
		}
		return params;
	}
	
	public static void main(String[] args) throws IOException {
		    String s =  "T:\\crtech\\chaoran-space\\CooperOP\\cooperop\\WEB-INF\\apps\\ipc\\resource\\img\\1077029(6).JPG";
		    String t=   "T:\\crtech\\chaoran-space\\CooperOP\\cooperop\\WEB-INF\\apps\\ipc\\resource\\img\\1077029(5).JPG";
		   // copyFile(new File(s), new File(t));
		    String gurl = "http://168.168.170.121:11007/DrugInstructionFiles/王.html";
		    String substring = gurl.substring(gurl.lastIndexOf("/")+1, gurl.length());
		    log.debug(gurl.substring(0, gurl.lastIndexOf("/")+1)+URLEncoder.encode(substring, "UTF-8"));
		//String string = "11<img src=\"/res/ipc/hytimages/images/drugimages/1062023(1).png\" width=\"\">";
		//String regxpForImgTag = "<\\s*img\\s*([^>]*)\\s*/?\\s*>";
		/*Pattern p = Pattern.compile(regxpForImgTag, Pattern.MULTILINE);
		Matcher m = p.matcher(string);
		while(m.find()) {
			log.debug(m.group(0).trim());
			String regxpForImgSrc = "(src|SRC)=(\\\"|\\')(.*?)(\\\"|\\')";
			Pattern pattern = Pattern.compile(regxpForImgSrc, Pattern.MULTILINE);
			Matcher matcher = pattern.matcher(m.group(0).trim());
			while(matcher.find()) {
				log.debug(matcher.group(0).trim());
				String[] trim = matcher.group(0).trim().split("\"");
				String src = trim[trim.length-1];
				int one = src.lastIndexOf("/");
				String add_name="";
				String houz = "";
				String face = "";
				if (one >= 0 ) {
					String add_name_all = src.substring(one+1, src.length());
					face = src.substring(0, one+1);
					int lastIndexOf = add_name_all.lastIndexOf(".");
					add_name = add_name_all.substring(0, lastIndexOf);
					houz = add_name_all.substring(lastIndexOf+1, add_name_all.length());
				}else {
					String add_name_all = src;
					int lastIndexOf = add_name_all.lastIndexOf(".");
					add_name = add_name_all.substring(0, lastIndexOf);
					houz = add_name_all.substring(lastIndexOf+1, add_name_all.length());
				}
				log.debug();
			}
		}*/
	}
	
	/**
	 * 拷贝文件
	 * */
	private static void copyFile(File source, File target) throws IOException {  
		if (target.exists()) {
			target.delete();
		}
		if (!source.exists()) {
			return;
		}
	    FileChannel in = null;  
	    FileChannel out = null;  
	    FileInputStream inStream = null;  
	    FileOutputStream outStream = null;  
	    try {
	        inStream = new FileInputStream(source);  
	        outStream = new FileOutputStream(target);  
	        in = inStream.getChannel();  
	        out = outStream.getChannel();  
	        in.transferTo(0, in.size(), out);  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	    	inStream.close();  
	    	in.close();  
	    	outStream.close();  
	    	out.close();  
	    }  
	} 
	
	//test 不调用客户端生成链接 直接拼地址  emm地址仅仅是换了drug_code而已
	public void quickGet(){
		try {
			connect("ipc");
			DrugSmsDao drug=new DrugSmsDao();
			List<Record> drugcodes = new ArrayList<Record>();
			try {
				drugcodes=drug.query(new HashMap<String, Object>()).getResultset();
				drug.deleteAllTemp(new HashMap<String, Object>());
				drug.updateHaveUrl(new HashMap<String, Object>());
			}catch (Exception e) {
				e.getStackTrace();
			} finally {
				disconnect();
			}
			String body = null, url = null;
			for (Record code : drugcodes) {
				//url = "http://"+GlobalVar.getSystemProperty("hyt_url") +"/DrugInstructionFiles/"+code.getString("sys_drug_code")+".html";
				//url=new EngineDrug().execMethod(code.getString("sys_drug_code"));
				//url = "http://168.168.170.121:11007/DrugInstructionFiles/32_12107.html";
				//http://168.168.170.121:11007/DrugInstructionFiles/166576.html
				//http://168.168.170.121:11007/DrugInstructionFiles/15404.html
				String string_code = code.getString("sys_drug_code");
				string_code = string_code.replaceAll("\\|", "_").replaceAll("\\;", "_")
				.replaceAll("\\/", "_").replaceAll("\\?", "_")
				.replaceAll("\\:", "_").replaceAll("\\@", "_")
				.replaceAll("\\&", "_").replaceAll("\\=", "_")
				.replaceAll("\\+", "_").replaceAll("\\$", "_")
				.replaceAll("\\,", "_").replaceAll("\\#", "_").replaceAll("\\*", "_");
				url=new EngineDrug().execMethod(code.getString("sys_drug_code"));
				//url = "http://"+SystemConfig.getSystemConfigValue("hospital_common", "url_ipc")+"/DrugInstructionFiles/"+string_code+".html";
				try {
					body = geturlbody(url);
					body=body.replaceAll("<loml", "<loml>");
					body=body.replaceAll("<l", "<l>");
					Map<String,Object> map = JavaScriptUtil.getFieldsValue(body);
					long img_count = 0;
					boolean check = false;
					Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
					while (iterator.hasNext()) {
						if (map.get(iterator.next().getKey()) != null) {
							check = true;
							break;
						}
					}
					if(check) {
						//添加药品说明书图片的解析
						for (Map.Entry<String, Object> entry : map.entrySet()) {
							try {
								String key = entry.getKey();
								Object object = entry.getValue();
								if (!CommonFun.isNe(object)) {
									String value = object.toString();
									String regxpForImgTag = "<\\s*img\\s*([^>]*)\\s*/?\\s*>";
									Pattern p = Pattern.compile(regxpForImgTag, Pattern.MULTILINE);
									Matcher m = p.matcher(value);
									String face = "";
									String add_name ="";
									String houz = "";
									while(m.find()) {
										String data = m.group(0).trim();//img标签<img src="111.jqp" />
										String regxpForImgSrc = "(src|SRC)=(\\\"|\\')(.*?)(\\\"|\\')";
										Pattern pattern = Pattern.compile(regxpForImgSrc, Pattern.MULTILINE);
										Matcher matcher = pattern.matcher(data);
										while (matcher.find()) {
											img_count += 1;
											String[] trim = matcher.group(0).trim().split("\"");
											String src = trim[trim.length-1];
											//src有两种,一种为"111.jpg",一种为"images\drugimages\111.jpg"
											int one = src.lastIndexOf("/");
											if (one >= 0 ) {
												String add_name_all = src.substring(one+1, src.length());
												face = src.substring(0, one+1);//前缀
												int lastIndexOf = add_name_all.lastIndexOf(".");
												add_name = add_name_all.substring(0, lastIndexOf);//文件名
												houz = add_name_all.substring(lastIndexOf+1, add_name_all.length());//后缀
											}else {
												String add_name_all = src;
												int lastIndexOf = add_name_all.lastIndexOf(".");
												add_name = add_name_all.substring(0, lastIndexOf);//文件名
												houz = add_name_all.substring(lastIndexOf+1, add_name_all.length());//后缀
											}
											value = value.replace(src, face+"sys" + string_code + "p" + img_count +"."+houz);
											
											//拷贝文件,更改文件名称:  sys+标准库code(|替换为K)+p+递增数字(从0开始)
											URL resource = Thread.currentThread().getContextClassLoader().getResource("/");
											String courseFile = resource.getPath().substring(resource.getPath().lastIndexOf(":")-1, resource.getPath().indexOf("/WEB-INF/")+9).replace("/", "\\");
											String shuoms_img_address = SystemConfig.getSystemConfigValue("ipc", "shuoms_img_address", "apps\\hospital_common\\resource\\drugimg");
											String file_add = courseFile + shuoms_img_address + "\\";
											File craete_file = new File(file_add+face.replace("/", "\\\\"));
											if(!craete_file.exists()) {
												craete_file.mkdirs();
											}
											//String file_add = SystemConfig.getSystemConfigValue("ipc", "img_add", "X:\\Cooperop\\apache-tomcat-8.0.24\\webapps\\ROOT\\WEB-INF\\apps\\ipc\\resource\\hytimages\\");
											String hty_add = SystemConfig.getSystemConfigValue("ipc", "hty_add", "X:\\hyt1\\慧药通\\分析服务WebService接口\\hytimages\\");
											String files = hty_add + face + add_name + "." + houz;
							                String change = file_add + face + "sys" + string_code + "p" + img_count + "." + houz;
							                File file = new File(change.replaceAll("/", "\\\\"));
							                if (file.exists()) {
							                	file.delete();
											}
							                copyFile(new File(files.replaceAll("/", "\\\\")), new File(change.replaceAll("/", "\\\\")));
										}
									}
									//重新插入
									map.put(key, value);
								}
							} catch (Exception e) {
								e.printStackTrace();
								continue;
							}
						}
						//------------------------
						map.put("sys_drug_code", code.get("sys_drug_code"));
						map.put("owner_create", "0");
						String address = (String)map.get("manufacturer");
						String sc_name = getAddress(address,"企业名称:");
						String sc_address = getAddress(address,"生产地址:");
						String sc_phone = getAddress(address,"电话号码:");
						if (CommonFun.isNe(sc_name)) {
							sc_name = getAddress(address,"企业名称：");
						}
						if (CommonFun.isNe(sc_address)) {
							sc_address = getAddress(address,"生产地址：");
						}
						if (CommonFun.isNe(sc_phone)) {
							sc_phone = getAddress(address,"电话号码：");
						}
						map.put("manufacturer_short_name", sc_name);
						map.put("manufacturer_address", sc_address);
						map.put("manufacturer_tel", sc_phone);
						try {
							connect("ipc");
							DrugSmsDao drugSmsDao = new DrugSmsDao();
							drugSmsDao.insert_temp(map);//插入说明书表 临时表
							Map<String,Object> mParam=new HashMap<String,Object>();
							mParam.put("drug_code", code.get("sys_drug_code"));
							mParam.put("haveurl", "1") ;
							drugSmsDao.updateSysDict(mParam);//TODO :更新标准库药品数据字典
						}catch (Exception e) {
							e.getStackTrace();
							continue;
						}  finally {
							disconnect();
						}
					}
				}catch(Exception es) {
					log.info("获取说明书失败，说明书链接：" + url);
					log.error(es);
					continue;
				}
			}
			/*drug.deleteAll(new HashMap<String, Object>());//清空表数据，需要加条件删除，仅仅删除同步过来的药品说明书
			drug.insertTemptoSms(new HashMap<String, Object>());*/
			try {
				connect("ipc");
				new DrugSmsDao().execTmp2Spzl(new HashMap<String, Object>());
			} finally {
				disconnect();
			}
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			//disconnect();
			//Client.syncShuomsImg();
			try {
				new SyncInstruction().executeOn();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 更新弹窗消息的字段  , 已弹窗
	 * @param params
	 * @throws Exception
	 */
	public void updateNotices(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			AutoAuditDao aa = new AutoAuditDao();
			List auto_audit_ids = CommonFun.json2Object((String)params.get("auto_audit_ids"), List.class);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("is_pop", "1");
			for (int i = 0; i < auto_audit_ids.size(); i++) {
				map.put("id", auto_audit_ids.get(i));
				aa.update(map);
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	/** 测试查询审查列表 */
	public Result hyt_audit_test_result(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Result r = new AutoCommonDao().hyt_audit_test_result(params);
			return r;
		} catch (Exception e) { throw e; }
		finally { disconnect(); }
	}
	
	/**
	 * HYT审查测试
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void hyt_audit_test(Map<String, Object> params) throws Exception {
		try {
			//获取文件中的xml串, 注意相关文件中txt的xml文件必须以hyt_test开头
			String systemConfigValue = SystemConfig.getSystemConfigValue("ipc", "hyt_test_audit_address", "D:\\hyt_test");
			File file = new File(systemConfigValue);
			File[] files = file.listFiles();
			for (File file2 : files) {
				String name = file2.getName();
				if(name.contains("hyt_test")) {
					String xml = readTxt(systemConfigValue+"\\"+name).toLowerCase();
					try {
						//String url = "http://"+GlobalVar.getSystemProperty("local_ip_port","8085") + "/optionifs";
						TestHYTAudit.doPost(xml);
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) { throw e; }
		finally { disconnect(); }
	}
	
	/** 
     * 解析普通文本文件  流式文件 如txt 
     * @param path 
     * @return 
     */  
    @SuppressWarnings("unused")  
    public static String readTxt(String path){  
        StringBuilder content = new StringBuilder("");  
        try {  
            String code = resolveCode(path);  
            File file = new File(path);  
            InputStream is = new FileInputStream(file);  
            InputStreamReader isr = new InputStreamReader(is, code);  
            BufferedReader br = new BufferedReader(isr);   
            String str = "";  
            while (null != (str = br.readLine())) {  
                content.append(str);  
            }  
            br.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
            System.err.println("读取文件:" + path + "失败!");  
        }  
        return content.toString();  
    }  
      
      
      
    public static String resolveCode(String path) throws Exception {  
        InputStream inputStream = new FileInputStream(path);    
        byte[] head = new byte[3];    
        inputStream.read(head);      
        String code = "gb2312";  //或GBK  
        if (head[0] == -1 && head[1] == -2 )    
            code = "UTF-16";    
        else if (head[0] == -2 && head[1] == -1 )    
            code = "Unicode";    
        else if(head[0]==-17 && head[1]==-69 && head[2] ==-65)    
            code = "UTF-8";    
            
        inputStream.close();  
          
        return code;  
    } 
    
    /**
     * 升级审查结果问题表check_result_info中的特殊规则结果字段
     * @param params
     * @throws Exception
     */
    public void updateCRIResult(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			new CheckResultInfoDao().update(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
