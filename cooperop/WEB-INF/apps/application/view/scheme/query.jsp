<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="测试" disloggedin="true" dispermission="true" ismodal="true">
<style type="text/css">
	.new-fix{
	    position: fixed;
	    top: 0;
	    right: 0;
	    z-index: 999;
	    width: 100%;
	}
	.portlet>.portlet-title, .portlet.box>.portlet-title {
	    margin-top: 0px !important;
        background-color: #e2e2e2;
	}
</style>
	<s:row style="${not empty fn:trim(scheme.filterflds) ? '' : 'display:none;'}">
		<s:form border="0" id="conditions">
			<s:row>
				<%-- <c:forEach items="${scheme.filterfields}" var="field">
					<s:textfield label="${field.chnname}" name="flt_${field.fdname}"></s:textfield>
				</c:forEach> --%>
				<s:textfield label="辅助查找" name="_filter" cols="2"></s:textfield>
				<s:button label="查询" icon="fa fa-search" color="green" onclick="query()"></s:button>
				<c:forEach items="${pageParam}" var="p">
					<input type="hidden" name="${p.key}" value="${p.value}"/>
				</c:forEach>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<c:set var="displyflds" value=",${scheme.undispflds},"></c:set>
		<s:table label="选择列表" id="optionlist" action="application.scheme.executeQueryScheme" select="${(scheme.multisel eq '是')?'multi':'single' }" autoload="false" fitwidth="true" sort="true" onrefreshed="refreshtable">
			<s:toolbar>
				<s:button icon="fa fa-check" label="确定" onclick="confirm();"></s:button>
				<c:if test="${scheme.selectmx eq '是'}">
					<s:button icon="fa fa-check" label="查看明细" onclick="showmx();"></s:button>
				</c:if>
				<s:button icon="fa fa-ban" label="取消" onclick="$.closeModal();"></s:button>
			</s:toolbar>
			<s:table.fields>
				<c:forEach items="${fields}" var="field">
					<c:set var="hide" value="false"></c:set>
					<c:set var="appf" value="-1"></c:set>
					<c:set var="fname" value=",${field.fdname},"></c:set>
					<c:set var="ffname" value="${field.fdname}"></c:set>
					<c:if test="${fn:indexOf(displyflds, fname) gt -1}">
						<c:set var="hide" value="true"></c:set>
					</c:if>
					<c:forEach items="${appflds }" var="pp" varStatus="b">
						<c:if test="${fn:toLowerCase(pp) eq ffname }">
							<c:set var="appf" value="${b.index }"></c:set>
						</c:if>
					</c:forEach>
					<s:table.field name="${field.fdname}" label="${field.chnname}" 
					width="${field.nouse eq 0 ? field.fdsize * 10 : field.nouse * 10 }" sort="true" hidden="${hide}"
					 app_field="${appf eq 0?'title_field': appf eq 1?'pre_title_field':appf eq 2?'content_field':appf eq 3?'state_field':'' }"></s:table.field>
				</c:forEach>
			</s:table.fields>
		</s:table>
	</s:row>
	<div style="display:none;" id="mxrow">
	<c:if test="${scheme.selectmx eq '是'}">
		<s:row>
			<s:table id="optionmxlist" action="application.scheme.queryDjSelectSchememx" select="multi" autoload="false" fitwidth="false" sort="true" onrefreshed="refreshtable">
				<s:toolbar>
					<s:button icon="fa fa-check" label="选择" onclick="confirm1();"></s:button>
				</s:toolbar>
				<s:table.fields>
				<c:forEach items="${mxfields}" var="field">
					<s:table.field name="${field.fdname}" label="${field.chnname}" width="${field.nouse eq 0 ? field.fdsize * 10 : field.nouse * 10 }" sort="true"></s:table.field>
				</c:forEach>
			</s:table.fields>
			</s:table>
		</s:row>
	</c:if>
	</div>
</s:page>
<script type="text/javascript">
	$(document).ready(function() {
		if (+"${count}" == 0) {
			$.warning("没有满足条件的记录！", function() {
				$.closeModal(false);
			});
		} else {
			query();
		}
		if($("#optionlist").attr("select") == "single"){
			$("#optionlist").find("tbody").on("dblclick","tr", function(){
				confirm();
			});
		}
		
		var tt = $("#optionlist_wrapper").parent().parent().find(".portlet-title").offset().top;
		  $(window).scroll(function(){  
		        //下面这句主要是获取网页的总高度，主要是考虑兼容性所以把Ie支持的documentElement也写了，这个方法至少支持IE8  --1470
		        var htmlHeight=document.body.scrollHeight||document.documentElement.scrollHeight;  
		        //clientHeight是网页在浏览器中的可视高度，  
		        var clientHeight=document.body.clientHeight||document.documentElement.clientHeight;  
		        //scrollTop是浏览器滚动条的top位置，  
		        var scrollTop=document.body.scrollTop||document.documentElement.scrollTop;  
		        //通过判断滚动条的top位置与可视网页之和与整个网页的高度是否相等来决定是否加载内容； 
		        if(scrollTop > tt){
		            $("#optionlist_wrapper").parent().parent().find(".portlet-title").addClass("new-fix");
		        }else{
		        	$("#optionlist_wrapper").parent().parent().find(".portlet-title").removeClass("new-fix");
		        }
		    })  

	});
	function query() {
		$("#optionlist").params($("#conditions").getData());
		$("#optionlist").refresh();
	}
	function showmx(){
		$("#mxrow").show();
		var d = $("#conditions").getData();
		var dd =  $("#optionlist").getSelected();
		d.djbh = dd[0].data.djbh;
		$("#optionmxlist").params(d);
		$("#optionmxlist").refresh();
	}
	function confirm() {
		if ($("#optionlist").getSelected().length == 0) {
			$.warning("请选择您所需要的选项。");
			return;
		}
		if('${pageParam.schemetype}' == 'dj_select_'){
			var d = $("#optionlist").getSelected();
			var data={flag: '${pageParam.flag}',system_product_code: '${pageParam.system_product_code}',gzid: '${pageParam.gzid}',
					fangabh : '${pageParam.fangabh}', fangalx: '${pageParam.fangalx}',tableid: '${pageParam.tableid}'};
			$.extend(true, data, d[0].data);
			$.call("application.scheme.executeDjSelectScheme",{"data":$.toJSON(data)},function(rtn){
				$.closeModal(rtn);
			});
		}else{
			$.closeModal($("#optionlist").getSelected());
		}
	}
	function confirm1(){
		if ($("#optionmxlist").getSelected().length == 0) {
			$.warning("请选择您所需要的明细选项。");
			return;
		}
		if('${pageParam.schemetype}' == 'dj_select_'){
			var d = $("#optionmxlist").getSelected();
			var data={flag: '${pageParam.flag}',system_product_code: '${pageParam.system_product_code}',gzid: '${pageParam.gzid}',
					fangabh : '${pageParam.fangabh}', fangalx: '${pageParam.fangalx}',tableid: '${pageParam.tableid}'};
			data.tr = d;
			$.extend(true, data, $("#optionlist").getSelected()[0].data);
			$.call("application.scheme.executeDjSelectSchememx",{"data":$.toJSON(data)},function(rtn){
				$.closeModal(rtn);
			});
		}
	}
	/* function refreshtable(rs) {
		if (+"${count}" == 1 && "${scheme.retu_one}" == "是" && rs.resultset.length == 1) {
			var d = {
				id: "_t1_row_1",
				data: rs.resultset[0],
				element: null
			}
		}
	} */
</script>