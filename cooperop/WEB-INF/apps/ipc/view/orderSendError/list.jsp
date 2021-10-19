<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="医嘱推送药师端失败查询" disloggedin="true">
<style type="text/css">
	.choose{background-color: #d3d3d3 !important;}
</style>
	<s:row>
		<s:form id="form" >
			<s:row>
				<s:datefield label="开始时间" name="start_time" format="yyyy-MM-dd HH:mm:ss" autocomplete="off"/>
				<s:datefield label="截止时间" name="end_time" format="yyyy-MM-dd HH:mm:ss" autocomplete="off"/>
			    <s:textfield label="快速查找" name="filter" placeholder="请输入开嘱医生姓名或者编号" cols="2"/>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="医嘱推送药师端失败查询" autoload="false" action="ipc.order_send_error.query" sort="true">
            <s:toolbar><s:button label="查询" icon="fa fa-search" onclick="query()"/></s:toolbar>
            <s:table.fields>
                <s:table.field name="auto_audit_id" label="合理用药审查ID"/>
                <s:table.field name="doctor_no" label="开嘱医生" datatype="script">
                	return record.doctor_name + '(' +record.doctor_no+')';
                </s:table.field>
                <s:table.field name="action" label="执行方法"/>
                <s:table.field name="create_time" label="推送时间"/>
                <s:table.field name="update_time" label="更新时间"/>
                <s:table.field name="send_times" label="推送次数"/>
                <s:table.field name="hospital_id" label="医院id"/>
                <s:table.field name="p_type" label="开单类型" datatype="script">
                	var p_type=record.p_type;
                	var d_type=record.d_type;
                	if(p_type==1){p_type='医嘱';}
                	else if(p_type==2){p_type='处方';}
                	if(d_type==1){d_type='住院';}
                	else if(d_type==2){d_type='急诊';}
                	else if(d_type==3){d_type='门诊';}
                	return '<a onclick="druglist(\''+record.auto_audit_id+'\', this);">'+d_type + p_type+ '</a>';
                </s:table.field>
                <s:table.field name="patient_name" label="病人" datatype="template">
                	<a href="javascript:void(0);" onclick="toPatient('$[patient_id]','$[visit_id]', this)">$[patient_name]</a>
                </s:table.field>
                <s:table.field name="patient_dept_name" label="开嘱科室"/>
                <s:table.field name="caozuo" label="操作" datatype="script">
                	var fhtml=[] ;
                	var state=record.state;
                	if(state=='HL_Y'){return '√';}
                	else{
                		fhtml.push('<a href="javascript:void(0)" onclick="auditDetail(\''+ record.common_id +'\')">');
                		fhtml.push('详情</a>');
                	}
                	return fhtml.join('');
                </s:table.field>
            </s:table.fields>
        </s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		query();
	});
	
	function query(){
		var data=$('#form').getData();
		$("#datatable").params(data);
		$("#datatable").refresh();
	}
	
	function auditDetail(common_id){
		  $.modal("/w/hospital_common/showturns/show.html?id="+ common_id,"查看审查详情",{
	    	width: '900px',
	    	height: '645px',
	        callback : function(e){}
	    });
	}
	  
	function toPatient(patient_id, visit_id, _this){
		$(_this).css('color', '#900987');
		$.modal('/w/hospital_common/showturns/patientdetail.html?patient_id='+patient_id+'&&visit_id='+visit_id+'',"查看审查详情",{
		  	width: '900px',
		  	height: '550px',
		      callback : function(e){}
		  });
	}
	
	function druglist(auto_audit_id, _this){
		$(_this).css('color', '#900987');
		$.modal('/w/ipc/auditresult/druglist.html?auto_audit_id='+ auto_audit_id,"查看医嘱",{
		  	width: '900px',
		  	height: '550px',
		    callback : function(e){}
		});
	}
	
	Date.prototype.Format=function (fmt) { //author: meizz 
	    var o={
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "H+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt=fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt=fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
</script>