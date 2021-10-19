<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="gzfh_5_1" init-action="" description=" 运营简讯（外系科室）-工作负荷-手术台次、实际占用床日" system_product_code="iad" title="" remark="" flag="chart" type="chart"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("iad.chart.chart.gzfh_5_1", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("iad.chart.chart.gzfh_5_1", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "iad.chart.chart.gzfh_5_1", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("iad.chart.chart.gzfh_5_1", null);
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
<s:row><s:form label="	运营简讯（外系科室）-工作负荷" icon="fa fa-list-alt" color="red" border="1" cols="4" istoorbar="N"><s:row type="frow"></s:row>
</s:form>
</s:row>
<s:row><s:table label="" cols="4" color="grey-silver" isdesign_="Y" tableid="a1" icon="" djselect="" subpage_name="" autoload="true" limit="25" sort="false" app_table="N" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="visit_dept" value="${empty visit_dept ? '' : visit_dept}" fdtype="字符" format="" dictionary="" label="科室" size="32" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="mzrc_bq" value="${empty mzrc_bq ? '' : mzrc_bq}" fdtype="实数" format="" dictionary="" label="手术台次本期" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="mzrc_sq" value="${empty mzrc_sq ? '' : mzrc_sq}" fdtype="实数" format="###岁" dictionary="" label="手术台次上期" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="mzrc_qntq" value="${empty mzrc_qntq ? '' : mzrc_qntq}" fdtype="实数" format="" dictionary="" label="手术台次去年同期" size="12" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="mzrc_hb" value="${empty mzrc_hb ? '' : mzrc_hb}" fdtype="实数" format="" dictionary="" label="手术台次环比" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="mzrc_tb" value="${empty mzrc_tb ? '' : mzrc_tb}" fdtype="实数" format="" dictionary="" label="手术台次同比" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="bq" value="${empty bq ? '' : bq}" fdtype="实数" format="" dictionary="" label="三级手术台次本期" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="sq" value="${empty sq ? '' : sq}" fdtype="实数" format="" dictionary="" label="三级手术台次上期" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="qntq" value="${empty qntq ? '' : qntq}" fdtype="实数" format="" dictionary="" label="三级手术台次去年同期" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="tb" value="${empty tb ? '' : tb}" fdtype="字符" format="" dictionary="" label="三级手术台次同比" size="10" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="hb" value="${empty hb ? '' : hb}" fdtype="字符" format="" dictionary="" label="三级手术台次环比" size="10" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="bq_4" value="${empty bq_4 ? '' : bq_4}" fdtype="字符" format="" dictionary="" label="四级手术台次本期" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="sq_4" value="${empty sq_4 ? '' : sq_4}" fdtype="字符" format="" dictionary="" label="四级手术台次上期" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="qntq_4" value="${empty qntq_4 ? '' : qntq_4}" fdtype="字符" format="" dictionary="" label="四级手术台次去年同期" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="hb_4" value="${empty hb_4 ? '' : hb_4}" fdtype="字符" format="" dictionary="" label="四级手术台次环比" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="" controltype="" align="left" width="100" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="tb_4" value="${empty tb_4 ? '' : tb_4}" fdtype="字符" format="" dictionary="" label="四级手术台次同比" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="" controltype="" align="left" width="100" fieldorder="" is_href="" app_field=""></s:table.field>
</s:table.fields>
</s:table>
</s:row>
<s:row ishidden="ishidden"><s:form label="表单" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4"><s:row type="frow"><s:textfield label="就诊科室" cols="1" name="visit_dept" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="32" precision="">${empty visit_dept ? '' : visit_dept}</s:textfield>
<s:textfield label="操作日期" cols="1" name="caozrq" placeholder="" fdtype="字符" defaultValue="${rq}" format="yyyy-MM" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision="">${empty caozrq ? rq : caozrq}</s:textfield>
</s:row>
</s:form>
</s:row>
</s:page>
