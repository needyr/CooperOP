<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="客服端测试模块" ismodal="true">

	<s:row>
		<s:form border="0">
			<s:toolbar>
				<s:button label="测试" icon="fa fa-cogs" onclick="testModule();" />
				<s:button label="取消" icon="fa fa-undo" onclick="$.closeModal(true);" />
			</s:toolbar>
			<s:row>
				<s:textarea id="request" label="请求交易体" cols="2" rows="25"></s:textarea>
				<s:textarea  id="respose" label="返回交易体" cols="2" rows="25"></s:textarea>
			</s:row>
		</s:form>
	</s:row>

</s:page>
<%----------------------------------------
	        JAVASCRIPT
-----------------------------------------%>
<script type="text/javascript">

	$(function() {
		$("#request").val(autoGetVal());
	});

	function autoGetVal() {
		var str= "${example}";
		str = str.replace(/>  /g, ">\r\n");
		return str;
	}
	function testModule() {
		var request = $('#request').val();
		if (!request) {
			$.message("request 不能为空！");
			return;
		}
		$.call("hospital_common.testmodule.clientTestModule", {
			data_webservice_code: '${pageParam.data_webservice_code}',
			code: '${pageParam.code}',
			type: '${pageParam.type}',
			request: request,
		}, function(rtn){
			if (rtn) {
				$("#respose").val(rtn);
			} else {
				$.message("respose 为空.");
			}
		});
	}

</script>
