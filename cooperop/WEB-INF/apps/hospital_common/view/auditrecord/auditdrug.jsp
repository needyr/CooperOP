<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="审查药品" disloggedin="true">
	<s:row>
		<s:table id="datatable" label="审查药品列表" autoload="false" action="ipc.auditresult.druglist2" sort="true">
            <s:table.fields>
                <s:table.field name="zu" label="组"></s:table.field>
                <s:table.field name="repeat_indicator" label="长/临" datatype="script">
                	var repeat_indicator=record.repeat_indicator;
                	if(repeat_indicator == '1'){
                		return '长';
                	}
                	if(repeat_indicator == '0'){
                		return '临';
                	}
                </s:table.field>
                <s:table.field name="enter_date_time" label="下达时间"></s:table.field>
                <s:table.field name="order_text" label="医嘱内容" datatype="template"> 
                	<a href="javascript:void(0);" onclick="yaopin('$[order_code]')">$[order_text]</a>
                </s:table.field>
                <s:table.field name="dosage" label="剂量"></s:table.field>
                <s:table.field name="administration" label="途径"></s:table.field>
                <s:table.field name="frequency" label="频次"></s:table.field>
                <s:table.field name="drug_message" label="药品信息"></s:table.field>
                <s:table.field name="doctor" label="开嘱医生"></s:table.field>
                <s:table.field name="sys_order_status" label="医嘱状态" datatype="script">
                	var sys_order_status=record.sys_order_status;
                	if(sys_order_status == '0'){
                		return '<font style="color: #00a093;">新开医嘱</font>';
                	}else{
                		return '在用医嘱';
                	}
                </s:table.field>
            </s:table.fields>
        </s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		var auto_audit_id = '${param.auto_audit_id}';
		query(auto_audit_id);
	});
	function query(auto_audit_id){
		$("#datatable").params({"auto_audit_id":auto_audit_id});
		$("#datatable").refresh();
	}
	function yaopin(drugcode){
		if(drugcode){
			//打开药品说明书
			$.modal("/w/ipc/auditresult/instruction.html?his_drug_code="+drugcode,"查看药品说明书",{
		        width:"80%",
		        height:"90%",
		        callback : function(e){
		        }
			});
		}else{
			$.message("请选择药品！");
		}
	}
</script>