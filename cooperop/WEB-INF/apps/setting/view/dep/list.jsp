<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="组织机构列表">
<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/theme/plugins/jstree/dist/themes/default/style.min.css" />
<script
	src="${pageContext.request.contextPath}/theme/plugins/jstree/dist/jstree.min.js"></script>
<script
	src="${pageContext.request.contextPath}/theme/pages/scripts/ui-tree.js"></script>
<div class="col-md-4">
	<iframe src="deptree.html" id ="deptree" width="99%" border="0" height="500">
	</iframe>
</div>
<div class="col-md-8">
	<iframe src="add.html" id ="add_" width="99%" border="0" height="400">
	</iframe>
</div>
</s:page>
<script type="text/javascript">
$(document).ready(function() {
	//treeinit(getTreeData());
});
function setchild(id, da) {
	var child = [];
	for (var i = 0; i < da.length; i++) {
		if (da[i].parent_id == id) {
			var d = {
				"text" : da[i].name,
				"id": da[i].id
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
	$.call("setting.dep.query", {start: 1, limit: 0}, function(rtn) {
		for (var i = 0; i < rtn.length; i++) {
			if (rtn[i].parent_id=='0') {
				var d = {
					"text" : rtn[i].name,
					"id": rtn[i].id
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
		'plugins' : [ "wholerow", "types", "contextmenu" ],
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
		},
		"contextmenu" : {
			"items" : {
				"create" : {
					"label" : "新增",
					"action" : function(data) {
						var inst = jQuery.jstree.reference(data.reference),  
	                    obj = inst.get_node(data.reference);
						$("#add_").attr("src","add.html?parent_id="+obj.id);
					}
				},
				"rename" : {
					"label" : "修改",
					"action" : function(data) {
						var inst = jQuery.jstree.reference(data.reference),  
	                    obj = inst.get_node(data.reference);
						$("#add_").attr("src","add.html?id="+obj.id);
					}
				},
				"remove" : {
					"label" : "删除",
					"action" : function(data) {
						var inst = jQuery.jstree.reference(data.reference),  
	                    obj = inst.get_node(data.reference);  
						$.call("setting.dep.delete", {id: obj.id}, function(rtn) {
							if (rtn.result == 'success') {
								$.message("删除成功",function(){
									$("#usertable").refresh();
								});
							}else if(rtn.result == 'N'){
								$.message("机构下已经添加有职员，不允许直接删除该机构！");
							}
						},null,{async: false});
					}
				}
			}
		}
	});
}
</script>