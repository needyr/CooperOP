<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="数据采集" >
<link href="${pageContext.request.contextPath}/res/hospital_common/css/guide/index.css" rel="stylesheet" type="text/css">
<style type="text/css">
.main {
	padding: 0px;
}
</style>
<s:row>
  <div class="note note-warning">
    <p>注意：如果页面不存在请启动DTC服务后再使用。请点击【一键预设SQL】</p>
  </div>
</s:row>
<div class="page-wrap">
	<div class="column-default" style="min-height: 300px;width: 100%;height:100%;">
		<ul class="large-btn">
			<li><a href="#" onclick="preSetAllSql();"><i class="fa fa-copy"></i>一键预设SQL</a></li>
			<li><a href="#" onclick="start2();"><i class="fa fa-copy"></i>数据采集页面</a></li>
		</ul>
	</div>
</div>
</s:page>
<script type="text/javascript">
	$(function(){
		
	});
	
	function start2(){
		$.modal("/w/dtc_admin/interface/index.html","数据采集",{
	        width:"90%",
	        height:"90%",
	        callback : function(e){
	        }
		});
	}

	//一键预设SQL
	function preSetAllSql() {
		layer.open({
			type: 1,
			title: '一键预设SQL-校验密码',
			area: ['300px', '200px'],
			btn: ['确定', '取消'],
			skin: 'layui-layer-lan',
			closeBtn: 0,
			content:'<div class="control-content">'
					+'<input style="height: 80px;margin-top:20px" class="form-control" type="password" cols="4" maxlength="2000" id="finish_text"></input>'
					+'</div>', //这里content是一个普通的String
			yes: function(index, layero){
				$.call('dtc_admin.interface.preSetAllSql', {password:layero.find('#finish_text').val()}, function(rtn){
					if(rtn > 0){
						layer.close(index);
						$.message("已更改"+rtn+"条记录");
					}else if(rtn = -1){
						$.message("密码不匹配!");
					}else{
						layer.close(index);
						$.message("执行失败!");
					}
				},function(){
					$.confirm("执行失败! 可能为地址配置错误,是否立即进行配置?",function callback(e){
						if(e==true){
							$.modal("/w/hospital_common/config/system_list.html","配置更改",{
								width:"90%",
								height:"90%",
								codes : '[{"code":"dtc.datasource.uri","system_product_code":"dtc_admin"}]',
								callback : function(e){

								}
							});
						}
					})
				});
			}
		});
	}
</script>