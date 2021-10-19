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
        <input type="hidden" value="${parent_bh }" name="parent_bh" />
        <c:if test="${not empty id}">
          <input type="hidden" value="${id }" name="id" />
          <s:row>
            <s:textfield name="id" value="${id }" disabled="true" encryption="false" label="标识" maxlength="10" required="false" cols="2"></s:textfield>
          </s:row>
        </c:if>
        <s:row>
          <s:textfield  value="${parent_bh }" disabled="true" encryption="false" label="上级标识" maxlength="50" required="false" cols="2"></s:textfield>
        </s:row>
        <s:row>
          <s:autocomplete label="表名" name="table_name" value="${table_name}" action="hospital_common.rule_maintenance.verifytables.queryallname" required="false" cols="2" limit="50">
		    <s:option value="$[name]" label="$[name]" onclick="changePro(this)">
                <p data-pro="$[product]" onclick="changePro(this)" style="width: 100%;margin:0 !important;">$[name] - $[product]</p>
		    </s:option>
		  </s:autocomplete>
            <input name="product" value="${product}" style="display: none">
        </s:row>
        <s:row>
          <s:textfield name="field" title="当唯一性校验开启以及联合校验时, 可以支持多个列的填入用英文逗号隔开" value="${field}" readonly="true" label="字段名" placeholder="当唯一性校验开启以及联合校验时, 可以支持多个列的填入用英文逗号隔开" maxlength="50" required="false" cols="2"></s:textfield>
        </s:row>
        <s:row>
          <s:select label="字段类型" name="field_type" value="${field_type }" required="false"  maxlength="50" cols="2">
			<s:option value="int" label="int"></s:option>
			<s:option value="float" label="float"></s:option>
			<s:option value="datetime" label="datetime"></s:option>
	      </s:select>
        </s:row>
        <s:row>
          <s:select label="时间格式" name="time_format" value="${time_format }" required="false"  maxlength="50" cols="2">
			<s:option value="yyyy-MM-dd" label="yyyy-MM-dd"></s:option>
			<s:option value="yyyy-MM-dd HH:mm" label="yyyy-MM-dd HH:mm"></s:option>
			<s:option value="yyyy-MM-dd HH:mm:ss" label="yyyy-MM-dd HH:mm:ss"></s:option>
			<s:option value="yyyy/MM/dd" label="yyyy/MM/dd"></s:option>
			<s:option value="yyyy/MM/dd HH:mm" label="yyyy/MM/dd HH:mm"></s:option>
			<s:option value="yyyy/MM/dd HH:mm:ss" label="yyyy/MM/dd HH:mm:ss"></s:option>
	      </s:select>
        </s:row>
        <s:row>
          <s:textfield name="float_num" value="${float_num }" encryption="false" label="小数点后位数" placeholder="字段类型为float时使用, 指定数字小数点后的位数" maxlength="50" required="false" cols="2"></s:textfield>
        </s:row>
        <s:row>
          <s:switch label="唯一性校验"  name="is_unique" value="${is_unique }" onvalue="1" offvalue="0" ontext="是" offtext="否" ></s:switch>
          <div class="cols1"><p style="margin-left:-110%"><font color="#999999">校验主表中的值是否唯一</font></p></div>
        </s:row>
        <s:row>
          <s:switch label="完整性校验"  name="is_null" value="${is_null }" onvalue="1" offvalue="0" ontext="是" offtext="否" ></s:switch>
          <div class="cols1"><p style="margin-left:-110%"><font color="#999999">校验主表中字段的值是否为空</font></p></div>
        </s:row>
        <s:row>
          <s:switch label="联合校验"  name="is_union" value="${is_union }" onvalue="1" offvalue="0" ontext="是" offtext="否" ></s:switch>
          <div class="cols1"><p style="margin-left:-110%"><font color="#999999">校验主表中记录是否在联合表中存在</font></p></div>
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


function isInteger(obj) {
	 return Math.floor(obj) === obj
}

function save(){
	var d = $("#addForm").getData();
	if('${id}'){
		$.call("hospital_common.rule_maintenance.verifyitems.update",d,function(s){
			$.closeModal(s);
		});
	}else{
		$.call("hospital_common.rule_maintenance.verifyitems.insert",d,function(s){
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