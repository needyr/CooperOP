<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑医嘱标签信息" ismodal="true"  disloggedin="true">
	<s:row>
		<s:form id="form" label="医嘱标签信息">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon="fa fa-save"></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<input type="hidden" name="ordertagid" value="${ordertagid}"/>
				<s:textfield label="标签编号" name="ordertagbh" required="true" cols="2" value="${ordertagbh}" ></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="标签名称" name="ordertagname" required="true" cols="3" value="${ordertagname}" ></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="标签缩写" name="ordertag_show"  cols="2" value="${ordertag_show}" ></s:textfield>
				<s:switch label="是否开启" value="${beactive}" name="beactive" onvalue="是" offvalue="否" ontext="是" offtext="否" ></s:switch>
			</s:row>
			<s:row>
				<s:textfield label="标签说明" name="ordertag_shuom" cols="3" value="${ordertag_shuom}" ></s:textfield>
			</s:row>
			<%-- <s:row>
				<s:autocomplete  id="ssks" label="项目ID" name="xmid" action="hospital_common.dict.sysdrugxm.query" limit="10" editable="false" cols="2" value="${xmid}" required="true">
					<s:option value="$[xmid]" label="$[xmid]">
						<span style="float:left;display:block;width:50px;">$[xmid]</span>
						<span style="float:left;display:block;width:50px;">$[xmbh]</span>
						<span style="float:left;display:block;width:100px;">$[xmmch]</span>
					</s:option>
				</s:autocomplete>
				<s:textfield label="项目编号" name="xmbh" disabled="disabled" value="${xmbh}"  required="true" cols="1" ></s:textfield>
			</s:row> --%>
			<s:row>
				<s:textarea label="备注" cols="3"  name="beizhu"
					rows="2">${beizhu}</s:textarea>
			</s:row>
			
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
$(function (){
	debugger
	//修改操作禁止修改
	var otid = '${param.ordertagid}';
	if(otid){
		/* $("input[name='ordertagid']").attr("disabled","disabled"); */
		$("input[name='ordertagbh']").attr("disabled","disabled");
		$("input[name='xmid']").attr("disabled","disabled");
	}
		//切换药品项目
	/* $("#ssks").change(function(){
		getdrugXM($(this).val());
	}); */
});

function save(){
	var sdata=$("#form").getData();
	if (!$("form").valid()){
		return false;
	}
	debugger
	if('${param.ordertagid}'){
		$.call("hospital_common.dict.sysorderstag.update",sdata,function(s){
		    $.closeModal();
    	});
	}else{
		$.call("hospital_common.dict.sysorderstag.insert",sdata,function(s){
			if (s==2){
				$.message("该医嘱标签已创建，请重新输入！");
			}else{
		    	$.closeModal();
			}
    	});
	}
}
/* function getdrugXM(xmid){
	 $.call("hospital_common.dict.sysdrugxm.edit",{xmid :xmid},function(rtn){
		if(rtn==null||typeof(rtn) == "undefined"){
			$("input[name='xmbh']").val("");
		}else{
			$("input[name='xmbh']").val(rtn.xmbh);
		}
	}); 
} */
function cancel(){
	$.closeModal(false);
}
</script>