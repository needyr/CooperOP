<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="医嘱列表">
	<s:row>
		<s:form id="dataform" label="筛选条件">
			<s:row>
				<s:textfield name="order_text" placeholder="请输入医嘱内容" label="患者药品信息" ></s:textfield>
				<s:select name="is_drug" label="查看">
					<s:option label="全部" value=""></s:option>
					<s:option label="药品" value="1"></s:option>
				</s:select>
			
				<s:button label="查询" icon="fa fa-search" onclick="query()" style="background: #368bdb;text-align: center;color: #FFF;"></s:button>
			</s:row>
		</s:form>
	</s:row>

	<s:row>
		 <s:table id="datatable" autoload="false" fitwidth="false"  action="hospital_common.report.queryhis.query_orders"
		 label="医嘱信息"  >
		 <s:toolbar>
		 	<span>患者ID:${param.patient_id};&nbsp;
		 	   <c:if test="${d_type eq '1'}">
		 		 住院次数：${param.visit_id};&nbsp;
				 </c:if></span> 
		 	<a onclick="toPatient('${param.patient_id}','${param.visit_id}' )">患者详情</a>
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
	             
	            <c:if test="${d_type eq '1'}">
	             <s:table.field name="group_id" label="医嘱号"  width="80px" ></s:table.field>
	             </c:if>
	             <c:if test="${d_type ne '1'}">
	             <s:table.field name="group_id" label="处方号"  width="80px" ></s:table.field>
	             </c:if>
	             <c:if test="${d_type eq '1'}">
	             <s:table.field name="start_date_time" label="开始时间"  width="125px" ></s:table.field>
	             <s:table.field name="stop_date_time" label="结束时间"  width="125px" ></s:table.field>
	             </c:if>
	             <c:if test="${d_type ne '1'}">
	             <s:table.field name="enter_date_time" label="就诊日期"  width="140px" ></s:table.field>
	             </c:if>        
	          
	             <s:table.field name="dosage" label="剂量"  width="80px" datatype="script">
	                 if(record.dosage_units != null){
	                   return record.dosage +'   ('+record.dosage_units +')';
	                 }
	                  return record.dosage;
	             </s:table.field>
	              <s:table.field name="order_text" label="医嘱内容"  width="200px" ></s:table.field>
	             <s:table.field name="administration" label="途径"  width="80px" ></s:table.field>
	             <s:table.field name="frequency" label="频次"  width="120px" ></s:table.field>
	             <s:table.field name="beizhu" label="嘱托"  width="80px" ></s:table.field>
	             <s:table.field name="dept_name" label="科室"  width="120px" ></s:table.field>
	             <s:table.field name="freq_detail" label="医生说明"  width="80px" ></s:table.field>
	             <s:table.field name="doctor" label="医生"  width="80px" ></s:table.field>
	             <s:table.field name="stop_doctor" label="停止医生"  width="80px" ></s:table.field>
	             <s:table.field name="nurse" label="护士"  width="80px" ></s:table.field>
	        </s:table.fields>
	    </s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	//初始化--查询
	$(function() {
		  $('.dataTables_scrollBody').css('min-height',"100px");
			 $(window).resize(function(){
				    $('.dataTables_scrollBody').css('height',$(window).height() -280-$("div[ctype='form']").parent().parent().height());
				  }).resize();
		
		query();
	});

	function query() {
		var fdata = $("#dataform").getData();
		 fdata.patient_id='${param.patient_id}';
		 fdata.visit_id='${param.visit_id}';
		 
		 if('${param.group_id}'){
			 fdata.group_id='${param.group_id}'; 	
		 }
		
		$("#datatable").params(fdata);
		$("#datatable").refresh();
	}

	//查询患者信息
	function toPatient(patient_id, visit_id) {
		$.modal('/w/hospital_common/showturns/patientdetail.html?patient_id=' + patient_id + '&&visit_id=' + visit_id + '', "查看审查详情", {
			width : "90%",
			height : "550px",
			callback : function(e) {
			}
		});
	}


	
</script>