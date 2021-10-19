<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="cn.crtech.cooperop.bus.util.GlobalVar"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<head>
	<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/plugins/jquery-migrate.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/plugins/jquery.cokie.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/plugins/jquery.json.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/plugins/layer/layer.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/scripts/common.js" type="text/javascript"></script>
	<link href="${pageContext.request.contextPath}/res/hospital_common/css/pat/bill.css" rel="stylesheet" type="text/css">
	<link type="text/css" rel="stylesheet" href="/theme/plugins/font-awesome/css/font-awesome.min.css">
	<link type="text/css" rel="stylesheet" href="/theme/plugins/simple-line-icons/simple-line-icons.min.css">
</head>
<body>
	<div id="wrapper">
		<!-- 收费项目 -->
        <div class="items span-content">
        	<div class="table_div" style="height: 461px">
        		<table id="bitems" data-q="0">
					<thead>
						<tr>
							<!-- <th style="width: 30px">组</th>
							<th style="width: 40px">长/临</th> -->
	                 		<th style="width: 60px">类别</th>
	                 		<th style="width: 120px">收费时间</th>
	                 		<th style="width: 100px">项目编码</th>
	                 		<th style="width: 200px">项目名称</th>
	                 		<th style="width: 100px">规格</th>
	                 		<th style="width: 60px">数量单位</th>
	                 		<th style="width: 50px">单价/元</th>
	                 		<th style="width: 60px">金额</th>
	                 		<!-- <th style="width: 60px">途径</th> 
	                 		<th style="width: 60px">频次</th>-->
	                 		<th style="width: 250px">药品信息</th>
	                 		<th style="width: 60px">科室</th>
	                 		<th style="width: 60px">医生</th>
	                 		<th style="width: 60px">护士</th>
	                    </tr>
					</thead>
					<tbody>
					
					</tbody>
				</table>
        	</div>
        	<div class="page_div">
        		<span style="position: fixed;left: 18px;">第1页，共100页</span>
			    <a href="#" class="pagenum">上一页</a>
			   	<a href="#" class="pagenum">1</a>
			    <a href="#" class="pagenum">2</a>
		        <a href="#" class="pagenum">3</a>
		        <a href="#" class="pagenum">4</a>
		        <a href="#" class="pagenum">...</a>
		        <a href="#" class="pagenum">下一页</a>
		    </div>
        </div>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		patientBillItems(1, 1, 15, 'one');
	})
	
	function patientBillItems(start, page, limit, everytime){
		var is_query = $('#bitems').data('q');
		if((is_query == 0 && everytime == 'one') || everytime == 'more'){
			var tbodyhtml = [];
			var reqdata = {
				'patient_id' : '${param.patient_id}',
				'visit_id' : '${param.visit_id}',
				'start' : start,
				'limit': limit
			};
			$.call('hospital_common.patient.queryBillItems', reqdata, function(rtn){
				//alert('start' + start + 'page' + page + 'limit' + limit);
				$('#bitems').find('tbody').html('');
				if(rtn && rtn.bitems){
					var items = rtn.bitems.resultset;
					var countNum = rtn.bitems.count;
					for(var i=0; i< items.length; i++){
						console.log(items.shpgg);
						tbodyhtml.push('<tr>');
						/* tbodyhtml.push('<td style="width: 60px">'+ (items[i].tag == null?'':items[i].tag) +'</td>');
						tbodyhtml.push('<td style="width: 60px">'+ (items[i].repeat_indicator == null?'':items[i].repeat_indicator) +'</td>'); */
						tbodyhtml.push('<td style="width: 60px">'+ (items[i].item_class == null ? "": items[i].item_class) +'</td>');
						tbodyhtml.push('<td style="width: 120px">'+ items[i].billing_date_time +'</td>');
						tbodyhtml.push('<td style="width: 100px">'+ items[i].item_code +'</td>');
						tbodyhtml.push('<td style="width: 200px">'+ items[i].item_name +'</td>');
						tbodyhtml.push('<td style="width: 200px">'+ items[i].shpgg +'</td>');
						tbodyhtml.push('<td style="width: 80px">'+ ((parseFloat(items[i].shl)) + items[i].dw) +'</td>');
						tbodyhtml.push('<td style="width: 80px">'+ parseFloat(items[i].dj) +'</td>');
						tbodyhtml.push('<td style="width: 80px">'+ parseFloat(items[i].je) +'</td>');
						/* tbodyhtml.push('<td style="width: 80px">'+ (items[i].administration == null ? "": items[i].administration) +'</td>'); 
						tbodyhtml.push('<td style="width: 80px">'+ (items[i].frequency == null ? "": items[i].frequency) +'</td>');*/
						tbodyhtml.push('<td style="width: 250px">'+ (items[i].drug_message == null ? "":items[i].drug_message ) +'</td>');
						tbodyhtml.push('<td style="width: 60px">'+ items[i].dept_name +'</td>');
						tbodyhtml.push('<td style="width: 80px">'+ (items[i].doctor == null ? "": items[i].doctor) +'</td>');
						tbodyhtml.push('<td style="width: 80px">'+ (items[i].nurse == null ? "": items[i].nurse) +'</td>');
						tbodyhtml.push('</tr>');
					}
					$('#bitems').find('tbody').append(tbodyhtml.join(''));
					pageNumRe(countNum, page, 15);
				}
			});
			$('#bitems').data('q', '1');
		}
	}
	
	function pageNumRe(count, page, rnum){
		var pagehtml = [];
		var maxpage = (count%rnum == 0 ? (count/rnum) :(parseInt(count/rnum) + 1));
		pagehtml.push('<span style="position: fixed;left: 18px;">共'+count+'条，第'+page+'页，共'+ maxpage +'页，每页'+ rnum +'条</span>');
		//start = 10(page-1)+1
		if(page  >1){
			pagehtml.push('<a href="#" class="pagenum" data-num = "'+(page -1)+'">上一页</a>');
			//pagehtml.push('<a href="#" class="pagenum" data-num = "1">1</a>');
		}
		if(page > 1){
			pagehtml.push('<a href="#" class="pagenum"  data-num = "1">1</a>');
		}
		if(page > 2){
			pagehtml.push('<a href="#" class="pagenum"  data-num = "2">2</a>');
		}
		if(page -3 >0){
			pagehtml.push('<a href="#" class="pagenum" style="pointer-events: none;">...</a>');
		} 
		pagehtml.push('<a href="#" class="pagenum bechoose" data-num = "'+(page)+'">'+ page +'</a>');
		 if(page< maxpage- 2){
			pagehtml.push('<a href="#" class="pagenum" style="pointer-events: none;">...</a>');
		}
		 if(page< maxpage- 1){
				pagehtml.push('<a href="#" class="pagenum"  data-num = "'+(maxpage - 1)+'">'+ (maxpage - 1) +'</a>');
			}
		if(page < maxpage){
			pagehtml.push('<a href="#" class="pagenum"  data-num = "'+(maxpage)+'">'+ (maxpage) +'</a>');
		} 
		if(page < maxpage){
			/* pagehtml.push('<a href="#" class="pagenum"  data-num = "'+(maxpage)+'">'+ (maxpage) +'</a>'); */
			pagehtml.push('<a href="#" class="pagenum" data-num = "'+(page+1)+'">下一页</a>');
		}
		$('.page_div').html('');
		$('.page_div').append(pagehtml.join(''));
		$('.page_div .pagenum').click(function(){
			var page = parseInt($(this).data('num'));
			//pageNumRe(98, page, 10);
			patientBillItems(rnum*(page-1) +1, page, rnum, 'more');
		});
	}
</script>