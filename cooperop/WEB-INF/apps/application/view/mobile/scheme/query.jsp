<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<style type="text/css">
	.cols4 .quanxuan{
		float: left;
	    color: #4bc464;
	    line-height: 30px;
	    padding: 0 10px;
	    font-size: 16px;
		
	} 
	.cols4 .liebiao{
		display:block;
		/* text-align:center; */
		font-size: 18px;
    	line-height: 32px;
		
	} 
	.cols4 .queding{
		float:right;
		color: #4bc464;
	    line-height: 30px;
	    padding: 0 10px;
	    font-size: 16px;
	} 
	
	.gd{
		position: fixed !important;
	    width: 100%;
	    z-index: 999;
	    background-color: #f9f9f9;
	    border-bottom: 1px solid #eee;
	}
	
	.main-massage .dataTables_wrapper .table-scrollable table.dataTable {
    margin-top: 5px !important;
    
}
table[select='single']>tbody>tr>td:first-child{
	display:none;
} 

</style>
<s:page title="测试" disloggedin="true" dispermission="true" ismodal="true">
	<s:row>
		<div class="cols4 gd">
			<span class="queding" onclick="confirm();">确定</span>
			<span class="liebiao">选择列表</span>
		</div>
	</s:row>
	<s:row style="margin-top:2em;"></s:row>
	<s:row style="${not empty fn:trim(scheme.filterflds) ? '' : 'display:none;'}">
		<s:form border="0" id="conditions">
			<s:row>
				<%-- <c:forEach items="${scheme.filterfields}" var="field">
					<s:textfield label="${field.chnname}" name="flt_${field.fdname}"></s:textfield>
				</c:forEach> --%>
				<s:textfield label="辅助查找" name="_filter"></s:textfield>
				<s:button label="查询" icon="fa fa-search" color="green" onclick="query()"></s:button>
				<c:forEach items="${pageParam}" var="p">
					<input type="hidden" name="${p.key}" value="${p.value}"/>
				</c:forEach>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<c:set var="displyflds" value=",${scheme.displyflds},"></c:set>
		<s:table app_table ="N" label="" id="optionlist" action="application.scheme.executeQueryScheme" 
		select="${(scheme.multisel eq '是')?'multi':'single' }" autoload="false" fitwidth="false" sort="true" onrefreshed="refreshtable">
			
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
			$("#optionlist").find("tbody").on("click","tr", function(){
				confirm();
			});
		}
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
			var data = {multisel: '${pageParam.multisel}', result: $("#optionlist").getSelected()};
			$.closeModal(data);
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