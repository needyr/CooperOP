<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<style>
.lj{
	background-color:#f1a9a9;
	width:45px;;
	display:inline-block;
	text-align: center;
    padding: 2px;
    border: 1px solid #e1d38d;
}
.ts{
	background-color:#ede5bd;
	width:45px;;
	display:inline-block;
	text-align: center;
	padding: 2px;
    border: 1px solid #e1d38d;
}
.ss{
	background-color:#bab6bd;
	width:45px;;
	display:inline-block;
	text-align: center;
	padding: 2px;
    border: 1px solid #e1d38d;
}
.kb{
	background-color:#dcffdc;
	width:45px;;
	display:inline-block;
	text-align: center;
	padding: 2px;
    border: 1px solid #e1d38d;
}
</style>
<s:page title="审查规则维护" disloggedin="true">
	<div class="note note-warning">
	温馨提示：操作等级中
	<div class="kb" style="height:20px"> </div>
	为审查【通过】；
	<div class="ts" style="height:20px"> </div>
	为审查【提示】，具有警示作用，不影响用药；
	<div class="lj" style="height:20px"> </div>
	为审查【拦截】，不能用药，医生可强制使用；
	<div class="ss" style="height:20px"> </div>
	为审查【驳回】，医生必须修改医嘱/处方
	</div>
	<s:row>
		<s:form label="审查规则维护" id="query_form">
			<s:row>
				<s:textfield name="query_name" label="关键字" placeholder="请输入分类名称"></s:textfield>
				<s:button label="查询" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>

	<s:row>
		<s:table id="datatable" label="分类管理" autoload="false" 
			action="hospital_common.auditset.checksort.query">
			<s:toolbar>
				<s:button label="新增" onclick="editWindow('',1)"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="sort_id" label="分类编号"></s:table.field>
				<s:table.field name="sort_name" label="分类名称"></s:table.field>
				<%-- <s:table.field name="interceptor_name" label="住院拦截等级"></s:table.field>
				<s:table.field name="info_name" label="住院提示等级"></s:table.field>
				<s:table.field name="complain_name" label="住院驳回等级"></s:table.field>
				<s:table.field name="interceptor_name_outp" label="门诊拦截等级"></s:table.field>
				<s:table.field name="info_name_outp" label="门诊提示等级"></s:table.field>
				<s:table.field name="complain_name_outp" label="门诊驳回等级"></s:table.field>
				<s:table.field name="interceptor_name_emergency" label="急诊拦截等级"></s:table.field>
				<s:table.field name="info_name_emergency" label="急诊提示等级"></s:table.field>
				<s:table.field name="complain_name_emergency" label="急诊驳回等级"></s:table.field> --%>
				<s:table.field name="zy" label="住院操作等级" datatype="script">
					var interceptor_level = record.interceptor_level
					var info_level = record.info_level
					var complain_level = record.complain_level
					var level_all = '${levelList}';
					var level = eval('('+level_all+')')
					var level_length = level.length;
					var complain = '';
					var interceptor = '';
					var info = '';
					var str = '';
					var set = new Set();
					for(var j=0;j < level_length;j++){
						var level_code=level[j].level_code;
						var level_name=level[j].level_name;
						if(complain_level <= level_code){
							var set_old = set.size;
							set.add(level[j].level_name)
							var set_new = set.size;
							if(set_old < set_new){
							complain = complain+'<span class="ss">'+level[j].level_name+'</span>';
							}
						}
					}
					for(var j=0;j < level_length;j++){
						var level_code=level[j].level_code;
						var level_name=level[j].level_name;
						if(interceptor_level <= level_code){
							var set_old = set.size;
							set.add(level[j].level_name)
							var set_new = set.size;
							if(set_old < set_new){
							interceptor = interceptor+'<span class="lj">'+level[j].level_name+'</span>';
							}
						}
					}
					for(var j=0;j < level_length;j++){
						var level_code=level[j].level_code;
						var level_name=level[j].level_name;
						if(info_level <= level_code){
							var set_old = set.size;
							set.add(level[j].level_name)
							var set_new = set.size;
							if(set_old < set_new){
							info = info+'<span class="ts">'+level[j].level_name+'</span>';
							}
						}
					}
					for(var j=0;j < level_length;j++){
						var level_code=level[j].level_code;
						var level_name=level[j].level_name;
						var set_old = set.size;
						set.add(level[j].level_name)
						var set_new = set.size;
						if(set_old < set_new){
						str = str+'<span class="kb">'+level[j].level_name+'</span>';
						}
					}
					return str+info+interceptor+complain;
				</s:table.field>
				<s:table.field name="mz" label="门诊操作等级" datatype="script">
					var interceptor_level = record.interceptor_level_outp
					var info_level = record.info_level_outp
					var complain_level = record.complain_level_outp
					var level_all = '${levelList}';
					var level = eval('('+level_all+')')
					var level_length = level.length;
					var complain = '';
					var interceptor = '';
					var info = '';
					var str = '';
					var set = new Set();
					for(var j=0;j < level_length;j++){
						var level_code=level[j].level_code;
						var level_name=level[j].level_name;
						if(complain_level <= level_code){
							var set_old = set.size;
							set.add(level[j].level_name)
							var set_new = set.size;
							if(set_old < set_new){
							complain = complain+'<span class="ss">'+level[j].level_name+'</span>';
							}
						}
					}
					for(var j=0;j < level_length;j++){
						var level_code=level[j].level_code;
						var level_name=level[j].level_name;
						if(interceptor_level <= level_code){
							var set_old = set.size;
							set.add(level[j].level_name)
							var set_new = set.size;
							if(set_old < set_new){
							interceptor = interceptor+'<span class="lj">'+level[j].level_name+'</span>';
							}
						}
					}
					for(var j=0;j < level_length;j++){
						var level_code=level[j].level_code;
						var level_name=level[j].level_name;
						if(info_level <= level_code){
							var set_old = set.size;
							set.add(level[j].level_name)
							var set_new = set.size;
							if(set_old < set_new){
							info = info+'<span class="ts">'+level[j].level_name+'</span>';
							}
						}
					}
					for(var j=0;j < level_length;j++){
						var level_code=level[j].level_code;
						var level_name=level[j].level_name;
						var set_old = set.size;
						set.add(level[j].level_name)
						var set_new = set.size;
						if(set_old < set_new){
						str = str+'<span class="kb">'+level[j].level_name+'</span>';
						}
					}
					return str+info+interceptor+complain;
				</s:table.field>
				<s:table.field name="jz" label="急诊操作等级" datatype="script">
					var interceptor_level = record.interceptor_level_emergency
					var info_level = record.info_level_emergency
					var complain_level = record.complain_level_emergency
					var level_all = '${levelList}';
					var level = eval('('+level_all+')')
					var level_length = level.length;
					var complain = '';
					var interceptor = '';
					var info = '';
					var str = '';
					var set = new Set();
					for(var j=0;j < level_length;j++){
						var level_code=level[j].level_code;
						var level_name=level[j].level_name;
						if(complain_level <= level_code){
							var set_old = set.size;
							set.add(level[j].level_name)
							var set_new = set.size;
							if(set_old < set_new){
							complain = complain+'<span class="ss">'+level[j].level_name+'</span>';
							}
						}
					}
					for(var j=0;j < level_length;j++){
						var level_code=level[j].level_code;
						var level_name=level[j].level_name;
						if(interceptor_level <= level_code){
							var set_old = set.size;
							set.add(level[j].level_name)
							var set_new = set.size;
							if(set_old < set_new){
							interceptor = interceptor+'<span class="lj">'+level[j].level_name+'</span>';
							}
						}
					}
					for(var j=0;j < level_length;j++){
						var level_code=level[j].level_code;
						var level_name=level[j].level_name;
						if(info_level <= level_code){
							var set_old = set.size;
							set.add(level[j].level_name)
							var set_new = set.size;
							if(set_old < set_new){
							info = info+'<span class="ts">'+level[j].level_name+'</span>';
							}
						}
					}
					for(var j=0;j < level_length;j++){
						var level_code=level[j].level_code;
						var level_name=level[j].level_name;
						var set_old = set.size;
						set.add(level[j].level_name)
						var set_new = set.size;
						if(set_old < set_new){
						str = str+'<span class="kb">'+level[j].level_name+'</span>';
						}
					}
					return str+info+interceptor+complain;
				</s:table.field>
				<s:table.field name="outpatient_check" label="门诊审查" datatype="script">
					if(record.outpatient_check==1) return "启用"
					else return "<font color='red'>停用</font>"
				</s:table.field>
				<s:table.field name="emergency_check" label="急诊审查" datatype="script">
					if(record.emergency_check==1) return "启用"
					else return "<font color='red'>停用</font>"
				</s:table.field>
				<s:table.field name="hospitalization_check" label="住院审查" datatype="script">
					if(record.hospitalization_check==1) return "启用"
					else return "<font color='red'>停用</font>"
				</s:table.field>
				<s:table.field name="state" label="启用" datatype="script">
					if(record.state==1) return "是"
					else return "否"
				</s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template">
					<a onclick="editWindow('$[p_key]',2)">修改</a>
					<a onclick="delWindow('$[p_key]')">删除</a>
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
	function editWindow(p_key,upOrAdd) {
		$.modal("ipc_edit.html", "编辑信息", {
			width : "680px",
			height : "85%",
			"product_code":'ipc',
			"p_key":p_key,
			"upOrAdd":upOrAdd,
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
	
	function del(p_key){
		ajax: $.call("hospital_common.auditset.checksort.delete",{"p_key":p_key},function(s){
			query();
		}) 
	}
	
	//删除警告弹窗
	function delWindow(p_key) {
		$.confirm('确定删除?删除之后无法恢复！',function(choose){
			if(choose == true){
				del(p_key);
			}
		});

	}
</script>