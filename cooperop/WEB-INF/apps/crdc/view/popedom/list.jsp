<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>

<s:page title="功能权限管理">
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/theme/plugins/jstree/dist/themes/default/style.min.css" />
	<script
		src="${pageContext.request.contextPath}/theme/plugins/jstree/dist/jstree.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/theme/pages/scripts/ui-tree.js"></script>
	<s:row>
		<div class="col-md-8">
			<s:form label="权限管理">
				<s:toolbar>
					<s:button onclick="add();" label="新增产品"></s:button>
				</s:toolbar>
				<s:row>
					<div class="portlet-body">
						<div id="tree_2" class="tree-demo"></div>
					</div>
				</s:row>
			</s:form>
		</div>
	</s:row>
</s:page>
<script type="text/javascript">
	$(document).ready(function() {
		treeinit(getTreeData());
	});
	function setchild(id, da) {
		var child = [];
		for (var i = 0; i < da.length; i++) {
			if (da[i].system_popedom_id_parent == id) {
				var d = {
					"text" : da[i].id + "-" + da[i].name + "[" + da[i].code
							+ "]"
				};
				if (da[i].childnums > 0) {
					d.children = setchild(da[i].id, da);
				}
				child.push(d);
			}
		}
		return child;
	}
	function getTreeData() {
		var r = [];
		$.call("crdc.popedom.query", {}, function(rtn) {
			for (var i = 0; i < rtn.length; i++) {
				if (!rtn[i].system_popedom_id_parent) {
					var d = {
						"text" : rtn[i].id + "-" + rtn[i].name + "["
								+ rtn[i].code + "]"
					};
					if (rtn[i].childnums > 0) {
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
			},
			"contextmenu" : {
				"items" : {
					"create" : {
						"label" : "新增",
						"action" : function(data) {
							var inst = jQuery.jstree.reference(data.reference),  
		                    obj = inst.get_node(data.reference);  
							$.modal("add.html","新增",{
								"system_popedom_id_parent" : obj.text.split("-")[0],
								callback : function(r){
									if(r){
										location.reload();
									}
								}
							});
						}
					},
					"rename" : {
						"label" : "修改",
						"action" : function(data) {
							var inst = jQuery.jstree.reference(data.reference),  
		                    obj = inst.get_node(data.reference);
							var p = inst.get_parent(data.reference);
							$.modal("add.html","修改",{
								"id" : obj.text.split("-")[0],
								callback : function(r){
									if(r){
										location.reload();
									}
								}
							});
						}
					},
					"remove" : {
						"label" : "删除",
						"action" : function(data) {
							var inst = jQuery.jstree.reference(data.reference),  
		                    obj = inst.get_node(data.reference);  
							$.call("crdc.popedom.deletep", {"id" : obj.text.split("-")[0]}, function(rtn) {
								if (rtn.result == 'success') {
									location.reload();
								}
							},null,{async: false});
						}
					}
				}
			}
		});
	}
	function add(){
		$.modal("add.html","新增",{
			callback : function(r){
				if(r){
					location.reload();
				}
			}
		});
	}
</script>