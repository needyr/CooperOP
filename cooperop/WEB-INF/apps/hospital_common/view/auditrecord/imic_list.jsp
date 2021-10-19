<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="审方查询" disloggedin="true">
<style type="text/css">
	.choose{background-color: #d3d3d3 !important;}
	#msgdtl{
		color: #5b8e9e;
	    font-size: 15px;
	    line-height: 22px;
	    padding-left: 5px;
	    border-left: 5px solid #dce0d4;
	    font-family: '微软雅黑';
    }
</style>
	<s:row>
		<s:form id="form" >
			<s:row>
				<s:datefield label="审查日期从" name="start_time" format="yyyy-MM-dd HH:mm:ss" autocomplete="off"></s:datefield>
				<s:datefield label="至" name="end_time" format="yyyy-MM-dd HH:mm:ss" autocomplete="off"></s:datefield>
				<s:textfield name="doctor" label="开嘱人" placeholder="请输入开嘱人姓名"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield name="dept" label="患者科室" placeholder="请输入科室名称或编码"></s:textfield>
				<s:textfield name="patient" label="患者" placeholder="请输入患者姓名或ID"></s:textfield>
				<s:textfield name="remark" label="当前状态" placeholder=""></s:textfield>
			</s:row>
			<s:row>
				<s:checkbox label="工作站类型" name="d_type" cols="2">
					<s:option label="住院" value="1"></s:option>
					<s:option label="门诊" value="2"></s:option>
					<s:option label="急诊" value="3"></s:option>
					<s:option label="护士站" value="4"></s:option>
					<s:option label="医保结算" value="5"></s:option>
				</s:checkbox>
				<s:checkbox label="智能审查结果" name="sys_audit_result" cols="1">
					<s:option label="通过" value="MY"></s:option>
					<s:option label="提示" value="MT"></s:option>
					<s:option label="拦截" value="MQ"></s:option>
					<s:option label="驳回" value="MN"></s:option>
				</s:checkbox>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="审方列表" autoload="false" action="hospital_common.auditrecord.query" sort="true">
            <s:toolbar>
            	<s:button label="查看审方报表" icon="fa fa-table" onclick="count_table();"></s:button>
            	<s:button label="审方耗时散点图" icon="fa fa-table" onclick="costChart();"></s:button>
            	<s:button label="查询" icon="fa fa-search" onclick="query();"></s:button>
            </s:toolbar>
            <s:table.fields>
                <s:table.field name="create_time_common" label="提交审方时间" datatype="script" width="120" sort="true">
                	return record.create_time_common.substring(5);
                </s:table.field>
                <s:table.field name="p_type" label="工作站类型" datatype="script" width="50" sort="true">
                	var d_type = record.d_type;
                	if(d_type == 1 ){
                		d_type = '住院';
                	}else if(d_type == 2){
                		d_type = '门诊';
                	}else if (d_type == 3){
                		d_type = '急诊';
                	}else if(d_type == 4){
                		d_type = '护士站';
                	}else if(d_type == 5){
                		d_type = '医保结算';
                	}
                	return '<a onclick="druglist(\''+record.id+'\', this);">'+d_type+ '</a>';
                </s:table.field>
                <s:table.field name="patient_name_s" label="患者姓名" datatype="template" width="60">
                	<a href="javascript:void(0);" onclick="toPatient('$[patient_id]','$[visit_id]', this)">$[patient_name]</a>
                </s:table.field>
                <s:table.field name="interface_type_name" label="医保体系" width="120" sort="true"></s:table.field>
                <s:table.field name="claim_type_name" label="就诊类型" width="100"></s:table.field>
                <s:table.field name="deptment_name" label="开嘱科室" sort="true" width="100" datatype="script">
                	var deptment_name = record.deptment_name;
					if(!deptment_name){
						deptment_name = " ";
					}
					var show = deptment_name;
					if(deptment_name && deptment_name.length >= 9){
						show = deptment_name.substring(0, 8) + '...';
					}
					return '<span title="'+deptment_name+'">'+show+'</span>';
                </s:table.field>
                <s:table.field name="doctor_no" label="开嘱人" datatype="script" width="80">
                	return record.doctor_name + '(' +record.doctor_no+')';
                </s:table.field>
            	<s:table.field name="sys_audit_result" label="智能审查" datatype="script" width="40" sort="true">
                	var sar = record.sys_audit_result;
                	if(sar == 'MY'){
                		sar = '通过√';
                	}else if(sar == 'MT'){
                		sar = '提示√';
                	}else if(sar == 'MQ'){
                		sar = '拦截?';
                	}else if(sar == 'MN'){
                		sar = '驳回×';
                	}else if(sar == 'NOCHECK'){
                		sar = '不审√';
                	}else if(sar == 'MF'){
                		sar = '超时√';
                	}else{
                		sar = ' ';
                	}
                	return sar;
                </s:table.field>
                <s:table.field name="remark" label="当前状态" sort="true"></s:table.field>
                <s:table.field name="costs" label="耗时/秒" datatype="script" width="50" sort="true">
                	return record.costs/1000;
                </s:table.field>
                 <s:table.field name="ip" label="用户IP" sort="true" datatype="string"></s:table.field>
                <s:table.field name="caozuo" label="操作" datatype="script" width="60">
                	var fhtml = [] ;
                	var state = record.sys_audit_result;
                	if(!state){
                		state = record.state;
                	}
                	if('MTMQMNMF'.indexOf(state) < 0){
                		if(record.d_type == '5'){
                			fhtml.push('<a href="javascript:void(0)" onclick="auditDetail2(\''+ record.common_id +'\', \''+record.state+'\', \''+record.patient_id+'\', \''+record.visit_id+'\', \''+record.doctor_no+'\')">');
                			fhtml.push('详情</a>');
                		}else{
                			fhtml.push('√');
                		}
                	}else{
                		if(record.d_type == '5'){
                			fhtml.push('<a href="javascript:void(0)" onclick="auditDetail2(\''+ record.common_id +'\', \''+record.state+'\', \''+record.patient_id+'\', \''+record.visit_id+'\', \''+record.doctor_no+'\')">');
                			fhtml.push('详情</a>');
                		}else{
                			fhtml.push('<a href="javascript:void(0)" onclick="auditDetail(\''+ record.common_id +'\', \''+record.state+'\')">');
                			fhtml.push('详情</a>');
                		}
                	}
                	fhtml.push(' | <a href="javascript:void(0)" onclick="seeCInfo(\''+ record.common_id +'\')">日志</a>');
                	return fhtml.join('');
                </s:table.field>
            </s:table.fields>
        </s:table>
        <div id="msgdtl">
        	
        </div>
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
		//setInterval('sand1()', 1000);
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
	
	function auditDetail(common_id, state){
		  $.modal("/w/hospital_imic/auditresult/imic_show.html?id="+ common_id+"&state="+state,"查看医保审查详情",{
	    	width: '936px',
	    	height: '600px',
	        callback : function(e){}
	    });
	}
	
	function auditDetail2(common_id, state, patient_id, visit_id, doctor_no){
		  $.modal("/w/hospital_imic/simic/index.html?id="+ common_id+"&state="+state+"&patient_id="+patient_id+"&visit_id="+visit_id+"&doctor_no="+doctor_no+"&see=1","查看医保结算审查详情",{
	    	width: '940px',
	    	height: '705px',
	        callback : function(e){}
	    });
	}
	  
	function toPatient(patient_id, visit_id, _this){
		$(_this).css('color', '#900987');
		$.modal('/w/hospital_common/showturns/patientdetail.html?patient_id='+patient_id+'&&visit_id='+visit_id+'',"查看审查详情",{
		  	width: '900px',
		  	height: '550px',
		    callback : function(e){}
		  });
	}
	
	function druglist(id, _this){
		$(_this).css('color', '#900987');
		$.modal('/w/hospital_imic/auditresult/itemlist2.html?imic_audit_id='+ id,"查看参与本次审查的计费项目",{
		  	width: '95%',
		  	height: '95%',
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
	
	function count_table(){
		var data = $('#form').getData();
		$.modal('/w/hospital_imic/chart/chart/BB08.html?start_date='+data.start_time+'&end_date='+data.end_time,"查看审查统计报表",{
		  	width: '99%',
		  	height: '99%',
		    callback : function(e){}
		});
	}
	
	/* function seeDtl(info){
		$('#msgdtl').html(info.replace(/#/g,'<br>'));
		index = layer.open({
			  type: 1,
			  title: "查看日志记录",
			  area: ['600px', '636px'],
			  content: $("#msgdtl")
		});
	} */
	
	function seeCInfo(common_id){
		$.modal('/w/hospital_imic/auditresult/info.html',"查看审查日志",{
		  	width: '950px',
		  	height: '600px',
		  	id: common_id,
		    callback : function(e){}
		});
	}
	
	function costChart(){
		$.modal('/w/hospital_imic/auditresult/costchart.html',"今日审核记录-审核耗时散点图",{
		  	width: '1024px',
		  	height: '700px',
		    callback : function(e){}
		});
	}
</script>