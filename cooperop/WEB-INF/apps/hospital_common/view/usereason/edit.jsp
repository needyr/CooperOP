<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="添加节点" >
    <s:row>
        <s:form >
            <s:row>
                <s:form id="form" border="0" label="参数信息">
                <s:toolbar>
                    <s:button label="保存" onclick="save()"></s:button>
                    <s:button label="取消" onclick="quxiao()"></s:button>
                </s:toolbar>
                 <s:row>
                    <c:choose>
                    <c:when test="${param.usereason_product_id != '' && param.usereason_product_id != null}">
	                    <c:if test="${id == null}" >
	                    <input type="hidden" name="usereason_product_id" value="${param.usereason_product_id}"/>
	                    </c:if>
	                    <c:if test="${id !=null && id !=''}" >
	                        <input type="hidden" name="id" value="${id}"/>
	                        <s:autocomplete label="所属项目名" value="${param.usereason_product_id}" text="${usereasonproduct_name}" name="usereason_product_id" params="{&#34;sys_product_code&#34;: &#34;${param.product_code}&#34;}" action="hospital_common.usereason.queryPro" limit="10" cols="4" required="true">
                                <s:option value="$[id]" label="$[usereasonproduct_name]"></s:option>
                            </s:autocomplete>
	                    </c:if>
	                    <s:textfield label="理由名称编号" name="usereasontype_bh" value="${usereasontype_bh}" required="true" cols="4"></s:textfield>
	                    <s:textfield label="理由名称" name="usereasontype_name" value="${usereasontype_name}" cols="4" required="true"></s:textfield>

						<s:autocomplete label="医嘱审查分类" value="${apa_check_sorts_id}"
							text="${s_name}" name="apa_check_sorts_id"
							params="{&#34;product_code&#34;: &#34;${param.product_code}&#34;,&#34;usereason_product_id&#34;: &#34;${param.usereason_product_id}&#34;}"
							action="hospital_common.usereason.queryCheck" limit="10"
							cols="4" required="true">
							<s:option value="$[sort_id]" label="$[sort_name]"></s:option>
						</s:autocomplete>

								<s:switch name="beactive" label="是否启用" ontext="开" offtext="关" offvalue="0" onvalue="1" value="${beactive}" cols="2"></s:switch>
	                    <s:switch name="is_must" label="是否必填" ontext="是" offtext="否" offvalue="0" onvalue="1" value="${is_must}" cols="2"></s:switch>
                    </c:when>
                    <c:otherwise>
                    <input type="hidden" name="id" value="${id}"/>
                   
                    <s:textfield label="产品编号" name="usereasonproduct_bh" value="${usereasonproduct_bh}" required="true" cols="4"></s:textfield>
                    <s:textfield label="理由产品名称" name="usereasonproduct_name" value="${usereasonproduct_name}" cols="4" required="true"></s:textfield>
                    <s:textfield label="使用产品代码" name="system_product_code" value="${system_product_code}" cols="4" required="true"></s:textfield>    
                    <s:textfield label="使用产品名称" name="system_product_name" value="${system_product_name}" cols="4" required="true"></s:textfield> 
                    <s:switch name="beactive" label="是否启用" ontext="开" offtext="关" offvalue="0" onvalue="1" value="${beactive}" cols="1"></s:switch>
                    </c:otherwise>
                    </c:choose>
                </s:row>
                </s:form>
            </s:row>
                
        </s:form>
    </s:row>
</s:page>
<script >

/* 	$(function(){
		var temp="${param.usereason_product_id}"
		alert(temp);
	}) */

    function save() {
        if(!$("form").valid()){
            return;
        }
        var d=$("#form").getData();
        d.sys_product_code = "${param.product_code}";
        $.call("hospital_common.usereason.save", d, function(rtn) {
            if (rtn>0) {
                $.closeModal(true);
            }
        },null,{async: false});
    }
    
    function quxiao(){
        $.closeModal(false);
    }
</script>