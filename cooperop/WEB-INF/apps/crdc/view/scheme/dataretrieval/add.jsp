<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" ismodal="true">
	<s:row>
		<s:form border="0" id="myform">
			<s:row>
				<s:select label="所属产品" name="system_product_code" action="application.common.listProducts" value="${system_product_code }" required="true">
					<s:option value="$[code]" label="$[name]"></s:option>
				</s:select>
				<s:textfield label="方案编号" name="fangabh" required="required"
					value="${'Y' eq param.is_import?'':fangabh}" islabel="${not empty $return.fangabh and 'Y' ne param.is_import}"></s:textfield>
				<s:textfield label="方案类型" name="fangalx" required="required"
					value="${'Y' eq param.is_import?'':fangalx}" islabel="${not empty $return.fangabh and 'Y' ne param.is_import}"></s:textfield>
				<s:textfield label="方案名称" name="fangamch"
					value="${fangamch}"></s:textfield>
			</s:row> 
			<s:row>
				<s:textfield label="窗口标题" name="dialog_cap" value="${dialog_cap}"></s:textfield>
				<s:textfield label="窗口高度"  name="dialog_hei"
					value="${dialog_hei}" placeholder="如‘500px’或者屏幕宽度‘50%’"></s:textfield>
				<s:textfield label="窗口宽度" name="dialog_wid"
					value="${dialog_wid}" placeholder="如‘500px’或者屏幕宽度‘50%’"></s:textfield>
				<s:select label="焦点位置" name="focusto" value="${focusto}">
					<s:option value="助记码" label="助记码"></s:option>
					<s:option value="数据区" label="数据区"></s:option>
					<s:option value="分类树" label="分类树"></s:option>
				</s:select>
			</s:row>
			<s:row>
				<s:switch label="显示分类树" name="tree_visib" value="${tree_visib}"
				offvalue="否" onvalue="是">
				</s:switch>
				<s:select label="分类方案编号" name="hzcode" value="${focusto}">
					<s:option value="bmzl" label="bmzl"></s:option>
					<s:option value="ckspkc_show" label="ckspkc_show"></s:option>
					<s:option value="ckzl" label="ckzl"></s:option>
					<s:option value="dj_czqxkz" label="dj_czqxkz"></s:option>
					<s:option value="jglist" label="jglist"></s:option>
					<s:option value="spzl" label="spzl"></s:option>
					<s:option value="wldwzl" label="wldwzl"></s:option>
					<s:option value="zhiyzl" label="zhiyzl"></s:option>
				</s:select>
				<s:textfield label="分类树标题" name="tree_cap" cols="2"
					value="${tree_cap}"></s:textfield>
			</s:row>
			<s:row style="display:none;">
				<s:checkbox></s:checkbox>
			</s:row>
			<s:row>
				<div class="cols4">
					<div class="control-content">
						<div ctype="checkbox" class="checkbox-list">
							<label class="checkbox-inline"> <input type="checkbox"
								name="multisel"
								<c:if test="${$return.multisel=='是'}">checked="true"</c:if> />允许多选
							</label> <label class="checkbox-inline"> <input type="checkbox"
								name="retu_one"
								<c:if test="${$return.retu_one=='是'}">checked="true"</c:if> />单行返回
							</label> <label class="checkbox-inline"> <input type="checkbox"
								name="foneretu"
								<c:if test="${$return.foneretu=='是'}">checked="true"</c:if> />查找单行返回
							</label> <label class="checkbox-inline"> <input type="checkbox"
								name="zyfilter_beactive"
								<c:if test="${$return.zyfilter_beactive=='是'}">checked="true"</c:if> />资源过滤
							</label> <label class="checkbox-inline"> <input type="checkbox"
								name="isrefresh"
								<c:if test="${$return.isrefresh=='是'}">checked="true"</c:if> />单据临时数据回调
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
				<s:textfield label="隐藏字段"  name="undispflds"
					value="${undispflds}"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="字段顺序" name="displyflds" value="${displyflds}"></s:textfield>
				<s:textfield label="编辑顺序" name="editflds" value="${editflds}"></s:textfield>
				<s:textfield label="手机展示字段"  name="app_fields"
					value="${app_fields}" placeholder="请按照【标题，副标题，内容，状态】顺序填写，逗号隔开，允许用，，跳过" cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:switch label="有子表" name="subgrid" value="${subgrid}" offvalue="否" onvalue="是">
				</s:switch>
				<s:textfield label="子表隐藏字段" name="unsubdisflds"
					value="${unsubdisflds}"></s:textfield>
				<s:textfield label="子表字段顺序" name="subdisflds" value="${subdisflds}"></s:textfield>
				<s:switch label="跨数据库连接" name="islink"  value="${islink}" offvalue="否" onvalue="是">
				</s:switch>
				${islink}
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:tabpanel>
			<s:form label="资料检索SQL" icon="fa fa-gift" active="true" id="myform1">
				<s:row>
					<s:textarea cols="4" height="300" label="SQL" name="zdysqls"
						autosize="false" >${zdysqls}</s:textarea>
				</s:row>
			</s:form>
			<s:form label="正向过滤SQL" icon="fa fa-gift" id="myform2">
				<s:row>
					<s:textarea cols="4" height="300" label="SQL" name="zyfilter_zxsql"
						autosize="false">${zyfilter_zxsql}</s:textarea>
				</s:row>
			</s:form>
			<s:form label="反向过滤SQL" icon="fa fa-gift" id="myform3">
				<s:row>
					<s:textarea cols="4" height="300" label="SQL" name="zyfilter_fxsql"
						autosize="false">${zyfilter_fxsql}</s:textarea>
				</s:row>
			</s:form>
			<s:form label="子表SQL" icon="fa fa-gift" id="myform4">
				<s:row>
					<s:textarea cols="4" height="300" label="SQL" name="subdatasql"
						autosize="false">${subdatasql}</s:textarea>
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
			data[$(this).attr("name")] = $(this).val();
		});
		var ism = '${param.is_import}';
		if(!${not empty $return.fangabh} || ism  == 'Y'){
			$.call("crdc.scheme.dataretrieval.save", data, function(data) {
				if (data == 'Y') {
					alert("保存成功!");
					$.closeModal(true);
					//location.href = "add.html";
				}
			});
		} else {
			data['fangabh']='${fangabh}';
			data['fangalx']='${fangalx}';
			$.call("crdc.scheme.dataretrieval.update", data, function(data) {
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
