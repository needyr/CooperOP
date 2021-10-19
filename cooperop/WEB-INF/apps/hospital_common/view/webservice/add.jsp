<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="新增客户端">
	<s:row>
		<div class="note note-warning">
			<ol>
				<li>注意修改定义中的地址端口信息</li>
				<li>注意修改验权参数中的用户信息</li>
				<li><b>初始化脚本请自行拷贝至数据库执行，注意修改相关名称.</b></li>
			</ol>
		</div>
		<s:form label="客户端定义" id="dataForm">
			<s:toolbar>
				<s:button label="保存" icon="fa fa-edit" onclick="save();"></s:button>
				<s:button label="取消" icon="fa fa-ban" onclick="$.closeModal(false);"></s:button>
			</s:toolbar>
			<s:row>
				<s:select required="true" label="客户端类型" name="type" cols="2" value="${type }" action="hospital_common.webservice.listType" onchange="changeType()">
					<s:option value="" label="请选择"></s:option>
					<s:option value="$[code]" label="$[code]-$[name]"></s:option>
				</s:select>
				<s:button icon="fa fa-book" label="查看类型定义" onclick="showTypeDetail()"></s:button>
			</s:row>
			<s:row>
				<s:textfield required="true" name="code" label="客户端编号"></s:textfield>
				<s:textfield required="true" name="name" label="客户端名称" cols="3"></s:textfield>
			</s:row>
			<s:row>
				<s:textarea required="true" label="连接定义" cols="4" name="definition" rows="10" autosize="false"></s:textarea>
			</s:row>
			<s:row>
				<s:textarea label="验权参数" cols="4" name="header" rows="2" autosize="false"></s:textarea>
			</s:row>
			<s:row>
				<s:textarea label="初始化脚本" name="initsql" cols="4" rows="10" readonly="true" autosize="false"></s:textarea>
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
		data.definition = data.definition.trim();
		data.header = data.header.trim();
		delete data.initsql;
		$.call("hospital_common.webservice.insert", data, function(rtn) {
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
		$.call("hospital_common.webservice.type", {
			code : code
		}, function(rtn) {
			$("[name='code']").val(rtn.code + "_1");
			$("[name='name']").val(rtn.name + "_1");
			$("[name='definition']").val(rtn.definition);
			$("[name='header']").val(rtn.initparams);
			$("[name='initsql']").val(rtn.initsql);
		});
	}
	function showTypeDetail() {
		var code = $("select[name='type']").val();
		if (!code) {
			$.warning("请选择所需查看的类型.");
			return;
		}
		$.modal("type.html", "查看客户端类型定义", {
			code : code
		});
	}

	
</script>