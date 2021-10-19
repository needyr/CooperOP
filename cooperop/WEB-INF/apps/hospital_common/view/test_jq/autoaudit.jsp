<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="测试智能审查">
<s:row>
	<s:form id="conditions" border="0">
		<s:row>
			<s:textarea label="数据来源sql" cols="3" name="source_sql"
			value="select PATIENT_ID,visit_id,doctor,doctor_no from test_orders where order_class='0' group by PATIENT_ID,visit_id,doctor,doctor_no "></s:textarea>
			<s:textfield label="并发数" name="bf_num" placeholder="并发数量"></s:textfield>
			<s:textfield label="每单间隔时间" name="jg_num"></s:textfield>
			<s:button label="执行" onclick="exe_autoaudit()" icon="fa fa-search"></s:button>
		</s:row>
	</s:form>
	<s:form id="dataform" border="0" label="统计信息" icon="fa fa-table">
		<s:toolbar >
				<s:button icon="" label="查询结果" onclick="queryRsult()"></s:button>
		</s:toolbar>
		<s:row>
			<s:textfield label="最大时间" name="max_time" readonly="true"></s:textfield>
			<s:textfield label="最小时间" name="min_time" readonly="true"></s:textfield>
			<s:textfield label="平均时间" name="avg_time" readonly="true"></s:textfield>
			<s:textfield label="完成率" name="complete" readonly="true"></s:textfield>
			<s:textfield label="通过率" name="pass" readonly="true"></s:textfield>
		</s:row>
	</s:form>
	
</s:row>
		<s:row>
			<s:table label="服务端列表" id="dataTable" autoload="ture" 
				action="hospital_common.test_jq.query" icon="fa fa-table" fitwidth="true" sort="true">
				<s:table.fields>
					<s:table.field label="名称" name="name" datatype="string"
						defaultsort="asc"></s:table.field>
					<s:table.field label="审查结果" name="sh_jieguo" datatype="script"
						sort="true" defaultsort="asc">
						if(record.sh_jieguo == "HL_Y"){
							return "审查通过";
						}else if(record.sh_jieguo == "HL_N"){
							return "<font color='red'>审查不通过</font>";
						}
						else if(record.sh_jieguo == "HL_F"){
							return "<font color='red'>审查超时</font>";
						}
						else if(record.sh_jieguo == "HL_T"){
							return "<font color='#c59509'>审查提示</font>";
						}
					</s:table.field>
					<s:table.field label="状态" name="state" datatype="script"
						sort="true">
						if(record.state == 1){
							return "已完成";
						}
						return "<font color='red'>错误</font>";
					</s:table.field>
					<s:table.field label="花费时间（毫秒）" name="cost_time" datatype="string"
						sort="true"></s:table.field>
					<s:table.field name="caozuo" label="操作" datatype="template" width="70">
					<a href="javascript:void(0)" onclick="queryResult2('$[test_id]')">查看</a>
					</s:table.field>
				</s:table.fields>
			</s:table>
		</s:row>
	
</s:page>
<script type="text/javascript">
	$(function(){
	});
	function query(){
		$.call("hospital_common.test_jq.queryInfo", {type:"1"}, function(info){
			$("input[name='max_time']").val(info.max_time+"ms");
			$("input[name='min_time']").val(info.min_time+"ms");
			$("input[name='avg_time']").val(info.avg_time+"ms");
			$("input[name='complete']").val(info.complete+"%");
			$("input[name='pass']").val(info.pass+"%");
		});
		$("#dataTable").params({type:"1"});
		$("#dataTable").refresh();
	}
	function exe_autoaudit() {
		$.call("hospital_common.test_jq.test_autoaudit", $("#conditions").getData(), function(){
		});
	}
	function queryRsult(){
		query();
	}
	
	function queryResult2(id){
		console.log(id)
		$.modal("/w/hospital_common/test_jq/detail.html","查看",{
			width:"900px",
			height:"90%",
			id: id,
			callback : function(e){
				
			}
		});
	}
</script>
