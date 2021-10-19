<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑存储过程信息" ismodal="true">
	<s:row>
		<s:form id="form" label="链接信息">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon="fa fa-save"></s:button>
				<s:button label="取消" onclick="cancel()" icon="fa fa-times"></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield label="处理方法编码" name="proc_code" required="true" cols="1" value="${proc_code}"></s:textfield>
				<%-- <s:textfield label="处理方法名称" name="proc_name" cols="1" value="${proc_name}" required="true"></s:textfield> --%>
				<s:switch label="状态" name="state" onvalue="1" offvalue="0" ontext="启用" offtext="停用" value="${state}"></s:switch>
			</s:row>
			<s:row>
				<s:textfield label="处理方法名称" name="proc_name_cn" value="${proc_name_cn}" required="true"></s:textfield>
				<s:autocomplete label="数据库连接" name="db_code" action="hospital_common.trade.db.query" text="${db_code}" params="{&#34;state&#34; : 1}" limit="10" editable="false" cols="2" value="${db_code}" required="true" >
					<s:option value="$[db_code]" label="$[db_code]">
						$[db_code]　[$[description]]
					</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<s:textarea label="SQL语句" name="proc_sql" required="true" cols="3" rows="10">${proc_sql}</s:textarea>
			</s:row>
			<s:row>
				<s:textfield label="描述/备注" name="description" required="false" cols="3" >${description}</s:textfield>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
var proc_code = '${param.proc_code}';
$(function(){
	if(proc_code != ''){
		$('[name="proc_code"]').attr('disabled', 'true');
	}
})

function save(){
	var sdata=$("#form").getData();
	if (!$("form").valid()){
		return false;
	}
	if(proc_code != ''){
		$.call("hospital_common.trade.proc.update",sdata , function(s){
    		$.closeModal(true);
    	});
	}else{
		$.call("hospital_common.trade.proc.insert",sdata,function(s){
    		$.closeModal(true);
    	});
	}
}

function cancel(){
	$.closeModal(false);
}
</script>