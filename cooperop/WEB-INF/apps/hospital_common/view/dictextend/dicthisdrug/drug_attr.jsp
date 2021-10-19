<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<style>
.attr_box{
	width: 100%;
}
.attr_inpit{
	width: 100px;
	height: 20px;
}
</style>
<s:page title="药品属性维护">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/plugins/jstree/dist/themes/default/style.min.css" />
<script
	src="${pageContext.request.contextPath}/theme/plugins/jstree/dist/jstree.min.js"></script>
<script
	src="${pageContext.request.contextPath}/theme/pages/scripts/ui-tree.js"></script>
<script
	src="${pageContext.request.contextPath}/res/hospital_common/js/input_verify_tools.js"></script>
<s:row>
	<div class="col-md-8">
		<s:form label="药品属性" id="dataForm">
			<s:row>
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
$(document).ready(function() {
	treeinit(getTreeData());
	$('#tree_2').css('height', $(window).height() - 185)
});

function query(){
	$('#tree_2').jstree(true).destroy();
	treeinit(getTreeData());
	$('#tree_2').css('height', $(window).height() - 185)
}

function setchild(id, da) {
	var child = [];
	for (var i = 0; i < da.length; i++) {
		if (da[i].parent_id == id) {
			var d = {
				"text" : whxm(da[i]),
				"xmid" : da[i].xmid,
				"fdtype" : da[i].fdtype,
				"parent_id" : da[i].parent_id,
				"class_code" : da[i].class_code,
				"value" : da[i].value
			};
			if (da[i].childnums > 0) {
				d.children = setchild(da[i].drugtagid, da);
			}
			child.push(d);
		}
	}
	return child;
}

function whxm(data){
	var rtn = [];
	//rtn.push('<div class="attr_box">');
	rtn.push('<p style="display: inherit;width: 200px;">'+data.xmmch+'</p>');
	var select_attr_value = data.select_attr_value;
	if(select_attr_value){
		var selects =  select_attr_value.split(',');
		var check = false;
		if(selects.length == 2){
			for(var i=0;i<selects.length;i++){
				if(selects[i] == '是' || selects[i] == '否'){
					check = true;
				}else{
					check = false;
					break;
				}
			}
		}
		if(check){
			rtn.push('<select onclick="zzmp(event)" onchange="input_change(this)" data-d=\''+JSON.stringify(data)+'\' aria-invalid="false" class="attr_inpit" name="attr_value" value="'+(data.value?data.value:'')+'">');
			rtn.push('<option value=""></option>');
			for(var i=0;i<selects.length;i++){
				if(selects[i] == (data.value?data.value:'')){
					rtn.push('<option value="'+selects[i]+'" selected>'+selects[i]+'</option>');
				}else{
					rtn.push('<option value="'+selects[i]+'">'+selects[i]+'</option>');
				}
			}
			rtn.push('</select>');
		}else{
			rtn.push('<input autocomplete="off" onFocus="add_event(this)" onChange="input_change(this)" data-type="'+type_transform(data.fdtype)+'" onclick="zzmp(event)" data-d=\''+JSON.stringify(data)+'\' style="position: absolute;width: 80px;" class="attr_inpit judge_type" name="attr_value" type="text" value="'+(data.value?data.value:'')+'">');
			rtn.push('<select onchange="xzxm(this)" onclick="zzmp(event)" style="width: 100px;height: 19px;" aria-invalid="false" value="'+(data.value?data.value:'')+'">');
			rtn.push('<option value=""></option>');
			for(var i=0;i<selects.length;i++){
				if(selects[i] == (data.value?data.value:'')){
					rtn.push('<option value="'+selects[i]+'" selected>'+selects[i]+'</option>');
				}else{
					rtn.push('<option value="'+selects[i]+'">'+selects[i]+'</option>');
				}
			}
			rtn.push('</select>');
		}
	}else{
		rtn.push('<input autocomplete="off" onFocus="add_event(this)" onChange="input_change(this)" data-type="'+type_transform(data.fdtype)+'" onclick="zzmp(event)" data-d=\''+JSON.stringify(data)+'\' class="attr_inpit judge_type" name="attr_value" type="text" value="'+(data.value?data.value:'')+'">');
		rtn.push(data.xmdw?data.xmdw:'');
	}
	//rtn.push('</div>');
	return rtn.join('');
}

function type_transform(type){
	if(type == '整数'){
		type = 'int';
	}else if(type == '浮点'){
		type = 'number';
	}
	return type;
}


function input_change(_this){
	var id = $(_this).parent().parent().attr('id');
	var obj = $('#tree_2').jstree().get_node(id);
	var value = $(_this).val();
	var pa = $(_this).parent();
	if(pa.find('select')){
		//pa.find('select').attr('value',value);
		//console.log(value)
		pa.find('select').find("option[value='"+value+"']").attr("selected","selected").siblings().removeAttr("selected");
	}
	if(pa.find('input')){
		pa.find('input').attr('value',value);
	}
	pa.find('i').remove();
	var text = pa.html();
	$('#tree_2').jstree().set_text(obj, text);
}

function xzxm(_this){
	$(_this).parent().children('input[name=attr_value]').val($(_this).val());
	input_change(_this)
}

function getTreeData() {
	var r = [];
	$.call("hospital_common.dictextend.dicthisdrug.queryAttrTree", {drug_code:'${param.drug_code}'}, function(data_attr) {
		var rtn = data_attr.attr;
		for (var i = 0; i < rtn.length; i++) {
			if (!rtn[i].parent_id) {
				var d = {
					"text" : rtn[i].xmmch,
					"xmid" : rtn[i].xmid,
					"fdtype" : rtn[i].fdtype,
					"parent_id" : rtn[i].parent_id,
					"class_code" : rtn[i].class_code,
					"value" : rtn[i].value
				};
				if (rtn[i].childnums > 0) {
					d.children = setchild(rtn[i].class_code, rtn);
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
		'plugins' : [ "wholerow", "types"],
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
	}).bind("ready.jstree",function(e,data){//树形加载前判断哪个节点被选中
		var node = $('#tree_2').jstree()._model.data
		for(var i in node){
			var node_data = node[i];
			if(node_data.id!="#"){
				if(node_data.original.value && node_data.parent){
					var my_is_open = $('#tree_2').jstree().get_node(node_data.parent)
					$('#tree_2').jstree().open_node(my_is_open)
				}
			}
		}
	}).on('changed.jstree', function (e, data) {
		
	});
	
}

function save(){
	//$.closeModal(rtn_data);
	zkqb();
	if(infoState()){
		var list = [];
		$('[name=attr_value]').each(function(){
			if($(this).val()){
				var call_data = eval('(' + $(this).attr('data-d') + ')');
				call_data.attr_value = $(this).val();
				list.push(call_data)
			}
		})
		$.call('hospital_common.dictextend.dicthisdrug.disposeAttr',{data: JSON.stringify(list), drug_code:'${param.drug_code}'},function(rtn){
			$.closeModal(true);
		},null,{async: false})
	}else{
		alert('输入数据存在异常!')
	}
	
}

function zkqb(){
	$("#tree_2").jstree().open_all();
}

function gbqb(){
	$("#tree_2").jstree().close_all();
}

function zzmp(event){
	event.stopPropagation();
}
</script>