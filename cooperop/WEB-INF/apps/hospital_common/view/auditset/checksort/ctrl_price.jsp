<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<style>
.main {
	padding: 5px !important;
}

#all {
	position: relative;
	width: 100%;
	min-width: 700px;
}

#all_left {
	position: absolute;
	top: 0px;
	width: calc(45% - 15px);
}

#left {
	position: absolute;
	top: 0px;
	min-width: 320px;
	width: calc(100% - 30px);
}

#middle {
	position: absolute;
	right: 0px;
	width: 30px;
	height: 700px;
	float: left;
}

#middle p {
	position: relative;
	top: 400px;
	width: 8px;
	height: 10px;
	font-size: 24px;
	font-weight: bolder;
	font-size: 24px;
	color: #bbbbbb;
}

#right {
	position: absolute;
	top: 0px;
	right: 10px;
	width: 55%;
	height: 700px;
}
</style>
<s:page title="费用项目控制转自费" disloggedin="true">
	<s:row>
		<div id="all">
			<div id="all_left">
				<div id="left">
					<s:row>
						<s:form label="费用项目字典信息筛选" id="leftform">
							<s:row>
								<s:autocomplete label="选择类别" name="class_code" placeholder="请输入类别编码、名称或拼音首字母" cols="2" action="hospital_common.auditset.checksort.queryBillItemClass" limit="5">
									<s:option value="$[class_code]" label="$[class_name]">$[class_code] $[class_name]</s:option>
								</s:autocomplete>
							</s:row>
							<s:row>
								<s:textfield label="单位" name="units" placeholder="请输入单位" cols="2"></s:textfield>
							</s:row>
							<s:row>
								<s:textfield label="费用项目信息" placeholder="请输入编码、名称或拼音首字母" name="filter_info" cols="2"></s:textfield>
								<s:button label="查询" icon="fa fa-search" onclick="queryPrice()" style="background: #368bdb;text-align: center;color: #FFF;"></s:button>
							</s:row>
						</s:form>
					</s:row>

					<s:row>
						<s:table action="hospital_common.auditset.checksort.queryPrice" autoload="false" id="lefttable" label="费用项目字典信息" sort="true" select="multi">
							<s:toolbar>
								<s:button label="添加已选中" onclick="addSelect()" icon="fa fa-plus"></s:button>
							</s:toolbar>
							<s:table.fields>
								<s:table.field name="item_code" label="编码" sort="true"></s:table.field>
								<s:table.field name="item_name" label="名称" sort="true"></s:table.field>
								<s:table.field name="units" label="单位" sort="true"></s:table.field>
								<s:table.field name="price" label="费用" sort="true"></s:table.field>
								<s:table.field name="class_name" label="类别" sort="true"></s:table.field>
								<s:table.field name="caozuo" label="转自费设置" datatype="template">
									<a onclick="addZzf('$[item_code]', '$[item_name]','$[price]', '$[units]','$[item_class]','$[input_code]')">添加</a>
								</s:table.field>
							</s:table.fields>
						</s:table>
					</s:row>
				</div>
				<div id="middle">
					<p>>></p>
				</div>
			</div>
			<div id="right">
				<s:row>
					<s:form id="rightform" label="转自费信息筛选">
						<s:row>
							<s:autocomplete label="选择类别" name="class_code" placeholder="请输入类别编码、名称或拼音首字母" cols="2" action="hospital_common.auditset.checksort.queryBillItemClass" limit="5">
								<s:option value="$[class_code]" label="$[class_name]">$[class_code] $[class_name]</s:option>
							</s:autocomplete>
						</s:row>
						<s:row>
							<s:textfield label="单位" name="units" placeholder="请输入单位" cols="2"></s:textfield>
						</s:row>
						<s:row>
							<s:textfield label="费用项目信息" placeholder="请输入编码、名称或拼音首字母" name="filter_info" cols="2"></s:textfield>
							<s:button label="查询" icon="fa fa-search" onclick="queryPriceCtrl()" style="background: #368bdb;text-align: center;color: #FFF;"></s:button>
						</s:row>
					</s:form>
				</s:row>

				<s:row>
					<s:table action="hospital_common.auditset.checksort.queryPriceZzf" autoload="false" id="zzfTable" sort="true" label="${param.type_name }">
						<s:table.fields>
							<s:table.field name="create_time" label="创建时间" datatype="datetime" sort="true" defaultsort="desc"></s:table.field>
							<s:table.field name="item_code" label="编码" sort="true"></s:table.field>
							<s:table.field name="item_name" label="名称" sort="true"></s:table.field>
							<s:table.field name="units" label="单位" sort="true"></s:table.field>
							<s:table.field name="price" label="费用" sort="true"></s:table.field>
							<s:table.field name="class_name" label="类别" sort="true"></s:table.field>
							<s:table.field name="caozuo" label="操作" datatype="template">
								<a onclick="delWindow('$[id]')">删除</a>
							</s:table.field>
						</s:table.fields>
					</s:table>
				</s:row>
			</div>
		</div>
	</s:row>
	<script type="text/javascript">
		$(function() {
			query();
		});
		var tdata = {};
		tdata.sort_id = '${param.sort_id}';
		tdata.type = '${param.type}';

		//type（1允许转自费，0不允许转自费）
		function query() {
			queryPrice();
			queryPriceCtrl();
		}

		//刷新辅助表
		function queryPrice() {
			var qdata = $("#leftform").getData();
			qdata.sort_id = tdata.sort_id;
			qdata.type = tdata.type;
			$("#lefttable").params(qdata);
			$("#lefttable").refresh();
		}

		//控制费用项目转自费表
		function queryPriceCtrl() {
			var qdata = $("#rightform").getData();
			qdata.sort_id = tdata.sort_id;
			qdata.type = tdata.type;
			$("#zzfTable").params(qdata);
			$("#zzfTable").refresh();
		}

		//添加费用项目信息
		function addZzf(item_code, item_name, price, units, item_class,input_code) {
			var zzfdata = {};
			zzfdata.type = tdata.type;
			zzfdata.sort_id = tdata.sort_id;
			zzfdata.item_code = item_code;
			zzfdata.item_name = item_name;
			zzfdata.price = price;
			zzfdata.units = units;
			zzfdata.item_class = item_class;
			zzfdata.input_code = input_code;
			$.call("hospital_common.auditset.checksort.insertPriceCtrl", zzfdata, function(d) {
				if (d > 0) {
					query();
				}
			}, null, {
				nomask : true
			});
		}

		//批量添加
		function addSelect() {
			//整理数据
			var selectData = $("#lefttable").getSelected();
			var item_codes = "("
			if (selectData.length > 0) {
				$.confirm("您已选中" + selectData.length + "条信息，是否全部添加？", function(choose) {
					if (choose == true) {
						$.each(selectData, function(i, val) {
							item_codes += "'" + val.data.item_code + "',"
						});
						item_codes = item_codes.substring(0, item_codes.length - 1) + ")";
						$.call("hospital_common.auditset.checksort.insertPriceCtrlBatch", {
							"sort_id" : tdata.sort_id,
							"type" : tdata.type,
							"item_codes" : item_codes
						}, function(d) {
							query();
						}, null, {
							nomask : true
						});
					}
				});
			} else {
				$.message("您还未选中信息，请选中后再添加!")
			}
		}

		//删除警告弹窗
		function delWindow(id) {
			$.confirm('确定删除?删除之后需重新添加！', function(choose) {
				if (choose == true) {
					del(id);
				}
			});
		}

		function del(id) {
			$.call("hospital_common.auditset.checksort.delPriceZzf", {
				"id" : id
			}, function(s) {
				query();
			});
		}
	</script>
</s:page>





