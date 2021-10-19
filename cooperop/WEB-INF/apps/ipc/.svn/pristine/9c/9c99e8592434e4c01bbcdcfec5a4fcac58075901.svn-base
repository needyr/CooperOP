<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="药师信息管理" >
	<s:row>
		<s:form border="0" id="form" label="快速查找">
			<s:row>
				<s:textfield label="快速查找" name="filter" placeholder="请输入药师姓名或手机号" cols="2"></s:textfield>
				<s:button label="查询" icon="" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="药师信息管理" autoload="false" action="ipc.pharmacist.query" sort="true" >
			<s:toolbar>
				<s:button icon="" label="新增药师" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="no" label="职工号" datatype="" ></s:table.field>
				<s:table.field name="name" label="药师姓名" datatype="" ></s:table.field>
				<s:table.field name="gender" label="性别" datatype="script" width="28px">
					if(record.gender==1){
						return "男";
					}else if(record.gender==0){
						return "女";
					}else {
						return "待定";
					}
				</s:table.field>
				<s:table.field name="order_type" label="审方类型" datatype="script" width="50px">
					var get_order = record.order_type;
					var ss = [];
					if(get_order){
						if(get_order.indexOf("1") > -1){
							ss.push('住院');
						}
						if(get_order.indexOf("2") > -1){
							ss.push('门诊');
						}
						if(get_order.indexOf("3") > -1){
							ss.push('急诊');
						}
						if(get_order.indexOf("1") <= -1 && get_order.indexOf("2") <= -1 && get_order.indexOf("3") <= -1){
							ss.push('全部');
						}
					}
					return ss.join(',');
				</s:table.field>
				<s:table.field name="mobile" label="手机" datatype="" width="100px"></s:table.field>
				<s:table.field name="deptname" label="科室" datatype="" ></s:table.field>
				<s:table.field name="state" label="是否启用" datatype="script" width="50px">
					switch (+record.state) {
						case 1:
							return '<span class="font-green">启用</span>';
						case 0:
							return '<span class="font-gray">停用</span>';
					}
				</s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="70px">
					<a href="javascript:void(0)" onclick="update('$[id]',$[sid])">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]',$[sid])">删除</a>
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
		/* qdata.ts='${param.ts}';
		qdata.vs='${param.vs}';
		qdata.uid='${param.uid}'; */
		$("#datatable").params(qdata);
		$("#datatable").refresh();
	}
	
	function Add(){
		$.modal("edit.html","新增药师信息",{
			width:"80%",
			height:"90%",
			callback : function(e){
				query();
			}
		});
	}
	
	function update(id,sid){
		$.modal("edit.html","修改药师信息",{
			width:"80%",
			height:"90%",
			id:id,
			sid:sid,
			callback : function(e){
				query();
			}
		});
	}
	
	function Delete(id,sid){
		$.confirm("是否确认删除？删除后无法恢复！",function callback(e){
			if(e==true){
				$.call("ipc.pharmacist.delete",{"id":id,"sid":sid},function (rtn){
					query();
				})
			}
		})	
	}
	
</script>