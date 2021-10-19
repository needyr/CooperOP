<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="客户端升级版本信息" disloggedin="true">
	<s:row>
		<s:form id="form" label="快速查找">
			<s:row>
				<s:textfield label="版本号" name="version_no"></s:textfield>
				<s:checkbox name="d_type_query" cols="1">
					<s:option value="1" label="住院"></s:option>
					<s:option value="2" label="门诊"></s:option>
					<s:option value="3" label="急诊"></s:option>
					<s:option value="4" label="护士"></s:option>
					<s:option value="5" label="医保结算"></s:option>
				</s:checkbox>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
	    <s:table id="datatable" label="客户端升级版本信息" autoload="false"  action="hospital_common.imversion.queryVersionAll"  sort="true">
	        <s:toolbar>
				<s:button label="抓取数据" onclick="capture()" icon="fa fa-plus"></s:button>
				<s:button label="查询" onclick="query()" icon="fa fa-search"></s:button>
			</s:toolbar>
			<s:table.fields>
		        <s:table.field name="ip_address" label="IP地址" ></s:table.field>
		        <s:table.field name="d_type" label="工作站类型" datatype="script">
		        	var d = record.d_type;
		        	if(d == 1){
		        		return '住院';
		        	}else if (d == 2){
		        		return '门诊';
		        	}else if (d == 3){
		        		return '急诊';
		        	}else if (d == 4){
		        		return '护士';
		        	}else if (d == 5){
		        		return '医保结算';
		        	}else {
		        		return '其他';
		        	}
		        	
		        </s:table.field>
		        <s:table.field name="dept_name" label="科室名称" ></s:table.field>
		        <s:table.field name="version" label="版本" ></s:table.field>
		        <s:table.field name="state" label="升级状态" datatype="script">
		        	var d = record.state;
		        	if(d == 1){
		        		return '正常';
		        	}else if (d == 2){
		        		return '<font style="color:red">升级中</font>';
		        	}
		        </s:table.field>
			</s:table.fields>
	    </s:table>
	</s:row>
</s:page>
<script type="text/javascript">
$(function (){
	
});
function query(){
	var qdata=$("#form").getData();
	$("#datatable").params(qdata);
	$("#datatable").refresh();
}

function capture(){
	$.call("hospital_common.imversion.capture",{},function(s){
		query();
	});
}
</script>