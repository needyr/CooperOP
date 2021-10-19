<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="药品说明书校验" disloggedin="true">
	<s:row>
		<s:form label="筛选条件" id="tj-form">
			<s:row>
				<s:textfield name="key" label="关键字" placeholder="请输入药品编号，名称，或输入码" cols="1"></s:textfield>
				<s:select label="类型" name="drug_indicator" value="all" cols="1">
					<s:option value="all" label="全部"></s:option>
					<c:forEach items="${types}" var="t">
						<s:option value="${t.drug_indicator}" label="${t.drug_indicator}"></s:option>
					</c:forEach>
				</s:select>
				<s:radio  label="有无说明书" name="is_had" cols="1" value="all" style="border: 0">
					<s:option value="all" label="全部" ></s:option>
					<s:option value="1" label="有"></s:option>
					<s:option value="0" label="无"></s:option>
				</s:radio>
			</s:row>
			<s:row>
				<s:radio  label="有无库存" name="is_kc" cols="1" value="all" style="border: 0">
					<s:option value="all" label="全部" ></s:option>
					<s:option value="1" label="有"></s:option>
					<s:option value="0" label="无"></s:option>
				</s:radio>
				<s:radio  label="状态" name="is_verify" cols="2" value="all" style="border: 0">
					<s:option value="all" label="全部" ></s:option>
					<s:option value="1" label="正确"></s:option>
					<s:option value="2" label="配错"></s:option>
					<s:option value="3" label="其他错误"></s:option>
					<s:option value="0" label="未校验"></s:option>
				</s:radio>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="院内药品信息" autoload="false" action="hospital_common.dictdrug.queryHPDrug"  >
			<s:toolbar>
				<s:button label="查询" icon="fa fa-search" onclick="query();"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="drug_code" label="药品编号"  ></s:table.field>
				<s:table.field name="drug_name" label="药品名称" datatype="script">
					var spk = record.sys_drug_code;
					var info = '';
					info = record.drug_name +' | ' + record.druggg+' | ' + record.drug_unit+' | '+ record.shengccj+' | ' + record.pizhwh;
					var str = '';
					if(spk){
						spk = '<a onclick="drug(\''+ info +'\', \''+record.drug_code +'\')">'+ record.drug_name +'</a>'
					}else{
						spk = record.drug_name;
					}
					return spk;
				</s:table.field>
				<s:table.field name="druggg" label="规格"  ></s:table.field>
				<s:table.field name="drug_unit" label="单位"  ></s:table.field>
				<s:table.field name="shengccj" label="生产厂家"  ></s:table.field>
				<s:table.field name="pizhwh" label="批准文号"  ></s:table.field>
				<s:table.field name="drug_indicator" label="药品类型"  ></s:table.field>
				<s:table.field name="usejlgg" label="使用剂量" datatype="script">
					return parseFloat(record.usejlgg) +record.use_dw;
				</s:table.field>
				<s:table.field name="is_verify" label="校验状态" datatype="script" width="200px">
					if(record.is_verify == 1){
						return '<font style="color: green;">正确</font>';
					}else if(record.is_verify == 0){
						return '<font style="color: #b9b6b6;">未校验</font>';
					}else if(record.is_verify == 2){
						return '<font style="color: red;">配错</font>';
					}else if(record.is_verify == 3){
						var str = record.verify_des;
						if(str){
							var len = str.length;
							if(len >= 32){
								str = record.verify_des.substring(0,31) + '...';
							}
							return '<font style="color: #b79a03;" title="'+record.verify_des+'" >['+str +']</font>';
						}else{
							return '<font style="color: #b79a03;">其他错误</font>';
						}
						
					}
				</s:table.field>
				<s:table.field name="verify_user" label="校验人" datatype="script" width="80px">
					var verify_user = record.verify_user;
					if(verify_user){
						return verify_user.substring(0, verify_user.indexOf('|'));
					}else{
						return '';
					}
				</s:table.field>
				<s:table.field name="caozuo" label="操作[标记]" datatype="template" width="155">
					<a title="说明书信息与药品一致" onclick="update('$[p_key]', 1, '')">正确</a> |
					<a title="说明书信息与药品完全不匹配" onclick="update('$[p_key]', 2, '')">配错</a> |
					<a title="添加文字说明" onclick="update('$[p_key]', 3, '$[verify_des]')">其他错误</a> |
					<a title="重置为未校验" onclick="update('$[p_key]', 0, '')">重置</a>
					<!-- <a>说明书</a> -->
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
	<div id="des" style=" height:185px; width:350px; margin: 5px;">
		<s:row>
			<form id="desform">
				<s:textarea name="q_des" cols="3" rows="6" required="true" ></s:textarea>
				<div id="sur" style="margin-left: 160px; margin-top: 10px;">
					<s:button label="取消" icon="fa fa-times" onclick="closeDiv();"></s:button>
					<s:button label="确认" icon="fa fa-check" onclick="beSure();"></s:button>
				</div>
				
			</form>
		</s:row>
	</div>
</s:page>
<script type="text/javascript">
$(function(){
	$('#des').hide();
	query();
});
var	id;
var udata = {};

function query(){
	var qdata=$("#tj-form").getData();
	$("#datatable").params(qdata);
	start = $("#datatable").dataTable().fnSettings()._iDisplayStart; 
	total = $("#datatable").dataTable().fnSettings().fnRecordsDisplay();
	var p = $("#datatable").DataTable().page();
	if((total-start)==1){
		if (start > 0) {
			$("#datatable").refresh_table(p-1);
		}else{
			$("#datatable").refresh();
		}
	}else{
		$("#datatable").refresh_table(p);
	}
}

function drug(info, drug_code){
	$.modal("instruction.html",info ,{
		width:"90%",
		height:"90%",
		drug_code: drug_code,
		callback : function(e){
		}
	});
}

function update(p_key ,verify, des){
	udata.p_key = p_key;
	udata.is_verify = verify;
	udata.verify_des = des;
	if(verify == 3){
		id = layer.open({
			type: 1,
			title: "情况说明",
			area: ['360px', '237px'], //宽高
			content: $("#des")
		});
		$('[name="q_des"]').setData(des);
	}else{
		$.call('hospital_common.dictdrug.verify', udata, function(){
			query();
		});
	}
}

function beSure(){
	if (!$("form").valid()){
		return false;
	}
	var _des = $('[name="q_des"]').val();
	udata.verify_des = _des.replace(/\n/g, '');
	$.call('hospital_common.dictdrug.verify', udata, function(){
		query();
	});
	$('[name="q_des"]').setData('');
	layer.close(id);
}

function closeDiv(){
	$('[name="q_des"]').setData('');
	layer.close(id);
}
</script>