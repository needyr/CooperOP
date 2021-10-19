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
<s:page ismodal="true" title="">
	<s:row>
		<s:form label="字段属性" id="setForm">
			<s:row>
				<%-- <s:autocomplete label="字段名" name="name" value="${pageParam.name }" action="crdc.scheme.fields.list">
					<s:option value="$[fdname]" label="$[chnname]">$[code]$[fdname]$[chnname]</s:option>
				</s:autocomplete> --%>
				<s:textfield label="字段名" dblaction="queryfields" name="name" value="${pageParam.name }"/>
				<s:textfield label="标题" name="label" value="${pageParam.label }"></s:textfield>
				<s:textfield label="字段长度" name="size" value="${pageParam.size }"></s:textfield>
				<s:textfield label="显示宽度" name="maxlength" value="${pageParam.maxlength }"></s:textfield>
				<s:textfield label="小数位数" name="digitsize" value="${pageParam.digitsize }"></s:textfield>
				<s:textfield label="显示小数位" name="precision" type="text" value="${f.attrs.precision }"/>
				<s:textfield label="字段类型" name="fdtype" value="${pageParam.fdtype }"></s:textfield>
				<s:textfield label="表达式" name="dictionary" value="${pageParam.dictionary }"></s:textfield>
			</s:row>
			<s:row>
				<s:radio label="字段值排列方式" name="align" value="${pageParam.align }" cols="2">
					<s:option label="居左" value="left"></s:option>
					<s:option label="居中" value="center"></s:option>
					<s:option label="居右" value="right"></s:option>
				</s:radio>
				<s:switch label="启动校验" name="checkable" value="${pageParam.checkable }" onvalue="true" offvalue="false"></s:switch>
				<s:switch label="查重字段" name="callduplicate" value="${pageParam.callduplicate }" onvalue="true" offvalue="false"></s:switch>
			</s:row>
			<s:row>
				<s:switch label="活动" name="available" value="${empty pageParam.available?'true':pageParam.available }" onvalue="true" offvalue="false"></s:switch>
				<s:switch label="可编辑" name="editable" value="${empty pageParam.editable?'false':pageParam.editable }" onvalue="true" offvalue="false"></s:switch>
				<s:select label="字段展示控件" name="controltype" value="${pageParam.controltype }">
					<s:option label="文本框" value="textfield"></s:option>
					<s:option label="下拉框" value="select"></s:option>
					<s:option label="日期" value="datefield"></s:option>
					<s:option label="时间" value="timefield"></s:option>
					<s:option label="文件" value="file"></s:option>
				</s:select>
			</s:row>
			<s:row>
				<s:textfield cols="4" label="进入执行函数" name="enter_action" value="${pageParam.enter_action }" dblaction="queryschemes,${pageParam.schemeid },${pageParam.system_product_code }"/>
				<s:textfield cols="4" label="双击执行函数" name="dbl_action" value="${pageParam.dbl_action }" dblaction="queryschemes,${pageParam.schemeid },${pageParam.system_product_code }"/>
				<s:textfield cols="4" label="修改执行函数" name="modify_action" value="${pageParam.modify_action }" dblaction="queryschemes,${pageParam.schemeid },${pageParam.system_product_code }"/>
				<s:textfield cols="4" label="退出执行函数" name="out_action" value="${pageParam.out_action }" dblaction="queryschemes,${pageParam.schemeid },${pageParam.system_product_code }"/>
			</s:row>
			<s:row>
				<s:button onclick="save();return false;" color="green" label="保存"></s:button>
				<s:button color="red" onclick="returnback();return false;" label="取消"></s:button>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	function save() {
		var data = $("#setForm").getData();
		$.closeModal(data);
	}
	function returnback() {
		$.closeModal(false);
	}
</script>
