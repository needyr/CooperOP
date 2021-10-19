<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="医保控费系统配置管理" >
	<s:row>
		<s:form border="0" id="form" label="快速查找">
			<s:row>
				<s:textfield label="关键字" name="filter" placeholder="请输入配置名称" cols="2"></s:textfield>
				<s:button label="查询" icon="" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="医保控费系统配置管理" autoload="false" action="hospital_common.config.query" sort="true" >
			<s:toolbar>
				<s:button icon="" label="新增配置" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="code" label="配置编号" datatype="" ></s:table.field>
				<s:table.field name="name" label="配置名称" datatype="" ></s:table.field>
				<s:table.field name="value" label="值" datatype="" ></s:table.field>
				<%-- <s:table.field name="remark" label="描述" datatype="" ></s:table.field> --%>
				<s:table.field name="caozuo" label="操作" datatype="template" >
					<a href="javascript:void(0)" onclick="update('$[code]')">修改</a>
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
		qdata.system_product_code = 'hospital_imic';
		$("#datatable").params(qdata);
		$("#datatable").refresh();
	}
	
	function Add(){
		$.modal("edit.html","新增配置",{
			width:"60%",
			height:"70%",
			system_product_code:'hospital_imic',
			callback : function(e){
				query();
			}
		});
	}
	
	function update(code){
		if(code == 'time_pharmacist_work'){
			$.modal("worktimeedit.html","修改配置",{
				width:"60%",
				height:"70%",
				code:code,
				system_product_code:'hospital_imic',
				callback : function(e){
					query();
				}
			});
		}else{
			$.modal("edit.html","修改配置",{
				width:"60%",
				height:"70%",
				code:code,
				system_product_code:'hospital_imic',
				callback : function(e){
					query();
				}
			});
		}
	}
	
</script>