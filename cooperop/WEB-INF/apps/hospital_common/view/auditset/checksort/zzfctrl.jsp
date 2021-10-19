<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>

<s:page title="控制转自费" disloggedin="true">

	<s:row>
		<s:form id="dataform" label="自定义控制转自费">
			<!-- 1、科室dept，2、人员user，3、费用price -->
			<s:row>
				<s:row>
					<s:radio label="科室设置" name="zzf1" onchange="checkCtrlZzf('1',this)" cols="2" value="${zzf_dept eq null?'-1':zzf_dept }">
						<s:option value="-1" label="不设置"></s:option>
						<s:option value="0" label="黑名单"></s:option>
						<s:option value="1" label="白名单"></s:option>
					</s:radio>
					<a onclick="deptCtrl()" class="zzf_details1"></a>
				</s:row>
			</s:row>
			<s:row>
				<s:radio label="人员设置" name="zzf2" onchange="checkCtrlZzf('2',this)" cols="2" value="${zzf_user eq null?'-1':zzf_user }">
					<s:option value="-1" label="不设置"></s:option>
					<s:option value="0" label="黑名单"></s:option>
					<s:option value="1" label="白名单"></s:option>
				</s:radio>
				<a onclick="userCtrl()" class="zzf_details2"></a>
			</s:row>

			<s:row>
				<s:radio label="费用项目设置" name="zzf3" onchange="checkCtrlZzf('3',this)" cols="2" value="${zzf_price eq null?'-1':zzf_price }">
					<s:option value="-1" label="不设置"></s:option>
					<s:option value="0" label="黑名单"></s:option>
					<s:option value="1" label="白名单"></s:option>
				</s:radio>
				<a onclick="priceCtrl()" class="zzf_details3"></a>
			</s:row>
		</s:form>
	</s:row>


	<script type="text/javascript">
		//初始化
		$(function() {
			zzfinit();
		});

		var sort_id = "${param.sort_id}";
		var sort_name = "${param.sort_name}";
		function zzfinit() {
			if ("${zzf_dept}" == "0") {
				$(".zzf_details1").show().html("黑名单详情");
			} else if ("${zzf_dept}" == "1") {
				$(".zzf_details1").show().html("白名单详情");
			} else {
				$(".zzf_details1").hide();
			}

			if ("${zzf_user}" == "0") {
				$(".zzf_details2").show().html("黑名单详情");
			} else if ("${zzf_user}" == "1") {
				$(".zzf_details2").show().html("白名单详情");
			} else {
				$(".zzf_details2").hide();
			}

			if ("${zzf_price}" == "0") {
				$(".zzf_details3").show().html("黑名单详情");
			} else if ("${zzf_price}" == "1") {
				$(".zzf_details3").show().html("白名单详情");
			} else {
				$(".zzf_details3").hide();
			}
		}

		function checkCtrlZzf(zzf_type, _this) {
			var fdata = $("#dataform").getData();
			var type = "-1";
			var zzf_details = ".zzf_details" + zzf_type;

			if (zzf_type == "1") {
				type = fdata.zzf1;
			} else if (zzf_type == "2") {
				type = fdata.zzf2;
			} else if (zzf_type == "3") {
				type = fdata.zzf3;
			}

			detailset(type, zzf_details);
			updateCtrlZzf(type, zzf_type);
		}

		function detailset(type, zzf_details) {
			if (type == "-1") {
				$(zzf_details).hide();
			} else if (type == "0") {
				$(zzf_details).show().html("黑名单详情");
			} else if (type == "1") {
				$(zzf_details).show().html("白名单详情");
			}
		}
		//1、科室dept，2、人员user，3、费用price
		//切换时，判断audit_rule_ctrl_zzf表中是否有该规则的数据
		//有：则修改该audit_rule_ctrl_zzf信息；无则添加
		function updateCtrlZzf(type, zzf_type) {
			$.call("hospital_common.auditset.checksort.checkCtrlZzf", {
				"sort_id" : sort_id,
				"zzf_type" : zzf_type,
				"type" : type
			}, function(s) {
				if (s > 0) {
					if (type == "-1") {
						$.message("状态转变为【不设置】，其他状态已被禁用！");
					} else if (type == "0") {
						$.message("状态转变为【黑名单】，其他状态已被禁用！");
					} else if (type == "1") {
						$.message("状态转变为【白名单】，其他状态已被禁用！");
					}
				}
			});
		}

		//1、科室控制转自费
		function deptCtrl() {
			var type = $("#dataform").getData().zzf1;
			var type_name = "";
			if (type == "0") {
				type_name = "【黑名单】-不允许转自费的科室列表";
			} else if (type == "1") {
				type_name = "【白名单】-允许转自费的科室列表";
			} else {
				type_name = "状态异常，请检查";
			}

			$.modal("ctrl_dept.html", "科室控制转自费【规则编号："+sort_id+"；规则名称："+sort_name+"】", {
				width : "100%",
				height : "100%",
				"sort_id" : sort_id,
				"type" : type,
				"type_name" : type_name,
				callback : function(e) {

				}
			});
		}

		//2、人员控制转自费
		function userCtrl(zzf_type) {
			var type = $("#dataform").getData().zzf2;
			var type_name = "";
			if (type == "0") {
				type_name = "【黑名单】-不允许转自费的人员列表";
			} else if (type == "1") {
				type_name = "【白名单】-允许转自费的人员列表";
			} else {
				type_name = "状态异常，请检查";
			}

			$.modal("ctrl_user.html", "人员控制转自费【规则编号："+sort_id+"；规则名称："+sort_name+"】", {
				width : "100%",
				height : "100%",
				"sort_id" : sort_id,
				"type" : type,
				"type_name" : type_name,
				callback : function(e) {

				}
			});
		}

		//3、费用项目控制转自费
		function priceCtrl(zzf_type) {
			var type = $("#dataform").getData().zzf3;
			var type_name = "";
			if (type == "0") {
				type_name = "【黑名单】-不允许转自费的费用项目列表";
			} else if (type == "1") {
				type_name = "【白名单】-允许转自费的费用项目列表";
			} else {
				type_name = "状态异常，请检查";
			}

			$.modal("ctrl_price.html", "费用项目控制转自费【规则编号："+sort_id+"；规则名称："+sort_name+"】", {
				width : "100%",
				height : "100%",
				"sort_id" : sort_id,
				"type" : type,
				"type_name" : type_name,
				callback : function(e) {

				}
			});
		}

	</script>
</s:page>



