<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="XML节点添加" ismodal="true">
    <s:row>
        <s:form border="0" id="data-form" fclass="portlet light bordered">
            <s:toolbar>
                <s:button label="保存" onclick="saveData()" icon="glyphicon glyphicon-floppy-saved"></s:button>
                <s:button label="取消" onclick="$.closeModal(false);" icon=""></s:button>
            </s:toolbar>
            <s:row>
                <c:if test="${empty param.parent_node}">
                    <s:select id="node-type" name="node_type" label="XML结构模板" required="true"
                              action="hospital_common.xmlcheck.queryXmlType">
                        <s:option value="$[type_id]" label="$[type_id]">
                            <span>$[type_name]</span>
                            <span style="margin-left: 80px;">$[type_descrption]</span>
                        </s:option>
                    </s:select>
                    <s:button label="新增模板类型" onclick="addNodeType();" style="margin-left:30px;border:0;"></s:button>
                </c:if>
            </s:row>
            <s:row>
                <c:if test="${empty param.parent_node}">
                    <s:textfield label="父节点Id" name="parent_node" value="${param.parent_node}"></s:textfield>
                </c:if>
                <s:textfield label="节点代码" name="node_code"></s:textfield>
                <s:textfield label="节点名称" name="node_name" required="true"></s:textfield>
            </s:row>
            <s:row>
                <s:switch cols="1" label="是否必需" name="node_null" onvalue="0" offvalue="1" required="true"></s:switch>
                <s:switch cols="1" label="是否为null" name="node_leaf" onvalue="1" offvalue="0" required="true"></s:switch>
            </s:row>
            <s:row>
                <s:switch cols="1" label="是否有子项" name="node_not" onvalue="0" offvalue="1" required="true"></s:switch>
                <s:switch cols="1" label="是否可以多项" name="node_muti" onvalue="1" offvalue="0" required="true"></s:switch>
            </s:row>
            <s:row>
                <s:select label="校验方式" name="check_method" action="hospital_common.xmlcheck.queryCheckMethod" cols="1">
                    <s:option value="$[check_id]" label="$[check_name]"></s:option>
                </s:select>
                <s:select label="问题提示等级" name="level" action="hospital_common.xmlcheck.queryResultLevel" cols="1">
                    <s:option value="$[p_key]" label="$[levelname]"></s:option>
                </s:select>
            </s:row>
            <s:row>
                <s:textarea cols="2" label="校验表达式" style="min-height:100px" name="node_method"></s:textarea>
                <s:textfield label="方法参数" cols="2" name="params"></s:textfield>
            </s:row>
            <s:row>
                <s:textfield cols="1" label="正常查询结果数" name="correct_num"></s:textfield>
            </s:row>
            <s:row>
                <s:textarea cols="2" label="结果提示信息" style="min-height:100px" name="description"></s:textarea>
            </s:row>
        </s:form>
    </s:row>
</s:page>
<script>
    var node = {
        type : '${param.node_type}',
        parent : '${param.parent_node}'
    }
    $(function () {
        if (node.type) {
            $("input[name='node_type']").attr("disabled", "disabled");
            $("input[name='parent_node']").attr("disabled", "disabled");
        }
    });

    function saveData() {
        if (!$("form").valid()) {
            $.message("还存在必填信息未填写,请检查后提交");
            return;
        }
        var formData = $("#data-form").getData();
        if (node.type) {
            formData.node_type = node.type;
            formData.parent_node = node.parent;
        }
        $.call("hospital_common.xmlcheck.saveNode", formData, function (rtn) {
            $.closeModal(rtn);
        });
    }
    function addNodeType() {
        $.modal("type_edit.html", "新增", {
            width: '30%',
            height: '30%',
            callback: function (rtn) {
                if (rtn) {
                    location.reload();
                }
            }
        });
    }
</script>
