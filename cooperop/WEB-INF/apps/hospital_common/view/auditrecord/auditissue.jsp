<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="审查结果" disloggedin="true">
	<s:row>
		<s:table id="datatable" label="审查结果列表" autoload="false" action="ipc.autoaudit.queryResult" sort="true">
            <s:table.fields>
                <s:table.field name="is_new" label="医嘱类型" datatype="script">
                	var is_new=record.is_new;
                	if(is_new == '1'){
                		return '<font color="#00a093">新开医嘱</font>';
                	}else{
                		return '在用医嘱';
                	}
                </s:table.field>
                <s:table.field name="check_result_state" label="问题等级" datatype="script">
                	var state=record.check_result_state;
                	if(state == 'Y'){
                		return '通过';
                	}
                	if(state == 'N'){
                		return '<font color="red">拦截</font>';
                	}
                	if(state == 'T'){
                		return '<font color="#63a952" >提示</font>';
                	}
                	if(state == 'B'){
                		return '<font color="red" >驳回</font>';
                	}
                </s:table.field>
                <s:table.field name="sys_level_name" label="拦截等级"></s:table.field>
                <s:table.field name="sys_sort_name" label="问题类型"></s:table.field>
                <s:table.field name="description" label="审查结果" datatype="script">
                	var description=record.description;
                	var length=description.length;
                	if(length >= 18){
                		return '<font title="'+description+'">'+description.substring(0,18)+'......</font>';
                	}else{
                		return description;
                	}
                </s:table.field>
                <s:table.field name="reference" label="数据参考"></s:table.field>
                <s:table.field name="create_time" label="插入时间"></s:table.field>
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
</script>