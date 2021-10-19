<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="HIS集成" >
<style type="text/css">
.main {
	padding: 20px;
	background-color: #e2f1ff;
    height: 100%;
}

.msg {
	font-size: 15px;
	/*text-align: center;*/
    letter-spacing: 2px;
    font-family:宋体
}

.title {
	font-size: 28px;
    font-weight: 600;
}
.warn {
	font-size: 15px;
    color: #d40000;
}
</style>
<div class="info">
	 <p class="title">HIS集成</p>
	 <div style="">
	  <p class="msg">该步骤需要进行HIS相关集成，集成包含以下两部分：<br>
	  一、数据库集成：<br>
	  1、集成文档：<a href="/rm/downTemp/hospital_common/2-1智能辅助决策支持云平台集成接口V3.4_数据库集成部分_TO开发者(2020-12).doc">
	  《智能辅助决策支持云平台集成接口_数据库集成部分》</a><br>
	  2、集成接口重要程度说明：<br>
	  &nbsp★表示该表在我们数据库中的重要程度。<br>
	  &nbsp★★★★是最重要的，每个字段取值都需要完全准确，否则会影响系统使用。<br>
	  <br>
	  二、HIS程序集成：<br>
     1、集成文档：<a href="/rm/downTemp/hospital_common/2-2智能辅助决策支持云平台集成接口V3.5.2_HIS程序集成部分(CS)_TO开发者(2019-10).doc">
         《智能辅助决策支持云平台集成接口_HIS程序集成部分》</a><br>
     2、集成时需要测试HIS功能节点如下：<br>
         初始化：住院医嘱、住院处方、门诊处方、住院护士工作站、医保结算切换患者时。<br>
         合理用药审查：住院医嘱提交、住院处方提交、门诊处方提交、住院护士校对医嘱、门诊收费(收费处、移动支付)。<br>
         医保控费审查：住院医嘱提交、住院处方提交、门诊处方提交、住院护士站收费(批量收费)<br>
         医保病组分组审查：住院诊断录入界面、医生工作界面，右键或者按钮集成<br>
         医保结算审查：医生工作界面、护士工作站界面、医保结算工作界面，右键或者按钮集成<br></p>
	</div>
</div>
</s:page>
<script type="text/javascript">
	$(function(){
		//console.log('${params.id}')
	});
	
</script>