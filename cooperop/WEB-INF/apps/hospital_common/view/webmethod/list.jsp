<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="客服端接口管理" ismodal="true">

	<s:row>
	
		<s:row>
			<s:form id="conditions" border="0">
					<s:row>
						<input type="hidden" value="${pageParam.data_webservice_code }" name="data_webservice_code" />
						<%-- <s:select label="刷新频率" value="5000" id="refresh_time">
							<s:option label="100毫秒" value="100" />
							<s:option label="200毫秒" value="200" />
							<s:option label="300毫秒" value="300" />
							<s:option label="500毫秒" value="500" />
							<s:option label="1秒钟" value="1000" />
							<s:option label="2秒钟" value="2000" />
							<s:option label="3秒钟" value="3000" />
							<s:option label="5秒钟" value="5000" />
							<s:option label="10秒钟" value="10000" />
							<s:option label="15秒钟" value="15000" />
							<s:option label="20秒钟" value="20000" />
							<s:option label="30秒钟" value="30000" />
							<s:option label="45秒钟" value="45000" />
							<s:option label="1分钟" value="60000" />
							<s:option label="2分钟" value="120000" />
							<s:option label="3分钟" value="180000" />
							<s:option label="5分钟" value="300000" />
							<s:option label="10分钟" value="600000" />
							<s:option label="15分钟" value="900000" />
							<s:option label="20分钟" value="1200000" />
							<s:option label="30分钟" value="1800000" />
						</s:select> --%>
						<s:textfield label="关键字" name="key" 
							cols="2" placeholder="请输入交易编号、名称、描述、存储过程"></s:textfield>
						<s:button label="查询" onclick="queryWebmethod()" icon="fa fa-search"></s:button>
					</s:row>
			</s:form>
		</s:row>
		
		<s:row>
			<s:table label="接口列表" autoload="false" id="dataTable"
				action="hospital_common.webmethod.query" sort="true" fitwidth="true" icon="fa fa-table">
				<s:toolbar>
					<s:button icon="fa fa-file-o" label="新增接口" size="btn-sm btn-default"
						onclick="addWebmethod()"></s:button>
				</s:toolbar>
				<s:table.fields>
					<s:table.field name="code" datatype="string" label="交易编号" sort="true"
						defaultsort="asc"></s:table.field>
					<s:table.field name="name" datatype="string" label="交易名称" sort="true"></s:table.field>
					<c:choose>
					<c:when test="${pageParam.type ne 'DB' }">
					 <s:table.field name="execute_procedure" datatype="string" label="存储过程" sort="true"></s:table.field> --%>
					</c:when>
					</c:choose>
					<s:table.field name="param_procedure" datatype="string" label="存储过程参数" sort="true"></s:table.field>
					<s:table.field name="description" datatype="string" label="交易描述" sort="true"></s:table.field>
					<s:table.field name="cycle_cron" datatype="string" label="触发周期" sort="true"></s:table.field>
					<s:table.field name="can_manual" datatype="string" label="初始化运行" sort="true"></s:table.field>
					<s:table.field name="look" datatype="template" label="查看" width="160" align="center">
						<a style="margin: 0px 5px;" href="javascript:void(0)"
							onclick="lookTestModule('${pageParam.data_webservice_code}', '$[code]', '${pageParam.type}')">测试</a>
						<c:choose>
						<c:when test="${pageParam.type ne 'DB' }">
						 <a style="margin: 0px 5px;" href="javascript:void(0)"
							onclick="lookRefTable('$[code]', '$[name]')">参数表</a> 
						</c:when>
						</c:choose>
						<a style="margin: 0px 5px;" href="javascript:void(0)"
							onclick="lookLog('$[code]')">定时异常</a>
					</s:table.field>
					<s:table.field label="服务" name="service" datatype="script"
						align="center" width="120">
						var html = [];
						if (record.scheduleException) {
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
							onclick="modifyWebmethod('$[code]', '$[name]')">修改</a>
						<a style="margin: 0px 5px;" href="javascript:void(0)"
							onclick="deleteWebmethod('$[code]', '$[name]')">删除</a>
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

	var stop_refresh = -1;
	$(document).ready(function() {
		//stop_refresh = setInterval(queryWebmethod, 5000);
		queryWebmethod();
		$("#conditions").keypress(function(e) {
			if ((e ? e : event).keyCode == 13) {
				queryWebmethod();
			}
		});
	});
	
	$('#refresh_time').change(function(){
		if (stop_refresh != -1)
			clearInterval(stop_refresh);
		stop_refresh = setInterval(queryWebmethod, this.value);
	});
	
	function addWebmethod() {
		add(null, null);
	}
	
	function deleteWebmethod(code, name) {
		$.confirm('是否确认删除接口 "' + name + '" ？删除后将无法恢复！', function(btn) {
			if (btn) {
				$.call("hospital_common.webmethod.delete", {
					code: code,
					data_webservice_code: '${pageParam.data_webservice_code}',
				}, function(rtn) {
					if (rtn) {
						queryWebmethod();
					}
				});
			}
		});
	}
	
	function modifyWebmethod(code, name) {
		add(code, name);
	}
	
	function queryWebmethod() {
		$("#dataTable").params($("#conditions").getData());
		$("#dataTable").refresh();
	}
	
	function reloadService(code, name, running) {
		$.confirm('是否立即重启 "' + name + ' "？', function(yn){
			if (yn) {
				$.call("hospital_common.webmethod.reload", {
					data_webservice_code: '${pageParam.data_webservice_code}',
					code: code
				}, function(rtn){
					queryWebmethod();
				});
			}
		});
	}
	function stopService(code, name, running) {
		$.confirm('是否立即停止 "' + name + ' "？', function(yn){
			if (yn) {
				$.call("hospital_common.webmethod.stop", {
					data_webservice_code: '${pageParam.data_webservice_code}',
					code: code
				}, function(rtn){
					queryWebmethod();
				});
			}
		});
	}
	
	function add(code, name) {
		$.modal("edit.html", name ? "修改接口-" + name : "增加接口", {
			code: code,
			data_webservice_code: '${pageParam.data_webservice_code}',
			callback: function(rtn) {
				if (rtn)
					queryWebmethod();
			}
		});
	}
	
	function lookRefTable(code, name){
		$.modal("../webtableref/list.html", "参数表管理-" + name, {
			data_webservice_code : '${pageParam.data_webservice_code}',
			data_webservice_method_code: code,
			callback: function(rtn) {
				if (rtn) 
					queryWebmethod();
			}
		});
	} 
	
	function lookTestModule(webservice_code, method_code, type_name) {
		$.modal("../testmodule/client.html", "客服端测试模块[" + webservice_code + "##" + method_code + "]", {
			data_webservice_code: webservice_code,
			code: method_code,
			type: type_name,
			callback: function(rtn) {
				if (rtn) {
					queryWebmethod();
				}
			}
		});		
	}
	
	function lookLog(code){
		$.modal("../schedule/list.html", "客服端定时异常日志-" + 
				'${pageParam.data_webservice_code}' + "##" + code, {
			data_webservice_code : '${pageParam.data_webservice_code}',
			data_webservice_method_code: code,
			callback: function(rtn) {
				if (rtn) 
					queryWebmethod();
			}
		});
	}
	
</script>