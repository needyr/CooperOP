<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/header.jsp"></c:import>
<div ctype="toolbar" class="navbar navbar-default" role="navigation">
	<!-- Collect the nav links, forms, and other content for toggling -->
	<div class="collapse navbar-collapse navbar-ex1-collapse">
		<ul class="nav navbar-nav">
			<li><a href="javascript:;" action="x41"> 保存 </a></li>
			<li><a href="javascript:;" action="x41"> 提交 </a></li>
			<li class="dropdown"><a href="javascript:;"
				class="dropdown-toggle" data-toggle="dropdown"> 更多 <i
					class="fa fa-angle-down"></i>
			</a>
				<ul class="dropdown-menu">
					<li><a href="javascript:;" action="x41"> Action </a></li>
					<li><a href="javascript:;" action="x41"> Another action </a></li>
					<li><a href="javascript:;" action="x41"> Something else
							here </a></li>
					<li><a href="javascript:;" action="x41"> Separated link </a></li>
					<li><a href="javascript:;" action="x41"> One more
							separated link </a></li>
				</ul></li>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<li><a href="javascript:;" action="x41"> Link </a></li>
			<li class="dropdown"><a href="javascript:;"
				class="dropdown-toggle" data-toggle="dropdown"> Dropdown <i
					class="fa fa-angle-down"></i>
			</a>
				<ul class="dropdown-menu pull-right">
					<li><a href="javascript:;" action="x41"> Action </a></li>
					<li><a href="javascript:;" action="x41"> Another action </a></li>
					<li><a href="javascript:;" action="x41"> Something else
							here </a></li>
					<li><a href="javascript:;" action="x41"> Separated link </a></li>
				</ul></li>
		</ul>
	</div>
	<!-- /.navbar-collapse -->
</div>
<div class="row">
<div class="col-md-6">
					<div class="portlet blue-hoki box">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-cogs"></i>Default Tree
							</div>
							<div class="tools">
								<a href="javascript:;" class="collapse">
								</a>
								<a href="#portlet-config" data-toggle="modal" class="config">
								</a>
								<a href="javascript:;" class="reload">
								</a>
								<a href="javascript:;" class="remove">
								</a>
							</div>
						</div>
						<div class="portlet-body">
							<div id="tree_1" class="tree-demo">
								<ul>
									<li>
										 Root node 1
										<ul>
											<li data-jstree='{ "selected" : true }'>
												<a href="javascript:;">
												Initially selected </a>
											</li>
											<li data-jstree='{ "icon" : "fa fa-briefcase icon-state-success " }'>
												 custom icon URL
											</li>
											<li data-jstree='{ "opened" : true }'>
												 initially open
												<ul>
													<li data-jstree='{ "disabled" : true }'>
														 Disabled Node
													</li>
													<li data-jstree='{ "type" : "file" }'>
														 Another node
													</li>
												</ul>
											</li>
											<li data-jstree='{ "icon" : "fa fa-warning icon-state-danger" }'>
												 Custom icon class (bootstrap)
											</li>
										</ul>
									</li>
									<li data-jstree='{ "type" : "file" }'>
										<a href="http://www.jstree.com" target="_blank">
										Clickanle link node </a>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
	<div class="col-md-12">
		<div class="portlet box grey-silver">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-gift"></i>Form Sample
				</div>
				<div ctype="toolbar" class="tools">
					<button type="button" class="btn btn-sm btn-default" action="x41">
						<i class="fa fa-user"></i> Profile
					</button>
					<button type="button" class="btn btn-sm btn-default" action="x41">
						<i class="fa fa-cogs"></i> Settings
					</button>
					<button type="button" class="btn btn-sm btn-default" action="x41">
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
							<li><a href="javascript:;" action="x41">Action</a></li>
							<li><a href="javascript:;" action="x41">Another action</a></li>
							<li><a href="javascript:;" action="x41">Something else
									here</a></li>
							<li class="divider"></li>
							<li><a href="javascript:;" action="x41">Separated link</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="portlet-body">
				<div ctype="form" class="form-horizontal">
					<div class="row-fluid">
						<div class="cols">
							<label class="control-label">文本框</label>
							<div class="control-content">
								<input ctype="textfield" type="text" class="form-control"
									placeholder="Chee Kin" required="required">
							</div>
						</div>
						<div class="cols">
							<label class="control-label">静态框框</label>
							<div class="control-content">
								<p class="form-control-static">静态框框静态框框静态框框</p>
							</div>
						</div>
						<div class="cols">
							<label class="control-label">密码框</label>
							<div class="control-content">
								<input ctype="password" type="password" class="form-control"
									placeholder="Chee Kin" required="required">
							</div>
						</div>
						<div class="cols">
							<label class="control-label">下拉选择</label>
							<div class="control-content">
								<!-- select2me -->
								<select ctype="select" class="form-control "
									placeholder="Chee Kin" value="2" required="required"
									readonly="readonly">
									<option value="">请选择...</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
								</select>
							</div>
						</div>
						<div class="cols">
							<label class="control-label">逻辑开关</label>
							<div class="control-content">
								<input ctype="switch" type="checkbox" checked
									ontext="&nbsp;是&nbsp;" offtext="&nbsp;否&nbsp;"
									oncolor="primary" offcolor="default">
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="cols2">
							<label class="control-label">文本框</label>
							<div class="control-content">
								<input ctype="textfield" type="text" class="form-control"
									placeholder="Chee Kin" value="只读文本框" readonly="readonly">
							</div>
						</div>
						<div class="cols">
							<label class="control-label">日期</label>
							<div class="control-content has-icon">
								<i class="control-icon fa fa-calendar"></i> <input
									ctype="datefield" class="form-control date-picker" type="text"
									value="" min="-1w" max="+10d" required="required" />
							</div>
						</div>
						<div class="cols">
							<label class="control-label">时间</label>
							<div class="control-content has-icon">
								<i class="control-icon fa fa-clock-o"></i> <input
									ctype="timefield" class="form-control time-picker" type=""
									text"" value="" required="required" />
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="cols4">
							<label class="control-label">文本区域</label>
							<div class="control-content">
								<textarea ctype="textarea" class="form-control autosizeme"
									placeholder="Chee Kin" rows="1" required="required"></textarea>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="cols2">
							<label class="control-label">单选</label>
							<div class="control-content">
								<div ctype="radio" class="radio-list" required="required">
									<label class="radio-inline"> <input type="radio"
										name="optionsRadios" value="option1" required="required">Radio
										1
									</label> <label class="radio-inline"> <input type="radio"
										name="optionsRadios" value="option2" required="required">Radio
										2
									</label> <label class="radio-inline"> <input type="radio"
										name="optionsRadios" value="option3" required="required"
										disabled>Disabled
									</label>
								</div>
							</div>
						</div>
						<div class="cols2">
							<label class="control-label">复选</label>
							<div class="control-content">
								<div ctype="checkbox" class="checkbox-list" required="required">
									<label class="checkbox-inline"> <input type="checkbox"
										name="optionsCheckboxs" value="option1" required="required">Checkbox
										1
									</label> <label class="checkbox-inline"> <input type="checkbox"
										name="optionsCheckboxs" value="option2" required="required">Checkbox
										2
									</label> <label class="checkbox-inline"> <input type="checkbox"
										name="optionsCheckboxs" value="option3" required="required"
										disabled>Disabled
									</label>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="cols">
							<label class="control-label">双击事件</label>
							<div class="control-content dblclick-open">
								<button type="button" class="open-icon">
									<i class="fa fa-ellipsis-h"></i>
								</button>
								<input ctype="textfield" dbl_action="X41" type="text"
									class="form-control" placeholder="Chee Kin">
							</div>
						</div>
						<div class="cols2">
							<label class="control-label">双击事件(图标)</label>
							<div class="control-content dblclick-open has-icon">
								<i class="control-icon fa fa-weixin"></i>
								<button type="button" class="open-icon">
									<i class="fa fa-ellipsis-h"></i>
								</button>
								<input ctype="textfield" dbl_action="X41" type="text"
									class="form-control" placeholder="Chee Kin">
							</div>
						</div>
						<button ctype="button" action="x41" type="button"
							class="btn btn-sm red">
							<i class="fa fa-ellipsis-h"></i> 这是个表单按钮
						</button>
					</div>
					<div class="row-fluid">
						<div class="cols4">
							<label class="control-label">富文本编辑器</label>
							<div class="control-content">
								<div ctype="richeditor" name="rich" height="300">
									<h1 style="text-align: center;">啊十大发生的发</h1>
									<h2 style="text-align: left;">1.富文本编辑器</h2>
									<blockquote>
										爽了吧？ <img style="width: 30px;"
											src="data:image/gif;base64,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"
											data-filename="4RWZQC(@}7@7AUX%[}K)_5T.gif">
									</blockquote>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="cols2">
							<label class="control-label">图片组件</label>
							<div class="control-content">
								<div ctype="image" class="imageupload">
									<ul class="files">
									</ul>
									<div class="fileupload-buttonbar">
										<!-- The fileinput-button span is used to style the file input field as button -->
										<span class="fileinput-button" title="添加文件"> <i
											class="fa fa-plus"></i> <input type="file" name="files[]"
											multiple="" title="添加文件">
										</span>
										<!-- The global file processing state -->
										<span class="fileupload-process"> </span>
									</div>
								</div>
							</div>
						</div>
						<div class="cols2">
							<label class="control-label">文件组件</label>
							<div class="control-content">
								<div ctype="file" class="fileupload">
									<div class="fileupload-buttonbar">
										<!-- The fileinput-button span is used to style the file input field as button -->
										<span class="btn btn-sm green fileinput-button"> <i
											class="fa fa-plus"></i> <span>添加文件</span> <input type="file"
											name="files[]" multiple="" title="添加文件">
										</span>
										<button type="submit" class="btn btn-sm blue start">
											<i class="fa fa-upload"></i> <span> 开始上传</span>
										</button>
										<!-- The global file processing state -->
										<span class="fileupload-process"> </span>
									</div>
									<table role="presentation" class="table table-striped clearfix">
										<tbody class="files">
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<button ctype="button" action="x41" type="button"
							class="btn btn-sm btn-default" style="margin-left: 80px;">
							<i class="fa fa-user"></i> Profile
						</button>
						<button ctype="button" action="x41" type="button"
							class="btn btn-sm btn-success">
							<i class="fa fa-user"></i> Profile
						</button>
						<button ctype="button" action="x41" type="button"
							class="btn btn-sm btn-info">
							<i class="fa fa-user"></i> Profile
						</button>
						<button ctype="button" action="x41" type="button"
							class="btn btn-sm btn-warning">
							<i class="fa fa-user"></i> Profile
						</button>
						<button ctype="button" action="x41" type="button"
							class="btn btn-sm btn-danger">
							<i class="fa fa-user"></i> Profile
						</button>
						<button ctype="button" action="x41" type="button"
							class="btn btn-sm btn-primary">
							<i class="fa fa-user"></i> Profile
						</button>
						<button ctype="button" action="x41" type="button"
							class="btn btn-sm btn-link">
							<i class="fa fa-user"></i> Profile
						</button>
						<button ctype="button" action="x41" type="button"
							class="btn btn-sm blue">
							<i class="fa fa-user"></i> Profile
						</button>
						<button ctype="button" action="x41" type="button"
							class="btn btn-sm red-pink">
							<i class="fa fa-user"></i> Profile
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-md-12">
		<div class="portlet box grey-silver">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-gift"></i>Table Sample
				</div>
				<div ctype="toolbar" class="tools">
					<button type="button" class="btn btn-sm btn-default" action="x41">
						<i class="fa fa-sort"></i> Profile
					</button>
					<button type="button" class="btn btn-sm btn-default" action="x41">
						<i class="fa fa-sort-asc"></i> Settings
					</button>
					<button type="button" class="btn btn-sm btn-default" action="x41">
						<i class="fa fa-sort-desc"></i> Feeds
					</button>
					<div class="btn-group pull-right">
						<button type="button"
							class="btn btn-sm btn-default dropdown-toggle"
							data-toggle="dropdown" data-hover="dropdown" data-delay="1000"
							data-close-others="true" aria-expanded="false">
							更多 <i class="fa fa-angle-down"></i>
						</button>
						<ul class="dropdown-menu pull-right" role="menu">
							<li><a href="javascript:;" action="x41">Action</a></li>
							<li><a href="javascript:;" action="x41">Another action</a></li>
							<li><a href="javascript:;" action="x41">Something else
									here</a></li>
							<li class="divider"></li>
							<li><a href="javascript:;" action="x41">Separated link</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="portlet-body">
				<table ctype="table" theight="300" fitwidth="true"
					class="table table-striped table-bordered table-hover table-nowrap">
					<thead>
						<tr>
							<th scope="col">Column header 1</th>
							<th scope="col">Column header 2</th>
							<th scope="col">Column header 3</th>
							<th scope="col">Column header 4</th>
							<th scope="col">Column header 5</th>
							<th scope="col">Column header 6</th>
							<th scope="col">Column header 7</th>
							<th scope="col">Column header 8</th>
							<th scope="col">Column header 9</th>
							<th scope="col">Column header 10</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>我去我去阿里妈妈我去我去阿里妈妈我去我去阿里妈妈我去我去阿里妈妈我去我去阿里妈妈我去我去阿里妈妈</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
						</tr>
						<tr>
							<td>你去你去</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
						</tr>
						<tr>
							<td>阿里巴巴</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
						</tr>
						<tr>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
						</tr>
						<tr>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
						</tr>
						<tr>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
						</tr>
						<tr>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
						</tr>
						<tr>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
						</tr>
						<tr>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
						</tr>
						<tr>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
						</tr>
						<tr>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
						</tr>
						<tr>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
							<td>Table data</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-md-12">
		<div ctype="tabpanel" class="portlet box grey-silver tab-portlet">
			<div class="portlet-title">
				<ul class="nav nav-tabs">
					<li class="active"><a><i class="fa fa-gift"></i>Tab
							Samples 1</a></li>
					<li><a><i class="fa fa-gift"></i>Tab Samples 2</a></li>
					<li><a><i class="fa fa-gift"></i>Tab Samples 3</a></li>
				</ul>
				<div class="tab-tools">
					<div ctype="toolbar" class="tools active">
						<button type="button" class="btn btn-sm btn-default" action="x41">
							<i class="fa fa-user"></i> Profile1
						</button>
						<button type="button" class="btn btn-sm btn-default" action="x41">
							<i class="fa fa-cogs"></i> Settings1
						</button>
						<button type="button" class="btn btn-sm btn-default" action="x41">
							<i class="fa fa-bullhorn"></i> Feeds1
						</button>
						<div class="btn-group pull-right">
							<button type="button"
								class="btn btn-sm btn-default dropdown-toggle"
								data-toggle="dropdown" data-hover="dropdown" data-delay="1000"
								data-close-others="true" aria-expanded="false">
								更多1 <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right" role="menu">
								<li><a href="javascript:;" action="x41">Action1</a></li>
								<li><a href="javascript:;" action="x41">Another action1</a>
								</li>
								<li><a href="javascript:;" action="x41">Something else
										here1</a></li>
								<li class="divider"></li>
								<li><a href="javascript:;" action="x41">Separated link1</a>
								</li>
							</ul>
						</div>
					</div>
					<div ctype="toolbar" class="tools">
						<button type="button" class="btn btn-sm btn-default" action="x41">
							<i class="fa fa-user"></i> Profile2
						</button>
						<button type="button" class="btn btn-sm btn-default" action="x41">
							<i class="fa fa-cogs"></i> Settings2
						</button>
						<button type="button" class="btn btn-sm btn-default" action="x41">
							<i class="fa fa-bullhorn"></i> Feeds2
						</button>
						<div class="btn-group pull-right">
							<button type="button"
								class="btn btn-sm btn-default dropdown-toggle"
								data-toggle="dropdown" data-hover="dropdown" data-delay="1000"
								data-close-others="true" aria-expanded="false">
								更多2 <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right" role="menu">
								<li><a href="javascript:;" action="x41">Action2</a></li>
								<li><a href="javascript:;" action="x41">Another action2</a>
								</li>
								<li><a href="javascript:;" action="x41">Something else
										here2</a></li>
								<li class="divider"></li>
								<li><a href="javascript:;" action="x41">Separated link2</a>
								</li>
							</ul>
						</div>
					</div>
					<div ctype="toolbar" class="tools">
						<button type="button" class="btn btn-sm btn-default" action="x41">
							<i class="fa fa-user"></i> Profile3
						</button>
						<button type="button" class="btn btn-sm btn-default" action="x41">
							<i class="fa fa-cogs"></i> Settings3
						</button>
						<button type="button" class="btn btn-sm btn-default" action="x41">
							<i class="fa fa-bullhorn"></i> Feeds3
						</button>
						<div class="btn-group pull-right">
							<button type="button"
								class="btn btn-sm btn-default dropdown-toggle"
								data-toggle="dropdown" data-hover="dropdown" data-delay="1000"
								data-close-others="true" aria-expanded="false">
								更多3 <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right" role="menu">
								<li><a href="javascript:;" action="x41">Action3</a></li>
								<li><a href="javascript:;" action="x41">Another action3</a>
								</li>
								<li><a href="javascript:;" action="x41">Something else
										here3</a></li>
								<li class="divider"></li>
								<li><a href="javascript:;" action="x41">Separated link3</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="portlet-body">
				<div class="tab-content">
					<div class="tab-pane active">
						<div ctype="form" class="form-horizontal">
							<div class="row-fluid">
								<div class="cols">
									<label class="control-label">文本框</label>
									<div class="control-content">
										<input ctype="textfield" type="text" class="form-control"
											placeholder="Chee Kin" required="required">
									</div>
								</div>
								<div class="cols">
									<label class="control-label">密码框</label>
									<div class="control-content">
										<input ctype="password" type="password" class="form-control"
											placeholder="Chee Kin" required="required">
									</div>
								</div>
								<div class="cols">
									<label class="control-label">下拉选择</label>
									<div class="control-content">
										<!-- select2me -->
										<select ctype="select" class="form-control "
											placeholder="Chee Kin" value="2" required="required"
											readonly="readonly">
											<option value="">请选择...</option>
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
										</select>
									</div>
								</div>
								<div class="cols">
									<label class="control-label">逻辑开关</label>
									<div class="control-content">
										<input ctype="switch" type="checkbox" checked
											class="make-switch" data-size="small"
											data-on-text="&nbsp;是&nbsp;" data-off-text="&nbsp;否&nbsp;"
											data-on-color="primary" data-off-color="default">
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="cols2">
									<label class="control-label">文本框</label>
									<div class="control-content">
										<input ctype="textfield" type="text" class="form-control"
											placeholder="Chee Kin" value="只读文本框" readonly="readonly">
									</div>
								</div>
								<div class="cols">
									<label class="control-label">日期</label>
									<div class="control-content has-icon">
										<i class="control-icon fa fa-calendar"></i> <input
											ctype="datefield" class="form-control date-picker"
											type="text" value="" required="required" />
									</div>
								</div>
								<div class="cols">
									<label class="control-label">时间</label>
									<div class="control-content has-icon">
										<i class="control-icon fa fa-clock-o"></i> <input
											ctype="timefield" class="form-control time-picker" type=""
											text"" value="" required="required" />
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="cols4">
									<label class="control-label">文本区域</label>
									<div class="control-content">
										<textarea ctype="textarea" class="form-control autosizeme"
											placeholder="Chee Kin" rows="1" required="required"></textarea>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="tab-pane">
						<div class="form-horizontal">
							<div class="row-fluid">
								<div class="cols2">
									<label class="control-label">单选</label>
									<div class="control-content">
										<div ctype="radio" class="radio-list" required="required">
											<label class="radio-inline"> <input type="radio"
												name="optionsRadios" value="option1" required="required">Radio
												1
											</label> <label class="radio-inline"> <input type="radio"
												name="optionsRadios" value="option2" required="required">Radio
												2
											</label> <label class="radio-inline"> <input type="radio"
												name="optionsRadios" value="option3" required="required"
												disabled>Disabled
											</label>
										</div>
									</div>
								</div>
								<div class="cols2">
									<label class="control-label">复选</label>
									<div class="control-content">
										<div ctype="checkbox" class="checkbox-list"
											required="required">
											<label class="checkbox-inline"> <input
												type="checkbox" name="optionsCheckboxs" value="option1"
												required="required">Checkbox 1
											</label> <label class="checkbox-inline"> <input
												type="checkbox" name="optionsCheckboxs" value="option2"
												required="required">Checkbox 2
											</label> <label class="checkbox-inline"> <input
												type="checkbox" name="optionsCheckboxs" value="option3"
												required="required" disabled>Disabled
											</label>
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="cols">
									<label class="control-label">双击事件</label>
									<div class="control-content dblclick-open">
										<button type="button" class="open-icon">
											<i class="fa fa-ellipsis-h"></i>
										</button>
										<input ctype="textfield" dbl_action="X41" type="text"
											class="form-control" placeholder="Chee Kin">
									</div>
								</div>
								<div class="cols2">
									<label class="control-label">双击事件(图标)</label>
									<div class="control-content dblclick-open has-icon">
										<i class="control-icon fa fa-weixin"></i>
										<button type="button" class="open-icon">
											<i class="fa fa-ellipsis-h"></i>
										</button>
										<input ctype="textfield" dbl_action="X41" type="text"
											class="form-control" placeholder="Chee Kin">
									</div>
								</div>
								<button ctype="button" action="x41" type="button"
									class="btn btn-sm red">
									<i class="fa fa-ellipsis-h"></i> 这是个表单按钮
								</button>
							</div>
							<div class="row-fluid">
								<div class="cols2">
									<label class="control-label">图片组件</label>
									<div class="control-content">
										<input ctype="file" type="file" accept="image/*"
											class="form-control" placeholder="Chee Kin">
									</div>
								</div>
								<div class="cols2">
									<label class="control-label">文件组件</label>
									<div class="control-content">
										<input ctype="file" type="file" accept="*"
											class="form-control" placeholder="Chee Kin">
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<button ctype="button" action="x41" type="button"
									class="btn btn-sm btn-default" style="margin-left: 80px;">
									<i class="fa fa-user"></i> Profile
								</button>
								<button ctype="button" action="x41" type="button"
									class="btn btn-sm btn-success">
									<i class="fa fa-user"></i> Profile
								</button>
								<button ctype="button" action="x41" type="button"
									class="btn btn-sm btn-info">
									<i class="fa fa-user"></i> Profile
								</button>
								<button ctype="button" action="x41" type="button"
									class="btn btn-sm btn-warning">
									<i class="fa fa-user"></i> Profile
								</button>
								<button ctype="button" action="x41" type="button"
									class="btn btn-sm btn-danger">
									<i class="fa fa-user"></i> Profile
								</button>
								<button ctype="button" action="x41" type="button"
									class="btn btn-sm btn-primary">
									<i class="fa fa-user"></i> Profile
								</button>
								<button ctype="button" action="x41" type="button"
									class="btn btn-sm btn-link">
									<i class="fa fa-user"></i> Profile
								</button>
								<button ctype="button" action="x41" type="button"
									class="btn btn-sm blue">
									<i class="fa fa-user"></i> Profile
								</button>
								<button ctype="button" action="x41" type="button"
									class="btn btn-sm red-pink">
									<i class="fa fa-user"></i> Profile
								</button>
							</div>
						</div>
					</div>
					<div class="tab-pane">
						<div class="form-horizontal">
							<div class="row-fluid">
								<div class="cols4">
									<label class="control-label">富文本编辑器</label>
									<div class="control-content">
										<div ctype="richeditor" height="300">
											<h1 style="text-align: center;">啊十大发生的发</h1>
											<h2 style="text-align: left;">1.富文本编辑器</h2>
											<blockquote>
												爽了吧？ <img style="width: 30px;"
													src="data:image/gif;base64,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"
													data-filename="4RWZQC(@}7@7AUX%[}K)_5T.gif">
											</blockquote>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<c:import url="/footer.jsp"></c:import>
<script type="text/javascript">
	$(document).ready(function() {
		//$(document).ccinit();
	});
</script>
