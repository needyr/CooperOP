<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="个人资料" dispermission="true">
<style>
.profile-userpic .img-responsive {
    float: none;
    margin: 0 auto;
    text-align: center;
    width: 100px;
    height: 100px;
    line-height: 100px;
    -webkit-border-radius: 50% !important;
    -moz-border-radius: 50% !important;
    border-radius: 50% !important;
    background: #EEE;
}
.profile-userpic .img-responsive .fa {
    font-size: 64px;
    line-height: 100px;
    color: #4B77BE;
}
.profile-userpic .img-responsive .fa.female {
    font-size: 64px;
    line-height: 100px;
    color: #C94449;
}
.profile-usertitle {
    text-align: center;
    margin-top: 20px;
}
.profile-usertitle-name {
  color: #5a7391;
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 7px;
}
.profile-usertitle-job {
  text-transform: uppercase;
  color: #5b9bd1;
  font-size: 13px;
  font-weight: 800;
  margin-bottom: 7px;
}
</style>
	<s:row>
		<div class="profile-userpic">
			<c:choose>
				<c:when test="${not empty avatar}">
					<img class="img-responsive" src="${request.contextPath }/rm/s/application/${avatar}S" alt="..." />
				</c:when>
				<c:otherwise>
					<span class="img-responsive"><i class="fa fa-user ${gender eq 0 ? 'female' : '' }"></i></span>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="profile-usertitle">
			<div class="profile-usertitle-name">
				 ${name}
			</div>
			<div class="profile-usertitle-job">
				 ${role_names }
			</div>
		</div>
		<s:form border="0" id="myform">
			<s:row>
				<s:textfield label="真实姓名" cols="2" islabel="true"
					value="${actual_name}"></s:textfield>
				<s:textfield label="性别" islabel="true" cols="2" value="${gender eq 0 ? '女' : '男'}"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="所属部门" islabel="true" cols="4" value="${fn:replace(department_names, ',', '>')}"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="办公电话" cols="2" islabel="true" value="${telephone}"></s:textfield>
				<s:textfield label="移动电话" cols="2" islabel="true" required="true" value="${mobile}"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="邮箱" cols="2" islabel="true"  value="${email}"></s:textfield>
				<s:textfield label="qq" cols="2" islabel="true" value="${qq}"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
	function save() {
		if (!$("form").valid()) {
			return;
		}
		$.call("application.mine.update", $("#myform").getData(), function(rtn) {
			if (rtn.result == 'success') {
				$.message("更新成功");
			}
		},null,{async: false});
	}
</script>

