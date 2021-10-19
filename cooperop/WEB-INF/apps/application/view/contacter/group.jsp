<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="创建群" disloggedin="true">
<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/theme/plugins/jstree/dist/themes/default/style.min.css" />
	<script
		src="${pageContext.request.contextPath}/theme/plugins/jstree/dist/jstree.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/theme/pages/scripts/ui-tree.js"></script>
	<s:row>
		<s:form label="创建群" id="myform">
			<s:toolbar>
				<s:button label="完成" onclick="save();"></s:button>
			</s:toolbar>
			<s:row>
				<input type="hidden" name="id" value="${id }"/>
				<s:textfield label="群名称" name="name" value="${name }" cols="2" required="true"></s:textfield>
				<s:switch label="是否需要验证" name="need_audit" value="${need_audit }" ontext="是" offtext="否" onvalue="1" offvalue="0"></s:switch>
				<s:switch label="是否公开" name="is_public" value="${is_public }" ontext="是" offtext="否" onvalue="1" offvalue="0"></s:switch>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<div class="col-md-4">
			<s:form label="机构">
				<s:row>
					<div class="portlet-body">
						<div id="tree_1" class="tree-demo"></div>
					</div>
				</s:row>
			</s:form>
		</div>
		<div class="col-md-4">
			<s:row>
				<s:form label="可选人员">
					<s:toolbar>
						<s:button label="全选" onclick="selectall();"></s:button>
						<s:button label="添加" onclick="addselected();"></s:button>
					</s:toolbar>
					<s:row>
						<div id="p1" class="checkbox-list"
							style="border: 0px !important; line-height: 20px !important;">
						</div>
					</s:row>
				</s:form>
			</s:row>
		</div>
		<div class="col-md-4">
			<s:form label="已选人员">
				<s:toolbar>
					<s:button label="全选" onclick="selectall1();"></s:button>
					<s:button label="移除" onclick="cancelselected();"></s:button>
				</s:toolbar>
				<s:row>
					<div id="p2" class="checkbox-list"
						style="border: 0px !important; line-height: 20px !important;">
					</div>
				</s:row>
			</s:form>
		</div>
	</s:row>
</s:page>
<script type="text/javascript">
$(document).ready(function() {
	var d ;
	var url;
	if('${pageParam.id}'){
		url = "application.contacter.contacterusers";
		d = {type: '${pageParam.type}',id: '${pageParam.id}'};
	}else{
		url = "application.contacter.contactersByIds";
		var s = [];
		s.push({type: "U", id: userinfo.id});
		d = {contacter_ids : $.toJSON(s)}
	}
	if (d) {
		$.call(url, d, function(rtn) {
			if (rtn) {
				for (var i = 0; i < rtn.length; i++) {
					var html = [];
					html.push('<label>');
					html.push('<input type="checkbox" name="selectpersons" ptype="'+rtn[i].type+'" value="'
							+ rtn[i].id + '" title="' + rtn[i].name + '('
							+ rtn[i].department_name + ')"/>');
					html.push(' ' + rtn[i].name + '(' + rtn[i].department_name
							+ ') ');
					html.push('</label>');
					$("#p2").append(html.join(""));
				}
			}
		});
	}
	treeinit(getTreeData());
});
function setchild(id, da) {
	var child = [];
	for (var i = 0; i < da.length; i++) {
		if (da[i].parent_id == id) {
			var d = {
				"text" : da[i].name,
				"id" : da[i].id
			};
			if (da[i].id == '448') {
				d.state = {
					"opened" : true
				};
			}
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
	$.call("setting.dep.query", {
		start : 1,
		limit : 0
	}, function(rtn) {
		for (var i = 0; i < rtn.length; i++) {
			if (rtn[i].parent_id == '0') {
				var d = {
					"text" : rtn[i].name,
					"id" : rtn[i].id
				};
				if (rtn[i].child_num > 0) {
					d.children = setchild(rtn[i].id, rtn);
				}
				d.state = {
					"opened" : true
				};
				r.push(d);
			}
		}
	}, null, {
		async : false
	});
	return r;
}
function treeinit(d) {
	$('#tree_1').jstree({
		'plugins' : [ "wholerow", "types" ],
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
	}).on("changed.jstree", function(e, data) {
		querypersons(data.selected);
	});
}
function querypersons(depid) {
	var data = {
		depid : depid
	};
	data.selected = [];
	$("input:checkbox[name=selectpersons]").each(function() {
		data.selected.push({
			"system_user_id" : $(this).val()
		});
	});
	$("input:checkbox[name=persons]").parent().remove();
	$.call("setting.user.queryByDep", {
		"data" : $.toJSON(data)
	}, function(rtn) {
		if (rtn) {
			for (var i = 0; i < rtn.length; i++) {
				var html = [];
				html.push('<label>');
				html.push('<input type="checkbox" name="persons" ptype="U" value="'
						+ rtn[i].id + '" title="' + rtn[i].name + '('
						+ rtn[i].department_name + ')"/>');
				html.push(' ' + rtn[i].name + '(' + rtn[i].department_name
						+ ') ');
				html.push('</label>');
				$("#p1").append(html.join(""));
			}
		}
	});
}
function selectall() {
	$("[name = persons]:checkbox").attr("checked", true);
}
function selectall1() {
	$("[name = selectpersons]:checkbox").attr("checked", true);
}
function addselected() {
	$("input:checkbox[name=persons]:checked")
			.each(
					function() {
						var $th = $(this);
						var html = [];
						html.push('<label>');
						html
								.push('<input type="checkbox" name="selectpersons" ptype="'+$th.attr("ptype")+'" value="'
										+ $th.val()
										+ '" title="'
										+ $th.attr("title") + '"/>');
						html.push(' ' + $th.attr("title") + ' ');
						html.push('</label>');
						$("#p2").append(html.join(""));
						$th.parent().remove();
					})
}
function cancelselected() {
	$("input:checkbox[name=selectpersons]:checked").each(
			function() {
				var $th = $(this);
				var html = [];
				html.push('<label>');
				html.push('<input type="checkbox" name="persons" ptype="'+$th.attr("ptype")+'" value="'
						+ $th.val() + '" title="' + $th.attr("title")
						+ '"/>');
				html.push(' ' + $th.attr("title") + ' ');
				html.push('</label>');
				$("#p1").append(html.join(""));
				$th.parent().remove();
			});
}
function save(){
	if (!$("form").valid()) {
		return false;	
	}
	var d = $("#myform").getData();
	d.group_users = [];
	$("input:checkbox[name=selectpersons]").each(function() {
		var $th = $(this);
		d.group_users.push({"system_user_id": $th.val()});
	});
	 $.call("application.contacter.createGroup", {data : $.toJSON(d)}, function(rtn) {
		 $.closeModal(true);
	});
}
</script>
