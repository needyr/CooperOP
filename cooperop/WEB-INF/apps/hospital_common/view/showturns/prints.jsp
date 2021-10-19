<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
	<meta charset="utf-8">
	<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js" type="text/javascript"></script>
		
		<style type="text/css">
			*{
				margin: 0px auto;
				padding: 0px;
				font-family: unset;
				color: #313131;
			}
			 
			.all{
				width: 800px;
				border: 1px  solid;
				padding-bottom: 30px;
			}
			
			.main_title{
			    margin-top: 20px;
			    text-align: center;
			}
			.pat_info{
				text-align: center;
			}
			
			.item_title{
				width: 170px;
			    border: 0px solid;
			    display: -webkit-inline-box;
			    margin-top: 15px;
			}
			
			.item_value{
				border-bottom: 1px dashed;
			    width: 98px;
			    border-bottom: 1px dashed;
			    display: -webkit-inline-box;
			}
			
			.report{
				width: 705px;
    			border: 0px solid red;
    			margin-top: 10px;
    			line-height: 26px;
			}
			
			.pat_name{
				font-weight: 600;
				text-align: right;
			}
			
			.pat_dept{
				width: 705px;
    			border: 0px solid red;
    			margin-top: 10px;
			}
			
			.dept_title{
				display: -webkit-inline-box;
				width: 228px;
				margin-top: 25px;
			}
			
			.table_rep{
				width: 705px;
    			border: 0px solid red;
    			margin-top: 10px;
			}
			
			.div_table{
				margin-top: 5px;
			}
			
			 table {
				width: 700px;
				min-height: 25px;
				line-height: 25px;
				text-align: center;
				border-collapse: collapse;
				padding:2px;
			 }
			 
			 table,table tr th, table tr td { border:1px solid black; }  
			 
			 #inp_cz{
			 	position: absolute;
			    margin-left: 730px;
			    margin-top: 10px;
			    cursor: pointer;
			    border-radius: 31px;
			    border: none;
			    background: #dae4f7;
			    width: 50px;
			    height: 50px;
			    outline: none;
			 }
			 
			 input#inp_cz:hover {
    			background: #b7cedc;
			}
		</style>
		<style media="print">
		  @page {
		   size: auto;
		   margin: 0mm;
		  }
 		</style>
	</head>
	<body>
		<div class="all">
			<input type="button" value="打印" id="inp_cz" onclick="prints();" />
			<h3 class="main_title">医保人员核对及使用自费或部分自费药品丶诊疗情况登记表</h3>
			<p class="pat_info">
				<span class="item_title"><b>姓名：</b>张三</span>
				<span class="item_title"><b>性别：</b>男</span>
				<span class="item_title"><b>医保号：</b>1234567890</span>
				<span class="item_title"><b>ID号：</b>9876543210</span>
			</p>
			<div class="report">
				告知：我院为三甲基医院将，这就是好说的粉红色的带货费是电话度搜及偶滴神会计师的缴费
				候收到货穷三代院为三甲基医院将，这就是好说的粉红色的带货费是电话度搜及偶滴神会计
				师的缴费候收到货穷三代（以上为举例说明）
				<p class="pat_name">知晓病人或家属请签字确认： <span class="item_value"></span></p>
			</div>
			<p class="pat_dept">
				<span class="dept_title" style="width: 180px;"><b>入院时间: </b>2018-12-22</span>
				<span class="dept_title" style="width: 364px"><b>入院科室：</b>21三体综合征科101病区</span>
				<span class="dept_title" style="width: 140px;"><b>床号：</b>20181222</span>
				<span class="dept_title"><b>入经治医生签字：</b><span class="item_value"></span></span>
				<span class="dept_title"><b>科室负责人签字：</b><span class="item_value"></span></span>
				<span class="dept_title"><b>医保办签字：</b><span class="item_value" style="width: 130px"></span></span>
			</p>
			<p class="table_rep">以下为病人使用自费药品部分药品诊疗项目统一签字，同意请签字</p>
			<div class="div_table">
				<table>
					<thead>
						<tr>
							<th>序</th>
							<th>药品诊疗项目</th>
							<th>数量</th>
							<th>金额</th>
							<th>费用等级</th>
							<th>使用理由</th>
							<th>病人或家属签字</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>阿莫西林焦浪颗粒与板蓝根含片</td>
							<td>1</td>
							<td>100.00</td>
							<td>甲类</td>
							<td>我今天就是要用</td>
							<td>李思怡哥</td>
						</tr>
						<tr>
							<td>1</td>
							<td>阿莫西林焦浪颗粒与板蓝根含片</td>
							<td>1</td>
							<td>100.00</td>
							<td>甲类</td>
							<td>我今天就是要用</td>
							<td>李思怡哥</td>
						</tr>
						<tr>
							<td>1</td>
							<td>阿莫西林焦浪颗粒与板蓝根含片</td>
							<td>1</td>
							<td>100.00</td>
							<td>甲类</td>
							<td>我今天就是要用</td>
							<td>李思怡哥</td>
						</tr>
						<tr>
							<td>1</td>
							<td>阿莫西林焦浪颗粒与板蓝根含片</td>
							<td>1</td>
							<td>100.00</td>
							<td>甲类</td>
							<td>我今天就是要用</td>
							<td>李思怡哥</td>
						</tr>
						<tr>
							<td>1</td>
							<td>阿莫西林焦浪颗粒与板蓝根含片</td>
							<td>1</td>
							<td>100.00</td>
							<td>甲类</td>
							<td>我今天就是要用</td>
							<td>李思怡哥</td>
						</tr>
						<tr>
							<td>1</td>
							<td>阿莫西林焦浪颗粒与板蓝根含片</td>
							<td>1</td>
							<td>100.00</td>
							<td>甲类</td>
							<td>我今天就是要用</td>
							<td>李思怡哥</td>
						</tr>
						<tr>
							<td>1</td>
							<td>阿莫西林焦浪颗粒与板蓝根含片</td>
							<td>1</td>
							<td>100.00</td>
							<td>甲类</td>
							<td>我今天就是要用</td>
							<td>李思怡哥</td>
						</tr>
						<tr>
							<td>1</td>
							<td>阿莫西林焦浪颗粒与板蓝根含片</td>
							<td>1</td>
							<td>100.00</td>
							<td>甲类</td>
							<td>我今天就是要用</td>
							<td>李思怡哥</td>
						</tr>
						<tr>
							<td>1</td>
							<td>阿莫西林焦浪颗粒与板蓝根含片</td>
							<td>1</td>
							<td>100.00</td>
							<td>甲类</td>
							<td>我今天就是要用</td>
							<td>李思怡哥</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</body>
</html>


<script type="text/javascript">
	function prints(){
		$('#inp_cz').css('display', 'none');
		window.print();
		$('#inp_cz').css('display', 'block');
	}
	
</script>