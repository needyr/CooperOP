<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="系统配置管理" >
	<s:row>
		<s:form border="0" id="form" collapsed="true" extendable="true">
			<s:row>
				<s:textfield label="关键字" name="filter" placeholder="请输入配置名称" cols="2"></s:textfield>
				<s:button label="查询" icon="" onClick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="系统配置管理" height="180" limit="0" autoload="false" action="hospital_common.config.queryCodes" sort="true" >
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
				<s:table.field name="remark" label="描述" datatype="" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" >
					<a href="javascript:void(0)" onclick="update('$[code]','$[system_product_code]')">修改</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		$(window).resize(function(){
			$('.dataTables_scrollBody').css('height',$(window).height() -
					270 - $("div[ctype='form']").parent().parent().height());
			$('.dataTables_scrollBody').css('min-height','100px');
			$('.dataTables_scrollBody').css('overflow','auto');
		}).resize();

		$("i.showhide").on("click", function(){
			$('.dataTables_scrollBody').css('height',$(window).height() -
					270 - $("div[ctype='form']").parent().parent().height());
			$('.dataTables_scrollBody').css('min-height','100px');
			$('.dataTables_scrollBody').css('overflow','auto');
		})
		query();
	});

	function query(){
		var qdata=$("#form").getData();
		if('${param.codes}'){
			qdata.codes = '${param.codes}';
		}else{
			qdata.codes = '[{"code":"common.listen.time","system_product_code":"hospital_common"}'
					+ ',{"code":"hty_add","system_product_code":"ipc"}'
					+ ',{"code":"hyt_dll_audit_address","system_product_code":"ipc"}'
					+ ',{"code":"hyt_jgdm","system_product_code":"ipc"}'
					+ ',{"code":"im_version","system_product_code":"hospital_common"}'
					+ ',{"code":"ipc.thirdparty.active","system_product_code":"ipc"}'
					+ ',{"code":"ipc.thirdparty.prompt","system_product_code":"ipc"}'
					+ ',{"code":"ipc_csmssq_open","system_product_code":"ipc"}'
					+ ',{"code":"time_pharmacist_work","system_product_code":"ipc"}'
					+ ',{"code":"time_pharmacist_work_emer","system_product_code":"ipc"}'
					+ ',{"code":"time_pharmacist_work_outp","system_product_code":"ipc"}'
					+ ',{"code":"wait_timeout_jz","system_product_code":"ipc"}'
					+ ',{"code":"wait_timeout_mz","system_product_code":"ipc"}'
					+ ',{"code":"wait_timeout_zy","system_product_code":"ipc"}'
					+ ',{"code":"d_type.join.audit","system_product_code":"hospital_imic"}'
					+ ',{"code":"imic_can_toselfpaid","system_product_code":"hospital_imic"}'
					+ ',{"code":"imic_can_toselfpaid_bill","system_product_code":"hospital_imic"}'
					+ ',{"code":"imic_isopen_bingzadmin","system_product_code":"hospital_imic"}'
					+ ',{"code":"url_imic","system_product_code":"hospital_common"}'
					+ ',{"code":"url_imic_drgs","system_product_code":"hospital_common"}'
					+ ',{"code":"rbf_address","system_product_code":"hospital_common"}'
					+ ',{"code":"local_server_address","system_product_code":"hospital_common"}'
					+ ']';
		}
		$("#datatable").params(qdata);
		$("#datatable").refresh();
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
					if(e){
						query();
					}
				}
			});
		}else{
			$.modal("edit.html","修改配置",{
				width:"60%",
				height:"70%",
				code:code,
				system_product_code:system_product_code,
				callback : function(e){
					if(e){
						query();
					}
				}
			});
		}
	}
	
</script>