<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="导出" disloggedin="true" ismodal="true">
<style type="text/css">
.ui-progressbar {
	position: relative;
}
.ui-progressbar .ui-progressbar-value{
  background: #ededed url(images/bg_fallback.png) 0 0 repeat-x; 
  background: -moz-linear-gradient(top, #ededed 0%, #c4c4c4 100%); 
  background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#ededed), color-stop(100%,#c4c4c4)); 
  background: -webkit-linear-gradient(top, #ededed 0%,#c4c4c4 100%); 
}
.ui-progressbar-value {
/*   background-image: url(images/pbar-ani.gif); */
}
.progress-label {
	position: absolute;
	left: 25%;
	top: 4px;
	font-weight: bold;
	text-shadow: 1px 1px 0 #fff;
}

.ui-progressbar {
	height: 2em;
	text-align: left;
	overflow: hidden;
}
</style>
	<div id="divProgerssbar">
		<div class="progress-label">正在加载...</div>
	</div>
	<div id="msg"></div>
<script>
	var $seq = '${$seq}';
	var interval;
	var pro = $("#divProgerssbar");
	var proLabel = $(".progress-label");
	// 	var msg = $("#msg");
	$(document).ready(function() {
		var options = {
			value : 0,
			max : 100
		};
		pro.progressbar(options);
		interval = setInterval(queryProcess, 1000);
	});
	function queryProcess() {
		$.call("application.excel.excelProcess", {
			"$seq" : $seq
		}, function(rtn) {
			console.log(rtn);
			var processBean = rtn.processBean;
			if(rtn.processBean.message!=null){
				$("#msg").html(rtn.processBean.message);
			};
			if (rtn.processBean.downloadUrl != null) {
				proLabel.html("完成 ! 文件已自动下载，如果未自动下载，<a href='${pageContext.request.contextPath}/"+rtn.processBean.downloadUrl+"'>点击下载</a>");
				pro.progressbar({
					"value" : 100
				});
				window.location.href = "${pageContext.request.contextPath}/" + rtn.processBean.downloadUrl;
				window.clearInterval(interval);
			} else if (processBean.total > -1) {
				var val = parseInt(+(processBean.currNumBegin
						/ processBean.total * 100));
				if (processBean.total <= processBean.currNumEnd) {
					proLabel.text("当前处理" + processBean.currNumBegin + " - "
							+ processBean.total + " 条数据，共：" + processBean.total
							+ "条数据。" + val + "%");
				} else {
					proLabel.text("当前处理" + processBean.currNumBegin + " - "
							+ processBean.currNumEnd + " 条数据，共："
							+ processBean.total + "条数据。" + val + "%");
				}
				pro.progressbar({
					"value" : val
				});
			}
		});
	}
</script>
</s:page>
