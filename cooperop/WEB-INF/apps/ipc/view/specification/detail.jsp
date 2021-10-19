<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="说明书详细信息" ismodal="true">
	<s:row>
		<s:form id="form" label="说明书详细信息">
			<s:row>
				<s:row>
					<%-- <s:radio label="是否自己创建" name="owner_create" value="${owner_create }" cols="1" islabel="true">
						<s:option value="0" label="汇药通同步"></s:option>
						<s:option value="1" label="自己创建"></s:option>
					</s:radio> --%>
					<s:textfield label="药品编号" name="sys_drug_code" value="" cols="1" islabel="true">${sys_drug_code }</s:textfield>
					<s:textfield label="中文名" name="name_cn" value="" cols="1" islabel="true">${name_cn }</s:textfield>
				</s:row>
				<s:row>
					<s:textfield label="英文名" name="name_en" value="" cols="1" islabel="true">${name_en }</s:textfield>
					<s:textfield label="通用名" name="name_generic" value="" cols="1" islabel="true">${name_generic }</s:textfield>
				</s:row>
				<s:row>
					<s:textarea label="生产厂家" name="manufacturer" value="" cols="3" islabel="true">${manufacturer }</s:textarea>
				</s:row>
				<s:row>
					<s:textfield label="生产厂家简称" name="manufacturer_short_name" value="" cols="1" islabel="true">${manufacturer_short_name }</s:textfield>
					<s:textfield label="生产地址" name="manufacturer_address" value="" cols="1" islabel="true">${manufacturer_address }</s:textfield>
					<s:textfield label="生产厂家联系电话" name="manufacturer_tel" value="" cols="1" islabel="true">${manufacturer_tel }</s:textfield>
				</s:row>
				<s:row>
					<s:textfield label="OCT类型" name="otc_type" value="" cols="1" islabel="true">${otc_type }</s:textfield>
					<s:textfield label="医保类型" name="medical_insurance_type" value="" cols="1" islabel="true">${medical_insurance_type }</s:textfield>
					<s:textfield label="是否基药" name="is_essential" value="" cols="1" islabel="true">${is_essential }</s:textfield>
				</s:row>
				<s:row>
					<s:textfield label="是否外用药" name="is_external" value="" cols="1" islabel="true">${is_external }</s:textfield>
					<s:textfield label="是否中药" name="is_chinese_medicine" value="" cols="1" islabel="true">${is_chinese_medicine }</s:textfield>
					<s:textfield label="中药保护品种级别" name="zb_type" value="" cols="1" islabel="true">${zb_type }</s:textfield>
				</s:row>
				<s:row>
					<s:textfield label="条码是否注册" name="is_gs1" value="" cols="1" islabel="true">${is_gs1 }</s:textfield>
					<s:textfield label="批准文号" name="approval_number" value="" cols="1" islabel="true">${approval_number }</s:textfield>
					<s:textfield label="批准日期" name="approval_date" value="" cols="1" islabel="true">${approval_date }</s:textfield>
				</s:row>
				<s:row>
					<s:textfield label="剂型" name="form" value="" cols="1" islabel="true">${form }</s:textfield>
					<s:textfield label="规格" name="spec" value="" cols="1" islabel="true">${spec }</s:textfield>
					<s:textfield label="有效期(月)" name="effective_term" value="" cols="1" islabel="true">${effective_term }</s:textfield>
				</s:row>
				<s:row>
					<s:textfield label="包装规格" name="packing" value="" cols="1" islabel="true">${packing }</s:textfield>
					<s:textfield label="执行标准" name="standard_exe" value="" cols="1" islabel="true">${standard_exe }</s:textfield>
					<s:textfield label="分装企业" name="sub_manufacturer" value="" cols="1" islabel="true">${sub_manufacturer }</s:textfield>
				</s:row>
				<s:row>
					<s:textfield label="进口药品注册证号" name="drugs_registration_imported" value="" cols="1" islabel="true">${drugs_registration_imported }</s:textfield>
					<s:textfield label="医药产品注册证号" name="products_registration" value="" cols="1" islabel="true">${products_registration }</s:textfield>
					<s:textfield label="输入码" name="input_code" value="" cols="1" islabel="true">${input_code }</s:textfield>
				</s:row>
				<s:row>
					<s:textfield label="贮藏条件" name="storage_condition" value="" cols="1" islabel="true">${storage_condition }</s:textfield>
					<s:textfield label="药品状态" name="drug_state" value="" cols="2" islabel="true">${drug_state }</s:textfield>
				</s:row>
				<s:row>
					<s:textfield label="说明书HTNL" name="html" value="" cols="1" islabel="true">${html }</s:textfield>
					<s:textfield label="FDA妊娠药物分级" name="fdanotes_id" value="" cols="2" islabel="true">${fdanotes_id }</s:textfield>
				</s:row>
				<s:row>
					<s:textarea label="用法用量" name="usage_dosage" value="" cols="3" islabel="true">${usage_dosage }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="适应症" name="indications" value="" cols="3" islabel="true">${indications }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="成分" name="ingredients" value="" cols="3" islabel="true">${ingredients }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="功能主治" name="attending" value="" cols="3" islabel="true">${attending }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="不良反应" name="adverse_reaction" value="" cols="3" islabel="true">${adverse_reaction }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="禁忌" name="taboo" value="" cols="3" islabel="true">${taboo }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="注意事项" name="attentions" value="" cols="3" islabel="true">${attentions }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="药物相互作用" name="interaction" value="" cols="3" islabel="true">${interaction }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="特殊人群用药" name="special_crowd" value="" cols="3" islabel="true">${special_crowd }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="药理作用" name="pharmacologic" value="" cols="3" islabel="true">${pharmacologic }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="警告" name="warn" value="" cols="3" islabel="true">${warn }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="孕妇及哺乳期妇女用药" name="drug_pregnant_lactationn" value="" cols="3" islabel="true">${drug_pregnant_lactation }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="儿童用药" name="drug_children" value="" cols="3" islabel="true">${drug_children }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="老人用药" name="drug_elderly" value="" cols="3" islabel="true">${drug_elderly }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="用药过量" name="drug_overdose" value="" cols="3" islabel="true">${drug_overdose }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="药理毒理" name="pharmacology_toxicology" value="" cols="3" islabel="true">${pharmacology_toxicology }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="药代动力学" name="pharmacokinetics" value="" cols="3" islabel="true">${pharmacokinetics }</s:textarea>
				</s:row>
				<s:row>
					<s:textfield label="分子式" name="molecular_formula" value="" cols="1" islabel="true">${molecular_formula }</s:textfield>
					<s:textfield label="分子量" name="molecular_weight" value="" cols="1" islabel="true">${molecular_weight }</s:textfield>
					<s:textfield label="修订日期" name="revision_date" value="" cols="1" islabel="true">${revision_date }</s:textfield>
				</s:row>
				<s:row>
					<s:textarea label="其余说明" name="other_msg" value="" cols="3" islabel="true">${other_msg }</s:textarea>
				</s:row>
			</s:row>
		</s:form>
	</s:row>
</s:page>