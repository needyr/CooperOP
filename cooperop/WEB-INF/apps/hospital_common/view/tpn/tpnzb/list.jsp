<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="TPN指标维护">
	<s:row>
		<s:table label="TPN指标维护" id="datatable" autoload="true"
			action="hospital_common.tpn.tpnzb.query">
			<s:toolbar>
				<s:button label="重新载入TPN审查规则" style="background: #fff5c1;" icon="" onclick="init_tpn();"></s:button>
				<s:button onclick="add()" label="新增TPN指标"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field label="指标ID" name="shengfang_tpnzbid"></s:table.field>
				<s:table.field label="指标编号" name="shengfang_tpnzbbh"></s:table.field>
				<s:table.field label="指标名称" name="name_tpnzb"></s:table.field>
				<s:table.field label="等级" name="level"></s:table.field>
				<s:table.field label="是否活动" name="beactive"></s:table.field>
				<s:table.field label="拼音码" name="pym"></s:table.field>
				<s:table.field label="开始年龄_TPN指标" name="nianl_start_tnpzb"></s:table.field>
				<s:table.field label="截止年龄_TPN指标" name="nianl_end_tpnzb"></s:table.field>
				<s:table.field label="备注" name="beizhu"></s:table.field>
				<s:table.field label="操作员" name="username"></s:table.field>
				<s:table.field label="操作" name="caozuo" datatype="template">
					<a
						onclick="zbmx('$[shengfang_tpnzbid]','$[name_tpnzb]','$[nianl_start_tnpzb]','$[nianl_end_tpnzb]')">查看明细</a>
					<a onclick="edit('$[shengfang_tpnzbid]')">修改</a>
					<a onclick="delWindow('$[shengfang_tpnzbid]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>

</s:page>
<script type="text/javascript">
	//新增
	function add() {
		$.modal("add.html", "新增信息", {
			width : "50%",
			height : "80%",
			callback : function(e) {
				$("#datatable").refresh();
			}

		})
	}

	//查看明细
	function zbmx(shengfang_tpnzbid, name_tpnzb, nianl_start_tnpzb,
			nianl_end_tpnzb) {
		$.modal("zbmx.html", "TPN明细维护", {
			width : "90%",
			height : "80%",
			"shengfang_tpnzbid" : shengfang_tpnzbid,
			"name_tpnzb" : name_tpnzb,
			"nianl_start_tnpzb" : nianl_start_tnpzb,
			"nianl_end_tpnzb" : nianl_end_tpnzb,
			callback : function(e) {
				$("#datatable").refresh();
			}

		})
	}

	//修改
	function edit(shengfang_tpnzbid) {
		$.modal("edit.html", "修改信息", {
			width : '600px',
			height : '600px',
			"shengfang_tpnzbid" : shengfang_tpnzbid,
			callback : function(r) {
				$("#datatable").refresh();
			}
		});
	}

	//删除
	function del(shengfang_tpnzbid) {
		ajax: $.call("hospital_common.tpn.tpnzb.delete", {
			"shengfang_tpnzbid" : shengfang_tpnzbid
		}, function(s) {
			$("#datatable").refresh();
		})
	}

	//删除警告弹窗
	function delWindow(shengfang_tpnzbid) {
		$.confirm('确定删除?删除之后无法恢复！', function(choose) {
			if (choose == true) {
				del(shengfang_tpnzbid);
			}
		});
	}

	function init_tpn(){
		$.confirm("确认重新载入TPN审查规则吗?",function callback(e){
            if(e==true){
            	$.call('hospital_common.tpn.xiangmuwh.init_tpn',{},function(){})
            }
        });
	}
</script>