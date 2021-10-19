<%@page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<style>
    .text1 {
        width: 100px;
        overflow: hidden;
        text-overflow: ellipsis;
        -o-text-overflow: ellipsis;
        -webkit-text-overflow: ellipsis;
        -moz-text-overflow: ellipsis;
        white-space: nowrap;
    }
</style>
<s:page title="项目调整记录维护">
    <div class="note note-warning">
        <p>实施人员进行升级和版本更新时必须进行记录，为后期统一版本做好准备</p>
    </div>
    <s:row>
        <s:form border="0" id="form" label="查找">
            <s:row>
                <s:autocomplete label="版本" name="version"
                                action="hospital_common.version_message.ycprojectupgradelog.queryVersion"
                                required="false" cols="1" limit="50">
                    <s:option value="$[version]" label="$[version]">
                        $[version]
                    </s:option>
                </s:autocomplete>
                <s:textfield label="产品" name="product_name" required="false"></s:textfield>
            </s:row>
            <s:row>
                <s:datefield label="开始时间" name="start_time" required="false"></s:datefield>
                <s:datefield label="结束时间" name="stop_time" required="false"></s:datefield>
            </s:row>
        </s:form>
    </s:row>
    <s:row>
        <s:table id="datatable" label="项目调整记录管理" autoload="false"
                 action="hospital_common.version_message.ycprojectupgradelog.query"  sort="true">
            <s:toolbar>
                <s:button icon="" label="查询" onclick="query()"></s:button>
                <s:button icon="" label="导出" onclick="daochu()"></s:button>
                <s:button icon="" label="新增项目调整记录" onclick="Add()"></s:button>
            </s:toolbar>
            <s:table.fields>
                <s:table.field name="upgrade_id" label="记录标识" datatype="" width="500px" sort="true"></s:table.field>
                <s:table.field name="product_name" label="产品名称" datatype="" width="200px" sort="true"></s:table.field>
                <s:table.field name="time" label="调整时间" sort="true"></s:table.field>
                <s:table.field name="version" label="版本" datatype="" sort="true"></s:table.field>
                <s:table.field name="update_content" label="更新内容" datatype="script" height="250px">
                    var html = [];
                    html.push('<div class="text1"><a style="color:black;text-decoration: none;" title="'+record.update_content+'" onclick="queryUpdateMg('+record.upgrade_id+')">'+record.update_content+'</a></div>');
                    return html.join('');
                </s:table.field>
                <s:table.field name="caozuo" label="操作" datatype="template" width="70px">
                    <a href="javascript:void(0)" onclick="update('$[upgrade_id]')">修改</a>
                    <!-- <a href="javascript:void(0)" onclick="Delete('$[module_id]')">删除</a> -->
                </s:table.field>
            </s:table.fields>
        </s:table>
    </s:row>
</s:page>
<script type="text/javascript">
    $(function () {
        query();
    });

    function query() {
        var qdata = $("#form").getData();
        $("#datatable").params(qdata);
        $("#datatable").refresh();
    }

    function Add() {
        $.modal("edit.html", "新增记录", {
            width: "30%",
            height: "40%",
            callback: function (e) {
                query();
            }
        });
    }

    function update(upgrade_id) {
        $.modal("edit.html", "修改记录", {
            width: "30%",
            height: "40%",
            upgrade_id: upgrade_id,
            callback: function (e) {
                query();
            }
        });
    }

    function Delete(module_id) {
        $.confirm("是否确认删除？", function callback(e) {
            if (e == true) {
                $.call("hospital_common.version_message.ycmoduledict.delete", {"module_id": module_id}, function (rtn) {
                    query();
                })
            }
        })
    }

    function daochu() {
        $("#datatable").export_table();
    }

    function queryUpdateMg(upgrade_id) {
        $.modal("edit.html", "更新内容", {
            width: "30%",
            height: "30%",
            upgrade_id: upgrade_id,
            msg: "t",
            callback: function (e) {
                query();
            }
        });
    }

</script>