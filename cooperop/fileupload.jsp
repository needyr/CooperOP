<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" disloggedin="true" >
	<s:form label="" id="fileform">
		<s:row>
			<s:image label="请选择图片" name="app_file_up" autoupload="true" module="${param.module }"></s:image>
		</s:row>
	</s:form>
</s:page>
<script type="text/javascript">
	function re(){
		//location.reload();
	}
</script>