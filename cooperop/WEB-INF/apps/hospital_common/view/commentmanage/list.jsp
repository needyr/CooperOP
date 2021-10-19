<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>

<s:page title="点评字典管理">
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/theme/plugins/jstree/dist/themes/default/style.min.css" />
	<script src="${pageContext.request.contextPath}/theme/plugins/jstree/dist/jstree.min.js"></script>
	<script src="${pageContext.request.contextPath}/theme/pages/scripts/ui-tree.js"></script>
	<style type="text/css">
		body{
			overflow-y: scroll;
		}
		.jstree-default .jstree-wholerow {
		    border: 1px solid #2c858a;
		    border-top: none;
		}
		.th_name{
		    height: 25px;
		    border: 1px solid #2c858a;
		    border-left: none;
		    display: table-cell;
		    overflow: hidden;
		    text-overflow: ellipsis;
		    white-space: nowrap;
		    text-align: center;
		    vertical-align: middle;
		}
		.com_name_1{
			width: 402px;
			border-right: 1px solid #2c858a;
			display:-webkit-inline-box;
			overflow: hidden;
		    text-overflow: ellipsis;
		    white-space: nowrap;
		}
		.com_name_2{
			width: 378px;
			border-right: 1px solid #2c858a;
			display:-webkit-inline-box;
			overflow: hidden;
		    text-overflow: ellipsis;
		    white-space: nowrap;
		}
		.com_name_3{
			width: 354px;
			border-right: 1px solid #2c858a;
			display:-webkit-inline-box;
			overflow: hidden;
		    text-overflow: ellipsis;
		    white-space: nowrap;
		}
		.com_name_4{
			width: 330px;
			border-right: 1px solid #2c858a;
			display:-webkit-inline-box;
			overflow: hidden;
		    text-overflow: ellipsis;
		    white-space: nowrap;
		}
		.code_s{
			/* padding-left: 5px; */
			width:100px;
			border-right: 1px solid #2c858a;
			display:-webkit-inline-box;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;
		}
		.sort_s{
			width:100px;
			border-right: 1px solid #2c858a;
			display:-webkit-inline-box;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;
			text-align: -webkit-center;
		}
		.comment_code_s{
			width:60px;
			border-right: 1px solid #2c858a;
			display:-webkit-inline-box;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;
			text-align: -webkit-center;
		}
		.description{
			width:400px;
			border-right: 1px solid #2c858a;
			display:-webkit-inline-box;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;
		}
		.descr_s{
			/* width:100px;  */
			/* border-right: 1px solid #2c858a; */
			display:-webkit-inline-box;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;
		}
		.beactive_s{
			width:60px;
			border-right: 1px solid #2c858a;
			display:-webkit-inline-box;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;
			text-align: -webkit-center;
		}
		.jstree-default .jstree-anchor {
		    line-height: 24px;
		    height: 24px;
		    width: 100px; 
		}
		/* 定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸 */
::-webkit-scrollbar
{
    width: 5px;
    height: 10px;
    background-color: #F5F5F5;
}
 
/*定义滚动条轨道 内阴影+圆角*/
::-webkit-scrollbar-track
{
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
    /* border-radius: 10px; */
    background-color: #F5F5F5;
}
 
/* 定义滑块 内阴影+圆角 */
::-webkit-scrollbar-thumb
{
    /* border-radius: 10px; */
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
    background-color: #c5c5c5;
}
	</style>
	
	<s:row>
		<div class="col-md-12">
			<s:form label="点评字典管理">
				<s:toolbar>
					<s:button onclick="add();" label="新增产品"></s:button>
				</s:toolbar>
				<s:row>
					<div id="liketab">
						<div height="25px" >
							<span class="th_name" style="width:453px;border-left: 1px solid #2c858a;">点评名称</span>
							<span class="th_name" style="width:60px">顺序号</span>
							<span class="th_name" style="width:100px">使用代码</span>
							<span class="th_name" style="width:100px">排序号</span>
							<span class="th_name" style="width:60px">是否活动</span>
							<span class="th_name" id="gz">规则说明</span>
						</div>
						<div class="portlet-body">
							<div id="tree_2" class="tree-demo"></div>
						</div>
					<div>
				</s:row>
			</s:form>
		</div>
		<%-- <div class="col-md-12">
			<s:table id="datatable" label="点评字典信息" autoload="false" action="hospital_common.commentmanage.queryComment">
			<s:table.fields>
				<s:table.field name="comment_code" label="点评编号" datatype="string" ></s:table.field>
				<s:table.field name="comment_name" label="点评名称" datatype="string" ></s:table.field>
				<s:table.field name="comment_jiancheng" label="点评简称" datatype="string" ></s:table.field>
				<s:table.field name="system_product_code" label="使用产品代码" datatype="string" ></s:table.field>
				<s:table.field name="system_product_name" label="使用产品名称" datatype="string" ></s:table.field>
				<s:table.field name="system_code" label="使用代码(自定义)" datatype="string" ></s:table.field>
				<s:table.field name="beactive" label="是否启用" datatype="string" width="50"></s:table.field>
				<s:table.field name="sort" label="排序号" datatype="number" ></s:table.field>
				<s:table.field name="is_sys" label="是否系统条目" datatype="script" width="72">
					var is_sys = record.is_sys;
					if(is_sys==1){
						return "是";
					}else{
						return "否";
					}
				</s:table.field>
				<s:table.field name="level" label="级次" datatype="number" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="70">
					<a href="javascript:void(0)" onclick="edit('$[id]')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','$[is_sys]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		</div> --%>
	</s:row>
</s:page>
<script type="text/javascript">
	var gzwidth = window.innerWidth - 859;
	$(document).ready(function() {
		$('#gz').css('width' , gzwidth);
		treeinit(getTreeData());
	});
	function setchild(id, da) {
		var child = [];
		//console.log(da);
		for (var i = 0; i < da.length; i++) {
			if (da[i].parent_id == id) {
				var text = '<span class="com_name_'+da[i].level+'">'+da[i].comment_jiancheng+'</span>'
				+'<span class="comment_code_s">'+da[i].comment_code+'</span>'
				+'<span class="code_s">'+da[i].system_code+'</span>'
				+'<span class="sort_s">'+da[i].sort+'</span>'
				+'<span class="beactive_s">'+da[i].beactive+'</span>'
				+'<span class="descr_s" style="width: '+(gzwidth-1)+'px" title="'+da[i].description+'">'+(da[i].description == null?"":da[i].description)+'</span>';
				var d = {
					"text" : text,
					"g_parent_id" : da[i].comment_jiancheng,
					"message_id" :	 da[i].id,
					"plevel" : da[i].level
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
		$.call("hospital_common.commentmanage.query", {}, function(rtn) {
			for (var i = 0; i < rtn.length; i++) {
				if (!rtn[i].parent_id) {
					var text = '<span class="com_name_'+rtn[i].level+'">'+rtn[i].comment_jiancheng+'</span>'
					+'<span class="comment_code_s">'+rtn[i].comment_code+'</span>'
					+'<span class="code_s">'+rtn[i].system_code+'</span>'
					+'<span class="sort_s">'+rtn[i].sort+'</span>'
					+'<span class="beactive_s">'+rtn[i].beactive+'</span>'
					+'<span class="descr_s" style="width: '+(gzwidth)+'px" title="'+rtn[i].description+'">'+(rtn[i].description == null?"":rtn[i].description)+'</span>';
					var d = {
						"text" : text,
						"g_parent_id" : rtn[i].comment_jiancheng,
						"message_id" : rtn[i].id,
						"plevel" : rtn[i].level
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
							if(obj.original.plevel == 4){
								$.message('该项不允许新增内容');
								return;
							}
							$.modal("add.html","新增点评字典",{
								width:'400px',
								height:'580px',
								"pname" : obj.g_parent_id,
								"ptid" : obj.original.message_id,
								"plevel" : obj.original.plevel,
								"tolevel" : parseInt(obj.original.plevel)+1,//作用于第几级
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
							var first = "";
							if(obj.original.plevel == 1){
								first = "1";
							}
							$.modal("add.html","修改点评字典",{
								width:'400px',
								height:'580px',
								"id" : obj.original.message_id,
								"first": first,
								"tolevel" : obj.original.plevel,//作用于第几级
								callback : function(r){
									if(r){
										reflashTree();
										query();
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
							if(obj.original.plevel == 1){
								$.message("无法删除一级字典");
							}else{
								$.confirm("是否确认删除？删除后无法恢复！",function callback(e){
									if(e==true){
										$.call("hospital_common.commentmanage.getIs_sys", {"id" : obj.original.message_id},function(r){
											if(r.is_sys==1){
												$.message("无法删除系统条目");
											}else{
												$.call("hospital_common.commentmanage.delete", {"id" : obj.original.message_id}, function(rtn) {
													if (rtn) {
														reflashTree();
														query();
													}
												},null,{async: false});
											}
										});
									}
								});
							}
						}
					}
				}
			}
		}).bind("activate_node.jstree", function (obj, e) {
		    var currentNode = e.node;
		    var pid = currentNode.original.message_id;
			$("#datatable").params({"id":pid});
			$("#datatable").refresh();
		});
	}
	
	function reflashTree(){
	    $('#tree_2').jstree(true).settings.core.data = getTreeData();
	    $('#tree_2').jstree(true).refresh();
	}
	
	function add(){
		$.modal("add.html","新增产品",{
			width:'400px',
			height:'580px',
			first:'1',
			callback : function(r){
				if(r){
					reflashTree();
				}
			}
		});
	}
	
	function edit(id){
		$.modal("add.html","修改点评信息",{
			width:'400px',
			height:'580px',
			id:id,
			callback : function(e){
				reflashTree();
				query();
			}
		});
	}
	
	 function Delete(id,is_sys){
		$.confirm("是否确认删除？删除后无法恢复！",function callback(e){
			if(e==true){
				if(is_sys==1){
					$.message("无法删除系统条目");
				}else{
					$.call("hospital_common.commentmanage.delete",{"id":id},function(s){
						reflashTree();
						query();
					});
				}
			}
		});
	}
	 
	 function query(){
		 $("#datatable").refresh();
	}
</script>