<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="自定义快速查看" >
	<s:row>
		<s:form border="0" id="form" label="快速查找">
			<s:row>
				<s:row>
				<s:textfield label="关键字" name="filter" placeholder="请输入药品" cols="2" ></s:textfield>
				<s:textfield label="操作人" name="patient" placeholder="请输入姓名" cols="1"></s:textfield>
				<s:datefield label="操作时间" name="mintime" format="yyyy-MM-dd HH:mm:ss" cols="1"  autocomplete="off"></s:datefield>
				<s:datefield label="至" name="maxtime" format="yyyy-MM-dd HH:mm:ss" cols="1"  autocomplete="off"></s:datefield>
				</s:row>
				<s:row>
					<s:checkbox label="已设置" name="hasset" cols="3" style="border:none">
					<s:option label="配伍问题" value="sfpeiw"></s:option>
					<s:option label="溶媒问题" value="sfrongm"></s:option>
					<s:option label="用量问题" value="sfyongl"></s:option>
					<s:option label="儿童用量问题" value="sfyonglchild"></s:option>
					<s:option label="用法问题" value="sfroute"></s:option>
					<s:option label="频率问题" value="sfpl"></s:option>
					<s:option label="审查通过" value="sfauditpass"></s:option>
					<s:option label="肌酐清除率" value="sfccr"></s:option>
					<s:option label="禁忌症问题" value="sfjjz"></s:option>
					<s:option label="适应症问题" value="sfsyz"></s:option>
					<s:option label="相互作用问题" value="sfxhzy"></s:option>
					<s:option label="相互作用问题" value="sflabtest"></s:option>
				</s:checkbox>
				</s:row>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="自定义内容" autoload="false" action="hospital_common.dictdrug.queryZdyAll" sort="true"  limit="10">
			<s:toolbar>
			<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="drug_code" label="药品编号" ></s:table.field>
				<s:table.field name="drug_name" label="药品名称" ></s:table.field>
				<s:table.field name="druggg" label="药品规格" datatype="" ></s:table.field>
			    <s:table.field name="drug_unit" label="单位" datatype="" ></s:table.field>
				<s:table.field name="shengccj" label="生产厂家" datatype="" ></s:table.field>
				<s:table.field name="nwarn_message" label="警示消息" width="300"></s:table.field>
				<s:table.field name="tiaojian" label="条件" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="formul_name" label="公式" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="sort_name" label="问题分类" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="问题等级" ></s:table.field>
				<s:table.field name="zdy_cz" label="操作人" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','$[table_name]','$[drug_code]')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','$[table_name]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		$("[name='hasset']").setData(['sfpeiw','sfrongm','sfyongl',
			'sfroute','sfpl','sfauditpass',
			'sfccr','sfjjz','sfsyz','sfxhzy','sfyonglchild','sflabtest']);
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
	
	function update(id,s ,spbh){
		$.modal("/w/hospital_common/customreview/"+s+"/datiledit.html","修改",{
			width:"70%",
			height:"90%",
			id:id,
			spbh:spbh,
			callback : function(e){
				query();
				var wt = '';
				if(s == 'sfpeiw'){
					wt = '配伍问题'
				}
				if(s == 'sfrongm'){
					wt = '溶媒问题'
				}
				if(s == 'sfyongl'){
					wt = '用量问题'
				}
				if(s == 'sfroute'){
					wt = '用药问题'
				}
				if(s == 'sfpl'){
					wt = '频率问题'
				}
				if(s == 'sfauditpass'){
					wt = '审查通过'
				}
				if(s == 'sfccr'){
					wt = '肌酐清除率'
				}
				if(s == 'sfjjz'){
					wt = '禁忌症问题'
				}
				if(s == 'sfsyz'){
					wt = '适应症问题'
				}
				if(s == 'sfxhzy'){
					wt = '相互作用问题'
				}
				if(s == 'sfyonglchild'){
					wt = '儿童用量问题'
				}
				if(s == 'sflabtest'){
					wt = '检验问题'
				}
				$.call("hospital_common.dictdrug.updateCz", 
						{drug_code: '${param.spbh}', zdy_cz: "修改"+wt}, function(){});
			}
		});
	}
	
	function Delete(id,s){
		$.confirm("是否确认删除，删除后无法恢复！",function callback(e){
			if(e==true){
				$.call("hospital_common.customreview."+s+".delete",{"id":id},function(e){
					query();
					var wt = '';
					if(s == 'sfpeiw'){
						wt = '配伍问题'
					}
					if(s == 'sfrongm'){
						wt = '溶媒问题'
					}
					if(s == 'sfyongl'){
						wt = '用量问题'
					}
					if(s == 'sfroute'){
						wt = '用药问题'
					}
					if(s == 'sfpl'){
						wt = '频率问题'
					}
					if(s == 'sfauditpass'){
						wt = '审查通过'
					}
					if(s == 'sfccr'){
						wt = '肌酐清除率'
					}
					if(s == 'sfjjz'){
						wt = '禁忌症问题'
					}
					if(s == 'sfsyz'){
						wt = '适应症问题'
					}
					if(s == 'sfxhzy'){
						wt = '相互作用问题'
					}
					if(s == 'sfyonglchild'){
						wt = '儿童用量问题'
					}
					if(s == 'sflabtest'){
						wt = '检验问题'
					}
					$.call("hospital_common.dictdrug.updateCz", 
							{drug_code: '${param.spbh}', zdy_cz: "修改"+wt}, function(){});
		    	}); 
			}
		});
	}
</script>