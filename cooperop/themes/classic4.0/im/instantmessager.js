var $chohoim = $chohoim || {};
(function(chohoim, window) {
	if (chohoim.inited) {
		return;
	}
	chohoim.config = ws_config;
	chohoim.CLIENT_TYPES = {
		WEBBROWSER : "Browser",
		MOBILE : "Phone",
		PAD : "Pad",
		TV : "TV",
	}
	
	var chohoim_msg_data;
	var log = {
			DEBUG : 4,
			LOG : 3,
			INFO : 2,
			WARN : 1,
			ERROR : 0,
			debug : function(event, msg) {
				if (window['console'] && +chohoim.params.debug >= log.DEBUG) {
					console.debug("ChohoIM" + " | " + formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.sss") + "| Debug | " + event + " | "
							+ ($.isPlainObject(msg) ? JSON.stringify(msg) : msg));
				}
			},
			log : function(event, msg) {
				if (window['console'] && +chohoim.params.debug >= log.LOG) {
					console.log("ChohoIM" + " | " + formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.sss") + "| Log | " + event + " | "
							+ ($.isPlainObject(msg) ? JSON.stringify(msg) : msg));
				}
			},
			info : function(event, msg) {
				if (window['console'] && +chohoim.params.debug >= log.INFO) {
					console.info("ChohoIM" + " | " + formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.sss") + "| Info | " + event + " | "
							+ ($.isPlainObject(msg) ? JSON.stringify(msg) : msg));
				}
			},
			warn : function(event, msg) {
				if (window['console'] && +chohoim.params.debug >= log.WARN) {
					console.warn("ChohoIM" + " | " + formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.sss") + "| Warning | " + event + " | "
							+ ($.isPlainObject(msg) ? JSON.stringify(msg) : msg));
				}
			},
			error : function(event, msg, error) {
				if (window['console'] && +chohoim.params.debug >= log.ERROR) {
					/*console.error("ChohoIM" + " | " + formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.sss") + "| Error | " + event + " | "
							+ ($.isPlainObject(msg) ? JSON.stringify(msg) : msg) + " | "
							+ JSON.stringify(error));*/
					if (error) {
						console.error(error);
					}
				}
			}
		};
	chohoim.params = {
		url : undefined,
		debug : log.INFO,
		client_type : chohoim.CLIENT_TYPES.WEBBROWSER,
		ready : function(userinfo) {
			logined(userinfo);
		},
		receive: function(msg, from) {
			log.debug("receive", msg);
			if (typeof msg["action"] != "undefined") {
				chohoim.actions[msg["action"]](msg.data, from);
			}
		},
		closed : function(evt) {
			if(typeof(crtech) != 'undefined'){
				crtech.logout();
			}else{
				location.href = cooperopcontextpath + rtn.redirect_url;
			}
			/*chohojQuery(".choho-im .choho-im-toggle").show();
			chohojQuery(".choho-im .choho-im-warper").attr("style", "display:none;");
			$.error("服务器连接断开，请稍后重试，或与客服联系。",function() {
				$.logout();
			});*/
		}
	};
	chohoim.hide = function() {
		chohojQuery(".choho-im .choho-im-toggle").show();
		chohojQuery(".choho-im .choho-im-warper").attr(
				"style", "display:none;");
	};
	chohoim.show = function() {
		chohojQuery(".choho-im .choho-im-toggle").hide();
		chohojQuery(".choho-im .choho-im-warper").attr("style", "");
	}
	chohoim.paint = function() {
		initOnlineStatusSelect();
		chohojQuery("#page-content .page-tab iframe ").contents().find(
		".content .leftMain .iptDiv .choho-im-search-input")
		.on({
			"focus" : function() {
				chohojQuery(this).parent().addClass("active");
			},
			"blur" : function() {
				var $tt = chohojQuery(this).parent();
				setTimeout(function() {
					$tt.removeClass("active");
				}, 200);
			},
			"keyup" : function() {
				var $t = chohojQuery(this);
				var key = chohojQuery.trim($t.val());
				function search() {
					$t.siblings(".choho-im-search-warper").find(".choho-im-search-result-list").html("");
					wscall("loadContactors", {type: ['D', 'U', 'G'], filter: $t.data("filter"), sort: 'order_no'}, function(rtn) {
						function draw(contactor) {
							var jobj = chohojQuery(html_template["choho-im-sidebar-search-result-comment"]);
							jobj.attr("data-window-id", contactor['type'] + '-' + contactor['id']);
							jobj.find(".choho-im-avatar .choho-im-avatar-img").css("backgroundImage", "url('" + getAvatarSrc(contactor) + "')");
							jobj.find(".choho-im-comment-content .choho-im-comment-name").text(contactor['name']);
							if (contactor["type"] == "U") {
								jobj.find(".choho-im-comment-content .choho-im-comment-text").text("[" + contactor['department_name'] + "]");
							} else if (contactor["type"] == "D" || contactor["type"] == "G") {
								//jobj.find(".choho-im-comment-content .choho-im-comment-text").text("(" + contactor['usernum'] + ")");
							}

							jobj.on({
								"click": function() {
									openWindow(contactor);
								}
							});
							$t.siblings(".choho-im-search-warper").find("." + contactor["type"]).append(jobj);
						}
						for (var i in rtn.contactors) {
							draw(rtn.contactors[i]);
						}
					}, function(errormsg) {
						log.error("searchContactor", errormsg);
					});

				}
				if ($t.data("t")) {
					clearTimeout($t.data("t"));
				}
				if (key && key != $t.data("filter")) {
					$t.data("filter", key);
					$t.data("t", setTimeout(search, 500));
				}
			}
		});
		chohojQuery("#page-content .page-tab iframe ").contents().find(
		".content .leftMain .iptDiv .icon-close")
		.click(
				function() {
					var $t = chohojQuery(this).siblings(".choho-im-search-input");
					$t.val("");
					if ($t.data("t")) {
						clearTimeout($t.data("t"));
						$t.removeData("t");
					}
					if ($t.data("filter")) {
						$t.removeData("filter");
					}
					$t.siblings(".choho-im-search-warper").find(".choho-im-search-result-list").html("");
				});
	};
	chohoim.init = function(params) {
		chohoim.params = chohojQuery.extend(true, chohoim.params, params);
		if (!chohoim.params["no_ui"]) {
			chohoim.paint();
		}
		$chohows.init(chohoim.params);
		$('#header_suggestions_bar').click(function(){
			loadSessions();
			loadContactors();
			loadGroups();
		})
		/*if(typeof crtech != "undefined"){
			crtech.closeModal();
		}
		if(typeof crtech != "undefined"){
			crtech.modal("/w/hospital_common/system/msgalert/index.html?uid="+params.userid+"&_CRSID="+params._CRSID, "250", "85");
		}*/
	};
	
	
	var logined = function(userinfo) {
		setUserInfo(userinfo);
		setOnlineStatus(userinfo['default_online_status'] || 'online');
		/*if (!chohoim.params["no_ui"]) {
			loadSessions();
			loadContactors();
			loadGroups();
		}*/
	}
	
	var wscall = function(action, data, callback, error) {
		$chohows.send({
			data: {action: action, data: data}, 
			callback: callback,
			error: error
		});
	};
	var openGroupEditWindow = function(data) {
		var userlist = chohojQuery(".choho-im .choho-im-group-warper .group-right");
		userlist.find(".group-right-user").html('');
		var tt = chohojQuery(".choho-im .choho-im-warper .choho-im-group-warper .group-bar");
		tt.find("input").val(data['name'] || '群组名字');
		tt.find("span").text(data['name'] || '群组名字');
		if(data['id'] == 'new'){
			chohoim.userinfo
			var user = chohojQuery(html_template["choho-im-content-right-window-group-userlist-user"]);
			user.find(".choho-im-avatar .choho-im-avatar-img").css("backgroundImage", "url('" + getAvatarSrc(chohoim.userinfo) + "')");
			user.find(".choho-im-title").text(chohoim.userinfo.name);
			user.attr("data-g-id", 'new');
			user.attr("data-u-id", chohoim.userinfo.id);
			userlist.find(".group-right-user").append(user);
			tt.find("span").hide();
			tt.find(".edit-groupname").hide();
			tt.find("input").show();
		}else{
			wscall("listContractorUsers", {contacter_type: 'G', contacter_id: data['id'], sort: 'name'}, function(rtn) {
				function c(u) {
					u['type'] = 'U';
					var user = chohojQuery(html_template["choho-im-content-right-window-group-userlist-user"]);
					user.find(".choho-im-avatar .choho-im-avatar-img").css("backgroundImage", "url('" + getAvatarSrc(u) + "')");
					user.attr("data-g-id", 'new');
					user.attr("data-u-id", u['id']);
					user.find(".choho-im-title").text(u.name);	
					user.on("dblclick", function() {
						//openWindow(u);
						if(user.attr("data-u-id") != chohoim.userinfo.id){
							user.remove();
						}
					});
					userlist.find(".group-right-user").append(user);
				}
				for (var i in rtn.users) {
					c(rtn.users[i]);
				}
				//userlist.find(".choho-im-userlist-title").text("群组成员(" + rtn.users.length + ")");
			}, function(errormsg) {
				log.error("listContractorUsers", errormsg);
			});
			var tt = chohojQuery(".choho-im .choho-im-warper .choho-im-group-warper .group-bar");
			tt.find(".edit-groupname").on("click", function(){
				tt.find("span").hide();
				$(this).hide();
				tt.find("input").show();
			});
		}
		chohojQuery(".choho-im .choho-im-group-warper").removeClass("hide");
		chohojQuery(".choho-im .choho-im-warper .choho-im-group-warper .group-right .group-right-button").find(".group-save").on("click", function(){
			var gdata = {id: data['id']};
			gdata.name = chohojQuery(".choho-im-group-warper .group-bar>input[name='group_name']").val();
			gdata.userlist = [];
			userlist.find(".group-right-user").find(".choho-im-userlist-user").each(function(){
				gdata.userlist.push({system_user_id: $(this).attr("data-u-id")});
			});
			wscall("saveGroup", {jdata: $.toJSON(gdata)}, function(r){
				closeGroupEditWindow();
				//修改群组后，
				if(gdata == 'new'){
					//新增刷新右边的联系群组信息
					loadGroups();
				}else{
					//刷新聊天框右边的群组成员
					var userlist = chohojQuery('.choho-im .choho-im-content-warper .choho-im-content .choho-im-right .choho-im-window .choho-im-bottom .choho-im-userlist');
					userlist.find(".choho-im-userlist-list").html('');
					wscall("listContractorUsers", {contacter_type: 'G', contacter_id: data['id'], sort: 'name'}, function(rtn) {
						function c(u) {
							u['type'] = 'U';
							var user = chohojQuery(html_template["choho-im-content-right-window-userlist-user"]);
							user.find(".choho-im-avatar .choho-im-avatar-img").css("backgroundImage", "url('" + getAvatarSrc(u) + "')");
							user.find(".choho-im-avatar .choho-im-status-icon").attr("class", getOnlineState(u));	
							user.find(".choho-im-avatar .choho-im-status-icon").attr("data-user-id", u['id']);
							user.find(".choho-im-title").text(u.name);	
							user.dblclick(function() {
								openWindow(u);
							});
							userlist.find(".choho-im-userlist-list").append(user);
						}
						var ol = 0;
						for (var i in rtn.users) {
							if (rtn.users[i]['online_status'] != 'offline' && rtn.users[i]['online_status'] != 'hidden') {
								ol += 1;
								c(rtn.users[i]);
							}
						}
						for (var i in rtn.users) {
							if (rtn.users[i]['online_status'] != 'offline' && rtn.users[i]['online_status'] != 'hidden') {
							} else {
								c(rtn.users[i]);
							}
						}
						userlist.find(".choho-im-userlist-title").text("群组成员(" + ol + "/" + rtn.users.length + ")");
					}, function(errormsg) {
						log.error("listContractorUsers", errormsg);
					});
				}
				
			 }, function(errormsg) {
					log.error("saveGroup", errormsg);
			 });
		});
		chohojQuery(".choho-im .choho-im-warper .choho-im-group-warper .group-right .group-right-button").find(".group-cancel").on("click", function(){
			closeGroupEditWindow();
		});
		chohojQuery(".choho-im .choho-im-warper .choho-im-group-warper .group-bar .close-group").on("click", function(){
			closeGroupEditWindow();
		});
	}
	var closeGroupEditWindow = function() {
		var tt = chohojQuery(".choho-im .choho-im-warper .choho-im-group-warper .group-bar");
		tt.find("span").show();
		tt.find(".edit-groupname").show();
		tt.find("input").hide();
		tt.find("input").val('');
		chohojQuery(".choho-im .choho-im-group-warper").addClass("hide");
		chohojQuery(".choho-im .choho-im-group-warper .group-right .group-right-user").html('');//children(".choho-im-userlist-user").remove();;
	}
	var openWindow = function(data) {
		log.log("openWindow", data);
		var window_id = data.type + "-" + data.id;
		if (data.type == 'U' && data.id == chohoim.userinfo.id) {
			return;
		}
		var right_content = chohojQuery("#page-content .page-tab iframe ").contents().find(".right-content");
		var right_window = chohojQuery(html_template["choho-im-content-right-window"]);
		var right_profile = chohojQuery(html_template["choho-im-content-right-window-profile"]);
		var right_group = chohojQuery(html_template["choho-im-content-right-window-userlist"]);
		if(window_id == right_content.attr("data-window-id")){
			return ;
		}
		right_content.html('');
		right_content.attr("data-window-id", window_id);
		right_window.attr("data-window-id", window_id);
		right_window.find(".r-title span").text(data['name']);

			var history_limit = 50, limit = 20, count = 0, page = 0;
			wscall("listSessionMessages", {target: data['type'], noread: "noread", session_id: data['id'], start: 0, limit: limit, sort: 'send_time desc'}, function(rtn) {
				count = rtn.count;
				for (var i = rtn.resultset.length - 1; i >= 0; i --) {
					var message = chohojQuery(html_template["choho-im-content-messages-message"]);
					message.attr("class",   rtn.resultset[i]['send_type'] == 'from'?'rightText':'leftText');
					message.find("img").attr("src", getAvatarSrc(rtn.resultset[i]['send_type'] == 'from' ? chohoim.userinfo : data));
					message.find(".im-name").text(rtn.resultset[i]['system_user_name']);
					message.find(".timeDiv .timeSpan").text(rtn.resultset[i]['send_time_label']);
					if (rtn.resultset[i].type != "T") {
						if (rtn.resultset[i].mime_type && rtn.resultset[i].mime_type.indexOf("image/") == 0) {
							var img = chohojQuery(html_template["choho-im-content-messages-message-image"]);
							img.find("img").attr("src", chohoim.config.http_url + '/cimcjs/rm/' + rtn.resultset[i].content);
							img.find("img").attr("alt", rtn.resultset[i].file_name || '屏幕截图');
							img.find("img").attr("download", rtn.resultset[i].file_name || '屏幕截图');
							img.find(".choho-im-file-name").text(rtn.resultset[i].file_name);
							img.find(".choho-im-file-name").attr("title", rtn.resultset[i].file_name);
							img.find(".im-file-size").text($chohocommon.formatfilesize(+rtn.resultset[i].file_size));
							message
							.find(
									".wenben")
							.append(img);
						} else if (rtn.resultset[i].mime_type && rtn.resultset[i].mime_type.indexOf("audio/") == 0) {
							var img = chohojQuery(html_template["choho-im-content-messages-message-audio"]);
							img.find("audio").attr("src", chohoim.config.http_url + '/cimcjs/rm/' + rtn.resultset[i].content);
							img.find("audio").attr("alt", rtn.resultset[i].file_name || '语音消息');
							img.find("audio").attr("download", rtn.resultset[i].file_name || '语音消息');
							img.find(".choho-im-file-name").text(rtn.resultset[i].file_name);
							img.find(".choho-im-file-name").attr("title", rtn.resultset[i].file_name);
							img.find(".im-file-size").text($chohocommon.formatfilesize(+rtn.resultset[i].file_size));
							message.find(".wenben").append(img);
						} else if (rtn.resultset[i].mime_type && rtn.resultset[i].mime_type.indexOf("video/") == 0) {
							var img = chohojQuery(html_template["choho-im-content-messages-message-video"]);
							img.find("video").attr("src", chohoim.config.http_url + '/cimcjs/rm/' + rtn.resultset[i].content);
							img.find("video").attr("alt", rtn.resultset[i].file_name || '小视频');
							img.find("video").attr("download", rtn.resultset[i].file_name || '小视频');
							img.find(".choho-im-file-name").text(rtn.resultset[i].file_name);
							img.find(".choho-im-file-name").attr("title", rtn.resultset[i].file_name);
							img.find(".im-file-size").text($chohocommon.formatfilesize(+rtn.resultset[i].file_size));
							message.find(".wenben").append(img);
						} else {
							var img = chohojQuery(html_template["choho-im-content-messages-message-file"]);
							img.find(".text-x a").attr("href", chohoim.config.http_url + '/cimcjs/rm/' + rtn.resultset[i].content);
							img.find(".text-x a").attr("download", rtn.resultset[i].file_name);
							img.find(".r-msg .text-m").text(rtn.resultset[i].file_name);
							img.find(".r-msg .text-m").attr("title", rtn.resultset[i].file_name);
							//img.find(".choho-im-file-icon").css("backgroundImage", "url(" + getFileIcon(rtn.resultset[i].mime_type, rtn.resultset[i].file_name) +")");
							img.find("im-file-size").text($chohocommon.formatfilesize(+rtn.resultset[i].file_size));
							message
							.find(
									".wenben")
							.append(img);
						}
					} else {
						message.find(".wenben").html(rtn.resultset[i].content);
					}
					right_window.find(".textDiv").append(message);
					right_window.find(".textDiv").scrollTop(right_window.find(".textDiv")[0].scrollHeight);
					var comment = chohojQuery("#page-content .page-tab iframe ").contents().find(".leftMain .listDiv .list[data-window-id='" + window_id + "']");
					comment.find(".weiSpan").text(0);
					comment.find(".weiSpan").addClass('hide');
				}
			}, function(errormsg) {
				log.error("listSessionMessages", errormsg);
			});
			right_window.find(".r-title .icon-user").on("click", function(){
				if(data.type == 'U'){
					chohojQuery("#page-content .page-tab iframe ").contents().find(".rightMain-mask").addClass("active");
					right_profile.addClass("active");
				}else{
					chohojQuery("#page-content .page-tab iframe ").contents().find(".rightMain-mask").addClass("active");
					right_group.addClass("active");
				}
			});
			right_window.find(".ipt .tw-div .sendfile").change("input:file", function() {
				if(data["id"] == 'root000'){
					return;
				}
				if (chohojQuery(this).find("input:file")[0].files.length == 0) return;
				var file = chohojQuery(this).find("input:file")[0].files[0];
				sendMessage('F', {type: 'blob', file: file})
				right_window.find(".ipt .tw-div .sendfile").html(right_window.find(".ipt .tw-div .sendfile").html());
			});
			
			right_window.find(".ipt .tw-div .sendimage").change("input:file", function() {
				if(data["id"] == 'root000'){
					return;
				}
				if (chohojQuery(this).find("input:file")[0].files.length == 0) return;
				var file = chohojQuery(this).find("input:file")[0].files[0];
				sendMessage('I', {type: 'blob', file: file})
				right_window.find(".ipt .tw-div .sendimage").html(right_window.find(".ipt .tw-div .sendimage").html());
			});
			
			var sendMessage = function(type, content) {
				var message = chohojQuery(html_template["choho-im-content-messages-message"]);
				$chohows.send({
					data : {
						action : "send",
						data : {
							target : data["type"],
							send_to : data["id"],
							type: type,
							content : content,
							send_time: $chohocommon.formatdate(new Date(), 'yyyy-MM-dd HH:mm:ss')
						}
					},
					beforesend : function(data) {
						log.log('beforesend', data);
						var t = chohojQuery.extend(true, {}, chohoim.userinfo, {type: 'U'});
						message.attr("class", "rightText");
						message.find("img").attr("src", getAvatarSrc(t));
						message.find(".im-name").text(t.name);
						message.find(".timeDiv .timeSpan").text(data.data.send_time);
						if (data.data.type == 'F' || data.data.type == 'I') {
							if (data.data.content.contentType.indexOf("image/") == 0) {
								var img = chohojQuery(html_template["choho-im-content-messages-message-image"]);
								img.find("img").attr("src", data.data.content.dataurl);
								img.find("img").attr("alt", data.data.content.fileName || '屏幕截图');
								img.find("img").attr("download", data.data.content.fileName || '屏幕截图');
								img.find(".choho-im-file-name").text(data.data.content.fileName);
								img.find(".choho-im-file-name").attr("title", data.data.content.fileName);
								img.find(".im-file-size").text($chohocommon.formatfilesize(data.data.content.size));
								message
								.find(
										".wenben")
								.append(img);
								right_window.find(".textDiv").append(message);
								setTimeout(function() {
									right_window.find(".textDiv").scrollTop(right_window.find(".textDiv")[0].scrollHeight);
								}, 100);
							} else if (data.data.content.contentType
									.indexOf("audio/") == 0) {
								var img = chohojQuery(html_template["choho-im-content-messages-message-audio"]);
								img.find("audio").attr("src", data.data.content.dataurl);
								img.find("audio").attr("alt", data.data.content.fileName || '语音消息');
								img.find("audio").attr("download", data.data.content.fileName || '语音消息');
								img.find(".choho-im-file-name").text(data.data.content.fileName);
								img.find(".choho-im-file-name").attr("title", data.data.content.fileName);
								img.find(".choho-im-file-size").text($chohocommon.formatfilesize(data.data.content.size));
								message
								.find(
										".choho-im-message-content .choho-im-message-message")
								.append(img);
								right_window.find(".choho-im-messages").append(message);
								setTimeout(function() {
									right_window.find(".choho-im-messages").scrollTop(right_window.find(".choho-im-messages")[0].scrollHeight);
								}, 100);
							} else if (data.data.content.contentType
									.indexOf("video/") == 0) {
								var img = chohojQuery(html_template["choho-im-content-messages-message-video"]);
								img.find("video").attr("src", data.data.content.dataurl);
								img.find("video").attr("alt", data.data.content.fileName || '小视频');
								img.find("video").attr("download", data.data.content.fileName || '小视频');
								img.find(".choho-im-file-name").text(data.data.content.fileName);
								img.find(".choho-im-file-name").attr("title", data.data.content.fileName);
								img.find(".im-file-size").text($chohocommon.formatfilesize(data.data.content.size));
								message
								.find(
										".choho-im-message-content .choho-im-message-message")
								.append(img);
								right_window.find(".choho-im-messages").append(message);
								setTimeout(function() {
									right_window.find(".choho-im-messages").scrollTop(right_window.find(".choho-im-messages")[0].scrollHeight);
								}, 100);
							} else {
								var img = chohojQuery(html_template["choho-im-content-messages-message-file"]);
								img.find(".choho-im-file-download").attr("href", data.data.content.dataurl);
								img.find(".choho-im-file-download").attr("download", data.data.content.fileName);
								img.find(".choho-im-file-name").text(data.data.content.fileName);
								img.find(".choho-im-file-name").attr("title", data.data.content.fileName);
								img.find(".choho-im-file-icon").css("backgroundImage", "url(" + getFileIcon(data.data.content.contentType, data.data.content.fileName) + ")");
								img.find(".im-file-size").text($chohocommon.formatfilesize(data.data.content.size));
								message
								.find(
										".wenben")
								.append(img);
								right_window.find(".textDiv").append(message);
								right_window.find(".textDiv").scrollTop(right_window.find(".textDiv")[0].scrollHeight);
							}
						}  else {
							message.find(".wenben").html(data.data.content);
							right_window.find(".textDiv").append(message);
							right_window.find(".textDiv").scrollTop(right_window.find(".textDiv")[0].scrollHeight);
						}
						//重绘会话列表
						var window_id = data.data.target + "-" + data.data.send_to;
						var comment = chohojQuery("#page-content .page-tab iframe ").contents().find(".leftMain .listDiv .list[data-window-id='" + window_id + "']");
						if (comment.length > 0) {
							comment.remove();
						}
						var session;
						var user;
						if (data.data.target == 'G') {
							for (var i in groups) {
								if (groups[i].id == data.data.send_to) {
									session = chohojQuery.extend(true, {
										target: data.data.target,
										session_id: data.data.send_to,
										session_name: groups[i].name,
										read_user_id: chohoim.userinfo.id,
										last_content: undefined,
										last_content_type: data.data.type,
										last_content_mime_type: undefined,
										last_send_user_name: chohoim.userinfo.name,
										last_send_time: Date.parse(data.data.send_time),
										noreadnum: 0
									}, group[i]);
									user = group[i];
								}
							}
						} else {
							var bk = false;
							function b(tn) {
								if (tn.type == data.data.target && tn.id == data.data.send_to) {
									session = chohojQuery.extend(true, {
										target: data.data.target,
										session_id: data.data.send_to,
										session_name: tn.name,
										read_user_id: chohoim.userinfo.id,
										last_content: undefined,
										last_content_type: data.data.type,
										last_content_mime_type: undefined,
										last_send_user_name: chohoim.userinfo.name,
										last_send_time: Date.parse(data.data.send_time),
										noreadnum: 0
									}, tn);
									user = tn;
									bk = true;
								}
								if (!bk && tn.children.length > 0) {
									for (var i in tn.children) {
										b(tn.children[i]);
										if (bk) break;
									}
								}
							}
							b(contactors);
						}
						if (data.data.type == 'F' || data.data.type == 'I') {
							session["last_content"] = data.data.content.fileName;
							session["last_content_mime_type"] = data.data.content.contentType;
						} else {
							session["last_content"] = data.data.content;
						}
						drawSession(session, true);
					},
					callback : function(data) {
						//log.log('callback', data);
						message.find(".choho-im-message-content .choho-im-message-title .choho-im-message-time").text(data.data_time_label);
						var window_id = data.target + "-" + data.send_to;
						var comment = chohojQuery(".choho-im .choho-im-sidebar .choho-im-content .choho-im-tab[data-tab-id='comments'] .choho-im-comment[data-window-id='" + window_id + "']");
						comment.find(".choho-im-comment-content .choho-im-comment-time").text(data.data_time_label_sort);
					},
					error : function(errormsg) {
						message.find(".choho-im-message-content .choho-im-message-title .choho-im-error").removeClass(".hide");
						message.find(".choho-im-message-content .choho-im-message-title .choho-im-error").text("发送失败：" + errormsg);
					}
				});
			}
			
			var sendTextMessage = function() {
				if (!right_window.find(".ipt .choho-im-text1 .choho-im-text-content").text() &&
						right_window.find(".ipt .choho-im-text1 .choho-im-text-content").html().indexOf('img') < 0) {
					return;
				}
				var _content = right_window.find(".ipt .choho-im-text1 .choho-im-text-content").clone().removeAttr("class").removeAttr("contenteditable").prop('outerHTML');
				sendMessage('T', _content);
				right_window.find(".ipt .choho-im-text1 .choho-im-text-content").html('');
				right_window.find(".ipt .choho-im-text1 .choho-im-text-content").focus();
			}
			right_window.find(".ipt .send").click(function() {
				if(data["id"] == 'root000'){
					return;
				}
				sendTextMessage();
			});
			right_window.find(".ipt .fa-plus").click(function() {
				if(data["id"] == 'root000'){
					return;
				}
				right_window.find(".ipt .tw-div").addClass("active");
			});
			right_window.find(".ipt .tw-div .r-close").click(function() {
				if(data["id"] == 'root000'){
					return;
				}
				right_window.find(".ipt .tw-div").removeClass("active");
			});
			right_window.find(".ipt .choho-im-text1 .choho-im-text-content").on({
				"paste" : function(e) {
					if(data["id"] == 'root000'){
						return;
					}
					var editor = chohojQuery(this);
					var event = event || e;
					event = event.originalEvent;
					var clipboardData = event.clipboardData || window.clipboardData; 
					console.log(clipboardData.types);
				    var ua = window.navigator.userAgent;
				    // Mac平台下Chrome49版本以下 复制Finder中的文件的Bug Hack掉
				    if(clipboardData.items && clipboardData.items.length === 2 && clipboardData.items[0].kind === "string" && clipboardData.items[1].kind === "file" &&
				    		clipboardData.types && clipboardData.types.length === 2 && clipboardData.types[0] === "text/plain" && clipboardData.types[1] === "Files" &&
				        ua.match(/Macintosh/i) && Number(ua.match(/Chrome\/(\d{2})/i)[1]) < 49){
				        return;
				    }
				    for (var i = 0; i < clipboardData.items.length; i++) {
						if (/^text\/plain/.test(clipboardData.items[i].type) && clipboardData.items[i].kind == 'string') {
							clipboardData.items[i].getAsString(function(str) {
								editor.html(editor.html() + str.replace(/\r\n/g, "<br/>"));
							});
						} else if (/^image/.test(clipboardData.items[i].type)) {
							var blob = clipboardData.items[i].getAsFile();
							if (blob && blob.size > 0) {
								sendMessage('I', {type: 'blob', blob: blob});
							}
						} else if (clipboardData.items[i].kind == 'file'){
							var blob = clipboardData.items[i].getAsFile();
							if (blob && blob.size > 0) {
								sendMessage('F', {type: 'blob', blob: blob});
							}
						}
					}
					return false;
				},
				"drop" : function(e) {
					var editor = chohojQuery(this);
					var event = event || e;
					event = event.originalEvent;
					var clipboardData = event.dataTransfer; 
					console.log(clipboardData.types);
				    var ua = window.navigator.userAgent;
				    // Mac平台下Chrome49版本以下 复制Finder中的文件的Bug Hack掉
				    if(clipboardData.items && clipboardData.items.length === 2 && clipboardData.items[0].kind === "string" && clipboardData.items[1].kind === "file" &&
				    		clipboardData.types && clipboardData.types.length === 2 && clipboardData.types[0] === "text/plain" && clipboardData.types[1] === "Files" &&
				        ua.match(/Macintosh/i) && Number(ua.match(/Chrome\/(\d{2})/i)[1]) < 49){
				        return;
				    }
				    for (var i = 0; i < clipboardData.items.length; i++) {
						if (/^text\/plain/.test(clipboardData.items[i].type) && clipboardData.items[i].kind == 'string') {
							clipboardData.items[i].getAsString(function(str) {
								editor.html(editor.html() + str.replace(/\r\n/g, "<br/>"));
							});
						} else if (/^image/.test(clipboardData.items[i].type)) {
							var blob = clipboardData.items[i].getAsFile();
							if (blob && blob.size > 0) {
								sendMessage('I', {type: 'blob', blob: blob});
							}
						} else if (clipboardData.items[i].kind == 'file'){
							var blob = clipboardData.items[i].getAsFile();
							if (blob && blob.size > 0) {
								sendMessage('F', {type: 'blob', blob: blob});
							}
						}
					}
					return false;
				}
			});
			right_window.find(".ipt .choho-im-text1 .choho-im-text-content").keydown(function(e) {
				var hot_key = 13;
				var event = event || e;
	    		if (event.keyCode == hot_key && event.ctrlKey) {
		    		sendTextMessage();
		    		return false;
		    	}
			});
			
			var history = chohojQuery(html_template["choho-im-content-right-window-history"]);
			right_window.find(".icon-clock").on("click", function(){
				chohojQuery("#page-content .page-tab iframe ").contents().find(".rightMain-mask").addClass("active");
				history.addClass("active");
				listHistoryMessage(Math.floor(count / history_limit));
			});
			history.find(".r-time .r-fy .first").click(function() {
				if (!chohojQuery(this).hasClass("disabled")) {
					listHistoryMessage(0);
				}
			});
			history.find(".r-time .r-fy .prev").click(function() {
				if (!chohojQuery(this).hasClass("disabled")) {
					listHistoryMessage(page - 1);
				}
			});
			history.find(".r-time .r-fy .next").click(function() {
				if (!chohojQuery(this).hasClass("disabled")) {
					listHistoryMessage(page + 1);
				}
			});
			history.find(".r-time .r-fy .last").click(function() {
				if (!chohojQuery(this).hasClass("disabled")) {
					listHistoryMessage(Math.floor(count / history_limit));
				}
			});
			
			function listHistoryMessage(p) {
				page = page * history_limit >= count ? Math.floor(count / history_limit) : p;
				page = page < 0 ? 0 : page;
				var l = count - history_limit * Math.floor(count / history_limit);
				if (l == 0) l = history_limit;
				history.find(".textDiv").html(""); 
				wscall("listSessionMessages", {target: data['type'], session_id: data['id'], start: page * history_limit + 1, limit: history_limit, sort: 'send_time'}, function(rtn) {
					count = rtn.count;
					page = (rtn.start - 1) / history_limit;
					if (rtn.start - 1 > 0) {
						history.find(".r-time .r-fy .prev").removeClass("disabled");
						history.find(".r-time .r-fy .first").removeClass("disabled");
					} else {
						history.find(".r-time .r-fy .first").addClass("disabled");
						history.find(".r-time .r-fy .prev").addClass("disabled");
					}
					if (rtn.count <= (page + 1) * history_limit) {
						history.find(".r-time .r-fy .last").addClass("disabled");
						history.find(".r-time .r-fy .next").addClass("disabled");
					} else {
						history.find(".r-time .r-fy .last").removeClass("disabled");
						history.find(".r-time .r-fy .next").removeClass("disabled");
					}
					for (var i in rtn.resultset) {
						var message = chohojQuery(html_template["choho-im-content-right-window-history-message"]);
						message.addClass(rtn.resultset[i]['send_type']);
						message.find(".im-name").text(rtn.resultset[i]['system_user_name']);
						message.find(".timeDiv .timeSpan").text(rtn.resultset[i]['send_time_label']);
						if (rtn.resultset[i].type != "T") {
							if (rtn.resultset[i].mime_type && rtn.resultset[i].mime_type.indexOf("image/") == 0) {
								var img = chohojQuery(html_template["choho-im-content-right-window-history-message-image"]);
								img.find("img").attr("src", chohoim.config.http_url + '/cimcjs/rm/' + rtn.resultset[i].content);
								img.find("img").attr("alt", rtn.resultset[i].file_name || '屏幕截图');
								img.find("img").attr("download", rtn.resultset[i].file_name || '屏幕截图');
								img.find(".choho-im-file-name").text(rtn.resultset[i].file_name);
								img.find(".choho-im-file-name").attr("title", rtn.resultset[i].file_name);
								img.find(".im-file-size").text($chohocommon.formatfilesize(+rtn.resultset[i].file_size));
								message.find(".wenben").append(img);
							} else if (rtn.resultset[i].mime_type && rtn.resultset[i].mime_type.indexOf("audio/") == 0) {
								var img = chohojQuery(html_template["choho-im-content-right-window-history-message-audio"]);
								img.find("audio").attr("src", chohoim.config.http_url + '/cimcjs/rm/' + rtn.resultset[i].content);
								img.find("audio").attr("alt", rtn.resultset[i].file_name || '语音消息');
								img.find("audio").attr("download", rtn.resultset[i].file_name || '语音消息');
								img.find(".choho-im-file-name").text(rtn.resultset[i].file_name);
								img.find(".choho-im-file-name").attr("title", rtn.resultset[i].file_name);
								img.find(".choho-im-file-size").text($chohocommon.formatfilesize(+rtn.resultset[i].file_size));
								message.find(".choho-im-history-message-content").append(img);
							} else if (rtn.resultset[i].mime_type && rtn.resultset[i].mime_type.indexOf("video/") == 0) {
								var img = chohojQuery(html_template["choho-im-content-right-window-history-message-video"]);
								img.find("video").attr("src", chohoim.config.http_url + '/cimcjs/rm/' + rtn.resultset[i].content);
								img.find("video").attr("alt", rtn.resultset[i].file_name || '小视频');
								img.find("video").attr("download", rtn.resultset[i].file_name || '小视频');
								img.find(".choho-im-file-name").text(rtn.resultset[i].file_name);
								img.find(".choho-im-file-name").attr("title", rtn.resultset[i].file_name);
								img.find(".choho-im-file-size").text($chohocommon.formatfilesize(+rtn.resultset[i].file_size));
								message.find(".choho-im-history-message-content").append(img);
							} else {
								var img = chohojQuery(html_template["choho-im-content-right-window-history-message-file"]);
								img.find(".choho-im-file-download").attr("href", chohoim.config.http_url + '/cimcjs/rm/' + rtn.resultset[i].content);
								img.find(".choho-im-file-download").attr("download", rtn.resultset[i].file_name);
								img.find(".choho-im-file-name").text(rtn.resultset[i].file_name);
								img.find(".choho-im-file-name").attr("title", rtn.resultset[i].file_name);
								img.find(".choho-im-file-icon").css("backgroundImage", "url(" + getFileIcon(rtn.resultset[i].mime_type, rtn.resultset[i].file_name) + ")");
								img.find(".im-file-size").text($chohocommon.formatfilesize(+rtn.resultset[i].file_size));
								message.find(".wenben").append(img);
							}
						} else {
							message.find(".wenben").html(rtn.resultset[i].content);
						}
						history.find(".textDiv").append(message);
					}
					history.find(".textDiv").scrollTop(history.find(".textDiv")[0].scrollHeight);
				}, function(errormsg) {
					log.error("listSessionMessages", errormsg);
				});
			}
			
			right_window.find(".choho-im-toolbar .history").click(function() {
				if (history.hasClass("hide")) {
					history.removeClass("hide");
					right_window.find(".choho-im-bottom .choho-im-profile").addClass("hide");
					right_window.find(".choho-im-bottom .choho-im-userlist").addClass("hide");
					chohojQuery(this).addClass("selected");
				} else {
					history.addClass("hide");
					right_window.find(".choho-im-bottom .choho-im-profile").removeClass("hide");
					right_window.find(".choho-im-bottom .choho-im-userlist").removeClass("hide");
					chohojQuery(this).removeClass("selected");
					history.find(".choho-im-history-messages").html("");
				}
			});
			
			if (data['type'] == 'U') {
				right_profile.find(".renText img").attr("src", getAvatarSrc(data));
				right_profile.find(".renText .renName").text(data['name']);
				right_profile.find(".renText .renZhi").text(data['department_name'] + ' ' + (data['position'] || ''));
				right_profile.find(".msg .tel-val").html(data['telephone'] ? '<a href="callto:' + data['telephone'] + '">' + data['telephone'] + '</a>' : '');
				right_profile.find(".msg .mobile-val").html(data['mobile'] ? '<a href="callto:' + data['mobile'] + '">' + data['mobile'] + '</a>' : '');
				right_profile.find(".msg .qq-val").html(data['qq']? '<a href="http://wpa.qq.com/msgrd?v=1&uin=' + data['qq'] + '&site=qq&menu=yes" target="_blank">' + data['qq'] + '</a>' : '');
				right_profile.find(".msg .email-val").html(data['email'] ? '<a href="mailto:' + data['email'] + '">' + data['email'] + '</a>' : '');
			} else if (data['type'] == 'G' || data['type'] == 'D') {
				//var userlist = chohojQuery(html_template["choho-im-content-right-window-userlist"]);
				wscall("listContractorUsers", {contacter_type: data['type'], contacter_id: data['id'], sort: 'name'}, function(rtn) {
					function c(u) {
						u['type'] = 'U';
						var user = chohojQuery(html_template["choho-im-content-right-window-userlist-user"]);
						user.find("img").attr("src", getAvatarSrc(u));
						user.find("i").attr("class", getOnlineState(u));	
						user.find("i").attr("data-user-id", u['id']);
						user.find("span").text(u.name);	
						user.dblclick(function() {
							openWindow(u);
						});
						right_group.find("ul").append(user);
					}
					var ol = 0;
					for (var i in rtn.users) {
						if (rtn.users[i]['online_status'] != 'offline' && rtn.users[i]['online_status'] != 'hidden') {
							ol += 1;
							c(rtn.users[i]);
						}
					}
					for (var i in rtn.users) {
						if (rtn.users[i]['online_status'] != 'offline' && rtn.users[i]['online_status'] != 'hidden') {
						} else {
							c(rtn.users[i]);
						}
					}
					right_group.find("p").text("群组成员(" + ol + "/" + rtn.users.length + ")");
				}, function(errormsg) {
					log.error("listContractorUsers", errormsg);
				});
				if (data['type'] == 'D') {
					right_group.find('.group-footer').remove();
				}
				right_group.find(".group-footer .icon-users").parent().on("click", function(){
					var d = {id: data['id'], name: data['name']};
					openGroupEditWindow(d);
				});
				//TODO 退群，邀请好友
				right_content.append(right_group);
			}
		chohojQuery(".choho-im .choho-im-content-warper").removeAttr("style");
		right_window.find(".ipt .input").focus();
		
		right_content.append(right_window);
		right_content.append(right_profile);
		right_content.append(history);
	}
	
	var closeWindow = function(window_id) {}
	
	var getAvatarSrc = function(data) {
		if (data['avatar']) {
			return chohoim.config.http_url + '/cimcjs/rm/' + data['avatar'];
		} else if (data['type'] == 'G') {
			return chohoim.config.http_url + '/themes/im/css/img/group.png';
		} else if (data['type'] == 'D') {
			return chohoim.config.http_url + '/themes/im/css/img/department.png';
		} else if (data['type'] == 'U' && +data['gender'] == 0 ) {
			return chohoim.config.http_url + '/themes/im/css/img/female.png';
		}
		return chohoim.config.http_url + '/themes/im/css/img/male.png';
	}
	
	var getOnlineState = function(data) {
		if (data['online_status'] && data['type'] == 'U') {
			return "choho-im-status-icon " + (data['online_status'] == 'hidden' ? online_statuses['offline']["clazz"] : online_statuses[data['online_status']]["clazz"]);
		} else if (data['type'] == 'U') {
			return "choho-im-status-icon " + online_statuses['offline']["clazz"];
		}
		return "choho-im-status-icon hide";
	}
	
	var getFileIcon = function(mime_type, filename) {
		if (filename && filename.lastIndexOf(".") >= 0) {
			var ext = filename.substring(filename.lastIndexOf(".") + 1);
			return chohoim.config.http_url + '/themes/im/fileicons/ext/' + ext + ".png";
		} else if (mime_type) {
			return chohoim.config.http_url + '/themes/im/fileicons/' + mime_type + ".png";
		} else {
			return chohoim.config.http_url + '/themes/im/fileicons/unkown.png';
		}
	}
	
	function drawSession(session, prepend) {
		var jobj = chohojQuery(html_template["choho-im-sidebar-choho-im-comment"]);
		jobj.attr("data-window-id", session['target'] + '-' + session['session_id']);
		jobj.find("img").attr("src", getAvatarSrc(session));
		jobj.find(".status").attr("class", getOnlineState(session));
		if (session['target'] == 'U') {
			jobj.find(".status").attr("data-user-id", session['session_id']);
		}
		jobj.find(".titleP").text(session['session_name']);
		var send_date = new Date(+session['last_send_time']);
		var now = new Date();
		var time_label = $chohocommon.formatdate(send_date, "yyyy-MM-dd");
		if ($chohocommon.formatdate(send_date, "yyyy-MM-dd") == $chohocommon.formatdate(now, "yyyy-MM-dd")) {
			time_label = $chohocommon.formatdate(send_date, "HH:mm");
		} else if ($chohocommon.formatdate(send_date, "yyyy") == $chohocommon.formatdate(now, "yyyy")) {
			time_label = $chohocommon.formatdate(send_date, "MM-dd");
		}
		jobj.find(".daySpan").text(time_label);
		
		var content = $chohocommon.removehtml(session['last_content']);
		if (session['last_content_type'] == "F" || session['last_content_type'] == "I") {
			content = "[文件]";
		}
		if (session['last_content_type'] != "T") {
			if (session['last_content_mime_type'] && session['last_content_mime_type'].indexOf("image/") == 0) {
				content = '图片: ' + (session['last_content'] || '屏幕截图');
			} else if (session['last_content_mime_type'] && session['last_content_mime_type'].indexOf("audio/") == 0) {
				content = '音频: ' + (session['last_content'] || '语音消息');
			} else if (session['last_content_mime_type'] && session['last_content_mime_type'].indexOf("video/") == 0) {
				content = '视频: ' + (session['last_content'] || '小视频');
			} else {
				content = '文件: ' + session['last_content'];
			}
		}
		if (session['target'] != "U") {
			content = session["last_send_user_name"] + ": " + content;
		}
		jobj.find(".textP").text(content.substring(0, 40));
		jobj.find(".weiSpan").text(session['noreadnum']);
		if (+session['noreadnum'] > 0) {
			jobj.find(".weiSpan").removeClass('hide');
		}
		jobj.on({
			"click": function() {
				if (+session['noreadnum'] > 0) {
					var n2 = session['noreadnum'];
					var cjn = chohojQuery(".head .t-icon .icon-bubbles").find("span");
					var n1 = cjn.text();
					var n3 = n1 - n2;
					cjn.text(n3);
					if(n3 < 1){
						cjn.hide();
					}
				}
				openWindow(session);
				
			},
			"dblclick": function() {
				openWindow(session);
				if (+session['noreadnum'] > 0) {
					var n2 = session['noreadnum'];
					var cjn = chohojQuery(".head .t-icon .icon-bubbles").find("span");
					var n1 = cjn.text();
					var n3 = n1 - n2;
					cjn.text(n3);
					if(n3 < 1){
						cjn.hide();
					}
				}
			}
		});
		if (prepend) {
			chohojQuery("#page-content .page-tab iframe ").contents().find(".leftMain .listDiv").prepend(jobj);
		} else {
			chohojQuery("#page-content .page-tab iframe ").contents().find(".leftMain .listDiv").append(jobj);
		}
		if(session['session_id'] == 'root000'){
			if (+session['noreadnum'] > 0) {
				var n2 = session['noreadnum'];
				var cjn = chohojQuery(".head .t-icon .icon-bubbles").find("span");
				var n1 = cjn.text();
				var n3 = n1 - n2;
				cjn.text(n3);
				if(n3 < 1){
					cjn.hide();
				}
			}
			setTimeout(function() {
				openWindow(session);
			}, 300);
			
		}
	}
	
	var sessions = [];
	var loadSessions = function() {
		wscall("loadSessions", {sort: "last_send_time desc", start: 1, limit: 100}, function(rtn) {
			sessions = rtn.sessions;
			var noreadnum = 0;
			for (var i in sessions) {
				noreadnum += +sessions[i]['noreadnum'];
				drawSession(sessions[i]);
			}
			chohojQuery(".head .t-icon .icon-bubbles").find("span").text(noreadnum);
			if (noreadnum > 0) {
				chohojQuery(".head .t-icon .icon-bubbles").find("span").show();
			} else {
				chohojQuery(".head .t-icon .icon-bubbles").find("span").hide();
			}
		}, function(errormsg) {
			log.error("loadSessions", errormsg);
		});
	};
	

	var contactors = {
		id : '0',
		type : 'D',
		children : []
	};
	
	var loadContactors = function() {
		wscall("loadContactors", {type: ['D', 'U'], sort: 'order_no'}, function(rtn) {
			contactors = {
					id: '0',
					type: 'D',
					node_id: 'D|0',
					children: []
				};
			function setChildren(tn) {
				for (var i = 0; i < rtn.contactors.length; i++) {
					if (tn['id'] == rtn.contactors[i]['parent_department'] && 
							(rtn.contactors[i]['type'] == tn['type'] || 
									(rtn.contactors[i]['type'] == 'U' && tn['type'] == 'D'))) {
						rtn.contactors[i]['node_id'] = rtn.contactors[i]['type'] + "|" + rtn.contactors[i]['id'],
						rtn.contactors[i]['children'] = [];
						setChildren(rtn.contactors[i]);
						tn.children.push(rtn.contactors[i]);
						rtn.contactors.splice(i, 1);
						i--;
					}
				}
			}
			setChildren(contactors);
			chohojQuery("#page-content .page-tab iframe ").contents().find(".content .leftMain .jg-listDiv").html('');
			drawChildren(chohojQuery("#page-content .page-tab iframe ").contents().find(".content .leftMain .jg-listDiv"), contactors);
		}, function(errormsg) {
			log.error("loadSessions", errormsg);
		});
	};
	function drawChildren(pjobj, tn) {
		var dhang;
		if(tn.id == '0'){
			pjobj.html('');
			dhang = chohojQuery(html_template["choho-im-sidebar-choho-im-contactor-daohang"]);
			var daoh = $(' <a data-id="'+tn["type"]+'=-'+tn["id"]+'" href="javascript:void(0)">首页 </a>');
			daoh.on("click", function(){
				drawChildren(pjobj, tn);
				chohojQuery(this).nextAll().remove();
			});
			dhang.append(daoh);
		}else{
			dhang = pjobj.find(".dhP");
			var daoh = $(' <a data-id="'+tn["type"]+'=-'+tn["id"]+'" href="javascript:void(0)"> >'+tn["name"]+' </a>');
			daoh.on("click", function(){
				drawChildren(pjobj, tn);
				chohojQuery(this).nextAll().remove();
				//chohojQuery(this).remove();
			});
			dhang.append(daoh);
		}
		pjobj.html('');
		pjobj.append(dhang);
		for (var i = 0; i < tn.children.length; i++) {
			if (tn.children[i].type != 'U' || (tn.children[i].online_status != 'offline' && tn.children[i].online_status != 'hidden')) {
				drawNode(pjobj, tn.children[i]);
			}
		}
		for (var i = 0; i < tn.children.length; i++) {
			if (tn.children[i].type != 'U' || (tn.children[i].online_status != 'offline' && tn.children[i].online_status != 'hidden')) {
			} else {
				drawNode(pjobj, tn.children[i]);
			}
		}
	}
	function drawNode(pjobj, tn) {
		var jobj = chohojQuery(html_template["choho-im-sidebar-choho-im-contactor"]);
		jobj.find("img").attr("src", getAvatarSrc(tn));
		/*jobj.find(".choho-im-avatar .choho-im-status-icon").attr("class",getOnlineState(tn));*/
		/*if (tn['type'] == 'U') {
			jobj.find(".choho-im-avatar .choho-im-status-icon").attr("data-user-id", tn['id']);
		}*/
		jobj.find(".titleP > .content-text").text(tn['name']);
		if (tn.children.length > 0) {
			jobj.find(".titleP > .child_num").text("("+tn.children.length+")");
			jobj.find(".titleP > .child_num").removeClass('hide');
			jobj.find(".fa-angle-right").removeClass('hide');
		}
		jobj.find(".titleP").on({
			"click": function() {
				openWindow(tn);
			}
		});
		jobj.find("img").on({
			"click": function() {
				openWindow(tn);
			}
		});
		jobj.children(".fa-angle-right").on({
			"click": function() {
				if (tn['children'].length > 0) {
					drawChildren(pjobj, tn);
				}
			},
		});
		pjobj.append(jobj);
		/*if (+tn['children'].length == 0) {
			jobj.find(".content-red").append(">");
			jobj.find(".content-red").on("click", function(){
				
			});
			jobj.find(".choho-im-contactor-children").remove();
			jobj.removeClass("collapsed");
		}*/
	}
	var groups = [];
	var loadGroups = function() {
		wscall("loadContactors", {type: ['G'], sort: 'order_no'}, function(rtn) {
			groups = rtn.contactors;
			function draw(contactor) {
				var jobj = chohojQuery(html_template["choho-im-sidebar-choho-im-group"]);
				jobj.attr("data-window-id", contactor['type'] + '-' + contactor['id']);
				jobj.find("img").attr("src", getAvatarSrc(contactor));
				jobj.find(".titleP").text(contactor['name']);
				//jobj.find(".choho-im-group-content .choho-im-group-text").text(contactor['last_sender'] + '：' + $chohocommon.removehtml(contactor['last_content']).substring(0, 20));
				jobj.on({
					"click": function() {
					},
					"dblclick": function() {
						openWindow(contactor);
					}
				});
				chohojQuery("#page-content .page-tab iframe ").contents().find(".leftMain .qz-listDiv").append(jobj);
			}
			for (var i in groups) {
				if (groups[i]['type'] == 'G') {
					draw(groups[i]);
				}
			}
		}, function(errormsg) {
			log.error("loadSessions", errormsg);
		});
	};
	
	var setUserInfo = function(userinfo) {
		chohoim.userinfo = userinfo;
		userinfo.type = 'U';
		//chohojQuery(".choho-im .choho-im-toggle").css("backgroundImage", "url('" + getAvatarSrc(userinfo) + "')");
		chohojQuery(".choho-im .choho-im-sidebar > .choho-im-profile > .choho-im-avatar > .choho-im-avatar-img").css("backgroundImage", "url('" + getAvatarSrc(userinfo) + "')");

		chohojQuery(
				".choho-im .choho-im-sidebar > .choho-im-profile > .choho-im-title > .choho-im-name")
				.text(userinfo["name"]);
		chohojQuery(
				".choho-im .choho-im-sidebar > .choho-im-profile > .choho-im-title > .choho-im-department")
				.text(userinfo["department_name"] + ' ' + (userinfo['position'] || ''));
	};

	var online_statuses = {
		'online' : {
			clazz : 'iconfont icon-zaixianim',
			text : '在线',
			canselect : true
		},
		'busy' : {
			clazz : 'iconfont icon-mangluim',
			text : '忙碌',
			canselect : true
		},
		'dnd' : {
			clazz : 'icon-ban',
			text : '请勿打扰',
			canselect : true
		},
		'afk' : {
			clazz : 'iconfont icon-likaiim',
			text : '离开',
			canselect : true
		},
		'hidden' : {
			clazz : 'choho-im-hidden',
			text : '隐身',
			canselect : false
		},
		'offline' : {
			clazz : 'choho-im-offline',
			text : '离线',
			canselect : false
		},
	};

	var initOnlineStatusSelect = function() {
		var status_select = [];
		for ( var key in online_statuses) {
			if (online_statuses[key]["canselect"]) {
				var _to = chohojQuery(html_template["choho-im-sidebar-status-select-option"]);
				_to.attr("online-status", key);
				_to.find(".choho-im-status-icon").addClass(
						online_statuses[key]["clazz"]);
				_to.find(".choho-im-status-text").text(
						online_statuses[key]["text"]);
				chohojQuery(
						".choho-im .choho-im-sidebar > .choho-im-profile > .choho-im-title > .choho-im-status > .choho-im-status-select")
						.append(_to);
			}
		}
		chohojQuery(
				".choho-im .choho-im-sidebar > .choho-im-profile > .choho-im-title > .choho-im-status > .choho-im-status-select > a")
				.click(function() {
					setOnlineStatus(chohojQuery(this).attr("online-status"));
				});
		chohojQuery(
				".choho-im .choho-im-sidebar > .choho-im-profile > .choho-im-title > .choho-im-status")
				.on(
						{
							mouseover : function() {
								chohojQuery(
										".choho-im .choho-im-sidebar > .choho-im-profile > .choho-im-title > .choho-im-status > .choho-im-status-select")
										.show();
							},
							mouseout : function() {
								chohojQuery(
										".choho-im .choho-im-sidebar > .choho-im-profile > .choho-im-title > .choho-im-status > .choho-im-status-select")
										.hide();
							}
						});
	};

	var setOnlineStatus = function(status) {
		chohojQuery(
				".choho-im .choho-im-sidebar > .choho-im-profile > .choho-im-title > .choho-im-status > .choho-im-status-icon")
				.attr(
						"class",
						"choho-im-status-icon "
								+ online_statuses[status]["clazz"]);
		chohojQuery(
				".choho-im .choho-im-sidebar > .choho-im-profile > .choho-im-title > .choho-im-status > .choho-im-status-text")
				.text(online_statuses[status]["text"]);
		chohojQuery(".choho-im .choho-im-toggle > .choho-im-status-icon").attr(
				"class",
				"choho-im-status-icon " + online_statuses[status]["clazz"]);
		chohojQuery(
				".choho-im .choho-im-sidebar > .choho-im-profile > .choho-im-title > .choho-im-status > .choho-im-status-select")
				.hide();
		/*wscall("changeOnlineStatus", {online_status: status}, function(rtn) {
		}, function(errormsg) {
			log.error("setOnlineStatus", errormsg);
		});*/
	}
	
	chohoim.actions = {
		send: function(data, from) {
			try{
				if(typeof crtech != "undefined"){
					crtech.IMMessage("123");
					var uuid = data.send_to;
					if (data.target == 'U' && data.send_type == 'to') {
						uuid = from.userinfo.id;
					}
					if(uuid == 'root000' && data.auto_audit_id){
						//crtech.modal("/w/ipc/auditresult/notice.html?uid="+chohoim.userinfo.id, "250", "85");
					}
				}
			}catch (e) {
			}
			
			from.userinfo.type = 'U';
			log.log("receive message: " + JSON.stringify(from) + JSON.stringify(data));
			var window_id = data.target + "-" + data.send_to;
			if (data.target == 'U' && data.send_type == 'to') window_id = data.target + "-" + from.userinfo.id;
			var right_window = chohojQuery("#page-content .page-tab iframe ").contents().find(".right-content");
			var comment = chohojQuery("#page-content .page-tab iframe ").contents().find(".leftMain .listDiv .list[data-window-id='" + window_id + "']");
			//重绘会话列表，顺序和未读
			var noreadnum = 0;
			if ((right_window.length == 0 || right_window.is(":hidden")) && chohoim.userinfo.id != from.userinfo.id) {
				noreadnum = +comment.find(".weiSpan").text() + 1;
			}
			if (comment.length > 0) {
				comment.remove();
			}
			var session  = {
				target: data.target,
				session_id: data.session_id,
				session_name: data.session_name,
				read_user_id: data.read_user_id,
				last_content: data.file_name || data.content,
				last_content_type: data.type,
				last_content_mime_type: data.mime_type,
				last_send_user_name: data.system_user_name,
				last_send_time: new Date(+data.send_time),
				noreadnum: noreadnum
			};
			var user;
			if (data.target == 'G') {
				for (var i in groups) {
					if (groups[i].id == data.send_to) {
						session = chohojQuery.extend(true, session, group[i]);
						user = group[i];
					}
				}
			} else {
				var bk = false;
				function b(tn) {
					if (tn.type == data.target && ((tn.type == 'U' && tn.id == data.session_id) || (tn.type != 'U' && tn.id == data.send_to))) {
						session = chohojQuery.extend(true, session, tn);
						user = tn;
						bk = true;
					}
					if (!bk && tn.children.length > 0) {
						for (var i in tn.children) {
							b(tn.children[i]);
							if (bk) break;
						}
					}
				}
				b(contactors);
			}
			drawSession(session, true);
			//若消息窗口开启，则重绘消息窗口
			if (right_window.length > 0) {
				
				var message = chohojQuery(html_template["choho-im-content-messages-message"]);
				message.attr("class",   data.send_type == 'from'?'rightText':'leftText');
				message.find("img").attr("src", getAvatarSrc(data['send_type'] == 'from' ? chohoim.userinfo : data));
				message.find(".im-name").text(data['system_user_name']);
				message.find(".timeDiv .timeSpan").text(data.send_time_label);
				
				if (data.type == 'F' || data.type == 'I') {
					if (data.mime_type && data.mime_type.indexOf("image/") == 0) {
						var img = chohojQuery(html_template["choho-im-content-messages-message-image"]);
						img.find("img").attr("src", chohoim.config.http_url + '/cimcjs/rm/' + data.content);
						img.find("img").attr("alt", data.file_name || '屏幕截图');
						img.find("img").attr("download", data.file_name || '屏幕截图');
						img.find(".choho-im-file-name").text(data.file_name);
						img.find(".choho-im-file-name").attr("title", data.file_name);
						img.find(".im-file-size").text($chohocommon.formatfilesize(+data.file_size));
						message
						.find(
								".wenben")
						.append(img);
						right_window.find(".textDiv").append(message);
						setTimeout(function() {
							right_window.find(".textDiv").scrollTop(right_window.find(".textDiv")[0].scrollHeight);
						}, 100);
					} else if (data.mime_type && data.mime_type.indexOf("audio/") == 0) {
						var img = chohojQuery(html_template["choho-im-content-messages-message-video"]);
						img.find("video").attr("src", chohoim.config.http_url + '/cimcjs/rm/' + data.content);
						img.find("video").attr("alt", data.file_name || '小视频');
						img.find("video").attr("download", data.file_name || '小视频');
						img.find(".choho-im-file-name").text(data.file_name);
						img.find(".choho-im-file-name").attr("title", data.file_name);
						img.find(".choho-im-file-size").text($chohocommon.formatfilesize(+data.file_size));
						message
						.find(
								".choho-im-message-content .choho-im-message-message")
						.append(img);
						right_window.find(".choho-im-messages").append(message);
						setTimeout(function() {
							right_window.find(".choho-im-messages").scrollTop(right_window.find(".choho-im-messages")[0].scrollHeight);
						}, 100);
					} else if (data.mime_type && data.mime_type.indexOf("video/") == 0) {
						var img = chohojQuery(html_template["choho-im-content-messages-message-video"]);
						img.find("video").attr("src", chohoim.config.http_url + '/cimcjs/rm/' + data.content);
						img.find("video").attr("alt", data.file_name || '小视频');
						img.find("video").attr("download", data.file_name || '小视频');
						img.find(".choho-im-file-name").text(data.file_name);
						img.find(".choho-im-file-name").attr("title", data.file_name);
						img.find(".choho-im-file-size").text($chohocommon.formatfilesize(+data.file_size));
						message
						.find(
						".choho-im-message-content .choho-im-message-message")
						.append(img);
						right_window.find(".choho-im-messages").append(message);
						setTimeout(function() {
							right_window.find(".choho-im-messages").scrollTop(right_window.find(".choho-im-messages")[0].scrollHeight);
						}, 100);
					} else {
						var img = chohojQuery(html_template["choho-im-content-messages-message-file"]);
						img.find(".choho-im-file-download").attr("href", chohoim.config.http_url + '/cimcjs/rm/' + data.content);
						img.find(".choho-im-file-download").attr("download", data.file_name);
						img.find(".choho-im-file-name").text(data.file_name);
						img.find(".choho-im-file-name").attr("title", data.file_name);
						img.find(".choho-im-file-icon").css("backgroundImage", "url(" + getFileIcon(data.mime_type, data.file_name) +")");
						img.find(".im-file-size").text($chohocommon.formatfilesize(+data.file_size));
						message
						.find(
								".wenben")
						.append(img);
						right_window.find(".textDiv").append(message);
						right_window.find(".textDiv").scrollTop(right_window.find(".textDiv")[0].scrollHeight);
					}
				}  else {
					message.find(".wenben").html(data.content);
					right_window.find(".textDiv").append(message);
					right_window.find(".textDiv").scrollTop(right_window.find(".textDiv")[0].scrollHeight);
				}
				wscall("read", {target: data['type'], session_id: data['send_type']}, function(rtn) {
				}, function(errormsg) {
					log.error("read", errormsg);
				});
			}
			//若消息窗口目前未激活，则发送系统通知
			if ((right_window.length == 0 || right_window.is(":hidden")) && chohoim.userinfo.id != from.userinfo.id) {
				var content = [];
				if (session['type'] == "U") {
					content.push([from.userinfo.department_name ? from.userinfo.department_name : '',
							from.userinfo.position ? from.userinfo.position : '',
									from.userinfo.name ? from.userinfo.name : ''].join(" "));
				} else {
					content.push([session.name, from.userinfo.name ? from.userinfo.name : ''].join(" "));
				}
				if (data['type'] != "T") {
					if (data.content.contentType && data.content.contentType.indexOf("image/") == 0) {
						content.push('发送图片：\r\n' + (data.content.fileName || '屏幕截图') + " " + $chohocommon.formatfilesize(+data.content.size));
					} else if (data.content.contentType && data.content.contentType.indexOf("audio/") == 0) {
						content.push('发送音频：\r\n' + (data.content.fileName || '语音消息') + " " + $chohocommon.formatfilesize(+data.content.size));
					} else if (data.content.contentType && data.content.contentType.indexOf("video/") == 0) {
						content.push('发送视频：\r\n' + (data.content.fileName || '小视频') + " " + $chohocommon.formatfilesize(+data.content.size));
					} else {
						content.push('发送文件：\r\n' + data.content.fileName + " " + $chohocommon.formatfilesize(+data.content.size));
					}
				} else {
					content.push('说：\r\n' + $chohocommon.removehtml(data['content']).substring(0, 20));
				}
				$chohocommon.winNotify({
					content: content.join(""),
					clickback: function() {
						chohojQuery(".choho-im .choho-im-toggle").hide();
						chohojQuery(".choho-im .choho-im-warper").attr("style", "");
						openWindow(session);
						if (+session['noreadnum'] > 0) {
							var n2 = session['noreadnum'];
							var cjn = chohojQuery(".choho-im .choho-im-toggle .choho-im-nums");
							var n1 = cjn.text();
							var n3 = n1 - n2;
							cjn.text(n3);
							if(n3 < 1){
								cjn.addClass("hide");
							}
						}
						window.focus();
					},
					icon: getAvatarSrc(session),
					id: from.userinfo.id
				});
			}
		},
		changeUserOnlineStatus: function(params) {
			log.log("changeUserOnlineStatus", params);
			chohojQuery(".choho-im")
					.find("[data-user-id='" + params.userid + "']")
					.attr(
							"class",
							"choho-im-status-icon "
									+ (params['online_status'] == 'hidden' ? online_statuses['offline']["clazz"]
											: online_statuses[params['online_status']]["clazz"]));
			
			for (var i in sessions) {
				if (sessions[i].type == 'U' && sessions[i].id == params.userid) {
					sessions[i]['online_status'] = params['online_status'];
					break;
				}
			}
			for (var i in groups) {
				if (groups[i].type == 'U' && groups[i].id == params.userid) {
					groups[i]['online_status'] = params['online_status'];
					break;
				}
				groups[i]['online_status'] = params['online_status'];
			}
			
//			function ct(tn) {
//				if (tn.type == 'U' && tn.id == params.userid) {
//					tn['online_status'] = params['online_status'];
//					if (tn.id != chohoim.userinfo.id) {
//						var content = [
//						               tn.department_name ? tn.department_name + ' ' : '',
//						            		   tn.position ? tn.position + ' ' : '',
//						            				   tn.name ? tn.name + ' ' : '' ];
//						if (tn.online_status == "online") {
//							content.push('已上线');
//						} else if (tn.online_status == "offline") {
//							content.push('已下线');
//						} else if (tn.online_status == "hidden") {
//							content.push('已下线');
//						} else if (tn.online_status == "busy") {
//							content.push('正在忙碌');
//						} else if (tn.online_status == "dnd") {
//							content.push('目前不希望被打扰');
//						} else if (tn.online_status == "afk") {
//							content.push('已离开');
//						}
//						$chohocommon.winNotify({
//							content: content.join(" "),
//							clickback: function() {
//								chohojQuery(".choho-im .choho-im-toggle").hide();
//								chohojQuery(".choho-im .choho-im-warper").attr("style", "");
//								openWindow(tn);
//								window.focus();
//							},
//							icon: getAvatarSrc(tn),
//							id: params.userid
//						});
//					}
//				}
//				if (tn.children.length > 0) {
//					for (var i in tn.children) {
//						ct(tn.children[i]);
//					}
//				}
//			}
//			
//			ct(contactors);
		},
		saveGroup: function(data){
			var window_id = data.id;
			if (chohojQuery(".choho-im .choho-im-leftside").children(".choho-im-window[data-window-id='" + window_id + "']").length == 0) {
				loadGroups();
			}else{
				//刷新聊天框右边的群组成员
				var userlist = chohojQuery('.choho-im .choho-im-content-warper .choho-im-content .choho-im-right .choho-im-window .choho-im-bottom .choho-im-userlist');
				userlist.find(".choho-im-userlist-list").html('');
				wscall("listContractorUsers", {contacter_type: 'G', contacter_id: data['id'], sort: 'name'}, function(rtn) {
					function c(u) {
						u['type'] = 'U';
						var user = chohojQuery(html_template["choho-im-content-right-window-userlist-user"]);
						user.find(".choho-im-avatar .choho-im-avatar-img").css("backgroundImage", "url('" + getAvatarSrc(u) + "')");
						user.find(".choho-im-avatar .choho-im-status-icon").attr("class", getOnlineState(u));	
						user.find(".choho-im-avatar .choho-im-status-icon").attr("data-user-id", u['id']);
						user.find(".choho-im-title").text(u.name);	
						user.dblclick(function() {
							openWindow(u);
						});
						userlist.find(".choho-im-userlist-list").append(user);
					}
					var ol = 0;
					for (var i in rtn.users) {
						if (rtn.users[i]['online_status'] != 'offline' && rtn.users[i]['online_status'] != 'hidden') {
							ol += 1;
							c(rtn.users[i]);
						}
					}
					for (var i in rtn.users) {
						if (rtn.users[i]['online_status'] != 'offline' && rtn.users[i]['online_status'] != 'hidden') {
						} else {
							c(rtn.users[i]);
						}
					}
					userlist.find(".choho-im-userlist-title").text("群组成员(" + ol + "/" + rtn.users.length + ")");
				}, function(errormsg) {
					log.error("listContractorUsers", errormsg);
				});
			
			}
		},
		sendTips : function(pa){
			/*if(typeof crtech != "undefined"){
				crtech.closeModal();
			}*/
			if(typeof crtech != "undefined"){
				if((chohoim_msg_data && (pa==null || pa=='' || pa == undefined))
					|| (pa && (chohoim_msg_data==null || chohoim_msg_data=='' || chohoim_msg_data==undefined))){
					crtech.modal("/w/hospital_common/system/msgalert/index.html?uid="+chohoim.params.userid+"&_CRSID="+chohoim.params._CRSID, "250", "85");
				}else if(chohoim_msg_data && pa){
					if(pa.count == chohoim_msg_data.count){
						var new_msg_ids = pa.msg_ids;
						var old_msg_ids = chohoim_msg_data.msg_ids;
						var msg_flash_check = false;
						if(new_msg_ids && old_msg_ids){
							var new_list_ids = new_msg_ids.split(',');
							var old_list_ids = old_msg_ids.split(',');
							for (var i = 0; i < new_list_ids.length; i++) {
								if(old_list_ids.indexOf(new_list_ids[i]) == -1){
									msg_flash_check = true;
									break;
								}
							}
							for (var i = 0; i < old_list_ids.length; i++) {
								if(new_list_ids.indexOf(old_list_ids[i]) == -1){
									msg_flash_check = true;
									break;
								}
							}
						}
						if(msg_flash_check){
							crtech.modal("/w/hospital_common/system/msgalert/index.html?uid="+chohoim.params.userid+"&_CRSID="+chohoim.params._CRSID, "250", "85");
						}
					}else{
						crtech.modal("/w/hospital_common/system/msgalert/index.html?uid="+chohoim.params.userid+"&_CRSID="+chohoim.params._CRSID, "250", "85");
					}
				}
				chohoim_msg_data = pa;
			}
		}
	}

	var html_template = {
		"choho-im" : [
				'<div class="choho-im">',
				'	<div class="choho-im-toggle">',
				'		<span class="choho-im-nums hide">0</span>',
				'		<i class="choho-im-status-icon choho-im-offline"></i>',
				'	</div>',
				'	<div class="choho-im-warper" style="display:none;">',
				'<div class="choho-im-group-warper hide">',
					'<div class="group-bar">',
						'<span>群组名字</span>',
						'<input name="group_name" class="group_name" value="群组名字"/>',
						'<a href="javascript:void(0);" class="edit-groupname"><i class="cimfa cimfa-edit"></i></a>',
						
						'<a href="javascript:void(0);" class="close-group"><i class="cimfa cimfa-times"></i></a>',
					'</div>',
					'<div class="group-left">',
						/*'<input class="group-edit"/>',*/
						'<div class="group-left-user">',
						'</div>',
					'</div>',
					'<div class="group-right">',
						'<div class="group-right-user">',
						'</div>',
						'<div class="group-right-button">',
						'	<a class="group-save">确定</a>',
						'	<a class="group-cancel">取消</a>',
						'</div>',
					'</div>',
				'</div>',
				
				'		<div class="choho-im-content-warper" style="display:none;">',
				'			<div class="choho-im-content">',
				'				<div class="choho-im-leftside">',
				'				</div>',
				'				<div class="choho-im-right">',
				'					<div class="choho-im-oper">',
				'						<a class="choho-im-oper-btn btn-max" href="javascript:void(0)"><i class="cimfa cimfa-square-o"></i></a>',
				'						<a class="choho-im-oper-btn btn-normal" href="javascript:void(0)"><i class="cimfa cimfa-delicious"></i></a>',
				'						<a class="choho-im-oper-btn btn-close" href="javascript:void(0)"><i class="cimfa cimfa-times"></i></a>',
				'					</div>',
				'				</div>',
				'			</div>',
				'		</div>',
				'		<div class="choho-im-sidebar">',
				'			<div class="choho-im-profile">',
				'				<div class="choho-im-oper">',
				'					<a class="choho-im-oper-btn" href="javascript:void(0)"><i class="cimfa cimfa-caret-square-o-down"></i></a>',
				'					<a class="choho-im-oper-btn btn-close" href="javascript:void(0)"><i class="cimfa cimfa-times"></i></a>',
				'				</div>',
				'				<div class="choho-im-avatar">',
				'					<div class="choho-im-avatar-img"></div>',
				'				</div>',
				'				<div class="choho-im-title">',
				'					<h3 class="choho-im-name"></h3>',
				'					<div class="choho-im-department"></div>',
				'					<div class="choho-im-status">',
				'						<i class="choho-im-status-icon choho-im-offline"></i>',
				'						<div class="choho-im-status-text">离线</div>',
				'						<div class="choho-im-status-select">',
				'						</div>',
				'					</div>',
				'				</div>',
				'			</div>',
				'			<div class="choho-im-search">',
				'				<input type="search" class="choho-im-search-input" placeholder="搜索：联系人、部门、群组" title="输入姓名、拼音、电话号码查找联系人，还可以输入电话号码查找陌生人">',
				'				<div class="choho-im-search-warper">',
				'				  <div class="choho-im-search-result">',
				'					<div class="choho-im-search-result-title">联系人</div>',
				'					<div class="choho-im-search-result-list U"></div>',
				'				  </div>',
				'				  <div class="choho-im-search-result">',
				'				    <div class="choho-im-search-result-title">部门</div>',
				'				    <div class="choho-im-search-result-list D"></div>',
				'				  </div>',
				'				  <div class="choho-im-search-result">',
				'				  	<div class="choho-im-search-result-title">群组</div>',
				'				  	<div class="choho-im-search-result-list G"></div>',
				'				  </div>',
				'				</div>',
				'				<a class="choho-im-search-btn btn-search" href="javascript:void(0)"><i class="cimfa cimfa-search"></i></a>',
				'				<a class="choho-im-search-btn btn-clear" href="javascript:void(0)"><i class="cimfa cimfa-times"></i></a>',
				'			</div>',
				'			<div class="choho-im-tabs">',
				'				<a class="choho-im-tab selected" data-tab-id="comments" title="会话" href="javascript:void(0)">',
				'					<i class="cimfa cimfa-comments-o"></i>',
				'				</a>',
				'				<a class="choho-im-tab" data-tab-id="contactor" title="通讯录" href="javascript:void(0)">',
				'					<i class="cimfa cimfa-sitemap"></i>',
				'				</a>',
				'				<a class="choho-im-tab" data-tab-id="usergroup" title="我的群组" href="javascript:void(0)">',
				'					<i class="cimfa cimfa-users"></i>',
				'				</a>',
				'			</div>',
				'			<div class="choho-im-content">',
				'				<div class="choho-im-tab selected" data-tab-id="comments"></div>',
				'				<div class="choho-im-tab" data-tab-id="contactor"></div>',
				'				<div class="choho-im-tab choho-im-group" data-tab-id="usergroup">',
				'				  <div class="choho-im-group-toolbar">',
				'					<div class="choho-im-toolbar-right">',
				'					  <a class="choho-im-btn" href="javascript:void(0)" title="创建新的讨论组"><i class="cimfa cimfa-plus"></i>创建</a>	',			
				'					</div>',
				'			      </div>',
				'				  <div class="choho-im-group-warp">',
				'				  </div>',
				'				</div>',				
				'			</div>', 
				'		</div>', 
				'	</div>', 
				'</div>' ].join(""),
		"choho-im-sidebar-status-select-option" : [
				'<a href="javascript:void(0);" class="choho-im-status-option">',
				'	<span class="choho-im-status-label">',
				'		<i class="choho-im-status-icon"></i>', '	</span>',
				'	<span class="choho-im-status-text"></span>', '</a>' ]
				.join(""),
		"choho-im-sidebar-search-result-comment" : [
   		        '<a class="choho-im-comment" href="javascript:void(0);">',
   				'	<div class="choho-im-avatar">',
   				'		<div class="choho-im-avatar-img"></div>',
   				'	</div>',
   				'	<div class="choho-im-comment-content">',
   				'		<div class="choho-im-comment-name"></div>',
   				'		<div class="choho-im-comment-text"></div>',
   				'	</div>',
   				'</a>'].join(""),
		"choho-im-sidebar-choho-im-comment" : [
				'<div class="list">',
				'<img src="../img/logo1.png" alt="">',
				'<p class="titleP">测试群会话</p>',
				'<p class="textP">最受欢迎的送祝福方式</p>',
				'<span class="daySpan">5月4日</span>',
				'<span class="weiSpan hide">9</span>',
				'<i class="iconfont icon-mangluim status" ></i>',
				'</div>'].join(""),
		"choho-im-sidebar-choho-im-contactor-daohang" : [
          		'<p class="dhP"></p>'].join(""),
		"choho-im-sidebar-choho-im-contactor" : [
				'<div class="list">',
				'<img src="../img/logo1.png" alt="">',
				'<p class="titleP"><span class="content-text">测试群会话</span> <span class="child_num hide">(5)</span></p>',
				'  <i class="fa fa-angle-right hide"></i>',
				'</div>'].join(""),
		"choho-im-sidebar-choho-im-group" : [
				'<div class="list">',
				'<img src="../img/logo1.png" alt="">',
				'<p class="titleP">测试群会话</p>',
				'</div>'].join(""),
		"choho-im-content-choho-im-group" : [
 				'<div class="choho-im-contactor collapsed" href="javascript:void(0);">',
 				'	<a href="javascript:void(0)">',
 				'		<div class="choho-im-contactor-collapse">',
 				'			<i class="cimfa cimfa-caret-right choho-im-contactor-collapsed"></i>',
 				'			<i class="cimfa cimfa-caret-down choho-im-contactor-extended"></i>',
 				'		</div>',
				'		<div class="choho-im-avatar">',
				'			<div class="choho-im-avatar-img"></div>',
				/*'			<i class="choho-im-status-icon choho-im-offline"></i>',*/
				'		</div>',
 				'		<div class="choho-im-contactor-content">',
 				'			<div class="choho-im-contactor-name"></div>',
 				'			<div class="choho-im-contactor-noread hide"></div>',
 				'		</div>',
 				'	</a>',
 				'	<div class="choho-im-contactor-children"></div>',
 				'</div>'].join(""),
		"choho-im-content-leftside-window" : [
				'<div class="choho-im-window">',
				'	<a href="javascript:void(0);" class="choho-im-window-icon">',
				'		<span class="choho-im-avatar">',
				'			<div class="choho-im-avatar-img"></div>',
				'			<i class="choho-im-status-icon choho-im-offline"></i>',
				'		</span>', '		<span class="choho-im-title"></span>', '	</a>',
				'	<a class="choho-im-window-close" href="javascript:void(0)">',
				'		<i class="cimfa cimfa-times"></i>', '	</a>', '</div>' ]
				.join(""),
		"choho-im-content-right-window" : [
				'<div class="rightMain">',
				'<p class="r-title"><span>标题</span><i class="icon-clock"></i><i class="icon-user"></i></p>',
				'<div class="textDiv">',
				'</div>',
				'<div class="ipt">',
				'   <div class="choho-im-text1">',
				'		<div class="choho-im-text-content" contenteditable="true" style="font-family: 微软雅黑; color: rgb(0, 0, 0); font-weight: normal; font-style: normal; text-decoration: none;">',
				'		</div>',
				'	</div>',
				'    <i class="fa fa-plus"></i>',
				'    <button class="send">发送</button>',
				'    <div class="tw-div">',
				'   	 <div class="sendimage">',
				'        <input type="file" accept="image/*" class="img-ipt"/>',
				'        <i class="icon-picture"></i>',
				'   	 </div>',
				'   	 <div class="sendfile">',
				'        <input type="file" class="folder-ipt"/>',
				'        <i class="icon-folder"></i>',
				'   	 </div>',
				'        <i class="icon-close r-close"></i>',
				'    </div>',
				'</div>',
				'</div>'].join(""),
		"choho-im-content-messages-message": [
				'<div class="rightText">',
				'  <div class="timeDiv">',
				'      <span class="timeSpan">4月16日12:33</span>',
				'  </div>', 
				'     <img src="../img/timg.jpg" alt="">',
				'    <p class="im-name"></p>',
				'    <div class="wenben"></div>',
				'</div>'].join(""),
		"choho-im-content-messages-message-image": [
                '<div class="im-img-content">',
                '<img src="/tt.png" alt="">',
                '<span class="im-file-size"></span></div>'].join(""),     
                
  		"choho-im-content-messages-message-audio": [
				'<div>',
				'	<audio controls preload="meta" style="max-width:100%;vertical-align: top;background:#FFF;"/>',
  				'	<div style="display: -webkit-box;display: -moz-box;display: box;-webkit-box-orient: horizontal;-moz-box-orient: horizontal;box-orient: horizontal;padding: 2px 1em;background: #fafafa;">',
  				'		<div class="choho-im-file-name" style="font-size: 12px;color:#333;text-align:left;overflow:hidden;text-overflow: ellipsis;white-space: nowrap;-webkit-box-flex: 1;-moz-box-flex:1;box-flex:1;">',
  				'		</div>',
  				'		<div class="choho-im-file-size" style="font-size:12px;font-weight: normal;color:#999;text-align: right;overflow:hidden;text-overflow: ellipsis;white-space: nowrap;width: 80px;">',
  				'		</div>',
  				'	</div>',
				'</div>'].join(""),
		"choho-im-content-messages-message-video": [
                '<div>',
                '	<video controls preload="meta" style="max-width:100%;vertical-align: top;background:#FFF;"/>',
  				'	<div style="display: -webkit-box;display: -moz-box;display: box;-webkit-box-orient: horizontal;-moz-box-orient: horizontal;box-orient: horizontal;padding: 2px 1em;background: #fafafa;">',
  				'		<div class="choho-im-file-name" style="font-size: 12px;color:#333;text-align:left;overflow:hidden;text-overflow: ellipsis;white-space: nowrap;-webkit-box-flex: 1;-moz-box-flex:1;box-flex:1;">',
  				'		</div>',
  				'		<div class="choho-im-file-size" style="font-size:12px;font-weight: normal;color:#999;text-align: right;overflow:hidden;text-overflow: ellipsis;white-space: nowrap;width: 80px;">',
  				'		</div>',
  				'	</div>',
                '</div>'].join(""),
  		"choho-im-content-messages-message-file": [
				'<div class="im-message-text">',
				'<div class="text-i"><i class="fa fa-file-text-o"></i></div>',
				'<div class="r-msg">',
				'    <div class="text-m"></div>',
				'    <div class="text-d im-file-size"></div>',
				'</div>',
				'<div class="text-x"><a href="">下载</a></div>',
				'</div>   '].join(""),                               
		"choho-im-content-right-window-profile" : [
	               '<div class="rightMain1 choho-im-profile">',
	               '    <div class="renText">',
	               '        <img src="../img/logo1.png" alt="">',
		            '       <p class="renName"></p>',
		            '       <p class="renZhi"></p>',
		            '   </div>',
		            '   <div class="msg">',
		            '       <ul>',
		            '           <li>',
		            '               <p class="DH">电话</p>',
		            '               <p class="WB"></p>',
		            '           </li>',
		            '           <li>',
		            '               <p class="DH">邮箱</p>',
		            '               <p class="WB"></p>',
		            '           </li>',
		            '           <li>',
		            '               <p class="DH">手机</p>',
		            '               <p class="WB"></p>',
		            '           </li>',
		            '           <li>',
		            '               <p class="DH">QQ</p>',
		            '               <p class="WB"></p>',
		            '           </li>',
		            '       </ul>',
		            '   </div>',
	               '</div>'].join(""),
		"choho-im-content-right-window-userlist" : [
		        '<div class="rightMain1 groupDiv">',
				'	<p>超然研发部(8/19)</p>',
				'    <div class="groupUl">',
				'	    <ul>',
				'	    </ul>',
				'	</div>',
				'	<div class="group-footer">',
				'    <button><i class="icon-users"></i>管理群组</button>',
				'    <button><i class="icon-user-follow"></i>邀请成员</button>',
				'    <button class="t-btn"><i class="icon-user-unfollow  "></i>退出群组</button>',
				'    </div>',
				'</div>'].join(""),
		"choho-im-content-right-window-userlist-user" : [
   				'	        <li>',
   				'	            <img src="../img/timg.jpg" alt="">',
   				'	            <i class="iconfont icon-zaixianim"></i>',
   				'	            <span>名字</span>',
   				'	        </li>'].join(""),
		"choho-im-content-right-window-group-userlist-user" : [
				'	        <li>',
				'	            <img src="../img/timg.jpg" alt="">',
				'	            <i class="iconfont icon-zaixianim"></i>',
				'	            <span>名字</span>',
				'	        </li>'].join(""),
		"choho-im-content-right-window-history" : [
				'<div class="rightMain1 history">',
				'	<p>聊天记录</p>',
				'	<div class="textDiv">    ',                              
				'	</div>',
			    '    <div class="r-time">',
			    '        <div class="time-input-div">',
			    '            <i class="icon-magnifier"></i>',
			    '            <input type="text">',
			    '        </div>',
			    '        <div class="r-fy">',
			    '            <span class="sty">2013.3.4</span>',
			    '            <i class="fa fa-step-backward first"></i>',
			    '            <i class="fa fa-chevron-left prev"></i>',
			    '            <span class="dq-page">1</span>',
			    '            <span>/12</span>',
			    '            <i class=" fa fa-chevron-right next"></i>',
			    '            <i class=" fa fa-step-forward last"></i>',
			    '        </div>',
			    '    </div>',
		        '</div>'].join(""),
		"choho-im-content-right-window-history-message" : [
		        '<div class="leftText">',
                '   <div class="timeDiv">',
                '       <span class="timeSpan">4月16日12:23</span>',
                '   </div>',
                '   <img src="../img/timg.jpg" alt="">',
                '   <p class="im-name">名字</p>',
                '   <div class="wenben">',
                '   </div>',
                '</div>'].join(""),
		"choho-im-content-right-window-history-message-image": [
                '<div class="im-img-content">',
                '<img src="/tt.png" alt="">',
                '<span class="im-file-size"></span></div>'].join(""),
  		"choho-im-content-right-window-history-message-audio": [
				'<div>',
				'	<audio controls preload="meta" style="max-width:100%;vertical-align: top;background:#FFF;"/>',
  				'	<div style="display: -webkit-box;display: -moz-box;display: box;-webkit-box-orient: horizontal;-moz-box-orient: horizontal;box-orient: horizontal;padding: 2px 1em;background: #fafafa;">',
  				'		<div class="choho-im-file-name" style="font-size: 12px;color:#333;text-align:left;overflow:hidden;text-overflow: ellipsis;white-space: nowrap;-webkit-box-flex: 1;-moz-box-flex:1;box-flex:1;">',
  				'		</div>',
  				'		<div class="choho-im-file-size" style="font-size:12px;font-weight: normal;color:#999;text-align: right;overflow:hidden;text-overflow: ellipsis;white-space: nowrap;width: 80px;">',
  				'		</div>',
  				'	</div>',
				'</div>'].join(""),
		"choho-im-content-right-window-history-message-video": [
                '<div>',
                '	<video controls preload="meta" style="max-width:100%;vertical-align: top;background:#FFF;"/>',
  				'	<div style="display: -webkit-box;display: -moz-box;display: box;-webkit-box-orient: horizontal;-moz-box-orient: horizontal;box-orient: horizontal;padding: 2px 1em;background: #fafafa;">',
  				'		<div class="choho-im-file-name" style="font-size: 12px;color:#333;text-align:left;overflow:hidden;text-overflow: ellipsis;white-space: nowrap;-webkit-box-flex: 1;-moz-box-flex:1;box-flex:1;">',
  				'		</div>',
  				'		<div class="choho-im-file-size" style="font-size:12px;font-weight: normal;color:#999;text-align: right;overflow:hidden;text-overflow: ellipsis;white-space: nowrap;width: 80px;">',
  				'		</div>',
  				'	</div>',
                '</div>'].join(""),
  		"choho-im-content-right-window-history-message-file": [
				'<div class="im-message-text">',
				'<div class="text-i"><i class="fa fa-file-text-o"></i></div>',
				'<div class="r-msg">',
				'    <div class="text-m"></div>',
				'    <div class="text-d im-file-size"></div>',
				'</div>',
				'<div class="text-x"><a href="">下载</a></div>',
				'</div>   '].join(""),         
	};
})($chohoim, window);