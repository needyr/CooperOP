<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="产品管理">
	<div class="note note-warning">温馨提示：此处仅显示已授权的产品清单。</div>
	<s:row>
		<s:table icon="fa fa-table" id="producttable" label="已授权产品列表"
			action="crdc.product.query" autoload="true"
			sort="true" fitwidth="true">
			<s:toolbar>
				<s:button icon="fa fa-file-o" label="添加产品" size="btn-sm btn-default" onclick="add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="code" datatype="string" label="产品编码" sort="true" defaultsort="asc"></s:table.field>
				<s:table.field name="name" label="产品名称" datatype="string" sort="true"></s:table.field>
				<s:table.field name="popedom" label="根权限" datatype="script">
					return record["popedom"] + "-" + record["popedom_names"];
				</s:table.field>
				<s:table.field name="default_role_name" label="默认角色" sort="true"></s:table.field>
				<s:table.field name="oper" datatype="template" label="操作" align="center">
					<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="modify('$[code]')">修改</a>
					<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="deleteProduct('$[code]')">删除</a>
					<a style="margin: 0px 5px;" class="font-red-sunglo" href="javascript:void(0)" onclick="deploy('$[code]')">全部发布</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(document).ready(function() {
	});
	function query() {
		$("#producttable").params($("#conditions").getData());
		$("#producttable").refresh();
	}
	function add() {
		$.modal("add.html", "新增产品", {
			width: '400px',
			height: '400px',
			callback: function(rtn) {
				if (rtn) {
					query();
				}
			}
		});
	}
	function modify(code) {
		$.modal("modify.html", "新增产品", {
			width: '400px',
			height: '400px',
			code: code,
			callback: function(rtn) {
				if (rtn) {
					query();
				}
			}
		});
	}
	function deleteProduct(code) {
		$.confirm("是否确认删除此产品？删除后将无法恢复！", function(c) {
			if (c) {
				$.call("crdc.product.delete", {
					code: code
				}, function(rtn) {
					$("#producttable").refresh();
				});
			}
		});
	}
</script>