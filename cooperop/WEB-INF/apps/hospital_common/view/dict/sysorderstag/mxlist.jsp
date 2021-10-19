<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="医嘱标签值维护" disloggedin="true">
	<s:row>
		<%-- <s:form id="form" label="快速查找">
			<s:row>
				<s:textfield label="关键字"  name="filter" placeholder="请输入项目名称或值关键字" cols="2"></s:textfield>
				<s:button label="查询" icon="fa fa-search" onclick="query('${param.ordertagid}')"></s:button>
			</s:row>
		</s:form> --%>
		<s:table id="tagtable" label="医嘱标签信息" autoload="false" action="hospital_common.dict.sysorderstag.query">
			<s:table.fields>
				<s:table.field name="ordertagid"  label="标签ID" ></s:table.field>
				<s:table.field name="ordertagbh" label="标签编号" ></s:table.field>
				<s:table.field name="ordertagname"  label="标签名称" ></s:table.field>
				<s:table.field name="beactive"  label="是否开启" ></s:table.field>
				<s:table.field name="ordertag_show"   label="标签缩写" ></s:table.field>
				<s:table.field name="ordertag_shuom"  label="标签说明" ></s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
	<input type="hidden" id="tagid" name="tagid" value="id"/>
	<s:row>
		<s:table id="datatable" label="医嘱标签对应的项目信息" autoload="false"  sort="true" action="hospital_common.dict.sysorderstag.queryOrdersValue"  >
			<s:toolbar>
				<s:button icon="fa fa-plus" label="新增" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="xmid" label="项目ID" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="tiaojian_name" label="条件" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="createtime" label="创建时间" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="script" width="75px">
					var html = [];
					html.push('<a data-column="'+record.instance+'"  onclick="update(\''+record.id+'\',\''+record.xmid+'\')">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;');
					html.push('<a data-column="'+record.instance+'"  onclick="del(\''+record.id+'\')">删除</a>');
					return html.join("");
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
$(function(){
	$("#tagtable_paginate").hide();
	$("#tagtable_info").hide();
	$("#tagtable_length").hide();
	var tagid='${param.ordertagid}';
	$("#tagid").val(tagid);
	query(tagid);
	queryTagTable(tagid);
	
});

function query(tagid){
	var qdata={};
	if(typeof tagid == "undefined" || tagid == null || tagid == ""){
		qdata.ordertagid=$("#tagid").val();
	}else{
		qdata.ordertagid=tagid;
	}
	$("#datatable").params(qdata);
	$("#datatable").refresh();
	
}
function queryTagTable(tagid){
	var qdata={};
	if(typeof tagid == "undefined" || tagid == null || tagid == ""){
		qdata.ordertagid=$("#tagid").val();
	}else{
		qdata.ordertagid=tagid;
	}
	$("#tagtable").params(qdata);
	$("#tagtable").refresh();
}
function Add(){
	var tagid=$("#tagid").val();
	$.modal("mxedit.html","新增",{
		width:"50%",
		height:"55%",
		ordertagid:tagid,
		callback : function(e){
			query();
		}
	});
}


function update(id,xmid){
	$.modal("mxedit.html","修改",{
		width:"50%",
		height:"55%",
		id:id,
		xmid:xmid,
		callback : function(e){
			query();
		}
	});
}

function del(id){
	$.confirm("是否确认删除该条数据？",function callback(e){
		if(e==true){
			$.call("hospital_common.dict.sysorderstag.ordersValueDelete",{id :id},function(rtn){
				if(rtn){
					query();
				}
			})
		}
	})
	
}

</script>