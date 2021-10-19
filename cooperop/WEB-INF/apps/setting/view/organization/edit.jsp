<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="编辑机构信息" ismodal="true">
	<script src="${pageContext.request.contextPath}/res/setting/js/pingyin.js" type="text/javascript"></script>
	<s:row>
		<s:form border="0" id="myform">
			<s:toolbar>
				<s:button icon="fa fa-edit" size="btn-sm btn-default" label="保存" onclick="save();" type="submit"></s:button>
				<s:button icon="fa fa-ban" size="btn-sm btn-default" label="取消" onclick="$.closeModal(false);"></s:button>
			</s:toolbar>
			<s:row>
			<div class="col-md-7">
			<s:row>
				<input type="hidden" name="id" value="${param.id }" />
				<c:if test="${ empty id }">
					<s:textfield label="机构编码" cols="2" onchange="checkJigid(this)" name="jigid" required="true"
						value="${jigid}"></s:textfield>
				</c:if>
				<c:if test="${ not empty id }">
					<s:textfield label="机构编码" cols="2" name="jigid" islabel="true" value="${jigid}"></s:textfield>
				</c:if>
				<s:switch label="是否启用" name="state" onvalue="1" offvalue="0" ontext="启用" offtext="停用" value="${empty id ? '1' : state}"></s:switch>
			</s:row>
			<s:row>
				<s:textfield label="机构名称" cols="4" name="jigname" required="true"
					value="${jigname}"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="机构电话" cols="4" name="jigtel" value="${jigtel}"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="机构地址" cols="4" name="jigaddress" value="${jigaddress}"></s:textfield>
			</s:row>
			</div>
			<div class="col-md-5">
			<s:row>
				<s:image  label="证件" cols="4" name="image" value="${image}" maxlength="1"></s:image>
			</s:row>
			</div>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>

	function checkJigid(t) {
		var jigid = $(t).val();
		//$.confirm('是否确认创建机构编码 "' + jigid + '" ？', function(yn){
		//	if (yn) {
				$.call("setting.organization.checkJigid", {jigid: jigid}, function(rtn) {
					if (rtn) {
						$.message(jigid + " - 机构编码已经注册，请重新编辑。", function() {
							$(t).val("").focus();
						});
					}
				}, null, {async: false});			
		//	}
		//});
	}
	
	function save() {
		if (!$("form").valid()) {
			return false;
		}
		var data = $("#myform").getData();
		data.pym = getPYM(data.jigname);
		var path = "setting.organization.insert";
		if (data.id) {
			path = "setting.organization.update";
		} else {
			delete data.id;
		}
		
		$.call(path, data, function(rtn) {
			$.closeModal(rtn ? true : false);
		});
	}
</script>
