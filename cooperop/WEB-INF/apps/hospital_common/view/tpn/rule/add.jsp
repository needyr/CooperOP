<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="新增">
	<s:row>
		<s:form label="TPN资料" id="dataForm">

			<s:toolbar>
				<s:row>
					<s:button label="保存" onclick="save()" icon="fa fa-save" style="background: #368bdb;text-align: center;color: #FFF;"></s:button>
					<s:button label="取消" onclick="$.closeModal(false);" icon="icon-ban"></s:button>
				</s:row>
			</s:toolbar>

			<s:row>
				<c:if test="${empty tpnzl_id}">
				<s:textfield label="TPN编号"   name="tpnzl_id" required="true"></s:textfield>
				</c:if>
				<c:if test="${not empty tpnzl_id}">
				<input type="text" name="tpnzl_id" style="display:none" value="${tpnzl_id}">
				</c:if>
				<s:textfield label="TPN资料名称" name="tpnzl_name"   required="true" value="${tpnzl_name }"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="开始年龄" name="tpnzl_nianl_start" datatype="number" value="${tpnzl_nianl_start }"></s:textfield>
				<s:textfield label="结束年龄" name="tpnzl_nianl_end" datatype="number" value="${tpnzl_nianl_end }"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="TPN资料类型" name="tpnzl_lx" value="${tpnzl_lx }"></s:textfield>
				<s:switch label="是否启用" name="state" value="${state}" offtext="否" offvalue="0" ontext="是" onvalue="1"></s:switch>
			</s:row>
			<s:row>
				<s:textfield label="序号" name="sort" required="true" value="${sort }"></s:textfield>
			</s:row>
			<s:row>
				<s:textarea label="备注" name="beizhu"  cols="4" value="${beizhu }"></s:textarea>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
//新增
	function save() {
		if (!$("form").valid()) {
			return false;
		}
		var data = $("#dataForm").getData();
		if('${tpnzl_id}'){
			$.call("hospital_common.tpn.rule.updateById", data, function(rtn) {
				$.closeModal(true);			
			});
		}else{
			$.call("hospital_common.tpn.rule.insert", data, function(rtn) {
				if (rtn) {
					$.closeModal(true);			
				}else{
					$.message("TPN编号重复，请重新输入！");
				}
			});
		}
		
		
	}
	
</script>
