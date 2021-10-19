<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="药品属性值维护" disloggedin="true">
	<s:row>
		<s:form id="form" label="快速查找">
			<s:row>
				<s:textfield label="关键字"  name="filter" placeholder="请输入值关键字" cols="2"></s:textfield>
				<s:button label="查询" icon="fa fa-search" onclick="query('${param.xmid}')"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<input type="hidden" id="xmid" name="xmid"/>
	<input type="hidden" id="xmmch" name="xmmch"/>
	<s:row>
		<s:table id="datatable" label="药品属性值" autoload="false"  sort="true" action="hospital_common.dict.sysdrugxm.queryXmValue"  >
			<s:toolbar>
				<s:button icon="fa fa-plus" label="新增" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="xmid" label="项目ID" sort="true" defaultsort="asc"></s:table.field>
				<s:table.field name="xmmch" label="名称" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="script" width="75px">
					var html = [];
					html.push('<a data-column="'+record.instance+'"  onclick="update(\''+record.xmid+'\',\''+record.xmmch+'\',\''+record.value+'\')">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;');
					html.push('<a data-column="'+record.instance+'"  onclick="del(\''+record.xmid+'\',\''+record.xmmch+'\',\''+record.value+'\')">删除</a>');
					return html.join("");
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
$(function(){
	var XMID='${param.xmid}';
	var xmmch='${param.xmmch}';
    $("#xmid").val(XMID);
    $("#xmmch").val(xmmch);
	query(XMID);
});

function query(XMID){
	debugger
	var qdata=$("#form").getData();
	if(typeof XMID == "undefined" || XMID == null || XMID == ""){
		qdata.XMID=$("#xmid").val();
	}else{
		qdata.XMID=XMID;
	}
	$("#datatable").params(qdata);
	$("#datatable").refresh();
}

function Add(){
	$.modal("xmvalueedit.html","新增",{
		width:"40%",
		height:"45%",
		xmid:$("#xmid").val(),
		xmmch:$("#xmmch").val(),
		callback : function(e){
			query();
		}
	});
}


function update(xmid,xmmch,value){
	$.modal("xmvalueedit.html","修改",{
		width:"40%",
		height:"45%",
		xmid:xmid,
		xmmch:xmmch,
		value:value,
		callback : function(e){
			query();
		}
	});
}

function del(xmid,xmmch,value){
	$.confirm("是否确认删除该条数据？",function callback(e){
		if(e==true){
			$.call("hospital_common.dict.sysdrugxm.xmValueDelete",{xmid :xmid , xmmch :xmmch , value :value},function(rtn){
				if(rtn){
					query();
				}
			})
		}
	})
	
}

</script>