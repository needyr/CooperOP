<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="审核规则维护" disloggedin="true">
	<s:row>
		<div class="note note-info">
			<p>说明： 此处定义各个审核引擎的审核规则，与系统规则匹配后，进行统一管理</p>
		</div>
	</s:row>

	<s:row>
		<s:form label="快速查找" id="query_form">
			<s:row>
				<s:textfield name="query_name" label="关键字" placeholder="请输入标准、审方编号或名称"></s:textfield>
				<s:button label="查询" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>

	<s:row>
		<s:table id="datatable" label="医保审核规则定义" autoload="false" sort="true"
				 action="hospital_common.auditset.mapchecksort.query">
			<s:toolbar>
				<s:button label="新增规则" onclick="editWindow('','',1)"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="thirdt_code" label="规则编号"></s:table.field>
				<s:table.field name="thirdt_name" label="规则名称"></s:table.field>
				<s:table.field name="check_type" label="审核引擎" datatype="script" sort="true">
					if(record.check_type==1) return "智能审查"
					else if(record.check_type==2) return "自定义审查"
				</s:table.field>
				<s:table.field name="system_sort" label="对应的系统规则" datatype="template">
					<a href="javascript:void(0);" onclick="sortAdmin('$[sort_name]');">$[sort_id] - $[sort_name]</a>
				</s:table.field>
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

	function editWindow(m_p_key,s_p_key,upOrAdd) {
		$.modal("edit.html", "编辑规则", {
			width : "500px",
			height : "60%",
			"m_p_key":m_p_key,
			"upOrAdd":upOrAdd,
			"product_code":"hospital_imic",
			callback : function(e) {
				query();
			}

		});
	}

	function query() {
		var qdata = $("#query_form").getData();
		qdata.product_code='hospital_imic';
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

	function sortAdmin(sort_name){
		$.modal("/w/hospital_common/auditset/checksort/imic_list.html", "查看系统规则：" + sort_name, {
			width : "95%",
			height : "95%",
			sort_name : sort_name,
			callback : function(e) {
				query();
			}

		});
	}
</script>