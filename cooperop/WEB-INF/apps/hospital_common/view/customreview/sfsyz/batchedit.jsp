<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>

<s:page title="编辑适应症问题" ismodal="true">
	<s:row>
		<s:form id="form" label="适应症问题">
			<s:toolbar>
				<s:button label="药品说明书" onclick="getDrugsms()" icon=""></s:button>
				<s:button label="筛选" onclick="showData()" icon=""></s:button>
				<s:button label="保存" onclick="batchSave()" icon="glyphicon glyphicon-floppy-saved"></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<s:datefield label="开始时间" name="start_time" format="yyyy-MM-dd HH:mm:ss" autocomplete="off"></s:datefield>
				<s:datefield label="截止时间" name="end_time" format="yyyy-MM-dd HH:mm:ss" autocomplete="off"></s:datefield>
			</s:row>
			<s:row>
				<div id="lj" style="width:20%;float: left;">是否拦截 <input name="is_n" value="0" type="radio" onclick="setLjInput(this)"></input></div>
				<div id="zdy" style="display: none; width:20%; float: left;">是否自定义 <input name="is_zdy" value="0" type="radio" onclick="setZDYInput(this)"></input></div>
			</s:row> 
			<s:row>
				<%-- <s:textfield label="商品编号" name="spbh" required="true" value="${param.spbh}" cols="1" disabled="disabled"></s:textfield> --%>
				<input name="drug_code" value="${param.spbh}" hidden="true"/>
			</s:row>
		</s:form>
			<s:row>
				<table border="1" id="datatable" style="width: 100%">
				  <tr>
				  	<th>选择</th>
				    <th>诊断代码</th>
				    <th>诊断名称</th>
				    <th>数量</th>
				  </tr>
				<!--   <tr class="table-row">
				  	<td>January</td>
				    <td>$100</td>
				    <td>$100</td>
				    <td>$100</td>
				  </tr> -->
				</table>
			</s:row> 
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		var today ;
		$.call('hospital_common.showturns.onlyGetTime', {}, function(rtn){
			today = rtn.today;
			$('[name = start_time]').val(today + ' 00:00:00');
			$('[name = end_time]').val(today + ' 23:59:59');
		},function(e){}, {async: false, remark: false   });
		showData();
	});
	
	
	function query(){
		var qdata=$("#form").getData();
		$("#datatable").params(qdata);
		$("#datatable").refresh();
	}
	
	//取消
    function cancel(){
    	$.closeModal(false);
    }
	
  //打开药品说明书
	function getDrugsms(){
		var drugcode = '${param.spbh}';
		$.modal("/w/ipc/auditresult/instruction.html?his_drug_code="+drugcode,"查看药品说明书",{
	        width:"70%",
	        height:"100%",
	        callback : function(e){
	        }
		});
		
	};
	
	function showData(){
 		var action="hospital_common.customreview.sfsyz.batchQuery";
 		var qdata=$("#form").getData();
 		console.log();
 		//qdata["is_n"]=$("#zdy input").val();
 		//qdata["is_zdy"]=$("#lj input").val();
  		$.call(action,qdata,function(s){
  			var content="";
  			content+=" <tr>";
  			content+="<th>选择</th>";
  			content+="<th>诊断代码</th>";
		    content+="<th>诊断名称</th>";
		    content+="<th>数量</th>";
		    content+="</tr>";
  			for(var i=0;i<s.resultset.length;i++){
  				content+="<tr class='table-row' onclick='clickData(this)'>";
  				content+="<td><input style='left:20px;' type='checkbox' class='table-row-input'/></td>";
  				content+="<td name='diagnosis_code'>"+s.resultset[i].diagnosis_code+"</td>";
  				content+="<td name='diagnosis_desc'>"+s.resultset[i].diagnosis_desc+"</td>";
  				content+="<td name='counts'>"+s.resultset[i].counts+"</td>";
  				content+="<td class='table-row-td' style='display:none'>0</td>";
  				content+="</tr>";
  			}
  			$("#datatable").html(content);
  		}) 
 	}
	
	function clickData(t){
		var temp=$(t).find(".table-row-td").html();
		if(temp==0){
			$(t).css("background","#b4b4b4");
			$(t).find(".table-row-td").html(1);
			$(t).find(".table-row-input").attr("checked","checked");
		} else{
			$(t).css("background","");
			$(t).find(".table-row-td").html(0);
			$(t).find(".table-row-input").removeAttr("checked");
		}
	}
	
	function batchSave() {
		$.confirm("确认保存？",function callback(e){
			if(e==true){
				var data=[];
				 $("#datatable tr.table-row").each(function (index,obj) {
					 var temp = $(obj).find(".table-row-td").html();
					 if(temp==1){
						 data.push({
							 	diagnosis_code:$(obj).find("td").eq(1).html(),
							 	diagnosis_name:$(obj).find("td").eq(2).html(),
							 	value:$(obj).find("td").eq(1).html(),
							 	//counts:$(obj).find("td").eq(3).html(),
							 	tiaojian:"or",
							 	xmmch:"诊断代码",
							 	xmbh:"050",
								FDNAME:"diagnosis_CODE",
							 	formul:"=",
							 	parent_id:'${param.parent_id}',
							 	spbh: '${param.spbh}'
				            })
					 }
				 })
				 var jsondata=JSON.stringify(data);
				 $.call("hospital_common.customreview.sfsyz.batchSave", 
						 {data:jsondata}, function(){
							 $.closeModal(false);
					});
			}
		})
	}
	
	function setLjInput(t){
	    if ($(t).data('waschecked') == true)
	    {
	    	$(t).prop('checked', false);
	    	$(t).data('waschecked', false);
	    	$(t).val("0")
	    	$("#zdy").hide();
	    	$("#zdy input").val("0");
	    	$("#zdy input").prop('checked', false);
	    	$("#zdy input").data('waschecked', false);
	    }
	    else
	    {
	    	$(t).prop('checked', true);
	    	$(t).data('waschecked', true);
	    	$(t).val("1")
	    	$("#zdy").show();
	    	
	    }
	}
	
	function setZDYInput(t){
	    if ($(t).data('waschecked') == true)
	    {
	    	$(t).prop('checked', false);
	    	$(t).data('waschecked', false);
	    	$(t).val("0")
	    }
	    else
	    {
	    	$(t).prop('checked', true);
	    	$(t).data('waschecked', true);
	    	$(t).val("1")
	    }
	}
	
</script>