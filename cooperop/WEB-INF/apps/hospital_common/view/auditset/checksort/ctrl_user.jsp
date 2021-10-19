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
<s:page title="人员控制转自费" disloggedin="true">
	<s:row>
		<div id="all">
			<div id="all_left">
				<div id="left">
					<s:row>
						<s:form label="人员字典信息筛选" id="leftform">
							<s:row>
								<s:autocomplete label="选择科室"  name="dept_code" placeholder="请输入科室编码、名称或拼音首字母"
								 cols="2" action="hospital_common.auditset.checksort.queryDeptAll" limit="5">
									<s:option value="$[dept_code]" label="$[dept_name]">$[dept_code] $[dept_name]</s:option>
								</s:autocomplete>
							</s:row>
							<s:row>
								<s:textfield label="工作角色" name="job_role" placeholder="请输入工作角色" cols="2"></s:textfield>
							</s:row>
							<s:row>
								<s:textfield label="人员信息" placeholder="请输入编码、名称或拼音首字母" name="filter_info" cols="2"></s:textfield>
								<s:button label="查询" icon="fa fa-search" onclick="queryUser()" style="background: #368bdb;text-align: center;color: #FFF;"></s:button>
							</s:row>
						</s:form>
					</s:row>

					<s:row>
						<s:table action="hospital_common.auditset.checksort.queryUser" autoload="false" id="lefttable" label="人员字典信息" sort="true" select="multi">
							<s:toolbar>
								<s:button label="添加已选中" onclick="addSelect()" icon="fa fa-plus"></s:button>
							</s:toolbar>
							<s:table.fields>
								<s:table.field name="user_id" label="人员编码" sort="true"></s:table.field>
								<s:table.field name="user_name" label="人员名称" sort="true"></s:table.field>
								<s:table.field name="sex" label="性别" sort="true"></s:table.field>
								<s:table.field name="dept_name" label="科室名称" sort="true"></s:table.field>
								<s:table.field name="job_role" label="工作角色" sort="true"></s:table.field>
								<s:table.field name="caozuo" label="转自费设置" datatype="template">
									<a onclick="addZzf('$[user_id]', '$[user_name]','$[user_dept]', '$[sex]','$[job_role]','$[input_code]')">添加</a>
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
								<s:autocomplete label="选择科室"  name="dept_code" placeholder="请输入科室编码、名称或拼音首字母"
								 cols="2" action="hospital_common.auditset.checksort.queryDeptAll" limit="5">
									<s:option value="$[dept_code]" label="$[dept_name]">$[dept_code] $[dept_name]</s:option>
								</s:autocomplete>
							</s:row>
							<s:row>
								<s:textfield label="工作角色" name="job_role" placeholder="请输入工作角色" cols="2"></s:textfield>
							</s:row>
							<s:row>
								<s:textfield label="人员信息" placeholder="请输入编码、名称或拼音首字母" name="filter_info" cols="2"></s:textfield>
								<s:button label="查询" icon="fa fa-search" onclick="queryUserCtrl()" style="background: #368bdb;text-align: center;color: #FFF;"></s:button>
							</s:row>
					</s:form>
				</s:row>

				<s:row>
					<s:table action="hospital_common.auditset.checksort.queryUserZzf" autoload="false" id="zzfTable" sort="true" label="${param.type_name }">
						<s:table.fields>
							<s:table.field name="create_time" label="创建时间" datatype="datetime" sort="true" defaultsort="desc"></s:table.field>
							<s:table.field name="user_id" label="人员编码" sort="true"></s:table.field>
							<s:table.field name="user_name" label="人员名称" sort="true"></s:table.field>
							<s:table.field name="sex" label="性别" sort="true"></s:table.field>
							<s:table.field name="dept_name" label="科室名称" sort="true"></s:table.field>
							<s:table.field name="job_role" label="工作角色" sort="true"></s:table.field>
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
			queryUser();
			queryUserCtrl();
		}

		//刷新辅助表
		function queryUser() {
			var qdata = $("#leftform").getData();
			qdata.sort_id = tdata.sort_id;
			qdata.type = tdata.type;
			$("#lefttable").params(qdata);
			$("#lefttable").refresh();
		}

		//控制人员转自费表
		function queryUserCtrl() {
			var qdata = $("#rightform").getData();
			qdata.sort_id = tdata.sort_id;
			qdata.type = tdata.type;
			$("#zzfTable").params(qdata);
			$("#zzfTable").refresh();
		}

		//添加人员信息
		function addZzf(user_id, user_name, user_dept, sex, job_role,input_code) {
			var zzfdata = {};
			zzfdata.type = tdata.type;
			zzfdata.sort_id = tdata.sort_id;
			zzfdata.user_id = user_id;
			zzfdata.user_name = user_name;
			zzfdata.user_dept = user_dept;
			zzfdata.sex = sex;
			zzfdata.job_role = job_role;
			zzfdata.input_code = input_code;
			$.call("hospital_common.auditset.checksort.insertUserCtrl", zzfdata, function(d) {
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
			var user_ids = "("
			if (selectData.length > 0) {
				$.confirm("您已选中" + selectData.length + "条信息，是否全部添加？", function(choose) {
					if (choose == true) {
						$.each(selectData, function(i, val) {
							user_ids += "'" + val.data.user_id + "',"
						});
						user_ids = user_ids.substring(0, user_ids.length - 1) + ")";
						$.call("hospital_common.auditset.checksort.insertUserCtrlBatch", {
							"sort_id" : tdata.sort_id,
							"type" : tdata.type,
							"user_ids" : user_ids
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
			$.call("hospital_common.auditset.checksort.delUserZzf", {
				"id" : id
			}, function(s) {
				query();
			});
		}
	</script>
</s:page>





