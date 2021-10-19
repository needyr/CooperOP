<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/header.jsp?ismodal=true"></c:import>
<%
	String str = request.getParameter("attdata");
	Map<String, Object> map = CommonFun.json2Object(str, Map.class);
	Map<String, Object> attrs = (Map<String, Object>) map.get("attrs");
	List<Map<String, Object>> contents = (List<Map<String, Object>>) map.get("contents");
	pageContext.setAttribute("table", attrs);
	pageContext.setAttribute("contents", contents);
%>
<div class="row">
	<div class="col-md-12 ">
		<div class="portlet box purple ">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-gift"></i>
				</div>
			</div>
			<div class="portlet-body">
				<form class="form-horizontal" id="setFrom">
					<div class="row-fluid">
						<div class="cols">
							<label class="control-label">中文说明</label>
							<div class="control-content">
								<input type="text" name="label" class="form-control"
									value="${table.label }" />
							</div>
						</div>
						<div class="cols">
							<label class="control-label">备注</label>
							<div class="control-content">
								<input type="text" name="placeholder" class="form-control"
									value="${table.placeholder }" />
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="cols">
							<label class="control-label">记录提交执行函数</label>
							<div class="control-content">
								<input type="text" name="recordSubmitAction" class="form-control"
									value="${table.recordSubmitAction }" />
							</div>
						</div>
						<div class="cols">
							<label class="control-label">记录删除执行函数</label>
							<div class="control-content">
								<input type="text" name="recordDeleteAction" class="form-control"
									value="${table.recordDeleteAction }" />
							</div>
						</div>
						<div class="cols">
							<label class="control-label">记录增加执行函数</label>
							<div class="control-content">
								<input type="text" name="recordAddtAction" class="form-control"
									value="${table.recordAddtAction }" />
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="cols">
							<label class="control-label">记录行校验函数</label>
							<div class="control-content">
								<input type="text" name="recordCheckAction" class="form-control"
									value="${table.recordCheckAction }" />
							</div>
						</div>
						<div class="cols">
							<label class="control-label">记录移动执行函数</label>
							<div class="control-content">
								<input type="text" name="recordMoveAction" class="form-control"
									value="${table.recordMoveAction }" />
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="cols">
						</div>
						<div class="cols">
							<button type="submit" onclick="save();" class="btn green">保存</button>
							<button type="button" class="btn red" onclick="returnback()">取消</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-md-12 ">
		<button type="add" class="btn red" onclick="addc()">新增字段</button>
		<button type="add" class="btn red" onclick="modifyc()">修改字段</button>
		<button type="delete" class="btn red" onclick="deletec()">删除字段</button>
		<table id="table_"
			class="table table-striped table-bordered table-hover table-nowrap">
			<thead>
				<tr>
					<th></th>
					<th>字段名称</th>
					<th>字段类型</th>
					<th>显示标题</th>
					<th>长度</th>
					<th>显示长度</th>
					<th>小数位</th>
					<th>显示小数位</th>
					<th>可编辑</th>
					<th>排列</th>
					<th>进入执行函数</th>
					<th>双击执行函数</th>
					<th>修改执行函数</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${contents }" var="f">
				<tr >
					<td><input type="checkbox" name="selected"/></td>
					<td>${f.attrs.name }<input name="name" type="hidden" value="${f.attrs.name }"/></td>
					<td>${f.attrs.type }<input name="type" type="hidden" value="${f.attrs.type }"/></td>
					<td>${f.attrs.label }<input name="label" type="hidden" value="${f.attrs.label }"/></td>
					<td>${f.attrs.size }<input name="size" type="hidden" value="${f.attrs.size }"/></td>
					<td>${f.attrs.maxlength }<input name="maxlength" type="hidden" value="${f.attrs.maxlength }"/></td>
					<td>${f.attrs.digitsize }<input name="digitsize" type="hidden" value="${f.attrs.digitsize }"/></td>
					<td>${f.attrs.digits }<input name="digits" type="hidden" value="${f.attrs.digits }"/></td>
					<td>${f.attrs.readonly }<input name="readonly" type="hidden" value="${f.attrs.readonly }"/></td>
					<td>${f.attrs.align }<input name="align" type="hidden" value="${f.attrs.align }"/></td>
					<td>${f.attrs.enteraction }<input name="enteraction" type="hidden" value="${f.attrs.enteraction }"/></td>
					<td>${f.attrs.dblaction }<input name="dblaction" type="hidden" value="${f.attrs.dblaction }"/></td>
					<td>${f.attrs.modifyaction }<input name="modifyaction" type="hidden" value="${f.attrs.modifyaction }"/></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<c:import url="/footer.jsp?ismodal=true"></c:import>
<script>
	function save() {
		var data = $("#setFrom").getData();
		var trdata = $("#table_ > tbody > tr");
		var children = [];
		trdata.each(function(){
			var child = "";
			var inp = this.find("input");
			for(var i=0; i<inp.length;i++){
				if(inp[i].attr("name")!="selected"){
					var t = inp[i].attr("name")+":"+inp[i].val();
					if(child==""){
						child = t;
					}else{
						child += ","+t;
					}
				}
			}
			if(child != ""){
				child = "{" + child + "}";
				children.push($.parseJSON(child));
			}
		  });
		data.children = children;
		$.closeModal(data);
	}
	function returnback() {
		$.closeModal(false);
	}
 	function addc() {
		$.modal("addc.html", "新增", {
			callback: function(rtn) {
				if(rtn){
					var t = [];
					t.push('<tr>');
					t.push('<td>${f.attrs.name }<input name="name" type="hidden" value="${f.attrs.name }"/></td>');
					t.push('<td>${f.attrs.type }<input name="type" type="hidden" value="${f.attrs.type }"/></td>');
					t.push('<td>${f.attrs.label }<input name="label" type="hidden" value="${f.attrs.label }"/></td>');
					t.push('<td>${f.attrs.size }<input name="size" type="hidden" value="${f.attrs.size }"/></td>');
					t.push('<td>${f.attrs.maxlength }<input name="maxlength" type="hidden" value="${f.attrs.maxlength }"/></td>');
					t.push('<td>${f.attrs.digitsize }<input name="digitsize" type="hidden" value="${f.attrs.digitsize }"/></td>');
					t.push('<td>${f.attrs.digits }<input name="digits" type="hidden" value="${f.attrs.digits }"/></td>');
					t.push('<td>${f.attrs.readonly }<input name="readonly" type="hidden" value="${f.attrs.readonly }"/></td>');
					t.push('<td>${f.attrs.align }<input name="align" type="hidden" value="${f.attrs.align }"/></td>');
					t.push('<td>${f.attrs.enteraction }<input name="enteraction" type="hidden" value="${f.attrs.enteraction }"/></td>');
					t.push('<td>${f.attrs.dblaction }<input name="dblaction" type="hidden" value="${f.attrs.dblaction }"/></td>');
					t.push('<td>${f.attrs.modifyaction }<input name="modifyaction" type="hidden" value="${f.attrs.modifyaction }"/></td>');
					t.push('</tr>');
					t.join('');
					$("#table_ > tbody").append(t);
				}
			}
		})
	} 
	function deletec() {
		$("#table_ > tbody").find("input[type=checkbox]").each(function(){
			if(this.checked){
				$(this).parents("tr").remove();
			}
		});
	}
	 function modifyc() {
		var s = $("#table_ > tbody").find("input:checkbox[name='selected']:checked")[0];
		var tr = $(s).parents("tr");
		var inp = tr.find("input");
		var child = "";
		tr.find("input").each(function(){
			var $this = $(this);
			if($this.attr("name")!="selected"){
				var t ;
				if($this.val()){
					t = $this.attr("name")+":'"+$this.val()+"'";
				}else{
					t = $this.attr("name")+":"+null;
				}
				if(child==""){
					child = t;
				}else{
					child += ","+t;
				}
			}
		});
		if(child != ""){
			child = "{" + child + "}";
		}
		$.modal("modifyc.html", "修改", {
			tdata:$.parseJSON(child),
			callback: function(rtn) {
				if(rtn){
					$(tr).remove();
					var t = [];
					t.push('<tr>');
					t.push('<td>${f.attrs.name }<input name="name" type="hidden" value="${f.attrs.name }"/></td>');
					t.push('<td>${f.attrs.type }<input name="type" type="hidden" value="${f.attrs.type }"/></td>');
					t.push('<td>${f.attrs.label }<input name="label" type="hidden" value="${f.attrs.label }"/></td>');
					t.push('<td>${f.attrs.size }<input name="size" type="hidden" value="${f.attrs.size }"/></td>');
					t.push('<td>${f.attrs.maxlength }<input name="maxlength" type="hidden" value="${f.attrs.maxlength }"/></td>');
					t.push('<td>${f.attrs.digitsize }<input name="digitsize" type="hidden" value="${f.attrs.digitsize }"/></td>');
					t.push('<td>${f.attrs.digits }<input name="digits" type="hidden" value="${f.attrs.digits }"/></td>');
					t.push('<td>${f.attrs.readonly }<input name="readonly" type="hidden" value="${f.attrs.readonly }"/></td>');
					t.push('<td>${f.attrs.align }<input name="align" type="hidden" value="${f.attrs.align }"/></td>');
					t.push('<td>${f.attrs.enteraction }<input name="enteraction" type="hidden" value="${f.attrs.enteraction }"/></td>');
					t.push('<td>${f.attrs.dblaction }<input name="dblaction" type="hidden" value="${f.attrs.dblaction }"/></td>');
					t.push('<td>${f.attrs.modifyaction }<input name="modifyaction" type="hidden" value="${f.attrs.modifyaction }"/></td>');
					t.push('</tr>');
					t.join('');
					$("#table_ > tbody").append(t);
				}
			}
		});
	} 
</script>
