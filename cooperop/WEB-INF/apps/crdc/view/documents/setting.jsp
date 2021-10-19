<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
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
	pageContext.setAttribute("control", attrs);
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
					<c:choose>
						<c:when test="${param.type eq 'button'}">
							<div class="row-fluid">
								<div class="cols">
									<label class="control-label">对应查询页</label>
									<div class="control-content">
										<input type="text" class="form-control" />
									</div>
								</div>
								<div class="cols">
									<div class="cols2">
										<label class="control-label">颜色</label>
										<div class="control-content">
											<ul class="icheck-colors">
												<li></li>
												<li class="red"></li>
												<li class="green"></li>
												<li class="blue"></li>
												<li class="yellow"></li>
												<li class="purple"></li>
											</ul>
										</div>
										<input type="hidden" name="color" id="color_"
											class="form-control" value="${control.color }" />
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="cols3">
									<label class="control-label">按钮标题</label>
									<div class="control-content">
										<input type="text" name="label" class="form-control"
											value="${control.label }" />
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="cols3">
									<label class="control-label">执行函数</label>
									<div class="control-content">
										<input type="text" name="action" class="form-control"
											value="${control.action }" />
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="cols"></div>
								<div class="cols">
									<button type="submit" class="btn green" onclick="save();">确定</button>
									<button type="button" class="btn red">取消</button>
								</div>
							</div>
						</c:when>
						<c:when test="${param.type eq 'form'}">
							<div class="row-fluid">
								<div class="cols">
									<label class="control-label">表单名</label>
									<div class="control-content">
										<input type="text" name="label" class="form-control"
											value="${control.label }" />
									</div>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="row-fluid">
								<div class="cols">
									<label class="control-label">字段名 </label>
									<div class="control-content">
										<input type="text" name="name" class="form-control"
											value="${control.name }" />
									</div>
								</div>
								<div class="cols">
									<label class="control-label">中文说明</label>
									<div class="control-content">
										<input type="text" name="label" class="form-control"
											value="${control.label }" />
									</div>
								</div>
								<div class="cols">
									<label class="control-label">备注</label>
									<div class="control-content">
										<input type="text" name="placeholder" class="form-control"
											value="${control.placeholder }" />
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="cols">
									<label class="control-label">字段类型</label>
									<div class="control-content">
										<input type="text" name="column_type" class="form-control"
											value="${control.type }" />
									</div>
								</div>
								<div class="cols">
									<label class="control-label">字段长度</label>
									<div class="control-content">
										<input type="text" name="maxlength" class="form-control"
											value="${control.maxlength }" />
									</div>
								</div>
								<div class="cols">
									<label class="control-label">小数位数</label>
									<div class="control-content">
										<input type="text" name="digits " class="form-control"
											value="${control.digits }" />
									</div>
								</div>
							</div>
							<hr />
							<div class="row-fluid">
								<div class="cols">
									<label class="control-label">缺省值</label>
									<div class="control-content">
										<input type="text" name="defaultValue" class="form-control"
											value="${control.defaultValue }" />
									</div>
								</div>
								<div class="cols">
									<label class="control-label"></label>
									<div class="control-content">
										<div ctype="checkbox" class="checkbox-list"
											required="required">
											<label class="checkbox-inline"> <input
												type="checkbox" name="readonly" value="option2">可编辑
											</label> <label class="checkbox-inline"> <input
												type="checkbox" name="required" value="option3">必填
											</label> <label class="checkbox-inline"> <input
												type="checkbox" name="encryption" value="option3">加密
											</label>
										</div>
									</div>
								</div>
							</div>
							<hr />

							<div class="row-fluid">
								<div class="cols">
									<label class="control-label">创建执行函数</label>
									<div class="control-content">
										<input type="text" name="create_action" class="form-control"
											value="${control.create_action }" />
									</div>
								</div>
								<div class="cols">
									<label class="control-label">修改执行函数</label>
									<div class="control-content">
										<input type="text" name="modify_action" class="form-control"
											value="${control.modify_action }" />
									</div>
								</div>
								<div class="cols">
									<label class="control-label">进入执行函数</label>
									<div class="control-content">
										<input type="text" name="enter_action" class="form-control"
											value="${control.enter_action }" />
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="cols">
									<label class="control-label">双击执行函数</label>
									<div class="control-content">
										<input type="text" name="dblaction" class="form-control"
											value="${control.dblaction }" />
									</div>
								</div>
								<div class="cols">
									<label class="control-label">退出执行函数</label>
									<div class="control-content">
										<input type="text" name="out_action" class="form-control"
											value="${out.create_action }" />
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="cols">
									<button type="add" class="btn blue">引用其他查询方案</button>
								</div>
								<div class="cols2">
									<button type="submit" onclick="save();" class="btn green">确定</button>
									<button type="button" class="btn red" onclick="returnback()">取消</button>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
				</form>
			</div>
		</div>
	</div>
</div>
<c:import url="/footer.jsp?ismodal=true"></c:import>
<script>
	function save() {
		var data = {};
		var attrs = $("#setFrom").getData();
		data.attrs = attrs;
		$.closeModal(data);
	}
	function returnback() {
		$.closeModal(false);
	}
	$(document).ready(function() {
		$(".icheck-colors").click(function(e) {
			var color = $(this).find(".active").attr("class").split(" ")[0];
			if (color == "active") {
				color = "default";
			}
			$("#color_").val(color);
		});
	});
</script>
