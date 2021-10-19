<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>

<s:page title="" disloggedin="true">

	<s:row>
		<s:form id="form" label="分类信息">

			<s:toolbar>
				<s:button label="确认" onclick="submitData()" icon=""></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>

			<s:row>
				<s:textfield name="sort_id" id="sort_id" label="分类编号" cols="2"
					required="true" value="${sort_id}"></s:textfield>
				<s:switch name="state" label="是否启用" value="${state}" onvalue="1"
					offvalue="0" ontext="是" offtext="否"></s:switch>
			</s:row>

			<s:row>
				<s:textfield name="sort_name" id="sort_name" label="分类名称" cols="3"
					required="true" value="${sort_name}"></s:textfield>
			</s:row>

			<s:row>
				<s:textfield name="sort_num" label="排序值" cols="3" required="true"
					value="${sort_num}"></s:textfield>
			</s:row>
			
			<s:row name="tabpanel_q">
			<s:tabpanel>
			<s:form label="住院操作级别" active="true">
				<s:row>
				<s:radio name="complain_level" label="住院驳回级别" cols="3" onchange="addDescription()"
					required="true"
					value="${empty complain_level ? levelList[0].level_code:complain_level}"
					style="border:none">
                  
					<c:forEach var="level" items="${levelList}">
						<s:option value="${level.level_code}" label="${level.level_name}(${level.level_code})"></s:option>
                  	</c:forEach>
				</s:radio>
			</s:row>

			<s:row>
				<s:radio name="interceptor_level" label="住院拦截级别" cols="3" onchange="addDescription()"
					required="true"
					value="${empty interceptor_level ? levelList[0].level_code:interceptor_level}"
					style="border:none">
                  
					<c:forEach var="level" items="${levelList}">
						<s:option value="${level.level_code}" label="${level.level_name}(${level.level_code})"></s:option>
                  	</c:forEach>
				</s:radio>
				</s:row>
	
				<s:row>
					<s:radio name="info_level" label="住院提示级别" cols="3" required="true" onchange="addDescription()"
						value="${empty info_level ? levelList[0].level_code:info_level}"
						style="border:none">
						<c:forEach var="level" items="${levelList}">
							<s:option value="${level.level_code}" label="${level.level_name}(${level.level_code})"></s:option>
	                  	</c:forEach>
					</s:radio>
				</s:row>
			</s:form>
			<s:form label="门诊操作级别">
				<s:row>
				<s:radio name="complain_level_outp" label="门诊驳回级别" cols="3" onchange="addDescription()"
					required="true"
					value="${empty complain_level_outp ? levelList[0].level_code:complain_level_outp}"
					style="border:none">
                  
					<c:forEach var="level" items="${levelList}">
						<s:option value="${level.level_code}" label="${level.level_name}(${level.level_code})"></s:option>
                  	</c:forEach>
				</s:radio>
			</s:row>

			<s:row>
				<s:radio name="interceptor_level_outp" label="门诊拦截级别" cols="3" onchange="addDescription()"
					required="true"
					value="${empty interceptor_level_outp ? levelList[0].level_code:interceptor_level_outp}"
					style="border:none">
                  
					<c:forEach var="level" items="${levelList}">
						<s:option value="${level.level_code}" label="${level.level_name}(${level.level_code})"></s:option>
                  	</c:forEach>
				</s:radio>
				</s:row>
	
				<s:row>
					<s:radio name="info_level_outp" label="门诊提示级别" cols="3" required="true" onchange="addDescription()"
						value="${empty info_level_outp ? levelList[0].level_code:info_level_outp}"
						style="border:none">
						<c:forEach var="level" items="${levelList}">
							<s:option value="${level.level_code}" label="${level.level_name}(${level.level_code})"></s:option>
	                  	</c:forEach>
					</s:radio>
				</s:row>
			</s:form>
			<s:form label="急诊操作级别">
				<s:row>
				<s:radio name="complain_level_emergency" label="急诊驳回级别" cols="3" onchange="addDescription()"
					required="true"
					value="${empty complain_level_emergency ? levelList[0].level_code:complain_level_emergency}"
					style="border:none">
                  
					<c:forEach var="level" items="${levelList}">
						<s:option value="${level.level_code}" label="${level.level_name}(${level.level_code})"></s:option>
                  	</c:forEach>
				</s:radio>
			</s:row>

			<s:row>
				<s:radio name="interceptor_level_emergency" label="急诊拦截级别" cols="3" onchange="addDescription()"
					required="true"
					value="${empty interceptor_level_emergency ? levelList[0].level_code:interceptor_level_emergency}"
					style="border:none">
                  
					<c:forEach var="level" items="${levelList}">
						<s:option value="${level.level_code}" label="${level.level_name}(${level.level_code})"></s:option>
                  	</c:forEach>
				</s:radio>
				</s:row>
	
				<s:row>
					<s:radio name="info_level_emergency" label="急诊提示级别" cols="3" required="true" onchange="addDescription()"
						value="${empty info_level_emergency ? levelList[0].level_code:info_level_emergency}"
						style="border:none">
						<c:forEach var="level" items="${levelList}">
							<s:option value="${level.level_code}" label="${level.level_name}(${level.level_code})"></s:option>
	                  	</c:forEach>
					</s:radio>
				</s:row>
			</s:form>
				
			</s:tabpanel>
			</s:row>
			
			<s:row>
				<s:switch name="outpatient_check" label="门诊是否审查"
					value="${outpatient_check}" onvalue="1" offvalue="0" ontext="是"
					offtext="否"></s:switch>
				<s:switch name="emergency_check" label="急诊是否审查"
					value="${emergency_check}" onvalue="1" offvalue="0" ontext="是"
					offtext="否"></s:switch>
				<s:switch name="hospitalization_check" label="住院是否审查"
					value="${hospitalization_check}" onvalue="1" offvalue="0"
					ontext="是" offtext="否"></s:switch>
			</s:row>

			<s:row>
				<s:textarea name="audit_explain" required="true"
					value="${audit_explain}" label="审查说明" cols="3"></s:textarea>
			</s:row>
			
			<s:row>
				<s:textarea name="description" required="true" disabled="false" 
					value="${description}" label="规则说明" cols="3"></s:textarea>
			</s:row>

		</s:form>
	</s:row>

</s:page>


<script>
	//初始化
	$(function() {
		/* 1添加,2更新 */
		var upOrAdd = '${param.upOrAdd}';
		if (upOrAdd == 2) {
			$('[name = "sort_id"]').attr('disabled', 'disabled');
		}
		addDescription();
		$('div[name="tabpanel_q"]').children().css('padding-left','0px');
		$('div[name="tabpanel_q"]').children().children().children('[class="portlet-title"]').css('margin-bottom','0px');
		$('div[name="tabpanel_q"]').children().children().children('[class="portlet-body"]').css({'border-bottom':'1px solid #d8d8d8','border-left':'1px solid #d8d8d8','border-right':'1px solid #d8d8d8'});
	});

	function submitData() {
		/* 1添加,2更新 */
		var upOrAdd = '${param.upOrAdd}';
		var methodName;
		var fdata = $("#form").getData();
		fdata.product_code = '${param.product_code}';
		/* 判断所需要调用方法 */
		if (upOrAdd == 1) {
			methodName = "hospital_common.auditset.checksort.insert";
		} else if (upOrAdd == 2) {
			methodName = "hospital_common.auditset.checksort.update";
			fdata.p_key = '${p_key}';
		}

		/* 必填验证 */
		if ($("form").valid()) {

			/* 提交数据 */
			ajax: $.call(methodName, fdata, function(s) {
				if (s > 0) {
					$.closeModal(false);
				} else {
					if (upOrAdd == 1) {
						$.message("分类编号重复，请重新输入");
					} else {
						$.message("编辑失败");
					}

				}

			});
		}
	}

	function cancel() {
		$.closeModal(false);
	}
	
	//这个函数在radio值改变时触发
	function addDescription(){
		
		var interceptorName=$("[name='interceptor_level']:checked").parent().parent().parent().text();
		
		var infoName=$("[name='info_level']:checked").parent().parent().parent().text();
		
		var complainName=$("[name='complain_level']:checked").parent().parent().parent().text();
		
		var content="";
		content+="该类问题的严重程度高于或等于【"+infoName+"】时判定为提示，";
		content+="高于或等于【"+interceptorName+"】时判定为拦截，高于【"+complainName+"】时判定为驳回";
		
		$("[name='description']").val(content);
		
	}
</script>