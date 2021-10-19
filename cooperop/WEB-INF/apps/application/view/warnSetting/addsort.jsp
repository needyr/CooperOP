<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" ismodal="true">
	<s:row>
		<s:form id="myform">
			<s:row>
				<input type="hidden" name="id" value="${pageParam.id }"/>
				<s:textfield label="分类名称" name="name" required="true" value="${name}"></s:textfield>
				<s:textfield label="显示图标" name="icon" value="${icon}"></s:textfield>
				<s:textfield label="显示序号" name="order_no" required="true" value="${order_no}"></s:textfield>
				<s:button label="保存" onclick="save();" color="btn-info"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table label="分类列表" action="application.warnSetting.querySort" autoload="true" id="wtable" height="300">
			<s:table.fields>
				<s:table.field name="name" datatype="string" label="分类名称"></s:table.field>
				<s:table.field name="icon" datatype="string" label="分类图标"></s:table.field>
				<s:table.field name="order_no" datatype="string" label="排序"></s:table.field>
				<s:table.field name="opr" datatype="template" label="操作">
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="modify('$[id]')">修改</a>
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="deletew('$[id]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script>
	function save() {
		$.call("application.warnSetting.saveSort", $("#myform").getData(), function(rtn) {
			if (rtn) {
				location.reload();
			}
		});
	}
	function modify(id) {
		$.call("application.warnSetting.addsort", {id: id}, function(rtn) {
			if (rtn) {
				console.log(rtn)
				for(var i in rtn){
					$("#myform").find("[name="+i+"]").setData(rtn[i]);
				}
			}
		});
	}
	function deletew(id){
		$.confirm("确定删除分类？", function(r){
			if(r){
				$.call("application.warnSetting.deleteSort", {"id" : id}, function(rtn) {
					if (rtn) {
						$("#wtable").refresh();
					}
				});
			}
		});
	}
</script>
