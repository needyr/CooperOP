<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="药品使用权限维护" >
	<s:row>
		<s:form border="0" id="form" label="快速查找">
			<s:row>
				<s:textfield label="关键字" name="filter" placeholder="请输入药品名称或药品首字母码" cols="1"></s:textfield>
				<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="已设置使用权限的药品" autoload="true" action="hospital_common.drugcontrol.query" sort="true" >
			<s:toolbar>
				<s:button icon="fa fa-plus" label="新增" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="drug_code" label="药品编号" datatype="" ></s:table.field>
				<s:table.field name="drug_name" label="药品名称" datatype="" ></s:table.field>
			    <s:table.field name="druggg" label="药品规格" datatype="" ></s:table.field>
			    <s:table.field name="drug_unit" label="单位" datatype="" ></s:table.field>
				<s:table.field name="shengccj" label="生产厂家" datatype="" ></s:table.field>
				<s:table.field name="pizhwh" label="批准文号" datatype="" ></s:table.field>
				<s:table.field name="zdy_cz" label="上次操作" datatype="" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" >
					<a href="javascript:void(0)" onclick="update('$[drug_code]')">修改</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		query();
	});

	function query(){
		var qdata=$("#form").getData();
		$("#datatable").params(qdata);
		$("#datatable").refresh();
	}
	
	function Add(){
		$.modal("edit.html","新增",{
			width:"90%",
			height:"90%",
			callback : function(e){
				query();
			}
		});
	}
	
	function update(spbh){
		$.modal("edit.html","修改",{
			width:"90%",
			height:"90%",
			spbh:spbh,
			callback : function(e){
				query();
			}
		});
	}
</script>