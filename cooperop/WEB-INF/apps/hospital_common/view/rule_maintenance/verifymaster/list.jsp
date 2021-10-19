<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="" >
	<s:row>
		<s:form border="0" id="form" label="快速查找">
			<s:row>
				<s:textfield label="快速查找" name="sousuo" placeholder="请输入标识或者描述关键字或者编号" cols="2"></s:textfield>
				<s:button label="查询" icon="" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="" autoload="false" action="hospital_common.rule_maintenance.verifymaster.query" >
			
			<s:table.fields>
			    <s:table.field name="id" label="标识" datatype="" ></s:table.field>
				<s:table.field name="description" label="维护描述" datatype="" ></s:table.field>
				<s:table.field name="bh" label="编号" datatype="" ></s:table.field>
				
				<s:table.field name="caozuo" label="操作" datatype="template" width="70px">
					<a href="javascript:void(0)" onclick="querySubtable('$[id]')">维护规则</a>
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
	
	function querySubtable(id){
		$.modal("/w/hospital_common/rule_maintenance/verifyitems/list.html","规则维护",{
			width:"100%",
			height:"100%",
			parent_bh:id,
			callback : function(e){
				query();
			}
		});
	}
	
	
	
</script>