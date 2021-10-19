<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<style>
.form-horizontal .row-fluid>.cols, .form-horizontal .row-fluid>.cols1{
	width: 25% !important;
}
.form-horizontal .row-fluid>.cols1{
	width: 25% !important;
}
.form-horizontal .row-fluid>.cols2{
	width: 50% !important;
}
.form-horizontal .row-fluid>.cols3{
	width: 75% !important;
}
.form-horizontal .row-fluid>.cols4{
	width: 100% !important;
}
.bootstrap-switch {
  height: 30px;
}
.title {
	background-color: #000080;
    height: 30px;
    line-height: 30px;
    font-size: 14px;
    color: white;
    margin-bottom: 10px;
    display: inline-block;
    padding-left: 5px;
    padding-right: 5px;
    margin-top: 27px;
}

.portlet-title{
    position: fixed;
    background-color: #f5f5f6;
    width: calc(100% - 58px);
    z-index: 9999999999999999999999;
}
</style>
<s:page title="编辑用量问题" ismodal="true">
	<s:row>
		<s:form id="form" label="用量问题">
			<s:toolbar>
				<s:button label="药品说明书" onclick="getDrugsms()" icon=""></s:button>
				<s:button label="保存" onclick="save()" icon="glyphicon glyphicon-floppy-saved"></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row><div class="title">规则设定</div></s:row>
			<s:row>
				<c:if test="${empty is_audit}">
				<s:switch label="是否开启审查" value="1" name="is_audit" onvalue="1" offvalue="0" ontext="是" offtext="否" ></s:switch>
				</c:if>
				<c:if test="${not empty is_audit}">
				<s:switch label="是否开启审查" value="${is_audit}" name="is_audit" onvalue="1" offvalue="0" ontext="是" offtext="否" ></s:switch>
				</c:if>
				<s:select label="和其他行的条件" name="tiaojian" cols="1" value="${tiaojian}" >
					<s:option value="and" label="and"></s:option>
					<s:option value="or" label="or"></s:option>
				</s:select>
			</s:row>
			<s:row>
				<s:autocomplete  id="" label="问题分类" name="apa_check_sorts_id" action="hospital_common.auditset.checksort.queryListByIpc" limit="10" editable="false" cols="2" value="${sort_name}" required="true">
					<s:option value="$[sort_id]" label="$[sort_name]">
						$[sort_name]
					</s:option>
				</s:autocomplete>
				<s:autocomplete  id="" label="问题等级" name="sys_check_level" action="hospital_common.auditset.checklevel.queryListByIpc" limit="10" editable="false" cols="1" value="${sys_check_level_name}" required="true">
					<s:option value="$[sys_check_level]" label="$[sys_check_level_name]">
						$[sys_check_level_name]
					</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<input type="hidden" name="fdname" value="${fdname}" />
				<%-- <s:textfield label="" name=""  cols="3" value="" islabel="true"></s:textfield> --%>
				<s:textfield label="年龄段" name="nianl_c"  cols="1" disabled="disabled" value="${nianl_c}" dbl_action='zl_select_shengfyl_06,shengfangzl_yongl'></s:textfield>
				<%-- <c:if test="${not empty nianl_unit}">
				    <s:select name="nianl_unit" label="年龄单位" cols="1" value="${nianl_unit}">  
						<s:option label="岁" value="岁"></s:option>
						<s:option label="月" value="月"></s:option>
						<s:option label="天" value="天"></s:option>
					</s:select>
				</c:if>
				<c:if test="${empty nianl_unit}">
				    <s:select name="nianl_unit" label="年龄单位" cols="1" value="岁">  
						<s:option label="岁" value="岁"></s:option>
						<s:option label="月" value="月"></s:option>
						<s:option label="天" value="天"></s:option>
					</s:select>
				</c:if> --%>
				<s:textfield label="年龄范围" name="nianl_start"  cols="1" value="${nianl_start}"></s:textfield>
				<s:textfield label="至" name="nianl_end"  cols="1" value="${nianl_end}"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="体重段" name="weight_c"  cols="1" disabled="disabled" value="${weight_c}" dbl_action='zl_select_shengfangzl_weight,shengfangzl'></s:textfield>
				<c:if test="${not empty weight_unit}">
				<s:textfield label="体重单位" name="weight_unit"  cols="1" disabled="disabled" value="${weight_unit}" dbl_action=''></s:textfield>
				</c:if>
				<c:if test="${empty weight_unit}">
				<s:textfield label="体重单位" name="weight_unit"  cols="1" disabled="disabled" value="Kg" dbl_action=''></s:textfield>
				</c:if>
				<s:textfield label="体重范围" name="weight_start"  cols="1" value="${weight_start}"></s:textfield>
				<s:textfield label="至" name="weight_end"  cols="1" value="${weight_end}"></s:textfield>
				<%-- <s:select name="weight_end" label="体重单位" cols="1" value="${3}" action="">
					<s:option value="$[]" label="$[]"></s:option>
				</s:select> --%>
			</s:row>
			<%-- <s:row>
				<input type="hidden" name="dept_code_permit" value="${dept_code_permit}">
				<s:textfield cols="2"   id="dept_name_permit" name="dept_name_permit" label="科室白名单" value="${dept_name_permit}"  placeholder="单击选择科室，不选为全部科室"></s:textfield>
				<input type="hidden" name="dept_code_ban" value="${dept_code_ban}">
				<s:textfield cols="2"   id="dept_name_ban" name="dept_name_ban" label="科室黑名单" value="${dept_name_ban}"  placeholder="单击选择科室"></s:textfield>
			</s:row> --%>
			<s:row>
			    <s:textfield label="给药方式like" name="routename"  cols="2" disabled="disabled" value="${routename}" dbl_action='zl_select_shengfyl_07,shengfangzl_yongl' ></s:textfield>
			</s:row>
			<s:row>
				<input type="hidden" name="xmmch" value="${xmmch}"/>
				<input type="hidden" name="fdname" value="${fdname}"/>
				<%-- <s:textfield label="项目编号" name="xmbh"  cols="1" disabled="disabled" value="${xmbh}" ></s:textfield> --%>
				<%-- <s:textfield label="项目名称" disabled="disabled" name="xmmch"  cols="1" value="${xmmch}" dbl_action='zl_select_shengfyl_01,shengfangzl_yongl'></s:textfield> --%>
				<s:autocomplete action="hospital_common.customreview.sfyongl.shengfangzl_xm" cols="1" label="项目名称" name="xmbh" data-fd="${fdname}" limit="10" value="${xmbh}" text="${xmmch}" > 
					<s:option value="$[xmbh]" label="$[xmmch]"></s:option>
				</s:autocomplete>
				<s:textfield label="公式"  disabled="disabled" name="formul"  cols="1" value="${formul_name}" dbl_action='zl_select_shengfyl_02,shengfangzl_yongl'></s:textfield>
				<s:textfield label="值" name="value"  cols="1" value="${value}" ></s:textfield>
				<c:if test="${empty param.spbh}">
				<s:select name="value_unit" label="值单位" cols="1" value="${value_unit_default}">
					<s:option label="${value_unit_default}" value="${value_unit_default}"></s:option>
					<s:option label="喷" value="喷"></s:option>
					<s:option label="吸" value="吸"></s:option>
				</s:select>
				</c:if>
				<c:if test="${not empty param.spbh}">
				<s:select name="value_unit" label="值单位" cols="1" value="${value_unit}" >
					<s:option label="${value_unit_default}" value="${value_unit_default}"></s:option>
					<s:option label="喷" value="喷"></s:option>
					<s:option label="吸" value="吸"></s:option>
				</s:select>
				</c:if>
			</s:row>
			<s:row>
				<s:textarea label="或者计算公式" name="calculation_formula" cols="4" placeholder="请输入计算公式"  rows="" value="">${calculation_formula}</s:textarea>
			</s:row>
			<s:row>
				<s:textarea label="警示消息" name="nwarn_message" cols="4" placeholder="请输入警示提示消息内容"  rows="" value="">${nwarn_message}</s:textarea>
			</s:row>
			<s:row>
				<s:textfield label="备注" name="beizhu"  cols="4" value="${beizhu}"></s:textfield>
			</s:row>
			<s:row>
				<%-- <s:textfield label="商品编号" name="spbh" required="true" value="${param.spbh}" cols="1" disabled="disabled"></s:textfield> --%>
				<input name="spbh" value="${param.spbh}" hidden="true"/>
			</s:row>
			<s:row><div class="title">点评预判规则设定</div></s:row>
			<s:row>
				<s:switch label="是否开启预判" value="${is_antic}" name="is_antic" onvalue="1" offvalue="0" ontext="是" offtext="否" ></s:switch>
				<s:switch label="合理性" value="${comment_result}" name="comment_result" onvalue="1" offvalue="0" ontext="是" offtext="否" ></s:switch>
			</s:row>
			<s:row>
				<input type="hidden" name="comment_content_code" value="${comment_content_code}" >
				<div class="cols3">
				<label class="control-label">点评预判内容</label>
				<div class="control-content">
				<textarea style="height: 60px;" readonly="readonly" class="form-control" placeholder="双击选择"  name="comment_content" cols="4"  data-autosize-on="true">${comment_content}</textarea>
				</div>
				</div>
				<a onclick="_clean()">清空</a>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">

$(function(){
	$("[name='value_unit']").parent().parent().css('display','none');
	
	var xmmch = $("[name='xmmch']").val();
	if(xmmch && (xmmch == '单次用量' || xmmch == '全天用量' ||
			xmmch == '每公斤单次用量' || xmmch == '每公斤全天用量')){
		$("[name='value_unit']").parent().parent().css('display','block');
	}
	
	$("[name='xmbh']").change(function() {
		var sdata=$("#form").getData();
		var value = $("[name='xmbh']").val();
		$("[name='fdname']").val('');
		$("[name='xmmch']").val('');
		if(value){
			$.call('hospital_common.customreview.sfyongl.shengfangzl_xm',{xmbh:sdata.xmbh},function(rtn){
				if(rtn.resultset){
					$("[name='fdname']").val(rtn.resultset[0].fdname);
				}
				$("[name='xmmch']").val(value);
				if(value && (value == '单次用量' || value == '全天用量' ||
						value == '每公斤单次用量' || value == '每公斤全天用量')){
					$("[name='value_unit']").parent().parent().css('display','block');
				}else{
					$("[name='value_unit']").parent().parent().css('display','none');
				}
			},function(e){},{async:false})
		}
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
			sdata.id='${param.id}';
			if('${sort_name}' == sdata.apa_check_sorts_id){
				sdata.apa_check_sorts_id = '${apa_check_sorts_id}';
			} 
			if('${sys_check_level_name}' == sdata.sys_check_level){
				sdata.sys_check_level = '${sys_check_level}';
			} 
			$.call("hospital_common.customreview.sfyongl.save",sdata,function(s){
				$.closeModal(s);
	    	});
			if(sdata.id){
				$.call("hospital_common.dictdrug.updateCz", 
						{drug_code: '${param.spbh}', zdy_cz: "修改用量问题"}, function(){});
			}else{
				$.call("hospital_common.dictdrug.updateCz", 
						{drug_code: '${param.spbh}', zdy_cz: "新增用量问题"}, function(){});
			}
	     }
	}
	
	//取消
    function cancel(){
    	$.closeModal(false);
    }
	
  //打开药品说明书
	function getDrugsms(){
		var drugcode = '${param.spbh}';
		$.modal("/w/ipc/auditresult/instruction.html?his_drug_code="+drugcode,"查看药品说明书",{
	        width:"70%",
	        height:"100%",
	        callback : function(e){
	        }
		});
		
	};
	
	/* 科室 */
	$('[name=dept_name_permit]').click(function(){
		var code = $('[name=dept_code_permit]').val();
		//$.modal("/w/ipc/sample/choose/department.html", "添加科室", {
		$.modal("/w/hospital_common/customreview/choose/department.html", "添加科室", {
			height: "550px",
			width: "60%",
			code: code,
			maxmin: false,
			callback : function(rtn) {
				if(rtn){
					var name = rtn.name.toString();
					var code = rtn.code.toString();
					$('[name=dept_name_permit]').val(name);
					$('[name=dept_code_permit]').val(code);
				}
		    }
		})
	})
	
	$('[name=dept_name_ban]').click(function(){
		var code = $('[name=dept_code_ban]').val();
		//$.modal("/w/ipc/sample/choose/department.html", "添加科室", {
		$.modal("/w/hospital_common/customreview/choose/department.html", "添加科室", {
			height: "550px",
			width: "60%",
			code: code,
			maxmin: false,
			callback : function(rtn) {
				if(rtn){
					var name = rtn.name.toString();
					var code = rtn.code.toString();
					$('[name=dept_name_ban]').val(name);
					$('[name=dept_code_ban]').val(code);
				}
		    }
		})
	})
	
	$('[name=comment_content]').dblclick(function(){
		var data2= {} ;
		//data2.check_gx = _code;
		//data2.comment_content = $("#comment_content_id").val();
		$.modal('/w/hospital_common/commentmanage/opinion/commentlist.html','添加',{
			content: 'no',
			data: JSON.stringify(data2),
			width: '450px',
			comment_code: $('[name=comment_content_code]').val(),
			callback : function(rtn){
				if(rtn){
					if(rtn.code){
						var code = '';
						for(var i=0;i< rtn.code.length;i++){
							if(i == 0){
								code = rtn.code[i].code;
							}else{
								code = code + ',' + rtn.code[i].code;
							}
						}
						$('[name=comment_content_code]').empty();
						$('[name=comment_content_code]').val('');
						$('[name=comment_content_code]').val(code);
					}
					if(rtn.content || rtn.code){
						$('[name=comment_content]').empty();
						$('[name=comment_content]').val('');
						$('[name=comment_content]').val(rtn.content);
					}
				}
	        }
		})
	});

	function _clean(){
		$('[name=comment_content_code]').empty();
		$('[name=comment_content_code]').val('');
		$('[name=comment_content]').empty();
		$('[name=comment_content]').val('');
	}
</script>