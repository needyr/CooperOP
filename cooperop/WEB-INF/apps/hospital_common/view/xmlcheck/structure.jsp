<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="xml数据结构模板维护" disloggedin="true">
        <link rel="stylesheet" type="text/css"
              href="${pageContext.request.contextPath}/theme/plugins/jstree/dist/themes/default/style.min.css" />
        <script
                src="${pageContext.request.contextPath}/theme/plugins/jstree/dist/jstree.min.js"></script>
        <script
                src="${pageContext.request.contextPath}/theme/pages/scripts/ui-tree.js"></script>
        <s:row>
            <div class="col-md-12">
                <s:form label="xml数据模板管理">
                    <s:toolbar>
                        <s:button onclick="add();" label="新增xml数据模板" icon="glyphicon glyphicon-plus-sign"></s:button>
                    </s:toolbar>
                    <s:row>
                        <div class="portlet-body">
                            <div id="tree_2" class="tree-demo"></div>
                        </div>
                    </s:row>
                </s:form>
            </div>
        </s:row>
</s:page>
<script>
    var nodeType = '${param.node_type}';
    $(document).ready(function() {
        treeInit(getTreeData());
    });
    function setChild(id, data) {
        var child = [];
        for (var i = 0; i < data.length; i++) {
            if (data[i].parent_node == id) {
                var d = {
                    "text" :  data[i].node_name + "<em style='display: none'>" + data[i].p_key + "-" + data[i].node_type + "</em>"
                };
                if (data[i].childnums > 0) {
                    d.children = setChild(data[i].p_key, data);
                }
                child.push(d);
            }
        }
        return child;

    }
    function getTreeData() {
        var treeData = [];
        $.call("hospital_common.xmlcheck.queryXmlByNodeType", {"node_type":nodeType}, function(rtn) {
            for (var i = 0; i < rtn.length; i++) {
                if (!rtn[i].parent_node) {
                    var showData = {
                        "text" : rtn[i].node_name  + "<em style='display: none'>" + rtn[i].p_key + "-" + rtn[i].node_type + "</em>"
                    };
                    if (rtn[i].childnums > 0) {
                        showData.children = setChild(rtn[i].p_key, rtn);
                    }
                    treeData.push(showData);
                }
            }
        }, null, {
            async : false
        });
        return treeData;
    }
    function treeInit(treeData) {
        $('#tree_2').jstree({
            'plugins' : [ "wholerow", "types","contextmenu" ],
            'core' : {
                "themes" : {
                    "responsive" : false
                },
                "data" : treeData
            },
            "types" : {
                "default" : {
                    "icon" : "fa fa-sliders"
                },
                "file" : {
                    "icon" : "fa fa-sliders"
                }
            },
            "contextmenu" : {
                "items" : {
                    "create" : {
                        "label" : "新增",
                        "action" : function(treeData) {
                            var inst = jQuery.jstree.reference(treeData.reference),
                                obj = inst.get_node(treeData.reference);
                            var dataValue = obj.text.substring(obj.text.indexOf(">")+1,obj.text.lastIndexOf("<"));
                            $.modal("structure_add.html","新增",{
                                "parent_node" : dataValue.split("-")[0],
                                "node_type" : dataValue.split("-")[1],
                                callback : function(rtn){
                                    if(rtn){
                                        location.reload();
                                    }
                                }
                            });
                        }
                    },
                    "rename" : {
                        "label" : "修改",
                        "action" : function(treeData) {
                            var inst = jQuery.jstree.reference(treeData.reference),
                                obj = inst.get_node(treeData.reference);
                            var dataValue = obj.text.substring(obj.text.indexOf(">")+1,obj.text.lastIndexOf("<"));
                            $.modal("structure_edit.html","修改",{
                                "p_key" : dataValue.split("-")[0],
                                callback : function(rtn){
                                    if(rtn){
                                        location.reload();
                                    }
                                }
                            });
                        }
                    },
                    "remove" : {
                        "label" : "删除",
                        "action" : function(treeData) {
                            $.confirm('是否确认删除该节点以及该节点所有子节点的信息？删除后将无法恢复！', function(yn){
                                if (yn){
                                    var inst = jQuery.jstree.reference(treeData.reference),
                                        obj = inst.get_node(treeData.reference);
                                    var dataValue = obj.text.substring(obj.text.indexOf(">")+1,obj.text.lastIndexOf("<"));
                                    console.log(dataValue);
                                    $.call("hospital_common.xmlcheck.deleteNode", {"p_key" : dataValue.split("-")[0]}, function(rtn) {
                                        if (rtn.result == 'success') {
                                            location.reload();
                                        }else{
                                            $.message("删除失败");
                                            location.reload();
                                        }
                                    },null,{async: false});
                                }
                            });
                        }
                    }
              }
            }
        });
    }
    function add(){
        $.modal("xml_structure_add.html","新增",{
            callback : function(rtn){
                if(rtn){
                    location.reload();
                }
            }
        });
    }
</script>