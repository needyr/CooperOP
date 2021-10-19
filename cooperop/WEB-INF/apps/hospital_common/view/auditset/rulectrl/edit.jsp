<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="编辑监控信息" disloggedin="true">

	<s:row>
		<s:form id="form" label="监控设置">

			<s:toolbar>
				<s:button label="确认" onclick="save()" icon="fa fa-check"></s:button>
				<s:button label="取消" onclick="cancel()" icon="fa fa-times"></s:button>
			</s:toolbar>

			<s:row>
				<%-- <s:textfield name="id" id="sort_id" label="编号" cols="2" required="true" value="${id}" /> --%>
				<s:select label="开单来源" name="audit_sur" value="${audit_sur}" cols="2">
					<s:option label="住院" value="1"></s:option>
					<s:option label="门诊" value="2"></s:option>
					<s:option label="急诊" value="3"></s:option>
				</s:select>
				<s:switch name="state" label="是否启用" value="${state}" onvalue="1" offvalue="0" ontext="是" offtext="否" />
			</s:row>
			
			<s:row>
				<s:radio name="rejected_level" label="驳回该级别及以上问题" cols="3" onchange="addDescription()"
					required="true"
					value="${empty rejected_level ? level_list[0].level_code: rejected_level}"
					style="border:none">
                  
					<c:forEach var="level" items="${level_list}">
						<s:option value="${level.level_code}" label="${level.level_name}(${level.level_code})"></s:option>
                  	</c:forEach>
				</s:radio>
			</s:row>
			
			<s:row>
				<s:radio name="intercept_level" label="拦截该级别及以上问题" cols="3" onchange="addDescription()"
					required="true"
					value="${empty intercept_level ? level_list[0].level_code: intercept_level}"
					style="border:none">
                  
					<c:forEach var="level" items="${level_list}">
						<s:option value="${level.level_code}" label="${level.level_name}(${level.level_code})"></s:option>
                  	</c:forEach>
				</s:radio>
			</s:row>
			
			<s:row>
				<s:radio name="prompt_level" label="提示该级别及以上问题" cols="3" onchange="addDescription()"
					required="true"
					value="${empty prompt_level ? level_list[0].level_code: prompt_level}"
					style="border:none">
                  
					<c:forEach var="level" items="${level_list}">
						<s:option value="${level.level_code}" label="${level.level_name}(${level.level_code})"></s:option>
                  	</c:forEach>
				</s:radio>
			</s:row>

		</s:form>
	</s:row>

</s:page>


<script type="text/javascript">
	var id = '${param.id}';
	$(function(){
		if(id){
			$('[name="audit_sur"]').attr('disabled', 'disabled');
		}
	});

	function save(params) {
		var data = $('#form').getData();
		data.sys_p_key = '${param.sys_p_key}';
		if(id){
			data.id = id;
		}
		if ($("form").valid()) {
			//检验重复
			$.call('hospital_common.auditset.rulectrl.save', data, function(rtn){
				if(rtn == -1){
					$.message('该规则下已包含此开单来源，请重新选择！');
				}else{
					$.closeModal(true);
				}
			});
		}else{
			return ;
		}

	}

	function cancel() {
		$.closeModal(false);
	}
</script>