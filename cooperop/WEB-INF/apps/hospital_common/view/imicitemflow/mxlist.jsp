<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="项目限专规则" ismodal="true">
	<s:row>
		<s:form id="form" label="审批项目信息">
			<s:row>
				<%-- <s:autocomplete  id="ssks" label="项目编号" name="p_key" action="hospital_common.imiccustomre.querynotimicsc" limit="10" editable="false" cols="1" required="true">
					<s:option value="$[p_key]" label="$[p_key]">
						<span style="float:left;display:block;width:80px;">$[item_code]</span>
						<span style="float:left;display:block;width:200px;">$[item_name]</span>
						<span style="float:left;display:block;width:70px;">$[interface_type_name]</span>
					</s:option>
				</s:autocomplete> --%>
				 <input name="parent_id" type="hidden"/>
				 <s:textfield name="item_name" label="项目名称" cols="2"  disabled="disabled" title="" ></s:textfield>
				 <s:textfield name="pizhwh" label="批准文号"  cols="1" disabled="disabled" value="${pizhwh}"></s:textfield>
			</s:row>
			<s:row>
				 <s:textfield name="item_spec" label="规格"  disabled="disabled" value="${item_spec}"></s:textfield>
				 <s:textfield name="item_units" label="单位"  disabled="disabled" value="${item_units}"></s:textfield>
				 <s:textfield name="shengccj" label="生产厂家"  disabled="disabled" value="${shengccj}"></s:textfield>
				 <s:textfield name="interface_type_name" label="医保类型" disabled="disabled"></s:textfield>
				 <s:textfield name="display_name" label="转自费流程" disabled="disabled"></s:textfield>
				 <s:textfield name="charge_type_name" label="费别"  disabled="disabled"></s:textfield>
				 <s:textfield name="dept_name" label="科室"  disabled="disabled"></s:textfield>
				 <s:textfield name="doctor_name" label="医生" disabled="disabled" ></s:textfield>
				 <s:textfield name="patient_name" id="patient_name" label="患者" disabled="disabled"></s:textfield>
			
			</s:row>
		</s:form>
	</s:row>
	<s:table id="datatable" label="规则" autoload="false" action="hospital_common.imicitemflow.querymx" sort="true"  limit="10" active="true">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add()"></s:button>
			</s:toolbar>
			<s:table.fields >
		        <s:table.field name="nwarn_message" label="警示信息" datatype="script"  >
					var nwarn_message=record.nwarn_message;
					if(nwarn_message != null && nwarn_message != ""){
	                	var length=nwarn_message.length;
	                	if(length >= 9){
	                		return '<font title="'+nwarn_message+'">'+nwarn_message.substring(0,9)+'...</font>';
	                	}else{
	                		return nwarn_message;
	                	}
					}else{
						return "";
					}
				</s:table.field>
				<s:table.field name="d_type_name" label="类型" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="thirdt_name" label="问题类型" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="提示等级" ></s:table.field>
		        <s:table.field name="caozuo" label="操作" width="100" datatype="script">
		        	var html = [];
					html.push('<a href="javascript:void(0)" onclick="update(\''+record.id+'\',\''+record.parent_id+'\')">修改</a>     ');
					html.push('<a href="javascript:void(0)" onclick="Delete(\''+record.id+'\',\''+record.parent_id+'\')">删除</a>');
					return html.join("");
				</s:table.field>
			</s:table.fields>
		</s:table>
</s:page>
<script type="text/javascript">
	$(function(){
		var id = '${param.id}'
		getitem(id);
		query(id);
	});
	function query(id){
		$("#datatable").params({"parent_id":id});
		$("#datatable").refresh();
	}
	/* 新增 */
	function add(){
		var parent_id= $("[name='parent_id']").val();
		$.modal("mxedit.html","新增项目",{
			width:"75%",
			height:"85%",
			parent_id:$("[name='parent_id']").val(),
			callback : function(e){
				query(parent_id);
			}
		});
	};
	/* 编辑 */
	function update(id,parent_id){
		$.modal("mxedit.html","编辑",{
			width:"75%",
			height:"85%",
			id:id,
			callback : function(e){
				query(parent_id);
			}
		});
	}
	function getitem(id){
    	$.call("hospital_common.imicitemflow.edit",{"id":id},function(rtn){
   			$("[name='parent_id']").val(rtn.id);
   			$("[name='item_name']").val(rtn.item_name);
   			$("[name='item_name']")[0].title=rtn.item_name;
   			$("[name='interface_type_name']").val(rtn.interface_type_name);
   			$("[name='interface_type_name']")[0].title=rtn.interface_type_name;
   			$("[name='display_name']").val(rtn.display_name);
   			$("[name='display_name']")[0].title=rtn.display_name;
   			$("[name='charge_type_name']").val(rtn.charge_type_name);
   			$("[name='charge_type_name']")[0].title=rtn.charge_type_name;
   			$("[name='dept_name']").val(rtn.dept_name);
   			$("[name='dept_name']")[0].title=rtn.dept_name;
   			$("[name='doctor_name']").val(rtn.doctor_name);
   			$("[name='doctor_name']")[0].title=rtn.doctor_name;
   			$("[name='patient_name']").val(rtn.patient_name);
   			$("[name='patient_name']")[0].title=rtn.patient_name;
   			$("[name='item_spec']").val(rtn.item_spec);
   			$("[name='item_spec']")[0].title=rtn.item_spec;
			$("[name='item_units']").val(rtn.item_units);
   			$("[name='item_units']")[0].title=rtn.item_units;
			$("[name='shengccj']").val(rtn.shengccj);
   			$("[name='shengccj']")[0].title=rtn.shengccj;
			$("[name='pizhwh']").val(rtn.pizhwh);
   			$("[name='pizhwh']")[0].title=rtn.pizhwh;
    		
    	}); 
    }
	function Delete(id,parent_id){
		$.confirm("是否确认删除，删除后无法恢复！",function callback(e){
			if(e==true){
				$.call("hospital_common.imicitemflow.deletemx",{"id":id},function(e){
					query(parent_id);
		    	}); 
			}
		});
	}
</script>