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
	pageContext.setAttribute("ftype", request.getParameter("type"));
%>
<s:page ismodal="true" title="">
	<s:row>
		<s:form id="setFrom" fclass="portlet light bordered">
			<s:row>
				<%-- <s:autocomplete label="字段名" name="name" value="${control.name }" action="crdc.scheme.fields.list" id="fieldname" onchange="initfield()">
					<s:option value="$[fdname]" label="$[fdname]">$[fdname] ($[chnname])</s:option>
				</s:autocomplete> --%>
				<s:textfield label="字段名" dblaction="queryfields" name="name" value="${control.name }"/>
				<%-- <s:textfield label="字段名" name="name" value="${control.name }"></s:textfield> --%>
				<s:textfield label="中文说明" name="label" value="${control.label }"></s:textfield>
				<s:textfield label="备注" name="placeholder"
					value="${control.placeholder }"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="字段类型" name="fdtype" value="${control.fdtype }"></s:textfield>
				<s:textfield label="字段长度" name="size"
					value="${control.maxlength }"></s:textfield>
				<s:textfield label="小数位数" name="digits" value="${control.precision }"></s:textfield>
			</s:row>
			<s:row>
				<s:select label="宽度" name="cols"
					value="${empty control.cols?'1':control.cols }">
					<s:option label="1" value="1"></s:option>
					<s:option label="2" value="2"></s:option>
					<s:option label="3" value="3"></s:option>
					<s:option label="4" value="4"></s:option>
				</s:select>
				<s:textfield label="缺省值" name="defaultValue"
					value="${control.defaultValue }"></s:textfield>
				<s:textfield label="格式化" name="format" value="${control.format }"></s:textfield>
				<s:textfield label="焦点去向字段" name="nextfocusfield"
					value="${control.nextfocusfield }"></s:textfield>
			<%-- 	<s:switch label="活动" name="ishidden" value="${empty control.ishidden?'false':control.ishidden }"  onvalue="false" offvalue="true"></s:switch> --%>
			</s:row>
			<s:row>
				<s:switch label="无边框" name="islabel" value="${control.islabel }" onvalue="true"></s:switch>
				<s:switch label="可编辑" name="readonly" value="${empty control.readonly?'false':control.readonly }"  onvalue="false" offvalue="true"></s:switch>
				<s:switch label="必填" name="required" value="${control.required }" onvalue="true"></s:switch>
				<s:switch label="加密" name="encryption" value="${control.encryption }" onvalue="true"></s:switch>
			</s:row>
			<c:if test="${ftype ne 'autocomplete' }">
				<s:row>
					<s:textfield label="创建执行函数" name="create_action"
						value="${control.create_action }" dblaction="queryschemes,${control.schemeid },${control.system_product_code }"></s:textfield>
					<s:textfield label="修改执行函数" name="modify_action"
						value="${control.modify_action }" dblaction="queryschemes,${control.schemeid },${control.system_product_code }"></s:textfield>
					<s:textfield label="进入执行函数" name="enter_action"
						value="${control.enter_action }" dblaction="queryschemes,${control.schemeid },${control.system_product_code }"></s:textfield>
				</s:row>
				<s:row>
					<s:textfield label="双击执行函数" name="dbl_action"
						value="${control.dbl_action }" dblaction="queryschemes,${control.schemeid },${control.system_product_code }"></s:textfield>
					<s:textfield label="退出执行函数" name="out_action"
						value="${control.out_action }" dblaction="queryschemes,${control.schemeid },${control.system_product_code }"></s:textfield>
				</s:row>
			</c:if>
			<c:if test="${ftype eq 'autocomplete' }">
				<s:row>
					<s:select label="查询类别" name="autocomplete_initaction" value="${control.autocomplete_initaction }">
						<s:option label="查询职员" value="setting.user.querymine"></s:option>
						<s:option label="查询部门" value="setting.dep.querydep"></s:option>
					</s:select>
				</s:row>
			</c:if>
			<s:row  >
				<s:textfield label="明细id" name="tableid" value="${control.tableid }" cols="2"
				 placeholder="明细表格的id，如table1"></s:textfield>
				<s:textfield label="表达式" name="expressions" value="${control.expressions }" cols="2"
				 placeholder="如：sum(column1)或者count(1)"></s:textfield>
			</s:row>
			<s:row>
				<%-- <div class="cols">
					<s:button label="引用其他查询方案"></s:button>
				</div> --%>
				<div class="cols2">
					<s:button onclick="save();" color="green" label="确定"></s:button>
					<s:button color="red" onclick="returnback()" label="取消"></s:button>
				</div>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
	function save() {
		var data = {};
		var attrs = $("#setFrom").getData();
		attrs.maxlength = attrs.size;
		attrs.precision = attrs.digits;
		delete attrs.size;
		delete attrs.digits;
		data.attrs = attrs;
		$.closeModal(data);
	}
	function returnback() {
		$.closeModal(false);
	}
	function initfield(){
		var attrs = $("#setFrom").getData();
		$.call("crdc.scheme.fields.list",{filter:attrs.name},function(data){
			var d = data.resultset[0];
			if(d){
				$("#setFrom").find("input[name='filedtype']").val(d.fdtype);
				$("#setFrom").find("input[name='maxlength']").val(d.fdsize);
				$("#setFrom").find("input[name='precision']").val(d.fddec);
				$("#setFrom").find("input[name='placeholder']").val(d.beizhu);
				$("#setFrom").find("input[name='label']").val(d.chnname);
				if(d.nouse){
					if(d.nouse<1){
						d.nouse = 1;
					}else if(d.nouse>4){
						d.nouse = 4;
					}
					$("#setFrom").find("select[name='cols']").val(d.nouse);
				}
			}
		});
	}
	/* $(document).ready(function() {
		$(".icheck-colors").click(function(e) {
			var color = $(this).find(".active").attr("class").split(" ")[0];
			if (color == "active") {
				color = "default";
			}
			$("#color_").val(color);
		});
	}); */
</script>
