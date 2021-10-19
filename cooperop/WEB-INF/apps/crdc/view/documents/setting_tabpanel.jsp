<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
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
	pageContext.setAttribute("panel", attrs);
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
							<label class="control-label">宽度</label>
							<div class="control-content">
								<input type="text" name="cols" class="form-control"
									value="${panel.cols }" />
							</div>
						</div>
						<div class="cols2">
							<label class="control-label">颜色</label>
							<div class="control-content">
								<ul class="icheck-colors">
									<li class="${(panel.color eq 'grey')?'active':''}"></li>
									<li class="red ${(panel.color eq 'red')?'active':''}"></li>
									<li class="green ${(panel.color eq 'green')?'active':''}"></li>
									<li class="blue ${(panel.color eq 'blue')?'active':''}"></li>
									<li class="yellow ${(panel.color eq 'yellow')?'active':''}"></li>
									<li class="purple ${(panel.color eq 'purple')?'active':''}"></li>
								</ul>
							</div>
							<input type="hidden" name="color" id="color_"
								class="form-control" value="${panel.color }" />
						</div>
					</div>
					<c:forEach items="${contents }" var="t" varStatus="n">
						<c:if test="${t.type eq 'tab' }">
							<div class="row-fluid" rowtype="tabrow">
								<div class="cols">
									<label class="control-label">页面名称</label>
									<div class="control-content">
										<input type="text" name="label" class="form-control"
											value="${t.attrs.label }" />
									</div>
								</div>
								<div class="cols">
									<label class="control-label">排序</label>
									<div class="control-content">
										<input type="hidden" name="oldindex" value="${n.index }" /> <input
											type="text" name="contentIndex" class="form-control"
											value="${n.index }" />
									</div>
								</div>
								<button onclick="deleter(this);return false;" class="btn red">删除页面</button>
							</div>
						</c:if>
					</c:forEach>
					<div class="row-fluid" id="controlsrow">
						<div class="cols"></div>
						<div class="cols">
							<button type="add" onclick="add();return false;" class="btn blue">新增tab页面</button>
							<button type="submit" onclick="save();return false;"
								class="btn green">保存</button>
							<button type="button" class="btn red"
								onclick="returnback();return false;">取消</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<c:import url="/footer.jsp?ismodal=true"></c:import>
<script>
	function save() {
		var d = $("#setFrom").getData();
		var data = {};
		var data.attrs = {};
		data.attrs.cols = d.cols;
		data.attrs.color = d.color;
		data.children = [];

		$("#setFrom").find("div[rowtype='tabrow']").each(function() {
			var child = '';
			$(this).find("input").each(
					function() {
						var t = '"' + $(this).attr("name") + '":"'
								+ $(this).val() + '"';
						if (child == '') {
							child = t;
						} else {
							child += ',' + t;
						}
					});
			if (child != '') {
				child = '{' + child + '}';
				data.children.push($.parseJSON(child));
			}
		});
		//$.closeModal(data);
	}
	function returnback() {
		$.closeModal(false);
	}
	function add() {
		var addr = [];
		addr.push('<div class="row-fluid" rowtype="tabrow">                             ');
		addr.push('	<div class="cols">                                                  ');
		addr.push('		<label class="control-label">页面名称</label>                       ');
		addr.push('		<div class="control-content">                                    ');
		addr.push('			<input type="text" name="label" class="form-control"/>       ');
		addr.push('		</div>                                                           ');
		addr.push('	</div>                                                               ');
		addr.push('	<div class="cols">                                                   ');
		addr.push('		<label class="control-label">排序</label>                         ');
		addr.push('		<div class="control-content">                                    ');
		addr.push('			<input type="text" name="contentIndex" class="form-control"/>');
		addr.push('		</div>                                                           ');
		addr.push('	</div>                                                               ');
		addr.push('	<button onclick="deleter(this);return false;" class="btn red">删除页面</button>');
		addr.push('</div>                                                                ');
		$("#controlsrow").before(addr.join(''));
	}
	function deleter(_this) {
		//$.comfirm
		var $this = $(_this);
		$this.parent().remove();
		return false;
	}
</script>
