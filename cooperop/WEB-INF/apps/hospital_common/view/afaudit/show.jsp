<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="事后审查队列详情" disloggedin="false">
<style>
.statisticss{width:70%;margin: auto;}
.statisticss .statistics{width:100%;float:left;margin:auto;margin-bottom:5px;text-align:left;}
.statisticss .statistics :nth-child(1){display:block;font-family:"微软雅黑";font-size:15px;width:50%;float:left;}
.statisticss .statistics :nth-child(2){display:block;font-family:"微软雅黑";font-size:15px;width:50%;float:left;text-align:right;}
.chartsdiv{width:30%;float:left;margin-top:5px;}
.chartscolor{display:block;width:18px;height:10px;float:right;margin-right:10px;}
.chartscolorfrom{float:left;line-height:10px;font-size:10px;} 
</style>
<!-- highcharts -->
<script src="${pageContext.request.contextPath}/theme/plugins/highcharts-4.1.9/js/highcharts.js"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/highcharts-4.1.9/js/modules/exporting.js"></script>
	<s:row>
	    <div class="col-md-3">
	        <s:form id="statistics" label="队列审查结果统计">
				<s:row>
				    <div class='chartsdiv'><span class='chartscolorfrom'>待审</span><span class='chartscolor' style="background:#696969;"></span></div>
				    <div class='chartsdiv'><span class='chartscolorfrom'>已审</span><span class='chartscolor' style="background:#90ED7D;"></span></div>
				    <div class='chartsdiv'><span class='chartscolorfrom'>通过</span><span class='chartscolor' style="background:#7CFC00;"></span></div>
				    <div class='chartsdiv'><span class='chartscolorfrom'>驳回</span><span class='chartscolor' style="background:#FF7F50;"></span></div>
				    <div class='chartsdiv'><span class='chartscolorfrom'>提示</span><span class='chartscolor' style="background:#FFD700;"></span></div>
				    <div class='chartsdiv'><span class='chartscolorfrom'>待决策</span><span class='chartscolor' style="background:#00BFFF;"></span></div>
				    <div id="charts" style="height:250px;width:100%;float:left;"></div>
				    <div class='statisticss'>
				        <div class='statistics'><span>最高耗时:</span><span id="sta_max_elapsed_time"></span></div>
				        <div class='statistics'><span>最低耗时:</span><span id="sta_min_elapsed_time"></span></div>
				        <div class='statistics'><span>平均耗时:</span><span id="sta_avg_elapsed_time"></span></div>
				        <div class='statistics'><span>总耗时:</span><span id="sta_sum_elapsed_time"></span></div>
				    </div>
				</s:row>
			</s:form>
	    </div>
	    <div class="col-md-9">
	    	<s:form id="form">
	    		<s:row>
	    			<s:textfield label="患者科室" name="dept" placeholder="请输入科室编码或科室名称"></s:textfield>
	    			<s:textfield label="患者" name="patient" placeholder="请输入患者姓名或编号"></s:textfield>
	    		</s:row>
	    	</s:form>
		    <s:table id="datatable" label="事后审查队列详情" autoload="false" action="hospital_common.afaudit.queryDetails" sort="true" limit="10">
				<s:toolbar>
				    <input value="Y,N,T,Q" class="resultSerch" type="checkbox"><span>已审</span>
				    <input value="Y" class="resultSerch" type="checkbox"><span>通过</span>
				    <input value="T" class="resultSerch" type="checkbox"><span>提示</span>
				    <input value="Q" class="resultSerch" type="checkbox"><span>拦截</span>
				    <input value="N" class="resultSerch" type="checkbox"><span>驳回</span>
					<div style="float:left;height:36px;line-height:38px;"><span style="line-height:38px;">执行次数：</span></div>
					<div style="position:relative;width:50px;height:36px;float:left;line-height:36px;margin-right:20px;">
					  <select class="option_audit_times" name="option_audit_times" onmousedown="if(this.options.length>3){this.size=5}" onblur="this.size=0" onchange="this.size=0" style="position:absolute;z-index:1;min-height:25px;width:50px;margin-top:7px;"></select>
					</div>
					<input type="hidden" value="${param.audit_times}" name="audit_times">
	            	<s:button label="查询" icon="fa fa-search" onclick="query();"/>
	            </s:toolbar>
				<s:table.fields>
	                <s:table.field name="p_type" label="开单类型" datatype="script">
	                	var p_type = record.p_type;
	                	var d_type = record.d_type;
	                	if(p_type == 1){p_type= '医嘱';}
	                	else if (p_type == 2){p_type= '处方';}
	                	if(d_type == 1 ){d_type = '住院';}
	                	else if(d_type == 3){d_type = '急诊';}
	                    else if (d_type == 2){d_type = '门诊';}
	                	return '<a onclick="druglist(\''+record.patient_id+'\',\''+record.visit_id+'\', this);">'+d_type + p_type+ '</a>';
	                </s:table.field>
	                <s:table.field name="patient_name" label="患者" datatype="template" width="50px">
	                	<a href="javascript:void(0);" onclick="toPatient('$[patient_id]','$[visit_id]', this)">$[patient_name]</a>
	                </s:table.field>
	                <s:table.field name="dept_name" label="开嘱科室"/>
	                <%-- <s:table.field name="doctor_no" label="开嘱医生" datatype="script" width="80px">
	                	return record.doctor_name + '(' +record.doctor_no+')';
	                </s:table.field> --%>
	                <s:table.field name="common_start_time" label="审查开始时间" sort="true" width="140px"/>
	                <s:table.field name="common_end_time" label="审查结束时间" sort="true" defaultsort="desc" width="140px"/>
	                <s:table.field name="cost_time" label="审查耗时" sort="true" datatype="script">
	                    var state=record.final_state;
	                	if(state){
	                	    var cost_time=(record.cost_time)/1000;
	                        var color="#696969";
	                        if(cost_time>20){color="#CC0033";}
	                        else if(cost_time>=0) {color="black";}
	                        return "<span style='color:"+color+"'>"+cost_time.toFixed(3)+"s</span>";
	                	}else{return '';}
	                    
	                </s:table.field>
	                <s:table.field name="final_state" label="审查结果" datatype="script">
	                    var state=record.final_state;
	                    var html = "<span style='color:#696969'>暂无结果</span>";
	                    if(state == 'Y'){html = "<span>通过</span>"}
	                    else if(state == 'N'){html = "<span>驳回</span>"}
	                    else if(state == 'T'){html = "<span>提示</span>"}
	                    else if(state == 'Q'){html = "<span>待决策</span>"}
	                    return html;
	                </s:table.field>
	                <s:table.field name="caozuo" label="操作" datatype="script" width="32px">
	                    var state=record.final_state;
	                     var fhtml = [] ;
	                	if(state != 'Y' && state){
	                	    fhtml.push('<a href="javascript:void(0)" onclick="auditDetail(\''+ record.common_id +'\', this)">');
	                	    fhtml.push('详情</a>&nbsp;&nbsp;');
	                	}else{
	                		fhtml.push('<a href="javascript:void(0)" style="color: #bbbbbb;">');
	                	    fhtml.push('详情</a>&nbsp;&nbsp;');
	                	}
	                	if('${param.products}'.indexOf('hospital_imic') >=0 ){
                	    	fhtml.push('|<a href="javascript:void(0)" onclick="imic_log(\''+ record.common_id +'\', this);">医保日志</a>');
                	    }
                	    if('${param.products}'.indexOf('ipc') >=0 ){
                	    	fhtml.push('|<a href="javascript:void(0)" onclick="ipc_log(\''+ record.common_id +'\', this);">合理用药日志</a>');
                	    }
                	    return fhtml.join('');
	                </s:table.field>
	            </s:table.fields>
			</s:table>
	    </div>
	</s:row>
</s:page>
<script type="text/javascript">
var drow = 0;
	$(function(){
		query();
		addAuditTimes();
	});
	
	function query(){
		var data = $('#form').getData();
		var resultSerch=[];
		$('.resultSerch').each(function(){
			if($(this).attr("checked")){
				resultSerch.push($(this).val());
			}
		});
		data.resultSerch=resultSerch.join(',');
		var audit_queue_id='${param.audit_queue_id}';//队列id
		if(audit_queue_id){data.audit_queue_id=audit_queue_id;}
		var audit_times=$("[name='option_audit_times']").val();//第几次执行
		if(audit_times){data.audit_times=audit_times;}
		$("#datatable").params(data);
		$("#datatable").refresh();
		if(drow == 0){
			getStatistics(audit_queue_id,audit_times);//统计信息
			drow++;
		}
		
	}
	//填充第几次执行下拉框
	function addAuditTimes(){
		var times=$("[name='audit_times']").val();
		for(var i=times;i>0;i--){
			$("[name='option_audit_times']").append("<option value="+i+">"+i+"</option>");
		}
	}
	
	//查看患者
	function toPatient(patient_id, visit_id, _this){
		$(_this).css('color', '#900987');
		$.modal('/w/hospital_common/patient/index.html?patient_id='+patient_id+'&visit_id='+visit_id+'',"查看患者详情",{
		  	width: '900px',
		  	height: '550px',
		    callback : function(e){}
		  });
	}
	//获取统计信息
	function getStatistics(audit_queue_id,audit_times){
		$.call("hospital_common.afaudit.getStatistics",{
			audit_queue_id:audit_queue_id,
			audit_times:audit_times
		},function(rtn){
			var data_count=rtn.data_count,sum_y=rtn.sum_y,sum_n=rtn.sum_n,sum_t=rtn.sum_t,sum_q=rtn.sum_q,
			    max_elapsed_time=rtn.max_elapsed_time/1000,min_elapsed_time=rtn.min_elapsed_time/1000,
			    avg_elapsed_time=rtn.avg_elapsed_time/1000,sum_elapsed_time=rtn.sum_elapsed_time/1000,
			    ok_data_count=Number(sum_y)+Number(sum_n)+Number(sum_t)+Number(sum_q),//已审
			    wait_data_count=data_count-ok_data_count;//待审
			var data = {
			    		data_count:data_count,sum_y:sum_y,sum_n:sum_n,sum_t:sum_t,sum_q:sum_q,
				        max_elapsed_time:max_elapsed_time,min_elapsed_time:min_elapsed_time,
					    avg_elapsed_time:avg_elapsed_time,sum_elapsed_time:sum_elapsed_time,
					    ok_data_count:ok_data_count,wait_data_count:wait_data_count
					  };
			highcharts(data);//更新统计图表
			$("#sta_max_elapsed_time").text(max_elapsed_time.toFixed(3)+"s");
			$("#sta_min_elapsed_time").text(min_elapsed_time.toFixed(3)+"s");
			$("#sta_avg_elapsed_time").text(avg_elapsed_time.toFixed(3)+"s");
			$("#sta_sum_elapsed_time").text(sum_elapsed_time.toFixed(3)+"s");
			changeStatisticsColor($("#sta_max_elapsed_time"),max_elapsed_time);
			changeStatisticsColor($("#sta_min_elapsed_time"),min_elapsed_time);
			changeStatisticsColor($("#sta_avg_elapsed_time"),avg_elapsed_time);
			changeStatisticsColor($("#sta_sum_elapsed_time"),avg_elapsed_time);
		});
	}
	//统计耗时样式
	function changeStatisticsColor(obj,time){//obj 改变样式的对象，time 耗时
		var color="#696969";
        if(time>20){color="#CC0033";}
        else if(time>=0){color="black";}
        $(obj).css("color",color);
	}
	
	//审查详情
	function auditDetail(common_id, _this){
		$(_this).css('color', '#840fa9');
		$.modal("audit_detail.html?id="+ common_id,"查看审查详情",{
		   	width: '936px',
		   	height: '600px',
	        callback : function(e){}
	    });
	}
	
	//查看医嘱
	function druglist(patient_id,visit_id, _this){
		$(_this).css('color', '#900987');
		$.modal('/w/hospital_common/afaudit/druglist.html?patient_id='+ patient_id+"&visit_id="+ visit_id,"查看医嘱",{
		  	width: '900px',
		  	height: '550px',
		    callback : function(e){}
		});
	}
	//highchars图表
    function highcharts(rtn){
	    var categories = ["已审","待审"],
		    yscolor=['#7CFC00','#FF7F50','#FFD700','#00BFFF'],//已审的颜色，根据categories的顺序
		    data = [
		    	{
		            "y": parseInt(rtn.ok_data_count),
		            "color": '#90ED7D',
		            "drilldown": {
		                "name": "已审",
		                "categories": ["通过","驳回","提示","待决策"],
		                "data": [parseInt(rtn.sum_y),parseInt(rtn.sum_n),parseInt(rtn.sum_t),parseInt(rtn.sum_q)]
		            }
		        },
		        {
		            "y": parseInt(rtn.wait_data_count),
		            "color": '#696969',
		            "drilldown": {
		                "name": "待审",
		                "categories": ["待审"],
		                "data": [parseInt(rtn.wait_data_count)]
		            }
		        }
		    ],
		    browserData = [],versionsData = [],i,j,dataLen = data.length,drillDataLen,brightness;
		for (i = 0; i < dataLen; i += 1) {
		    browserData.push({
			    name: categories[i],
			    y: data[i].y,
			    color: data[i].color
			});
	    }
		drillDataLen = data[0].drilldown.data.length;//已审外圈
		for (j = 0; j < drillDataLen; j += 1) {
		    versionsData.push({
		        name: data[0].drilldown.categories[j],
		        y: data[0].drilldown.data[j],
		        color: yscolor[j]
		    });
		}
		versionsData.push({//未审外圈
	        name: data[1].drilldown.categories[0],
	        y: data[1].drilldown.data[0],
	        color: '#696969'
	    });
		$('#charts').highcharts({
		    chart: {type: 'pie'},
			title: {text: '审查总数：'+rtn.data_count},
			tooltip:{formatter: function () {
				if(this.point.name=='待审'){
					return '100%';
				}else{
					return (this.point.y*100/rtn.ok_data_count).toFixed(1)+'%';
				}
			}},//鼠标移入显示
			plotOptions: {
			    pie: {
			        allowPointSelect:true,//选中某块区域是否允许分离
			        cursor:'pointer',
			        shadow: false,
			        center: ['50%', '50%'],
			        dataLabels:{
			        	enabled:true,//是否直接呈现数据 也就是外围显示数据与否
			        	format:'{point.name}:{point.y}'
			        }
			    }
			},
			series: [{
			    name: '记录数',
			    data: browserData,
			    size: '40%',
			    dataLabels: {
		            color: '#ffffff',
		            distance: -5//显示距离
		        }
			}, {
			    name: '记录数',
			    data: versionsData,
			    size: '55%',
			    innerSize: '40%',
			    dataLabels: {
		            color: '#ffffff',
		            distance: 3
		        }
		    }]
	    });
	}
	
	function imic_log(common_id){
		$.modal('/w/hospital_imic/auditresult/info.html',"查看医保审查日志",{
		  	width: '950px',
		  	height: '600px',
		  	id: common_id,
		    callback : function(e){}
		});
	}
	
	function ipc_log(common_id){
		$.modal('/w/hospital_common/auditrecord/info.html',"查看合理用药审查日志",{
		  	width: '950px',
		  	height: '600px',
		  	id: common_id,
		    callback : function(e){}
		});
	}
</script>