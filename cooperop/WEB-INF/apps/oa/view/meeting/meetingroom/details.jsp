<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" ismodal="true" >
	<style type="text/css">
		.ptitle{
		    text-align: center;
		    width: 100%;
		    font-size: 20px;
		    margin-bottom: 20px;
		}
	</style>
	<s:row>
		<s:form>
			<s:row>
				<div class="ptitle">${name } 详细信息</div>
			</s:row>
			<s:row>
				<s:textfield label="可容纳人数:" name="galleryful"  value="${galleryful }"  islabel="true"></s:textfield>
				<s:textfield label="会议室地址:" name="address" value="${address }"  islabel="true"></s:textfield>
			</s:row>
			<s:row>
				<s:image label="会议室图片:" name="img" value="${img }"  islabel="true" ></s:image>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table  label="设备列表" autoload="true" id="dtable" action="oa.meeting.meetingFacility.query" sort="true" fitwidth="true">
			<s:table.fields>
				<s:table.field name="name" datatype="string"  label="设备名称" sort="true"></s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
var id='${pageParam.id}';

$(document).ready(function() {
	query();
});
function query(){
	var d={meeting_id:id};
	$("#dtable").params(d);
	$("#dtable").refresh();
}
	
</script>