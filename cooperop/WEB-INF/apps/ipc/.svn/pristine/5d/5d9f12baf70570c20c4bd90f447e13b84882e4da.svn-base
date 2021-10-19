<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="处方点评结果查看" disloggedin="true">
<style type="text/css">
	.quesdetail{border-bottom: 1px dashed;}
	.ptitle{
	 	font-size: 14px;
	 	color: #666666;
	    line-height: 30px;
	    border-bottom: 1px solid #cccccc;
	}
	.simpleinfo{
		margin-left:40px;
		border-bottom: 1px solid #cccccc;
    	border-top: 1px solid #cccccc;
    	margin-bottom: 20px;
	}
	.simpleinfo span{
	    width: 236px;
    	display: inline-table;
    	line-height: 20px;
	}
	#dpdiv{
		display: none;
		height: 700px;
	}
	.sititle{
		margin-left:40px;
		font-size: 14px;
	}
	.rstate1{
	color:green;
	font-size:14px;
	}
	.rstate2{
	color:red;
	font-size:14px;
	}
	.rstate3{
	color:#dac20a;
	font-size:14px;
	}
	.dpjg{
	margin-top: 10px;
    margin-bottom: 10px;
    color: #480b05;
	}
	.pti{
	border-bottom: 1px solid #d4eef7;
	 word-wrap:break-word
	}
	</style>
 	<div class="col-md-9">
		<s:row>
			<s:table id="datatable" active="true" label="点评医嘱/处方选择" autoload="false" action="ipc.comment.queryCRorders" sort="true" select="single">
				<s:toolbar>
					<%-- <s:button label="药品说明书" onclick="yaopin();" icon=""></s:button> --%>
					<%-- <s:button label="" onclick="yaopin();" icon=""></s:button> --%>
				</s:toolbar>
				<s:table.fields>
					<s:table.field name="cresult" label="警" width="20px" datatype="script">
						return '<font style="color: red; font-size:14px; font-weight:600 ">×</font>';
					</s:table.field> 
					<s:table.field name="patient_name" label="病人信息" datatype="String" width="120px">
					</s:table.field>
					<s:table.field name="check_level_name" label="严重程度" width="60px"></s:table.field>
				    <s:table.field name="tag" label="组" width="20px"></s:table.field>
					<s:table.field name="lc" label="长/临" datatype="script" width="40px">
						var cl=record.repeat_indicator;
						if(cl=='0'){
							return '临';
						}else if(cl=='1'){
							return '长';
						}else{
							return '';
						}
					</s:table.field>
					<s:table.field name="order_text" label="药品名称" width="240px"></s:table.field>
					<s:table.field name="enter_date_time" label="开嘱时间" width="130px"></s:table.field>
					<s:table.field name="stop_date_time" label="停嘱时间" width="130px"></s:table.field>
					<s:table.field name="order_class" label="类别" width="60px"></s:table.field>
					<s:table.field name="administration" label="给药方式" width="80px"></s:table.field>
					<s:table.field name="jixing" label="剂型" width="60px"></s:table.field>
					<s:table.field name="dosage" label="剂量" datatype="script" width="60px">
						var x=record.dosage;
						return parseFloat(x);
					</s:table.field>
					<s:table.field name="dosage_units" label="单位" width="60px"></s:table.field>
					<s:table.field name="frequency" label="频次" width="60px"></s:table.field>
					<s:table.field name="dept_name" label="开嘱科室" width="80px"></s:table.field>
					<s:table.field name="doctor" label="开嘱医生" width="80px"></s:table.field>
					<s:table.field name="property_toxi" label="毒理分类" width="60px"></s:table.field>
					<s:table.field name="comment_username" label="点评人" width="80px"></s:table.field>
					<s:table.field name="comment_datetime" label="点评时间" width="130px"></s:table.field>
					
					<%-- <s:table.field name="caozuo" datatype="template" label="操作">
						<a href="javascript:void(0);" onclick="tocomment();">点评</a>
					</s:table.field> --%>
				</s:table.fields>
			</s:table>
			
			</s:row>
			
 	</div>
 	<div class="col-md-3" style="border-left: 1px #cccccc dashed;" id="dpdiv">
 		<p class="ptitle">点评结果</p>
 		<div style="height:700px;overflow: auto;">
		<s:form id="formcheck" >
		<div style="height:300px;overflow: auto;">
			<s:row>
					<div class="resultdp" ></div>
			</s:row>
		</div>
		</s:form>
		</div>
 	</div>

	
</s:page>
<script type="text/javascript">
	var selectData={};
	var cindex;
	
	$(function(){
		query();
		$("#datatable").find("tbody").on("click", "tr", function() {
				selectData = $("#datatable").getSelected()[0].data;
				$("#dpdiv").css("display","block");
				var sdata = {
					order_no: selectData.order_no,
					group_id: selectData.group_id,
					visit_id: selectData.visit_id,
					patient_id: selectData.patient_id,
					sample_orders_id:selectData.sample_orders_id
				};
			
				$.call("ipc.comment.queryLastResult", sdata, function(rtn){
					//显示问题
					console.log(rtn);
					$(".resultdp").html('');
					if(rtn.lastr && rtn.lastr.length >= 1){
						var html =[];
						html.push('<p>点评结果：</p><ul class="pti"><li class="dpjg">');
						html.push("不合理");
						html.push('</li></ul>');
						if(rtn.lastr[0].comment_result_message){
							html.push('<p>药师意见：</p><ul class="pti"><li class="dpjg">');
							html.push(rtn.lastr[0].comment_result_message);
							html.push('</li></ul>');
						}
						
						if(rtn.lastr[0].comment_name){
							html.push('<p>点评细则：');
							html.push('</p>');
							html.push('<ul class="pti">');
							for(i=0;i<rtn.lastr.length;i++){
								if(rtn.lastr[i].comment_name != null ){
									html.push('<li class="dpjg">');
									html.push(rtn.lastr[i].comment_name);
									html.push('</li>');
								}
							}
							html.push('</ul>')
						}
						$(".resultdp").html(html.join(''));
					}
				});
			}); 
	});

	function query(){
		var data={
				sample_id: '${param.sample_id}',
				patient_id: '${param.patient_id}'
		};
		$("#datatable").params(data);
		$("#datatable").refresh();
	}
	
	function yaopin(){
		var drugcode = selectData.order_code;
		if(drugcode){
			//打开药品说明书
			layer.open({
				  type: 2,
				  title: $(this).text(),
				  //skin: 'layui-layer-rim', //加上边框
				  area: ['800px', '580px'], //宽高
				  //content: "instruction.html?his_drug_code="+drugcode
				  content: "/w/ipc/auditresult/instruction.html?his_drug_code="+drugcode	  
			});
		}else{
			$.message("请选择药品！");
		}
	}
</script>