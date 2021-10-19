<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="cn.crtech.cooperop.bus.util.GlobalVar"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<style>
    .mytable tr td {
        border-left: 1px solid black;
    }

    .mytable tr th {
        border-left: 1px solid black;
        border-bottom: 1px solid black;
        text-align: center;
    }
    .textbox {
        font-size: 15px;
        font-weight: 500;
    }
</style>
<s:page title="" disloggedin="true">
    <div id="tmp_box"><div>
</s:page>
<script src="/theme/plugins/html2canvas.js" type="text/javascript"></script>
<%--<script src="/themes/classic4.0/plugins/html2canvas.js" type="text/javascript"></script>--%>
<script type="text/javascript">
    $(function(){
        var s_time="${param.s_time}";
        var e_time="${param.e_time}";
        var patient_id="${param.patient_id}";
        var visit_id="${param.visit_id}";

        $.call('hospital_common.patient.exportDignosis',{
            patient_id:patient_id,
            visit_id:visit_id,
            s_time:s_time,
            e_time:e_time,
            limit:"-1"
        },function(rtn){
            var html = [];
            html.push('<div style="padding: 20px;" id="my_diagnosis_msg">');
            html.push('<h2 style="text-align: center;font-size:14px;">'+rtn.hospital_name+'</h2>');
            html.push('<h1 style="text-align: center;font-size:18px;">诊断记录单</h1>');
            html.push('<div><table style="width:100%;" class="textbox"><tr>');
            html.push('<td>姓名：'+rtn.patient.patient_name+'</td>');
            html.push('<td>床号：'+(rtn.patient.bed_no?rtn.patient.bed_no:'')+'</td>');
            html.push('<td>入院科室：'+(rtn.patient.dept_in_name?rtn.patient.dept_in_name:'')+'</td>');
            html.push('<td>病人ID：'+("${param.patient_id}"?"${param.patient_id}":'')+'</td>');
            html.push('<td>住院号：'+rtn.patient.patient_no+'</td>');
            html.push('</tr></table></div>');
            html.push('<table style="width:100%;border: 1px solid black;border-collapse: collapse;" class="mytable">');
            html.push('<tr>');
            html.push('<th width="10%">诊断类别</th>');
            html.push('<th width="10%">诊断序号</th>');
            html.push('<th width="15%">诊断编码</th>');
            html.push('<th width="45%">诊断名称</th>');
            html.push('<th width="20%">诊断日期</th>');
            html.push('</tr>');
            if(rtn.diagnosis){
                for (var i=0;i<rtn.diagnosis.length;i++){
                    html.push('<tr>');
                    html.push('<td>'+(rtn.diagnosis[i].diagnosisclass_name?rtn.diagnosis[i].diagnosisclass_name:'')+'</td>');
                    html.push('<td>'+(rtn.diagnosis[i].diagnosis_no?rtn.diagnosis[i].diagnosis_no:'')+'</td>');
                    html.push('<td>'+(rtn.diagnosis[i].diagnosis_code?rtn.diagnosis[i].diagnosis_code:'')+'</td>');
                    html.push('<td>'+(rtn.diagnosis[i].diagnosis_desc?rtn.diagnosis[i].diagnosis_desc:'')+'</td>');
                    html.push('<td>'+(rtn.diagnosis[i].diagnosis_date?rtn.diagnosis[i].diagnosis_date:'')+'</td>');
                    html.push('</tr>');
                }
            }
            html.push('</table>');
            html.push('</div>');
            $('#tmp_box').append(html.join(''));
            new html2canvas($('#tmp_box')[0],{
                scale: 2
            }).then(canvas => {
                // canvas为转换后的Canvas对象
                var imgUri = canvas.toDataURL("image/png").replace("image/png", "image/octet-stream"); // 获取生成的图片的url
                var a = document.createElement("a");
                a.download =  rtn.hospital_name+''+'患者诊断记录单.png';
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