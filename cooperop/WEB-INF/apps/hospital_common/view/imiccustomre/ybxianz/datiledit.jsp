<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="项目限专规则" ismodal="true">
	<s:row>
		<s:form id="form" label="项目限专规则">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon=""></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<s:switch label="是否启用" value="${empty state? 1: state}" name="state" onvalue="1" offvalue="0"  ontext="启用" offtext="停用"></s:switch>
				<%-- <s:select label="和其他行的条件" name="tiaojian"  value="${tiaojian}" >
					<s:option value="and" label="and" ></s:option>
					<s:option value="or" label="or"></s:option>
				</s:select> --%>
				<s:autocomplete  id="" label="问题分类" name="apa_check_sorts_id" action="hospital_common.auditset.checksort.queryListByImic" limit="10" editable="false" cols="1" text="${thirdt_name}" value="${apa_check_sorts_id}" required="true">
					<s:option value="$[thirdt_code]" label="$[thirdt_name]">
						$[thirdt_name]
					</s:option>
				</s:autocomplete>
				<s:autocomplete  id="check_level" label="问题等级" name="sys_check_level" action="hospital_common.imiccustomre.ybxianz.queryListByImic" limit="10" editable="false" cols="1" text="${sys_check_level_name}" value="${sys_check_level}" required="true">
					<s:option value="$[sys_check_level]" label="$[sys_check_level_name]">
						<span>$[sys_check_level]</span>&nbsp;&nbsp;&nbsp;
						<span>$[sys_check_level_name]</span>
					</s:option>
				</s:autocomplete>
			</s:row>
			<%-- <s:row>
				<s:select label="类型" name="d_type"  value="${d_type}" >
					<s:option value="1" label="住院" ></s:option>
					<s:option value="2" label="门诊"></s:option>
				</s:select>
				<s:autocomplete label="医保体系" name="interface_type" action="hospital_common.imiccustomre.dictYBType" editable="false" text="${interface_type_name}" value="${empty interface_type? '全部': interface_type}" required="true">
					<s:option value="$[interface_type]" label="$[interface_type_name]">
						$[interface_type_name]
					</s:option>
				</s:autocomplete>
			</s:row> --%>
			<s:row>
				<s:textfield label="年龄段" name="nianl_c"  cols="1" disabled="disabled" value="${nianl_c}" dbl_action='zl_select_yb_xianz_01,yb_shengfangzl_xianz'></s:textfield>
				<s:textfield label="年龄范围" name="nianl_start"  cols="1" value="${nianl_start}"></s:textfield>
				<s:textfield label="至" name="nianl_end"  cols="1" value="${nianl_end}"></s:textfield>
			</s:row>
			<%-- <s:row>
				<s:textfield label="项目编号" name="xmbh"  cols="1" disabled="disabled" value="${xmbh}" dbl_action='zl_select_yb_xianz_02,yb_shengfangzl_xianz'></s:textfield>
				<s:textfield label="项目名称" disabled="disabled" name="xmmch"  cols="1" value="${xmmch}"></s:textfield>
				<input  hidden="true"  name="xmid"  value="${xmid}">
				<input type="hidden" name="fdname" value="${fdname}" />
				<s:textfield label="公式"  disabled="disabled" name="formul"  cols="1" value="${formul_name}" dbl_action='zl_select_yb_xianz_03,yb_shengfangzl_xianz'></s:textfield>
			</s:row> --%>
			<%-- <s:row>
				<s:textfield label="值" name="value"  cols="2" value="${value}"></s:textfield>
				<s:switch label="违规后是否可以转自费" value="${is_to_zf}" name="is_to_zf" onvalue="1" offvalue="0"  ontext="是" offtext="否"></s:switch>
			</s:row> --%>
			<s:row>
				<input type="hidden" name="charge_type_code" value="${charge_type_code}">
				<s:textfield cols="2"  id="charge_type_name" name="charge_type_name" label="费别" value="${charge_type_name}"  placeholder="单击选择费别"></s:textfield>
			</s:row>
			<s:row>
				<input type="hidden" name="dept_code" value="${dept_code}">
				<s:textfield cols="2"   id="dept_name" name="dept_name" label="科室" value="${dept_name}"  placeholder="单击选择科室"></s:textfield>
			</s:row>
			<s:row>
				<input type="hidden" name="doctor_no" value="${doctor_no}">
				<s:textfield cols="2"   id="doctor_name" name="doctor_name" label="医生" value="${doctor_name}"   placeholder="单击选择医生"></s:textfield>
			</s:row>
			<s:row>
				<input type="hidden" name="patient_id" value="${patient_id}">
				<s:textfield cols="2" name="patient_name" id="patient_name" label="患者" value="${patient_name}"   placeholder="单击选择患者"></s:textfield>
			</s:row>
			<%-- <s:row>
				<s:autocomplete  label="转自费流程" name="wf_process_id" action="hospital_common.imiccustomre.ybxianz.queryWfprocess"  editable="false" cols="2" text="${display_name}" value="${wf_process_id}" >
					<s:option value="$[id]" label="$[display_name]">
						$[display_name]
					</s:option>
				</s:autocomplete>
			</s:row> --%>
			<s:row>
				<s:textarea label="警示消息" name="nwarn_message" cols="4" placeholder="请输入警示提示消息内容"  required="true" value="">${nwarn_message}</s:textarea>
			</s:row>
			<s:row>
				<s:textfield label="备注" name="beizhu"  cols="4" value="${beizhu}"></s:textfield>
				<input type="hidden" name="item_code" id="item_code" value="${item_code}"></input>
			</s:row>
		</s:form>
		<s:table id="diat" label="诊断信息" autoload="false" action="hospital_common.imiccustomre.ybxianz.queryDias" sort="true">
			<s:toolbar>
				<s:button label="新增" onclick="addDia();"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="id" label="编码"></s:table.field>
				<s:table.field name="xmmch" label="项目名称"></s:table.field>
				<s:table.field name="formul_name" label="公式"></s:table.field>
				<s:table.field name="value" label="值"></s:table.field>
				<s:table.field name="diagnosis_code" label="诊断代码"></s:table.field>
				<s:table.field name="diagnosis_name" label="诊断名称"></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="60px">
					<a href="javascript:void(0)" onclick="updateDia('$[id]')">修改</a> | 
					<a href="javascript:void(0)" onclick="delDia('$[id]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		var item_code = '${param.item_code}'
		if(item_code){
			$("#item_code").val('${param.item_code}');
			$("#check_level")[0].value="";
		}
		$("#patient_name").css("cursor","pointer");
		$("#doctor_name").css("cursor","pointer");
		$("#dept_name").css("cursor","pointer");
		$("#charge_type_name").css("cursor","pointer");
		queryDia();
	});
	
	function save(){
		var patrn = /[`~!#$^&_\-+=<>?"{}|,\/;'\·~！#￥……&（）——\-+={}|《》？“”【】、；‘’，。、]/im;  
		var str = $('[name=value]').val();
		if (patrn.test(str)) {// 如果包含特殊字符
	         $.message('请勿在值中添加特殊字符,请检查!');
	    }else{
			if (!$("form").valid()){
		   		return false;
		   	}
			var sdata=$("#form").getData();
			sdata.id='${param.id}';
			sdata.interface_type_name = $('[name="interface_type"]').val();
			if('${sort_name}' == sdata.apa_check_sorts_id){
				sdata.apa_check_sorts_id = '${apa_check_sorts_id}';
			} 
			if('${sys_check_level_name}' == sdata.sys_check_level){
				sdata.sys_check_level = '${sys_check_level}';
			} 
			$.call("hospital_common.imiccustomre.ybxianz.save",sdata,function(s){
				$.closeModal(s);
	    	});
	    }
	};
	//取消
    function cancel(){
    	$.closeModal(false);
    };
	
  //打开药品说明书
	function getDrugsms(){
		var drugcode = '${param.drug_code}';
		$.modal("/w/hospital_common/additional/sysinstruction.html?drug_code="+drugcode,"查看药品说明书",{
	        width:"70%",
	        height:"100%",
	        callback : function(e){
	        }
		})
		
	}
	/* 费别 */
	$('[name=charge_type_name]').click(function(){
		var code = $('[name=charge_type_code]').val();
		$.modal("/w/ipc/sample/choose/feibie.html", "添加费别", {
			height: "550px",
			width: "60%",
			code: code,
			maxmin: false,
			callback : function(rtn) {
				if(rtn){
					console.log(rtn)
					var name = rtn.name.toString();
					var code = rtn.code.toString();
					$('[name=charge_type_name]').val(name);
					$('[name=charge_type_code]').val(code);
				}
		    }
		})
	})
	/* 科室 */
	$('[name=dept_name]').click(function(){
		var code = $('[name=dept_code]').val();
		$.modal("/w/hospital_common/customreview/choose/department.html", "添加科室", {
			height: "550px",
			width: "60%",
			code: code,
			maxmin: false,
			callback : function(rtn) {
				if(rtn){
					var name = rtn.name.toString();
					var code = rtn.code.toString();
					$('[name=dept_name]').val(name);
					$('[name=dept_code]').val(code);
				}
		    }
		})
	})
	/* 医生 */
	$('[name=doctor_name]').click(function(){
		var code = $('[name=doctor_no]').val();
		$.modal("/w/hospital_common/customreview/choose/doctor.html", "添加医生", {
			height: "550px",
			width: "50%",
			code: code,
			maxmin: false,
			callback : function(rtn) {
				if(rtn){
					var name = rtn.name.toString();
					var code = rtn.code.toString();
					$('[name=doctor_name]').val(name);
					$('[name=doctor_no]').val(code);
				}
		    }
		})
	});
	/* 患者 */
	$('[name=patient_name]').click(function(){
		var code = $('[name=patient_id]').val();
		$.modal("/w/hospital_common/customreview/choose/patient.html", "添加患者", {
			height: "550px",
			width: "50%",
			code: code,
			maxmin: false,
			callback : function(rtn) {
				if(rtn){
					var name = rtn.name.toString();
					var code = rtn.code.toString();
					$('[name=patient_name]').val(name);
					$('[name=patient_id]').val(code);
				}
		    }
		})
	});
		
	function addDia(){
		if('${param.id}' == ''){
			$.message('请先填写并保存[项目限专规则]!');
			return;
		}

		$.modal("diainfo.html","新增",{
			width:"65%",
			height:"55%",
			parent_id: '${param.id}',
			item_code: '${param.item_code}',
			callback : function(e){
				queryDia();
			}
		});
	}
	
	function updateDia(id){
		$.modal("diainfo.html","修改",{
			width:"65%",
			height:"55%",
			id: id,
			parent_id: '${param.id}',
			callback : function(e){
				queryDia();
			}
		});
	}
	
	function queryDia(){
		$("#diat").params({parent_id: '${param.id}'});
		$("#diat").refresh();
	}
	
	function delDia(id){
		$.confirm("是否确认删除，删除后无法恢复！",function callback(e){
			if(e==true){
				$.call("hospital_common.imiccustomre.ybxianz.deleteDia",{"id":id},function(e){
					queryDia();
		    	}); 
			}
		});
	}
</script>