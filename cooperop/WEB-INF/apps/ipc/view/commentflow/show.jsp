<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="事后点评查看">
	<s:row>
		<s:form id="form_query" label="">
			<s:row>
				<s:textfield name="patient_no_query" label="住院号" cols="1"></s:textfield>
				<s:textfield name="patient_id_query" label="患者ID/姓名" cols="1"></s:textfield>
				<s:select name="comment_state_query" label="状态" cols="1" value="-1">  
					<s:option label="全部" value="-1"></s:option>
					<s:option label="已点评" value="1"></s:option>
					<s:option label="未点评" value="0"></s:option>
				</s:select>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="患者列表查看" autoload="false" action="ipc.commentflow.queryPats" sort="true" >
			<s:toolbar>
				<s:button icon="fa fa-search" label="查询" onclick="query()"></s:button>
				<%-- <s:button icon="" label="完成点评" onclick="endComment()"></s:button>
				<s:button icon="" label="转交点评" onclick="cancelComment()"></s:button> --%>
			</s:toolbar> 
			<s:table.fields>
			 	<s:table.field name="state" label="状态" datatype="script" width="40px">
					 	var state = record.state;
					 	if(state==1){
					 		return '<font color="green">已点评</font>';
					 	}else if(state==0){
					 		return '<font color="red">未点评</font>';
					 	}
				</s:table.field>
				<s:table.field name="charge_type" label="费别" width="120px"></s:table.field>
				<s:table.field name="patient_name" label="患者姓名" width="100px"></s:table.field>
				<s:table.field name="patient_no" label="住院号"></s:table.field>
			    <s:table.field name="patient_id" label="患者ID" ></s:table.field>
				<%-- <s:table.field name="visit_id" label="住院次数" ></s:table.field> --%>
				<s:table.field name="admission_datetime" label="入院时间" width="130px"></s:table.field>
				<s:table.field name="dept_in" label="入院科室" ></s:table.field>
				<s:table.field name="discharge_datetime" label="出院时间" width="130px"></s:table.field>
				<s:table.field name="dept_discharge" label="出院科室" ></s:table.field>
				<%-- <s:table.field name="doctor_in_charge" label="经治医生" ></s:table.field> --%>
				<s:table.field name="attending_doctor" label="主治医生" ></s:table.field>
				<%-- <s:table.field name="director" label="科主任" ></s:table.field> --%>
				<s:table.field name="pat_adm_condition" label="入院病情" ></s:table.field>
				<s:table.field name="handler_name" label="点评人" ></s:table.field>
				<%-- <s:table.field name="shenfen" label="身份" ></s:table.field> --%>
				<s:table.field name="caozuo" label="操作" datatype="template" width="70px">
					<a href="javascript:void(0)" style="color:#216cf9" onclick="comment('$[sample_id]','$[patient_id]','$[id]','$[visit_id]')">详情</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	var djbh = '${param.djbh}';
	$(function(){
		query();
	});
	
	function query(){
		var comment_user = '${param.comment_user}';
		var sample_id = '${param.sample_id}';
		var data = $("#form_query").getData();
		if(sample_id){
			data.comment_user = comment_user;
			data.sample_id = sample_id;
			$("#datatable").params(data);
		}else if (djbh){
			data.djbh = djbh;
			$("#datatable").params(data);
		}
		$("#datatable").refresh();
	}
	
	function getTj(){
		
	}
	
	function endComment(){
		var data = {
				djbh: djbh,
				task_id: '${param.task_id}',
				audited: 'Y'
			};
		$.call("ipc.commentflow.finish",data,function(rtn){
			if(rtn.result == 'N'){
				$.message("完成所有点评任务才能完成点评");
			}else{
				$.closeModal(true);
			}
		});
	}
	
	function cancelComment(){
		var data = {
			djbh: djbh,
			task_id: '${param.task_id}',
			audited: 'N',
			advice: ''
		};
		$.call("ipc.commentflow.cancel",data,function(){
			$.closeModal(true);
		});
	}
	
	function comment(sample_id,patient_id,id,visit_id){
	  console.log(sample_id+' '+patient_id);
	  $.modal("detailshow.html","点评细则查看",{
	       width:"100%",
	       height:"100%",
	       sample_id: sample_id,
	       patient_id: patient_id,
	       sample_pid: id,
	       visit_id: visit_id,
	       comment_user: '${param.comment_user}',
	       callback : function(rtn){
	           query();
	       }
	   });
	}
</script>