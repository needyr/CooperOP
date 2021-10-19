<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/theme/plugins/jquery.json.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/res/crdc/scripts/designer.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/res/crdc/scripts/jasper.js"></script>
<script
		src="${pageContext.request.contextPath}/theme/pages/scripts/components-context-menu.js"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/theme/plugins/bootstrap-contextmenu/bootstrap-contextmenu.js"></script>
	<script
		src="${pageContext.request.contextPath}/theme/scripts/controls/controls.js"
		type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/res/crdc/css/designer.css">
<div id="context-menu">
	<ul class="dropdown-menu" role="menu">
		<li><a href="javascript:void(0);" atype="report"> <i class="icon-settings"></i>
				总体设置
		</a></li>
		<li><a href="javascript:void(0);" atype="expression"> <i class="icon-settings"></i>
				赋值
		</a></li>
		<li><a href="javascript:void(0);" atype="pattern"> <i class="icon-settings"></i>
				格式设置
		</a></li>
		<li><a href="javascript:void(0);" atype="padding"> <i class="icon-settings"></i>
				边框样式设置
		</a></li>
		<li><a href="javascript:void(0);" atype="delete"> <i class="icon-trash"></i>
				删除
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
				<div class="portlet-body">
					<div class="panel-group accordion scrollable" id="accordion2">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a class="accordion-toggle collapsed" data-toggle="collapse"
										data-parent="#accordion2" href="#collapse_2_1"
										aria-expanded="false"> <i class="fa fa-th"></i>布局组件
									</a>
								</h4>
							</div>
							<div id="collapse_2_1" class="panel-collapse collapse"
								aria-expanded="false" style="height: 0px;">
								<div class="panel-body">
									<ul class="list-group" stype="row">
										<li class="list-group-item" stype="title"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i
											class="fa fa-credit-card"></i>title</li>
										<li class="list-group-item" stype="pageHeader"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i
											class="fa fa-columns"></i>page header</li>
										<li class="list-group-item" stype="columnHeader"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i
											class="fa fa-list-alt"></i>column header</li>
										<li class="list-group-item" stype="detail"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i
											class="fa fa-tasks"></i>detail</li>
										<li class="list-group-item" stype="columnFooter"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i
											class="fa fa-table"></i>column footer</li>
										<li class="list-group-item" stype="pageFooter"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i class="fa fa-list"></i>page
											footer</li>
										<li class="list-group-item" stype="summary"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i class="fa fa-list"></i>summary
										</li>
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
									<ul class="list-group" stype="text">
										<li class="list-group-item" stype="staticText"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>Static
											text</li>
										<li class="list-group-item" stype="textField"
											ondragover="allowDrop(event)" draggable="true"
											ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>文本框
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
						<i class="fa fa-edit"></i>单据设计器
					</div>
					<div class="tools">
						<button type="button" class="btn btn-sm btn-default"
							onclick="savejasper()">
							<i class="fa fa-save"></i> 保存
						</button>
					</div>
				</div>
				<div>
					<div class="portlet-body" id="_uicontent_" jtype="page"
						ondragover="allowDrop(event)" ondrop="drop(event,this)"
						style="min-height: 400px;"></div>
				</div>
			</div>
		</div>
	</div>
</s:page>
<<script type="text/javascript">
$(document).ready(function (){
	if('${pageParam.id}'){
		$.call("crdc.designer.designer.loadjasper", {id : '${pageParam.id}'}, function(rtn) {
			var $html = $(rtn.html);
			$("#_uicontent_").append($html);
		});
	}
	
	$('div[jtype]').contextmenu({
		  target:'#context-menu',
		  before: function(e,context) {
			  var jtype = $(context).attr("jtype");
			  $("#context-menu").find("[atype=report]").parent().hide();
			  if($(context).attr("class") == "row"){
				  $("#context-menu").find("[atype=expression]").parent().hide();
				  $("#context-menu").find("[atype=pattern]").parent().hide();
				  $("#context-menu").find("[atype=padding]").parent().show();
			  }else if(jtype == "page"){
				  $("#context-menu").find("[atype=report]").parent().show();
				  $("#context-menu").find("[atype=expression]").parent().hide();
				  $("#context-menu").find("[atype=pattern]").parent().hide();
				  $("#context-menu").find("[atype=padding]").parent().hide();
			  }else if(jtype == "staticText"){
				  $("#context-menu").find("[atype=expression]").parent().show();
				  $("#context-menu").find("[atype=padding]").parent().show();
				  $("#context-menu").find("[atype=pattern]").parent().hide();
			  }else if(jtype == "textField"){
				  $("#context-menu").find("[atype=expression]").parent().show();
				  $("#context-menu").find("[atype=pattern]").parent().show();
				  $("#context-menu").find("[atype=padding]").parent().show();
			  }
			  return true;
		  },
		  onItem: function(context,e) {
			  var atype = $(e.toElement).attr("atype");
			  var jtype = $(context).attr("jtype");
			  if(atype == "report"){
				  $.modal("setting.html", "report", {
					  height:"400px",
					  width:"800px",
					  name: context.attr("name"),
					  pageHeight: context.css("height").split("px")[0],
					  pageWidth: context.css("width").split("px")[0],
					  topMargin: context.css("margin-top").split("px")[0],
					  rightMargin: context.css("margin-right").split("px")[0],
					  bottomMargin: context.css("margin-bottom").split("px")[0],
					  leftMargin: context.css("margin-left").split("px")[0],
					  callback: function(rtn) {
						if(rtn){
							$(context).attr("name",rtn.name);
							$(context).css("height",rtn.pageHeight);
							$(context).css("width",rtn.pageWidth);
							$(context).css("margin-top",rtn.topMargin);
							$(context).css("margin-right",rtn.rightMargin);
							$(context).css("margin-bottom",rtn.bottomMargin);
							$(context).css("margin-left",rtn.leftMargin);
						}
					  }
				  });
			  }else if(atype == "expression"){
				  $.modal("settingtext.html", "expression", {
					  textval : $(context).html(),
					  jtype:jtype,
					  fieldtype:$(context).attr("fieldtype"),
					  classtype:$(context).attr("classtype"),
					  callback: function(rtn) {
						if(rtn){
							$(context).html(rtn.textval);
							$(context).attr("fieldtype",rtn.fieldtype);
							$(context).attr("classtype",rtn.classtype);
						}
					  }
				  });
			  }else if(atype == "pattern"){
				  
			  }else if(atype == "padding"){
				  $.modal("settingborder.html", "padding", {
					  height:"400px",
					  width:"800px",
					  padding_top: context.css("padding-top").split("px")[0],
					  padding_bottom: context.css("padding-bottom").split("px")[0],
					  padding_left: context.css("padding-left").split("px")[0],
					  padding_right: context.css("padding-right").split("px")[0],
					  border_top: context.css("border-top").split("px")[0],
					  border_bottom: context.css("border-bottom").split("px")[0],
					  border_left: context.css("border-left").split("px")[0],
					  border_right: context.css("border-right").split("px")[0],
					  height_: context.css("height").split("px")[0],
					  width_: context.css("width").split("px")[0],
					  callback: function(rtn) {
						if(rtn){
							$(context).css("padding-top",rtn.padding_top);
							$(context).css("padding-bottom",rtn.padding_bottom);
							$(context).css("padding-left",rtn.padding_left);
							$(context).css("padding-right",rtn.padding_right);
							$(context).css("border-top",rtn.border_top);
							$(context).css("border-bottom",rtn.border_bottom);
							$(context).css("border-left",rtn.border_left);
							$(context).css("border-right",rtn.border_right);
							$(context).css("height",rtn.height_);
							$(context).css("width",rtn.width_);
						}
					  }
				  });
			  }else if(atype == "delete"){
				  $(context).remove();
			  }
		  }
		});
});

</script>