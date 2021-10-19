<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="药品标签">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/plugins/jstree/dist/themes/default/style.min.css" />
<script
	src="${pageContext.request.contextPath}/theme/plugins/jstree/dist/jstree.min.js"></script>
<script
	src="${pageContext.request.contextPath}/theme/pages/scripts/ui-tree.js"></script>
<s:row>
	<div class="col-md-8">
		<s:form label="药品标签" id="dataForm">
			<s:row>
			<s:textfield cols="1" name="filter" label="名称/编号"></s:textfield>
			<s:button onclick="query();" label="筛选"></s:button>
			<s:button onclick="save();" label="保存"></s:button>
			<s:button onclick="zkqb();" label="展开全部"></s:button>
			<s:button onclick="gbqb();" label="关闭全部"></s:button>
			<div class="portlet-body">
				<div id="tree_2" class="tree-demo" style="width:100%;overflow: auto;height:calc(100% - 200px)"></div>
			</div>
			</s:row>
		</s:form>
	</div>
</s:row>
</s:page>
<script type="text/javascript">
	var rtn_data = [];
	var tag_codes = '${param.code}';
	var _check = new Set();
	$(document).ready(function() {
		treeinit(getTreeData());
		$('#tree_2').css('height', $(window).height() - 165)
	});
	
	
	function query(){
		$('#tree_2').jstree(true).destroy();
		treeinit(getTreeData());
		$('#tree_2').css('height', $(window).height() - 165)
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
					"drugtag_show" : da[i].drugtag_show,
					"is_tag" : da[i].is_tag,
					"drugtag_shuom" : da[i].drugtag_shuom
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
		//rtn_data = [];
		var data = $("#dataForm").getData();
		var req = {};
		req.filter = data.filter;
		if('${param.qx}'){
			req.qx = 1;
		}
		$.call("hospital_common.dict.sysdrugtag.queryTree", req, function(rtn) {
			for (var i = 0; i < rtn.length; i++) {
				if (!rtn[i].parent_id) {
					var d = {
						"text" : rtn[i].drugtagbh + "-" + rtn[i].drugtag_show + "["
								+ rtn[i].drugtag_shuom + "]",
						"drugtagid" : rtn[i].drugtagid,
						"drugtagname" : rtn[i].drugtagname,
						"drugtagbh" : rtn[i].drugtagbh,
						"drugtag_show" : rtn[i].drugtag_show,
						"is_tag" : rtn[i].is_tag,
						"drugtag_shuom" : rtn[i].drugtag_shuom
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
			'plugins' : [ "wholerow", "checkbox", "types"],
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
			'checkbox': {
			     "three_state": false
			 }
		}).bind("loaded.jstree",function(e,data){//树形加载前判断哪个节点被选中
			  var node = $('#tree_2').jstree()._model.data
			  if(tag_codes){
				  for(var i in node){
					  var node_data = node[i];
					  if(node_data.id!="#"){
						  if(node_data.original.is_tag  == '1'){
						  if((','+tag_codes+',').indexOf(','+node_data.original.drugtagbh+',') != -1){
							  $("#tree_2").jstree().open_all();
							  var len_check = _check.size;
							  _check.add(node_data.original.drugtagbh);
							  if(len_check != _check.size){
								  rtn_data.push({"code":node_data.original.drugtagbh,
							  		"name":node_data.original.drugtagname,
							  		"jc":node_data.original.drugtag_show,
							  		"all":node_data.original.drugtagbh+"-"+node_data.original.drugtag_show+
							  		"-"+(node_data.original.drugtag_shuom?node_data.original.drugtag_shuom:"")});
							  }
							  _check.add(node_data.original.drugtagbh);
							  $("#"+node_data.id).children('a').click();
							  $("#tree_2").jstree().close_all();
						 }else { $("#tree_2").jstree().close_all(); }
						 }
					  }
				  }
			  }
		  }).on('changed.jstree', function (e, data) {
			var ddd = data.node;
			if(ddd){
			if(ddd.children.length > 0 && ddd.state.selected){
			    $("#"+ddd.id).children('.jstree-ocl').click();
			} 
			if(ddd.state.selected){
				if(ddd.original.is_tag == '1'){
				var len_check = _check.size;
				_check.add(ddd.original.drugtagbh);
				if(len_check != _check.size){
					rtn_data.push({"code":ddd.original.drugtagbh,
			  		"name":ddd.original.drugtagname,
			  		"jc":ddd.original.drugtag_show,
			  		"all":ddd.original.drugtagbh+"-"+ddd.original.drugtag_show+
			  		"-"+(ddd.original.drugtag_shuom?ddd.original.drugtag_shuom:"")});
					if(tag_codes){
						tag_codes = tag_codes + ',' + ddd.original.drugtagbh;
					}else{
						tag_codes = ddd.original.drugtagbh;
					}
				}
				}
			}else{
				var len_check = _check.size;
				_check.delete(ddd.original.drugtagbh);
				if(len_check != _check.size){
					var rtn_data_tmp = rtn_data;
					for(var i=0;i<rtn_data_tmp.length;i++){
						if(rtn_data_tmp[i].code == ddd.original.drugtagbh){
							rtn_data.splice(i,1);
						}
					}
					if(tag_codes){
						var tag_tmp = (tag_codes+',').replace(ddd.original.drugtagbh+',','');
						var last_value = tag_tmp.substr(tag_tmp.length-1,1);
						if(last_value == ','){
							tag_codes = tag_tmp.substr(0,tag_tmp.length-1);
						}else{
							tag_codes = tag_tmp;
						}
					}
				}
			}
			}
		});
	}
	
	function save(){
		/* console.log(rtn_data)
		console.log(tag_codes)
		return */
		$.closeModal(rtn_data);
	}
	
	function zkqb(){
		$("#tree_2").jstree().open_all();
	}
	
	function gbqb(){
		$("#tree_2").jstree().close_all();
	}
</script>