<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%
	List<Map<String, Object>> f = CommonFun.json2Object(request.getParameter("fields"), List.class);
	String s = "";
	for(Map<String, Object> m : f){
		s += "{"+m.get("name")+"}";
	}
	pageContext.setAttribute("fvalue", s);
	pageContext.setAttribute("fields", f);
	pageContext.setAttribute("pageattr", CommonFun.json2Object(request.getParameter("pageattr"), Map.class));
%>
<s:page title="流程属性" ismodal="true">
	<s:row>
		<s:form id="fform">
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
				<s:textfield cols="1" datatype="number" label="期望完成时间" name="expireTime" value="${pageParam.expireTime}" placeholder="任务到达后多少分钟未处理将认为超期"></s:textfield>
				<s:textfield cols="1" datatype="number" label="提醒时间" name="reminderTime" value="${pageParam.reminderTime}" placeholder="任务到达后多少分钟未处理将发消息提醒"></s:textfield>
				<s:textfield cols="1" datatype="number" label="提醒次数" name="reminderRepeat" value="${pageParam.reminderRepeat}" placeholder="重复提醒次数，每【提醒时间】分钟提醒"></s:textfield>
			</s:row>
			<s:row>
				<s:select id="processorsv" label="主办变量" placeholder="根据变量获取主办人" name="processorsv" value="${pageParam.processorsv }" onchange="updatesql();">
					<s:option label="" value=""></s:option>
					<s:option label="发起人" value="creator"></s:option>
				</s:select>
				<s:autocomplete id="processors_role_label" label="or 主办角色" name="processor_role" action="setting.role.query" value="${pageParam.processor_role}" text="${pageParam.processors_role_label }" placeholder="按角色的机构和部门权限进行自动分配">
					<s:option label="$[name]" value="$[id]">$[name]</s:option>
				</s:autocomplete>
				<s:autocomplete cols="2" id="processors_label" label="or 指定主办人" name="processors" action="setting.user.querymine" value="${pageParam.processors}" text="${pageParam.processors_label }" placeholder="职员编号，','分割">
					<s:option label="$[id]-$[name]" value="$[id]">$[id]-$[name]</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>				
				<s:autocomplete cols="2" id="assistants_role_label" label="or 协办角色" name="assistant_role" action="setting.role.query" value="${pageParam.assistant_role}" text="${pageParam.assistants_role_label }" placeholder="按角色的机构和部门权限进行自动分配">
					<s:option label="$[name]" value="$[id]">$[name]</s:option>
				</s:autocomplete>
				<s:autocomplete id="assistants_label" cols="2" label="or 指定协办人" name="assistants" action="setting.user.querymine" value="${pageParam.assistants}" text="${pageParam.assistants_label }" placeholder="职员编号，','分割">
					<s:option label="$[id]-$[name]" value="$[id]">$[id]-$[name]</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<s:textarea cols="4" autosize="true" rows="4" label="主办人获取SQL" name="processor_scheme" value="${pageParam.processor_scheme}" placeholder="主办人获取SQL语句，最终结果必须是只包含一个字符串字段（职员编号）的结果集，传入变量只有djbh。如：select a from b where djbh = :djbh"></s:textarea>
			</s:row>
			</c:if>
			<c:if test="${param.type eq 'countersign' }">
			<s:row>
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
				<s:textarea cols="3" autosize="true" rows="6" label="状态判断SQL" name="state_scheme" value="${pageParam.state_scheme}" required="true" placeholder="完成状态节点等待的判断条件SQL语句，最终结果必须是一条只包含一个字符串字段（是/否）的记录，传入变量只有djbh。如：select a from b where djbh = :djbh"></s:textarea>
			</s:row>
			</c:if>
			<c:if test="${param.type eq 'auto' }">
			<s:row>
				<s:textarea cols="3" autosize="true" rows="6" label="自动执行SQL" name="execute_scheme" value="${pageParam.execute_scheme}" required="true" placeholder="任务到达此节点时自动执行SQL，无需存在返回"></s:textarea>
			</s:row>
			</c:if>
			<c:if test="${param.type eq 'judge' }">
			<s:row>
				<s:textarea cols="3" autosize="true" rows="6" label="判断SQL" name="expr_scheme" value="${pageParam.expr_scheme}" required="true" placeholder="流向判断条件SQL语句，最终结果必须是一条只包含一个字符串字段（后续流向编号）的记录，传入变量只有djbh。如：select a from b where djbh = :djbh"></s:textarea>
			</s:row>
			</c:if>
			<s:row>
				<s:textarea cols="2" rows="4" label="可读字段" name="canreadfields" value="${pageParam.canreadfields }" 
				placeholder="直接输入，可在下方列表中双击加入字段变量" required="true"></s:textarea>
				<s:select id="fieldsselect" label="文档变量" multiple="multiple" style="height:auto;" ondblclick="addParam();">
					<c:forEach items="${fields}" var="f">
						<s:option label="${f.label }{${f.name }}" value="${f.name }"></s:option>
					</c:forEach>
				</s:select>
				<s:switch label="全部加入" onchange="add();" name="addall"></s:switch>
			</s:row>
			<s:row>
				<s:textarea cols="2" rows="4" label="可改字段" name="canmodifyfields" value="${pageParam.canmodifyfields }" 
				placeholder="直接输入，可在下方列表中双击加入字段变量" required="true"></s:textarea>
				<s:select id="fieldsselect1" label="文档变量" multiple="multiple" style="height:auto;" ondblclick="addParam1();">
					<c:forEach items="${fields}" var="f">
						<s:option label="${f.label }{${f.name }}" value="${f.name }"></s:option>
					</c:forEach>
				</s:select>
				<s:switch label="全部加入" onchange="add1()" name="addall1"></s:switch>
			</s:row>
			<s:row>
				<s:textarea cols="2" rows="4" label="必填字段" name="requiredfields" value="${pageParam.requiredfields }" 
				placeholder="直接输入，可在下方列表中双击加入字段变量" required="true"></s:textarea>
				<s:select id="fieldsselect2" label="文档变量" multiple="multiple" style="height:auto;" ondblclick="addParam2();">
					<c:forEach items="${fields}" var="f">
						<s:option label="${f.label }{${f.name }}" value="${f.name }"></s:option>
					</c:forEach>
				</s:select>
				<s:switch label="全部加入" onchange="add2()" name="addall2"></s:switch>
			</s:row>
			<s:row>
				<s:switch label="可驳回" name="caozuo1" value="${pageParam.caozuo1 }" ></s:switch>
				<s:switch label="可驳回上一步" name="caozuo2" value="${pageParam.caozuo2 }"></s:switch>
				<%-- <s:switch label="驳回" name="caozuo3" value="${pageParam.caozuo3 }"></s:switch> --%>
				<%-- <s:switch label="可暂存" name="caozuo4" value="${pageParam.caozuo4 }"></s:switch> --%>
				<%-- <s:checkbox label="操作权限" name="caozuo" value="${pageParam.caozuo }" cols="4">
					<s:option label="驳回" value="1"></s:option>
					<s:option label="驳回上一步" value="2"></s:option>
					<s:option label="重审" value="3"></s:option>
					<s:option label="待定" value="4"></s:option>
				</s:checkbox> --%>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	function save() {
		if (!$("form").valid()) {
			return false;	
		}
		var data = $("form").getData();
		data.assistants_role_label = $("#assistants_role_label").val();
		data.assistants_label = $("#assistants_label").val();
		data.processors_role_label = $("#processors_role_label").val();
		data.processors_label = $("#processors_label").val();
		data.instance_bill = '${pageattr.system_product_code}'+".document."+'${pageattr.flag}'+"."+'${pageattr.schemeid}'+data.id;
		$.closeModal(data);
	}
	function updatesql(){
		var data = $("form").getData();
		var s = [];
		if(data.processorsv=='creator'){
			s.push("select creator from cooperop.dbo.wf_order(nolock)  where order_no = :djbh ");
		}
		$("[name='processor_scheme']").setData(s.join(""));
	}
	function addParam() {
		if ($("#fieldsselect").val()) {
			$("[name='canreadfields']").val($("[name='canreadfields']").val() + "{" + $("#fieldsselect").val() + "}")
		}
	}
	function addParam1() {
		if ($("#fieldsselect1").val()) {
			$("[name='canmodifyfields']").val($("[name='canmodifyfields']").val() + "{" + $("#fieldsselect1").val() + "}")
		}
	}
	function addParam2() {
		if ($("#fieldsselect2").val()) {
			$("[name='requiredfields']").val($("[name='requiredfields']").val() + "{" + $("#fieldsselect2").val() + "}")
		}
	}
	function add(){
		var data = $("form").getData();
		if(data.addall=='是'){
			v = '${fvalue}';
			$("[name='canreadfields']").val(v);
		}
	}
	function add1(){
		var data = $("form").getData();
		if(data.addall1=='是'){
			v = '${fvalue}';
			$("[name='canmodifyfields']").val(v);
		}
	}
	function add2(){
		var data = $("form").getData();
		if(data.addall2=='是'){
			v = '${fvalue}';
			$("[name='requiredfields']").val(v);
		}
	}
</script>