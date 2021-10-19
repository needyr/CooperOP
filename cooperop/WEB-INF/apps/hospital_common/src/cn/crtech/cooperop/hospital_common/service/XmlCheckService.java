package cn.crtech.cooperop.hospital_common.service;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.XmlCheckDao;

import java.util.*;
import java.util.regex.Pattern;

public class XmlCheckService extends BaseService {
    /**
     * @param params 传入的XML数据
     * @return 校验结果问题集
     * @title 校验XML数据结构
     * @description 用于校验XML结构匹配，内容匹配问题
     * @author lichuanqian
     * @date 2021-05-18 16:49
     */
    public List<Record> checkXML(Map<String, Object> params) throws Exception {
        //获取xml数据json串
        Object json = params.get("input_data");
        if (CommonFun.isNe(json)) {
            List<Record> list = new ArrayList();
            Record record = new Record();
            record.put("error", "XML数据为空，请重新输入后再提交");
            record.put("tip_level", "3");
            list.add(record);
            return list;
        }
        String jsonString = json.toString();
        // 去除xml串特殊字符，防止类型转换失败
        jsonString = jsonString.replaceAll("[\\t\\n\\r]", "");
        jsonString = jsonString.replaceAll("\\\\", "/");
        //将json串转换为Map
        Map map = CommonFun.json2Object(jsonString, Map.class);
        //json转换为map且不为空
        if (CommonFun.isNe(map)) {
            List<Record> list = new ArrayList();
            Record record = new Record();
            record.put("error", "xml数据格式不规范，请重新检查后提交");
            record.put("tip_level", "3");
            list.add(record);
            return list;
        }
        //获取模板类型
        String nodeType = params.get("node_type").toString();
        //校验
        List<Record> errorList = verifyXML(map, nodeType);
        if (CommonFun.isNe(errorList) && errorList.size() == 0){
            return null;
        }
        //把校验结果根据提示程度排序
        return orderByOneAttribute(errorList, "tip_level");
    }
    
    /**
     * 查询XMl模板类型
     */
    public Result queryXmlType(Map<String, Object> req) throws Exception {
        try {
            connect("guide");
            return new XmlCheckDao().queryXmlType(req);
        } catch (Exception e) {
            log.error("查询模板类型失败", e);
            throw e;
        } finally {
            disconnect();
        }
    }

    public Result queryCheckMethod(Map<String, Object> params) throws Exception {
        try {
            connect("guide");
            return new XmlCheckDao().queryCheckMethod(params);
        } catch (Exception e) {
            log.error("查询节点校验方式失败", e);
            throw e;
        } finally {
            disconnect();
        }
    }

    public Result queryResultLevel(Map<String, Object> params) throws Exception {
        try {
            connect("guide");
            return new XmlCheckDao().queryResultLevel(params);
        } catch (Exception e) {
            log.error("查询节点提示级别失败", e);
            throw e;
        } finally {
            disconnect();
        }
    }

    public Result queryXmlByNodeType(Map<String, Object> req) throws Exception {
        try {
            connect("guide");
            Record record = new Record();
            if (!CommonFun.isNe(req.get("node_type"))) {
                record.put("node_type", req.get("node_type"));
            }
            return new XmlCheckDao().query(record);
        } catch (Exception e) {
            log.error("根据XML模板类型查询节点失败", e);
            throw e;
        } finally {
            disconnect();
        }
    }

    public int deleteNode(Map<String, Object> params) throws Exception {
        try {
            connect("guide");
            //开启事务
            start();
            Map<String, Object> record = new HashMap<>();
            XmlCheckDao xmlCheckDao = new XmlCheckDao();
            if (CommonFun.isNe(params.get("p_key"))) {
                return -1;
            } else {
                record.put("p_key", params.get("p_key"));
                //查询当前节点是否存在子节点
                Record result = get(params);

                if (Integer.parseInt(result.get("childnums").toString()) > 0) {
                    //存在子节点先删除所有子节点
                    Map<String, Object> condition = new HashMap<>();

                    condition.put("parent_node", params.get("p_key"));

                    xmlCheckDao.deleteNode(condition);
                }
                //删除当前节点
                return xmlCheckDao.deleteNode(record);
            }
        } catch (Exception ex) {
            //回滚
            rollback();
            log.error("删除XML节点失败", ex);
            throw ex;
        } finally {
            disconnect();
        }
    }

    public Record get(Map<String, Object> params) throws Exception {
        try {
            connect("guide");
            XmlCheckDao xcd = new XmlCheckDao();
            return xcd.get(params);
        } catch (Exception ex) {
            log.error("获取XML节点失败", ex);
            throw ex;
        } finally {
            disconnect();
        }
    }

    public int update(Map<String, Object> params) throws Exception {
        try {
            connect("guide");
            XmlCheckDao xcd = new XmlCheckDao();
            if (CommonFun.isNe(params.get("p_key"))) {
                return -1;
            }
            return xcd.update(params);
        } catch (Exception ex) {
            log.error("更新XML节点失败", ex);
            throw ex;
        } finally {
            disconnect();
        }
    }

    public int save(Map<String, Object> params) throws Exception {
        try {
            connect("guide");
            XmlCheckDao xcd = new XmlCheckDao();
            return xcd.save(params);
        } catch (Exception ex) {
            log.error("保存XML节点失败", ex);
            throw ex;
        } finally {
            disconnect();
        }
    }

    public int saveXMLType(Map<String, Object> params) throws Exception {
        try {
            connect("guide");
            XmlCheckDao xcd = new XmlCheckDao();
            return xcd.saveXMLType(params);
        } catch (Exception ex) {
            log.error("保存XML模板类型失败", ex);
            throw ex;
        } finally {
            disconnect();
        }
    }

    public int updateXMLType(Map<String, Object> params) throws Exception {
        try {
            connect("guide");
            XmlCheckDao xcd = new XmlCheckDao();
            if (CommonFun.isNe(params.get("type_id"))){
                return -1;
            }
            return xcd.updateXMLType(params);
        } catch (Exception ex) {
            log.error("更新XML模板类型失败", ex);
            throw ex;
        } finally {
            disconnect();
        }
    }


    /**
     * @param xml     xml经过json转换为map的值
     * @param xmlType xml模板类型
     * @return 解析完成后返回的结果集
     * @description 用于解析xml的数据map到指定格式
     * @author lichuanqian
     * @date 2021-05-10 13:45
     */
    public List<Record> verifyXML(Map<String, Object> xml, String xmlType) {
        //返回的问题集合
        List<Record> error = null;
        try {
            //格式转换后的xml数据集合
            Map<String, Object> parseMap = new HashMap<>();
            Iterator iterator = xml.keySet().iterator();
            //解析请求Xml节点名称为指定格式  request.patient.id
            while (iterator.hasNext()) {
                //当前节点名称
                String key = iterator.next().toString();
                //解析后的节点名称
                parseMap.putAll(parseXmlStructure(null, key, xml, parseMap));
            }
            Record record = new Record();
            record.put("node_type", xmlType);
            connect("guide");
            //根据模板类型得到XML属性集合
            Result query = new XmlCheckDao().query(record);
            List<Record> list = query.getResultset();
            //将父节点属性封装进去  为了方便后面查询父节点属性使用
            for (Record r : list) {
                if (CommonFun.isNe(r.get("parent_name"))) {
                    continue;
                }
                for (Record rs : list) {
                    if (!CommonFun.isNe(r.get("parent_name"))
                            && r.get("parent_name").equals(rs.get("node_name").toString())) {
                        r.put("parentNode", rs);
                    }
                }
            }
            //将查询出来的xml节点名称也解析为指定格式  request.patient.id
            list = getQueryXmlParseResult(list);
            //得到传入XML结构问题集合
            error = getXMlQuestion(parseMap, list);
            //如果存在结构问题直接返回结构问题，反之返回内容问题
            if (!CommonFun.isNe(error) && error.size() > 0) {
                return error;
            }
            if (error.size() == 0) {
                //得到内容问题
                List<Record> xmlContentError = getXmlContentError(parseMap, list);
                error.addAll(xmlContentError);
            }
            return error;
        } catch (Exception e) {
            log.error("校验功能失败", e);
        } finally {
            disconnect();
            return error;
        }
    }


    /**
     * @param parentNodeName 父节点名称
     * @param nodeName       当前节点名称
     * @param node           节点集
     * @param parseMap       解析结果集
     * @return parseMap 解析完成后返回的结果集
     * @description 用于解析xml的数据map到指定格式
     * @author lichuanqian
     * @date 2021-05-10 13:45
     */
    public Map<String, Object> parseXmlStructure(String parentNodeName, String nodeName, Map<String, Object> node, Map<String, Object> parseMap) {
        //如果当前节点为根节点
        if (CommonFun.isNe(parentNodeName)) {
            //获取该节点下的所有子节点
            Object obj = node.get(nodeName);
            //如果该节点不是叶子节点，并且该节点各不相同
            if (obj instanceof LinkedHashMap) {
                //非叶子节点的值为 #
                parseMap.put(nodeName, "#");
                Map<String, Object> valueMap = (Map<String, Object>) obj;
                //得到该节点的子节点key值集
                Iterator<String> iterator = valueMap.keySet().iterator();
                while (iterator.hasNext()) {
                    //将该节点下的所有子节点进行同等检测
                    String next = iterator.next();
                    //递归调用本方法，并将返回值存入集合
                    parseMap.putAll(parseXmlStructure(nodeName, next, valueMap, parseMap));
                }
            }
            //如果该节点存在多个相同子节点
            else if (obj instanceof ArrayList) {
                ArrayList<String> valueList = new ArrayList<>();
                ArrayList<Map<String, Object>> valueMap = (ArrayList<Map<String, Object>>) obj;
                for (int i = 0; i < valueMap.size(); i++) {
                    //非叶子节点的值为 #
                    valueList.add("#");
                }
                parseMap.put(nodeName, valueList);
                //分别处理每个节点
                for (Map<String, Object> vobj : valueMap) {
                    Iterator<String> iterator = vobj.keySet().iterator();
                    while (iterator.hasNext()) {
                        String next = iterator.next();
                        parseMap.putAll(parseXmlStructure(nodeName, next, vobj, parseMap));
                    }
                }
            }
            //叶子节点
            else {
                //直接插入解析结果
                if (CommonFun.isNe(obj)) {
                    //给空值节点赋一个默认值
                    obj = "value_null";
                }
                putValue(parseMap, nodeName, obj);
                //返回
                return parseMap;
            }
        } else { //不是根节点
            //获取该节点下的所有子节点
            Object obj = node.get(nodeName);
            //如果该节点不是叶子节点
            if (obj instanceof LinkedHashMap) {
                //如果该节点名已经解析过了
                parseMap.put(parentNodeName + "." + nodeName, "#");
                Map<String, Object> valueMap = (Map<String, Object>) obj;
                //得到该节点的子节点key值集
                Iterator<String> iterator = valueMap.keySet().iterator();
                while (iterator.hasNext()) {
                    String next = iterator.next();
                    parseMap.putAll(parseXmlStructure(parentNodeName + "." + nodeName, next, valueMap, parseMap));
                }
            }
            //如果该节点存在多个相同子节点
            else if (obj instanceof ArrayList) {
                ArrayList<String> valueList = new ArrayList<>();
                ArrayList<?> values = (ArrayList<?>) obj;
                //如果该节点集为值而不是子节点
                if (values.get(0) instanceof String) {
                    ArrayList<String> valueMap = (ArrayList<String>) obj;
                    for (String vobj : valueMap) {
                        putValue(parseMap, parentNodeName + "." + nodeName, vobj);
                    }
                    return parseMap;
                } else {
                    ArrayList<Map<String, Object>> valueMap = (ArrayList<Map<String, Object>>) obj;
                    for (int i = 0; i < valueMap.size(); i++) {
                        //非叶子节点的值为 #
                        valueList.add("#");
                    }
                    parseMap.put(parentNodeName + "." + nodeName, valueList);
                    for (Map<String, Object> vobj : valueMap) {
                        Iterator<String> iterator = vobj.keySet().iterator();
                        while (iterator.hasNext()) {
                            String next = iterator.next();
                            parseMap.putAll(parseXmlStructure(parentNodeName + "." + nodeName, next, vobj, parseMap));
                        }
                    }
                }

            }
            //叶子节点
            else {
                //防止内容为空时，获取内容为null
                if (CommonFun.isNe(obj)) {
                    obj = "value_null";
                }
                putValue(parseMap, parentNodeName + "." + nodeName, obj);
                return parseMap;
            }
        }
        return parseMap;
    }

    //添加值  如果存在多个相同key的值，则用集合装载
    public void putValue(Map<String, Object> record, String key, Object value) {
        //如果该值已经存在
        if (CommonFun.isNe(record.get(key))) {
            record.put(key, value);
            return;
        }
        Object obj = record.get(key);
        //如果已存在多个值
        if (obj instanceof ArrayList) {
            ArrayList<Object> valueList = (ArrayList<Object>) obj;
            valueList.add(value);
            record.put(key, valueList);
            //如果只存在一个值
        } else if (obj instanceof String) {
            ArrayList<Object> valueList = new ArrayList<>();
            valueList.add(record.get(key).toString());
            valueList.add(value);
            record.put(key, valueList);
        }
    }


    /**
     * @param recordList 查询出来的节点集
     * @param nodeName   节点名称
     * @param parent     父节点名称
     * @return Record    该节点名称解析结果
     * @title 将数据库中的xml节点转换为指定格式
     * @description
     * @author lichuanqian
     * @date 2021-05-11 16:31
     */
    public Record getNodeName(List<Record> recordList, String nodeName, String parent, Record record) {

        for (Record r : recordList) {
            //找到父节点
            if (!parent.equals(r.get("node_name"))) {
                continue;
            }
            //获取父节点的节点名
            String node_name = r.get("node_name").toString();
            //得到传入节点的父节点的父节点
            Object obj = r.get("parentNode");
            //如果父节点的父节点不为空
            if (!CommonFun.isNe(obj) && obj instanceof Record) {
                Record parentNode = (Record) obj;
                //父节点仍然存在父节点，则再次回调自身方法
                if (!CommonFun.isNe(record.get("node_parse"))) {
                    String node_parse = record.get("node_parse").toString();
                    record.put("node_parse", parent + "." + node_parse);
                } else {
                    record.put("node_parse", parent + "." + nodeName);
                }
                record = getNodeName(recordList, node_name, parentNode.get("node_name").toString(), record);
            } else {
                //父节点的父节点为空，则直接将父节点名称拼接该节点名称，然后返回
                if (!CommonFun.isNe(record.get("node_parse"))) {
                    String node_parse = record.get("node_parse").toString();
                    record.put("node_parse", parent + "." + node_parse);
                } else {
                    record.put("node_parse", parent + "." + nodeName);
                }
                return record;
            }
            break;
        }
        return record;
    }

    /**
     * @param data     传入的xml数据集
     * @param queryXml 查询出来的xml属性集
     * @return record 问题结果集
     * @title 获取校验问题结果
     * @description
     * @author lichuanqian
     * @date 2021-05-11 17:55
     */
    public List<Record> getXMlQuestion(Map<String, Object> data, List<Record> queryXml) {
        List<Record> errorRecord = new ArrayList<>();
        for (Record r : queryXml) {
            //解析后的节点名称
            String node_name = r.get("parse_node_name").toString();
            //节点内容是否可以为空
            String node_null = r.get("node_null").toString();
            //节点是否为叶节点
            String node_leaf = r.get("node_leaf").toString();
            //节点是否可以缺少
            String node_not = r.get("node_not").toString();
            //解析后的父节点名称
            Object parentName = r.get("parse_parent_name");
            String parent_name = null;
            if (!CommonFun.isNe(parentName)) {
                parent_name = parentName.toString();
            }
            //校验传入数据的xml结构  该节点内容为空
            if (CommonFun.isNe(data.get(node_name))) {
                //该节点不允许允许缺少
                if (!"1".equals(node_not)) {
                    Record record = new Record();
                    record.put("error", node_name + "不能缺少,请检查当前该节点是否存在");
                    record.put("tip_level", r.get("level") == null ?'1':r.get("level"));
                    errorRecord.add(record);
                    continue;
                }
                Object parentNode = r.get("parentNode");
                //该节点父节点存在
                if (!CommonFun.isNe(parentNode) && parentNode instanceof Record) {
                    Record pNode = (Record) parentNode;
                    //当其父节点是可以缺少，当前节点内容不可以为空，但传入数据中父节点不为空时，抛出问题
                    if ("1".equals(pNode.get("node_not").toString())
                            && !CommonFun.isNe(data.get(parent_name))
                            && "0".equals(r.get("node_null"))) {
                        Record record = new Record();
                        record.put("error", node_name + "的父节点存在，" + node_name + "不能缺少[内容不能为空]");
                        record.put("tip_level", r.get("level") == null ?'1':r.get("level"));
                        errorRecord.add(record);
                    }
                }
            } else {
                //获取父节点的个数
                //父节点存在
                if (!CommonFun.isNe(parentName)) {
                    //父节点数据不为空，说明父节点存在
                    if (!CommonFun.isNe(data.get(parentName))) {
                        Object object = data.get(parentName);
                        //如果父节点存在多个
                        if (object instanceof ArrayList) {
                            ArrayList<String> parentList = (ArrayList<String>) object;
                            Object childNode = data.get(node_name);
                            //该子节点只存在一个
                            if (childNode instanceof String) {
                                //该节点内容不能为空
                                if ("0".equals(node_null)) {
                                    Record record = new Record();
                                    record.put("error", node_name + "节点缺少,请检查当前该节点与父节点个数是否匹配");
                                    record.put("tip_level", r.get("level") == null ?'1':r.get("level"));
                                    errorRecord.add(record);
                                }
                            } else if (childNode instanceof ArrayList) {
                                //该节点内容不能为空
                                if ("0".equals(node_null)) {
                                    ArrayList<String> childList = (ArrayList) childNode;
                                    if (childList.size() != parentList.size()) {
                                        Record record = new Record();
                                        record.put("error", node_name + "节点个数与父节点个数不匹配,请检查当前该节点与父节点个数是否匹配");
                                        record.put("tip_level", r.get("level") == null ?'1':r.get("level"));
                                        errorRecord.add(record);
                                    }
                                }
                            }
                        }
                    }
                }
                Object objectValue = data.get(node_name);
                //判断该节点是否为叶节点
                if (objectValue instanceof String) {
                    String value = objectValue.toString();
                    //非叶节点
                    if ("#".equals(value)) {
                        //模板中该节点为叶节点
                        if ("1".equals(node_leaf)) {
                            Record record = new Record();
                            record.put("error", node_name + "节点为内容节点，不允许存在其他子节点，请检查后更正");
                            record.put("tip_level", r.get("level") == null ?'1':r.get("level"));
                            errorRecord.add(record);
                        }
                    } else {  //叶节点
                        //节点内容为空
                        if ("value_null".equals(data.get(node_name).toString().trim())) {
                            if ("0".equals(node_null)) {
                                Record record = new Record();
                                record.put("error", node_name + "节点内容不能为空，请重新检查！！");
                                record.put("tip_level", r.get("level") == null ?'1':r.get("level"));
                                errorRecord.add(record);
                            }
                        }
                    }
                } else if (objectValue instanceof ArrayList) {
                    //如果该节点不允许存在多个节点
                    if ("0".equals(r.get("node_muti").toString())) {
                        Record record = new Record();
                        record.put("error", node_name + "节点不允许存在多个相同节点，请检查后更正");
                        record.put("tip_level", r.get("level") == null ?'1':r.get("level"));
                        errorRecord.add(record);
                    }
                    ArrayList<String> valueList = (ArrayList<String>) objectValue;
                    for (int i = 0; i < valueList.size(); i++) {
                        if ("#".equals(valueList.get(i))) {
                            if ("1".equals(node_leaf)) {
                                Record record = new Record();
                                record.put("error", "第" + (i + 1) + "个" + node_name + "节点为值节点，不允许存在其他节点，请检查后更正");
                                record.put("tip_level", r.get("level") == null ?'1':r.get("level"));
                                errorRecord.add(record);
                            }
                        } else if ("value_null".equals(valueList.get(i))) {
                            if ("0".equals(node_null)) {
                                Record record = new Record();
                                record.put("error", "第" + (i + 1) + "个" + node_name + "节点内容不能为空，请重新检查！！");
                                record.put("tip_level",r.get("level") == null ?'1':r.get("level"));
                                errorRecord.add(record);
                            }
                        }
                    }
                }
            }
        }
        return errorRecord;
    }

    /**
     * @param list 待转的xml集合
     * @return 解析完成的xml
     * @title 将查询出来的xml结构转换为 类型 xxx.父节点名.当前节点名格式
     * @author lichuanqian
     * @date 2021-05-12 16:38
     */
    public List<Record> getQueryXmlParseResult(List<Record> list) {
        //将该节点名一统一为前面解析的格式（例如 request.patient.xx ）
        for (Record r : list) {
            //父节点为空
            if (CommonFun.isNe(r.get("parent_name"))) {
                //根节点
                r.put("parse_node_name", r.get("node_name"));
                r.put("parse_parent_name", null);
            } else {
                Record decor = new Record();
                String nodeName = r.get("node_name").toString();
                String parentName = r.get("parent_name").toString();
                //获取解析后的节点record
                Record nameRecord = getNodeName(list, nodeName, parentName, decor);
                //解析后的节点名不为空
                if (!CommonFun.isNe(nameRecord.get("node_parse")) && nameRecord.get("node_parse").toString().lastIndexOf(".") > -1) {
                    String nodeParse = nameRecord.get("node_parse").toString();
                    //保存解析后的节点名
                    r.put("parse_node_name", nodeParse);
                    //保存解析后的父节点名称
                    r.put("parse_parent_name", nodeParse.substring(0, nodeParse.lastIndexOf(".")));
                }
            }
        }
        return list;
    }

    /**
     * @param data         输入的xml数据
     * @param xmlStructure 存储的xml结构信息
     * @return Record 解析结果
     * @title 校验节点内容
     * @author lichuanqian
     * @date 2021-05-13 9:23
     */
    public List<Record> getXmlContentError(Map<String, Object> data, List<Record> xmlStructure) {
        //内容错误集合
        List<Record> contentError = new ArrayList<>();
        for (Record r : xmlStructure) {
            //该节点是否为非叶节点
            boolean not_leaf = false;
            if (data.get(r.get("parse_node_name")) instanceof ArrayList) {
                ArrayList<String> value_list = (ArrayList) data.get(r.get("parse_node_name"));
                if ("#".equals(value_list.get(0))) {
                    not_leaf = true;
                }
            }
            //检验每一个叶节点  如果该节点是叶节点且节点值不为空
            if (!"#".equals(data.get(r.get("parse_node_name")))
                    && !"value_null".equals(data.get(r.get("parse_node_name")))
                    && !not_leaf) {
                //节点校验方法
                Object check_obj = r.get("node_check_method");
                //节点查询方法
                Object method_obj = r.get("node_method");
                //节点查询参数
                Object params_obj = r.get("params");
                //节点校验方法不为空
                if (!CommonFun.isNe(check_obj)) {
                    String node_check_method = check_obj.toString();
                    //如果是数据库内容校验
                    if ("sql".equals(node_check_method)) {
                        Record param_record = new Record();
                        String node_method = method_obj.toString();
                        String params = params_obj.toString();
                        //将参数分割出来
                        String[] params_split = params.split("\\,");
                        String node_name = r.get("parse_node_name").toString();
                        //如果该节点值存在多个
                        if (data.get(node_name) instanceof ArrayList) {
                            //将节点值通过集合装载
                            ArrayList<String> value_List = (ArrayList<String>) data.get(r.get("parse_node_name"));
                            //循环校验每一个节点值
                            for (int j = 0; j < value_List.size(); j++) {
                                //如果该值不为空
                                if (!"value_null".equals(value_List.get(j))) {
                                    //得到参数map
                                    for (int i = 0; i < params_split.length; i++) {
                                        //获取每一个SQL参数的值
                                        Object valueList = data.get(params_split[i]);
                                        //如果该参数值存在多个
                                        if (valueList instanceof ArrayList) {
                                            ArrayList<String> object = (ArrayList<String>) valueList;
                                            String object_params = object.get(j);
                                            param_record.put(params_split[i].substring(params_split[i].lastIndexOf(".") + 1), object_params);
                                        } else if (valueList instanceof String) {
                                            param_record.put(params_split[i].substring(params_split[i].lastIndexOf(".") + 1), valueList.toString());
                                        }
                                    }
                                    try {
                                        connect();
                                        Result query = new XmlCheckDao().queryValue(node_method, param_record);
                                        if (CommonFun.isNe(query)) {//查询结果为空也抛出问题
                                            Record record = new Record();
                                            record.put("error", "第" + (j + 1) + "个" + r.get("parse_node_name") + "节点错误，" + r.get("description"));
                                            record.put("tip_level", r.get("level") == null ?'1':r.get("level"));
                                            contentError.add(record);
                                        } else {//如果查询结果个数与预期个数不匹配，则返回错误
                                            if (query.getResultset().size() != Integer.parseInt(r.get("correct_num").toString())) {
                                                Record record = new Record();
                                                record.put("error", "第" + (j + 1) + "个" + r.get("parse_node_name") + "节点错误，" + r.get("description"));
                                                record.put("tip_level", r.get("level") == null ?'1':r.get("level"));
                                                contentError.add(record);
                                            }
                                        }
                                        param_record.clear();
                                    } catch (Exception ex) {
                                        log.error("XML内容校验功能-sql校验出错", ex);
                                    } finally {
                                        disconnect();
                                    }
                                }
                            }

                        } else if (data.get(node_name) instanceof String) {
                            //得到参数map
                            for (int i = 0; i < params_split.length; i++) {
                                //获取每一个参数的值
                                Object valueList = data.get(params_split[i]);
                                String object_params = valueList.toString();
                                param_record.put(params_split[i].substring(params_split[i].lastIndexOf(".") + 1), object_params);
                            }
                            try {
                                connect();
                                Result query = new XmlCheckDao().queryValue(node_method, param_record);
                                if (CommonFun.isNe(query)) { //查询结果为空也抛出问题
                                    Record record = new Record();
                                    record.put("error", r.get("parse_node_name").toString() + "节点错误，" + r.get("description"));
                                    record.put("tip_level", r.get("level") == null ?'1':r.get("level"));
                                    contentError.add(record);
                                } else { //如果查询结果个数与预期个数不匹配，则返回错误
                                    if (query.getResultset().size() != Integer.parseInt(r.get("correct_num").toString())) {
                                        Record record = new Record();
                                        record.put("error", r.get("parse_node_name").toString() + "节点错误，" + r.get("description"));
                                        record.put("tip_level", r.get("level") == null ?'1':r.get("level"));
                                        contentError.add(record);
                                    }
                                }
                            } catch (Exception ex) {
                                log.error("XML内容校验功能-sql校验出错", ex);
                            } finally {
                                disconnect();
                            }
                        }
                    }
                    //如果是校验日期格式
                    else if ("checkDate".equals(node_check_method)) {
                        String node_name = r.get("parse_node_name").toString();
                        Boolean flag = true;
                        //如果存在多个值
                        if (data.get(node_name) instanceof ArrayList) {
                            ArrayList<String> value_List = (ArrayList<String>) data.get(r.get("parse_node_name"));
                            for (int j = 0; j < value_List.size(); j++) {
                                //如果该值不为空
                                if (!"value_null".equals(value_List.get(j))) {
                                    flag = CheckDate(((ArrayList) data.get(r.get("parse_node_name"))).get(j).toString(), method_obj.toString());
                                    if (!flag) {
                                        Record record = new Record();
                                        record.put("error", "第" + (j + 1) + "个" + r.get("parse_node_name") + "节点错误，" + r.get("description"));
                                        record.put("tip_level",r.get("level") == null ?'1':r.get("level"));
                                        contentError.add(record);
                                    }
                                }
                            }
                        } else if (data.get(node_name) instanceof String) {
                            flag = CheckDate(data.get(r.get("parse_node_name")).toString(), method_obj.toString());
                            if (!flag) {
                                Record record = new Record();
                                record.put("error", r.get("parse_node_name").toString() + "节点错误，" + r.get("description"));
                                record.put("tip_level", r.get("level") == null ?'1':r.get("level"));
                                contentError.add(record);
                            }
                        }
                    }
                }
            }
        }
        return contentError;
    }

    /**
     * @param value 日期值
     * @param rule  日期格式
     * @return 解析结果  true表示符合，false表示不符合
     * @title 校验节点内容
     * @author lichuanqian
     * @date 2021-05-13 10:10
     */
    private Boolean CheckDate(String value, String rule) {
        Boolean flag = true;
        String matchStr = "";
        switch (rule) {
            case "dd/mm/yyyy":
                matchStr = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
                break;
            case "dd-mm-yyyy":
                matchStr = "(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-((19|20)\\d\\d)";
                break;
            case "yyyy-MM-dd":
                matchStr = "(^[0-9]{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";
                break;
            case "yyyy-MM-dd HH:mm:ss":
                matchStr = "(^[0-9]{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])\\s" +
                        "(0?1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])";
                break;
            case "yyyy/MM/dd HH:mm:ss":
                matchStr = "(^[0-9]{4})/(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])\\s" +
                        "(0?1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])";
                break;
            case "yyyy/MM/dd":
                matchStr = "(^[0-9]{4})/(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])";
                break;
            default:
                break;
        }
        //如果还存在其他格式可在此处进行扩展
        Pattern datePattern = Pattern.compile(matchStr);
        if (!datePattern.matcher(value).matches()) {
            flag = false;
        }
        return flag;
    }

    /**
     * 根据某一实体属性对集合进行排序 (该属性值必须为集合数值类型)
     * */
    public List<Record> orderByOneAttribute (List<Record> list,String attr){
        List<Record> sortList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        for (Record record : list){
            set.add(Integer.parseInt(record.get(attr).toString()));
        }
        List<Integer> levelList = new ArrayList<>();
        levelList.addAll(set);
        //升序
        Collections.sort(levelList);
        //降序
        Collections.reverse(levelList);
        for (Integer integer : levelList){
            for (Record record : list){
                if (Integer.parseInt(record.get(attr).toString()) == integer){
                    sortList.add(record);
                }
            }
        }
        return sortList;
    }
}
