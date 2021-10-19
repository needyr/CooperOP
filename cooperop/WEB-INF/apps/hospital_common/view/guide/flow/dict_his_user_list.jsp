<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="用户数据查看" >
	<s:row>
		<s:form border="0" id="form" label="快速查找">
			<s:row>
				<s:textfield label="快速查找" name="sousuo" placeholder="请输入名称的关键字" cols="2"></s:textfield>
				<s:button label="查询" icon="" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="" autoload="false" action="hospital_common.flow.dicthisuserlist.query" >
			<s:table.fields>
			    <s:table.field name="pkey_id" label="上级标识" datatype="" ></s:table.field>
				<s:table.field name="p_key" label="标识" datatype="" ></s:table.field>
				<s:table.field name="db_user" label="HIS用户登录名" datatype="" ></s:table.field>
				<s:table.field name="user_id" label="用户唯一标识号" datatype="" ></s:table.field>
				<s:table.field name="user_name" label="用户名称" datatype="" ></s:table.field>
				<s:table.field name="user_dept" label="用户科室ID" datatype="" ></s:table.field>
				<s:table.field name="input_code" label="输入码" datatype="" ></s:table.field>
				<s:table.field name="mobilephone" label="手机号" datatype="" ></s:table.field>
				<s:table.field name="sex" label="性别" datatype="" ></s:table.field>
				<s:table.field name="job_role" label="工作角色" datatype="" ></s:table.field>
				<s:table.field name="anti_level" label="角色级别" datatype="" ></s:table.field>
				<s:table.field name="job_level" label="配对医生级别" datatype="" ></s:table.field>
				<s:table.field name="is_doctor" label="是否是医生" datatype="script" >
				  if(record.is_doctor == 1){
				       return '是';	    
				    }else{
				      return '否';
				    }
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
		var qdata=$("#form").getData();
		$("#datatable").params(qdata);
		$("#datatable").refresh();
	}
	
</script>