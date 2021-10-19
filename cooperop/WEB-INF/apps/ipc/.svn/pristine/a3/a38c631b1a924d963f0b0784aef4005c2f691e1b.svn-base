<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="点评历史记录查询" disloggedin="false">
	<s:row>
		<s:table id="datatable" label="点评历史" icon="fa fa-list" autoload="true"
			sort="true" limit="10" fitwidth="true" action="ipc.comment.querySample">
			<s:table.fields>
				<s:table.field name="id" datatype="string" label="抽样ID" ></s:table.field>
				<s:table.field name="create_user" datatype="string" label="抽样人"></s:table.field>
				<s:table.field name="comment_finish_time" datatype="string" label="点评完成时间"></s:table.field>
				<s:table.field name="d_type" datatype="script" label="样本来源" sort="true">
					if(record.d_type == '1'){
						return '住院';
					}else if(record.d_type == '3'){
						return '急诊';
					}else if(record.d_type == '2'){
						return '门诊';
					}
				</s:table.field>
				<s:table.field name="sample_num" datatype="string" label="抽样数量"></s:table.field>
				<%-- <s:table.field name="comment_num" datatype="string" label="已点评数量"></s:table.field> --%>
				<s:table.field name="state" datatype="script" label="点评状态" sort="true" defaultsort="asc">
					if(record.state == '0' ){
						return '<font>未点评</font>';
					}else if(record.state == '1'){
						return '<font color="orange">点评中...</font>';
					}else if(record.state == '2'){
						return '<font color="#10e349">点评完成</font>';
					}else if(record.state == '-1'){
						return '<font color="#a16600">转交，重新分配</font>';
					}
				</s:table.field>
				<s:table.field name="time" datatype="script" format="yyyy-MM-dd" label="抽样时间条件">
					if(record.sample_start_time && !record.sample_end_time){
						return record.sample_start_time+"~现在";
					}else if(record.sample_end_time && !record.sample_start_time){
						return "现在~"+record.sample_end_time;
					}else if(record.sample_end_time && record.sample_start_time){
						return record.sample_start_time+"~"+record.sample_end_time;
					}else{
						return "";
					}
				</s:table.field>
				<s:table.field name="sample_type" datatype="script" label="抽样方式" >
					if(record.sample_type == '1'){
						return '人工抽样';
					}else if(record.sample_type == '2'){
						return '随机抽样';
					}
				</s:table.field>
				<s:table.field name="dept_name" datatype="string" label="抽样科室" ></s:table.field>
				<s:table.field name="fp_name" datatype="string" label="分配处理人" ></s:table.field>
				<s:table.field name="change" label="抽样操作" datatype="script">
					var comment_flag = record.comment_flag;
					var html = [];
					if(comment_flag != '2' && record.state != '-1'){
						html.push('<a href="javascript:void(0)" onclick="edit(');
						html.push('\''+record.sample_start_time+'\',');
						html.push('\''+record.sample_end_time+'\',');
						html.push('\''+record.d_type+'\',');
						html.push('\''+record.sample_patient_idorname+'\',');
						html.push('\''+record.p_type+'\',');
						html.push('\''+record.id+'\')">');
						html.push('修改  </a>');
						html.push('<a href="javascript:void(0)" onclick="remove(');
						html.push('\''+record.id+'\')">');
						html.push('移除</a>');
					}else if(comment_flag == '2'){
						html.push('<a href="javascript:void(0)" onclick="lookResult(');
						html.push('\''+record.comment_user+'\',');
						html.push('\''+record.id+'\')">');
						html.push('点评结果</a>');
					}
					return html.join('');
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		query();
	});
	
	function lookResult(comment_user,sample_id){
		$.modal("pats_history.html","查看结果",{
			width:'90%',
			height:'90%',
			sample_id: sample_id,
			comment_user: comment_user,
			callback : function(e){
				query();
			}
		});
	}
	
	function query(){
		$("#datatable").refresh();
	}
	
</script>