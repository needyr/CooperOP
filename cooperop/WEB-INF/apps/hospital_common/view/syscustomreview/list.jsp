<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="药品自定义审查维护" >
	<s:row>
		<s:form border="0" id="form" label="快速查找">
			<s:row>
				<s:textfield label="关键字" name="filter" placeholder="请输入药品名称或首字母码" cols="2"></s:textfield>
				<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
				<s:row>
					<s:checkbox label="已设置" name="hasset" cols="3" style="border:none">
					<s:option label="药品属性" value="dict_sys_drug_mx"></s:option>
					<s:option label="配伍问题" value="sys_shengfangzl_peiw"></s:option>
					<s:option label="溶媒问题" value="sys_shengfangzl_rongm"></s:option>
					<s:option label="用量问题" value="sys_shengfangzl_yongl"></s:option>
					<s:option label="用法问题" value="sys_shengfangzl_routename"></s:option>
					<s:option label="频率问题" value="sys_shengfangzl_pl"></s:option>
					<s:option label="肌酐清除率" value="sys_shengfangzl_ccr"></s:option>
					<s:option label="禁忌症问题" value="sys_shengfangzl_jjz"></s:option>
					<s:option label="适应症问题" value="sys_shengfangzl_syz"></s:option>
					<s:option label="相互作用问题" value="sys_shengfangzl_xhzy"></s:option>
				</s:checkbox>
				</s:row>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="已设置自定义审查的药品" autoload="true" action="hospital_common.sysdictdrug.queryzdysc" sort="true" >
			<s:toolbar>
				<s:button icon="" label="新增" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="drug_code" label="药品编号" datatype="" ></s:table.field>
				<s:table.field name="drug_name" label="药品名称" datatype="" ></s:table.field>
			    <s:table.field name="druggg" label="药品规格" datatype="" ></s:table.field>
			    <s:table.field name="drug_unit" label="单位" datatype="" ></s:table.field>
				<s:table.field name="shengccj" label="生产厂家" datatype="" ></s:table.field>
				<s:table.field name="pizhwh" label="批准文号" datatype="" ></s:table.field>
				<%-- <s:table.field name="zdy_cz" label="上次操作" datatype="" ></s:table.field> --%>
				<s:table.field name="caozuo" label="操作" datatype="template" >
					<a href="javascript:void(0)" onclick="update('$[drug_code]')">修改</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		$("[name='hasset']").setData(['dict_sys_drug_mx','sys_shengfangzl_peiw','sys_shengfangzl_rongm',
			'sys_shengfangzl_yongl','sys_shengfangzl_routename','sys_shengfangzl_pl',
			'sys_shengfangzl_ccr','sys_shengfangzl_jjz','sys_shengfangzl_syz','sys_shengfangzl_xhzy']);
		query();
	});

	function query(){
		var qdata=$("#form").getData();
		$("#datatable").params(qdata);
		console.log(qdata);
		start = $("#datatable").dataTable().fnSettings()._iDisplayStart; 
		total = $("#datatable").dataTable().fnSettings().fnRecordsDisplay();
		var p = $("#datatable").DataTable().page();
		if((total-start)==1){
			if (start > 0) {
				$("#datatable").refresh_table(p-1);
			}else{
				$("#datatable").refresh();
			}
		}else{
			$("#datatable").refresh_table(p);
		}
		
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
	
	function update(drug_code){
		$.modal("edit.html","修改",{
			width:"90%",
			height:"90%",
			drug_code:drug_code,
			callback : function(e){
				query();
			}
		});
	}
</script>