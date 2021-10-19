<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="添加事后点评抽样">
<s:row>
<s:table id="datatable" label="事后点评患者列表" icon="fa fa-list" autoload="false"
			sort="true" limit="10" fitwidth="true" action="ipc.sample.queryAll" 
			select="multi" >
		<s:toolbar>
			<s:button label="添加" onclick="Add();"></s:button> 
		</s:toolbar>
		<s:table.fields>
		<s:table.field name="patient_id" datatype="string" label="患者ID" sort="true"></s:table.field>
		<s:table.field name="patient_name" datatype="template" label="患者名称">
			<a href="javascript:void(0);" onclick="topatient('$[patient_id]','$[visit_id]');">$[patient_name]</a>
		</s:table.field>
		<s:table.field name="visit_id" datatype="string" label="入院次数"></s:table.field>
		<s:table.field name="sex" datatype="string" label="性别"></s:table.field>
		<s:table.field name="age" datatype="string" label="年龄"></s:table.field>
		<%-- <s:table.field name="identity" datatype="string" label="身份"></s:table.field> --%>
		<s:table.field name="charge_type" datatype="string" label="费别"></s:table.field>
		<s:table.field name="d_type" datatype="script" label="患者类型">
					if(record.d_type == '1'){
						return '住院';
					}else if(record.d_type == '3'){
						return '急诊';
					}else if(record.d_type == '2'){
						return '门诊';
					}else{
						return '测试';
					}
		</s:table.field>
		<s:table.field name="p_type" datatype="script" label="医嘱/处方">
			if(record.p_type == '1'){
				return '医嘱';
			}else if(record.p_type != '1'){
				return '处方';
			}
		</s:table.field>
		<s:table.field name="zaichu" datatype="string" label="病人住院状态"></s:table.field>
		<s:table.field name="dept_name" datatype="string" label="科室"></s:table.field>
		<%-- <s:table.field name="doctor" datatype="string" label="医生"></s:table.field> --%>
	</s:table.fields>
</s:table>
</s:row>
</s:page>
<script>
$(function(){
	query();
});
function query(){
	$("#datatable").params({
		mintime:'${param.mintime}',
		maxtime:'${param.maxtime}',
		sample_id:'${param.sample_id}',
	});
	$("#datatable").refresh();
}

function Add(){
	var select = $('#datatable').getSelected();
	var data = {};
	data.sample_id = '${param.sample_id}';
	data.mintime= '${param.mintime}';
	data.maxtime= '${param.maxtime}';
	data.select= $.toJSON(select);
	$.call("ipc.sample.addSample",data,function(rtn){
		$.closeModal();
	})
}

</script>