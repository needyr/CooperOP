<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/header.jsp"></c:import>
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
		<li><a href="javascript:void(0);" atype="setting"> <i class="icon-settings"></i>
				单据域属性编辑
		</a></li>
		<li><a href="javascript:void(0);" atype="delete"> <i class="icon-trash"></i>
				删除
		</a></li>
		<!-- <li><a href="javascript:;"> <i class="icon-present"></i> New
				Event <span class="badge badge-success">4</span>
		</a></li>
		<li><a href="javascript:;"> <i class="icon-basket"></i> New
				order
		</a></li>
		<li class="divider"></li>
		<li><a href="javascript:;"> <i class="icon-flag"></i> Pending
				Orders <span class="badge badge-danger">4</span>
		</a></li>
		<li><a href="javascript:;"> <i class="icon-users"></i>
				Pending Users <span class="badge badge-warning">12</span>
		</a></li> -->
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
								<ul class="list-group" stype="layout">
									<li class="list-group-item" stype="toolbar"
										ondragover="allowDrop(event)" draggable="true"
										ondragstart="dragadd(event, this)"><i
										class="fa fa-credit-card"></i>工具条</li>
									<li class="list-group-item" stype="tabpanel"
										ondragover="allowDrop(event)" draggable="true"
										ondragstart="dragadd(event, this)"><i
										class="fa fa-columns"></i>多页签</li>
									<li class="list-group-item" stype="form"
										ondragover="allowDrop(event)" draggable="true"
										ondragstart="dragadd(event, this)"><i
										class="fa fa-list-alt"></i>表单</li>
									<li class="list-group-item" stype="row"
										ondragover="allowDrop(event)" draggable="true"
										ondragstart="dragadd(event, this)"><i class="fa fa-tasks"></i>行
									</li>
									<li class="list-group-item" stype="table"
										ondragover="allowDrop(event)" draggable="true"
										ondragstart="dragadd(event, this)"><i class="fa fa-table"></i>表格
									</li>
									<li class="list-group-item" stype="list"
										ondragover="allowDrop(event)" draggable="true"
										ondragstart="dragadd(event, this)"><i class="fa fa-list"></i>列表
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
								<ul class="list-group" stype="control">
									<li class="list-group-item" stype="textfield"
										ondragover="allowDrop(event)" draggable="true"
										ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>文本框
									</li>
									<li class="list-group-item" stype="datefield"
										ondragover="allowDrop(event)" draggable="true"
										ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>日期时间
									</li>
									<li class="list-group-item" stype="switchfield"
										ondragover="allowDrop(event)" draggable="true"
										ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>逻辑开关
									</li>
									<li class="list-group-item" stype="radio"
										ondragover="allowDrop(event)" draggable="true"
										ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>单选
									</li>
									<li class="list-group-item" stype="checkbox"
										ondragover="allowDrop(event)" draggable="true"
										ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>复选
									</li>
									<li class="list-group-item" stype="select"
										ondragover="allowDrop(event)" draggable="true"
										ondragstart="dragadd(event, this)"><i class="fa fa-edit"></i>下拉选择
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
					<i class="fa fa-edit"></i>单据设计器
				</div>
				<div class="tools">
					<button type="button" class="btn btn-sm btn-default">
						<i class="fa fa-user"></i> Profile
					</button>
					<button type="button" class="btn btn-sm btn-default">
						<i class="fa fa-cogs"></i> Settings
					</button>
					<button type="button" class="btn btn-sm btn-default">
						<i class="fa fa-bullhorn"></i> Feeds
					</button>
					<div class="btn-group pull-right">
						<button type="button"
							class="btn btn-sm btn-default dropdown-toggle"
							data-toggle="dropdown" data-hover="dropdown" data-delay="1000"
							data-close-others="true" aria-expanded="false">
							更多 <i class="fa fa-angle-down"></i>
						</button>
						<ul class="dropdown-menu pull-right" role="menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li><a href="#">Something else here</a></li>
							<li class="divider"></li>
							<li><a href="#">Separated link</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="portlet-body" id="_uicontent_"
				ondragover="allowDrop(event)" ondrop="dropPage(this)"
				style="min-height: 400px;"></div>
		</div>
	</div>
</div>
<c:import url="/footer.jsp"></c:import>
<script type="text/javascript">
	
</script>
