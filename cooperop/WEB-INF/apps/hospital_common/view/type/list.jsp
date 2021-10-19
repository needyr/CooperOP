<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="服务端管理">

	<s:row>
	
		<s:row>
			<s:form id="conditions" border="0">
				<s:row>
					<%-- <s:select label="服务端类型" name="type" cols="2" action="hospital_common.type.listType">
						<s:option value="" label="全部" ></s:option>
						<s:option value="$[code]" label="$[code]-$[name]" ></s:option>
					</s:select> --%>
					<s:textfield label="关键字" name="key" placeholder="请输入服务端名称、编号"></s:textfield>
					<s:button label="查询" onclick="queryService()" icon="fa fa-search"></s:button>
				</s:row>
			</s:form>
		</s:row>

		<s:row>
			<s:table label="服务端列表" id="dataTable" autoload="false" 
				action="hospital_common.type.query" icon="fa fa-table" fitwidth="true" sort="true">
				<s:toolbar>
					<s:button label="新建服务端" icon="fa fa-file-o" 
						size="btn-sm btn-default" onclick="addService();"></s:button>
				</s:toolbar>
				<s:table.fields>
					<s:table.field label="服务端编码" name="code" datatype="string"
						sort="true" defaultsort="asc"></s:table.field>
					<s:table.field label="服务端名称" name="name" datatype="string"
						sort="true"></s:table.field>
					<s:table.field label="服务端类型" name="type" datatype="string"
						sort="true"></s:table.field>
					<s:table.field label="服务端接口" name="impl" datatype="template">
						<a style="margin:0 5px;" href="javascript:void(0)"
							onclick="manageService('$[code]', '$[type]', '$[name]');">管理</a>
					</s:table.field>
					<s:table.field label="运行状态" name="service" datatype="script"
						align="center" width="120">
						var html = [];
						if (record.exception) {
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
					<s:table.field label="操作" name="oper" datatype="template"
						align="center" width="80">
						<a style="margin:0 5px;" href="javascript:void(0)"
							onclick="modifyService('$[code]', '$[name]');">修改</a>
						<a style="margin:0 5px;" href="javascript:void(0)"
							onclick="deleteService('$[code]', '$[name]');">删除</a>
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

	$(function(){
		queryService();
		$("#conditions").keypress(function(e){
			if ((e ? e : event).keyCode == 13) {
				queryService();
			}
		});
	});
	function addService() {
		$.modal("add.html", "增加服务端", {
			callback: function(rtn) {
				if (rtn)
					queryService();
			}
		});
	}
	function deleteService(code, name) {
		$.confirm('是否确认删除 "' + name + '" ？删除后将无法恢复！', function(yn){
			if (yn) {
				$.call("hospital_common.type.delete", {
					code: code
				}, function(rtn){
					if (rtn)
						queryService();
				});
			}
		});
	}
	function reloadService(code, name, running) {
		$.confirm('是否立即重启 "' + name + ' "？', function(yn){
			if (yn) {
				$.call("hospital_common.type.reload", {
					code: code
				}, function(rtn){
					$('#dataTable').refresh();
				});
			}
		});
	}
	function stopService(code, name, running) {
		$.confirm('是否立即停止 "' + name + ' "？', function(yn){
			if (yn) {
				$.call("hospital_common.type.stop", {
					code: code
				}, function(rtn){
					$('#dataTable').refresh();
				});
			}
		});
	}
	function modifyService(code, name) {
		$.modal("edit.html", name ? "修改服务端-" + name : "增加服务端", {
			code: code,
			callback: function(rtn) {
				if (rtn)
					queryService();
			}
		});
	}
	function queryService() {
		$("#dataTable").params($("#conditions").getData());
		$("#dataTable").refresh();
	}
	function manageService(code, type_name, name) {
		$.modal("../method/list.html", "接口管理-" + name, {
			data_service_code: code,
			type: type_name,
			callback: function(rtn) {
				if (rtn) {
					queryService();
				}
			}
		});
	}
</script>
