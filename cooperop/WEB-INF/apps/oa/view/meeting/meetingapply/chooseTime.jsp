<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="">
	<script src="${contextpath}/theme/plugins/moment.min.js"></script>
	<script src="${contextpath}/theme/plugins/fullcalendar/fullcalendar.min.js" type="text/javascript"></script>
	<script src="${contextpath}/theme/plugins/jquery-easypiechart/jquery.easypiechart.min.js" type="text/javascript"></script>
	<script src="${contextpath}/theme/plugins/jquery.sparkline.min.js" type="text/javascript"></script>
	<script src="${contextpath}/theme/plugins/fullcalendar/lang/zh-cn.js" type="text/javascript"></script>
	<script src="${contextpath}/theme/scripts/metronic.js" type="text/javascript"></script>
	<link href="${contextpath}/theme/plugins/fullcalendar/fullcalendar.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="${contextpath}/res/oa/css/pc/meetingcalendar.css">
<style type="text/css">
.fc_workday{
	background-color: #51a351!important;
	color: #FFFFFF!important;
}

.fc-content > div {
	display: none !important;
}
</style>
	<s:row style="height:0px;">
		<s:form id="settingForm">
			<s:row>
				<input type="hidden" name="meeting_id" value="${param.meetroom }" />
			</s:row>
		</s:form>
	</s:row>
	<s:row style="" id="calendarshow">
		<div style="margin: 20px 20px 5px 20px;">
			<div id="calendar" class="has-toolbar">
			</div>
		</div>
	</s:row>
</s:page>
<script type="text/javascript">
$(document).ready(function(){
	calendarinit();
})
function calendarinit(){
	if (!jQuery().fullCalendar) {
	    return;
	}
	var weeks = [];
	var date = new Date();
	var d = date.getDate();
	var m = date.getMonth();
	var y = date.getFullYear();
	var h = {};

	if (Metronic.isRTL()) {
	    if ($('#calendar').parents(".portlet").width() <= 720) {
	        $('#calendar').addClass("mobile");
	        h = {
	            right: 'title, prev, next',
	            center: '',
	            left: 'agendaDay, agendaWeek, month, today'
	        };
	    } else {
	        $('#calendar').removeClass("mobile");
	        h = {
	            right: 'title',
	            center: '',
	            left: 'agendaDay, agendaWeek, month, today, prev,next'
	        };
	    }
	} else {
	    if ($('#calendar').parents(".portlet").width() <= 720) {
	        $('#calendar').addClass("mobile");
	        h = {
	            left: '',
	            center: '',
	            right: 'title, prev, next'
	        };
	    } else {
	        $('#calendar').removeClass("mobile");
	        h = {
	       	 left : '',
	            center: 'title',
	            right: ''
	        };
	    }
	}
	var da = $("#settingForm").getData();
		     $('#calendar').fullCalendar('destroy'); // destroy the calendar
		     $('#calendar').fullCalendar({ //re-initialize the calendar
		         header: h,
		      	 allDay: false,
		      	allDaySlot :false,
		      	allDayDefault: false,
		    	//  minTime:1,
		      	//maxTime:11,
		         defaultView: 'agendaWeek', // change default view with available options from http://arshaw.com/fullcalendar/docs/views/Available_Views/ 
		         slotMinutes: 30,
		        // events: meeting,
		         eventSources : function(start, end, timezone, callback) {
					$('#calendar').fullCalendar('removeEvents');
					var sdate = moment(new Date(start)).format("YYYY-MM-DD");
					var edate = moment(new Date(end)).format("YYYY-MM-DD");
					var events = [];
					da.sdate = sdate;
					da.edate = edate;
			        $.call("oa.meeting.meetingapply.query",da,function(rtn){
						var room_id = $("#settingForm").getData().meetroom;
						var meeting = [];
						var list = rtn.resultset;
						for(var i = 0 ;i<list.length;i++){
							var now = (new Date()).getTime();
							var color="red";//进行中
							if(list[i].end_time < now){//已结束
								color="gray";
							}else if(list[i].start_time > now){//未开始
								color="green";
							}
							var event = {
								id: list[i].id,
					            title: list[i].name,
								creator_id : list[i].creator,
								creator_name : list[i].creator_name,
					            start: new Date(list[i].start_time),
					            end: new Date(list[i].end_time),
					            backgroundColor: color
					         }
				            event.className = "event";
							event.allDay= false;
							events.push(event);
						}
						callback(events);
					});
		         },
		         dayClick: function(date, allDay, jsEvent,view) {
		        	 var d = new Date(date);
		        	 var hour = 0;
		        	 if(d.getHours()-8 > 0){
		        		 hour = d.getHours()-8;
		        	 }else{
		        		 hour = d.getHours()+16;
		        	 }
		         	$.modal("add.html", "新增", {
		    			width : '800px',
		    			height : '600px',
		    			meeting_id : da.meeting_id,
		    			start_time: d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate()+" "+hour+":"+d.getMinutes(),
		    			callback : function(rtn) {
		    				if(rtn){
		    					calendarinit();
		    				}
		    		    }
		    		});
			     },
			     eventClick: function(event) {
			    	 if(userinfo.id == event.creator_id){
				    	 $.modal("add.html", "修改", {
			    			width : '800px',
			    			height : '600px',
			    			id : event.id,
			    			callback : function(rtn) {
			    				if(rtn){
			    					calendarinit();
			    				}
			    		    }
			    		});
			    	 }else{
			    		 $.modal("tips.html", "提示", {
				    			width : '400px',
				    			height : '110px',
				    			start : event.start,
				    			end : event.end,
				    			creator : event.creator_name
				    	});
			    	 }
		         }
		     });
		$("#calendarshow").show();
}
</script>