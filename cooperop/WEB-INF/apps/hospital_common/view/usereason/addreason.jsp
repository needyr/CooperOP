<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="用药理由">
    <s:row>
        <s:form id="form" label="参数信息">
         <s:toolbar>
            <s:button label="保存" onclick="save()"></s:button>
            <s:button label="取消" onclick="quxiao()"></s:button>
         </s:toolbar>
         <s:row>
            <s:textfield name="usereasondetail_bh" label="编号" value="${usereasondetail_bh}" cols="4" required="true"></s:textfield>
            <s:textfield name="usereasondetail_name" label="完整名称" value="${usereasondetail_name}" cols="4" required="true"></s:textfield>
            <s:textfield name="usereasondetail_jiancheng" label="简称" value="${usereasondetail_jiancheng}" cols="4" required="true"></s:textfield>
            <c:choose>
            <c:when test="${id !='' && id!=null}">
                <s:textfield name="id" label="理由编号" readonly="true" cols="4" required="true">${id}</s:textfield>
                <s:autocomplete label="所属问题" value="${param.parent_name}" name="usereason_type_id" params="{&#34;sys_product_code&#34;: &#34;${param.product_code}&#34;}" action="hospital_common.usereason.queryType" limit="10" cols="4" required="true">
                    <s:option value="$[id]" label="$[usereasontype_name]"></s:option>
                </s:autocomplete>
            </c:when>
            <c:otherwise>
                <s:textfield name="usereason_type_id" label="所属问题" value="${param.parent_name}" readonly="true" cols="4"></s:textfield>
            </c:otherwise>
            </c:choose>
            <s:switch name="beactive" label="是否启用" ontext="开" offtext="关" offvalue="0" onvalue="1" value="${beactive}" cols="1" ></s:switch>
        </s:row>
        </s:form>
   </s:row>
</s:page>
<script>

 function save(){
    //空表单提交验证
    if (!$("form").valid()){
        return false;
    }
    var d = $("#form").getData();
    if(d.usereason_type_id == '${param.parent_name}'){
        d.usereason_type_id='${param.parent_id}';
    }
    //新增
   $.call("hospital_common.usereason.saveDetail",d,function(s){
        $.closeModal(s);
    }); 
} 

//取消
function quxiao(){
    $.closeModal(false);
}
</script>