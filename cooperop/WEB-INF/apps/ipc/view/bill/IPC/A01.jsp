<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="A01" init-action="" flag="IPC" description="超说明书用药申请" system_product_code="ipc" djzy="超说明书用药申请_临床医生" helpno="超说明书用药申请_临床医生" title="超说明书用药申请_临床医生" remark="" printmx="" sub="" modalwidth="" modalheight="" djprintsql="" othermxsql="" type="bill"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("ipc.bill.IPC.A01", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("ipc.bill.IPC.A01", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "ipc.bill.IPC.A01", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("ipc.bill.IPC.A01", null);
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
<s:row><s:form label="超说明书用药申请" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4" istoorbar="Y"><s:row type="frow"><s:textfield label="" cols="4" name="message_a" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="true" readonly="true" required="0" encryption="0" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="2000" precision="">${empty message_a ? '' : message_a}</s:textfield>
<s:textfield label="" cols="4" name="message_b" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="true" readonly="true" required="0" encryption="0" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="2000" precision="">${empty message_b ? '' : message_b}</s:textfield>
</s:row>
<s:row type="frow"><s:textfield label="" cols="4" name="message_peiw" placeholder="---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------" fdtype="字符" defaultValue="[请填写以下信息，*为必填项]  " format="" nextfocusfield="" islabel="true" readonly="true" required="0" encryption="0" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="4000" precision="">${empty message_peiw ? '[请填写以下信息，*为必填项]  ' : message_peiw}</s:textfield>
</s:row>
<s:row type="frow"><s:datefield label="日期" cols="1" name="rq" value="${empty rq ? rq : rq}" placeholder="" fdtype="字符" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision=""></s:datefield>
<s:textfield label="操作用户" cols="1" name="username" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="255" precision="">${empty username ? '' : username}</s:textfield>
</s:row>
<s:row type="frow"><s:textfield label="科室名称" cols="1" name="dept_name" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="true" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="zl_select_IPCA01_01" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty dept_name ? '' : dept_name}</s:textfield>
</s:row>
<s:row type="frow"><s:textfield label="药品代码" cols="1" name="drug_code" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="0" readonly="false" required="true" encryption="0" create_action="" modify_action="" enter_action="" dbl_action="zl_select_IPCA01_02" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty drug_code ? '' : drug_code}</s:textfield>
<s:textfield label="药品名称" cols="1" name="drug_name" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="true" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="zl_select_IPCA01_02" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty drug_name ? '' : drug_name}</s:textfield>
<s:textfield label="药品规格" cols="1" name="drug_spec" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="32" precision="">${empty drug_spec ? '' : drug_spec}</s:textfield>
</s:row>
<s:row type="frow"><s:textfield label="药品单位" cols="1" name="drug_unit" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty drug_unit ? '' : drug_unit}</s:textfield>
<s:textfield label="生产厂家" cols="1" name="shengccj" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty shengccj ? '' : shengccj}</s:textfield>
<s:textfield label="批准文号" cols="1" name="pizhwh" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="40" precision="">${empty pizhwh ? '' : pizhwh}</s:textfield>
</s:row>
<s:row type="frow"><s:textarea label="用药目的" cols="4" name="offlabel_mudi" placeholder="用药目的" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="true" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="2000" precision="">${empty offlabel_mudi ? '' : offlabel_mudi}</s:textarea>
</s:row>
<s:row type="frow"><s:checkbox label="超说明书内容" cols="4" name="offlabel_neirong" value="${empty offlabel_neirong ? '' : offlabel_neirong}" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="true" encryption="否" dictionary="" enter_action="" modify_action="" v1="" v2="" clabel="超配伍问题" contentIndex="0" contentvalue="0" maxlength="2000" precision=""><s:option label="超配伍问题" contentIndex="0" value="0"></s:option>
<s:option label="超溶媒问题" contentIndex="1" value="1"></s:option>
<s:option label="超用量问题" contentIndex="2" value="2"></s:option>
<s:option label="超用法问题" contentIndex="3" value="3"></s:option>
<s:option label="超频率问题" contentIndex="4" value="4"></s:option>
<s:option label="超禁忌症问题" contentIndex="5" value="5"></s:option>
<s:option label="超适应症问题" contentIndex="6" value="6"></s:option>
<s:option label="其他问题" contentIndex="7" value="99"></s:option>
</s:checkbox>
</s:row>
<s:row type="frow"><s:textarea label="权威性依据" cols="4" name="offlabel_yiju" placeholder="权威性依据" fdtype="varchar" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="true" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="2000" precision="">${empty offlabel_yiju ? '' : offlabel_yiju}</s:textarea>
</s:row>
<s:row type="frow"><s:textarea label="简述病例" cols="4" name="offlabel_jiansbl" placeholder="申请主治医师简述病例及签名" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="true" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="2000" precision="">${empty offlabel_jiansbl ? '' : offlabel_jiansbl}</s:textarea>
</s:row>
<s:row type="frow"><s:textarea label="风险及应急预案" cols="4" name="offlabel_fengxian" placeholder="申请主治医师简述超说明书可能出现的风险和应急预案" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="true" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="2000" precision="">${empty offlabel_fengxian ? '' : offlabel_fengxian}</s:textarea>
</s:row>
<s:row type="frow"><s:file label="循证文件" cols="4" name="filename" value="${empty filename ? '' : filename}" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="0" readonly="false" required="true" encryption="0" deleteable="true" addable="true" downloadable="true" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="40" precision=""></s:file>
<s:image label="图片附件" cols="4" accept="image/*" name="imagename" value="${empty imagename ? '' : imagename}" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="0" readonly="false" required="0" encryption="0" deleteable="true" addable="true" downloadable="true" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="512" precision=""></s:image>
</s:row>
<s:row type="frow"><s:taskhistory label="taskhistory" cols="" djbh="${djbh}"></s:taskhistory>
</s:row>
<s:row type="frow" ishidden="ishidden"><s:textfield label="单据编号" cols="1" name="djbh" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="15" precision="">${empty djbh ? '' : djbh}</s:textfield>
<s:textfield color="grey" label="科室代码" name="dept_code" placeholder="" fdtype="字符" cols="1" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty dept_code ? '' : dept_code}</s:textfield>
</s:row>
<s:toolbar><s:button label="打印" icon="glyphicon glyphicon-print" size="btn-sm" nextfocusfield="" action="da_yin_IPCA01_02" color=""></s:button>
<s:button label="提交并打印" icon="glyphicon glyphicon-ok" size="btn-sm" nextfocusfield="" action="da_save_yin_IPCA01_02" color=""></s:button>
</s:toolbar>
</s:form>
</s:row>
</s:page>
