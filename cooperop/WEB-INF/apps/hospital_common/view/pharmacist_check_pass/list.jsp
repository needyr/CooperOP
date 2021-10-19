<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="不合理用药调整维护" >
	<s:row>
		<s:form id="form" label="快速查找">
			<s:row>
				<s:datefield label="审查调整时间从" name="s_time" format="yyyy-MM-dd HH:mm:ss" autocomplete="off"></s:datefield>
				<s:datefield label="至" name="e_time" format="yyyy-MM-dd HH:mm:ss" autocomplete="off"></s:datefield>
				<s:textfield label="关键字" name="drug_name" placeholder="请输入药品名称" cols="1"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" height="400" label="标准分类与审查结果分类匹配维护" autoload="true" action="hospital_common.pharmacist_check_pass.query" sort="true" >
			<s:toolbar>
				<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="caozuo" label="操作" datatype="template" width="70">
					<a href="javascript:void(0)" onclick="update('$[id]')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]')">删除</a>
				</s:table.field>
				<s:table.field name="sort_name" label="类别名称" ></s:table.field>
				<s:table.field name="level_name" label="严重级别"></s:table.field>
				<s:table.field name="change_level_name" label="调整后严重级别"></s:table.field>
				<s:table.field name="related_drugs_show" label="药品信息" width="200" datatype="script">
				var html = [], code = [] ,drug = [];
	            	if(record.drug_codes){
		            	code = record.drug_codes.replace(/\[/g,'').replace(/\]/g,'').replace(/\s/g,"").split(',');
	            	}
	            	if(record.related_drugs_show){
		            	drug = record.related_drugs_show.replace(/\s/g,"").split('][');
	            	}
	            	if(drug){
	            		var _code = '';
		            	for(var i = 0; i< drug.length; i++){
			            	if(code[i]){
			            		_code = code[i].replace('[', '').replace(']', '');
			            		html.push('<a onclick="drugSms(\''+_code+'\');" data-id = "'+drug+'">'+drug[i].replace(/\[/g, '').replace(/\]/g, '')+ '</a><br>');
			            	}else{
			            		html.push(drug[i].replace(/\[/g, '').replace(/\]/g, '')+'<br>');
			            	}
		            		
		            	}
		            	return html.join('');
	            	}else{
	            		return '';
	            	}
				</s:table.field>
				<s:table.field name="description" label="问题描述" width="500"></s:table.field>
				<s:table.field name="reference" label="参考"  width="100"></s:table.field>
				<s:table.field name="shenc_pass_time" label="审查调整时间" width="150" sort="true" defaultsort="desc"></s:table.field>
				<s:table.field name="shenc_pass_ren" label="审查调整人"></s:table.field>
				<s:table.field name="shenc_pass_gnmch" label="审查调整功能名称" ></s:table.field>
				<s:table.field name="shenc_pass_pharmacist_advice" label="药师意见" width="500"></s:table.field>
				<s:table.field name="id" label="主键"></s:table.field>
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
	
	function update(pharmacist_check_pass_id){
		$.modal("edit.html","修改",{
			width:"500px",
			height:"65%",
			id: pharmacist_check_pass_id,
			callback : function(e){
				query();
			}
		});
	}
	
	function Delete(id){
		$.confirm("确认删除？\t\n删除后无法恢复！",function callback(e){
			if(e==true){
				$.call("hospital_common.pharmacist_check_pass.delete",{"id":id},function(s){
					query();
				});
			}
		});
	}
	
	function drugSms(drug_code){
		 if(drug_code.indexOf('[') > 0){
			 return ;
		 }
		 $.modal("/w/ipc/auditresult/instruction.html?his_drug_code="+drug_code,"查看药品说明书",{
	        width:"70%",
	        height:"90%",
	        callback : function(e){
	        }
		}); 
		event.stopPropagation();
	}
</script>