<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="个人配合单" dispermission="true">
	<s:row>
		<s:form label="个人配合单" id="freeForm">
			<s:row>
				<s:textfield label="主题" name="subject" cols="3" islabel="true" required="true">${subject}</s:textfield>
				<s:textfield label="单据号" name="djbh" cols="1" islabel="true" value="${djbh}" placeholder="提交后自动生成" readonly="true"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="起草人" islabel="true" value="${system_user_name}"></s:textfield>
				<s:textfield label="所属部门" islabel="true" value="${system_department_name}"></s:textfield>
				<s:textfield label="所属岗位" islabel="true" value="${system_role_names}"></s:textfield>
				<s:textfield label="起草时间" islabel="true" value="${create_time}"></s:textfield>
			</s:row>
			<c:if test="${not empty cc }">
			<s:row>
				<div crid="" class="cols4">
					<label class="control-label">抄送</label>
					<div class="control-content">
						<p class="form-control-static">
							<c:set var="tn" value="${fn:split(cc_name, ',')}"></c:set>
							<c:set var="td" value="${fn:split(cc_department_name, ',')}"></c:set>
							<c:forEach items="${fn:split(cc, ',')}" var="t" varStatus="i">
								${i.index gt 0 ? ',' : '' }${tn[i.index] }(${td[i.index] })
							</c:forEach>
						</p>
					</div>
				</div>
			</s:row>
			</c:if>
			<s:row>
				<s:richeditor label="内容"  islabel="true" name="content" cols="4" required="true" toolbar="full" height="300">${content}</s:richeditor>
			</s:row>
			<s:row>
				<s:file label="附件" name="attach_files"  deleteable="false" cols="4" value="${attach_files }"></s:file>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<div class="cols4 nolabel">
			<div ctype="taskhistory" djbh="${djbh}"></div>
		</div>
	</s:row>
	<s:row>
		<s:form id="conditions" label="处理意见">
			<s:row>
				<input type="hidden" name="task_id" value="${task_id }"/>
				<input type="hidden" name="djbh" value="${djbh }"/>
				<s:select label="处理结果" name="audited" required="true" value="">
					<s:option value="" label="请选择..."></s:option>
					<s:option value="Y" label="交由他人处理"></s:option>
					<%-- <s:option value="R" label="返回上一人处理"></s:option>
					<s:option value="B" label="返回申请人"></s:option> --%>
					<s:option value="F" label="结束流程"></s:option>
				</s:select>
				<s:autocomplete label="转交" name="operator" action="application.contacter.queryMine" params="{&#34;type&#34;:&#34;U&#34;}" required="true" value="${operator}" text="${operator_name}${empty operator ? '' : '('}${operator_department_name}${empty operator ? '' : ')'}">
					<s:option label="$[name]" value="$[id]">$[name]</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<s:richeditor label="处理意见" name="advice" cols="4" required="true" toolbar="full" height="200"></s:richeditor>
			</s:row>
			<s:row>
				<div class="cols2">
					<s:button label="确定" icon="fa fa-check" color="btn-success" onclick="save();"></s:button>
					<s:button label="取消" icon="fa fa-ban" onclick="cancel();"></s:button>
				</div>
			</s:row>
		</s:form>
	</s:row>
	<c:import url='../suggestions/suggestions.jsp?ptype=free'></c:import>
</s:page>
<script type="text/javascript">
	$(document).ready(function() {
		$("[name='audited']").change(function() {
			var $t = $(this);
			if ($t.val() == "Y") {
				$("[name='operator']").closest(".cols1").show();
			} else {
				$("[name='operator']").closest(".cols1").hide();
			}
		});
		
		$("[name='audited']").change();
	});
	
	function save() {
		if (!$("form").valid()) {
			return;
		}
		var data = $("#conditions").getData();
		data["id"] = "${id}";
		data["attach_files"] = $("#freeForm").getData().attach_files;
		var page = "application.task.freenext";
		var message = "处理成功，任务已交由“" + $("[name='operator']").val() + "”处理。";
		if (data.audited == "F") {
			page = "application.task.freefinish";
			message = "处理成功，流程已结束。";
		}
		$.call(page, data, function(rtn) {
			$.success(message, function() {
				$.closeModal(true);
			});
		});
	}

	function cancel() {
		$.closeModal(false);
	}
</script>