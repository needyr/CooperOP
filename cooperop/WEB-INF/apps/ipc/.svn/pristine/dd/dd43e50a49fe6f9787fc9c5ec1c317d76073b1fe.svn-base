<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="审查问题标记差评" disloggedin="true">
	<s:row>
		<s:form id="form" label="快速查找">
			<s:row>
				<input type="hidden" name="product_code"  value="hospital_common"/>
				<s:textfield label="关键字"  name="filter" placeholder="请输入药品名称名称" cols="1"></s:textfield>
				<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="	审查问题标记差评列表" autoload="true"  sort="false" action="ipc.badpost.query"  >
			<s:table.fields>
				<s:table.field name="related_drugs_show" label="药品名称"  datatype="template">
					<a href="javascript:void(0);" onclick="yaopin('$[order_code]','$[related_drugs_show]')">$[related_drugs_show]</a>
				</s:table.field>
				<s:table.field name="sort_name" label="审查类型"></s:table.field>
				<s:table.field name="sys_level_name" label="问题等级"></s:table.field>
				<s:table.field name="description" label="审查结果"  datatype="script">
					var description=record.description;
                	var length=description.length;
                	if(length >= 28){
                		return '<font title="'+description+'">'+description.substring(0,28)+'......</font>';
                	}else{
                		return description;
                	}
				</s:table.field>
				
				<s:table.field name="reference" label="参考"></s:table.field>
				<s:table.field name="is_new" label="医嘱类型" datatype="script">
					var is_new=record.is_new;
					if(is_new==1){
						return "新开医嘱";
					}else {
						return "在用医嘱";
					}
				</s:table.field>
				<s:table.field name="doctor_name" label="开嘱医生"></s:table.field>
				<s:table.field name="Check_Pharmacist" label="审查药师"></s:table.field>
				<s:table.field name="d_type" label="科室类型"  datatype="script">
					var d_type=record.d_type;
					var dept_name=record.dept_name;
					if(d_type==1){
						d_type = "住院";
					}else if(d_type==2){
						d_type =  "门诊";
					}else if(d_type==3){
						d_type =  "急诊";
					}
					return record.dept_name+'['+d_type+']';
				</s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="70px">
					<a href="javascript:void(0)" onclick="update('$[related_drugs_show]')">调整结果</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">

function query(){
	$("#datatable").params($("#form").getData());
	$("#datatable").refresh();
}

function update(drug_name){
	var drug_name=drug_name.replace("[","").replace("]","").replace(" ","");
	var index = $.modal("/w/hospital_common/changeqdetail/showq.html","修改",{
		width:"80%",
	    height:"95%",
		drug_name:drug_name,
		callback : function(e){
			query();
		}
	});
	layer.full(index);
}
function yaopin(drugcode,drugname){
	var drugname=drugname.replace("[","").replace("]","").replace(" ","");
	$.modal("/w/ipc/auditresult/instruction.html",'【'+ drugname+"】说明书",{
		width:"80%",
	    height:"90%",
		his_drug_code:drugcode,
		callback : function(e){
			query();
		}
	});
}
</script>