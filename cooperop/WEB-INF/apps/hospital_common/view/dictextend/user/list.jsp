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
.tags_choose{
	width: 100%;
	border: 1px dashed #d0d0d0;
	min-height: 28px;
	float: right;
	padding: 3px;
}

.tag_type{
	border: 1px solid #ff8100;
	border-radius: 8px !important;
	padding: 2px;
	font-family: 微软雅黑;
	background-color: #ffdd00;
	font-weight: 600;
	font-size: 10px;
	margin: 2px;
	display: -webkit-inline-box;
}
</style>
<s:page title="用户维护" >
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
		<s:table id="datatable" height="250" label="" autoload="false" action="hospital_common.dictextend.user.query"  >
			<s:table.fields>
				<s:table.field name="is_wh" label="维护"  datatype="template">
					<s:checkbox name="is_wh" value="$[is_wh]" onchange="updateCb(this,'$[p_key]','is_wh')">
						<s:option value ="1" label=""></s:option>
					</s:checkbox>
				</s:table.field>
				<s:table.field name="p_key" label="代码"  width="100" ></s:table.field>
				<s:table.field name="user_name" label="名称"  width="100" ></s:table.field>
				<s:table.field name="tags" label="权限标签" datatype="script" width="300">
					var qx_tags = record.qx_tags;
					var html = [];
					var p_key = record.p_key.replace(/\|/g,'_');
					html.push('<div id="tags_choose_'+p_key+'" data-tag="'+record.tags+'" onclick="add_tags(this,\''+p_key+'\');" class="tags_choose">');
					if(qx_tags){
						var tags = qx_tags.split(',');
						for(var i=0;i < tags.length;i++){
							var tag = tags[i].split('-');
							html.push('<font title="'+tag[1]+'" data-tagbh="'+tag[0]+'" data-tagshow="'+tag[2]+'" class="tag_type">'+tag[2]+'</font>');
						}
					}
					html.push('</div>');
					return html.join('');
				</s:table.field>
				<s:table.field name="sex" label="性别"  width="100" ></s:table.field>
				<s:table.field name="dept_name" label="科室"  width="100" ></s:table.field>
				<s:table.field name="job_role" label="医院级别"  width="100" ></s:table.field>
				<s:table.field name="job_level" label="配对级别"  datatype="template" width="100">
					<s:select name="job_level" value="$[job_level]" onchange="updateZd(this,'$[p_key]','job_level')">
					  <s:option value ="临床医师" label="临床医师"></s:option>
					  <s:option value ="主治医师" label="主治医师"></s:option>
					  <s:option value ="主任医师" label="主任医师"></s:option>
					  <s:option value ="其他" label="其他"></s:option>
					</s:select>
				</s:table.field>
				<s:table.field name="is_doctor" label="是否临床医生" datatype="template"  width="100" >
					<s:checkbox name="is_doctor" value="$[is_doctor]" onchange="updateCb(this,'$[p_key]','is_doctor')">
						<s:option value ="1" label=""></s:option>
					</s:checkbox>
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
		url: "/w/hospital_common/dictextend/user/updateByCode.json",
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

function add_tags(_this,p_key){
	var code = $(_this).attr('data-tag');
	$.modal("/w/hospital_common/dict/sysdrugtag/list.html", "添加权限标记", {
		height: "90%",
		width: "50%",
		code: code,
		qx:"权限",
		maxmin: false,
		callback : function(rtn) {
			if(rtn){
				var code = [];
				var old_pkey = p_key;
				var new_pkey = p_key.replace(/\_/g,'|');
				$('#tags_choose_'+old_pkey).empty();
				var spanhtml='';
				for(var i=0;i<rtn.length;i++){
					var t = rtn[i].all.split('-');
					code.push(rtn[i].code);
					spanhtml += '<font title="'+t[2]+'" data-tagbh="'+t[0]+'" data-tagname="'+t[2]+'" data-tagshow="'+t[1]+'" class="tag_type">'+t[1]+'</font>';
				}
				document.getElementById("tags_choose_"+old_pkey).innerHTML = spanhtml;
				document.getElementById("tags_choose_"+old_pkey).setAttribute('data-tag',code);
				var data = '{"'+'tags'+'":"'+code.toString()+'","p_key":"'+new_pkey+'"}';
				update(eval('(' + data + ')'));
			}
	    }
	}); 
}
</script>