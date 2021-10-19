<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="角色列表">
	<s:row>
		<s:form id="conForm">
			<s:row>
				<s:select label="所属产品" name="system_product_code" action="application.common.listProducts">
					<s:option value="$[code]" label="$[name]"></s:option>
				</s:select>
				<s:textfield label="角色名称" name="namekey" tips="角色名称"></s:textfield>
				<s:autocomplete label="人员" name="user_id" action="setting.user.queryV">
					<s:option label="$[name]" value="$[id]">
						<span>$[name]</span>
						<span style="color:#999;margin-left:10px;">$[mobile]</span>
					</s:option>
				</s:autocomplete>
				<s:button label="查询" onclick="query()" color="blue"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table label="角色列表" autoload="true" id="roletable"
			action="setting.role.query" sort="true" fitwidth="true">
			<s:toolbar>
				<s:button icon="fa fa-file-o" label="新增" size="btn-sm btn-default"
					onclick="add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="system_product_code" datatype="string" label="所属产品"
					sort="true" ></s:table.field>
				<s:table.field name="name" datatype="string" label="角色名称"
					sort="true" defaultsort="asc"></s:table.field>
				<s:table.field name="description" datatype="string" label="描述"
					sort="true"></s:table.field>
				<s:table.field name="oper" datatype="template" label="操作" align="left" width="260">
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="sq('$[id]')">授权</a>
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="modify('$[id]')">修改</a>
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="deleterole('$[id]','$[name]')">删除</a>
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="settingperson('$[id]')">人员设置</a>
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="settingpersons('$[id]')">批量添加人员</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
function query(){
		$("#roletable").params($("#conForm").getData());
		$("#roletable").refresh();
	}
	function add(){
		$.modal("add.html", "新增", {
			width : '50%',
			height : '40%',
			callback : function(rtn) {
				if(rtn){
					$("#roletable").refresh();
				}
		    }
		});
	}
	function modify(id){
		$.modal("add.html", "修改", {
			width : '50%',
			height : '40%',
			id : id,
			callback : function(rtn) {
				if(rtn){
					$("#roletable").refresh();
				}
		    }
		});
	}
	function sq(id){
		$.modal("popedom.html", "授权", {
			width : '50%',
			height : '80%',
			id : id,
			callback : function(rtn) {
				if(rtn){
					$("#roletable").refresh();
				}
		    }
		});
	}
	function settingperson(id){
		$.modal("settingperson.html", "授权人员", {
			width : '80%',
			height : '90%',
			system_role_id : id,
			callback : function(rtn) {
				if(rtn){
					$("#roletable").refresh();
				}
		    }
		});
	}
	function settingpersons(id){
		$.modal("settingpersons.html", "批量授权人员", {
			width : '80%',
			height : '90%',
			system_role_id : id,
			callback : function(rtn) {
				if(rtn){
					$("#roletable").refresh();
				}
		    }
		});
	}
	function deleterole(id,name) {
		$.confirm('确定要删除\"'+name+'\"的角色吗？',function(rt){
			if(rt){
				$.call("setting.role.delete", {id: id}, function(rtn) {
					if (rtn) {
						$("#roletable").refresh();
					}
				},null,{async: false});
			}
		})
	}
</script>