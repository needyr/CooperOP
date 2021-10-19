<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="说明书管理" ismodal="true">
	<s:row>
		<s:form border="0" id="form" label="快速查找">
			<s:row>
				<s:textfield label="药品编号" name="sys_drug_code" placeholder="请输入药品编号"></s:textfield>
				<s:textfield label="批准文号" name="approval_number" placeholder="请输入批准文号"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="快速查找" name="filter" placeholder="请输入药品名称、生产厂家" cols="2"></s:textfield>
				<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="说明书管理" autoload="false" action="hospital_common.customspecification.query" sort="true" >
			<s:toolbar>
				<s:button icon="" label="新增说明书" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="sys_drug_code" label="药品编号" datatype="" sort="true"></s:table.field>
				<s:table.field name="name_cn" label="药品名称" datatype="script" sort="true">
					if(record.name_en){
						return record.name_cn+"["+record.name_en+"]"
					}
					return record.name_cn
				</s:table.field>
				<s:table.field name="packing" label="包装规格" sort="true"></s:table.field>
				<s:table.field name="approval_number" label="批准文号" width="250" sort="true"></s:table.field>
				<s:table.field name="manufacturer_short_name" label="生产厂家简称" datatype="" sort="true"></s:table.field>
				<s:table.field name="operation_log" label="上次操作" datatype="String" sort="true"></s:table.field>
				<%-- <s:table.field name="input_code" label="输入码" datatype="" ></s:table.field>
				<s:table.field name="name_en" label="英文名" datatype="" ></s:table.field>
				<s:table.field name="approval_date" label="批准日期" datatype="" ></s:table.field>
				<s:table.field name="standard_exe" label="执行标准" datatype="" ></s:table.field>
				<s:table.field name="manufacturer_address" label="生产地址" datatype="" ></s:table.field>
				<s:table.field name="manufacturer_tel" label="生产厂家联系电话" datatype="" ></s:table.field>
				<s:table.field name="drugs_registration_imported" label="进口药品注册证号" datatype="" ></s:table.field>
				<s:table.field name="products_registration" label="医药产品品注册证号" datatype="" ></s:table.field>
				<s:table.field name="name_generic" label="通用名" datatype="" ></s:table.field>
				<s:table.field name="sub_manufacturer" label="分装企业" datatype="" ></s:table.field>
				<s:table.field name="manufacturer" label="生产厂家" datatype="" ></s:table.field>
				<s:table.field name="owner_create" label="是否自己创建" datatype="" ></s:table.field>
				<s:table.field name="name_en" label="英文名" datatype="" ></s:table.field> 
				<s:table.field name="name_generic" label="通用名" datatype="" ></s:table.field>
				<s:table.field name="otc_type" label="OCT类型" datatype="" ></s:table.field>
				<s:table.field name="medical_insurance_type" label="医保类型" datatype="" ></s:table.field>
				<s:table.field name="is_essential" label="是否基药" datatype="" ></s:table.field>
				<s:table.field name="is_external" label="是否外用药" datatype="" ></s:table.field>
				<s:table.field name="is_chinese_medicine" label="是否中药" datatype="" ></s:table.field>
				<s:table.field name="zb_type" label="中药保护等级" datatype="" ></s:table.field>
				<s:table.field name="form" label="剂型" datatype="" ></s:table.field>
				<s:table.field name="spec" label="规格" datatype="" ></s:table.field>
				<s:table.field name="indications" label="适应症" datatype="" ></s:table.field>
				<s:table.field name="effective_term" label="有效期" datatype="" ></s:table.field>
				<s:table.field name="storage_condition" label="贮藏条件" datatype="" ></s:table.field>
				<s:table.field name="ingredients" label="成分" datatype="" ></s:table.field>
				<s:table.field name="usage_dosage" label="用法用量" datatype="" ></s:table.field>
				<s:table.field name="attending" label="功能主治" datatype="" ></s:table.field>
				<s:table.field name="adverse_reaction" label="不良反应" datatype="" ></s:table.field>
				<s:table.field name="taboo" label="禁忌" datatype="" ></s:table.field>
				<s:table.field name="attentions" label="注意事项" datatype="" ></s:table.field>
				<s:table.field name="interaction" label="药物相互作用" datatype="" ></s:table.field>
				<s:table.field name="drug_state" label="药品状态" datatype="" ></s:table.field>
				<s:table.field name="drug_pregnant_lactation" label="孕妇及哺乳妇女用药" datatype="" ></s:table.field>
				<s:table.field name="drug_children" label="儿童用药" datatype="" ></s:table.field>
				<s:table.field name="drug_elderly" label="老年用药" datatype="" ></s:table.field>
				<s:table.field name="drug_overdose" label="用药过量" datatype="" ></s:table.field>
				<s:table.field name="standard_exe" label="执行标准" datatype="" ></s:table.field> --%>
				<s:table.field name="caozuo" label="操作" datatype="template" width="100px" >
					<a href="javascript:void(0)" onclick="detail('$[sys_drug_code]')">详情</a>
					<a href="javascript:void(0)" onclick="update('$[sys_drug_code]')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[sys_drug_code]')">删除</a>
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
		/* qdata.ts='${param.ts}';
		qdata.vs='${param.vs}';
		qdata.uid='${param.uid}'; */
		$("#datatable").params(qdata);
		$("#datatable").refresh();
	}
	
	function Add(){
		$.modal("edit.html","新增说明书信息",{
			width:"80%",
			height:"90%",
			callback : function(e){
				query();
			}
		});
	}
	
	function update(sys_drug_code){
		$.modal("edit.html","修改说明书信息",{
			width:"80%",
			height:"90%",
			sys_drug_code:sys_drug_code,
			callback : function(e){
				query();
			}
		});
	}
	
	function detail(sys_drug_code){
		$.modal("detail.html","查看说明书信息",{
			width:"80%",
			height:"90%",
			sys_drug_code:sys_drug_code,
			callback : function(e){
				query();
			}
		});
	}
	
	function Delete(sys_drug_code){
		$.confirm("是否确认删除？删除后无法恢复！",function callback(e){
			if(e==true){
				$.call("hospital_common.customspecification.delete",{"sys_drug_code":sys_drug_code},function (rtn){
					query();
				})
			}
		})	
	}
	
</script>