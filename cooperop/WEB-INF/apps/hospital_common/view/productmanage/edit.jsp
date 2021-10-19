<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑产品信息" ismodal="true">
	<s:row>
		<s:form id="form" label="产品信息">
			<s:toolbar>
				<s:button label="保存" onclick="saveOrUpdate()" icon="glyphicon glyphicon-floppy-saved"></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<s:row>
					<c:if test="${not empty param.code}">
						<input type="hidden" name="code" value="${code }"/>
					</c:if>
					<c:if test="${empty param.code}">
						<s:textfield label="产品编号" name="code" required="true" cols="2" value="" />
					</c:if>
					<s:textfield label="产品名称" name="name" required="true" cols="2" value="${name }" />
				</s:row>
				<s:row>
					<s:textfield label="产品权限" name="popedom" cols="2" value="${popedom }"></s:textfield>
					<s:textfield label="默认规则" name="default_role" cols="2" value="${default_role }"></s:textfield>
				</s:row>
				<s:row>
					<s:switch label="是否开启" name="is_active" onvalue="1" offvalue="0" ontext="是" offtext="否" value="${is_active }"/> 
					<s:switch label="是否为审查服务" name="is_check_server" onvalue="1" offvalue="0" ontext="是" offtext="否" value="${is_check_server }"/> 
				</s:row>
				<s:row>
					<s:textfield label="产品接口链接" name="interface_url" cols="2" value="${interface_url }" />
				</s:row>	
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		if('${param.id}'!=null&&typeof('${param.id}')!='undefind'&&'${param.id}'!=''){
			$("input[name='no']").attr("disabled","disabled");
		}
			
	})

	function saveOrUpdate(){
		if (!$("form").valid()){
    		return false;
    	}
		var data=$("#form").getData();
		var code = '${param.code }';
		if(code != null&&typeof(code)!='undefind'&&code!=''){
			//不为空， 修改
			$.call("hospital_common.productmanage.update",data,function(s){
	    		$.closeModal(s);
	    	});
		}else{
			//为空，新增
			//判断输入的编号是否已存在
			$.call("hospital_common.productmanage.queryHas",{"code":data.code},function(rtn){
				if(rtn.resultset.length<1){
					//输入的编号不存在，可新增
					$.call("hospital_common.productmanage.save",data,function(s){
			   			$.closeModal(s);
			   		});
	    		}else{
	    			//输入的编号已经存在
	    			$.message("产品编号已存在，请重新输入!");
	    			$("input[name='code']").val("");
	    		} 
			});
		}
	}

	//取消
    function cancel(){
    	$.closeModal(false);
    }
</script>