<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="BB01_3_1_1" init-action="" description="审查严重程度[更多]->科室->医生->患者" system_product_code="ipc" title="审查严重程度[更多]➩科室➩医生➩病人" remark="审查严重程度[更多]➩科室➩医生➩病人" flag="chart" type="chart"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("ipc.chart.chart.BB01_3_1_1", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("ipc.chart.chart.BB01_3_1_1", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "ipc.chart.chart.BB01_3_1_1", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("ipc.chart.chart.BB01_3_1_1", null);
	}
	Map<String, Object> pageParam = (Map<String, Object>)pageContext.getAttribute("pageParam"); 
	Iterator<String> it1 = pageParam.keySet().iterator(); 
	while (it1.hasNext()) {   
		String key1 = it1.next();  
		pageContext.setAttribute(key1, pageParam.get(key1));   
	}  
	Iterator<String> it = a.keySet().iterator(); 
	while (it.hasNext()) {   
		String key = it.next();  
		pageContext.setAttribute(key, a.get(key));   
	}  
%>
	 <c:if test="${not empty param.fromtable }">  
<input type="hidden" name="fromtable" value="${param.fromtable}">
	 </c:if> 
	 <c:if test="${not empty param.ptableid }">  
<input type="hidden" name="ptableid" value="${param.ptableid}">
	 </c:if> 
	 <c:if test="${not empty param.p_pageid }">  
<input type="hidden" name="p_pageid" value="${param.p_pageid}">
	 </c:if> 
	 <c:if test="${not empty param.p_dj_sn }">  
<input type="hidden" name="p_dj_sn" value="${param.p_dj_sn}">
	 </c:if> 
	 <c:if test="${not empty param.p_dj_sort }">  
<input type="hidden" name="p_dj_sort" value="${param.p_dj_sort}">
	 </c:if> 
	 <c:if test="${not empty param.p_gzid }">  
<input type="hidden" name="p_gzid" value="${param.p_gzid}">
	 </c:if> 
<input type="hidden" name="djbs" value="${djbs}">
<input type="hidden" name="_CRSID" value="${_CRSID}">
<input type="hidden" name="djlx" value="${djlx}">
<input type="hidden" name="gzid" value="${gzid}">
<input type="hidden" name="pageid" value="${pageid}">
<input type="hidden" name="company_id" value="${company_id}">
	 <c:if test="${empty param.ptableid }">  
<input type="hidden" name="task_id" value="${param.task_id}">
<input type="hidden" name="djbh" value="${djbh}">
<input type="hidden" name="clientid" value="${clientid}">
<input type="hidden" name="zhiyid" value="${zhiyid}">
<input type="hidden" name="username" value="${username}">
<input type="hidden" name="userid" value="${userid}">
<input type="hidden" name="uname" value="${uname}">
<input type="hidden" name="lgnname" value="${lgnname}">
<input type="hidden" name="rq" value="${rq}">
<input type="hidden" name="kaiprq" value="${kaiprq}">
<input type="hidden" name="riqi" value="${riqi}">
<input type="hidden" name="ontime" value="${ontime}">
<input type="hidden" name="jigid" value="${jigid}">
<input type="hidden" name="jigname" value="${jigname}">
<input type="hidden" name="dep_code" value="${dep_code}">
<input type="hidden" name="dep_name" value="${dep_name}">
<input type="hidden" name="xaxis" value="${pageParam.xaxis}">
	 </c:if> 
<s:row><s:form label="审查结果下钻" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4" istoorbar="Y"><s:row type="frow"><s:textfield label="严重程度" cols="1" name="check_level_name" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="50" precision="">${empty check_level_name ? '' : check_level_name}</s:textfield>
<s:textfield label="科室名称" cols="1" name="dept_name" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty dept_name ? '' : dept_name}</s:textfield>
<s:textfield label="医生" cols="1" name="doctor" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="32" precision="">${empty doctor ? '' : doctor}</s:textfield>
<s:radio label="" cols="1" name="is_after" value="${empty is_after ? '' : is_after}" placeholder="是否事后审查" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" clabel="全部" contentIndex="0" contentvalue="0" maxlength="2" precision=""><s:option label="全部" contentIndex="0" value="0"></s:option>
<s:option label="事后审查" contentIndex="1" value="1"></s:option>
<s:option label="前置审查" contentIndex="2" value="2"></s:option>
</s:radio>
</s:row>
<s:toolbar><s:button label="刷新" icon="icon-reload" nextfocusfield="" action="querymx" color=""></s:button>
<s:button label="导出" icon="icon-shuffle" size="btn-sm" nextfocusfield="" action="exportexcel" color=""></s:button>
</s:toolbar>
</s:form>
</s:row>
<s:row ishidden="ishidden"><s:form label="表单" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4"><s:row type="frow"><s:datefield label="开始日期" cols="1" name="start_date" value="${empty start_date ? rq : start_date}" placeholder="" fdtype="整数" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="" precision=""></s:datefield>
<s:datefield label="截止时间" cols="1" name="end_date" value="${empty end_date ? rq : end_date}" placeholder="" fdtype="字符" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision=""></s:datefield>
</s:row>
<s:row type="frow"><s:select label="科室类型" cols="1" name="d_type" value="${empty d_type ? '' : d_type}" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" clabel="全部" contentIndex="0" contentvalue="0" maxlength="50" precision=""><s:option label="全部" contentIndex="0" value="0"></s:option>
<s:option label="住院" contentIndex="1" value="1"></s:option>
<s:option label="门诊" contentIndex="2" value="2"></s:option>
<s:option label="急诊" contentIndex="3" value="3"></s:option>
</s:select>
<s:select label="医嘱/处方" cols="1" name="p_type" value="${empty p_type ? '' : p_type}" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" clabel="全部" contentIndex="0" contentvalue="0" maxlength="8" precision=""><s:option label="全部" contentIndex="0" value="0"></s:option>
<s:option label="医嘱" contentIndex="1" value="1"></s:option>
<s:option label="处方" contentIndex="2" value="2"></s:option>
</s:select>
</s:row>
<s:row type="frow"><s:textfield label="科室代码" cols="1" name="dept_code" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty dept_code ? '' : dept_code}</s:textfield>
<s:textfield label="医生编号" cols="1" name="doctor_no" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="" precision="">${empty doctor_no ? '' : doctor_no}</s:textfield>
</s:row>
</s:form>
</s:row>
<s:row><s:tabpanel color="grey-silver" cols="4"><s:table label="审查严重程度[更多]➩科室➩医生➩患者" cols="4" color="grey-silver" active="true" isdesign_="Y" tableid="01" icon="icon-user-following" autoload="true" toIndex="0" sort="false" myIndex="0" djselect="" subpage_name="" limit="25" app_table="N" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="dept_name" value="${empty dept_name ? '' : dept_name}" fdtype="字符" format="" dictionary="" label="科室名称" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="100" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="patient_name" value="$[patient_name]"  fdtype="字符" format="" dictionary="" label="患者姓名" size="1280" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="150" fieldorder="" is_href="hospital_common.patient.index" app_field="" datatype="template"><s:textfield readonly="true" name="patient_name" value="$[patient_name]"  fdtype="字符" format="" dictionary="" size="1280" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="150" fieldorder="" is_href="hospital_common.patient.index" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="check_level_name" value="${empty check_level_name ? '' : check_level_name}" fdtype="字符" format="" dictionary="" label="严重程度" size="50" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="sort_name" value="${empty sort_name ? '' : sort_name}" fdtype="字符" format="" dictionary="" label="审查类型" size="50" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="80" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="related_drugs_show" value="${empty related_drugs_show ? '' : related_drugs_show}" datatype="script"  fdtype="字符" format="" dictionary="" label="药品信息" size="1024" maxlength_show="60" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="200" fieldorder="" is_href="" app_field=""> var html = [];  if(record.related_drugs_show && record.related_drugs_show.length>60){  	html.push('<a href="javascript:void(0);" title="'+record.related_drugs_show+'" style="cursor: pointer;"');  	html.push(' onclick="show_maxlength(this);" ');  	html.push('>');  	html.push(record.related_drugs_show.substring(0,60)+'……');  	html.push('</a>');  }else{  	html.push(record.related_drugs_show);  }  return html.join(''); </s:table.field>
<s:table.field name="audit_source_type" value="${empty audit_source_type ? '' : audit_source_type}" fdtype="字符" format="" dictionary="" label="审查来源" size="50" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="description" value="${empty description ? '' : description}" datatype="script"  fdtype="文本" format="" dictionary="" label="描述" size="" maxlength_show="60" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="350" fieldorder="" is_href="" app_field=""> var html = [];  if(record.description && record.description.length>60){  	html.push('<a href="javascript:void(0);" title="'+record.description+'" style="cursor: pointer;"');  	html.push(' onclick="show_maxlength(this);" ');  	html.push('>');  	html.push(record.description.substring(0,60)+'……');  	html.push('</a>');  }else{  	html.push(record.description);  }  return html.join(''); </s:table.field>
<s:table.field name="reference" value="${empty reference ? '' : reference}" datatype="script"  fdtype="字符" format="" dictionary="" label="参考" size="500" maxlength_show="8" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="100" fieldorder="" is_href="" app_field=""> var html = [];  if(record.reference && record.reference.length>8){  	html.push('<a href="javascript:void(0);" title="'+record.reference+'" style="cursor: pointer;"');  	html.push(' onclick="show_maxlength(this);" ');  	html.push('>');  	html.push(record.reference.substring(0,8)+'……');  	html.push('</a>');  }else{  	html.push(record.reference);  }  return html.join(''); </s:table.field>
<s:table.field name="check_datetime" value="${empty check_datetime ? '' : check_datetime}" fdtype="字符" format="" dictionary="" label="审查日期" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="160" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="doctor_advice" value="${empty doctor_advice ? '' : doctor_advice}" fdtype="字符" format="" dictionary="" label="医生意见" size="100" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="yaoshi_advice" value="${empty yaoshi_advice ? '' : yaoshi_advice}" fdtype="字符" format="" dictionary="" label="药师意见" size="1000" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="yaoshi_name" value="${empty yaoshi_name ? '' : yaoshi_name}" fdtype="字符" format="" dictionary="" label="药师名称" size="50" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
</s:table.fields>
</s:table>
</s:tabpanel>
</s:row>
</s:page>
