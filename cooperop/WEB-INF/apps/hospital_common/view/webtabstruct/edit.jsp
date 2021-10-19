<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="表结构信息" ismodal="true">
	<s:row>
		<s:form label="表结构信息" id="fform">
			<s:toolbar>
				<s:button icon="fa fa-edit" size="btn-sm btn-default" label="保存" onclick="save()" type="submit"></s:button>
				<s:button icon="fa fa-ban" size="btn-sm btn-default" label="取消" onclick="$.closeModal();"></s:button>
			</s:toolbar>
			<s:row>
				<input type="hidden" name="id" value="${id }"></input>
				<s:textfield cols="4" label="表名称" name="name" value="${tablename}"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
		<div class="note note-warning" style="margin: 0;
	    padding: 10px 30px 10px 15px;">
					<h5 style="font-size:12px;">温馨提示：</h5>
					<span>A、image 类型不用设置长度</span>
					<div>B、修改字段数据类型时应该判断是否可以转换，如：不能直接从 decimal 转换成 image，但是可以先删除该字段，点保存，在打开新增</div>
					<div>C、表结构默认包含以下三个属性,且不为null且不能重复添加以下属性: </div>
					<ol style="padding:5px;padding-left:15px;font-size:10px;margin:0px">
						<li>data_webservice_quene_id</li>
						<li>id</li>	
						<li>parent_id</li>
					</ol>	
				</div>
		<s:row>
		<s:table label="表结构信息" limit="0" sort="true" id="subtable" isdesign="true">
			<s:toolbar>
				<s:button icon="fa fa-file-o" size="btn-sm btn-default" label="添加" onclick="addSub()" type="submit"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="colname" label="列名" datatype="template">
					<s:textfield label="" name="colname" value="$[colname]"></s:textfield>
				</s:table.field>
				<s:table.field id="dtype" name="datatype" label="数据类型" datatype="template">
					<s:select label="" name="datatype" value="$[datatype]">
						<s:option label="numeric" value="numeric"></s:option>
						<s:option label="decimal" value="decimal"></s:option>
						<s:option label="varchar" value="varchar"></s:option>
						<s:option label="image" value="image"></s:option>
					</s:select>
				</s:table.field>
				<s:table.field name="length" label="数据长度" datatype="template">
					<s:textfield label="" name="length" value="$[length]" placeholder="浮点数类型时：如18,0"></s:textfield>
				</s:table.field>
				
				<%-- +++ --%>
				<s:table.field name="alias" label="别名" datatype="template">
					<s:textfield label="" name="alias_name" value="$[alias_name]"></s:textfield>
				</s:table.field>
				<%-- +++ --%>
				<%-- 
				<s:table.field name="nullable" label="是否允许NULL值" datatype="template">
					<s:select label="" name="nullable" value="$[nullable]">
						<s:option label="是" value="false"></s:option>
						<s:option label="否" value="true"></s:option>
					</s:select>
				</s:table.field>--%>
				<s:table.field name="oper" datatype="template" label="操作" align="center" width="80">
					<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="deleteSub($(this))">删除</a>
				</s:table.field>
			</s:table.fields>
			<s:table.data>
				<%-- c:forEach begin="0" end="${fn:length(fields) > 4 ? fn:length(fields) - 1 : 4 }" items="${fields }" varStatus="i" var="f">
					<tr >
						<td>${f.name }</td>
						<td>${f.type_name }</td>
						<td>${f.precision }</td>
						<td>${f.nullable}</td>
						<td></td>
					</tr>
				</c:forEach> --%>
				<c:forEach begin="0" end="${fn:length(fields) > 4 ? fn:length(fields) - 1 : 4 }" varStatus="i">
					<tr data-colname="${fields[i.index].name}" data-datatype="${fields[i.index].type_name}"
					 data-length="${fields[i.index].precision}" data-nullable="${fields[i.index].nullable}"
					 data-alias_name="${fields[i.index].alias_name}">
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<%-- +++ --%>
						<td></td>
						<%-- +++ --%>
					</tr>
				</c:forEach>
			</s:table.data>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		//判断列名不重复
		$("#subtable").find("tbody").delegate("tr > td > div > input","blur",function(){
			var _text = $(this).val();
			var count = 0;
			//列名不为空才验证是否重复
			if(_text){
				$("#subtable").find("tbody").find("tr").each(function(){
					var _tr = $(this);
					var _tdata = _tr.getData();
					if(_tdata.colname && _text == _tdata.colname){
						count ++;
					}
				});
				if(count>1){
					alert("该列名"+ _text +"已经存在，列名不能重复，请重新填写");
					$(this).val("");
					$(this).focus();
				}
			}
		});
		
		//数据类型长度varchar>0,或者numeric不为0,0
		$("#subtable").find("tbody").delegate("tr > td > div > input[name='length']","blur",function(){
			var _value = $(this).val();
			if(_value != null && _value != undefined && _value != ""){
				if(_value.indexOf(",")>0){
					var temp = _value.split(",")[0];
					if(temp<=0){
						alert("数据长度必须大于0");
						$(this).val("");
						$(this).focus();
					}
				}
				if(_value<=0 || _value == "0,0"){
					alert("数据长度必须大于0");
					$(this).val("");
					$(this).focus();
				}
			}
		});
		
		//设置表名称输入框不可用，不可修改表名称
		$("input[name='name']").attr("disabled","disabled");
		
	});
	function save() {
		if (!$("form").valid()) {
			return false;	
		}
		// var data = $("#fform").getData();
		var page = "hospital_common.webtabstruct.insert";
		if ($("input[name='id']").val()) {
			page = "hospital_common.webtabstruct.update";
		} else {
			delete $("input[name='id']").val();
		}
		//定义json字符串
		var data = {name:"${pageParam.tablename}",options: []}
		
		var isTypeNull = false;
		$("#subtable").find("tbody").find("tr").each(function () {
			var _tr = $(this);
			var _tdata = _tr.getData();
			//console.log(_tdata);
			//将json转换成数组然后放入到json对象的options数组中
			_tdata["colname"] = _tdata.colname;
			if (_tdata.colname && !_tdata.datatype)
				isTypeNull = true;
			else if (_tdata.colname && _tdata.datatype != 'image' && !_tdata.length)
				isTypeNull = true;
			if (_tdata.datatype == "image")
				_tdata["datatype"] = _tdata.datatype;
			else
				_tdata["datatype"] = _tdata.datatype + "(" + _tdata.length + ")";
			_tdata["nullable"] = _tdata.nullable;
			console.log(_tdata);
			if (_tdata.colname && _tdata.colname != "data_webservice_quene_id" && 
					_tdata.colname != "id"  && _tdata.colname != "parent_id") {
				data.options.push(_tdata);
			}
		});
		console.log(data);
		if (isTypeNull) {
			$.message("数据类型不能为空或者数据长度不能为空！");
			return false;
		}
		//json数据提交
		if($("input[name='id']").val() != null && $("input[name='id']").val() != "" && $("input[name='id']").val() != undefined){
			//存在表提示是否重建表
			$.confirm("表已经存在是否重新建表？", function(btn) {
				if (btn) {
					$.call(page, {json: $.toJSON(data)}, function(rtn){
						$.message("创建成功！", function() {
							$.closeModal(true); 
						});
					});
				}
			});
		}else{
			$.call(page, {json: $.toJSON(data)}, function(rtn) {
				$.closeModal(true);
			});
		}
	}
	
	function addSub() {
		$("#subtable").newBlankRow();
	}
	function deleteSub(jobj) {
		$("#subtable").DataTable().row(jobj.parent().parent()).remove().draw(false);
	}
</script>