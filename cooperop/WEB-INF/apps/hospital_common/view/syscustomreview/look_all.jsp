<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="系统自定义快速查看" >
	<s:row>
		<s:form border="0" id="form" label="快速查找">
			<s:row>
				<s:row>
				<s:textfield label="关键字" name="filter" placeholder="请输入药品编号或药品名称或药品首字母码" cols="2" ></s:textfield>
				<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
				<s:button label="导出" icon="fa fa-random" action="toexcel"></s:button>
				<%-- <s:textfield label="操作人" name="patient" placeholder="请输入姓名" cols="1"></s:textfield>
				<s:datefield label="操作时间" name="mintime" format="yyyy-MM-dd HH:mm:ss" cols="1"  autocomplete="off"></s:datefield>
				<s:datefield label="至" name="maxtime" format="yyyy-MM-dd HH:mm:ss" cols="1"  autocomplete="off"></s:datefield> --%>
				</s:row>
				<s:row>
					<s:checkbox label="已设置" name="hasset" cols="3" style="border:none">
					<s:option label="配伍问题" value="syssfpeiw"></s:option>
					<s:option label="溶媒问题" value="syssfrongm"></s:option>
					<s:option label="用量问题" value="syssfyongl"></s:option>
					<s:option label="用法问题" value="syssfroute"></s:option>
					<s:option label="频率问题" value="syssfpl"></s:option>
					<s:option label="肌酐清除率" value="syssfccr"></s:option>
					<s:option label="禁忌症问题" value="syssfjjz"></s:option>
					<s:option label="适应症问题" value="syssfsyz"></s:option>
					<s:option label="相互作用问题" value="syssfxhzy"></s:option>
				</s:checkbox>
				</s:row>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="系统自定义内容" autoload="false" action="hospital_common.sysdictdrug.queryZdyAll" sort="true"  limit="10">
			<s:table.fields >
				<s:table.field name="drug_code" label="药品编号" ></s:table.field>
				<s:table.field name="drug_name" label="药品名称" ></s:table.field>
				<s:table.field name="druggg" label="药品规格" datatype="" ></s:table.field>
			    <s:table.field name="drug_unit" label="单位" datatype="" ></s:table.field>
				<s:table.field name="shengccj" label="生产厂家" datatype="" ></s:table.field>
				<s:table.field name="nwarn_message" label="警示消息" width="300"></s:table.field>
				<s:table.field name="nianl" label="年龄范围" datatype="script" >
					var nianl_start = record.nianl_start;
					var nianl_end = record.nianl_end;
					var nianl_unit = record.nianl_unit;
					if(nianl_start || nianl_end){
						return nianl_start + '-' + nianl_end + nianl_unit;
					}
					return '';
				</s:table.field>
				<s:table.field name="weight" label="体重范围" datatype="script" >
					var weight_start = record.weight_start;
					var weight_end = record.weight_end;
					var weight_unit = record.weight_unit;
					if(weight_start || weight_end){
						return weight_start + '-' + weight_end + weight_unit;
					}
					return '';
					return record.weight_start + '-' + record.weight_end + record.weight_unit
				</s:table.field>
				<s:table.field name="routename" label="给药方式" datatype="" ></s:table.field>
				<s:table.field name="tiaojian" label="条件" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="formul_name" label="公式" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="sort_name" label="问题分类" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="问题等级" ></s:table.field>
				<%-- <s:table.field name="zdy_cz" label="操作人" ></s:table.field> --%>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="">
					<a href="javascript:void(0)" onclick="update('$[id]','$[table_name]','$[drug_code]')">修改</a>
					<!-- <a href="javascript:void(0)" onclick="Delete('$[id]','$[table_name]')">删除</a> -->
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		$("[name='hasset']").setData(['syssfpeiw','syssfrongm','syssfyongl',
			'syssfroute','syssfpl',
			'syssfccr','syssfjjz','syssfsyz','syssfxhzy']);
		query();
	});

	function query(){
		var qdata=$("#form").getData();
		$("#datatable").params(qdata);
		//console.log(qdata);
		start = $("#datatable").dataTable().fnSettings()._iDisplayStart; 
		total = $("#datatable").dataTable().fnSettings().fnRecordsDisplay();
		var p = $("#datatable").DataTable().page();
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
	
	function update(id,s,drug_code){
		$.modal("/w/hospital_common/syscustomreview/"+s+"/datiledit.html","修改",{
			width:"70%",
			height:"90%",
			id:id,
			drug_code:drug_code,
			callback : function(e){
				query();
				var wt = '';
				if(s == 'syssfpeiw'){
					wt = '配伍问题'
				}
				if(s == 'syssfrongm'){
					wt = '溶媒问题'
				}
				if(s == 'syssfyongl'){
					wt = '用量问题'
				}
				if(s == 'syssfroute'){
					wt = '用药问题'
				}
				if(s == 'syssfpl'){
					wt = '频率问题'
				}
				if(s == 'syssfccr'){
					wt = '肌酐清除率'
				}
				if(s == 'syssfjjz'){
					wt = '禁忌症问题'
				}
				if(s == 'syssfsyz'){
					wt = '适应症问题'
				}
				if(s == 'syssfxhzy'){
					wt = '相互作用问题'
				}
				/* $.call("hospital_common.dictdrug.updateCz", 
						{drug_code: '${param.spbh}', zdy_cz: "修改"+wt}, function(){}); */
			}
		});
	}
	
	function Delete(id,s){
		$.confirm("是否确认删除，删除后无法恢复！",function callback(e){
			if(e==true){
				$.call("hospital_common.syscustomreview."+s+".delete",{"id":id},function(e){
					query();
					var wt = '';
					if(s == 'syssfpeiw'){
						wt = '配伍问题'
					}
					if(s == 'syssfrongm'){
						wt = '溶媒问题'
					}
					if(s == 'syssfyongl'){
						wt = '用量问题'
					}
					if(s == 'syssfroute'){
						wt = '用药问题'
					}
					if(s == 'syssfpl'){
						wt = '频率问题'
					}
					if(s == 'syssfauditpass'){
						wt = '审查通过'
					}
					if(s == 'syssfccr'){
						wt = '肌酐清除率'
					}
					if(s == 'syssfjjz'){
						wt = '禁忌症问题'
					}
					if(s == 'syssfsyz'){
						wt = '适应症问题'
					}
					if(s == 'syssfxhzy'){
						wt = '相互作用问题'
					}
					/* $.call("hospital_common.dictdrug.updateCz", 
							{drug_code: '${param.spbh}', zdy_cz: "修改"+wt}, function(){}); */
		    	}); 
			}
		});
	}
</script>