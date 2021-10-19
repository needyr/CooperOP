<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="PhaCheckPass" init-action="" system_product_code="hospital_common" description="药师审查通过资料" tablename="pharmacist_check_pass" tablekey="ID" title="药师审查通过资料" remark="药师审查通过资料" flag="m" type="materials"><%
	Map<String, Object> a = null;
		a = BillAction.init("hospital_common.materials.m.PhaCheckPass", (String)request.getParameter("id"));
	Iterator<String> it = a.keySet().iterator(); 
	while (it.hasNext()) {   
		String key = it.next();  
		pageContext.setAttribute(key, a.get(key));   
	}  
		pageContext.setAttribute("xaxis", request.getParameter("xaxis"));   
%>
<input type="hidden" name="pageid" value="${pageid}">
<input type="hidden" name="company_id" value="${company_id}">
<input type="hidden" name="ID" value="${id}">
<input type="hidden" name="tablekey_" value="ID">
<input type="hidden" name="djbs" value="${djbs}">
<input type="hidden" name="djlx" value="${djlx}">
<input type="hidden" name="_CRSID" value="${_CRSID}">
<s:row><s:form label="药师审查合理信息" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4" istoorbar="Y"><s:row type="frow"><s:switch label="是否活动" cols="1" name="beactive" value="${empty beactive ? '是' : beactive}" ontext="是" offtext="否" onvalue="是" offvalue="否" defaultValue="是" nextfocusfield=""></s:switch>
<s:select label="审查调整级别" cols="1" name="shenc_change_level" value="${empty shenc_change_level ? '' : shenc_change_level}" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" dictionary="" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" clabel="合理" contentIndex="0" contentvalue="0" maxlength="10" precision=""><s:option label="合理" contentIndex="0" value="0"></s:option>
<s:option label="关注★" contentIndex="1" value="1"></s:option>
<s:option label="慎用★★" contentIndex="2" value="2"></s:option>
<s:option label="不推荐★★★" contentIndex="3" value="3"></s:option>
<s:option label="禁忌★★★★" contentIndex="4" value="4"></s:option>
</s:select>
</s:row>
<s:row type="frow"><s:textarea label="药师调整意见" cols="4" name="shenc_pass_pharmacist_advice" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="100" precision="">${empty shenc_pass_pharmacist_advice ? '' : shenc_pass_pharmacist_advice}</s:textarea>
</s:row>
<s:row type="frow"><s:textarea label="审查调整来源" cols="4" name="shenc_pass_source" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="200" precision="">${empty shenc_pass_source ? '' : shenc_pass_source}</s:textarea>
</s:row>
<s:row type="frow"><s:textfield label="审查调整时间" cols="1" name="shenc_pass_time" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="19" precision="">${empty shenc_pass_time ? '' : shenc_pass_time}</s:textfield>
<s:textfield label="审查调整人" cols="1" name="shenc_pass_ren" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="50" precision="">${empty shenc_pass_ren ? '' : shenc_pass_ren}</s:textfield>
<s:textfield label="审查调整功能名称" cols="1" name="shenc_pass_gnmch" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="100" precision="">${empty shenc_pass_gnmch ? '' : shenc_pass_gnmch}</s:textfield>
</s:row>
<s:row type="frow"><s:textfield label="不合理类别" cols="1" name="sort_name" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="50" precision="">${empty sort_name ? '' : sort_name}</s:textfield>
<s:textfield label="警示级别" cols="1" name="level_name" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="50" precision="0">${empty level_name ? '' : level_name}</s:textfield>
</s:row>
<s:row type="frow"><s:textarea label="药品信息" cols="4" name="related_drugs_show" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="1024" precision="">${empty related_drugs_show ? '' : related_drugs_show}</s:textarea>
</s:row>
<s:row type="frow"><s:textarea label="参考" cols="4" name="reference" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="500" precision="">${empty reference ? '' : reference}</s:textarea>
</s:row>
<s:row type="frow"><s:textarea label="描述" cols="4" name="description" placeholder="" fdtype="文本" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="" precision="">${empty description ? '' : description}</s:textarea>
</s:row>
<s:row type="frow"><s:button label="查看医嘱" cols="" nextfocusfield="" icon="" action="" color=""></s:button>
</s:row>
<s:row type="frow" ishidden="ishidden"><s:textfield label="主键" cols="1" name="id" placeholder="" fdtype="数字" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="18" precision="">${empty id ? '' : id}</s:textfield>
</s:row>
<s:toolbar><s:button label="保存" icon="fa fa-save" nextfocusfield="" action="djsubmit" color=""></s:button>
</s:toolbar>
</s:form>
</s:row>
<s:row><s:table label="相关医嘱信息" cols="4" color="grey-silver" isdesign_="Y" tableid="01" djselect="" tablename="" icon="" autoload="false" fields="" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="" fdtype="" format="" dictionary="" label="姓名" size="" maxlength="" digitsize="" precision="" editable="true" available="true" align="left" fieldorder=""></s:table.field>
<s:table.field name="" fdtype="" format="" dictionary="" label="性别" size="" maxlength="" digitsize="" precision="" editable="true" available="true" align="left" fieldorder=""></s:table.field>
<s:table.field name="" fdtype="" format="###岁" dictionary="" label="年龄" size="" maxlength="" digitsize="" precision="" editable="true" available="true" align="left" fieldorder=""></s:table.field>
</s:table.fields>
</s:table>
</s:row>
</s:page>
