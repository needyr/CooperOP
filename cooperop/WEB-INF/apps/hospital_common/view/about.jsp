<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>关于</title>
		<style type="text/css">
			*{
				margin: 0px auto;
				padding: 0px;
				font-size: 14px;
				font-family: "微软雅黑";
			}
			html{
				background-image: url('/theme/img/bg.jpeg');
			}
			
			#main{
				width: 600px;
				height: 360px;
				padding: 2px;
				padding: 20px 2px 2px 2px;
				box-shadow: 2px 3px 20px 2px #f1f1f1;
				margin-top: 80px;
				/* background: #ffffff; */
				background: #ffffffde;
				outline: 8px solid #ffffff2b;
			}
			
			.logo-info{
				line-height: 50px;
				height: 60px;
				text-align: center;
				border-bottom:2px solid #51ab9933;
			}
			
			.logo{
				width: 150px;
				height: 50px;
				border: 2px solid #55b3a0;
				box-shadow: 0px 2px 2px 0px #48464647;
			}
			
			.remark-div{
				text-align: center;
				line-height: 30px;
			}
			
			.authorise-div p{
				margin-top: 5px;
				text-align: center;
				line-height: 25px;
			}
			
			.authorise-div p font{
				color: #c73131;
				font-weight: 600;
			}
			
			.support-list{
				text-align: center;
				line-height: 25px;
			}
			
			.function-list{
				margin: 8px 0px 10px 0px;
			}
			
			.warn-des{
				line-height: 25px;
				text-align: center;
				color: #c73131;
			}
			
			table.gridtable {
			    font-family: verdana,arial,sans-serif;
			    font-size:11px;
			    color:#333333;
			    border-width: 1px;
			    border-color: #89b7ca;
			    border-collapse: collapse;
			}
			table.gridtable th {
			    border-width: 1px;
			    padding: 4px;
			    border-style: solid;
			    border-color: #89b7ca;
			}
			table.gridtable td {
			    border-width: 1px;
			    padding: 4px;
			    border-style: solid;
			    border-color: #89b7ca;
			}
		</style>
	</head>
	<body>
	  <div id="main">
		<div class="logo-info">
			<img class="logo" src="/theme/img/logo-big.png" />
			<!-- <span>四川医策科技有限公司</span> -->
		</div>
		<div class="remark-div">
			软件信息：智能辅助决策支持平台(IADSCP) v1.0 
		</div>
		<div class="authorise-div">
			<p>使用授权：<font>xx医院（2019-09-27~2099-09-09）</font></p>
			<!-- <p>授权时间：<font>2019-09-27~2099-09-09</font></p> -->
		</div>
		<div class="function-list">
			<!-- <ul>
				<li>合理用药审核监控</li>
				<li>医保审核监控</li>
				<li>质控预警提示监控</li>
			</ul> -->
			<table class="gridtable">
				<tr>
					<th>编号</th>
					<th>功能</th>
					<th>介绍</th>
				</tr>
				<tr>
					<td>1</td>
					<td>合理用药审核监控</td>
					<td>对医生开医嘱进行智能合理用药审核，多维度数据统计分析</td>
				</tr>
				<tr>
					<td>2</td>
					<td>医保审核监控</td>
					<td>对医生开医嘱，诊疗，护士计费智能医保审核，多维度数据统计分析</td>
				</tr>
				<tr>
					<td>3</td>
					<td>院内指控参数预警</td>
					<td>医生端质控参数预警提示，多维度数据统计分析</td>
				</tr>
			</table>
		</div>
		<div class="support-list">
			<span>
				技术支持：四川医策科技有限公司<br />
				开发商：四川医策科技有限公司
			</span>
		</div>
		<div class="warn-des">
			警告：未经授权许可禁止使用和传播
		</div>
	  </div>
	</body>
</html>