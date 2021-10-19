<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="节点属性修改" ismodal="true">
    <s:row>
        <s:form border="0" id="data-form" fclass="portlet light bordered">
            <s:toolbar>
                <s:button label="保存" onclick="save()" icon="glyphicon glyphicon-floppy-saved"></s:button>
                <s:button label="取消" onclick="$.closeModal(false);" icon=""></s:button>
            </s:toolbar>
            <s:row>
                <s:textfield label="节点代码" name="node_code" value="${node_code}"></s:textfield>
                <s:textfield label="节点名称" name="node_name" value="${node_name}"></s:textfield>

            </s:row>
            <s:row>
                <s:switch cols="1" label="是否必需" name="node_not" value="${node_not}" onvalue="0" offvalue="1"></s:switch>
                <s:switch cols="1" label="是否为null" name="node_null" value="${node_null}" onvalue="1"
                          offvalue="0"></s:switch>
            </s:row>
            <s:row>
                <s:switch cols="1" label="是否有子项" name="node_leaf" value="${node_leaf}" onvalue="0"
                          offvalue="1"></s:switch>
                <s:switch cols="1" label="是否可以多项" name="node_muti" onvalue="1" offvalue="0"
                          value="${node_muti}"></s:switch>
            </s:row>
            <s:row>
                <s:select label="校验方式" name="check_method" value="${check_method}"
                          action="hospital_common.xmlcheck.queryCheckMethod" cols="1">
                    <s:option value="$[check_id]" label="$[check_name]"></s:option>
                </s:select>
                <s:select label="问题提示等级" name="level" value="${level}"
                          action="hospital_common.xmlcheck.queryResultLevel" cols="1">
                    <s:option value="$[p_key]" label="$[levelname]"></s:option>
                </s:select>
            </s:row>
            <s:row>
                <s:textarea cols="2" label="校验表达式" style="min-height:200px"
                            name="node_method">${node_method}</s:textarea>
                <s:textfield label="方法参数" cols="2" value="${params}" name="params"></s:textfield>
            </s:row>
            <s:row>
                <s:textfield cols="1" label="正常查询结果数" value="${correct_num}" name="correct_num"></s:textfield>
            </s:row>
            <s:row>
                <s:textarea cols="2" label="结果提示信息" style="min-height:100px"
                            name="description">${description}</s:textarea>
            </s:row>
        </s:form>
    </s:row>
</s:page>
<script>
    function save() {
        if (!$("form").valid()) {
            $.message("还存在必填信息未填写,请检查后提交");
        } else {
            var formData = $("#data-form").getData();
            formData.p_key = '${param.p_key}';
            $.call("hospital_common.xmlcheck.updateNode", formData, function (rtn) {
                $.closeModal(rtn);
            });
        }
    }
</script>

