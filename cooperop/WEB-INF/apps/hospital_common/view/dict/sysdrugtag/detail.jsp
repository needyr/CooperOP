<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="药品标签维护">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/plugins/jstree/dist/themes/default/style.min.css" />
<script
	src="${pageContext.request.contextPath}/theme/plugins/jstree/dist/jstree.min.js"></script>
<script
	src="${pageContext.request.contextPath}/theme/pages/scripts/ui-tree.js"></script>
<s:row>
	<div class="col-md-8">
		<s:form label="药品标签维护" id="dataForm">
			<s:toolbar>
				<s:button onclick="add();" label="新增"></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield cols="1" name="filter" label="名称/编号"></s:textfield>
				<s:button onclick="query();" label="筛选"></s:button>
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
	
	function query(){
		$('#tree_2').jstree(true).settings.core.data = getTreeData();
	    $('#tree_2').jstree(true).refresh();
	}
	
	function setchild(id, da) {
		var child = [];
		for (var i = 0; i < da.length; i++) {
			if (da[i].parent_id == id) {
				var d = {
					"text" : da[i].drugtagbh + "-" + da[i].drugtag_show + "[" + da[i].drugtag_shuom
							+ "]",
					"drugtagid" : da[i].drugtagid,
					"drugtagname" : da[i].drugtagname,
					"drugtagbh" : da[i].drugtagbh,
					"is_permission" : da[i].is_permission,
					"is_tag" : da[i].is_tag
				};
				if (da[i].childnums > 0) {
					d.children = setchild(da[i].drugtagid, da);
				}
				child.push(d);
			}
		}
		return child;
	}
	function getTreeData() {
		var r = [];
		var data = $("#dataForm").getData();
		$.call("hospital_common.dict.sysdrugtag.queryTree", {filter: data.filter}, function(rtn) {
			//console.log(rtn)
			for (var i = 0; i < rtn.length; i++) {
				if (!rtn[i].parent_id) {
					var d = {
						"text" : rtn[i].drugtagbh + "-" + rtn[i].drugtag_show + "["
								+ rtn[i].drugtag_shuom + "]",
						"drugtagid" : rtn[i].drugtagid,
						"drugtagname" : rtn[i].drugtagname,
						"drugtagbh" : rtn[i].drugtagbh,
						"is_permission" : rtn[i].is_permission,
						"is_tag" : rtn[i].is_tag
					};
					if (rtn[i].childnums > 0) {
						d.children = setchild(rtn[i].drugtagid, rtn);
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
							$.modal("edit.html","新增",{
								"parent_id" : obj.original.drugtagid,
								"parent_name" : obj.original.drugtagname,
								callback : function(r){
									if(r){
										reflashTree();
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
							$.modal("edit.html","修改",{
								"drugtagid" : obj.original.drugtagid,
								callback : function(r){
									if(r){
										reflashTree();
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
							$.confirm("是否确认删除该条数据？",function callback(e){
								if(e==true){
									$.call("hospital_common.dict.sysdrugtag.delete",{drugtagid: obj.original.drugtagid},function(rtn){
										if(rtn){
											reflashTree();
										}
									})
								}
							},null,{async: false})
						}
					},
					"add_drug" : {
						"label" : "添加药品",
						"action" : function(data) {
							var inst = jQuery.jstree.reference(data.reference),  
		                    obj = inst.get_node(data.reference);
							var p = inst.get_parent(data.reference);
							if(obj.original.is_tag == '1'){
							$.modal("/w/hospital_common/dictextend/dicthisdrug/drug.html","添加药品",{
								"drugtagbh" : obj.original.drugtagbh,
								callback : function(r){
									if(r){
										reflashTree();
									}
								}
							});
							}else{
								$.message('非标签!不能添加药品!');
							}
						}
					},
					"add_user" : {
						"label" : "添加权限医生",
						"action" : function(data) {
							var inst = jQuery.jstree.reference(data.reference),  
		                    obj = inst.get_node(data.reference);
							var p = inst.get_parent(data.reference);
							if(obj.original.is_permission == '1'){
								$.modal("/w/hospital_common/dictextend/user/doctor.html","添加权限医生",{
									"drugtagbh" : obj.original.drugtagbh,
									callback : function(r){
										if(r){
											reflashTree();
										}
									}
								});
							}else{
								$.message('非权限标签!不能添加医生!');
							}
						}
					}
				}
			}
		});
	}
	function add(){
		$.modal("edit.html","新增",{
			callback : function(r){
				if(r){
					reflashTree();
				}
			}
		});
	}
	
	function reflashTree(){
	    $('#tree_2').jstree(true).settings.core.data = getTreeData();
	    $('#tree_2').jstree(true).refresh();
	}
</script>