<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" ismodal="true">
	<s:row>
		<s:form border="0" id="myform">
			<s:toolbar>
				<s:button label="保存" onclick="save();"></s:button>
			</s:toolbar>
			<s:row>
				<input type="hidden" name="id" value="${pageParam.id }"/>
				<s:radio label="产品" name="system_product_code" value="${system_product_code }" action="application.common.listProducts" 
					required="true" cols="4">
					<s:option label="$[name]" value="$[code]"></s:option>
				</s:radio>
			</s:row>
			<s:row>
				<s:select label="预警分类" name="sort_id" value="${sort_id }" required="true" action="application.warnSetting.querySort">
					<s:option label="$[name]" value="$[id]"></s:option>
				</s:select>
				<%-- 
				<s:textfield label="图标" name="icon" value="${icon}"></s:textfield> --%>
				<%-- <s:textfield label="action" name="java的action" value="${action}"></s:textfield> --%>
				<s:textfield label="存储过程" name="scheme" value="${scheme}"></s:textfield>
				<s:textfield label="序号" name="order_no" required="true" value="${order_no}"></s:textfield>
				<s:textfield label="刷新时间/秒" name="refresh_time" required="true" value="${refresh_time}" datatype="number" placeholder="间隔多少秒刷新，不得小于5秒"></s:textfield>
				<s:switch label="启用" name="state" value="${state}" onvalue="1" offvalue="0"></s:switch>
				<s:switch label="公共权限" name="is_public" value="${is_public}" onvalue="1" offvalue="0" onchange="show();"></s:switch>
				<s:textfield label="描述" name="discription" required="true" value="${discription}" cols="4"></s:textfield>
			</s:row>
			<s:row id="show_">
				<s:selecttree label="部门" name="system_department_id" action="hospital_common.dict.sysdept.queryTreeRe" value="${system_department_id}" text="${department_name }"
					nodeid="id" nodename="name" pid="parent_id" select="checkbox">
				</s:selecttree>
				<%-- <s:select cols="2" label="岗位权限" name="system_post_id" action="hr.commonquery.post.query" value="${system_post_id}" placeholder="输入岗位、部门进行查询">
					<s:option label="$[name]" value="$[id]" title="$[dep_path]"></s:option>
				</s:select> --%>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
$(document).ready(function(){
	show();
});
	function save() {
		var data = $("#myform").getData();
		if(data.is_public == '1'){
			delete data.system_department_id;
			delete data.system_post_id;
		}
		$.call("application.warnSetting.save", data, function(rtn) {
			if (rtn) {
				$.closeModal(true);
			}
		});
	}
	function show(){
		if($("#myform").getData().is_public == '1'){
			$("#show_").hide();
		}else{
			$("#show_").show();
		}
	}
</script>
