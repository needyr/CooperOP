<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="岗位列表">
<style type="text/css">
.minegroup>li>a{
	padding : 2px 5px!important;
	line-height: 15px!important; 
}
</style>
	<c:if test="${empty pageParam.depid }">
		<s:row>
			<s:form id="dform"  label="条件区域" color="green">
				<s:toolbar>
					<s:button label="查询" onclick="query1();" color="blue"></s:button>
				</s:toolbar>
				<s:row>
					<s:autocomplete label="部 门" name="depid" action="setting.dep.querydep">
						<s:option label="$[name]" value="$[id]">$[name]</s:option>
					</s:autocomplete>
					<s:textfield label="岗 位" name="postfilter" placeholder="输入关键字"></s:textfield>
					<s:autocomplete label="性 质" name="property" action="setting.post.queryProperty">
						<s:option value="$[property]">$[property]</s:option>
					</s:autocomplete>
				</s:row>
				<s:row>
					<s:select label="状 态" name="state">
						<s:option label="全部" value=""></s:option>
						<s:option label="正常" value="1"></s:option>
						<s:option label="停用" value="0"></s:option>
					</s:select>
					<s:select label="编制状态" name="compilation_state">
						<s:option label="全部" value=""></s:option>
						<s:option label="饱和" value="0"></s:option>
						<s:option label="超编" value="1"></s:option>
						<s:option label="缺编" value="-1"></s:option>
					</s:select>
				</s:row>
			</s:form>
		</s:row>
	</c:if>
	<s:row>
		<s:table label="现有岗位" autoload="false" id="posttable" action="setting.post.query" sort="true" 
		fitwidth="true" limit="50" tableid="tt" height="250">
			<s:toolbar>
				<s:button icon="fa fa-file-o" label="新增岗位" size="btn-sm btn-default" onclick="add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="jg_dep_post" datatype="string" label="[部门]岗位名称" sort="true"  ></s:table.field>
				<s:table.field name="no" datatype="string" label="岗位编号" sort="true" defaultsort="asc"  width="70"></s:table.field>
				<s:table.field name="compilation" label="编制" sort="true" width="40"></s:table.field>
				<s:table.field name="unum" label="现有人数" sort="true" width="40"></s:table.field>
				<s:table.field name="superior_name" datatype="string" label="直接上级" sort="true" ></s:table.field>
				<s:table.field name="state" datatype="script" label="状态" width="40">
					var html = [];
					if (record.state == 1) {
						html.push('<font class="font-green">正常</font>');
					} else if(record.state == 0) {
						html.push('<font class="font-red">停用</font>');
					}
					return html.join("");
				</s:table.field>
				<s:table.field name="oper" datatype="template" label="操作" align="left" width="75">
					<s:buttongroup label="操作"  type="link" class="minegroup">
						<s:buttongroup.button label="修改" onclick="modify('$[id]','$[name]')"></s:buttongroup.button>
						<s:buttongroup.button label="删除" onclick="del('$[id]')"></s:buttongroup.button>
						<s:buttongroup.button label="详情" onclick="detail('$[id]','$[name]')"></s:buttongroup.button>
					</s:buttongroup>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(document).ready(function(){
		query1();
	});
	function query1(){
		var d = $("#dform").getData();
		if('${pageParam.depid}'){
			d.depid = '${pageParam.depid}';
		}
		$("#posttable").params(d);
 		$("#posttable").refresh();
	}
	function add(){
		$.modal("add.html", "新增", {
			width : '80%',
			height : '60%',
			depid : '${pageParam.depid}',
			callback : function(rtn) {
				if(rtn){
					$("#posttable").refresh();
				}
		    }
		});
	}
	function modify(id,depname){
		$.modal("add.html", "["+depname+"] 岗位修改", {
			width : '80%',
			height : '60%',
			id : id,
			depid : '${pageParam.depid}',
			callback : function(rtn) {
				if(rtn){
					$("#posttable").refresh();
				}
		    }
		});
	}
	function del(id){
		$.confirm("删除后无法恢复，是否继续！" ,function (rtn){
			if(rtn){
				$.call("setting.post.delete", {"id" :id}, function(rtn) {
					if (rtn) {
						$("#posttable").refresh();
					}
				});
			}
		});
	}
 	function detail(id,name){
 		$.modal("detail.html", "["+name+"] 岗位详细信息", {
 			width : '80%',
 			height : '90%',
 			id : id,
 			name : name,
 			callback : function(rtn) {
 				if(rtn){
 				//	$("#posttable").refresh();
 				}
 		    }
 		});
 	}
 	function setmoney(id){
		$.modal("setsalary.html", "薪资方案", {
			width : '80%',
			height : '95%',
			system_post_id: id,
			callback : function(rtn) {
				if(rtn){
					$("#dtable").refresh();
				}
		    }
		});
	}
</script>