<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="审方查询" disloggedin="true">
<style type="text/css">
	.choose{background-color: #d3d3d3 !important;}
</style>
	<s:row>
		<s:form id="form" >
			<s:row>
				<s:datefield label="开始时间" name="start_time" format="yyyy-MM-dd HH:mm:ss" autocomplete="off"></s:datefield>
				<s:datefield label="截止时间" name="end_time" format="yyyy-MM-dd HH:mm:ss" autocomplete="off"></s:datefield>
				<s:textfield name="doctor" label="医生"></s:textfield>
				<s:select cols="1" label="审查类型" name="audit_type" value="-1">
					<s:option value="-1" label="全部" ></s:option>
					<s:option label="即时审查" value="0"></s:option>
					<s:option label="事后审查" value="1"></s:option>
				</s:select>
				<s:select cols="1" label="审查结果" name="auditResult" value="-1">
					<s:option value="-1" label="全部" ></s:option>
					<s:option label="通过" value="Y"></s:option>
					<s:option label="提示" value="T"></s:option>
					<s:option label="拦截" value="Q"></s:option>
					<s:option label="不通过" value="N"></s:option>
				</s:select>
				<s:autocomplete label="审查科室" name="deptname"  cols="1" action="hospital_common.auditset.checkdept.queryDeptAll" limit="10">
					<s:option value="$[dept_code]" label="$[dept_name]">
						<span style="float:left;display:block;width:183px;">$[dept_name]</span>
					</s:option>
				</s:autocomplete>
				<s:textfield name="str_consumingTime" placeholder="单位/s" label="最低耗时"></s:textfield>
				<s:textfield name="end_consumingTime" placeholder="单位/s" label="最高耗时"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="审方列表" autoload="false" action="hospital_common.auditrecord.queryAuditAll" sort="true">
            <s:toolbar>
            	<s:button label="刷新" icon="fa fa-search" onclick="query();"></s:button>
            </s:toolbar>
            <s:table.fields>
                <s:table.field name="create_time" label="提交审方时间" sort="true"></s:table.field>
                <s:table.field name="patient_name" label="患者" width="120px" datatype="template">
                	<a href="javascript:void(0);" onclick="toPatient('$[patient_id]','$[visit_id]', this)">$[patient_name]</a>
                </s:table.field>
                <s:table.field name="visit_id" label="住院次数"></s:table.field>
                <s:table.field name="dept_code" label="开嘱科室"></s:table.field>
                <s:table.field name="doctor_no" label="开嘱医生" datatype="script">
                	return record.doctor_name + '(' +record.doctor_no+')';
                </s:table.field>
                <s:table.field name="is_after" label="审查类型" datatype="script">
                	var is_after = record.is_after; 
                	if(is_after == '1'){
                		return '事后审查';
                	}else{
                		return '即时审查';
                	}
                </s:table.field>
                <s:table.field name="state" label="审方结果" datatype="script">
                	var state = record.state;
                	var state_name ;
                	if(state == 'Y'){
                		state_name = '通过';
                	}else if(state == 'N'){
                		state_name = '<font style="color: red">驳回</font>';
                	}else if(state == 'T'){
                		state_name = '<font style="color: green">提示</font>';
                	}else if(state == 'Q'){
                		state_name = '<font style="color: red">拦截</font>';
                	}
                	return state_name;
                </s:table.field>
                 <s:table.field name="costtime" label="审查总耗时" datatype="script" sort="true" >
                 	var costtime = record.costtime;
                 	return (costtime/1000)+'s';
                 </s:table.field>
                 <s:table.field name="demo_resp" label="参与产品" datatype="script">
                 	var demo_resp = jQuery.parseJSON(record.demo_resp);
                 	var flag = demo_resp.flag;
                 	if(flag.indexOf("a") >= 0 && flag.indexOf("b") >= 0) { 
					    return 'ipc & imic'
					}else if(flag.indexOf("a") >= 0){
						return 'ipc'
					}else if(flag.indexOf("b") >= 0){
						return 'imic'
					}else{
						return ''
					}
                 </s:table.field>
                <s:table.field name="caozuo" label="操作" datatype="script">
                	var fhtml = [] ;
                	var state = record.state;
                	fhtml.push('<a href="javascript:void(0)" onclick="auditDetail(\''+ record.id +'\')">详情</a>');
                	fhtml.push(' | <a href="javascript:void(0)" onclick="seeCInfo(\''+ record.id +'\')">imic日志</a>');
                    return fhtml.join('');
                </s:table.field>
            </s:table.fields>
        </s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		var today ;
		$.call('hospital_common.showturns.onlyGetTime', {}, function(rtn){
			today = rtn.today;
			$('[name = start_time]').val(today + ' 00:00:00');
			$('[name = end_time]').val(today + ' 23:59:59');
		},function(e){}, {async: false, remark: false  });
		query();
		$('#datatable').on('click', 'tbody tr', function(e) { 
			$(this).addClass('choose').siblings().removeClass('choose');
		});
		setInterval('sand1()', 1000);
	});
	
	function sand1(){
		$('.up_time').each(function(){
			var _this = $(this);
			var new_time = parseInt(_this.text()) + 1;
			if(new_time >= 300 && new_time <= 600){
				_this.css('color', 'orange');
			}else if (new_time > 600){
				_this.css('color', 'red');
			}
			_this.text(new_time);
		});
	}
	
	function query(){
		var data = $('#form').getData();
		$("#datatable").params(data);
		$("#datatable").refresh();
	}
	
	function auditDetail(common_id){
		 /*  $.modal("/w/hospital_common/showturns/show.html?id="+ common_id,"查看审查详情",{
	    	width: '935px',
	    	height: '645px',
	        callback : function(e){}
	    }); */
		$.modal("detail.html?id="+ common_id,"查看审查详情",{
	    	width: '1100px',
	    	height: '590px',
	        callback : function(e){}
	    }); 
	}
	  
	function toPatient(patient_id, visit_id, _this){
		$(_this).css('color', '#900987');
		$.modal('/w/hospital_common/patient/index.html?patient_id='+patient_id+'&&visit_id='+visit_id+'',"查看审查详情",{
		  	width: '900px',
		  	height: '550px',
		      callback : function(e){}
		  });
	}
	
	function druglist(id, _this){
		$(_this).css('color', '#900987');
		$.modal('/w/ipc/auditresult/druglist.html?auto_audit_id='+ id,"查看医嘱",{
		  	width: '900px',
		  	height: '550px',
		    callback : function(e){}
		});
	}
	
	function seeCInfo(common_id){
		$.modal('/w/hospital_imic/auditresult/info.html',"查看审查日志",{
		  	width: '950px',
		  	height: '600px',
		  	id: common_id,
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