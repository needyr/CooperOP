<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="病组评估金额字典">
	<s:row>
		<s:form id="dataform">
			<s:row>
				<s:autocomplete name="interface_type" label="医保接口类型" placeholder="请输入医保接口类型名称或编码"
					action="hospital_common.dictsys.dgpgje.queryInterfaceType"
					limit="5">
					<s:option value="$[interface_type]" label="$[interface_type_name]">$[interface_type] $[interface_type_name]</s:option>
				</s:autocomplete>
				
				<s:autocomplete name="dg_grade" label="人员类别"  placeholder="请输入人员类别名称或编码"
					action="hospital_common.dictsys.dggrade.query" limit="5">
					<s:option value="$[dg_grade_code]" label="$[dg_grade_name]">$[dg_grade_code] $[dg_grade_name]</s:option>
				</s:autocomplete>

			</s:row>
			<s:row>
				<s:autocomplete name="dg_icd_remark" label="病组分组"
					placeholder="请输入病组分组名称或编码或详情"
					action="hospital_common.dictsys.dg.query" limit="5">
					<s:option value="$[dg_icd],$[dg_remark]"
						label="$[dg_name],$[dg_remark]"> 
						<span style="color: green">$[dg_icd]</span>
						<span style="color: red">$[dg_name]</span>
						   $[dg_remark]</s:option>
				</s:autocomplete>
				<span style="position: relative; left: 40px;"> <s:button
						label="查询" icon="fa fa-search" onclick="query()"
						style="background: #368bdb;text-align: center;color: #FFF;"></s:button>
				</span>
			</s:row>
		</s:form>

	</s:row>
	<s:row>
		<s:table action="hospital_common.dictsys.dgpgje.query"
			autoload="false" label="病组评估金额字典" id="datatable" sort="true">
			<s:toolbar>
				<s:button label="新增" onclick="editWindow('','新增',1)"
					icon="fa fa-plus"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="interface_type_name" label="医保接口类型" sort="true"></s:table.field>
				<s:table.field name="dg_grade_name" label="人员类别" sort="true"></s:table.field>
				<s:table.field name="dg_icd" label="诊断编码" sort="true"></s:table.field>
				<s:table.field name="dg_name" label="病组名称" sort="true"></s:table.field>		
				<s:table.field name="dg_remark" label="分组详情" sort="true"></s:table.field>
				<s:table.field name="dg_pgje" label="系统病组评估金额" sort="true"></s:table.field>
				<s:table.field name="dg_pgje_hospital" label="医院自定义评估金额" sort="true"></s:table.field>
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
	function editWindow(id, pageType, upOrAdd) {
		$.modal("edit.html", pageType, {
			width : "600px",
			height : "500px",
			"id" : id,
			"upOrAdd" : upOrAdd,
			callback : function(e) {
				if (e) {
					query();
				}
			}
		});
	}

	//删除警告弹窗
	function delWindow(id) {
		$.confirm('确定删除?删除之后无法恢复！', function(choose) {
			if (choose == true) {
				del(id);
			}
		});
	}
	function del(id) {
		$.call("hospital_common.dictsys.dgpgje.delete", {
			"id" : id
		}, function(s) {
			if (s > 0) {
				query();
			} else {
				$.message("删除失败");
			}
		})
	}

	
</script>