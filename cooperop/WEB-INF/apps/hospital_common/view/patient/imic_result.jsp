<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="患者收费记录" disloggedin="true">
<style type="text/css">
	.main{
	    padding: 0px;
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
	.dataTables_scrollBody{
		height: 290px !important;
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
	
	body{
		overflow: hidden;
	}
	
	.li-type{
		list-style: none;
	    /* border: 1px solid #ded0d0; */
	    line-height: 30px;
	    padding-left: 10px;
	    color: #2a768e;
	    font-weight: 200;
	    white-space: nowrap;
	    overflow: hidden;
	    text-overflow: ellipsis;
	}
	
	li.li-type:hover {
	    color: #095252;
	}
	
	.bechoosed {
	    color: #095252;
	    font-weight: 600;
	    background: #f3f3f3;
	}
	
	.li-title{
		list-style: none;
		font-size: 14px;
		color: #080808;
		font-weight: 300;
		text-align: center;
		border-bottom: 1px solid #a5a5a5;
	}
</style>
	<div id="widder">
		<div class="dleft" style="width: 20%; height: 455px; border:0px solid; float: left; overflow: auto;">
			<div id="container_ul" style="padding: 0px 13px 0px 10px;">
				<ul style="padding: 10px;border: 1px solid #d6d0d0; cursor: pointer;height: 430px;">
					<li class="li-title">医保审查规则</li>
					<c:forEach items="${$return.hospital_imic.types}" var="typ">
						<li class="li-type" data-id="${typ.sort_id}" title="${typ.sort_name}[${typ.shl}]">${typ.sort_name}[${typ.shl}]</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="drifht" style="width: 79%; height: 455px; border:0px solid; float: left; overflow: auto;">
			<s:row>
				<s:form id="form"  fclass="portlet light bordered" collapsed="true" extendable="true" >
					<s:row>
						<s:datefield label="开始时间" autocomplete="off" name="s_time" format="yyyy-MM-dd HH:mm:ss" cols="2"></s:datefield>
						<s:datefield label="截止时间" autocomplete="off" name="e_time" format="yyyy-MM-dd HH:mm:ss" cols="2"></s:datefield>
						<s:button label="查询" icon="fa fa-search" onclick="query();"></s:button>
					</s:row>
				</s:form>
			</s:row>
			<s:row>
			    <s:table id="datatable" fitwidth="false" autoload="false"  action="hospital_common.patient.queryImicDtl" limit="-1" >
			        <s:table.fields>
			             <s:table.field name="billing_date_time" label="收费时间"  width="70px"></s:table.field>
			             <s:table.field name="item_class_name" label="类别"  width="50px"></s:table.field>
			             <s:table.field name="related_items_show" label="项目名称"  width="150px"></s:table.field>
			             <s:table.field name="item_spec" label="规格" width="90px"></s:table.field>
			              <s:table.field name="price" label="单价" ></s:table.field>
			             <s:table.field name="amount" label="数量单位" width="70px" datatype="script">
			             	return parseFloat(record.amount) +record.units;
			             </s:table.field>
			             <s:table.field name="costs" label="金额/元"   ></s:table.field>
			             <s:table.field name="sys_check_level_name" label="审查级别"></s:table.field>
			            <%--  <s:table.field name="units" label="单位" width="70px"></s:table.field> --%>
			             <s:table.field name="description" label="审查结果"  width="150px"></s:table.field>
			            <%--  <s:table.field name="" label="单价" ></s:table.field> --%>
			             <%-- <s:table.field name="" label="保内金额"   ></s:table.field>
			             <s:table.field name="" label="扣除金额"   ></s:table.field> --%>
			             <s:table.field name="dept_name" label="开单科室" width="70px"></s:table.field>
			             <s:table.field name="doctor" label="开单医生" width="60px"></s:table.field>
			            
			        </s:table.fields>
			    </s:table>
			</s:row>
		</div>
	</div>
	
</s:page>
<script type="text/javascript">
var myscroll = 0;
var widthscroll = 0;
	$(function(){
		$('.dataTables_scrollBody').css('max-height', window.innerHeight - 151);
		defaultChoose();
		query();
		$('.li-type').click(function(){
			$(this).addClass('bechoosed').siblings().removeClass('bechoosed');
			var sort_id = $(this).data('id');
			query(sort_id);
		});
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
	
	function query(sort_id){
		var formData = $('#form').getData();
		formData.timeout = 0;
		formData.patient_id = '${param.patient_id}';
		formData.visit_id = '${param.visit_id}';
		if(sort_id){
			formData.sort_id = sort_id;
		}
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
	
</script>