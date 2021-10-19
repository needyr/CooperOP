<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>

<s:page title="药理分类">
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/theme/plugins/jstree/dist/themes/default/style.min.css" />
	<script
		src="${pageContext.request.contextPath}/theme/plugins/jstree/dist/jstree.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/theme/pages/scripts/ui-tree.js"></script>
	<s:row>
		<div class="col-md-8">
			<s:form label="药理分类管理">
				<s:toolbar>
					<s:button label="清空" onclick="cancel()"></s:button>
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
			if (da[i].parent_id == id) {
				var d = {
					"text" : "["+ da[i].drug_ylfl_name + "]-" + da[i].drug_ylfl_code
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
		$.call("hospital_common.dictextend.dicthisdrug.query_ylfl", {}, function(rtn) {
			for (var i = 0; i < rtn.length; i++) {
				if (!rtn[i].parent_id) {
					var d = {
						"text" : "["+ rtn[i].drug_ylfl_name + "]-" +rtn[i].drug_ylfl_code
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
			}
		}).bind('dblclick.jstree',function(event){
			var now_node = $('#tree_2').jstree().get_selected(true)[0];
			if(now_node.children.length <= 0){
				$.closeModal(now_node.text.split(']-')[1]);
			}
	    });;
	}

function cancel(){
	$.closeModal('deletetext');
}
</script>