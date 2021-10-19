<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="编辑" disloggedin="true" flag="">
  <s:row>
    <s:form id="addForm" label="信息" action="">
      <s:toolbar>
        <s:button label="确认" onclick="save()" icon=""></s:button>
	    <s:button label="取消" onclick="cancel()" icon=""></s:button>
      </s:toolbar>
      <s:row>
		  <c:if test="${not empty upgrade_id}">
        <input type="hidden" value="${upgrade_id }" name="upgrade_id" />
		  </c:if>
        <c:if test="${empty msg }">
        <c:if test="${not empty upgrade_id}">
          <s:row>
            <s:textfield name="upgrade_id" value="${upgrade_id }" disabled="true" encryption="false" label="产品标识" maxlength="10" required="false" cols="2"></s:textfield>
          </s:row>
        </c:if>
          <s:row>
              <s:textfield label="产品名称" value="${product_name}" name="product_name" cols="2" required="true"></s:textfield>
		</s:row>
	    <s:row>
	    <s:datefield label="时间" value="${time }" name="time" cols="2" required="true"></s:datefield>
	    </s:row>
		<s:row>
		<s:textfield name="version" value="${version }" encryption="false" label="版本" maxlength="50" required="true" cols="2"></s:textfield>
		</s:row>
        </c:if>
		<s:row>
		<s:textarea name="update_content" value="${update_content }" encryption="false" label="更新内容" maxlength="2550" required="true" cols="2" height="200px"></s:textarea>
		</s:row>
      </s:row>
    </s:form>
  </s:row>
</s:page>
<script type="text/javascript">

//取消
function cancel(){
	$.closeModal(false);
}
function save(){
	if (!$("form").valid()){
		return false;
	}
	var d = $("#addForm").getData();
	if('${upgrade_id}'){
		$.call("hospital_common.version_message.ycprojectupgradelog.update",d,function(s){
			$.closeModal(s);
		});
	}else{
		$.call("hospital_common.version_message.ycprojectupgradelog.insert",d,function(s){
			$.closeModal(true);
		});
	}

}


</script>
