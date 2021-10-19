<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" ismodal="true">
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
<div class="col-md-4">
	<s:row>
		<s:table label="已授权人员" action="setting.role.queryPersonByRole" id="dtable" height="400" autoload="false">
			<s:toolbar>
				<%--<s:button label="查询" onclick=""></s:button>--%>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="no" label="工号" width="60"></s:table.field>
				<s:table.field name="system_user_name" label="姓名" width="60"></s:table.field>
				<s:table.field name="oper" datatype="template" label="操作" align="left" >
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="deleterole('$[system_user_id]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
	<s:row>
	<s:form label="选择人员" id="pers">
		<s:row>
			<div crid="" class="cols4 nolabel">
				<div class="control-content dblclick-open" id="selectdiv">
					<button class="open-icon" type="button" onclick="showusers();"><i class="icon-user-follow"></i></button>
					<select class="form-control emailto" name="chry" multiple="multiple">
						<c:if test="${not empty chry }">
							<c:forEach items="${chry}" var="t" varStatus="i">
								<option value="${t.target}|${t.send_to}" selected="selected">${t.cc_name}</option>
							</c:forEach>
						</c:if>
					</select>
				</div>
			</div>
		</s:row>
	</s:form>
	</s:row>
</div>
<div class="col-md-8">
	<iframe src="" id ="add_" width="99%" border="0" height="500">
	</iframe>
</div>
</s:page>
<script>
$(document).ready(function() {
	query();
	query1();
	initselect2();
});
function query1(){
	$("#dtable").params({"system_role_id" :'${pageParam.system_role_id}'});
	$("#dtable").refresh();
}
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
		    		type: ['U'],
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
function showusers(){
	var data = $("#pers").getData();
	var contacter_ids = [];
	for (var i in data.chry) {
		var _to = data.chry[i].split("|");
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
				ht.push('<select class="form-control emailto" name="chry" multiple="multiple">');
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
function query(){
	$("#add_").attr("src","setdeps.html?system_role_id=${pageParam.system_role_id}");
}
function deleterole(id){
	var d = $("#myform").getData();
	$.confirm("删除后无法恢复，是否继续！" ,function (rtn){
		if(rtn){
			$.call("setting.role.deleteRule", {system_user_id: id, system_role_id: '${pageParam.system_role_id}'}, function(rtn) {
				if (rtn) {
					$("#dtable").refresh();
				}
			},null,{async: false});
		}
	});
}
</script>
