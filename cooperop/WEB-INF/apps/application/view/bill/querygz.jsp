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
<s:page title="未完成单据" disloggedin="true">
	<s:row>
		<s:form id="qform" fclass="portlet light bordered">
			<s:row>
				<s:textfield cols="2" label="关键字" name="keyword" value=""/>
				<s:button label="查询" onclick="query()" color="blue"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table cols="4" label="未完成单据" action="application.bill.querygz" autoload="false" id="dtable" select="single">
			<s:toolbar>
				<s:button label="确定" onclick="getsel();"></s:button>
				<s:button label="取消" onclick="quxiao();"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="gzid" label="挂账编号"></s:table.field>
				<s:table.field name="gzdesc" label="描述"></s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(document).ready(function(){
		query();
	})
	function query(){
		var d = $("#qform").getData();
		d.djbhbs = '${pageParam.djbhbs}';
		console.log('${pageParam.djbhbs}');
		$("#dtable").params(d);
		$("#dtable").refresh();
	}
	function getsel(){
		var d = $("#dtable").getSelected();
		$.closeModal({gzid: d[0].data.gzid});
	}
	function quxiao(){
		$.closeModal(false);
	}
</script>
