<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="" disloggedin="true">
	<s:row>
	
		<s:row>
			<s:textfield name="doctor_appeal_reason" id="appeal" label="申诉理由：" cols="3" value="${hospital_imic.doctor_appeal_reason}" style="border:none #FFFFFF; border-bottom:#777777 solid 1px; background:transparent;"></s:textfield>
		</s:row>
	</s:row>
	
	<div style="float: right; position: absolute; right: 10px;bottom: 10px">
		<s:button label="确定" class="layui-btn layui-btn-sm" onclick="submitAppeal()"></s:button>
	</div>
	
</s:page>
<script>

 	function submitAppeal(){
		var doctor_choose="${param.doctor_choose}";
		var imic_info_id="${param.imic_info_id}";
		var doctor_appeal_reason=$("#appeal").val();
		ajax: $.call("hospital_common.showturns.iMicChoose", {
			"doctor_choose":doctor_choose,
			"imic_info_id":imic_info_id,
			"doctor_appeal_reason":doctor_appeal_reason
				}, function(s) {
					var index = parent.layer.getFrameIndex(window.name);//先得到当前iframe层的索引
					parent.layer.close(index); //再执行关闭
				});
		
	}

</script>
