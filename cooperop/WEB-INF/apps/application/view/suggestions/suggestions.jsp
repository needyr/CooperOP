<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="cn.crtech.cooperop.application.action.SuggestionsAction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<%
	Map<String , Object> map = new HashMap<String, Object>();
	map.put("suggestions_id", request.getParameter("suggestions_id"));
	map.put("order_id", request.getParameter("order_id"));
	map.put("djbh", request.getParameter("djbh"));
	pageContext.setAttribute("djbh", request.getParameter("djbh"));
	pageContext.setAttribute("task_id", request.getParameter("task_id"));
	pageContext.setAttribute("order_id", map.get("order_id"));
	pageContext.setAttribute("suggestions_id", map.get("suggestions_id"));
	pageContext.setAttribute("sug", new SuggestionsAction().suggestions(map));
	pageContext.setAttribute("histcs", new SuggestionsAction().querySuggestions(map));
%>
	<div class="page-container">
<div>
<div class="page-content" style="min-height:320px">
<script src="/theme/scripts/controls/input/checkbox.js" type="text/javascript"></script>
<script src="/theme/scripts/controls/controls.js" type="text/javascript"></script>
<link href="/theme/plugins/select2/css/select2.min.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="/theme/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="/theme/plugins/select2/i18n/zh-CN.js"></script>
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
	
<div type="row" crid="" style="height:px;" class="row-fluid ">

		
<div crid="" class="cols4">
<div class="portlet box blue-hoki" style="border-width: 1px;">
<div class="portlet-title">
<div class="caption">历史</div>
</div>
<div class="portlet-body">
<div id="querySuggestions_wrapper" class="dataTables_wrapper no-footer"><div class="table-scrollable"><div class="dataTables_scroll"><div class="dataTables_scrollHead" style="overflow: hidden; position: relative; border: 0px; width: 100%;"><div class="dataTables_scrollHeadInner" style="box-sizing: content-box; width: 1056px; padding-right: 0px;"><table cooperoptype="datatable" class="table table-striped table-bordered table-hover table-nowrap dataTable no-footer" fitwidth="true" sort="false" limit="25" autoload="false" action="application.suggestions.querySuggestions" cols="4" role="grid" style="margin-left: 0px; width: 1056px;margin-bottom: 0px !important;"><thead>
<tr role="row">

</tr>
<tr role="row"><th class="sorting_disabled" rowspan="1" colspan="1" style="width: 200px;">节点</th><th class="sorting_disabled" rowspan="1" colspan="1" style="width: 160px;">时间</th><th class="sorting_disabled" rowspan="1" colspan="1" style="width: 252px;">消息</th><th class="sorting_disabled" rowspan="1" colspan="1" style="width: 415px;">抄送人员</th></tr></thead></table></div></div><div class="dataTables_scrollBody" style="overflow: auto; width: 100%;"><table ctype="table" cooperoptype="datatable" class="table table-striped table-bordered table-hover table-nowrap dataTable no-footer" fitwidth="true" sort="false" limit="25" autoload="false" action="application.suggestions.querySuggestions" id="querySuggestions" cols="4" role="grid" aria-describedby="querySuggestions_info" ctid="_t1_" cinited="cinited" style="width: 1056px;"><thead>
<tr role="row" style="height: 0px;">

</tr>
<tr role="row" style="height: 0px;"><th class="sorting_disabled" rowspan="1" colspan="1" style="width: 200px; padding-top: 0px; padding-bottom: 0px; border-top-width: 0px; border-bottom-width: 0px; height: 0px;"><div class="dataTables_sizing" style="height:0;overflow:hidden;">节点</div></th><th class="sorting_disabled" rowspan="1" colspan="1" style="width: 160px; padding-top: 0px; padding-bottom: 0px; border-top-width: 0px; border-bottom-width: 0px; height: 0px;"><div class="dataTables_sizing" style="height:0;overflow:hidden;">时间</div></th><th class="sorting_disabled" rowspan="1" colspan="1" style="width: 252px; padding-top: 0px; padding-bottom: 0px; border-top-width: 0px; border-bottom-width: 0px; height: 0px;"><div class="dataTables_sizing" style="height:0;overflow:hidden;">消息</div></th><th class="sorting_disabled" rowspan="1" colspan="1" style="width: 415px; padding-top: 0px; padding-bottom: 0px; border-top-width: 0px; border-bottom-width: 0px; height: 0px;"><div class="dataTables_sizing" style="height:0;overflow:hidden;">抄送人员</div></th></tr></thead>

<tbody>
<c:forEach items="${histcs }" var="c">
	<tr role="row" class="odd">
	<td>${c.creator_name }</td><td>${c.create_time }</td><td>${c.message }</td><td>${c.csry }</td></tr>
</c:forEach>

</tbody>
</table></div></div></div></div>
</div>
</div>
</div>

	
</div>

	
<div type="row" crid="" style="height:px;" class="row-fluid ">

		
<div crid="" class="cols4">
<div class="portlet box blue-hoki " style="border-width: 1px;height: px;">
<div class="portlet-title">
<div class="caption">补充意见</div>
<div crid="" ctype="toolbar" class="tools" cinited="cinited">

				
<button crid="" action="" class="btn btn-sm btn-sm btn-default " ctype="button" t="1" type="" onclick="submitsuggestions();" cinited="cinited">提交补充意见
</button>

			
</div>
</div>
<div ctype="form" class="form-horizontal" color="" id="suggestions" cinited="cinited">
<div type="row" crid="" style="height:px;" class="row-fluid ">

				
			
</div>

<div type="row" crid="" style="height:px;" class="row-fluid ">
	<c:if test="${not empty sug.hchry }">
		<div crid="" class="cols3" id="cccc">
			<label class="control-label">历史抄送人员</label>
			<div class="control-content">
			<div value="" ctype="checkbox" class="checkbox-list" islabel="false" contentindex="0" contentvalue="0" encryption="false" cols="2" maxlength="16" 
			isherf="false" label="抄送人员" name="hchry1" clabel="未执行" fdtype="字符" autocomplete="off" >
				<c:forEach items="${sug.hchry }" var="c">
					<label class="checkbox-inline"><input value="${c.actor_id }" name="hchry1" ccinput="ccinput" type="checkbox">${c.actor_name}</label>
				</c:forEach>
			</div>
			</div>
		</div>
		<div crid="" class="cols3" id="cccc">
			<div class="control-content">
			<div value="" ctype="checkbox" class="checkbox-list" islabel="false" contentindex="0" contentvalue="0" encryption="false" cols="2" maxlength="16" 
			isherf="false" label="全选" name="qx" clabel="未执行" fdtype="字符" autocomplete="off" >
				<label class="checkbox-inline"><input value="${c.actor_id }" name="qx" ccinput="ccinput" type="checkbox" onchange="isqx();">全选</label>
			</div>
			</div>
		</div>
	</c:if>
</div>
<div type="row" crid="" style="height:px;" class="row-fluid ">
<div crid="" class="cols4">
					<label class="control-label">抄送人员</label>
					<div class="control-content dblclick-open" id="selectdiv">
						<button class="open-icon" type="button" onclick="showusers();"><i class="icon-user-follow"></i></button>
						<select class="form-control emailto" name="chry" multiple="multiple">
						</select>
					</div>
				</div>
</div>
<div type="row" crid="" style="height:px;" class="row-fluid ">

				
<div crid="" class="cols4">
<label class="control-label">补充意见</label>
<div class="control-content">
<textarea style="overflow-y: hidden; resize: horizontal; height: 48px;" ctype="textarea" class="form-control" type="textarea" islabel="false" isherf="false" label="补充意见" encryption="false" htmlescape="false" name="message" cols="4" autosize="true" data-autosize-on="true" cinited="cinited"></textarea>
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
<script type="text/javascript">
	$(document).ready(function() {
		initselect2();
	});
	function submitsuggestions(){
		var d = $("#suggestions").getData();
		if(!d.message){
			$.message("请填写意见！");
			return;
		}
		d.order_id = '${order_id}';
		d.task_id = '${task_id}';
		d.djbh = '${djbh}';
		d.hchry = [];
		var pageid = $("input[name='pageid']").val();
		if('${param.ptype}'=='free'){
			d.info_bill = 'application.task.freetaskaudit';
		}
		for (var i in d.chry) {
			var _to = d.chry[i].split("|");
			d.hchry.push({
				actor_id: _to[1]
			});
		}
		for(var i in d.hchry1){
			d.hchry.push({
				actor_id: d.hchry1[i]
			});
		}
		if(d.hchry.length==0){
			$.message("请选择抄送人员！");
			return;
		}
		delete d.chry;
		delete d.hchry1;
		delete d.qx;
		$.call("application.suggestions.save", {data : $.toJSON(d)}, function(rtn) {
			$.closeModal(true);
		},null,{async: false});
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
		var data = $("#suggestions").getData();
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
	function isqx(){
		var d = $("#suggestions").getData();
		if(d.qx.length > 0){
			$("[ctype='checkbox'][name='hchry1']").find("span").each(function(){
				var $t = $(this);
				if(!$t.hasClass("checked")){
					$t.addClass("checked");
					$t.find("input").attr("checked","checked");
				}
			});
		}
	}
</script>
