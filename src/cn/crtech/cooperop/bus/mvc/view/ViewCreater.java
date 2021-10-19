package cn.crtech.cooperop.bus.mvc.view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import cn.crtech.cooperop.bus.engine.window.HtmlWindow;
import cn.crtech.cooperop.bus.mvc.service.ViewService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;

public class ViewCreater {
	public static void create(String type, String flag, String scheme,String system_product_code) throws Exception {
		Record view = new ViewService().get(type, flag, scheme,system_product_code);
		if (view == null) {
			throw new Exception("单据未找到");
		}
		
		ObjectInputStream ois = null;
		
		try {
			ois = new ObjectInputStream(new ByteArrayInputStream(view.getBytes("content")));
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (ois != null) ois.close();
		}
		
		@SuppressWarnings("unchecked")
		Map<String, Object> definition =  (Map<String, Object>) ois.readObject();
		
		String pageid = view.getString("system_product_code") + "." + view.getString("type") + "." + view.getString("flag") + "." + view.getString("id");
		String jspFileName = GlobalVar.getWorkPath() + HtmlWindow.getViewPath(pageid);
		saveJSP(obj2jsp(pageid, definition,null).toString(), jspFileName);
	}
	public static void create(String type, String flag, String scheme, String system_product_code, Map<String, Object> p) throws Exception {
		Record view = new ViewService().get(type, flag, scheme, system_product_code);
		if (view == null) {
			throw new Exception("单据未找到");
		}
		
		ObjectInputStream ois = null;
		
		try {
			ois = new ObjectInputStream(new ByteArrayInputStream(view.getBytes("content")));
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (ois != null) ois.close();
		}
		
		@SuppressWarnings("unchecked")
		Map<String, Object> definition =  (Map<String, Object>) ois.readObject();
		
		String pageid = view.getString("system_product_code") + "." + view.getString("type") + "." + view.getString("flag") + "." + view.getString("id");
		String jspFileName = GlobalVar.getWorkPath() + HtmlWindow.getViewPath(pageid);
		saveJSP(obj2jsp(pageid, definition, p).toString(), jspFileName);
	}
	public static void create(String type, String flag, String scheme, String system_product_code, String pageid) throws Exception {
		Record view = new ViewService().get(type, flag, scheme, system_product_code);
		if (view == null) {
			throw new Exception("单据未找到");
		}
		
		ObjectInputStream ois = null;
		
		try {
			ois = new ObjectInputStream(new ByteArrayInputStream(view.getBytes("content")));
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (ois != null) ois.close();
		}
		
		@SuppressWarnings("unchecked")
		Map<String, Object> definition =  (Map<String, Object>) ois.readObject();
		
		String jspFileName = GlobalVar.getWorkPath() + HtmlWindow.getViewPath(pageid);
		saveJSP(obj2jsp(pageid, definition,null).toString(), jspFileName);
	}
	public static void create(String productCode) throws Exception {
		Record params = new Record();
		params.put("system_product_code", productCode);
		Result rs = new ViewService().query(params);
		for (Record view : rs.getResultset()) {
			create(view.getString("type"), view.getString("flag"), view.getString("id"),view.getString("system_product_code"));
		}
	}

	public static String getControlHtml(String jsonstr, Object is_mobile) throws Exception {
		return getControlHtml(CommonFun.json2Object(jsonstr, Map.class), is_mobile);
	}

	public static String getControlHtml(Map<String, Object> control, Object is_mobile) throws Exception {
		return Transform(obj2xml(control).toString(), is_mobile);
	}

	public static StringBuffer obj2xml(Map<String, Object> control) throws Exception {
		StringBuffer str = new StringBuffer();
		str.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
		str.append(objxml(control));
		return str;
	}

	public static StringBuffer objxml(Map<String, Object> control) throws Exception {
		StringBuffer node = new StringBuffer();
		String type = (String) control.get("type");
		Map<String, Object> attrs = (Map<String, Object>) control.get("attrs");
		List<Map<String, Object>> contents = (List<Map<String, Object>>) control.get("contents");
		Object text = null;
		node.append("<cooperop-" + type);
		if (attrs != null) {
			Iterator<String> it = attrs.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				if (attrs.get(key) != null && !"initsql".equals(key)) {
					node.append(" " + key + "=\"" + attrs.get(key) + "\"");
				}
				if("option".equals(type) && "optiontext".equals(key)){
					text = attrs.get(key);
				}
			}
		}
		node.append(">\r\n");
		/*if("radio".equals(type)|| "select".equals(type)|| "checkbox".equals(type)){
			node.append("<cooperop-option name=\"_test\" label=\"_test\"></");
		}*/
		if(text != null){
			node.append(text);
		}
		if (contents != null) {
			for (Map<String, Object> c : contents) {
				node.append(objxml(c));
			}
		}
		node.append("</cooperop-" + type + ">\r\n");
		return node;
	}

	private static String Transform(String xmlstr, Object is_mobile) throws Exception {
		String xslFileName = GlobalVar.getWorkPath() + "/" + GlobalVar.getSystemProperty("theme.pc.path")
			+ "/views/view.xsl";
		if("Y".equals(is_mobile)){
			xslFileName = GlobalVar.getWorkPath() + "/" + GlobalVar.getSystemProperty("theme.app.path")
			+ "/views/view.xsl";
		}
		ByteArrayInputStream bis = null;
		ByteArrayOutputStream bos = null;
		try {

			bis = new ByteArrayInputStream(xmlstr.getBytes("UTF-8"));
			bos = new ByteArrayOutputStream();

			TransformerFactory tFac = TransformerFactory.newInstance();
			Source xslSource = new StreamSource(xslFileName);
			Transformer t = tFac.newTransformer(xslSource);
			t.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			t.setOutputProperty(OutputKeys.METHOD, "html");
			Source source = new StreamSource(bis);
			javax.xml.transform.Result result = new StreamResult(bos);
			t.transform(source, result);
			String s = new String(bos.toByteArray(), "utf-8");
			return s;
		} catch (TransformerConfigurationException e) {
			throw e;
		} catch (TransformerException e) {
			throw e;
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	public static StringBuffer obj2jsp(String pageid, Map<String, Object> control, Map<String, Object> p) throws Exception {
		StringBuffer str = new StringBuffer();
		str.append("<%@page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>\r\n");
		str.append("<%@taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>\r\n");
		str.append("<%@taglib prefix=\"fn\" uri=\"http://java.sun.com/jsp/jstl/functions\"%>\r\n");
		str.append("<%@taglib prefix=\"fmt\" uri=\"http://java.sun.com/jsp/jstl/fmt\"%>\r\n");
		str.append("<%@taglib prefix=\"s\" uri=\"http://www.crtech.cn/jstl/cooperoptld\"%>\r\n");
		str.append("<%@page import=\"java.util.Map\" %>\r\n");
		str.append("<%@page import=\"java.util.Iterator\" %>\r\n");
		str.append("<%@page import=\"cn.crtech.cooperop.application.action.BillAction\" %>\r\n");
		str.append("<%@page import=\"cn.crtech.cooperop.bus.util.CommonFun\" %>\r\n");
		str.append(objjsp(pageid, control, p));
		return str;
	}

	private static StringBuffer objjsp(String pageid, Map<String, Object> control, Map<String ,Object> p) throws Exception {
		StringBuffer node = new StringBuffer();
		String type = (String) control.get("type");
		Map<String, Object> attrs = (Map<String, Object>) control.get("attrs");
		//移除设计器属性
		attrs.remove("isdesign");
		attrs.remove("draggable");
		attrs.remove("ondragstart");
		attrs.remove("ondrop");
		attrs.remove("ondragover");
		attrs.remove("crid");
		List<Map<String, Object>> contents = (List<Map<String, Object>>) control.get("contents");
		if("tablefields".equals(type)){
			type = "table.fields";
		}else if("tablefield".equals(type)){
			type = "table.field";
		}else if("table".equals(type)){
			attrs.remove("initsql");
			attrs.remove("countsql");
		}else if("page".equals(type)){
			attrs.remove("initdjsql");
		}else if("chart".equals(type)){
			attrs.remove("yaxis_sql");
			attrs.remove("xaxis_sql");
		}
		node.append("<s:" + type);
		String richvalue = null;
		Object text = null;
		if (attrs != null) {
			Iterator<String> it = attrs.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				if (attrs.get(key) != null) {
					if("option".equals(type)){
						if("label".equals(key) && "_test".equals(attrs.get(key))){
							return new StringBuffer();
						}
						if("optiontext".equals(key)){
							text = attrs.get(key);
						}
					}
					if(p != null){
						if ("ishidden".equals(key) || "readonly".equals(key) || "required".equals(key)) {
							
						}else{
							node.append(" " + key + "=\"" + attrs.get(key).toString() + "\"");
						}
					}else{
						node.append(" " + key + "=\"" + attrs.get(key).toString() + "\"");
					}
					if ("name".equals(key) && !CommonFun.isNe(attrs.get(key))) {  //单据提取
						if(p != null){
							if(p.get("canreadfields") != null){
								String s = p.get("canreadfields").toString().replace("{", "");
								String[] cr = s.split("}");
								String f = "0";
								for(int i=0;i<cr.length;i++){
									if(cr[i].equals(attrs.get(key))){
										f = "1";
									}
								}
								if(f.equals("0")){
									node.append(" "+"ishidden='true'");
								}
							}
							if(p.get("canmodifyfields") != null){
								String s = p.get("canmodifyfields").toString().replace("{", "");
								String[] cm = s.split("}");
								String f = "0";
								for(int i=0;i<cm.length;i++){
									if(cm[i].equals(attrs.get(key))){
										f = "1";
									}
								}
								if(f.equals("0")){
									node.append(" "+"readonly='true'");
								}
							}
							if(p.get("requiredfields") != null){
								String s = p.get("requiredfields").toString().replace("{", "");
								String[] cm = s.split("}");
								String f = "0";
								for(int i=0;i<cm.length;i++){
									if(cm[i].equals(attrs.get(key))){
										f = "1";
									}
								}
								if(f.equals("1")){
									node.append(" "+"required='true'");
								}
							}
						}
						if(CommonFun.isNe(attrs.get("controltype"))){
							if("richeditor".equals(type)){
								richvalue = "${empty " + attrs.get(key).toString().toLowerCase() + " ? '"
										+ (CommonFun.isNe(attrs.get("defaultValue")) ? "" : attrs.get("defaultValue")) + "' : "
										+ attrs.get(key).toString().toLowerCase() + "}";
							}else{
								Object s = "''";
								if(!CommonFun.isNe(attrs.get("defaultValue"))){
									Object dv = attrs.get("defaultValue");
									if("${rq}".equals(dv)){
										s = "rq";
									}else if("${ontime}".equals(dv)){
										s = "ontime";
									}else if("${username}".equals(dv)){
										s = "username";
									}else if("${dep_code}".equals(dv)){
										s = "dep_code";
									}else if("${jigid}".equals(dv)){
										s = "jigid";
									}else if("${dep_name}".equals(dv)){
										s = "dep_name";
									}else if("${userid}".equals(dv)){
										s = "userid";
									}else if("${uname}".equals(dv)){
										s = "uname";
									}else if("${zhiyid}".equals(dv)){
										s = "zhiyid";
									}else if("${xaxis}".equals(dv)){
										s = "param.xaxis";
									}else{
										s = "'"+dv+"'";
									}
								}
								String dtype = pageid.split("\\.")[1];
								if("chart".equals(dtype)){
									if("textarea".equals(type) || "textfield".equals(type)){
										richvalue = "${empty " + attrs.get(key).toString().toLowerCase() + " ? "
												+ s + " : "
												+ attrs.get(key).toString().toLowerCase() + "}";
									}else{
										node.append(" value=\"${empty " + attrs.get(key).toString().toLowerCase() + " ? "
												+ s + " : "
												+ attrs.get(key).toString().toLowerCase() + "}\"");
									}
								}else{
									if("textarea".equals(type) || "textfield".equals(type)){
										richvalue = "${empty " + attrs.get(key).toString().toLowerCase() + " ? "
												+ s + " : "
												+ attrs.get(key).toString().toLowerCase() + "}";
									}else{
										 if(!"table.field".equals(type)){
											 node.append(" value=\"${empty " + attrs.get(key).toString().toLowerCase() + " ? "
													 + s + " : "
													 + attrs.get(key).toString().toLowerCase() + "}\"");
										 }
									}
								}
							}
							if(!CommonFun.isNe(attrs.get("maxlength_show"))){
								node.append(" datatype=\"script\" ");
								String keyname = attrs.get(key).toString().toLowerCase();
								StringBuffer bb = new StringBuffer();
								bb.append(" var html = []; ");
								bb.append(" if(record."+keyname+" && record."+keyname+".length>"+attrs.get("maxlength_show")+"){ ");
								bb.append(" 	html.push('<a href=\"javascript:void(0);\" title=\"'+record."+keyname+"+'\" style=\"cursor: pointer;\"'); ");
								bb.append(" 	html.push(' onclick=\"show_maxlength(this);\" '); ");
								bb.append(" 	html.push('>'); ");
								bb.append(" 	html.push(record."+keyname+".substring(0,"+attrs.get("maxlength_show")+")+'……'); ");
								bb.append(" 	html.push('</a>'); ");
								bb.append(" }else{ ");
								bb.append(" 	html.push(record."+keyname+"); ");
								bb.append(" } ");
								bb.append(" return html.join(''); ");
								text = bb.toString();
							}
						}else{
							node.append(" value=\"$[" + attrs.get(key).toString().toLowerCase() + "]\" ");
						}
					}
				}
			}
		}
		if("table".equals(type)){
			node.append(" action=\"application.bill.queryTable\" ");
			//node.append(" autoload=\"true\" ");
			node.append(" select=\"multi\" ");
			/*if(!CommonFun.isNe(attrs.get("totals"))) {
				node.append(" totals=\":totals\" ");
			}*/
			//node.append(" fitwidth=\":table_fitwidth\" ");
		}
		if("textarea".equals(type) || "textfield".equals(type)){
			node.append(">");
			node.append(richvalue);
		}else{
			node.append(">");
		}
		if(text != null){
			node.append(text);
		}
		if("richeditor".equals(type)){
			node.append(richvalue);
		}else if("table.field".equals(type)){
			
		}else if ("page".equals(type)) {
			String dtype = pageid.split("\\.")[1];
			if("materials".equals(dtype)){
				node.append("<%\r\n");
				node.append("\tMap<String, Object> a = null;\r\n");
				node.append("\t\ta = BillAction.init(\"" + pageid + "\", (String)request.getParameter(\""+((attrs.get("tablekey") == null)?"":(attrs.get("tablekey").toString()).toLowerCase())+"\"));\r\n");
				node.append("\tIterator<String> it = a.keySet().iterator(); \r\n");
				node.append("\twhile (it.hasNext()) {   \r\n");
				node.append("\t	String key = it.next();  \r\n");
				node.append("\t	pageContext.setAttribute(key, a.get(key));   \r\n");
				node.append("\t}  \r\n");
				node.append("\t	pageContext.setAttribute(\"xaxis\", request.getParameter(\"xaxis\"));   \r\n");
				node.append("%>\r\n");
				node.append("<input type=\"hidden\" name=\"pageid\" value=\"${pageid}\">\r\n");
				node.append("<input type=\"hidden\" name=\"company_id\" value=\"${company_id}\">\r\n");
				node.append("<input type=\"hidden\" name=\""+attrs.get("tablekey")+"\" value=\"${"+((attrs.get("tablekey") == null)?"":(attrs.get("tablekey").toString()).toLowerCase())+"}\">\r\n");
				node.append("<input type=\"hidden\" name=\"tablekey_\" value=\""+attrs.get("tablekey")+"\">\r\n");
				node.append("<input type=\"hidden\" name=\"djbs\" value=\"${djbs}\">\r\n");
				node.append("<input type=\"hidden\" name=\"djlx\" value=\"${djlx}\">\r\n");
				node.append("<input type=\"hidden\" name=\"_CRSID\" value=\"${_CRSID}\">\r\n");
			}else{
				node.append("<%\r\n");
				node.append("\tMap<String, Object> a = null;\r\n");
				node.append("\tif(!CommonFun.isNe(request.getParameter(\"djbh\"))){\r\n");
				node.append("\t\ta = BillAction.init(\"" + pageid + "\", (String)request.getParameter(\"djbh\"));\r\n");
				node.append("\t} else if(!CommonFun.isNe(request.getParameter(\"gzid\"))){\r\n");
				node.append("\t\ta = BillAction.initFromCache(\"" + pageid + "\", (String)request.getParameter(\"gzid\"));\r\n");
				
				node.append("\t} else if(!CommonFun.isNe(request.getParameter(\"p_dj_sn\"))){\r\n");
				node.append("\t\ta = BillAction.initFromCacheMX(request.getParameter(\"p_pageid\")");
				node.append(", \"" + pageid + "\"");
				node.append(", (String)request.getParameter(\"ptableid\")");
				node.append(", (String)request.getParameter(\"p_dj_sn\")");
				node.append(", (String)request.getParameter(\"p_dj_sort\")");
				node.append(", (String)request.getParameter(\"p_gzid\"));\r\n");
				
				node.append("\t} else {\r\n");
				node.append("\t\ta = BillAction.init(\"" + pageid + "\", null);\r\n");
				node.append("\t}\r\n");
				
				//node.append("\t if(!CommonFun.isNe(request.getParameter(\"p_pageid\"))){\r\n");
				node.append("\tMap<String, Object> pageParam = (Map<String, Object>)pageContext.getAttribute(\"pageParam\"); \r\n");
				node.append("\tIterator<String> it1 = pageParam.keySet().iterator(); \r\n");
				node.append("\twhile (it1.hasNext()) {   \r\n");
				node.append("\t	String key1 = it1.next();  \r\n");
				node.append("\t	pageContext.setAttribute(key1, pageParam.get(key1));   \r\n");
				node.append("\t}  \r\n");
				
				//node.append("\t} \r\n");
				
				node.append("\tIterator<String> it = a.keySet().iterator(); \r\n");
				node.append("\twhile (it.hasNext()) {   \r\n");
				node.append("\t	String key = it.next();  \r\n");
				node.append("\t	pageContext.setAttribute(key, a.get(key));   \r\n");
				node.append("\t}  \r\n");
				
				node.append("%>\r\n");
				if(p!=null && p.get("caozuo")!=null){
					node.append("<input type=\"hidden\" name=\"caozuo\" value=\"${param.caozuo}\">\r\n");
				}
				node.append("\t <c:if test=\"${not empty param.fromtable }\">  \r\n");
				node.append("<input type=\"hidden\" name=\"fromtable\" value=\"${param.fromtable}\">\r\n");
				node.append("\t </c:if> \r\n");
				node.append("\t <c:if test=\"${not empty param.ptableid }\">  \r\n");
				node.append("<input type=\"hidden\" name=\"ptableid\" value=\"${param.ptableid}\">\r\n");
				node.append("\t </c:if> \r\n");
				node.append("\t <c:if test=\"${not empty param.p_pageid }\">  \r\n");
				node.append("<input type=\"hidden\" name=\"p_pageid\" value=\"${param.p_pageid}\">\r\n");
				node.append("\t </c:if> \r\n");
				node.append("\t <c:if test=\"${not empty param.p_dj_sn }\">  \r\n");
				node.append("<input type=\"hidden\" name=\"p_dj_sn\" value=\"${param.p_dj_sn}\">\r\n");
				node.append("\t </c:if> \r\n");
				node.append("\t <c:if test=\"${not empty param.p_dj_sort }\">  \r\n");
				node.append("<input type=\"hidden\" name=\"p_dj_sort\" value=\"${param.p_dj_sort}\">\r\n");
				node.append("\t </c:if> \r\n");
				node.append("\t <c:if test=\"${not empty param.p_gzid }\">  \r\n");
				node.append("<input type=\"hidden\" name=\"p_gzid\" value=\"${param.p_gzid}\">\r\n");
				node.append("\t </c:if> \r\n");
				
				node.append("<input type=\"hidden\" name=\"djbs\" value=\"${djbs}\">\r\n");
				node.append("<input type=\"hidden\" name=\"_CRSID\" value=\"${_CRSID}\">\r\n");
				node.append("<input type=\"hidden\" name=\"djlx\" value=\"${djlx}\">\r\n");
				node.append("<input type=\"hidden\" name=\"gzid\" value=\"${gzid}\">\r\n");
				node.append("<input type=\"hidden\" name=\"pageid\" value=\"${pageid}\">\r\n");
				node.append("<input type=\"hidden\" name=\"company_id\" value=\"${company_id}\">\r\n");
				node.append("\t <c:if test=\"${empty param.ptableid }\">  \r\n");
				node.append("<input type=\"hidden\" name=\"task_id\" value=\"${param.task_id}\">\r\n");
				node.append("<input type=\"hidden\" name=\"djbh\" value=\"${djbh}\">\r\n");
				node.append("<input type=\"hidden\" name=\"clientid\" value=\"${clientid}\">\r\n");
				node.append("<input type=\"hidden\" name=\"zhiyid\" value=\"${zhiyid}\">\r\n");
				node.append("<input type=\"hidden\" name=\"username\" value=\"${username}\">\r\n");
				
				node.append("<input type=\"hidden\" name=\"userid\" value=\"${userid}\">\r\n");
				node.append("<input type=\"hidden\" name=\"uname\" value=\"${uname}\">\r\n");
				
				node.append("<input type=\"hidden\" name=\"lgnname\" value=\"${lgnname}\">\r\n");
				node.append("<input type=\"hidden\" name=\"rq\" value=\"${rq}\">\r\n");
				node.append("<input type=\"hidden\" name=\"kaiprq\" value=\"${kaiprq}\">\r\n");
				node.append("<input type=\"hidden\" name=\"riqi\" value=\"${riqi}\">\r\n");
				node.append("<input type=\"hidden\" name=\"ontime\" value=\"${ontime}\">\r\n");
				node.append("<input type=\"hidden\" name=\"jigid\" value=\"${jigid}\">\r\n");
				node.append("<input type=\"hidden\" name=\"jigname\" value=\"${jigname}\">\r\n");
				node.append("<input type=\"hidden\" name=\"dep_code\" value=\"${dep_code}\">\r\n");
				node.append("<input type=\"hidden\" name=\"dep_name\" value=\"${dep_name}\">\r\n");
				node.append("<input type=\"hidden\" name=\"xaxis\" value=\"${pageParam.xaxis}\">\r\n");
				node.append("\t </c:if> \r\n");
			}
			if(p!=null && p.get("caozuo")!=null){
				node.append("<c:import url='../../../../application/view/task/audit.jsp?caozuo="+p.get("caozuo")+"'></c:import>\r\n");
			}
			if(p!=null && p.get("doc")!=null){
				node.append("<c:import url='../../../../application/view/task/submitprocess.jsp'></c:import>\r\n");
			}
			/*if("chart".equals(dtype)){
				node.append("<div class=\"col-md-6\">");
			}*/
		}
		if (contents != null) {
			for (Map<String, Object> c : contents) {
				node.append(objjsp(pageid, c, p));
			}
			/*String dtype = pageid.split("\\.")[1];
			if("query".equals(dtype)){
				if("tablefields".equals(control.get("type"))){
					node.append("<s:table.field name=\"oper\" label=\"操作\" datatype=\"script\" align=\"center\">\r\n");
					node.append("if (+record.state == 1) {\r\n");
					node.append("return \"<font color=\'green\'>已办结</font>\";\r\n");
					node.append("} else if (+record.state == 0){\r\n");
					node.append("return \"办理中\";\r\n");
					node.append("}else{\r\n");
					
					node.append("}\r\n");
					node.append("</s:table.field>\r\n");
				}
			}*/
		}
		if ("page".equals(type)) {
			/*String dtype = pageid.split("\\.")[1];
			if("chart".equals(dtype)){
				node.append("</div>");
				node.append("<div class=\"col-md-6\">");
				node.append(" <div class =\"chart_container\"  style=\"min-width:400px;height:400px;\"></div>");
				node.append("</div>");
			}*/
			if(p!=null && p.get("suggestions")!=null){
				node.append("<c:import url='../../../../application/view/suggestions/suggestions.jsp'></c:import>\r\n");
			}
		}
		node.append("</s:" + type + ">\r\n");
		/*if ("page".equals(type)) {
			String dtype = pageid.split("\\.")[1];
			if("chart".equals(dtype)){
				node.append("<script type=\"text/javascript\">");	
				node.append("$.initChart();");	
				node.append("</script>");	
			}
		}*/
		return node;
	}


	private static void saveJSP(String definition, String jspFileName) throws Exception {
		FileOutputStream fos = null;
		try {
			File jspFile = new File(jspFileName);
			if (!jspFile.getParentFile().exists()) {
				jspFile.getParentFile().mkdirs();
			}
			if (!jspFile.exists()) {
				jspFile.createNewFile();
			}
			fos = new FileOutputStream(jspFile);
			fos.write(definition.getBytes("UTF-8"));
			fos.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private static void Transform(String xmlFileName, String xslFileName, String htmlFileName) {
		try {
			TransformerFactory tFac = TransformerFactory.newInstance();
			Source xslSource = new StreamSource(xslFileName);
			Transformer t = tFac.newTransformer(xslSource);
			t.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			t.setOutputProperty(OutputKeys.METHOD, "html");
			File xmlFile = new File(xmlFileName);
			File htmlFile = new File(htmlFileName);
			if (!htmlFile.exists()) {
				htmlFile.createNewFile();
			}
			Source source = new StreamSource(xmlFile);
			javax.xml.transform.Result result = new StreamResult(htmlFile.toURI().getPath());
			t.transform(source, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String xmlFileName = "cooperop/themes/classic1.0/views/page.xml";
//		String xslFileName = "cooperop/themes/classic1.0/views/view.xsl";
//		String htmlFileName = "cooperop/WEB-INF/apps/crdc/view/test.jsp";
//		Transform(xmlFileName, xslFileName, htmlFileName);
		String jspFileName = "cooperop/WEB-INF/apps/crdc/view/test2.jsp";
		Map<String, Object> definition = new HashMap<String, Object>();
		definition.put("type", "page");
		Map<String, Object> attrs = new HashMap<String, Object>();
		definition.put("attrs", attrs);
		attrs.put("modal", "true");
		attrs.put("disloggedin", "true");
		attrs.put("dispermission", "true");
		attrs.put("title", "测试2");
		List<Map<String, Object>> contents = new ArrayList<Map<String, Object>>();
		definition.put("contents", contents);
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("type", "toolbar");
		contents.add(content);content = new HashMap<String, Object>();
		content.put("type", "row");
		contents.add(content);
		try {
			saveJSP(obj2jsp("crdc.test2", definition,null).toString(), jspFileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
