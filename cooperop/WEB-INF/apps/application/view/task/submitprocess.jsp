<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<div class="page-container">
	<div>
		<div class="">
			<div type="row" crid="" style="height: px;" class="row-fluid ">
				<div crid="" class="cols4">
					<div class="portlet box blue-hoki purple"
						style="border-width: 1px; height: px;">
						<div class="portlet-title">
							<div class="caption">操作区域</div>
							<div crid="" ctype="toolbar" class="tools" cinited="cinited">
								<button crid="" action="" class="btn btn-sm btn-sm btn-default "
									ctype="button" t="1" type="" onclick="submitprocess()"
									cinited="cinited">提交</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	
	$(document).ready(function() {
	});
	function submitprocess(){
		if (!$("form").valid()) {
			return false;	
		}
		$.confirm("是否确认提交？", function(r) {
			if (r) {
				var d = {
						"gzid":$("[name='gzid']").val(),
						"djbh":$("[name='djbh']").val(),
						"clientid":$("[name='clientid']").val(),
						"djbs":$("[name='djbs']").val(),
						"djlx":$("[name='djlx']").val(),
						"rq":$("[name='rq']").val(),
						"kaiprq":$("[name='kaiprq']").val(),
						"riqi":$("[name='riqi']").val(),
						"ontime":$("[name='ontime']").val(),
						"gzid":$("[name='gzid']").val(),
						"pageid":$("[name='pageid']").val(),
						"jigid":$("[name='jigid']").val()
				};
				$(document).find("[ctype='form'][cinited]").each(function(){
					$.extend(true , d , $(this).getData());
				});
				d.tables = [];
				$(document).find("[ctype='table'][cinited]").each(function(index,tobj){
					var table = {"tableid":$(tobj).attr("tableid")};
					table.tr = [];
					var fields = $(tobj).data("_tc");
					$(tobj).find("tbody>tr").each(function(){
						var data = $.extend(true,{},$(tobj).getData($(this).attr("id")),$(this).getData());
						var tr = {};
						for(var i in fields){
							tr[fields[i].name]= data[fields[i].name];
						}
						
						table.tr.push(tr);
					})
					d.tables.push(table);
				});
				$.call("application.bill.submit",{"data":$.toJSON(d)},function(rtn){
					if(rtn=="-1"){
						$.message("单据已经进入流程，请先将流程撤回！");
					}else{
						$("[name='djbh']").val(rtn);
						location.reload();
					}
				});
			}
		});
	}


	
</script>