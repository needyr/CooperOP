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
					<s:autocomplete  id="ssks" label="药品编号" name="drug_code" action="hospital_common.sysdictdrug.querynotzdysc" limit="10" editable="false" cols="1" value="${param.drug_code}" required="true">
						<s:option value="$[drug_code]" label="$[drug_code]">
							<span style="float:left;display:block;width:60px;">$[drug_code]</span>
							<span style="float:left;display:block;width:130px;">$[drug_name]</span>
							<span style="float:left;display:block;width:100px;">●$[druggg]</span>
							<span style="float:left;display:block;width:200px;">●$[shengccj]</span>
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
	<s:tabpanel>
		<s:table id="dtada" label="药品属性" autoload="false" action="hospital_common.syscustomreview.sysdrugmx.query" sort="true"  limit="10" active="true" onclick="queryDrug();">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="addDrug()"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="drug_code" label="药品编号" ></s:table.field>
				<s:table.field name="xmid" label="项目编号" sort="true"></s:table.field>
				<%-- <s:table.field name="displayorder" label="显示顺序" ></s:table.field> --%>
				<s:table.field name="xiangm" label="项目名称" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="dw" label="项目单位" ></s:table.field>
				<s:table.field name="message" label="项目说明" ></s:table.field>
				<s:table.field name="beizhu" label="备注" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="70">
					<!-- <a href="javascript:void(0)" onclick="updateDrug('$[drug_code]','$[xmid]')">修改</a> -->
					<a href="javascript:void(0)" onclick="DeleteDrug('$[drug_code]','$[xmid]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		<s:table id="datatable0" label="配伍问题" autoload="false" action="hospital_common.syscustomreview.syssfpeiw.query" sort="true"  limit="10" onclick="query('datatable0');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('syssfpeiw','datatable0')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="编号" sort="true"></s:table.field>
				<s:table.field name="nwarn_message" label="警示消息" ></s:table.field>
				<s:table.field name="tiaojian" label="条件" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="formul_name" label="公式" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="sort_name" label="问题分类" ></s:table.field>
				<s:table.field name="routename" label="给药方式" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="问题等级" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','syssfpeiw','datatable0')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','syssfpeiw','datatable0','配伍问题')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		<s:table id="datatable1" label="溶媒问题" autoload="false" action="hospital_common.syscustomreview.syssfrongm.query" sort="true"  limit="10" onclick="query('datatable1');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('syssfrongm','datatable1')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="编号" sort="true"></s:table.field>
				<s:table.field name="nwarn_message" label="警示消息" ></s:table.field>
				<s:table.field name="tiaojian" label="条件" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="formul_name" label="公式" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="sort_name" label="问题分类" ></s:table.field>
				<s:table.field name="routename" label="给药方式" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="问题等级" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','syssfrongm','datatable1')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','syssfrongm','datatable1','溶媒问题')">删除</a>
				</s:table.field>
			</s:table.fields>		</s:table>
		<s:table id="datatable2" label="用量问题" autoload="false" action="hospital_common.syscustomreview.syssfyongl.query" sort="true"  limit="10" onclick="query('datatable2');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('syssfyongl','datatable2')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="编号" sort="true"></s:table.field>
				<s:table.field name="nwarn_message" label="警示消息" ></s:table.field>
				<s:table.field name="tiaojian" label="条件" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="formul_name" label="公式" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="sort_name" label="问题分类" ></s:table.field>
				<s:table.field name="routename" label="给药方式" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="问题等级" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','syssfyongl','datatable2')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','syssfyongl','datatable2','用药问题')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		<s:table id="datatable3" label="用法问题" autoload="false" action="hospital_common.syscustomreview.syssfroute.query" sort="true"  limit="10" onclick="query('datatable3');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('syssfroute','datatable3')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="编号" sort="true"></s:table.field>
				<s:table.field name="nwarn_message" label="警示消息" ></s:table.field>
				<s:table.field name="tiaojian" label="条件" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="formul_name" label="公式" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="sort_name" label="问题分类" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="问题等级" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','syssfroute','datatable3')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','syssfroute','datatable3','用法问题')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		<s:table id="datatable4" label="频率问题" autoload="false" action="hospital_common.syscustomreview.syssfpl.query" sort="true"  limit="10" onclick="query('datatable4');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('syssfpl','datatable4')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="编号" sort="true"></s:table.field>
				<s:table.field name="nwarn_message" label="警示消息" ></s:table.field>
				<s:table.field name="tiaojian" label="条件" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="formul_name" label="公式" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="sort_name" label="问题分类" ></s:table.field>
				<s:table.field name="routename" label="给药方式" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="问题等级" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','syssfpl','datatable4')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','syssfpl','datatable4','频率问题')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		<s:table id="datatable6" label="肌酐清除率" autoload="false" action="hospital_common.syscustomreview.syssfccr.query" sort="true"  limit="10" onclick="query('datatable6');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('syssfccr','datatable6')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="编号" sort="true"></s:table.field>
				<s:table.field name="nwarn_message" label="警示消息" ></s:table.field>
				<s:table.field name="tiaojian" label="条件" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="formul_name" label="公式" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="sort_name" label="问题分类" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="问题等级" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','syssfccr','datatable6')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','syssfccr','datatable6','肌酐清除率')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		<s:table id="datatable7" label="禁忌症问题" autoload="false" action="hospital_common.syscustomreview.syssfjjz.query" sort="true"  limit="10" onclick="query('datatable7');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('syssfjjz','datatable7')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="编号" sort="true"></s:table.field>
				<s:table.field name="jingjz_message" label="描述" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','syssfjjz','datatable7')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','syssfjjz','datatable7','禁忌症问题')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		<s:table id="datatable8" label="适应症问题" autoload="false" action="hospital_common.syscustomreview.syssfsyz.query" sort="true"  limit="10" onclick="query('datatable8');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('syssfsyz','datatable8')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="编号" sort="true"></s:table.field>
				<s:table.field name="nwarn_message" label="警示消息" ></s:table.field>
				<s:table.field name="tiaojian" label="条件" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="formul_name" label="公式" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="sort_name" label="问题分类" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="问题等级" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','syssfsyz','datatable8')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','syssfsyz','datatable8','适应症问题')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		<s:table id="datatable9" label="相互作用问题" autoload="false" action="hospital_common.syscustomreview.syssfxhzy.query" sort="true"  limit="10" onclick="query('datatable9');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('syssfxhzy','datatable9')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="编号" sort="true"></s:table.field>
				<s:table.field name="nwarn_message" label="警示消息" ></s:table.field>
				<s:table.field name="tiaojian" label="条件" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="formul_name" label="公式" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="sort_name" label="问题分类" ></s:table.field>
				<s:table.field name="routename" label="给药方式" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="问题等级" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','syssfxhzy','datatable9')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','syssfxhzy','datatable9','相互作用问题')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:tabpanel>
</s:page>

<script type="text/javascript">
		$(function (){
			//修改操作禁止修改药品
			var drug_code = '${param.drug_code}';
			if(drug_code){
				$("input[name='drug_code']").attr("disabled","disabled");
				getdrug('${param.drug_code}');
				//刷新第一个表格
				$("#dtada").params({drug_code:drug_code});
				$("#dtada").refresh();
			}
			
			//选择药品（切换药品）
			$("#ssks").change(function(){
				//根据drug_code去获取药品信息
				getdrug($(this).val());
				//刷新问题为：当前药品的问题
				$(".tab-pane.active").find("table[ctype='table']").click();
				
			});
			//queryDrug();
		});

		
		function queryDrug(){
			var drug_code = '${param.drug_code}';
			if(!drug_code){
				drug_code=$("#form").getData().drug_code;
			}
			$("#dtada").params({"drug_code": drug_code});
			start = $("#dtada").dataTable().fnSettings()._iDisplayStart; 
			total = $("#dtada").dataTable().fnSettings().fnRecordsDisplay();
			var p = $("#dtada").DataTable().page();
			if((total-start)==1){
				if (start > 0) {
					$("#dtada").refresh_table(p-1);
				}else{
					$("#dtada").refresh();
				}
			}else{
				$("#dtada").refresh_table(p);
			}
		}
		//点击页签再去查询其他的
		/* $(".nav.nav-tabs ").find("li").click(function (){
			query($(this).index());
		})
		 */
		function query(tid){
			var qdata=$("#form").getData();
			var ss='${param.drug_code}';
			if(ss){
				//$("input[name='spbh']").attr("readonly","true");
				qdata.drug_code=ss;
			}
			$("#"+tid).params(qdata);
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
		//var choose=["","sfrongm","sfyongl","sfroute","sfpl"];
		
		function add(s,d){
			if (!$("form").valid()){
	    		return false;
	    	}
			var drug_code;
			var is_newadd;
			if('${param.drug_code}'==null||'${param.drug_code}'==''){
				drug_code=$("#form").getData().drug_code;
			}else{
				drug_code='${param.drug_code}';
			}
			if(s == 'syssfjjz'){
				is_newadd = 1;
			}else{
				is_newadd = 0;
			}
			$.modal("/w/hospital_common/syscustomreview/"+s+"/datiledit.html","新增",{
				width:"70%",
				height:"90%",
				drug_code:drug_code,
				is_newadd: is_newadd,
				callback : function(e){
					query(d);
				}
			});
		}
		
		function addDrug(){
			if (!$("form").valid()){
	    		return false;
	    	}
			var drug_code = '${param.drug_code}';
			if(!drug_code){
				drug_code=$("#form").getData().drug_code;
			}
			$.modal("/w/hospital_common/syscustomreview/sysdrugmx/drugedit.html","新增",{
				width:"500px",
				height:"90%",
				drug_code:drug_code,
				callback : function(e){
					queryDrug();
				}
			});
		}
		
		
		function update(id,s,d){
			var drug_code;
			if('${param.drug_code}'==null||'${param.drug_code}'==''){
				drug_code=$("#form").getData().drug_code;
			}else{
				drug_code='${param.drug_code}';
			}
			$.modal("/w/hospital_common/syscustomreview/"+s+"/datiledit.html","修改",{
				width:"70%",
				height:"90%",
				id:id,
				drug_code:drug_code,
				callback : function(e){
					query(d);
				}
			});
		}
		
		function updateDrug(drug_code,xmid){
			$.modal("/w/hospital_common/syscustomreview/sysdrugmx/drugedit.html","修改",{
				width:"60%",
				height:"90%",
				drug_code:drug_code,
				xmid:xmid,
				callback : function(e){
					queryDrug();
				}
			});
		}
		
		function Delete(id,s,d,wt){
			$.confirm("是否确认删除，删除后无法恢复！",function callback(e){
				if(e==true){
					$.call("hospital_common.syscustomreview."+s+".delete",{"id":id},function(e){
						query(d);
						/* $.call("hospital_common.dictdrug.updateCz", 
								{drug_code: '${param.spbh}', zdy_cz: "删除"+wt}, function(){}); */
			    	}); 
				}
			});
		}
		
		function DeleteDrug(drug_code,xmid){
			$.confirm("是否确认删除，删除后无法恢复！",function callback(e){
				if(e==true){
					$.call("hospital_common.customreview.hisdrugmx.delete",
							{"drug_code": drug_code,"xmid": xmid},
							function(rtn){
								queryDrug();	
						});
					/* $.call("hospital_common.dictdrug.updateCz", 
							{drug_code: drug_code, zdy_cz: "删除药品属性"}, function(){}); */
				}
			});
		}
		
		function cancel(){
	    	$.closeModal(false);
	    }
		
		//新增根据drugcode去获取该药品所有信息   返回rtn：药品信息
		function getdrug(drugcode){
			 $.call("hospital_common.sysdictdrug.getforzdy",{"drug_code":drugcode},function(rtn){
					console.log(rtn);
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
			var drugcode = $("#form").getData().drug_code;
			$.modal("/w/hospital_common/additional/sysinstruction.html?drug_code="+drugcode,"查看药品说明书",{
		        width:"70%",
		        height:"100%",
		        callback : function(e){
		        }
    		});
		};
		
</script>