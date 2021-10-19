<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="" ismodal="true">
	<s:row>
		<s:form id="form" >
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon=""></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<c:if test="${ empty item_code}" >
					<s:autocomplete  id="ssks" label="编号" name="p_key" action="hospital_common.imiccustomre.querynotimicsc" limit="10" editable="false" cols="2" required="true" value="${item_code}">
						<s:option value="$[p_key]" label="$[p_key]">
							<span style="float:left;display:block;width:100px;">$[item_code]</span>
							<span style="float:left;display:block;width:250px;">$[item_name]</span>
							<span style="float:left;display:block;width:100px;">$[interface_type_name]</span>
						</s:option>
					</s:autocomplete>
				</c:if>
				<c:if test="${ not empty item_code}" >
					<s:autocomplete  id="ssks" label="项目编号" name="p_key" action="hospital_common.imiccustomre.querynotimicsc" limit="10" editable="false" cols="2" required="true" value="${item_code}">
						<s:option value="$[p_key]" label="$[p_key]">
							<span style="float:left;display:block;width:80px;">$[item_code]</span>
							<span style="float:left;display:block;width:200px;">$[item_name]</span>
							<span style="float:left;display:block;width:70px;">$[interface_type_name]</span>
						</s:option>
					</s:autocomplete>
				</c:if>
			</s:row>
			 <s:row>
				 <input name="id" type="hidden" value="${id}"/>
				 <input name="interface_type" type="hidden" value="${interface_type}"/>
				 <input name="item_code" type="hidden" value="${item_code}"/>
				 <s:textfield name="item_name" label="项目名称"  disabled="disabled" value="${item_name}"></s:textfield>
				 <s:textfield name="interface_type_name" label="医保类型" disabled="disabled" value="${interface_type_name}"></s:textfield>
				 <s:textfield name="item_spec" label="规格"  disabled="disabled" value="${item_spec}"></s:textfield>
				 <s:textfield name="item_units" label="单位"  disabled="disabled" value="${item_units}"></s:textfield>
				 <s:textfield name="shengccj" label="生产厂家"  disabled="disabled" value="${shengccj}"></s:textfield>
				 <s:textfield name="pizhwh" label="批准文号"  disabled="disabled" value="${pizhwh}"></s:textfield>
			</s:row>
			<s:row>
				<s:radio label=" " name="is_to_zf"  cols="1" value="${ empty is_to_zf ? 1 : is_to_zf}" >
					<s:option value="1" label="可以转自费"></s:option>
					<s:option value="0" label="不可转自费"></s:option>
				</s:radio>
			</s:row>
			<s:row>
				<s:autocomplete  label="转自费流程" id="wf_process_id" name="wf_process_id" action="hospital_common.imiccustomre.ybxianz.queryWfprocess"  limit="10"  editable="false" cols="2" text="${display_name}" value="${wf_process_id}" >
					<s:option value="$[id]" label="$[display_name]">
						$[display_name]
					</s:option>
				</s:autocomplete>
			</s:row>
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
			<s:row>
				<s:textfield cols="3" label="审查说明" name="wf_process_shuom" value="${wf_process_shuom}"   placeholder="该描述将会在审查结果里展示到给医生"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		$("#patient_name").css("cursor","pointer");
		$("#doctor_name").css("cursor","pointer");
		$("#dept_name").css("cursor","pointer");
		$("#charge_type_name").css("cursor","pointer");
		var id = '${param.id}'
		if(id != ''){
			$("input[name='p_key']").attr("disabled","disabled");
		}
		//（切换项目）
		$("#ssks").change(function(){
			getitem($(this).val());
		});
		
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
			$.call("hospital_common.imicitemflow.saveflow",sdata,function(s){
				$.closeModal(s);
	    	});
	    }
	};
	//取消
    function cancel(){
    	$.closeModal(false);
    };
    /* 新增根据item code去获取该项目信息  */ 
    function getitem(p_key){
    	$.call("hospital_common.imiccustomre.getforinsurvsprice",{"p_key":p_key},function(rtn){
    		if(rtn==null||typeof(rtn) == "undefined"){
    			$("input[name='item_code']").val("");
    			$("input[name='item_name']").val("");
    			$("input[name='interface_type']").val("");
    			$("input[name='interface_type_name']").val("");
    			$("input[name='item_spec']").val("");
    			$("input[name='item_units']").val("");
    			$("input[name='shengccj']").val("");
    			$("input[name='pizhwh']").val("");
    		}else{
    			$("input[name='item_code']").val(rtn.item_code);
    			$("input[name='item_name']").val(rtn.item_name);
    			$("input[name='interface_type']").val(rtn.interface_type);
    			$("input[name='interface_type_name']").val(rtn.interface_type_name);
    			$("input[name='item_spec']").val(rtn.item_spec);
    			$("input[name='item_units']").val(rtn.item_units);
    			$("input[name='shengccj']").val(rtn.shengccj);
    			$("input[name='pizhwh']").val(rtn.pizhwh);
    		}
    	}); 
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
    	$.modal("/w/ipc/sample/choose/department.html", "添加科室", {
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
    	$.modal("/w/ipc/sample/choose/doctor.html", "添加医生", {
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
    	$.modal("/w/ipc/sample/choose/patient.html", "添加患者", {
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
</script>