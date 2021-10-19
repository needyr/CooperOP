<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="医保自定义审查维护" >
	<s:row>
		<s:form border="0" id="form" label="项目信息">
			<s:row>
				<s:autocomplete  id="ssks" label="编号" name="item_code" action="hospital_common.imiccustomre.querynotimicsc" limit="10" editable="false" cols="1" value="${param.item_code}" required="true">
					<s:option value="$[item_code]" label="$[item_code]">
						<span style="float:left;display:block;width:90px;">$[item_code]</span>
						<span style="float:left;display:block;width:350px;"> • $[item_name]</span>
						<span style="float:left;display:block;width:70px;"> • $[class_name]</span>
						<span style="float:left;display:block;width:50px;"> • $[item_spec]</span>
						<span style="float:left;display:block;width:70px;"> • $[units]</span>
					</s:option>
				</s:autocomplete>
				 <s:textfield name="item_code" value="" label="项目编号"  disabled="disabled"></s:textfield>
				 <s:textfield name="item_name" value="" label="项目名称"  disabled="disabled"></s:textfield>
				 <s:textfield name="class_name" value="" label="项目类型"  disabled="disabled"></s:textfield>
				 <s:textfield name="item_spec" value="" label="项目规格"  disabled="disabled"></s:textfield>
				 <s:textfield name="units" value="" label="项目单位" disabled="disabled"></s:textfield>
				 <s:textfield name="price" value="" label="价格" disabled="disabled"></s:textfield>
				 <s:textfield name="start_date"  label="启动日期" disabled="disabled" format="yyyy-MM-dd HH:mm:ss"></s:textfield>
				 <s:textfield name="stop_date" label="停用日期" disabled="disabled" format="yyyy-MM-dd HH:mm:ss"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
	<s:tabpanel>
		<s:table id="dtada" label="医保限专规则" autoload="false" action="hospital_common.imiccustomre.ybxianz.query" sort="true"  limit="10" active="true" onclick="queryDrug();">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('ybxianz','dtada')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="charge_type_name" label="费别" datatype="script" >
					var charge_type_name=record.charge_type_name;
					if(charge_type_name != null && charge_type_name != ""){
	                	var length=charge_type_name.length;
	                	if(length >= 9){
	                		return '<font title="'+charge_type_name+'">'+charge_type_name.substring(0,9)+'...</font>';
	                	}else{
	                		return charge_type_name;
	                	}
					}else{
						return "";
					}
				</s:table.field>
				<s:table.field name="dept_name" label="科室" datatype="script"  >
					var dept_name=record.dept_name;
					if(dept_name != null && dept_name != ""){
	                	var length=dept_name.length;
	                	if(length >= 9){
	                		return '<font title="'+dept_name+'">'+dept_name.substring(0,9)+'...</font>';
	                	}else{
	                		return dept_name;
	                	}
					}else{
						return "";
					}
				</s:table.field>
				<s:table.field name="doctor_name" label="医生" datatype="script"  >
					var doctor_name=record.doctor_name;
					if(doctor_name != null && doctor_name != ""){
	                	var length=doctor_name.length;
	                	if(length >= 9){
	                		return '<font title="'+doctor_name+'">'+doctor_name.substring(0,9)+'...</font>';
	                	}else{
	                		return doctor_name;
	                	}
					}else{
						return "";
					}
				</s:table.field>
				<s:table.field name="patient_name" label="患者" datatype="script"  >
					var patient_name=record.patient_name;
					if(patient_name != null && patient_name != ""){
	                	var length=patient_name.length;
	                	if(length >= 9){
	                		return '<font title="'+patient_name+'">'+patient_name.substring(0,9)+'...</font>';
	                	}else{
	                		return patient_name;
	                	}
					}else{
						return "";
					}
				</s:table.field>
				<s:table.field name="display_name" label="转自费流程" datatype="script"  >
					var display_name=record.display_name;
					if(display_name != null && display_name != ""){
	                	var length=display_name.length;
	                	if(length >= 9){
	                		return '<font title="'+display_name+'">'+display_name.substring(0,9)+'...</font>';
	                	}else{
	                		return display_name;
	                	}
					}else{
						return "";
					}
				</s:table.field>
				<s:table.field name="nwarn_message" label="警示信息" datatype="script"  >
					var nwarn_message=record.nwarn_message;
					if(nwarn_message != null && nwarn_message != ""){
	                	var length=nwarn_message.length;
	                	if(length >= 9){
	                		return '<font title="'+nwarn_message+'">'+nwarn_message.substring(0,9)+'...</font>';
	                	}else{
	                		return nwarn_message;
	                	}
					}else{
						return "";
					}
				</s:table.field>
				<s:table.field name="d_type_name" label="类型" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="thirdt_name" label="问题类型" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="提示等级" ></s:table.field>
				<s:table.field name="is_to_zf_name" label="转自费" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','ybxianz','dtada')">修改</a> 
					<a href="javascript:void(0)" onclick="Delete('$[id]','ybxianz','dtada')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		<s:table id="datatable0" label="医保项目规则" autoload="false" action="hospital_common.imiccustomre.ybitem.query" sort="true"  limit="10" onclick="query('datatable0');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('ybitem','datatable0')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="nwarn_message" label="警示信息" ></s:table.field>
				<s:table.field name="d_type_name" label="类型" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="thirdt_name" label="问题类型" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="提示等级" ></s:table.field>
				<s:table.field name="state" label="是否启用" datatype="script">
					var state = record.state;
					if(state == 1){
						return '启用';
					}else if(state == 0){
						return '停用';
					}else{
						return '';
					}
				</s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','ybitem','datatable0')">修改</a> 
					<a href="javascript:void(0)" onclick="Delete('$[id]','ybitem','datatable0')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		
		<s:table id="datatable2" label="医保项目驳回规则" autoload="false" action="hospital_common.imiccustomre.viorul.query" sort="true"  limit="10" onclick="query('datatable2');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('viorul','datatable2')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="nwarn_message" label="警示信息" ></s:table.field>
				<s:table.field name="d_type" label="类型" datatype="script">
					var d_type = record.d_type;
					if(d_type == 1){
						return '住院';
					}else if(d_type == '2'){
						return '门诊';
					}
				</s:table.field>
				<s:table.field name="thirdt_name" label="问题类型"></s:table.field>
				<s:table.field name="sys_check_level_name" label="驳回等级" ></s:table.field>
				<s:table.field name="state" label="状态" datatype="script">
					var state = record.state;
					if(state == 1){
						return '启用';
					}else if(state == 0){
						return '停用';
					}else{
						return '';
					}
				</s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','viorul','datatable2')">修改</a> 
					<a href="javascript:void(0)" onclick="Delete('$[id]','viorul','datatable2')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:tabpanel>
</s:page>

<script type="text/javascript">
		$(function (){
			//修改操作禁止修改药品
			var item_code = '${param.item_code}';
			if(item_code){
				getdrug(item_code);
				//刷新第一个表格
				$("#dtada").params({item_code: item_code});
				$("#dtada").refresh();
			}
			
			//选择药品（切换药品）
			$("#ssks").change(function(){
				//根据drug_code去获取药品信息
				getdrug($(this).val());
				//刷新问题为：当前药品的问题
				//queryDrug();
			});
		});

        //监控键盘Enter 回车键按下的动作
        document.onkeydown = function(e){
            if((e||event).keyCode == 13) {
                //如果当前为添加
                if('${param.item_code}' == null||'${param.item_code}' == ''){
                    var item_code = item_code = $("#form").getData().item_code;
                    if (!item_code){
                        $.message("搜索时医保项目编码不能为空");
                    }else {
                        $("#dtada").params({"item_code": item_code});
                        $("#dtada").refresh();
                    }
                }
            }
        };

		
		function queryDrug(){
			var item_code = '${param.item_code}';
			if(!item_code){
				item_code=$("#form").getData().item_code;
			}
			$("#dtada").params({"item_code": item_code});
			$("#dtada").refresh();
		}
		
		function query(tid){
			var item_code;
			if('${param.item_code}'==null||'${param.item_code}'==''){
				item_code=$("#form").getData().item_code;
			}else{
				item_code='${param.item_code}';
			}
			$("#"+tid).params({"item_code": item_code});
			$("#"+tid).refresh();
		}
		
		function add(s,d){
			if (!$("form").valid()){
	    		return false;
	    	}
			var item_code;
			if('${param.item_code}'==null||'${param.item_code}'==''){
				item_code=$("#form").getData().item_code;
			}else{
				item_code='${param.item_code}';
			}
			$.modal("/w/hospital_common/imiccustomre/"+s+"/datiledit.html","新增",{
				width:"75%",
				height:"95%",
				item_code:item_code,
				callback : function(e){
					query(d);
				}
			});
		}
			
		
		function update(id,s,d){
			$.modal("/w/hospital_common/imiccustomre/"+s+"/datiledit.html","修改",{
				width:"75%",
				height:"95%",
				id:id,
				callback : function(e){
					query(d);
				}
			});
		}
		
			
		function Delete(id,s,d){
			$.confirm("是否确认删除，删除后无法恢复！",function callback(e){
				if(e==true){
					$.call("hospital_common.imiccustomre."+s+".delete",{"id":id},function(e){
						query(d);
			    	}); 
				}
			});
		}
		
		function cancel(){
	    	$.closeModal(false);
	    }

		//新增根据item code去获取该项目信息   返回rtn：药品信息
		function getdrug(item_code){
			 $.call("hospital_common.imiccustomre.getforinsurvsprice",{"item_code": item_code},function(rtn){
					if(rtn==null||typeof(rtn) == "undefined"){
						$("input[name='item_code']").val("");
						$("input[name='item_name']").val("");
						$("input[name='class_name']").val("");
						$("input[name='item_spec']").val("");
						$("input[name='units']").val("");
						$("input[name='price']").val("");
						$("input[name='start_date']").val("");
						$("input[name='stop_date']").val("");						
					}else{
						$("input[name='item_code']").val(rtn.item_code);
						$("input[name='item_name']").val(rtn.item_name);
						$("input[name='class_name']").val(rtn.class_name);
						$("input[name='item_spec']").val(rtn.item_spec);
						$("input[name='units']").val(rtn.units);
						$("input[name='price']").val(rtn.price);
						$("input[name='start_date']").setData(new Date(rtn.start_date *1).toLocaleDateString());
						//alert(new Date(rtn.start_date *1).toLocaleDateString());
						$("input[name='stop_date']").setData(new Date(rtn.stop_date *1).toLocaleDateString());
					}
				}); 
		}
		
</script>