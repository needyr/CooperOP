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
				<input type="hidden" name="id" value="${param.id }"/>
				<s:autocomplete label="部门" name="department" action="setting.dep.querydep" value="${p.department}" text="${p.department_name}" onchange="getpost()" limit="10">
					<s:option value="$[id]" label="$[name]">$[code]-$[name]</s:option>
				</s:autocomplete>
				<s:autocomplete id="post" label="岗位" name="position" action="setting.post.query" required="true" onFocus="setdep()"
					value="${p.position}" text="${p.position_name}" limit="10" editable="false">
					<s:option value="$[id]" label="$[jg_dep_post]">$[jg_dep_post]</s:option>
				</s:autocomplete>
				<s:textfield label="职工号" name="no" required="true"
					value="${p.no}"></s:textfield>
				<s:textfield label="姓名" name="name" required="true">${p.name}</s:textfield>
<%-- 				<s:textfield label="密码" name="password" required="true"
					value="${p.password}"></s:textfield> --%>
			</s:row>
			<s:row>
				<s:radio label="性别" name="gender" required="true" value="${p.gender}">
					<s:option label="女" value="0"></s:option>
					<s:option label="男" value="1"></s:option>
				</s:radio>
				<s:datefield label="生日" name="birthday" value="${p.birthday}" format="yyyy-MM-dd"></s:datefield>
				<s:textfield label="办公电话" name="telephone" value="${p.telephone}"></s:textfield>
				<s:textfield label="移动电话" name="mobile"  value="${p.mobile}"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="邮箱" name="email" required="true"
					value="${p.email}"></s:textfield>
				<s:textfield label="微信" name="weixin" value="${p.weixin}"></s:textfield>
				<s:textfield label="qq" name="qq" value="${p.qq}"></s:textfield>
				<s:textfield label="APP设备ID" name="deviceid" value="${p.deviceid}"></s:textfield>
			</s:row>
			<s:row>
				<s:radio label="状态" name="state" value="${empty p.state?'1':p.state }" cols="2">
					<s:option label="正常" value="1"></s:option>
					<s:option label="停用" value="0"></s:option>
					<s:option label="删除" value="-1"></s:option>
				</s:radio>
			</s:row>
			<s:row>
				<div class="cols3">
				</div>
				<div class="cols">
					<s:button onclick="save();" color="green" label="保存"></s:button>
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
		//console.log($("#myform").getData().name);
		var data = $("#myform").getData();
		$.call("setting.user.save", data , function(rtn) {
			if (rtn.result == 'success') {
				$.closeModal(true);
			}
		},null,{async: false});
	}
	function getpost(){
		var depid=$("#myform").getData().department;
		$("#post").params({depid : depid});
		
	}
	function setdep(){
		var d = $("#myform").getData();
		if(!d.department){
			$.message("请先选择人员所在部门!");
		}
	}
</script>
