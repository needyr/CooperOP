<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="">
	<s:row>
		<s:form id="myform" label="" >
			<s:toolbar>
				<s:button label="保存" onclick="save();"></s:button>
			</s:toolbar>
			<s:row>
				<input type="hidden" name="position_id" id="position_id" value="${position_id }"/>
				<input type="hidden" name="id" value="${pageParam.id }"/>
				<c:if test="${empty pageParam.id }">
					<c:if test="${empty pageParam.depid }">
						<s:autocomplete label="部 门" name="depid" action="setting.dep.querydep" value="${depid }" required="true">
							<s:option label="$[name]" value="$[id]">$[code]-$[name]</s:option>
						</s:autocomplete>
					</c:if>
					<c:if test="${not empty pageParam.depid }">
						<input type="hidden" name="depid" value="${pageParam.depid }"/>
					</c:if>
				</c:if>
			</s:row>
			<s:row>
				<s:textfield label="岗位名称" name="name" maxlength="20" required="true" value="${name}"></s:textfield>
				<s:textfield label="岗位编码" name="no" maxlength="32" value="${no}"></s:textfield>
				<s:textfield label="别 名" name="othername" maxlength="20" value="${othername}"></s:textfield>
					<s:textfield label="编 制：" name="compilation" maxlength="20" value="${compilation}"></s:textfield>
			</s:row>
			<s:row>
				<s:autocomplete label="上级岗位" name="superior" action="setting.post.query" id="postid" value="${superior }" text="${superior_name}">
					<s:option label="$[jg_dep_post]" value="$[id]">$[jg_dep_post]</s:option>
				</s:autocomplete>
				<s:textfield label="性 质" name="property" maxlength="20" value="${property}"></s:textfield>
				<s:radio label="管理层" name="ismanagement" value="${ismanagement }">
					<s:option label="是" value="1"></s:option>
					<s:option label="否" value="0" checked="true"></s:option>
				</s:radio>
			</s:row>
			<s:row>
				<s:textarea label="岗位福利" cols="4" name="welfare" maxlength="300">${welfare}</s:textarea>
				<s:textarea label="岗位职责" cols="4" name="responsibility" maxlength="1000" >${responsibility}</s:textarea>
				<s:textarea label="招聘要求" cols="4" name="requirements" maxlength="800">${requirements}</s:textarea>
				<s:textarea label="描 述" cols="4" name="describe" maxlength="1000">${describe}</s:textarea>
			</s:row>
			<s:row>
				<s:file label="材料" name="material" cols="4">${material }</s:file>
			</s:row>
			<s:row>
				<s:textarea label="备注" name="remark" cols="4" maxlength="2000">${remark }</s:textarea>
			</s:row>
		</s:form>
	</s:row>
	<%-- <s:row>
		<s:row>
			<s:form id="reqForm" label="添加入职前员工需要提供资质证明">
				<s:toolbar>
					<s:button label="添加" onclick="savereq();"></s:button>
				</s:toolbar>
				<s:row>
					<s:textfield label="名称" name="title" maxlength="64" cols="4"></s:textfield>
				</s:row>
			</s:form>
		</s:row>
		<s:row>
			<s:table label="入职前员工需要提供资质证明列表" id="reqtable" action="setting.post.queryReq" autoload="false">
				<s:table.fields>
					<s:table.field name="title" datatype="string"></s:table.field>
					<s:table.field name="opr" datatype="template">
						<a href="javascript:void(0)" onclick="delreq($[id])"> 删除</a>
					</s:table.field>
				</s:table.fields>
			</s:table>
		</s:row>
	</s:row> --%>
</s:page>
<script type="text/javascript">
	$(document).ready(function(){
		$("#postid").params({out_id:'${pageParam.id}'});
	});
	function save(){
		if (!$("form").valid()) {  
			return false;	
		}
		var d = $("#myform").getData();
		$.call("setting.post.save", d, function(rtn) {
			if (rtn) {
				$.closeModal(true);
			}
		});
	}
	function saveReq(){
		if (!$("reqForm").valid()) {  
			return false;	
		}
		var position_id = $("#postid").val();
		if(position_id){
			var d = $("#reqForm").getData();
			$.call("setting.post.saveReq", d, function(rtn) {
				if (rtn) {
					$("#reqtable").refresh();
				}
			});
		}else{
			$.call("setting.post.save", d, function(rtn) {
				if (rtn) {
					$("#postid").val(rtn.id);
					$.call("setting.post.saveReq", d, function(r) {
						if (r) {
							$("#reqtable").params({position_id: rtn.id});
							$("#reqtable").refresh();
						}
					});
				}
			});
		}
	}
	function delreq(id){
		$.call("setting.post.delReq", d, function(rtn) {
			if (rtn) {
				("#reqtable").refresh();
			}
		});
	}
</script>