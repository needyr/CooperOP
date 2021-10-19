<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<style type="text/css">
.main {
    padding: 0px !important;
}
#tags_choose,#tags_choose_fifter{
	width: calc(100% - 170px);
	border: 1px dashed #d0d0d0;
	min-height: 28px;
	float: left;
	margin-bottom: 6px;
	margin-right: 10px;
	padding: 3px;
}

#tags_choose_fifter {
	cursor: pointer;
}

.tag_type{
	border: 1px solid #ff8100;
	border-radius: 8px !important;
	padding: 2px;
	font-family: 微软雅黑;
	background-color: #ffdd00;
	font-weight: 600;
	font-size: 10px;
	margin: 2px;
	cursor: pointer;
	display: -webkit-inline-box;
}

.bac{
	background-color: #f9f1d9 !important;
}
.dataTables_wrapper .table-scrollable .dataTables_scrollBody tbody tr .radio-list, .dataTables_wrapper .table-scrollable .dataTables_scrollBody tbody tr .checkbox-list {
    background-color: rgba(0,0,0,0) !important;
    border: 0px solid #e5e5e5 !important;
    text-align: center !important;
}
.tags_choose{
	width: 100%;
	border: 1px dashed #d0d0d0;
	min-height: 28px;
	float: right;
	padding: 3px;
}

.tag_type{
	border: 1px solid #ff8100;
	border-radius: 8px !important;
	padding: 2px;
	font-family: 微软雅黑;
	background-color: #ffdd00;
	font-weight: 600;
	font-size: 10px;
	margin: 2px;
	display: -webkit-inline-box;
}
.cols1{
	width: 25% !important;
}

/*为空时显示 element attribute content*/
#tags_choose_fifter:empty:before{
    content: attr(placeholder);   /* element attribute*/
    /*content: 'this is content';*/
    color:#b7b7b7 !important;
    line-height: 24px;
}
/*焦点时内容为空*/
#tags_choose_fifter:focus:before{
    content:none;
}

</style>
<s:page title="药品维护" >
	<s:row>
		<s:form id="form" extendable="true" collapsed="true">
			<s:row>
				<s:textfield label="关键字" name="filter" placeholder="请输入名称" cols="1"></s:textfield>
				<s:select label="药品类别" name="drug_indicator" value="all" cols="1">
					<s:option value="all" label="全部"></s:option>
					<c:forEach items="${types}" var="t">
						<s:option value="${t.drug_indicator}" label="${t.drug_indicator}"></s:option>
					</c:forEach>
				</s:select>
				<s:checkbox label="抗菌药" cols="1" name="filter_kjy">
					<s:option value="1" label="是"></s:option>
					<s:option value="0" label="否"></s:option>
				</s:checkbox>
				<s:checkbox label="库存" cols="1" name="filter_kc">
					<s:option value="1" label="有"></s:option>
					<s:option value="0" label="无"></s:option>
				</s:checkbox>
				<s:radio label="是否存在属性" cols="1" name="is_attr">
					<s:option value="-1" label="全部"></s:option>
					<s:option value="1" label="是"></s:option>
					<s:option value="0" label="否"></s:option>
				</s:radio>
				<s:radio label="TPN相关药品" cols="1" name="is_tpn">
					<s:option value="-1" label="全部"></s:option>
					<s:option value="1" label="是"></s:option>
					<s:option value="0" label="否"></s:option>
				</s:radio>
			</s:row>
			<s:row>
				<div style="width:100%;margin: 1px 0px 6px;">
					<font style="float: left;width:96px;margin-left: 4px;margin-top:5px;">标签类型</font>
					<div id="tags_choose_fifter" ondblclick="add_tags_fifter(this);" placeholder="双击选择标签"></div>
					<a onclick="qk_tag_fifter(this)" style="line-height: 28px;">清空</a>
					<%-- <select style="width:calc(20% - 9px);height: 31px;" id="tags_select">
						<option value="" selected = "selected">--必选--</option>
					</select> --%>
				</div>
			</s:row>
			<s:row>
			<s:button label="全部查询" icon="fa fa-search" name="query" onclick="query_all()"></s:button>
			<s:button label="已维护查询" icon="fa fa-search" name="query" onclick="query_finish()"></s:button>
			<s:button label="未维护查询" icon="fa fa-search" name="query" onclick="query_not()"></s:button>
			<s:button label="筛选列 " icon="fa fa-random" name="" onclick="saix()"></s:button>
			<s:button label="导出" icon="fa fa-random" onclick="tableToExcel('datatable', '导出药品')"></s:button>
			<%-- <s:button label="导出" icon="fa fa-random" action="toexcel"></s:button> --%>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="" autoload="false" fitwidth="false" action="hospital_common.dictextend.dicthisdrug.query"  >
			<s:table.fields>
				<s:table.field name="is_wh" label="维护"  datatype="template" width="50">
					<s:checkbox name="is_wh" value="$[is_wh]" onchange="updateCb(this,'$[drug_code]','is_wh')">
						<s:option value ="1" label=""></s:option>
					</s:checkbox>
					<!-- <input value="1" name="is_wh" onchange="updateCb(this,'$[drug_code]','is_wh')" type="checkbox" style="height:15px;width: 15px;"> -->
				</s:table.field>
				<s:table.field name="drug_code" label="编号"  width="100"  ></s:table.field>
				<s:table.field name="tags" label="药品标签" datatype="script" width="200" >
					var drug_tags = record.drug_tags;
					var html = [];
					var drug_code = record.drug_code;
					html.push('<div id="tags_choose_'+drug_code+'" data-tag="'+record.tags+'" onclick="add_tags(this,\''+drug_code+'\');" class="tags_choose">');
					if(drug_tags){
						var tags = drug_tags.split(',');
						for(var i=0;i < tags.length;i++){
							var tag = tags[i].split('-');
							html.push('<font title="'+tag[1]+'" data-tagbh="'+tag[0]+'" data-tagshow="'+tag[2]+'" class="tag_type">'+tag[2]+'</font>');
						}
					}
					html.push('</div>');
					return html.join('');
				</s:table.field>
				<s:table.field name="attr" label="属性添加" datatype="script" width="100">
					return '<a onclick="attr_edit('+"'"+record.drug_code+"'"+');">修改属性'+ (record.check_drug_mx == '1'?'<i class="fa fa-list"><i>':'') +'</a>';
				</s:table.field>
				<s:table.field name="drug_name" datatype="script" label="药品名称"  width="150">
					return '<a onclick="yaopin(\''+record.drug_code+'\')">'+record.drug_name+'</a>';
				</s:table.field>
				<s:table.field name="common_name" datatype="script" label="药品通用名称"  width="150">
				if(record.common_name){
				 return '<a onclick="common_name_msg(this,\''+record.drug_code+'\',\''+record.common_name+'\')"  class="fa fa-edit"></a>&nbsp;'+record.common_name;
				}else{
				 return '<a onclick="common_name_msg(this,\''+record.drug_code+'\',\'\')"  class="fa fa-edit"></a>&nbsp;';
				}
				</s:table.field>
				<s:table.field name="jixing" label="剂型" width="100"></s:table.field>
				<s:table.field name="druggg" label="规格" width="100"></s:table.field>
				<s:table.field name="shengccj" label="生产厂家" width="100"></s:table.field>
				<s:table.field name="use_dw" label="给药单位" width="50"></s:table.field> 
				<s:table.field name="pack_unit" label="计价单位" width="50"></s:table.field> 
				<s:table.field name="is_anti_drug" label="抗菌药物"  datatype="template" width="100">
					<s:select name="is_anti_drug" value="$[is_anti_drug]" onchange="updateZd(this,'$[drug_code]','is_anti_drug')">
					  <s:option value ="0" label="非抗菌药"></s:option>
					  <s:option value ="1" label="抗细菌药"></s:option>
					  <s:option value ="2" label="抗真菌药"></s:option>
					  <s:option value ="3" label="抗结核药"></s:option>
					  <s:option value ="4" label="其他抗菌药"></s:option>
					</s:select>
				</s:table.field>
				<s:table.field name="anti_level" label="抗菌药物级别"  datatype="template" width="100">
					<s:select name="anti_level" value="$[anti_level]" onchange="updateZd(this,'$[drug_code]','anti_level')">
					  <s:option value ="1" label="非限制使用"></s:option>
					  <s:option value ="2" label="限制使用"></s:option>
					  <s:option value ="3" label="特殊使用"></s:option>
					</s:select>
				</s:table.field>
				<s:table.field name="check_height" label="必须有身高"  datatype="template" width="100">
					<s:select name="check_height" value="$[check_height]" onchange="updateZd(this,'$[drug_code]','check_height')">
					  <s:option value ="0" label="否"></s:option>
					  <s:option value ="1" label="是"></s:option>
					</s:select>
				</s:table.field>
				<s:table.field name="check_weight" label="必须有体重"  datatype="template" width="100">
					<s:select name="check_weight" value="$[check_weight]" onchange="updateZd(this,'$[drug_code]','check_weight')">
					  <s:option value ="0" label="否"></s:option>
					  <s:option value ="1" label="是"></s:option>
					</s:select>
				</s:table.field>
				<s:table.field name="check_ast" label="必须皮试"  datatype="template" width="100">
					<s:select name="check_ast" value="$[check_ast]" onchange="updateZd(this,'$[drug_code]','check_ast')">
					  <s:option value ="0" label="否"></s:option>
					  <s:option value ="1" label="是"></s:option>
					</s:select>
				</s:table.field>
				<s:table.field name="is_jings_drug" label="精神类药品"  datatype="template" width="100">
					<s:checkbox name="is_jings_drug" value="$[is_jings_drug]" onchange="updateCb(this,'$[drug_code]','is_jings_drug')">
						<s:option value ="1" label=""></s:option>
					</s:checkbox>
				</s:table.field>
				<s:table.field name="is_dux_drug" label="毒性药品"  datatype="template" width="100">
					<s:checkbox name="is_dux_drug" value="$[is_dux_drug]" onchange="updateCb(this,'$[drug_code]','is_dux_drug')">
						<s:option value ="1" label=""></s:option>
					</s:checkbox>
				</s:table.field>
				<s:table.field name="is_maz_drug" label="麻醉药品"  datatype="template" width="100">
					<s:checkbox name="is_maz_drug" value="$[is_maz_drug]" onchange="updateCb(this,'$[drug_code]','is_maz_drug')">
						<s:option value ="1" label=""></s:option>
					</s:checkbox>
				</s:table.field>
				<s:table.field name="unit_conversion" label="给药单位换算关系" title="换算关系(计价单位与给药单位的换算关系)" datatype="template" width="150">
					<s:textfield name="unit_conversion" value="$[unit_conversion]" onchange="updateZd(this,'$[drug_code]','unit_conversion')">
					</s:textfield>
				</s:table.field>
				<s:table.field name="ddd_value" label="DDD值"  datatype="template" width="150">
					<s:textfield name="ddd_value" value="$[ddd_value]" onchange="updateZd(this,'$[drug_code]','ddd_value')">
					</s:textfield>
				</s:table.field>
				<s:table.field name="ddd_daylimit" label="限定日剂量(DDD)"  datatype="template" width="150">
					<s:textfield name="ddd_daylimit" value="$[ddd_daylimit]" onchange="updateZd(this,'$[drug_code]','ddd_daylimit')">
					</s:textfield>
				</s:table.field>
				<%-- <s:table.field name="ddd_unit" label="DDD值单位"  datatype="template" width="150">
					<s:textfield name="ddd_unit" value="$[ddd_unit]" onchange="updateZd(this,'$[drug_code]','ddd_unit')">
					</s:textfield>
				</s:table.field> --%>
				<%-- <s:table.field name="ddd_conversion" label="DDD值换算关系" title="换算关系(计价单位与DDD值单位的换算关系)" datatype="template" width="150">
					<s:textfield name="ddd_conversion" value="$[ddd_conversion]" onchange="updateZd(this,'$[drug_code]','ddd_conversion')">
					</s:textfield>
				</s:table.field> --%>
				<s:table.field name="ddd_conversion" label="g单位换算关系" title="g单位换算关系" datatype="template" width="150">
					<s:textfield name="ddd_conversion" value="$[ddd_conversion]" onchange="updateZd(this,'$[drug_code]','ddd_conversion')">
					</s:textfield>
				</s:table.field>
				<s:table.field name="is_jbyw" label="基本药物"  datatype="template" width="100">
					<%-- <s:select name="is_jbyw" value="$[is_jbyw]" onchange="updateZd(this,'$[drug_code]','is_jbyw')">
					  <s:option value ="0" label="否"></s:option>
					  <s:option value ="1" label="是"></s:option>
					</s:select> --%>
					<s:checkbox name="is_jbyw" value="$[is_jbyw]" onchange="updateCb(this,'$[drug_code]','is_jbyw')">
						<s:option value ="1" label=""></s:option>
					</s:checkbox>
				</s:table.field>
				<s:table.field name="is_xfj" label="兴奋剂"  datatype="template" width="100">
					<s:checkbox name="is_xfj" value="$[is_xfj]" onchange="updateCb(this,'$[drug_code]','is_xfj')" >
						<s:option value ="1" label=""></s:option>
					</s:checkbox>
				</s:table.field>
				<s:table.field name="is_zsj" label="注射剂"  datatype="template" width="100">
					<s:checkbox name="is_zsj" value="$[is_zsj]" onchange="updateCb(this,'$[drug_code]','is_zsj')">
						<s:option value ="1" label=""></s:option>
					</s:checkbox>
				</s:table.field>
				<s:table.field name="is_kfj" label="口服剂"  datatype="template" width="100">
					<s:checkbox name="is_kfj" value="$[is_kfj]" onchange="updateCb(this,'$[drug_code]','is_kfj')">
						<s:option value ="1" label=""></s:option>
					</s:checkbox>
				</s:table.field>
				<s:table.field name="is_rongm" label="是否溶媒"  datatype="template" width="100">
					<s:checkbox name="is_rongm" value="$[is_rongm]" onchange="updateCb(this,'$[drug_code]','is_rongm')">
						<s:option value ="1" label=""></s:option>
					</s:checkbox>
				</s:table.field>
				
				<s:table.field name="drug_ypfl" label="药品分类"  datatype="template" width="100">
					<s:select name="drug_ypfl" value="$[drug_ypfl]" onchange="updateZd(this,'$[drug_code]','drug_ypfl')">
					  <c:forEach var="ypfl" items="${ypfl}">
						  <s:option value ="${ypfl.drug_ypfl_code}" label="${ypfl.drug_ypfl_name}"></s:option>
					  </c:forEach>
					</s:select>
				</s:table.field>
				
				<s:table.field name="drug_ylfl" label="药理分类" datatype="template" width="100">
					<s:textfield placeholder="单击选择" name="drug_ylfl" value="$[drug_ylfl_name]" onclick="ylfl(this,'$[drug_code]','drug_ylfl')" readonly="true">
					</s:textfield>
				</s:table.field>
				
				<s:table.field name="zdy_shuoms_msg" label="自定义信息"  datatype="template" width="250">
					<s:textfield name="zdy_shuoms_msg" placeholder="双击填写" style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;" value="$[zdy_shuoms_msg]" ondblclick="zdy_shuoms_msg1(this,'$[drug_code]','zdy_shuoms_msg')"  readonly="true">
					</s:textfield>
				</s:table.field>
				
				<s:table.field name="dr" label="药品说明书附件" datatype="script" width="100">
					<!-- <input id="file1" type="file"  name="file1" multiple="multiple" accept=".pdf,.jpg,.png"> -->
					if(record.shuoms_file){
						return '<a label="" onclick="download_beiz('+"'"+record.drug_code+"'"+');">上传</a><i class="fa fa-file-pdf-o" style="margin-left:10px"></i>';
					}
					return '<a label="" onclick="download_beiz('+"'"+record.drug_code+"'"+');">上传</a>';
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script src="${pageContext.request.contextPath}/res/hospital_common/js/table_print_excel.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	query_all();
	$(window).resize(function(){
		$('.dataTables_scrollBody').css('height',$(window).height() - 
				200 - $("div[ctype='form']").parent().parent().height());
		$('.dataTables_scrollBody').css('width','100%');
		$('.dataTables_scrollBody').css('overflow','auto');	
	});
	$(window).resize();
	
	$("i.showhide").on("click", function(){
		$('.dataTables_scrollBody').css('height',$(window).height() - 
				200 - $("div[ctype='form']").parent().parent().height());
		$('.dataTables_scrollBody').css('width','100%');
		$('.dataTables_scrollBody').css('overflow','auto');	
	})
	
	$(".form-collapse-btn").on("click", function(){
		setTimeout(function(){
			$('.dataTables_scrollBody').css('height',$(window).height() - 
					200 - $("div[ctype='form']").parent().parent().height());
			$('.dataTables_scrollBody').css('width','100%');
			$('.dataTables_scrollBody').css('overflow','auto');	
		},500)
	})
});

function add_tags(_this,drug_code){
	var code = $(_this).attr('data-tag');
	/* $.modal("/w/hospital_common/abase/drugtags.html", "添加药品标记", {
		height: "550px",
		width: "80%",
		code: code,
		maxmin: false,
		callback : function(rtn) {
			if(rtn){
				var name = rtn.name.toString();
				var code = rtn.code.toString();
				var jc = rtn.jc.toString();
				$('#tags_choose_'+drug_code).empty();
				var all=rtn.all;
				var spanhtml=[];
				for(var i=0;i<all.length;i++){
					var t = all[i].split('-');
					spanhtml.push('<font title="'+t[2]+'" data-tagbh="'+t[0]+'" data-tagname="'+t[2]+'" data-tagshow="'+t[1]+'" class="tag_type">'+t[1]+'</font>');
				}
				//$('#tags_choose_'+drug_code).append(spanhtml);
				//$('#tags_choose_'+drug_code).attr('data-tag',code);
				document.getElementById("tags_choose_"+drug_code).innerHTML = spanhtml;
				document.getElementById("tags_choose_"+drug_code).setAttribute('data-tag',code);
				var data = '{"'+'tags'+'":"'+code+'","drug_code":"'+drug_code+'"}';
				update(eval('(' + data + ')'));
			}
	    }
	}); */
	$.modal("/w/hospital_common/dict/sysdrugtag/list.html", "添加药品标记", {
		height: "90%",
		width: "50%",
		code: code,
		maxmin: false,
		callback : function(rtn) {
			if(rtn){
				var code = [];
				$('#tags_choose_'+drug_code).empty();
				var spanhtml='';
				for(var i=0;i<rtn.length;i++){
					var t = rtn[i].all.split('-');
					code.push(rtn[i].code);
					spanhtml += '<font title="'+t[2]+'" data-tagbh="'+t[0]+'" data-tagname="'+t[2]+'" data-tagshow="'+t[1]+'" class="tag_type">'+t[1]+'</font>';
				}
				//$('#tags_choose_'+drug_code).append(spanhtml);
				//$('#tags_choose_'+drug_code).attr('data-tag',code);
				document.getElementById("tags_choose_"+drug_code).innerHTML = spanhtml;
				document.getElementById("tags_choose_"+drug_code).setAttribute('data-tag',code);
				var data = '{"'+'tags'+'":"'+code.toString()+'","drug_code":"'+drug_code+'"}';
				update(eval('(' + data + ')'));
			}
	    }
	}); 
}


function add_tags_fifter(_this){
	var code = $(_this).attr('data-tag');
	$.modal("/w/hospital_common/dict/sysdrugtag/list.html", "添加药品标记", {
		height: "90%",
		width: "50%",
		code: code,
		maxmin: false,
		callback : function(rtn) {
			if(rtn){
				var code = [];
				$('#tags_choose_fifter').empty();
				var spanhtml='';
				for(var i=0;i<rtn.length;i++){
					var t = rtn[i].all.split('-');
					code.push(rtn[i].code);
					spanhtml += '<font title="'+t[2]+'" data-tagbh="'+t[0]+'" data-tagname="'+t[2]+'" data-tagshow="'+t[1]+'" class="tag_type">'+t[1]+'</font>';
				}
				document.getElementById("tags_choose_fifter").innerHTML = spanhtml;
				document.getElementById("tags_choose_fifter").setAttribute('data-tag',code);
			}
	    }
	}); 
}

function query_all(){
	var formData = $('#form').getData();
	formData.timeout = 0;
	formData.is_wh_filter = 3;
	
	var drug_tag_fifter = $('#tags_choose_fifter').find("font");
	var tagbh=[];
	for(var i=0;i<drug_tag_fifter.length;i++){
		tagbh.push($(drug_tag_fifter[i]).attr("data-tagbh"));
	}
	formData.drug_tag_fifter=tagbh.toString();
	
	$("#datatable").params(formData);
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

function query_not(){
	var formData = $('#form').getData();
	formData.timeout = 0;
	formData.is_wh_filter = 0;
	var drug_tag_fifter = $('#tags_choose_fifter').find("font");
	var tagbh=[];
	for(var i=0;i<drug_tag_fifter.length;i++){
		tagbh.push($(drug_tag_fifter[i]).attr("data-tagbh"));
	}
	formData.drug_tag_fifter=tagbh.toString();
	$("#datatable").params(formData);
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

function query_finish(){
	var formData = $('#form').getData();
	formData.timeout = 0;
	formData.is_wh_filter = 1;
	var drug_tag_fifter = $('#tags_choose_fifter').find("font");
	var tagbh=[];
	for(var i=0;i<drug_tag_fifter.length;i++){
		tagbh.push($(drug_tag_fifter[i]).attr("data-tagbh"));
	}
	formData.drug_tag_fifter=tagbh.toString();
	$("#datatable").params(formData);
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

function updateZd(_this,drug_code,zd){
	var value = $(_this).val();
	var data = '{"'+zd+'":"'+value+'","drug_code":"'+drug_code+'"}';
	update(eval('(' + data + ')'));
}

function updateCb(_this,drug_code,zd){
	if($(_this).children().children().children().hasClass('checked')){
		value = 1;
	}else{
		value = 0;
	}
	var data = '{"'+zd+'":"'+value+'","drug_code":"'+drug_code+'"}';
	update(eval('(' + data + ')'));
}

function update(data){
	$.ajax({
		type: "post",
		url: "/w/hospital_common/dictextend/dicthisdrug/updateByCode.json",
		contentType: "application/x-www-form-urlencoded; charset-UTF-8",
		data: data,
		success: function(XMLHttpRequest, status){
			var one_click = $('.bac[name=query]');
			if(one_click.length > 0){
				one_click.click();
			}else{
				query_all();
			}
		}
	})
}

$('[name=query]').click(function(){
	$(this).addClass('bac').siblings().removeClass('bac');
})

function ylfl(_this,drug_code,zd){
	$.modal("ylfl.html","药理选择",{
		width:"400px",
		height:"500px",
		callback : function(rtn){
			if(rtn){
				if(rtn == 'deletetext'){
					$(_this).val('');
				}else{
					$(_this).val(rtn);
				}
				updateZd(_this,drug_code,zd);
			}
		}
	});
}

function zdy_shuoms_msg1(_this,drug_code,zd){
	var html = [];
	html.push('<textarea style="width:400px;height:180px" id="zdy_shuoms_msg_name" placeholder="" autocomplete="off">'+$(_this).val()+'</textarea>');
	layer.alert(html.join(""), {
		area: ["500px", "320px"],
		title: "内容填写",
		scrollbar: true,
		offset: '100px',
		btn: ["确认", "取消"], 
		success: function(layero, index) {
		},
		yes: function(index, layero) {
			var value = $(layero).find('#zdy_shuoms_msg_name').val();
			var data = '{"'+zd+'":"'+value+'","drug_code":"'+drug_code+'"}';
			update(eval('(' + data + ')'));
			layer.close(index);
		},
		cancel: function(index) {
		}
	});
}

function common_name_msg(_this,drug_code,msg){
	var html = [];
	html.push('<textarea style="width:400px;height:180px" id="common_name_msg" placeholder="请输入合理的药品通用名称" autocomplete="off">'+msg+'</textarea>');
	layer.alert(html.join(""), {
		area: ["500px", "320px"],
		title: "内容填写",
		scrollbar: true,
		offset: '100px',
		btn: ["确认", "取消"], 
		success: function(layero, index) {
		},
		yes: function(index, layero) {
			var value = $(layero).find('#common_name_msg').val();
			var data = '{"common_name":"'+value+'","drug_code":"'+drug_code+'"}';
			update(eval('(' + data + ')'));
			layer.close(index);
		},
		cancel: function(index) {
		}
	});
}

function yaopin(drug_code){
	$.modal("/w/hospital_common/additional/instruction.html?drug_code="+drug_code,"查看药品说明书",{
        width:"80%",
        height:"90%",
        callback : function(e){
        }
	});
}

function tableToExcel(tableID, fileName) {
	var data = $("#form").getData();
	$("#" + tableID).params_table(data);
	$("#" + tableID).excel_new();
	//$("#" + tableID).export_table();
}

function download_beiz(drug_code){
	$.modal("/w/hospital_common/dictextend/dicthisdrug/download.html?drug_code="+drug_code,"药品说明书附件上传",{
        width:"60%",
        height:"400px",
        callback : function(e){
        	var one_click = $('.bac[name=query]');
			if(one_click.length > 0){
				one_click.click();
			}else{
				query_all();
			}
        }
	});
}

function saix(){
	$('#datatable').setting_table();
}

function qk_tag_fifter(_this){
	$('#tags_choose_fifter').empty();
}

function attr_edit(drug_code){
	$.modal("/w/hospital_common/dictextend/dicthisdrug/drug_attr.html","药品属性维护",{
        width:"50%",
        height:"80%",
        drug_code: drug_code,
        callback : function(e){
        	if(e){
        		var one_click = $('.bac[name=query]');
    			if(one_click.length > 0){
    				one_click.click();
    			}else{
    				query_all();
    			}
        	}
        }
	});
}
</script>