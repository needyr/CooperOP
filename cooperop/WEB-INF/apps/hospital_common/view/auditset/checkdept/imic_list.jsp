<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="医保控费审查部门维护" >
	<s:row>
		<s:form id="form" label="快速查找">
			<s:row>
				<s:textfield label="关键字" name="condition" placeholder="请输入部门编号或名称" cols="2"></s:textfield>
				<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="参与医保控费审查的部门列表" autoload="false" action="hospital_common.auditset.checkdept.query">
			<s:toolbar>
				<s:button icon="" label="开启全部科室" onclick="_open('all')"></s:button>
				<s:button icon="" label="开启住院科室" onclick="_open('1')"></s:button>
				<s:button icon="" label="开启门诊科室" onclick="_open('2')"></s:button>
				<s:button icon="" label="开启急诊科室" onclick="_open('3')"></s:button>
				<s:button icon="" label="关闭全部科室" onclick="_close('all')"></s:button>
				<s:button icon="" label="关闭住院科室" onclick="_close('1')"></s:button>
				<s:button icon="" label="关闭门诊科室" onclick="_close('2')"></s:button>
				<s:button icon="" label="关闭急诊科室" onclick="_close('3')"></s:button>
				<s:button icon="" label="新增" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="id" label="ID"  ></s:table.field>
				<s:table.field name="dept_code" label="部门编号"  ></s:table.field>
				<s:table.field name="dept_name" label="部门名称" ></s:table.field>
				<s:table.field name="update_time" label="最后操作时间" datatype="script">
					return record.update_time == null?record.create_time: record.update_time;
				</s:table.field> 
				<s:table.field name="oper_user" label="操作人员" ></s:table.field>
				<s:table.field name="remark" label="备注" ></s:table.field> 
				<s:table.field name="state" label="状态" datatype="script">
					var state = record.state;
					if(state == 1){
						return '<font style="margin-left: 5px;color:#20862b">启用</font>';
					}else if(state == 0){
						return '<font style="margin-left: 5px;color:#000000">停用</font>';
					}
				</s:table.field>
				<s:table.field name="client_install" label="审方客户端" datatype="script">
					var client_install = record.client_install;
					if(client_install == 1){
						return '<font style="margin-left: 5px;color:#20862b">已安装</font>';
					}else if(client_install == 0){
						return '<font style="margin-left: 5px;color:#000000">未安装</font>';
					}
				</s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="100">
					<a href="javascript:void(0)" onclick="update('$[id]')">修改</a>
					<a href="javascript:void(0)" onclick="del('$[id]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
var ref_client_ins = 1;
$(function(){
	query();
});

/* function cjt(_this){
	var state = $(_this).text();
	if(state == '启用'){
		$(_this).text('停用');
		$(_this).css('color', '#000000')
	}else if(state == '停用'){
		$(_this).text('启用');
		$(_this).css('color', '#20862b')
	} 
} */

function query(){
	var qdata=$("#form").getData();
	qdata.product_code = 'hospital_imic';
	qdata.ref = ref_client_ins;
	$("#datatable").params(qdata);
	$("#datatable").refresh();
	if(ref_client_ins == 1){
		ref_client_ins = 0;
	}
}

function Add(){
	$.modal("edit.html","新增",{
		width:"50%",
		height:"70%",
		product_code : 'hospital_imic',
		callback : function(e){
			query();
		}
	});
}

function update(id){
	$.modal("edit.html","修改",{
		width:"50%",
		height:"70%",
		id: id,
		product_code : 'hospital_imic',
		callback : function(e){
			query();
		}
	});
}

function del(id){
	$.confirm("是否确认删除，删除后无法恢复！",function callback(e){
		if(e==true){
			$.call("hospital_common.auditset.checkdept.delete",{id: id},function(rtn){
				if(rtn){
					query();
				}
			})
		}
	});
}

function _open(dept){
	$.call('hospital_common.auditset.checkdept.open_close',{type:'open',dept:dept,product_code:'hospital_imic'},function(){
		query();
	})
}

function _close(dept){
	$.call('hospital_common.auditset.checkdept.open_close',{type:'close',dept:dept,product_code:'hospital_imic'},function(){
		query();
	})
}
</script>