<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="字典">
	<s:row>
		<s:form id="conForm" label="条件区域" color="green">
			<s:toolbar>
				<s:button label="查询" onclick="query()" icon="fa fa-search"></s:button>
			</s:toolbar>
			<s:row>
				<s:autocomplete id="databasename" label="数据库名" name="query_databasename" action="crdc.ddictionary.queryDatabase" 
					onchange="setdatabase();">
					<s:option label="$[databasename]" value="$[databasename]">$[databasename]</s:option>
				</s:autocomplete>
				<s:autocomplete id="tablename" label="表名" name="query_tablename" action="crdc.ddictionary.queryTable"
					onchange="query();">
					<s:option label="$[table_name]" value="$[table_name]">$[table_name]</s:option>
				</s:autocomplete>
				<s:textfield label="表注释" name="query_tabletype" onchange="query();" placeholder="输入关键字检索"></s:textfield>
				<s:autocomplete id="typeexplain" label="数据类型" name="query_typeexplain" action="crdc.ddictionary.queryTypeExplain"
					onchange="query();">
					<s:option label="$[type_explain]" value="$[type_explain]">$[type_explain]</s:option>
				</s:autocomplete>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table label="数据字典" autoload="false" id="overid" action="crdc.ddictionary.query" sort="true" fitwidth="true" >
			<s:toolbar>
				<c:if test="${user.id eq 'CRY0000root' }">
					<s:button icon="fa fa-exchange" label="在线同步" size="btn-sm btn-default" onclick="synchro()"></s:button>
				</c:if>
				<s:button icon="fa fa-eraser" label="清除所有数据(按条件)" size="btn-sm btn-default" onclick="delAll()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="databasename" datatype="string" label="数据库" sort="true" ></s:table.field>
				<s:table.field name="table_name" datatype="script" label="表名" sort="true" >
					var html=[];
					html.push('<a color="blue" onclick="showcolumns(\''+record.databasename+'\',\''+record.table_name+'\')">'+record.table_name+'</a>');
					return html.join("");
				</s:table.field>
				<s:table.field name="table_type" datatype="string" label="注释" sort="true" ></s:table.field>
				<s:table.field name="type_explain" datatype="string" label="类型" sort="true" ></s:table.field>
				<s:table.field name="rows" datatype="script" label="记录数(按数据库查询有效)" sort="true">
					var html=[];
					if(record.rows){
						if(record.rows>0){
							html.push('<font color="red">');
						}else{
							html.push('<font>');
						}
						html.push(record.rows+'</font>');
					}
					return html.join("");
				</s:table.field>
				<s:table.field name="remark" datatype="string" label="备注" sort="true" ></s:table.field>
				<s:table.field name="oper" datatype="script" label="操作" align="left" width="120">
					var html = [];
					if(record.type_explain!="系统数据" && record.is_del==1){
						html.push(' <a style="margin: 0px 5px;" href="javascript:void(0)" ');
						html.push(' onclick="del(\''+record.databasename+'\',\''+record.table_name+'\',\''+record.table_type+'\')">数据清除</a> ');
					}
					return html.join('');
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(document).ready(function() {
		console.log('${user.id}');
		query();
	});
	
	function query(){
		$("#overid").params($("#conForm").getData());
		$("#overid").refresh();
	}
	
	function synchro(){
		$.call("crdc.ddictionary.synchroOnline",{},function(rtn){
			if(rtn){
				$.message("同步成功！");
				$("#overid").refresh();
			}else{
				$.message("同步失败，请检查网络！");
			}
		});
	}
	
	function showcolumns(databasename,tablename){
		$.modal("tablecolumnsdictionary.html","["+databasename+".dbo."+tablename+"] 字段说明",{
			width : '80%',
			height : '90%',
			databasename:databasename,
			tablename:tablename,
			callback : function(rtn){
				
			}
		});
	}
	
	function setdatabase(){
		var d=$("#databasename").getData();
		$("#tablename").params(d);
		$("#tabletype").params(d);
		$("#typeexplain").params(d);
		query();
	}
	
	function del(databasename,tablename,tabletype) {
		var tablename=databasename+'.dbo.'+tablename;
		$.confirm("是否确认清除 ["+tablename+"]"+tabletype+" 中的所有数据？清除后将无法恢复！", function(c) {
			if (c) {
				$.call("crdc.ddictionary.deleteTableRows", {
					tablename : tablename,
				}, function(rtn) {
					if(rtn){
						$.message("["+tablename+"]"+tabletype+" 中的所有数据已清除完毕！");
						$("#overid").refresh();
					}
				});
			}
		});
	}
	
	function delAll(){
		var d=$("#conForm").getData();
		var databasename=d.query_databasename;
		if(!databasename){
			$.message("请在条件中指定一个数据库！");
		}else{
			var message="是否确认清除 ["+databasename+"数据库] 中所有表的数据？清除后无法恢复！";
			var tablename=d.query_tablename;
			var typeexplain=d.query_typeexplain;
			if(tablename){
				message="是否确认清除 ["+databasename+".dbo."+tablename+"] 中的所有数据？清除后将无法恢复！"
			}else if(typeexplain){
				message="是否确认清除 ["+databasename+"数据库] 中所有"+typeexplain+"表的数据？清除后无法恢复！";
			}
			$.confirm(message, function(c) {
				if (c) {
					$.call("crdc.ddictionary.deleteTablesRows", d, function(rtn) {
						if(rtn){
							$.message("数据已清除完毕！");
							$("#overid").refresh();
						}
					});
				}
			});
		}
	}
</script>