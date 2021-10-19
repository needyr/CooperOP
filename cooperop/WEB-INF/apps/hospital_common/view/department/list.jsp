<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="科室管理" >
	<s:row>
		<s:form border="0" id="form" label="快速查找">
			<s:row>
				<s:textfield label="快速查找" name="filter" placeholder="请输入科室编码或名称" cols="2"></s:textfield>
				<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="科室资料管理" autoload="false" action="hospital_common.department.query" sort="true" >
			<s:toolbar>
				<s:button icon="" label="新增科室" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="code" label="科室编码" datatype="" ></s:table.field>
				<s:table.field name="name" label="科室名称" datatype="" ></s:table.field>
				<s:table.field name="simplename" label="科室简称" datatype="" ></s:table.field>
				<s:table.field name="outp_or_inp" label="科室类型" datatype="script" >
					switch (+record.outp_or_inp) {
						case 1:
							return '门诊科室';
						case 0:
							return '住院科室';
					}
				</s:table.field>
				<s:table.field name="dept_attr" label="科室属性" datatype="" ></s:table.field>
				<s:table.field name="description" label="描述" datatype="" ></s:table.field>
				<s:table.field name="create_time" label="创建时间" datatype="" ></s:table.field>
				<s:table.field name="update_time" label="修改时间" datatype="" ></s:table.field>
				<s:table.field name="state" label="状态" datatype="script" >
					switch (+record.state) {
						case 1:
							return '<span class="font-green">启用</span>';
						case 0:
							return '<span class="font-gray">停用</span>';
					}
				</s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" >
					<a href="javascript:void(0)" onclick="update('$[id]')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]')">删除</a>
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
		/* qdata.ts='${param.ts}';
		qdata.vs='${param.vs}';
		qdata.uid='${param.uid}'; */
		$("#datatable").params(qdata);
		$("#datatable").refresh();
		//$.message("数据加载完成！");
	}
	
	function Add(){
		$.modal("edit.html","新增科室",{
			width:"60%",
			height:"80%",
			callback : function(e){
				query();
			}
		});
	}
	
	function update(id){
		$.modal("edit.html","修改科室",{
			width:"60%",
			height:"80%",
			id:id,
			callback : function(e){
				query();
			}
		});
	}
	
	function Delete(id){
		$.call("hospital_common.department.delete",{"id":id},function (rtn){
			query();
		})	
	}
	
</script>