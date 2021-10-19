<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>邮件</title>
<meta charset="UTF-8">
<meta name="viewport"
	content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,initial-scale=1.0,width=device-width" />
<meta name="format-detection"
	content="telephone=no,email=no,date=no,address=no">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/mobile/font-awesome/css/font-awesome.min.css?iml=Y">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/mobile/api.css?iml=Y">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/mobile/common.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/writeEmail.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/bootstrap/bootstrap.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/simple-line-icons/simple-line-icons.css?iml=Y">
<link href="${pageContext.request.contextPath }/theme/plugins/select2/css/select2.min.css?iml=Y" type="text/css" rel="stylesheet" />
<style type="text/css">
	.select2-container {
		width: 75%!important;
		float:right;
	}
	.select2-container--default .select2-selection--multiple {
	    font-size: 12px;
	    font-weight: normal;
	    color: #333;
	    background-color: #fff;
	    border: none;
	    border-radius: none;
	    min-height: 30px;
    }
    .select2-container--default.select2-container--focus .select2-selection--multiple {
   	      border-color: #FFF;
    }
    
    .select2-container--default.select2-container--open .select2-selection--multiple {
   	    border-bottom-color: #FFF;
    }
    
    .select2-container--default .select2-selection--multiple .select2-selection__rendered {
    	padding: 0px 2px;
    }
    
	.select2-container--default .select2-selection--multiple .select2-selection__rendered:before {
		display: table;
		content: " ";
	}
	
	.select2-container--default .select2-selection--multiple .select2-selection__rendered:after {
		display: table;
		content: " ";
		clear: both;
	}
	   
    .select2-container--default .select2-search--inline .select2-search__field {
   	    margin: 1px;
	    /* height: 26px!important; */
	    line-height: 47px;
	    padding: 0px!important;
	    height: 2.8em;
	    font-size: 1.2em;
    }
    
    .select2-container--default .select2-selection--multiple .select2-selection__choice {
   	   /*  padding: 0px 3px 0px 5px;
	    margin: 2px; */
	    border: 1px solid #e5e5e5;
	    background-image: none;
	    background-color: #fff;
	    filter: none;
	    -webkit-box-shadow: none !important;
	    box-shadow: none !important;
	    /* height: 26px; */
	    line-height: 2.8em;
    }
    .select2-container--default .select2-selection--multiple .select2-selection__choice__remove {
    	float: right;
    	margin-left: 2px;
    	margin-right: 0px;
    }
    
    .select2-results {
    	margin: 0;
	}
	.tishi{
	    font-size: 16px;
    	line-height: 2.8em;
    	color: #bbb;
    	display: inline-block;
    	width: 64px;
    }
</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/css/jquery.fileupload.css?iml=Y">
<link rel="stylesheet" href="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/css/jquery.fileupload-ui.css?iml=Y">

<script type="text/javascript">
 var cooperopcontextpath = '${pageContext.request.contextPath}';
</script>
<script
	src="${pageContext.request.contextPath}/theme/plugins/apicloud/api.js?iml=Y"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js?iml=Y"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/theme/plugins/jquery-migrate.min.js?iml=Y"
	type="text/javascript"></script>
<script type="text/javascript" src="/theme/plugins/jquery.json.min.js?iml=Y"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/vendor/tmpl.min.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/vendor/load-image.min.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/vendor/canvas-to-blob.min.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/blueimp-gallery/jquery.blueimp-gallery.min.js?iml=Y" type="text/javascript">
</script><script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/jquery.iframe-transport.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/jquery.fileupload.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-process.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-image.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-audio.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-video.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-validate.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-ui.js?iml=Y" type="text/javascript"></script>


<script src="${pageContext.request.contextPath}/theme/scripts/window.js?iml=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/common.js?iml=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/email.js?iml=Y"
	type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/theme/plugins/select2/select2.min.js?iml=Y"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/theme/plugins/select2/i18n/zh-CN.js?iml=Y"></script>
</head>
<body>
<div id="wrap">
	<div class="header">
		<div class="header-div">
			<a href="javascript: void(0);" class="tiaoZhuan" onclick="backtomain();"><i
				class="icon-arrow-left"></i></a>
			<h4 class="header-h3">邮件</h4>
			<span style="
			    position: absolute;
			    top: 1em;
			    right: 2em;
			    font-size: 16px;
			    color: #8edaf6;
			" onclick="send();">发送</span>
		</div>
	</div>
	<div id="main">
	    <div class="main-div">
	     <!--  <input type="text" class="ipt" placeholder="收件人："> -->
			<div class="control-content dblclick-open ipt" id="selectdivto">
				<span class="tishi">收件人：</span>
				<select class="form-control emailto" name="to" multiple="multiple">
					<c:if test="${param.type eq 'r' or param.type eq 'ra'}">
						<option value="U|${$return.send_user_id}" selected="selected">${$return.send_user_name}</option>
					</c:if>
					<c:if test="${not empty to and empty param.type}">
						<c:set var="tn" value="${fn:split(to_name, ';')}"></c:set>
						<c:forEach items="${fn:split(to, ';')}" var="t" varStatus="i">
							<option value="${t}" selected="selected">${tn[i.index] }</option>
						</c:forEach>
					</c:if>
				</select>
			</div>

			<div class="control-content dblclick-open ipt" id="selectdivcc">
				<span class="tishi">抄送人：</span>
				<select class="form-control emailto" name="cc" multiple="multiple">
					<c:if test="${not empty to and (empty param.type or param.type eq 'ra')}">
						<c:set var="tn" value="${fn:split(to_name, ';')}"></c:set>
						<c:set var="tm" value="U|${user.id}"></c:set>
						<c:forEach items="${fn:split(to, ';')}" var="t" varStatus="i">
							<c:if test="${t ne tm}">
								<option value="${t}" selected="selected">${tn[i.index] }</option>
							</c:if>
						</c:forEach>
					</c:if>
					<c:if test="${not empty cc and (empty param.type or param.type eq 'ra')}">
						<c:set var="tn" value="${fn:split(cc_name, ';')}"></c:set>
						<c:forEach items="${fn:split(cc, ';')}" var="t" varStatus="i">
							<option value="${t}" selected="selected">${tn[i.index] }</option>
						</c:forEach>
					</c:if>
				</select>
			</div>
				<div class="ipt">
				<div class="attach-button">
					<input type="hidden" name="attach_files" class="file_file" value=""/>
					<i class="icon-paper-clip attach1">
					<input name="attach_files_files"  type="file"/></i>
				</div>
				<span class="tishi">主題：</span>
		     	 <input type="text" class="zhuti" name="subject" value="${param.type eq 's' ? '转发：' : (param.type eq 'ra' or param.type eq 'r' ? '回复：' : '') }${$return.subject}"/>
				</div>
				<div class="fujian attach">
				
				</div>
		  <c:choose>
		  <c:when  test="${param.type eq 'r' or param.type eq 'ra' or param.type eq 's'}">
		      <div class="email-content" name="name" title="内容..." placeholder="内容..." contenteditable="true" style="-webkit-user-select:text" >
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<hr/>
				<blockquote>
					<p><b>发件人：</b>${$return.send_user_name}</p>
					<p><b>发送时间：</b><fmt:formatDate value="${$return.send_time}" pattern="yyyy年MM月dd日 HH:mm"/></p>
					<p><b>收件人：</b>${$return.to_name}</p>
					<c:if test="${not empty $return.cc_name}">
						<p><b>抄送：</b>${$return.cc_name}</p>
					</c:if>
					<p><b>主题：</b>${$return.subject}</p>
					<br/>
					${$return.content}
				</blockquote>
		      </div>
	      </c:when>
	      <c:otherwise>
	      	<div class="email-content" name="name" placeholder="内容..." contenteditable="true" 
	      	style="padding:.5em;width:100%;height:400px;overflow: scroll;-webkit-user-select:text">
				${$return.content}
			</div>
			</c:otherwise>
		</c:choose>
	    </div>
	</div>
</div>
<script type="text/javascript">
	onready = function(){
		
	}
	function re(){
		location.reload();
	}
	function backtomain(){
		$.close();
	}
	$(document).ready(function() {
		initselect2();
		$(".attach-button").ccinit_appfile();
		
		
	});
	function initselect2(){

		var ICONS = {
				"A": "<i class='fa fa-home'></i>&nbsp;",
				"D": "<i class='fa fa-sitemap'></i>&nbsp;",
				"G": "<i class='fa fa-users'></i>&nbsp;",
				"U": "<i class='fa fa-user'></i>&nbsp;",
				"E": "<i class='fa fa-envelope-o'></i>&nbsp;"
		};
		var EMAIL_REXP =  /^[A-Za-zd]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$/;
		var FUATURE_MAIL = ["126.com", "163.com", "263.com", "qq.com", 'sina.com', 'sohu.com', 'gmail.com', 'hotmail.com', 'yahoo.com'];
		$(".emailto").select2({
			placeholder: "输入中文或拼音首字母缩写选择内部通讯录或输入正确的外部邮箱地址。" ,
			tags: true ,
			tokenSeparators: [';', ' '],
			language: "zh-CN",
			allowClear: false ,
			templateResult: function(data, container) {
				if (!data.data) {
					return undefined;
				}
				return ICONS[data.data.type] + data.data.name;
			},
			templateSelection: function(data) {
				if (!data.data && data.id) {
					return ICONS[data.id.substring(0, 1)] + data.text;
				}
				return ICONS[data.data.type] + data.data.name;
			},
			escapeMarkup: function (m) {
                return m;
            },
			ajax: {
				delay: 250,
				cache: false ,
				url: function(params) {
					return "";
				},
				data: function (params) {
				    var queryParameters = {
				      filter: params.term,
				      except: this.val(),
				      start: 1,
				      limit: 20
				    }
				    return queryParameters;
				},
				processResults: function (data) {
					var rtn = {results: []};
					for (var i in data.resultset) {
						rtn.results.push({
							id: data.resultset[i].type + "|" + data.resultset[i].id,
							text: ICONS[data.resultset[i].type] + data.resultset[i].name,
							title: "[" + data.resultset[i].type_name + "]" + data.resultset[i].name,
							data: data.resultset[i]
						});
					}
					if (EMAIL_REXP.test( data.totals.id )) {
						rtn.results.push({
							id: data.totals.type + "|" + data.totals.id,
							text: ICONS[data.totals.type] + data.totals.name,
							title: "[" + data.totals.type_name + "]" + data.totals.name,
							data: data.totals
						});
					} else if (data.totals.id) {
						if (data.totals.id.indexOf('@') > -1) {
							var s = data.totals.id.substring(data.totals.id.indexOf('@') + 1);
							for (var i in FUATURE_MAIL) {
								if (FUATURE_MAIL[i].startsWith(s)) {
									var sm = data.totals.id.substring(0, data.totals.id.indexOf('@') + 1) + FUATURE_MAIL[i];
									rtn.results.push({
										id: data.totals.type + "|" + sm,
										text: ICONS[data.totals.type] + sm,
										title: "[" + data.totals.type_name + "]" + sm,
										data: $.extend(true, {}, data.totals, {id: sm, name: sm})
									});
								}
							}
						} else {
							for (var i in FUATURE_MAIL) {
								var sm = data.totals.id + "@" + FUATURE_MAIL[i];
								rtn.results.push({
									id: data.totals.type + "|" + sm,
									text: ICONS[data.totals.type] + sm,
									title: "[" + data.totals.type_name + "]" + sm,
									data: $.extend(true, {}, data.totals, {id: sm, name: sm})
								});
							}
						}
					}
				    return rtn;
				},
				transport: function (params, success, failure) {
				    return $.call("application.contacter.queryMine", params.data, success, failure, {nomask: true});
				}
			}
		});
	}
	
	function getMailData() {
		var data = {subject: $("input[name='subject']").val(),content: $(".email-content").html()};
		if ("${id}") {
			data["id"] = "${id}";
		}
		data["email_folder_id"] = "${param.email_folder_id}" || 2;
		data.to = $("select[name='to']").val();
		data.cc = $("select[name='cc']").val();
		var to = [];
		for (var i in data.to) {
			var _to = data.to[i].split("|");
			to.push({
				type: "to",
				target: _to[0],
				send_to: _to[1]
			});
		}
		for (var i in data.cc) {
			var _to = data.cc[i].split("|");
			to.push({
				type: "cc",
				target: _to[0],
				send_to: _to[1]
			});
		}
		delete data.to;
		delete data.cc;
		data.send_to = $.toJSON(to);
		return data;
	}
	function send() {
		$.call("application.email.send", getMailData(), function(rtn) {
			$.close();
		});
	}
</script>
</body>
</html>