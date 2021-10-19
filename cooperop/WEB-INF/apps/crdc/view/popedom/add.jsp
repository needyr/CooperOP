<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" ismodal="true">
<style type="text/css">

</style>
	<s:row>
		<s:form border="0" id="myform" fclass="portlet light bordered">
			<c:if test="${empty system_popedom_id_parent}">
				<s:row>
					<s:radio label="所属产品" name="plugin" action="application.common.listProducts" cols="3" value="${plugin }">
						<s:option value="$[code]" label="$[name]"></s:option>
					</s:radio>
				</s:row>
			</c:if>
			<s:row>
				<c:if test="${not empty system_popedom_id_parent}">
					<s:textfield label="父id" name="system_popedom_id_parent" value="${system_popedom_id_parent }" readonly="true"/>
				</c:if>
				<s:textfield label="权限id" name="id" value="${id }" required="true"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield id="name" cols="1" label="名称" name="name" value="${name}" required="true" onchange="show_style()"></s:textfield>
				<s:textfield cols="2" label="code" name="code" value="${code}" ></s:textfield>
			</s:row>
			<s:row>
				<s:switch cols="1" label="PC菜单" name="is_menu" value="${is_menu}" onvalue="1" offvalue="0"></s:switch>
				<s:switch cols="1" label="手机菜单" name="tel_menu" value="${tel_menu}" onvalue="1" offvalue="0"></s:switch>
				<c:if test="${empty event or event=='tt=_blank' or event==0}">
					<s:switch cols="1" label="新页签" name="event" value="${event}" onvalue="tt=_blank" offvalue="0"></s:switch>
				</c:if>
			</s:row>
			<s:row>
				<s:textfield cols="1" label="排序值" name="order_no" value="${order_no}"></s:textfield>
				<s:textfield id="icon" cols="1" label="图标" name="icon" value="${icon}" onchange="show_style()"></s:textfield>
				<s:button onclick="showicon();" label="查看图标库" color="blue"></s:button><div crid="" class="cols1">
				<label class="control-label" title="名称" height="40px">样式预览</label>
				<div class="control-content" >
				<i id="nstyle" class="" 
					style=" height: 30px;width:150px;
				    font-size: 15px;
				    line-height: 20px;
				    padding: 5px;
				    font-weight: normal;
				    color: #ddd;
				    background-color: #707B88;"></i>
				</div>
			</s:row>
			<s:row>
				<s:textarea label="参数" cols="3" maxlength="255" height="100" name="page_params" value="${page_params}"></s:textarea>
			</s:row>
			<s:row>
				<div class="cols1">
				</div>
				<div class="cols">
					<s:button onclick="save();" color="green" label="保存"></s:button>
				</div>
			</s:row>
		</s:form>
	</s:row>
	
</s:page>
<script>
    $(document).ready(function(){
    	show_style();
    	var v = '${system_popedom_id_parent }'
    	if(!v){
    		return;
    	}
    	if(!'${id }'){
	    	$.call("crdc.popedom.getMaxId", {system_popedom_id_parent:v}, function(rtn) {
				if(rtn){
					$("input[name='id']").val(rtn.result.id);
					$("input[name='order_no']").val(rtn.result.order);
				}
			},null,{async: false});
    	}
    });
	function show_style(){
		var i = document.getElementById('nstyle'); 
		var icon=$("#icon").val()
		if(!icon){
			icon="icon-equalizer";
		}
		i.setAttribute("class", icon);
		var t=$("#name").val();
		$("#nstyle").html('<font size="18px">&nbsp;&nbsp;'+t+'</font>');
	}
	function save() {
		if(!$("form").valid()){
			return;
		}
		$.call("crdc.popedom.save", $("#myform").getData(), function(rtn) {
			if (rtn.result == 'success') {
				$.closeModal(true);
			}
		},null,{async: false});
	}
	function showicon(){
		if(typeof crtechTogglePage == 'undefined'){
			window.open(cooperopcontextpath + "/w/crdc/ui_icons.html?ismodal=true");
		}else{
			$.modal(cooperopcontextpath + "/w/crdc/ui_icons.html?ismodal=true", "图标库",{
				callback : function(r){
				}
			});
		}
	}
</script>
