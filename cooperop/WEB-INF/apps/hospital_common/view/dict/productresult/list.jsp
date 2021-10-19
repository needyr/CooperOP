<%@page import="java.util.Date"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="产品审查结果字典管理" >
	<s:row>
		<s:form border="0" id="form" label="快速查找">
			<s:row>
				<s:textfield label="快速查找" name="filter" placeholder="请输入审查结果或产品编码" cols="2"></s:textfield>
				<s:checkbox label="所属审查产品" name="filterProduct" action="crdc.product.query" cols="2" style="border:none">
					<s:option label="$[name]" value="$[code]" >
						    <span style="display:block;width:110px;">$[name]</span>
					</s:option>
				</s:checkbox>
				<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="产品审查结果字典管理" autoload="false" action="hospital_common.dict.productresult.query" icon="fa fa-list" sort="true" >
			<s:toolbar>
				<s:button icon="glyphicon glyphicon-plus" label="新增规则" onclick="Add()"/>
			</s:toolbar>
			<s:table.fields>
			    <s:table.field label="ID" name="p_key"/>
				<s:table.field label="状态" name="manage_state"/>
				<s:table.field label="状态名称" name="manage_state_name"/>
				<s:table.field label="所属产品" name="name"/>
				<s:table.field label="优先级" name="priority"/>
				<s:table.field label="用于智能审查" name="usefor_manage" datatype="script">
				    var usefor_manage=record.usefor_manage;
				    var html;
				    if(usefor_manage==1){html="<span>是</span>";}
				    else{html="<span>否</span>"}
				    return html;
				</s:table.field>
				<s:table.field label="能否用药" name="use_flag" datatype="script">
				    var use_flag=record.use_flag;
				    var html;
				    if(use_flag==1){html="<span>能</span>";}
				    else{html="<span>不能</span>"}
				    return html;
				</s:table.field>
				<s:table.field label="状态sql表示" name="sql_record_flag"/>
				<s:table.field label="智能审查结果" name="final_state"/>
				<s:table.field label="审查说明flag" name="flag"/>
				<s:table.field label="描述" name="remark"/>
				<s:table.field label="操作" name="caozuo" datatype="template" width="80px">
					<a href="javascript:void(0)" onclick="edit('$[p_key]')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[p_key]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		setTimeout(function(){//产品筛选复选框默认全选
			 var data = [];
			 $("[name='filterProduct'] [type='checkbox']").each(function(){data.push($(this).val());});
			 $("[name='filterProduct']").setData(data);
		},100);
		query();
	});

	function query(){
		var qdata=$("#form").getData();
		$("#datatable").params(qdata);
		$("#datatable").refresh();
	}
	
	function Add(){
		$.modal("edit.html","新增规则信息",{
			width:"600px",
			height:"520px",
			callback : function(e){query();}
		});
	}
	
	function edit(id){
		$.modal("edit.html","修改规则信息",{
			width:"600px",
			height:"560px",
			p_key:id,
			callback : function(e){query();}
		});
	}
	
	function Delete(p_key){
		$.confirm("是否确认删除？删除后无法恢复！",function callback(e){
			if(e){
				$.call("hospital_common.dict.productresult.delete",{"p_key":p_key},function(rtn){
					if(rtn==1){query();}
		   		});
			}
		})	
	}
	
</script>