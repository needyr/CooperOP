<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="数据转换- 交易记录" >
	<s:row>
		<s:form id="form" label="快速查找">
			<s:row>
				<s:textfield label="关键字" name="filter" placeholder="请输入交易编码" cols="2"></s:textfield>
				<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="交易记录列表" autoload="false" action="hospital_common.trade.record.query" sort="true">
			<s:table.fields>
				<s:table.field name="id" label="ID"></s:table.field>
				<s:table.field name="trade_code" label="交易编码" sort="true" datatype="template">
					<a href="javascript:void(0);" onclick="tradeInfo('$[trade_code]')">$[trade_code]</a>
					|
					<a href="javascript:void(0);" onclick="seeOne('$[trade_code]')" title="仅看它/看全部"><i class="fa fa-search"></i></a>
				</s:table.field>
				<s:table.field name="create_time" label="开始时间" sort="true" width="120" datatype="script">
					var time = record.create_time;
					if(time){
						return '<font title="'+time+'">'+time.substring(5, time.length)+'</font>';
					}else{
						return '';
					}
				</s:table.field>
				<s:table.field name="end_time" label="结束时间" sort="true" width="110" datatype="script">
					var time = record.end_time;
					if(time){
						return '<font title="'+time+'">'+time.substring(5, time.length)+'</font>';
					}else{
						return '';
					}
				</s:table.field>
				<s:table.field name="cost_time" label="总耗时/s" sort="true" datatype="script" width="60">
					if(record.cost_time){
						return parseFloat(record.cost_time/1000);
					}else{
						return '';
					}
				</s:table.field>
				<s:table.field name="convert_cost_time" label="转换/s" sort="true" datatype="script" width="60">
					if(record.convert_cost_time){
						return parseFloat(record.convert_cost_time/1000);
					}else{
						return '';
					}
				</s:table.field>
				<s:table.field name="save_cost_time" label="存储/s" sort="true" datatype="script" width="60">
				
					if(record.save_cost_time){
						return parseFloat(record.save_cost_time/1000);
					}else{
						return '';
					}
				</s:table.field>
				<s:table.field name="rows_count" label="数据量" sort="true" width="60"></s:table.field>
				<s:table.field name="message" label="相关消息" sort="true" datatype="script">
					var msg = record.message;
					if(msg){
						return '<font title="'+msg+'">'+msg.substring(0, 60)+'...</font>';
					}else{
						return '';
					}
				</s:table.field>
				<s:table.field name="state" label="状态" datatype="script" sort="true" width="80">
					var state = record.state;
					if(state == 0){
						return '<font style="color: orange">正在进行</font>';
					}else if(state == 1){
						return "交易成功";
					}else if(state == -9){
						return '<font style="color: red">[转换]异常</font>';
					}else if(state == -1){
						return '<font style="color: red">[采集]异常</font>';
					}else{
						return "";
					}
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
	var qdata=$("#form").getData();
	$("#datatable").params(qdata);
	$("#datatable").refresh();
}

function tradeInfo(trade_code){
	$.modal("/w/hospital_common/trade/config/list.html","查看交易配置信息",{
		width:"90%",
		height:"90%",
		trade_code: trade_code,
		callback : function(e){
			if(e){
				query();
			}
		}
	});
}

function seeOne(trade_code){
	var oldf = $('[name="filter"]').val();
	if(oldf == trade_code){
		$('[name="filter"]').val('');
	}else if(oldf != trade_code){
		$('[name="filter"]').val(trade_code);
	}else{
		return;
	}
	query();
}

</script>