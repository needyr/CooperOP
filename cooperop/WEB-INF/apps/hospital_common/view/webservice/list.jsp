<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="客服端管理">

	<s:row>
	
		<s:row>
			<s:form id="conditions" border="0">
					<s:row>
						<s:select label="客户端类型" name="type" cols="2" action="hospital_common.webservice.listType">
							<s:option value="" label="全部" ></s:option>
							<s:option value="$[code]" label="$[code]-$[name]" ></s:option>
						</s:select>
						<s:textfield label="关键字" name="key" placeholder="请输入客服端名称、编号"></s:textfield>
						<s:button label="查询" onclick="queryWebservice()" icon="fa fa-search"></s:button>
					</s:row>
			</s:form>
		</s:row>
		
		<s:row>
			<s:table label="客服端列表" autoload="false" id="dataTable"
				action="hospital_common.webservice.query" sort="true" fitwidth="true" icon="fa fa-table">
				<s:toolbar>
					<s:button icon="fa fa-file-o" label="新增客服端" size="btn-sm btn-default"
						onclick="addWebservice()"></s:button>
				</s:toolbar>
				<s:table.fields>
					<s:table.field name="code" datatype="string" label="客服端编号" sort="true"
						defaultsort="asc"></s:table.field>
					<s:table.field name="name" datatype="string" label="客服端名称" sort="true"></s:table.field>
					<s:table.field name="type" datatype="string" label="客服端类型" sort="true"></s:table.field>
					<s:table.field name="look" datatype="template" label="客服端接口">
						<a style="margin: 0px 5px;" href="javascript:void(0)"
							onclick="lookMethod('$[code]', '$[type]', '$[name]')">管理</a>
					</s:table.field>
					<s:table.field label="运行状态" name="service" datatype="script"
						align="center" width="120">
						var html = [];
						if (record.exception == "needvalid") {
							html.push('<a class="btn btn-xs btn-danger" onclick="weimobValid(\'' + record.code + '\', \'' + record.name + '\');">立即授权</a>');
						} else if (record.exception) {
							html.push('<span class="font-red">启动异常</span>');
						} else if (record.running) {
							html.push('<span class="font-green">正在运行</span>');
						} else {
							html.push('<span class="font-red">已停止</span>');
						}
						html.push('<a style="margin:0 5px;" href="javascript:void(0)" onclick="reloadService(\'' + record.code + '\', \'' + record.name + '\');">重启</a>');
						html.push('<a style="margin:0 5px;" href="javascript:void(0)" onclick="stopService(\'' + record.code + '\', \'' + record.name + '\');">停止</a>');
						return html.join("");
					</s:table.field>
					<s:table.field name="oper" datatype="template" label="操作" align="center" width="80">
						<a style="margin: 0px 5px;" href="javascript:void(0)"
							onclick="modifyWebservice('$[code]', '$[name]')">修改</a>
						<a style="margin: 0px 5px;" href="javascript:void(0)"
							onclick="deleteWebservice('$[code]', '$[name]')">删除</a>
					</s:table.field>
				</s:table.fields>
			</s:table>
		</s:row>
		
	</s:row>
	
</s:page>
<%----------------------------------------
	        JAVASCRIPT
-----------------------------------------%>
<script type="text/javascript">

	$(document).ready(function() {
		queryWebservice();
		$("#conditions").keypress(function(e) {
			if ((e ? e : event).keyCode == 13) {
				queryWebservice();
			}
		});
	});
	
	function addWebservice() {
		add(null, null);
	}
	
	function deleteWebservice(code, name) {
		$.confirm('是否确认删除客服端 "' + name + '" ？删除后将无法恢复！', function(btn) {
			if (btn) {
				$.call("hospital_common.webservice.delete", {code:code}, function(rtn) {
					if (rtn) {
						queryWebservice();
					}
				});
			}
		});
	}
	
	function modifyWebservice(code, name) {
		$.modal("edit.html", name ? "修改客服端-" + name : "增加客服端", {
			code: code,
			callback: function(rtn) {
				if (rtn)
					queryWebservice();
			}
		});
	}
	
	function queryWebservice() {
		$("#dataTable").params($("#conditions").getData());
		$("#dataTable").refresh();
	}
	
	function reloadService(code, name, running) {
		$.confirm('是否立即重启 "' + name + ' "？', function(yn){
			if (yn) {
				$.call("hospital_common.webservice.reload", {
					code: code
				}, function(rtn){
					queryWebservice();
				});
			}
		});
	}
	function stopService(code, name, running) {
		$.confirm('是否立即停止 "' + name + ' "？', function(yn){
			if (yn) {
				$.call("hospital_common.webservice.stop", {
					code: code
				}, function(rtn){
					queryWebservice();
				});
			}
		});
	}
	
	function add(code, name) {
		$.modal("add.html", "增加客户端", {
			callback: function(rtn) {
				if (rtn)
					queryWebservice();
			}
		});
	}
	
	function lookMethod(code, type_name, name){
		$.modal("../webmethod/list.html", "接口管理-" + name, {
			data_webservice_code: code,
			type: type_name,
			callback: function(rtn) {
				if (rtn) 
					queryWebservice();
			}
		});
	}
	
	function weimobValid(code, name) {
		$.call("hospital_common.webservice.edit", {
			code: code
		}, function (rtn) {
			if (rtn) {
				$.modal($.parseJSON(rtn.header_1).authurl, name + "授权页面", {
					callback: function(rtn) {
						queryWebservice();
					}
				});
			}
		});
	}
</script>