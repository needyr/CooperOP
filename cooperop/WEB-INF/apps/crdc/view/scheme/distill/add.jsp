<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" ismodal="true">
	<s:row>
		<s:form border="0" id="myfrom">
			<s:row>
				<s:select label="所属产品" name="system_product_code" action="application.common.listProducts" value="${system_product_code }" required="true">
					<s:option value="$[code]" label="$[name]"></s:option>
				</s:select>
				<s:textfield label="方案编号" name="fangabh" required="required"
					value="${'Y' eq param.is_import?'':fangabh}" islabel="${not empty $return.fangabh and 'Y' ne param.is_import}"></s:textfield>
				<s:textfield label="方案类型" name="fangalx" required="required"
					value="${'Y' eq param.is_import?'':fangalx}" islabel="${not empty $return.fangalx and 'Y' ne param.is_import}"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="方案名称" name="fangamch" cols="2"
					value="${fangamch}"></s:textfield>
			</s:row>
			<s:row style="display:none;">
				<s:checkbox></s:checkbox>
			</s:row>
			<s:row>
				<div class="cols4">
					<div class="control-content">
						<div ctype="checkbox" class="checkbox-list">
							<label class="checkbox-inline"> <input type="checkbox"
								name="jilubaohu"
								<c:if test="${$return.jilubaohu=='是'}">checked="true"</c:if> />登记保护
							</label> <label class="checkbox-inline"> <input type="checkbox"
								name="emptylyb"
								<c:if test="${$return.emptylyb=='是'}">checked="true"</c:if> />清空原单
							</label> <label class="checkbox-inline"> <input type="checkbox"
								name="selectmx"
								<c:if test="${$return.selectmx=='是'}">checked="true"</c:if> />可选择明细
							</label> <label class="checkbox-inline"> <input type="checkbox"
								name="islink"
								<c:if test="${$return.islink=='是'}">checked="true"</c:if> />跨数据库连接
							</label>
						</div>
					</div>
				</div>
			</s:row>
			<s:row>
				<s:textfield label="查找字段" name="filterflds" value="${filterflds}"></s:textfield>
				<s:select label="辅助查找方式" name="searchtype" value="${searchtype}">
					<s:option value="PartialMatch" label="PartialMatch"></s:option>
					<s:option value="LeftMatch" label="LeftMatch"></s:option>
					<s:option value="RightMatch" label="RightMatch"></s:option>
				</s:select>
				<s:textfield label="手机展示字段"  name="app_fields"
					value="${app_fields}" placeholder="请按照【标题，副标题，内容，状态】顺序填写，逗号隔开，允许用，，跳过" cols="2"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
	<s:tabpanel>
			<s:form label="单据选择SQL" icon="fa fa-gift" active="true" id="myform1">
				<s:row>
					<s:textarea cols="4" height="300" label="SQL" name="sql_text"
						autosize="false" >${sql_text}</s:textarea>
				</s:row>
			</s:form>
			<s:form label="单据抬头SQL" icon="fa fa-gift" id="myform2">
				<s:row>
					<s:textarea cols="4" height="300" label="SQL" name="m_sql"
						autosize="false">${m_sql}</s:textarea>
				</s:row>
			</s:form>
			<s:form label="单据明细SQL" icon="fa fa-gift" id="myform3">
				<s:row>
					<s:textarea cols="4" height="300" label="SQL" name="d_sql"
						autosize="false">${d_sql}</s:textarea>
				</s:row>
			</s:form>
		</s:tabpanel>
	</s:row>
	
	<div class="row">
		<div class="col-md-1 col-md-offset-9">
			<button type="button" class="btn btn-sm btn-default" onclick="importO();">引用其它方案</button>
		</div>
		<div class="col-md-1 col-md-offset-1">
			<button onclick="save()" id="sub" type="button"
				class="btn btn-sm btn-default">保存</button>
		</div>
	</div>
</s:page>
<script>
	function save() {
		var data = $(document).getData();
		$("input:checkbox").each(function() {
			if ($(this).attr("checked") == 'checked') {
				$(this).val('是');
			} else {
				$(this).val('否');
			}
			//alert($(this).val()+"--"+$(this).attr("name"));
			data[$(this).attr("name")] = $(this).val();
		});
		// var data = $("#myform").getData();
		//for (var i in data)  alert(i + ':' + data[i]);
		//var data1 = $("#myform1").getData();
		//var data2 = $("#myform2").getData();
		//var data3 = $("#myform3").getData();
		//console.log(data2);
		//$.extend(true, data, data1, data2, data3);
		var ism = '${param.is_import}';
		if(!${not empty $return.fangabh} || ism  == 'Y'){
			$.call("crdc.scheme.distill.save", data, function(data) {
				if (data == 'Y') {
					alert("保存成功!");
					$.closeModal(true);
					//location.href = "add.html";
				}
			});
		} else {
			data['fangabh']='${fangabh}';
			data['fangalx']='${fangalx}';
			$.call("crdc.scheme.distill.update", data, function(data) {
				if (data == 'Y') {
					alert("操作成功!");
					$.closeModal(true);
				}
			});
		}
	}
	function importO(){
		$.modal("query.html", "引用方案", {
			callback : function(rr) {
				if(rr){
					var d = rr[0]['data'];
					location.href = "add.html?is_import=Y&fangabh="+d.fangabh+"&fangalx="+d.fangalx;
					/* $.call("crdc.scheme.dataretrieval.add", rr[0]['data'] , function(d){
						console.log(d);
						$("#myform").setData(d);
					}); */
				}
	    	}
		})
	}

</script>
