<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="自定义审查维护" >
	<s:row>
		<s:form border="0" id="form" label="药品信息">
			<s:toolbar>
				<s:button label="药品说明书" icon="" onclick="getDrugsms();"></s:button>
			</s:toolbar>
			<s:row>
					<s:autocomplete  id="ssks" label="药品编号" name="spbh" action="hospital_common.dictdrug.querynotzdysc" limit="10" editable="false" cols="1" value="${param.spbh}" required="true">
						<s:option value="$[drug_code]" label="$[drug_code]">
							<span style="float:left;display:block;width:100px;">$[drug_code]</span>
							<span style="float:left;display:block;width:150px;">$[drug_name]</span>
							<span style="float:left;display:block;width:100px;">$[druggg]</span>
							<span style="float:left;display:block;width:200px;">$[shengccj]</span>
						</s:option>
					</s:autocomplete>
				
					 <s:textfield name="drug_name" value="" label="药品名称"  disabled="disabled" cols="2"></s:textfield>
					 <s:textfield name="druggg" value="" label="规格" disabled="disabled"></s:textfield>
					 <s:textfield name="drug_unit" value="" label="单位" disabled="disabled"></s:textfield>
					<s:textfield name="syjl" value="" label="使用计量单位" disabled="disabled"></s:textfield>
					 <s:textfield name="shengccj" value="" label="生产厂家" disabled="disabled" cols="2"></s:textfield>
					 <s:textfield name="pizhwh" value="" label="批准文号" disabled="disabled"></s:textfield>
					
			</s:row>
		</s:form>
	</s:row>
	<s:row>
	<s:tabpanel>
		<s:table id="departmenttable" label="科室设置" autoload="false" action="hospital_common.drugcontrol.queryByDrugCode" sort="true"  limit="10" active="true" onclick="query('departmenttable');">
			<s:toolbar>
				<s:button label="新增" icon="fa fa-plus" onclick="add('department','departmenttable')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="ID" width="23" ></s:table.field>
				<s:table.field name="condition" label="名单" datatype="script" width="38">
					var condition=record.condition;
					if(condition=="1"){
						return "白名单";
					}else{
						return "黑名单";
					}
				</s:table.field>
				<s:table.field name="value_name" label="值" ></s:table.field>
				<s:table.field name="remark" label="备注" ></s:table.field>
				<s:table.field name="description" label="描述"></s:table.field>
				<s:table.field name="beactive" label="状态" datatype="script" width="28">
					var beactive=record.beactive;
					if(beactive=="1"){
						return "开启";
					}else{
						return "关闭";
					}
				</s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="53" >
					<a href="javascript:void(0)" onclick="update('$[id]','department','departmenttable')">修改</a>
					<a href="javascript:void(0)" onclick="del('$[id]','departmenttable')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		<s:table id="doctortable" label="医生设置" autoload="false" action="hospital_common.drugcontrol.queryByDrugCode" sort="true"  limit="10"  onclick="query('doctortable');">
			<s:toolbar>
				<s:button label="新增" icon="fa fa-plus" onclick="add('doctor','doctortable')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="ID" width="23"></s:table.field>
				<s:table.field name="condition" label="名单" datatype="script" width="38">
					var condition=record.condition;
					if(condition=="1"){
						return "白名单";
					}else{
						return "黑名单";
					}
				</s:table.field>
				<s:table.field name="value_name" label="值" ></s:table.field>
				<s:table.field name="remark" label="备注" ></s:table.field>
				<s:table.field name="description" label="描述"  ></s:table.field>
				<s:table.field name="beactive" label="状态" datatype="script" width="28">
					var beactive=record.beactive;
					if(beactive=="1"){
						return "开启";
					}else{
						return "关闭";
					}
				</s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="53" >
					<a href="javascript:void(0)" onclick="update('$[id]','doctor','doctortable')">修改</a>
					<a href="javascript:void(0)" onclick="del('$[id]','doctortable')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		<s:table id="patienttable" label="病人设置" autoload="false" action="hospital_common.drugcontrol.queryByDrugCode" sort="true"  limit="10"  onclick="query('patienttable');">
			<s:toolbar>
				<s:button label="新增" icon="fa fa-plus" onclick="add('patient','patienttable')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="ID" width="23" ></s:table.field>
				<s:table.field name="condition" label="名单" datatype="script" width="38">
					var condition=record.condition;
					if(condition=="1"){
						return "白名单";
					}else{
						return "黑名单";
					}
				</s:table.field>
				<s:table.field name="value_name" label="值" ></s:table.field>
				<s:table.field name="remark" label="备注" ></s:table.field>
				<s:table.field name="description" label="描述"></s:table.field>
				<s:table.field name="beactive" label="状态" datatype="script" width="28">
					var beactive=record.beactive;
					if(beactive=="1"){
						return "开启";
					}else{
						return "关闭";
					}
				</s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="53"  >
					<a href="javascript:void(0)" onclick="update('$[id]','patient','patienttable')">修改</a>
					<a href="javascript:void(0)" onclick="del('$[id]','patienttable')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		<s:table id="roletable" label="角色设置" autoload="false" action="hospital_common.drugcontrol.queryByDrugCode" sort="true"  limit="10"  onclick="query('roletable');">
			<s:toolbar>
				<s:button label="新增" icon="fa fa-plus" onclick="add('role','roletable')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="ID" width="23" ></s:table.field>
				<s:table.field name="condition" label="名单" datatype="script" width="38">
					var condition=record.condition;
					if(condition=="1"){
						return "白名单";
					}else{
						return "黑名单";
					}
				</s:table.field>
				<s:table.field name="value_name" label="值" ></s:table.field>
				<s:table.field name="remark" label="备注" ></s:table.field>
				<s:table.field name="description" label="描述" ></s:table.field>
				<s:table.field name="beactive" label="状态" datatype="script" width="28">
					var beactive=record.beactive;
					if(beactive=="1"){
						return "开启";
					}else{
						return "关闭";
					}
				</s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="53" >
					<a href="javascript:void(0)" onclick="update('$[id]','role','roletable')">修改</a>
					<a href="javascript:void(0)" onclick="del('$[id]','roletable')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:tabpanel>
	</s:row>
</s:page>

<script type="text/javascript">
		$(function (){
			//修改操作禁止修改药品
			var spbhs = '${param.spbh}';
			if(spbhs){
				$("input[name='spbh']").attr("disabled","disabled");
				getdrug('${param.spbh}');
				query("departmenttable");
			}
			
		});
		//选择药品（切换药品）
		$("#ssks").change(function(){
			//根据drug_code去获取药品权限信息
			getdrug($(this).val());
			query("departmenttable");
		});
		
		function query(table){
			var item=table.substring(0,table.length-5);
			drug_code=$("#form").getData().spbh;
			$("#"+table).params({"drug_code": drug_code,"item":item});
			$("#"+table).refresh();
		}
		
		
		function add(xm,table){
			drug_code=$("#form").getData().spbh;
			var url=xm+"edit.html";
			if(drug_code!=""){
				$.modal(url,"新增",{
					width:"60%",
					height:"66%",
					maxmin: false,
					drug_code:drug_code,
					callback : function(e){
						query(table);
					}
				});
			}else{
				$.message("请先选择药品！");
			}
		}
		
		
		function update(id,xm,table){
			var url=xm+"edit.html";
			$.modal(url,"修改",{
				width:"60%",
				height:"66%",
				maxmin: false,
				id:id,
				callback : function(e){
					query(table);
				}
			});
		}

		
		function del(id,table){
			$.confirm("是否确认删除，删除后无法恢复！",function callback(e){
				if(e==true){
					$.call("hospital_common.drugcontrol.delete",
							{"id": id},
							function(rtn){
								query(table);	
						});
				}
			});
		}
		
		function cancel(){
	    	$.closeModal(false);
	    }
		
		//新增根据drugcode去获取该药品所有信息   返回rtn：药品信息
		function getdrug(drugcode){
			 $.call("hospital_common.dictdrug.getforzdy",{"drug_code":drugcode},function(rtn){
					if(rtn==null||typeof(rtn) == "undefined"){
						$("input[name='drug_name']").val("");
						$("input[name='drug_unit']").val("");
						$("input[name='druggg']").val("");
						$("input[name='shengccj']").val("");
						$("input[name='pizhwh']").val("");
						$("input[name='syjl']").val("");
					}else{
						$("input[name='drug_name']").val(rtn.drug_name);
						$("input[name='drug_unit']").val(rtn.drug_unit);
						$("input[name='druggg']").val(rtn.druggg);
						$("input[name='shengccj']").val(rtn.shengccj);
						$("input[name='pizhwh']").val(rtn.pizhwh);
						$("input[name='syjl']").val(rtn.syjl);
					}
				}); 
		}
		
		//打开药品说明书
		function getDrugsms(){
			var drugcode = $("#form").getData().spbh;
			$.modal("/w/ipc/auditresult/instruction.html?his_drug_code="+drugcode,"查看药品说明书",{
		        width:"70%",
		        height:"100%",
		        callback : function(e){
		        }
    		});
		};
		
</script>