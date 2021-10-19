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
							<label class="control-label">字段名称</label>
							<div class="control-content">
								<input type="text" name="name" class="form-control"
									value="${table.name }" />
							</div>
						</div>
						<div class="cols">
							<label class="control-label">字段中文说明</label>
							<div class="control-content">
								<input type="text" name="label" class="form-control"
									value="${table.label }" />
							</div>
						</div>
						<div class="cols">
							<button>查询</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<c:import url="/footer.jsp?ismodal=true"></c:import>
<script>
	
</script>
