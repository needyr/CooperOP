<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="流程错误日志查看" >
<style type="text/css">
.open {
    background-color: #56d0ff;
    font-size: 12px;
    border-radius: 20px;
    padding: 2px;
}

.complete {
    background-color: #ddb133;
    font-size: 12px;
    border-radius: 20px;
    padding: 2px;
}

a {
	cursor: pointer;
}
</style>
<s:row>
<s:form id="dataform">
	<s:row>
		<s:textfield placeholder="检索流程名称" name="name"></s:textfield>
		<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
	</s:row>
</s:form>

</s:row>
<s:row>
	<s:table action="hospital_common.guide.flow.queryFlowErrorLog" sort="true" autoload="false" fitwidth="true" label="日志查看"  id="datatable">
		<s:table.fields>
			<s:table.field name="name" label="流程名称" ></s:table.field>
			<s:table.field name="error_title" label="错误标题" datatype="script">
				var error_msg = record.error_msg.replace(/\"/g,"\/\'");
				return '<a data-msg="'+error_msg+'" onClick="mx(this)" title="查看详情">'+record.error_title+'</a>'
			</s:table.field>
			<s:table.field name="create_time" sort="true" label="操作时间" datatype="datetime" format="yyyy-MM-dd HH:mm:ss"></s:table.field>
		</s:table.fields>
	</s:table>
</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		query();
	});
	
	function query() {
		var data = $('#dataform').getData();
		$("#datatable").params(data);
		$("#datatable").refresh();
	}
	
	function mx(info){
		var msg = $(info).attr('data-msg').replace(/\/\'/g,"\"");
		var msgs = eval ("(" + msg + ")");
		var error = "";
		for(var i=0;i<msgs.stackTrace.length;i++){
			error += msgs.stackTrace[i].className + "." + msgs.stackTrace[i].methodName + "(" + msgs.stackTrace[i].fileName + ":" + msgs.stackTrace[i].lineNumber + ")<br>"
		}
		layer.open({
		  type: 1, 
		  title: '信息',
		  area: ['80%', '80%'],
		  skin: 'layui-layer-lan',
		  content:'<div style="margin-left:20px;font-size:15px">'
			  + "cause:" + msgs.cause + ":"
			  + msgs.message + "<br>"
			  + '<div style="margin-left:40px;font-size:15px">'+error+'</div>'
			  +'</div>'
		});
	}
</script>