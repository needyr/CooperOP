<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="审查参数" disloggedin="true">
<style type="text/css">
.main {
    padding: 0px !important;
}
.choose{background-color: #d3d3d3 !important;}
#msgdtl{
	color: #1a1e1f;
	font-size: 14px;
	line-height: 22px;
	padding-left: 5px;
	border-left: 5px solid #dce0d4;
	font-family: '微软雅黑';
	height: 450px;
}
</style>
	<s:row>
       	<c:choose>
       		<c:when test="${empty his_req}">
       			未找到审查请求参数
       		</c:when>
       		<c:otherwise>
       		<s:tabpanel>
       			<s:form label="审查日志" active="true">
       				<s:row>
       					<div id="msgdtl">
	       					<textarea rows="30" style="width: 100%; height: 100%;" disabled="disabled">${clog}</textarea>
      					</div>
       				</s:row>
       			</s:form>
       			<s:form label="审查请求参数">
       				<s:row>
       					<div id="msgdtl">
	       					<textarea rows="30" style="width: 100%; height: 100%;" disabled="disabled">${his_req}</textarea>
      					</div>
       				</s:row>
       			</s:form>
       			<s:form label="合理用药引擎请求参数">
       				<s:row>
       					<div id="msgdtl">
	       					<textarea rows="30" style="width: 100%; height: 100%;" disabled="disabled">${thirdt_request}</textarea>
      					</div>
       				</s:row>
       			</s:form>
       			<s:form label="合理用药引擎返回参数">
       				<s:row>
       					<div id="msgdtl">
	       					<textarea rows="30" style="width: 100%; height: 100%;" disabled="disabled">${thirdt_response}</textarea>
      					</div>
       				</s:row>
       			</s:form>
       		</s:tabpanel>
       			
       		</c:otherwise>
       	</c:choose>
	</s:row>
</s:page>