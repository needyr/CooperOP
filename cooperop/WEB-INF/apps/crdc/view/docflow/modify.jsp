<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="">
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/theme/plugins/datatables/extensions/Scroller/css/dataTables.scroller.min.css" />
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/theme/plugins/datatables/extensions/ColReorder/css/dataTables.colReorder.min.css" />
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/theme/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" />
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/theme/plugins/datatables/media/js/jquery.dataTables.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/theme/plugins/datatables/extensions/TableTools/js/dataTables.tableTools.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/theme/plugins/datatables/extensions/ColReorder/js/dataTables.colReorder.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/theme/plugins/datatables/extensions/Scroller/js/dataTables.scroller.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/theme/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script>
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/theme/plugins/bootstrap-summernote/summernote.css"></link>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/theme/plugins/bootstrap-summernote/summernote.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/theme/plugins/bootstrap-summernote/lang/summernote-zh-CN.js"></script>
	<script
		src="${pageContext.request.contextPath}/theme/pages/scripts/components-context-menu.js"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/theme/plugins/bootstrap-contextmenu/bootstrap-contextmenu.js"></script>
	<script
		src="${pageContext.request.contextPath}/theme/scripts/controls/controls.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/theme/scripts/controls/layout/form.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/theme/scripts/controls/layout/row.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/theme/scripts/controls/layout/table.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/theme/scripts/controls/layout/tabpanel.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/theme/scripts/controls/layout/toolbar.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/theme/scripts/controls/input/textfield.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/theme/scripts/controls/input/password.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/theme/scripts/controls/input/textarea.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/theme/scripts/controls/input/datefield.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/theme/scripts/controls/input/timefield.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/theme/scripts/controls/input/checkbox.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/theme/scripts/controls/input/radio.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/theme/scripts/controls/input/richeditor.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/theme/scripts/controls/input/select.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/theme/scripts/controls/input/switch.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/theme/scripts/controls/input/button.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/theme/scripts/controls/input/file.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/theme/scripts/controls/input/image.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/theme/scripts/controls/input/taskhistory.js"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/theme/plugins/jquery.json.min.js"></script>
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/res/crdc/css/flowdesigner.css"></link>
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/res/crdc/css/designer.css">
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/res/crdc/scripts/docdesigner.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/res/crdc/scripts/docdrag.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/res/crdc/scripts/docflowdesigner.js"></script>
	<s:toolbar>
		<s:button fdc="save" icon="fa fa-save" label="保存" onclick="save();"></s:button>
		<s:button fdc="deploy" icon="fa fa-share-square-o" label="发布" onclick="deploy();"></s:button>
		<s:button onclick="closeMe();" icon="fa fa-ban" label="取消"></s:button>
	</s:toolbar>
	<s:row>
		<s:tabpanel>
			<s:form label="文档设计器" active="true">
				<s:row>
					<div id="context-menu">
						<ul class="dropdown-menu" role="menu">
							<li><a href="javascript:void(0);" atype="setting"> <i
									class="icon-settings"></i> 单据域属性编辑
							</a></li>
							<li><a href="javascript:void(0);" atype="delete"> <i
									class="icon-trash"></i> 删除
							</a></li>
						</ul>
					</div>
					<div class="row">
						<div class="col-md-2">
							<div class="portlet box green-seagreen">
								<div class="portlet-title">
									<div class="caption">
										<i class="fa fa-cubes"></i>组件库
									</div>
								</div>
								<div class="portlet-body" id="tool_box">
									<div class="panel-group accordion scrollable" id="accordion2">
										<div class="panel panel-default">
											<div class="panel-heading">
												<h4 class="panel-title">
													<a class="accordion-toggle collapsed"
														data-toggle="collapse" data-parent="#accordion2"
														href="#collapse_2_1" aria-expanded="false"> <i
														class="fa fa-th"></i>布局组件
													</a>
												</h4>
											</div>
											<div id="collapse_2_1" class="panel-collapse collapse"
												aria-expanded="false" style="height: 0px;">
												<div class="panel-body">
													<ul class="list-group" stype="layout">
														<!-- <li class="list-group-item" stype="toolbar"
														ondragover="allowDrop(event)" draggable="true"
														ondragstart="dragadd(event, this)"><i
														class="fa fa-credit-card"></i>工具条</li> -->
														<li class="list-group-item" stype="row"
															ondragover="allowDrop(event)" draggable="true"
															ondragstart="dragadd(event, this)"><i
															class="fa fa-tasks"></i>行</li>
														<li class="list-group-item" stype="row_hidden"
															ondragover="allowDrop(event)" draggable="true"
															ondragstart="dragadd(event, this)"><i
															class="fa fa-tasks"></i>隐藏区</li>
														<li class="list-group-item" stype="form"
															ondragover="allowDrop(event)" draggable="true"
															ondragstart="dragadd(event, this)"><i
															class="fa fa-list-alt"></i>表单</li>
														<li class="list-group-item" stype="table"
															ondragover="allowDrop(event)" draggable="true"
															ondragstart="dragadd(event, this)"><i
															class="fa fa-table"></i>表格</li>
														<li class="list-group-item" stype="tabpanel"
															ondragover="allowDrop(event)" draggable="true"
															ondragstart="dragadd(event, this)"><i
															class="fa fa-columns"></i>多页签</li>
														<!-- <li class="list-group-item" stype="list"
														ondragover="allowDrop(event)" draggable="true"
														ondragstart="dragadd(event, this)"><i class="fa fa-list"></i>列表
													</li> -->
													</ul>
												</div>
											</div>
										</div>
										<div class="panel panel-default">
											<div class="panel-heading">
												<h4 class="panel-title">
													<a class="accordion-toggle collapsed" data-toggle="collapse"
														data-parent="#accordion2" href="#collapse_2_2"
														aria-expanded="false"> <i class="fa fa-edit"></i>输入组件
													</a>
												</h4>
											</div>
											<div id="collapse_2_2" class="panel-collapse collapse"
												aria-expanded="false" style="height: 0px;">
												<div class="panel-body">
													<ul class="list-group" stype="control">
														<li class="list-group-item" stype="textfield"
															ondragover="allowDrop(event)" draggable="true"
															ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>文本框
														</li>
														<li class="list-group-item" stype="datefield"
															ondragover="allowDrop(event)" draggable="true"
															ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>日期控件
														</li>
														<li class="list-group-item" stype="timefield"
															ondragover="allowDrop(event)" draggable="true"
															ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>时间控件
														</li>
														<li class="list-group-item" stype="autocomplete"
															ondragover="allowDrop(event)" draggable="true"
															ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>智能选择
														</li>
														<li class="list-group-item" stype="select"
															ondragover="allowDrop(event)" draggable="true"
															ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>下拉选择
														</li>
														<li class="list-group-item" stype="radio"
															ondragover="allowDrop(event)" draggable="true"
															ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>单选
														</li>
														<li class="list-group-item" stype="checkbox"
															ondragover="allowDrop(event)" draggable="true"
															ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>复选
														</li>
														<li class="list-group-item" stype="switch"
															ondragover="allowDrop(event)" draggable="true"
															ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>逻辑开关
														</li>
														<li class="list-group-item" stype="textarea"
															ondragover="allowDrop(event)" draggable="true"
															ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>文本区域
														</li>
														<li class="list-group-item" stype="richeditor"
															ondragover="allowDrop(event)" draggable="true"
															ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>富文本编辑器
														</li>
														<li class="list-group-item" stype="image"
															ondragover="allowDrop(event)" draggable="true"
															ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>图片
														</li>
														<li class="list-group-item" stype="file"
															ondragover="allowDrop(event)" draggable="true"
															ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>文件
														</li>
														<li class="list-group-item" stype="button"
															ondragover="allowDrop(event)" draggable="true"
															ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>按钮
														</li>
													</ul>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-10">
							<div class="portlet box blue-chambray">
								<div class="portlet-title">
									<div class="caption">
										<i class="fa fa-edit"></i>文档表单
									</div>
									<div class="tools">
										<button type="button" class="btn btn-sm btn-default"
											onclick="setpage();">
											<i class="fa fa-cogs"></i> 单据汇总设置
										</button>
									</div>
								</div>
								<div class="portlet-body" id="_uicontent_"
									ondragover="allowDrop(event)" ondrop="dropPage(this)"
									style="overflow: hidden; overflow-y: auto; padding-bottom: 50px;"></div>
							</div>
						</div>
					</div>
				</s:row>
			</s:form>
			<s:form label="流程设计器">
				<s:row>
					<div class="row">
						<div class="col-md-2">
							<div class="portlet box green-seagreen">
								<div class="portlet-title">
									<div class="caption">
										<i class="fa fa-cubes"></i>节点定义
									</div>
								</div>
								<div class="portlet-body" id="tool_box2">
									<div class="panel-group accordion scrollable" id="accordion3">
										<ul class="list-group">
											<li class="list-group-item" fnt="start"><i
												class="fa fa-play"></i>开始节点</li>
											<li class="list-group-item" fnt="task"><i
												class="fa fa-user"></i>任务节点</li>
											<li class="list-group-item" fnt="countersign"><i
												class="fa fa-users"></i>会签节点</li>
											<li class="list-group-item" fnt="auto"><i
												class="fa fa-cog"></i>自动节点</li>
											<li class="list-group-item" fnt="end"><i
												class="fa fa-stop"></i>结束节点</li>
										</ul>
									</div>
								</div>
							</div>
							<div class="note note-warning"
								style="margin: 0; padding: 10px 30px 10px 15px;">
								<h5 style="font-size: 12px;">温馨提示：</h5>
								<ol style="padding: 5px; font-size: 10px; margin: 0px">
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
										<i class="fa fa-edit"></i>流程设计器
									</div>
									<div class="tools">
										<button type="button" class="btn btn-sm btn-default"
											fdc="zoomIn">
											<i class="fa fa-search-plus"></i>放大
										</button>
										<button type="button" class="btn btn-sm btn-default"
											fdc="zoomOut">
											<i class="fa fa-search-minus"></i>缩小
										</button>
										<button type="button" class="btn btn-sm btn-default"
											fdc="fitWidth">
											<i class="fa fa-arrows-h"></i>适合宽度
										</button>
										<button type="button" class="btn btn-sm btn-default"
											fdc="fitHeight">
											<i class="fa fa-arrows-v"></i>适合高度
										</button>
										<button type="button" class="btn btn-sm btn-default"
											fdc="fitPage">
											<i class="fa fa-arrows"></i>适合页面
										</button>
										<button type="button" class="btn btn-sm btn-default"
											fdc="actualSize">
											<i class="fa fa-refresh"></i>原始大小
										</button>
										<a class="divider"></a>
										<button type="button" class="btn btn-sm btn-default"
											fdc="autoLayout">
											<i class="fa fa-share-alt"></i>自动布局
										</button>
									</div>
								</div>
								<div class="portlet-body">
									<div class="flowdesigner"></div>
								</div>
							</div>
						</div>
					</div>
				</s:row>
			</s:form>
		</s:tabpanel>
	</s:row>
</s:page>
<script type="text/javascript">
var flowdesigner = null;
$(document).ready(function() {
	$(".flowdesigner").closest(".form-horizontal").removeClass("form-horizontal");
	flowdesigner = $(".flowdesigner").flowdesigner();
	$(window).resize(function() {
		$("#_uicontent_").height(document.body.clientHeight - $("#_uicontent_").offset().top - 120);
		$(".flowdesigner").height(document.body.clientHeight - $("#_uicontent_").offset().top - 270);
		//$(".flowdesigner").height(document.body.clientHeight - $("#_uicontent_").offset().top - 90);
		$("#tool_box").height(document.body.clientHeight - $("#_uicontent_").offset().top);
		$("#tool_box").css("overflow","auto");
		$("#tool_box2").height(document.body.clientHeight - $("#_uicontent_").offset().top - 420);
		$("#tool_box2").css("overflow","auto");
	});
	$(window).resize();
	$("#accordion3").find(".list-group-item").attr("draggable", "true");
	$("#accordion3").find(".list-group-item").on("dragstart", function() {
		$(this).attr("selected", "selected");
	});
	$(".flowdesigner").on("dragover", function(ev) {
		ev.preventDefault();
	});
	$(".flowdesigner").on("drop", function(ev) {
		var flow_node_type = $("#accordion3").find(".list-group-item[selected]").attr("fnt");
		var x = ev.originalEvent.offsetX;
		var y = ev.originalEvent.offsetY;

		$("#accordion3").find(".list-group-item").removeAttr("selected");
		flowdesigner.createNode(flow_node_type, x, y);
	});
	
	if ('${param.schemeid}') {
		designer.ccload('${param.schemeid}', '${param.type}',
				'${param.flag}','${param.system_product_code}');
		var fid = "DFL_" + '${param.flag}' + "_" + '${param.schemeid}';
		flowdesigner.load('${param.system_product_code}', fid);
	}else{
		setpage();
		flowdesigner.newFlow();
	}
} );
function setpage() {
	var d = designer.page.getData().attrs;
	d.runtitle = d.title;
	$.modal("setting_page.html", "汇总设置", $.extend(true, {
		callback : function(rtn) {
			if (rtn) {
				$.extend(true, designer.page.attrs, rtn);
				designer.page.attrs.type = "document";
				designer.page.attr_p = [];
				var tname = "dfl_"+d.flag+"_"+d.schemeid;
				designer.page.attr_p.push({"lx":"H", "tbname":tname});
				flowdesigner.flow.id = "DFL_" + designer.page.attrs.flag + "_" + designer.page.attrs.schemeid;
				flowdesigner.flow.name = designer.page.attrs.description;
				flowdesigner.flow.system_product_code = designer.page.attrs.system_product_code;
			}
		}
	}, d));
}

function save(){
	var d = designer.page.getData().attrs;
	d.attr_p = [];
	var tname = "dfl_"+d.flag+"_"+d.schemeid;
	d.attr_p.push({"lx":"H", "tbname":tname});
	if(!d.schemeid){
		$.message("单据类型不能为空，请在‘单据汇总设置’中填写单据类型！");
		return ;
	}
	if(!d.flag){
		$.message("单据标识不能为空，请在‘单据汇总设置’中填写单据标识！");
		return ;
	}
	var r = designer.ccsave();
	if(r == "success"){
		flowdesigner.save();
		if ('${param.schemeid}') {
			//$.message("保存成功！");
		}else{
			location.href = "list.html?_pid_=${param._pid_}";
		}
	}
}
function deploy(){
	var d = designer.page.getData();
	d.attr_p = [];
	var tname = "dfl_"+d.attrs.flag+"_"+d.attrs.schemeid;
	d.attr_p.push({"lx":"H", "tbname":tname});
	$.call("crdc.designer.queryHZtable", {tname: tname, system_product_code: d.attrs.system_product_code}, function(r) {
		if(r.c>0){
			$.confirm("该工作表已经存在，覆盖工作表？",function(rtn){
				if(rtn){
					d.hastable = true;
					console.log(flowdesigner);
					$.call("crdc.docflow.deploy", {jdata: $.toJSON(d),"wfjson": $.toJSON(flowdesigner.flow.getData())}, function(data) {
						$.message("ok!");
					});
				}
			})
		}else{
			$.call("crdc.docflow.deploy", {jdata: $.toJSON(d),"wfjson": $.toJSON(flowdesigner.flow.getData())}, function(data) {
				$.message("ok!");
			});
		}
	});
}
</script>
