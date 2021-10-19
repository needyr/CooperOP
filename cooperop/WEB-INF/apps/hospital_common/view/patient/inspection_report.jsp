<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="cn.crtech.cooperop.bus.util.GlobalVar"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="" disloggedin="true">
<div id="container" style="width:80%;"></div>
</s:page>
<script type="text/javascript">
	$(function(){
		$('#container').css('height', window.innerHeight - 50);
		var report_item_code = '${param.report_item_code}';
		$.call('hospital_common.patient.insp_report',{report_item_code:report_item_code,
		patient_id: '${param.patient_id}',
		visit_id:'${param.visit_id}'},function(rtn){
			if(rtn && rtn.report){
				var re = rtn.report;
				var x = [];
				var y = [];
				for(var i=0;i<re.length;i++){
					var string = re[i];
					x.push(string.result_date_time);
					y.push(+string.result);
				}
				var chart = Highcharts.chart('container', {
					title: {
						text: '${param.report_item_name}'+'('+'${param.unit}'+')'
					},
					yAxis: {
						title: {
							text: '${param.unit}'
						}
					},
					xAxis: {
						categories: x
					},
					legend: {
						layout: 'vertical',
						align: 'right',
						verticalAlign: 'middle'
					},
					plotOptions: {
						line: {
							dataLabels: {
								// 开启数据标签
								enabled: false          
							},
							// 关闭鼠标跟踪，对应的提示框、点击事件会失效
							enableMouseTracking: true
						}
					},
					series: [{
						name: '检验指标',
						data: y
					}]
				});
			}
		})
	})
</script>