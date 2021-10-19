<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑说明书信息" ismodal="true">
	<s:row>
		<s:form id="form" label="说明书信息">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon=""></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<s:row>
					<c:if test="${not empty param.sys_drug_code}">
						<s:textfield label="药品编号" name="sys_drug_code" value="" cols="1" readonly="true">${sys_drug_code }</s:textfield>
					</c:if>
					<c:if test="${empty param.sys_drug_code}">
						<s:textfield label="药品编号" name="sys_drug_code" required="true" cols="1" value="" ></s:textfield>
					</c:if>
					<s:textfield label="中文名" name="name_cn" value="" cols="1" required="true">${name_cn }</s:textfield>
				</s:row>
				<s:row>
					<s:textfield label="英文名" name="name_en" value="" cols="1" >${name_en }</s:textfield>
					<s:textfield label="通用名" name="name_generic" value="" cols="1">${name_generic }</s:textfield>
				</s:row>
				<s:row>
					<s:textarea label="生产厂家" name="manufacturer" value="" cols="3" required="true">${manufacturer }</s:textarea>
				</s:row>
				<s:row>
					<s:textfield label="生产厂家简称" name="manufacturer_short_name" value="" cols="1">${manufacturer_short_name }</s:textfield>
					<s:textfield label="生产地址" name="manufacturer_address" value="" cols="1">${manufacturer_address }</s:textfield>
					<s:textfield label="生产厂家联系电话" name="manufacturer_tel" value="" cols="1">${manufacturer_tel }</s:textfield>
				</s:row>
				<s:row>
					<s:radio label="OTC类型" name="otc_type" value="${otc_type }" cols="2">
						<s:option value="甲类" label="甲类"></s:option>
						<s:option value="乙类" label="乙类"></s:option>
						<s:option value="处方药" label="处方药"></s:option>
					</s:radio>
					<s:radio label="医保类型" name="medical_insurance_type" value="${medical_insurance_type }" cols="1">
						<s:option value="甲类" label="甲类"></s:option>
						<s:option value="乙类" label="乙类"></s:option>
					</s:radio>
				</s:row>
				<s:row>
					<s:switch label="是否基药" name="is_essential" value="${is_essential }" onvalue="是" offvalue="否" ontext="是" offtext="否" cols="1" ></s:switch>
					<s:switch label="是否外用药" name="is_external" value="${is_external }" onvalue="是" offvalue="否" ontext="是" offtext="否" cols="1" ></s:switch>
					<s:switch label="是否中药" name="is_chinese_medicine" value="${is_chinese_medicine }" onvalue="是" offvalue="否" ontext="是" offtext="否" cols="1" ></s:switch>
				</s:row>
				<s:row>
					<s:radio label="中药保护品种级别" name="zb_type" value="${zb_type }" cols="1">
						<s:option value="一级" label="一级"></s:option>
						<s:option value="二级" label="二级"></s:option>
					</s:radio>
					<s:switch label="条码是否注册" name="is_gs1" value="${is_gs1 }" onvalue="是" offvalue="否" ontext="是" offtext="否" cols="1" ></s:switch>
				</s:row>
				<s:row>
					<s:textfield label="批准文号" name="approval_number" value="" cols="1">${approval_number }</s:textfield>
					<s:textfield label="批准日期" name="approval_date" value="" cols="1">${approval_date }</s:textfield>
				</s:row>
				<s:row>
					<s:textfield label="剂型" name="form" value="" cols="1">${form }</s:textfield>
					<s:textfield label="规格" name="spec" value="" cols="1">${spec }</s:textfield>
					<s:textfield label="有效期(月)" name="effective_term" value="" cols="1">${effective_term }</s:textfield>
				</s:row>
				<s:row>
					<s:textfield label="包装规格" name="packing" value="" cols="1">${packing }</s:textfield>
					<s:textfield label="执行标准" name="standard_exe" value="" cols="1">${standard_exe }</s:textfield>
					<s:textfield label="分装企业" name="sub_manufacturer" value="" cols="1">${sub_manufacturer }</s:textfield>
				</s:row>
				<s:row>
					<s:textfield label="进口药品注册证号" name="drugs_registration_imported" value="" cols="1">${drugs_registration_imported }</s:textfield>
					<s:textfield label="医药产品注册证号" name="products_registration" value="" cols="1">${products_registration }</s:textfield>
					<s:textfield label="输入码" name="input_code" value="" cols="1">${input_code }</s:textfield>
				</s:row>
				<s:row>
					<s:textfield label="贮藏条件" name="storage_condition" value="" cols="1">${storage_condition }</s:textfield>
					<s:textfield label="药品状态" name="durg_state" value="" cols="2">${durg_state }</s:textfield>
				</s:row>
				<s:row>
					<s:textfield label="说明书HTNL" name="html" value="" cols="1">${html }</s:textfield>
					<s:textfield label="FDA妊娠药物分级" name="fdanotes_id" value="" cols="2">${fdanotes_id }</s:textfield>
				</s:row>
				<s:row>
					<s:textarea label="用法用量" name="usage_dosage" value="" cols="3">${usage_dosage }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="适应症" name="indications" value="" cols="3">${indications }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="成分" name="ingredients" value="" cols="3">${ingredients }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="功能主治" name="attending" value="" cols="3">${attending }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="不良反应" name="adverse_reaction" value="" cols="3">${adverse_reaction }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="禁忌" name="taboo" value="" cols="3">${taboo }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="注意事项" name="attentions" value="" cols="3">${attentions }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="药物相互作用" name="interaction" value="" cols="3">${interaction }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="特殊人群用药" name="special_crowd" value="" cols="3">${special_crowd }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="药理作用" name="pharmacologic" value="" cols="3">${pharmacologic }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="警告" name="warn" value="" cols="3">${warn }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="孕妇及哺乳期妇女用药" name="drug_pregnant_lactation" value="" cols="3">${drug_pregnant_lactation }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="儿童用药" name="drug_children" value="" cols="3">${drug_children }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="老人用药" name="drug_elderly" value="" cols="3">${drug_elderly }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="用药过量" name="drug_overdose" value="" cols="3">${drug_overdose }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="药理毒理" name="pharmacology_toxicology" value="" cols="3">${pharmacology_toxicology }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="药代动力学" name="pharmacokinetics" value="" cols="3">${pharmacokinetics }</s:textarea>
				</s:row>
				<s:row>
					<s:textfield label="分子式" name="molecular_formula" value="" cols="1">${molecular_formula }</s:textfield>
					<s:textfield label="分子量" name="molecular_weight" value="" cols="1">${molecular_weight }</s:textfield>
				</s:row>
				<s:row>
					<s:textarea label="其余说明" name="other_msg" value="" cols="3">${other_msg }</s:textarea>
				</s:row>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">

	function save(){
		if (!$("form").valid()){
    		return false;
    	}
		var data=$("#form").getData();
		if('${param.sys_drug_code}'!=null&&typeof('${param.sys_drug_code}')!='undefind'&&'${param.sys_drug_code}'!=''){
			$.call("ipc.specification.update",data,function(s){
	    		$.closeModal(s);
	    	});
		}else{
			$.call("ipc.specification.isHas",{"sys_drug_code":data.sys_drug_code},function(rtn){
				if(rtn.resultset.length<1){
					data.owner_create=1;
	    			$.call("ipc.specification.insert",data,function(s){
	    	    		$.closeModal(s);
	    	    	});
	    		}else{
	    			$.message("药品编号已存在，请重新输入!");
	    			$("input[name='sys_drug_code']").val("");
	    		} 
			});
		}
	}

	//取消
    function cancel(){
    	$.closeModal(false);
    }
</script>