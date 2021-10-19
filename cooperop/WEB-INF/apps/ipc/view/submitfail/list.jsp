<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="云端宕机处理">
<s:row>
<s:form border="0" id="form" label="">
	<s:row>
		<s:textfield label="患者ID/姓名" name="name" cols="1"></s:textfield>
		<s:textfield label="医生" name="d_name" cols="1"></s:textfield>
		<input type="hidden"  name="deptfifter" value="">
		<s:textfield cols="1"  name="dept_name" label="开嘱科室" readonly="true" dbl_action="ss();"></s:textfield>	
		<s:datefield label="审查时间" name="mintime" format="yyyy-MM-dd HH:mm:dd"  cols="1" ></s:datefield>
		<s:datefield label="至" name="maxtime" format="yyyy-MM-dd HH:mm:dd"  cols="1" ></s:datefield>
		<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
	</s:row>
</s:form>
</s:row>
<s:row>
<s:table action="ipc.submitfail.failList" sort="true" label="强制使用列表" autoload="false" id="table">
	<s:toolbar>
		<s:button label="开启自动刷新" name="fla"  onclick="autoflash()"></s:button>
	</s:toolbar>
	<s:table.fields>
	<s:table.field name="create_time" label="审查时间" sort="true" defaultsort="desc"></s:table.field>
	<s:table.field name="p_type" label="开单类型" datatype="script">
    	var p_type = record.p_type;
    	var d_type = record.d_type;
    	if(p_type == 1){
    		p_type= '医嘱';
    	}else if (p_type == 2){
    		p_type= '处方';
    	}
    	if(d_type == 1 ){
    		d_type = '住院';
    	}else if(d_type == 2){
    		d_type = '急诊';
    	}else if (d_type == 3){
    		d_type = '门诊';
    	}
    	return '<a onclick="druglist(\''+record.id+'\', this);">'+d_type + p_type+ '</a>';
    </s:table.field>
	<s:table.field name="patient" label="患者" datatype="template">
		<a href="javascript:void(0);" onclick="toPatient('$[patient_id]','$[visit_id]', this)">$[patient]</a>
	</s:table.field>
	<s:table.field name="deptment_name" label="开嘱科室"></s:table.field>
	<s:table.field name="doctor_name" label="开嘱医生" datatype="script">
		return record.doctor_name + '('+ record.doctor_no +')'
	</s:table.field>
	<s:table.field name="timeout" label="延迟时间" calss="timeoutchenge"></s:table.field>
	<s:table.field name="caozuo" label="操作" datatype="script">
		return '<a onclick="detailinfo(\''+record.common_id+'\');">详情 </a>|||<a onclick="pass(\''+record.id+'\',\'Y\');"> 通过 </a><a onclick="pass(\''+record.id+'\',\'N\');"> 不通过  </a><a onclick="pass(\''+record.id+'\',\'D\');"> 医生决定 </a>';
	</s:table.field>
	</s:table.fields>
</s:table>
</s:row>
</s:page>

<script type="text/javascript">
$(function(){
	var flash_Interval;
});

function timeoutchenge(){
	
}

function query(){
	var data = $('#form').getData();
	data.sync = false;
	$('#table').params(data);
	$('#table').refresh();
}

function detailinfo(id){
	autoflash()
	$.modal("/w/hospital_common/showturns/show.html","审查信息",{
		width:"935px",
		height:"640px",
		id: id,
		callback : function(e){
		}
	});
}

$('[name=dept_name]').dblclick(function(){
	autoflash()
	var code = $('[name=deptfifter]').val();
	var datasouce = $('[name=datasouce]').val();
	$.modal("/w/ipc/sample/choose/department.html", "添加科室", {
		height: "550px",
		width: "40%",
		code: code,
		maxmin: false,
		datasouce: datasouce,
		callback : function(rtn) {
			if(rtn){
				var name = rtn.name.toString();
				var code = rtn.code.toString();
				$('[name=dept_name]').val(name);
				$('[name=deptfifter]').val(code);
			}
	    }
	});
});

function pass(auto_audit_id,state){
	var content = '';
	if(state == 'N'){
		content = '【不通过】';
	}else if(state == 'Y'){
		content = '【通过】';
	}else if(state == 'D'){
		content = '【医生决定】';
	}
	$.confirm("是否确认"+content+"？",function callback(e){
		if(e==true){
			$.call("ipc.submitfail.ispass",{id:auto_audit_id,state:state},function(rtn){
				if(rtn == 0){
					layer.massege('已被云端药师审查!');
				}
				query();
			})
		}
	})
}

function druglist(id, _this){
	autoflash()
	$(_this).css('color', '#900987');
	$.modal('/w/ipc/auditresult/druglist.html?auto_audit_id='+ id,"查看医嘱",{
	  	width: '900px',
	  	height: '550px',
	    callback : function(e){}
	});
}

function autoflash(){
	var content = $('[name="fla"]').text();
	if(content == '开启自动刷新'){
		$('[name="fla"]').text('停止自动刷新');
		query();
		flash_Interval = setInterval(function(){
			query();
		},10000)
	}else{
		clearInterval(flash_Interval);
		$('[name="fla"]').text('开启自动刷新');
	}
}

function toPatient(patient_id, visit_id, _this){
	$(_this).css('color', '#900987');
	$.modal('/w/hospital_common/showturns/patientdetail.html?patient_id='+patient_id+'&&visit_id='+visit_id+'',"查看审查详情",{
	  	width: '900px',
	  	height: '550px',
	      callback : function(e){}
  });
}
</script>