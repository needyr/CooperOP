<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" ismodal="true">
<div class="col-md-4">
	<s:row>
		<s:form id="ucform" label="人员过滤">
			<s:row>
				<input type="hidden" name="system_role_id" value="${pageParam.system_role_id }"/>
				<!-- <s:autocomplete label="人员" name="role_user_id" action="bmc.ssc.pm.user.queryUsers" limit="10" editable="false" cols="2">
					<s:option label="$[name] $[sm]" value="$[id]">
						<span style="display:inline-block;min-width:55px;">$[name]</span>
						<span style="margin-left:5px;color:#aaa;">$[sm]</span>
					</s:option>
				</s:autocomplete> -->
				<s:textfield label="人员" name="role_user_id" cols="2"></s:textfield>
				<s:button label="查询" onclick="query1();" style="margin-left:10px;"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table label="已授权人员" action="setting.role.queryPersonByRole" id="dtable" height="200" autoload="false">
			<s:table.fields>
				<s:table.field name="no" label="工号" width="60"></s:table.field>
				<s:table.field name="system_user_name" label="姓名" width="60"></s:table.field>
				<s:table.field name="oper" datatype="template" label="操作" align="left" >
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="query2('$[system_user_id]')">查看权限</a>
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="deleterole('$[system_user_id]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
	<s:row>
		<s:form label="授权人员" id="myform">
			<s:row>
				<input type="hidden" name="system_role_id" value="${pageParam.system_role_id }"/>
<%-- 				<s:autocomplete label="员工" action="application.contacter.queryMine" params="{&#34;type&#34;:&#34;U&#34;}" name="system_user" cols="4" onchange="query();"> --%>
<%-- 					<s:option label="$[name]" value="$[id]">$[name]</s:option> --%>
<%-- 				</s:autocomplete> --%>
				<s:autocomplete label="员工" action="setting.user.queryV" name="system_user" cols="4" onchange="query();">
					<s:option label="$[name]" value="$[id]">[$[department_name]]$[name]</s:option>
				</s:autocomplete>
			</s:row>
		</s:form>
	</s:row>
</div>
<div class="col-md-8">
	<iframe src="" id ="add_" width="99%" border="0" height="500">
	</iframe>
</div>
</s:page>
<script>
$(document).ready(function() {
	query();
	query1();
});
function query1(){
	var d = $("#ucform").getData();
	$("#dtable").params(d);
	$("#dtable").refresh();
}
function query(){
	var d = $("#myform").getData();
	$("#add_").attr("src","setdeps.html?system_user_id="+d.system_user+"&system_role_id="+d.system_role_id)
}
function query2(system_user){
	var d = $("#myform").getData();
	$("#add_").attr("src","setdeps.html?system_user_id="+system_user+"&system_role_id="+d.system_role_id)
}
function deleterole(id){
	var d = $("#myform").getData();
	$.confirm("删除后无法恢复，是否继续！" ,function (rtn){
		if(rtn){
			$.call("setting.role.deleteRule", {system_user_id: id, system_role_id: d.system_role_id}, function(rtn) {
				if (rtn) {
					$("#dtable").refresh();
				}
			},null,{async: false});
		}
	});
}
</script>
