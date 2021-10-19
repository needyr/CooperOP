<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<style>
.report_info{
	margin-right: 20px;
    font-size: 16px;
    width: 330px;
    border-bottom: 1px dashed #aaa0a0;
    display: inline-table;
    margin-top: 6px;
}
</style>
<s:page title="门（急）诊处方常规点评" disloggedin="true" >
<s:row>
	<s:form border="0" id="form" label="快速查找">
		<s:row>
			<s:datefield label="开始时间" name="start_time" placeholder="请输入创建时间" cols="1"></s:datefield>
			<s:datefield label="到" name="end_time" cols="1"></s:datefield>
		</s:row>
		<s:row>
			<s:checkbox label="注射剂" name="q_is_zs" >
				<s:option label="是" value="1"></s:option>
				<s:option label="否" value="0"></s:option>
			</s:checkbox>
			<s:checkbox label="是否合理" name="is_comment_result" >
				<s:option label="是" value="1"></s:option>
				<s:option label="否" value="0"></s:option>
			</s:checkbox>
		</s:row>
	</s:form>
	<s:form border="0" id="reportform" label="统计信息">
		<s:row>
		<div id="computation"></div>
		</s:row>
	</s:form>
</s:row>

<s:row>
<s:table label="常规点评" action="ipc.commentreport.query" autoload="false" id="datatable">
	<s:toolbar>
		<s:button icon="fa fa-search" label="查询" onclick="query()"></s:button>
	</s:toolbar>
	<s:table.fields>
		<s:table.field name="createtime" label="创建时间" datatype="date" format="yyyy-MM-dd hh:mm:ss" width="150"></s:table.field>
		<s:table.field name="patient_id" label="患者ID"></s:table.field>
		<s:table.field name="patient_name" label="患者姓名" datatype="template">
			<a href="javascript:void(0);" onclick="topatient('$[patient_id]','$[visit_id]');">$[patient_name]</a>
		</s:table.field>
		<s:table.field name="drug_breed_count" label="药品品种数" width="60"></s:table.field>
		<s:table.field name="kangjun_count" label="抗菌药 （品种数）" width="100"></s:table.field>
		<s:table.field name="is_zs" label="是否为注射剂" datatype="script" width="70">
			if(record.is_zs == 0){
				return '否';
			}
			return '是';
		</s:table.field>
		<s:table.field name="base_drug_count" label="药品通用名数" width="100"></s:table.field>
		<s:table.field name="doctor" label="开嘱医生"></s:table.field>
		<s:table.field name="check_pharmacist" label="审查、调配药师" width="100"></s:table.field>
		<s:table.field name="comment_result" label="是否合理" datatype="script" width="60">
			if(record.comment_result == 0){
				return '否';
			}
			return '是';
		</s:table.field>
		<s:table.field name="pharmacist_comment_id" label="存在问题"></s:table.field>
	</s:table.fields>
</s:table>
</s:row>
</s:page>
<script type="text/javascript">
$(function(){
	$("[name=q_is_zs]").setData(['0','1']);
	$("[name=is_comment_result]").setData(['0','1']);
})
function query(){
	var qdata=$("#form").getData();
	$.call("ipc.commentreport.basecomments",qdata,function(rtn){
		var html =[];
		$("#computation").empty();
		if(rtn){
			html.push('<span class="report_info">用药品种总数:'+rtn.drugcountall+'种 </span>');
			html.push('<span class="report_info">平均每张处方用药品种数:'+rtn.average_drug_breed_count+'种/处方</span>');
			html.push('<span class="report_info">使用抗菌药的处方数:'+rtn.usekjcount+'种 </span>');
			html.push('<span class="report_info">抗菌药使用百分率:'+(rtn.usezscount/rtn.pre_counts*100).toFixed(2)+'%</span>');
			html.push('<span class="report_info">使用注射剂的处方数:'+rtn.usezscount+'种 </span>');
			html.push('<span class="report_info">注射剂使用百分率:'+(rtn.usezscount/rtn.pre_counts*100).toFixed(2)+'%</span>');
			html.push('<span class="report_info">处方中使用药品通用名总数:'+rtn.usegeneralcount+'种 </span>');
			html.push('<span class="report_info">药品通用名占处方用药的百分率:'+(rtn.usegeneralcount/rtn.drugcountall*100).toFixed(2)+'%</span>');
			html.push('<span class="report_info">合理处方总数:'+rtn.passcount+'种 </span>');
			html.push('<span class="report_info">合理处方百分率:'+(rtn.passcount/rtn.pre_counts*100).toFixed(2)+'%</span>');
			html.push('<span class="report_info">抗菌药物使用比率:'+(rtn.useallkjcount/rtn.drugcountall*100).toFixed(2)+'%</span>');
		}
		$("#computation").append(html.join(''));
	});
	$("#datatable").params(qdata);
	$("#datatable").refresh();
}
function topatient(patient_id,visit_id){
	layer.open({
		  type: 2,
		  title: "患者详情",
		  //skin: 'layui-layer-rim', //加上边框
		  offset: '0px',
		  area: ['100%', '100%'], //宽高
		  content: "/w/hospital_common/additional/patientInfo.html?patient_id="+patient_id+"&&visit_id="+visit_id
	});
}
</script>