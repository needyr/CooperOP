<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" ismodal="true">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/plugins/ztree/css/zTreeStyle/zTreeStyle.css" />
<script src="${pageContext.request.contextPath}/theme/plugins/ztree/js/jquery.ztree.core.min.js"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/ztree/js/jquery.ztree.excheck.min.js"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/ztree/js/jquery.ztree.exedit.min.js"></script>
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
<!-- 					<div id="tree_2" class="tree-demo"></div> -->
					<ul id="treeDemo" class="ztree"></ul>
				</div>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
$(document).ready(function() {
	zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, getTreeData());
});
var zTreeObj;
var setting = {
	view: {
	    dblClickExpand: false,
		selectedMulti : true,//可以多选
		showLine: true
	},
	check: {  
	    enable: true ,//显示复选框  
	    chkStyle : "checkbox",
		chkboxType :{ "Y" : "ps", "N" : "ps" } //Y:勾选（参数：p:影响父节点），N：不勾（参数s：影响子节点）[p 和 s 为参数]
	},
	data: {
		key: {
		    title:"t"
		},
		simpleData: {
			enable: true,
			idKey: "id",
			pIdKey: "pId",
			rootPId: null
		}
	},
	callback: {
		onClick: zTreeOnClick
	}
};

function getTreeData() {
	var r = [];
	$.call("setting.role.queryDeps", {system_user_id: '${pageParam.system_user_id}',system_role_id: '${pageParam.system_role_id}'}, function(rtn) {
		for(var i in rtn){
			var data = {
				"id": rtn[i].id, 
				"pId": rtn[i].parent_id, 
				"name": rtn[i].name, 
// 				"icon": rtn[i].child_num>0 ? '/theme/plugins/ztree/css/zTreeStyle/img/diy/1_open.png' : '/theme/plugins/ztree/css/zTreeStyle/img/diy/1_close.png',
				"open": rtn[i].level<4 && rtn[i].child_num>0, 
				"checked": rtn[i].roleid ? true : false
			};
			r.push(data)
		}
	}, null, {async : false});
	return r;
}


function zTreeOnClick(event, treeId, treeNode) {
   zTreeObj.checkNode(treeNode, !treeNode.checked,null, true);
	return false;
};

function save(){
	var data = {system_user_id: '${pageParam.system_user_id}', system_role_id: '${pageParam.system_role_id}'};
	if(!data.system_user_id){
		$.message("请先选择授权人员！");
		return;
	}
	var deps = [];
	var nodes = zTreeObj.getCheckedNodes(true)
	for(var i in nodes){
		deps.push(nodes[i].id);
	}
	data.system_department_id = deps;
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
	var deps = [];
	var nodes = zTreeObj.getCheckedNodes(true)
	for(var i in nodes){
		deps.push(nodes[i].id);
	}
	data.system_department_id = deps;
	$.call("setting.role.setPopedoms", {jdata : $.toJSON(d)}, function(rtn) {
		if (rtn) {
			parent.location.reload()
		}
	},null,{async: false});
}
</script>
