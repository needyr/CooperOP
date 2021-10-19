<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="部门数据查看" >
	<s:row>
		<s:form border="0" id="form" label="快速查找">
			<s:row>
				<s:textfield label="快速查找" name="sousuo" placeholder="请输入科室名称的关键字" cols="2"></s:textfield>
				<s:button label="查询" icon="" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="" autoload="false" action="hospital_common.flow.dicthisdeptment.query" >
			<s:table.fields>
			    <s:table.field name="pkey_id" label="上级标识" datatype="" ></s:table.field>
				<s:table.field name="p_key" label="标识" datatype="" ></s:table.field>
				<s:table.field name="dept_code" label="科室代码" datatype="" ></s:table.field>
				<s:table.field name="dept_name" label="科室名称" datatype="" ></s:table.field>
				<s:table.field name="dept_attr" label="科室属性" datatype="" ></s:table.field>
				<s:table.field name="outp_or_inp" label="门诊住院科室标志" datatype="" ></s:table.field>
				<s:table.field name="jianchen" label="科室简称" datatype="" ></s:table.field>
				<s:table.field name="input_code" label="入院科室编码" datatype="" ></s:table.field>
				<s:table.field name="is_operation" label="是否手术" datatype="script" >
				   if(record.IS_Operation == 1){
				       return '是';	    
				    }else{
				      return '否';
				    }
				</s:table.field>
				<s:table.field name="bed_count" label="床位总数" datatype="" ></s:table.field>
				<s:table.field name="is_active" label="是否活动" datatype="script" >
				  if(record.is_active == 1){
				       return '是';	    
				    }else{
				      return '否';
				    }
				</s:table.field>
				<s:table.field name="area_code" label="地区编号" datatype="" ></s:table.field>
				<s:table.field name="area_name" label="地区名字" datatype="" ></s:table.field>
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