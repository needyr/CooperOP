<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<style>
.form-horizontal .row-fluid>.cols, .form-horizontal .row-fluid>.cols1{
	width: 25% !important;
}
.form-horizontal .row-fluid>.cols1{
	width: 25% !important;
}
.form-horizontal .row-fluid>.cols2{
	width: 50% !important;
}
.form-horizontal .row-fluid>.cols3{
	width: 75% !important;
}
.form-horizontal .row-fluid>.cols4{
	width: 100% !important;
}
</style>
<s:page title="编辑用量问题" ismodal="true">
	<s:row>
		<s:form id="form" label="用量问题">
			<s:toolbar>
				<s:button label="药品说明书" onclick="getDrugsms()" icon=""></s:button>
				<s:button label="保存" onclick="save()" icon=""></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<s:select label="和其他行的条件" name="tiaojian" cols="1" value="${tiaojian}" >
					<s:option value="and" label="and" ></s:option>
					<s:option value="or" label="or"></s:option>
				</s:select>
			</s:row>
			<s:row>
				<s:autocomplete  id="" label="问题分类" name="apa_check_sorts_id" action="hospital_common.auditset.checksort.queryListByIpc" limit="10" editable="false" cols="2" value="${sort_name}" required="true">
					<s:option value="$[sort_id]" label="$[sort_name]">
						$[sort_name]
					</s:option>
				</s:autocomplete>
				<s:autocomplete  id="" label="问题等级" name="sys_check_level" action="hospital_common.auditset.checklevel.queryListByIpc" limit="10" editable="false" cols="1" value="${sys_check_level_name}" required="true">
					<s:option value="$[sys_check_level]" label="$[sys_check_level_name]">
						$[sys_check_level_name]
					</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<input type="hidden" name="fdname" value="${fdname}" />
				<s:textfield label="年龄段" name="nianl_c"  cols="1" disabled="disabled" value="${nianl_c}" dbl_action='zl_select_sys_shengfyl_06,sys_shengfangzl_yongl'></s:textfield>
				<c:if test="${not empty nianl_unit}">
				    <s:select name="nianl_unit" label="年龄单位" cols="1" value="${nianl_unit}">  
						<s:option label="岁" value="岁"></s:option>
						<s:option label="月" value="月"></s:option>
						<s:option label="天" value="天"></s:option>
					</s:select>
				</c:if>
				<c:if test="${empty nianl_unit}">
				    <s:select name="nianl_unit" label="年龄单位" cols="1" value="岁">  
						<s:option label="岁" value="岁"></s:option>
						<s:option label="月" value="月"></s:option>
						<s:option label="天" value="天"></s:option>
					</s:select>
				</c:if>
				<s:textfield label="年龄范围" name="nianl_start"  cols="1" value="${nianl_start}"></s:textfield>
				<s:textfield label="至" name="nianl_end"  cols="1" value="${nianl_end}"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="体重段" name="weight_c"  cols="1" disabled="disabled" value="${weight_c}" dbl_action='zl_select_shengfangzl_weight,shengfangzl'></s:textfield>
				<c:if test="${not empty weight_unit}">
				<s:textfield label="体重单位" name="weight_unit"  cols="1" disabled="disabled" value="${weight_unit}" dbl_action=''></s:textfield>
				</c:if>
				<c:if test="${empty weight_unit}">
				<s:textfield label="体重单位" name="weight_unit"  cols="1" disabled="disabled" value="Kg" dbl_action=''></s:textfield>
				</c:if>
				<s:textfield label="体重范围" name="weight_start"  cols="1" value="${nianl_start}"></s:textfield>
				<s:textfield label="至" name="weight_end"  cols="1" value="${nianl_end}"></s:textfield>
				<%-- <s:select name="weight_end" label="体重单位" cols="1" value="${3}" action="">
					<s:option value="$[]" label="$[]"></s:option>
				</s:select> --%>
			</s:row>
			<s:row>
			    <s:textfield label="给药方式like" name="routename"  cols="2" disabled="disabled" value="${routename}" dbl_action='zl_select_sys_shengfyl_07,sys_shengfangzl_yongl' ></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="项目编号" name="xmbh"  cols="1" disabled="disabled" value="${xmbh}" dbl_action='zl_select_sys_shengfyl_01,sys_shengfangzl_yongl'></s:textfield>
				<s:textfield label="项目名称" disabled="disabled" name="xmmch"  cols="1" value="${xmmch}"></s:textfield>
				<input  hidden="true"  name="xmid"  value="${xmid}">
				<s:textfield label="公式"  disabled="disabled" name="formul"  cols="1" value="${formul_name}" dbl_action='zl_select_sys_shengfyl_02,sys_shengfangzl_yongl'></s:textfield>
				<s:textfield label="值" name="value"  cols="1" value="${value}" ></s:textfield>
			</s:row>
			<s:row>
				<s:textarea label="或者计算公式" name="calculation_formula" cols="4" placeholder="请输入计算公式"  rows="" value="">${calculation_formula}</s:textarea>
			</s:row>
			<s:row>
				<s:textarea label="警示消息" name="nwarn_message" cols="4" placeholder="请输入警示提示消息内容"  rows="" value="">${nwarn_message}</s:textarea>
			</s:row>
			<s:row>
				<s:textfield label="备注" name="beizhu"  cols="4" value="${beizhu}"></s:textfield>
			</s:row>
			<s:row>
				<input name="sys_p_key" value="${param.drug_code}" hidden="true"/>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
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
			sdata.id='${param.id}';
			if('${sort_name}' == sdata.apa_check_sorts_id){
				sdata.apa_check_sorts_id = '${apa_check_sorts_id}';
			} 
			if('${sys_check_level_name}' == sdata.sys_check_level){
				sdata.sys_check_level = '${sys_check_level}';
			} 
			$.call("hospital_common.syscustomreview.syssfyongl.save",sdata,function(s){
				$.closeModal(s);
	    	});
			/* if(sdata.id){
				$.call("hospital_common.dictdrug.updateCz", 
						{drug_code: '${param.spbh}', zdy_cz: "修改用量问题"}, function(){});
			}else{
				$.call("hospital_common.dictdrug.updateCz", 
						{drug_code: '${param.spbh}', zdy_cz: "新增用量问题"}, function(){});
			} */
	     }
	}
	
	//取消
    function cancel(){
    	$.closeModal(false);
    }
	
  //打开药品说明书
	function getDrugsms(){
		var drugcode = '${param.drug_code}';
		$.modal("/w/hospital_common/additional/sysinstruction.html?drug_code="+drugcode,"查看药品说明书",{
	        width:"70%",
	        height:"100%",
	        callback : function(e){
	        }
		});
		
	};
</script>