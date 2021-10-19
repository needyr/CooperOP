<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="文档流设计器">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/crdc/css/flowdesigner.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/crdc/scripts/flowdesigner.js"></script>
	<div class="row">
		<div class="col-md-2">
			<div class="portlet box green-seagreen">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-cubes"></i>节点定义
					</div>
				</div>
				<div class="portlet-body">
					<div class="panel-group accordion scrollable" id="accordion2">
						<ul class="list-group">
							<li class="list-group-item" fnt="start">
								<i class="fa fa-play"></i>开始节点
							</li>
							<li class="list-group-item" fnt="task">
								<i class="fa fa-user"></i>任务节点
							</li>
							<li class="list-group-item" fnt="countersign">
								<i class="fa fa-users"></i>会签节点
							</li>
							<li class="list-group-item" fnt="auto">
								<i class="fa fa-cog"></i>自动节点
							</li>
							<li class="list-group-item" fnt="end">
								<i class="fa fa-stop"></i>结束节点
							</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="note note-warning" style="margin: 0;
    padding: 10px 30px 10px 15px;">
				<h5 style="font-size:12px;">温馨提示：</h5>
				<ol style="padding:5px;font-size:10px;margin:0px">
					<li>上方的控件采用拖拽式放入设计区</li>
					<li>右键可以设置流程、节点、流向的属性</li>	
					<li>流向的右键建议在到达箭头处点击</li>
				</ol>	
			</div>
		</div>
		<div class="col-md-10">
			<div class="portlet box blue-chambray">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>文档流设计器
					</div>
					<div class="tools">
						<button type="button" class="btn btn-sm btn-default" fdc="save"><i class="fa fa-save"></i>保存</button>
						<button type="button" class="btn btn-sm btn-default" fdc="deploy"><i class="fa fa-share-square-o"></i>发布</button>
						<!-- <button type="button" class="btn btn-sm btn-default" onclick="closeMe();"><i class="fa fa-ban"></i>取消</button> -->
						<a class="divider"></a>
						<button type="button" class="btn btn-sm btn-default" fdc="zoomIn"><i class="fa fa-search-plus"></i>放大</button>
						<button type="button" class="btn btn-sm btn-default" fdc="zoomOut"><i class="fa fa-search-minus"></i>缩小</button>
						<button type="button" class="btn btn-sm btn-default" fdc="fitWidth"><i class="fa fa-arrows-h"></i>适合宽度</button>
						<button type="button" class="btn btn-sm btn-default" fdc="fitHeight"><i class="fa fa-arrows-v"></i>适合高度</button>
						<button type="button" class="btn btn-sm btn-default" fdc="fitPage"><i class="fa fa-arrows"></i>适合页面</button>
						<button type="button" class="btn btn-sm btn-default" fdc="actualSize"><i class="fa fa-refresh"></i>原始大小</button>
						<a class="divider"></a>
						<button type="button" class="btn btn-sm btn-default" fdc="autoLayout"><i class="fa fa-share-alt"></i>自动布局</button>
					</div>
				</div>
				<div class="portlet-body">
					<div class="flowdesigner">
					
					</div>
				</div>
			</div>
		</div>
	</div>
</s:page>
<script type="text/javascript">
$(document).ready(function() {
	$(window).resize(function() {
		$(".flowdesigner").height($(".page-container").height() - $(".flowdesigner").offset().top - 60);
	});
	$(window).resize();
	$(".list-group-item").attr("draggable", "true");
	$(".list-group-item").on("dragstart", function() {
		$(this).attr("selected", "selected");
	});
	$(".flowdesigner").on("dragover", function(ev) {
		ev.preventDefault();
	});
	$(".flowdesigner").on("drop", function(ev) {
		var flow_node_type = $(".list-group-item[selected]").attr("fnt");
		var x = ev.originalEvent.offsetX;
		var y = ev.originalEvent.offsetY;

		$(".list-group-item").removeAttr("selected");
		designer.createNode(flow_node_type, x, y);
	});
	
	$("button[fdc]").click(function() {
		var $this = $(this);
		if (designer[$this.attr("fdc")]) {
			designer[$this.attr("fdc")].call(designer);
		}
	});
	var designer = $(".flowdesigner").flowdesigner();
	if ("${pageParam.system_product_code}" != "" && "${pageParam.id}" != "") {
		designer.load("${pageParam.system_product_code}", "${pageParam.id}");
	} else {
		designer.newFlow();
	}
} );

/* function closeMe() {
	history.back();
} */

</script>
