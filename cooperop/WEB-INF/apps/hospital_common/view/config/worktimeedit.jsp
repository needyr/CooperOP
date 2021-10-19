<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑系统配置信息" ismodal="true">
	<s:row>
		<s:form id="form" label="配置信息">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon="glyphicon glyphicon-floppy-saved"></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield label="配置编号" name="code" required="true" cols="3" value="${code}" ></s:textfield>
			</s:row>
			<s:row>
				<s:select label="所属产品" name="system_product_code" value="${system_product_code}" action="hospital_common.config.queryProduct" cols="3">
						<s:option value="$[product_code]" label="$[product_name]" ></s:option>
				</s:select>
			</s:row>
			<s:row>
				<s:textfield label="配置名称" name="name" required="true" cols="3" value="${name}"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="星期一" name="value_1" cols="3" value=""></s:textfield>
				<s:textfield label="星期二" name="value_2" cols="3" value=""></s:textfield>
				<s:textfield label="星期三" name="value_3" cols="3" value=""></s:textfield>
				<s:textfield label="星期四" name="value_4" cols="3" value=""></s:textfield>
				<s:textfield label="星期五" name="value_5" cols="3" value=""></s:textfield>
				<s:textfield label="星期六" name="value_6" cols="3" value=""></s:textfield>
				<s:textfield label="星期日" name="value_0" cols="3" value=""></s:textfield>
				<input name="value" value="${value}" type="hidden" />
			</s:row>
			<s:row>
				<s:textarea label="描述" name="remark" required="false" cols="3" value="${remark}" rows="5" ></s:textarea>
			</s:row>
				<%-- <s:textfield name="system_product_code" required="true" cols="2" value="precheck"  islabel="true"></s:textfield> --%>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
var code = '${param.code}';
$(function(){
	if(code){
		$("input[name='code']").attr("disabled","disabled");
		var _value = $("input[name='value']").val();
		var _values = _value.split('A');
		for(var i=0; i<_values.length; i++){
			var x = _values[i].split('#');
			var add_time = "input[name='value_"+x[0]+"']";
			$(add_time).val(x[1]);
		}
	}else{
		//判断重复
		$("input[name='code']").blur(function(){
			var sdata=$("#form").getData();
			$.call("hospital_common.config.edit",sdata,function(s){
	    		if(s!=null){
	    			$.message("配置编号已存在！请重新输入");
	    			$("input[name='code']").val("");
	    		}
	    	});	
		});
	}
});

function save(){
	var sdata=$("#form").getData();
	delete sdata.value_0;
	delete sdata.value_1;
	delete sdata.value_2;
	delete sdata.value_3;
	delete sdata.value_4;
	delete sdata.value_5;
	delete sdata.value_6;
	var string = '';
	var ret= /(([0-1][0-9]|[2][0-3]):([0-5][0-9])\-([0-1][0-9]|[2][0-3]):([0-5][0-9]),)+/g;//00:22-00:22,00:22-00:22,
	for(var i=0;i<7;i++){
		var times = ($("input[name='value_"+i+"']").val()).replace(/\s/g,'');
		var time = times.split(',');
		var text = times+',';
		var text2 = text.replace(ret,'');
		console.log(text2)
		if(text == ',' || text2 == ''){
			if(time.length < 5){
				if(i==0){
					string = string + i + '#' + times
				}else{
					string = string + 'A' + i + '#' + times
				}
			}else{
				var meg = $("input[name='value_"+i+"']").attr('label');
				$.message('<span style="color: red;font-size: 25px;">'+meg+'</span> 时间段太多请减少到<span style="color: red;font-size: 25px;">5</span>条！');
				return;
			} 
		}else{
			var meg = $("input[name='value_"+i+"']").attr('label');
			$.message('<span style="color: red;font-size: 25px;">'+meg+'</span> 时间段格式错误！');
			return;
		}
	}
	sdata.value = string;
	if (!$("form").valid()){
		return false;
	}
	if(code){
		$.call("hospital_common.config.update",sdata,function(s){
    		$.closeModal(s);
    	});
	}else{
		$.call("hospital_common.config.insert",sdata,function(s){
    		$.closeModal(s);
    	});
	}
}

function cancel(){
	$.closeModal(false);
}
</script>