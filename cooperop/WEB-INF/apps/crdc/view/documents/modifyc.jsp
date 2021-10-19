<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
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
							<label class="control-label">字段名</label>
							<div class="control-content">
								<input type="text" name="name" class="form-control"
									value="${table.name }" />
							</div>
						</div>
						<div class="cols">
							<label class="control-label">标题</label>
							<div class="control-content">
								<input type="text" name="label" class="form-control"
									value="${table.label }" />
							</div>
						</div>
						<div class="cols">
							<label class="control-label">显示宽度</label>
							<div class="control-content">
								<input type="text" name="maxlength" class="form-control"
									value="${table.maxlength }" />
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="cols">
							<label class="control-label">显示小数位</label>
							<div class="control-content">
								<input type="text" name="digits" class="form-control"
									value="${table.digits }" />
							</div>
						</div>
						<div class="cols">
							<label class="control-label"></label>
							<div class="control-content">
								<div ctype="checkbox" class="checkbox-list" required="required">
									<label class="checkbox-inline"> <input type="checkbox"
										name="readonly" value="option2">可编辑
									</label> <label class="checkbox-inline"> <input type="checkbox"
										name="required" value="option3">必填
									</label> <label class="checkbox-inline"> <input type="checkbox"
										name="encryption" value="option3">加密
									</label>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="cols2">
							<label class="control-label">字段值排列方式</label>
							<div class="control-content">
								<div ctype="radio" class="radio-list" required="required">
									<label class="radio-inline"> <input type="radio"
										name="align" value="option1" required="required">居左
									</label> <label class="radio-inline"> <input type="radio"
										name="align" value="option2" required="required">居中
									</label> <label class="radio-inline"> <input type="radio"
										name="align" value="option3" required="required">居右
									</label>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="cols">
							<label class="control-label">进入执行函数</label>
							<div class="control-content">
								<input type="text" name="enteraction" class="form-control"
									value="${table.enteraction }" />
							</div>
						</div>
						<div class="cols">
							<label class="control-label">双击执行函数</label>
							<div class="control-content">
								<input type="text" name="dblaction" class="form-control"
									value="${table.dblaction }" />
							</div>
						</div>
						<div class="cols">
							<label class="control-label">修改执行函数</label>
							<div class="control-content">
								<input type="text" name="modifyaction" class="form-control"
									value="${table.modifyaction }" />
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="cols"></div>
						<div class="cols">
							<button type="add" onclick="addc()">新增字段</button>
							<button type="delete" onclick="deletec()">删除字段</button>
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
					<tr>
						<td><input type="checkbox" name="selected" /></td>
						<td>${f.attrs.name }<input name="name" type="hidden"
							value="${f.attrs.name }" /></td>
						<td>${f.attrs.type }<input name="type" type="hidden"
							value="${f.attrs.type }" /></td>
						<td>${f.attrs.label }<input name="label" type="hidden"
							value="${f.attrs.label }" /></td>
						<td>${f.attrs.size }<input name="size" type="hidden"
							value="${f.attrs.size }" /></td>
						<td>${f.attrs.maxlength }<input name="maxlength"
							type="hidden" value="${f.attrs.maxlength }" /></td>
						<td>${f.attrs.digitsize }<input name="digitsize"
							type="hidden" value="${f.attrs.digitsize }" /></td>
						<td>${f.attrs.digits }<input name="digits" type="hidden"
							value="${f.attrs.digits }" /></td>
						<td>${f.attrs.readonly }<input name="readonly" type="hidden"
							value="${f.attrs.readonly }" /></td>
						<td>${f.attrs.align }<input name="align" type="hidden"
							value="${f.attrs.align }" /></td>
						<td>${f.attrs.enteraction }<input name="enteraction"
							type="hidden" value="${f.attrs.enteraction }" /></td>
						<td>${f.attrs.dblaction }<input name="dblaction"
							type="hidden" value="${f.attrs.dblaction }" /></td>
						<td>${f.attrs.modifyaction }<input name="modifyaction"
							type="hidden" value="${f.attrs.modifyaction }" /></td>
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
			for(int i=0; i<inp.length;i++){
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
				children.push($.paseJSON(child));
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
				this.parent().remove();
			}
		});
	}
	function modifyc() {
		var inp = $("#table_ > tbody").find("input[type=checkbox]").parent().find("input");
		var child = "";
		for(int i=0; i<inp.length;i++){
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
		}
		$.modal("addc.html", "新增", {
			tdata:$.paseJSON(child),
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
		});
	}
</script>
