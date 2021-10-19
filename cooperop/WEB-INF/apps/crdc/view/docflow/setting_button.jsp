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
				<s:textfield label="对应查询页"></s:textfield>
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
				<s:textfield label="焦点去向字段" name="nextfocusfield"
					value="${control.nextfocusfield }"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="按钮标题" name="label" value="${control.label }"
					cols="2"></s:textfield>
				<s:textfield label="按钮图标" name="icon" value="${control.icon }"/>
				<s:button label="查看图标库" onclick="showicon();" color="green" icon="fa fa-eye"></s:button>
			</s:row>
			<s:row>
				<s:textfield label="执行函数" name="action" value="${control.action }"  dblaction="queryschemes,${control.schemeid },${control.system_product_code }"
					cols="3"></s:textfield>
			</s:row>
			<s:row>
				<s:button label="确定" onclick="save();" color="blue"></s:button>
				<s:button label="取消" onclick="returnback()" color="red"></s:button>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
	function save() {
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
	function showicon(){
		window.open(cooperopcontextpath + "/w/crdc/ui_icons.html?ismodal=true");
	}
jQuery(document).ready(function() {    
	FormiCheck.init(); // init page demo
    });
</script>
