<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑对接信息" disloggedin="true">
	<s:row>
		<s:form id="Mform" label="对接信息">
			<s:toolbar>
				<s:button label="确认" onclick="save()" icon=""></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<s:select label="审查分类" name="check_type" cols="3" value="${check_type}" >
					<s:option value="1" label="合理用药审查" ></s:option>
					<s:option value="2" label="自定义审查"></s:option>
				</s:select>
			</s:row>
			<s:row>
				<input type="hidden" name="sort_id_old" value="${sort_id }"/>
				<input type="hidden" name="type_old" value="${type}"/>
				<s:autocomplete id="sort_id_" label="标准类别" name="sort_id" action="hospital_common.checksort.query" limit="10" editable="false" cols="3" value="${sort_name}" required="true" placeholder="请选择标准类别">
					<s:option value="$[sort_id]" label="$[sort_name]" >$[sort_name]</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<s:textfield label="审方类别编号" name="type"  cols="3" value="${type}" required="true"></s:textfield>
				<s:textfield label="审方类别名称" name="name" required="true" cols="3" value="${name}"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		
	})

	function save(){
		//空表单提交验证
    	if (!$("form").valid()){
    		return false;
    	}
		
		var d = $("#Mform").getData();
		
		
		d.sort_name = $("#sort_id_").val();
		if(d.sort_name=='${sort_name}'){
			d.sort_id='${sort_id}';
		}

    	//新增
       $.call("hospital_common.checkresultsort.save",d,function(s){
    		$.closeModal(s);
    	}); 
	}

	//取消
    function cancel(){
    	$.closeModal(false);
    }
</script>