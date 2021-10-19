<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page ismodal="true" title="">
	<%
		String gj = request.getParameter("gj");
		Map<String, Object> map = CommonFun.json2Object(gj, Map.class);
		List<Map<String, Object>> jgs = (List<Map<String, Object>>) map.get("attr_gj_jg");
		pageContext.setAttribute("jgs", jgs);
		List<Map<String, Object>> gjs = (List<Map<String, Object>>) map.get("attr_gj");
		pageContext.setAttribute("gjs", gjs);
	%>
	<s:row style="display:none;" id="selfsetting">
		<s:table label="高级设置" select="multi" id="selftalbe" color="yellow">
			<s:toolbar>
				<s:button label="引入" onclick="importset1()"></s:button>
				<s:button label="取消" onclick="closerow()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="shezhibs" label="标识"></s:table.field>
				<s:table.field name="shezmc" label="设置名称"></s:table.field>
				<s:table.field name="shezlx" label="类型"></s:table.field>
				<s:table.field name="quesz" label="缺省值"></s:table.field>
				<s:table.field name="shurz" label="输入值"></s:table.field>
				<s:table.field name="shuom" label="说明" width="300"></s:table.field>
			</s:table.fields>
			<s:table.data>
				<c:forEach items="${gjs }" var="gj" varStatus="a">
					<tr id="childs">
						<td><s:textfield name="shezhibs" value="${gj.shezhibs }" /></td>
						<td><s:textfield name="shezmc" value="${gj.shezmc }" /></td>
						<td><s:select name="shezlx" value="${gj.shezlx }">
								<s:option label="字符型" value="字符型" />
								<s:option label="逻辑型" value="逻辑型" />
								<s:option label="枚举型" value="枚举型" />
								<s:option label="整数型" value="整数型" />
							</s:select></td>
						<td><s:textfield name="quesz" value="${gj.quesz }" /></td>
						<td><s:textfield name="shurz" value="${gj.shurz }" /></td>
						<td><s:textarea name="shuom" value="${gj.shuom }"
								autosize="false" /></td>
					</tr>
				</c:forEach>
			</s:table.data>
		</s:table>
	</s:row>
	<s:row style="display:none;" id="otherdj">
		<s:form label="单据选择" id="djform" color="yellow">
			<s:row>
				<s:autocomplete label="单据" name="djselect" action="crdc.designer.query" cols="2" onchange="querysetting()">
					<s:option value="$[id]" label="$[id]">$[flag] ($[id])</s:option>
				</s:autocomplete>
			</s:row>
		</s:form>
		<s:table label="单据设置" action="crdc.designer.querySetting" autoload="false" id="djsetting" select="multi" color="yellow">
			<s:toolbar>
				<s:button label="引入" onclick="importset2()"></s:button>
				<s:button label="取消" onclick="closerow()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="shezhibs" label="标识"></s:table.field>
				<s:table.field name="shezmc" label="设置名称"></s:table.field>
				<s:table.field name="shezlx" label="类型"></s:table.field>
				<s:table.field name="quesz" label="缺省值"></s:table.field>
				<s:table.field name="shurz" label="输入值"></s:table.field>
				<s:table.field name="shuom" label="说明" width="300"></s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
	<s:row>
		<s:form label="机构选择" id="jgform">
			<s:row>
				<s:autocomplete name="jgselect" label="选择机构" action="crdc.designer.queryJG" onchange="showJGSet()" cols="2">
					<s:option value="$[jigid]" label="$[jigid] ($[jigname])">$[jigid] ($[jigname])</s:option>
				</s:autocomplete>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="table_" label="机构设置列表" select="multi">
			<s:toolbar>
				<s:button label="新增" onclick="addr()"></s:button>
				<s:button label="引用其他单据设置" onclick="importjg()"></s:button>
				<s:button label="删除" onclick="deleter()"></s:button>
				<s:button label="保存" onclick="save()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="jigid" label="机构id"></s:table.field>
				<s:table.field name="shezhibs" label="标识"></s:table.field>
				<s:table.field name="shezmc" label="设置名称"></s:table.field>
				<s:table.field name="shezlx" label="类型"></s:table.field>
				<s:table.field name="quesz" label="缺省值"></s:table.field>
				<s:table.field name="shurz" label="输入值"></s:table.field>
				<s:table.field name="shuom" label="说明" width="300"></s:table.field>
			</s:table.fields>
			<s:table.data>
				<c:if test="${not empty jgs }">
					<c:forEach items="${jgs }" var="jg" varStatus="a">
						<tr jgid="${jg.jigid }" style="display:none;">
							<td><s:textfield name="jigid" value="${jg.jigid }" readonly="true"/></td>
							<td><s:textfield name="shezhibs" value="${jg.shezhibs }" readonly="true"/></td>
							<td><s:textfield name="shezmc" value="${jg.shezmc }" readonly="true"/></td>
							<td><s:select name="shezlx" value="${jg.shezlx }" readonly="true">
									<s:option label="字符型" value="字符型" />
									<s:option label="逻辑型" value="逻辑型" />
									<s:option label="枚举型" value="枚举型" />
									<s:option label="整数型" value="整数型" />
								</s:select></td>
							<td><s:textfield name="quesz" value="${jg.quesz }" /></td>
							<td><s:textfield name="shurz" value="${jg.shurz }" /></td>
							<td><s:textarea name="shuom" value="${jg.shuom }"
									autosize="false" /></td>
						</tr>
					</c:forEach>
				</c:if>
				<tr id="childs">
					<td><s:textfield name="jigid" value="" readonly="true"/></td>
					<td><s:textfield name="shezhibs" value="" readonly="true"/></td>
					<td><s:textfield name="shezmc" value="" readonly="true"/></td>
					<td><s:select name="shezlx" value="" readonly="true">
							<s:option label="字符型" value="字符型" />
							<s:option label="逻辑型" value="逻辑型" />
							<s:option label="枚举型" value="枚举型" />
							<s:option label="整数型" value="整数型" />
						</s:select></td>
					<td><s:textfield name="quesz" value="" /></td>
					<td><s:textfield name="shurz" value="" /></td>
					<td><s:textarea name="shuom" value=""
							autosize="false" /></td>
				</tr>
			</s:table.data>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(document).ready(function() {
	})
	function save() {
		var data = {};
		data.attr_gj = [];
		var trdata = $("#table_ > tbody > tr");
		trdata.each(function(index) {
			var tdd = $(this).getData();
			if (!tdd.shezhibs) {
				return false;
			}
			data.attr_gj.push(tdd);
		});
		$.closeModal(data);
	}
	function deleter() {
		$("#table_ > tbody").find("input[type=checkbox]").each(function() {
			if (this.checked) {
				$(this).parents("tr").remove();
			}
		});
	}
	function addr() {
		$("#otherdj").hide();
		$("#selfsetting").show();
	}
	function importjg(){
		$("#selfsetting").hide();
		$("#otherdj").show();
	}
	function closerow(){
		$("#selfsetting").hide();
		$("#otherdj").hide();
	}
	function querysetting(){
		var data = $("#djform").getData();
		var d = data.djselect;
		$("#djsetting").params({"djlx":d});
		$("#djsetting").refresh();
	}
	function showJGSet(){
		var data = $("#jgform").getData();
		var d = data.jgselect;
		$("#table_ > tbody").find("tr").each(function(index,o){
			$(this).hide();
			if($(this).find("input[name='jigid']").attr("jgid") == d){
				$(this).show();
			}
		});
	}
	function importset1(){
		var data = $("#jgform").getData();
		if(data.jgselect){
			var trdata = $("#selftalbe > tbody > tr");
			trdata.each(function(){
				if($(this).find("input[type=checkbox]").val() == "on"){
					var tdd = $(this).getData();
					var tr = $("#table_ > tbody > tr:last");
			 		var trnew = tr.clone();
			 		trnew.find("input[name='jigid']").val(tdd.jgselect);
			 		trnew.find("input[name='shezhibs']").val(tdd.shezhibs);
			 		trnew.find("input[name='shezmc']").val(tdd.shezmc);
			 		trnew.find("input[name='shezlx']").val(tdd.shezlx);
			 		trnew.find("input[name='quesz']").val(tdd.quesz);
			 		trnew.find("input[name='shurz']").val(tdd.shurz);
			 		trnew.find("input[name='shuom']").val(tdd.shuom);
			 		trnew.insertBefore(tr);
				}
			  }); 
		}
	}
	function importset2(){
		var data = $("#jgform").getData();
		if(data.jgselect){
			var trdata = $("#djsetting > tbody > tr");
			trdata.each(function(){
				if($(this).find("input[type=checkbox]").val() == "on"){
					var tdd = $(this).getData();
					var tr = $("#table_ > tbody > tr:last");
			 		var trnew = tr.clone();
			 		trnew.find("input[name='jigid']").val(tdd.jgselect);
			 		trnew.find("input[name='shezhibs']").val(tdd.shezhibs);
			 		trnew.find("input[name='shezmc']").val(tdd.shezmc);
			 		trnew.find("input[name='shezlx']").val(tdd.shezlx);
			 		trnew.find("input[name='quesz']").val(tdd.quesz);
			 		trnew.find("input[name='shurz']").val(tdd.shurz);
			 		trnew.find("input[name='shuom']").val(tdd.shuom);
			 		trnew.insertBefore(tr);
				}
			  }); 
		}else{
			$.message("请先选择机构！");
		}
	}
</script>
