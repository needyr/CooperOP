<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<style>
.mytable{
    table-layout: fixed;
    empty-cells: show;
    border-collapse: collapse;
    margin: 0 auto;
    border: 1px solid #c1c1c1;
    color: #666;
    width: 100%; 
}

.mytable{
	padding-bottom: 10px;
}
/* .mytable{
	border: 1px solid #c1c1c1;
} */
.mytable>tbody>tr:hover{
    background-color: #e6e6e7 !important;
    cursor: pointer;
}

.mytable tr >th{
    border: 1px solid #c1c1c1;
   	padding: 5px 3px;
    background: #f1f1f1;
    height: 30px;
    font-size: 12px;
}

.mytable>tbody>tr>td{
    border: 1px solid #c1c1c1;
    padding: 5px 3px;
    font-size: 12px;
    line-height: 20px;
    word-break:break-all
}

.mytable>tbody>tr{

}

.mytable>tbody>tr:nth-child(2n){
    background-color: #f9f9f9;
}

.mytable>tbody>tr.active,
.mytable>tbody>tr:nth-child(2n).active {
    background-color: #414141 !important;
    color: #fff;
}
.tbody_Div{
	overflow-x: auto;
    overflow-y: scroll;
	border: 1px solid #c1c1c1;
	height:calc(100% - 45px);
}
.tbth{
	width: 8px;
}

/*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
::-webkit-scrollbar
{
    width: 8px;
    height: 10px;
    background-color: #F5F5F5;
}
 
/*定义滚动条轨道 内阴影+圆角*/
::-webkit-scrollbar-track
{
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
    border-radius: 10px;
    background-color: #F5F5F5;
}
 
/*定义滑块 内阴影+圆角*/
::-webkit-scrollbar-thumb
{
    border-radius: 10px;
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
    background-color: #cecccc;
}
.thead_Div{
	overflow: hidden;
	/* border-bottom: 1px solid #c1c1c1; */
}
</style>
<s:page title="TPN详细信息" disloggedin="true">
<s:row>
	<div style="height: 90%;width: 100%;float: left;">
		<div id="container_table" style="height: calc(90% + 14px);width: 30%;z-index: 2;background-color: rgb(239, 242, 245);position: absolute;">
			<div class="thead_Div" id="table_thead" >
					<table class="mytable">
						<thead>
							<tr>
								<th width="50px">超标</th>
								<th width="200px">项目名称</th>
								<th width="80px">指标量</th>
								<th width="200px">指标范围</th>
								<th class="tbth"></th>
							</tr>
						</thead>
					</table>
				</div>
				<div class="tbody_Div" id="table_tbody" >
					<table class="mytable" id="table_content" >
						<tbody>
							<!-- <tr>
								<td width="200px">1</td>
								<td width="80px">1</td>
								<td width="200px">1</td>
								<td width="30px">1</td>
							</tr> -->
						</tbody>
					</table>
				</div>
		</div>
		<div id="container_leida" style="height: 90%;width: 70%;z-index: 1;position: absolute;margin-left: 30%;">
		</div>
	</div>
	<!-- <div style="height: 50%;width: 50%;float: right;">
		<div id="container_line" style="height: 95%;width: 100%;">
		</div>
		<div id="container_pie" style="height: 95%;width: 100%;">
		</div>
	</div> -->
</s:row>
</s:page>
<script type="text/javascript">
//需要修改
var patient_id = '${patient_id}';
var visit_id = '${visit_id}';
var group_id = '${group_id}';
var order_no = '${order_no}'; 
/* var patient_id = '10011';
var visit_id = '1';
var group_id = '1';
var order_no = '1#1'; */

var data = {};
data.patient_id = patient_id;
data.visit_id = visit_id;
data.order_no = order_no;
data.group_id = group_id;
data.auto_audit_id = '${auto_audit_id}';
//console.log(data)
var timer = null;
$(function (){
	$('#container_table #table_tbody').on({
        mouseover : function(){
        	$('#container_table').css('width','80%')
        },
        mouseout : function(){
        	$('#container_table').css('width','30%')
        } 
    });
});

$.call('hospital_common.showturns.queryTpn',data,function(rtn){
var value = eval('('+rtn+')');
//雷达
var radar_data = value.radar;
if(radar_data){
	console.log(radar_data)
	var leida = Highcharts.chart('container_leida', {
		chart: {
			polar: true,
			type: 'line'
		},
		title: {
			text: 'TPN指标雷达图',
			x: -50
		},
		credits: {
			enabled:false
		},
		pane: {
			size: '80%'
		},
		xAxis: {
			categories: radar_data.x_name,
			tickmarkPlacement: 'on',
			lineWidth: 0,
			labels: {
				style: {
					color: 'black',
					fontSize: "10px"
				}
			}
		},
		yAxis: {
			gridLineInterpolation: 'polygon',
			lineWidth: 0,
			min: 0
		},
		tooltip: {
			shared: true,
			pointFormat: '<span style="color:{series.color}">{series.name}:<b>{point.real_y}</b><br/>'
		},
		colors: [
	     	'rgb(157, 197, 253)',
	     	'rgb(236, 236, 236)',
	        'rgb(190, 255, 189)'
	    ],
		legend: {
			align: 'right',
			verticalAlign: 'top',
			y: 70,
			layout: 'vertical'
		},
		series: [{
			type: 'area',
			name: '最大值',
			data: radar_data.show_max_list,
			pointPlacement: 'on',
			marker:{
				radius: '2',
				states:{
					hover:{enabled:true,radius: '3'}
				}
			}
		}, 
		{
			name: '最小值',
			type: 'area',
			data: radar_data.show_min_list,
			pointPlacement: 'on',
			marker:{
				radius: '2',
				states:{
					hover:{enabled:true,radius: '3'}
				}
			}
		},
		{
			name: '实际值',
			type: 'area',
			data: radar_data.show_current_list,
			pointPlacement: 'on',
			marker:{
				radius: '2',
				states:{
					hover:{enabled:true,radius: '3'}
				}
			}
		},]
	});
}

//饼
var pie_data = value.pie;
if(pie_data){
	var pie = Highcharts.chart('container_pie', {
		chart: {
			plotBackgroundColor: null,
			plotBorderWidth: null,
			plotShadow: false,
			type: 'pie'
		},
		credits: {
			enabled:false
		},
		title: {
			text: '营养配比图'
		},
		tooltip: {
			pointFormat : '<b>{point.name}</b>: {point.percentage:.1f} %'
		},
		plotOptions: {
			pie: {
				allowPointSelect: true,
				cursor: 'pointer',
				dataLabels: {
					enabled: true,
					distance:-15,
					format: '<b>{point.name}</b>',
					style: {
						color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
					}
				}
			}
		},
		series: [{
			colorByPoint: true,
			name: ' ',
			data: pie_data
		}]
	})
}

//曲线
var line_data = value.line;
if(line_data){
	var line = Highcharts.chart('container_line', {
		title: {
			text: '磷酸钙沉淀图'
		},
		subtitle: {
			text: ''
		},
		credits: {
			enabled:false
		},
		tooltip: {
			shared: true,
			formatter : function (){ // 提示框格式化字符串
				var po = this.points;
				var html = '';
				$.each(po,function(index){
					html = html + '<span>【'+this.series.name+'】'+this.point.series.xAxis.axisTitle.textStr+':<b>'+po[index].x+'</b></span><br/><span>【'+this.series.name+'】'+this.point.series.yAxis.axisTitle.textStr	+':<b>'+po[index].y+'</b></span><br/>'
				})
	            return html;
	        },
		},
		yAxis: {
			title: {
				text: '钙<br>mmol/L'
			}
		},
		xAxis: {
			title: {
				text: '磷<br>mmol/L'
			}
			//categories: [10,20,30,40,50]
		},
		legend: {
			layout: 'vertical',
			align: 'right',
			verticalAlign: 'middle'
		},
		series: [{
			name: '正常曲线',
			data: line_data.value
		},{
			name: '当前值',
			data: [line_data.current],
			marker:{
				symbol:'triangle',
				radius: '8',
				fillColor: 'red',
				states:{
					hover:{
						enabled:true,
						radius: '9'
					}
				}
			}
		}]
})
}

//描绘表
if(value.radar && value.radar.table){
	var table_data = value.radar.table;
	var html = [];
	$.each(table_data,function(i){
		/*if(table_data[i].value_current>table_data[i].value_highest){
			html.push('<tr style="background-color: #ffc6c6 !important;">');
		}
		else if(table_data[i].value_current<table_data[i].value_lowest){
			html.push('<tr style="background-color: #c6edff !important;">');
		}
		else{
			html.push('<tr>');
		}*/
		html.push('<tr>');
		if(table_data[i].value_current>table_data[i].value_highest){
			html.push('<td width="50px" style="color:red;font-size: 20px;">↑</td>');	
		}
		else if(table_data[i].value_current<table_data[i].value_lowest){
			html.push('<td width="50px" style="color:red;font-size: 20px;">↓</td>');	
		}
		else{
			html.push('<td width="50px"></td>');
		}
		html.push('<td width="200px">'+table_data[i].show_fdname+'</td>');
		html.push('<td width="80px">'+table_data[i].value_current+'</td>');
		html.push('<td width="200px">'+table_data[i].value_lowest+' ~ '+table_data[i].value_highest+'</td>');
		html.push('</tr>');
	})
	$('#table_content').append(html.join());
}
})

function syncMove(theadId, tbodyId){
	try{
	document.getElementById(tbodyId).onscroll=function(e) {
		document.getElementById(theadId).scrollLeft = document.getElementById(tbodyId).scrollLeft;
	}}catch(err){
		console.log('onscroll is error, don\'t worry ');
	}
}
syncMove('table_thead','table_tbody');

</script>