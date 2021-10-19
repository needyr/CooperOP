<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="首页警示设置列表">
	<s:row>
		<s:table label="首页警示设置列表" autoload="true" id="wtable" action="application.warnSetting.query" sort="true" fitwidth="true">
			<s:toolbar>
				<s:button label="新增" size="btn-sm btn-default" onclick="add()"></s:button>
				<s:button label="分类管理" size="btn-sm btn-default" onclick="addsort()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="sort_name" datatype="string" label="所属分类" sort="true" ></s:table.field>
				<s:table.field name="system_product_name" datatype="string" label="产品" sort="true" ></s:table.field>
				<s:table.field name="order_no" datatype="string" label="显示顺序" sort="true" defaultsort="asc"></s:table.field>
				<s:table.field name="state" datatype="script" label="状态">
					if(record.state == 1){
						return "<font color='green'>启用</font>";
					}else{
						return "<font color='grey'>停用</font>";
					}
				</s:table.field>
				<s:table.field name="discription" datatype="string" label="描述" ></s:table.field>
				<s:table.field name="oper" datatype="template" label="操作" align="left" width="200">
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="modify('$[id]')">修改</a>
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="deletew('$[id]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	function query(){
		$("#wtable").params($("#conForm").getData());
		$("#wtable").refresh();
	}
	function add(){
		$.modal("add.html", "新增", {
			width : '80%',
			height : '60%',
			callback : function(rtn) {
				$("#wtable").refresh();
		    }
		});
	}
	function addsort(){
		$.modal("addsort.html", "分类管理", {
			width : '60%',
			height : '70%',
			callback : function(rtn) {
		    }
		});
	}
	function modify(id){
		$.modal("add.html", "修改", {
			width : '80%',
			height : '60%',
			id : id,
			callback : function(rtn) {
				$("#wtable").refresh();
		    }
		});
	}
	function deletew(id){
		$.confirm("确定删除预警设置？", function(r){
			if(r){
				$.call("application.warnSetting.delete", {"id" : id}, function(rtn) {
					if (rtn) {
						$("#wtable").refresh();
					}
				});
			}
		});
	}
</script>