<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="编辑" disloggedin="true" flag="">
  <s:row>
    <s:form id="addForm" label="编辑" action="">
      <s:toolbar>
        <s:button label="确认" onclick="save()" icon=""></s:button>
	    <s:button label="取消" onclick="cancel()" icon=""></s:button>
      </s:toolbar>
      <s:row>
        <c:if test="${not empty type_id}">
          <input type="hidden" value="${type_id }" name="type_id" />
          <s:row>
            <s:textfield name="type_id" value="${type_id }" disabled="true" encryption="false" label="更新类型标识" maxlength="10" required="true" cols="2"></s:textfield>
          </s:row>
        </c:if>
        
        <s:row>
          <s:textfield name="type_name" value="${type_name }" encryption="false" label="更新类型名称" maxlength="50" required="true" cols="2"></s:textfield>
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
	if('${type_id}'){
		$.call("hospital_common.version_message.ycupgradetypedict.update",d,function(s){
			$.closeModal(s);
		});
	}else{
		$.call("hospital_common.version_message.ycupgradetypedict.insert",d,function(s){
			$.closeModal(true);
		});
	}
   
}


</script>