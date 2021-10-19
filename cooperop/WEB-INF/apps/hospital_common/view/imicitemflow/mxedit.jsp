<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="规则定义" ismodal="true">
	<s:row>
		<s:form id="form" label="规则定义">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon=""></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<s:select label="和其他行的条件" name="tiaojian"  value="${tiaojian}" >
					<s:option value="and" label="and" ></s:option>
					<s:option value="or" label="or"></s:option>
				</s:select>
				<s:select label="类型" name="d_type"  value="${d_type}" >
					<s:option value="1" label="住院" ></s:option>
					<s:option value="2" label="门诊"></s:option>
				</s:select>
			</s:row>
			<s:row>
				<s:autocomplete  id="" label="问题分类" name="apa_check_sorts_id" action="hospital_common.auditset.checksort.queryListByImic" limit="10" editable="false" cols="2" text="${thirdt_name}" value="${apa_check_sorts_id}" required="true">
					<s:option value="$[thirdt_code]" label="$[thirdt_name]">
						$[thirdt_name]
					</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<s:autocomplete  id="check_level" label="问题等级" name="sys_check_level" action="hospital_common.imiccustomre.ybxianz.queryListByImic" limit="10" editable="false" cols="2" text="${sys_check_level_name}" value="${sys_check_level}" required="true">
					<s:option value="$[sys_check_level]" label="$[sys_check_level_name]">
						<span>$[sys_check_level]</span>&nbsp;&nbsp;&nbsp;
						<span>$[sys_check_level_name]</span>
					</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<s:textfield label="年龄段" name="nianl_c"  cols="1" disabled="disabled" value="${nianl_c}" dbl_action='zl_select_yb_item_flow_01,yb_shengfangzl_item_flow'></s:textfield>
				<s:textfield label="年龄范围" name="nianl_start"  cols="1" value="${nianl_start}"></s:textfield>
				<s:textfield label="至" name="nianl_end"  cols="1" value="${nianl_end}"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="项目编号" name="xmbh"  cols="1" disabled="disabled" value="${xmbh}" dbl_action='zl_select_yb_item_flow_02,yb_shengfangzl_item_flow'></s:textfield>
				<s:textfield label="项目名称" disabled="disabled" name="xmmch"  cols="1" value="${xmmch}"></s:textfield>
				<input  hidden="true"  name="xmid"  value="${xmid}">
				<input type="hidden" name="fdname" value="${fdname}" />
				<s:textfield label="公式"  disabled="disabled" name="formul"  cols="1" value="${formul_name}" dbl_action='zl_select_yb_item_flow_03,yb_shengfangzl_item_flow'></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="值" name="value"  cols="2" value="${value}"></s:textfield>
			</s:row>
			<s:row>
				<s:textarea label="警示消息" name="nwarn_message" cols="4" placeholder="请输入警示提示消息内容"  rows="" value="">${nwarn_message}</s:textarea>
			</s:row>
			<s:row>
				<s:textfield label="备注" name="beizhu"  cols="4" value="${beizhu}"></s:textfield>
				<input type="hidden" name="parent_id" id="parent_id" value="${parent_id}"></input>
				<input type="hidden" name="id" id="id" value="${id}"></input>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		var id = '${param.id}'
		var parent_id = '${param.parent_id}'
		if(id == ''){
			$("#parent_id").val('${param.parent_id}');
			$("#check_level")[0].value="";
		}
	});
	function save(){
		var patrn = /[`~!#$^&_\-+=<>?"{}|,\/;'\·~！#￥……&（）——\-+={}|《》？“”【】、；‘’，。、]/im;  
		var str = $('[name=value]').val();
		if (patrn.test(str)) {// 如果包含特殊字符
	         $.message('请勿在值中添加特殊字符,请检查!');
	    }else{
			if (!$("form").valid()){
		   		return false;
		   	}
			var sdata=$("#form").getData();
			/* sdata.parent_id='${param.parent_id}'; */
			if('${sort_name}' == sdata.apa_check_sorts_id){
				sdata.apa_check_sorts_id = '${apa_check_sorts_id}';
			} 
			if('${sys_check_level_name}' == sdata.sys_check_level){
				sdata.sys_check_level = '${sys_check_level}';
			} 
			$.call("hospital_common.imicitemflow.save",sdata,function(s){
				$.closeModal(s);
	    	});
	    }
	};
	//取消
    function cancel(){
    	$.closeModal(false);
    };
</script>