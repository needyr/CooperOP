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
	<link rel="stylesheet" href="/theme/plugins/jquery-file-upload/css/jquery.fileupload.css">
<link rel="stylesheet" href="/theme/plugins/jquery-file-upload/css/jquery.fileupload-ui.css">
<script src="/theme/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js" type="text/javascript"></script>
<script src="/theme/plugins/jquery-file-upload/js/vendor/tmpl.min.js" type="text/javascript"></script>
<script src="/theme/plugins/jquery-file-upload/js/vendor/load-image.min.js" type="text/javascript"></script>
<script src="/theme/plugins/jquery-file-upload/js/vendor/canvas-to-blob.min.js" type="text/javascript"></script>
<script src="/theme/plugins/jquery-file-upload/blueimp-gallery/jquery.blueimp-gallery.min.js" type="text/javascript"></script>
<script src="/theme/plugins/jquery-file-upload/js/jquery.iframe-transport.js" type="text/javascript"></script>
<script src="/theme/plugins/jquery-file-upload/js/jquery.fileupload.js" type="text/javascript"></script>
<script src="/theme/plugins/jquery-file-upload/js/jquery.fileupload-process.js" type="text/javascript"></script>
<script src="/theme/plugins/jquery-file-upload/js/jquery.fileupload-image.js" type="text/javascript"></script>
<script src="/theme/plugins/jquery-file-upload/js/jquery.fileupload-audio.js" type="text/javascript"></script>
<script src="/theme/plugins/jquery-file-upload/js/jquery.fileupload-video.js" type="text/javascript"></script>
<script src="/theme/plugins/jquery-file-upload/js/jquery.fileupload-validate.js" type="text/javascript"></script>
<script src="/theme/plugins/jquery-file-upload/js/jquery.fileupload-ui.js" type="text/javascript"></script>
	
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
		
		
	<script src="/theme/plugins/jquery-file-upload/js/jquery.fileupload-image.js" type="text/javascript">
	</script>
	
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
		src="${pageContext.request.contextPath}/res/crdc/scripts/drag.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/theme/plugins/jquery.json.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/res/crdc/scripts/designer.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/res/crdc/scripts/drag.js"></script>
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/res/crdc/css/designer.css">
		
	<div id="context-menu">
		<ul class="dropdown-menu" role="menu">
			<li><a href="javascript:void(0);" atype="setting"> <i
					class="icon-settings"></i> ?????????????????????
			</a></li>
			<li><a href="javascript:void(0);" atype="delete"> <i
					class="icon-trash"></i> ??????
			</a></li>
		</ul>
	</div>
	<div class="row">
		<div style="padding-left: 240px;">
		<div class="colmd2" style="position: fixed; width: 200px; top: 20px; left: 20px;">
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-cubes"></i>?????????
					</div>
				</div>
				<div class="portlet-body" id="tool_box">
					<div class="panel-group accordion scrollable" id="accordion2">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a class="accordion-toggle collapsed" data-toggle="collapse"
										data-parent="#accordion2" href="#collapse_2_1"
										aria-expanded="false"> <i class="fa fa-th"></i>????????????
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
										class="fa fa-credit-card"></i>?????????</li> -->
										<li class="list-group-item" stype="row"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i
											class="fa fa-tasks"></i>???</li>
										<li class="list-group-item" stype="row_hidden"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i
											class="fa fa-tasks"></i>?????????</li>
										<li class="list-group-item" stype="form"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i
											class="fa fa-list-alt"></i>??????</li>
										<li class="list-group-item" stype="table"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i
											class="fa fa-table"></i>??????</li>
										<li class="list-group-item" stype="tabpanel"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i
											class="fa fa-columns"></i>?????????</li>
										<!-- <li class="list-group-item" stype="list"
										ondragover="allowDrop(event)" draggable="true"
										ondragstart="dragadd(event, this)"><i class="fa fa-list"></i>??????
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
										aria-expanded="false"> <i class="fa fa-edit"></i>????????????
									</a>
								</h4>
							</div>
							<div id="collapse_2_2" class="panel-collapse collapse"
								aria-expanded="false" style="height: 0px;">
								<div class="panel-body">
									<ul class="list-group" stype="control">
										<li class="list-group-item" stype="textfield"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>?????????
										</li>
										<li class="list-group-item" stype="datefield"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>????????????
										</li>
										<li class="list-group-item" stype="timefield"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>????????????
										</li>
										<li class="list-group-item" stype="autocomplete"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>????????????
										</li>
										<li class="list-group-item" stype="select"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>????????????
										</li>
										<li class="list-group-item" stype="radio"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>??????
										</li>
										<li class="list-group-item" stype="checkbox"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>??????
										</li>
										<li class="list-group-item" stype="switch"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>????????????
										</li>
										<li class="list-group-item" stype="textarea"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>????????????
										</li>
										<li class="list-group-item" stype="richeditor"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>??????????????????
										</li>
										<li class="list-group-item" stype="image"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>??????
										</li>
										<li class="list-group-item" stype="file"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>??????
										</li>
										<li class="list-group-item" stype="button"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>??????
										</li>
										<li class="list-group-item" stype="taskhistory"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>??????????????????
										</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="colmd10">
			<div class="portlet box blue"  style="border:1px solid #67809F !important">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>???????????????
					</div>
					<div class="tools">
						<button type="button" class="btn btn-sm btn-default"
							onclick="istools(this);">
							<i class="fa fa-credit-card"></i> ?????????
						</button>
						<button type="button" class="btn btn-sm btn-default"
							onclick="save();">
							<i class="fa fa-save"></i> ??????
						</button>
						<button type="button" class="btn btn-sm btn-default"
							onclick="reset1();">
							<i class="fa fa-reply"></i> ????????????
						</button>
						<button type="button" class="btn btn-sm btn-default"
							onclick="back2list();">
							<i class="fa fa-backward"></i> ??????
						</button>
						<button type="button" class="btn btn-sm btn-default"
							onclick="createTable();">
							<i class="fa fa-chain"></i> ???????????????
						</button>
						<button type="button" class="btn btn-sm btn-default"
							onclick="setpage();">
							<i class="fa fa-cogs"></i> ??????????????????
						</button>
						<div class="btn-group pull-right">
							<button type="button"
								class="btn btn-sm btn-default dropdown-toggle"
								data-toggle="dropdown" data-hover="dropdown" data-delay="1000"
								data-close-others="true" aria-expanded="false">
								?????? <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right" role="menu">
								<!-- <li><a href="javascript:setimpl();">????????????</a></li> -->
								<li><a href="javascript:setap();">??????????????????</a></li>
								<li class="divider"></li>
								<li><a href="javascript:setgj();">????????????</a></li>
								<li><a href="javascript:setgj_jg();">??????????????????</a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="portlet-body" id="_uicontent_"
					ondragover="allowDrop(event)" ondrop="dropPage(this)"
					style="overflow: hidden; overflow-y: auto; padding-bottom: 50px;"></div>
			</div>
		</div>
		</div>
	</div>
</s:page>
<script type="text/javascript">
	$(document).ready(
			function() {
				if ('${param.schemeid}') {
					designer.ccload('${param.schemeid}', '${param.type}',
							'${param.flag}','${param.system_product_code}');
				}else{
					setpage();
				}
				
				$(window).resize(
				function() {
					$("#_uicontent_").height(
							document.body.clientHeight
									- $("#_uicontent_").offset().top
									- 130);
					$("#tool_box").height(document.body.clientHeight- 130);
					$("#tool_box").css("overflow","auto");
				});
				$(window).resize(); 
			});
	function setpage() {
		var d = designer.page.getData().attrs;
		d.runtitle = d.title;
		$.modal("setting_page.html", "????????????", $.extend(true, {
			callback : function(rtn) {
				if (rtn) {
					$.extend(true, designer.page.attrs, rtn);
					designer.page.attrs.type = "bill";
				}
			}
		}, d));
	}
	function setgj() {
		var d = {};
		if(designer.page.attr_gj){
			d.attr_gj = designer.page.attr_gj;
		}
		$.modal("setting_gj.html", "????????????",{
			gj:$.toJSON(d),
			callback : function(rtn) {
				if (rtn) {
					designer.page.attr_gj = rtn.attr_gj;
				}
			}
		});
	}
	function setap() {
		var d = {};
		if(designer.page.attr_p){
			d.attr_p = designer.page.attr_p;
		}
		$.modal("setting_page_save.html", "??????????????????",{
			gj:$.toJSON(d),
			callback : function(rtn) {
				if (rtn) {
					designer.page.attr_p = rtn.attr_p;
				}
			}
		});
	}
	function setgj_jg() {
		var d = {};
		if(designer.page.attr_gj){
			d.attr_gj = designer.page.attr_gj;
		}
		if(designer.page.attr_gj_jg){
			d.attr_gj_jg = designer.page.attr_gj_jg;
		}
		$.modal("setting_gj_jg.html", "????????????",{
			gj:$.toJSON(d),
			callback : function(rtn) {
				if (rtn) {
					designer.page.attr_gj_jg = rtn.attr_gj_jg;
				}
			}
		});
	}
	function save() {
		var d = designer.page.getData().attrs;
		if(!d.schemeid){
			$.message("?????????????????????????????????????????????????????????????????????????????????");
			return ;
		}
		if(!d.flag){
			$.message("?????????????????????????????????????????????????????????????????????????????????");
			return ;
		}
		var r = designer.ccsave();
		if(r == "success"){
			if ('${param.schemeid}') {
				$.message("?????????????????????");
			}else{
				//location.href = "list.html?_pid_=${param._pid_}";
				var wid = top.$(".page-content-tabs").find(".nav-pills").find("li.active").attr("wid");
				$.closeTabpage();
			}
		}
	}
	function reset1(){
		var d = designer.page.getData().attrs;
		if(!d.schemeid){
			$.message("?????????????????????????????????????????????????????????????????????????????????");
			return ;
		}
		if(!d.flag){
			$.message("?????????????????????????????????????????????????????????????????????????????????");
			return ;
		}
		$.modal("../list_view_hist.html", "????????????",{
				flag: d.flag,
				type: "bill",
				id: d.schemeid,
				system_product_code: d.system_product_code,
				callback : function(rtn) {
					if (rtn) {
						location.reload();
					}
				}
		});
	}
	function back2list(){
		$.confirm("???????????????????????????????????????", function(r) {
			if (r) {
				//location.href = "list.html?_pid_=${param._pid_}";
				var wid = top.$(".page-content-tabs").find(".nav-pills").find("li.active").attr("wid");
				$.closeTabpage();
			}
		});
	}
	function istools(_this){
		var $th = $(_this);
		var flag = true;
		$(designer.page.contents).each(function(index, obj) {
			if(obj.type == "toolbar"){
				obj.parent.contents.splice(index,1);
				obj.element.remove();
				flag = false;
				$th.css("color","");
			}
		});
		if(flag){
			designer.page.append("toolbar",{ondrop:"dropPageToolbar(this)",
				ondragover:"allowDrop(event)",crid:new Date().getTime()},"tool2page")
			$th.css("color","#B9B9B9");
			if(designer.page.contents.length>1){
				designer.page.contents.unshift(designer.page.contents.pop());
			}
		}
	}
	function createTable(){
		var d = designer.page.getData();
		var p = designer.page.attr_p
		var tname;
		for(var i =0;i<p.length;i++){
			if(p[i].lx=="H"){
				tname = p[i].tbname;
			}
		}
		if(!tname){
			$.message("??????????????????????????????????????????");
			return;
		}
		$.call("crdc.designer.queryHZtable", {tname: tname, system_product_code: d.attrs.system_product_code}, function(r) {
			if(r.c>0){
				$.confirm("?????????????????????????????????????????????",function(rtn){
					if(rtn){
						d.hastable = true;
						$.call("crdc.designer.createHZtable", {jdata: $.toJSON(d)}, function(data) {
							$.message("ok!");
						});
					}
				})
			}else{
				$.call("crdc.designer.createHZtable", {jdata: $.toJSON(d)}, function(data) {
					$.message("ok!");
				});
			}
		});
	}
</script>
