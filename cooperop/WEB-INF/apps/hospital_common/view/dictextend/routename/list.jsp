<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<style type="text/css">
.main {
    padding: 0px !important;
}
.bac{
	background-color: #f9f1d9 !important;
}
.dataTables_wrapper .table-scrollable .dataTables_scrollBody tbody tr .radio-list, .dataTables_wrapper .table-scrollable .dataTables_scrollBody tbody tr .checkbox-list {
    background-color: rgba(0,0,0,0) !important;
    border: 0px solid #e5e5e5 !important;
    text-align: center !important;
}
</style>
<s:page title="给药途径维护" >
	<s:row>
		<s:form id="form" extendable="true" collapsed="true">
			<s:row>
				<s:textfield label="关键字" name="filter" placeholder="请输入名称" cols="1"></s:textfield>
			</s:row>
			<s:row>
			<s:button label="全部查询" icon="fa fa-search" name="query" onclick="query_all()"></s:button>
			<s:button label="已维护查询" icon="fa fa-search" name="query" onclick="query_finish()"></s:button>
			<s:button label="未维护查询" icon="fa fa-search" name="query" onclick="query_not()"></s:button>
			<s:button label="导出" icon="fa fa-random" onclick="tableToExcel('datatable', '导出药品')"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" height="250" label="" autoload="false" action="hospital_common.dictextend.routename.query"  >
			<s:table.fields>
				<s:table.field name="is_wh" label="维护"  datatype="template">
					<s:checkbox name="is_wh" value="$[is_wh]" onchange="updateCb(this,'$[p_key]','is_wh')">
						<s:option value ="1" label=""></s:option>
					</s:checkbox>
				</s:table.field>
				<s:table.field name="routename_code" label="给药途径代码"  width="100" ></s:table.field>
				<s:table.field name="routename_name" label="给药途径名称"  width="100" ></s:table.field>
				<s:table.field name="routename_leib" label="给药途径类别"  datatype="template" width="100">
					<s:select name="routename_leib" value="$[routename_leib]" onchange="updateZd(this,'$[p_key]','routename_leib')">
					  <s:option value ="口服" label="口服"></s:option>
					  <s:option value ="注射(全身)" label="注射(全身)"></s:option>
					  <s:option value ="注射(局部)" label="注射(局部)"></s:option>
					  <s:option value ="外用" label="外用"></s:option>
					  <s:option value ="其他(全身)" label="其他(全身)"></s:option>
					  <s:option value ="其他(局部)" label="其他(局部)"></s:option>
					</s:select>
				</s:table.field>
				<s:table.field name="routename_jch" label="给药方式简称" datatype="template"  width="100" >
					<s:textfield name="routename_jch" value="$[routename_jch]" onchange="updateZd(this,'$[p_key]','routename_jch')">
					</s:textfield>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script src="${pageContext.request.contextPath}/res/hospital_common/js/table_print_excel.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	query_all();
	$(window).resize(function(){
		$('.dataTables_scrollBody').css('height',$(window).height() - 
				200 - $("div[ctype='form']").parent().parent().height());
		$('.dataTables_scrollBody').css('width','100%');
		$('.dataTables_scrollBody').css('overflow','auto');	
	});
	$(window).resize();
	$("i.showhide").on("click", function(){
		$('.dataTables_scrollBody').css('height',$(window).height() - 
				200 - $("div[ctype='form']").parent().parent().height());
		$('.dataTables_scrollBody').css('width','100%');
		$('.dataTables_scrollBody').css('overflow','auto');	
	})
	$(".form-collapse-btn").on("click", function(){
		setTimeout(function(){
			$('.dataTables_scrollBody').css('height',$(window).height() - 
					200 - $("div[ctype='form']").parent().parent().height());
			$('.dataTables_scrollBody').css('width','100%');
			$('.dataTables_scrollBody').css('overflow','auto');	
		},500)
	})
});

function query_all(){
	var formData = $('#form').getData();
	formData.timeout = 0;
	formData.is_wh_filter = 3;
	$("#datatable").params(formData);
	start = $("#datatable").dataTable().fnSettings()._iDisplayStart; 
	total = $("#datatable").dataTable().fnSettings().fnRecordsDisplay();
	var p = $("#datatable").DataTable().page();
	if((total-start)==1){
		if (start > 0) {
			$("#datatable").refresh_table(p-1);
		}else{
			$("#datatable").refresh();
		}
	}else{
		$("#datatable").refresh_table(p);
	}
}

function query_not(){
	var formData = $('#form').getData();
	formData.timeout = 0;
	formData.is_wh_filter = 0;
	$("#datatable").params(formData);
	start = $("#datatable").dataTable().fnSettings()._iDisplayStart; 
	total = $("#datatable").dataTable().fnSettings().fnRecordsDisplay();
	var p = $("#datatable").DataTable().page();
	if((total-start)==1){
		if (start > 0) {
			$("#datatable").refresh_table(p-1);
		}else{
			$("#datatable").refresh();
		}
	}else{
		$("#datatable").refresh_table(p);
	}
}

function query_finish(){
	var formData = $('#form').getData();
	formData.timeout = 0;
	formData.is_wh_filter = 1;
	$("#datatable").params(formData);
	start = $("#datatable").dataTable().fnSettings()._iDisplayStart; 
	total = $("#datatable").dataTable().fnSettings().fnRecordsDisplay();
	var p = $("#datatable").DataTable().page();
	if((total-start)==1){
		if (start > 0) {
			$("#datatable").refresh_table(p-1);
		}else{
			$("#datatable").refresh();
		}
	}else{
		$("#datatable").refresh_table(p);
	}
}

function updateZd(_this,p_key,zd){
	var value = $(_this).val();
	var data = '{"'+zd+'":"'+value+'","p_key":"'+p_key+'"}';
	update(eval('(' + data + ')'));
}

function updateCb(_this,p_key,zd){
	if($(_this).children().children().children().hasClass('checked')){
		value = 1;
	}else{
		value = 0;
	}
	var data = '{"'+zd+'":"'+value+'","p_key":"'+p_key+'"}';
	update(eval('(' + data + ')'));
}

function update(data){
	$.ajax({
		type: "post",
		url: "/w/hospital_common/dictextend/routename/updateByCode.json",
		contentType: "application/x-www-form-urlencoded; charset-UTF-8",
		data: data,
		success: function(XMLHttpRequest, status){
			var one_click = $('.bac[name=query]');
			if(one_click.length > 0){
				one_click.click();
			}else{
				query_all();
			}
		}
	})
}

$('[name=query]').click(function(){
	$(this).addClass('bac').siblings().removeClass('bac');
})

function tableToExcel(tableID, fileName) {
	var data = $("#form").getData();
	$("#" + tableID).params_table(data);
	$("#" + tableID).excel_new();
	//$("#" + tableID).export_table();
}
</script>