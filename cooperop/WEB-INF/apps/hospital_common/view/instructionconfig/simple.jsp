<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="简要信息显示项配置" >
	<s:row>
		<s:table id="datatable" label="简要信息显示项配置" autoload="false" action="hospital_common.instructionconfig.queryBrief" sort="true" limit="0">
			<s:table.fields>
				 <s:table.field name="column_name" label="字段" datatype="" hidden="true"></s:table.field> 
				 <s:table.field name="column_name_cn" label="字段名" datatype="" ></s:table.field>
				 <s:table.field name="brief_sort_id" label="简要信息排序值" datatype="" ></s:table.field> 
				<s:table.field name="brief_is_show" label="简要信息显示" datatype="script" >
					switch (+record.brief_is_show) {
						case 1:
							return '<span style="color:green">显示</span>';
						case 0:
							return '<span style="color:#8e95a7">隐藏</span>';
					}
				</s:table.field>
				<!-- <a href="javascript:void(0)" data-column='$[column_name]' onclick="moveup('$[column_name]',this)" class="moveup">上移</a>
					<a href="javascript:void(0)" data-column='$[column_name]' onclick="movedown('$[column_name]',this)" class="movedown">下移</a> -->
				<%-- <s:table.field name="brief_title" label="显示标题" datatype="" /> --%>
				<s:table.field name="brief_title" label="显示标题"  datatype="template">
					<s:textfield name="brief_title" value="$[brief_title]" onchange="updateTitle(this,'$[column_name]')"></s:textfield>
				</s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="script" >
					
					
					var html = [];
					html.push('<a data-column="'+record.column_name+'" onclick="moveup(\''+record.column_name+'\',this)" class="moveup">上移</a>　　');
					html.push('<a data-column="'+record.column_name+'" onclick="movedown(\''+record.column_name+'\',this)" class="movedown">下移</a>　　');
					html.push('<a data-column="'+record.column_name+'" onclick="changeState(\''+record.column_name+'\','+record.brief_is_show+')">');
					html.push(record.brief_is_show==1?"隐藏":"显示")
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
	
	function updateTitle(_this,column_name){
		var brief_title=$(_this).val();
		$.call("hospital_common.instructionconfig.updateTitle",{"brief_title":brief_title,"column_name":column_name},function(rtn){
			query();
		},function(e){}, {async: false, remark: false  });
	}

	function query(){
		var qdata=$("#form").getData();
		$("#datatable").params(qdata);
		$("#datatable").refresh();
	}

	function moveup(column_name, _this){
		var last_column_name = $(_this).parents("tr").prev().find(".movedown").attr("data-column");
		if(last_column_name){
			$.call("hospital_common.instructionconfig.moveUpBrief",
					{
						"column_name":column_name,
						"last_column_name":last_column_name
					},
					function(rtn){
						query();
					},
			 		function(e){},
			 		{
			 		   async: false,
			 		   remark: false
			 		}
			); 
		}else{
			$.message("当前已处于首行！");
		}
		
	}
	
	function movedown(column_name, _this){
		var next_column_name = $(_this).parents("tr").next().find(".moveup").attr("data-column");
		if(next_column_name){
			$.call("hospital_common.instructionconfig.moveDownBrief",
				{
					"column_name":column_name,
					"next_column_name":next_column_name
				},
				function(rtn){
				query();
				},
				function(e){
					
				}, 
				{
					async: false, 
				    remark: false  
				}
			); 
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
		 $.call("hospital_common.instructionconfig.updateShowBrief",
				 {
			      "column_name":column_name,
			      "brief_is_show":is_show
			     },
			     function(rtn){
					query();
		 		 },
		 		 function(e){},
		 		 {
		 		   async: false,
		 		   remark: false
		 		 }
		); 
	}
	
</script>