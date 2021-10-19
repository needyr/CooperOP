<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="服务更新" ismodal="true">
	<s:row>
		<div class="note note-warning">
			<ol>
				<li>注意修改定义中的地址端口信息</li>
				<li>注意修改验权参数中的用户信息</li>
				<li><b>初始化脚本请自行拷贝至数据库执行，注意修改相关名称.</b></li>
			</ol>
		</div>
		<s:form label="服务信息" id="dataForm">
			<s:toolbar>
				<s:button label="保存" icon="fa fa-edit" onclick="save();"></s:button>
				<s:button label="取消" icon="fa fa-ban" onclick="$.closeModal(true);"></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield label="客户端编号" name="code" id="id_code" required="true">${pageParam.code }</s:textfield>
				<s:textfield label="客户端名称" name="name">${name }</s:textfield>
				<s:select label="客户端类型" name="type" value="${type }" action="hospital_common.webservice.listType">
					<s:option value="" label="请选择" ></s:option>
					<s:option value="DB" label="DB" ></s:option>
					<s:option value="$[code]" label="$[code]-$[name]" ></s:option>
				</s:select>
				<s:button icon="fa fa-book" label="查看类型定义" onclick="showTypeDetail()"></s:button>		
				 <c:choose>
					<c:when test="${type ne 'DB' }">
						<s:textarea label="连接定义" name="definition" cols="4" required="true">${definition_1 }</s:textarea>
					</c:when> 
				  </c:choose>  
				<s:textarea label="验权参数" name="header" cols="4">${header_1 }</s:textarea>
			</s:row>
		</s:form>
	</s:row>
	
</s:page>
<%----------------------------------------
	        JAVASCRIPT
-----------------------------------------%>
<script type="text/javascript">

	$(function(){
		if ('${pageParam.code }') {
			$("#id_code").attr("disabled",true); 
		}
	});
	
	function save() {
		if (!$("form").valid()) {
			return false;	
		}
		var path = '${pageParam.code }' ? "hospital_common.webservice.update"
				: "hospital_common.webservice.insert";
		var data = $("#dataForm").getData();
		if(data.definition){
			data.definition = data.definition.trim();
		}
		data.header = data.header.trim();
		$.call(path, data, function(rtn) {
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
		$.modal("type.html", "查看客户端类型定义", {
			code: code
		});
	}
</script>
