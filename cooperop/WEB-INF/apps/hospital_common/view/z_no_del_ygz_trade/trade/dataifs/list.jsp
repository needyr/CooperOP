<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="数据采集-接口配置" >
	<s:row>
		<s:form id="form" label="快速查找">
			<s:row>
				<s:textfield label="关键字" name="filter" placeholder="请输入目标数据表名，表中文名或描述" cols="2"></s:textfield>
				<s:button id="query-btn" label="查询" icon="fa fa-search" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="待采集数据表" autoload="false" action="hospital_common.trade.dataifs.query"  >
			<s:toolbar>
				<s:button icon="fa fa-plus" label="新增" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="id" label="ID"></s:table.field>
				<s:table.field name="code" label="目标数据表"></s:table.field>
				<s:table.field name="name" label="中文名"></s:table.field>
				<s:table.field name="interface_url" label="[数据转换]接口链接(在交易接口中维护)" datatype="script">
					var iurl = record.interface_url;
					if(iurl){
						return iurl;		
					}else{
						return '<font style="color: red">缺失</font>';
					}
				</s:table.field>
				<s:table.field name="cycle_cron" label="定时表达式"></s:table.field>
				<s:table.field name="description" label="描述"></s:table.field>
				<s:table.field name="state" label="状态" datatype="script">
					var state = record.state;
					if(state == 0){
						return '停用';
					}else if(state == 1){
						return '<font style="color: green;">启用</font>';
					}else{
						return '';
					}
				</s:table.field>
				<s:table.field name="is_init_call" label="初始化调用" datatype="script">
					var is_init_call = record.is_init_call;
					if(is_init_call == 0){
						return '停用';
					}else if(is_init_call == 1){
						return '<font style="color: green;">启用</font>';
					}else{
						return '';
					}
				</s:table.field>
				<s:table.field name="is_ds_call" label="定时调用" datatype="script">
					var is_ds_call = record.is_ds_call;
					if(is_ds_call == 0){
						return '停用';
					}else if(is_ds_call == 1){
						return '<font style="color: green;">启用</font>';
					}else{
						return '';
					}
				</s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="100">
					<a href="javascript:void(0)" onclick="admin('$[code]')">管理</a> |
					<a href="javascript:void(0)" onclick="update('$[code]')">修改</a> |
					<a href="javascript:void(0)" onclick="del('$[code]')">删除</a>
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
	$("#datatable").params(qdata);
	$("#datatable").refresh();
}

function Add(){
	$.modal("edit.html","新增",{
		width:"70%",
		height:"60%",
		callback : function(e){
			if(e){
				query();
			}
		}
	});
}

function update(code){
	$.modal("edit.html","修改",{
		width:"70%",
		height:"60%",
		code: code,
		callback : function(e){
			if(e){
				query();
			}
		}
	});
}

function del(code){
	$.confirm('确定删除?删除之后无法恢复！',function(choose){
		if(choose == true){
			$.call("hospital_common.trade.dataifs.delete",{code: code},function(rtn){
				if(rtn > 0){
					query();
				}else{
					$.message('删除失败！');
				}
			})
		}
	});
}

function admin(code){
	$.modal("/w/hospital_common/trade/config/list.html","数据交易接口管理",{
		width:"90%",
		height:"90%",
		trade_code: code,
		callback : function(e){
			if(e){
				query();
			}
		}
	});
}
</script>