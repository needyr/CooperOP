<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="自定义医保项目审批" disloggedin="true">
	<s:row>
		<s:form id="form" label="快速查找">
			<s:row>
				<s:textfield label="关键字" name="filter" placeholder="项目名称、	医保体系名称、转自费流程、费别、科室、医生、患者" cols="2"></s:textfield>
				<s:radio label="是否可以转自费" name="is_to_zf"  cols="1" value="" >
					<s:option value="1" label="可以转自费"></s:option>
					<s:option value="0" label="不可转自费"></s:option>
					<s:option value="" label="全部"></s:option>
				</s:radio>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
	    <s:table id="datatable" label="医保自定义审批项目维护" autoload="true"  action="hospital_common.imicitemflow.query"  sort="true">
	        <s:toolbar>
				<s:button label="新增" onclick="add()" icon="fa fa-plus"></s:button>
				<s:button label="查询" onclick="query()" icon="fa fa-search"></s:button>
			</s:toolbar>
			<s:table.fields>
		        <s:table.field name="item_name" label="项目名称" ></s:table.field>
		        <s:table.field name="interface_type_name" label="医保体系名称" ></s:table.field>
		        <s:table.field name="display_name" label="转自费流程" ></s:table.field>
		        <s:table.field name="charge_type_name" label="费别" datatype="script" >
					var charge_type_name=record.charge_type_name;
					if(charge_type_name != null && charge_type_name != ""){
	                	var length=charge_type_name.length;
	                	if(length >= 15){
	                		return '<font title="'+charge_type_name+'">'+charge_type_name.substring(0,15)+'...</font>';
	                	}else{
	                		return charge_type_name;
	                	}
					}else{
						return "";
					}
				</s:table.field>
				<s:table.field name="dept_name" label="科室" datatype="script"  >
					var dept_name=record.dept_name;
					if(dept_name != null && dept_name != ""){
	                	var length=dept_name.length;
	                	if(length >= 15){
	                		return '<font title="'+dept_name+'">'+dept_name.substring(0,15)+'...</font>';
	                	}else{
	                		return dept_name;
	                	}
					}else{
						return "";
					}
				</s:table.field>
				<s:table.field name="doctor_name" label="医生" datatype="script"  >
					var doctor_name=record.doctor_name;
					if(doctor_name != null && doctor_name != ""){
	                	var length=doctor_name.length;
	                	if(length >= 15){
	                		return '<font title="'+doctor_name+'">'+doctor_name.substring(0,15)+'...</font>';
	                	}else{
	                		return doctor_name;
	                	}
					}else{
						return "";
					}
				</s:table.field>
				<s:table.field name="patient_name" label="患者" datatype="script"  >
					var patient_name=record.patient_name;
					if(patient_name != null && patient_name != ""){
	                	var length=patient_name.length;
	                	if(length >= 15){
	                		return '<font title="'+patient_name+'">'+patient_name.substring(0,15)+'...</font>';
	                	}else{
	                		return patient_name;
	                	}
					}else{
						return "";
					}
				</s:table.field>
		        <s:table.field name="is_to_zf" label="是否可以转自费" width="90px" datatype="script" >
		        	var is_to_zf=record.is_to_zf;
		        	if(is_to_zf == 1){
		        		return '<font color="green">是</font>';
		        	}
		        	if(is_to_zf == 0){
		        		return '否';
		        	}
		        </s:table.field>
		        <s:table.field name="caozuo" label="操作" width="120" datatype="script">
		        	var html = [];
					html.push('<a href="javascript:void(0)" onclick="update(\''+record.id+'\')">编辑</a>   ');
					html.push('<a href="javascript:void(0)" onclick="mxlist(\''+record.id+'\')">明细</a>   ');
					html.push('<a href="javascript:void(0)" onclick="Delete(\''+record.id+'\')">删除</a>');
					return html.join("");
				</s:table.field>
			</s:table.fields>
	    </s:table>
	</s:row>
</s:page>
<script type="text/javascript">
$(function (){
	$("#patient_name").css("cursor","pointer");
	$("#doctor_name").css("cursor","pointer");
	$("#dept_name").css("cursor","pointer");
	$("#charge_type_name").css("cursor","pointer");
	//（切换项目）
	$("#ssks").change(function(){
		getitem($(this).val());
	});
});
function query(){
	var qdata=$("#form").getData();
	$("#datatable").params(qdata);
	$("#datatable").refresh();
}
/* 新增 */
function add(){
	$.modal("edit.html","新增项目",{
		width:"75%",
		height:"85%",
		callback : function(e){
			query();
		}
	});
};
/* 编辑 */
function update(id){
	$.modal("edit.html","编辑",{
		width:"75%",
		height:"85%",
		id:id,
		callback : function(e){
			query();
		}
	});
}
/* 明细 */
function mxlist(id){
	$.modal("mxlist.html","明细",{
		width:"85%",
		height:"95%",
		id:id,
		callback : function(e){
			query();
		}
	});
}
function Delete(id){
	$.confirm("是否确认删除，删除后无法恢复！",function callback(e){
		if(e==true){
			$.call("hospital_common.imicitemflow.delete",{"id":id},function(e){
				query();
	    	}); 
		}
	});
}
/* 新增根据item code去获取该项目信息  */ 
function getitem(p_key){
	$.call("hospital_common.imiccustomre.getforinsurvsprice",{"p_key":p_key},function(rtn){
		if(rtn==null||typeof(rtn) == "undefined"){
			$("input[name='item_code']").val("");
			$("input[name='item_name']").val("");
			$("input[name='interface_type']").val("");
			$("input[name='interface_type_name']").val("");
		}else{
			$("input[name='item_code']").val(rtn.item_code);
			$("input[name='item_name']").val(rtn.item_name);
			$("input[name='interface_type']").val(rtn.interface_type);
			$("input[name='interface_type_name']").val(rtn.interface_type_name);
		}
	}); 
}

</script>