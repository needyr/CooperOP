<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="TPN规则明细">
	<s:row>
		<s:form label="TPN规则明细新增" id="dataForm" style="display:none">
			<s:row>
				<s:button  label="保存" onclick="save()" style="background: #368bdb;text-align: center;color: #FFF;float:right;"></s:button>
			</s:row>
			<s:row>
				<s:switch label="是否启用" name="state" offtext="否" ontext="是" onvalue="1" offvalue="0"></s:switch>
				<s:textfield label="规则分组" name="groupsn" required="true" value="1"></s:textfield>
			</s:row>
			<s:row style="display:none">
				<s:textfield label="" id="fdname1"  name="fdname" ></s:textfield>
			</s:row>
			<s:row>
				<s:autocomplete label="项目名称" name="xmmch" action="hospital_common.tpn.rule.getXmmch" required="true"  limit="3">
						<s:option value="$[xmmch]" label="$[xmmch]"  data-fd="$[fdname]" ><p onclick="xz01('$[fdname]')">$[xmmch]</p></s:option>
				</s:autocomplete>

				<s:autocomplete label="公式" name="formul" required="true" >
					<s:option value="$[value]" label="$[name]"></s:option>
						<s:option data-value="&gt;"  data-name="大于" ></s:option>
						<s:option data-value="&lt;"  data-name="小于" ></s:option>
						<s:option data-value="&gt;="  data-name="大于等于" ></s:option>
						<s:option data-value="&lt;="  data-name="小于等于" ></s:option>
						<s:option data-value="!="  data-name="不等于" ></s:option>
						<s:option data-value="="  data-name="等于" ></s:option>
						<s:option data-value="like"  data-name="类似" ></s:option>
						<s:option data-value="not like"  data-name="不类似" ></s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<s:textfield label="值" name="value" required="true"></s:textfield>
				<s:textfield label="单位" name="unit"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table label="TPN规则明细" id="datatable" autoload="false"
			action="hospital_common.tpn.rule.queryTpnzlRule">
			<s:toolbar>
				<s:row>
					<s:button label="新增" onclick="add()"></s:button>
					<s:button label="返回" onclick="$.closeModal(false);"></s:button>
				</s:row>
			</s:toolbar>
			<s:table.fields>
				<s:table.field label="项目名称" name="xmmch"></s:table.field>
				<s:table.field label="字段名" name="fdname"></s:table.field>
				<s:table.field label="公式" name="formul"></s:table.field>
				<s:table.field label="值" name="value"></s:table.field>
				<s:table.field label="规则分组" name="groupsn"></s:table.field>
				<s:table.field label="单位" name="unit"></s:table.field>
                <s:table.field label="状态" name="state" datatype="script">
					if(record.state == 1){
						return "<span style='color: green'>开启</span>";
					}else{
						return "<span style='color: red'>停用</span>";
					}
				</s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="script">		
	                var html = [];
					html.push('<a data-column="'+record.column_name+'" onclick="delWindow(\''+record.id+'\')">删除</a>　　');
					html.push('<a data-column="'+record.column_name+'" onclick="changeState2(\''+record.id+'\','+record.state+')">');
					html.push(record.state==1?"停用":"启用")
					html.push('</a>');
					return html.join("");
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
	
	function xz01(fdname){
		$("#fdname1").val(fdname);
		console.log($("#fdname1").val())
	}

	function query() {
		var formData = {
			"tpnzl_id" : '${param.tpnzl_id}'
		}
		$("#datatable").params(formData);
		$("#datatable").refresh();
	}
	//保存
	function save() {
		if (!$("form").valid()) {
			return false;
		}
		var data = $("#dataForm").getData();
		data.tpnzl_id='${param.tpnzl_id}';
		
		$.call("hospital_common.tpn.rule.insertTpnMX", data, function(rtn) {
			if (rtn) {
				$("#datatable").refresh();
			}
		});
	}
	//新增
	function add(){
		 $("#dataForm").slideToggle("slow");
	}
	//停用、启用
	function changeState2(id,state) {
		if (state == 1){
			state = 0;
		}else{
			state = 1;
		}
		$.call("hospital_common.tpn.rule.updateByState2",{"id": id, "state": state},function(rtn){
			query();
		})
	}
	
	//删除
	function del(id) {
		ajax: $.call("hospital_common.tpn.rule.deleteRuleMX", {
			"id" : id
		}, function(s) {
			query();
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
