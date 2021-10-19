<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="我的任务列表" dispermission="true">
	<s:row>
		<s:form id="conditions">
			<s:row>
				<s:datefield label="开始日期" name="create_time_b" format="yyyy-MM-dd" value="${pageParam.time }"></s:datefield>
				<s:datefield label="截至日期" name="create_time_e" format="yyyy-MM-dd" value="${pageParam.time }"></s:datefield>
				<s:button label="查询" icon="fa fa-search" color="btn-success" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="optionlist" action="application.task.queryMine" autoload="false" fitwidth="true" sort="true" label="任务列表" limit="10">
			<s:table.fields>
				<s:table.field name="system_product_name" label="所属产品"  sort="true" width="80" app_field="state_field"></s:table.field>
				<s:table.field name="order_no" label="单据号"  sort="true" width="100" app_field="content_field"></s:table.field>
				<s:table.field name="subject" label="主题"  datatype="script" sort="true" app_field="title_field">
					var html = [];
					html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" ');
					html.push('onclick="todeal(\'' + record.info_bill + '\', \'' + record.order_no + '\', ');
					html.push('\'' + record.task_id + '\',\'' + record.order_id + '\',\'' + record.node_name + '\')">');
					if (record.is_cc == 1) {
						html.push('抄送：');
					}
					html.push(record.subject);
					html.push('</a>');
					return html.join("");
				</s:table.field>
				<s:table.field name="node_name" label="当前环节" width="60" app_field="pre_title_field">
				</s:table.field>
				<s:table.field name="create_user_name" label="发起人" width="60" app_field="content_field"></s:table.field>
				<s:table.field name="create_time" label="到达时间" sort="true" width="60"></s:table.field>
				<s:table.field name="expire_mins" label="剩余时间" align="right" sort="true" defaultsort="asc" datatype="script" width="60">
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
					if (record.expire_mins == 99999999) {
						html.push('<font class="font-green">充裕</font>');
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
					align="center" width="60">
					var html = [];
					if (record.is_cc == 1) {
						html.push('待<font class="font-yellow-casablanca">' + (record.operator_name ? record.operator_name.substring(0, record.operator_name.length - 1) : '') + '</font>处理');
					} else {
						html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" ');
						html.push('onclick="to_deal(\'' + record.node_bill + '\', \'' + record.order_no + '\', ');
						html.push('\'' + record.task_id + '\',\'' + record.order_id + '\',\'' + record.node_name + '\')">处理</a>');
					}
					return html.join("");
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(document).ready(function() {
		query();
	});
	function changeProduct() {
		var data = $("#conditions").getData();
		$("#process_id").params({system_product_code: data["system_product_code"]});
	}
	function query() {
		var d = $("#conditions").getData();
		$("#optionlist").params(d);
		$("#optionlist").refresh();
	}
	function to_deal(pageid,order_no,task_id,order_id,node_name, is_cs){
		todeal(pageid,order_no,task_id,order_id,node_name, is_cs);
	}
	function todeal(pageid,order_no,task_id,order_id,node_name, is_cs){
		if(is_cs == 1){
			$.modal(pageid, node_name, {
				pageid: pageid,
				djbh: order_no,
				order_id: order_id,
				task_id: task_id,
				callback : function(rtn){
					if(rtn){
						start = $("#optionlist").dataTable().fnSettings()._iDisplayStart; 
						total = $("#optionlist").dataTable().fnSettings().fnRecordsDisplay();
						var p = $("#optionlist").DataTable().page();
						if((total-start)==1){
							if (start > 0) {
								$("#optionlist").refresh_table(p-1);
							}else{
								$("#optionlist").refresh();
							}
						}else{
							$("#optionlist").refresh_table(p);
						}
					}
				}
			}, is_cs);
			return;
		}
		var u = pageid.split(".").join("/");
		var url = cooperopcontextpath + "/w/" + u + ".html";
		var wei = '99%';
		var hei = '99%';
		var mwei = GetQueryString(pageid,"wei");
		var mhei = GetQueryString(pageid,"hei");
		if(mwei){
			wei = mwei+'px';
		}
		if(mhei){
			hei = mhei+'px';
		}
		$.modal(url,node_name,{
			width :wei,
			height :hei,
			tourl: url,
			djbh: order_no,
			order_id: order_id,
			task_id: task_id,
			callback : function(rtn){
				if(rtn){
					start = $("#optionlist").dataTable().fnSettings()._iDisplayStart; 
					total = $("#optionlist").dataTable().fnSettings().fnRecordsDisplay();
					var p = $("#optionlist").DataTable().page();
					if((total-start)==1){
						if (start > 0) {
							$("#optionlist").refresh_table(p-1);
						}else{
							$("#optionlist").refresh();
						}
					}else{
						$("#optionlist").refresh_table(p);
					}
				}
			}
		});
	}
	function GetQueryString(str,name){
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var after = str.split("?")[1];
	     var r = null;
	     if(after){
	    	 r = after.match(reg)
	     }
	     if(r!=null){
		     return  unescape(r[2]); 
	     }else{
		     return null;
	     }
	}
	function createFreeTask() {
		var url = cooperopcontextpath + "/w/application/task/freetask.html";
		$.modal(url, "新建个人配合单", {
			callback : function(rtn){
				$("#optionlist2").refresh();
			}
		});
	}
	function showdetail(pageid,order_no,task_id,node_name){
		var u = pageid.split(".").join("/");
		var url = cooperopcontextpath + "/w/" + u + ".html";
		$.modal(url,node_name,{
			djbh: order_no,
			task_id: task_id,
			callback : function(rtn){
				$("#optionlist").refresh_talbe($("#optionlist").DataTable().page());
			}
		})
	}
</script>