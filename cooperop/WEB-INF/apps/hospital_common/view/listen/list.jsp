<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="服务监听" disloggedin="true">
		<s:row>
			<s:form label="智能助手" id="data-form">
				<s:row>
					<s:textfield label="密码" name="password" cols="1" required="true"></s:textfield>
					<s:button label="智能处理" onclick="reStart()"></s:button>
				</s:row>
			</s:form>
		</s:row>
</s:page>
<script type="text/javascript">
	function reStart() {
		if ($("form").valid()){
			$.call("hospital_common.listen.reStart", $("#data-form").getData(), function (ret){
				if (ret && ret.state == 1){
					$.message("正在为您智能处理异常，请稍等...(可能耗费数分钟！)");
				}else{
					$.message("对不起，密码错误，无法完成操作！");
				}
			});
		}
	}
</script>
