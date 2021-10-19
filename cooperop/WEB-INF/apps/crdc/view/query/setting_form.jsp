<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%
	String str = request.getParameter("attdata");
	Map<String, Object> map = CommonFun.json2Object(str, Map.class);
	Map<String, Object> attrs = (Map<String, Object>) map.get("attrs");
	List<Map<String, Object>> contents = (List<Map<String, Object>>) map.get("contents");
	pageContext.setAttribute("control", attrs);
	pageContext.setAttribute("contents", contents);
%>
<s:page ismodal="true" title="">
<script type="text/javascript"
		src="${pageContext.request.contextPath}/theme/plugins/icheck/icheck.min.js"></script>
<script type="text/javascript"
		src="${pageContext.request.contextPath}/theme/pages/scripts/form-icheck.js"></script>
	<s:row>
		<s:form id="setFrom" fclass="portlet light bordered">
			<s:row>
				<s:textfield label="表单名" name="label" value="${control.label }"></s:textfield>
				<s:switch label="添加工具条" name="istoorbar" onvalue="Y" offvalue="N" value="${control.istoorbar }"></s:switch>
				<s:textfield label="图标" name="icon" value="${control.icon }"></s:textfield>
				<s:button label="查看图标库" onclick="showicon();" color="green" icon="fa fa-eye"></s:button>
			</s:row>	
			<s:row>	
				<s:switch label="手机默认隐藏" name="app_collapsed" onvalue="Y" offvalue="N" value="${control.app_collapsed }"></s:switch>
				<c:if test="${control.canSort eq 'Y'}">
		 			<s:textfield label="显示顺序" name="toIndex" value="${control.myIndex }" max="${control.myMaxIndex }" min="0"></s:textfield>
		 			<input type="hidden" name="myIndex" value="${control.myIndex }"/>
		 		</c:if>	
				<div class="cols2">
					<label class="control-label">颜色</label>
					<div class="control-content">
						<ul class="icheck-colors">
							<li class="${(empty control.color or control.color eq 'default')?'active':'' }"></li>
							<li class="red ${control.color eq 'red'?'active':'' }"></li>
							<li class="green ${control.color eq 'green'?'active':'' }"></li>
							<li class="blue ${control.color eq 'blue'?'active':'' }"></li>
							<li class="yellow ${control.color eq 'yellow'?'active':'' }"></li>
							<li class="purple ${control.color eq 'purple'?'active':'' }"></li>
						</ul>
					</div>
					<input type="hidden" name="color" id="color_" class="form-control"
						value="${control.color }" />
				</div>
			</s:row>
			<s:row>
				<s:button label="确定" onclick="save();" color="blue" style="margin-left:200px;"></s:button>
				<s:button label="取消" onclick="returnback();" color="red"></s:button>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
	function save() {
		if (!$("form").valid()) {
			return false;	
		}
		var data = {};
		var attrs = $("#setFrom").getData();
		data.attrs = attrs;
		$.closeModal(data);
	}
	function returnback() {
		$.closeModal(false);
	}
	$(document).ready(function() {
		$(".icheck-colors").click(function(e) {
			var color = $(this).find(".active").attr("class").split(" ")[0];
			if (color == "active") {
				color = "default";
			}
			$("#color_").val(color);
		});
	});
	jQuery(document).ready(function() {    
		FormiCheck.init(); // init page demo
	    });
	function showicon(){
		window.open(cooperopcontextpath + "/w/crdc/ui_icons.html?ismodal=true");
	}
</script>
