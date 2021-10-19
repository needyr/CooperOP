<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="说明书显示项配置" >
	<%-- <s:row>
		<s:form border="0" id="form" label="快速查找">
			<s:row>
				<s:textfield label="快速查找" name="filter" placeholder="请输入字段名称" cols="2"></s:textfield>
				<s:button label="查询" icon="" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row> --%>
	<s:row>
		<s:table id="datatable" label="说明书显示项配置" autoload="false" action="hospital_common.instructionconfig.query" sort="true" limit="">
			<s:table.fields>
				 <s:table.field name="column_name" label="字段" datatype="" hidden="true"></s:table.field> 
				<s:table.field name="column_name_cn" label="字段名" datatype="" ></s:table.field>
				 <s:table.field name="sms_sort_id" label="说明书排序值" datatype="" ></s:table.field> 
				<s:table.field name="sms_is_show" label="说明书显示" datatype="script" >
					switch (+record.sms_is_show) {
						case 1:
							return '<span style="color:green">显示</span>';
						case 0:
							return '<span style="color:#8e95a7">隐藏</span>';
					}
				</s:table.field>
				<!-- <a href="javascript:void(0)" data-column='$[column_name]' onclick="moveup('$[column_name]',this)" class="moveup">上移</a>
					<a href="javascript:void(0)" data-column='$[column_name]' onclick="movedown('$[column_name]',this)" class="movedown">下移</a> -->
				<s:table.field name="caozuo" label="操作" datatype="script" >
					var html = [];
					html.push('<a data-column="'+record.column_name+'" onclick="moveup(\''+record.column_name+'\',this)" class="moveup">上移</a>　　');
					html.push('<a data-column="'+record.column_name+'" onclick="movedown(\''+record.column_name+'\',this)" class="movedown">下移</a>　　');
					html.push('<a data-column="'+record.column_name+'" onclick="changeState(\''+record.column_name+'\','+record.sms_is_show+')">');
					html.push(record.sms_is_show==1?"隐藏":"显示")
					html.push('</a>');
					return html.join("");
				</s:table.field>
			</s:table.fields>
		</s:table>
		</s:row>
		
</s:page>
<script type="text/javascript">
	$(function(){
		query();
		
	});

	function query(){
		var qdata=$("#form").getData();
		$("#datatable").params(qdata);
		$("#datatable").refresh();
	}
	
	function update(column_name){
		$.modal("edit.html","修改显示项",{
			width:"60%",
			height:"80%",
			column_name:column_name,
			callback : function(e){
				query();
			}
		});
	}
	
	
	function moveup(column_name, _this){
		var last_column_name = $(_this).parents("tr").prev().find(".movedown").attr("data-column");
		if(last_column_name){
			$.call("hospital_common.instructionconfig.moveUp",{"column_name":column_name,"last_column_name":last_column_name},function(rtn){
				query();
			},function(e){}, {async: false, remark: false   });
		}else{
			$.message("当前已处于首行！");
		} 
		 
	}
	
	function movedown(column_name, _this){
		var next_column_name = $(_this).parents("tr").next().find(".moveup").attr("data-column");
		if(next_column_name){
			$.call("hospital_common.instructionconfig.moveDown",{"column_name":column_name,"next_column_name":next_column_name},function(rtn){
				query();
			},function(e){}, {async: false, remark: false   }); 
		}else{
			$.message("当前已处于末行！");
		}
	    
	}
	
	function changeState(column_name,state){
		var is_show;
		if(state==1){
			is_show=0;
		}else{
			is_show=1;
		}
		 $.call("hospital_common.instructionconfig.updateShow",{"column_name":column_name,"sms_is_show":is_show},function(rtn){
				query();
		 },function(e){}, {async: false, remark: false   }); 
	}
	
</script>