	function initAll(){
		$("#moreTask").on("click", function(){
			showMoreTask();
		});
		
		initTask();
		initWarn();
		init_notic();
		init_calendar();
		//setInterval(refreshfunc, 6000);
	}
	function refreshfunc(){
		initTask();
		init_notic();
		calendarUtil.refreshEventCss(); 
	}
	 function initWarnContent(warn){
		$.call("application.warnSetting.initWarnContent", warn, function(r){
			if(r){
				$("#warning_panel .contentbox .warning-list .war-item ").find("dd[data-id='"+warn.id+"']").html("");
				for(var j=0; j<r.resultset.length; j++){
					var cont = r.resultset[j];
					var html1 = [];
					html1.push('<a href="javascript:void(0);">'+cont.label_+'<span ');
					html1.push(' class="');
					if(cont.level_ == 0){
						
					}else if(cont.level_ == 1){
						html1.push('text-green');
					}else if(cont.level_ == 2){
						html1.push('text-yellow');
					}else if(cont.level_ == 3){
						html1.push('text-red');
					}
					html1.push('" >'+cont.num_+'</span></a>');
					var $html = $(html1.join(''));
					var url;
					if(cont.pageid_){
						var params = {};
						if(cont.iscs_ == "1"){
							var p = cont.pageid_.split(".");
							if(p[1] == 'bill'){
								params = {"djlx": p[3],
										"djbs": p[2],
										"product_code": p[0]};
								url = "crtech://cs/bill";
							}else if(p[1] == 'query'){
								params = {"queryid": p[3], "product_code": p[0]};
								url = "crtech://cs/query";
							}else if(p[1] == 'materials'){
								params = {"product_code": ps[3],
										"fangabh": p[2],	
									    "djbh": ""
									};
								url = "crtech://cs/materials";
							} else if(p[1] == 'audit'){
								params = {"shenhid": p[3], "product_code": p[0]};
								url = "crtech://cs/audit";
							}
							params = $.extend(true, cont, params);
						}else{
							var u = cont.pageid_.replace(/\./g, "/");
							url = cooperopcontextpath + "/w/" + u+".html";
							params = cont;
							
						}
						(function () {
							var a_cont = cont;
							var a_url = url;
							var a_params = params;
							$html.on("click", function(){
								$.openTabPage(a_cont.pageid_, a_cont.label_, a_url, false, a_params);
							});
						})();
					}
					//$("#warning_panel .contentbox .warning-list .war-item ").find("dd[data-id='"+warn.id+"']").html("");
					$("#warning_panel .contentbox .warning-list .war-item ").find("dd[data-id='"+warn.id+"']").append($html);
				}
			}
		}, null, {nomask: true});
	}
	
	function initWarn(){
		$.call("application.warnSetting.initWarn", {state: 1}, function(rtn){
			if(rtn && rtn.sorts && rtn.warns){
				for(var i=0; i<rtn.sorts.resultset.length; i++){
					var html = [];
					var sort = rtn.sorts.resultset[i];
					html.push('<div class="war-item" data-id="'+sort.id+'">');
					html.push('	<dl>');
					html.push('		<dt><a href="javascript: void(0);"><span class="iconbox"><i class="'+sort.icon+'"></i></span>'+sort.name+'</a></dt>');
					var child_num = 0;
					for(var j=0; j<rtn.warns.resultset.length; j++){
						var warn = rtn.warns.resultset[j];
						if(warn.sort_id == sort.id){
							child_num++;
							html.push('<dd data-id="'+warn.id+'"></dd>');
						}
					}
					html.push('	</dl>');
					html.push('</div>');
					if(child_num >0 ){
						$("#warning_panel .contentbox .warning-list").append(html.join(""));
					}
				}
				for(var j=0; j<rtn.warns.resultset.length; j++){
					var warn = rtn.warns.resultset[j];
					if(+warn.refresh_time >= 5){
						initWarnContent(warn);
						setInterval(function(){
							initWarnContent(warn);
						}, warn.refresh_time * 1000);
					}else{
						initWarnContent(warn);
					}
				}
			}
		}, null, {nomask: true});
	}
	function initTask(){
		$.call("application.task.queryMine",{limit: 15}, function(rtn){
			if(rtn){
				var html = [];
				for(var i=0; i<rtn.resultset.length; i++){
					var task = rtn.resultset[i];
					html.push('<li ');
					html.push(' info_bill= "'+task.info_bill);
					html.push('" order_no= "'+task.order_no);
					html.push('" task_id= "'+task.task_id);
					html.push('" order_id= "'+task.order_id);
					html.push('" node_name= "'+task.node_name);
					html.push('" node_bill= "'+task.node_bill);
					html.push('" >');
					html.push('	<a class="ms" href="javascript: void(0);"  title="【'+task.order_no+'】 '+ task.subject +'"> '+ task.subject +'</a> ');
					html.push('	<span class="time">'+ task.create_time +'</span> ');
					html.push('	<span class="status hideText">'+ task.node_name +'</span> ');
					html.push('	<a class="deal-btn" href="javascript: void(0);"  >处理</a> ');
					html.push('</li>');
				}
				var $html = $(html.join(''));
				$html.find("a.ms").on("click", function(){
					var $node = $(this).parent();
					var pageid = $node.attr("info_bill");
					var order_no = $node.attr("order_no");
					var task_id = $node.attr("task_id");
					var order_id = $node.attr("order_id");
					var node_name = $node.attr("node_name");
					todeal(pageid, order_no, task_id,order_id, node_name);
				});
				$html.find("a.deal-btn").on("click", function(){
					var $node = $(this).parent();
					var pageid = $node.attr("node_bill");
					var order_no = $node.attr("order_no");
					var task_id = $node.attr("task_id");
					var order_id = $node.attr("order_id");
					var node_name = $node.attr("node_name");
					todeal(pageid, order_no, task_id, order_id, node_name);
				});
				$("#task_panel .contentbox .tasks-list ul").html($html);
			}
		}, null, {nomask: true});
	}
	function showMoreTask(){
		$.openTabPage("task_mine", "待办事项", cooperopcontextpath + "/w/application/task/mine.html", true);
	}
	function to_deal(pageid, order_no, task_id, order_id, node_name, wx_auth){
		todeal(pageid,order_no,task_id,order_id,node_name,wx_auth);
	}
	function todeal(pageid,order_no,task_id,order_id,node_name){
		var u = pageid.split(".").join("/");
		var url = cooperopcontextpath + "/w/" + u + ".html";
		var wei = '99%';
		var hei = '99%';
		var mwei = GetQueryString(pageid,"wei");
		var mhei = GetQueryString(pageid,"hei");
		if(mwei){
			wei = mwei+'px';
		}
		if(mhei){
			hei = mhei+'px';
		}
		$.modal(url,node_name,{
			width :wei,
			height :hei,
			tourl: url,
			djbh: order_no,
			order_id: order_id,
			task_id: task_id,
			callback : function(rtn){
				if(rtn){
					initTask();
				}
			}
		});
	}
	function GetQueryString(str,name){
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var after = str.split("?")[1];
	     var r = null;
	     if(after){
	    	 r = after.match(reg)
	     }
	     if(r!=null){
		     return  unescape(r[2]); 
	     }else{
		     return null;
	     }
	}
	//公告
	function init_notic(){
		$.call("oa.notice.queryMine",{is_release:1},function(rtn){
			if(rtn.count > 0) {
				var res = rtn.resultset;
				var html = [];
				for(var i in res){
					if(i>3) break;
					html.push('<li><a href="#" '+(res[i].is_read == 1 ? 'style="color:#aaa;"' : '')+' onclick="show_content('+res[i].id+')">'+res[i].subject+'</a><span>'+formatDate(new Date(res[i].begin_time), "yyyy-MM-dd hh:mm:ss")+'</span></li>');
				}
				$(".notice-list .nlist").empty().append(html.join(''));
			}
		}, null, {nomask: true})
		$(".notice #moreNotiy").on("click",function(){
			var url = cooperopcontextpath + "/w/oa/notice/mine.html";
			$.openTabPage("system_notification", "通知公告", url, false, "");
		})
	}
	function show_content(id){
		$.modal("/w/oa/notice/detail.html","",{
			id: id,
			preview: true,
			callback : function(rtn){
			}
		})
	}
	function init_calendar(){
		calendarUtil.initCalendarStructure($("#calendar"));  //加载日历结构
		calendarUtil.refreashDayNum();  //加载日数
		calendarUtil.refreshEventCss();  //加载事件样式
	}
	var calendarUtil = {
			//初始化结构
			"initCalendarStructure" : function(calendarDiv){
				var html = [];
				html.push('<div class="columnCard">');
				html.push('	<div class="calendar-cr">');
				html.push('		<div class="calendar-layout">');
				html.push('			<div id="calendar-title" class="calendar-tools">');
				html.push('				<h5>');
//				html.push('					<span class="cyear title-text"></span><span class="cmonth title-text"></span>');
				html.push('					<div class="cyear">');
				html.push('						<span class="title-text"></span>');
				html.push('						<div class="choose-year"></div>');
				html.push('					</div>');
				html.push('					<div class="cmonth">');
				html.push('						<span class="title-text"></span>');
				html.push('						<div class="choose-month">');
				html.push('							<a>一月</a><a>二月</a><a>三月</a><a>四月</a><a>五月</a><a>六月</a><a>七月</a><a>八月</a><a>九月</a><a>十月</a><a>十一月</a><a>十二月</a>');
				html.push('						<div>');
				html.push('					</div>');
				html.push('				</h5>');
				html.push('				<a class="btn-previous changemonth" href="#"><i class="cicon icon-back"></i></a>');
				html.push('				<a class="btn-next changemonth" href="#"><i class="cicon icon-go"></i></a>');
				html.push('			</div>');
				html.push('			<ul class="ca-title">');
				html.push('				<li>一</li><li>二</li><li>三</li><li>四</li><li>五</li><li>六</li><li>日</li>');
				html.push('			</ul>');
				html.push('			<ul id="calendar-days"></ul>');
				html.push('			<a id="changetoday" class="btn-today" href="#"><span>今</span></a>');
				html.push('			<a class="btn-add" href="#"><i class="cicon icon-add"></i></a>');
				html.push('		</div>');
				html.push('		<div class="calendar-details">');
				html.push('			<div class="calendar-tools">');
				html.push('				<h5><span></span><a class="control" href="#">关闭</a></h5>');
				html.push('			</div>');
				html.push('			<div id="date_event" class="matter"></div>');
				html.push('		</div>');
				html.push('	</div>');
				html.push('</div>');
				calendarDiv.append(html.join(''));
				//日历按钮事件-添加备忘
				$(".calendar .calendar-cr .calendar-layout > .btn-add").on("click", function(){
					$.modal(cooperopcontextpath + "/w/oa/calendar/memo/add.html", "备忘 - 添加", {
						width : '800px',
						height : '600px',
						callback : function(rtn) {
							if(rtn){
								//刷新事件提示
								calendarUtil.refreshEventCss();
							}
					    }
					})
				})
				//月份更改按钮事件
				$("#calendar-title > .changemonth").on("click", function(){
					var date = new Date();
					var calendarTitle = $("#calendar-title h5");
					var year = parseInt(calendarTitle.attr("data-year"), 10);
					var month = parseInt(calendarTitle.attr("data-month"), 10);
					if($(this).hasClass("btn-next")){
						if(month == 12){
							year += 1;
							month = 1;
						}else{
							month += 1;
						}
					}else{
						if(month == 1){
							year -= 1;
							month = 12;
						}else{
							month -= 1;
						}
					}
					calendarTitle.attr("data-year", year).attr("data-month", month);
					calendarUtil.refreashDayNum();
					calendarUtil.refreshEventCss();
				})
				//切换月份按钮
				$("#changetoday").on("click", function(){
					var date = new Date();
					var year = date.getFullYear();
					var month = date.getMonth()+1;
					var calendarTitle = $("#calendar-title h5");
					calendarTitle.attr("data-year", year).attr("data-month", month);
					calendarUtil.refreashDayNum();
					calendarUtil.refreshEventCss();
					$(this).hide();
				})
				//月份选择按钮
				$("#calendar-title h5 .cmonth").on("click", function(event){
					$(".choose-month").show();
					event.stopPropagation();
				});
				//点击外部隐藏
				$("body").on("click", function(event){
					$(".choose-month").hide();
					event.stopPropagation();
				})
				$("#calendar-title h5 .choose-month a").on("click", function(){
					var month = $("#calendar-title h5 .choose-month a").index(this)+1;
					$("#calendar-title h5").attr("data-month", month);
					$(".choose-month").hide();
					calendarUtil.refreashDayNum();
					calendarUtil.refreshEventCss();
					return false;
				});
				//年份选择按钮
				$("#calendar-title h5 .cyear").on("click", function(event){
					var html = [];
					var year = parseInt($("#calendar-title h5").attr("data-year"), 10);
					//左边年份
					html.push('<div class="yearbox left-yearbox">');
					for(var i = 5; i > 0; i--){
						html.push('<a>'+(year-i)+'</a>');
					}
					html.push('</div>');
					//右边年份
					html.push('<div class="yearbox right-yearbox">');
					for(var i = 0; i < 5; i++){
						html.push('<a>'+(year+i)+'</a>');
					}
					html.push('</div>');
					//下方翻页
					html.push('<div class="change-years">');
					html.push('	<a class="btn-changeyear before-years"><i class="cicon icon-back"></i></a>');
					html.push('	<a class="btn-changeyear after-years"><i class="cicon icon-go"></i></a>');
					html.push('</div>');
					$(".choose-year").empty().append(html.join(''));
					$(".choose-year").show();
					//翻页事件
					$(".choose-year .change-years .btn-changeyear").on("click", function(event){
						var n = 10;
						if($(this).hasClass("before-years")){
							var targetYear = parseInt($(".choose-year .left-yearbox a:first").text(), 10);
							$(".choose-year .left-yearbox a").each(function(){
								$(this).text(targetYear - n--);
							})
							$(".choose-year .right-yearbox a").each(function(){
								$(this).text(targetYear - n--);
							})
						}else{
							var targetYear = parseInt($(".choose-year .right-yearbox a:last").text(), 10);
							$(".choose-year .left-yearbox a").each(function(){
								$(this).text(targetYear + ++n -10);
							})
							$(".choose-year .right-yearbox a").each(function(){
								$(this).text(targetYear + ++n -10);
							})
						}
						event.stopPropagation();
					});
					$("#calendar-title h5 .choose-year .yearbox a").on("click", function(){
						var year = $(this).text();
						$("#calendar-title h5").attr("data-year", year);
						$(".choose-year").hide();
						calendarUtil.refreashDayNum();
						calendarUtil.refreshEventCss();
						return false;
					});
					//点击外部隐藏
					$("body").on("click", function(event){
						$(".choose-year").hide();
						event.stopPropagation();
					})
					event.stopPropagation();
				});
			},
			//刷新日历日数
			"refreashDayNum" : function(){
				var html = [];
				var days = 40;  //显示40天
				var date = new Date();
				var thisyear = date.getFullYear();
				var thismonth = date.getMonth()+1;
				var year = date.getFullYear();
				var month = date.getMonth()+1;
				var today = formatDate(date, "yyyy-MM-dd");
				var calendarTitle = $("#calendar-title h5");
				if(calendarTitle.attr("data-year")){
					year = calendarTitle.attr("data-year");
					month = calendarTitle.attr("data-month");
				}
				if(thisyear != year || thismonth != month){
					$("#changetoday").show();
				}else{
					$("#changetoday").hide();
				}
				calendarTitle.find(".cyear .title-text").text(year+'年');
				calendarTitle.find(".cmonth .title-text").text(month+'月');
				var daycount = new Date(year, month, 0).getDate();
				var month_begin = formatDate(new Date(year, month - 1, 1), 'yyyy-MM-dd');
				var month_end = formatDate(new Date(year, month, 0), 'yyyy-MM-dd');
				var beforeDays = 0;
				var afterDays = 0;
				var week = new Date(Date.parse(month_begin)).getDay();
				if(week == 0) week = 7;
				//上月日数
				beforeDays = week == 1 ? 7 : week - 1;
				for(var i = beforeDays; i > 0; i--){
			 		var before_date = getNextDate(month_begin, 0-i);
					var num = before_date.split("-")[2];
					var daynum = '<li class="before-date other-date" data-date="'+before_date+'"><span><a data-date="'+before_date+'" href="#">'+num+'</a></span></li>';
					html.push(daynum);
					if(i == beforeDays){
						calendarTitle.attr("begin_date", before_date);
					}
				}
				//本月日数
				for(var i = 1; i < daycount+1; i++){
					var data_date = year+'-'+(month<10?'0'+month:month)+'-'+(i<10?'0'+i:i);
					var daynum = '<li data-date="'+data_date+'"><span><a data-date="'+data_date+'" href="#">'+i+'</a></span></li>';
					if(today == data_date) daynum = '<li data-date="'+data_date+'"><span class="today"><a data-date="'+data_date+'" href="#">'+i+'</a></span></li>';
					html.push(daynum);
				}
				//次月日数
				afterDays = days - beforeDays - daycount;
				for(var i = 1; i <= afterDays; i++){
			 		var after_date = getNextDate(month_end, i);
					var num = parseInt(after_date.split("-")[2],  10);
					var daynum = '<li class="before-date other-date" data-date="'+after_date+'"><span><a data-date="'+after_date+'" href="#">'+num+'</a></span></li>';
					html.push(daynum);
					if(i == afterDays){
						calendarTitle.attr("end_date", after_date);
					}
				}
// 				console.log("year:"+year+", month:"+month+", today:"+today+", daycount:"+daycount+", month_begin:"+month_begin+", month_end:"+month_end+", beforeDays:"+beforeDays+", afterDays:"+afterDays);
				calendarTitle.attr("data-year", year).attr("data-month", month);
				$("#calendar-days").empty().append(html.join(''));
				//日数点击事件
				$("#calendar-days li > span > a").on("click",function(){
					var click_date = $(this).attr("data-date");
					$.call("oa.calendarEvent.queryMine", {"event_date":click_date}, function(rtn){
						//显示当天事件详情窗口
						var title_date = click_date.split("-");
						$(".calendar-details h5 > span").text(title_date[0]+'年 '+title_date[1]+'月 '+title_date[2]+'日');
						$(".calendar-details").show();
						$(".calendar-details > .calendar-tools a").on("click", function(){
							$(".calendar-details").hide();
						});
						//加载当天事件
						$("#date_event").empty();
						var type_html = [];
						var event_html = [];
						var remind = true;
						var task = true;
						var note = true;
						if(note){
							type_html = [];
							type_html.push('<dl id="calendar_note" class="msbox">');
							type_html.push('	<dt><span class="event-type">记事</span>');
							type_html.push('		<span class="btnbox"><a href="#"><i class="cicon icon-add"></i></a></span>');
							type_html.push('	</dt>');
							type_html.push('</dl>');
							$("#date_event").append(type_html.join(''));
							note = false;
							//按钮事件-添加备忘
							$("#calendar_note > dt > .btnbox > a").on("click", function(){
								$.modal(cooperopcontextpath + "/w/oa/calendar/memo/add.html", "备忘 - 添加", {
									width : '800px',
									height : '600px',
									memo_time : click_date,
									callback : function(rtn) {
										if(rtn){
											$(".calendar-details").hide();
											calendarUtil.refreshEventCss();
											setTimeout(function(){
												$("#calendar-days li[data-date = "+click_date+"] > span > a").click();
											}, 100);
										}
								    }
								})
							})
						}
						if(rtn.count >0){
							var res = rtn.resultset;
							for(var i in res){
								event_date = formatDate(new Date(res[i].expire_time), 'yyyy-MM-dd');
								if(click_date == event_date){
									event_html = [];
									if('remind' == res[i].dl){
										if(remind){
											type_html = [];
											type_html.push('<dl id="calendar_remind" class="msbox">');
											type_html.push('	<dt><span class="event-type">'+res[i].dlmc+'</span>');
											type_html.push('	</dt>');
											type_html.push('</dl>');
											$("#date_event").append(type_html.join(''));
											remind = false;
										}
										event_html.push('<dd>');
										event_html.push('	<span class="event-xl">['+res[i].xlmc+']</span>');
										event_html.push('	<span class="event-subject">'+res[i].subject+'</span>');
										event_html.push('	<span class="btnbox">');
										event_html.push('		<a class="showdetail" data-id="'+res[i].data_id+'" href="#">查看</a>');
										event_html.push('	</span>');
										event_html.push('</dd>');
										$(".calendar .calendar-details #calendar_remind").append(event_html.join(''));
									}
									if('task' == res[i].dl){
										if(task){
											type_html = [];
											type_html.push('<dl id="calendar_task" class="msbox">');
											type_html.push('	<dt><span class="event-type">'+res[i].dlmc+'</span>');
											type_html.push('	</dt>');
											type_html.push('</dl>');
											$("#date_event").append(type_html.join(''));
											task = false;
										}
										event_html.push('<dd>');
										event_html.push('	<span class="event-xl">['+res[i].xlmc+']</span>');
										event_html.push('	<span class="event-subject">'+res[i].subject+'</span>');
//		 								event_html.push('	<span class="btnbox">');
//		 								var dataParams = JSON.parse(res[i].data_params);
//		 								event_html.push('		<a class="showdetail" data-id="'+res[i].data_id+'" data-taskid="'+dataParams.task_id+'" href="#">处理</a>');
//		 								event_html.push('	</span>');
										event_html.push('</dd>');
										$(".calendar .calendar-details #calendar_task").append(event_html.join(''));
									}
									if('note' == res[i].dl){
										event_html.push('<dd>');
										event_html.push('	<span class="event-xl">['+res[i].xlmc+']</span>');
										event_html.push('	<span class="event-subject">'+res[i].subject+'</span>');
										event_html.push('	<span class="btnbox">');
										event_html.push('		<a class="modify" data-id="'+res[i].data_id+'" href="#"><i class="cicon icon-edit"></i></a>');
										event_html.push('		<a class="del" data-id="'+res[i].data_id+'" href="#"><i class="cicon icon-del"></i></a>');
										event_html.push('	</span>');
										event_html.push('</dd>');
										$(".calendar .calendar-details #calendar_note").append(event_html.join(''));
									}
								}
							}
							//按钮事件-查看提醒
							$("#calendar_remind > dd > .btnbox > a.showdetail").on("click", function(){
								$.modal(cooperopcontextpath + "/w/oa/notice/detail.html", "", {
									id : $(this).attr("data-id")
								})
							})
							//按钮事件-任务处理
//		 					$("#calendar_task > dd > .btnbox > a.showdetail").on("click", function(){
//		 						var task_id = $(this).attr("data-taskid");
//		 						console.log(task_id)
//		 					})
							//按钮事件-修改备忘
							$("#calendar_note > dd > .btnbox > a.modify").on("click", function(){
								$.modal(cooperopcontextpath + "/w/oa/calendar/memo/add.html", "备忘 - 添加", {
									width : '800px',
									height : '600px',
									id : $(this).attr("data-id"),
									memo_time : click_date,
									callback : function(rtn) {
										if(rtn){
											$(".calendar-details").hide();
											calendarUtil.refreshEventCss();
											setTimeout(function(){
												$("#calendar-days li[data-date = "+click_date+"] > span > a").click();
											}, 100);
										}
								    }
								})
							})
							//按钮事件-删除备忘
							$("#calendar_note > dd > .btnbox > a.del").on("click", function(){
								var id = $(this).attr("data-id");
								$.confirm("删除后不可恢复，是否继续？", function(ncr){
									if(ncr){
										$.call("oa.calendar.memo.delete", {id:id}, function(ndr){
											if(ndr){
												$(".calendar-details").hide();
												calendarUtil.refreshEventCss();
												setTimeout(function(){
													$("#calendar-days li[data-date = "+click_date+"] > span > a").click();
												}, 100);
											}
										})
									}
								})
							})
						}
					}, null, {nomask: true});
				});
			},
			//刷新日历事件样式
			"refreshEventCss" : function(){
				var calendarTitle = $("#calendar-title h5");
				var begin_date = calendarTitle.attr("begin_date");
				var end_date = calendarTitle.attr("end_date");
				//事件样式
				$.call("oa.calendarEvent.queryMine", {"begin_date": begin_date, "end_date": end_date}, function(rtn){
					if(rtn.count > 0){
						var res = rtn.resultset;
						var event_date = "";
						var daynum;
						for(var i in res){
							event_date = formatDate(new Date(res[i].create_time), 'yyyy-MM-dd');
							var espan = $("#calendar-days li[data-date = "+event_date+"] span");
							if(!espan.hasClass("sign"))
							espan.addClass("sign");
						}
					}
				}, null, {nomask: true})
				//休息日样式
				$.call("oa.calendar.calendar.query", {"sdate":begin_date, "edate":end_date}, function(rtn){
					if(rtn.count > 0){
						var res = rtn.resultset;
						for(var i in res){
							if(res[i].workday != 1){
// 								$("#calendar-days li[data-date = "+res[i].gongli+"] span").append('<b class="xiu"></b>');  //图标显示休假
								$("#calendar-days li[data-date = "+res[i].gongli+"] a").addClass("xiu").attr("title", res[i].ev_remark);  //颜色显示休假
							}else{
								if(res[i].is_work == 0){
									$("#calendar-days li[data-date = "+res[i].gongli+"] a").attr("title", res[i].ev_remark);
								}
								$("#calendar-days li[data-date = "+res[i].gongli+"] a").removeClass("xiu").attr("title", res[i].ev_remark); 
							}
						}
					}
				}, null, {nomask: true})
			}
	}
	function getNextDate(date, day) { 
	　　var dd = new Date(date);
	　　dd.setDate(dd.getDate() + day);
	　　var y = dd.getFullYear();
	　　var m = dd.getMonth() + 1 < 10 ? "0" + (dd.getMonth() + 1) : dd.getMonth() + 1;
	　　var d = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate();
	　　return y + "-" + m + "-" + d;
	}