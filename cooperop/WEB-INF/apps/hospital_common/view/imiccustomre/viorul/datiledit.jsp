<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>--%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %> 
<s:page title="医保项目违规驳回规则" ismodal="true">
	<s:row>
		<s:form id="form" label="医保项目驳回规则">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon=""></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<s:switch label="是否启用" value="${empty state? 1: state}" name="state" onvalue="1" offvalue="0"  ontext="是" offtext="否"></s:switch>
				<s:select label="类型" name="d_type"  value="${d_type}" >
					<s:option value="1" label="住院" ></s:option>
					<s:option value="2" label="门诊"></s:option>
				</s:select>
				<s:autocomplete label="医保体系" name="interface_type" action="hospital_common.imiccustomre.dictYBType" editable="false" text="${interface_type_name}" value="${empty interface_type? '全部': interface_type}" required="true">
					<s:option value="$[interface_type]" label="$[interface_type_name]">
						$[interface_type_name]
					</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<s:autocomplete  id="" label="问题分类" name="apa_check_sorts_id" action="hospital_common.auditset.checksort.queryListByImic" limit="10" editable="false" cols="2" text="${thirdt_name}" value="${apa_check_sorts_id}" required="true">
					<s:option value="$[thirdt_code]" label="$[thirdt_name]">
						$[thirdt_code] - $[thirdt_name]
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
				<s:textarea label="警示消息" name="nwarn_message" cols="4" placeholder="请输入警示提示消息内容"  required="true" value="">${nwarn_message}</s:textarea>
			</s:row>
			<s:row>
				<s:textfield label="备注" name="beizhu"  cols="4" value="${beizhu}"></s:textfield>
				<input type="hidden" name="item_code" id="item_code" value="${item_code}"></input>
			</s:row>
		</s:form>
	</s:row>
	<script type="text/javascript">
	$(function(){
		var item_code = '${param.item_code}'
		if(item_code){
			$("#item_code").val('${param.item_code}');
			$("#check_level")[0].value="";
		}
	})
	
	function save(){
		//var patrn = /[`~!#$^&_\-+=<>?"{}|,\/;'\·~！#￥……&（）——\-+={}|《》？“”【】、；‘’，。、]/im;  
		//var str = $('[name=value]').val();
		//if (patrn.test(str)) {// 如果包含特殊字符
	         //$.message('请勿在值中添加特殊字符,请检查!');
	     //}else{
			if (!$("form").valid()){
		   		return false;
		   	}
			var sdata=$("#form").getData();
			sdata.id='${param.id}';
			sdata.interface_type_name = $('[name="interface_type"]').val();
			if('${sort_name}' == sdata.apa_check_sorts_id){
				sdata.apa_check_sorts_id = '${apa_check_sorts_id}';
			} 
			if('${sys_check_level_name}' == sdata.sys_check_level){
				sdata.sys_check_level = '${sys_check_level}';
			} 
			$.call("hospital_common.imiccustomre.viorul.save",sdata,function(s){
				$.closeModal(s);
	    	});
	     //}
	}
	
	//取消
    function cancel(){
    	$.closeModal(false);
    }
	</script>
</s:page>