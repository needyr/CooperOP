<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="数据转换- 交易配置" >
	<s:row>
		<s:form id="form" label="快速查找">
			<s:row>
				<s:textfield label="关键字" name="filter" placeholder="请输入交易编码，接口编号，交易类型（DS，JS），交互方式（GET，PUSH）或描述" cols="2"></s:textfield>
				<s:button id="query-btn" label="查询" icon="fa fa-search" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="交易接口（采集方调用）列表" autoload="false" action="hospital_common.trade.config.query"  >
			<s:toolbar>
				<s:button icon="fa fa-plus" label="新增" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="id" label="ID"></s:table.field>
				<s:table.field name="trade_code" label="交易编码"></s:table.field>
				<s:table.field name="data_interface_code" label="接口编码"></s:table.field>
				<%-- <s:table.field name="trade_type" label="交易类型" datatype="script" width="60">
					
				</s:table.field> --%>
				<%-- <s:table.field name="interaction_type" label="交互方式" datatype="script" width="80">
					var itype = record.interaction_type;
					if(itype == 'GET'){
						return '[数据采集]调用';
					}else if(itype == 'PUSH'){
						return '推给[数据采集]';
					}else{
						return '';
					}
				</s:table.field> --%>
				<s:table.field name="convert_deal" label="处理方法" datatype="template">
					<a href="javascript:void(0);" onclick="procInfo('$[convert_deal]')">$[convert_deal]</a>
				</s:table.field>
				<s:table.field name="convert_way" label="数据转换方式" datatype="script">
					var convert_way = record.convert_way;
					if(convert_way == 'PROC'){
						return 'SQL转换';
					}else if(itype == 'PUSH'){
						return 'JAVA转换';
					}else{
						return '';
					}
				</s:table.field>
				<s:table.field name="call_type" label="调用类型" datatype="script">
					var call_type = record.call_type;
					if(call_type == 'INIT'){
						return '初始化调用';
					}else if(call_type == 'DS'){
						return '定时调用';
					}else{
						return '';
					}
				</s:table.field>
				<s:table.field name="interface_url" label="调用链接"></s:table.field>
				<s:table.field name="description" label="描述/备注"  ></s:table.field>
				<s:table.field name="state" label="状态" datatype="script">
					var state = record.state;
					if(state == 0){
						return "停用" ;
					}else if(state == 1){
						return '<font style="color: green;">启用</font>';
					}
				</s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="80">
					<a href="javascript:void(0)" onclick="update('$[trade_code]')">修改</a>
					<a href="javascript:void(0)" onclick="del('$[trade_code]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
var trade_code = '${param.trade_code}';
$(function(){
	if(trade_code != ''){
		$('[name="filter"]').val(trade_code);
	}
	query();
});

function query(){
	var qdata=$("#form").getData();
	$("#datatable").params(qdata);
	$("#datatable").refresh();
}

function Add(){
	$.modal("edit.html","新增",{
		width:"70%",
		height:"60%",
		callback : function(e){
			if(e){
				query();
			}
		}
	});
}

function update(trade_code){
	$.modal("edit.html","修改",{
		width:"70%",
		height:"60%",
		trade_code: trade_code,
		callback : function(e){
			if(e){
				query();
			}
		}
	});
}

function del(trade_code){
	$.confirm('确定删除?删除之后无法恢复！',function(choose){
		if(choose == true){
			$.call("hospital_common.trade.config.delete",{trade_code: trade_code},function(rtn){
				if(rtn > 0){
					query();
				}else{
					$.message('删除失败！');
				}
			})
		}
	});
}

function procInfo(proc_code){
	$.modal("/w/hospital_common/trade/proc/list.html","查看处理方法信息",{
		width:"90%",
		height:"90%",
		proc_code: proc_code,
		callback : function(e){
			if(e){
				query();
			}
		}
	});
}
</script>