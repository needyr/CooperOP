<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="医保自定义审查维护" >
	<s:row>
		<s:form border="0" id="form" label="快速查找" >
			<s:row>
				<s:textfield label="关键字" name="filter" placeholder="项目名称、项目类别" cols="1"></s:textfield>
				<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
				<s:row>
					<s:checkbox label="已设置" name="hasset" cols="3" style="border:none">
					<s:option label="医保限专规则" value="yb_shengfangzl_xianz"></s:option>
					<s:option label="医保项目规则" value="yb_shengfangzl_item"></s:option>
					<s:option label="医保项目驳回规则" value="yb_shengfangzl_reject"></s:option>
				</s:checkbox>
				</s:row>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="医保自定义审查规则" autoload="true" action="hospital_common.imiccustomre.queryimicsc" sort="true" >
			<s:toolbar>
				<s:button icon="" label="新增" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="item_code" label="项目编码"></s:table.field>
				<s:table.field name="class_name" label="项目类型"></s:table.field>
			    <s:table.field name="item_name" label="项目名称"></s:table.field>
				<s:table.field name="item_spec" label="规格"></s:table.field>
				<s:table.field name="units" label="项目单位"></s:table.field>
				<s:table.field name="price" label="价格/元"></s:table.field>
				<s:table.field name="start_date" label="启动日期" format="yyyy-MM-dd HH:mm:ss" datatype="datetime"></s:table.field>
				<s:table.field name="stop_date" label="停用日期" format="yyyy-MM-dd HH:mm:ss" datatype="datetime"></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" >
					<a href="javascript:void(0)" onclick="update('$[p_key]','$[item_code]')">修改</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		$("[name='hasset']").setData(['yb_shengfangzl_xianz','yb_shengfangzl_item', 'yb_shengfangzl_reject']);
		query();
	});

	function query(){
		var qdata=$("#form").getData();
		$("#datatable").params(qdata);
		$("#datatable").refresh();
	}
	
	function Add(){
		$.modal("edit.html","新增",{
			width:"90%",
			height:"90%",
			callback : function(e){
				query();
			}
		});
	}
	
	function update(p_key,item_code){
		$.modal("edit.html","修改",{
			width:"90%",
			height:"90%",
			item_code: item_code,
			callback: function(e){
				query();
			}
		});
	}
</script>