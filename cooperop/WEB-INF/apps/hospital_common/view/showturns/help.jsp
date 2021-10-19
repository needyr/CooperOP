<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="utf-8">
		<title>帮助</title>
		<style type="text/css">
			* {
				margin: 0px;
				padding: 0px;
				font-size: 12px;
				font-family: '微软雅黑';
			}
			
			.ques {
				background: #f5f5f5;
				margin: 2px;
				padding-left: 0px;
			}
			
			h4 {
				color: #b35716;
			    font-size: 16px;
			    line-height: 32px;
			    font-weight: 400;
			    padding-left: 2px;
			    background: #e5e7e8;
			    border-left: 6px solid #da9937;
			   /*  box-shadow: 2px 1px 4px 1px #c7c7c7; */
			}
			
			p {
				padding-left: 15px;
				line-height: 20px;
			}
</style>
	</head>
	<body>
		<div id="container">
			<div class="ques">
				<h4>如何操作</h4>
				<p>审查结果为提示时，点击【已阅读】即可关闭提示窗口，医嘱保存成功</p>
				<p>审查结果为拦截时，点击【返回调整】关闭窗口，需要在HIS系统中修改医嘱重新保存；点击【强制使用】保存医嘱成功</p>
				<p>审查结果为驳回时，点击【返回调整】关闭窗口，需要在HIS系统中修改医嘱重新保存(如果仅包含医保问题，将驳回问题转为自费后可强制使用)</p>
			</div>
			<div class="ques">
				<h4>在什么情况下强制使用</h4>
				<p>查看【合理用药审查问题】，确认拦截问题后，依然决定使用</p>
				<p>查看【医保审查问题】，确认拦截问题后，选择了转为自费使用，并且告知患者</p>
				<p>填写强制使用理由（强制使用将会被记录）</p>
			</div>
			<div class="ques">
				<h4>如何验证是否存在智能审查结果所述内容</h4>
				<p>药品说明书：点击药品名称即可查看药品说明书</p>
				<p>患者信息：点击【患者详情】可查看患者的基本信息，诊断，医嘱，检查，检验，体征，手术，费用</p>
				<p>计费项目：点击【计费项目】可查看参与当次审查的所有计费项目</p>
				<p>查看详情：点击【查看详情】可查看当前问题的详细内容及与当前问题相关的计费或医嘱</p>
			</div>
			<div class="ques">
				<h4>如何看待智能审查的问题</h4>
				<p>医保问题：右上角图标内文字为【保】；合理用药问题：右上角图标内文字为【药】</p>
				<p>审查规则：第一行有色字体标识该问题属于那种规则的问题；如【配伍禁忌】</p>
				<p>严重程度：右上角图标为橙色则问题相对轻微，红色则较为严重，黑色为非常严重</p>
				<p>违反合理用药规则用药可能会对患者造成伤害，违反医保规则医院可能被罚款</p>
			</div>
			<div class="ques">
				<h4>我觉得当前审查的问题有误，不应该提示</h4>
				<p>医保问题：建议科室内讨论后联系【医保科】相关负责人</p>
				<p>合理用药问题：建议科室内讨论后联系【药剂科】相关负责人</p>
				<p>质控预警提示：建议科室内讨论后联系【质控科】相关负责人</p>
				<p>功能无法使用：联系【信息科】相关负责人</p>
			</div>
			
			<!-- <div class="ques">
				<h4>如何查看药品说明书</h4>
				<p>点击蓝色药品名称可查看药品说明书;若在HIS系统内,右键扩展-药品说明书</p>
			</div>
			<div class="ques">
				<h4>不满意审查结果，系统误判</h4>
				<p>将问题上的[向下大拇指图标]点亮，即可标记为质疑，或联系药剂科药师</p>
			</div>
			<div class="ques">
				<h4>何时能收到药师审查结果</h4>
				<p>点击强制使用后,非药师工作时间系统直接通过;若3分钟无药师接单,则系统返回[医生决定];若药师接单,审查完成后15秒内,小弹窗提示审查结果</p>
			</div>
			<div class="ques">
				<h4>被拦截后如何修改医嘱</h4>
				<p>只需修改问题栏右上角带有红色标记的问题</p>
			</div>
			<div class="ques">
				<h4>查看患者信息</h4>
				<p>点击蓝色[患者详情]</p>
			</div>
			<div class="ques">
				<h4>如何处理药师审查结果(小弹窗)</h4>
				<p>药师审查[通过],点击查看可查看审查明细,点击已阅可关闭该条信息</p>
				<p>药师审查[不通过],请给改患者重新开医嘱/处方,再次提交审查[保存]</p>
				<p>药师审查[医生决定],点击处理，选择是否用药</p>
				
			</div>
			<div class="ques">
				<h4>忘记处理药师审查[不通过]和[医生决定]</h4>
				<p>
					护士提交医嘱时会有审查不通过,等待医生决策的提示
				</p>
			</div> -->
		</div>
	</body>
</html>