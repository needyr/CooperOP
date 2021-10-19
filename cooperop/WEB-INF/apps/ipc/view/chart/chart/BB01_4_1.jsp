<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="BB01_4_1" init-action="" description="审查不合格药品[更多]->药品->科室" system_product_code="ipc" title="审查不合格药品[更多]➩药品➩科室" remark="审查不合格药品[更多]➩药品➩科室" flag="chart" type="chart"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("ipc.chart.chart.BB01_4_1", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("ipc.chart.chart.BB01_4_1", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "ipc.chart.chart.BB01_4_1", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("ipc.chart.chart.BB01_4_1", null);
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
<s:row><s:form label="筛选条件" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4" istoorbar="Y"><s:row type="frow"><s:textfield label="审查类型" cols="1" name="sort_name" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="50" precision="">${empty sort_name ? '' : sort_name}</s:textfield>
<s:textfield label="科室名称" cols="1" name="dept_name" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty dept_name ? '' : dept_name}</s:textfield>
<s:textfield label="科室代码" cols="1" name="dept_code" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty dept_code ? '' : dept_code}</s:textfield>
<s:radio label="" cols="1" name="is_after" value="${empty is_after ? '' : is_after}" placeholder="是否事后审查" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" clabel="全部" contentIndex="0" contentvalue="0" maxlength="2" precision=""><s:option label="全部" contentIndex="0" value="0"></s:option>
<s:option label="事后审查" contentIndex="1" value="1"></s:option>
<s:option label="前置审查" contentIndex="2" value="2"></s:option>
</s:radio>
<s:radio label="" cols="1" name="is_dengji" value="${empty is_dengji ? '2' : is_dengji}" placeholder="" fdtype="字符" defaultValue="2" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" clabel="全部" contentIndex="0" contentvalue="0" maxlength="10" precision=""><s:option label="全部" contentIndex="0" value="0"></s:option>
<s:option label="提示等级" contentIndex="1" value="1"></s:option>
<s:option label="拦截等级" contentIndex="2" value="2"></s:option>
</s:radio>
</s:row>
<s:toolbar><s:button label="刷新" icon="icon-reload" nextfocusfield="" action="querymx" color=""></s:button>
<s:button label="导出" icon="icon-shuffle" size="btn-sm" nextfocusfield="" action="exportexcel" color=""></s:button>
</s:toolbar>
</s:form>
</s:row>
<s:row ishidden="ishidden"><s:form label="审查不合格分类_条件" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4" istoorbar="N"><s:row type="frow"><s:datefield label="开始日期" cols="1" name="start_date" value="${empty start_date ? rq : start_date}" placeholder="" fdtype="整数" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="" precision=""></s:datefield>
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
<s:row type="frow"><s:textfield label="药品代码" cols="1" name="drug_code" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="32" precision="">${empty drug_code ? '' : drug_code}</s:textfield>
<s:textfield label="药品名称" cols="1" name="drug_name" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty drug_name ? '' : drug_name}</s:textfield>
</s:row>
<s:row type="frow"><s:textfield label="科室类型" cols="1" name="d_type" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="50" precision="">${empty d_type ? '' : d_type}</s:textfield>
<s:textfield label="医嘱/处方" cols="1" name="p_type" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="8" precision="">${empty p_type ? '' : p_type}</s:textfield>
</s:row>
</s:form>
</s:row>
<s:row><s:tabpanel color="grey-silver" cols="4"><s:table label="审查不合格药品[更多]➩药品➩科室" cols="4" color="grey-silver" active="true" isdesign_="Y" tableid="01" icon="glyphicon glyphicon-sort-by-attributes" autoload="true" toIndex="0" sort="true" myIndex="0" djselect="ipc.chart.chart.BB01_4_1_1" subpage_name="审查不合格分类[更多]➩[:drug_name]➩[:dept_name]➩患者" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="dept_name" value="${empty dept_name ? '' : dept_name}" fdtype="字符" format="" dictionary="" label="科室名称" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="150" fieldorder="0" is_href="" app_field=""></s:table.field>
<s:table.field name="用法用量" value="${empty 用法用量 ? '' : 用法用量}" fdtype="实数	" format="" dictionary="" label="用法用量" size="50" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="1" is_href="" app_field=""></s:table.field>
<s:table.field name="溶媒问题l" value="${empty 溶媒问题l ? '' : 溶媒问题l}" fdtype="实数	" format="" dictionary="" label="溶媒问题" size="50" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="2" is_href="" app_field=""></s:table.field>
<s:table.field name="给药途径" value="${empty 给药途径 ? '' : 给药途径}" fdtype="实数	" format="" dictionary="" label="给药途径" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="3" is_href="" app_field=""></s:table.field>
<s:table.field name="配伍禁忌" value="${empty 配伍禁忌 ? '' : 配伍禁忌}" fdtype="实数	" format="" dictionary="" label="配伍禁忌" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="4" is_href="" app_field=""></s:table.field>
<s:table.field name="相互作用" value="${empty 相互作用 ? '' : 相互作用}" fdtype="实数	" format="" dictionary="" label="相互作用" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="5" is_href="" app_field=""></s:table.field>
<s:table.field name="适应症" value="${empty 适应症 ? '' : 适应症}" fdtype="实数	" format="" dictionary="" label="适应症" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="6" is_href="" app_field=""></s:table.field>
<s:table.field name="禁忌症" value="${empty 禁忌症 ? '' : 禁忌症}" fdtype="实数	" format="" dictionary="" label="禁忌症" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="7" is_href="" app_field=""></s:table.field>
<s:table.field name="儿童老年人用药" value="${empty 儿童老年人用药 ? '' : 儿童老年人用药}" fdtype="实数	" format="" dictionary="" label="儿童老年人用药" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="8" is_href="" app_field=""></s:table.field>
<s:table.field name="病生理状" value="${empty 病生理状 ? '' : 病生理状}" fdtype="实数	" format="" dictionary="" label="病生理状" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="9" is_href="" app_field=""></s:table.field>
<s:table.field name="重复用药" value="${empty 重复用药 ? '' : 重复用药}" fdtype="实数	" format="" dictionary="" label="重复用药" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="10" is_href="" app_field=""></s:table.field>
<s:table.field name="医保" value="${empty 医保 ? '' : 医保}" fdtype="实数	" format="" dictionary="" label="医保" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="11" is_href="" app_field=""></s:table.field>
<s:table.field name="超级使用" value="${empty 超级使用 ? '' : 超级使用}" fdtype="实数	" format="" dictionary="" label="超级使用" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="12" is_href="" app_field=""></s:table.field>
<s:table.field name="无正当理由用药" value="${empty 无正当理由用药 ? '' : 无正当理由用药}" fdtype="实数	" format="" dictionary="" label="无正当理由用药" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="13" is_href="" app_field=""></s:table.field>
<s:table.field name="跨性别用药" value="${empty 跨性别用药 ? '' : 跨性别用药}" fdtype="实数	" format="" dictionary="" label="跨性别用药" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="14" is_href="" app_field=""></s:table.field>
<s:table.field name="药品使用问题" value="${empty 药品使用问题 ? '' : 药品使用问题}" fdtype="实数	" format="" dictionary="" label="药品使用问题" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="15" is_href="" app_field=""></s:table.field>
<s:table.field name="处方使用问题" value="${empty 处方使用问题 ? '' : 处方使用问题}" fdtype="实数	" format="" dictionary="" label="处方使用问题" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="16" is_href="" app_field=""></s:table.field>
<s:table.field name="其它" value="${empty 其它 ? '' : 其它}" fdtype="实数	" format="" dictionary="" label="其它" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="17" is_href="" app_field=""></s:table.field>
<s:table.field name="dept_code" value="$[dept_code]"  fdtype="字符" format="" dictionary="" label="科室代码" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="60" fieldorder="18" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="dept_code" value="$[dept_code]"  fdtype="字符" format="" dictionary="" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="60" fieldorder="18" is_href="" app_field="">null</s:textfield>
</s:table.field>
</s:table.fields>
</s:table>
</s:tabpanel>
</s:row>
</s:page>
