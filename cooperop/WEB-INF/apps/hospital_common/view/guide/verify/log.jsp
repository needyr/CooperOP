<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="数据校验日志" >
<style type="text/css">
.choose{background-color: #d3d3d3 !important;}
</style>
	<s:row>
		<s:form id="form" label="筛选条件">
			<s:row>
				<s:datefield label="开始时间" name="start_time" format="yyyy-MM-dd HH:mm:ss" autocomplete="off"></s:datefield>
				<s:datefield label="截止时间" name="end_time" format="yyyy-MM-dd HH:mm:ss" autocomplete="off"></s:datefield>
				<s:radio label="校验名称" cols="1" name="master_bh" value="">
					<s:option label="全部" value=""></s:option>
					<s:option label="数据采集后" value="1"></s:option>
					<s:option label="事后审查后" value="2"></s:option>
				</s:radio>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="日志信息" autoload="false" action="hospital_common.guide.verify.queryLog" icon="fa fa-list" sort="true">
			<s:toolbar>
				<s:button label="查询" icon="fa fa-search" onclick="query();"></s:button>
			</s:toolbar>
            <s:table.fields>
                <s:table.field name="check_time" label="创建时间" sort="true" datatype="datetime" format="yyyy-MM-dd HH:mm:ss"></s:table.field>
                <s:table.field name="description" label="校验名称" sort="true"></s:table.field>
                <s:table.field name="is_abnormal" label="是否存在错误" sort="true" datatype="script">
                	var a = record.is_abnormal;
                	if(a == '1'){
                		return '<lable style="color:red">是</lable>';
                	}
                	return '否';
                </s:table.field>
                <s:table.field name="state" label="状态" datatype="script" sort="true">
                	var a = record.state;
                	if(a == '0'){
                		return '<lable style="color:green">完成</lable>';
                	}
                	return '<lable style="color:red">校验中</lable>';
                </s:table.field>
                <s:table.field name="caozuo" label="操作" datatype="script">
                	var fhtml = [] ;
                	fhtml.push('<a href="javascript:void(0)" onclick="auditDetail(\''+record.log_bh+'\')">');
                	fhtml.push('详情</a>');
                	return fhtml.join('');
                </s:table.field>
            </s:table.fields>
        </s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		query();
		$('#datatable').on('click', 'tbody tr', function(e) { 
			$(this).addClass('choose').siblings().removeClass('choose');
		});
	});
	
	function query(){
		var data = $('#form').getData();
		$("#datatable").params(data);
		$("#datatable").refresh();
	}
	
	function auditDetail(log_bh){
	    $.modal("log_mx.html","日志详情",{
	    	width: '90%',
	    	height: '90%',
	    	log_bh: log_bh,
	        callback : function(e){}
	    });
	}
	
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "H+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
</script>
