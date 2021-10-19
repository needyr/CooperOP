<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="加密" disloggedin="true" flag="">
  <s:row>
    <s:form id="Form" label="加密" action="">
      <s:toolbar>
        <s:button label="确认" onclick="save()" icon=""></s:button>
        <s:button label="清空" onclick="empty()" icon=""></s:button>
	    <!--<s:button label="取消" onclick="cancel()" icon=""></s:button> -->
      </s:toolbar>
      <s:row>
          <s:row>
            <s:textfield id="hospital_id" name="hospital_id" value="" encryption="false" label="医院ID" maxlength="50" required="false" cols="2"></s:textfield>
          </s:row>
          <s:row>
            <s:textfield id="hospital_name" name="hospital_name" value="" encryption="false" label="医院名称" maxlength="50" required="false" cols="2"></s:textfield>
          </s:row>
          <s:row>
            <s:textfield id="encrypt_result" name="encrypt_result" value="" disabled="true" encryption="false" label="加密结果" maxlength="10" required="false" cols="2"></s:textfield>
          </s:row>
      </s:row>
    </s:form>
  </s:row>
</s:page>
<script type="text/javascript">

function save(){
	var d = $("#Form").getData();
	$.call("hospital_common.guide.flow.encryptmd5",d,function(s){
		document.getElementById("encrypt_result").value = s.encrypt_result;
	});
}

function empty(){
	document.getElementById("hospital_id").value = "";
	document.getElementById("hospital_name").value = "";
	document.getElementById("encrypt_result").value = "";
}



</script>