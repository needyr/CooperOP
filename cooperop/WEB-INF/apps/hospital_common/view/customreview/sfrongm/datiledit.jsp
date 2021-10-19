<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<style>
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
<s:page title="编辑溶媒问题" ismodal="true">
	<s:row>
		<s:form id="form" label="溶媒问题">
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
				<s:select label="条件" name="tiaojian" cols="1" value="${tiaojian}" >
					<s:option value="and" label="and" ></s:option>
					<s:option value="or" label="or"></s:option>
				</s:select>
				<s:switch name="is_must_rongm" label="必须使用溶媒" ontext="是" offtext="否" offvalue="否" onvalue="是" value="${is_must_rongm}" cols="1"></s:switch>
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
				<s:textfield label="年龄段" name="nianl_c"  cols="1" disabled="disabled" value="${nianl_c}" dbl_action='zl_select_shengfrm_06,shengfangzl_rongm'></s:textfield>
				<s:textfield label="年龄范围" name="nianl_start"  cols="1" value="${nianl_start}"></s:textfield>
				<s:textfield label="至" name="nianl_end"  cols="1" value="${nianl_end}"></s:textfield>
			</s:row>
			<s:row>
			    <s:textfield label="给药方式like" name="routename"  cols="2" disabled="disabled" value="${routename}" dbl_action='zl_select_shengfrm_07,shengfangzl_rongm' ></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="开始液体量" name="s_ml_volume"  cols="1" value="${s_ml_volume}" placeholder="提示：液体量单位：ml" ></s:textfield>
				<s:textfield label="截止液体量" name="e_ml_volume"  cols="1" value="${e_ml_volume}" placeholder="提示：液体量单位：ml" ></s:textfield>
			</s:row>
			<s:row>
				<input type="hidden" name="xmbh" value="${xmbh}" />
				<s:textfield label="项目名称" name="xmmch"  cols="1" disabled="disabled" value="${xmmch}" dbl_action='zl_select_shengfrm_02,shengfangzl_rongm'></s:textfield>
				<%-- <s:textfield label="项目名称" disabled="disabled" name="xmmch"  cols="1" value="${xmmch}"></s:textfield> --%>
				<s:textfield label="公式"  disabled="disabled" name="formul"  cols="1" value="${formul_name}" dbl_action='zl_select_shengfrm_03,shengfangzl_rongm'></s:textfield>
				<s:textfield label="值" name="value"  cols="1" value="${value}"     dbl_action='zl_select_shengfrm_01,shengfangzl_rongm'></s:textfield>
			</s:row>
			<%-- <s:row>
				<s:textarea label="或者计算公式" name="calculation_formula" cols="4" placeholder="请输入计算公式"  rows="" value="">${calculation_formula}</s:textarea>
			</s:row> --%>
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
				<div class="cols2">
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

	function save(){
		var patrn = /[`~!#$^&_\-+=<>?"{}|,\/;'\·~！#￥……&（）——\-+={}|《》？“”【】、；‘’，。、]/im;  
		var rex = /^(-|\+)?\d+(\.\d+)?$/;
		var str = $('[name=value]').val();
		var str2 = $('[name=s_ml_volume]').val();
		var str3 = $('[name=e_ml_volume]').val();
		if(str2=='' && str3=='' || rex.test(str2) && rex.test(str3)){
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
				$.call("hospital_common.customreview.sfrongm.save",sdata,function(s){
					$.closeModal(s);
		    	});
				if(sdata.id){
					$.call("hospital_common.dictdrug.updateCz", 
							{drug_code: '${param.spbh}', zdy_cz: "修改溶媒问题"}, function(){});
				}else{
					$.call("hospital_common.dictdrug.updateCz", 
							{drug_code: '${param.spbh}', zdy_cz: "新增溶媒问题"}, function(){});
				}
		     }
		}else{
			$.message('[液体量]中请添加正确的值!');
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