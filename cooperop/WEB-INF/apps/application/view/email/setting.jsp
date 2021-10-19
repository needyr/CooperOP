<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="我的邮件-设置" dispermission="true">
	<s:row>
		<div class="col-md-12">
			<s:tabpanel>
				<s:table id="folderlist" label="我的文件夹" active="true" action="application.email.queryMineFolder" autoload="true" icon="fa fa-folder-o" sort="false" limit="0">
					<s:toolbar>
						<s:button label="新建文件夹" icon="fa fa-plus" onclick="newFolder()"></s:button>
					</s:toolbar>
					<s:table.fields>
						<s:table.field name="name" label="文件夹" datatype="script">
							var blank_str = "&nbsp;&nbsp;&nbsp;&nbsp;";
							var html = [];
							for (var i = 1; i < record.level; i ++) {
								html.push(blan_str);
							}
							if (record.child_num > 0) {
								html.push('<i class="fa fa-plus-square-o" style="margin-right:5px;"></i>');
							}
							html.push('<i class="fa fa-folder" style="color:#E8AC51;margin-right:5px;"></i>');
							html.push(record.name);
							html.push('<a href="javascript:void(0);" onclick="modifyName(\'' + record.id + '\', \'' + record.name + '\')" style="margin-left:10px;">[修改]</a>');
							return html.join("");
						</s:table.field>
						<s:table.field name="noreadnum" label="未读邮件" datatype="number" align="right">
						</s:table.field>
						<s:table.field name="email_num" label="总邮件" datatype="number" align="right">
						</s:table.field>
						<s:table.field name="order_no" label="排序" datatype="template" align="center" width="100">
							<s:textfield datatype="number" name="order_no" value="$[order_no]" fid="$[id]" onblur="modifyOrder('$[id]', $(this).val(), '$[order_no]');"></s:textfield>
						</s:table.field>
						<s:table.field name="oper" label="操作" datatype="template" align="center" width="100">
							<a href="javascript:void(0);" onclick="deleteFolder('$[id]', '$[name]')">删除</a>
						</s:table.field>
					</s:table.fields>
				</s:table>
				<s:table id="serverlist" label="其他邮箱" action="application.email.queryMineServer" autoload="true" icon="fa fa-globe" sort="false" limit="0">
					<s:toolbar>
						<s:button label="新建外部邮箱" icon="fa fa-plus" onclick="newServer();"></s:button>
					</s:toolbar>
					<s:table.fields>
						<s:table.field name="name" label="邮箱" datatype="string">
						</s:table.field>
						<s:table.field name="email" label="邮件地址" datatype="string">
						</s:table.field>
						<s:table.field name="cycle_time" label="收件周期（分钟）" datatype="number" align="right">
						</s:table.field>
						<s:table.field name="oper" label="操作" datatype="template" align="center" width="100">
							<a href="javascript:void(0);" onclick="modifyServer('$[id]', '$[name]')" style="margin-right:10px;">修改</a>
							<a href="javascript:void(0);" onclick="deleteServer('$[id]', '$[name]')">删除</a>
						</s:table.field>
					</s:table.fields>
				</s:table>
			</s:tabpanel>
		</div>
	</s:row>
</s:page>
<script type="text/javascript">
function newFolder() {
	$.inputbox("新建文件夹", "", function(text) {
		if (text) {
			$.call("application.email.insertFolder", {name: text}, function(rtn) {
				$("#folderlist").refresh();
			});				
		}
	}, "请输入新建文件夹名称...");
}
function modifyName(folder_id, folder_name) {
	$.inputbox("修改文件夹名称", folder_name, function(text) {
		if (text) {
			$.call("application.email.updateFolder", {id: folder_id, name: text}, function(rtn) {
				$("#folderlist").refresh();
			});				
		}
	}, "将文件夹“" + folder_name + "”改为：");
}
function modifyOrder(folder_id, order_no, old_order) {
	if (+order_no > 0 && +order_no != +old_order) {
		$.call("application.email.updateFolder", {id: folder_id, order_no: order_no}, function(rtn) {
			$("#folderlist").refresh();
		});	
	}			
}
function deleteFolder(folder_id, folder_name) {
	$.confirm("是否确认删除文件夹“" + folder_name + "”?<br/>PS.文件夹中的所有邮件将会被移动回收件箱。", function(choose) {
		if (choose) {
			$.call("application.email.deleteFolder", {id: folder_id}, function(rtn) {
				$("#folderlist").refresh();
			});	
		}			
	});
}
function newServer() {
	var url = cooperopcontextpath + "/w/application/email/modifyServer.html";
	$.modal(url, "新增外部邮箱", {
		callback: function(rtn) {
			if (rtn) {
				$("#serverlist").refresh();
			}
		}
	});
}
function modifyServer(server_id, server_name) {
	var url = cooperopcontextpath + "/w/application/email/modifyServer.html";
	$.modal(url, "修改外部邮箱“" + server_name + "”", {
		id: server_id,
		callback: function(rtn) {
			if (rtn) {
				$("#serverlist").refresh();
			}
		}
	});
}
function deleteServer(server_id, server_name) {
	$.confirm("是否确认删除外部邮箱“" + server_name + "”?", function(choose) {
		if (choose) {
			$.call("application.email.deleteServer", {id: server_id}, function(rtn) {
				$("#serverlist").refresh();
			});	
		}			
	});
}

</script>
