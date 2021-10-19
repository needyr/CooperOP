<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="医院与系统药品配对维护">
	<style type="text/css">
		.empcl{
			line-height: 30px;
		}
	</style>
	<s:row>
		<div class="col-md-6">
			<s:row>
				<s:form border="0" id="form" label="快速查找">
						<s:toolbar>
							<s:button label="一键匹配" icon="" onclick="yjpp();" title="按照国药准字自动匹配药品"></s:button>
							<s:button label="初始化" icon="" onclick="init();" ></s:button>
						</s:toolbar>
						<s:row>
							<s:textfield label="关键字" name="filter" placeholder="请输入药品编号,名称,生产厂家,批准文号或简拼" cols="3"></s:textfield>
							<s:radio  label="筛选条件" name="sxtj" required="true" cols="2" value="all" style="border:0">
								<s:option value="all" label="全部" ></s:option>
								<s:option value="not" label="未匹配"></s:option>
								<s:option value="has" label="已匹配"></s:option>
							</s:radio>
							<%-- <s:textfield label="生产厂家" name="shengccj" placeholder="请输入生产厂家" cols="4"></s:textfield> --%>
							<%-- <s:button label="查询" icon="" onclick="queryin()"></s:button> --%>
						</s:row>
						<s:row>
							<s:select label="药品类别" name="drug_indicator" value="all" cols="3">
								<s:option value="all" label="全部"></s:option>
								<c:forEach items="${types}" var="t">
									<s:option value="${t.drug_indicator}" label="${t.drug_indicator}"></s:option>
								</c:forEach>
							</s:select>
						</s:row>
				</s:form>
			</s:row>
			<s:row>
				<s:table id="tablein" label="药品信息" autoload="false" action="hospital_common.dictdrug.queryin" sort="true" active="true" limit="10" >
					 <s:toolbar>
						<s:button label="查询" icon="fa fa-search" onclick="queryin()"></s:button>
					</s:toolbar> 
					<s:table.fields >
						<s:table.field name="drug_info" label="药品信息" datatype="script" >
							var drug_info='';
							var drug_code=record.drug_code;
							if(drug_code!=null){
								drug_info+='('+drug_code+')';
							}
							var drug_name=record.drug_name;
							if(drug_name != null){
								drug_info+=drug_name;
							}
							var druggg=record.druggg;
							if(druggg!=null){
								drug_info+='&nbsp;[&nbsp;'+druggg+'&nbsp;]<br>';
							}
							var pizhwh=record.pizhwh;
							if(pizhwh!=null){
								drug_info+='[&nbsp;'+pizhwh+'&nbsp;]&nbsp;';
							}
							var shengccj=record.shengccj;
							if(shengccj!=null){
								drug_info+=shengccj;
							}
							return drug_info;
						</s:table.field> 
					   <%--  <s:table.field name="drug_code" label="药品编号" sort="true"></s:table.field> 
						<s:table.field name="drug_name" label="药品名称" ></s:table.field>
						<s:table.field name="druggg" label="药品规格" ></s:table.field>
						<s:table.field name="shengccj" label="生产厂家" ></s:table.field>
						<s:table.field name="pizhwh" label="批准文号" datatype=""></s:table.field> --%>
						<s:table.field name="sysname" label="标准库药品名称" datatype="script" width="90px">
							var s=record.sysname;
							if(s==null){
								s="";
							}
							return '<a href="javascript:void(0)"  onclick="queryone(\''+record.sys_p_key+'\');">' + s + '</a>'
						</s:table.field>
						<s:table.field name="caozuo" label="操作" datatype="script" width="55px">
							var syspkey = record.sys_p_key;
							var html = [];
							if(!syspkey){
							html.push('<a href="javascript:void(0)" onclick="importSYS(\''+record.p_key+'\',\''+record.drug_code+'\')">设为标准</a>  ');
							}else{
							html.push('<a href="javascript:void(0)" style="color:#9e9c9c;font-weight: 200;" onclick="removeSYS(\''+record.drug_code+'\')">清除匹配</a>  ');
							}
							html.push('<div class="link-top" style="width: 60%;height: 1px;border-top: solid #9e9c9c 2px;position: relative;left: 20%;"></div>');
							html.push('<a href="javascript:void(0)" style="font-size: 13px;" onclick="autohx(this,');
							html.push('\''+record.drug_code+'\',');
							html.push('\''+record.drug_name+'\',');
							html.push('\''+record.druggg+'\',');
							html.push('\''+record.shengccj+'\',');
							html.push('\''+record.pizhwh+'\',');
							html.push('\''+record.drug_unit+'\',');
							html.push('\''+record.p_key+'\')">');
							html.push('候选药品</a>');
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
						<s:button label="国药准字查找" onclick="queryzz('forsys')"></s:button>
						<s:button label="查找" onclick="queryright('forsys')"></s:button>
					</s:toolbar>
					<s:row>
						<input type="hidden"  name="p_key" value="-9" >
						<s:textfield label="药品名称" name="drug_name" placeholder="请输入药品名称" cols="2"></s:textfield><a class="empcl" onclick="cleart('drug_name');">清空</a>
					</s:row>
					<s:row>
						<s:textfield label="国药准字" name="pizhwh" placeholder="请输入国药准字" cols="2"></s:textfield><a class="empcl" onclick="cleart('pizhwh');">清空</a>
					</s:row>
					<s:row>
						<s:textfield label="药品规格" name="druggg" placeholder="请输入药品规格" cols="2"></s:textfield><a class="empcl" onclick="cleart('druggg');">清空</a>
					</s:row>
					<s:row>
						<s:textfield label="药品单位" name="drug_unit" placeholder="请输入药品单位" cols="2"></s:textfield><a class="empcl" onclick="cleart('drug_unit');">清空</a>
					</s:row>
					<s:row>
						<s:textfield label="生产厂家" name="shengccj" placeholder="请输入生产厂家" cols="2"></s:textfield><a class="empcl" onclick="cleart('shengccj');">清空</a>
						<%-- <s:button label="按生产厂家筛选候选药品" onclick="queryright('fortemp')"></s:button> --%>
					</s:row>
				</s:form>
			</s:row>
			
			<s:row>
				 <s:form label="从初始化而来的候选药品中筛选（带有匹配度）" id="cjdata">
					<s:toolbar>
						<s:button label="筛选" onclick="queryright('fortemp')"></s:button>
					</s:toolbar>
					<s:row>
						<s:textfield name="shengccj" label="生产厂家" placeholder="请输入生产厂家" cols="3"></s:textfield>
					</s:row>
			 	</s:form> 
			</s:row>
			
			<s:row>
				 <s:table id="tableright" label="匹配到的标准库药品信息" autoload="false" action="hospital_common.dictdrug.querysys" limit="10">
					<s:table.fields >
						<s:table.field name="caozuo" label="操作" datatype="template" width="30px">
								<a href="javascript:void(0)" onclick="update('$[p_key]')">匹配</a>
						</s:table.field>
						<s:table.field name="pizhwh" label="批准文号"></s:table.field> 
						<s:table.field name="drug_name" label="药品名称（匹配度）" datatype="script">
							var hadsms = 0;
							if(record.mscode){
								hadsms = 1;
							}
							if(record.spmch_ppd=='undefind'||record.spmch_ppd==null||record.spmch_ppd==''){
								if(hadsms == 1){
									return '<a onclick="durgins(\''+ record.mscode+'\')">'+record.drug_name+'</a>';
								}else{
									return record.drug_name;
								}
							}else{
								if(hadsms == 1){
									return '<a onclick="durgins(\''+ record.mscode+'\')">'+record.drug_name+' ('+record.spmch_ppd+'%)'+'</a>';
								}else{
									return record.drug_name+" ("+record.spmch_ppd+"%)";
								}
							}
						</s:table.field>
						<s:table.field name="druggg" label="药品规格（匹配度）" datatype="script">
							if(record.shpgg_ppd=='undefind'||record.shpgg_ppd==null||record.shpgg_ppd==''){
								if(record.pphyt=='1'){
									return "<font color='green'>"+record.druggg+"</font>"
								}else{
									return record.druggg;
								}
							}else{
								if(record.pphyt=='1'){
									return "<font color='green'>"+record.druggg+" ("+record.shpgg_ppd+"%)"+"</font>"
								}else{
									return record.druggg+" ("+record.shpgg_ppd+"%)";
								}
							}
						</s:table.field>
						<s:table.field name="shengccj" label="生产厂家（匹配度）" datatype="script">
						    if(record.shengccjname_ppd=='undefind'||record.shengccjname_ppd==null||record.shengccjname_ppd==''){
								if(record.pphyt=='1'){
									return "<font color='green'>"+record.shengccj+"</font>"
								}else{
									return record.shengccj;
								}
							}else{
								if(record.pphyt=='1'){
									return "<font color='green'>"+record.shengccj+" ("+record.shengccjname_ppd+"%)"+"</font>"
								}else{
									return record.shengccj+" ("+record.shengccjname_ppd+"%)";
								}
							}
						</s:table.field>
						<s:table.field name="drug_code" label="药品编号"  datatype="script">
								if(record.pphyt=='1'){
									return "<font color='green'>"+record.drug_code+"</font>"
								}else{
									return record.drug_code;
								}
						</s:table.field>
						<%-- <s:table.field name="sys_p_key" label="标准库药品编号" datatype=""></s:table.field> --%>
					</s:table.fields>
				 </s:table> 
			</s:row>
			
		</div>
	</s:row>
</s:page> 
<script type="text/javascript">
	//全局变量容器
	var ygzl = {};
	
	$(function(){
		queryin();
	});
	//刷新左表(重新查询)
	function queryin(){
		var qdata=$("#form").getData();
		//console.log(qdata);
		$("#tablein").params(qdata);
		/* $("#tablein").refresh();
		
		 */
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

		//$.message("数据加载完成！");
	}
	
	//查看药品
	function queryone(s){
		$.modal("datil.html","详细信息",{
			width:"500px",
			height:"400px",
			p_key:s,
			callback : function(e){
				//queryin();
			}
		});
	}
	
	//匹配
	function update(p_key){
		var qdata=$("#formright").getData();
		 $.call("hospital_common.dictdrug.save",{"drug_code": ygzl.his_drug_code, "sys_p_key":p_key},function(s){
			 queryin();
			 //$("input[type=reset]").trigger("click");
    	}); 
	}
	
	//自动匹配
	function autohx(_this,drug_code,drug_name,druggg,shengccj,pizhwh,drug_unit, his_p_key){
		ygzl.his_drug_code = drug_code;
		$("input[name='p_key']").val("");
		$("input[name='drug_name']").val("");
	    $("input[name='druggg']").val("");
	    $("input[name='shengccj']").val("");
	    $("input[name='drug_unit']").val(""); 
	    $("input[name='pizhwh']").val("");
		$(_this).parents("tr").css("background-color","#ccc").siblings().css("background-color","");
		$("input[name='p_key']").val(his_p_key);
		
		queryright(null);
		
	    $("input[name='drug_name']").val(drug_name.trim());
	    
		$("input[name='druggg']").val(druggg == 'null' ? "" : (druggg.trim()));
		$("input[name='shengccj']").val(shengccj == 'null' ? "" : (shengccj.trim()));
		$("input[name='pizhwh']").val(pizhwh == 'null' ? "" : (pizhwh.trim()));
		$("input[name='drug_unit']").val(drug_unit == 'null' ? "" : (drug_unit.trim())); 
	}
	//刷新右表
	function queryright(stype){
		//console.log($("#formright").getData());
		var qdata=$("#formright").getData();
		var ss = qdata.p_key;
		if(ss==-9){
			$.message("请选择要匹配的药品！");
			return ;
		}
		console.log(qdata);
		if(stype=='fortemp'){
			qdata = null;
			qdata = $("#cjdata").getData();
			qdata.p_key = ss;
			console.log(qdata);
		}
		qdata.stype=stype;
		$("#tableright").params(qdata);
		$("#tableright").refresh();
	}
	
	//一键匹配
	function yjpp(){
		$.confirm("将批准文号相同的院内药品与标准库药品匹配？",function callback(e){
			if(e==true){
				$.call("hospital_common.dictdrug.yjpp",{},function(s){
					 queryin();
		   		});
			}
		});
	}
	
	//初始化药品匹配度top100
	function init(){
		$.confirm("确认执行初始化？（初始化将会耗费大量时间，如已经初始化，则无需再次执行！）",function callback(e){
			if(e==true){
				$.call("hospital_common.dictdrug.callinit",{},function(s){
					 $.message("后台已开始初始化数据，不会影响您的工作！")
		   		});
			}
		});
	}
	
	//设为标准 ：将药品导入到标准库 并与之匹配
	function importSYS(p_key,drug_code){
		$.confirm("将该药品信息导入标准药品库，并与之匹配！",function callback(e){
			if(e==true){
				$.call("hospital_common.dictdrug.importSYS",{p_key: p_key,drug_code: drug_code},function(rtn){
					queryin();
				});
			}
		});
		
	}
	
	//清除匹配
	function removeSYS(drug_code){
		$.confirm("将该药品还原为未匹配状态！",function callback(e){
			if(e==true){
				$.call("hospital_common.dictdrug.removeSYS",{"drug_code":drug_code,"sys_p_key":""},function(rtn){
					queryin();
				});
			}
		});
		
	}
	
	function durgins(drug_code){
		$.modal("/w/hospital_common/additional/sysinstruction.html?drug_code="+drug_code,"查看药品说明书",{
	        width:"80%",
	        height:"90%",
	        callback : function(e){
	        }
		});
	}
	
	function cleart(_name){
		var _cho = '$(\'[name='+_name+']\').val("")';
		eval(_cho);
	}
	
	//按国药准字匹配
	//刷新右表
	function queryzz(stype){
		var qdata=$("#formright").getData();
		var zdata = {
			'p_key': qdata.p_key,
			'pizhwh': qdata.pizhwh
		};
		var ss = qdata.p_key;
		if(ss==-9){
			$.message("请选择要匹配的药品！");
			return ;
		}
		zdata.stype=stype;
		$("#tableright").params(zdata);
		$("#tableright").refresh();
	}
</script>