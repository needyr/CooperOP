<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="医院与系统诊断疾病配对维护">
	<s:row>
		<div class="col-md-6">
			<s:row>
				<s:form border="0" id="form" label="快速查找">
						<s:toolbar>
							<s:button label="一键匹配" icon="" onclick="yjpp();" title="自动匹配疾病"></s:button>
							<s:button label="初始化" icon="" onclick="init();" style="background-color:#acaaaac2"></s:button>
						</s:toolbar>
						<s:row>
							<s:textfield label="关键字" name="filter" placeholder="请输入疾病名称" cols="4" id="filter"></s:textfield>
							<s:radio  label="筛选条件" name="sxtj" required="true" cols="4" value="all" border="0">
								<s:option value="all" label="全部" ></s:option>
								<s:option value="not" label="未匹配"></s:option>
								<s:option value="has" label="已匹配"></s:option>
							</s:radio>
						</s:row>
				</s:form>
			</s:row>
			<s:row>
				<s:table id="tablein" label="疾病信息" autoload="false" action="hospital_common.dict.diagnosis.queryin" sort="true" active="true" limit="10" >
					<s:toolbar>
						<s:button label="查询" icon="fa fa-search" onclick="queryin()"></s:button>
					</s:toolbar>
					<s:table.fields >
						<s:table.field name="diagnosis_code" label="疾病代码"></s:table.field>
						<s:table.field name="diagnosis_name" label="疾病名称"></s:table.field>
						<s:table.field name="input_code" label="输入码"></s:table.field>
						<s:table.field name="sys_p_key_name" label="标准库疾病名称"></s:table.field>
						<s:table.field name="caozuo" label="操作" datatype="script" width="120px">
							var syspkey = record.sys_p_key;
							var html = [];
							if(!syspkey){
							html.push('<a href="javascript:void(0)" onclick="direptUpdateSys(\''+record.diagnosis_code+'\',\''+record.diagnosis_name+'\',\''+record.input_code+'\',\''+record.p_key+'\')">设为标准</a> | ');
							}
							html.push('<a href="javascript:void(0)" onclick="autohx(');
							html.push('\''+record.p_key+'\',');
							html.push('\''+record.diagnosis_name+'\')">');
							html.push('获取候选项</a>');
							return html.join('');
						</s:table.field>
					</s:table.fields>
				</s:table>
			</s:row>
			
		</div>
		
		<div class="col-md-6">
			<s:row>
				<s:form  id="formright" label="从全部标准药品库中查找匹配药品（无匹配度）">
					<s:toolbar>
						<s:button label="查找" onclick="queryright('sys')"></s:button>
					</s:toolbar>
					<s:row>
						<input type="hidden" name="p_key" value="-9">
						<s:textfield name="jb_name" label="疾病名称" cols="3" id="jb_name"></s:textfield>
					</s:row>
				</s:form>
			</s:row>
			
			<s:row>
				<s:form  id="formpipei" label="从初始化而来的候选疾病中筛选（带有匹配度）">
					<s:toolbar>
						<s:button label="查找" onclick="queryright('tem')"></s:button>
					</s:toolbar>
					<s:row>
						<s:textfield name="jb_name_c" label="候选疾病名称" cols="3" id="jb_name_c"></s:textfield>
					</s:row>
				</s:form>
			</s:row>
			
			<s:row>
				 <s:table id="tableright" label="匹配到的标准库疾病信息" autoload="false" action="hospital_common.dict.diagnosis.querysys" limit="10">
					<s:table.fields >
						<input type="hidden" id="pipei">
						<s:table.field name="diagnosis_code" label="疾病编号" ></s:table.field> 
						<s:table.field name="diagnosis_name" label="疾病名称（匹配度）" datatype="script">
							if(record.diagnosis_name_ppd=='undefind'||record.diagnosis_name_ppd==null||record.diagnosis_name_ppd==''){
								return record.diagnosis_name;
							}
							return record.diagnosis_name+" ("+record.diagnosis_name_ppd+"%)";
						</s:table.field>
						<s:table.field name="caozuo" label="操作" datatype="template" width="30px">
								<a href="javascript:void(0)" onclick="update('$[p_key]','$[his_p_key]')">匹配</a>
						</s:table.field>
					</s:table.fields>
				 </s:table> 
			</s:row>
			
		</div>
	</s:row>
</s:page> 
<script type="text/javascript">
	$(function(){
		queryin();
	});
	//刷新左表(重新查询)
	function queryin(){
		var qdata=$("#form").getData();
		$("#tablein").params(qdata);
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
	}
	
	//匹配
	function update(sys_p_key,his_p_key){
		var p_key = $("input[name='p_key']").val();
		if(p_key == '-9'){
			 $.call("hospital_common.dict.diagnosis.updateMapping",{"sys_p_key":sys_p_key,"his_p_key":his_p_key},function(s){
				 queryin();
	    	});
		}else {
			 $.call("hospital_common.dict.diagnosis.updateMapping",{"sys_p_key":sys_p_key,"his_p_key":p_key},function(s){
				 queryin();
	    	});
		}
		 
	}
	
	//自动匹配
	function autohx(his_p_key,diagnosis_name){
		$("input[name='jb_name']").val(diagnosis_name);
		$("input[name='jb_name_c']").val(diagnosis_name);
		$("input[name='p_key']").val(his_p_key);
		queryright(his_p_key);
	}
	//刷新右表
	function queryright(stype){
		var qdata2=$("#formpipei").getData();
		var p_key = $("input[name='p_key']").val();
		if(stype =="tem"){
			$("#tableright").params(qdata2);
		}else if(stype=="sys"){
			var jb_name = $("input[name='jb_name']").val();
			$("#tableright").params({"jb_name":jb_name});
		}else{
			$("#tableright").params({"p_key":stype});
		}
		if(p_key != '-9'){
			$("#tableright").refresh();
		}else{
			$.message("请选择疾病！");
		}
	}
	
	//一键匹配
	function yjpp(){
		$.confirm("注意！将根据完全相同的疾病名称一键匹配",function callback(e){
			if(e==true){
				$.call("hospital_common.dict.diagnosis.updateSureMapping",{},function(s){
					 queryin();
		   		});
			}
		});
	}
	
	function direptUpdateSys(diagnosis_code,diagnosis_name,input_code,p_key){
		$.confirm("将该疾病信息导入标准诊断疾病库，并与之匹配！",function callback(e){
			if(e==true){
				$.call("hospital_common.dict.diagnosis.direptUpdateSys",{
						"diagnosis_code": diagnosis_code,
						"diagnosis_name": diagnosis_name,
						"input_code": input_code,
						"p_key": p_key
					},function(s){
			 			queryin();
  				});
			}
		});
		
	}
	
	//初始化药品匹配度top100
	function init(){
		$.confirm("确认执行初始化？（初始化将会耗费大量时间，如已经初始化，则无需再次执行！）",function callback(e){
			if(e==true){
				$.call("hospital_common.dict.diagnosis.callinit",{},function(s){
					 $.message("后台已开始初始化数据，不会影响您的工作！")
		   		});
			}
		});
	}
	
</script>