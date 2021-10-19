<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="医保审查规则维护" disloggedin="true">
	<s:row>
		<s:form label="筛选条件" id="query_form">
			<s:row>
				<s:textfield name="query_name" label="关键字" placeholder="请输入规则名称"></s:textfield>
				<s:button label="查询" onclick="query()" icon="fa fa-search"></s:button>
			</s:row>
		</s:form>
	</s:row>

	<s:row>
		<s:table id="datatable" label="医保审查规则" autoload="false" action="hospital_common.auditset.rulectrl.query">
			<s:toolbar>
				<s:button label="新增" onclick="add()" icon="fa fa-plus"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="id" label="编号"></s:table.field>
				<s:table.field name="audit_sur" label="审查来源" datatype="script">
					var sur = record.audit_sur;
					if(sur == 1){
						sur = '住院';
					}else if(sur == 2){
						sur = '门诊';
					}else if(sur == 3){
						sur = '急诊';
					}else{
						sur = '<font style="color: red">错误</font>';
					}
					return sur;
				</s:table.field>
				<s:table.field name="prompt_level" label="提示" datatype="script">
					var lvs = eval('(${lvs})');
					var _var = 'lvs.lv' + record.prompt_level + '.level_name';
					return eval(_var);
				</s:table.field>
				<s:table.field name="intercept_level" label="拦截" datatype="script">
					var lvs = eval('(${lvs})');
					var _var = 'lvs.lv' + record.intercept_level + '.level_name';
					return eval(_var);
				</s:table.field>
				<s:table.field name="rejected_level" label="驳回" datatype="script">
					var lvs = eval('(${lvs})');
					var _var = 'lvs.lv' + record.rejected_level + '.level_name';
					return eval(_var);
				</s:table.field>
				<s:table.field name="state" label="状态" datatype="script">
					var state = record.state;
					if(state == '1'){
						return '启用';
					}else{
						return '停用';
					}
				</s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template">
					<a onclick="update('$[id]')">修改</a>
					<a onclick="del('$[id]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>

</s:page>
<script type="text/javascript">
	$(function() {
		query();
	});

	function add() {
		$.modal("edit.html", "编辑信息", {
			width : "650px",
			height : "350px",
			'product_code': 'hospital_imic',
			'sys_p_key': '${param.sys_p_key}',
			callback : function(e) {
				query();
			}

		});
	}
	
	function update(id) {
		$.modal("edit.html", "编辑信息", {
			width : "650px",
			height : "350px",
			'product_code': 'hospital_imic',
			'id': id,
			'sys_p_key': '${param.sys_p_key}',
			callback : function(e) {
				if(e){
					query();
				}
			}

		});
	}

	function query() {
		var qdata = $("#query_form").getData();
		qdata.product_code='hospital_imic';
		qdata.sys_p_key='${param.sys_p_key}';
		$("#datatable").params(qdata);
		$("#datatable").refresh();

	}
	
	function del(id){
		$.confirm('确定删除?删除之后无法恢复！',function(choose){
			if(choose == true){
				$.call("hospital_common.auditset.rulectrl.delete",{"id": id},function(rtn){
						if(rtn == -1){
							$.message('操作失败！');
						}else{
							query();
						}
				}) 
			}
		});
	}
</script>