<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="病组分组资料">
	<s:row>
		<s:form id="dataform">
			<s:row>
				<s:textfield placeholder="请输入诊断代码或病组名称" name="name"></s:textfield>
				<s:button label="查询" icon="fa fa-search" onclick="query()" style="background: #368bdb;text-align: center;color: #FFF;"></s:button>
			</s:row>
		</s:form>

	</s:row>
	<s:row>
		<s:table action="hospital_common.dictsys.dg.query" autoload="false"
			label="病组分组资料"  id="datatable" sort="true">
			<s:toolbar>
				<s:button label="新增" onclick="editWindow('','新增',1)" icon="fa fa-plus"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="dg_icd" label="诊断编码" sort="true"></s:table.field>
				<s:table.field name="dg_name" label="病组名称" sort="true"></s:table.field>
				<s:table.field name="dg_remark" label="分组详情" sort="true"></s:table.field>
				<s:table.field name="dg_tablepar" label="病组批次号" sort="true"></s:table.field>
				<s:table.field name="dg_zl_id" label="项目编码" sort="true"></s:table.field>
				<s:table.field name="dg_zl_name" label="项目名称" sort="true"></s:table.field>
				<s:table.field name="dg_type_name" label="分组类型" sort="true" ></s:table.field>
				<s:table.field name="beizhu" label="备注" sort="true"></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template">
					<a onclick="editWindow('$[id]','修改',2)">修改</a>
					<a onclick="delWindow('$[id]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	//初始化--查询
	$(function() {
		query();
	})

	function query() {
		var data = $('#dataform').getData();
		$("#datatable").params(data);
		$("#datatable").refresh();
	}

	//编辑弹窗
	function editWindow(id,pageType,upOrAdd) {
		$.modal("edit.html", pageType, {
			width : "700px",
			height : "530px",
			"id":id,
			"upOrAdd":upOrAdd,
			callback : function(e) {
              if(e){
            	query(); 
              }			
			}
		});
	}
	

	
	//删除警告弹窗
	function delWindow(id) {
		$.confirm('确定删除?删除之后无法恢复！',function(choose){
			if(choose == true){
				del(id);
			}
		});
	}
	function del(id){
		 $.call("hospital_common.dictsys.dg.delete",{"id":id},function(s){
			if(s>0){
				query();	
			}else{
				$.message("删除失败");
			}			
		}) 
	}
</script>