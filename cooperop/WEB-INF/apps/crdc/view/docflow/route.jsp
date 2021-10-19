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
	pageContext.setAttribute("fields", CommonFun.json2Object(request.getParameter("fields"), List.class));
	pageContext.setAttribute("pageattr", CommonFun.json2Object(request.getParameter("pageattr"), Map.class));
%>
<s:page title="流程属性" ismodal="true">
	<s:row>
		<s:form>
			<s:toolbar>
				<s:button icon="fa fa-edit" size="btn-sm btn-default" label="保存" onclick="save()"></s:button>
				<s:button icon="fa fa-ban" size="btn-sm btn-default" label="取消" onclick="$.closeModal();"></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield cols="2" label="流向编号" name="id" value="${pageParam.id}" placeholder="源节点是判断节点时作为流向判断条件" ></s:textfield>
				<s:textfield cols="2" label="流向名称" name="name" value="${pageParam.name}" placeholder="用于展现的流程名称" ></s:textfield>
			</s:row>
			<s:row>
				<s:select label="文档变量" name="fieldsselect1">
					<s:option label="发起人{creator_name}" value="creator_name"></s:option>
					<s:option label="发起人编号{creator_no}" value="creator_no"></s:option>
					<s:option label="发起部门{department_name}" value="department_name"></s:option>
					<c:forEach items="${fields}" var="f">
						<s:option label="${f.label }{${f.name }}" value="${f.name }"></s:option>
					</c:forEach>
				</s:select>
				<s:select name="conditions">
					<s:option label="并且大于" value="1"></s:option>
					<s:option label="并且等于" value="2"></s:option>
					<s:option label="并且小于" value="3"></s:option>
					<s:option label="并且不等于" value="4"></s:option>
					<s:option label="或者大于" value="5"></s:option>
					<s:option label="或者等于" value="6"></s:option>
					<s:option label="或者小于" value="7"></s:option>
					<s:option label="或者不等于" value="8"></s:option>
				</s:select>
				<s:textfield name="values" ></s:textfield>
				<s:button label="添加条件" onclick="updatesql()"></s:button>
			</s:row>
			<s:row>
				<s:textarea cols="3" autosize="true" rows="6" label="判断SQL" name="expr_scheme" value="${pageParam.expr_scheme}" placeholder="流向判断条件SQL语句，源节点不是判断节点时有效，最终结果必须是一条只包含一个字符串字段的记录，当返回Y时表示此流向有效，其他表示无效，无记录也是无效，传入变量只有djbh。如：select a from b where djbh = :djbh"></s:textarea>
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
		$.closeModal(data);
	}
	function updatesql(){
		var data = $("form").getData();
		if(data.fieldsselect1){
			var s = [];
			if(data.expr_scheme){
				s.push(data.expr_scheme);
			}else{
				s.push("select case when(count(1)>0) then 'Y' else 'N' end from dfl_"+'${pageattr.flag}'+"_"+'${pageattr.schemeid}' +"(nolock)  t ");
				s.push(" left join cooperop.dbo.wf_order(nolock)  wo on wo.order_no = t.djbh ");
				s.push(" where t.djbh=:djbh ");
			}
			if(data.fieldsselect1 == 'creator_name'){
				if(data.conditions == '2'){
					s.push("  and wo.creator =  (select id from cooperop.dbo.v_system_user(nolock)  where name='"+data.values+"')")
				}else if(data.conditions == '4'){
					s.push("  and wo.creator !=  (select id from cooperop.dbo.v_system_user(nolock)  where name='"+data.values+"')")
				}else if(data.conditions == '6'){
					s.push("  or wo.creator =  (select id from cooperop.dbo.v_system_user(nolock)  where name='"+data.values+"')")
				}else if(data.conditions == '8'){
					s.push("  or wo.creator !=  (select id from cooperop.dbo.v_system_user(nolock)  where name='"+data.values+"')")
				}
			}else if(data.fieldsselect1 == 'creator_no'){
				if(data.conditions == '2'){
					s.push("  and wo.creator =  (select id from cooperop.dbo.v_system_user(nolock)  where no='"+data.values+"')")
				}else if(data.conditions == '4'){
					s.push("  and wo.creator !=  (select id from cooperop.dbo.v_system_user(nolock)  where no='"+data.values+"')")
				}else if(data.conditions == '6'){
					s.push("  or wo.creator =  (select id from cooperop.dbo.v_system_user(nolock)  where no='"+data.values+"')")
				}else if(data.conditions == '8'){
					s.push("  or wo.creator !=  (select id from cooperop.dbo.v_system_user(nolock)  where no='"+data.values+"')")
				}
				
			}else if(data.fieldsselect1 == 'department_name'){
				if(data.conditions == '2'){
					s.push(" and (select  stringvalue  from dbo.parsejson(wo.variable) where  ")
					s.push(" name = 'system_department_id') =(select id from cooperop.dbo.system_department(nolock) where name ='"+data.values+"')")
				}else if(data.conditions == '4'){
					s.push(" and (select  stringvalue  from dbo.parsejson(wo.variable) where  ")
					s.push(" name = 'system_department_id') !=(select id from cooperop.dbo.system_department(nolock)  where name ='"+data.values+"')")
				}else if(data.conditions == '6'){
					s.push(" or (select  stringvalue  from dbo.parsejson(wo.variable) where  ")
					s.push(" name = 'system_department_id') =(select id from cooperop.dbo.system_department(nolock)  where name ='"+data.values+"')")
				}else if(data.conditions == '8'){
					s.push(" or (select  stringvalue  from dbo.parsejson(wo.variable) where  ")
					s.push(" name = 'system_department_id') !=(select id from cooperop.dbo.system_department(nolock)  where name ='"+data.values+"')")
				}
			}/* else if(data.fieldsselect1 == 'department_no'){
				s.push(" and (select  stringvalue  from    dbo.parsejson(wo.variable) where  ")
				s.push(" name = 'system_department_id') =(select id from system_department where no ='"+data.values+"')")
			} */else{
				if(data.conditions < 5){
					s.push(" and t."+data.fieldsselect1);
				}else{
					s.push(" or t."+data.fieldsselect1);
				}
				if(data.conditions == '1' || data.conditions == '5'){
					s.push(" > '"+data.values+"'");
				}else if(data.conditions == '2' || data.conditions == '6'){
					s.push(" = '"+data.values+"'");
				}else if(data.conditions == '3' || data.conditions == '7'){
					s.push(" < '"+data.values+"'");
				}else if(data.conditions == '4' || data.conditions == '8'){
					s.push(" != '"+data.values+"'");
				}
			}
			$("[name='expr_scheme']").setData(s.join(""));
		}
	}
</script>