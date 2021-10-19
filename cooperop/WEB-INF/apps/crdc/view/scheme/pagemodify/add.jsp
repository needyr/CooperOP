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
				<s:textfield label="方案名称" name="fangamch" 
					value="${fangamch}"></s:textfield>
				<s:switch label="批量执行方案" name="batch_exe" value="${batch_exe }" onvalue="Y" offvalue="N"></s:switch>
				<s:switch label="关闭单据" name="is_close" value="${is_close }" onvalue="1" offvalue="0"></s:switch>
			</s:row>
			<s:row>
				<s:textarea cols="4" height="300" label="SQL" name="zdysqls"
						autosize="false" >${zdysqls}</s:textarea>
			</s:row>
		</s:form>
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
// 		console.log(data);
		var ism = '${param.is_import}';
		if(!${not empty $return.fangabh} || ism  == 'Y'){
			$.call("crdc.scheme.pagemodify.save", data, function(data) {
				if (data == 'Y') {
					alert("保存成功!");
					$.closeModal(true);
					//location.href = "add.html";
				}
			});
		} else {
			data['fangabh']='${fangabh}';
			data['fangalx']='${fangalx}';
			$.call("crdc.scheme.pagemodify.update", data, function(data) {
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
