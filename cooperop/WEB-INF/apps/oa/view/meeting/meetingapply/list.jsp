<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="会议室记录" >
    <link rel="stylesheet" type="text/css" href="${contextpath}/res/oa/css/pc/rooms.css">

	<div class="meeting">
		<div class="filter">
			<div class="ms-box">
				<ul class="label-select-s clearfix">
					<li>标签</li>
				</ul>
			</div>
		</div>
		<div class="room-list">
			<div class="column-layout clearfix">
				
			</div>
		</div>
	</div>
</s:page>
<script type="text/javascript">
$(document).ready(function(){
	cinit();
// 	$(".label-select-s").remove();
	/* $(".label-select-s .tags>a").on("click", function(){
		var $t = $(this);
		if($t.hasClass("active")){
			$t.removeClass("active");
			alert($t.text()+"-remove");
		}else{
			$t.addClass("active");
			alert($t.text()+"-add");
		}
	})  */
})
var hasroom = 0;

function cinit(){
	refreshMeetingRooms();
	if(hasroom == 1){
		loadTags();
	}
}

function refreshMeetingRooms(){
	$(".items").remove();
	var actives = $(".label-select-s .active");
	var tags = "";
	actives.each(function(){
		tags += $(this).text()+",";
	})
	$.call("oa.meeting.meetingapply.showList", {tags:tags}, function(rtn){
		if(rtn.count > 0){
			var mr = rtn.resultset;
			var html = [];
			for(var i in mr){
				var room=mr[i];
				html.push("<div class='items'><div class='room-card ms-box'>");
					//使用状态判断
					if(room.online == 1){
						html.push("<span  class='status-tips'>使用中</span>");
					}
					//会议室名
					html.push("<h4 class='title'>"+room.name+"</h4>");
					html.push("<ul class='gs'>");
						//地址
						html.push("<li><i class='cicon icon-location2'></i>"+room.address+"</li>");
						//人数
						html.push("<li><i class='cicon icon-peoples'></i><em>"+room.galleryful+"</em>人</li>");
						//设备  循环
						var facilities=room.facilities;
						html.push("<li class='tag clearfix'>");
						if(facilities.length > 0){
							for(var i in facilities){ 
								html.push("<span>"+facilities[i].name+"</span>");
							}
						}else{
							html.push("<span>暂无设备</span>");
						}
						html.push("</li>");
					//ul结束
					html.push("</ul>");
					//使用状态
					html.push("<div class='status'>");
						html.push("<div class='tab clearfix'>");
							//点击事件
							html.push("<a href='#' onclick='refreshList(this,"+room.id+",0"+")'>我的预约</a>");
							html.push("<a href='#' class='active' onclick='refreshList(this,"+room.id+")'>预约列表</a>");
							html.push("<a href='#' onclick='refreshList(this,"+room.id+",1"+")'>历史列表</a>");
						html.push("</div>");
						var useRecord=room.meetingRecord; 
						if(useRecord.length > 0){
							//会议记录 ul标签开始
							html.push("<ul class='yylist' id='"+'ls'+room.id+"'>")
							for(var i in useRecord){
								var record=useRecord[i];
								var startDate=formatDate(new Date(record.start_time), 'yyyy-MM-dd');
								var start=formatDate(new Date(record.start_time), 'HH:mm');
								var end=formatDate(new Date(record.end_time), 'HH:mm');
								//判断是否进行中
								var now = (new Date()).getTime();
								if(now > record.start_time){
									var progress = (now - record.start_time)/(record.end_time - record.start_time);
									var str=Number(progress*100).toFixed(1);
								    str+="%";
									html.push("<li class='doing'>")
									html.push("<span class='tips'>进行中</span>")
									html.push("<span class='progress' style='width: "+str+"'></span>")
									html.push("<span class='date'>"+startDate+"</span>")
									html.push("<span class='times'>"+start+"~"+end+"</span>"+record.name+"</li>")
								}else{
									html.push("<li>")
									html.push("<span class='date'>"+startDate+"</span>")
									html.push("<span class='times'>"+start+"~"+end+"</span>")
									html.push(record.name+"</li>")
								}
								if(i == 1 && record.number > 2){
									html.push("<div class='align-c'><a class='btn-more' href='#' onclick='moreRecord("+room.id+",\""+room.name+"\")'>更多</a></div>");
								}
							}
							//ul标签结束
							html.push("</ul>")
						}else{
							html.push("<span class='tips-empty'>暂无预约会议</span>");
						}
						//会议室状态完
						html.push("</div>");
						//预约
						html.push("<a class='reserve' href='#' onclick='apply("+room.id+")'>立即预约</a>")
					html.push("</div>");
				html.push("</div>");
			}
			$(".column-layout").append(html.join(""));
			hasroom = 1;
		}else{
			if(tags){
				$.message("没有符合条件的会议室!");
			}else{
				$.error("还没有创建会议室！");
			}
		}
	},null,{async:false})
}

function loadTags(){
	$.call("oa.meeting.meetingTags.queryDistinct", {}, function(rtn){
		if(rtn.count > 0){
			$(".label-select-s").find(".tags").remove();
			var bq = rtn.resultset;
			var html = [];
			for(var i in bq){
				html.push('<li class="tags"><a href="#">'+bq[i].name+'</a></li>');
			}
			$(".label-select-s").append(html.join(""));
			$(".label-select-s .tags>a").on("click", function(){
				var $t = $(this);
				if($t.hasClass("active")){
					$t.removeClass("active");
				}else{
					$t.addClass("active");
				}
				refreshMeetingRooms();
			})
		}else{
			$(".label-select-s").remove();
		}
	},null,{async:false})
}


//会议室申请
function apply(id){
	$.modal("chooseTime.html", "会议室预约", {
		width : '800px',
		height : '600px',
		meetroom : id,
		callback : function(rtn) {
			cinit();
	    }
	});
}

function refreshList(t,room_id,flag){
	if(!$(t).hasClass("active")){
		$(t).parent().find(".active").removeClass("active");
		$(t).addClass("active");
		$("#ls"+room_id).empty();
		var status=$(t).parent().parent();
		//移除 暂无预约会议
		if(status.find("span").hasClass("tips-empty")){
			status.find("span").remove();
		}
		var html = [];
		if(flag == 0){//查找自己的预约
			var userid=userinfo.id;
			$.call("oa.meeting.meetingapply.queryLimitSubscribeList", {meeting_id:room_id,filter:userid,after:1}, function(rtn){
				if(rtn.count > 0){
					var useRecord = rtn.resultset;
					//会议记录 ul标签开始
					for(var i in useRecord){
						var record=useRecord[i];
						var startDate=formatDate(new Date(record.start_time), 'yyyy-MM-dd');
						var start=formatDate(new Date(record.start_time), 'HH:mm');
						var end=formatDate(new Date(record.end_time), 'HH:mm');
						//判断是否进行中
						var now = (new Date()).getTime();
						if(now > record.start_time){
							var progress = (now - record.start_time)/(record.end_time - record.start_time);
							var str=Number(progress*100).toFixed(1);
						    str+="%";
							html.push("<li class='doing'>")
							html.push("	<span class='tips'>进行中</span>")
							html.push("	<span class='progress' style='width: "+str+"'></span>")
							html.push("<span class='date'>"+startDate+"</span>")
							html.push("	<span class='times'>"+start+"~"+end+"</span>")
							html.push(record.name+"</li>")
						}else{
							html.push("<li>")
							html.push("<span class='date'>"+startDate+"</span>")
							html.push("<span class='times'>"+start+"~"+end+"</span>")
							html.push(record.name+"</li>")
						}
						if(i == 1 &&  record.number> 2){//数量超过两条
							html.push("<div class='align-c'><a class='btn-more' href='#' onclick='moreRecord("+room_id+",\""+record.meeting_name+"\")'>更多</a></div>");
						}
					}
				}else{//增加 暂无预约会议
					status.append("<span class='tips-empty'>暂无预约会议</span>");
				}
			},null,{async:false});
		}else if(flag == 1){//历史记录
			$.call("oa.meeting.meetingapply.queryLimitSubscribeList", {meeting_id:room_id,before:1}, function(rtn){
				if(rtn.count > 0){
					var useRecord = rtn.resultset;
					//会议记录 ul标签开始
					for(var i in useRecord){
						var record=useRecord[i];
						var startDate=formatDate(new Date(record.start_time), 'yyyy-MM-dd');
						var start=formatDate(new Date(record.start_time), 'HH:mm');
						var end=formatDate(new Date(record.end_time), 'HH:mm');
						html.push("<li>");
						html.push("<span class='date'>"+startDate+"</span>")
						html.push("<span class='times'>"+start+"~"+end+"</span>");
						html.push(record.name+"</li>")
						if(i == 1 &&  record.number> 2){//数量超过两条
							html.push("<div class='align-c'><a class='btn-more' href='#' onclick='moreRecord("+room_id+",\""+record.meeting_name+"\")'>更多</a></div>");
						}
					}
				}else{//增加 暂无预约会议 若无
					status.append("<span class='tips-empty'>暂无预约会议</span>");
				}
			},null,{async:false});
		}else{//查找预约列表
			$.call("oa.meeting.meetingapply.queryLimitSubscribeList", {meeting_id:room_id,after:1}, function(rtn){
				if(rtn.count > 0){
					//移除 暂无预约会议
					if(status.find("span").hasClass("tips-empty")){
						status.find("span").remove();
					}
					var useRecord = rtn.resultset;
					//会议记录 ul标签开始
					for(var i in useRecord){
						var record=useRecord[i];
						var startDate=formatDate(new Date(record.start_time), 'yyyy-MM-dd');
						var start=formatDate(new Date(record.start_time), 'HH:mm');
						var end=formatDate(new Date(record.end_time), 'HH:mm');
						//判断是否进行中
						var now = (new Date()).getTime();
						if(now > record.start_time){
							var progress = (now - record.start_time)/(record.end_time - record.start_time);
							var str=Number(progress*100).toFixed(1);
						    str+="%";
							html.push("<li class='doing'>")
							html.push("<span class='tips'>进行中</span>")
							html.push("<span class='progress' style='width: "+str+"'></span>")
							html.push("<span class='date'>"+startDate+"yy</span>")
							html.push("<span class='times'>"+start+"~"+end+"</span>"+record.name+"</li>")
						}else{
							html.push("<li>")
							html.push("<span class='date'>"+startDate+"</span>")
							html.push("<span class='times'>"+start+"~"+end+"</span>")
							html.push(record.name+"</li>")
						}
						if(i == 1 &&  record.number> 2){//数量超过两条
							html.push("<div class='align-c'><a class='btn-more' href='#' onclick='moreRecord("+room_id+",\""+record.meeting_name+"\")'>更多</a></div>");
						}
					}
				}else{//增加 暂无预约会议
					status.append("<span class='tips-empty'>暂无预约会议</span>");
				}
			},null,{async:false});
		}
		if(html.length > 0){
			if(status.find("ul").hasClass("yylist")){//判断有无ul标签
				$("#ls"+room_id).append(html.join(""));
			}else{
				var id="ls"+room_id;
				var ul="<ul class='yylist' id="+id+" >";
				ul += (html.join(""));
				ul += "</ul>";
				status.append(ul);
			}
		}
	}
}

//历史记录
function moreRecord(room_id,name){
	$.modal("moreList.html",name+" 的使用记录", {
		width : '850px',
		height : '600px',
		meeting_id : room_id,
		callback : function(rtn) {
			cinit();
	    }
	});
}

</script>