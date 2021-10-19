<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="新增模板类型" ismodal="true">
    <s:row>
        <s:form id="type-form" border="0">
            <s:toolbar>
                <s:button label="保存" onclick="saveData()" icon="glyphicon glyphicon-floppy-saved"></s:button>
                <s:button label="取消" onclick="$.closeModal(false);" icon=""></s:button>
            </s:toolbar>
            <s:row>
                <s:textfield label="模板名称" name="type_name" required="true" onchange="queryXMlType();"></s:textfield>
            </s:row>
            <s:row>
                <s:textfield label="模板描述" name="type_descrption"></s:textfield>
            </s:row>
        </s:form>
    </s:row>
</s:page>
<script>
    function saveData() {
        if (!$("form").valid()) {
            $.message("还存在必填信息未填写,请检查后提交");
            return;
        }
        var formData = $("#type-form").getData();
        if ('${param.type_id}') { //修改
            formData.type_id = '${param.type_id}';
            $.call("hospital_common.xmlcheck.updateXMLType", formData, function (rtn) {
                $.closeModal(rtn);
            });
        } else { //新增
            $.call("hospital_common.xmlcheck.saveXMLType", formData, function (rtn) {
                $.closeModal(rtn);
            });
        }

    }

    function queryXMlType() {
        var typeValue = $("input[name='type_name']").val();
        if (typeValue) {
            $.call("hospital_common.xmlcheck.queryXmlType", {"type_name": typeValue}, function (rtn) {
                if (rtn.count > 0) {
                    $.message("该节点类型已存在,请重新输入");
                    $("input[name='type_name']").val("");
                }
            });
        }
    }
</script>