<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="数据转换-数据库连接配置" >
	<s:row>
		<s:form id="form" label="快速查找">
			<s:row>
				<s:textfield label="关键字" name="filter" placeholder="请输入数据库编码，名称，配置名或数据库类型" cols="2"></s:textfield>
				<s:button id="query-btn" label="查询" icon="fa fa-search" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="数据库链接列表" autoload="false" action="hospital_common.trade.db.query"  >
			<s:toolbar>
				<s:button icon="fa fa-plus" label="新增" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="id" label="ID"></s:table.field>
				<s:table.field name="db_code" label="编码"></s:table.field>
				<s:table.field name="db_name" label="数据库"></s:table.field>
				<s:table.field name="db_url" label="数据库连接"></s:table.field>
				<s:table.field name="db_user" label="用户名"></s:table.field>
				<s:table.field name="db_pwd" label="密码"></s:table.field>
				<s:table.field name="db_config_name" label="配置名" title="数据库配置名称"></s:table.field>
				<s:table.field name="description" label="描述"></s:table.field>
				<s:table.field name="db_type" label="数据库类型"></s:table.field>
				<s:table.field name="state" label="状态" datatype="script" title="停用时，新增存储过程将不能选择该数据库连接">
					var state = record.state;
					if(state == 0){
						return "停用" ;
					}else if(state == 1){
						return '<font style="color: green;">启用</font>';
					}
				</s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="80">
					<a href="javascript:void(0)" onclick="update('$[db_code]')">修改</a>
					<a href="javascript:void(0)" onclick="del('$[db_code]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
var db_code = '${param.db_code}';
$(function(){
	if(db_code != ''){
		$('[name="filter"]').val(db_code);
	}
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

function update(db_code){
	$.modal("edit.html","修改",{
		width:"70%",
		height:"60%",
		db_code: db_code,
		callback : function(e){
			if(e){
				query();
			}
		}
	});
}

function del(db_code){
	$.confirm('确定删除?删除之后无法恢复！',function(choose){
		if(choose == true){
			$.call("hospital_common.trade.db.delete",{db_code: db_code},function(rtn){
				if(rtn > 0){
					query();
				}else{
					$.message('删除失败！');
				}
			})
		}
	});
}
</script>