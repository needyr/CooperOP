<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑">
   <s:row>
        <s:form id="form" label="拦截等级配对">
            <s:toolbar>
                <s:button label="确认" onclick="save()" icon=""></s:button>
                <s:button label="取消" onclick="quxiao()" icon=""></s:button>
            </s:toolbar>
            <s:row>
                <s:textfield label="标准拦截级别" name="sys_check_level"  cols="3" value="${sys_check_level}" required="true"></s:textfield>
                <s:textfield label="标准拦截名称" name="sys_check_level_name" required="true" cols="3" value="${sys_check_level_name}"></s:textfield>
                <s:textfield label="严重级别" name="check_level" required="true" cols="3" value="${check_level}"></s:textfield>
                <s:textfield label="严重级别名称" name="check_level_name" required="true" cols="3" value="${check_level_name}"></s:textfield>
                <%-- <s:textfield label="等级星级" name="star_level" required="true" cols="2" value="${star_level}" readonly="true"></s:textfield> --%>
                <s:autocomplete label="选择产品" name="system_product_code" value="${system_product_code}" text="${pro_name}" action="hospital_common.usereason.queryProduct" cols="3" limit="10">
                    <s:option value="$[code]" label="$[name]">
                        $[code]-$[name]
                    </s:option>
                    </s:autocomplete>
                <s:textfield label="审查来源" name="source" required="true" cols="3" value="${source}"></s:textfield>
                <%-- <s:switch name="is_active" label="是否启用" ontext="开" offtext="关" offvalue="0" onvalue="1" value="${is_active}" cols="1"></s:switch> --%>
            </s:row>
        </s:form>
    </s:row>
</s:page>
<script>
$(function(){
})

function save(){
   //空表单提交验证
   if (!$("form").valid()){
       return false;
   }
   var d = $("#form").getData();
   if("${sys_check_level}"){
   	d.old_sys_check_level = "${sys_check_level}";
   	d.old_check_level = "${check_level}";
   }
   if('${sys_check_level}' != null && '${sys_check_level}'!=''){
	   $.call("hospital_common.checklevel.update",d,function(s){
	        $.closeModal(s);
	    }); 
   }else{
	   $.call("hospital_common.checklevel.insert",d,function(s){
		   if(s==0){
			   $("input[name='sys_check_level']").val("");
			   $("input[name='system_product_code']").val("");
			   $.confirm("当前填写的映射已存在！\t\n请重新填写！",function callback(e){
		            if(e==true){
		               return null;
		            }
		        });
		   }else{
			    $.closeModal(s);
		   }
       }); 
   }
}

function quxiao(){
    $.closeModal(false);
}
</script>