<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="人员列表">
	<s:row>
		<s:form id="conForm">
			<s:row>
				<s:autocomplete label="部门" name="base_dep" action="setting.dep.querydep">
					<s:option label="$[name]" value="$[id]">$[name]</s:option>
				</s:autocomplete>
				<s:textfield label="关键字查询" name="filter" placeholder="输入人员编号、名字"></s:textfield>
				<s:button label="查询" onclick="query()" color="blue"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table label="人员列表" autoload="true" id="usertable"
			action="setting.user.query" sort="true" fitwidth="true">
			<s:toolbar>
				<s:button icon="fa fa-file-o" label="新增人员" size="btn-sm btn-default"
					onclick="add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="no" datatype="string" label="职工号"
					sort="true"></s:table.field>
				<s:table.field name="name" datatype="string" label="姓名"
					sort="true" ></s:table.field>
				<s:table.field name="department_name" datatype="string" label="所属部门"
					sort="true" defaultsort="asc"></s:table.field>
				<s:table.field name="mobile" datatype="string" label="联系电话"
					sort="true"></s:table.field>
				<s:table.field name="email" datatype="string" label="邮箱"></s:table.field>
				<s:table.field name="state" datatype="script" label="状态">
					var html = [];
					if (record.state == 1) {
						html.push('<font class="font-green">正常</font>');
					} else if(record.state == 0) {
						html.push('<font class="font-red">停用</font>');
					}
					return html.join("");
				</s:table.field>
				<s:table.field name="oper" datatype="template" label="操作" align="left" width="200">
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="resetpassword('$[id]')">重置密码</a>
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="modify('$[id]')">修改</a>
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="deleteuser('$[id]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
function query(){
		$("#usertable").params($("#conForm").getData());
		$("#usertable").refresh();
	}
	function add(){
		$.modal("add.html", "新增", {
			width : '80%',
			height : '60%',
			callback : function(rtn) {
				if(rtn){
					$("#usertable").refresh();
				}
		    }
		});
	}
	function modify(id){
		$.modal("add.html", "修改", {
			width : '80%',
			height : '60%',
			id : id,
			callback : function(rtn) {
				if(rtn){
					$("#usertable").refresh();
				}
		    }
		});
	}
	
	function deleteuser(id){
		$.confirm("删除后无法恢复，是否继续！" ,function (rtn){
			if(rtn){
				$.call("setting.user.delete", {"id" :id}, function(rtn) {
					if (rtn) {
						$("#usertable").refresh();
					}
				},null,{async: false});
			}
		});
	}
	function resetpassword(id){
		$.confirm("重置密码，是否继续！" ,function (rtn){
			if(rtn){
				$.call("setting.user.resetpwd", {"id" :id}, function(rtn) {
					if (rtn) {
						$.message("密码重置成功！");
					}
				},null,{async: false});
			}
		});
	}
</script>