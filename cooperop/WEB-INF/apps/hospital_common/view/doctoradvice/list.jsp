<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="医生建议常用语维护" ismodal="true">
	<s:row>
		<s:form border="0" id="form" label="快速查找">
			<s:row>
				<s:textfield label="快速查找" name="filter" placeholder="请输入建议名" cols="2"></s:textfield>
				<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="医生建议常用语管理" autoload="false" action="hospital_common.doctoradvice.query" sort="true" >
			<s:toolbar>
				<s:button icon="" label="新增医生建议" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="advicename" label="建议名称" datatype="" ></s:table.field>
				<s:table.field name="description" label="描述" datatype="" ></s:table.field>
				<s:table.field name="usetimes" label="使用次数" datatype="" ></s:table.field>
				<s:table.field name="isopen" label="是否启用" datatype="script" sort="true">
					if(record.isopen == 'Y'){
						return "是"
					}else{
						return "否"
					}
				</s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" >
					<a href="javascript:void(0)" onclick="update('$[id]')">修改</a>
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
	
	function Add(){
		$.modal("edit.html","新增建议",{
			width:"60%",
			height:"40%",
			/* ts:'${param.ts}',
			vs:'${param.vs}',
			uid:'${param.uid}', */
			callback : function(e){
				query();
			}
		});
	}
	
	function update(id){
		$.modal("edit.html","修改建议",{
			width:"60%",
			height:"40%",
			id:id,
			/* ts:'${param.ts}',
			vs:'${param.vs}',
			uid:'${param.uid}', */
			callback : function(e){
				query();
			}
		});
	}
	
</script>