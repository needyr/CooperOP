<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<style type="text/css">
	.xz{
		background-color: #bfbfbf !important;
	}
</style>
<s:page title="属性维护" >
<s:form border="0" id="form" >
	<s:row>
		<s:textfield label="类别名"  id="classname" cols="1"></s:textfield>
		<s:button label="新增类别" onclick="addClass()"></s:button>
	</s:row>
		
</s:form>
<div style="height:5px;">
</div>
<div style="width:50%;float:left;" id="div01">
	<s:tabpanel id="ccc">
	 <c:forEach items="${list }" var="s" varStatus="xb">
	
	<c:if test="${xb.index==0 }">
		<s:table id="${s.class_code }"  label="${s.class_name }" autoload="true" action="hospital_common.tpn.xiangmuwh.getXm" sort="true"  height="400" limit="10" active="true">
			<s:toolbar>
				<s:button label="删除类别" icon="" onclick="deleteClass('${s.class_code }')"></s:button>
				<s:button label="新增属性" icon="" onclick="addXm('${s.class_name }','${s.class_code }')"></s:button>
				<s:button label="重新载入TPN审查规则" style="background: #fff5c1;" icon="" onclick="init_tpn();"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="xmid" label="项目编码" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="xmdw" label="项目单位" ></s:table.field>
				<s:table.field name="fdtype" label="浮点类型" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="70">
					<a href="javascript:void(0)" onclick="deleteXm('$[xmmch]','${s.class_code }','$[xmid]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	 </c:if>
	 <c:if test="${xb.index !=0 }">
		<s:table id="${s.class_code }"   label="${s.class_name }" autoload="true" action="hospital_common.tpn.xiangmuwh.getXm" sort="true"  height="400" limit="10" onclick="getXm('${s.class_code }',this)">
			<s:toolbar>
				<s:button label="删除类别" icon="" onclick="deleteClass('${s.class_code }')"></s:button>
				<s:button label="新增属性" icon="" onclick="addXm('${s.class_name }','${s.class_code }')"></s:button>
				<s:button label="重新载入TPN审查规则" style="background: #fff5c1;" icon="" onclick="init_tpn();"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="xmid" label="项目编码" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="xmdw" label="项目单位" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="70">
					<a href="javascript:void(0)" onclick="deleteXm('$[xmmch]','${s.class_code }','$[xmid]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	 </c:if>
	
 	</c:forEach>
	</s:tabpanel>
	</div>
	<div style="width:50%;float:left">
		<s:table id="value01" autoload="false" action="hospital_common.tpn.xiangmuwh.getXmValue" height="400">
			<s:toolbar>
				<s:button label="新增属性值" icon="" onclick="addXmValue();"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="value" label="项目值" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="70">
					<a href="javascript:void(0)" onclick="deleteXmValue('$[xmmch]','$[id]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</div>
	<input id="xmid" hidden></input>
	<input id="xmmch" hidden></input>
</s:page>
<script type="text/javascript">
	
	function getXm(tid,obj){
		if($(obj).hasClass("active")){	
		var data={"class_code":tid}
		$("#"+tid).params(data);
		start = $("#"+tid).dataTable().fnSettings()._iDisplayStart; 
		total = $("#"+tid).dataTable().fnSettings().fnRecordsDisplay();
		var p = $("#"+tid).DataTable().page();
		if((total-start)==1){
			if (start > 0) {
				$("#"+tid).refresh_table(p-1);
			}else{
				$("#"+tid).refresh();
			}
		}else{
			$("#"+tid).refresh_table(p);
		}
		}
	}
	
	$(function(){
		$('#div01 table').on('click', 'tbody tr', function(e) { 
			var td=$(this).children("td").get(0);
			var val=td.innerText;
			$(this).addClass("xz").siblings().removeClass("xz");
			$("#xmid").val(val);
			$("#xmmch").val($(this).children("td").get(1).innerText);
			var xmid={"xmid": val};
			$("#value01").params(xmid);
			start = $("#value01").dataTable().fnSettings()._iDisplayStart; 
			total = $("#value01").dataTable().fnSettings().fnRecordsDisplay();
			var p = $("#value01").DataTable().page();
			if((total-start)==1){
				if (start > 0) {
					$("#value01").refresh_table(p-1);
				}else{
					$("#value01").refresh();
				}
			}else{
				$("#value01").refresh_table(p);
			}
		});
		$(".portlet").css("padding","0px");
		$(".row-fluid").css("padding","5px");
	})
	
	function deleteXm(xmmch,tid, xmid){
		console.dir(xmmch+tid);
		$.call("hospital_common.tpn.xiangmuwh.deleteXm", {"xmmch": xmmch,"xmid":xmid}, function(rtn){
			if(rtn>=1){
				alert("删除成功");
			}else{
				alert("删除失败");
			}
			$("#"+tid).refresh();
		});
	}
	
	function deleteXmValue(xmmch,id){
		console.dir(xmmch+id);
		$.call("hospital_common.tpn.xiangmuwh.deleteXmValue", {"id": id}, function(rtn){
			if(rtn>=1){
				alert("删除成功");
			}else{
				alert("删除失败");
			}
			$("#value01").refresh();
		});
	}
	
	function addXmValue(){
		if(!($("#xmid").val())){
			alert("请先选择项目");
			return;
		}
		var xmid = $("#xmid").val();
		var xmmch = $("#xmmch").val();
		$.modal("xmvalue.html","新增项目值",{
			width:"30%",
			height:"70%",
			xmmch:xmmch,
			xmid:xmid,
			callback : function(e){
				if(e){
					$("#value01").refresh();
				}
			}
		});
	}
	
	function addXm(name,code){
		console.dir(name);
		$.modal("addxm.html","新增"+name+"类别的项目",{
			width:"30%",
			height:"70%",
			class_name:name,
			class_code:code,
			callback : function(e){
				if(e){
					$("#"+code).refresh();
				}
			}
		});
	}
	
	function addClass(){
		var classname = $("#classname").val();
		if(classname){
			$.call("hospital_common.tpn.xiangmuwh.addClass", {"classname": classname}, function(rtn){
				if(rtn>=1){
					alert("新增成功");
					window.location.reload();
				}else{
					alert("新增失败，可能是数据库的原因");
				}
			});
		}else{
			alert("请先输入类别名");
		}
	}
	function deleteClass(code){
		$.call("hospital_common.tpn.xiangmuwh.deleteClass", {"code": code}, function(rtn){
			if(rtn>=1){
				alert("删除成功");
				window.location.reload();
			}else{
				alert("删除失败，可能是数据库的原因");
			}
		});
	}
	
	function init_tpn(){
		$.confirm("确认重新载入TPN审查规则吗?",function callback(e){
            if(e==true){
            	$.call('hospital_common.tpn.xiangmuwh.init_tpn',{},function(){})
            }
        });
	}
</script>