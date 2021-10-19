<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" ismodal="true">
<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/theme/plugins/jstree/dist/themes/default/style.min.css" />
<script
	src="${pageContext.request.contextPath}/theme/plugins/jstree/dist/jstree.min.js"></script>
<script
	src="${pageContext.request.contextPath}/theme/pages/scripts/ui-tree.js"></script>
	<s:row>
		<s:form label="组织机构管理" >
			<s:toolbar>
			<c:if test="${not empty pageParam.system_user_id}">
				<s:button label="保存" onclick="save();"></s:button>
			</c:if>
			<c:if test="${empty pageParam.system_user_id}">
				<s:button label="保存" onclick="saves();"></s:button>
			</c:if>
			</s:toolbar>
			<s:row>
				<div class="portlet-body">
					<div id="tree_2" class="tree-demo"></div>
				</div>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
$(document).ready(function() {
	treeinit(getTreeData());
});
function setchild(id, da) {
	var child = [];
	for (var i = 0; i < da.length; i++) {
		if (da[i].parent_id == id) {
			var state = {};
			if(da[i].roleid){
				state.selected = true;
				state.opened = true;
			}
			var d = {
				"text" : da[i].name,
				"id": da[i].id,
				"state" :state
			};
			if (da[i].child_num > 0) {
				d.children = setchild(da[i].id, da);
			}
			child.push(d);
		}
	}
	return child;
}
function getTreeData() {
	var r = [];
	$.call("setting.role.queryDeps", {system_user_id: '${pageParam.system_user_id}',system_role_id: '${pageParam.system_role_id}'}, function(rtn) {
		for (var i = 0; i < rtn.length; i++) {
			if (rtn[i].parent_id=='0') {
				var state = {};
				if(rtn[i].roleid){
					state.selected = true;
					state.opened = true;
				}
				var d = {
					"text" : rtn[i].name,
					"id": rtn[i].id,
					"state": state
				};
				if (rtn[i].child_num > 0) {
					d.children = setchild(rtn[i].id, rtn);
				}
				r.push(d);
			}
		}
	}, null, {
		async : false
	});
	return r;
}
function treeinit(d) {
	$('#tree_2').jstree({
		'plugins' : [ "wholerow", "checkbox", "types", "contextmenu" ],
		'core' : {
			"themes" : {
				"responsive" : false
			},
			"check_callback" : false,
			"data" : d
		},
		"types" : {
			"default" : {
				"icon" : "fa fa-folder icon-state-warning icon-lg"
			},
			"file" : {
				"icon" : "fa fa-file icon-state-warning icon-lg"
			}
		}
	});
}
	function save() {
		var data = {system_user_id: '${pageParam.system_user_id}', system_role_id: '${pageParam.system_role_id}'};
		if(!data.system_user_id){
			$.message("请先选择授权人员！");
			return;
		}
		var s = $('#tree_2').jstree("get_checked");
		data.system_department_id = s;
		$.call("setting.role.setPopedom", {jdata : $.toJSON(data)}, function(rtn) {
			if (rtn) {
				location.reload();
			}
		},null,{async: false});
	}
	function saves() {
		var d = $('#pers', parent.document).getData();
		d.system_users=[]
		for (var i in d.chry) {
			var _to = d.chry[i].split("|");
			d.system_users.push({
				system_user_id: _to[1]
			});
		}
		delete d.chry;
		d.system_role_id='${pageParam.system_role_id}';
		if(d.system_users.length==0){
			$.message("请先选择授权人员！");
			return;
		}
		var s = $('#tree_2').jstree("get_checked");
		d.system_department_id = s;
		$.call("setting.role.setPopedoms", {jdata : $.toJSON(d)}, function(rtn) {
			if (rtn) {
				parent.location.reload()
			}
		},null,{async: false});
	}
</script>
