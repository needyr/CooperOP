<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="医嘱标签字典维护" disloggedin="true">
	<div class="note note-warning">
	温馨提示：此处为定义医嘱标签字典的维护页面
	</div>
	<s:row>
		<s:form id="form" label="快速查找">
			<s:row>
				<s:textfield label="关键字"  name="filter" placeholder="请输入标签名称关键字" cols="1"></s:textfield>
				<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="医嘱标签字典维护" autoload="false"  sort="true" action="hospital_common.dict.sysorderstag.query"  >
			<s:toolbar>
				<s:button icon="fa fa-plus" label="新增" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="ordertagid" label="标签ID" sort="true" defaultsort="asc"></s:table.field>
				<s:table.field name="ordertagbh" label="标签编号" ></s:table.field>
				<s:table.field name="ordertagname" label="标签名称" ></s:table.field>
				<s:table.field name="beactive" label="是否开启" ></s:table.field>
				<s:table.field name="ordertag_show" label="标签缩写" ></s:table.field>
				<s:table.field name="ordertag_shuom" label="标签说明" ></s:table.field>
				<s:table.field name="beizhu" label="备注" ></s:table.field>
				<%-- <s:table.field name="xmid" label="项目ID" ></s:table.field>
				<s:table.field name="xmbh" label="项目编号" ></s:table.field> --%>
				<s:table.field label="操作" name="oper" datatype="template" width="">
					<a style="margin:0 5px;" href="javascript:void(0)"
						onclick="manage('$[ordertagid]');">管理</a>
					<a style="margin:0 5px;" href="javascript:void(0)"
						onclick="update('$[ordertagid]');">修改</a>
					<a style="margin:0 5px;" href="javascript:void(0)"
						onclick="del('$[ordertagid]');">删除</a>
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
		height:"60%",
		callback : function(e){
			query();

		}
	});
}

function update(ordertagid){
	$.modal("edit.html","修改",{
		width:"50%",
		height:"60%",
		ordertagid:ordertagid,
		callback : function(e){
			query();
		}
	});
}


function manage(ordertagid){
	$.modal("mxlist.html","医嘱项目管理",{
		width:"80%",
		height:"75%",
		ordertagid:ordertagid,
		callback : function(e){
			query();
		}
	});
}

function del(ordertagid){
	$.confirm("是否确认删除该条数据？",function callback(e){
		if(e==true){
			$.call("hospital_common.dict.sysorderstag.delete",{ordertagid: ordertagid},function(rtn){
				if(rtn){
					query();
				}
			})
		}
	})
	
}

</script>