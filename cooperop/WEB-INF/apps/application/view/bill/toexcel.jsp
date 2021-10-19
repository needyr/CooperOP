<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="导入excel" dispermission="true">
	<s:row>
		<s:form id="qform" >
			<s:toolbar>
				<s:button label="下载导入模版" onclick="downexcel();"></s:button>
				<s:button label="导入数据" onclick="upexcel();"></s:button>
			</s:toolbar>
			<s:row>
				<input type="hidden" name="pageid" value="${param.pageid }">
				<input type="hidden" name="is_mx" value="${param.ismx }">
				<input type="hidden" name="tableid" value="${param.tableid }">
				<input type="hidden" name="gzid" value="${param.gzid }">
				<s:file label="添加excel" maxlength="1" name="up_excel"></s:file>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	$(document).ready(function(){
		
	})
	function downexcel(){
		var da = $("#qform").getData();
		var uu = "?pageid="+da.pageid+"&gzid="+da.gzid+"&is_mx="+da.is_mx+"&tableid="+da.tableid;
		var url = cooperopcontextpath + "/rm/downexcel/downexcel"+uu;
		location.href=url;
	}
	function upexcel(){
		$.call("application.bill.importExcels", $("#qform").getData() ,function(){
			$.closeModal(true);
		})
	}
</script>
