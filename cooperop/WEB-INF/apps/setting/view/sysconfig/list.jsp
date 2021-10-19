<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="字段列表">
	<s:row>
		<s:form id="conForm">
			<s:row>
				<s:textfield label="关键字查询" name="filter" tips="输入字段西文、中文名查询"></s:textfield>
				<s:button label="查询" onclick="query()" color="blue"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table label="配置列表" autoload="true" id="sysconfig"
			action="setting.sysconfig.query" sort="true" fitwidth="true">
			<s:toolbar>
				<s:button icon="icon-reload" label="重新加载"
					onclick="reloadconfig()"></s:button>
				<s:button icon="fa fa-file-o" label="新增配置"
					onclick="add()"></s:button>
				<s:button icon="fa fa-qrcode" label="设置app二维码" 
					onclick="addqrcode()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="system_prodcut_code" datatype="string" label="产品"
					sort="true" ></s:table.field>
				<s:table.field name="name" datatype="string" label="配置名称"
					sort="true" defaultsort="asc"></s:table.field>
				<s:table.field name="code" datatype="string" label="配置编码"
					sort="true"></s:table.field>
				<s:table.field name="oper" datatype="template" label="操作" align="left" width="200">
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="modify('$[code]')">修改</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	function query(){
		$("#sysconfig").params($("#conForm").getData());
		$("#sysconfig").refresh();
	}
	function add(){
		$.modal("add.html", "新增", {
			width : '60%',
			height : '60%',
			callback : function(rtn) {
				$("#sysconfig").refresh();
		    }
		});
	}
	function addqrcode(){
		$.modal("qrcode.html", "app二维码设置", {
			width : '60%',
			height : '60%',
			callback : function(rtn) {
				$("#sysconfig").refresh();
		    }
		});
	}
	function modify(fdname){
		$.modal("add.html", "修改", {
			width : '80%',
			height : '60%',
			code : code,
			callback : function(rtn) {
				$("#sysconfig").refresh();
		    }
		});
	}
	function reloadfield(){
		$.call("setting.sysconfig.reloadconfig", {}, function(rtn) {
			if(rtn){
				$.message("加载成功！");			
			}
		});
	}
</script>