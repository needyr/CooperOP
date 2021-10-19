<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="TPN明细维护">
	<s:row>
		<s:form>
			<s:toolbar>
				<s:button label="返回" onclick="$.closeModal(false);"></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield label="审方指标ID" value="${param.shengfang_tpnzbid }" readonly="true"></s:textfield>
				<s:textfield label="审方指标名称" value="${param.name_tpnzb }" readonly="true"></s:textfield>
			</s:row>

			<s:row>
				<s:textfield label="年龄区间（岁）" value="${param.nianl_start_tnpzb }" readonly="true"></s:textfield>
				<s:textfield label="至" value="${param.nianl_end_tpnzb }" readonly="true"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table label="TPN指标明细" autoload="true" sort="false"
			action="hospital_common.tpn.tpnzb.queryTpnzbMX" id="datatable">
			<s:toolbar>
				<s:row>
					<s:button label="新增" onclick="add('${param.shengfang_tpnzbid }')"></s:button>
				</s:row>
			</s:toolbar>
			<s:table.fields>
				<s:table.field label="指标名称" name="tpnzb_name"></s:table.field>
				<s:table.field label="指标最小值" name="tpnzb_min"></s:table.field>
				<s:table.field label="指标最大值" name="tpnzb_max"></s:table.field>
				<s:table.field label="指标单位" name="tpnzb_dw"></s:table.field>
				<s:table.field label="备注" name="beizhu"></s:table.field>
				<s:table.field label="公式" name="formul_tpnzb"></s:table.field>
				<s:table.field label="显示顺序" name="dj_sn" sort="true" defaultsort="asc"></s:table.field>
				<s:table.field label="显示图片比例" name="show_bl"></s:table.field>
				<s:table.field label="显示单位" name="show_dw"></s:table.field>
				<s:table.field label="操作" name="caozuo" datatype="template">
					<a
						onclick="edit('$[id]')">修改</a>
					<a onclick="delWindow('$[id]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>

<script type="text/javascript">
/* 初始化 */
$(function() {
	query();
});

function query() {
	var formData = {
		"shengfang_tpnzbid" : '${param.shengfang_tpnzbid}'
	}
	$("#datatable").params(formData);
	$("#datatable").refresh();
}
	//新增
	function add(shengfang_tpnzbid) {
		$.modal("zbmxAdd.html", "新增信息", {
			width : "50%",
			height : "90%",
			"shengfang_tpnzbid" : shengfang_tpnzbid,
			callback : function(e) {
				$("#datatable").refresh();
			}

		})
	}
	//修改
	function edit(id) {
		$.modal("zbmxEdit.html", "修改信息", {
			width : '610px',
			height : '620px',
			"id" : id,
			callback : function(r) {
				$("#datatable").refresh();
			}
		});
	}
	
	//删除
	function del(id) {
		ajax: $.call("hospital_common.tpn.tpnzb.deleteZbmx", {
			"id" : id
		}, function(s) {
			$("#datatable").refresh();
		})
	}

	//删除警告弹窗
	function delWindow(id) {
		$.confirm('确定删除?删除之后无法恢复！', function(choose) {
			if (choose == true) {
				del(id);
			}
		});
	}

</script>
