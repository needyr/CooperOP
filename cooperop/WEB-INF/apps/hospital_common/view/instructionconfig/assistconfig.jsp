<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="辅助查询显示项配置" >
	<s:row>
		<s:table id="datatable" label="辅助查询显示项配置" autoload="false" limit="0" action="hospital_common.assistconfig.query" sort="true" >
			<s:table.fields>
				 <s:table.field name="queryname" label="辅助查询名称" datatype="" ></s:table.field> 
				 <s:table.field name="url" label="链接" datatype="template" >
				 	<s:textfield name="url" value="$[url]" onchange="updateUrl(this,'$[id]')"></s:textfield>
				 </s:table.field>
				 <s:table.field name="sort_id" label="辅助查询排序值" datatype="" ></s:table.field> 
				<s:table.field name="is_show" label="辅助查询显示" datatype="script" >
					switch (+record.is_show) {
						case 1:
							return '<span style="color:green">显示</span>';
						case 0:
							return '<span style="color:#8e95a7">隐藏</span>';
					}
				</s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="script" >
					var html = [];
					html.push('<a data-column="'+record.id+'" onclick="moveup(\''+record.id+'\',this)" class="moveup">上移</a>　　');
					html.push('<a data-column="'+record.id+'" onclick="movedown(\''+record.id+'\',this)" class="movedown">下移</a>　　');
					html.push('<a data-column="'+record.id+'" onclick="changeState(\''+record.id+'\','+record.is_show+')">');
					html.push(record.is_show==1?"隐藏":"显示")
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
	
	function updateUrl(_this,id){
		var url=$(_this).val();
		$.call("hospital_common.assistconfig.updateUrl",{"id":id,"url":url},function(rtn){
			query();
		},function(e){}, {async: false, remark: false  });
	}

	function moveup(id, _this){
		var last_id = $(_this).parents("tr").prev().find(".movedown").attr("data-column");
		if(last_id){
			 $.call("hospital_common.assistconfig.moveUp",{"id":id,"last_id":last_id},function(rtn){
				query();
			}); 
		}else{
			$.message("当前已处于首行！");
		}
		
	}
	
	function movedown(id, _this){
		var next_id = $(_this).parents("tr").next().find(".moveup").attr("data-column");
		if(next_id){
			$.call("hospital_common.assistconfig.moveDown",{"id":id,"next_id":next_id},function(rtn){
				query();
			},function(e){}, {async: false, remark: false   }); 
		}else{
			$.message("当前已处于末行！");
		}
	    
	}
	
	function changeState(id,state){
		var is_show;
		if(state==1){
			is_show=0;
		}else{
			is_show=1;
		}
		$.call("hospital_common.assistconfig.updateShow",{"id":id,"is_show":is_show},function(rtn){
				query();
		},function(e){}, {async: false, remark: false   }); 
	}
	
	
	
</script>