<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="客服端测试程序" ismodal="true">
	<s:row>
		<s:form label="交易体信息" border="0" id="dataForm">
			<s:toolbar>
				<s:button label="测试" icon="glyphicon glyphicon-cog" onclick="" />
				<s:button label="新建" onclick="addNewTrading();"></s:button>
				<s:button label="取消" icon="fa fa-ban" onclick="$.closeModal(true);"></s:button>
			</s:toolbar>
			<%--
			<s:row>
				<s:textfield label="参数名称定义" name="param_name"></s:textfield>
				<s:button label="22" icon="" onclick=""></s:button>
			</s:row>
			<s:row style="border:1px solid #67809F;padding:10px;">
				<s:row id="body_row">
				</s:row>
				<s:row id="child_row">
				</s:row>
				<s:row>
					<s:button label="子交易体" onclick="addChlid();"></s:button>
				</s:row>
			</s:row>	
			 --%>
		</s:form>
	</s:row>
</s:page>
<%----------------------------------------
	        JAVASCRIPT
-----------------------------------------%>
<script type="text/javascript">

	$(function () {
		addNewTrading();
	});
	
	function addTrading(body_row_id) {
		var html = [];
		for (var i = 0; i < 5; i++) {
			var label = "测试" + i;
			var name = 'n' + i;
			html.push('<div crid="" class="cols1">' +
					'<label class="control-label">'+ label +'</label>' +
					'<div class="control-content">' +
					'<input ctype="textfield" value="' +
					'" class="form-control" type="text" islabel="false" encryption="false"' + 
					'htmlescape="false" cols="1" isherf="false" label="测试" name=' + name +
					'autocomplete="off" cinited="cinited">' +
					'</div>' +
					'</div>');
		}
		$('#' + body_row_id)[0].innerHTML=html.join('');		
	}
	
	var body_row_id = 0;
	var child_row_id = 0;
	function addNewTrading() {
		newTradingCore('body_row_id' + ++body_row_id, 'child_row_id' + ++child_row_id);
	}
	
	function newTradingCore(body_row_id, child_row_id) {
		$('#dataForm').append($('<div type="row" crid="" style="height:px;" class="row-fluid ">' +
				'<div crid="" class="cols1">' +
				'<label class="control-label">参数名称定义</label>' +
				'<div class="control-content">' +
				'<input ctype="textfield" value="' +
				'" class="form-control" type="text" islabel="false" encryption="false" htmlescape="false" cols="1" isherf="false" label="参数名称定义" name="param_name" autocomplete="off" cinited="cinited">' +
				'</div>' +
				'</div>' + 	'<button crid="" action="" class="btn btn-sm btn-sm btn-default " ctype="button" t="1" type="button" onclick="addChlid(\'' + child_row_id + '\',' + 0 + ');" cinited="cinited">子交易体' +
				'</button>' +
				'</div>' +
				'<div type="row" crid="" style="border:1px solid #67809F;padding:10px;margin-bottom:20px;" class="row-fluid ">' +
				'<div type="row" crid="" style="height:px;" class="row-fluid " id="' + body_row_id + '">' +
				'</div>' +
				'<div type="row" crid="" style="height:px;" class="row-fluid " id="' + child_row_id + '">' +
				'</div>' +
				'<div type="row" crid="" style="height:px;" class="row-fluid ">' +
				'</div>' +
				'</div>'));
		addTrading(body_row_id);
	}
	
	function addChlid(row_id, marginLeft) {
		var html = [];
		var nextid = row_id + ++child_row_id;
		marginLeft += 10;
		for (var i = 0; i < 5; i++) {
			var label = "测试" + i;
			var name = 'nn' + i;
			html.push((i == 0? '<div type="row" crid="" style="height:px;margin-left:' + marginLeft + 'px;" class="row-fluid "' +
					'>' + '<div type="row" crid="" style="height:px;" class="row-fluid ">' +
					'<div crid="" class="cols1">' +
					'<label class="control-label">参数名称定义</label>' +
					'<div class="control-content">' +
					'<input ctype="textfield" value="' +
					'" class="form-control" type="text" islabel="false" encryption="false" htmlescape="false" cols="1" isherf="false" label="参数名称定义" name="param_name" autocomplete="off" cinited="cinited">' +
					'</div>' +
					'</div>' + '<button crid="" action="" class="btn btn-sm btn-sm btn-default " ctype="button" t="1" type="button" onclick="addChlid(\'' + nextid + '\',' + marginLeft + ');" cinited="cinited">下一级' +
					'</button>' +
					'</div>': '') +
					'<div crid="" class="cols1">' +
					'<label class="control-label">'+ label +'</label>' +
					'<div class="control-content">' +
					'<input ctype="textfield" value="' +
					'" class="form-control" type="text" islabel="false" encryption="false"' + 
					'htmlescape="false" cols="1" isherf="false" label="测试" name=' + name +
					'autocomplete="off" cinited="cinited">' +
					'</div>' +
					'</div>' + (i == 4 ? '</div><div type="row" crid="" style="height:px;" class="row-fluid " id="' + nextid + '">' : ''));
		}
		$('#' + row_id)[0].innerHTML=$('#' + row_id)[0].innerHTML + html.join('');
	}
	
</script>
