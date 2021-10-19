<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="cooperop-page">
		<xsl:choose>
			<!-- <xsl:when test="@designer = 'true'">
				<xsl:apply-templates></xsl:apply-templates>
			</xsl:when> -->
			<xsl:when test="@isblank = 'true'">
				<xsl:value-of select="$jsp-header" disable-output-escaping="yes"></xsl:value-of>
				<script type="text/javascript">
					var cooperopcontextpath = "<xsl:value-of select="$contextpath"></xsl:value-of>";
					var module = "<xsl:value-of select="$module"></xsl:value-of>";
					var pageid = "<xsl:value-of select="$pageid"></xsl:value-of>";
					var userinfo = <xsl:value-of select="$userinfo"></xsl:value-of>;
					var rm_url = "<xsl:value-of select="$rm_url"></xsl:value-of>";
					var ws_config = {
						http_url: "<xsl:value-of select="$http_url"></xsl:value-of>",
						ws_url: "<xsl:value-of select="$ws_url"></xsl:value-of>",
						token_key: "<xsl:value-of select="$ws_token_key"></xsl:value-of>",
						app_key: "<xsl:value-of select="$ws_app_key"></xsl:value-of>",
						app_id: "yaoxunkang"
					}
				</script>
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
						<meta name="renderer" content="webkit"></meta>
						<title><xsl:if test="@title != ''"><xsl:value-of select="@title"></xsl:value-of></xsl:if></title>
						<script type="text/javascript">
							var cooperopcontextpath = "<xsl:value-of select="$contextpath"></xsl:value-of>";
							var module = "<xsl:value-of select="$module"></xsl:value-of>";
							var pageid = "<xsl:value-of select="$pageid"></xsl:value-of>";
							var userinfo = <xsl:value-of select="$userinfo"></xsl:value-of>;
							var rm_url = "<xsl:value-of select="$rm_url"></xsl:value-of>";
							var ws_config = {
								http_url: "<xsl:value-of select="$http_url"></xsl:value-of>",
								ws_url: "<xsl:value-of select="$ws_url"></xsl:value-of>",
								token_key: "<xsl:value-of select="$ws_token_key"></xsl:value-of>",
								app_key: "<xsl:value-of select="$ws_app_key"></xsl:value-of>",
								app_id: "yaoxunkang"
							}
						</script>
						<link rel="stylesheet" type="text/css" href="{$contextpath}/theme/css/custom.css" />
						<link rel="shortcut icon" href="{$contextpath}/theme/favicon.ico" />
						<link href="{$contextpath}/theme/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet"
							type="text/css" />
						<link href="{$contextpath}/theme/plugins/google-fonts/opensans.css" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/plugins/bootstrap/css/bootstrap.min.css?m={$module}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/common.css" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/main/cicon/iconfont.css" rel="stylesheet" type="text/css" />
						<!-- 控件css -->
						<link href="{$contextpath}/theme/css/components/input/textfield.css?_v={$_v}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/components/input/autocomplete.css?_v={$_v}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/components/input/button.css?_v={$_v}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/components/input/chart.css?_v={$_v}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/components/input/checkbox.css?_v={$_v}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/components/input/datefield.css?_v={$_v}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/components/input/daterange.css?_v={$_v}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/components/input/file.css?_v={$_v}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/components/input/image.css?_v={$_v}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/components/input/numberfield.css?_v={$_v}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/components/input/numberrange.css?_v={$_v}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/components/input/radio.css?_v={$_v}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/components/input/richeditor.css?_v={$_v}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/components/input/select.css?_v={$_v}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/components/input/selecttree.css?_v={$_v}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/components/input/slider.css?_v={$_v}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/components/input/switch.css?_v={$_v}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/components/input/taskhistory.css?_v={$_v}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/components/input/textarea.css?_v={$_v}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/components/input/textrange.css?_v={$_v}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/components/input/timefield.css?_v={$_v}" rel="stylesheet" type="text/css" />
						<link href="{$contextpath}/theme/css/components/input/timerange.css?_v={$_v}" rel="stylesheet" type="text/css" />
						<!--//lijun   开始-->
						<!-- <link href="http://at.alicdn.com/t/font_1122195_lj66u8bqwl.css"  rel="stylesheet" type="text/css" /> -->
						<link href="{$contextpath}/theme/css/page.css" rel="stylesheet" type="text/css" />
						<!--//lijun   结束-->
						<script src="{$contextpath}/theme/plugins/jquery.min.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/jquery-migrate.min.js" type="text/javascript"></script>
						<!-- IMPORTANT! Load jquery-ui.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
						<script src="{$contextpath}/theme/plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"
							type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/bootstrap-growl/jquery.bootstrap-growl.min.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/jquery.blockui.min.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/jquery.cokie.min.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/jquery.json.min.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/pinyinjs-master/dict/pinyin_dict_firstletter.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/pinyinjs-master/pinyinUtil.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/bootstrap-tabdrop/js/bootstrap-tabdrop.js" type="text/javascript"></script>
						<xsl:value-of select="$ie8-html5" disable-output-escaping="yes"></xsl:value-of>
						<script type="text/javascript" src="{$contextpath}/theme/plugins/autosize/autosize.min.js"></script>
						<script src="{$contextpath}/theme/scripts/format.min.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/layer/layer.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/scripts/common.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/highcharts-9.1.2/js/highcharts.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/highcharts-9.1.2/js/highcharts-more.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/highcharts-9.1.2/js/modules/drilldown.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/highcharts-9.1.2/js/modules/exporting.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/html2canvas.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/FileSaver.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/plugins/jquery.wordexport.js" type="text/javascript"></script>
						
						<!-- <script src="{$contextpath}/theme/plugins/polyfill/polyfill.js" type="text/javascript"></script> -->
						
						<xsl:call-template name="cooperop-form-link"></xsl:call-template>
						<xsl:call-template name="cooperop-condition-link"></xsl:call-template>
						<xsl:call-template name="cooperop-table-link"></xsl:call-template>
						<xsl:call-template name="cooperop-list-link"></xsl:call-template>
						<xsl:call-template name="cooperop-tabpanel-link"></xsl:call-template>
						<xsl:call-template name="cooperop-toolbar-link"></xsl:call-template>
						<xsl:call-template name="cooperop-row-link"></xsl:call-template>
						<xsl:call-template name="cooperop-column-link"></xsl:call-template>
						
						<xsl:call-template name="cooperop-autocomplete-link"></xsl:call-template>
						<xsl:call-template name="cooperop-button-link"></xsl:call-template>
						<xsl:call-template name="cooperop-datefield-link"></xsl:call-template>
						<xsl:call-template name="cooperop-checkbox-link"></xsl:call-template>
						<xsl:call-template name="cooperop-file-link"><xsl:with-param name="rm_url" select="$rm_url" /></xsl:call-template>
						<xsl:call-template name="cooperop-image-link">
							<xsl:with-param name="rm_url" select="$rm_url" />
						</xsl:call-template>
						<xsl:call-template name="cooperop-pdf-link"></xsl:call-template>
						<xsl:call-template name="cooperop-radio-link"></xsl:call-template>
						<xsl:call-template name="cooperop-richeditor-link"></xsl:call-template>
						<xsl:call-template name="cooperop-select-link"></xsl:call-template>
						<xsl:call-template name="cooperop-select2-link"></xsl:call-template>
						<xsl:call-template name="cooperop-select-link"></xsl:call-template>
						<xsl:call-template name="cooperop-selecttree-link"></xsl:call-template>
						<xsl:call-template name="cooperop-switch-link"></xsl:call-template>
						<xsl:call-template name="cooperop-textarea-link"></xsl:call-template>
						<xsl:call-template name="cooperop-textfield-link"></xsl:call-template>
						<xsl:call-template name="cooperop-timefield-link"></xsl:call-template>
						<xsl:call-template name="cooperop-chart-link"></xsl:call-template>
						<xsl:call-template name="cooperop-taskhistory-link"></xsl:call-template>
						<script src="{$contextpath}/theme/scripts/controls/input/password.js" type="text/javascript"></script>
						<script src="{$contextpath}/theme/scripts/controls/controls.js" type="text/javascript"></script>
						<xsl:if test="$rm_url != ''">
							<script type="text/javascript" src="{$rm_url}/jssdk/chohorm.js"></script>
						</xsl:if>
						</head>
						<xsl:call-template name="view-body">
						</xsl:call-template>
						<script type="text/javascript">
							$("div[ctype='form']").each(function(index){
								var $this = $(this);
								if($this.attr("extendable") == 'true'){
									if ($this.attr("collapsed") == 'true') {
										$this.parent().css("display", "none");
										$this.parent().parent().css("padding", "0 0 0 0");
									}
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
				</html>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="view-body">
		<body>
			<xsl:for-each select="./@*[name()!='title'][name()!='class'][name()!='ismodal'][name()!='disloggedin'][name()!='dispermission']">
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
			<!-- BEGIN CONTAINER -->
			<div class="main">
				<!-- BEGIN CONTENT -->
				<div class="content home clearfix  ng-scope">
					<form action="javascript:void(0);" method="post" onsubmit="return false;">
						<xsl:apply-templates></xsl:apply-templates>
					</form>
				</div>
			</div>
		</body>
	</xsl:template>
	<xsl:variable name="jsp-header"><![CDATA[
<!DOCTYPE html>
	]]></xsl:variable>
	<xsl:variable name="ie8-html5"><![CDATA[
		<!--[if lt IE 9]> <script src="{$contextpath}/theme/plugins/respond.min.js"></script> <script src="{$contextpath}/theme/plugins/excanvas.min.js"></script> <![endif] -->
	]]></xsl:variable>
</xsl:stylesheet>