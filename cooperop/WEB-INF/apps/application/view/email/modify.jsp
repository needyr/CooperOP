<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="我的邮件-详情" dispermission="true">
<link href="${pageContext.request.contextPath }/theme/plugins/select2/css/select2.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath }/theme/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/theme/plugins/select2/i18n/zh-CN.js"></script>
<style type="text/css">
	.select2-container {
		width: 100%!important;
	}
	.select2-container--default .select2-selection--multiple {
	    font-size: 12px;
	    font-weight: normal;
	    color: #333;
	    background-color: #fff;
	    border: 1px solid #e5e5e5;
	    min-height: 30px;
    }
    .select2-container--default.select2-container--focus .select2-selection--multiple {
   	    border-color: #999;
    }
    
    .select2-container--default.select2-container--open .select2-selection--multiple {
   	    border-bottom-color: #e5e5e5;
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
    	height: 26px!important;
    	line-height: 26px;
    	padding: 0px!important;
    }
    
    .select2-container--default .select2-selection--multiple .select2-selection__choice {
   	    padding: 0px 3px 0px 5px;
	    margin: 2px;
	    border: 1px solid #e5e5e5;
	    background-image: none;
	    background-color: #fff;
	    filter: none;
	    -webkit-box-shadow: none !important;
	    box-shadow: none !important;
	    height: 26px;
	    line-height: 26px;
    }
    .select2-container--default .select2-selection--multiple .select2-selection__choice__remove {
    	float: right;
    	margin-left: 2px;
    	margin-right: 0px;
    }
    
    .select2-results {
    	margin: 0;
	}
</style>
	<s:row>
		<s:form id="conditions" label="新邮件">
			<s:toolbar>
				<s:button label="发送" icon="fa fa-send" onclick="send();"></s:button>
				<s:button label="保存" icon="fa fa-save" onclick="save();"></s:button>
			</s:toolbar>
			<s:row>
				<div crid="" class="cols4">
					<label class="control-label">收件人</label>
					<div class="control-content dblclick-open" id="selectdivto">
						<button class="open-icon" type="button" onclick="showusers('to');"><i class="icon-user-follow"></i></button>
						<select class="form-control emailto" name="to" multiple="multiple">
							<c:if test="${pageParam.type eq 'r' or pageParam.type eq 'ra'}">
								<option value="U|${send_user_id}" selected="selected">${send_user_name}</option>
							</c:if>
							<c:if test="${not empty to and empty pageParam.type}">
								<c:set var="tn" value="${fn:split(to_name, ';')}"></c:set>
								<c:forEach items="${fn:split(to, ';')}" var="t" varStatus="i">
									<option value="${t}" selected="selected">${tn[i.index] }</option>
								</c:forEach>
							</c:if>
						</select>
					</div>
				</div>
			</s:row>
			<s:row>
				<div crid="" class="cols4">
					<label class="control-label">抄送</label>
					<div class="control-content dblclick-open" id="selectdivcc">
						<button class="open-icon" type="button" onclick="showusers('cc');"><i class="icon-user-follow"></i></button>
						<select class="form-control emailto" name="cc" multiple="multiple">
							<c:if test="${not empty to and (empty pageParam.type or pageParam.type eq 'ra')}">
								<c:set var="tn" value="${fn:split(to_name, ';')}"></c:set>
								<c:set var="tm" value="U|${user.id}"></c:set>
								<c:forEach items="${fn:split(to, ';')}" var="t" varStatus="i">
									<c:if test="${t ne tm}">
										<option value="${t}" selected="selected">${tn[i.index] }</option>
									</c:if>
								</c:forEach>
							</c:if>
							<c:if test="${not empty cc and (empty pageParam.type or pageParam.type eq 'ra')}">
								<c:set var="tn" value="${fn:split(cc_name, ';')}"></c:set>
								<c:forEach items="${fn:split(cc, ';')}" var="t" varStatus="i">
									<option value="${t}" selected="selected">${tn[i.index] }</option>
								</c:forEach>
							</c:if>
						</select>
					</div>
				</div>
			</s:row>
			<s:row>
				<div crid="" class="cols4">
					<label class="control-label">密送</label>
					<div class="control-content dblclick-open" id="selectdivbcc">
						<button class="open-icon" type="button" onclick="showusers('bcc');"><i class="icon-user-follow"></i></button>
						<select class="form-control emailto" name="bcc" multiple="multiple">
							<c:if test="${not empty bcc and empty pageParam.type}">
								<c:set var="tn" value="${fn:split(bcc_name, ';')}"></c:set>
								<c:forEach items="${fn:split(bcc, ';')}" var="t" varStatus="i">
									<option value="${t}" selected="selected">${tn[i.index] }</option>
								</c:forEach>
							</c:if>
						</select>
					</div>
				</div>
			</s:row>
			<s:row>
				<s:textfield cols="4" label="主题" name="subject" required="true" value="${pageParam.type eq 's' ? '转发：' : (pageParam.type eq 'ra' or pageParam.type eq 'r' ? '回复：' : '') }${subject}"></s:textfield>
			</s:row>
			<s:row>
				<c:choose>
					<c:when test="${pageParam.type eq 'r' or pageParam.type eq 'ra' or pageParam.type eq 's'}">
						<s:richeditor cols="4" name="content" height="200">
							<br/>
							<hr/>
							<blockquote>
								<p><b>发件人：</b>${send_user_name}</p>
								<p><b>发送时间：</b><fmt:formatDate value="${send_time}" pattern="yyyy年MM月dd日 HH:mm"/></p>
								<p><b>收件人：</b>${to_name}</p>
								<c:if test="${not empty cc_name}">
									<p><b>抄送：</b>${cc_name}</p>
								</c:if>
								<p><b>主题：</b>${subject}</p>
								<br/>
								${content}
							</blockquote>
						</s:richeditor>
					</c:when>
					<c:otherwise>
						<s:richeditor cols="4" name="content" height="200">${content}</s:richeditor>
					</c:otherwise>
				</c:choose>
			</s:row>
			<s:row>
				<s:file cols="4" label="附件" name="attach_files" value="${attach_files}" maxlength="10"></s:file>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	$(document).ready(function() {
		initselect2();
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
		var data = $("#conditions").getData();
		if ("${id}") {
			data["id"] = "${id}";
		}
		data["email_folder_id"] = "${param.email_folder_id}" || 2;
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
		for (var i in data.bcc) {
			var _to = data.bcc[i].split("|");
			to.push({
				type: "bcc",
				target: _to[0],
				send_to: _to[1]
			});
		}
		delete data.to;
		delete data.cc;
		delete data.bcc;
		data.send_to = $.toJSON(to);
		return data;
	}
	
	function send() {
		if (!$("form").valid()) {
			return false;	
		}
		$.call("application.email.send", getMailData(), function(rtn) {
			top.$(".page-content-tabs").close_tabwindow();
		});
	}
	
	function save() {
		if (!$("form").valid()) {
			return false;	
		}
		$.call("application.email.save", getMailData(), function(rtn) {
			top.$(".page-content-tabs").close_tabwindow();
		});
	}
	function showusers(em){
		var data = $("#conditions").getData();
		var contacter_ids = [];
		for (var i in data[em]) {
			var _to = data[em][i].split("|");
			contacter_ids.push({type: _to[0],id: _to[1]});
		}
		var url = cooperopcontextpath + "/w/application.contacter.select.html";
		$.modal(url, "选择", {
			width : '80%',
			height : '80%',
			contacter_ids : $.toJSON(contacter_ids),
			callback : function(rtn) {
				if(rtn){
					var d = rtn.data;
					var ht = [];
					ht.push('<button class="open-icon" type="button" onclick="showusers(\''+em+'\');"><i class="icon-user-follow"></i></button>');
					ht.push('<select class="form-control emailto" name="'+em+'" multiple="multiple">');
					for(var i in d){
						ht.push('<option value="'+d[i].type+'|'+d[i].id+'" selected="selected">'+d[i].name+'</option>');
					}
					ht.push('</select>');
					$("#selectdiv"+em).html(ht.join(''));
					initselect2();
				}
		    }
		});
	}
</script>
