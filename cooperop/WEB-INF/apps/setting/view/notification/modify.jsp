<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="通知公告编辑" ismodal="true">
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
		<s:form id="myform" label="通知公告">
			<s:toolbar>
				<s:button icon="fa fa-save" label="保存" onclick="save()"></s:button>
				<s:button icon="fa fa-ban" label="取消" onclick="cancel()"></s:button>
			</s:toolbar>
			<s:row>
				<input type="hidden" name="id" value="${id }"/>
				<s:select label="类别" name="notification_type" required="true" value="${notification_type }" dictionary="notification_type" cold="1">
					<s:option label="" value=""></s:option>
				</s:select>
				<s:textfield label="主题" cols="2" name="subject" required="true"
					>${subject}</s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="作者" name="author"
					value="${empty author ? user.baseDepName : author}"></s:textfield>
				<s:switch label="发布状态" name="published" value="${empty published ? 1 : published}" onvalue="1" ontext="发布" offvalue="0" offtext="不发布"></s:switch>
			</s:row>
			<s:row>
				<fmt:formatDate value="${pdate}" var="tmp" pattern="yyyy-MM-dd"/>
				<s:datefield label="发布日期" name="pdate_date" value="${empty tmp ? now.date : tmp}"></s:datefield>
				<fmt:formatDate value="${pdate}" var="tmp" pattern="HH:mm"/>
				<s:timefield label="发布时间" name="pdate_time" value="${empty tmp ? now.time : tmp}" format="HH:mm"></s:timefield>
				<fmt:formatDate value="${edate}" var="tmp" pattern="yyyy-MM-dd"/>
				<s:datefield label="结束日期" name="edate_date" value="${tmp}"></s:datefield>
				<fmt:formatDate value="${edate}" var="tmp" pattern="HH:mm"/>
				<s:timefield label="结束时间" name="edate_time" value="${tmp}" format="HH:mm"></s:timefield>
			</s:row>
			<s:row>
				<s:richeditor cols="4" label="内容" name="content" height="200" toolbar="full">${content}</s:richeditor>
			</s:row>
			<s:row>
				<s:file cols="4" label="附件" name="attach_files" value="${attach_files}" maxlength="30"></s:file>
			</s:row>
			<s:row >
				<div crid="" class="cols4">
					<label class="control-label">通知人员</label>
					<div class="control-content dblclick-open" id="selectdiv">
						<button class="open-icon" type="button" onclick="showusers();"><i class="icon-user-follow"></i></button>
						<select class="form-control emailto" name="send_to" multiple="multiple">
							<c:if test="${not empty sendto }">
								<c:forEach items="${sendto}" var="t" varStatus="i">
									<option value="${t.target}|${t.send_to}" selected="selected">${t.cc_name}</option>
								</c:forEach>
							</c:if>
						</select>
					</div>
				</div>
				<%-- <s:textfield label="目标" name="can_download" value="1"></s:textfield> --%>
			</s:row>
			<s:row>
				<s:image cols="3" label="背景图" name="header_image" value="${header_image}" maxlength="1"></s:image>
				<div class="cols1 nolabel">
					<div class="note note-warning">
						用于替换首页显示通知公告时的背景图，不上传表示使用默认的背景图。
					</div>
				</div>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
$(document).ready(function(){
	initselect2();
})
function initselect2(){
	var ICONS = {
			"A": "<i class='fa fa-home'></i>&nbsp;",
			"D": "<i class='fa fa-sitemap'></i>&nbsp;",
			"G": "<i class='fa fa-users'></i>&nbsp;",
			"U": "<i class='fa fa-user'></i>&nbsp;",
			"E": "<i class='fa fa-envelope-o'></i>&nbsp;"
	};
	$(".emailto").select2({
		placeholder: "输入中文或拼音首字母缩写选择内部通讯录或输入正确的外部邮箱地址。" ,
		tags: false ,
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
			    return rtn;
			},
			transport: function (params, success, failure) {
			    return $.call("application.contacter.queryMine", params.data, success, failure, {nomask: true});
			}
		}
	});
}
function save() {
	if (!$("form").valid()) {
		return false;	
	}
	var data = $("#myform").getData();
	data.pdate = $.trim([data.pdate_date, data.pdate_time].join(" "));
	delete data.pdate_date;
	delete data.pdate_time;
	data.edate = $.trim([data.edate_date, data.edate_time].join(" "));
	delete data.edate_date;
	delete data.edate_time;
	data.sendto = [];
	for (var i in data.send_to) {
		var _to = data.send_to[i].split("|");
		data.sendto.push({
			target: _to[0],
			send_to: _to[1]
		});
	}
	delete data.send_to;
	var page = "setting.notification.update";
	if ("${id}" == "") {
		page = "setting.notification.insert";
	}
 	$.call(page, {jsondata: $.toJSON(data)}, function(rtn) {
		$.success("保存成功！", function() {
			$.closeModal(true);
		});
	},null);
}
function cancel() {
	$.closeModal(false);
}
function showusers(){
	var data = $("#myform").getData();
	var contacter_ids = [];
	for (var i in data.send_to) {
		var _to = data.send_to[i].split("|");
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
				ht.push('<select class="form-control emailto" name="send_to" multiple="multiple">');
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
