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
				<%-- <c:if test="${empty param.spbh}">
					<s:autocomplete  id="ssks" label="药品编号" name="spbh" action="hospital_common.dictdrug.querynotzdysc" limit="10" editable="false" cols="1" value="${spbh}" required="true">
							<s:option value="$[drug_code]" label="$[drug_code]">
								<span style="float:left;display:block;width:200px;">$[drug_code]</span>
								<span style="float:left;display:block;width:200px;">$[drug_name]</span>
							</s:option>
					</s:autocomplete>
				</c:if >
				<c:if test="${not empty param.spbh}"> --%>
					<s:autocomplete  id="ssks" label="药品编号" name="spbh" action="hospital_common.dictdrug.querynotzdysc" limit="10" editable="false" cols="1" value="${param.spbh}" required="true">
						<s:option value="$[drug_code]" label="$[drug_code]">
							<span style="float:left;display:block;width:200px;">$[drug_code]</span>
							<span style="float:left;display:block;width:200px;">$[drug_name]</span>
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
		<s:table id="dtada" label="药品属性" autoload="false" action="hospital_common.customreview.hisdrugmx.query" sort="true"  limit="10" active="true" onclick="queryDrug();">
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
		<s:table id="datatable0" label="配伍问题" autoload="false" action="hospital_common.customreview.sfpeiw.query" sort="true"  limit="10" onclick="query('datatable0');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('sfpeiw','datatable0')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="编号" sort="true"></s:table.field>
				<s:table.field name="sort_name" label="问题分类" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="问题等级" ></s:table.field>
				<s:table.field name="nwarn_message" label="警示消息" ></s:table.field>
				<s:table.field name="routename" label="给药方式" ></s:table.field>
				<s:table.field name="tiaojian" label="条件" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="formul_name" label="公式" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','sfpeiw','datatable0')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','sfpeiw','datatable0','配伍问题')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		<s:table id="datatable1" label="溶媒问题" autoload="true" action="hospital_common.customreview.sfrongm.query" sort="true"  limit="10" onclick="query('datatable1');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('sfrongm','datatable1')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="编号" sort="true"></s:table.field>
				<s:table.field name="sort_name" label="问题分类" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="问题等级" ></s:table.field>
				<s:table.field name="nwarn_message" label="警示消息" ></s:table.field>
				<s:table.field name="routename" label="给药方式" ></s:table.field>
				<s:table.field name="tiaojian" label="条件" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="formul_name" label="公式" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','sfrongm','datatable1')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','sfrongm','datatable1','溶媒问题')">删除</a>
				</s:table.field>
			</s:table.fields>		</s:table>
		<s:table id="datatable2" label="用量问题" autoload="false" action="hospital_common.customreview.sfyongl.query" sort="true"  limit="10" onclick="query('datatable2');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('sfyongl','datatable2')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="编号" sort="true"></s:table.field>
				<s:table.field name="sort_name" label="问题分类" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="问题等级" ></s:table.field>
				<s:table.field name="nwarn_message" label="警示消息" ></s:table.field>
				<s:table.field name="routename" label="给药方式" ></s:table.field>
				<s:table.field name="tiaojian" label="条件" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="formul_name" label="公式" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','sfyongl','datatable2')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','sfyongl','datatable2','用药问题')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		<s:table id="datatable10" label="儿童用量问题" autoload="false" action="hospital_common.customreview.sfyonglchild.query" sort="true"  limit="10" onclick="query('datatable10');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('sfyonglchild','datatable10')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="编号" sort="true"></s:table.field>
				<s:table.field name="sort_name" label="问题分类" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="问题等级" ></s:table.field>
				<s:table.field name="nwarn_message" label="警示消息" ></s:table.field>
				<s:table.field name="routename" label="给药方式" ></s:table.field>
				<s:table.field name="tiaojian" label="条件" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="formul_name" label="公式" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','sfyonglchild','datatable10')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','sfyonglchild','datatable10','儿童用药问题')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		<s:table id="datatable3" label="用法问题" autoload="false" action="hospital_common.customreview.sfroute.query" sort="true"  limit="10" onclick="query('datatable3');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('sfroute','datatable3')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="编号" sort="true"></s:table.field>
				<s:table.field name="sort_name" label="问题分类" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="问题等级" ></s:table.field>
				<s:table.field name="nwarn_message" label="警示消息" ></s:table.field>
				<s:table.field name="tiaojian" label="条件" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="formul_name" label="公式" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','sfroute','datatable3')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','sfroute','datatable3','用法问题')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		<s:table id="datatable4" label="频率问题" autoload="false" action="hospital_common.customreview.sfpl.query" sort="true"  limit="10" onclick="query('datatable4');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('sfpl','datatable4')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="编号" sort="true"></s:table.field>
				<s:table.field name="sort_name" label="问题分类" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="问题等级" ></s:table.field>
				<s:table.field name="nwarn_message" label="警示消息" ></s:table.field>
				<s:table.field name="tiaojian" label="条件" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="formul_name" label="公式" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','sfpl','datatable4')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','sfpl','datatable4','频率问题')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		<s:table id="datatable5" label="审查通过" autoload="false" action="hospital_common.customreview.sfauditpass.query" sort="true"  limit="10" onclick="query('datatable5');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('sfauditpass','datatable5')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="编号" sort="true"></s:table.field>
				<s:table.field name="nwarn_message" label="警示消息" ></s:table.field>
				<s:table.field name="tiaojian" label="条件" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="routename" label="给药方式" ></s:table.field>
				<s:table.field name="formul_name" label="公式" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="sort_name" label="问题分类" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="问题等级" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','sfauditpass','datatable5')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','sfauditpass','datatable5','审查通过')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		<s:table id="datatable6" label="肌酐清除率" autoload="false" action="hospital_common.customreview.sfccr.query" sort="true"  limit="10" onclick="query('datatable6');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('sfccr','datatable6')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="编号" sort="true"></s:table.field>
				<s:table.field name="sort_name" label="问题分类" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="问题等级" ></s:table.field>
				<s:table.field name="nwarn_message" label="警示消息" ></s:table.field>
				<s:table.field name="tiaojian" label="条件" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="formul_name" label="公式" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','sfccr','datatable6')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','sfccr','datatable6','肌酐清除率')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		<s:table id="datatable7" label="禁忌症问题" autoload="false" action="hospital_common.customreview.sfjjz.query" sort="true"  limit="10" onclick="query('datatable7');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('sfjjz','datatable7')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="编号" sort="true"></s:table.field>
				<s:table.field name="jingjz_message" label="描述" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','sfjjz','datatable7')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','sfjjz','datatable7','禁忌症问题')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		<s:table id="datatable8" label="适应症问题" autoload="false" action="hospital_common.customreview.sfsyz.query" sort="true"  limit="10" onclick="query('datatable8');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('sfsyz','datatable8')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="编号" sort="true"></s:table.field>
				<s:table.field name="syz_message" label="描述" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','sfsyz','datatable8')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','sfsyz','datatable8','适应症问题')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		<s:table id="datatable9" label="相互作用问题" autoload="false" action="hospital_common.customreview.sfxhzy.query" sort="true"  limit="10" onclick="query('datatable9');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('sfxhzy','datatable9')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="编号" sort="true"></s:table.field>
				<s:table.field name="sort_name" label="问题分类" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="问题等级" ></s:table.field>
				<s:table.field name="nwarn_message" label="警示消息" ></s:table.field>
				<s:table.field name="routename" label="给药方式" ></s:table.field>
				<s:table.field name="tiaojian" label="条件" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="formul_name" label="公式" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','sfxhzy','datatable9')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','sfxhzy','datatable9','相互作用问题')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		<s:table id="datatable11" label="检验问题" autoload="false" action="hospital_common.customreview.sflabtest.query" sort="true"  limit="10" onclick="query('datatable11');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('sflabtest','datatable11')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="编号" sort="true"></s:table.field>
				<s:table.field name="sort_name" label="问题分类" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="问题等级" ></s:table.field>
				<s:table.field name="nwarn_message" label="警示消息" ></s:table.field>
				<s:table.field name="routename" label="给药方式" ></s:table.field>
				<s:table.field name="tiaojian" label="条件" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="formul_name" label="公式" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','sflabtest','datatable11')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','sflabtest','datatable11','检验问题')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
		<s:table id="datatable12" label="孕妇用量问题" autoload="false" action="hospital_common.customreview.sfyonglyf.query" sort="true"  limit="10" onclick="query('datatable12');">
			<s:toolbar>
				<s:button label="新增" icon="" onclick="add('sfyonglyf','datatable12')"></s:button>
			</s:toolbar>
			<s:table.fields >
				<s:table.field name="id" label="编号" sort="true"></s:table.field>
				<s:table.field name="sort_name" label="问题分类" ></s:table.field>
				<s:table.field name="sys_check_level_name" label="问题等级" ></s:table.field>
				<s:table.field name="nwarn_message" label="警示消息" ></s:table.field>
				<s:table.field name="routename" label="给药方式" ></s:table.field>
				<s:table.field name="tiaojian" label="条件" ></s:table.field>
				<s:table.field name="xmmch" label="项目名称" ></s:table.field>
				<s:table.field name="formul_name" label="公式" ></s:table.field>
				<s:table.field name="value" label="值" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template"  width="70">
					<a href="javascript:void(0)" onclick="update('$[id]','sfyonglyf','datatable12')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]','sfyonglyf','datatable12','孕妇用量问题')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:tabpanel>
</s:page>

<script type="text/javascript">
		$(function (){
			//修改操作禁止修改药品
			var spbhs = '${param.spbh}';
			if(spbhs){
				$("input[name='spbh']").attr("disabled","disabled");
				getdrug('${param.spbh}');
			}

			//选择药品（切换药品）
			$("#ssks").change(function(){
				//根据drug_code去获取药品信息
				getdrug($(this).val());
				//刷新问题为：当前药品的问题
				$(".tab-pane.active").find("table[ctype='table']").click();
			});
			queryDrug();
		});


		function queryDrug(){
			var drug_code = '${param.spbh}';
			if(!drug_code){
				drug_code=$("#form").getData().spbh;
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
			var ss='${param.spbh}';
			if(ss){
				//$("input[name='spbh']").attr("readonly","true");
				qdata.spbh=ss;
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
			var spbh;
			var is_newadd;
			if('${param.spbh}'==null||'${param.spbh}'==''){
				spbh=$("#form").getData().spbh;
			}else{
				spbh='${param.spbh}';
			}
			if(s == 'sfjjz' || s == 'sfsyz'){
				is_newadd = 1;
			}else{
				is_newadd = 0;
			}
			$.modal("/w/hospital_common/customreview/"+s+"/datiledit.html","新增",{
				width:"70%",
				height:"90%",
				spbh:spbh,
				is_newadd: is_newadd,
				callback : function(e){
					query(d);
				}
			});
		}

		function addDrug(){
			var drug_code = '${param.spbh}';
			if(!drug_code){
				drug_code=$("#form").getData().spbh;
			}
			$.modal("/w/hospital_common/customreview/hisdrugmx/drugedit.html","新增",{
				width:"500px",
				height:"90%",
				drug_code:drug_code,
				callback : function(e){
					queryDrug();
				}
			});
		}


		function update(id,s,d){
			var spbh;
			if('${param.spbh}'==null||'${param.spbh}'==''){
				spbh=$("#form").getData().spbh;
			}else{
				spbh='${param.spbh}';
			}
			$.modal("/w/hospital_common/customreview/"+s+"/datiledit.html","修改",{
				width:"70%",
				height:"90%",
				id:id,
				spbh:spbh,
				callback : function(e){
					query(d);
				}
			});
		}

		function updateDrug(drug_code,xmid){
			$.modal("/w/hospital_common/customreview/hisdrugmx/drugedit.html","修改",{
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
					$.call("hospital_common.customreview."+s+".delete",{"id":id},function(e){
						query(d);
						$.call("hospital_common.dictdrug.updateCz",
								{drug_code: '${param.spbh}', zdy_cz: "删除"+wt}, function(){});
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
					$.call("hospital_common.dictdrug.updateCz",
							{drug_code: drug_code, zdy_cz: "删除药品属性"}, function(){});
				}
			});
		}

		function cancel(){
	    	$.closeModal(false);
	    }

		//新增根据drugcode去获取该药品所有信息   返回rtn：药品信息
		function getdrug(drugcode){
			 $.call("hospital_common.dictdrug.getforzdy",{"drug_code":drugcode},function(rtn){
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
			debugger
			var drugcode = $("#form").getData().spbh;
			$.modal("/w/ipc/auditresult/instruction.html?his_drug_code="+drugcode,"查看药品说明书",{
		        width:"70%",
		        height:"100%",
		        callback : function(e){
		        }
    		});
		};

</script>
