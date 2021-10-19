<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="">
	<s:row>
		<s:form fclass="portlet light bordered">
			<s:row>
				<s:textfield islabel="true" label="部 门 ：" name="department_name" value="${department_name}"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield islabel="true" label="岗 位 ：" name="name" value="${name}"></s:textfield>
				<s:textfield islabel="true" label="别 名 ：" name="othername" value="${othername}"></s:textfield>
				<s:textfield islabel="true" label="性 质 ：" name="property" value="${property}"></s:textfield>
				<s:textfield islabel="true" label="绩 效 ：" name="performance" value="${performance}"></s:textfield>
			</s:row>
			<s:row>
				<s:textarea islabel="true" label="福 利 ：" cols="4" name="welfare" value="${welfare}"></s:textarea>
				<s:textarea islabel="true" label="职 责 ：" cols="4" name="responsibility" value="${responsibility}"></s:textarea>
				<s:textarea islabel="true" label="招聘要求 ：" cols="4" name="requirements" value="${requirements}"></s:textarea>
				<s:textarea islabel="true" label="描 述 ：" cols="4" name="describe" value="${describe}"></s:textarea>
			</s:row>
			<s:row>
				<s:file islabel="true" label="材 料 ：" name="material" value="${material }" cols="4"></s:file>
			</s:row>
			<s:row>
				<s:textarea islabel="true" label="备 注 ：" name="remark" value="${remark }" cols="4"></s:textarea>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
</script>