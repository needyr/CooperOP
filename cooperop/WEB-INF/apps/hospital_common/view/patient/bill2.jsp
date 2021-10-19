<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="患者收费记录" disloggedin="true">
<style type="text/css">
	.main{
		    /* padding: 0px; */
    		padding-top: 10px !important;
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
		<s:form id="form" fclass="portlet light bordered" collapsed="true" extendable="true" >
			<s:row>
				<s:datefield label="最早收费时间" autocomplete="off" name="s_time" format="yyyy-MM-dd HH:mm:ss" cols="2"></s:datefield>
			</s:row>
			<s:row>
				<s:datefield label="最晚收费时间" autocomplete="off" name="e_time" format="yyyy-MM-dd HH:mm:ss" cols="2"></s:datefield>
			</s:row>
			<s:row>
				<s:autocomplete  id="class_type" label="类别" name="class_name" action="hospital_common.patient.billType" limit="10" editable="false" cols="1">
					<s:option value="$[class_name]" label="$[class_name]"/>
				</s:autocomplete>
				<s:textfield name="item_name" label="项目名称"></s:textfield>
				<s:button label="查询" icon="fa fa-search" onclick="query();"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
	    <s:table id="datatable" fitwidth="false" autoload="false"  action="hospital_common.patient.queryBill" sort="true">
	        <s:toolbar>
	        	<font style="color: #dc7b16;">提示：转自费项目为系统标记，具体是否转自费需要查看HIS上传医保数据</font>
	        </s:toolbar>
	        <s:table.fields>
	             <s:table.field name="class_name" label="类别"  width="60px" ></s:table.field>
	             <c:if test="${zzf_show eq 1}">
	             	<s:table.field name="zzf_state" label="转自费标识"  width="80px" datatype="script">
	             		var zzf = record.zzf_state;
	             		if(zzf){
	             			if(zzf == 1){
	             				return '已转自费 <a title="操作记录" href="javascript:void(0)" onclick="operRecord(\''+record.patient_id+'\', \''+record.visit_id+'\', \''+record.item_code+'\', \''+(record.group_id==null?'':record.group_id)+'\', \''+(record.order_no==null?'':record.order_no)+'\', \''+(record.order_sub_no==null?'':record.order_sub_no)+'\', \''+record.item_name+'\')"><i class="fa fa-list-ol"></i></a>';
	             			}else if(zzf == -1){
	             				return '取消转自费 <a title="操作记录" href="javascript:void(0)" onclick="operRecord(\''+record.patient_id+'\', \''+record.visit_id+'\', \''+record.item_code+'\', \''+(record.group_id==null?'':record.group_id)+'\', \''+(record.order_no==null?'':record.order_no)+'\', \''+(record.order_sub_no==null?'':record.order_sub_no)+'\', \''+record.item_name+'\')"><i class="fa fa-list-ol"></i></a>';
	             			}else{
	             				return '';
	             			}
	             		}else{
	             			return '';
	             		}
	             	</s:table.field>
	             </c:if>
	             <s:table.field name="billing_date_time" label="收费时间"  width="120px" ></s:table.field>
	             <s:table.field name="item_code" label="项目编码"  width="100px"></s:table.field>
	             <s:table.field name="item_name" label="项目名称"  width="200px" sort="true" datatype="script">
	             	var zzf = record.zzf_state;
             		var is_drug = record.is_drug;
	             	if(zzf == 1){
	             		if(is_drug){
		             		return '<a id="shuoms" style="color: #ec6d00;" onclick="yaopin(+'+"'"+record.item_code+"'"+')">'+record.item_name+'</a>';
	             		}
	             		return '<font style="color: #ec6d00;">' + record.item_name + '</font>';
	             	}else{
	             		if(is_drug){
		             		return '<a id="shuoms" onclick="yaopin('+"'"+record.item_code+"'"+')">'+record.item_name+'</a>';
	             		}
	             		return record.item_name;
	             	}
	             	
	             </s:table.field>
	             <s:table.field name="shpgg" label="规格"  width="100px" ></s:table.field>
	             <s:table.field name="shl" label="数量单位"  width="60px"  datatype="script">
	             	return parseFloat(record.shl) + record.dw;
	             </s:table.field>
	             <s:table.field name="dj" label="单价/元"  width="50px"  datatype="script">
	             	return parseFloat(record.dj);
	             </s:table.field>
	             <s:table.field name="je" label="金额"  width="50px"  datatype="script">
	             	return parseFloat(record.je);
	             </s:table.field>
	             <s:table.field name="drug_message" label="药品信息"  width="250px" ></s:table.field>
	             <s:table.field name="dept_name" label="科室"  width="60px" ></s:table.field>
	             <s:table.field name="doctor" label="医生"  width="60px" ></s:table.field>
	             <s:table.field name="nurse" label="护士"  width="60px" ></s:table.field>
	        </s:table.fields>
	    </s:table>
	</s:row>
</s:page>
<script type="text/javascript">
var myscroll = 0;
var widthscroll = 0;
	$(function(){
		$('.dataTables_scrollBody').css('max-height', window.innerHeight - 260);
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
	
	function saix(){
		$('#datatable').setting_table();
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
	
	function operRecord(patient_id, visit_id, item_code, group_id, order_no , order_sub_no, item_name){
		$.modal("/w/hospital_imic/selfpaid/operlist.html", " ["+item_name+"] 转自费记录查看", {
			width : "600px",
			height : "450px",
			patient_id: patient_id,
			visit_id: visit_id,
			item_code: item_code,
			group_id: group_id,
			order_no: order_no,
			order_sub_no: order_sub_no,
			callback : function(e) {
			}
		});
	}
</script>