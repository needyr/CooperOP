<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="系统规则与第三方规则对应维护" disloggedin="true">

	<s:row>
		<s:form label="快速查找" id="query_form">
			<s:row>
				<s:textfield name="query_name" label="关键字" placeholder="请输入标准、审方编号或名称"></s:textfield>
				<s:button label="查询" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>

	<s:row>
		<s:table id="datatable" label="标准分类与审查结果分类匹配维护" autoload="false" 
			action="hospital_common.auditset.mapchecksort.query">
			<s:toolbar>
				<s:button label="新增匹配" onclick="editWindow('','',1)"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="sort_id" label="标准类别编号"></s:table.field>
				<s:table.field name="sort_name" label="标准类别名称"></s:table.field>
				<s:table.field name="check_type" label="审查分类" datatype="script">
					if(record.check_type==1) return "智能审查"
					else if(record.check_type==2) return "自定义审查"
				</s:table.field>
				<s:table.field name="thirdt_code" label="审方类别编号" datatype=""></s:table.field>
				<s:table.field name="thirdt_name" label="审方类别名称" datatype=""></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template">
					<a onclick="editWindow('$[m_p_key]','$[s_p_key]',2)">修改</a>
					<a onclick="delWindow('$[m_p_key]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>

</s:page>
<script>

	$(function() {
		query();
	});

	//编辑弹窗
	function editWindow(m_p_key,s_p_key,upOrAdd) {
		$.modal("edit.html", "新增参数", {
			width : "500px",
			height : "60%",
			"m_p_key":m_p_key,
			"upOrAdd":upOrAdd,
			"product_code":"ipc",
			callback : function(e) {
				query();
			}

		});
	}

	function query() {
		var qdata = $("#query_form").getData();
		qdata.product_code='ipc';
		$("#datatable").params(qdata);
		$("#datatable").refresh();

	}
	
	function del(m_p_key){
		ajax: $.call("hospital_common.auditset.mapchecksort.delete",{"m_p_key":m_p_key},function(s){
			query();
		}) 
	}
	
	//删除警告弹窗
	function delWindow(m_p_key) {
		$.confirm('确定删除?删除之后无法恢复！',function(choose){
			if(choose == true){
				del(m_p_key);
			}
		});
	}
</script>