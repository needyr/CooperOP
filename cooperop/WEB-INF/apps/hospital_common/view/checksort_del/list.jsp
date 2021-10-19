<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="智能审查标准分类管理">
<s:row>
</s:row>
	<s:row>
		<s:form border="0" id="form" label="快速查找">
			<s:row>
				<s:textfield label="关键字" name="filter" placeholder="请输入分类名称" cols="2"></s:textfield>
				<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="审查标准分类管理" autoload="false" action="hospital_common.checksort.query" sort="true">
			<s:toolbar>
				<s:button icon="" label="新增分类" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
			    <s:table.field name="sort_id" label="分类编号" datatype="" ></s:table.field>
				<s:table.field name="sort_name" label="分类名称" datatype="" ></s:table.field>
				<%-- <s:table.field name="type" label="合理用药分类" datatype="script" width="72">
					if(record.type=='1'){
						return "全院用药审查"
					}else if(record.type=='2'){
						return "自定义审查"
					}else if(record.type=='3'){
						return "his审查"
					}
				</s:table.field> --%>
				<s:table.field name="interceptor_name" label="拦截级别" width="50"></s:table.field>
				<s:table.field name="info_name" label="提示级别" width="50"></s:table.field>
				<s:table.field name="outpatient_check" label="门诊是否审查" datatype="script" width="72">
					if(record.outpatient_check == 'Y'){
						return "是"
					}else{
						return "否"
					}
				</s:table.field>
				<s:table.field name="emergency_check" label="急诊是否审查" datatype="script" width="72">
					if(record.emergency_check == 'Y'){
						return "是"
					}else{
						return "否"
					}
				</s:table.field>
				<s:table.field name="hospitalization_check" label="住院是否审查" datatype="script" width="72">
					if(record.hospitalization_check == 'Y'){
						return "是"
					}else{
						return "否"
					}
				</s:table.field>
				<s:table.field name="state" label="是否启用" datatype="script" width="50">
					if(record.state == 'Y'){
						return "是"
					}else{
						return "否"
					}
				</s:table.field>
				<s:table.field name="audit_explain" label="审查说明" hidden="true"></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="70">
					<a href="javascript:void(0)" onclick="update('$[sort_id]')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[sort_id]','$[sort_name]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		
	</s:row>
	
</s:page>
<script type="text/javascript">
	$(function(){
		query();
	});
	
	function query(){
		var qdata=$("#form").getData();
		$("#datatable").params(qdata);
		$("#datatable").refresh();
	}
	
	function Add(){
		$.modal("edits.html","新增分类",{
			width:"600px",
			height:"70%",
			callback : function(e){
				query();
			}
		});
	}
	
	function update(sort_id){
		$.modal("edits.html","修改分类",{
			width:"600px",
			height:"70%",
			sort_id:sort_id,
			callback : function(e){
				query();
			}
		});
	}
	
	 function Delete(sort_id,sort_name){
		$.confirm("是否确认删除分类\t"+sort_name+"？\t\n删除后无法恢复！",function callback(e){
			if(e==true){
				$.call("hospital_common.checksort.delete",{"sort_id":sort_id},function(s){
					query();
				});
			}
		});
	} 
</script>