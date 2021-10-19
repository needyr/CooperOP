<%@page import="cn.crtech.cooperop.application.action.TaskAction"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%
	String s = request.getParameter("caozuo");
	String task_id = request.getParameter("task_id");
	pageContext.setAttribute("caozuo", s);
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("task_id",task_id);
	pageContext.setAttribute("routes", TaskAction.listTaskRoutes(map));
%>
<div class="page-container">
	<div>
		<div class="">
			<div type="row" crid="" style="height: px;" class="row-fluid ">
				<div crid="" class="cols4">
					<div class="portlet box blue-hoki purple"
						style="border-width: 1px; height: px;">
						<div class="portlet-title">
							<div class="caption">审核操作区域</div>
							<div crid="" ctype="toolbar" class="tools" cinited="cinited">
								<button crid="" action="" class="btn btn-sm btn-sm btn-default "
									ctype="button" t="1" type="" onclick="approval1();" id="approval"
									cinited="cinited">通过</button>
								<button crid="" action="" class="btn btn-sm btn-sm btn-default "
									ctype="button" t="1" type="" onclick="reject1()" id="reject"
									cinited="cinited">驳回</button>
								<button crid="" action="" class="btn btn-sm btn-sm btn-default "
									ctype="button" t="1" type="" onclick="back1()" id="back"
									cinited="cinited">驳回上一步</button>
								<button crid="" action="" class="btn btn-sm btn-sm btn-default "
									ctype="button" t="1" type="" onclick="reaudit1()" id="reaudit"
									cinited="cinited">重审</button>
								<button crid="" action="" class="btn btn-sm btn-sm btn-default "
									ctype="button" t="1" type="" onclick="save1()" id="save"
									cinited="cinited">暂定</button>
							</div>
						</div>
						<div ctype="form" class="form-horizontal" color="purple"
							cinited="cinited">
							<div type="row" crid="" style="height: px;" class="row-fluid ">
								<c:if test="${not empty routes }">
									<div crid="" class="cols1">
										<label class="control-label">下一节点</label>
										<div class="control-content">
											<select name="nextnode" class="form-control " ctype="select" required="required" islabel="true" isherf="false"
											 	label="下一节点" encryption="false" cols="1" cinited="cinited" aria-required="true" 
												aria-invalid="false">
											 		<c:forEach items="${routes }" var="r">
												 		<option value="${r.tonode }">${r.target_node.name }</option>
											 		</c:forEach>
											</select>
										</div>
									</div>
									<%-- <div crid="" class="cols1">
										<label class="control-label">节点处理人</label>
										<div class="control-content">
											<select name="nextnode" class="form-control " ctype="select" required="required" islabel="true" isherf="false"
											 	label="节点处理人" encryption="false" cols="1" cinited="cinited" aria-required="true" 
												aria-invalid="false">
											 		<c:forEach items="${routes }" var="r">
												 		<option value="${r.tonode }">${r.target_node.name }</option>
											 		</c:forEach>
											</select>
										</div>
									</div> --%>
								</c:if>
								<div crid="" class="cols4">
									<label class="control-label">审核意见</label>
									<div class="control-content">
										<textarea
											style="overflow-y: hidden; resize: horizontal; height: 48px;"
											ctype="textarea" class="form-control" type="textarea"
											islabel="false" isherf="false" label="审核意见"
											encryption="false" htmlescape="false" name="advice" cols="4"
											autosize="true" data-autosize-on="true" cinited="cinited"></textarea>
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
		var caozuo = '${caozuo}';
		var c = caozuo.replace("[","").replace("]","").replace(" ","");
		var s = c.split(",");
		var reject;
		var back;
		var reaudit;
		var save;
		for(var i=0;i<s.length;i++){
			if(s[i] == "1"){
				reject = "1";
			}else if(s[i] == "2"){
				back = "2";
			}else if(s[i] == "3"){
				reaudit = "3";
			}else if(s[i] == "4"){
				save = "4";
			}
		}
		
		if(!reject){
			$("#reject").remove();
		}
		if(!back){
			$("#back").remove();
		}
		if(!reaudit){
			$("#reaudit").remove();
		}
		if(!save){
			$("#save").remove();
		}
	});
	function getd(){
		var d = {
				"gzid":$("[name='gzid']").val(),
				"djbh":$("[name='djbh']").val(),
				"clientid":$("[name='clientid']").val(),
				"djbs":$("[name='djbs']").val(),
				"djlx":$("[name='djlx']").val(),
				"rq":$("[name='rq']").val(),
				"kaiprq":$("[name='kaiprq']").val(),
				"riqi":$("[name='riqi']").val(),
				"ontime":$("[name='ontime']").val(),
				"gzid":$("[name='gzid']").val(),
				"pageid":$("[name='pageid']").val(),
				"jigid":$("[name='jigid']").val()
		};
		$(document).find("[ctype='form'][cinited]").each(function(){
			$.extend(true , d , $(this).getData());
		});
		delete d.chry;
		delete d.hchry1;
		delete d.qx;
		d.tables = [];
		$(document).find("[ctype='table'][cinited]:not('#querySuggestions')").each(function(index,tobj){
			var table = {"tableid":$(tobj).attr("tableid")};
			table.tr = [];
			var fields = $(tobj).data("_tc");
			$(tobj).find("tbody>tr").each(function(){
				var data = $.extend(true,{},$(tobj).getData($(this).attr("id")),$(this).getData());
				var tr = {};
				for(var i in fields){
					tr[fields[i].name]= data[fields[i].name];
				}
				
				table.tr.push(tr);
			})
			d.tables.push(table);
		});
		return d;
	}
	function approval1() {
		if (!$("form").valid()) {
			return false;	
		}
		$.confirm("是否确认通过？", function(r) {
			if (r) {
				var d = getd();
				d.task_id = $("[name='task_id']").val();
				d.adivce = $("[name='adivce']").val();
				d.audited = "Y";
				delete d.hchry1;
				delete d.qx;
				delete d.chry;
				delete d.message;
				$.call("application.bill.approval",{"data":$.toJSON(d)},function(rtn){
					//if(rtn){
					//	$.message("操作成功！");
						$.closeModal(true);
					//}
				});
			}
		});
	}

	function reject1() {
		$.confirm("是否确认驳回？", function(r) {
			if (r) {
				var d = getd();
				d.task_id = $("[name='task_id']").val();
				d.adivce = $("[name='adivce']").val();
				d.audited = "N";
				$.call("application.bill.approval",{"data":$.toJSON(d)},function(rtn){
					if(rtn){
						$.message("操作成功！");
						$.closeModal(true);
					}
				});
			}
		});
	}

	function back1() {
		$.confirm("是否确认驳回上一步？", function(r) {
			if (r) {
				var d = getd();
				d.task_id = $("[name='task_id']").val();
				d.adivce = $("[name='adivce']").val();
				d.audited = "NL";
				$.call("application.bill.approvalnotsave",{"data":$.toJSON(d)},function(rtn){
					if(rtn){
						$.closeModal(true);
					}
				});
			}
		});
	}

	function reaudit1() {

	}

	function save1() {

	}

	
</script>