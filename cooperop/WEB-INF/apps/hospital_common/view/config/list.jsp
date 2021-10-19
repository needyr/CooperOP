<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="系统配置管理" >
	<s:row>
		<s:form border="0" id="form" label="快速查找">
			<s:row>
				<s:textfield label="关键字" name="filter" placeholder="请输入配置名称" cols="2"></s:textfield>
				<s:button label="查询" icon="" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="系统配置管理" autoload="false" action="hospital_common.config.query" sort="true" >
			<s:toolbar>
				<s:button icon="" label="新增配置" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="code" label="配置编号" datatype="" ></s:table.field>
				<s:table.field name="name" label="配置名称" datatype="" ></s:table.field>
				<s:table.field name="value" label="值" datatype="script" >
					if(record.code == 'time_pharmacist_work' ||
					record.code == 'time_pharmacist_work_outp' ||
					record.code == 'time_pharmacist_work_emer'){
						var _value = record.value;
							if(_value){
								var _values = _value.split('A');
							var l = _values.length;
							var result = '';
							for(var i=0; i < l; i++){
								var x = _values[i].split('#');
								if(x[0] == '1' && x[1]){
									result = result + "星期一："+x[1]+"<br>";
								}else if(x[0] == '2' && x[1]){
									result = result + "星期二："+x[1]+"<br>";
								}else if(x[0] == '3' && x[1]){
									result = result + "星期三："+x[1]+"<br>";
								}else if(x[0] == '4' && x[1]){
									result = result + "星期四："+x[1]+"<br>";
								}else if(x[0] == '5' && x[1]){
									result = result + "星期五："+x[1]+"<br>";
								}else if(x[0] == '6' && x[1]){
									result = result + "星期六："+x[1]+"<br>";
								}else if(x[0] == '0' && x[1]){
									result = result + "星期日："+x[1]+"<br>";
								}
							}
							return result;
						}else{
							return '';
						}
					}else{
						return record.value;
					}
				</s:table.field>
				<%-- <s:table.field name="remark" label="描述" datatype="" ></s:table.field> --%>
				<s:table.field name="caozuo" label="操作" datatype="template" >
					<a href="javascript:void(0)" onclick="update('$[code]','$[system_product_code]')">修改</a>
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
	
	function Add(){
		$.modal("edit.html","新增配置",{
			width:"60%",
			height:"70%",
			callback : function(e){
				query();
			}
		});
	}
	
	function update(code,system_product_code){
		console.log(system_product_code);
		if(code == 'time_pharmacist_work' || 
				code == 'time_pharmacist_work_outp' || code == 'time_pharmacist_work_emer'){
			$.modal("worktimeedit.html","修改配置",{
				width:"60%",
				height:"70%",
				code:code,
				system_product_code:system_product_code,
				callback : function(e){
					query();
				}
			});
		}else{
			$.modal("edit.html","修改配置",{
				width:"60%",
				height:"70%",
				code:code,
				system_product_code:system_product_code,
				callback : function(e){
					query();
				}
			});
		}
	}
	
</script>