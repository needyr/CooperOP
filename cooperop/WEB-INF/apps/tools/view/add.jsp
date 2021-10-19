<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" ismodal="true">
	<s:row>
		<s:form border="0" id="myform" fclass="portlet light bordered">
			<s:row>
				
				<s:textfield label="update" name="table_name" required="true"
					value=""></s:textfield>
				<s:textfield label="set" name="fromfields" required="true"
					value=""></s:textfield>
				<s:textfield label="=（加密）" name="tofields" required="true"
					value=""></s:textfield>	
				
			</s:row>
			<s:row>
				<s:textarea label="where" name="condition" required="true"></s:textarea>	
				<s:textfield label="主键" name="keys" required="true"
					value=""></s:textfield>
			</s:row>
				<s:row>
				<div class="cols3">
				</div>
				<div class="cols">
					<s:button onclick="save();" color="green" label="执行"></s:button>
				</div>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
function save() {
	if (!$("form").valid()) {
		return false;	
	}
	$.confirm("开始执行，请耐心等待",function(rtn){
		if(rtn){
			$.call("tools.user.save", $("#myform").getData(), function(rtn) {
				if (rtn.result == 'success') {
					$.success("执行成功");
				}
			},null,{async: false});
		}
	});
	
}
</script>
