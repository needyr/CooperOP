<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<html>
<head>
   <link type="text/css" rel="stylesheet" href="/theme/plugins/font-awesome/css/font-awesome.min.css">
   <link type="text/css" rel="stylesheet" href="/theme/plugins/simple-line-icons/simple-line-icons.min.css">
   <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/theme/css/myicon/iconfont.css" >
<style type="text/css">
	  a{display: inline-block;
	    width: 200px;
	    height: 100px;
	    border: 1px solid #eee;
	    position: relative;
	    margin: 10px;
	}
	
	a:hover{
	    box-shadow: 0px 0px 15px #00000021;
	}
	
	  span{
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
	</style>
</head>
<body>

	<c:forEach items = "${$return.ipc_assist}" var = "ipca">
		<a href="${ipca.address}" >
			<span title="${ipca.remark}">${ipca.fz_title}</span>
			 <!-- <i class="ar icon-tag"></i>  -->
			 <i class="ar iconfont ${ipca.uicon}"></i>  
		</a>
	</c:forEach>
	<%-- <a href="http://ay140213171003z:11007/MedicalFormulaCalculate">
		<span>医学公式</span>
		<i class="ar iconfont icon-kaohejisuangongshi"></i>
	</a>
	<a href="http://ay140213171003z:11007/DrugInstructionQuery">
		<span>药品说明书</span>
		<i class="ar iconfont icon-yaopin1"></i>
	</a>
	<a href="http://ay140213171003z:11007/DrugContraindications">
		<span>中西药用药禁忌</span>
		<i class="ar iconfont  icon-jindu"></i>
	</a>
	<a href="http://ay140213171003z:11007/DrugProblemsQuery">
		<span>相互作用、配伍禁忌查询</span>
		<i class="ar iconfont icon-xianghujiaohuan"></i>
	</a>
	<a href="http://ay140213171003z:11007/AntibacterialsRegulations">
		<span>抗菌药管理100问</span>
		<i class="ar iconfont icon-wenhao"></i>
	</a>
	<a href="http://ay140213171003z:11007/DrugEducation">
		<span>用药教育</span>
		<i class="ar iconfont icon-jiaoyu"></i>
	</a>
	<a href="http://ay140213171003z:11007/DrugReferences">
		<span>药物参考信息</span>
		<i class="ar iconfont icon-cankaowenjian"></i>
	</a>
	<a href="http://ay140213171003z:11007/LabTV">
		<span>校验值</span>
		<i class="ar iconfont icon-erji-lujingguanli"></i>
	</a>
	<a href="http://ay140213171003z:11007/CG">
		<span>临床指南</span>
		<i class="ar iconfont icon-zhinan"></i>
	</a>
	<a href="http://ay140213171003z:11007/CP">
		<span>临床路径</span>
		<i class="ar iconfont icon-lujingchaxun"></i>
	</a>
	<a href="http://ay140213171003z:11007/PharmaceuticalNews">
		<span>医药时讯</span>
		<i class="ar iconfont icon-zixun1"></i>
	</a>
	<a href="http://ay140213171003z:11007/DrugIndicationQuery">
		<span>对症找药</span>
		<i class="ar iconfont icon-zhaodaofind"></i>
	</a>
	<a href="http://ay140213171003z:11007/FDAClassQuery">
		<span>FDA妊娠期药物分类查询</span>
		<i class="ar iconfont icon-fenlei2"></i>
	</a> --%>
</body>
</html>
	
	
