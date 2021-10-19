<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="报表设计" >
	<s:row>
		<s:form id="qform" fclass="portlet light bordered">
			<s:row>
				<s:radio label="所属产品" name="system_product_code" action="application.common.listProducts" cols="4">
					<s:option value="$[code]" label="$[name]"></s:option>
				</s:radio>
			</s:row>
			<s:row>
				<s:autocomplete label="报表标识" name="id" value="${tcdata.id }" id="autoquery"
					action="crdc.designer.query">
					<s:option value="$[id]" label="$[flag]$[id]($[name])">$[flag]$[id]($[name])</s:option>
				</s:autocomplete>
				<s:radio cols="2" label="状态" name="state" value="a" >
					<s:option value="a" label="全部"></s:option>
					<s:option value="0" label="待发布"></s:option>
					<s:option value="1" label="已发布"></s:option>
				</s:radio>
			</s:row>
			<s:row>
				<s:textfield cols="2" label="关键字" name="filter" value=""/>
				<s:button label="查询" onclick="query()" color="blue"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table cols="4" label="报表列表" sort="false" action="crdc.designer.query" autoload="false" id="dtable" select="multi">
			<s:toolbar>
				<s:button label="导入报表" onclick="upfile();"></s:button>
				<s:button label="导出报表" onclick="downfile();"></s:button>
				<s:button label="新增报表" onclick="adddj();"></s:button>
				<s:button label="修改报表" onclick="modifydj();"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="system_product_code" label="产品"></s:table.field>
				<s:table.field name="id" label="报表标识"></s:table.field>
				<s:table.field name="description" label="报表描述"></s:table.field>
				<s:table.field name="state" label="发布状态" datatype="script" align="center" sort="true">
					if (+record.state == 1) {
						return '<font color="green">已发布</font>';
					} else {
						return '未发布';
					}
				</s:table.field>
				<s:table.field name="oper" datatype="template" label="操作"
					align="left" width="200">
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="modifydj('$[type]', '$[id]', '$[flag]', '$[system_product_code]')">修改</a>
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="deletedj('$[type]', '$[id]', '$[flag]', '$[system_product_code]')">删除</a>
					<a style="margin: 0px 5px;" class="font-red-sunglo"
						href="javascript:void(0)"
						onclick="undeploy('$[type]', '$[id]', '$[flag]', '$[system_product_code]')">停用</a>
			<!-- 		<a style="margin: 0px 5px;" class="font-red-sunglo"
						href="javascript:void(0)"
						onclick="deploy('$[type]', '$[id]', '$[flag]')">发布</a> -->
					<a style="margin: 0px 5px;" class="font-green"
						href="${contextpath}/w/$[system_product_code]/$[type]/$[flag]/$[id].html"
						target="_blank">预览</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
$(document).ready(function(){
	query();
	$("#autoquery").params({type : "chart"});
})
	function adddj() {
		$.openTabPage("new_chart", "新增报表", "crdc.chart.modify", {_pid_: '${param._pid_}'});
	}
function modifydj(type, id, flag,system_product_code) {
	$.openTabPage(system_product_code+"chart_" + id, system_product_code+"_"+id, "crdc.chart.modify", {schemeid: id,
		flag: flag,
		type: type,
		_pid_: '${param._pid_}',
		system_product_code: system_product_code});
}
	/* function deploy(type, id, flag) {
		$.call("crdc.designer.saveAndDeploy", {
			type : type,
			flag : flag,
			id : id
		}, function(rtn) {
			$.success("发布成功!");
			$("#dtable").refresh();
		})
	} */
	function undeploy(type, id, flag,system_product_code) {
		$.call("crdc.designer.undeploy", {
			type : type,
			flag : flag,
			id : id,
			system_product_code: system_product_code
		}, function(rtn) {
			$("#dtable").refresh();
		})
	}
	function deletedj(type, id, flag,system_product_code) {
		$.confirm("是否确认删除此查询？", function(r) {
			if (r) {
				$.call("crdc.designer.delete", {
					type : type,
					flag : flag,
					id : id,
					system_product_code: system_product_code
				}, function(rtn) {
					$.success("删除成功!");
					$("#dtable").refresh();
				})
			}
		})
	}
	function query(){
		var d = $("#qform").getData();
		d.type = "chart";
		$("#dtable").params(d);
		$("#dtable").refresh();
	}
	
	function downfile(){
		var d = $("#dtable").getSelected();
		if(d.length > 0){
			$.call("crdc.designer.downFile", {
				data : $.toJSON(d)
			}, function(rtn) {
				window.open(cooperopcontextpath + "/rm/d/crdc/" + rtn.fileid);
			});
		}else{
			$.message("请先选择导出的数据！");
		}
	}
	function upfile(){
		$.modal(cooperopcontextpath + "/w/crdc/import_file.html", "导入报表", {
			width: '400px',
			height: '400px',
			callback: function(r){
			if(r){
				$("#dtable").refresh();
			}
		}});
	}
</script>
