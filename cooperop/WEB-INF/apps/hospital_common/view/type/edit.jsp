<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="服务端更新" ismodal="true">

	<s:row>
		<s:form label="服务端信息" id="dataForm">
			<s:toolbar>
				<s:button label="保存" icon="fa fa-edit" onclick="save();"></s:button>
				<s:button label="取消" icon="fa fa-ban" onclick="$.closeModal(true);"></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield label="服务端编号" id="id_code" name="code" required="true">${pageParam.code }</s:textfield>
				<s:textfield label="服务端名称" name="name">${name }</s:textfield>
				<s:select required="true" label="服务端类型" name="type" value="${type }" action="hospital_common.type.listType">
					<s:option value="" label="请选择"></s:option>
					<s:option value="$[code]" label="$[code]-$[name]"></s:option>
				</s:select>
				<s:button icon="fa fa-book" label="查看类型定义" onclick="showTypeDetail()"></s:button>
				<s:textarea label="服务描述" name="description" cols="4">${description }</s:textarea>
			</s:row>
		</s:form>	
	</s:row>
	
</s:page>
<%----------------------------------------
	        JAVASCRIPT
-----------------------------------------%>
<script type="text/javascript">

	$(document).ready(function(){
		if ('${pageParam.code}') {
			$("#id_code").attr("disabled",true);
		}
	});
	function save() {
		if (!$("form").valid()) {
			return false;
		}
		var path = '${pageParam.code}' ? "hospital_common.type.update"
					: "hospital_common.type.insert";
		var data = $("#dataForm").getData();
		data.description = data.description.trim();
		$.call(path, data, function(rtn){
			if (rtn) {
				$.closeModal(true);
			}
		});
	}
	function showTypeDetail() {
		var code = $("select[name='type']").val();
		if (!code) {
			$.warning("请选择所需查看的类型.");
			return;
		}
		$.modal("type.html", "查看服务端类型定义", {
			code : code
		});
	}
</script>
