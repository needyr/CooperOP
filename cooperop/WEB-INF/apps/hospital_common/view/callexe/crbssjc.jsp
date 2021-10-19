<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="外部程序调用-传染病实时监测系统" disloggedin="true">
<style type="text/css">
	.start_link{
		display: inline-block;
	    width: 200px;
	    height: 100px;
	    border: 1px solid #eee;
	    position: relative;
	    margin: 10px;
	}
	
	a:hover{
	    box-shadow: 0px 0px 15px #00000021;
	}
	
	  .t_exe{
	    display: inline-block;
	    width: 199px;
	    position: absolute;
	    top: 0px;
	    text-align: center;
	    background-color: #f8f6f6;
	    line-height: 25px;
	    color: #686767;
	    left: 0px;
	    font-size:14px;	
	   }
	 .ar{
	    color: #787878;
	    font-size: 40px;
	    position: absolute;
	    line-height: 75px;
	    top: 25px;
	    width: 200px;
	    display: inline-block;
	    text-align: center;
	}
	.iconimg{
		width: 70px;
	    height: 68px;
	    margin-top: 30px;
	    margin-left: 60px;
	}
	</style>
	<s:row>
		<a href="javascript:void(0);" onclick="callexe();" class="start_link">
			<span class="t_exe">传染病实时监测系统</span>
			<img class="iconimg" src="${pageContext.request.contextPath}/res/hospital_common/img/exe_001.png">
		</a>
	</s:row>
</s:page>
<script>
	function callexe(){
		var path = 'C:\\YunClient_YiCe\\NIS2019\\NIS.exe UserBoot 3320';
		//path = path.replace('#patient_id', '${param.patient_id}');
		//path = path.replace('#visit_id', '${param.visit_id}');
		//path = "notepad";
		try{
			crtech.exec(path);
		}catch(err){
			$.message('请使用IADSCP客户端访问！');
		}
	}
</script>