<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" >
	<s:row>
		<s:form border="0" id="myform" fclass="portlet light bordered">
			<s:row>
				<s:file name="upfile" cols="4" maxlength="1" autoupload="false" required="true"></s:file>
			</s:row>
			<s:row>
				<div class="cols3">
				</div>
				<div class="cols">
					<s:button onclick="save();" color="green" label="保存"></s:button>
				</div>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
	function save() {
		if (!$("form").valid()) {
			return false;	
		}
		var data = $("#myform").getData();
		$.call("crdc.designer.upFile", data, function(rtn) {
			if (rtn.result == 0) {
				$.confirm("导入数据已存在，是否导入覆盖？", function(r) {
					if (r) {
						data.forceInsert = "Y";
						$.call("crdc.designer.upFile", data, function(rr) {
							if(rr){
								$.message("导入成功！", function(){
									$.closeModal(true);
								});
							}
						});
					}
				});
			}else if (rtn.result == 1) {
				$.message("导入成功！", function(){
					$.closeModal(true);
				});
			}else if (rtn.result == 2) {
				$.message("导入失败，文件格式有误！");
			}
		});
	}
</script>
