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
        <c:if test="${not empty id}">
          <input type="hidden" value="${id }" name="id" />
          <s:row>
            <s:textfield name="id" value="${id }" disabled="true" encryption="false" label="标识" maxlength="10" required="false" cols="2"></s:textfield>
          </s:row>
        </c:if>
        <s:row>
          <input type="hidden" value="${parent_id }" name="parent_id" />
          <s:textfield  value="${parent_id }" disabled="true" encryption="false" label="上级标识" maxlength="50" required="false" cols="2"></s:textfield>
        </s:row>
        <s:row>
          <s:autocomplete label="表名" name="table_name" value="${table_name}" action="hospital_common.rule_maintenance.verifytables.queryallname" required="false" cols="2" limit="50">
            <s:option value="$[name]" label="$[name]">
              <p data-pro="$[product]" onclick="changePro(this)" style="width: 100%;margin:0 !important;">$[name] - $[product]</p>
            </s:option>
          </s:autocomplete>
          <input name="product" value="${product}" style="display: none">
        </s:row>
        <s:row>
          <s:textfield name="field" value="${field }" readonly="true" label="字段名" title="字段名多个英文逗号隔开, 并且需要与父表field对应位置" placeholder="字段名多个英文逗号隔开, 并且需要与父表field对应位置" maxlength="50" required="false" cols="2"></s:textfield>
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
	
	var d = $("#addForm").getData();
	if('${id}'){
		$.call("hospital_common.rule_maintenance.verifyitemchild.update",d,function(s){
			$.closeModal(s);
		});
	}else{
		$.call("hospital_common.rule_maintenance.verifyitemchild.insert",d,function(s){
			$.closeModal(s);
		});
	}
   
}

function changePro(_this){
  $('[name="product"]').val($(_this).attr("data-pro"));
}

$('[name=field]').dblclick(function(){
  var code = $('[name=field]').val();
  if($("[name=table_name]").val()){
    $.modal("/w/hospital_common/abase/table_fields.html", "添加字段", {
      height: "550px",
      width: "50%",
      code: code,
      product:$('[name="product"]').val(),
      table_name: $('[name=table_name]').val(),
      maxmin: false,
      callback : function(rtn) {
        if(rtn){
          var code = rtn.code.toString();
          $('[name=field]').val(code);
        }
      }
    });
  }else{
    $.message("请选择表")
  }
})
</script>