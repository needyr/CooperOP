<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="新增服务端">
	<s:row>
		<div class="note note-warning">
			<ol>
				<li>不可编辑部分主要提供客户端连接时的参考信息</li>
				<li>提供给客户端时注意修改相关参数</li>
			</ol>
		</div>
		<s:form label="服务端定义" id="dataForm">
			<s:toolbar>
				<s:button label="保存" icon="fa fa-edit" onclick="save();"></s:button>
				<s:button label="取消" icon="fa fa-ban" onclick="$.closeModal(false);"></s:button>
			</s:toolbar>
			<s:row>
				<s:select required="true" label="服务端类型" name="type" cols="2" value="${type }" action="hospital_common.type.listType" onchange="changeType()">
					<s:option value="" label="请选择"></s:option>
					<s:option value="$[code]" label="$[code]-$[name]"></s:option>
				</s:select>
				<s:button icon="fa fa-book" label="查看类型定义" onclick="showTypeDetail()"></s:button>
			</s:row>
			<s:row>
				<s:textfield required="true" name="code" label="服务端编号"></s:textfield>
				<s:textfield required="true" name="name" label="服务端名称" cols="3"></s:textfield>
			</s:row>
			<s:row>
				<s:textarea label="描述" cols="4" name="description" rows="2"></s:textarea>
			</s:row>
			<s:row>
				<s:textarea readonly="true" label="连接定义参考" cols="4" name="definition" rows="10" autosize="false"></s:textarea>
			</s:row>
			<s:row>
				<s:textarea readonly="true" label="连接验权参考" cols="4" name="initparams" rows="2" autosize="false"></s:textarea>
			</s:row>
			<s:row>
				<s:textarea readonly="true" label="备注" cols="4" name="remark" rows="5" autosize="false"></s:textarea>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	function save() {
		if (!$("form").valid()) {
			return false;
		}
		var data = $("#dataForm").getData();
		data.description = data.description.trim();
		delete data.definition;
		delete data.initparams;
		delete data.remark;
		$.call("hospital_common.type.insert", data, function(rtn) {
			if (rtn) {
				$.closeModal(true);
			}
		});
	}

	function changeType() {
		var code = $("select[name='type']").val();
		if (!code) {
			return;
		}
		$.call("hospital_common.type.type", {
			code : code
		}, function(rtn) {
			$("[name='code']").val(rtn.code + "_1");
			$("[name='name']").val(rtn.name + "_1");
			$("[name='definition']").val(rtn.definition);
			$("[name='initparams']").val(rtn.initparams);
			$("[name='remark']").val(rtn.remark);
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