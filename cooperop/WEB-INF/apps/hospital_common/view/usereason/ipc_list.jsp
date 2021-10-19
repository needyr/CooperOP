<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="合理用药使用理由管理">
    <link rel="stylesheet" type="text/css"
        href="${pageContext.request.contextPath}/theme/plugins/jstree/dist/themes/default/style.min.css" />
    <script
        src="${pageContext.request.contextPath}/theme/plugins/jstree/dist/jstree.min.js"></script>
    <script
        src="${pageContext.request.contextPath}/theme/pages/scripts/ui-tree.js"></script>
    <s:row>
    <div class="col-md-3">
       <s:form label="合理用药监控">
           <s:toolbar>
                 <s:button onclick="add();" label="新增项目类别"></s:button>
           </s:toolbar>
           <s:row>
               <div class="portlet-body">
                   <div id="tree_2" class="tree-demo"></div>
               </div>
           </s:row>
       </s:form>
    </div>
    <div class="col-md-9" >
        <s:row>
        <input type="hidden" name="getParentId" id="getParentId"/>
        <input type="hidden" name="getParentName" id="getParentName"/>
        <s:table id="table" label="使用理由管理" autoload="false" action="hospital_common.usereason.queryDetail" sort="true" >
            <input type="hidden" name="usereason_type_id" value="${usereason_type_id}" />
            <s:toolbar >
                  <s:button label="添加" onclick="addReason();" id="tool"></s:button>
            </s:toolbar>
            <s:table.fields>
                <s:table.field name="id" label="序号" hidden="true">${id}</s:table.field>
                <s:table.field name="usereasondetail_bh" label="理由编号">${usereasondetail_bh}</s:table.field>
                <s:table.field name="usereasondetail_name" label="完整名称">${usereasondetail_name}</s:table.field>
                <s:table.field name="usereasondetail_jiancheng" label="简称">${usereasondetail_jiancheng}</s:table.field>
                <s:table.field label="所属问题分类名" value="${usereasontype_name}" name="usereasontype_name"></s:table.field>
                <s:table.field name="beactive" label="是否启用" datatype="script" width="50">
                    if(record.beactive == '1'){
                        return "是"
                    }else{
                        return "否"
                    }
                </s:table.field>
                <s:table.field name="caozuo" label="操作" datatype="template" width="70">
                    <a href="javascript:void(0)" onclick="update('$[id]')">修改</a>
                    <a href="javascript:void(0)" onclick="Delete('$[id]')">删除</a>
                </s:table.field>
            </s:table.fields>
        </s:table>
        </s:row>
    </div>
    </s:row>
</s:page> 
<script type="text/javascript">
var getParentId="";

$(document).ready(function() {
    treeinit(getTreeData());
    $("#tool").hide();
});

function query(){
     $("#table").refresh();
}

function setchild(id,da) {
    var child = [];
    for(var i = 0; i < da.length; i++){
        if(id == da[i].usereason_product_id){
            var d={
                "text" : da[i].usereasontype_name,
                "date" : da[i].id+"-"+da[i].usereason_product_id
                };
            child.push(d);
        }
    }
    return child;
}

function getTreeData() {
    var r=[];
    $.call("hospital_common.usereason.query", {"sys_product_code":"ipc"}, function(rtn) {
        var parent = rtn.parent;
        var child = rtn.child;
        for (var i = 0; i < parent.length; i++) {
            var d = {
                "text" : parent[i].usereasonproduct_name,
                "date" : parent[i].id,
                "code" : parent[i].name 
            };
            d.children = setchild(parent[i].id,child);
            r.push(d);
        }
    }, null, {
        async : false
    });
    return r;
}

function treeinit(d) {
    $('#tree_2').jstree({
        'plugins' : [ "wholerow","types", "contextmenu" ],
        'core' : {
            "themes" : {
                "responsive" : false
            },
            "check_callback" : false,
            "data" : d
        },
        "types" : {
            "default" : {
                "icon" : "fa fa-folder icon-state-warning icon-lg"
            },
            "file" : {
                "icon" : "fa fa-file icon-state-warning icon-lg"
            }
        },
        "contextmenu" : {
            "items" : {
                "create" : {
                    "label" : "新增",
                    "action" : function(data) {
                        var inst = jQuery.jstree.reference(data.reference),  
                        obj = inst.get_node(data.reference);  
                        var p = inst.get_parent(data.reference);
                        var check=obj.original.date.split("-")[1];
                        if(check !=null && check !=''){
                            addReason();
                        }else{
                            $.modal("edit.html","新增",{
                                width:"500px",
                                height:"450px",
                                "usereason_product_id" : obj.original.date.split("-")[0],
                                "product_code":"ipc",
                                callback : function(r){
                                    if(r){
                                    	reflashTree();
                                    }
                                }
                            });
                        }
                    }
                },
                "rename" : {
                    "label" : "修改",
                    "action" : function(data) {
                        var inst = jQuery.jstree.reference(data.reference),  
                        obj = inst.get_node(data.reference);
                        var p = inst.get_parent(data.reference);
                        $.modal("edit.html","修改",{
                            width:"500px",
                            height:"450px",
                            "id" : obj.original.date.split("-")[0],
                            "usereason_product_id" : obj.original.date.split("-")[1],
                            "product" : obj.original.code,
                            "product_code":"ipc",
                            callback : function(r){
                                if(r){
                                	reflashTree();
                                }
                            }
                        });
                    }
                },
                "remove" : {
                    "label" : "删除",
                    "action" : function(data) {
                        var inst = jQuery.jstree.reference(data.reference),  
                        obj = inst.get_node(data.reference);  
                        $.confirm("确认删除该项以及它的所有子项？\t\n删除后无法恢复！",function callback(e){
                            if(e==true){
                                $.call("hospital_common.usereason.delete", {"id" : obj.original.date.split("-")[0],"usereason_product_id" : obj.original.date.split("-")[1]}, function(rtn) {
                                    if (rtn > 0) {
                                    	reflashTree();
                                        query();
                                    }
                                },null,{async: false});
                            }
                        });
                    }
                }
            }
        }
    }).bind("activate_node.jstree",function(obj,e){
        var currentNode=e.node;
        var id = currentNode.original.date.split("-")[0];
        var pid = currentNode.original.date.split("-")[1];
        var name = currentNode.text;
        if(pid!=null&&pid!=''){
            $("#getParentId").val(id);
            $("#getParentName").val(name);
            $("#table").params({"parent_id":id});
            $("#table").refresh();
            $("#tool").show();
        }else{
        	$("#tool").hide();
        	$("#table").params();
            $("#table").refresh();
        }
    });
}

function reflashTree(){
    $('#tree_2').jstree(true).settings.core.data = getTreeData();
    $('#tree_2').jstree(true).refresh();
}

function add(){
    $.modal("edit.html","新增",{
        width:"500px",
        height:"450px",
        "product_code":"ipc",
        "usereason_product_id" : '',
        callback : function(r){
            if(r){
            	reflashTree();
            }
        }
    });
}

function addReason(){
    var id=$("#getParentId").val();
    var name=$("#getParentName").val();
    $.modal("addreason.html","新增",{
        width:"500px",
        height:"400px",
        "parent_id":id, 
        "parent_name":name,
        callback : function(r){
            if(r){
            	query();
            }
        }
    });
}

function update(id){
    var name=$("#getParentName").val();
    var parent_id=$("#getParentId").val();
    $.modal("addreason.html","修改",{
        width:"500px",
        height:"400px",
        "id":id,
        "parent_id":parent_id,
        "parent_name":name,
        "product_code":"ipc",
        callback : function(e){
            query();
        }
    });
}

function Delete(id){
    $.confirm("确认删除？\t\n删除后无法恢复！",function callback(e){
        if(e==true){
            $.call("hospital_common.usereason.deleteDetail",{"id":id},function(s){
                query();
            });
        }
    });
}
</script>