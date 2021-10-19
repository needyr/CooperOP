<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑禁忌症问题" ismodal="true">
	<s:row>
		<s:form id="form" label="禁忌症问题">
			<s:toolbar>
				<s:button label="药品说明书" onclick="getDrugsms()" icon=""></s:button>
				<s:button label="保存" onclick="save()" icon=""></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<s:select label="条件" name="tiaojian" cols="1" value="${tiaojian}" >
					<s:option value="and" label="and" ></s:option>
					<s:option value="or" label="or"></s:option>
				</s:select>
			</s:row>
			<s:row>
				<s:autocomplete  id="" label="问题等级" name="sys_check_level" action="hospital_common.auditset.checklevel.queryListByIpc" limit="10" editable="false" cols="1" value="${sys_check_level_name}" required="true">
					<s:option value="$[sys_check_level]" label="$[sys_check_level_name]">
						$[sys_check_level_name]
					</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<input type="hidden" name="fdname" value="${fdname}" />
				<s:textfield label="年龄段" name="nianl_c"  cols="1" value="${nianl_c}" dbl_action='zl_select_sys_shengfjjz_06,sys_shengfangzl_jjz' disabled="disabled"></s:textfield>
				<s:textfield label="年龄范围" name="nianl_start"  cols="1" value="${nianl_start}"></s:textfield>
				<s:textfield label="至" name="nianl_end"  cols="1" value="${nianl_end}"></s:textfield>
			</s:row>
			<s:row>
			    <s:textarea label="禁忌症描述" name="jingjz_message"  cols="3" value="${jingjz_message}" ></s:textarea>
			</s:row>
			<s:row>
				<s:textarea label="警示消息" name="nwarn_message" cols="4" placeholder="请输入警示提示消息内容"  rows="" value="">${nwarn_message}</s:textarea>
			</s:row>
			<s:row>
				<s:textfield label="备注" name="beizhu"  cols="4" value="${beizhu}"></s:textfield>
			</s:row>
			<s:row>
				<input name="sys_p_key" value="${param.drug_code}" hidden="true"/>
			</s:row>
		</s:form>
		<c:if test="${param.is_newadd != 1}">
		<s:table id="datatable" label="诊断信息" autoload="false" action="hospital_common.syscustomreview.syssfjjz.query_mx" sort="true"  limit="10">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add()"></s:button>
				<%-- <s:button label="批量新增" icon="" onclick="add_check()"></s:button>
				<s:button label="批量分级新增" icon="" onclick="adds()"></s:button> --%>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="编号" sort="true"></s:table.field>
				<s:table.field name="tiaojian" label="条件" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="formul_name" label="公式" ></s:table.field>
				<s:table.field name="diagnosis_name" label="诊断名称" ></s:table.field>
				<s:table.field name="diagnosis_code" label="诊断代码" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','禁忌症诊断')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		</c:if>
	</s:row>
</s:page>
<script type="text/javascript">

$(function(){
	query();
})
	function query(){
		$("#datatable").params({"parent_id":'${param.id}'});
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
	
	function save(){
		if (!$("form").valid()){
	   		return false;
	   	}
		var sdata=$("#form").getData();
		sdata.id='${param.id}';
		if('${sort_name}' == sdata.apa_check_sorts_id){
			sdata.apa_check_sorts_id = '${apa_check_sorts_id}';
		} 
		if('${sys_check_level_name}' == sdata.sys_check_level){
			sdata.sys_check_level = '${sys_check_level}';
		} 
		$.call("hospital_common.syscustomreview.syssfjjz.save",sdata,function(s){
			$.closeModal(s);
    	});
		/* if(sdata.id){
			$.call("hospital_common.dictdrug.updateCz", 
					{drug_code: '${param.spbh}', zdy_cz: "修改禁忌症问题"}, function(){});
		}else{
			$.call("hospital_common.dictdrug.updateCz", 
					{drug_code: '${param.spbh}', zdy_cz: "新增禁忌症问题"}, function(){});
		} */
	}
	
	//取消
    function cancel(){
    	$.closeModal(false);
    }
	
  //打开药品说明书
	function getDrugsms(){
		var drugcode = '${param.drug_code}';
		$.modal("/w/hospital_common/additional/sysinstruction.html?drug_code="+drugcode,"查看药品说明书",{
	        width:"70%",
	        height:"100%",
	        callback : function(e){
	        }
		});
	};
	
	function Delete(id,wt){
		$.confirm("是否确认删除，删除后无法恢复！",function callback(e){
			if(e==true){
				$.call("hospital_common.syscustomreview.syssfjjz.delete_mx",{"id":id},function(e){
					query();
					/* $.call("hospital_common.dictdrug.updateCz", 
							{drug_code: '${param.spbh}', zdy_cz: "删除"+wt}, function(){}); */
		    	}); 
			}
		});
	}
	
	function add(){
		var drug_code = '${param.drug_code}';
		$.modal("/w/hospital_common/syscustomreview/syssfjjz/diagnosis.html","新增",{
			width:"60%",
			height:"70%",
			parent_id: '${param.id}',
			drug_code: drug_code,
			callback : function(e){
				query();
			}
		});
	}
	
	function update(id){
		$.modal("/w/hospital_common/syscustomreview/syssfjjz/diagnosis.html","修改",{
			width:"60%",
			height:"70%",
			id: id,
			parent_id: '${param.id}',
			callback : function(e){
				query();
			}
		});
	}
</script>