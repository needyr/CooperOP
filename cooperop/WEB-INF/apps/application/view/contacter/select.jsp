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
<s:page title="选择组织架构" disloggedin="true">
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/theme/plugins/jstree/dist/themes/default/style.min.css" />
	<script
		src="${pageContext.request.contextPath}/theme/plugins/jstree/dist/jstree.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/theme/pages/scripts/ui-tree.js"></script>
	<s:row>
		<div class="col-md-4">
			<s:form label="机构">
				<s:toolbar>
					<s:button label="添加" onclick="selectDep();"></s:button>
					<%-- <s:button label="添加" onclick="addselected();"></s:button> --%>
				</s:toolbar>
				<s:row>
					<div class="portlet-body">
						<div id="tree_1" class="tree-demo"></div>
					</div>
				</s:row>
			</s:form>
		</div>
		<div class="col-md-4">
			<%-- <s:row>
				<s:form label="搜索人员" id="selectp">
					<s:toolbar>
						<s:button label="添加" onclick="addselected1();"></s:button>
					</s:toolbar>
					<s:row>
						<s:autocomplete name="keyperson" action="setting.user.querymine" label="筛选" cols="4" placeholder="输入名字，部门，或拼音首字母查询">
							<s:option label="$[name]" value="$[id]">$[name]</s:option>
						</s:autocomplete>
					</s:row>
				</s:form>
			</s:row> --%>
			<s:row>
				<s:form label="可选人员">
					<s:toolbar>
						<s:button label="全选" onclick="selectall();"></s:button>
						<%-- <s:button label="添加" onclick="addselected();"></s:button> --%>
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
					<s:button label="全部取消" onclick="selectall1();"></s:button>
					<%-- <s:button label="取消" onclick="cancelselected();"></s:button> --%>
					<s:button label="完成选择" onclick="sureselected();"></s:button>
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
	var a=null;
	var b=[];
	$(document).ready(function() {
		var contacter_ids = '${param.contacter_ids}';
		if ('${param.contacter_ids}') {
			for(var i=0;i<JSON.parse(contacter_ids).length;i++){
				if(JSON.parse(contacter_ids)[i].type == 'D'){
					b.push(JSON.parse(contacter_ids)[i].id);
				}
			}
			$.call("application.contacter.contactersByIds", {contacter_ids : contacter_ids}, function(rtn) {
				if (rtn) {
					for (var i = 0; i < rtn.length; i++) {
						var html = [];
						if(rtn[i].type == 'D'){
							html.push('<label style="cursor:pointer" ondblclick="removedep(this)" name="selectpersons" ptype="D" value="'
									+ rtn[i].id
									+ '" title="'
									+ rtn[i].name + '">');
							html.push(' ' + rtn[i].name + '(' + rtn[i].department_name
									+ ') ');
							html.push('</label>');
							$("#p2").append(html.join(""));
						}else{
							html.push('<label type="checkbox" name="selectpersons" ondblclick="removeperson(this)" ptype="'+rtn[i].type+'" value="'
									+ rtn[i].id + '" title="' + rtn[i].name + '('
									+ rtn[i].department_name + ')">');
							html.push(' ' + rtn[i].name + '(' + rtn[i].department_name
									+ ') ');
							html.push('</label>');
							$("#p2").append(html.join(""));
						}
					}
				}
			},null,{async: false});
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
				if (da[i].child_num > 0) {
					d.children = setchild(da[i].id, da);
				}
				if (da[i].id == '448') {
					d.state = {
						"opened" : true
					};
				}
				for(var j=0;j<b.length;j++){
					if(da[i].id == b[j]){
						d.state = {"selected": true};
						break;
					}
				}
				child.push(d);
			}
		}
		return child;
	}
	function getTreeData() {
		var r = [];
		var d = {
				start : 1,
				limit : 0
			};
		if('${pageParam.queryDep}'){
			d.cid='${pageParam.queryDep}';
			querypersons(d.cid);
		}
		var cid = d.cid;
		$.call("setting.dep.query", d, function(rtn) {
			for (var i = 0; i < rtn.length; i++) {
				if (rtn[i].parent_id == '0' || rtn[i].id == cid) {
					var d = {
						"text" : rtn[i].name,
						"id" : rtn[i].id
					};
					if (rtn[i].child_num > 0) {
						d.children = setchild(rtn[i].id, rtn);
					}
					for(var j=0;j<b.length;j++){
						if(rtn[i].id == b[j]){
							d.state = {"selected": true};
							break;
						}
					}
					if(d.state == null){
						d.state = {
							"opened" : true
						};
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
		$('#tree_1').jstree({
			'plugins' : [ "wholerow","checkbox", "types" ],
			'core' : {
				"themes" : {
					"responsive" : false
				},
				"check_callback" : false,
				"data" : d
			},
			'checkbox' :{
				"three_state" : false,
				"cascade" : "down",
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
// 			var d=data.selected[data.selected.length-1];
// 			if(d!=null){
// 				querypersons(d);
// 			}
// 			a=data.selected;
			var d=data.selected;
			if(d != null){
				querypersons(d);
			}
			a=data.selected;
		});
	}
	function querypersons(depid) {
		var data = {
			depid : depid
		};
		if('${pageParam.scheduling_type}'){
			data.scheduling_type = '${pageParam.scheduling_type}';
		}
		data.selected = [];
		$("#p2").find("label").each(function() {
			data.selected.push({
				"system_user_id" : $(this).attr("value")
			});
		});
		$("#p1").find("label").each(function() {
			$(this).remove();
		})
		$.call("setting.user.queryByDep", {
			"data" : $.toJSON(data)
		}, function(rtn) {
			if (rtn) {
				for (var i = 0; i < rtn.length; i++) {
					var html = [];
					html.push('<label style="cursor:pointer" ondblclick="addperson(this)" name="persons" ptype="U" value="'
							+ rtn[i].id + '" title="' + rtn[i].name + '('
							+ rtn[i].department_name + ')">');
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
		$("#p1").find("label").each(function(){
			var $th = $(this);
			var html = [];
			html.push('<label style="cursor:pointer" ondblclick="removeperson(this)" name="selectpersons" ptype="'+$th.attr("ptype")+'" value="'
							+ $th.attr("value")
							+ '" title="'
							+ $th.attr("title") + '">');
			html.push(' ' + $th.attr("title") + ' ');
			html.push('</label>');
			$("#p2").append(html.join(""));
			$th.remove();
		});
	}
	function selectDep() {
		if(b.length !=0 || a != null){
			$.call("setting.dep.query",{depSelected : a,depHasSelected : b},function(rtn){
				if(rtn){
					for(var i=0 ;i<rtn.length;i++){
						b.push(rtn[i].id);
						var html = [];
						html.push('<label style="cursor:pointer" ondblclick="removedep(this)" name="selectpersons" ptype="D" value="'
										+ rtn[i].id
										+ '" title="'
										+ rtn[i].name + '">');
						html.push(' ' + rtn[i].name + '(' + rtn[i].name
								+ ') ');
						html.push('</label>');
						$("#p2").append(html.join(""));
					}
				}
			}, null, {
				async : false});
		}
	}
	function selectall1() {
		$("#p2").find("label").each(function(){
			var $th = $(this);
			var html = [];
			if($th.attr("ptype") == 'U'){
				html.push('<label style="cursor:pointer" ondblclick="addperson(this)" name="selectpersons" ptype="'+$th.attr("ptype")+'" value="'
								+ $th.attr("value")
								+ '" title="'
								+ $th.attr("title") + '">');
				html.push(' ' + $th.attr("title") + ' ');
				html.push('</label>');
				$("#p1").append(html.join(""));
			}
			$th.remove();
		});
		b=[];
	}
	function addperson(_this){
		var $th = $(_this);
		var html = [];
		html.push('<label style="cursor:pointer" ondblclick="removeperson(this)" name="persons" ptype="'+$th.attr("ptype")+'" value="'
						+ $th.attr("value")
						+ '" title="'
						+ $th.attr("title") + '">');
		html.push(' ' + $th.attr("title") + ' ');
		html.push('</label>');
		$("#p2").append(html.join(""));
		$th.remove();
	}
	function removeperson(_this){
		var $th = $(_this);
		var html = [];
		if($th.attr("ptype") == 'U'){
			html.push('<label style="cursor:pointer" ondblclick="addperson(this)" name="persons" ptype="'+$th.attr("ptype")+'" value="'
					+ $th.attr("value") + '" title="' + $th.attr("title")
					+ '">');
			html.push(' ' + $th.attr("title") + ' ');
			html.push('</label>');
			$("#p1").append(html.join(""));
		}
		$th.remove();
	}
	function removedep(_this){
		var $th = $(_this);
		var n = b.indexOf($th.attr("value"));
		if(n!=-1){
		    b.splice(n,1);
		}
		var html = [];
		$th.remove();
	}
	function sureselected(){
		var d = {};
		d.data = [];
		$("#p2").find("label").each(function(){
			var $th = $(this);
			d.data.push({"type": $th.attr("ptype"), "id": $th.attr("value"),"name": $th.attr("title")});
		});
		$.closeModal(d);
	}
</script>
