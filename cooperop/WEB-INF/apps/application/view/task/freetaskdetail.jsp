<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="个人配合单" dispermission="true">
	<s:row>
		<s:form label="个人配合单">
			<s:row>
				<s:textfield label="主题" name="subject" cols="3" islabel="true" 
				required="true" >${subject}</s:textfield>
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
				<s:file label="附件" name="attach_files"  islabel="true" cols="4" value="${attach_files }"></s:file>
			</s:row>
		</s:form>
	</s:row>
	<s:row type="frow">
<s:taskhistory label="taskhistory" cols="" djbh="${djbh}">
</s:taskhistory>
</s:row>
	 <c:import url='../suggestions/suggestions.jsp?ptype=free'></c:import>
</s:page>
