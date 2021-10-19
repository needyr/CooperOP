<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="PD001" init-action="" description="HIS数据配对导出" system_product_code="hospital_common" title="HIS数据配对导出" remark="HIS数据配对导出" flag="q" type="query"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("hospital_common.query.q.PD001", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("hospital_common.query.q.PD001", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "hospital_common.query.q.PD001", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("hospital_common.query.q.PD001", null);
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
<s:row><s:form label="HIS药品字典" icon="icon-book-open" color="red" border="1" cols="4" istoorbar="Y" app_collapsed="N"><s:row type="frow"><s:textfield label="药品代码" cols="1" name="drug_code" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="32" precision="">${empty drug_code ? '' : drug_code}</s:textfield>
<s:textfield label="药品名称" cols="2" name="drug_name" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty drug_name ? '' : drug_name}</s:textfield>
</s:row>
<s:row type="frow"><s:radio label="使用配对时间" cols="1" name="is_zx" value="${empty is_zx ? '否' : is_zx}" placeholder="" fdtype="字符" defaultValue="否" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" clabel="否" contentIndex="0" contentvalue="否" maxlength="2" precision=""><s:option label="否" contentIndex="0" value="否"></s:option>
<s:option label="是" contentIndex="1" value="是"></s:option>
</s:radio>
<s:datefield label="配对开始日期" cols="1" name="start_pd_time" value="${empty start_pd_time ? rq : start_pd_time}" placeholder="" fdtype="字符" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision=""></s:datefield>
<s:datefield label="配对结束日期" cols="1" name="end_pd_time" value="${empty end_pd_time ? rq : end_pd_time}" placeholder="" fdtype="字符" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision=""></s:datefield>
</s:row>
<s:toolbar><s:button label="查询" icon="glyphicon glyphicon-repeat" nextfocusfield="" action="querymx" color=""></s:button>
</s:toolbar>
</s:form>
<s:tabpanel color="grey-silver" cols="4"><s:table label="HIS药品信息" cols="4" color="grey-silver" active="true" isdesign_="Y" tableid="CX_01" icon="" djselect="" tablekey="" limit="25" toIndex="0" sort="true" autoload="false" istoorbar="N" myIndex="0" fields="" app_table="false" height="" totals="" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="p_key" fdtype="字符" format="" dictionary="" label="序号" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="0" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_code" fdtype="字符" format="" dictionary="" label="药品代码" size="32" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="1" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_name" fdtype="字符" format="" dictionary="" label="药品名称" size="128" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="300" fieldorder="2" is_href="" app_field=""></s:table.field>
<s:table.field name="druggg" fdtype="字符" format="" dictionary="" label="药品规格" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="3" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_unit" fdtype="字符" format="" dictionary="" label="药品单位" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="4" is_href="" app_field=""></s:table.field>
<s:table.field name="shengccj" fdtype="字符" format="" dictionary="" label="生产厂家" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="400" fieldorder="5" is_href="" app_field=""></s:table.field>
<s:table.field name="pizhwh" fdtype="字符" format="" dictionary="" label="批准文号" size="40" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="6" is_href="" app_field=""></s:table.field>
<s:table.field name="jixing" fdtype="字符" format="" dictionary="" label="剂型" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="7" is_href="" app_field=""></s:table.field>
<s:table.field name="usejlgg" fdtype="整数" format="" dictionary="" label="使用剂量" size="14" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="8" is_href="" app_field=""></s:table.field>
<s:table.field name="use_dw" fdtype="字符" format="" dictionary="" label="使用单位" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="9" is_href="" app_field=""></s:table.field>
<s:table.field name="input_code" fdtype="字符" format="" dictionary="" label="简称" size="100" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="10" is_href="" app_field=""></s:table.field>
<s:table.field name="sys_p_key" fdtype="字符" format="" dictionary="" label="标准库编号" size="100" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="11" is_href="" app_field=""></s:table.field>
<s:table.field name="property_toxi" fdtype="字符" format="" dictionary="" label="毒理分类" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="12" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_indicator" fdtype="字符" format="" dictionary="" label="药品类别" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="13" is_href="" app_field=""></s:table.field>
<s:table.field name="pd_time" fdtype="字符" format="" dictionary="" label="配对时间" size="23" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="14" is_href="" app_field=""></s:table.field>
<s:table.field name="use_30day" fdtype="字符" format="" dictionary="" label="30天是否在用" size="100" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="15" is_href="" app_field=""></s:table.field>
</s:table.fields>
</s:table>
<s:table label="标准库药品信息" cols="4" color="grey-silver" active="false" isdesign_="Y" tableid="CX_02" icon="" djselect="" tablekey="" limit="25" toIndex="1" sort="true" autoload="false" istoorbar="Y" myIndex="1" fields="" app_table="false" height="" totals="" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="p_key" fdtype="字符" format="" dictionary="" label="序号" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="0" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_code" fdtype="字符" format="" dictionary="" label="药品代码" size="32" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="1" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_name" fdtype="字符" format="" dictionary="" label="药品名称" size="128" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="300" fieldorder="2" is_href="" app_field=""></s:table.field>
<s:table.field name="druggg" fdtype="字符" format="" dictionary="" label="药品规格" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="300" fieldorder="3" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_unit" fdtype="字符" format="" dictionary="" label="药品单位" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="4" is_href="" app_field=""></s:table.field>
<s:table.field name="包装规格" fdtype="" format="" dictionary="" label="包装规格" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="5" is_href="" app_field=""></s:table.field>
<s:table.field name="包装单位" fdtype="" format="" dictionary="" label="包装单位" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="6" is_href="" app_field=""></s:table.field>
<s:table.field name="shengccj" fdtype="字符" format="" dictionary="" label="生产厂家" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="300" fieldorder="7" is_href="" app_field=""></s:table.field>
<s:table.field name="jixing" fdtype="字符" format="" dictionary="" label="剂型" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="8" is_href="" app_field=""></s:table.field>
<s:table.field name="use_30day" fdtype="" format="" dictionary="" label="30天使用" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="300" fieldorder="9" is_href="" app_field=""></s:table.field>
<s:table.field name="pizhwh" fdtype="字符" format="" dictionary="" label="批准文号" size="40" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="10" is_href="" app_field=""></s:table.field>
</s:table.fields>
<s:toolbar><s:button label="导出" icon="glyphicon glyphicon-random" nextfocusfield="" action="exportexcel" color=""></s:button>
</s:toolbar>
</s:table>
<s:table label="医生信息" cols="4" color="grey-silver" active="false" isdesign_="Y" tableid="CX03" icon="" djselect="" tablekey="" limit="25" toIndex="2" app_table="false" sort="false" autoload="false" istoorbar="Y" myIndex="2" fields="" height="" totals="" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="医生code" fdtype="" format="" dictionary="" label="医生code" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="医生姓名" fdtype="" format="" dictionary="" label="医生姓名" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="简拼" fdtype="" format="" dictionary="" label="简拼" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="科室code" fdtype="" format="" dictionary="" label="科室code" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="病区code" fdtype="" format="" dictionary="" label="病区code" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="是否有处方权限" fdtype="" format="" dictionary="" label="是否有处方权限" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="抗菌药三级权限" fdtype="" format="" dictionary="" label="抗菌药三级权限" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="状态" fdtype="" format="" dictionary="" label="状态" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="分院code" fdtype="" format="" dictionary="" label="分院code" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
</s:table.fields>
<s:toolbar><s:button label="导出" icon="glyphicon glyphicon-random" nextfocusfield="" action="exportexcel" color=""></s:button>
</s:toolbar>
</s:table>
<s:table label="给药途径信息" cols="4" color="grey-silver" active="false" isdesign_="Y" tableid="CX04" icon="" djselect="" tablekey="" limit="25" toIndex="3" app_table="false" sort="false" autoload="false" istoorbar="Y" myIndex="3" fields="" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="用药途径代码" fdtype="" format="" dictionary="" label="用药途径代码" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="用药途径名称" fdtype="" format="" dictionary="" label="用药途径名称" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
</s:table.fields>
<s:toolbar><s:button label="导出" icon="glyphicon glyphicon-random" nextfocusfield="" action="exportexcel" color=""></s:button>
</s:toolbar>
</s:table>
<s:table label="给药频次信息" cols="4" color="grey-silver" active="false" isdesign_="Y" tableid="CX05" icon="" djselect="" tablekey="" limit="25" toIndex="4" app_table="false" sort="false" autoload="false" istoorbar="Y" myIndex="4" fields="" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="用药频次代码" fdtype="" format="" dictionary="" label="用药频次代码" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="描述" fdtype="" format="" dictionary="" label="描述" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
</s:table.fields>
<s:toolbar><s:button label="导出" icon="glyphicon glyphicon-random" nextfocusfield="" action="exportexcel" color=""></s:button>
</s:toolbar>
</s:table>
<s:table label="诊断信息" cols="4" color="grey-silver" active="false" isdesign_="Y" tableid="CX06" icon="" djselect="" tablekey="" limit="25" toIndex="5" app_table="false" sort="false" autoload="false" istoorbar="Y" myIndex="5" fields="" height="" totals="" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="诊断代码" fdtype="" format="" dictionary="" label="诊断代码" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="诊断名称" fdtype="" format="" dictionary="" label="诊断名称" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
</s:table.fields>
<s:toolbar><s:button label="导出" icon="glyphicon glyphicon-random" nextfocusfield="" action="exportexcel" color=""></s:button>
</s:toolbar>
</s:table>
<s:table label="无说明书药品" cols="4" color="grey-silver" active="false" isdesign_="Y" tableid="dddd" icon="" djselect="" tablekey="" limit="25" toIndex="6" app_table="false" sort="false" autoload="false" istoorbar="Y" myIndex="6" fields="" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="drug_code" fdtype="字符" format="" dictionary="" label="药品代码" size="32" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="0" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_name" fdtype="字符" format="" dictionary="" label="药品名称" size="128" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="150" fieldorder="1" is_href="" app_field=""></s:table.field>
<s:table.field name="druggg" fdtype="字符" format="" dictionary="" label="药品规格" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="2" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_unit" fdtype="字符" format="" dictionary="" label="药品单位" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="3" is_href="" app_field=""></s:table.field>
<s:table.field name="包装规格" fdtype="字符" format="" dictionary="" label="包装规格" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="4" is_href="" app_field=""></s:table.field>
<s:table.field name="包装单位" fdtype="字符" format="" dictionary="" label="包装单位" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="5" is_href="" app_field=""></s:table.field>
<s:table.field name="jixing" fdtype="字符" format="" dictionary="" label="剂型" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="8" is_href="" app_field=""></s:table.field>
<s:table.field name="shengccj" fdtype="字符" format="" dictionary="" label="生产厂家" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="7" is_href="" app_field=""></s:table.field>
<s:table.field name="pizhwh" fdtype="字符" format="" dictionary="" label="批准文号" size="40" maxlength_show="" digitsize="0" precision="" available="true" sort="" controltype="" align="" width="100" fieldorder="6" is_href="" app_field=""></s:table.field>
<s:table.field name="use_30day" fdtype="" format="" dictionary="" label="30天内是否使用" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="9" is_href="" app_field=""></s:table.field>
</s:table.fields>
<s:toolbar><s:button label="导出" icon="glyphicon glyphicon-random" nextfocusfield="" action="exportexcel" color=""></s:button>
</s:toolbar>
</s:table>
</s:tabpanel>
</s:row>
</s:page>
