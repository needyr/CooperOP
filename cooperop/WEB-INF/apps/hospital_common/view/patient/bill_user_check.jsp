<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="检验第二用户登录" disloggedin="true">
<s:form id="form">
<s:toolbar>
<s:button label="检验" onClick="is_permit();"></s:button>
</s:toolbar>
<s:row>
<s:textfield cols="1" required="true" label="用户名"  name="userid"></s:textfield>	
<s:textfield cols="1" type="password" required="true" label="密码"  name="password"></s:textfield>	
</s:row>
</s:form>
</s:page>
<script>
function is_permit(){
	if (!$("form").valid()){
   		return false;
   	}
	var sdata=$("#form").getData();
	$.call('hospital_common.patientdata.bill.is_permit',sdata,function(r){
		if(r.check){
			//alert('检验成功')
			$.closeModal(r);
		}else{
			alert('用户名或密码错误，或不能用相同账户')
		}
	})
}

</script>