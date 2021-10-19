<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="自定义规则操作查看" >
	<s:row>
		<s:form border="0" id="form" label="快速查找">
			<s:row>
				<s:textfield label="快速查找" name="drug" placeholder="请输入药品编码或名称" cols="1"></s:textfield>
				<s:datefield label="操作时间" name="mintime" format="yyyy-MM-dd HH:mm:ss" placeholder="请选择日期" cols="1"></s:datefield>
				<s:datefield label="至" name="maxtime" format="yyyy-MM-dd HH:mm:ss" placeholder="请选择日期" cols="1"></s:datefield>
				<s:textfield label="操作人" name="operator" placeholder="请输入操作人编码或名称" cols="1"></s:textfield>
				<s:textfield label="操作项目" name="xm" cols="1"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="自定义规则操作查看 " autoload="false" action="hospital_common.dictdrug.queryzdycz" sort="true" >
			<s:toolbar>
				<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="drug_code" label="药品编码" datatype="" ></s:table.field>
				<s:table.field name="drug_name" label="药品名称" datatype="" ></s:table.field>
				<s:table.field name="druggg" label="药品规格" datatype="" ></s:table.field>
			    <s:table.field name="drug_unit" label="单位" datatype="" ></s:table.field>
				<s:table.field name="shengccj" label="生产厂家" datatype="" ></s:table.field>
				<s:table.field name="zdy_cz" label="操作项目" datatype="" ></s:table.field>
				<s:table.field name="admin" label="操作人" datatype="" ></s:table.field>
				<s:table.field name="create_time" label="操作时间" datatype="" sort="true" defaultsort="desc"></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="70">
					<a href="javascript:void(0)" onclick="get('$[id]')">查看详情</a>
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
		/* qdata.ts='${param.ts}';
		qdata.vs='${param.vs}';
		qdata.uid='${param.uid}'; */
		$("#datatable").params(qdata);
		$("#datatable").refresh();
		//$.message("数据加载完成！");
	}
	
	function get(id){
		$.modal("/w/hospital_common/customreview/cz_all_mx.html?id="+id,"查看详情",{
	        width:"40%",
	        height:"70%",
	        callback : function(e){
	        }
		});
	}
</script>