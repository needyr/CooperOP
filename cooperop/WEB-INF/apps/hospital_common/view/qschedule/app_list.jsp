<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="系统手动维护" disloggedin="true">
	<div class="note note-warning">application
	温馨提示：1、此处仅显示当前产品定时器任务清单；
	2、当修改了定时器执行周期或者状态后，需重启服务方可生效；
	3、请勿在定时器执行周期内或无特殊情况下手动进行“执行”操作。
	</div>
	<s:row>
		<s:form id="form" label="快速查找">
			<s:row>
				<input type="hidden" name="product_code"  value="application"/>
				<s:textfield label="关键字"  name="filter" placeholder="请输入定时器任务名称" cols="1"></s:textfield>
				<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="所属产品定时器任务列表" autoload="false"  sort="true" action="hospital_common.qschedule.query"  >
			<s:toolbar>
				<s:button icon="fa fa-plus" label="新增" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="p_key" label="编号" sort="true" defaultsort="asc"></s:table.field>
				<s:table.field name="name" label="名称"  ></s:table.field>
				<s:table.field name="product_code" label="所属产品"  ></s:table.field>
				<s:table.field name="sys_time_expression_name" label="执行周期"  ></s:table.field>
				<s:table.field name="remark" label="任务" ></s:table.field>
				<s:table.field name="state" label="状态" datatype="script" sort="true" defaultsort="asc">
					if(record.state == 1){
						return "<span style='color:green'>开启</span>";
					}else{
						return "<span style='color:gray'>停用</span>";
					}
				</s:table.field>
				<s:table.field name="last_use_time" label="上次主动触发时间" sort="true" defaultsort="asc" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="script" >
					var html = [];
					html.push('<a data-column="'+record.instance+'"  onclick="update(\''+record.p_key+'\',\''+record.state+'\',\''+record.classz+'\')">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;');
					html.push('<a data-column="'+record.instance+'" onclick="updateState(\''+record.instance+'\','+record.state+')">切换状态</a>&nbsp;&nbsp;&nbsp;&nbsp;');
					html.push('<a data-column="'+record.instance+'"  onclick="execute(\''+record.p_key+'\',\''+record.state+'\',\''+record.classz+'\')">执行</a>');
					return html.join("");
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
	$("#datatable").params($("#form").getData());
	$("#datatable").refresh();
}

function Add(){
	$.modal("edit.html","新增",{
		width:"55%",
		height:"70%",
		callback : function(e){
			query();

		}
	});
}

function update(p_key){
	$.modal("edit.html","修改",{
		width:"55%",
		height:"70%",
		p_key:p_key,
		callback : function(e){
			query();
		}
	});
}

function updateState(instance,state){
	if (state == 1){
		state = 0;
	}else{
		state = 1;
	}
	$.call("hospital_common.qschedule.updateByState",{"instance": instance, "state": state},function(rtn){
		query();
	})
	
}
function execute(p_key,state,classz){
	
	$.confirm("是否确认执行？",function callback(e){
		if(e==true){
			$.call("hospital_common.qschedule.update",{"p_key": p_key},function(rtn){
				query();
			})
			$.call("hospital_common.qschedule.execute",{"classz": classz})
		}
	})
	
}

</script>