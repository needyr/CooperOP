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
.des-t {
    line-height: 25px;
    font-size: 14px;
    font-weight: 600;
}
.des-c {
    padding-left: 20px;
    font-size: 12px;
}
</style>
<s:page title="" disloggedin="true">
<div id="tmp_box"><div>
</s:page>
<script src="/theme/plugins/html2canvas.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	var req_dept_name="${param.req_dept_name}";
	var exam_date_time="${param.exam_date_time}";
	var report_date_time="${param.report_date_time}";
	var req_physician="${param.req_physician}";
	var exam_sub_class="${param.exam_sub_class}";
	var exam_class="${param.exam_class}";
	
	$.call('hospital_common.patient.exportCheck',{patient_id:"${param.patient_id}",
		visit_id:"${param.visit_id}",
		exam_no:"${param.exam_no}"
	},function(rtn){
		var html = [];
		html.push('<div style="padding: 20px;" id="my_exam_msg">');
	    html.push('<h1 style="text-align: center;font-size:18px;">'+rtn.hospital_name+'</h1>');
	    html.push('<h2 style="text-align: center;font-size:14px;">'+(exam_class?exam_class:'')+'报告单</h2>');
	    html.push('<table style="width:100%;border-top: 1px solid black;border-bottom: 1px solid black;">');
	    html.push('<tr>');
	    html.push('<td>姓名：'+rtn.patient.patient_name+'</td>');
		html.push('<td>性别：'+rtn.patient.sex+'</td>');
		html.push('<td>年龄：'+rtn.patient.age+'</td>');
	    html.push('<td>病历号：'+("${param.patient_id}"?"${param.patient_id}":'')+'</td>');
	    html.push('</tr>');
	    html.push('<tr>');
	    var ly = "";
	    if(rtn.exam){
	    	for(var i=0;i<rtn.exam.length;i++){
	    		ly += rtn.exam[i].patient_source?rtn.exam[i].patient_source:'';
	    	}
	    }
	    html.push('<td>来源：'+ly+'</td>');
	    html.push('<td>申请科室：'+(req_dept_name?req_dept_name:'')+'</td>');
	    html.push('<td>申请医生：'+(req_physician?req_physician:'')+'</td>');
	    html.push('<td>床号：'+(rtn.patient.bed_no?rtn.patient.bed_no:'')+'</td>');
	    html.push('</tr>');
	    html.push('<tr>');
	    html.push('<td>住院号：'+rtn.patient.patient_no+'</td>');
	    html.push('<td>检查项目：'+(exam_sub_class?exam_sub_class:'')+'</td>');
	    html.push('<td></td>');
	    html.push('<td></td>');
	    html.push('</tr>');
	    html.push('</table>');
	    html.push('<div class="img"></div>');
	    html.push('<p class="des-t">检查所见：</p>');
	    html.push('<p class="des-c">');
	    if(rtn.exam){
	    	for(var i=0;i<rtn.exam.length;i++){
	    		 html.push((rtn.exam[i].description?rtn.exam[i].description:'')+'<br>');
	    	}
	    }
	    html.push('</p>');
	    html.push('<p class="des-t">'+(exam_class?exam_class:'')+'提示：');
	    html.push('</p>');
	    html.push('<p class="des-c">');
	    if(rtn.exam){
	    	for(var i=0;i<rtn.exam.length;i++){
	    		 html.push((rtn.exam[i].impression?rtn.exam[i].impression:'')+'<br>');
	    	}
	    }
	    html.push('</p>');
	    html.push('<table style="width:100%;border-bottom: 1px solid black;">');
	    html.push('<tr>');
	    var jcyc = "";
	    if(rtn.exam){
	    	for(var i=0;i<rtn.exam.length;i++){
	    		jcyc += rtn.exam[i].report?rtn.exam[i].report:'';
	    	}
	    }
	    html.push('<td style="text-align: left;">检查医生：'+jcyc+'</td>');
	    html.push('<td style="text-align: right;">报告时间：'+(report_date_time?report_date_time:'')+'</td>');
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
			a.download =  rtn.hospital_name+''+'报告单.png';
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