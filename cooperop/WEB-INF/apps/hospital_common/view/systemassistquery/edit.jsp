<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑辅助查询" ismodal="true">
	<s:row>
		<s:form id="form" label="辅助查询配置信息">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon=""></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<input type="hidden" name="id" value="${id}">
				<s:textfield label="标题" name="fz_title" required="true" cols="3" value="${fz_title}" ></s:textfield>
			</s:row>
			<s:row>
				<s:autocomplete label="地址" name="dict_sys_url_id" action="hospital_common.dict.dictsysurl.query"  value="${dict_sys_url_id}" text="${url_name} ${address}" limit="10" required="true" cols="3">
						<s:option value="$[id]" label="$[address]">$[url_name]-$[address]</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<s:textfield label="图标" name="uicon" required="false" cols="2" value="${uicon}"></s:textfield>
				预览： <button  class="btn btn-sm btn-sm btn-default " ctype="button" t="1" type="button" onclick="" cinited="cinited" style="border: 0px">
				<i class="" id="uuic"></i>
				</button>
				<s:button label="查看图标库" onclick="seeicon();"></s:button>
			</s:row>
			<s:row>
				<s:textarea label="描述" name="description" required="false" cols="3" >${description}</s:textarea>
			</s:row>
			<s:row>
				<s:switch label="是否启用" name="state" onvalue="1" offvalue="0" ontext="是" offtext="否" value="${state}"></s:switch>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
$("#uuic").attr("class", '${uicon}');
$(function(){
	$("[name='uicon']").blur(function(){
		var icon = $("[name='uicon']").val();
		$("#uuic").attr("class", icon);
	});
})

function save(){
	var sdata=$("#form").getData();
	if (!$("form").valid()){
		return false;
	}
	if(sdata.id){
		$.call("hospital_common.systemassistquery.update",sdata,function(rtn){
    		$.closeModal(rtn);
    	});
	}else{
		delete sdata.id;
		$.call("hospital_common.systemassistquery.insert",sdata,function(rtn){
    		$.closeModal(rtn);
    	});
	}
}

function seeicon(){
	$.modal("/w/crdc/ui_icons.html?ismodal=true.html","查看图标库",{
		width:"90%",
		height:"90%",
		callback : function(cb){}
	});
}

function cancel(){
	$.closeModal(false);
}
</script>