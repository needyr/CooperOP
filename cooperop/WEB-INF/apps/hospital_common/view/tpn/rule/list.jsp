<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="TPN规则定义">
	<s:row>
		<s:form id="query_form">
			<s:row>
				<s:textfield name="query_name" placeholder="TPN规则名称"></s:textfield>
				<s:button label="查询" style="background: #368bdb;text-align: center;color: #FFF;" onclick="query()" icon="fa fa-search"></s:button>
			</s:row>
		</s:form>
	</s:row>

	<s:row>
		<s:table label="TPN规则定义" id="datatable" autoload="false"
			action="hospital_common.tpn.rule.query">
			<s:toolbar>
				<s:button label="重新载入TPN审查规则"  style="background: #fff5c1;" icon="" onclick="init_tpn();"></s:button>
				<s:button onclick="add()" label="新增" icon="fa fa-add"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field label="序号" name="sort" ></s:table.field>
				<s:table.field label="TPN规则名称" name="tpnzl_name"></s:table.field>
				<s:table.field label="开始年龄" name="tpnzl_nianl_start" ></s:table.field>
				<s:table.field label="截至年龄" name="tpnzl_nianl_end" ></s:table.field>
				<s:table.field label="状态" name="state" datatype="script">
					if(record.state == 1){
						return "<span style='color: green'>开启</span>";
					}else{
						return "<span style='color: red'>停用</span>";
					}
				</s:table.field>
				<s:table.field label="备注" name="beizhu"></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="script">		
	                var html = [];
					html.push('<a data-column="'+record.column_name+'" onclick="edit2(\''+record.tpnzl_id+'\')">修改行内容</a>　　');
					html.push('<a data-column="'+record.column_name+'" onclick="edit(\''+record.tpnzl_id+'\')">修改明细</a>　　');
					html.push('<a data-column="'+record.column_name+'" onclick="delWindow(\''+record.tpnzl_id+'\')">删除</a>　　');
					html.push('<a data-column="'+record.column_name+'" onclick="changeState(\''+record.tpnzl_id+'\','+record.state+')">');
					html.push(record.state==1?"停用":"启用")
					html.push('</a>');
					return html.join("");
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>

</s:page>
<script type="text/javascript">
	$(function() {
		query();
	});

	function query() {
		var qdata = $("#query_form").getData();
		$("#datatable").params(qdata);
		$("#datatable").refresh();

	}

	//新增
	function add() {
		$.modal("add.html", "新增", {
			width : "50%",
			height : "70%",
			callback : function(e) {
				query();
			}

		})
	}

	//修改
	function edit(tpnzl_id) {
		$.modal("edit.html", "新增", {
			width : "50%",
			height : "70%",
			"tpnzl_id" : tpnzl_id,
			callback : function(e) {
				query();
			}

		})
	}
	
	//修改
	function edit2(tpnzl_id) {
		$.modal("add.html", "修改", {
			width : "50%",
			height : "70%",
			"tpnzl_id" : tpnzl_id,
			callback : function(e) {
				query();
			}

		})
	}

	//停用、启用
	function changeState(tpnzl_id,state) {
		if (state == 1){
			state = 0;
		}else{
			state = 1;
		}
		$.call("hospital_common.tpn.rule.updateByState",{"tpnzl_id": tpnzl_id, "state": state},function(rtn){
			query();
		})
	}

	//删除
	function del(tpnzl_id) {
		ajax: $.call("hospital_common.tpn.rule.delete", {
			"tpnzl_id" : tpnzl_id
		}, function(s) {
			query();
		})
	}

	//删除警告弹窗
	function delWindow(tpnzl_id) {
		$.confirm('确定删除?删除之后无法恢复！', function(choose) {
			if (choose == true) {
				del(tpnzl_id);
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