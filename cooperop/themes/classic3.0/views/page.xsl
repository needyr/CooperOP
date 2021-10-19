<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="cooperop-page">
		<xsl:choose>
			<xsl:when test="@designer = 'true'">
				<xsl:apply-templates></xsl:apply-templates>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$jsp-header" disable-output-escaping="yes"></xsl:value-of>
				<html>
					<head>
						<meta http-equiv="Content-type" content="text/html; charset=utf-8"></meta>
						<meta charset="UTF-8"></meta>
						<meta http-equiv="X-UA-Compatible" content="IE=edge"></meta>
						<meta content="width=device-width, initial-scale=1.0" name="viewport"></meta>
						<meta http-equiv="Cache-Control" content="no-cache,no-store, must-revalidate"></meta>
						<meta http-equiv="pragma" content="no-cache"></meta>
						<meta http-equiv="expires" content="0"></meta>
						<meta name="renderer" content="webkit"></meta>
						<title><xsl:if test="@title != ''"><xsl:value-of select="@title"></xsl:value-of>-</xsl:if><xsl:value-of select="$system_title"></xsl:value-of></title>
						<link rel="shortcut icon" href="{$contextpath}/favicon.ico" />
						<link href="{$contextpath}/theme/plugins/font-awesome/css/font-awesome.min.css?m={$module}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/plugins/simple-line-icons/simple-line-icons.min.css?m={$module}" rel="stylesheet"
							type="text/css" />
						<link href="{$contextpath}/theme/plugins/google-fonts/opensans.css?m={$module}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/plugins/bootstrap/css/bootstrap.min.css?m={$module}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/plugins/uniform/css/uniform.default.css?m={$module}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/plugins/bootstrap-switch/css/bootstrap-switch.min.css?m={$module}" rel="stylesheet"
							type="text/css" />
						<script src="{$contextpath}/theme/plugins/jquery.min.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/jquery-migrate.min.js" type="text/javascript"></script>
						<!-- IMPORTANT! Load jquery-ui.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
						<script src="{$contextpath}/theme/plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"
							type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/jquery.blockui.min.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/jquery.cokie.min.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/jquery.json.min.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/bootstrap-tabdrop/js/bootstrap-tabdrop.js" type="text/javascript"></script>
						<script type="text/javascript" src="{$contextpath}/theme/scripts/controls/input/password.js"></script>
						<xsl:value-of select="$ie8-html5" disable-output-escaping="yes"></xsl:value-of>
						<script type="text/javascript" src="{$contextpath}/theme/plugins/autosize/autosize.min.js"></script>
						<script src="{$contextpath}/theme/layout/scripts/metronic.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/scripts/format.min.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/layout/scripts/layout.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/layer/layer.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/scripts/common.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/highcharts-4.1.9/js/highcharts.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/highcharts-4.1.9/js/highcharts-more.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/highcharts-4.1.9/js/modules/exporting.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/highcharts-4.1.9/js/modules/drilldown.js" type="text/javascript"></script>
						<script type="text/javascript">
							var cooperopcontextpath = "<xsl:value-of select="$contextpath"></xsl:value-of>";
							var module = "<xsl:value-of select="$module"></xsl:value-of>";
							var pageid = "<xsl:value-of select="$pageid"></xsl:value-of>";
							var userinfo = <xsl:value-of select="$userinfo"></xsl:value-of>;
							var ws_config = {
								http_url: "<xsl:value-of select="$http_url"></xsl:value-of>",
								ws_url: "<xsl:value-of select="$ws_url"></xsl:value-of>",
								token_key: "<xsl:value-of select="$ws_token_key"></xsl:value-of>",
								app_key: "<xsl:value-of select="$ws_app_key"></xsl:value-of>",
								app_id: "yaoxunkang"
							}
						</script>
						<xsl:call-template name="cooperop-form-link"></xsl:call-template>
						<xsl:call-template name="cooperop-table-link"></xsl:call-template>
						<xsl:call-template name="cooperop-tabpanel-link"></xsl:call-template>
						<xsl:call-template name="cooperop-toolbar-link"></xsl:call-template>
						<xsl:call-template name="cooperop-row-link"></xsl:call-template>
						
						<xsl:call-template name="cooperop-autocomplete-link"></xsl:call-template>
						<xsl:call-template name="cooperop-button-link"></xsl:call-template>
						<xsl:call-template name="cooperop-datefield-link"></xsl:call-template>
						<xsl:call-template name="cooperop-checkbox-link"></xsl:call-template>
						<xsl:call-template name="cooperop-file-link"></xsl:call-template>
						<xsl:call-template name="cooperop-image-link"></xsl:call-template>
						<xsl:call-template name="cooperop-radio-link"></xsl:call-template>
						<xsl:call-template name="cooperop-richeditor-link"></xsl:call-template>
						<xsl:call-template name="cooperop-select-link"></xsl:call-template>
						<xsl:call-template name="cooperop-switch-link"></xsl:call-template>
						<xsl:call-template name="cooperop-textarea-link"></xsl:call-template>
						<xsl:call-template name="cooperop-textfield-link"></xsl:call-template>
						<xsl:call-template name="cooperop-timefield-link"></xsl:call-template>
						<xsl:call-template name="cooperop-taskhistory-link"></xsl:call-template>
						<xsl:call-template name="cooperop-chart-link"></xsl:call-template>
						<xsl:call-template name="cooperop-selecttree-link"></xsl:call-template>
						<link href="{$contextpath}/theme/css/components.css?m={$module}" id="style_components" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/plugins.css?m={$module}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/layout/css/layout.css?m={$module}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/custom.css?m={$module}" rel="stylesheet" type="text/css" />
						<script src="{$contextpath}/theme/scripts/controls/controls.js" type="text/javascript"></script>
						<xsl:if test="@isfull = 'true'">
						<link rel="stylesheet" type="text/css"
							href="{$contextpath}/theme/plugins/jstree/dist/themes/default/style.min.css?m={$module}" />
						<script
							src="{$contextpath}/theme/plugins/jstree/dist/jstree.min.js"></script>
						<script
							src="{$contextpath}/theme/pages/scripts/ui-tree.js"></script>
						<script src="{$contextpath}/theme/scripts/index.js" type="text/javascript"></script>
						<script type="text/javascript" src="{$contextpath}/themes/im/script/client.js"></script>
						<script type="text/javascript" >
							$(document).ready(function() {
								$cimc.init({
									userid: userinfo.id
								});
							});
						</script>
						</xsl:if>
						</head>
					<xsl:choose>
						<xsl:when test="@isfull = 'true'">
							<xsl:call-template name="view-body">
								<xsl:with-param name="isfull">true</xsl:with-param>
							</xsl:call-template>
						</xsl:when>
						<xsl:otherwise>
							<xsl:call-template name="view-body">
								<xsl:with-param name="isfull"></xsl:with-param>
							</xsl:call-template>
						</xsl:otherwise>
					</xsl:choose>
				</html>
				<script type="text/javascript">
					var refreshcycle = <xsl:value-of select="$refreshcycle"></xsl:value-of>;
					var ismodal = <xsl:value-of select="$ismodal"></xsl:value-of>;
					apiready = function () {
						cloud_app.is_app = true;
						cloud_app.app_name = api.appName;
					}
					cwbready = function() {
				var html = [];
				html.push('<li class="dropdown">');
				html.push('<a title="重载" class="dropdown-toggle reload" href="javascript:void(0);" onclick="location.reload(true);">');
				html.push('<i class="icon-reload"></i>');
				html.push('</a>');
				html.push('</li>');
				html.push('<li class="dropdown">');
				html.push('<a title="最小化" class="dropdown-toggle min" href="javascript:void(0);" onclick="jwin.min()">');
				html.push('<i class="min-icon"></i>');
				html.push('</a>');
				html.push('</li>');
				html.push('<li class="dropdown">');
				html.push('<a title="最大化" class="dropdown-toggle max" href="javascript:void(0);">');
				html.push('<i class="max-icon"></i>');
				html.push('</a>');
				html.push('</li>');
				html.push('<li class="dropdown">');
				html.push('<a title="退出系统" class="dropdown-toggle exit" href="javascript:void(0);" onclick="jwin.exit()">');
				html.push('<i class="exit-icon"></i>');
				html.push('</a>');
				html.push('</li>');
				$('.page-header.navbar .top-menu .navbar-nav').css("margin-right", "0px");
				$('.page-header.navbar .top-menu .navbar-nav').append(html.join(""));
				$('.page-header.navbar .top-menu .navbar-nav > li.dropdown').on('click', '.dropdown-toggle.max, .dropdown-toggle.normal', function(e) {
		            e.preventDefault();
		            if ($(this).hasClass("max")) {
		                $(this).removeClass("max").addClass("normal").attr("title", "还原窗口");
		                $(this).find("i").removeClass("max-icon").addClass("normal-icon");
		                jwin.max();
		                
		            } else {
		                $(this).removeClass("normal").addClass("max").attr("title", "最大化");
		                $(this).find("i").removeClass("normal-icon").addClass("max-icon");
		                jwin.normal();
		            }
		        });
				var mf = false;
				$(".page-header").on({
					"mousedown": function() {
						mf = true;
						$(".page-header").css("cursor", "move");
					},
					"mousemove": function(event) {
						if (mf) {
							var e = event || window.event;  
							$(".page-header").css("cursor", "move");
							jwin.position(e.screenX, e.screenY, 25);
						}
					},
					"mouseup": function() {
						mf = false;
						$(".page-header").css("cursor", "default");
					}
				});
			}
					$(document).ready(function() {
						Layout.init();
						setTimeout('$("#pageloading").remove();',100);
					});
					$("div[ctype='form']").each(function(index){
						var $this = $(this);
						if($this.attr("extendable") == 'true'){
							$this.parent().css("display", "none");
							$this.parent().parent().css("padding", "0 0 0 0");
						}
					});
					$("i.showhide").on("click", function(){
						var $this = $(this);
						if($this.hasClass("fa-angle-double-up")){
							$this.parent().prev().css("padding", "0 0 0 0");
							$this.parent().prev().find(".portlet-body").css("display", "none");
							$this.removeClass("fa-angle-double-up");
							$this.addClass("fa-angle-double-down");
						}else{
							$this.parent().prev().css("padding", "12px 20px 15px 20px");
							$this.parent().prev().find(".portlet-body").css("display", "");
							$this.removeClass("fa-angle-double-down");
							$this.addClass("fa-angle-double-up");
						}
						
					});
				</script>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="view-body">
		<xsl:param name="isfull" />
		<body>
			<xsl:if test="$isfull = 'true'">
				<xsl:attribute name="class">page-quick-sidebar-over-content page-header-fixed page-sidebar-fixed page-footer-fixed</xsl:attribute>
			</xsl:if>
			<xsl:for-each select="./@*[name()!='title'][name()!='class'][name()!='isfull'][name()!='disloggedin'][name()!='dispermission']">
				<xsl:attribute name="{name()}"><xsl:value-of select="." disable-output-escaping="yes"></xsl:value-of></xsl:attribute>
			</xsl:for-each>
			<div id="pageloading">
				<div class="layui-layer-shade" times="3"
					style="z-index:19891016; background-color:#fff; opacity:1; filter:alpha(opacity=100);"></div>
				<div class="layui-layer layui-anim layui-layer-loading " type="loading" times="3" showtime="0" contype="string"
					style="z-index: 999999999; top: 215px; left: 744.5px;">
					<div class="layui-layer-content"></div>
					<span class="layui-layer-setwin"></span>
				</div>
			</div>
			<xsl:if test="$isfull = 'true'">
				<xsl:call-template name="view-header"></xsl:call-template>
			</xsl:if>
			<div class="clearfix"></div>
			<!-- BEGIN CONTAINER -->
			<div class="page-container">
				<xsl:if test="$isfull = 'true'">
					<xsl:call-template name="view-slider-menu"></xsl:call-template>
				</xsl:if>
				<!-- BEGIN CONTENT -->
				<div>
					<xsl:if test="$isfull = 'true'">
						<xsl:attribute name="class">page-content-wrapper</xsl:attribute>
					</xsl:if>
					<xsl:if test="$isfull = 'true'">
						<div class="page-content-tabs tabbable tabbable-tabdrop full-height-content" ctype="tabwindow">
							<ul class="nav nav-pills">
								<xsl:if test="$url != '' or $page != ''">
									<li wid="welcome" class="active" title="平台首页"><div><a class="nav-title" href="javascript:void(0);">平台首页</a><a class="nav-refresh" title="刷新" href="javascript:void(0);"><i class="fa fa-refresh"></i></a><a class="nav-close" title="关闭" href="javascript:void(0);"><i class="fa fa-close"></i></a></div></li>
								</xsl:if>
							</ul>
							<div class="tab-content">
								<xsl:if test="$url != '' or $page != ''">
									<div class="tab-pane active" wid="welcome">
										<iframe frameborder="0" style="width:100%;border:0px;">
											<xsl:choose>
												<xsl:when test="$url != ''">
													<xsl:attribute name="src"><xsl:value-of select="$url"></xsl:value-of></xsl:attribute>
												</xsl:when>
												<xsl:when test="$page != ''">
													<xsl:attribute name="src"><xsl:value-of select="$contextpath"></xsl:value-of>/w/<xsl:value-of select="translate($page, '.', '/')"></xsl:value-of>.html</xsl:attribute>
												</xsl:when>
											</xsl:choose>
										</iframe>
									</div>
								</xsl:if>
							</div>
						</div>
						<!-- BEGIN QUICK SIDEBAR -->
						<!-- <a href="javascript:;" class="page-quick-sidebar-toggler"><i class="icon-close"></i></a>
						<div class="page-quick-sidebar-wrapper">
							<div class="page-quick-sidebar">
								<div class="nav-justified">
									<ul class="nav nav-tabs nav-justified">
										<li class="active" id="siderbar_message_session" title="会话">
											<a href="#quick_sidebar_tab_message_session" data-toggle="tab">
											<i class="icon-bubbles" style="font-size: 18px;"></i>
											<span class="badge badge-danger" style="display:none;">
												0
											</span>
											</a>
										</li>
										<li id="siderbar_message_contacter" title="联系人">
											<a href="#quick_sidebar_tab_message_contacter" data-toggle="tab">
											<i class="icon-user" style="font-size: 18px;"></i>
											</a>
										</li>
									</ul>
									<div class="tab-content">
										<div class="tab-pane page-quick-sidebar-chat active" id="quick_sidebar_tab_message_session">
											<div class="page-quick-sidebar-toolbar">
												<a href="javascript:void(0)" onclick="messagesession();"><i class="fa fa-refresh"></i>刷新</a>
											</div>
											<div class="page-quick-sidebar-chat-users">
												<ul class="media-list list-items">
												</ul>
											</div>
										</div>
										<div class="tab-pane page-quick-sidebar-chat" id="quick_sidebar_tab_message_contacter">
											<div class="page-quick-sidebar-toolbar">
												<input type="search" name="filter" class="filter" placeholder="搜索..."></input>
												<a href="javascript:void(0)" title="搜索" onclick="contacters();"><i class="fa fa-search"></i></a>
												<a href="javascript:void(0)" title="清除条件" onclick="$(this).siblings('input').val('');contacters();"><i class="fa fa-eraser"></i></a>
												<a href="javascript:void(0)" title="创建群组" onclick="createContacters();"><i class="glyphicon glyphicon-plus"></i></a>
											</div>
											<div class="page-quick-sidebar-chat-users">
											</div>
										</div>
									</div>
								</div>
							</div>
						</div> -->
						<!-- END QUICK SIDEBAR -->
					</xsl:if>
					<xsl:if test="$isfull != 'true'">
						<div class="page-content">
							<!-- BEGIN PAGE HEADER -->
							<!-- <div class="page-bar">
								<ul class="page-breadcrumb">
									<li>
										<i class="fa fa-home"></i>
										<a href="{$contextpath}/{$welcomepage}">首页</a>
										<i class="fa fa-angle-right"></i>
									</li>
									<xsl:for-each select="cooperop-menuinfos/cooperop-menuinfo">
										<li>
											<a>
												<xsl:choose>
													<xsl:when test="@code='' or @code='#'">
														<xsl:attribute name="href">javascript:void(0);</xsl:attribute>
													</xsl:when>
													<xsl:otherwise>
														<xsl:attribute name="href"><xsl:value-of select="$contextpath"></xsl:value-of>/w/<xsl:value-of select="translate(@code, '.', '/')"></xsl:value-of>.html?_pid_=<xsl:value-of select="@id"></xsl:value-of>&amp;<xsl:value-of select="@event"></xsl:value-of></xsl:attribute>
													</xsl:otherwise>
												</xsl:choose>
												<xsl:value-of select="@name"></xsl:value-of>
											</a>
											<xsl:if test="position() &lt; last()">
												<i class="fa fa-angle-right"></i>
											</xsl:if>
										</li>
									</xsl:for-each>
								</ul>
							</div> -->
							<form action="javascript:void(0);" method="post" onsubmit="return false;">
							<xsl:apply-templates></xsl:apply-templates>
							</form>
						</div>
					</xsl:if>
				</div>
			</div>
			<xsl:if test="$isfull = 'true'">
				<xsl:call-template name="view-footer"></xsl:call-template>
			</xsl:if>
		</body>
	</xsl:template>
	<xsl:template name="view-header">
		<!-- BEGIN HEADER -->
		<div class="page-header navbar navbar-fixed-top">
			<!-- BEGIN HEADER INNER -->
			<div class="page-header-inner">
				<!-- BEGIN LOGO -->
				<div class="page-logo">
					<a href="{$contextpath}/{$welcomepage}">
					<xsl:choose>
						<xsl:when test="$url != ''">
							<xsl:attribute name="href">javascript:void(0);</xsl:attribute>
							<xsl:attribute name="onclick">$(".page-content-tabs").open_tabwindow("welcome", "平台首页", "<xsl:value-of select="$url"></xsl:value-of>");</xsl:attribute>
						</xsl:when>
						<xsl:when test="$page != ''">
							<xsl:attribute name="href">javascript:void(0);</xsl:attribute>
							<xsl:attribute name="onclick">$(".page-content-tabs").open_tabwindow("welcome", "平台首页", "<xsl:value-of select="$contextpath"></xsl:value-of>/w/<xsl:value-of select="translate($page, '.', '/')"></xsl:value-of>.html");</xsl:attribute>
						</xsl:when>
					</xsl:choose>
					<xsl:value-of select="$system_title"></xsl:value-of></a>
					<div class="menu-toggler sidebar-toggler hide">
						<!-- DOC: Remove the above "hide" to enable the sidebar toggler button on header -->
					</div>
				</div>
				<!-- END LOGO -->
				<!-- BEGIN RESPONSIVE MENU TOGGLER -->
				<a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse">
				</a>
				<!-- END RESPONSIVE MENU TOGGLER -->
				<!-- BEGIN TOP NAVIGATION MENU -->
				<div class="top-menu">
					<ul class="nav navbar-nav pull-right">
						<!-- BEGIN NOTIFICATION -->
						<!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
						<li class="dropdown" id="header_notification_bar">
							<a href="javascript:;" class="dropdown-toggle" title="通知公告">
								<i class="icon-bell"></i>
								<span class="badge badge-danger" style="display:none;">
									0
								</span>
							</a>
						</li>
						<!-- END NOTIFICATION -->
						<!-- BEGIN TODO -->
						<li class="dropdown" id="header_suggestions_bar">
							<a href="javascript:;" class="dropdown-toggle" title="消息提醒">
								<i class="icon-volume-2"></i>
								<span class="badge badge-danger" style="display:none;">
									0
								</span>
							</a>
						</li>
						<!-- END TODO -->
						<!-- BEGIN TODO -->
						<li class="dropdown" id="header_task_bar">
							<a href="javascript:;" class="dropdown-toggle" title="待办事项">
								<i class="icon-calendar"></i>
								<span class="badge badge-danger" style="display:none;">
									0
								</span>
							</a>
						</li>
						<!-- END TODO -->
						<!-- BEGIN INBOX -->
						<!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
						<li class="dropdown" id="header_inbox_bar">
							<a href="javascript:;" class="dropdown-toggle" title="我的邮件">
								<i class="icon-envelope-open"></i>
								<span class="badge badge-primary" style="display:none;">
									0
								</span>
							</a>
						</li>
						<!-- END INBOX -->
						<!-- BEGIN QUICK SIDEBAR TOGGLER -->
						<!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
						<!-- <li class="dropdown dropdown-quick-sidebar-toggler" id="header_message_bar">
							<a href="javascript:;" class="dropdown-toggle" title="即时消息">
								<i class="icon-bubbles"></i>
								<span class="badge badge-default" style="display:none;">
									0
								</span>
							</a>
						</li> -->
						<!-- END QUICK SIDEBAR TOGGLER -->
						<!-- BEGIN USER LOGIN DROPDOWN -->
						<!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
						<li class="dropdown dropdown-user">
							<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
								<div class="icon-circle"><i class="fa fa-user"></i></div>
								<img alt="" class="img-circle" src="{$contextpath}/theme/img/avatar3_small.jpg" />
								<span class="username username-hide-on-mobile"></span>
								<i class="fa fa-angle-down"></i>
							</a>
							<ul class="dropdown-menu dropdown-menu-default">
								<!-- <li>
									<a href="javascript:;">
										<xsl:attribute name="onclick">$(".page-content-tabs").open_tabwindow("mine_profile", "主页定制", "<xsl:value-of select="$contextpath"></xsl:value-of>/w/application/mine/setting_main.html");</xsl:attribute>
										<i class="icon-settings"></i>
										个性主页
									</a>
								</li> -->
								<!-- <li>
									<a href="javascript:;">
										<xsl:attribute name="onclick">$(".page-content-tabs").open_tabwindow("mine_profile", "我的资料", "<xsl:value-of select="$contextpath"></xsl:value-of>/w/application/mine/profile.html");</xsl:attribute>
										<i class="icon-user"></i>
										我的资料
									</a>
								</li> -->
								<li>
									<a href="javascript:;">
										<xsl:attribute name="onclick">$(".page-content-tabs").open_tabwindow("mine_profile", "个人设置", "<xsl:value-of select="$contextpath"></xsl:value-of>/w/application/mine/profile.html");</xsl:attribute>
										<i class="icon-settings"></i>
										个人设置
									</a>
								</li>
								<li>
									<a href="javascript:;">
										<xsl:attribute name="onclick">$(".page-content-tabs").open_tabwindow("mine_changepwd", "修改密码", "<xsl:value-of select="$contextpath"></xsl:value-of>/w/application/mine/changepwd.html");</xsl:attribute>
										<i class="icon-lock"></i>
										修改密码
									</a>
								</li>
								<li>
									<a href="javascript:void(0);" onclick="$.logout();">
										<i class="icon-key"></i>
										注销用户
									</a>
								</li>
							</ul>
						</li>
						<!-- END USER LOGIN DROPDOWN -->
					</ul>
				</div>
				<!-- END TOP NAVIGATION MENU -->
			</div>
			<!-- END HEADER INNER -->
		</div>
		<!-- END HEADER -->
	</xsl:template>
	<xsl:template name="view-slider-menu">
		<!-- BEGIN SIDEBAR -->
		<div class="page-sidebar-wrapper">
			<!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
			<!-- DOC: Change data-auto-speed="200" to adjust the sub menu slide up/down speed -->
			<div class="page-sidebar navbar-collapse collapse">
				<!-- BEGIN SIDEBAR MENU -->
				<!-- DOC: Apply "page-sidebar-menu-light" class right after "page-sidebar-menu" to enable light sidebar menu style(without 
					borders) -->
				<!-- DOC: Apply "page-sidebar-menu-hover-submenu" class right after "page-sidebar-menu" to enable hoverable(hover vs 
					accordion) sub menu mode -->
				<!-- DOC: Apply "page-sidebar-menu-closed" class right after "page-sidebar-menu" to collapse("page-sidebar-closed" class 
					must be applied to the body element) the sidebar sub menu mode -->
				<!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
				<!-- DOC: Set data-keep-expand="true" to keep the submenues expanded -->
				<!-- DOC: Set data-auto-speed="200" to adjust the sub menu slide up/down speed -->
				<ul class="page-sidebar-menu" data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200">
					<!-- DOC: To remove the sidebar toggler from the sidebar you just need to completely remove the below "sidebar-toggler-wrapper" 
						LI element -->
					<li class="sidebar-toggler-wrapper">
						<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
						<div class="sidebar-toggler"></div> <!-- END SIDEBAR TOGGLER BUTTON -->
					</li>
					<li class="tooltips" data-container="body" data-placement="right" data-html="true" data-original-title="平台首页">
						<xsl:if test="menuinfos/menuinfo">
							<xsl:attribute name="class">tooltips open active</xsl:attribute>
						</xsl:if>
						<a href="{$contextpath}/{$welcomepage}">
							<xsl:choose>
								<xsl:when test="$url != ''">
									<xsl:attribute name="href">javascript:void(0);</xsl:attribute>
									<xsl:attribute name="onclick">$(".page-content-tabs").open_tabwindow("welcome", "平台首页", "<xsl:value-of select="$url"></xsl:value-of>");</xsl:attribute>
								</xsl:when>
								<xsl:when test="$page != ''">
									<xsl:attribute name="href">javascript:void(0);</xsl:attribute>
									<xsl:attribute name="onclick">$(".page-content-tabs").open_tabwindow("welcome", "平台首页", "<xsl:value-of select="$contextpath"></xsl:value-of>/w/<xsl:value-of select="translate($page, '.', '/')"></xsl:value-of>.html");</xsl:attribute>
								</xsl:when>
							</xsl:choose>
							<i class="icon-home"></i>
							<span class="title">平台首页</span>
						</a>
					</li>
					<xsl:for-each select="/cooperop-page/cooperop-menus/cooperop-menu">
						<xsl:if test="@level = '1'">
							<xsl:call-template name="mainmenu">
								<xsl:with-param name="id"><xsl:value-of select="@id"></xsl:value-of></xsl:with-param>
							</xsl:call-template>
						</xsl:if>
					</xsl:for-each>
				</ul>
				<!-- END SIDEBAR MENU -->
			</div>
		</div>
		<!-- END SIDEBAR -->
	</xsl:template>
	<xsl:template name="mainmenu">
		<xsl:param name="id"></xsl:param>
		<li>
			<xsl:if test="/cooperop-page/cooperop-menuinfos/cooperop-menuinfo[@id=$id]">
				<xsl:attribute name="class">open active</xsl:attribute>
			</xsl:if>
			<a href="javascript:void(0);">
				<xsl:choose>
					<xsl:when test="@code='' or @code='#'">
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="onclick">$(".page-content-tabs").open_tabwindow("<xsl:value-of select="@id"></xsl:value-of>", "<xsl:value-of select="@name"></xsl:value-of>", "<xsl:value-of select="$contextpath"></xsl:value-of>/w/<xsl:value-of select="translate(@code, '.', '/')"></xsl:value-of>.html?_pid_=<xsl:value-of select="@id"></xsl:value-of>&amp;<xsl:value-of select="@event"></xsl:value-of>");</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
				<i class="icon-grid">
					<xsl:if test="@icon != ''">
						<xsl:attribute name="class"><xsl:value-of select="@icon"></xsl:value-of></xsl:attribute>
					</xsl:if>
				</i>
				<span class="title"><xsl:value-of select="@name"></xsl:value-of></span>
				<xsl:if test="@child_num &gt; '0'">
					<span class="arrow">
						<xsl:if test="/cooperop-page/cooperop-menuinfos/cooperop-menuinfo[@id=$id]">
							<xsl:attribute name="class">arrow open</xsl:attribute>
						</xsl:if>
					</span>
				</xsl:if>
			</a>
			<xsl:if test="@child_num &gt; '0'">
				<ul class="sub-menu">
					<xsl:call-template name="submenu-loop">
						<xsl:with-param name="system_popedom_id_parent"><xsl:value-of select="@id"></xsl:value-of></xsl:with-param>
						<xsl:with-param name="level"><xsl:value-of select="@level"></xsl:value-of></xsl:with-param>
					</xsl:call-template>
				</ul>
			</xsl:if>
		</li>
	</xsl:template>
	<xsl:template name="submenu-loop">
		<xsl:param name="system_popedom_id_parent"></xsl:param>
		<xsl:param name="level"></xsl:param>
		<xsl:for-each select="/cooperop-page/cooperop-menus/cooperop-menu">
			<xsl:if test="@level = $level + 1 and @system_popedom_id_parent = $system_popedom_id_parent">
				<xsl:call-template name="submenu">
					<xsl:with-param name="id"><xsl:value-of select="@id"></xsl:value-of></xsl:with-param>
				</xsl:call-template>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="submenu">
		<xsl:param name="id"></xsl:param>
		<li>
			<xsl:if test="/cooperop-page/cooperop-menuinfos/cooperop-menuinfo[@id=$id]">
				<xsl:attribute name="class">open active</xsl:attribute>
			</xsl:if>
			<a href="javascript:void(0);">
				<xsl:choose>
					<xsl:when test="@code='' or @code='#'">
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="onclick">$(".page-content-tabs").open_tabwindow("<xsl:value-of select="@id"></xsl:value-of>", "<xsl:value-of select="@name"></xsl:value-of>", "<xsl:value-of select="$contextpath"></xsl:value-of>/w/<xsl:value-of select="translate(@code, '.', '/')"></xsl:value-of>.html?_pid_=<xsl:value-of select="@id"></xsl:value-of>&amp;<xsl:value-of select="@event"></xsl:value-of>");</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
				<i class="icon-equalizer">
					<xsl:if test="@icon != ''">
						<xsl:attribute name="class"><xsl:value-of select="@icon"></xsl:value-of></xsl:attribute>
					</xsl:if>
				</i>
				&#160;<xsl:value-of select="@name"></xsl:value-of>
				<xsl:if test="@child_num &gt; '0'">
					<span class="arrow">
						<xsl:if test="/cooperop-page/cooperop-menuinfos/cooperop-menuinfo[@id=$id]">
							<xsl:attribute name="class">arrow open</xsl:attribute>
						</xsl:if>
					</span>
				</xsl:if>
			</a>
			<xsl:if test="@child_num &gt; '0'">
				<ul class="sub-menu">
					<xsl:call-template name="submenu-loop">
						<xsl:with-param name="system_popedom_id_parent"><xsl:value-of select="@id"></xsl:value-of></xsl:with-param>
						<xsl:with-param name="level"><xsl:value-of select="@level"></xsl:value-of></xsl:with-param>
					</xsl:call-template>
				</ul>
			</xsl:if>
		</li>
	</xsl:template>
	<xsl:template name="view-footer">
		<!-- BEGIN FOOTER -->
		<div class="page-footer">
			<div class="page-footer-inner">
				<xsl:value-of select="$nowyear"></xsl:value-of> &#169;
				<a href="http://www.crtech.cn" title="{$copyright}" target="_blank"><xsl:value-of select="$copyright"></xsl:value-of></a>
				<i class="fa fa-phone"></i>
				<a href="tel:400-000-7932">400-000-7932</a>
				<i class="fa fa-envelope"></i>
				<a href="mailTo:chaoran_tech@yahoo.com.cn">chaoran_tech@yahoo.com.cn</a>
				<i class="fa fa-weixin"></i>
				<span>chaoran_weixin</span>
				<i class="fa fa-fax"></i>
				<a href="tel:028-61813208">028-61813208</a>
				<i class="fa fa-home"></i>
				<span>成都市三环武侯立交内侧
					星狮路711号大合仓商馆1栋1单元906室</span>
				<i class="fa fa-slack"></i>
				<span>610043</span>
			</div>
			<div class="scroll-to-top">
				<i class="icon-arrow-up"></i>
			</div>
		</div>
		<!-- END FOOTER -->
	</xsl:template>
	<xsl:variable name="jsp-header"><![CDATA[
<!DOCTYPE html>
	]]></xsl:variable>
	<xsl:variable name="ie8-html5"><![CDATA[
		<!--[if lt IE 9]> <script src="{$contextpath}/theme/plugins/respond.min.js"></script> <script src="{$contextpath}/theme/plugins/excanvas.min.js"></script> <![endif] -->
	]]></xsl:variable>
</xsl:stylesheet>