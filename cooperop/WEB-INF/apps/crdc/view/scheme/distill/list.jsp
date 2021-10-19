<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="提取方案">
<s:row>
	<s:form id="condi">
		<s:row>
			<s:radio label="所属产品" name="system_product_code" action="application.common.listProducts" cols="4">
				<s:option value="$[code]" label="$[name]"></s:option>
			</s:radio>
		</s:row>
		<s:row>
			<s:textfield cols="2" label="关键字" name="keyword" placeholder="输入方案编号，方案名称，方案类型进行查询" />
			<s:button label="查询" onclick="query()" color="blue"></s:button>
		</s:row>
	</s:form>
</s:row>
	<s:row>
		<s:table color="green" label="提取方案" autoload="true" id="mytable"
			action="crdc.scheme.distill.query" sort="true" fitwidth="true">
			<s:toolbar>
				<s:button icon="fa fa-file-o" label="新建方案" size="btn-sm btn-default"
					action="crdc.workflow.designer" onclick="add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="system_product_code" label="产品"
					sort="true" ></s:table.field>
				<s:table.field name="fangabh" datatype="string" label="方案编号"
					sort="true" defaultsort="asc"></s:table.field>
				<s:table.field name="fangalx" datatype="string" label="方案类型"
					sort="true"></s:table.field>
				<s:table.field name="fangamch" datatype="string" label="方案名称"
					sort="true"></s:table.field>
				<s:table.field name="oper" datatype="template" label="操作"
					align="center">
						<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="modify('$[fangabh]', '$[fangalx]')">修改</a>
					<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="deleteFlow('$[fangabh]', '$[fangalx]')">删除</a>
					
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
$(document).ready(function (){
	query();
})
function query(){
		var d = $("#condi").getData();
		$("#mytable").params(d);
		$("#mytable").refresh();
	}
	function add(){
		$.modal("add.html", "新增提取方案", {
			width : '80%',
			height : '80%',
			callback : function(rtn) {
				start = $("#mytable").dataTable().fnSettings()._iDisplayStart; 
				total = $("#mytable").dataTable().fnSettings().fnRecordsDisplay();
				var p = $("#mytable").DataTable().page();
				if((total-start)==1){
					if (start > 0) {
						$("#mytable").refresh_table(p-1);
					}else{
						$("#mytable").refresh();
					}
				}else{
					$("#mytable").refresh_table(p);
				}
		    }
		});
	}
	function modify(fangabh,fangalx) {
		$.modal("add.html", "修改提取方案", {
			fangalx :fangalx,
			fangabh : fangabh,
			width : '80%',
			height : '80%',
			callback : function(rtn) {
				start = $("#mytable").dataTable().fnSettings()._iDisplayStart; 
				total = $("#mytable").dataTable().fnSettings().fnRecordsDisplay();
				var p = $("#mytable").DataTable().page();
				if((total-start)==1){
					if (start > 0) {
						$("#mytable").refresh_table(p-1);
					}else{
						$("#mytable").refresh();
					}
				}else{
					$("#mytable").refresh_table(p);
				}
			}
		});
	}
	function deleteFlow(fangabh,fangalx) {
		$.confirm("是否确认删除该方案？删除后将无法恢复！", function(c) {
			if (c) {
				$.call("crdc.scheme.distill.delete", {
					fangabh:fangabh, 
					fangalx:fangalx
				}, function(rtn) {
					if(rtn=='Y'){
						start = $("#mytable").dataTable().fnSettings()._iDisplayStart; 
						total = $("#mytable").dataTable().fnSettings().fnRecordsDisplay();
						var p = $("#mytable").DataTable().page();
						if((total-start)==1){
							if (start > 0) {
								$("#mytable").refresh_table(p-1);
							}else{
								$("#mytable").refresh();
							}
						}else{
							$("#mytable").refresh_table(p);
						}
					}
				});
			}
		});
	}
</script>