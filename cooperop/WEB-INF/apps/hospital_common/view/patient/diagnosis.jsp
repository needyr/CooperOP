<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="患者诊断记录" disloggedin="true">
<style type="text/css">
	.main{
	    padding: 0px !important;
   		/*padding-top: 10px !important;*/
	}
	.descr_cla{
		overflow: hidden;
	    text-overflow: ellipsis;
	    white-space: nowrap;
	}
	.tioaz{
		margin-right: 40px;
    	float: right;
	}
	.portlet{
		margin: 0px !important;
	}
	
	.page-content {
		padding: 10px 0px 0px 0px !important;
	}
	
	.page-content .portlet.box>.portlet-body {
		padding: 5px 5px 0px 5px;
	}
</style>
	<s:row>
		<s:form id="form" fclass="portlet light bordered" collapsed="true" extendable="true" >
			<s:row>
				<s:datefield label="最早诊断时间" autocomplete="off" name="s_time" format="yyyy-MM-dd HH:mm:ss" cols="2"></s:datefield>
			</s:row>
			<s:row>
				<s:datefield label="最晚诊断时间" autocomplete="off" name="e_time" format="yyyy-MM-dd HH:mm:ss" cols="2"></s:datefield>
				<s:button label="查询" icon="fa fa-search" onclick="query();"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
	    <s:table id="datatable" autoload="false"  action="hospital_common.patient.queryDiagnosis"  sort="true">
			<s:toolbar>
				<s:button label="导出图片" onclick="img();"></s:button>
			</s:toolbar>
	        <s:table.fields>
	             <s:table.field name="diagnosisclass_name" label="诊断类别"  sort="true"></s:table.field>
				 <s:table.field name="diagnosis_first" label="第一诊断" datatype="script">
					 let html=[];
					 if(record.diagnosis_first == '1'){
					 	 html.push('<span class="pre-info">是</span>');
					 }else{
						 html.push('<span class="pre-info">否</span>');
					 }
					 return html.join("");
				 </s:table.field>
	             <s:table.field name="diagnosis_no" label="诊断序号"  sort="true"></s:table.field>
	             <s:table.field name="diagnosis_code" label="诊断编码"  sort="true"></s:table.field>
	             <s:table.field name="diagnosis_desc" label="诊断名称"  sort="true"></s:table.field>
	             <s:table.field name="diagnosis_date" label="诊断日期" sort="true"></s:table.field>
				 <s:table.field name="treat_days" label="治疗天数" sort="true"></s:table.field>
				 <s:table.field name="treat_result" label="治疗结果" sort="true"></s:table.field>
	        </s:table.fields>
	    </s:table>
	</s:row>
</s:page>
<script type="text/javascript">
var myscroll = 0;
var widthscroll = 0;
	$(function(){
		$('.dataTables_scrollBody').css('max-height', window.innerHeight - 210);
		//defaultChoose();
		query();
	});
	
	function p(s) {
	    return s < 10 ? '0' + s: s;
	}
	
	function defaultChoose(){
		var myDate = new Date();
		//获取当前年
		var year=myDate.getFullYear();
		//获取当前月
		var month=myDate.getMonth()+1;
		//获取当前日
		var date=myDate.getDate(); 
		//var h=myDate.getHours();       //获取当前小时数(0-23)
		//var m=myDate.getMinutes();     //获取当前分钟数(0-59)
		//var s=myDate.getSeconds();  
		var startTime=year+'-'+p(month)+"-"+p(date)+" 00:00:00";
		var endTime=year+'-'+p(month)+"-"+p(date)+" 23:59:59";
		$('[name="s_time"]').val(startTime);
		$('[name="e_time"]').val(endTime);
		$('[name="dept_name"]').val('全部');
		$('[name="sort_name"]').val('全部');
	}
	
	function query(){
		var formData = $('#form').getData();
		formData.timeout = 0;
		formData.patient_id = '${param.patient_id}';
		formData.visit_id = '${param.visit_id}';
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
		$('.dataTables_scrollBody').scroll(function(){
			myscroll = $(this).scrollTop();
			widthscroll = $(this).scrollLeft();
		})
	}
	//数据导出为图片
	function img(){
		var formData = $('#form').getData();
		$.modal("/w/hospital_common/patient/diagnosis_exports.html", "导出患者诊断记录", {
			height: "600px",
			width: "850px",
			s_time: formData.s_time,
			e_time: formData.e_time,
			patient_id:'${param.patient_id}',
			visit_id:'${param.visit_id}',
			callback : function(rtn) {
			}
		});
	}
</script>