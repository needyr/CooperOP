<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="患者用药记录" disloggedin="true">
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
	
	.dataTables_scrollHeadInner{
		padding-left: 0px !important;
		padding-right: 17px !important;
	}
</style>
	<s:row>
		<s:form id="form" autocomplete="off"  fclass="portlet light bordered" collapsed="true" extendable="true" >
			<s:row>
				<s:datefield label="最早下达时间" autocomplete="off" name="s_time" format="yyyy-MM-dd HH:mm:ss" cols="2"></s:datefield>
			</s:row>
			<s:row>
				<s:datefield label="最晚下达时间" autocomplete="off" name="e_time" format="yyyy-MM-dd HH:mm:ss" cols="2"></s:datefield>
				<s:button label="查询" icon="fa fa-search" onclick="query();"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
	    <s:table id="datatable" autoload="false" fitwidth="false"  action="hospital_common.patient.order2"  >
	    	<s:toolbar>
	    	<s:button label="导出图片" onclick="img();"></s:button>
	    	</s:toolbar>
	        <s:table.fields>
	             <s:table.field name="tag" label="组"  width="20px" ></s:table.field>
	             <s:table.field name="repeat_indicator" label="长/临"  width="40px" datatype="script">
	             	if(record.repeat_indicator == '0'){
	             		return '临';
	             	}else if(record.repeat_indicator == '1'){
	             		return '长';
	             	}else{
	             		return record.repeat_indicator;
	             	}
	             </s:table.field>
	             <s:table.field name="order_class_name" label="类别"  width="60px" ></s:table.field>
	             <c:if test="${$return.resultset[0].p_type eq '1'}">
	             <s:table.field name="group_id" label="医嘱号"  width="80px" ></s:table.field>
	             </c:if>
	             <c:if test="${$return.resultset[0].p_type eq '2'}">
	             <s:table.field name="group_id" label="处方号"  width="80px" ></s:table.field>
	             </c:if>
	             <c:if test="${$return.resultset[0].p_type eq '1'}">
	             <s:table.field name="start_date_time" label="开始时间"  width="125px" ></s:table.field>
	             <s:table.field name="stop_date_time" label="结束时间"  width="125px" ></s:table.field>
	             </c:if>
	             <c:if test="${$return.resultset[0].p_type eq '2'}">
	             <s:table.field name="enter_date_time" label="就诊日期"  width="140px" ></s:table.field>
	             </c:if>
	             <s:table.field name="order_text" label="医嘱内容"  width="200px" datatype="script">
	             		var is_drug = record.is_drug;
	             		if(is_drug){
		             		return '<a id="shuoms" onclick="yaopin('+"'"+record.order_code+"'"+')">'+record.order_text+'</a>';
	             		}
	             		return record.order_text;
	             </s:table.field>
	             <s:table.field name="dosage" label="剂量"  width="80px" ></s:table.field>
	             <s:table.field name="dosage_units" label="单位"  width="80px" ></s:table.field>
	             <s:table.field name="administration" label="途径"  width="80px" ></s:table.field>
	             <s:table.field name="frequency" label="频次"  width="120px" ></s:table.field>
	             <s:table.field name="drug_message" label="药品信息"  width="350px" ></s:table.field>
	             <s:table.field name="beizhu" label="嘱托"  width="80px" ></s:table.field>
	             <s:table.field name="freq_detail" label="医生说明"  width="80px" ></s:table.field>
	             <s:table.field name="doctor" label="医生"  width="80px" ></s:table.field>
	             <s:table.field name="stop_doctor" label="停止医生"  width="80px" ></s:table.field>
	             <s:table.field name="nurse" label="护士"  width="80px" ></s:table.field>
	             <s:table.field name="enter_date_time" label="医嘱下达时间"  width="140px" ></s:table.field>
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
		var html = '';
		html += '<div crid="" style="float: right;" ctype="toolbar" class="tools" cinited="cinited">';
		html += '<button crid="" action="" class="btn btn-sm btn-sm btn-default " ';
		html += 'ctype="button" t="1" type="button" onclick="saix()" name="" cinited="cinited">';
		html += '<i class="fa fa-random"></i>筛选列 </button></div>';
		$('.col-md-6.col-sm-6').eq(1).append(html)
	});
	
	function p(s) {
	    return s < 10 ? '0' + s: s;
	}

	function img(){
		var formData = $('#form').getData();
		$.modal("/w/hospital_common/patient/orders_export.html", "导出医嘱/处方", {
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
	
	function yaopin(drugcode){
		if(drugcode){
			//打开药品说明书
			$.modal("/w/hospital_common/additional/instruction.html?drug_code="+drugcode,"查看药品说明书",{
		        width:"80%",
		        height:"90%",
		        callback : function(e){
		        }
			});
		}else{
			$.message("请选择药品！");
		}
		//event.stopPropagation();
	}
	
	function saix(){
		$('#datatable').setting_table();
	}
</script>