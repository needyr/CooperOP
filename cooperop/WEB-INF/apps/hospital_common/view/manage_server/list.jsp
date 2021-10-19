<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="服务管理" disloggedin="true">
	<s:row>

	</s:row>
	<s:row>
	    <s:table id="datatable" label="服务" autoload="false"  action="hospital_common.manage_server.query" limit="-1">
	        <s:toolbar>
				<select name="sel" value="" cols="1" style="line-height: 28px;font-size: 12px;height: 28px;">
					<option label="" value=""></option>
					<option label="5s" value="5000"></option>
					<option label="10s" value="10000"></option>
					<option label="60s" value="60000"></option>
				<select>
				<s:button label="查询" onclick="query()" icon="fa fa-search"></s:button>
			</s:toolbar>
			<s:table.fields>
		        <s:table.field name="name" label="服务名" ></s:table.field>
		        <s:table.field name="info" label="描述" ></s:table.field>
		        <s:table.field name="status" label="状态" datatype="script">
					var sta = record.status;
					if(sta == '1'){
						return '<span style="color: #0b9cab">运行</span>';
					}else{
						return '<span style="color: #2c3145">停止</span>';
					}
				</s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="script">
					var sta = record.status;
					if(sta == '1'){
					return '<a style="color: red" onclick="stop(\''+record.name+'\');">关闭</a> | <a style="color: #0b9cab" onclick="restart(\''+record.name+'\');">重启</a>';
					}else{
					return '<a style="color: #0b9cab" onclick="start(\''+record.name+'\');">开启</a>';
					}
				</s:table.field>
			</s:table.fields>
	    </s:table>
	</s:row>
</s:page>
<script type="text/javascript">
var interval;
$(function (){
	query();
	$('[name=sel]').change(function(){
		var time = this.value;
		if (interval){
			clearInterval(interval);
		}
		if(time && time!=''){
			interval = setInterval(function(){
				query();
			},time)
		}
	});
});
function query(){
	var qdata=$("#form").getData();
	$("#datatable").params(qdata);
	$("#datatable").refresh();
}

function start(name){
	$.confirm("是否确认,启动？",function callback(e){
		if(e){
			$.call("hospital_common.manage_server.start",{"name": name},function(s){
				query();
			});
		}
	})
}
function stop(name){
	$.confirm("是否确认,停止？",function callback(e){
		if(e){
			$.call("hospital_common.manage_server.stop",{"name": name},function(s){
				query();
			});
		}
	})
}
function restart(name){
	$.confirm("是否确认,重启？", function callback(e) {
		if(e) {
			$.call("hospital_common.manage_server.restart", {"name": name}, function (s) {
				query();
			});
		}
	})

}
</script>
