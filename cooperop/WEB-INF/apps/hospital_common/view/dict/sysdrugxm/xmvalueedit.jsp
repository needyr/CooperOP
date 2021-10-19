<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑药品属性值信息" ismodal="true"  disloggedin="true">
	<s:row>
		<s:form id="form" label="药品属性值信息">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon="fa fa-save"></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<input type="hidden" name="xmid"  id="xmid" cols="3" value="${xmid}"/>
				<%-- <input type="hidden" name="oldxmmch"  id="oldxmmch" cols="3" value="${xmmch}"/> --%>
				<input type="hidden" name="oldvalue"  id="oldvalue" cols="3" value="${value}"/>
			</s:row>
			<s:row>
				<s:textfield label="名称" disabled="disabled" name="xmmch" id="xmmch" required="true" cols="3" value="${xmmch}" ></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="值" name="value" required="true" cols="3" value="${value}" ></s:textfield>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
$(function(){
	debugger
	$("#xmid").val('${param.xmid}');
	$("#xmmch").val('${param.xmmch}');
	/* $("#oldxmmch").val('${param.xmmch}'); */
	$("#oldvalue").val('${param.value}');
})
function save(){
	var sdata=$("#form").getData();
	if (!$("form").valid()){
		return false;
	}
	sdata.xmid=$("#xmid").val();
		debugger
	if('${param.value}'){
		/* sdata.oldxmmch=$("#oldxmmch").val(); */
		sdata.oldvalue=$("#oldvalue").val();
		sdata.isupd="true";
		$.call("hospital_common.dict.sysdrugxm.xmValueUpdate",sdata,function(s){
			if (s==2){
				$.message("该药品属性值已创建，请重新输入！");
			}else{
		    	$.closeModal();
			}
    	});
	}else{
		sdata.isadd="true";
		$.call("hospital_common.dict.sysdrugxm.xmValueInsert",sdata,function(s){
			if (s==2){
				$.message("该药品属性值已创建，请重新输入！");
			}else{
		    	$.closeModal();
			}
    	});
	}
}

function cancel(){
	$.closeModal(false);
}
</script>