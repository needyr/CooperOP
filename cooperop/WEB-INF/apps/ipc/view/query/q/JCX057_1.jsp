<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="JCX057_1" init-action="" description="医嘱审查流水查询" system_product_code="ipc" title="医嘱审查流水查询" remark="" flag="q" type="query"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("ipc.query.q.JCX057_1", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("ipc.query.q.JCX057_1", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "ipc.query.q.JCX057_1", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("ipc.query.q.JCX057_1", null);
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
<s:row><s:form label="医嘱审查流水查询" icon="fa fa-list-alt" color="default" border="1" cols="4" istoorbar="Y" app_collapsed="Y"><s:row type="frow"><s:datefield label="开始日期" cols="1" name="start_date" value="${empty start_date ? rq : start_date}" placeholder="" fdtype="字符" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision=""></s:datefield>
<s:datefield label="截止日期" cols="1" name="end_date" value="${empty end_date ? rq : end_date}" placeholder="" fdtype="字符" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision=""></s:datefield>
<s:textfield label="操作员" cols="1" name="zhiyname" placeholder="" fdtype="字符" defaultValue="${username}" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="20" precision="">${empty zhiyname ? username : zhiyname}</s:textfield>
</s:row>
<s:row type="frow"><s:select color="grey" label="科室类型" name="d_type" value="${empty d_type ? '1' : d_type}" placeholder="" fdtype="字符" cols="1" defaultValue="1" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" clabel="住院" contentIndex="0" contentvalue="1" maxlength="50" precision=""><s:option label="住院" contentIndex="0" value="1"></s:option>
<s:option label="门诊" contentIndex="1" value="2"></s:option>
</s:select>
<s:textfield color="grey" label="科室名称" name="dept_name" placeholder="" fdtype="字符" cols="1" defaultValue="全部" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="zl_select_JCX057_1_01" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty dept_name ? '全部' : dept_name}</s:textfield>
<s:textfield label="问题类型" cols="1" name="sort_name" placeholder="" fdtype="字符" defaultValue="全部" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="zl_select_JCX057_1_02" out_action="" tableid="" expressions="" maxlength="50" precision="">${empty sort_name ? '全部' : sort_name}</s:textfield>
</s:row>
<s:row type="frow"><s:select label="医嘱/处方" cols="1" name="p_type" value="${empty p_type ? '0' : p_type}" placeholder="" fdtype="字符" defaultValue="0" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" clabel="全部" contentIndex="0" contentvalue="0" maxlength="8" precision=""><s:option label="全部" contentIndex="0" value="0"></s:option>
<s:option label="医嘱" contentIndex="1" value="1"></s:option>
<s:option label="处方" contentIndex="2" value="2"></s:option>
</s:select>
<s:textfield label="患者姓名" cols="1" name="patient_name" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="1280" precision="">${empty patient_name ? '' : patient_name}</s:textfield>
<s:textfield label="住院号" cols="1" name="patient_no" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty patient_no ? '' : patient_no}</s:textfield>
</s:row>
<s:row type="frow" ishidden="ishidden"><s:textfield label="职员内码" cols="1" name="zhiyid" placeholder="" fdtype="字符" defaultValue="${userid}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="11" precision="">${empty zhiyid ? userid : zhiyid}</s:textfield>
</s:row>
<s:toolbar><s:button label="运行" icon="glyphicon glyphicon-play-circle" nextfocusfield="" action="querymx" color=""></s:button>
</s:toolbar>
</s:form>
<s:tabpanel color="grey-silver" cols="4"><s:table label="全部医嘱审查" cols="4" color="grey-silver" active="true" isdesign_="Y" tableid="JCX057_1" icon="glyphicon glyphicon-list-alt" djselect="" tablekey="" limit="25" toIndex="0" sort="false" autoload="false" istoorbar="Y" myIndex="0" fields="" app_table="false" height="" totals="" fitwidth="false" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="sort_name" fdtype="字符" format="" dictionary="" label="问题类型" size="50" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="0" is_href="" app_field=""></s:table.field>
<s:table.field name="sys_check_level_name" fdtype="字符" format="" dictionary="" label="警示级别" size="50" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="40" fieldorder="1" is_href="" app_field=""></s:table.field>
<s:table.field name="dept_name" fdtype="字符" format="" dictionary="" label="科室名称" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="100" fieldorder="2" is_href="" app_field=""></s:table.field>
<s:table.field name="patient_name" value="$[patient_name]"  fdtype="字符" format="" dictionary="" label="病人姓名" size="1280" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="3" is_href="hospital_common.additional.patientInfo" app_field="" datatype="template"><s:textfield readonly="true" name="patient_name" value="$[patient_name]"  fdtype="字符" format="" dictionary="" size="1280" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="3" is_href="hospital_common.additional.patientInfo" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="leixing" fdtype="字符" format="" dictionary="" label="类型" size="50" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="40" fieldorder="4" is_href="" app_field=""></s:table.field>
<s:table.field name="tag" fdtype="字符" format="" dictionary="" label="组" size="2" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="20" fieldorder="5" is_href="" app_field=""></s:table.field>
<s:table.field name="repeat_indicator" fdtype="字符" format="" dictionary="" label="长/临" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="6" is_href="" app_field=""></s:table.field>
<s:table.field name="order_class" fdtype="字符" format="" dictionary="" label="医嘱类别" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="50" fieldorder="7" is_href="" app_field=""></s:table.field>
<s:table.field name="administration" fdtype="字符" format="" dictionary="" label="用药途径" size="32" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="8" is_href="" app_field=""></s:table.field>
<s:table.field name="start_date_time" fdtype="字符" format="" dictionary="" label="开始时间" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="140" fieldorder="9" is_href="" app_field=""></s:table.field>
<s:table.field name="stop_date_time" fdtype="字符" format="" dictionary="" label="停止时间" size="19" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="140" fieldorder="10" is_href="" app_field=""></s:table.field>
<s:table.field name="order_text" value="$[order_text]"  fdtype="字符" format="" dictionary="" label="医嘱正文" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="200" fieldorder="11" is_href="hospital_common.additional.instruction" app_field="" datatype="template"><s:textfield readonly="true" name="order_text" value="$[order_text]"  fdtype="字符" format="" dictionary="" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="200" fieldorder="11" is_href="hospital_common.additional.instruction" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="dosage" fdtype="字符" format="" dictionary="" label="一次用量" size="16" maxlength_show="" digitsize="4" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="12" is_href="" app_field=""></s:table.field>
<s:table.field name="frequency" fdtype="字符" format="" dictionary="" label="频次" size="16" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="100" fieldorder="13" is_href="" app_field=""></s:table.field>
<s:table.field name="description" datatype="script"  fdtype="文本" format="" dictionary="" label="审查问题描述" size="" maxlength_show="120" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="600" fieldorder="14" is_href="" app_field=""> var html = [];  if(record.description && record.description.length>120){  	html.push('<a href="javascript:void(0);" title="'+record.description+'" style="cursor: pointer;"');  	html.push(' onclick="show_maxlength(this);" ');  	html.push('>');  	html.push(record.description.substring(0,120)+'……');  	html.push('</a>');  }else{  	html.push(record.description);  }  return html.join(''); </s:table.field>
<s:table.field name="reference" datatype="script"  fdtype="" format="" dictionary="" label="参考文献" size="300" maxlength_show="60" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="250" fieldorder="15" is_href="" app_field=""> var html = [];  if(record.reference && record.reference.length>60){  	html.push('<a href="javascript:void(0);" title="'+record.reference+'" style="cursor: pointer;"');  	html.push(' onclick="show_maxlength(this);" ');  	html.push('>');  	html.push(record.reference.substring(0,60)+'……');  	html.push('</a>');  }else{  	html.push(record.reference);  }  return html.join(''); </s:table.field>
<s:table.field name="order_no" fdtype="字符" format="" dictionary="" label="医嘱序号" size="32" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="16" is_href="" app_field=""></s:table.field>
<s:table.field name="order_status" fdtype="字符" format="" dictionary="" label="医嘱状态" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="17" is_href="" app_field=""></s:table.field>
<s:table.field name="freq_detail" fdtype="字符" format="" dictionary="" label="执行时间" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="18" is_href="" app_field=""></s:table.field>
<s:table.field name="doctor" fdtype="字符" format="" dictionary="" label="医生" size="32" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="19" is_href="" app_field=""></s:table.field>
<s:table.field name="stop_doctor" fdtype="字符" format="" dictionary="" label="停嘱医生" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="80" fieldorder="20" is_href="" app_field=""></s:table.field>
<s:table.field name="nurse" fdtype="字符" format="" dictionary="" label="护士" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="21" is_href="" app_field=""></s:table.field>
<s:table.field name="patient_id" fdtype="字符" format="" dictionary="" label="病人标识号" size="32" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="22" is_href="" app_field=""></s:table.field>
<s:table.field name="visit_id" fdtype="字符" format="" dictionary="" label="本次住院标识" size="32" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="23" is_href="" app_field=""></s:table.field>
<s:table.field name="auto_audit_time" fdtype="字符" format="" dictionary="" label="智能审查时间" size="23" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="24" is_href="" app_field=""></s:table.field>
<s:table.field name="pharmacist_exam_time" fdtype="字符" format="" dictionary="" label="药师审查时间" size="23" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="25" is_href="" app_field=""></s:table.field>
<s:table.field name="yaoshi_name" fdtype="字符" format="" dictionary="" label="药师名称" size="50" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="26" is_href="" app_field=""></s:table.field>
<s:table.field name="yaoshi_advice" fdtype="字符" format="" dictionary="" label="药师意见" size="1000" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="27" is_href="" app_field=""></s:table.field>
<s:table.field name="comment_username" fdtype="字符" format="" dictionary="" label="点评人" size="50" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="28" is_href="" app_field=""></s:table.field>
<s:table.field name="comment_result_message" fdtype="字符" format="" dictionary="" label="点评结果" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="29" is_href="" app_field=""></s:table.field>
<s:table.field name="comment_system_code" fdtype="字符" format="" dictionary="" label="点评代码" size="50" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="30" is_href="" app_field=""></s:table.field>
<s:table.field name="comment_name" fdtype="字符" format="" dictionary="" label="点评问题名称" size="50" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="31" is_href="" app_field=""></s:table.field>
<s:table.field name="comment_datetime" fdtype="字符" format="" dictionary="" label="点评时间" size="19" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="32" is_href="" app_field=""></s:table.field>
</s:table.fields>
<s:toolbar><s:button label="刷新" icon="icon-reload" nextfocusfield="" action="querymx" color=""></s:button>
<s:button label="导出" icon="glyphicon glyphicon-random" size="btn-sm" nextfocusfield="" action="exportexcel" color=""></s:button>
</s:toolbar>
</s:table>
<s:table label="不合理医嘱" cols="4" color="grey-silver" active="false" isdesign_="Y" tableid="JCX057_2" icon="glyphicon glyphicon-question-sign" djselect="" tablekey="" limit="25" toIndex="1" sort="false" autoload="false" istoorbar="Y" myIndex="1" fields="" app_table="false" height="" totals="" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="patient_name" value="$[patient_name]"  fdtype="字符" format="" dictionary="" label="病人姓名" size="1280" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="0" is_href="hospital_common.additional.patientInfo" app_field="" datatype="template"><s:textfield readonly="true" name="patient_name" value="$[patient_name]"  fdtype="字符" format="" dictionary="" size="1280" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="0" is_href="hospital_common.additional.patientInfo" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="dept_name" fdtype="字符" format="" dictionary="" label="科室名称" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="1" is_href="" app_field=""></s:table.field>
<s:table.field name="check_level_name" fdtype="字符" format="" dictionary="" label="严重程度" size="50" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="2" is_href="" app_field=""></s:table.field>
<s:table.field name="sort_name" fdtype="字符" format="" dictionary="" label="问题类别" size="50" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="50" fieldorder="3" is_href="" app_field=""></s:table.field>
<s:table.field name="ordermessage" fdtype="字符" format="" dictionary="" label="医嘱信息" size="2000" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="400" fieldorder="4" is_href="" app_field=""></s:table.field>
<s:table.field name="is_shenc_pass" value="$[is_shenc_pass]"  fdtype="字符" format="" dictionary="" label="审查合理" size="1" maxlength_show="" digitsize="1" precision="" available="true" sort="true" controltype="textfield" align="left" width="80" fieldorder="5" is_href="hospital_common.additional.patientInfo" app_field="" datatype="template"><s:textfield readonly="true" name="is_shenc_pass" value="$[is_shenc_pass]"  fdtype="字符" format="" dictionary="" size="1" maxlength_show="" digitsize="1" precision="" available="true" sort="true" controltype="textfield" align="left" width="80" fieldorder="5" is_href="hospital_common.additional.patientInfo" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="description" datatype="script"  fdtype="文本" format="" dictionary="" label="描述" size="" maxlength_show="500" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="500" fieldorder="6" is_href="" app_field=""> var html = [];  if(record.description && record.description.length>500){  	html.push('<a href="javascript:void(0);" title="'+record.description+'" style="cursor: pointer;"');  	html.push(' onclick="show_maxlength(this);" ');  	html.push('>');  	html.push(record.description.substring(0,500)+'……');  	html.push('</a>');  }else{  	html.push(record.description);  }  return html.join(''); </s:table.field>
<s:table.field name="reference" datatype="script"  fdtype="字符" format="" dictionary="" label="参考" size="500" maxlength_show="50" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="200" fieldorder="7" is_href="" app_field=""> var html = [];  if(record.reference && record.reference.length>50){  	html.push('<a href="javascript:void(0);" title="'+record.reference+'" style="cursor: pointer;"');  	html.push(' onclick="show_maxlength(this);" ');  	html.push('>');  	html.push(record.reference.substring(0,50)+'……');  	html.push('</a>');  }else{  	html.push(record.reference);  }  return html.join(''); </s:table.field>
<s:table.field name="audit_source_type" fdtype="字符" format="" dictionary="" label="审查来源" size="50" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="80" fieldorder="8" is_href="" app_field=""></s:table.field>
<s:table.field name="check_datetime" fdtype="字符" format="" dictionary="" label="审查日期" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="150" fieldorder="9" is_href="" app_field=""></s:table.field>
<s:table.field name="yaoshi_advice" fdtype="字符" format="" dictionary="" label="药师意见" size="1000" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="150" fieldorder="10" is_href="" app_field=""></s:table.field>
<s:table.field name="doctor_advice" fdtype="字符" format="" dictionary="" label="医生意见" size="100" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="150" fieldorder="11" is_href="" app_field=""></s:table.field>
<s:table.field name="auto_audit_id" fdtype="字符" format="" dictionary="" label="审查流水ID" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="200" fieldorder="12" is_href="" app_field=""></s:table.field>
</s:table.fields>
<s:toolbar><s:button label="刷新" icon="icon-reload" nextfocusfield="" action="querymx" color=""></s:button>
<s:button label="导出" icon="glyphicon glyphicon-random" size="btn-sm" nextfocusfield="" action="exportexcel" color=""></s:button>
</s:toolbar>
</s:table>
</s:tabpanel>
</s:row>
</s:page>
