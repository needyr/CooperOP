<%@page import="java.util.Date"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<style type="text/css">
#tags_choose{width: calc(100% - 110px);border: 1px dashed #d0d0d0;min-height: 28px;float: left;margin-bottom: 6px;margin-right: 10px;padding: 3px;}
.tag_type{border: 1px solid #ff8100;border-radius: 8px !important;padding: 2px;font-family: 微软雅黑;background-color: #ffdd00;font-weight: 600;font-size: 10px;margin: 2px;cursor: pointer;display: -webkit-inline-box;}
.bootstrap-switch{height:30px;}
.age_box{
	margin: 5px 0px 5px 100px;
}

.age_text{
	width: 50px;
    height: 30px;
    border: 1px solid #e5e5e5;
}

.jy_text{
	width: 50px;
    height: 30px;
    border: 1px solid #e5e5e5;
}

.age_text:focus{
	border-color: #00c1de;
    outline: 0;
    box-shadow: none;
}

.jy_text:focus{
	border-color: #00c1de;
    outline: 0;
    box-shadow: none;
}

.age_box2 span {
	font-size: 13px;
    display: inline;
}

.jy_box{
	width: 33.3333333%;
	float: left;
}

.jy_box2 span {
	font-size: 13px;
    display: inline;
}

.jy_all{
	border:1px solid #d2d2d2;
	min-height:30px;
	margin-bottom: 10px;
}

.jy_row_del:hover {
	color: #0072ff;
    cursor: pointer;
}

.pag {
    border: 1px solid #bcbcbc;
    min-height:20px;
    margin-bottom: 5px;
}

.drug_title {
	font-size: 13px;
    margin-left: 10px;
    margin-top: 5px;
    margin-bottom: 0;
    border-top: 1px solid #9d9d9d;
    border-right: 1px solid #9d9d9d;
    border-left: 1px solid #9d9d9d;
}

.drug_all {
    margin-bottom: 10px;
}

.pag_drug {
    border: 1px solid #bcbcbc;
    min-height:20px;
    margin-bottom: 5px;
}

.jy_text {
    width: 20%;
}
</style>
<script src="${pageContext.request.contextPath}/res/hospital_common/js/ChinesePY.js"></script>
<s:page title="专项点评字典信息" ismodal="true">
	<s:row>
		<s:form id="form" label="专项点评字典信息">
			<s:toolbar>
				<s:button label="保存" onclick="saveOrUpdate()" icon="glyphicon glyphicon-floppy-saved"/>
				<s:button label="取消" onclick="cancel()" icon="glyphicon glyphicon-remove"/>
			</s:toolbar>
				<s:row>
				    <c:if test="${!empty param.id}">
				        <input value="${id}" name="id" type="hidden">
				    </c:if>
				</s:row>
				<s:row>
				    <s:textfield label="点评字典名称" name="spcomment_name" value="${spcomment_name}" required="true" cols="3"/>
			    </s:row>
			    <s:row>
			        <s:textfield label="拼音名" name="pym" value="${pym}" required="true" cols="2"/>
			        <s:textfield label="起始间隔天数" name="day_interval" value="${day_interval}" required="true" cols="1" placeholder="大于等于0纯数字"/>
			    </s:row>
			    <s:row>
					<div style="width:100%;margin: 1px 0px 6px;">
						<font style="float: left;width:96px;margin-left: 4px;margin-top:5px;">标签类型</font>
						<div id="tags_choose" onclick="add_tags(this);"></div>
						<%-- <select style="width:calc(20% - 9px);height: 31px;" id="tags_select">
							<option value="" selected = "selected">--必选--</option>
						</select> --%>
					</div>
				</s:row>
			    <s:row>
					<s:textfield label="点评规则" name="sys_comment_rules_code_name" value="${sys_comment_rules_code_name}"  readonly="true" placeholder="双击选取点评规则(必填)" dbl_action="noaction()" cols="3"/>
			        <input name="sys_comment_rules_code" value="${sys_comment_rules_code}" type="hidden">
			    </s:row>
			    
			    <s:row>
				<div style="color: #3da910;">
				<s:row>
					<s:select label="报表排名类型" cols="2" name="report_type">
						<s:option label="" value=""></s:option>
						<s:option label="药品金额占比" value="1"></s:option>
						<s:option label="药品金额同比" value="2"></s:option>
						<s:option label="药品金额环比" value="3"></s:option>
						<s:option label="药品数量占比" value="4"></s:option>
						<s:option label="药品数量同比" value="5"></s:option>
						<s:option label="药品数量环比" value="6"></s:option>
					</s:select>
				</s:row>
				
				<s:row>
					<s:textfield label="前几名" name="report_num" cols="2"></s:textfield>
				</s:row>
				
				<s:row>
				<s:textfield label="报表地址" name="report_address" cols="2"></s:textfield>
				</s:row>
				</div>
				</s:row>
				
				
			    
	    <s:row>
			<s:checkbox label="类型" cols="3" name="type">
			<s:option label="门诊" value="1"></s:option>
			<s:option label="急诊" value="2"></s:option>
			<s:option label="在院" value="3"></s:option>
			<s:option label="出院" value="4"></s:option>
			</s:checkbox>
		</s:row>
		<s:row>
			<s:checkbox label="点评状态" cols="3" name="pr_state">
				<s:option label="已点评" value="1"></s:option>
				<s:option label="未点评" value="0"></s:option>
			</s:checkbox>
		</s:row>
		<s:row>
			<s:checkbox label="点评结果" cols="3" name="pr_result">
				<s:option label="合理" value="1"></s:option>
				<s:option label="不合理" value="2"></s:option>
			</s:checkbox>
		</s:row>
		<s:row>
		<input type="hidden" name="pr_person_code" >
		<div class="cols2">
		<label class="control-label">点评人</label>
		<div class="control-content">
		<textarea style="height: 50px;" readonly="readonly" class="form-control" placeholder="双击选择" name="pr_person_name" cols="4"  data-autosize-on="true"></textarea>
		</div>
		</div>
		<a onclick="clean_pr_person_name()">清空</a>
		</s:row>
		
		<s:row>
		<div style="color: #3da910;">
		<s:row>
			<s:checkbox label="切口类型" cols="2" name="incision_type">
			  <s:option value ="Ⅰ" label="I类"></s:option>
			  <s:option value ="Ⅱ" label="II类"></s:option>
			  <s:option value ="Ⅲ" label="III类"></s:option>
			</s:checkbox>
		</s:row>
		
		<s:row>
			<s:textfield label="切口科室前几名" name="incision_report_num" cols="2"></s:textfield>
		</s:row>
		
		<s:row>
			<s:textfield label="切口科室报表地址" name="incision_report_address" cols="2"></s:textfield>
		</s:row>
		</div>
		</s:row>
		
		<s:row>
		<input type="hidden" name="dept_code" >
		<div class="cols2">
		<label class="control-label">科室</label>
		<div class="control-content">
		<textarea style="height: 50px;" readonly="readonly" class="form-control" placeholder="双击选择科室" name="dept_name" cols="4"  data-autosize-on="true"></textarea>
		</div>
		</div>
		<a onclick="clean_dept_name()">清空</a>
		</s:row>
		
		<s:row>
		<input type="hidden" name="charge_doctor_code" >
		<div class="cols2">
		<label class="control-label">主管医生</label>
		<div class="control-content">
		<textarea style="height: 50px;" readonly="readonly" class="form-control" placeholder="双击选择医生" name="charge_doctor_name" cols="4"  data-autosize-on="true"></textarea>
		</div>
		</div>
		<a onclick="clean_charge_doctor_name()">清空</a>
		</s:row>
		
		<s:row>
		<input type="hidden" name="doctor_code" >
		<div class="cols2">
		<label class="control-label">开嘱医生</label>
		<div class="control-content">
		<textarea style="height: 50px;" readonly="readonly" class="form-control" placeholder="双击选择医生" name="doctor_name" cols="4"  data-autosize-on="true"></textarea>
		</div>
		</div>
		<a onclick="clean_doctor_name()">清空</a>
		</s:row>
		
		<s:row>
		<!-- <input type="hidden" name="diagnosisfifter" > -->
		<div class="cols2">
		<label class="control-label">诊断</label>
		<div class="control-content">
		<textarea style="height: 50px;" class="form-control" placeholder="双击选择诊断" name="diagnosis_name" cols="4"  data-autosize-on="true"></textarea>
		</div>
		</div>
		<a onclick="clean_diagnosis_name()">清空</a>
		</s:row>
		
		<s:row>
		<input type="hidden" name="exam_item_code" >
		<div class="cols2">
		<label class="control-label">检查项目</label>
		<div class="control-content">
		<textarea style="height: 50px;" readonly="readonly" class="form-control" placeholder="双击选择检查项目" name="exam_item_name" cols="4"  data-autosize-on="true"></textarea>
		</div>
		</div>
		<a onclick="clean_exam_item_name()">清空</a>
		</s:row>
		
		<!-- 检验添加 -->
		<s:row>
			<s:button label="添加检验条件(and)" icon="fa fa-plus" onclick="add_jy_pag_and()"></s:button>
			<s:button label="添加检验条件(or)" icon="fa fa-plus" onclick="add_jy_pag_or()"></s:button>
			<div class="pag">
			<%-- <s:button label="添加检验条件" icon="fa fa-plus" onclick="add_jy()"></s:button>
			<div class="jy_all">
				
			</div> --%>
			</div>
		</s:row>
		
		<s:row>
			<s:checkbox label="药品类型" cols="2" name="drug_type">
				<c:forEach var="ypfl" items="${ypfl}">
				<s:option value ="${ypfl.drug_ypfl_code}" label="${ypfl.drug_ypfl_name}"></s:option>
				</c:forEach>
			</s:checkbox>
		</s:row>
		
		<s:row>
			<s:checkbox label="抗菌药物" cols="2" name="is_kangjy">
				<s:option label="是" value="1"></s:option>
				<s:option label="否" value="0"></s:option>
			</s:checkbox>
		</s:row>
		
		<%-- <s:row>
		<input type="hidden" name="drug_code" >
		<div class="cols2">
		<label class="control-label">药品</label>
		<div class="control-content">
		<textarea style="height: 50px;" class="form-control" readonly="readonly" placeholder="双击选择药品" name="drug_name" cols="4"  data-autosize-on="true"></textarea>
		</div>
		</div>
		<a onclick="clean_drug_name()">清空</a>
		</s:row> --%>
		<s:row>
			<s:button label="药品条件(and)" icon="fa fa-plus" onclick="add_drug_pag_and()"></s:button>
			<s:button label="药品条件(or)" icon="fa fa-plus" onclick="add_drug_pag_or()"></s:button>
			<s:button label="删除全部" icon="" onclick="del_drug_all()"></s:button>
			<div class="pag_drug">
			</div>
		</s:row>
		
		<s:row>
		<input type="hidden" name="routename_code" >
		<div class="cols2">
		<label class="control-label">给药途径</label>
		<div class="control-content">
		<textarea style="height: 50px;" class="form-control" readonly="readonly" placeholder="双击选择给药途径" name="routename_name" cols="4"  data-autosize-on="true"></textarea>
		</div>
		</div>
		<a onclick="clean_routename_name()">清空</a>
		</s:row>
		
		<s:row>
			<s:checkbox label="用药目的" cols="3" name="use_purp"><!-- 为添加筛选 -->
				<s:option label="预防" value="1"></s:option>
				<s:option label="治疗" value="2"></s:option>
			</s:checkbox>
		</s:row>
		
		<s:row>
			<s:checkbox label="疾病类型" cols="3" name="diagno_type">
			  <s:option value ="0" label="非感染性疾病"></s:option>
			  <s:option value ="1" label="细菌感染"></s:option>
			  <s:option value ="2" label="真菌感染"></s:option>
			  <s:option value ="3" label="结核/麻风感染"></s:option>
			  <s:option value ="4" label="其他感染"></s:option>
			</s:checkbox>
		</s:row>
		
		<s:row>
			<div class="age_box">
				<label class="control-label">患者年龄</label>
				<div class="age_box2">
				<input class="age_text" type="text" name="min_age_year" value="${min_age_year}" ><span>岁</span>
				<input class="age_text" type="text" name="min_age_mon" value="${min_age_mon}" ><span>月</span>
				<input class="age_text" type="text" name="min_age_day" value="${min_age_day}"  ><span>天</span>
				<span style="font-size: 14px;margin-left:10px;margin-right:20px;line-height: 30px;" >至</span>
				<input class="age_text" type="text" name="max_age_year" value="${max_age_year}" ><span>岁</span>
				<input class="age_text" type="text" name="max_age_mon" value="${max_age_mon}" ><span>月</span>
				<input class="age_text" type="text" name="max_age_day" value="${max_age_day}" ><span>天</span>
				</div>
			</div>
		</s:row>
		
		<s:row>
		<s:textfield label="费别" name="feibie" value="${feibie}" cols="3" placeholder="输入费别关键字,只允许一个"></s:textfield>
		</s:row>
		
		<s:row>
		<s:textfield label="患者名称" name="patient_name" value="${patient_name}" cols="3" placeholder="输入患者名称,多个用逗号隔开"></s:textfield>
		</s:row>
		
		<s:row>
		<s:textfield label="门诊/住院号" name="pre_no" value="${pre_no}" cols="3" placeholder="输入门诊/住院号,多个用逗号隔开"></s:textfield>
		</s:row>
			    
				<s:row>
					<s:textarea label="备注" name="beizhu" value="${beizhu}" cols="3"/>
				</s:row>
				<s:row>
				    <s:switch label="是否启用" value="${beactive}" name="beactive" onvalue="1" offvalue="0" ontext="是" offtext="否" ></s:switch>
				</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript" src="/theme/scripts/controls/input/autocomplete.js"></script>
<script type="text/javascript">
	$(function(){
		loadzhuanxiangleixing();
		loadData();
	});
	
	function loadData(){
		if('${type}'){
			$("[name='type']").setData('${type}'.split(','));		
		}
		if('${pr_state}'){
			$("[name='pr_state']").setData('${pr_state}'.split(','));		
		}
		if('${pr_result}'){
			$("[name='pr_result']").setData('${pr_result}'.split(','));		
		}
		if('${pr_person_code}'){
			$("[name='pr_person_code']").val('${pr_person_code}');		
		}
		if('${pr_person_name}'){
			$("[name='pr_person_name']").val('${pr_person_name}');		
		}
		if('${dept_code}'){
			$("[name='dept_code']").val('${dept_code}');		
		}
		if('${dept_name}'){
			$("[name='dept_name']").val('${dept_name}');		
		}
		if('${charge_doctor_code}'){
			$("[name='charge_doctor_code']").val('${charge_doctor_code}');		
		}
		if('${charge_doctor_name}'){
			$("[name='charge_doctor_name']").val('${charge_doctor_name}');		
		}
		if('${doctor_code}'){
			$("[name='doctor_code']").val('${doctor_code}');		
		}
		if('${doctor_name}'){
			$("[name='doctor_name']").val('${doctor_name}');		
		}
		if('${diagnosis_name}'){
			$("[name='diagnosis_name']").val('${diagnosis_name}');		
		}
		if('${drug_type}'){
			$("[name='drug_type']").setData('${drug_type}'.split(','));		
		}
		if('${is_kangjy}'){
			$("[name='is_kangjy']").setData('${is_kangjy}'.split(','));		
		}
		/* if('${drug_code}'){
			$("[name='drug_code']").val('${drug_code}');		
		}
		if('${drug_name}'){
			$("[name='drug_name']").val('${drug_name}');		
		} */
		if('${routename_code}'){
			$("[name='routename_code']").val('${routename_code}');		
		}
		if('${routename_name}'){
			$("[name='routename_name']").val('${routename_name}');		
		}
		if('${use_purp}'){
			$("[name='use_purp']").setData('${use_purp}'.split(','));		
		}
		if('${diagno_type}'){
			$("[name='diagno_type']").setData('${diagno_type}'.split(','));		
		}
		if('${exam_item_code}'){
			$("[name='exam_item_code']").setData('${exam_item_code}'.split(','));		
		}
		if('${exam_item_name}'){
			$("[name='exam_item_name']").val('${exam_item_name}');		
		}
		if('${report_address}'){
			$("[name='report_address']").val('${report_address}');		
		}
		if('${report_type}'){
			$("[name='report_type']").val('${report_type}');	
		}else{
			$("[name='report_type']").val('');
		}
		if('${report_num}'){
			$("[name='report_num']").val('${report_num}');		
		}
		if('${incision_type}'){
			$("[name='incision_type']").setData('${incision_type}'.split(','));		
		}
		if('${incision_report_num}'){
			$("[name='incision_report_num']").val('${incision_report_num}');		
		}
		if('${incision_report_address}'){
			$("[name='incision_report_address']").val('${incision_report_address}');		
		}
		if('${lab_xml}'){
			var lab_xml = $.parseXML('${lab_xml}');
			var page = $(lab_xml).find("xml").find("page");
			var num = 0;
			 for(var ii=0;ii<page.length;ii++){
				var list = [];
				 var tiaoj = $($(page[ii]).find("tiaoj")[0]).text();
				 var param = $(page[ii]).find("jy");
				 var html = '';
				 if(tiaoj == 'and'){
					html += '<div class="jy_all _and">';
			    	html += '<button crid="" action="" class="btn btn-sm btn-sm btn-default " ctype="button" t="1" type="button" onclick="add_jy(this)" cinited="cinited"><i class="fa fa-plus"></i>:and</button>';
			    	html += '<button crid="" action="" class="btn btn-sm btn-sm btn-default " ctype="button" t="1" type="button" onclick="add_jy_re(this)" cinited="cinited">删除</button>';
		    	}else{
		    		html += '<div class="jy_all _or">';
		        	html += '<button crid="" action="" class="btn btn-sm btn-sm btn-default " ctype="button" t="1" type="button" onclick="add_jy(this)" cinited="cinited"><i class="fa fa-plus"></i>:or</button>';
		        	html += '<button crid="" action="" class="btn btn-sm btn-sm btn-default " ctype="button" t="1" type="button" onclick="add_jy_re(this)" cinited="cinited">删除</button>';
		    	}
				 for(var i=0;i<param.length;i++){
					var jy_item_code = $($(param[i]).find("jy_item_code")[0]).text();
				    var jy_item_name = $($(param[i]).find("jy_item_name")[0]).text();
				    var jy_min = $($(param[i]).find("jy_min")[0]).text();
				    var jy_max = $($(param[i]).find("jy_max")[0]).text();
				    var jy_content = $($(param[i]).find("jy_content")[0]).text();
				    var _map = {};
			    	num = num+1;
			    	html += '<div class="row-fluid jy_row" div_id="jy_item'+num+'" data-num="'+num+'">';
			    	html += '<div class="cols2"><label class="control-label" title="检验项目'+''+'">检验项目'+''+'</label>';
			   		html += '<div class="control-content has-icon"><i class="control-icon fa fa-caret-down"></i>';
					html += '<input ctype="autocomplete" value="" ';
					html += 'class="form-control ui-autocomplete-input" ';
					html += 'type="text" islabel="false" editable="false" ';
					html += 'isherf="false" label="检验项目" htmlescape="false" ';
					html += 'id="jy_item'+num+'" name="jy_item" action="hospital_common.dicthislabreportitem.query_items" ';
					html += 'cols="2" autocomplete="off" cinited="cinited">';
					html += '<div class="autocomplete-content">';
					html += '<div class="jy_val" data-value="$[item_code]" data-label="$[item_name]($[units])"></div>';
					html += '</div></div></div>';
					html += '<div class="jy_box">';
					html += '<div class="jy_box2">';
					html += '<span style="font-size: 13px;margin-right:5px;line-height: 30px;" >值</span>';
					html += '<input class="jy_text" type="text" name="jy_min" value="'+jy_min+'" >';
					html += '<span style="font-size: 14px;margin-left:10px;margin-right:20px;line-height: 30px;" >至</span>';
					html += '<input class="jy_text" type="text" name="jy_max" value="'+jy_max+'" >';
					html += '<span style="font-size: 14px;margin-left:10px;margin-right:20px;line-height: 30px;" >具体值</span>';
					html += '<input class="jy_text" type="text" name="jy_content" value="'+jy_content+'" >';
					html += '<i class="glyphicon glyphicon-remove jy_row_del" style="position: relative;top: 10px;float: right;"></i>';
					html += '</div>';
					html += '</div>';
					html += '</div>';
					_map.num = num;
					_map.jy_item_code = jy_item_code;
					_map.jy_item_name = jy_item_name;
					list.push(_map);
				 }
				html += '</div>'; 
				$('.pag').append(html);
				for(var iii=0;iii<list.length;iii++){
					$('#jy_item'+list[iii].num).data("text",list[iii].jy_item_code);
					$('#jy_item'+list[iii].num).val(list[iii].jy_item_name);
				    $('#jy_item'+list[iii].num).ccinit_autocomplete();
				}
				$('.jy_row_del').click(function(){
					$(this).parent().parent().parent().remove();
			    });
			 }
		}
		if('${drug_code}'){
			var lab_xml = $.parseXML('${drug_code}');
			var page = $(lab_xml).find("xml").find("page");
			for(var ii=0;ii<page.length;ii++){
				//-------------------------------------------
				var tiaoj = $($(page[ii]).find("tiaoj")).text();
				var html = [];
				if(tiaoj == 'and'){
					html.push('<div class="drug_all _and">');
					html.push('<input type="hidden" name="drug_code" value="'+$($(page[ii]).find("code")).text()+'" >');
					html.push('<label class="drug_title">药品<font style="font-weight: 600;">and</font></label>');
					html.push('<a class="drug_row_del" style="line-height: 18px;margin-left: 10px;">删除</a>');
					html.push('<a onclick="clean_drug_name(this)" style="line-height: 18px;margin-left: 25px;">清空</a>');
					html.push('<textarea style="height: 50px;resize: none;width:66%" ondblclick="drug_search(this)" class="form-control" readonly="readonly" placeholder="双击选择药品" name="drug_name" data-autosize-on="true">'+$($(page[ii]).find("name")).text()+'</textarea>');
					html.push('</div>');
				}else{
					html.push('<div class="drug_all _or">');
					html.push('<input type="hidden" name="drug_code" value="'+$($(page[ii]).find("code")).text()+'">');
					html.push('<label class="drug_title">药品<font style="font-weight: 600;">or</font></label>');
					html.push('<a class="drug_row_del" style="line-height: 18px;margin-left: 10px;">删除</a>');
					html.push('<a onclick="clean_drug_name(this)" style="line-height: 18px;margin-left: 25px;">清空</a>');
					html.push('<textarea style="height: 50px;resize: none;width:66%" ondblclick="drug_search(this)" class="form-control" readonly="readonly" placeholder="双击选择药品" name="drug_name" data-autosize-on="true">'+$($(page[ii]).find("name")).text()+'</textarea>');
					html.push('</div>');
				}
				$('.pag_drug').append(html.join(''));
				$('.drug_row_del').click(function(){
			    	$(this).parent().remove();
			    });
			}
		}
	}
	
	$('[name="spcomment_name"]').bind('input propertychange',function(){
		var pym = Pinyin.GetJP($('[name="spcomment_name"]').val());
		$('[name="pym"]').val(pym);
	})
	//加载专项类型
	function loadzhuanxiangleixing(){
		$.call("hospital_common.dict.sysspcomment.querytags",{},function(s){
			if(s&&s.resultset){
				var objs=s.resultset;
				var optionhtml=[];
				for(var i=0;i<objs.length;i++){
					var obj=objs[i];
					optionhtml.push('<option value="'+obj.drugtagbh+'" data-tagid="'+obj.drugtagid+'" data-tagname="'+obj.drugtagname+'" data-tagshow="'+obj.drugtag_show+'" data-tagshuom="'+obj.drugtag_shuom+'">'+obj.drugtag_show+'</option>');
				}
				$("#tags_select").append(optionhtml.join(''));
			}
			var order='${order}';
			if(order){
				var json=(eval("("+order+")"));
				var times=json.drugtagid.length;
				var spanhtml=[];
				for(var i=0;i<times;i++){
					spanhtml.push('<font data-tagbh="'+json.drugtagbh[i]+'" data-tagid="'+json.drugtagid[i]+'" data-tagname="'+json.drugtagname[i]+'" data-tagshow="'+json.drugtag_show[i]+'" class="tag_type">'+json.drugtag_show[i]+'</font>');
				}
				$("#tags_choose").append(spanhtml);
				/* $('[class="tag_type"]').dblclick(function(){
					$(this).remove();
				}) */
			}
   		});
	}

	//规范检查
	function runcheck(){
		if(!$("form").valid()){return false;}//必填检查
		else if(isNaN($('[name="day_interval"]').val())||$('[name="day_interval"]').val()<0){$('[name="day_interval"]').focus();return false;}//间隔天数必须大于等于0的纯数字
		//else if($("#tags_choose").find("font").length==0){return false;}//专项类型必填
		else if($("[name='sys_comment_rules_code']").val()==''){return false;}//点评规则必填
		return true;
	}
	
	function saveOrUpdate(){
		if(runcheck()){//规范检查
			var data=$("#form").getData();
			var tagbh=[];
			$("#tags_choose").find("font").each(function(){
				tagbh.push($(this).attr("data-tagbh"));
			});
			data.drugtagbh=tagbh.toString();
			//获取检验xml数据
			var _jy = $('.jy_all');
			if(_jy.length > 0){
				var jy_xml = '<xml>';
				var check = false;
				for (var j=0;j<_jy.length;j++){
					jy_xml += '<page>';
					var and_or = _jy.eq(j).attr('class');
					if(and_or.indexOf('_or') != -1 ){
						jy_xml += '<tiaoj>or</tiaoj>';
					}else if(and_or.indexOf('_and') != -1 ){
						jy_xml += '<tiaoj>and</tiaoj>';
					}
					var jys = _jy.eq(j).find('.jy_row');
					for (var f=0;f<jys.length;f++){
						var jy = jys.eq(f);
						if(jy.find('[name=jy_item]').eq(0).data("text") || jy.find('[name=jy_item]').eq(0).data("value")){
							jy_xml += '<jy>';
							jy_xml += '<jy_item_code>' + (jy.find('[name=jy_item]').eq(0).data("text") || jy.find('[name=jy_item]').eq(0).data("value")) + '</jy_item_code>';
							jy_xml += '<jy_item_name>' + jy.find('[name=jy_item]').eq(0).val() + '</jy_item_name>';
							jy_xml += '<jy_min>' + jy.find('[name=jy_min]').val() + '</jy_min>';
							jy_xml += '<jy_max>' + jy.find('[name=jy_max]').val() + '</jy_max>';
							jy_xml += '<jy_content>' + jy.find('[name=jy_content]').val() + '</jy_content>';
							jy_xml += '</jy>';
							check = true;
						}
					}
					jy_xml += '</page>';
				}
				jy_xml += '</xml>';
				if(check){
					data.lab_xml = jy_xml;
				}else{
					data.lab_xml = null;
				}
			}else{
				data.lab_xml = null;
			}
			delete data["jy_item"]; 
			delete data["jy_min"]; 
			delete data["jy_max"]; 
			delete data["jy_content"]; 
			//---------------------
			//获取药品筛选条件
			delete data["drug_code"]; 
			delete data["drug_name"]; 
			var _drug_all = $('.drug_all');
			if(_drug_all.length > 0){
				var drug_code_xml = '<xml>';
				var check = false;
				for (var j=0;j<_drug_all.length;j++){
					var drug_msg = $(_drug_all[j]);
					if(drug_msg.find('[name="drug_code"]').val()){
						drug_code_xml += '<page>';
						var and_or = drug_msg.attr('class');
						if(and_or.indexOf('_or') != -1 ){
							drug_code_xml += '<tiaoj>or</tiaoj>';
						}else if(and_or.indexOf('_and') != -1 ){
							drug_code_xml += '<tiaoj>and</tiaoj>';
						}
						drug_code_xml += '<code>'+drug_msg.find('[name="drug_code"]').val()+'</code>';
						drug_code_xml += '<name>'+drug_msg.find('[name="drug_name"]').val()+'</name>';
						drug_code_xml += '</page>';
						check = true;
					}
				}
				drug_code_xml += '</xml>';
				if(check){
					data.drug_code = drug_code_xml;
					data.drug_name = drug_code_xml;
				}
			}
			var id = '${param.id}';
			if(id!=null&&typeof(id)!='undefind'&&id!=''){//id不为空,修改
				$.call("hospital_common.dict.sysspcomment.update",data,function(s){
					if(s==1){$.closeModal(s);}
		    	});
			}else{//为空，新增
				$.call("hospital_common.dict.sysspcomment.save",data,function(s){
					if(s==1){$.closeModal(s);}
		   		});
			}
		}
	}
	//选择点评规则
	$("[name='sys_comment_rules_code_name']").dblclick(function(){
		var code = $('[name=sys_comment_rules_code]').val();
		$.modal("/w/hospital_common/dictsyscomment/show.html", "添加点评规则", {
			height: "550px",
			width: "40%",
			code: code,
			maxmin: false,
			callback : function(rtn) {
				if(rtn){
					var name = rtn.name.toString();
					var code = rtn.code.toString();
					$('[name=sys_comment_rules_code_name]').val(name);
					$('[name=sys_comment_rules_code]').val(code);
				}
		    }
		});
	});
	/* $('#tags_select').change(function(){
		var value = $('#tags_select option:selected').val();
		var check = 1;
		if(value){
			$('#tags_choose font').each(function(index){
				var tagbh = $(this).attr('data-tagbh');
				if(tagbh == value)
					check = 0;	
			})
			if(check == 1){
				var label = $('#tags_select option:selected').text();
				var tagshuom = $('#tags_select option:selected').attr('data-tagshuom');
				var tagid = $('#tags_select option:selected').attr('data-tagid');
				var tagname = $('#tags_select option:selected').attr('data-tagname');
				var tagshow = $('#tags_select option:selected').attr('data-tagshow');
				var html = $('#tags_choose').html();
				html+='<font data-tagbh="'+value+'" data-tagid="'+tagid+'" data-tagname="'+tagname+'" data-tagshow="'+tagshow+'" class="tag_type" title="'+tagshuom+'">'+label+'</font>';
				$('#tags_choose').html(html);
				$('[class="tag_type"]').dblclick(function(){
					$(this).remove();
				})
			}
		}
	}); */
	//取消
    function cancel(){
    	$.closeModal(false);
    }
	
    $('[name=dept_name]').dblclick(function(){
    	var code = $('[name=dept_code]').val();
    	var d = $("#myform").getData().type;
    	var type = [];
    	for(var i=0;i<d.length;i++){
    		if(d[i] == 1){
    			type.push(2);
    		}else if(d[i] == 2){
    			type.push(3);
    		}else if(d[i] == 3 || d[i] == 4){
    			type.push(1);
    		}
    	}
    	$.modal("/w/hospital_common/dict/sysdept/list.html", "添加科室", {
    		height: "90%",
    		width: "50%",
    		code: code,
    		datasouce: (type&&type.length>0?type:null),
    		maxmin: false,
    		callback : function(rtn) {
    			if(rtn){
    				var name = rtn.name.toString();
    				var code = rtn.code.toString();
    				$('[name=dept_name]').val(name);
    				$('[name=dept_code]').val(code);
    			}
    	    }
    	});
    });

    $('[name=pr_person_name]').dblclick(function(){
    	var code = $('[name=pr_person_code]').val();
    	$.modal("/w/hospital_common/abase/doctor.html", "添加用户", {
    		height: "550px",
    		width: "50%",
    		code: code,
    		maxmin: false,
    		callback : function(rtn) {
    			if(rtn){
    				var name = rtn.name.toString();
    				var code = rtn.code.toString();
    				$('[name=pr_person_name]').val(name);
    				$('[name=pr_person_code]').val(code);
    			}
    	    }
    	});
    });

    $('[name=charge_doctor_name]').dblclick(function(){
    	var code = $('[name=charge_doctor_code]').val();
    	$.modal("/w/hospital_common/abase/doctor.html", "添加用户", {
    		height: "550px",
    		width: "50%",
    		code: code,
    		maxmin: false,
    		callback : function(rtn) {
    			if(rtn){
    				var name = rtn.name.toString();
    				var code = rtn.code.toString();
    				$('[name=charge_doctor_name]').val(name);
    				$('[name=charge_doctor_code]').val(code);
    			}
    	    }
    	});
    });
    
    $('[name=exam_item_name]').dblclick(function(){
    	var code = $('[name=exam_item_code]').val();
    	$.modal("/w/hospital_common/abase/exam_item.html", "添加检查项目", {
    		height: "550px",
    		width: "50%",
    		code: code,
    		maxmin: false,
    		callback : function(rtn) {
    			if(rtn){
    				var name = rtn.name.toString();
    				var code = rtn.code.toString();
    				$('[name=exam_item_name]').val(name);
    				$('[name=exam_item_code]').val(code);
    			}
    	    }
    	});
    });

    $('[name=diagnosis_name]').dblclick(function(){
    	var code = $('[name=diagnosis_name]').val();
    	$.modal("/w/hospital_common/abase/diagnosis.html", "添加诊断", {
    		height: "550px",
    		width: "50%",
    		code: code,
    		maxmin: false,
    		callback : function(rtn) {
    			if(rtn){
    				var name = rtn.name.toString();
    				//var code = rtn.code.toString();
    				$('[name=diagnosis_name]').val(name);
    				//$('[name=diagnosisfifter]').val(code);
    			}
    	    }
    	});
    });

    $('[name=doctor_name]').dblclick(function(){
    	var code = $('[name=doctor_code]').val();
    	$.modal("/w/hospital_common/abase/doctor.html", "添加医生", {
    		height: "550px",
    		width: "50%",
    		code: code,
    		maxmin: false,
    		callback : function(rtn) {
    			if(rtn){
    				var name = rtn.name.toString();
    				var code = rtn.code.toString();
    				$('[name=doctor_name]').val(name);
    				$('[name=doctor_code]').val(code);
    			}
    	    }
    	});
    });

    $('[name=routename_name]').dblclick(function(){
    	var code = $('[name=routename_code]').val();
    	$.modal("/w/hospital_common/abase/routename.html", "添加给药途径", {
    		height: "550px",
    		width: "50%",
    		code: code,
    		maxmin: false,
    		callback : function(rtn) {
    			if(rtn){
    				var name = rtn.name.toString();
    				var code = rtn.code.toString();
    				$('[name=routename_name]').val(name);
    				$('[name=routename_code]').val(code);
    			}
    	    }
    	});
    });

    function clean_dept_name(){
    	$('[name=dept_code]').val('');
    	$('[name=dept_name]').val('');
    }

    function clean_diagnosis_name(){
    	//$('[name=diagnosisfifter]').val('');
    	$('[name=diagnosis_name]').val('');
    }

    function clean_drug_name(){
    	$('[name=drug_code]').val('');
    	$('[name=drug_name]').val('');
    }

    function clean_pr_person_name(){
    	$('[name=pr_person_code]').val('');
    	$('[name=pr_person_name]').val('');
    }

    function clean_charge_doctor_name(){
    	$('[name=charge_doctor_code]').val('');
    	$('[name=charge_doctor_name]').val('');
    }

    function clean_routename_name(){
    	$('[name=routename_code]').val('');
    	$('[name=routename_name]').val('');
    }

    function clean_doctor_name(){
    	$('[name=doctor_code]').val('');
    	$('[name=doctor_name]').val('');
    }
    
    function clean_exam_item_name(){
    	$('[name=exam_item_code]').val('');
    	$('[name=exam_item_name]').val('');
    }
    
    function add_tags(_this,type_code){
    	var tagbh=[];
		$("#tags_choose").find("font").each(function(){
			tagbh.push($(this).attr("data-tagbh"));
		});
		var code=tagbh.toString();
    	$.modal("/w/hospital_common/dict/sysdrugtag/list.html", "添加药品标记", {
    		height: "90%",
    		width: "50%",
    		code: code,
    		maxmin: false,
    		callback : function(rtn) {
    			if(rtn){
    				var code = [];
    				$('#tags_choose').empty();
    				var spanhtml='';
    				for(var i=0;i<rtn.length;i++){
    					var t = rtn[i].all.split('-');
    					code.push(rtn[i].code);
    					spanhtml += '<font title="'+t[2]+'" data-tagbh="'+t[0]+'" data-tagname="'+t[2]+'" data-tagshow="'+t[1]+'" class="tag_type">'+t[1]+'</font>';
    				}
    				//$('#tags_choose_'+drug_code).append(spanhtml);
    				//$('#tags_choose_'+drug_code).attr('data-tag',code);
    				document.getElementById("tags_choose").innerHTML = spanhtml;
    				//document.getElementById("tags_choose").setAttribute('data-tag',code);
    				//var data = '{"'+'tags'+'":"'+code.toString()+'","drug_code":"'+drug_code+'"}';
    				//update(eval('(' + data + ')'));
    			}
    	    }
    	}); 
    }
    
    function add_jy(_this){
    	//$('.jy_all').empty();
    	var html = '';
    	var jy_html = $('.jy_row');
    	var max_value = 0;
    	for(var i = 0;i<jy_html.length;i++){
    		if(max_value< +jy_html.eq(i).attr('data-num')){
    			max_value = +jy_html.eq(i).attr('data-num');
    		}
    	}
    	var num = max_value+1;
    	html += '<div class="row-fluid jy_row" data-num="'+num+'">';
    	html += '<div class="cols2"><label class="control-label" title="检验项目'+num+'">检验项目'+num+'</label>';
   		html += '<div class="control-content has-icon"><i class="control-icon fa fa-caret-down"></i>';
		html += '<input ctype="autocomplete" value="" ';
		html += 'class="form-control ui-autocomplete-input" ';
		html += 'type="text" islabel="false" editable="false" ';
		html += 'isherf="false" label="检验项目" htmlescape="false" ';
		html += 'id="jy_item'+num+'" name="jy_item" action="hospital_common.dicthislabreportitem.query_items" ';
		html += 'cols="2" autocomplete="off" cinited="cinited">';
		html += '<div class="autocomplete-content">';
		html += '<div data-value="$[item_code]" data-label="$[item_name]($[units])"></div>';
		html += '</div></div></div>';
		html += '<div class="jy_box">';
		html += '<div class="jy_box2">';
		html += '<span style="font-size: 13px;margin-right:5px;line-height: 30px;" >值</span>';
		html += '<input class="jy_text" type="text" name="jy_min" value="" >';
		html += '<span style="font-size: 14px;margin-left:10px;margin-right:20px;line-height: 30px;" >至</span>';
		html += '<input class="jy_text" type="text" name="jy_max" value="" >';
		html += '<span style="font-size: 14px;margin-left:10px;margin-right:20px;line-height: 30px;" >具体值</span>';
		html += '<input class="jy_text" type="text" name="jy_content" value="" >';
		html += '<i class="glyphicon glyphicon-remove jy_row_del" style="position: relative;top: 10px;float: right;"></i>';
		html += '</div>';
		html += '</div>';
		html += '</div>';
		$(_this).parent().append(html);
		$('.jy_row_del').click(function(){
			$(this).parent().parent().parent().remove();
	    });
	    $('#jy_item'+num).ccinit_autocomplete();
    }
    
    function add_jy_pag_and(){
    	//$('.pag')
    	var html = '';
    	var jy_html = $('.jy_row');
    	var max_value = 0;
    	for(var i = 0;i<jy_html.length;i++){
    		if(max_value< +jy_html.eq(i).attr('data-num')){
    			max_value = +jy_html.eq(i).attr('data-num');
    		}
    	}
    	var num = max_value+1;
    	html += '<div class="jy_all _and">';
    	html += '<button crid="" action="" class="btn btn-sm btn-sm btn-default " ctype="button" t="1" type="button" onclick="add_jy(this)" cinited="cinited"><i class="fa fa-plus"></i>:and</button>';
    	html += '<button crid="" action="" class="btn btn-sm btn-sm btn-default " ctype="button" t="1" type="button" onclick="add_jy_re(this)" cinited="cinited">删除</button>';
    	html += '<div class="row-fluid jy_row" data-num="'+num+'">';
    	html += '<div class="cols2"><label class="control-label" title="检验项目'+num+'">检验项目'+num+'</label>';
   		html += '<div class="control-content has-icon"><i class="control-icon fa fa-caret-down"></i>';
		html += '<input ctype="autocomplete" value="" ';
		html += 'class="form-control ui-autocomplete-input" ';
		html += 'type="text" islabel="false" editable="false" ';
		html += 'isherf="false" label="检验项目" htmlescape="false" ';
		html += 'id="jy_item'+num+'" name="jy_item" action="hospital_common.dicthislabreportitem.query_items" ';
		html += 'cols="2" autocomplete="off" cinited="cinited">';
		html += '<div class="autocomplete-content">';
		html += '<div data-value="$[item_code]" data-label="$[item_name]($[units])"></div>';
		html += '</div></div></div>';
		html += '<div class="jy_box">';
		html += '<div class="jy_box2">';
		html += '<span style="font-size: 13px;margin-right:5px;line-height: 30px;" >值</span>';
		html += '<input class="jy_text" type="text" name="jy_min" value="" >';
		html += '<span style="font-size: 14px;margin-left:10px;margin-right:20px;line-height: 30px;" >至</span>';
		html += '<input class="jy_text" type="text" name="jy_max" value="" >';
		html += '<span style="font-size: 14px;margin-left:10px;margin-right:20px;line-height: 30px;" >具体值</span>';
		html += '<input class="jy_text" type="text" name="jy_content" value="" >';
		html += '<i class="glyphicon glyphicon-remove jy_row_del" style="position: relative;top: 10px;float: right;"></i>';
		html += '</div>';
		html += '</div>';
		html += '</div>';
		html += '</div>';
		$('.pag').append(html);
		$('.jy_row_del').click(function(){
			$(this).parent().parent().parent().remove();
	    });
	    $('#jy_item'+num).ccinit_autocomplete();
    }
    
    function add_jy_pag_or(){
    	//$('.pag')
    	var html = '';
    	var jy_html = $('.jy_row');
    	var max_value = 0;
    	for(var i = 0;i<jy_html.length;i++){
    		if(max_value< +jy_html.eq(i).attr('data-num')){
    			max_value = +jy_html.eq(i).attr('data-num');
    		}
    	}
    	var num = max_value+1;
    	html += '<div class="jy_all _or">';
    	html += '<button crid="" action="" class="btn btn-sm btn-sm btn-default " ctype="button" t="1" type="button" onclick="add_jy(this)" cinited="cinited"><i class="fa fa-plus"></i>:or</button>';
    	html += '<button crid="" action="" class="btn btn-sm btn-sm btn-default " ctype="button" t="1" type="button" onclick="add_jy_re(this)" cinited="cinited">删除</button>';
    	html += '<div class="row-fluid jy_row" data-num="'+num+'">';
    	html += '<div class="cols2"><label class="control-label" title="检验项目'+num+'">检验项目'+num+'</label>';
   		html += '<div class="control-content has-icon"><i class="control-icon fa fa-caret-down"></i>';
		html += '<input ctype="autocomplete" value="" ';
		html += 'class="form-control ui-autocomplete-input" ';
		html += 'type="text" islabel="false" editable="false" ';
		html += 'isherf="false" label="检验项目" htmlescape="false" ';
		html += 'id="jy_item'+num+'" name="jy_item" action="hospital_common.dicthislabreportitem.query_items" ';
		html += 'cols="2" autocomplete="off" cinited="cinited">';
		html += '<div class="autocomplete-content">';
		html += '<div data-value="$[item_code]" data-label="$[item_name]($[units])"></div>';
		html += '</div></div></div>';
		html += '<div class="jy_box">';
		html += '<div class="jy_box2">';
		html += '<span style="font-size: 13px;margin-right:5px;line-height: 30px;" >值</span>';
		html += '<input class="jy_text" type="text" name="jy_min" value="" >';
		html += '<span style="font-size: 14px;margin-left:10px;margin-right:20px;line-height: 30px;" >至</span>';
		html += '<input class="jy_text" type="text" name="jy_max" value="" >';
		html += '<span style="font-size: 14px;margin-left:10px;margin-right:20px;line-height: 30px;" >具体值</span>';
		html += '<input class="jy_text" type="text" name="jy_content" value="" >';
		html += '<i class="glyphicon glyphicon-remove jy_row_del" style="position: relative;top: 10px;float: right;"></i>';
		html += '</div>';
		html += '</div>';
		html += '</div>';
		html += '</div>';
		$('.pag').append(html);
		$('.jy_row_del').click(function(){
	    	$(this).parent().parent().parent().remove();
	    });
	    $('#jy_item'+num).ccinit_autocomplete();
    }
    
    function add_jy_re(_this){
    	$(_this).parent().remove();
    }
    
    function add_drug_pag_and(){
    	var html = [];
    	html.push('<div class="drug_all _and">');
    	html.push('<input type="hidden" name="drug_code" >');
    	html.push('<label class="drug_title">药品<font style="font-weight: 600;">and</font></label>');
    	html.push('<a class="drug_row_del" style="line-height: 18px;margin-left: 10px;">删除</a>');
    	html.push('<a onclick="clean_drug_name(this)" style="line-height: 18px;margin-left: 25px;">清空</a>');
    	html.push('<textarea style="height: 50px;resize: none;width:85%" ondblclick="drug_search(this)" class="form-control" readonly="readonly" placeholder="双击选择药品" name="drug_name" data-autosize-on="true"></textarea>');
    	html.push('</div>');
    	$('.pag_drug').append(html.join(''));
    	$('.drug_row_del').click(function(){
        	$(this).parent().remove();
        });
    }

    function add_drug_pag_or(){
    	var html = [];
    	html.push('<div class="drug_all _or">');
    	html.push('<input type="hidden" name="drug_code">');
    	html.push('<label class="drug_title">药品<font style="font-weight: 600;">or</font></label>');
    	html.push('<a class="drug_row_del" style="line-height: 18px;margin-left: 10px;">删除</a>');
    	html.push('<a onclick="clean_drug_name(this)" style="line-height: 18px;margin-left: 25px;">清空</a>');
    	html.push('<textarea style="height: 50px;resize: none;width:85%" ondblclick="drug_search(this)" class="form-control" readonly="readonly" placeholder="双击选择药品" name="drug_name" data-autosize-on="true"></textarea>');
    	html.push('</div>');
    	$('.pag_drug').append(html.join(''));
    	$('.drug_row_del').click(function(){
        	$(this).parent().remove();
        });
    }

    function drug_search(_this){
    	var code = $(_this).parent().find('[name=drug_code]').val();
    	var tagbh=[];
		$("#tags_choose").find("font").each(function(){
			tagbh.push($(this).attr("data-tagbh"));
		});
		var tags=tagbh.toString();
    	$.modal("/w/hospital_common/abase/drug.html", "添加药品", {
    		height: "550px",
    		width: "80%",
    		code: code,
    		tags:tags,
    		maxmin: false,
    		callback : function(rtn) {
    			if(rtn){
    				var name = rtn.name.toString();
    				var code = rtn.code.toString();
    				$(_this).parent().find('[name=drug_name]').val(name);
    				$(_this).parent().find('[name=drug_code]').val(code);
    			}
    	    }
    	});
    }

    function drug_row_del(_this){
    	$(_this).parent().remove()
    }

    function del_drug_all(){
    	$('.pag_drug').children().remove()
    }
</script>