<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<style type="text/css">
#importentbu{
	color: #0eabc3;
    background-color: #ffffff;
    border: 1px solid #0eabc3;
}
#importentbu:hover{
	background: #FFFFFF;
    color: #1d808f;
    border: 1px solid #1d808f;
}
#importentbu2{
	color: #9a3c10;
    background-color: #ffffff;
    border: 1px solid #9a3c10;
}
#importentbu2:hover{
	background: #FFFFFF;
    color: #9a7c10;
    border: 1px solid #9a7c10;
}

.tiaojianall{
	display: block;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    width: 250px;
}


</style>
<s:page title="点评抽样集合">
	<s:row>
		<s:form border="0" id="seachform">
			<s:row>
				<s:datefield label="抽样时间" name="mintime" format="yyyy-MM-dd HH:mm:ss" placeholder="请选择日期" cols="1"></s:datefield>
				<s:datefield label="至" name="maxtime" format="yyyy-MM-dd HH:mm:ss" placeholder="请选择日期" cols="1"></s:datefield>
				<s:select cols="1" label="数据来源" name="datasouce" value="all">
					<s:option value="all" label="全部" ></s:option>
					<s:option label="住院" value="1"></s:option>
					<s:option label="急诊" value="3"></s:option>
					<s:option label="门诊" value="2"></s:option>
				</s:select>
				<s:select cols="1" label="病人住院状态" name="patient_state" value="all">
					<s:option value="all" label="全部" ></s:option>
					<s:option label="在院" value="1"></s:option>
					<s:option label="出院" value="2"></s:option>
				</s:select>
				<s:select cols="1" label="医嘱/处方" name="p_typeFifter" value="all">
					<s:option value="all" label="全部" ></s:option>
					<s:option label="医嘱" value="1"></s:option>
					<s:option label="处方" value="2"></s:option>
				</s:select>
				<s:autocomplete action="ipc.sample.querysystem" name="fenpei" 
					 label="分配药师名称" cols="1" limit="10" >
					<s:option value="$[id]" label="$[name]" ></s:option>
				</s:autocomplete>
				<s:radio  label="点评状态" name="dpzt" cols="1" value="all">
					<s:option value="all" label="全部" ></s:option>
					<s:option value="0" label="未点评" ></s:option>
					<s:option value="1" label="点评中..."></s:option>
					<s:option value="2" label="点评完成"></s:option>
					<s:option value="-1" label="转交，重新分配"></s:option>
				</s:radio>
				<s:radio  label="分配点评人" name="fpdpr" cols="1" value="">
					<s:option value="all" label="全部" ></s:option>
					<s:option value="0" label="本人" ></s:option>
				</s:radio>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
	<s:table id="datatable" label="点评抽样结果列表" icon="fa fa-list" autoload="false"
			sort="true" limit="25" fitwidth="true" action="ipc.sample.querysample">
			<s:toolbar>
				<s:button icon="fa fa-search" label="查询" onclick="seach();" id="importentbu"></s:button>
				<s:button label="随机抽样" icon="fa fa-circle-o-notch" onclick="tiaozhuanran();" id="importentbu2"></s:button>
				<s:button label="手动抽样" icon="fa fa-send" onclick="tiaozhuan();" id="importentbu2"></s:button>
				<s:button icon="fa fa-refresh" id="refresh" title="刷新" label="刷新"></s:button>
			</s:toolbar>
			<s:table.fields>
				<%-- <s:table.field name="d_type" datatype="script" label="样本来源">
					if(record.d_type == '1'){
						return '住院';
					}else if(record.d_type == '3'){
						return '急诊';
					}else if(record.d_type == '2'){
						return '门诊';
					}else if(record.d_type == '-1'){
						return '门急诊';
					}
				</s:table.field> --%>
				<s:table.field name="id" datatype="string" label="抽样ID" ></s:table.field>
				<s:table.field name="motif" datatype="string" label="主题" ></s:table.field>
				<s:table.field name="create_user" datatype="string" label="抽样人"></s:table.field>
				<s:table.field name="create_time" datatype="string" label="抽样时间" width="130"></s:table.field>
				<s:table.field name="sample_num" datatype="string" label="抽样数量" ></s:table.field>
				<%-- <s:table.field name="comment_num" datatype="string" label="已点评数量"></s:table.field> --%>
				<s:table.field name="state" datatype="script" label="点评状态">
					if(record.state == '0' ){
						return '<font color="red">未点评</font>';
					}else if(record.state == '1'){
						return '<font color="blue">点评中...</font>';
					}else if(record.state == '2'){
						return '<font">点评完成</font>';
					}else if(record.state == '-1'){
						return '<font color="#a16600">转交，重新分配</font>';
					}
				</s:table.field>
				<s:table.field name="time" datatype="script" label="抽样条件" width="250">
					var result="";
					var results="";
					if(record.doctor_name){
						result = result+"医生："+record.doctor_name+"; ";
					}
					if(record.dept_name){
						result = result+"科室："+record.dept_name+"; ";
					}
					if(record.drug_name){
						result = result+"药品："+record.drug_name+"; ";
					}
					if(record.feibie){
						result = result+"费别："+record.feibie+"; ";
					}
					if(record.unit){
						result = result+"抽样类型："+(record.unit == 1?"按人为单位":record.unit == 2?"按处方为单位":"按医嘱为单位")+"; ";
					}
					if(record.drug_type){
						result = result+"药品类型：全部；";
					}
					if(record.diagnosis_desc){
						result = result+"诊断："+record.diagnosis_desc+"; ";
					}
					return '<div title="result" class="tiaojianall">'+result+'</div>';
				</s:table.field>
				<%-- <s:table.field name="sample_type" datatype="script" label="抽样方式" >
					if(record.sample_type == '1'){
						return '手动抽样';
					}else if(record.sample_type == '2'){
						return '随机抽样';
					}else{
						return '测试抽样';
					}
				</s:table.field> --%>
				<%-- <s:table.field name="caozuo" label="分配" datatype="script" >
					var comment_flag = record.comment_flag;
					var html = [];
					if(comment_flag == '0'){
						html.push('<a href="javascript:void(0)" onclick="get(');
						html.push('\''+record.id+'\')">');
						html.push('分配</a>');
					}else if(comment_flag == '1'){
						html.push('<a href="javascript:void(0)" onclick="get(');
						html.push('\''+record.id+'\')">');
						html.push('已分</a>');
					}else if(comment_flag == '2'){
						html.push('<font>已分配</font>');
					}
					return html.join('');
				</s:table.field>
				<s:table.field name="tijiao" label="提交抽样" datatype="script">
					var comment_flag = record.comment_flag;
					var html = [];
					if(comment_flag == '0'){
						html.push('<font>请分配</font>');
					}else if(record.create_user == userinfo.no && record.state != '2' && record.comment_user_no == record.create_user ){
						html.push('<a href="javascript:void(0)" onclick="directcomment(');
						html.push('\''+record.id+'\')">');
						html.push('点评</a>');
					}else if(comment_flag == '1'){
						html.push('<a href="javascript:void(0)" onclick="tijiao(');
						html.push('\''+record.id+'\')">');
						html.push('提交</a>');
					}else if(comment_flag == '2' && (record.state == '2' || record.state == '1')){
						html.push('<font>已提</font>');
					}
					return html.join('');
				</s:table.field> --%>
				<s:table.field name="fp_name" datatype="string" label="分配点评人" ></s:table.field>
				<s:table.field name="change" label="操作" datatype="script" width="120">
					var comment_flag = record.comment_flag;
					var html = [];
					
					if(comment_flag == '0'){
						html.push('<a href="javascript:void(0)" style="color: red" onclick="get(');
						html.push('\''+(record.sample_num == null ?'0':record.sample_num)+'\',');
						html.push('\''+record.id+'\')">');
						html.push('分配 </a>');
					}else if(comment_flag == '1'){
						html.push('<a href="javascript:void(0)" style="color: #216cf9" onclick="get(');
						html.push('\''+(record.sample_num == null ?'0':record.sample_num)+'\',');
						html.push('\''+record.id+'\')">');
						html.push('分配 </a>');
					}
					
					if(comment_flag == '0'){
						
					}else if(record.create_user == userinfo.no && record.state != '2' && record.comment_user_no == record.create_user ){
						html.push('<a href="javascript:void(0)" style="color: red" onclick="directcomment(');
						html.push('\''+(record.djbh == null ?'':record.djbh)+'\',');
						html.push('\''+record.id+'\')">');
						html.push('点评 </a>');
					}else if(comment_flag == '1'){
						html.push('<a href="javascript:void(0)" style="color: red" onclick="tijiao(');
						html.push('\''+record.id+'\')">');
						html.push('提交 </a>');
					}else if(comment_flag == '2' && (record.state == '2' || record.state == '1')){
						
					}
					
					if(comment_flag != '2' && record.state != '-1' && record.sample_type != '1'){
						html.push('<a href="javascript:void(0)" style="color: #216cf9" onclick="look(');
						html.push('\''+record.sample_start_time+'\',');
						html.push('\''+record.sample_end_time+'\',');
						html.push('\''+record.d_type+'\',');
						html.push('\''+record.sample_patient_idorname+'\',');
						html.push('\''+record.p_type+'\',');
						html.push('\''+record.sample_type+'\',');
						html.push('\''+record.id+'\')">');
						html.push('查看  </a>');
						html.push('<a href="javascript:void(0)" style="color: #216cf9" onclick="remove1(');
						html.push('\''+record.id+'\')">');
						html.push('删除</a>');
					}else if(comment_flag != '2' && record.state != '-1'){
						html.push('<a href="javascript:void(0)" style="color: #216cf9" onclick="edit(');
						html.push('\''+record.sample_start_time+'\',');
						html.push('\''+record.sample_end_time+'\',');
						html.push('\''+record.d_type+'\',');
						html.push('\''+record.sample_patient_idorname+'\',');
						html.push('\''+record.p_type+'\',');
						html.push('\''+record.sample_type+'\',');
						html.push('\''+record.comment_way+'\',');
						html.push('\''+record.id+'\')">');
						html.push('修改  </a>');
						html.push('<a href="javascript:void(0)" style="color: #216cf9" onclick="remove1(');
						html.push('\''+record.id+'\')">');
						html.push('删除</a>');
					}else if(comment_flag == '2'){
						html.push('<a href="javascript:void(0)" style="color: #216cf9" onclick="lookResult(');
						html.push('\''+record.djbh+'\',');
						html.push('\''+record.id+'\')">');
						html.push('查看</a>');
					}
					return html.join('');
				</s:table.field>
			</s:table.fields>
		</s:table>
		</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		var data = $("#seachform").getData();
		$.call("ipc.sample.queryIsOwn",data,function(rtn){
			if( rtn.counts > 0 ){
				$('[name=fpdpr]').setData(['0']);
				autorefresh();
			}else{
				$('[name=fpdpr]').setData(['all']);
				autorefresh();
			}
		});
	});
	
	//自动刷新table
	function autorefresh(){
		query();
		/* setTimeout(function(){
			autorefresh();
		},30000); */
	}
	
	$("#refresh").bind("click",function(){
		query();
	})
	
	$("input[name=fpdpr]").bind("click",function(){
		autorefresh();
	})

	function query(){
		start = $("#datatable").dataTable().fnSettings()._iDisplayStart; 
		total = $("#datatable").dataTable().fnSettings().fnRecordsDisplay();
		var p = $("#datatable").DataTable().page();
		var data = $("#seachform").getData();
		$("#datatable").params(data);
		if((total-start)==1){
			if (start > 0) {
				$("#datatable").refresh_table(p-1);
			}else{
				$("#datatable").refresh();
			}
		}else{
			$("#datatable").refresh_table(p);
		}
	}
	
	function seach(){
		query();
	}
	
	function get(num,id){
		$.modal("distribution.html","点评分配",{
			width:'800px',
			height:'600px',
			id: id,
			num: num,
			callback : function(e){
				query();
			}
		});
	}
	
	function tijiao(id){
		$.call("ipc.sample.updateState",{"id":id},function(rtn){
			if(rtn){
				query();
			}
		})
	}
	function edit(sample_start_time,sample_end_time,d_type,patient,p_type,sample_type,comment_way,sample_id){
		var url = '';
		if(comment_way == '1'){
			url = '/w/ipc/sample/edit.html';
		}else{
			url = '/w/ipc/special_sample/edit.html';
		}
		$.modal(url,"修改抽样",{
			width:'90%',
			height:'90%',
			patient: patient,
			sample_id: sample_id,
			datasouce: d_type,
			mintime: sample_start_time,
			maxtime: sample_end_time,
			p_typeFifter: p_type,
			sample_type: sample_type,
			callback : function(e){
				if(e==2){
					window.location.href="/w/ipc/sample/list.html";
				}
					query();
			}
		});
	}
	
	function remove1(id){
		$.confirm("是否确认移除？",function callback(e){
			if(e==true){
				$.call("ipc.sample.remove",{"sample_id":id},function(rtn){
					if(rtn){
						query();
					}
				})
			}
		})
	}
	
	function lookResult(djbh,sample_id){
		$.modal("/w/ipc/commentflow/show.html","查看结果",{
			width:'90%',
			height:'90%',
			sample_id: sample_id,
			djbh: djbh,
			callback : function(e){
				query();
			}
		});
	}
	
	function tiaozhuan(){
		window.location.href="/w/ipc/sample/sampledetail.html";
	}
	
	function tiaozhuanran(){
		window.location.href="/w/ipc/sample/samplerandom.html";
	}
	
	function directcomment(djbh,id){
		if(djbh != ''){
			window.location.href="/w/application/task/mine.html";
		}else{
			$.call("ipc.sample.updateState",{"id":id},function(rtn){
				if(rtn){
					//query();
					window.location.href="/w/application/task/mine.html";
				}
			})
		}
		/* $.modal("/w/ipc/comment/patients.html","点评",{
			width:'90%',
			height:'90%',
			sample_id: id,
			callback : function(e){
				query();
			}
		}); */
	}
	
	function look(sample_start_time,sample_end_time,d_type,patient,p_type,sample_type,sample_id){
		$.modal("sampleshow.html","抽样查看",{
			width:'90%',
			height:'90%',
			patient: patient,
			sample_id: sample_id,
			datasouce: d_type,
			mintime: sample_start_time,
			maxtime: sample_end_time,
			p_typeFifter: p_type,
			sample_type: sample_type,
			callback : function(e){
				if(e==2){
					window.location.href="/w/ipc/sample/list.html";
				}
					query();
			}
		});
	}
</script>