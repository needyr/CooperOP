<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="图表编辑">
	<link type="text/css" rel="stylesheet" href="${contextpath}/res/${module}/codemirror/lib/codemirror.css"></link>
	<link type="text/css" rel="stylesheet" href="${contextpath}/res/${module}/codemirror/addon/fold/foldgutter.css"></link>
	<link type="text/css" rel="stylesheet" href="${contextpath}/res/${module}/codemirror/addon/hint/show-hint.css"></link>
	<link type="text/css" rel="stylesheet" href="${contextpath}/res/${module}/codemirror/addon/search/matchesonscrollbar.css"></link>
	<style type="text/css">
		.CodeMirror {border:1px solid #cfd6e0;height:200px;border-radius: 2px;overflow:hidden;}
	</style>
	<script type="text/javascript" src="${contextpath}/res/${module}/codemirror/lib/codemirror.js"></script>
	<script type="text/javascript" src="${contextpath}/res/${module}/codemirror/mode/sql/sql.js"></script>
	<script type="text/javascript" src="${contextpath}/res/${module}/codemirror/addon/fold/foldcode.js"></script>
	<script type="text/javascript" src="${contextpath}/res/${module}/codemirror/addon/fold/foldgutter.js"></script>
	<script type="text/javascript" src="${contextpath}/res/${module}/codemirror/addon/fold/brace-fold.js"></script>
	<script type="text/javascript" src="${contextpath}/res/${module}/codemirror/addon/fold/comment-fold.js"></script>
	<script type="text/javascript" src="${contextpath}/res/${module}/codemirror/addon/fold/indent-fold.js"></script>
	<script type="text/javascript" src="${contextpath}/res/${module}/codemirror/addon/fold/markdown-fold.js"></script>
	<script type="text/javascript" src="${contextpath}/res/${module}/codemirror/addon/hint/show-hint.js"></script>
	<script type="text/javascript" src="${contextpath}/res/${module}/codemirror/addon/hint/anyword-hint.js"></script>
	<script type="text/javascript" src="${contextpath}/res/${module}/codemirror/addon/hint/sql-hint.js"></script>
	<script type="text/javascript" src="${contextpath}/res/${module}/codemirror/addon/search/search.js"></script>
	<script type="text/javascript" src="${contextpath}/res/${module}/codemirror/addon/search/match-highlighter.js"></script>
	<script type="text/javascript" src="${contextpath}/res/${module}/codemirror/addon/search/matchesonscrollbar.js"></script>
	<script type="text/javascript" src="${contextpath}/res/${module}/codemirror/addon/search/searchcursor.js"></script>
	<script type="text/javascript" src="${contextpath}/res/${module}/codemirror/addon/search/jump-to-line.js"></script>
	<s:form label="${name}-图表编辑">
		<s:toolbar>
			<s:button id="btnSave" data-ccode="${code}" icon="fa fa-save" label="保存"></s:button>
		</s:toolbar>
		<s:row>
			<s:textfield islabel="true" label="编号" value="${code}" required="true"></s:textfield>
			<s:textfield label="名称" name="name" value="${name}" required="true" maxlength="32"></s:textfield>
			<s:textfield label="刷新周期(秒)" name="refresh_time" value="${refresh_time}" placeholder="0 不刷新" datatype="number" required="true" digits="digits"></s:textfield>
		</s:row>
		<s:row>
			<s:switch label="驾驶舱图表" name="iscockpit" value="${iscockpit}" required="true"></s:switch>
			<s:select label="下钻图表" name="childcode" value="${childcode}" action="pivascockpit.chart.query">
				<s:option label="$[name]" value="$[code]"></s:option>
			</s:select>
		</s:row>
		<s:row>
			<s:form label="驾驶舱图表数据查询">
				<s:row>
					<s:textarea id="cockpitsql" label="SQL脚本" cols="4" rows="6" name="cockpitsql" required="true" autosize="false">${cockpitsql}</s:textarea>
				</s:row>
				<s:row>
					<s:textfield label="排序规则" name="cockpitsort" placeholder="不用写ORDER BY" value="${cockpitsort}" ></s:textfield>
					<s:textfield label="行数限制" name="cockpitlimit" placeholder="0 不限制" value="${cockpitlimit}" datatype="number" digits="digits"></s:textfield>
				</s:row>
			</s:form>
		</s:row>
		<s:row>
			<s:form label="大屏图表数据查询">
				<s:row>
					<s:textarea id="fullsql" label="SQL脚本" cols="4" rows="6" name="fullsql" required="true" autosize="false">${fullsql}</s:textarea>
				</s:row>
				<s:row>
					<s:textfield label="排序规则" name="fullsort" placeholder="不用写ORDER BY" value="${fullsort}" ></s:textfield>
					<s:textfield label="行数限制" name="fulllimit" placeholder="0 不限制" value="${fulllimit}" datatype="number" digits="digits"></s:textfield>
				</s:row>
			</s:form>
		</s:row>
	</s:form>
	<script type="text/javascript">
	$(document).ready(function() {
		$("form").validate().settings.ignore = ":hidden[name!='cockpitsql'][name!='fullsql']";
		var cockpitsql_editor = CodeMirror.fromTextArea($("#cockpitsql").get(0), {
			lineNumbers: true,
			lineWrapping: true,
			matchBrackets: true,
			mode: {name: "sql", globalVars: true},
			indentWithTab: true,
			indentUnit: 4,
			extraKeys: { "Alt-/": "autocomplete" },
		    foldGutter: true,
		    gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"],
		    hintOptions: {
		    	completeOnSingleClick: false
		    }
		});
		cockpitsql_editor.on('change', function () {
			$("#cockpitsql").val(cockpitsql_editor.doc.getValue());
	    });
		var fullsql_editor = CodeMirror.fromTextArea($("#fullsql").get(0), {
			lineNumbers: true,
			lineWrapping: true,
			matchBrackets: true,
			mode: {name: "sql", globalVars: true},
			indentWithTab: true,
			indentUnit: 4,
			extraKeys: { "Alt-/": "autocomplete" },
		    foldGutter: true,
		    gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"],
		    hintOptions: {
		    	completeOnSingleClick: false
		    }
		});
		fullsql_editor.on('change', function () {
			$("#fullsql").val(fullsql_editor.doc.getValue());
	    });
		$("#btnSave").on("click", function() {
			var $this = $(this);
			var data = $("form").getData();
			data.code = $this.data("ccode");
			if ($("form").valid()) {
				$.call("pivascockpit.chart.save", data, function() {
					$.message("保存成功！");
				});			
			}
		})
	});
	</script>
</s:page>