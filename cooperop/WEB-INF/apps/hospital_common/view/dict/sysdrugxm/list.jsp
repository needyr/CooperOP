<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="药品属性字典维护" disloggedin="true">
	<div class="note note-warning">
	温馨提示：此处定义药品属性字典维护及对属性值进行管理
	</div>
	<s:row>
		<s:form id="form" label="快速查找">
			<s:row>
				<input type="hidden" name="product_code"  value="ipc"/>
				<s:textfield label="关键字"  name="filter" placeholder="请输入项目名称关键字或拼音码" cols="1"></s:textfield>
				<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="药品属性字典维护" autoload="false"  sort="true" action="hospital_common.dict.sysdrugxm.query"  >
			<s:toolbar>
				<s:button icon="fa fa-plus" label="新增" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="xmid" label="项目ID"  defaultsort="asc"></s:table.field>
				<s:table.field name="xmtype" label="项目类型" sort="true"></s:table.field>
				<s:table.field name="xmbh" label="项目编号"  ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="beactive" label="是否开启"></s:table.field>
				<s:table.field name="pym" label="拼音码" ></s:table.field>
				<s:table.field name="fdtype" label="数字类型" sort="true"></s:table.field>
				<s:table.field name="xmdw" label="单位" ></s:table.field>
				<s:table.field name="username" label="最后修改用户名称" ></s:table.field>
				<s:table.field name="lasttime" label="更新时间" ></s:table.field>
				<s:table.field label="操作" name="oper" datatype="template" width="117px">
					<a style="margin:0 5px;" href="javascript:void(0)"
						onclick="manage('$[xmid]','$[xmmch]');">管理</a>
					<a style="margin:0 5px;" href="javascript:void(0)"
						onclick="update('$[xmid]');">修改</a>
					<a style="margin:0 5px;" href="javascript:void(0)"
						onclick="del('$[xmid]');">删除</a>
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
		width:"50%",
		height:"55%",
		callback : function(e){
			query();
		}
	});
}

function manage(xmid,xmmch){
	$.modal("xmvaluelist.html","属性值管理",{
		width:"70%",
		height:"70%",
		xmid:xmid,
		xmmch:xmmch,
		callback : function(e){
			query();
		}
	});
}

function update(xmid){
	$.modal("edit.html","修改",{
		width:"50%",
		height:"55%",
		xmid:xmid,
		callback : function(e){
			query();
		}
	});
}

function del(xmid){
	$.confirm("是否确认删除该条数据？",function callback(e){
		if(e==true){
			$.call("hospital_common.dict.sysdrugxm.delete",{xmid :xmid},function(rtn){
				if(rtn){
					query();
				}
			})
		}
	})
	
}

</script>