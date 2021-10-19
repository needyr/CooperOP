<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="个性化主页设置" dispermission="true">
<style type="text/css">
.set-fun-div .set-title{
    text-align: center;
    position: relative;
   /*  top: 29px; */
}
.set-fun-div .set-type{
    float: right;
    font-size: 12px;
    margin: 0 10px 0 0;
}
.set-fun-div .set-product{
    float: left;
    margin: 0 0 0 20px;
    font-size: 16px;
}
.set-fun-div{
	/* box-shadow: 0 1px 4px rgba(0, 0, 0, 0.4), 0 0 40px rgba(0, 0, 0, 0.3) inset; */
	   /*  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.3), 0 0 40px rgba(0, 0, 0, 0.1) inset; */
	   position: relative;
	       box-shadow: 3px 4px 4px rgba(0, 0, 0, 0.3), 2px 2px 1px rgba(0, 0, 0, 0.1) inset;
	background-color: #fff;
	height:100px; 
	margin-bottom: 10px;
}

.set-fun-div.checked{
	background-color: #29c7bd;
	color: #FFF;
}
.order-span{
	display:none;
}
.set-fun-div.checked .order-span{
display:inline-block;
	float: right;
	margin: 2px 5px 0 0;
	font-size: 14px;
	width:18px;
	height:18px;
	background-color: red;
	border-radius : 50%!important;
	text-align: center;
}
.func-span{
	padding : 5px;
	margin: 2px 0 0 5px;
	font-size: 16px;
}

.set-menu-div{
	width: 120px;
    position: relative;
    display: inline-block;
    margin-bottom: 10px;
    margin: 5px 20px 0 0;
    line-height: 30px;
    white-space: nowrap;
    word-spacing: normal;
    overflow:  hidden;
    text-overflow: ellipsis;
}
.set-menu-type{
    display: block;
    font-size: 16px;
    font-weight: 400;
    text-align: left;
    padding-bottom: 5px;
    color: #787777;
}
.set-menu-type:after{
	content: ' :';
}
.set-menu-div .set-title{
	cursor: pointer;
	padding: 0 10px;
}
.set-menu-div .set-title:hover{
	color:#ee9543;
}
.row-fluid {
	margin-top:5px;
    padding-bottom: 20px;
    border-bottom: 1px solid #c7c4c4;
}
.set-menu-div .set-title.checked{
    padding: 4px 5px;
    background: #2fb3ff;
    color: #fff;
    border-radius: 13px!important;
}

</style>
	<s:row>
		<s:tabpanel color="green">
			<s:form id="menyform" color="green" label="添加快捷菜单" active="true">
				<s:toolbar>
					<s:button label="保存" onclick="save();" icon="fa fa-save" color="blue"></s:button>
				</s:toolbar>
				<c:forEach items="${self_products }" var="p">
					<c:if test="${not empty p.code }">
						<s:row>
							<div class="col-md-12">
								<span class="set-menu-type">${p.name }</span>
							</div>
							<c:forEach items="${self_menus }" var="m">
								<c:if test="${m.plugin eq p.code }">
									<div class="set-menu-div">
										<span class="set-title ${not empty m.ph_id?'checked':'' }" data-p-id=${m.id }>${m.name }</span>
									</div>
								</c:if>
							</c:forEach>
						</s:row>
					</c:if>
				</c:forEach>
			</s:form>
			<s:form id="myform" color="green" label="添加首页功能" >
				<s:toolbar>
					<s:button label="保存" onclick="save1();" icon="fa fa-save" color="blue"></s:button>
				</s:toolbar>
				<s:row>
					<c:forEach items="${charts }" var="cha">
						<div class="col-md-3">
							<div class="set-fun-div ${not empty cha.ph_id?'checked':'' }" 
							data-p-id="${cha.system_product_code }.chart.${cha.view_flag }.${cha.view_id }.${cha.flag }">
								<span class="order-span" title="显示序号">${cha.func_order }</span>
								<span class="func-span" title="所属功能块">${cha.description }</span>
								<h3 class="set-title" title="功能名称">${cha.title }</h3>
								<span class="set-type" title="功能类型">图表</span>
								<span class="set-product" title="所属产品">${cha.system_product_name }</span>
							</div>
						</div>
					</c:forEach>
				</s:row>
			</s:form>
		</s:tabpanel>
	</s:row>
</s:page>
<script>
$(document).ready(function(){
	$(".set-menu-div").on("click", ".set-title", function(){
		var $th = $(this);
		if($th.hasClass("checked")){
			$(this).removeClass("checked");
		}else{
			$(this).addClass("checked");
		}
	})
	$(".set-fun-div").on("click", function(){
		var $th = $(this);
		var func_order = $th.find(".order-span").text();
		var func_checked = 'N';
		if($th.hasClass("checked")){
			func_checked = 'Y'
		}
		$.modal("setorder.html", $th.find(".set-title").text(), {
			width : '30%',
			height : '50%',
			func_checked: func_checked,
			func_order: func_order,
			callback : function(rtn) {
				if(rtn){
					if(rtn.func_checked == 'Y'){
						$th.addClass("checked");
						$th.find(".order-span").text(rtn.func_order)
					}else{
						$th.removeClass("checked");
					}
				}
		    }
		});
		/* if($th.hasClass("checked")){
			$(this).removeClass("checked");
		}else{
			$(this).addClass("checked");
		} */
	})
})
function save() {
	var d = {"type": "M"};
	d.fks = [];
	$(".set-menu-div").find(".set-title.checked").each(function(index,obj){
		var $th = $(obj);
		d.fks.push({fk_id: $th.attr("data-p-id")});
		
	});
	$.call("application.mine.saveHomeSetting",{data: $.toJSON(d)}, function(rtn){
		if(rtn){
			$.message("设置成功！刷新主页即可加载。");
		}
	});
}
function save1() {
	var d = {"type": "C"};
	d.fks = [];
	$(".set-fun-div.checked").each(function(index,obj){
		var $th = $(obj);
		d.fks.push({fk_id: $th.attr("data-p-id"), order_no: $th.find(".order-span").text()});
		
	});
	$.call("application.mine.saveHomeSetting",{data: $.toJSON(d)}, function(rtn){
		if(rtn){
			$.message("设置成功！刷新主页即可加载。");
		}
	});
}
</script>

