<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="拦截等级配对">
    <s:row>
        <s:table id="datatable" label="拦截等级匹配维护" autoload="false" action="hospital_common.checklevel.query" >
            <s:toolbar>
                <s:button icon="" label="新增匹配" onclick="add()"></s:button>
            </s:toolbar>
            <s:table.fields>
                <s:table.field name="sys_check_level" label="标准拦截级别"></s:table.field>
                <s:table.field name="sys_check_level_name" label="标准拦截级别名称" ></s:table.field>
                <s:table.field name="check_level" label="合理用药严重级别"  ></s:table.field>
                <s:table.field name="check_level_name" label="合理用药严重级别名称" ></s:table.field>
                <s:table.field name="star_level" label="等级星级" ></s:table.field>
                <s:table.field name="system_product_code" hidden="true"></s:table.field>
                <s:table.field name="name" label="产品名" ></s:table.field>
                <s:table.field name="source" label="审查来源" ></s:table.field>
                <%-- <s:table.field name="is_active" label="是否启用" datatype="script" >
                    if(record.is_active == '1'){
                        return "是";
                    }else{
                        return "否";
                    }
                </s:table.field> --%>
                <s:table.field name="caozuo" label="操作" datatype="script" >
                    var html = [];
				    html.push('<a href=\"javascript:void(0)\" onclick=\"update(\''+record.sys_check_level+'\',\''+record.name+'\')\">修改</a>  ');
				    return html.join("");
                </s:table.field>
            </s:table.fields>
        </s:table>
    </s:row>
</s:page>
<script>
/* html.push('<a href=\"javascript:void(0)\" onclick=\"Delete(\''+record.sys_check_level+'\',\''+record.system_product_code+'\',\''+record.check_level+'\')\">删除</a>  ');
html.push('<a href=\"javascript:void(0)\" onclick=\"isactive(\''+record.sys_check_level+'\',\''+record.is_active+'\')\">');
html.push(record.is_active==1?"关闭":"开启")
html.push('</a>'); */
$(function(){
	query();
});
function query(){
    $("#datatable").refresh();
}
function isactive(id,is_active){
	$.call("hospital_common.checklevel.updateActive",{"sys_check_level":id,"is_active":is_active},function(){
		query();
	});
}
function add(){
    $.modal("edits.html","新增",{
        width:"450px",
        height:"70%",
        callback : function(e){
            query();
        }
    });
}
function update(sys_check_level,name){
    $.modal("edits.html","修改",{
        width:"450px",
        height:"70%",
        'sys_check_level':sys_check_level,
        callback : function(e){
            query();
        }
    });
}

function Delete(sys_check_level,system_product_code,check_level){
    $.confirm("确认删除？\t\n删除后无法恢复！",function callback(e){
        if(e==true){
            $.call("hospital_common.checklevel.delete",{"sys_check_level":sys_check_level,"system_product_code":system_product_code,"check_level":check_level},function(s){
                query();
            });
        }
    });
}

</script>