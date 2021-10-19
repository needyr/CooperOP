<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="接口管理">

	<s:row>

		<s:row>
			<s:form id="conditions" border="0">
				<s:row>
					<input type="hidden" name="data_service_code" value="${pageParam.data_service_code }" />
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
					<s:textfield label="关键字" name="key" cols="2" placeholder="请输入交易编码、名称、描述、存储过程"></s:textfield>
					<s:button label="查询" icon="fa fa-search"
						size="btn-sm btn-default" onclick="queryMethod();"></s:button>
				</s:row>
			</s:form>
		</s:row>

		<s:row>
			<s:table label="接口列表" id="dataTable" autoload="false"
				action="hospital_common.method.query" icon="fa fa-table" fitwidth="true" sort="true">
				<s:toolbar>
					<s:button label="新建接口" icon="fa fa-file-o" 
						size="btn-sm btn-default" onclick="addMethod();"></s:button>
				</s:toolbar>
				<s:table.fields>
					<s:table.field label="交易编号" name="code" datatype="string"
						sort="true" defaultsort="asc"></s:table.field>
					<s:table.field label="交易名称" name="name" datatype="string"
						sort="true"></s:table.field>
					<s:table.field label="存储过程" name="execute_procedure" datatype="string"
						sort="true"></s:table.field>
					<s:table.field label="交易描述" cols="4" name="description" datatype="string"
						sort="true"></s:table.field>
					<s:table.field label="查看" name="look" datatype="template"
						align="center" width="90">
						<a style="margin: 0px 5px;" href="javascript:void(0)"
							onclick="lookTestModule('${pageParam.data_service_code}', '$[code]', '${pageParam.type}')">测试</a>
						<a style="margin:0 5px;" href="javascript:void(0)"
							onclick="lookReft('$[code]', '$[name]');">参数表</a>
					</s:table.field>
					<s:table.field label="操作" name="oper" datatype="template"
						align="center" width="80">
						<a style="margin:0 5px;" href="javascript:void(0)"
							onclick="modifyMethod('$[code]', '$[name]');">修改</a>
						<a style="margin:0 5px;" href="javascript:void(0)"
							onclick="deleteMethod('$[code]', '$[name]');">删除</a>
					</s:table.field>
				</s:table.fields>
			</s:table>
		</s:row>
		
	</s:row>
	
</s:page>
<%-------------------------------------
			JAVASCRIPT
-------------------------------------%>
<script type="text/javascript">

	var stop_refresh = -1;
	$(document).ready(function(){
		//stop_refresh = setInterval(queryMethod, 5000);
		queryMethod();
		$("#conditions").keypress(function(e){
			if ((e ? e : event).keyCode == 13) {
				queryMethod();
			}
		});
	});
	
	$('#refresh_time').change(function(){
		if (stop_refresh != -1)
			clearInterval(stop_refresh);
		stop_refresh = setInterval(queryMethod, this.value);
	});
	
	function addMethod() {
		add(null, null);
	}
	function deleteMethod(code, name) {
		$.confirm('是否确认删除 "' + name + ' "？删除后将无法恢复！', function(yn){
			if (yn) {
				$.call("hospital_common.method.delete", {
					code: code,
					data_service_code: '${pageParam.data_service_code}'
				}, function(rtn){
					queryMethod();
				});
			}
		});
	}
	function modifyMethod(code, name) {
		add(code, name);
	}
	function queryMethod() {
		$("#dataTable").params($("#conditions").getData());
		$("#dataTable").refresh();
	}
	function lookReft(code, name) {
		$.modal("../reft/list.html", "参数表管理-" + name, {
			data_service_method_code: code,
			data_service_code: '${pageParam.data_service_code}',
			callback: function(rtn) {
				if (rtn) {
					queryMethod();
				}
			}
		});
	}
	
	
	function lookTestModule(service_code, method_code, type_name) {
		$.modal("../testmodule/server.html", "服务端测试模块[" + service_code + "##" + method_code + "]", {
			data_service_code: service_code,
			code: method_code,
			type: type_name,
			callback: function(rtn) {
				if (rtn) {
					queryMethod();
				}
			}
		});		
	}
	
	function add(code, name) {
		$.modal("edit.html", name ? "修改接口-" + name : "增加接口", {
			code: code,
			data_service_code: '${pageParam.data_service_code}',
			callback: function(rtn) {
				if (rtn)
					queryMethod();
			}
		});
	}

</script>
