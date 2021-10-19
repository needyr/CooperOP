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
		<s:form label="权限管理">
			<s:toolbar>
				<s:button label="授权" onclick="save();"></s:button>
			</s:toolbar>
			<s:row>
				<div class="portlet-body">
					<div id="tree_2" class="tree-demo"></div>
				</div>
			</s:row>
		</s:form>
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
				}else{
					if(da[i].hasp){
						d.state = {"selected": true};
					}
				}
				child.push(d);
			}
		}
		return child;
	}
	function getTreeData() {
		var r = [];
		$.call("setting.role.queryPopedomByRole", {roleid:'${pageParam.id}'}, function(rtn) {
			for (var i = 0; i < rtn.length; i++) {
				if (!rtn[i].system_popedom_id_parent) {
					var d = {
						"text" : rtn[i].id + "-" + rtn[i].name + "["
								+ rtn[i].code + "]"
					};
					
					if (rtn[i].childnums > 0) {
						d.children = setchild(rtn[i].id, rtn);
					}else{
						if(rtn[i].hasp){
							d.state = {"selected": true};
						}
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
			'plugins' : [ "wholerow", "checkbox", "types", "contextmenu","changed" ],
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
	function save(){
		var d = {roleid:'${pageParam.id}'};
		d.rps = [];
		var s = $('#tree_2').jstree().get_checked(true);
		for(var i =0;i<s.length;i++){
			var rp = {system_popedom_id : s[i].text.split("-")[0]};
			d.rps.push(rp);
		}
		$.call("setting.role.savePopedom", {data:$.toJSON(d)}, function(rtn) {
			if (rtn) {
				$.closeModal(false);
			}
		},null,{async: false});
	}
</script>