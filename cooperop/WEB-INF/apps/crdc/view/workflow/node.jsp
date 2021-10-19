<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="流程属性" ismodal="true">
	<s:row>
		<s:form>
			<s:toolbar>
				<s:button icon="fa fa-edit" size="btn-sm btn-default" label="保存" onclick="save()"></s:button>
				<s:button icon="fa fa-ban" size="btn-sm btn-default" label="取消" onclick="$.closeModal();"></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield cols="1" label="节点编号" name="id" value="${pageParam.id}" placeholder="流程唯一的节点英文编号" required="true"></s:textfield>
				<s:textfield cols="2" label="节点名称" name="name" value="${pageParam.name}" placeholder="用于展现的节点名称" required="true"></s:textfield>
			</s:row>
			<c:if test="${param.type eq 'task' }">
			<s:row>
				<s:autocomplete cols="1" label="节点单据" name="instance_bill" placeholder="在任务中心中使用此单据打开审核" required="true" action="crdc.workflowdesigner.selectbill" value="${pageParam.instance_bill}" params="{&#34;system_product_code&#34;:&#34;${pageParam.system_product_code }&#34;}" editable="true">
					<s:option value="$[system_product_code].$[type].$[flag].$[id]" label="$[system_product_code].$[type].$[flag].$[id]">$[system_product_code].$[type].$[flag].$[id]($[description])</s:option>
				</s:autocomplete>
				<%-- <s:textfield cols="1" label="节点单据" name="instance_bill" value="${pageParam.instance_bill}" placeholder="在任务中心中使用此单据打开审核" required="true" dbl_action="crdc.workflow.selectbill"></s:textfield> --%>
				<s:textfield cols="1" datatype="number" label="期望完成时间" name="expireTime" value="${pageParam.expireTime}" placeholder="任务到达后多少分钟未处理将认为超期"></s:textfield>
				<s:textfield cols="1" datatype="number" label="提醒时间" name="reminderTime" value="${pageParam.reminderTime}" placeholder="任务到达后多少分钟未处理将发消息提醒"></s:textfield>
				<s:textfield cols="1" datatype="number" label="提醒次数" name="reminderRepeat" value="${pageParam.reminderRepeat}" placeholder="重复提醒次数，每【提醒时间】分钟提醒"></s:textfield>
			</s:row>
			<s:row>
				<s:textarea cols="2" autosize="true" rows="4" label="主办人获取SQL" name="processor_scheme" placeholder="主办人获取SQL语句，最终结果必须是只包含一个字符串字段（职员编号）的结果集，传入变量只有djbh。如：select a from b where djbh = :djbh">${pageParam.processor_scheme}</s:textarea>
				<s:autocomplete id="processors_role_label" label="or 主办角色" name="processor_role" action="setting.role.query" value="${pageParam.processor_role}" text="${pageParam.processors_role_label }" placeholder="按角色的机构和部门权限进行自动分配">
					<s:option label="$[name]" value="$[id]">$[name]</s:option>
				</s:autocomplete>
				<s:autocomplete id="processors_post_label" label="or 主办岗位" name="processor_post" action="setting.post.query" value="${pageParam.processor_post}" text="${pageParam.processors_post_label }" placeholder="按岗位进行自动分配">
					<s:option label="$[jg_dep_post]" value="$[id]">$[jg_dep_post]</s:option>
				</s:autocomplete>
				<s:autocomplete cols="2" id="processors_label" label="or 指定主办人" name="processors" action="setting.user.querymine" value="${pageParam.processors}" text="${pageParam.processors_label }" placeholder="职员编号，','分割">
					<s:option label="$[id]-$[name]" value="$[id]">$[id]-$[name]</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>				
				<s:textarea cols="2" autosize="true" rows="4" label="协办人获取SQL" name="assistant_scheme" placeholder="协办人获取SQL语句，最终结果必须是只包含一个字符串字段（职员编号）的结果集，传入变量只有djbh。如：select a from b where djbh = :djbh">${pageParam.assistant_scheme}</s:textarea>
				<s:autocomplete cols="2" id="assistants_role_label" label="or 协办角色" name="assistant_role" action="setting.role.query" value="${pageParam.assistant_role}" text="${pageParam.assistants_role_label }" placeholder="按角色的机构和部门权限进行自动分配">
					<s:option label="$[name]" value="$[id]">$[name]</s:option>
				</s:autocomplete>
				<s:autocomplete id="assistants_label" cols="2" label="or 指定协办人" name="assistants" action="setting.user.querymine" value="${pageParam.assistants}" text="${pageParam.assistants_label }" placeholder="职员编号，','分割">
					<s:option label="$[id]-$[name]" value="$[id]">$[id]-$[name]</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<s:switch label="是否微信确认" name="wx_auth" value="${pageParam.wx_auth }" onvalue="Y" offvalue="N"></s:switch>
				<s:switch label="自动审核" name="auto_pass" value="${pageParam.auto_pass }" onvalue="Y" offvalue="N"></s:switch>
			</s:row>
			</c:if>
			<c:if test="${param.type eq 'countersign' }">
			<s:row>
				<s:textfield cols="1" label="节点单据" name="instance_bill" value="${pageParam.instance_bill}" placeholder="在任务中心中使用此单据打开审核" required="true" dbl_action="crdc.workflow.selectbill"></s:textfield>
				<s:textfield cols="1" datatype="number" label="期望完成时间" name="expireTime" value="${pageParam.expireTime}" placeholder="任务到达后多少分钟未处理将认为超期"></s:textfield>
				<s:textfield cols="1" datatype="number" label="提醒时间" name="reminderTime" value="${pageParam.reminderTime}" placeholder="任务到达后多少分钟未处理将发消息提醒"></s:textfield>
				<s:textfield cols="1" datatype="number" label="提醒次数" name="reminderRepeat" value="${pageParam.reminderRepeat}" placeholder="重复提醒次数，每【提醒时间】分钟提醒"></s:textfield>
			</s:row>
			<s:row>
				<s:textarea cols="2" autosize="true" rows="4" label="会签人获取SQL" name="countersign_scheme" value="${pageParam.countersign_scheme}" placeholder="会签人获取SQL语句，最终结果必须是只包含一个字符串字段（职员编号）的结果集，传入变量只有djbh。如：select a from b where djbh = :djbh"></s:textarea>
				<s:textfield cols="2" label="or 会签角色" name="countersign_role" value="${pageParam.countersign_role}" placeholder="按角色的机构和部门权限进行自动分配"></s:textfield>
				<s:textfield cols="2" label="or 指定会签人" name="countersigns" value="${pageParam.countersigns}" placeholder="职员编号，','分割"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield cols="2" datatype="number" label="会签成功人数" name="finish_num" value="${pageParam.finish_num}" placeholder="几人完成会签后任务完成，不等待其他人员，为0标示需全部会签。"></s:textfield>
			</s:row>
			</c:if>
			<c:if test="${param.type eq 'state' }">
			<s:row>
				<s:textarea cols="3" autosize="true" rows="6" label="状态判断SQL" name="state_scheme" required="true" placeholder="完成状态节点等待的判断条件SQL语句，最终结果必须是一条只包含一个字符串字段（是/否）的记录，传入变量只有djbh。如：select a from b where djbh = :djbh">${pageParam.state_scheme}</s:textarea>
			</s:row>
			</c:if>
			<c:if test="${param.type eq 'auto' }">
			<s:row>
				<s:textarea cols="3" autosize="true" rows="6" label="自动执行SQL" name="execute_scheme" required="true" placeholder="任务到达此节点时自动执行SQL，无需存在返回">${pageParam.execute_scheme}</s:textarea>
			</s:row>
			</c:if>
			<c:if test="${param.type eq 'judge' }">
			<s:row>
				<s:textarea cols="3" autosize="true" rows="6" label="判断SQL" name="expr_scheme" required="true" placeholder="流向判断条件SQL语句，最终结果必须是一条只包含一个字符串字段（后续流向编号）的记录，传入变量只有djbh。如：select a from b where djbh = :djbh">${pageParam.expr_scheme}</s:textarea>
			</s:row>
			</c:if>
			
			<c:if test="${param.type eq 'sub-process' }">
			<s:row>
				<s:autocomplete cols="1" label="节点单据" name="processname" placeholder="在任务中心中使用此单据打开审核" required="true" action="crdc.workflowdesigner.query" value="${pageParam.processname}" editable="true">
					<s:option value="$[system_product_code]-$[id]" label="$[system_product_code]-$[id]">$[system_product_code]-$[id]($[name])</s:option>
				</s:autocomplete>
				<%-- <s:textfield cols="1" label="子流程" name="processname" value="${pageParam.processname}" ></s:textfield> --%>
			</s:row>
			</c:if>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	function save() {
		if (!$("form").valid()) {
			return false;	
		}
		var data = $("form").getData();
		if(data.processor_role){
			data.processors_role_label = $("#processors_role_label").val();
		}
		
		if(data.processor_post){
			data.processors_post_label = $("#processors_post_label").val();
		}
		
		if(data.processors){
			data.processors_label = $("#processors_label").val();
		}
		
		if(data.assistant_role){
			data.assistants_role_label = $("#assistants_role_label").val();
		}
		
		if(data.assistants){
			data.assistants_label = $("#assistants_label").val();
		}
		$.closeModal(data);
	}
</script>