<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="处方点评结果查看(部门)" disloggedin="true">
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
	</style>
 	<div class="col-md-9">
		<s:row>
			<s:table id="datatable" active="true" label="点评医嘱/处方选择" autoload="false" action="ipc.comment.queryCRorders" sort="true" select="single">
				<s:toolbar>
					<%-- <s:button label="药品说明书" onclick="yaopin();" icon=""></s:button> --%>
					<%-- <s:button label="" onclick="yaopin();" icon=""></s:button> --%>
				</s:toolbar>
				<s:table.fields>
					<s:table.field name="comment_result" label="警" datatype="String">
					</s:table.field>
					<s:table.field name="sys_check_level_name" label="等级" ></s:table.field>
				    <s:table.field name="zu" label="组" ></s:table.field>
					<s:table.field name="lc" label="长/临" datatype="script">
						var cl=record.repeat_indicator;
						if(cl=='0'){
							return '临';
						}else if(cl=='1'){
							return '长';
						}
					</s:table.field>
					<s:table.field name="yp" label="类别" ></s:table.field>
					<s:table.field name="stime" label="开嘱时间" ></s:table.field>
					<s:table.field name="order_text" label="药品名称" ></s:table.field>
					<s:table.field name="gy" label="给药方式" ></s:table.field>
					<s:table.field name="property_toxi" label="毒理分类" ></s:table.field>
					<s:table.field name="jixing" label="剂型" ></s:table.field>
					<s:table.field name="dosage" label="剂量" datatype="script">
						var x=record.dosage;
						return parseFloat(x);
					</s:table.field>
					<s:table.field name="dosage_units" label="单位" ></s:table.field>
					<s:table.field name="frequency" label="频次" ></s:table.field>
					<s:table.field name="dept_name" label="开嘱科室" ></s:table.field>
					<s:table.field name="doctor" label="开嘱医生" ></s:table.field>
					<s:table.field name="stop_date_time" label="停嘱时间" ></s:table.field>
					<%-- <s:table.field name="caozuo" datatype="template" label="操作">
						<a href="javascript:void(0);" onclick="tocomment();">点评</a>
					</s:table.field> --%>
				</s:table.fields>
			</s:table>
			
			</s:row>
			<%-- <s:row>
				 <s:tabpanel>
					<s:form label="医嘱/处方问题" active="true" id="quest">
						<s:row>
							<div class="quesdiv">
								<!-- <div class="quesdetail">
									<p>
									问题分类：相互作用 
								</p>
								<p>
									严重程度：禁忌
								</p>
								<p>
									问题：XSADSDSADASDASDSADSADSADSA
								</p>
								</div> -->
							</div>
						</s:row>
	 				</s:form>
	 				<s:form label="点评记录">
	 				<s:row>
						877777777777
					</s:row>
	 				</s:form>
				</s:tabpanel> 
		</s:row> --%>
 	</div>
 	<div class="col-md-3" style="border-left: 1px #cccccc dashed;" id="dpdiv">
 		<p class="ptitle">点评结果</p>
 		<div style="height:500px;overflow: auto;">
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
					visit_id: selectData.visit_id,
					patient_id: selectData.patient_id
				};
			/* 	$.call("ipc.commentflow.getQuestions",sdata,function(rtn){
					 $(".quesdiv").empty();
					 var data=rtn.resultset;
					 if(data.length >0){
						 var html=[];
							for(i=0;i<data.length;i++){
								html.push(' <div class="quesdetail">');
								html.push(' <p>');
								if(data[i].check_result_state=='Y'){
									html.push('<span class="rstate1 fa fa-check-circle-o" title="通过级别问题" color="red"></span>');
								}else if(data[i].check_result_state=='N'){
									html.push('<span class="rstate2 icon-ban"  title="拦截级别问题"></span>');
								}else if(data[i].check_result_state=='T'){
									html.push('<span class="rstate3 fa fa-warning (alias)"  title="审查级别问题"></span>');
								}
								html.push(' 问题分类： '+data[i].sort_name);
								html.push(' </p>');
								html.push('<p>');
								html.push('严重程度：'+data[i].sys_check_level_name +'【' +data[i].star_level+'】');
								html.push('</p>');
								html.push('<p>');
								html.push('问题：'+data[i].description);
								html.push('</p>');
								html.push(data.reference);
								html.push('</p>');
								html.push('</div>');
								$(".quesdiv").append(html.join(''));
							} 
					 }
				}); */
				
				$.call("ipc.comment.queryLastResult",{p_key: selectData.p_key},function(rtn){
					//显示问题
					console.log(rtn);
					if(rtn.lastr){
						var html =[];
						html.push('<p>点评结果：');
						html.push("不合理");
						html.push('</p>');
						if(rtn.lastr[0].comment_content){
							html.push('<p>个人点评理由：');
							html.push(rtn.lastr[0].comment_content);
							html.push('</p>');
						}
						html.push('<ul>标准点评理由:')
						for(i=0;i<rtn.lastr.length;i++){
							html.push('<li>');
							html.push(rtn.lastr[i].comment_name);
							html.push('</li>');
						}
						html.push('</ul>')
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