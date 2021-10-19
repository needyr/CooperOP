<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="机构维护">
	<s:row>
		<s:form id="conForm">
			<s:row>
				<s:textfield cols="2" label="关键字查询" name="filter" placeholder="输入机构编号、名字或拼音码"></s:textfield>
				<s:button label="查询" onclick="query()" color="blue"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table label="机构列表" autoload="false" id="organizationtable"
			action="setting.organization.query" sort="true" fitwidth="true">
			<s:toolbar>
				<s:button icon="fa fa-file-o" label="新增机构" size="btn-sm btn-default"
					onclick="edit()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="jigid" datatype="string" label="机构编码"
					sort="true" defaultsort="asc"></s:table.field>
				<s:table.field name="jigname" datatype="string" label="机构名称"
					sort="true"></s:table.field>
				<s:table.field name="jigtel" datatype="string" label="机构电话"
					sort="true"></s:table.field>
				<s:table.field name="jigaddress" datatype="string" label="机构地址"
					sort="true"></s:table.field>
				<s:table.field name="state" datatype="script" label="状态" sort="true">
					if(+record.state == 0){
						return '<a href="javascript:void(0)" onclick="stateManage(' + record.id + ', this);"><span style="color:red;">停用</span></a>'
					}else if(+record.state == 1){
						return '<a href="javascript:void(0)" onclick="stateManage(' + record.id + ', this);"><span style="color:green;">启用</span></a>'
					} else  {
						return '<span>未识别</span>'
					}
				</s:table.field>
				<s:table.field name="oper" datatype="template" label="操作" align="left" width="120">
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="edit('$[id]', '$[jigname]')">修改</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">

	$(document).ready(function() {
		$("#conForm").dblclick(function(){
			query();
		});
		$("#conForm").keypress(function(e) {
			if ((e ? e : event).keyCode == 13) {
				query();
			}
		});
		query();
	});
	
	function stateManage(id, t) {
		var state = -1;
		if ($(t).find("span").text() == "停用") {
			state = 1;
		} else if ($(t).find("span").text() == "启用") {
			state = 0;
		} else {
			$.message("匹配错误。");
			return;
		}
		$.call("setting.organization.update", {id: id, state: state}, function(rtn) {
			if (rtn) {
				query();
			}
		}, null, {async: false});
	}
	
	function query() {
		$("#organizationtable").params($("#conForm").getData());
		$("#organizationtable").refresh();
	}
	
	function edit(id, name) {
		var obj = {
				width: '75%',
				height: '85%',
				callback : function(rtn) {
					if(rtn) {
						$("#organizationtable").refresh();
					}
			    }
		};
		if (id) {
			obj.id = id;
		}
		$.modal("edit.html", id ? ("修改机构 - " + name) : "新增机构", obj);
	}
</script>