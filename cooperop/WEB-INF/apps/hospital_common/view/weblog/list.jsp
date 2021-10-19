<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="客服端执行日志">

	<s:row>
		
		<s:row>
			<s:form id="conditions" border="0">
				<s:row>
					<s:datefield label="交易日期(起)" datatype="date" format="yyyy/mm/dd"
						name="begin_date"></s:datefield>
					<s:timefield label="交易时间(起)" datatype="time"
						name="begin_time"></s:timefield>
					<s:datefield label="交易日期(止)" datatype="date" format="yyyy/mm/dd"
						 name="end_date"></s:datefield>
					<s:timefield label="交易时间(止)" datatype="time"
						name="end_time"></s:timefield>
				</s:row>
				<s:row>
					<s:select label="服务编号" name="data_webservice_code"
						action="hospital_common.weblog.queryDistinct" id="webservice_code">
						<s:option label="" value="" />
						<s:option label="$[code]" value="$[code]">$[code]</s:option>
					</s:select>
					<s:select label="交易编号" name="data_webservice_method_code" id="webservice_method_code">
						<s:option label="" value="" />
					</s:select>
					<s:select label="完成情况" name="case" id="case_id" value="0">
						<s:option value="0" label="已完成"></s:option>
						<s:option value="1" label="未完成"></s:option>
					</s:select>
					<s:button label="搜索" icon="fa fa-search" onclick="weblogSearch();"
						style="margin-left:80px;" submit="false"></s:button>
				</s:row>
			</s:form>
		</s:row>
		
		<s:row>
			<s:table id="dataTable" sort="true" fitwidth="true"
				label="日志列表" icon="fa fa-table" autoload="false" action="hospital_common.weblog.query">
				<s:table.fields>
					<s:table.field name="id" label="流水号" datatype="int" sort="true" defaultsort="desc">
					</s:table.field>
					<s:table.field name="data_webservice_code" label="服务编号" datatype="string" sort="true">
					</s:table.field>
					<s:table.field name="data_webservice_method_code" label="交易编号" datatype="string" sort="true">
					</s:table.field>
					<s:table.field name="retry_times" label="重复交易次数" datatype="int" sort="true">
					</s:table.field>
					<s:table.field name="begin_time" label="交易开始时间" datatype="string" sort="true">
					</s:table.field>
					<s:table.field name="end_time" label="交易结束时间" datatype="string" sort="true">
					</s:table.field>
					<s:table.field name="state" label="状态" datatype="string" sort="true">
					</s:table.field>
					<s:table.field name="error_message" label="异常信息" datatype="string">
					</s:table.field>
					<%--<s:table.field name="request" label="请求值" datatype="string" sort="true">
					</s:table.field>
					<s:table.field name="result" label="结果值" datatype="string" sort="true">
					</s:table.field>--%>
					<s:table.field name="look" label="查看" datatype="template"
						align="center" width="140">
						<a style="margin:0 5px;" href="javascript:void(0)"
							onclick="lookParams('$[data_webservice_code]', '$[data_webservice_method_code]','$[id]');">参数</a>
						<a style="margin:0 5px;" href="javascript:void(0)"
							onclick="look('$[data_webservice_code]', '$[data_webservice_method_code]', '$[id]', 'request');">请求值</a>
						<a style="margin:0 5px;" href="javascript:void(0)"
							onclick="look('$[data_webservice_code]', '$[data_webservice_method_code]', '$[id]', 'result');">结果值</a>
					</s:table.field>
				</s:table.fields>
			</s:table>
		</s:row>
		
	</s:row>

</s:page>
<%------------------------------
			JAVASCRIPT
------------------------------%>
<script type="text/javascript">
	
	$(document).ready(function(){
		setTimeout(weblogSearch, 100);
		
		$("#webservice_code").change(function() {
			$("#webservice_method_code").empty();
			$("#webservice_method_code").append($("<option value=' '>" + ' ' + "</option>"));
			if (this.value) {
				$.call("hospital_common.weblog.queryDistinct", {
					data_webservice_code: this.value,
					start: -1,
					limit: -1
				}, function(rtn) {
					if(rtn) {
						for (var i = 0; i < rtn.resultset.length; i++)
							$("#webservice_method_code").append($("<option value="
									+ rtn.resultset[i].code
									+ ">" + rtn.resultset[i].code + "</option>"));
					}
				});
			}
			weblogSearch();
		});
		
		$("#webservice_method_code").change(function(){
			weblogSearch();
		});
		
		$("#case_id").change(function(){
			weblogSearch();
		});
		
	});
	
	function weblogSearch() {
		$("#dataTable").params($("#conditions").getData());
		$("#dataTable").refresh();
	}
	function look(data_webservice_code, data_webservice_method_code, id, method) {
		$.call("hospital_common.weblog.get", {
			id: id
		}, function(rtn) {
			if (rtn) {
				if ("request" == method) {
					$.modal("display.html", data_webservice_code+ "##"
							+ data_webservice_method_code, {
						name: "请求参数",
						value: rtn.request,
						callback: function(rtn) {
							if (rtn)
								weblogSearch();
						}
					});
				} else if ("result" == method) {
					$.modal("display.html", data_webservice_code + "##"
							+ data_webservice_method_code, {
						name: "返回值",
						value: rtn.result,
						callback: function(rtn) {
							if (rtn)
								weblogSearch();
						}
					});
				}
			}
		});
	}
	function lookParams(data_webservice_code, data_webservice_method_code, id) {
		var case_id = $("#case_id").val();
		$.modal("../webparams/list.html", "请求参数-" + data_webservice_code
					+ "##" + data_webservice_method_code, {
			id: id,
			case_id: case_id,
			callback: function(rtn) {
				if (rtn)
					weblogSearch();
			}
		});
	}

</script>