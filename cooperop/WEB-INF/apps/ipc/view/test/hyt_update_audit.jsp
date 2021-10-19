<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="【测试】审查知识库升级, 审查测试" disloggedin="true">
<s:row>
<s:table action="ipc.test.hyt_audit_test_result" id="datatable" autoload="true">
<s:toolbar>
<s:button label="测试" onclick="test();"></s:button>
<s:button label="查询" onclick="query();"></s:button>
</s:toolbar>
<s:table.fields>
<s:table.field name="id" label="审查ID"></s:table.field>
<s:table.field name="create_time" label="审查时间"></s:table.field>
<s:table.field name="result" label="审查结果" datatype="script">
var state = record.state;
var state_name ;
if(state == 'HL_Y'){
	state_name = '智能审查通过';
}else if(state == 'HL_N'){
	state_name = '<font style="color: #747f00">智能审查拦截，等待医生决策</font>';
}else if(state == 'HL_F'){
	state_name = '<font style="color: red">智能审查超时(通过)</font>';
}else if(state == 'DB'){
	var flag = record.order_flag;
	if(flag == '9'){
		state_name = '医生重复提交审查';
	}else{
		state_name = '智能审查拦截，医生返回调整';
	}
}else if(state == 'Y'){
	state_name = '智能审查拦截，药师审查通过';
	if(record.is_sure == 1){
		state_name = state_name + '，【医生已查看】';
	}
}else if(state == 'N'){
	state_name = '智能审查拦截，药师审查不通过';
	if(record.is_sure == 1){
		state_name = state_name + '，【医生已查看】';
	}
}else if(state == 'DS'){
	var flag = record.order_flag;
	if(flag == '-1'){
		state_name = '智能审查拦截，医生强制使用，药师审查超时，系统返回医生决定，医生确认用药';
	}else{
		state_name = '智能审查拦截，药师审查：医生决定，医生确认用药';
	}
}else if(state == 'DN'){
	var flag = record.order_flag;
	if(flag == '-1'){
		state_name = '智能审查拦截，医生强制使用，药师审查超时，系统返回医生决定，医生取消用药';
	}else{
		state_name = '智能审查拦截，药师审查：医生决定，医生取消用药';
	}
}else if(state == 'HL_T'){
	state_name = '智能审查提示(通过)';
}else if(state == 'D'){
	var flag = record.order_flag;
	if(flag == '-1'){
		state_name = '<font style="color: #747f00">智能审查拦截，医生强制使用，药师审查超时，系统自动返回医生决定</font>';
}else{
	state_name = '<font style="color: #747f00">智能审查拦截，药师审查：医生决定，等待医生决策</font>';
	}
}else if(state == 'DQ'){
	state_name = '<font style="color: #bd850a">智能审查拦截，医生强制使用，等待药师审查</font>';
}else if(state == 'DQ1'){
	state_name = '智能审查拦截，医生强制使用，药师非工作时间系统自动通过';
}else if(state == 'is_overtime'){
	state_name = '<font style="color: #bd850a">智能审查拦截，药师审查超时</font>';
}else{
	state_name = '<font style="color: red">发生未知问题</font>';
}
return state_name;
</s:table.field>
<s:table.field name="caozuo" label="详细信息" datatype="script">
	var fhtml = [] ;
 	var state = record.state;
 	if(state == 'HL_Y'){
 		return '√' + ' | <a href="javascript:void(0)" onclick="seeCInfo(\''+ record.common_id +'\')">日志</a>';
 	}else{
 		fhtml.push('<a href="javascript:void(0)" onclick="auditDetail(\''+ record.common_id +'\')">详情</a>');
 		fhtml.push(' | <a href="javascript:void(0)" onclick="seeCInfo(\''+ record.common_id +'\')">日志</a>');
 	}
 	return fhtml.join('');
</s:table.field>
</s:table.fields>
</s:table>
</s:row>
</s:page>
<script>
function test() {
	$.call('ipc.test.hyt_audit_test',{},function(){
		query();
	})
}

function query(){
	//var qdata=$("#form").getData();
	var qdata={};
	$("#datatable").params(qdata);
	$("#datatable").refresh();
}

function auditDetail(common_id){
	  $.modal("/w/hospital_common/showturns/show.html?id="+ common_id,"查看审查详情",{
  	width: '936px',
  	height: '604px',
      callback : function(e){}
  });
}

function seeCInfo(common_id){
	$.modal('/w/hospital_common/auditrecord/info.html',"查看审查日志",{
	  	width: '950px',
	  	height: '600px',
	  	id: common_id,
	    callback : function(e){}
	});
}
</script>