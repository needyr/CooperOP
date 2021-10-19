<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="k1111111" init-action="" system_product_code="oa" tablename="jqtest2" description="测试卡片" title="123123sdf" remark="asdf" flag="m" type="materials" tablekey="jqid"><%
	Map<String, Object> a = null;
		a = BillAction.init("oa.materials.m.k1111111", (String)request.getParameter("jqid"));
	Iterator<String> it = a.keySet().iterator(); 
	while (it.hasNext()) {   
		String key = it.next();  
		pageContext.setAttribute(key, a.get(key));   
	}  
		pageContext.setAttribute("xaxis", request.getParameter("xaxis"));   
%>
<input type="hidden" name="pageid" value="${pageid}">
<input type="hidden" name="company_id" value="${company_id}">
<input type="hidden" name="jqid" value="${jqid}">
<input type="hidden" name="tablekey_" value="jqid">
<input type="hidden" name="djbs" value="${djbs}">
<input type="hidden" name="djlx" value="${djlx}">
<input type="hidden" name="_CRSID" value="${_CRSID}">
<s:row><s:form label="表单" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4" istoorbar="Y"><s:row type="frow"><s:textfield label="发起人" cols="1" name="username1" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="16" precision="">${empty username1 ? '' : username1}</s:textfield>
<s:textfield label="通讯地址" cols="1" name="address" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="50" precision="">${empty address ? '' : address}</s:textfield>
<s:radio label="性别" cols="1" name="sex" value="${empty sex ? '' : sex}" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" clabel="女" contentIndex="0" maxlength="2" precision=""><s:option label="女" contentIndex="0" value="0"></s:option>
<s:option label="男" contentIndex="1" value="1"></s:option>
</s:radio>
</s:row>
<s:row type="frow"></s:row>
<s:toolbar><s:button label="保存" icon="fa fa-save" nextfocusfield="" action="djsubmit" color=""></s:button>
</s:toolbar>
</s:form>
</s:row>
</s:page>
