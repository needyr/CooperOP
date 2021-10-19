<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="驾驶舱设计器" isblank="true">
<html>
<head>
<meta charset="utf-8">
<title>${name}</title>
<meta name="format-detection" content="telephone=no,email=no,address=no,date=no" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no, viewport-fit=cover">
<meta name="apple-touch-fullscreen" content="yes">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
<meta name="renderer" content="webkit">
<!-- 避免IE使用兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- 针对手持设备优化，主要是针对一些老的不识别viewport的浏览器，比如黑莓 -->
<meta name="HandheldFriendly" content="true">
<!-- 微软的老式浏览器 -->
<meta name="MobileOptimized" content="320">
<!-- uc强制竖屏 -->
<meta name="screen-orientation" content="portrait">
<!-- QQ强制竖屏 -->
<meta name="x5-orientation" content="portrait">
<!-- UC强制全屏 -->
<meta name="full-screen" content="yes">
<!-- QQ强制全屏 -->
<meta name="x5-fullscreen" content="true">
<!-- UC应用模式 -->
<meta name="browsermode" content="application">
<!-- QQ应用模式 -->
<meta name="x5-page-mode" content="app">
<!-- windows phone 点击无高光 -->
<meta name="msapplication-tap-highlight" content="no">
<script type="text/javascript" src="${contextpath}/theme/plugins/jquery.min.js"></script>
<script type="text/javascript" src="${contextpath}/theme/plugins/jquery-migrate.min.js"></script>
<script type="text/javascript" src="${contextpath}/theme/plugins/jquery.json.min.js"></script>
<script type="text/javascript" src="${contextpath}/theme/scripts/common.js"></script>
<script type="text/javascript" src="${contextpath}/theme/plugins/layer/layer.js"></script> 
<c:if test="{not empty rm_url}">
	<script type="text/javascript" src="${rm_url}"></script>
</c:if>
<script type="text/javascript" src="${contextpath}/res/${module}/cockpit/cockpitdesigner.js"></script>

</head>
<body>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		var dataURLtoBlob = function(dataurl) {
			var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1], bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(
					n);
			while (n--) {
				u8arr[n] = bstr.charCodeAt(n);
			}
			return new Blob([
				u8arr
			], {
				type : mime
			});
		};
		
		var resmanager = window["$chohorm"] ? $window["$chohorm"] : {
			uploadBlob: function(blob, callback, error) {
				var xhr = new XMLHttpRequest();
				xhr.responseType = 'text';
				xhr.withCredentials = true
				xhr.onreadystatechange = function() {
					if (xhr.readyState == xhr.DONE && xhr.status != 200) {
						var message = $.trim(XMLHttpRequest.responseText) || xhr.statusText;
						if (this.status == 401) {
							message = "尚未登录";
						}
						if (!message) {
							if (XMLHttpRequest.status != 200) {
								message = "访问服务端异常，HTTP错误代码:" + XMLHttpRequest.status;
							}
						} else if (message == "timeout")
							message = "连接服务器超时。";
						else if (message == "error") {
							message = "服务端处理异常。";
							return;
						} else if (message == "notmodified")
							message = "服务端未更新。";
						else if (message == "parsererror")
							message = "解析服务端返回信息异常。";
						$.error("上传图片异常：" + message);
						$.unmask();
					}
				}
				xhr.onload = function() {
					$.unmask();
					callback($.parseJSON($.trim(xhr.responseText)));
				};
				xhr.open('POST', cooperopcontextpath + "/rm/ub/" + module+"/cockpit_preview.png", true);
				xhr.setRequestHeader('Content-Type', blob.type);
				xhr.send(blob);
			},
			commit: function(callback) {
				callback();
			}
		};
		
		function init() {
			$crcockpitdesigner.create("body", ${json});
			$("body").on("save", function(e, cockpit) {
				$.mask("正在生成截图信息...");
				for (var i = 0, tab = cockpit.tabs[i]; i < cockpit.tabs.length; i ++, tab = cockpit.tabs[i]) {
					tab.order_no = i + 1;
					var charts = [];
					for (var id in tab.charts) {
						var chart = tab.charts[id];
						chart.chart_code = chart.code;
						delete chart["code"];
					}
				}
				$crcockpitdesigner.getImage(function(canvas) {
					var blob = dataURLtoBlob(canvas.toDataURL("image/png"));
					resmanager.uploadBlob(blob, function(rtn) {
						$.unmask();
						if (rtn && rtn.files && rtn.files.length > 0) {
							cockpit.preview = rtn.files[0].file_id;
						}
						var tabs = cockpit.tabs;
						delete cockpit.tabs;
						cockpit.tabs = $.toJSON(tabs);
						$.call("pivascockpit.cockpit.save", cockpit, function() {
							resmanager.commit(function() {
								$.success("保存成功！");
							})
						});
					});
				});
			});
			$("body").on("preview", function(e, cockpit) {
				$.open("preview.html", {name: cockpit.name, json: $.toJSON(cockpit)}, cockpit.id || (new Date().getTime() + ""));
			});
		};
		
		if (window["$chohorm"]) {
			$.initRM(init);
		} else {
			init();
		}
	});

</script>
</html> 
</s:page>