<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="单个患者结算审查" disloggedin="true">
	<s:row>
		<div id="progress" style="color: #026e73; font-size: 18px;padding-left: 10px;">
			系统正在审查，请稍等 <span id="time_show" style="font-size: 18px;">0</span>s ...
		</div>
	</s:row>
<script type="text/javascript">
	var count = 0;
	$(function(){
		//启动审查
		var pat = {};
		pat.patient_id = '${param.patient_id}';
		pat.visit_id = '${param.visit_id}';
		pat.page = '${param.page}';
		pat.settlement_last_year = '${param.settlement_last_year}';
		pat.bill_start_time = '${param.start}';
		pat.bill_end_time = '${param.end}';
		// 读秒
		reftime();
		
		$.call('hospital_common.afaudit.auditonepat', pat, function(rtn){
			clearTimeout(count);//停止读秒，也可不写
			//审查完成，跳转页面
			if(rtn && rtn.msg == '1'){
				window.location.href='/w/hospital_imic/simic/index.html?id='+rtn.id+'&patient_id='+pat.patient_id+'&visit_id='+pat.visit_id+'&doctor_no='+rtn.doctor_no;
			}else{
				$.message(rtn.msg);
				$('#progress').append('[提示：' + rtn.msg + ']');
			}
		});
		
		
	});
	
	function reftime(){
		var num = parseInt($('#time_show').text());
		$('#time_show').text(num+1);
		count = setTimeout(function(){ reftime() }, 1000);
	}
</script>
</s:page>
