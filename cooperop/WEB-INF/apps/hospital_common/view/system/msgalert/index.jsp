<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="消息提示弹窗" disloggedin="true">
<style type="text/css">
	.main {
   		padding: 2px !important;
	}
	.page-content {
    	min-height: auto !important;
	}

    .closehh{
        height: 25px;
        position: absolute;
        bottom: 0;
        width: 100%;
    }

    .cmain{
        width: 240px;
	    height: 80px;
	    border: 1px solid #bbb;
	    border-radius: 3px;
	    margin-bottom: 10px;
	    position: relative;
	    padding: 0 5px;
	    margin: 5px 0;
	    overflow-x: hidden;
    }

    .cmain>p{
        font-size: 13px;
        text-align: left;
        margin-top: 5px;
        color: #6a6969;
        line-height: 23px;
    }

    .cmain>p .color1{
   		font-size:14px;
        margin: 0 2px;
    }

    .cmain>p .color2{
    	font-size:14px;
        margin: 0 2px;
        color: #cf0000;
    }

    .cmain>p .color3{
    	font-size:14px;
        margin: 0 2px;
        color: #038a08;
    }

    .cmain>p .color4{
   		font-size:14px;
        margin: 0 2px;
        color: #bd880b;
    }

    .be-read{
        font-size: 12px;
        width: 50px;
        height: 20px;
        display: block;
        text-align: center;
        margin-right: 10px;
        float: right;
        color: #fff;
        border-radius: 3px;
        background-color: #0d98d2;
        line-height: 20px;
        cursor: pointer;
    }

    .be-read:hover{
        background-color: #0e77d2;
        cursor: pointer;
    }
    .page-content{
	    padding: 0 5px 0 5px !important;
    }

    .color1{
   		font-size:14px;
        margin: 0 2px;
    }

    .color2{
    	font-size:14px;
        margin: 0 2px;
        color: #cf0000;
    }

    .color3{
    	font-size:14px;
        margin: 0 2px;
        color: #038a08;
    }

    .color4{
   		font-size:14px;
        margin: 0 2px;
        color: #bd880b;
    }
</style>
<body>
<!-- <div class="cmain">

</div> -->
<%-- <div class="cmain">
    <div class="closehh"><span class="be-read">确认</span></div>
    <p>药师陈药师<span class="color2">已通过</span>患者<span class="color1">吴国强</span>的医嘱</p>
</div> --%>
<div id="container">

</div>
</body>

<script type="text/javascript">
	var messageNum = 0;
	$(document).ready(function(){
		setInterval('checkOP()', 30000);

		$.call("hospital_common.system.msgalert.queryMsg",{},function(rtn){
			if(rtn && rtn.msgs){
				var check = 0;
				for(var i in rtn.msgs){
					var html = [];
					 var notice = rtn.msgs[i];
						html.push('<div class="cmain" >');
						html.push('<div class="closehh">');
						if(notice.can_is_read == 1){
							html.push('<span class="be-read" data-url="'+notice.page_url+'" data-source_type="'+notice.source_type+'" onclick="is_read(this,\''+notice.id+'\');">');
							html.push('已阅');
							html.push('</span>');
						}
						if(notice.page_url){
							html.push('<span class="be-read" data-msg_alert_id="'+notice.id+'" data-source_type="'+notice.source_type+'" data-userinfo_id="'+notice.send_to_user+'" data-dtl="'+ notice.content_detail +'" data-url="'+notice.page_url+'" data-par="'+notice.params+'"  onclick="seeMore(this,\''+notice.id+'\',\''+notice.source_type+'\');">');
							html.push('详情');
							html.push('</span>');
						}
						html.push('</div>');
						html.push('<p>');
						html.push(notice.content);
						html.push('</p></div>');
						$("#container").append(html.join(''));
					}
				messageNum = rtn.msgs.length;
			}
			//crtech.addWindowHeight(x); 为默认高度+新增数字*高度，如果为负数则关闭页面
			if(typeof crtech != "undefined"){
				crtech.addWindowHeight(--messageNum);
			}
		});
	} );

	function is_read(_this, id){
		var $this = $(_this);
		var source_type = $this.data('source_type');
		$.call("hospital_common.system.msgalert.beRead",{'id': id, 'is_read': 1}, function(rtn){
			if(source_type == '6'){
				var url = $(_this).data('url');
				if(url.indexOf('auto_audit_id=') > -1){
					var auto_audit_id= url.substr(url.indexOf('auto_audit_id=')+14,32);
					$.call("ipc.autoaudit.update",{'id': auto_audit_id,'is_sure': 1},function(rtn2){
						var size = $("form").find(".cmain").length;
						$this.parents(".cmain").remove();
						if(--messageNum < 0){
							crtech.closeModal();
						}else{
							crtech.addWindowHeight(messageNum);
						}
					});
				}
			}else{
				var size = $("form").find(".cmain").length;
				$this.parents(".cmain").remove();
				//调用crtech.addWindowHeight(size);刷新窗口大小
				if(--messageNum < 0){
					crtech.closeModal();
				}else{
					crtech.addWindowHeight(messageNum);
				}
			}
		});
	}

	function del_state(_this, id){
		var $this = $(_this);
		var size = $("form").find(".cmain").length;
		$this.parents(".cmain").remove();
		if(--messageNum < 0){
			crtech.closeModal();
		}else{
			crtech.addWindowHeight(messageNum);
		}
	}

	function seeMore(_this, msgid, source_type){
		// 拼接链接
		var purl = $(_this).data('url');
		var par = $(_this).data('par');
		var msg_alert_id = $(_this).data('msg_alert_id');
		var userinfo_id = $(_this).data('userinfo_id');
		var source_type = $(_this).data('source_type');
		if(purl.indexOf('?')<0){
			purl = purl + "?yyid=1";
		}
		if(par && par != 'undefined'){
			purl = purl + '?';
			var json_par = eval('(' + par + ')');
			$.each(json_par, function(jn , jv) {
				purl = purl + jn + '=' + jv + '&';
				});
		}
		//alert('${_CRSID}')
		if('${_CRSID}'){
			purl = purl + '&_CRSID=' + '${_CRSID}';
		}
		if(msg_alert_id){
			purl = purl + '&msg_alert_id=' + msg_alert_id;
		}
		if(userinfo_id){
			purl = purl + '&userinfo_id=' + userinfo_id;
		}
		if(source_type == '5' || source_type == '6'){
			purl = purl + '&source_type='+source_type;
		}
		// 标记已阅读
		var $this = $(_this);
		if(source_type == '6'){
			$.call("hospital_common.system.msgalert.beRead",{'id': msgid, 'is_read': 1, 'uid': '${param.uid}'}, function(rtn){
				if($(_this).data('url').indexOf('auto_audit_id=') > -1){
					var auto_audit_id= $(_this).data('url').substr($(_this).data('url').indexOf('auto_audit_id=')+14,32);
					$.call("ipc.autoaudit.update",{'id': auto_audit_id,'is_sure': 1},function(rtn2){
						var size = $("form").find(".cmain").length;
						$this.parents(".cmain").remove();
						if(--messageNum < 0){
							crtech.closeModal();
						}else{
							crtech.addWindowHeight(messageNum);
						}
					});
				}
			});
		}else if(source_type != '5'){
			$.call("hospital_common.system.msgalert.beRead",{'id': msgid, 'is_read': 1, 'uid': '${param.uid}'}, function(rtn){
				var size = $("form").find(".cmain").length;
				$this.parents(".cmain").remove();
				if(--messageNum < 0){
					crtech.closeModal();
				}else{
					crtech.addWindowHeight(messageNum);
				}
			});
		}
		url = encodeURIComponent(purl);
		//crtech.modal(url);
		//url = "http://localhost:8085" + url;
		var surl = window.location.protocol +"//"+window.location.host + purl;
		//surl = "http://127.0.0.1:9001" + purl;
		var dtl = $this.data('dtl');
		if(dtl != ''){
			if(surl.indexOf('?') >= 0){
				surl = surl + "&content_detail=" + dtl;
			}else{
				surl = surl + "?content_detail=" + dtl;
			}
		}
		if (crtech) {
			if(source_type == '5' || source_type == '6' ){
				surl = surl + '&uid=${param.uid}';
				crtech.open( encodeURI(surl), "940", "640");
			}else{
				surl = surl + '&uid=${param.uid}';
				crtech.open( encodeURI(surl), "700", "495");//宽高可以传百分比
			}
		}

		//del_state(_this, id);


	}

	function checkOP(){
		if(messageNum >= 0 && crtech){
		    // 未避免访问不到服务，而导致的页面无法自动关闭，先检测一次
            $.call('hospital_common.system.msgalert.testConnect', {}, function(ret){
                if(ret == 1){
                    crtech.open(window.location.origin + "/w/hospital_common/system/msgalert/remind.html", "288", "180");//宽高可以传百分比
                    // crtech.open(window.location.origin + "/w/hospital_common/system/msgalert/remind.html", "300", "225");//class3.0 使用
                    // crtech.open("file:///iadscplib/wait.html", "288", "180");// 弹出客户端网页
                    // crtech.modal("http://127.0.0.1:8084/w/hospital_common/auditrecord/msg.html", "800", "500");//宽高可以传百分比
                }
            },function(e){}, {async: true, nomask: true, timeout: 1000});
		}

	}
</script>

</s:page>
