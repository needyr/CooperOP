<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="流程总体控制" dispermission="true">
 	<s:row>
		<s:form label="筛选条件" icon="fa fa-tasks">
			<s:row>
				<s:form id="conditions2" fclass="portlet light bordered">
					<s:row>
						<s:select label="所属产品" name="system_product_code" action="application.common.listProducts" cols="1" value="" onchange="changeProduct()">
							<s:option value="" label="全部"></s:option>
							<s:option value="$[code]" label="$[name]"></s:option>
						</s:select>
						<s:autocomplete label="流程" id="process_id" name="process_id" action="application.task.listProcess" cols="1" limit="10">
							<s:option value="$[id]" label="$[name]($[count])">$[name]($[count])</s:option>
						</s:autocomplete>
						<s:autocomplete label="发起人" name="creator" action="application.contacter.queryMine" params="{&#34;type&#34;:&#34;U&#34;}" cols="1" limit="10">
							<s:option value="$[id]" label="$[name]">$[name]</s:option>
						</s:autocomplete>
					</s:row>
					<s:row>
						<s:datefield label="开始日期" name="create_time_b" format="yyyy-MM-dd"></s:datefield>
						<s:datefield label="截至日期" name="create_time_e" format="yyyy-MM-dd"></s:datefield>
					</s:row>
					<s:row>
						<s:textfield label="单据号/主题" name="keyword" tips="匹配单据号、主题"></s:textfield>
						<s:checkbox label="流程状态" name="process_state" cols="2">
							<s:option label="全部" value=""></s:option>
							<s:option label="进行中" value="1"></s:option>
							<s:option label="已办结" value="2"></s:option>
						</s:checkbox>
						<s:button label="查询" icon="fa fa-search" color="green" onclick="query()"></s:button>
					</s:row>
				</s:form>
			</s:row>
			<s:row>
				<s:table id="optionlist2" action="setting.task.queryTasking" autoload="false" fitwidth="true" sort="true" label="任务列表">
					<s:table.fields>
						<s:table.field name="system_product_name" label="所属产品"  sort="true" width="80"></s:table.field>
						<s:table.field name="order_no" label="单据号"  sort="true" width="120"></s:table.field>
						<s:table.field name="subject" label="主题"  datatype="script" sort="true">
							var html = [];
							html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" ');
							html.push('onclick="showdetail(\'' + record.info_bill + '\', \'' + record.order_no + '\', ');
							html.push('\'' + record.task_id + '\',\'' + record.order_id + '\',\'' + record.node_name + '\')">');
							if (record.is_cc == 1) {
								html.push('抄送：');
							}
							html.push(record.subject);
							html.push('</a>');
							return html.join("");
						</s:table.field>
						<s:table.field name="process_create_time" label="发起时间" align="center" defaultsort="desc" sort="true" width="120"></s:table.field>
						<s:table.field name="state" label="当前状态" width="80" datatype="script">
							var html=[];
							if (record.order_end_time) {
								html.push("已办结");
							} else if (record.system_product_process_id == 'free') {
								html.push('待<font class="font-yellow-casablanca">' + ((record.operator_name && record.operator_name.indexOf(',') > 0) ? record.operator_name.substring(0, record.operator_name.length - 1) : record.operator_name) + '</font>处理');
							} else {
								html.push(record.node_name);
							}
							return html.join("");
						</s:table.field>
						<s:table.field name="operator_name" label="当前处理人" width="80" datatype="script">
							var html=[];
							if (record.order_end_time) {
								html.push("");
							} else {
								html.push(record.operator_name);
							}
							return html.join("");
						</s:table.field>
						<s:table.field name="task_time_start" datatype="datetime" label="到达时间" align="center" sort="true" width="120"></s:table.field>
						<s:table.field name="expire_mins" label="剩余时间" align="right" sort="true" datatype="script" width="80">
							var comtime = function(mins) {
								if (mins / (24 * 60) >= 1) {
									return Math.floor(10 * mins / (24 * 60)) / 10 + "天";
								} else if (mins / 60 >= 1) {
									return Math.floor(10 * mins / 60) / 10 + "小时";
								} else {
									return mins + "分钟";
								}
							}
							var html = [];
							if (record.expire_mins == 99999999 || record.order_end_time) {
								html.push('<font class="font-grey">无</font>');
							} else if (record.expire_mins < 0) {
								html.push('<font class="font-red-flamingo">已过期' + comtime(-record.expire_mins) + '</font>');
							} else if (record.expire_mins > 0 && record.expire_mins <= 24 * 60) {
								html.push('<font class="font-yellow-gold">' + comtime(record.expire_mins) + '</font>');
							} else if (record.expire_mins > 0 && record.expire_mins <= 48 * 60) {
								html.push('<font class="font-yellow">' + comtime(record.expire_mins) + '后到期</font>');
							}
							return html.join("");
						</s:table.field>
						<s:table.field name="oper" datatype="script" label="操作"
							align="center" width="80">
							var html = [];
							if (record.order_end_time) {
							}else{
								//html.push('<a style="margin: 0px 1px;" href="javascript:void(0)" ');
								//html.push('onclick="todeal(\'' + record.process_name + '\', \'' + record.order_no + '\', ');
								//html.push('\'' + record.operator + '\',\'' + record.node_name + '\')">催办</a>');
								//html.push('<a style="margin: 0px 1px;" href="javascript:void(0)" ');
								//html.push('onclick="todeal(\'' + record.node_bill + '\', \'' + record.order_no + '\', ');
								//html.push('\'' + record.task_id + '\',\'' + record.node_name + '\')">补充</a>');
								html.push('<a style="margin: 0px 2px;" href="javascript:void(0)" ');
								html.push('onclick="finishp( ');
								html.push('\'' + record.order_id + '\')">撤销</a>');
							}
							return html.join("");
						</s:table.field>
					</s:table.fields>
				</s:table>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	$(document).ready(function() {
		//query();
	});
	function changeProduct() {
		var data = $("#conditions").getData();
		$("#process_id").params({system_product_code: data["system_product_code"]});
	}
	function query() {
		$("#optionlist2").params($("#conditions2").getData());
		$("#optionlist2").refresh();
	}
	function todeal(pageid,order_no,task_id,order_id,node_name){
		
	}
	function showdetail(pageid,order_no,task_id,order_id,node_name){
		var u = pageid.split(".").join("/");
		var url = cooperopcontextpath + "/w/" + u + ".html";
		$.modal(url,node_name,{
			djbh: order_no,
			task_id: task_id,
			callback : function(rtn){
				
			}
		})
	}
	function finishp(order_id){
		$.confirm("即将强行终止流程！是否继续操作？" ,function (rtn){
			if(rtn){
				$.call("setting.task.finish", {order_id: order_id}, function(rtn){
					if(rtn){
						$.message("流程已终止！", function(){
							query();
						})
					}
				});
			}
		});
	}
	function urgep(process_name, djbh, operator, node_name){
		$.call("setting.task.urgep", {
			process_name: process_name,
			djbh: djbh,
			operator: operator,
			node_name: node_name
			}, function(rtn){
				if(rtn){
					$.message("已通过即时消息通知审核人！", function(){
						//query();
					})
				}
		});
	}
</script>