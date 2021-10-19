<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="xml数据格式校验" disloggedin="true">
    <style>
        .main {
            padding: 0 !important;
        }

        #out-input {
            padding: 10px;
            max-height: 700px;
            border: 1px solid whitesmoke;
            overflow: auto;
            height: 700px;
            padding: 10px;
        }

        #out-input span:after {
            content: '';
            border-bottom: 1px solid #f3f0f0;
            height: 1px;
            display: block;
            margin-top: 4px;
            margin-bottom: 8px;
        }

        #out-input span em {
            font-style: normal;
        }
    </style>
    <s:row>
        <s:form label="XML数据结构校验" id="data-form">
            <s:row>
                <s:select id="node-type" name="node_type" label="XML结构模板" required="true"
                          action="hospital_common.xmlcheck.queryXmlType">
                    <s:option value="$[type_id]" label="$[type_name]"></s:option>
                </s:select>
                <s:button label="执行校验" cols="1" id="execute-btn" style="margin-left:30px;" onclick="query()"
                          icon="glyphicon glyphicon-search"></s:button>
                <s:button label="维护xml结构模板" cols="1" id="edit-btn" style="margin-left:30px;border:0;"
                          onclick="maintainXml();"></s:button>
            </s:row>
            <s:row>
                <div class="col-md-6">
                    <s:row>
                        <s:textarea cols="3" id="input-data" name="input_data"
                                    style="min-height:700px;max-height:700px;"
                                    label="待校验的XML" required="true"></s:textarea>
                    </s:row>
                </div>
                <div class="col-md-6">
                    <s:row>
                        <div id="out-input">
                        </div>
                    </s:row>
                </div>
            </s:row>
        </s:form>
    </s:row>
</s:page>
<script>
    $(function () {
        //默认加载值
        document.getElementById("input-data").value = "<request>\n" +
            "    <p_type>1</p_type>\n" +
            "    <d_type>1</d_type>\n" +
            "    <doctor>\n" +
            "        <code>1350</code>\n" +
            "        <name>杨德建</name>\n" +
            "        <duty>医生</duty>\n" +
            "        <departcode>20</departcode>\n" +
            "        <departname>重症医学科</departname>\n" +
            "    </doctor>\n" +
            "    <patient>\n" +
            "        <id>397189|2</id>\n" +
            "        <visitid>1</visitid>\n" +
            "        <code>885553127</code>\n" +
            "        <name>邓文强</name>\n" +
            "        <chargetype></chargetype>\n" +
            "        <sex>男</sex>\n" +
            "        <departcode>20</departcode>\n" +
            "        <departname>重症医学科</departname>\n" +
            "        <admissiondate>2020-12-27</admissiondate>\n" +
            "        <idcardno>510128196604172452</idcardno>\n" +
            "        <bedno></bedno>\n" +
            "        <type></type>\n" +
            "        <status>一般人员</status>\n" +
            "        <birthday>1966-04-17</birthday>\n" +
            "        <weight>0.00</weight>\n" +
            "    </patient>\n" +
            "    <diagnosis_info>\n" +
            "        <diagnosis>\n" +
            "            <date>2020-12-27 16:35:51</date>\n" +
            "            <no>1</no>\n" +
            "            <type>初步诊断</type>\n" +
            "            <code>I63.900</code>\n" +
            "            <desc>脑梗死</desc>\n" +
            "            <diagnosis_first>2</diagnosis_first>\n" +
            "        </diagnosis>\n" +
            "    </diagnosis_info>\n" +
            "    <rows>\n" +
            "        <row>\n" +
            "            <p_key>397189|157040989|160682968|30822</p_key>\n" +
            "            <repeat_indicator>1</repeat_indicator>\n" +
            "            <start_date_time>2020-12-29 17:00:00</start_date_time>\n" +
            "            <order_class></order_class>\n" +
            "            <order_code>30822|5757</order_code>\n" +
            "            <order_text>0.9%氯化钠注射液(基)(双阀)</order_text>\n" +
            "            <group_id>157040989</group_id>\n" +
            "            <order_no>157040989</order_no>\n" +
            "            <order_sub_no>160682968</order_sub_no>\n" +
            "            <dosage>48.000</dosage>\n" +
            "            <dosage_units>ml</dosage_units>\n" +
            "            <shl>0.48</shl>\n" +
            "            <drug_unit>ml</drug_unit>\n" +
            "            <administration>泵入</administration>\n" +
            "            <frequency>Q6H</frequency>\n" +
            "            <order_status>0</order_status>\n" +
            "            <stop_date_time></stop_date_time>\n" +
            "            <doctor_no>1350</doctor_no>\n" +
            "            <doctor>杨德建</doctor>\n" +
            "            <ordering_dept>20</ordering_dept>\n" +
            "            <ordering_deptname>重症医学科</ordering_deptname>\n" +
            "            <beizhu></beizhu>\n" +
            "            <enter_date_time>2020-12-29 17:00:00</enter_date_time>\n" +
            "        </row>\n" +
            "        <row>\n" +
            "            <p_key>397189|157040989|160682972|31079</p_key>\n" +
            "            <repeat_indicator>1</repeat_indicator>\n" +
            "            <start_date_time>2020-12-29 17:00:00</start_date_time>\n" +
            "            <order_class></order_class>\n" +
            "            <order_code>31079|6049</order_code>\n" +
            "            <order_text>注射用艾司奥美拉唑钠(艾速平)</order_text>\n" +
            "            <group_id>157040989</group_id>\n" +
            "            <order_no>157040989</order_no>\n" +
            "            <order_sub_no>160682972</order_sub_no>\n" +
            "            <dosage>40.000</dosage>\n" +
            "            <dosage_units>mg</dosage_units>\n" +
            "            <shl>1.00</shl>\n" +
            "            <drug_unit>mg</drug_unit>\n" +
            "            <administration>泵入</administration>\n" +
            "            <frequency>Q6H</frequency>\n" +
            "            <order_status>0</order_status>\n" +
            "            <stop_date_time></stop_date_time>\n" +
            "            <doctor_no>1350</doctor_no>\n" +
            "            <doctor>杨德建</doctor>\n" +
            "            <ordering_dept>20</ordering_dept>\n" +
            "            <ordering_deptname>重症医学科</ordering_deptname>\n" +
            "            <beizhu></beizhu>\n" +
            "            <enter_date_time>2020-12-29 17:00:00</enter_date_time>\n" +
            "        </row>\n" +
            "    </rows>\n" +
            "</request>";
        document.getElementById("input-data").style = "height:700px;";
    });

    function query() {
        if (!$("form").valid()) {
            $.message("还存在必填信息未填写,请检查后提交!");
            return;
        }
        var formData = $("#data-form").getData();
        var inputData = formData.input_data;
        inputData = '<root>' + inputData + '</root>';
        var jsonData = xml2json2(inputData);
        formData.input_data = JSON.stringify(jsonData);
        $.call("hospital_common.xmlcheck.getValidXmlResult", formData, function (rtn) {
            document.getElementById("out-input").innerHTML = "";
            if (rtn.length > 0) {
                var out = document.getElementById("out-input");
                var content = "";
                for (var i = 0; i < rtn.length; i++) {
                    if (1 == rtn[i].tip_level) {
                        content = content + "<span>提示： " + rtn[i].error + "</span>";
                    } else if (2 == rtn[i].tip_level) {
                        content = content + "<span ><em style='color:#de8b35'>警告</em>：" + rtn[i].error + " </span>";
                    } else if (3 == rtn[i].tip_level) {
                        content = content + "<span> <em style='color:#d83535'>严重警告</em>： " + rtn[i].error + " </span>";
                    } else {
                        content = content + "<span> <em style='color:black'>提示<em>： " + rtn[i].error + " </span>";
                    }
                }
                out.innerHTML = out.innerHTML + content;
            }
        });

    }

    //维护XML数据模板
    function maintainXml() {
        var typeId = $("#node-type").val();
        if (!typeId) {
            $.message("请先选择XML模板类型!");
            return;
        }
        $.modal("structure.html", "维护XML数据格式模板", {
            width: "100%",
            height: "100%",
            node_type: typeId,
            callback: function (e) {

            }
        });

    }
</script>
<script>
    function xml2json2(xml, extended) {
        if (!xml) return {}; // quick fail

        //### PARSER LIBRARY
        // Core function
        function parseXML(node, simple) {
            if (!node) return null;
            var txt = '', obj = null, att = null;
            var nt = node.nodeType, nn = jsVar(node.localName || node.nodeName);
            var nv = node.text || node.nodeValue || '';
            /*DBG*/ //if(window.console) console.log(['x2j',nn,nt,nv.length+' bytes']);
            if (node.childNodes) {
                if (node.childNodes.length > 0) {
                    /*DBG*/ //if(window.console) console.log(['x2j',nn,'CHILDREN',node.childNodes]);
                    $.each(node.childNodes, function (n, cn) {
                        var cnt = cn.nodeType, cnn = jsVar(cn.localName || cn.nodeName).toLowerCase();
                        var cnv = cn.text || cn.nodeValue || '';
                        /*DBG*/ //if(window.console) console.log(['x2j',nn,'node>a',cnn,cnt,cnv]);
                        if (cnt == 8) {
                            /*DBG*/ //if(window.console) console.log(['x2j',nn,'node>b',cnn,'COMMENT (ignore)']);
                            return; // ignore comment node
                        } else if (cnt == 3 || cnt == 4 || !cnn) {
                            // ignore white-space in between tags
                            if (cnv.match(/^\s+$/)) {
                                /*DBG*/ //if(window.console) console.log(['x2j',nn,'node>c',cnn,'WHITE-SPACE (ignore)']);
                                return;
                            }
                            ;
                            /*DBG*/ //if(window.console) console.log(['x2j',nn,'node>d',cnn,'TEXT']);
                            txt += cnv.replace(/^\s+/, '').replace(/\s+$/, '');
                            // make sure we ditch trailing spaces from markup
                        } else {
                            /*DBG*/ //if(window.console) console.log(['x2j',nn,'node>e',cnn,'OBJECT']);
                            obj = obj || {};
                            if (obj[cnn]) {
                                /*DBG*/ //if(window.console) console.log(['x2j',nn,'node>f',cnn,'ARRAY']);

                                // http://forum.jquery.com/topic/jquery-jquery-xml2json-problems-when-siblings-of-the-same-tagname-only-have-a-textnode-as-a-child
                                if (!obj[cnn].length) obj[cnn] = myArr(obj[cnn]);
                                obj[cnn] = myArr(obj[cnn]);

                                obj[cnn][obj[cnn].length] = parseXML(cn, true/* simple */);
                                obj[cnn].length = obj[cnn].length;
                            } else {
                                /*DBG*/ //if(window.console) console.log(['x2j',nn,'node>g',cnn,'dig deeper...']);
                                obj[cnn] = parseXML(cn);
                            }
                            ;
                        }
                        ;
                    });
                }
                ; //node.childNodes.length>0
            }
            ; //node.childNodes
            if (node.attributes) {
                if (node.attributes.length > 0) {
                    /*DBG*/ //if(window.console) console.log(['x2j',nn,'ATTRIBUTES',node.attributes])
                    att = {};
                    obj = obj || {};
                    $.each(node.attributes, function (a, at) {
                        var atn = jsVar('@' + at.name).toLowerCase(), atv = at.value;
                        att[atn] = atv;
                        if (obj[atn]) {
                            /*DBG*/ //if(window.console) console.log(['x2j',nn,'attr>',atn,'ARRAY']);

                            // http://forum.jquery.com/topic/jquery-jquery-xml2json-problems-when-siblings-of-the-same-tagname-only-have-a-textnode-as-a-child
                            //if(!obj[atn].length) obj[atn] = myArr(obj[atn]);//[ obj[ atn ] ];
                            obj[cnn] = myArr(obj[cnn]);

                            obj[atn][obj[atn].length] = atv;
                            obj[atn].length = obj[atn].length;
                        } else {
                            /*DBG*/ //if(window.console) console.log(['x2j',nn,'attr>',atn,'TEXT']);
                            obj[atn] = atv;
                        }
                        ;
                    });
                    //obj['attributes'] = att;
                }
                ; //node.attributes.length>0
            }
            ; //node.attributes
            if (obj) {
                obj = $.extend((txt != '' ? new String(txt) : {}), /* {text:txt},*/obj || {}/*, att || {}*/);
                //txt = (obj.text) ? (typeof(obj.text)=='object' ? obj.text : [obj.text || '']).concat([txt]) : txt;
                txt = (obj.text) ? ([obj.text || '']).concat([txt]) : txt;
                if (txt) obj.text = txt;
                txt = '';
            }
            ;
            var out = obj || txt;
            //console.log([extended, simple, out]);
            if (extended) {
                if (txt) out = {}; //new String(out);
                txt = out.text || txt || '';
                if (txt) out.text = txt;
                if (!simple) out = myArr(out);
            }
            ;
            return out;
        }; // parseXML
        // Core Function End
        // Utility functions
        var jsVar = function (s) {
            return String(s || '').replace(/-/g, "_");
        };

        // NEW isNum function: 01/09/2010
        // Thanks to Emile Grau, GigaTecnologies S.L., www.gigatransfer.com, www.mygigamail.com
        function isNum(s) {
            // based on utility function isNum from xml2json plugin (http://www.fyneworks.com/ - diego@fyneworks.com)
            // few bugs corrected from original function :
            // - syntax error : regexp.test(string) instead of string.test(reg)
            // - regexp modified to accept  comma as decimal mark (latin syntax : 25,24 )
            // - regexp modified to reject if no number before decimal mark  : ".7" is not accepted
            // - string is "trimmed", allowing to accept space at the beginning and end of string
            var regexp = /^((-)?([0-9]+)(([\.\,]{0,1})([0-9]+))?$)/
            return (typeof s == "number") || regexp.test(String((s && typeof s == "string") ? jQuery.trim(s) : ''));
        };
        // OLD isNum function: (for reference only)
        //var isNum = function(s){ return (typeof s == "number") || String((s && typeof s == "string") ? s : '').test(/^((-)?([0-9]*)((\.{0,1})([0-9]+))?$)/); };

        var myArr = function (o) {

            // http://forum.jquery.com/topic/jquery-jquery-xml2json-problems-when-siblings-of-the-same-tagname-only-have-a-textnode-as-a-child
            //if(!o.length) o = [ o ]; o.length=o.length;
            if (!$.isArray(o)) o = [o];
            o.length = o.length;

            // here is where you can attach additional functionality, such as searching and sorting...
            return o;
        };
        // Utility functions End
        //### PARSER LIBRARY END

        // Convert plain text to xml
        if (typeof xml == 'string') xml = $.parseXML(xml);

        // Quick fail if not xml (or if this is a node)
        if (!xml.nodeType) return;
        if (xml.nodeType == 3 || xml.nodeType == 4) return xml.nodeValue;

        // Find xml root node
        var root = (xml.nodeType == 9) ? xml.documentElement : xml;

        // Convert xml to json
        var out = parseXML(root, true /* simple */);

        // Clean-up memory
        xml = null;
        root = null;

        // Send output
        return out;
    }
</script>