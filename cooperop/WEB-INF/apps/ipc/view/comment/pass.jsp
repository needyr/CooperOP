<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="审查合理理由填写" dispermission="true">
	<s:row>
		<s:form  id="datatable" >
			<s:row>
				<c:if test="${empty pageParam.level}">
				<s:select label="拦截等级" cols="3" name="shenc_change_level" value="0">
					<s:option value="0" label="合理"></s:option>
					<s:option value="1" label="关注"></s:option>
					<s:option value="2" label="慎用"></s:option>
					<s:option value="3" label="不推荐"></s:option>
					<s:option value="4" label="禁忌"></s:option>
				</s:select>
				</c:if>
			</s:row>
			<s:row>
			<c:if test="${empty pageParam.level}">
			<s:textarea cols="3" name="shenc_pass_pharmacist_advice" label="理由" rows="4"></s:textarea>
			</c:if>
			<s:textarea cols="3" name="shenc_pass_source" label="理由来源" rows="4">${shenc_pass_source}</s:textarea>
			<s:textarea cols="3" name="pharmacist_todoctor_advice" label="给医生建议" rows="4">${pharmacist_todoctor_advice}</s:textarea>
			</s:row>
			<s:row>
				<div style="text-align: right;padding-right: 10px;">
					<s:button label="确认" icon="fa fa-edit" onclick="setHL();" color="blue"></s:button>
					<s:button label="取消" icon="fa fa-ban" onclick="$.closeModal();"></s:button>
				</div>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
function setHL(){
	var date = $("#datatable").getData();
	date.id = '${pageParam.check_result_info_id}';
	date.auto_audit_id = '${pageParam.auto_audit_id}';
	if('${pageParam.is_add}'){
		$.call("ipc.comment.updateHL",date,function(rtn){
			$.closeModal(rtn);
		});
	}else{
		date.user_name = '${pageParam.user_name}';
		if('${pageParam.level}'){
			date.shenc_change_level = '${pageParam.level}';
		}
		$.call("ipc.comment.setHL",date,function(rtn){
			$.closeModal(rtn);
		});
	}
}
</script>