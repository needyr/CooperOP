<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="详情" ismodal="true">
	<s:row>
		<s:form id="form" label="详情">
			    <c:if test="${not empty spbh}">
		   			<s:row>
						<s:textarea label="药品编号" islabel="true" cols="3" >${spbh}</s:textarea>
					</s:row>
				</c:if>
				   <c:if test="${not empty zdy_cz}">
		   			<s:row>
						<s:textarea label="操作项目" islabel="true" cols="3" >${zdy_cz}</s:textarea>
					</s:row>
				</c:if>
				   <c:if test="${not empty create_time}">
		   			<s:row>
						<s:textarea label="操作时间" islabel="true" cols="3" >${create_time}</s:textarea>
					</s:row>
				</c:if>
				   <c:if test="${not empty admin}">
		   			<s:row>
						<s:textarea label="操作人" islabel="true" cols="3" >${admin}</s:textarea>
					</s:row>
				</c:if>
				   <c:if test="${not empty admin_no}">
		   			<s:row>
						<s:textarea label="操作人编码" islabel="true" cols="3" >${admin_no}</s:textarea>
					</s:row>
				</c:if>
				   <c:if test="${not empty apa_check_sorts_id}">
		   			<s:row>
						<s:textarea label="审查问题类别" islabel="true" cols="3" >${apa_check_sorts_id}</s:textarea>
					</s:row>
				</c:if>
				   <c:if test="${not empty sys_check_level}">
		   			<s:row>
						<s:textarea label="审查级别" islabel="true" cols="3" >${sys_check_level}</s:textarea>
					</s:row>
				</c:if>
				   <c:if test="${not empty nwarn_message}">
		   			<s:row>
						<s:textarea label="警示消息" islabel="true" cols="3" >${nwarn_message}</s:textarea>
					</s:row>
				</c:if>
				   <c:if test="${not empty tiaojian}">
		   			<s:row>
						<s:textarea label="条件" islabel="true" cols="3" >${tiaojian}</s:textarea>
					</s:row>
				</c:if>
				   <c:if test="${not empty ccr_start}">
		   			<s:row>
						<s:textarea label="开始肌酐清除率" islabel="true" cols="3" >${ccr_start}</s:textarea>
					</s:row>
				</c:if>
				   <c:if test="${not empty ccr_end}">
		   			<s:row>
						<s:textarea label="结束肌酐清除率" islabel="true" cols="3" >${ccr_end}</s:textarea>
					</s:row>
				</c:if>
				   <c:if test="${not empty nianl_c}">
		   			<s:row>
						<s:textarea label="年龄类别" islabel="true" cols="3" >${nianl_c}</s:textarea>
					</s:row>
				</c:if>
				<c:if test="${not empty nianl_unit}">
		   			<s:row>
						<s:textarea label="年龄单位" islabel="true" cols="3" >${nianl_unit}</s:textarea>
					</s:row>
				</c:if>
				   <c:if test="${not empty nianl_start}">
		   			<s:row>
						<s:textarea label="开始年龄" islabel="true" cols="3" >${nianl_start}</s:textarea>
					</s:row>
				</c:if>
				   <c:if test="${not empty nianl_end}">
		   			<s:row>
						<s:textarea label="结束年龄" islabel="true" cols="3" >${nianl_end}</s:textarea>
					</s:row>
				</c:if>
				<c:if test="${not empty weight_c}">
		   			<s:row>
						<s:textarea label="体重段" islabel="true" cols="3" >${weight_c}</s:textarea>
					</s:row>
				</c:if>
				<c:if test="${not empty weight_unit}">
		   			<s:row>
						<s:textarea label="体重单位" islabel="true" cols="3" >${weight_unit}</s:textarea>
					</s:row>
				</c:if>
				<c:if test="${not empty weight_start}">
		   			<s:row>
						<s:textarea label="开始体重" islabel="true" cols="3" >${weight_start}</s:textarea>
					</s:row>
				</c:if>
				<c:if test="${not empty weight_end}">
		   			<s:row>
						<s:textarea label="结束体重" islabel="true" cols="3" >${weight_end}</s:textarea>
					</s:row>
				</c:if>
				   <c:if test="${not empty source}">
		   			<s:row>
						<s:textarea label="判断来源" islabel="true" cols="3" >${source}</s:textarea>
					</s:row>
				</c:if>
				   <c:if test="${not empty xmid}">
		   			<s:row>
						<s:textarea label="项目ID" islabel="true" cols="3" >${xmid}</s:textarea>
					</s:row>
				</c:if>
				   <c:if test="${not empty xmbh}">
		   			<s:row>
						<s:textarea label="项目编号" islabel="true" cols="3" >${xmbh}</s:textarea>
					</s:row>
				</c:if>
				   <c:if test="${not empty xmmch}">
		   			<s:row>
						<s:textarea label="项目名称" islabel="true" cols="3" >${xmmch}</s:textarea>
					</s:row>
				</c:if>
				   <c:if test="${not empty formul}">
		   			<s:row>
						<s:textarea label="公式" islabel="true" cols="3" >${formul}</s:textarea>
					</s:row>
				</c:if>
				   <c:if test="${not empty value}">
		   			<s:row>
						<s:textarea label="值" islabel="true" cols="3" >${value}</s:textarea>
					</s:row>
				</c:if>
				   <c:if test="${not empty beizhu}">
		   			<s:row>
						<s:textarea label="备注" islabel="true" cols="3" >${beizhu}</s:textarea>
					</s:row>
				</c:if>
				   <c:if test="${not empty frequency_n}">
		   			<s:row>
						<s:textarea label="频率" islabel="true" cols="3" >${frequency_n}</s:textarea>
					</s:row>
				</c:if>
				   <c:if test="${not empty jingjz_message}">
		   			<s:row>
						<s:textarea label="禁忌症显示信息" islabel="true" cols="3" >${jingjz_message}</s:textarea>
					</s:row>
				</c:if>
				   <c:if test="${not empty diagnosis_code}">
		   			<s:row>
						<s:textarea label="诊断编码" islabel="true" cols="3" >${diagnosis_code}</s:textarea>
					</s:row>
				</c:if>
				 <c:if test="${not empty diagnosis_name}">
		   			<s:row>
						<s:textarea label="诊断名称" islabel="true" cols="3" >${diagnosis_name}</s:textarea>
					</s:row>
				</c:if>
				 <c:if test="${not empty calculation_formula}">
		   			<s:row>
						<s:textarea label="计算公式" islabel="true" cols="3" >${calculation_formula}</s:textarea>
					</s:row>
				</c:if>
				 <c:if test="${not empty routename}">
		   			<s:row>
						<s:textarea label="给药方式" islabel="true" cols="3" >${routename}</s:textarea>
					</s:row>
				</c:if>
				 <c:if test="${not empty s_ml_volume}">
		   			<s:row>
						<s:textarea label="开始液体量" islabel="true" cols="3" >${s_ml_volume}</s:textarea>
					</s:row>
				</c:if>
				 <c:if test="${not empty e_ml_volume}">
		   			<s:row>
						<s:textarea label="截止液体量" islabel="true" cols="3" >${e_ml_volume}</s:textarea>
					</s:row>
				</c:if>
				 <c:if test="${not empty nwarn}">
		   			<s:row>
						<s:textarea label="警示灯等级" islabel="true" cols="3" >${nwarn}</s:textarea>
					</s:row>
				</c:if>
				 <c:if test="${not empty displayorder}">
		   			<s:row>
						<s:textarea label="药品属性排序号" islabel="true" cols="3" >${displayorder}</s:textarea>
					</s:row>
				</c:if>
				 <c:if test="${not empty xiangm}">
		   			<s:row>
						<s:textarea label="药品属性项目名称" islabel="true" cols="3" >${xiangm}</s:textarea>
					</s:row>
				</c:if>
				 <c:if test="${not empty dw}">
		   			<s:row>
						<s:textarea label="药品属性单位" islabel="true" cols="3" >${dw}</s:textarea>
					</s:row>
				</c:if>
				 <c:if test="${not empty message}">
		   			<s:row>
						<s:textarea label="消息" islabel="true" cols="3" >${message}</s:textarea>
					</s:row>
				</c:if>
				 <c:if test="${not empty zdy_type}">
		   			<s:row>
						<s:textarea label="自定义审查的类型" islabel="true" cols="3" >${zdy_type}</s:textarea>
					</s:row>
				</c:if>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">

</script>