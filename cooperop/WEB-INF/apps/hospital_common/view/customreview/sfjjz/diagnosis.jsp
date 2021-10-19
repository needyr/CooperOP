<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑诊断" ismodal="true">
	<s:row>
		<s:form id="form" label="禁忌症诊断">
			<s:toolbar>
				<s:button label="药品说明书" onclick="getDrugsms()" icon=""></s:button>
				<s:button label="保存" onclick="save()" icon="glyphicon glyphicon-floppy-saved"></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<%-- <s:select label="条件" name="tiaojian" cols="1" value="${tiaojian}" >
					<s:option value="and" label="and" ></s:option>
					<s:option value="or" label="or"></s:option>
				</s:select> --%>
			</s:row>
			<s:row>
				<input type="hidden" name="fdname" value="${fdname}" />
			</s:row>
			<s:row>
				<s:textfield label="项目编号" name="xmbh"  cols="1" value="${xmbh}" dbl_action='zl_select_shengfjjz_01,shengfangzl_jjz' disabled="disabled"></s:textfield>
				<s:textfield label="项目名称" disabled="disabled" name="xmmch"  cols="1" value="${xmmch}"></s:textfield>
				<s:textfield label="公式" disabled="disabled" name="formul"  cols="1" value="${formul_name}" dbl_action='zl_select_shengfjjz_02,shengfangzl_jjz'></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="值" name="value"  cols="2" value="${value}"    dbl_action='zl_select_shengfjjz_05,shengfangzl_jjz' ></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="诊断名称" name="diagnosis_name"  cols="2" value="${diagnosis_name}" disabled="disabled"></s:textfield>
				<s:textfield label="诊断代码" name="diagnosis_code"  value="${diagnosis_code}" cols="1" disabled="disabled"></s:textfield>
			</s:row>
			<s:row>
				<%-- <s:textfield label="商品编号" name="spbh" required="true" value="${param.spbh}" cols="1"  disabled="disabled"></s:textfield> --%>
				<input name="spbh" value="${param.spbh}" hidden="true"/>
				<input name="parent_id" value="${param.parent_id}" hidden="true"/>
			</s:row>	
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	
	function save(){
		if (!$("form").valid()){
	   		return false;
	   	}
		var sdata=$("#form").getData();
		sdata.id='${param.id}';
		$.call("hospital_common.customreview.sfjjz.save_mx",sdata,function(s){
			$.closeModal(s);
    	});
		if(sdata.id){
			$.call("hospital_common.dictdrug.updateCz", 
					{drug_code: '${param.spbh}', zdy_cz: "修改禁忌症诊断"}, function(){});
		}else{
			$.call("hospital_common.dictdrug.updateCz", 
					{drug_code: '${param.spbh}', zdy_cz: "新增禁忌症诊断"}, function(){});
		}
	}
	
	//取消
    function cancel(){
    	$.closeModal(false);
    }
	
  	//打开药品说明书
	function getDrugsms(){
		var drugcode = '${param.spbh}';
		$.modal("/w/ipc/auditresult/instruction.html?his_drug_code="+drugcode,"查看药品说明书",{
	        width:"70%",
	        height:"100%",
	        callback : function(e){
	        }
		});
		
	};
</script>