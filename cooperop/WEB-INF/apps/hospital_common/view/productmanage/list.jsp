<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="产品管理" >
	<s:row>
		<s:form border="0" id="form" collapsed="true" extendable="true">
			<s:row>
				<s:textfield label="快速查找" name="filter" placeholder="请输入产品名称" cols="2"></s:textfield>
				<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="产品管理" autoload="false" action="hospital_common.productmanage.query" sort="true" >
			<s:toolbar>
				<s:button icon="" label="新增产品" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="code" label="产品编码" datatype="" ></s:table.field>
				<s:table.field name="name" label="产品名称" datatype="" ></s:table.field>
				<s:table.field name="popedom" label="产品权限" datatype="" ></s:table.field>
				<s:table.field name="default_role" label="默认规则" datatype="" ></s:table.field>
				<s:table.field name="is_active" label="是否开启" datatype="script" >
					if(record.is_active==1){
						return "是";
					}else{
						return "否";
					}
				</s:table.field>
				<s:table.field name="is_check_server" label="是否为审查服务" datatype="script" >
					if(record.is_check_server == 1){
						return "是";
					}else{
						return "否";
					}
				</s:table.field>
				<s:table.field name="interface_url" label="产品接口链接" datatype="" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="80px">
					<a href="javascript:void(0)" onclick="edit('$[code]')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[code]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		$(window).resize(function(){
			$('.dataTables_scrollBody').css('height',$(window).height() -
					270 - $("div[ctype='form']").parent().parent().height());
			$('.dataTables_scrollBody').css('min-height','100px');
			$('.dataTables_scrollBody').css('overflow','auto');
		}).resize();

		$("i.showhide").on("click", function(){
			$('.dataTables_scrollBody').css('height',$(window).height() -
					270 - $("div[ctype='form']").parent().parent().height());
			$('.dataTables_scrollBody').css('min-height','100px');
			$('.dataTables_scrollBody').css('overflow','auto');
		})
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
		$.modal("edit.html","新增产品信息",{
			width:"600px",
			height:"400px",
			callback : function(e){
				query();
			}
		});
	}
	
	function edit(code){
		$.modal("edit.html","修改产品信息",{
			width:"600px",
			height:"400px",
			code:code,
			callback : function(e){
				query();
			}
		});
	}
	
	function Delete(code){
		$.confirm("是否确认删除？删除后无法恢复！",function callback(e){
			if(e==true){
				$.call("hospital_common.productmanage.delete",{"code":code},function (rtn){
					query();
				})
			}
		})	
	}
	
</script>