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
<s:page title="单据设计">
	<s:row>
		<s:form id="qform" fclass="portlet light bordered">
			<s:row>
				<s:radio label="所属产品" name="system_product_code" action="application.common.listProducts" cols="4">
					<s:option value="$[code]" label="$[name]"></s:option>
				</s:radio>
			</s:row>
			<s:row>
				<s:autocomplete label="卡片标识" name="id" value="${tcdata.id }" id="autozl"
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
		<s:table cols="4" label="资料卡片列表" sort="false" action="crdc.designer.query" autoload="false" id="dtable">
			<s:toolbar>
				<s:button label="新增卡片" onclick="adddj();"></s:button>
				<s:button label="修改卡片" onclick="modifydj();"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="system_product_code" label="产品"></s:table.field>
				<s:table.field name="id" label="卡片标识"></s:table.field>
				<s:table.field name="description" label="描述"></s:table.field>
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
						onclick="deploy('$[type]', '$[id]', '$[flag]', '$[system_product_code]')">发布</a>
					<a style="margin: 0px 5px;" class="font-red-sunglo"
						href="javascript:void(0)"
						onclick="undeploy('$[type]', '$[id]', '$[flag]', '$[system_product_code]')">停用</a>
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
	$("#autozl").params({type : "materials"});
})
	function adddj() {
		$.openTabPage("new_chart", "新增卡片", "crdc.materials.modify", {_pid_: '${param._pid_}'});
	}
	function modifydj(type, id, flag,system_product_code ) {
		$.openTabPage(system_product_code+"materials" + id, system_product_code+"_"+id, "crdc.materials.modify", {schemeid: id,
			flag: flag,
			type: type,
			_pid_: '${param._pid_}',
			system_product_code: system_product_code});
	}
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
	function deploy(type, id, flag,system_product_code) {
		$.call("crdc.designer.deploy", {
			type : type,
			flag : flag,
			id : id,
			system_product_code: system_product_code
		}, function(rtn) {
			$.success("发布成功!");
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
		d.type = "materials";
		$("#dtable").params(d);
		$("#dtable").refresh();
	}
</script>
