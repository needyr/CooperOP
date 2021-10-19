<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑链接信息" ismodal="true">
	<s:row>
		<s:form id="form" label="链接信息">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon="glyphicon glyphicon-floppy-saved"></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<input type="hidden" name="id" value="${id}">
				<s:textfield label="链接名称" name="url_name" required="true" cols="3" value="${url_name}" ></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="地址" name="address" cols="3" value="${address}" required="true"></s:textfield>
			</s:row>
			<s:row>
				<s:textarea label="描述" name="description" required="false" cols="3" >${description}</s:textarea>
			</s:row>
			<%-- <s:row>
				<s:switch label="是否启用" name="state" onvalue="1" offvalue="0" ontext="是" offtext="否" value="${state}"></s:switch>
			</s:row> --%>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
function save(){
	var sdata = $("#form").getData();
	sdata.state = 1 ;
	if (!$("form").valid()){
		return false;
	}
	if(sdata.id){
		$.call("hospital_common.dict.dictsysurl.update", sdata, function(s){
    		$.closeModal(s);
    	});
	}else{
		delete sdata.id;
		$.call("hospital_common.dict.dictsysurl.insert",sdata,function(s){
    		$.closeModal(s);
    	});
	}
}

function cancel(){
	$.closeModal(false);
}
</script>