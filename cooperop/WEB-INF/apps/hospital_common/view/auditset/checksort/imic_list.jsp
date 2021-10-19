<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="审查规则维护" disloggedin="true">
	<s:row>
		<div class="note note-info">
			<p> 说明： 系统审核规则，用于系统中各功能显示与管理 </p>
		</div>
	</s:row>

	<s:row>
		<s:form label="筛选条件" id="query_form">
			<s:row>
				<s:textfield name="query_name" label="关键字" placeholder="请输入分类名称"></s:textfield>
				<s:button label="查询" onclick="query()" icon="fa fa-search"></s:button>
			</s:row>
		</s:form>
	</s:row>

	<s:row>
		<s:table id="datatable" label="医保审核-系统规则管理" autoload="false" action="hospital_common.auditset.checksort.query" sort="true">
			<s:toolbar>
				<s:button label="新增" onclick="editWindow('',1)" icon="fa fa-plus"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="sort_id" label="规则编号"></s:table.field>
				<s:table.field name="sort_name" label="规则名称"></s:table.field>
				<s:table.field name="audit_explain" label="审查说明"></s:table.field>
				<s:table.field name="is_zzf" label="转自费" datatype="script">
					if(record.is_zzf == 1){
					   return "全部允许"
					}else if(record.is_zzf == 2){
					  return '<a onclick="toDeptCtrl(\''+record.sort_id+'\',\''+record.sort_name+'\')">自定义</a>'
					}else{
					  return '<font style="color: red">全部禁止</font>'
					}
					
				</s:table.field>
				<s:table.field name="state" label="状态" datatype="script">
					if(record.state==1) return "启用"
					else return '<font style="color: red">停用</font>'
				</s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="90px">
					<a onclick="editWindow('$[p_key]', 2)">修改</a>
					<a onclick="delWindow('$[p_key]','$[sort_id]','$[is_zzf]')">删除</a>
					<a onclick="manage('$[p_key]', '$[sort_name]', '$[state]', this)">监控</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>

</s:page>
<script>

	$(function() {
		if ('${param.sort_name}' != ''){
			$('[name="query_name"]').val('${param.sort_name}');
		}
		query();
	});


	function query() {
		var qdata = $("#query_form").getData();
		qdata.product_code='hospital_imic';
		$("#datatable").params(qdata);
		$("#datatable").refresh();
	}
	
	//编辑弹窗
	function editWindow(p_key,upOrAdd) {
		$.modal("edit.html", "编辑信息", {
			width : "650px",
			height : "600px",
			"product_code":'hospital_imic',
			"p_key":p_key,
			"upOrAdd":upOrAdd,
			callback : function(e) {
				query();
			}
		});
	}
	
	//科室控制转自费
	function toDeptCtrl(sort_id,sort_name){
		$.modal("zzfctrl.html", "自定义控制转自费【规则编号："+sort_id+"；规则名称："+sort_name+"】", {
			width : "800px",
			height : "400px",
			"sort_id" : sort_id,
			"sort_name" : sort_name,
			callback : function(e) {
				
			}
		});
	}
	
	
	

	
	function del(p_key,sort_id,is_zzf){
		ajax: $.call("hospital_common.auditset.checksort.delete",{
			"p_key":p_key,
			"sort_id":sort_id,
			"is_zzf":is_zzf
			},function(s){
				query();
		}) 
	}
	
		
	//删除警告弹窗
	function delWindow(p_key,sort_id,is_zzf) {
		$.confirm('确定删除?删除之后无法恢复！',function(choose){
			if(choose == true){
				del(p_key,sort_id,is_zzf);
			}
		});
	}
	
	function manage(p_key, sort_name, state ,_this){
		$(_this).css('color', '#a000a9');
		if(state != '1')
			state = '[已停用]'
		else
			state = '';
		$.modal("/w/hospital_common/auditset/rulectrl/imic_list.html", '医保审查规则【' + sort_name + '】监控'+state, {
			width : "90%",
			height : "90%",
			"product_code":'hospital_imic',
			"sys_p_key":p_key,
			callback : function(e) {
				//query();
			}

		});
	}
</script>