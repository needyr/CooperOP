<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="his与sys字典匹配管理">
	<s:row>
	<s:tabpanel >
		<s:table id="tableout" label="未匹配的字典内容" sort="true" action="hospital_common.dict.dictall.queryis" active="true">
			<s:table.fields>
			 	<c:forEach items="${fields}" var="fz" >
			 		<c:if test="${fz.column_name!='P_KEY'&&fz.column_name!='SYS_P_KEY' && fz.column_name!='PKEY_ID'}">
			 				<s:table.field name="${fn:toLowerCase(fz.column_name)}" label="${fz.column_description}" sort="true"></s:table.field>
			 		</c:if>
				</c:forEach>
				<s:table.field name="sysname" label="[标准库]-${clnamecn}" sort="false"></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="50">
					 <a href="javascript:void(0)" onclick="update('$[p_key]','$[sysname]', '$[hisname]', this)">匹配</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	
		<s:table id="tablein" label="已经匹配的字典内容" sort="true" action="hospital_common.dict.dictall.querynot" >
			<s:table.fields>
			 	<c:forEach items="${fields}" var="fz">
			 		<c:if test="${fz.column_name!='P_KEY' && fz.column_name!='SYS_P_KEY' && fz.column_name!='PKEY_ID'}">
			 				<s:table.field name="${fn:toLowerCase(fz.column_name)}" label="${fz.column_description}" sort="true"></s:table.field>
			 		</c:if>
				</c:forEach>
				<s:table.field name="sysname" style="color: red;" label="[标准库]-${clnamecn}" sort="false"></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="60">
					 <a href="javascript:void(0)" onclick="update('$[p_key]','$[sysname]', '$[hisname]')">修改匹配</a>
				</s:table.field>
			</s:table.fields>
		</s:table>  
	</s:tabpanel>
	</s:row>
</s:page>
<script type="text/javascript">
	$(document).ready(function(){
		query();
	});
	
	function query(){
		var data = {
			histname: '${histname}',
			systname: '${systname}',
			clname: '${clname}'
		};
		
		$("#tablein").params(data);
		start = $("#tablein").dataTable().fnSettings()._iDisplayStart; 
		total = $("#tablein").dataTable().fnSettings().fnRecordsDisplay();
		var p = $("#tablein").DataTable().page();
		if((total-start)==1){
			if (start > 0) {
				$("#tablein").refresh_table(p-1);
			}else{
				$("#tablein").refresh();
			}
		}else{
			$("#tablein").refresh_table(p);
		}
		$("#tableout").params(data);
		start = $("#tableout").dataTable().fnSettings()._iDisplayStart; 
		total = $("#tableout").dataTable().fnSettings().fnRecordsDisplay();
		var p = $("#tableout").DataTable().page();
		if((total-start)==1){
			if (start > 0) {
				$("#tableout").refresh_table(p-1);
			}else{
				$("#tableout").refresh();
			}
		}else{
			$("#tableout").refresh_table(p);
		}
	}
	
	function update(p_key, sysname, hisname, _this){
		$(_this).css('color', '#ab00ff');
		 /* if('${param.tname}'==null||'${param.tname}'==''){
			 var tname = 'dict_his_bill';
			 var clname='bill_name';
		 }else{
			 var tname = '${param.tname}';
			 var clname ='${param.clname}';
		 } */
		$.modal('edit.html','修改匹配',{
			width:"600px",
			height:"500px",
			tablename: '${histname}',
			systname: '${systname}',
			p_key: p_key,
			sysname: sysname,
			clname: '${clname}',
			hisname: hisname,
			callback:function(rtn){
				if(rtn == 1){
					query();
				}
			}
		})
	}
	
	/* function clearMap(){
		var tab = '${param.tname}';
		
	} */
</script>