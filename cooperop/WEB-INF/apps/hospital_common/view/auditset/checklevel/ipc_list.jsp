<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="审查等级维护" disloggedin="true">
	
	<s:row>
		<s:form id="queryform" label="快速查找">
			<s:row>
				<s:textfield name="level_name" label="关键字" placeholder="请输入等级名称"></s:textfield>
				<s:button label="查询" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>

	<s:row>
		<s:table id="datatable" label="标准审查等级维护" autoload="false" action="hospital_common.auditset.checklevel.query">
			<s:toolbar>
				<s:button label="新增" onclick="editWindow('',1)"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="level_code" label="等级编号"></s:table.field>
				<s:table.field name="level_name" label="等级名称"></s:table.field>
				<s:table.field name="level_star" label="星级"></s:table.field>
				<s:table.field name="description" label="说明"></s:table.field>
				<s:table.field name="operation" label="操作" datatype="template">
					<a onclick="editWindow('$[p_key]',2)">修改</a>
					<a onclick="delWindow('$[p_key]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>

</s:page>

<script>
	$(function() {
		query();
	});

	function query() {
		var formData = $("#queryform").getData();
		formData.product_code = 'ipc';
		$("#datatable").params(formData);
		$("#datatable").refresh();
	}

	function del(p_key){
		ajax: $.call("hospital_common.auditset.checklevel.delete",{"p_key":p_key},function(s){
			query();
		}) 
	}

	function editWindow(p_key,upOrAdd) {
		$.modal("edit.html", "新增参数", {
			width : "450px",
			height : "55%",
			"p_key":p_key,
			"upOrAdd":upOrAdd,
			"product_code":"ipc",
			callback : function(e) {
				query();
			}

		});
	}
	 
	function delWindow(p_key) {
			$.confirm('确定删除?删除之后无法恢复！',function(choose){
				if(choose == true){
					del(p_key);
				}
			});
		}
	
</script>