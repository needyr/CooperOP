<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" dispermission="true">
	<link href="${contextpath}/theme/plugins/fullcalendar/fullcalendar.css" rel="stylesheet" />
	<script src="${contextpath}/theme/plugins/moment.min.js"></script>
	<script src="${contextpath}/theme/plugins/fullcalendar/fullcalendar.min.js" type="text/javascript"></script>
	<script src="${contextpath}/theme/plugins/jquery-easypiechart/jquery.easypiechart.min.js" type="text/javascript"></script>
	<script src="${contextpath}/theme/plugins/jquery.sparkline.min.js" type="text/javascript"></script>
	<script src="${contextpath}/theme/plugins/fullcalendar/lang/zh-cn.js" type="text/javascript"></script>
	<script src="${contextpath}/theme/scripts/metronic.js" type="text/javascript"></script>
	<link href="${contextpath}/res/oa/css/pc/calendar.css" rel="stylesheet" type="text/css" >
	
	<div class="cr-calendar">
		<div class="calendar-btn">
			<button onclick="changeType(this,'basicDay')" >日</button>
			<button onclick="changeType(this,'basicWeek')">周</button>
			<button onclick="changeType(this,'month')" class="active">月</button>
		</div>
		<div id="calendar"></div>
	</div>

</s:page>

<script type="text/javascript">
var currentView = 'month';
var height = ($("body").height());
$(document).ready(function() {
	init(currentView);
	setInterval(function () {
		if(!$("#calendar").is(":hidden")){
			var current_date = $('#calendar').fullCalendar('getDate').format("YYYY-MM-DD");
			init(currentView, current_date);
		}
	}, 5000);
})
function changedate1(){
	var current_date = $('#calendar').fullCalendar('getDate').format("YYYY-MM-DD");
	init(currentView, current_date);
}
function init(time, cdate) {
	if (!jQuery().fullCalendar) {
        return;
    }
    var date = new Date();
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();
    var h = {};
    if (Metronic.isRTL()) {
        if ($('#calendar').parents(".portlet").width() <= 720) {
            $('#calendar').addClass("mobile");
            h = {right: 'title, prev, next',center: '',left: 'agendaDay, agendaWeek, month, today'};
        } else {
            $('#calendar').removeClass("mobile");
            h = {right: 'title',center: '',left: 'agendaDay, agendaWeek, month, today, prev,next'};
        }
    } else {
        if ($('#calendar').parents(".portlet").width() <= 720) {
            $('#calendar').addClass("mobile");
            h = {left: '',center: 'title, prev, next',right: ''};
         } else {
             $('#calendar').removeClass("mobile");
             h = {left : '',center: 'title',right: ''};
         }
    }
        
    $('#calendar').fullCalendar('destroy'); // destroy the calendar
    $('#calendar').fullCalendar({ //re-initialize the calendar
        header: h,
        defaultDate: cdate || moment(new Date()).format("YYYY-MM-DD"),
        height: height,
        weekMode : 'variable',//不固定周数，但高度固定
        defaultView: time, // change default view with available options from http://arshaw.com/fullcalendar/docs/views/Available_Views/ 
        eventSources : function(start, end, timezone, callback) {
			$('#calendar').fullCalendar('removeEvents');
			var sdate = moment(new Date(start)).format("YYYY-MM-DD");
			var edate = moment(new Date(end)).format("YYYY-MM-DD");
			var events = [];
			$.call("oa.calendar.calendar.query",{sdate: sdate, edate: edate},function(rtn){
				var data=rtn.resultset;
				for(var i=0 ;i<data.length; i++){
					var d = data[i];
					var date = d.gongli;
					$('#calendar').find("td.fc-day[data-date="+date+"]").css("vertical-align", "bottom");
					var html = [];
					html.push(' <div class="days work-status '+(d.workday != 1 ? "xiu" : "")+'">');
					//农历
					html.push('		<div class="day-num">');
					html.push('			<div class="date-lunar" title="'+d.daxie+'">'+d.daxie.slice(-3)+'</div>');
					html.push('			<span>'+date.slice(-2)+'</span>');
					html.push('		</div>');
					//放休图标
					html.push(' 	<div class="iswork">');
					html.push(' 	</div>');
					//放事件
					if(d.task_num > 0 || d.memo_num > 0 || d.remind_num > 0){
						$('#calendar').find("td.fc-day[data-date="+date+"]").attr("has-remind",'true');
						html.push(' <div class="remind-tips">');
						if(d.task_num > 0)
							html.push('<a href="#" title="任务" onclick="showTask(\''+date+'\')"><i class="cicon icon-clock"></i>'+d.task_num+'</a>');
						if(d.memo_num > 0)
							html.push('<a href="#" title="记事" onclick="showMemo(\''+date+'\')"><i class="cicon icon-star3"></i>'+d.memo_num+'</a>');
						if(d.remind_num > 0)
							html.push('<a href="#" title="提醒" onclick="showRemind(\''+date+'\')"><i class="cicon icon-ling2"></i>'+d.remind_num+'</a>');
						html.push(' </div>');
					}
					//备注
                    if(data[i].ev_remark){
                        html.push('<div class="remark">');
                        html.push('    <span title="'+data[i].ev_remark+'">'+data[i].ev_remark+'</span>');
                        html.push('</div>');
                    }
					html.push(' </div>');
					$('#calendar').find("td.fc-day.fc-widget-content[data-date='"+date+"']").append(html.join('')); 
				}
			},null,{async:false,nomask:true});
			callback(events);
        },
        dayClick:function(date, allDay, jsEvent,view) {
        	if(this.getAttribute("has-remind")){//有提醒的时候return
        		return;
        	}
        	//执行打开页面方法
        	addMemo('memo',date);
        },
     },null,{async:false});
}
//更改日历单位
function changeType(t,time){
	if(!$(t).hasClass('active')){
		$(t).parent().find('.active').removeClass('active');
		$(t).addClass('active');
		if(time == 'month'){
			init(time);
		}else{
			initCalendar(time);
		}
		currentView = time;
	}
}



//初始化周、日日历
function initCalendar(time, cdate) {
	if (!jQuery().fullCalendar) {
        return;
    }
    var date = new Date();
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();
    var h = {};
    var begin_date ;
    var end_date ;
    if (Metronic.isRTL()) {
        if ($('#calendar').parents(".portlet").width() <= 720) {
            $('#calendar').addClass("mobile");
            h = {right: 'title, prev, next',center: '',left: 'agendaDay, agendaWeek, month, today'};
        } else {
            $('#calendar').removeClass("mobile");
            h = {right: 'title',center: '',left: 'agendaDay, agendaWeek, month, today, prev,next'};
        }
    } else {
        if ($('#calendar').parents(".portlet").width() <= 720) {
            $('#calendar').addClass("mobile");
            h = {left: '',center: 'title, prev, next',right: ''};
         } else {
             $('#calendar').removeClass("mobile");
             h = {left : '',center: 'title',right: ''};
         }
    }
    $('#calendar').fullCalendar('destroy'); // destroy the calendar
    $('#calendar').fullCalendar({ //re-initialize the calendar
        header: h,
        height: height,
        weekMode : 'variable',//不固定周数，但高度固定
        defaultView: time, // change default view with available options from http://arshaw.com/fullcalendar/docs/views/Available_Views/ 
        defaultDate: cdate || moment(new Date()).format("YYYY-MM-DD"),
        eventSources : function(start, end, timezone, callback) {
			$('#calendar').fullCalendar('removeEvents');
			begin_date = moment(new Date(start)).format("YYYY-MM-DD");
			end_edate = moment(new Date(end)).format("YYYY-MM-DD");
			var events = [];
		    $.call("oa.calendarEvent.query",{begin_date: begin_date, end_date: end_date},function(rtn){
				calendarEvents = rtn.resultset;
				for(var i in calendarEvents){
					var html = [];
					var time = formatDate(new Date(calendarEvents[i].expire_time),'yyyy-MM-dd');
					var type = calendarEvents[i].dl;
					var dom = $('#calendar').find("td.fc-day.fc-widget-content[data-date='"+time+"']")
					if(dom.find(".matter-box").length == 0){
						html.push('<div class="matter-box" data-date="'+time+'"></div>');
						dom.append(html.join('')); 
					}
					if('remind' == type){
						if(dom.find(".msbox-remind").length == 0){
							html = [];
							html.push(' <dl  class="msbox-'+type+'">');
							html.push('		<dt><span class="event-type">'+calendarEvents[i].dlmc+'</span></dt>');
							html.push(' </dl>');
							dom.find(".matter-box").append(html.join(''));
						}
						html = [];
						html.push('<dd title="'+calendarEvents[i].subject+'" data-id="'+calendarEvents[i].data_id+'">');
// 						html.push('	<span class="event-xl">['+calendarEvents[i].xlmc+']</span>');
						html.push('	<span class="event-subject">'+calendarEvents[i].subject+'</span>');
// 						html.push('	<span class="btnbox">');
// 						html.push('		<a class="showdetail" data-id="'+calendarEvents[i].data_id+'" href="#">查看</a>');
// 						html.push('	</span>');
						html.push('</dd>');
						dom.find(".msbox-remind").append(html.join(''));
					}
					if('task' == type){
						if(dom.find(".msbox-task").length == 0){
							html = [];
							html.push(' <dl  class="msbox-'+type+'">');
							html.push('		<dt><span class="event-type">'+calendarEvents[i].dlmc+'</span></dt>');
							html.push(' </dl>');
							dom.find(".matter-box").append(html.join(''));
						}
						html = [];
						html.push(' <dd title="'+calendarEvents[i].subject+'">');
// 						html.push('		<span class="event-xl">['+calendarEvents[i].xlmc+']</span>');
						html.push('		<span class="event-subject">'+calendarEvents[i].subject+'</span>');
						html.push(' </dd>');
						dom.find(".msbox-task").append(html.join(''));
					}
					if('note' == type){
						if(dom.find(".msbox-note").length == 0){
							html = [];
							html.push('<dl  class="msbox-'+type+'">');
							html.push('	<dt><span class="event-type">'+calendarEvents[i].dlmc+'</span>');
// 							html.push('		<span class="btnbox"><a href="#" data-date="'+time+'"><i class="cicon icon-add"></i></a></span>');
							html.push('	</dt>');
							html.push('</dl>');
							dom.find(".matter-box").append(html.join(''));
						}
						html = [];
						html.push(' <dd  title="'+calendarEvents[i].subject+'">');
// 						html.push('		<span class="event-xl">['+calendarEvents[i].xlmc+']</span>');
						html.push('		<span class="event-subject">'+calendarEvents[i].subject+'</span>');
// 						html.push('		<span class="box-btn">');
// 						html.push('			<a class="modify" data-id="'+calendarEvents[i].data_id+'" href="#"><i class="cicon icon-edit"></i></a>');
// 						html.push('			<a class="del" data-id="'+calendarEvents[i].data_id+'" href="#"><i class="cicon icon-del"></i></a>');
// 						html.push('		</span>');
						html.push(' </dd>');
						dom.find(".msbox-note").append(html.join(''));
					}
				}
				//事件-查看提醒
				$(".msbox-remind").find("dd").on("click", function(){
					$.modal(cooperopcontextpath + "/w/oa/notice/detail.html", "", {
						id : $(this).attr("data-id")
					})
				})
				//事件-点击memo的dl
				$(".msbox-note").on("click", function(){
					var userid = userinfo.id;
					var date = $(this).parent().attr("data-date");
					$.modal("./memoList.html",time+ "备忘", {
						width : '800px',
						height : '600px',
						memo_time : date,
						userid : userid,
						callback : function(rtn) {
							if(rtn){
								init(currentView);
							}
					    }
					})
				})
				//事件-点击task的dl
				$(".msbox-task").on("click", function(){
					$.openTabPage("oa.calendar.calendar.task","待办事项",cooperopcontextpath +"/w/oa/calendar/calendar/task.html?time="+time, true);
				})
			},null,{async:false});
			callback(events);
        },
     });
}

function showMemo(time){
	var userid = userinfo.id;
	$.modal("./memoList.html",time+ "备忘", {
		width : '800px',
		height : '600px',
		memo_time : time,
		userid : userid,
		callback : function(rtn) {
			if(rtn){
				init(currentView);
			}
	    }
	})
}

function showTask(time){
	$.openTabPage("oa.calendar.calendar.task","待办事项",cooperopcontextpath +"/w/oa/calendar/calendar/task.html?time="+time, true);
}

function showRemind(time){
	$.modal("./remindList.html", "提醒列表", {
		width : '800px',
		height : '600px',
		begin_date : time,
		end_date : time,
		callback : function(rtn) {
			if(rtn){
				init(currentView);
			}
	    }
	})
}


function addMemo(type,date){
	if("memo" == type){
		$.modal("add.html", "新增备忘", {
			width : '800px',
			height : '600px',
			memo_time : date,
			callback : function(rtn) {
				if(rtn)
					init(currentView);
		    }
		});
	}
}
</script>