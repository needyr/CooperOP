<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="cn.crtech.cooperop.bus.util.GlobalVar"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<style>
table tr th{
	text-align: center;
}
</style>
<s:page title="" disloggedin="true">
<div id="tmp_box"><div>
</s:page>
<script src="/theme/plugins/html2canvas.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	var test_no = '${param.test_no}';
	var specimen = '${param.specimen}';
	var ordering_provider = '${param.ordering_provider}';
	var requested_date_time = '${param.requested_date_time}';
	var results_rpt_date_time = '${param.results_rpt_date_time}';
	var transcriptionist = '${param.transcriptionist}';
	var patient_id = '${param.patient_id}';
	var visit_id = '${param.visit_id}';
	var verified_by = '${param.verified_by}';
	$.call('hospital_common.patient.exportInspection',{patient_id:"${param.patient_id}",
		visit_id:"${param.visit_id}",
		test_no:test_no,
		specimen:specimen,
		ordering_provider:ordering_provider,
		requested_date_time:requested_date_time,
		results_rpt_date_time:results_rpt_date_time},function(rtn){
		
		var html = [];
		html.push('<div style="padding: 20px;" id="my_lab_msg">');
		html.push('<h1 style="text-align: center;font-size:18px;">'+rtn.hospital_name+specimen+'报告单</h1>');
		html.push('<table style="width:100%">');
		html.push('<tr>');
		html.push('<td>姓名：'+rtn.patient.patient_name+'</td>');
		html.push('<td>性别：'+rtn.patient.sex+'</td>');
		html.push('<td>年龄：'+rtn.patient.age+'</td>');
		html.push('<td>科别：'+rtn.patient.dept_in_name+'</td>');
		html.push('<td>样本编号：'+(test_no?test_no:'')+'</td>');
		html.push('</tr>');
		html.push('<tr>');
		html.push('<td>门诊号：'+rtn.patient.patient_no+'</td>');
		html.push('<td>标本种类：'+specimen+'</td>');
		html.push('<td>床号：'+(rtn.patient.bed_no?rtn.patient.bed_no:'')+'</td>');
		html.push('<td>病区：</td>');
		html.push('<td>申请医生：'+(ordering_provider?ordering_provider:'')+'</td>');
		html.push('</tr>');
		html.push('<tr>');
		html.push('<td>备注：</td>');
		html.push('<td>条形码：</td>');
		html.push('<td>申请日期：'+(requested_date_time?requested_date_time:'')+'</td>');
		html.push('<td></td>');
		html.push('<td></td>');
		html.push('</tr>');
		html.push('</table>');
		html.push('<div>临床诊断：');
		for(var i=0;i<rtn.diagnosGroup.length;i++){
			html.push(rtn.diagnosGroup[i].diagnosis_desc+',');
		}
		html.push('</div>');
		html.push('<div class="lab_box">');
		var labs = rtn.labs;
		html.push('<table style="text-align: center;border-bottom: 1px solid black;border-top: 1px solid black;width:100%;float:left;">');
		html.push('<thead style="border-bottom: 1px solid black;"><tr><th>代号</th><th>检验项目</th>');
		html.push('<th>结果</th><th></th><th>单位</th><th>参考值</th>');
		
		html.push('<th style="border-left: 1px solid black;">代号</th><th>检验项目</th>');
		html.push('<th>结果</th><th></th><th>单位</th><th>参考值</th></tr></thead>');
       if(labs){
       	for(var i=0;i<rtn.labs.length;i++){
       			html.push('<tr>');
       			html.push('<td>'+labs[i].report_item_code+'</lable>');
		        html.push('<td>'+labs[i].report_item_name+'</lable>');
		        html.push('<td>'+labs[i].result+'</lable>');
		        html.push('<td>'+labs[i].tag+'</lable>');
		        html.push('<td>'+labs[i].units+'</lable>');
		        html.push('<td>'+labs[i].print_context+'</lable>');
		        html.push('<td style="border-left: 1px solid black;">'+labs[i].report_item_code1+'</lable>');
		        html.push('<td>'+labs[i].report_item_name1+'</lable>');
		        html.push('<td>'+labs[i].result1+'</lable>');
		        html.push('<td>'+labs[i].tag1+'</lable>');
		        html.push('<td>'+labs[i].units1+'</lable>');
		        html.push('<td>'+labs[i].print_context1+'</lable>');
		        html.push('</tr>');
       	}
       }
       html.push('</table>');
       html.push('</div>');
       
  		html.push('<table style="width:100%">');
  		html.push('<tr>');
 		//html.push('<td>接收日期：</td>');
 		html.push('<td>报告日期：'+(results_rpt_date_time?results_rpt_date_time:'')+'</td>');
 		<!-- 等待加入具体数据 操作者、审核者 -->
 		html.push('<td>操作者：'+(transcriptionist?transcriptionist:'')+'</td>');
 		html.push('<td>审核者：'+(verified_by?verified_by:'')+'</td>');
 		html.push('<td style="text-align: right;">共'+(rtn.num?rtn.num:'0')+'项</td>');
 		html.push('</tr>');
 		html.push('</table>');
 		html.push('</div>');
 		$('#tmp_box').append(html.join(''));
 		new html2canvas($('#tmp_box')[0],{
 			scale: 2
 		}).then(canvas => {
           // canvas为转换后的Canvas对象
            var imgUri = canvas.toDataURL("image/png").replace("image/png", "image/octet-stream"); // 获取生成的图片的url
			var a = document.createElement("a");
			a.download =  rtn.hospital_name+specimen+'报告单.png';
			//a.href = link;
			a.href = imgUri;
			document.body.appendChild(a);
			a.click();
			document.body.removeChild(a);
			setTimeout(function(){
				$.closeModal(true);
			},2000);
			
       })
	});
})
</script>