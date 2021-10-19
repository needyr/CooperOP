<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="数据转换-存储过程维护" >
	<s:row>
		<s:form id="form" label="快速查找">
			<s:row>
				<s:textfield label="关键字" name="filter" placeholder="请输入存储过程编码，中文名，或数据库名称" cols="2"></s:textfield>
				<s:button id="query-btn" label="查询" icon="fa fa-search" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="处理方法列表" autoload="false" action="hospital_common.trade.proc.query"  >
			<s:toolbar>
				<s:button icon="fa fa-plus" label="新增" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="id" label="ID"></s:table.field>
				<s:table.field name="proc_code" label="处理方法编码"  ></s:table.field>
				<%-- <s:table.field name="proc_name" label="存储过程名"  ></s:table.field> --%>
				<s:table.field name="proc_name_cn" label="处理方法名称" ></s:table.field>
				<s:table.field name="db_code" label="数据库" datatype="template">
					<a href="javascript:void(0);" onclick="dbInfo('$[db_code]')">$[db_code]</a>
				</s:table.field>
				<s:table.field name="description" label="描述/备注"  ></s:table.field>
				<s:table.field name="state" label="状态" datatype="script">
					var state = record.state;
					if(state == 0){
						return "停用" ;
					}else if(state == 1){
						return '<font style="color: green;">启用</font>';
					}
				</s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="100">
					<a href="javascript:void(0)" onclick="update('$[proc_code]')">修改</a>
					<a href="javascript:void(0)" onclick="del('$[proc_code]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
var proc_code = '${param.proc_code}';
$(function(){
	if(proc_code != ''){
		$('[name="filter"]').val(proc_code);
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
		width:"90%",
		height:"90%",
		callback : function(e){
			if(e){
				query();
			}
		}
	});
}

function update(proc_code){
	$.modal("edit.html","修改",{
		width:"90%",
		height:"90%",
		proc_code: proc_code,
		callback : function(e){
			if(e){
				query();
			}
		}
	});
}

function del(proc_code){
	$.confirm('确定删除?删除之后无法恢复！',function(choose){
		if(choose == true){
			$.call("hospital_common.trade.proc.delete",{proc_code: proc_code},function(rtn){
				if(rtn > 0){
					query();
				}else{
					$.message('删除失败！');
				}
			})
		}
	});
}

function dbInfo(db_code){
	$.modal("/w/hospital_common/trade/db/list.html","查看数据库配置信息",{
		width:"90%",
		height:"90%",
		db_code: db_code,
		callback : function(e){
			if(e){
				query();
			}
		}
	});
}
</script>