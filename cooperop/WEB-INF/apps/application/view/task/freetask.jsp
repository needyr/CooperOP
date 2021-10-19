<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="个人配合单" dispermission="true">
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
		<s:form id="conditions" label="个人配合单">
			<s:toolbar>
				<s:button label="暂存" icon="fa fa-save" onclick="save();"></s:button>
				<s:button label="提交流程" icon="fa fa-share-square-o" onclick="start();"></s:button>
			</s:toolbar>
			<s:row>
				<input type="hidden" name="id" value="${id }"/>
				<s:textfield label="主题" name="subject" cols="3" required="true">${subject}</s:textfield>
				<s:textfield label="单据号" name="djbh" cols="1" value="${djbh}" placeholder="提交后自动生成" readonly="true"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="起草人" islabel="true" value="${user.name}(${user.baseDepName})"></s:textfield>
				<s:textfield label="所属岗位" islabel="true" value="${user.roleNames}"></s:textfield>
				<s:textfield label="起草时间" islabel="true" value="${now.datetime}"></s:textfield>
			</s:row>
			<s:row>
				<s:autocomplete label="转交" cols="4" name="operator" action="application.contacter.queryMine" params="{&#34;type&#34;:&#34;U&#34;}" required="true" value="${operator}" text="${operator_name}${empty operator ? '' : '('}${operator_department_name}${empty operator ? '' : ')'}">
					<s:option label="$[name]" value="$[id]">$[name]</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<div crid="" class="cols4">
					<label class="control-label">抄送</label>
					<div class="control-content">
						<div class="control-content dblclick-open" id="selectdiv">
						<button class="open-icon" type="button" onclick="showusers();"><i class="icon-user-follow"></i></button>
						<select class="form-control emailto" name="cc" multiple="multiple">
							<c:if test="${not empty cc }">
								<c:set var="tn" value="${fn:split(cc_name, ',')}"></c:set>
								<c:set var="td" value="${fn:split(cc_department_name, ',')}"></c:set>
								<c:forEach items="${fn:split(cc, ',')}" var="t" varStatus="i">
									<option value="U|${t}" selected="selected">${tn[i.index] }(${td[i.index] })</option>
								</c:forEach>
							</c:if>
						</select>
						</div>
					</div>
				</div>
			</s:row>
			<s:row>
				<s:richeditor label="内容" name="content" cols="4" required="true" toolbar="full" height="300">${content}</s:richeditor>
			</s:row>
			<s:row>
				<s:file label="附件" name="attach_files" cols="4" value="${attach_files }" maxlength="10"></s:file>
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
		$(".emailto").select2({
			placeholder: "输入中文或拼音首字母缩写选择。" ,
			tags: false ,
			tokenSeparators: [';', ' '],
			language: "zh-CN",
			allowClear: false ,
			templateResult: function(data, container) {
				if (!data.data) {
					return undefined;
				}
				return ICONS["U"] + data.data.name;
			},
			templateSelection: function(data) {
				if (!data.data && data.id) {
					return ICONS["U"] + data.text;
				}
				return ICONS["U"] + data.data.name;
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
				      type: ["U"],
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
				    return rtn;
				},
				transport: function (params, success, failure) {
				    return $.call("application.contacter.queryMine", params.data, success, failure, {nomask: true});
				}
			}
		});
	}
	function save() {
		var data = $("#conditions").getData();
		var cc = [];
		for (var i in data.cc) {
			var _to = data.cc[i].split("|");
			cc.push(_to[1]);
		}
		data.cc = cc.join(",");
		$.call("application.task.saveFreeTask", data, function(rtn) {
			$.message("保存个人配合单成功！", function() {
				$.closeModal(true);
			});
		});
	}
	function start() {
		if (!$("form").valid()) {
			return false;
		}
		var data = $("#conditions").getData();
		var cc = [];
		for (var i in data.cc) {
			var _to = data.cc[i].split("|");
			cc.push(_to[1]);
		}
		data.cc = cc.join(",");
		$.call("application.task.startFreeTask", data, function(rtn) {
			$.message("提交个人配合单成功！", function() {
				$.closeModal(true);
			});
		});
	}
	function showusers(){
		var data = $("#conditions").getData();
		var contacter_ids = [];
		for (var i in data.cc) {
			var _to = data.cc[i].split("|");
			contacter_ids.push({type: _to[0],id: _to[1]});
		}
		var d = {};
		var url = cooperopcontextpath + "/w/application.contacter.select.html";
		$.modal(url, "选择", {
			contacter_ids : $.toJSON(contacter_ids),
			width : '80%',
			height : '80%',
			callback : function(rtn) {
				if(rtn){
					var d = rtn.data;
					var ht = [];
					ht.push('<button class="open-icon" type="button" onclick="showusers();"><i class="icon-user-follow"></i></button>');
					ht.push('<select class="form-control emailto" name="cc" multiple="multiple">');
					for(var i in d){
						ht.push('<option value="'+d[i].type+'|'+d[i].id+'" selected="selected">'+d[i].name+'</option>');
					}
					ht.push('</select>');
					$("#selectdiv").html(ht.join(''));
					initselect2();
				}
		    }
		});
	}
</script>