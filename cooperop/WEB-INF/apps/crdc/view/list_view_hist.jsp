<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="历史记录" dispermission="true">
	<s:row>
		<s:table cols="4" label="记录列表" sort="true" action="crdc.designer.queryHist" autoload="false" id="dtable">
			<s:table.fields>
				<s:table.field name="create_time" label="备份时间" datatype="date" format="yyyy-MM-dd HH:mm" defaultsort="desc"></s:table.field>
				<s:table.field name="system_product_code" label="产品" ></s:table.field>
				<s:table.field name="flag" label="标识"></s:table.field>
				<s:table.field name="id" label="类型"></s:table.field>
				<s:table.field name="description" label="名称"></s:table.field>
				<s:table.field name="oper" datatype="template" label="操作"
					align="left" width="200">
					<a style="margin: 0px 5px;" class="font-red-sunglo"
						href="javascript:void(0)"
						onclick="reset('$[no]')">恢复</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
$(document).ready(function(){
	$("#dtable").params({type : '${pageParam.type}', id: '${pageParam.id}', flag: '${pageParam.flag}'});
	$("#dtable").refresh();
})
function reset(no){
	$.confirm("是否确认恢复到这条记录？", function(r) {
		if (r) {
			$.call("crdc.designer.reset", {no: no}, function(rtn) {
				if(rtn){
					$.closeModal(true);
				}
			});
		}
	});
}

</script>
