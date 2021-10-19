<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="修改药品属性" >
	<s:row>
		<s:form id="form" label="编辑药品属性">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon=""></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<input type="hidden" name="sys_p_key" value="${sys_p_key}">
			</s:row>
			<s:row>
				<input type="hidden" name="xmid" value="${xmid}">
			</s:row>
			<s:row>
				<s:textfield label="显示顺序" name="displayorder"  cols="4" value="${displayorder}"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="项目名称" name="xiangm"  cols="4" value="${xiangm}" dbl_action='zl_select_dict_sys_drug_mx_01,dict_sys_drug_mx' ></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="值" name="value"  cols="4" value="${value}" dbl_action='zl_select_dict_sys_drug_mx_02,dict_sys_drug_mx' ></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="项目单位" name="dw"  cols="4" value="${dw}" disabled="disabled"></s:textfield>
			</s:row>
			<s:row>
				<s:textarea label="项目说明" name="message" cols="4" >${message}</s:textarea>
			</s:row>
			<s:row>
				<s:textarea label="备注" name="beizhu" cols="4"  value="">${beizhu}</s:textarea>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	function cancel(){
		$.closeModal(false);
	}
	
	function save(){
		var patrn = /[`~!#$%^&_\-+=<>?"{}|,\/;'\·~！#￥%……&（）——\-+={}|《》？“”【】、；‘’，。、]/im;  
		var str = $('[name=value]').val();
		if (patrn.test(str)) {// 如果包含特殊字符
	         $.message('请勿在值中添加特殊字符,请检查!');
	     }else{
			if (!$("form").valid()){
		   		return false;
		   	}
			var tdata = $("#form").getData();
			console.log(tdata);
			tdata.drug_code = '${param.drug_code}';
			var xmid = '${param.xmid}';
			if(xmid){
				$.call("hospital_common.syscustomreview.sysdrugmx.update",tdata,function(rtn){
					$.closeModal(rtn);
				});
				/* $.call("hospital_common.dictdrug.updateCz", 
						{drug_code: '${param.drug_code}', zdy_cz: "修改药品属性"}, function(){}); */
				
			}else{
				$.call("hospital_common.syscustomreview.sysdrugmx.insert",tdata,function(rtn){
					$.closeModal(rtn);
				});
				/* $.call("hospital_common.dictdrug.updateCz", 
						{drug_code: '${param.drug_code}', zdy_cz: "新增药品属性"}, function(){}); */
			}
	     }
		
	}

</script>