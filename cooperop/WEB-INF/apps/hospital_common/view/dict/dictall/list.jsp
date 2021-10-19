<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="his与sys字典配对管理" >
	<s:row>
		<s:form border="0" id="form" label="基础信息匹配字典">
			<s:row>
				<s:autocomplete label="字典选择" name="tablename" placeholder="请选择待匹配的字典"  action="hospital_common.dict.dictall.querymap" limit="10" value="dict_his_diagnosisclass,dict_sys_diagnosisclass" text='诊断类别' cols="2">
					<s:option value="$[histname],$[systname]" label="$[mapcn]">
						<span style="display: block;width: 200px;float:left">$[mapcn]</span>
						<span style="display: block;width: 200px;float:left">[$[now_remark]]</span>
					</s:option>
				</s:autocomplete>
				<s:button label="查询" icon="fa fa-search" onclick="queryFrom()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	 <s:row>
		<iframe src="iflist.html?histname=dict_his_diagnosisclass&systname=dict_sys_diagnosisclass" id="tiframe" style="width: 100%;height:600px;border: 0px;scrolling : auto ">
		
		</iframe> 
	</s:row>
	
</s:page>
<script type="text/javascript">
	var ygzl = {};
	ygzl.now = parseFloat((new Date()).getTime());
	
	function queryFrom(){
		str=$("#form").getData().tablename;
		if (str==""){
			return false;
		}else{
			arr=str.split(",");
		    var url = "iflist.html?histname="+arr[0]+"&&systname="+arr[1];
			$("#tiframe").attr("src",url);  
		}
	}
	
	$('[name = "tablename"]').click(function(){
		var tm = parseFloat((new Date()).getTime()) - ygzl.now;
		if(tm > 3000){
			$.call('hospital_common.dict.dictall.reMapNum' , {}, function(rtn){
				ygzl.now = parseFloat((new Date()).getTime());
			},function(e){}, {async: false, remark: false});
			
		}
	});
</script>