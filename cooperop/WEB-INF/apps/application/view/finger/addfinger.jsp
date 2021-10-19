<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" ismodal="true" dispermission="true">
	<img src="${pageContext.request.contextPath}/theme/layout/img/finger.png" style="display:block;margin:15px auto;width:35%;border:1px solid #ddd;background-color:#ddd;">
	<div style="width:280px;font-size:16px;text-align:center;margin:15px auto;">
		登记一枚指纹共采集3次，
		灯闪烁停止后即采集一次。
	</div>
	<div style="width:280px;text-align:center;margin:2px auto;">
		<div>
			<span id="fnum"></span>
		</div>
		<div style="margin:10px 0 0 0;">
			<a onclick="addfinger();"><font color="blue" style="padding-right:20px">采集</font></a>
			<a onclick="delfinger();"><font color="blue" style="padding-right:20px">清除</font></a>
			<a onclick="goback();"><font color="blue">退出</font></a>
		</div>
	</div>
</s:page>
<script>
var system_user_id;
	$(document).ready(function() {
		system_user_id = '${pageParam.hrzhiyid}';
		if(system_user_id){
			$.getfingernums(system_user_id, function(rtn){
				console.log(rtn);
				$("#fnum").html('已采集<font color="red">'+rtn.fingers.length+'</font>枚指纹');
			});
		}else{
			$("#fnum").html('<font color="red">人员未找到，或者没有获取到职员id</font>');
		}
	})
	function addfinger(){
		if(system_user_id){
			$("#fnum").html('<font color="green">采集进行中...</font>');
			$("img").attr("src",cooperopcontextpath+"/theme/layout/img/finger-1.png");
			$.regfinger(system_user_id,
				{
				initimage: function(rrr){
					if(rrr.ret == 0){
						var ind = rrr.data.enroll_index-0+1;
						$("img").attr("src", cooperopcontextpath+"/theme/layout/img/finger-"+ind+".png");
					}
				},
				callback: function(rtn){
					//callback（选填）
					if (rtn) {
						$("img").attr("src","${pageContext.request.contextPath}/theme/layout/img/finger.png");
						$("#fnum").html('已采集<font color="red">'+rtn.fcount+'</font>枚指纹');
						$.cancelfinger();		//关闭指纹采集器
					}
				}
			});
		}
	}
	function delfinger(){
		if(system_user_id){
			$.deletefingers(system_user_id, function(rtn){
				$("#fnum").html('已采集<font color="red">'+0+'</font>枚指纹');
				$("img").attr("src","${pageContext.request.contextPath}/theme/layout/img/finger.png");
				$.cancelfinger();
			});
		}
		/* $.confirm("将清除所有指纹无法恢复，是否继续！" ,function (rtn){
			if(rtn){
				$.call("hr.user.deleteFinger", {"system_user_id" : '${pageParam.system_user_id}'}, function(rtn) {
					if (rtn) {
						$("#fnum").html('已采集<font color="red">'+0+'</font>枚指纹');
						$("img").attr("src","${pageContext.request.contextPath}/theme/layout/img/finger.png");
						$.cancelfinger();
					}
				});
			}
		}); */
	}
	function goback(){
		$.cancelfinger();
		$.closeModal(true);
	}
</script>
