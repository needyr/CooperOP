package cn.crtech.cooperop.hospital_common.action;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.XmlCheckService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DisLoggedIn
public class XmlcheckAction extends BaseAction {
    //Xml校验
    public List<Record> getValidXmlResult(Map<String, Object> req) throws Exception {
        List<Record> query = new XmlCheckService().checkXML(req);
        if (CommonFun.isNe(query) || query.size() == 0) {
            query = new ArrayList<>();
            Record record = new Record();
            record.put("error", "校验通过");
            record.put("tip_level", "1");
            query.add(record);
        }
        return query;
    }

    //查询模板类型
    public Result queryXmlType(Map<String, Object> req) throws Exception {
        Result result = new XmlCheckService().queryXmlType(req);
        return result;
    }

    //根据xml模板类型查询所有节点信息
    public List<Record> queryXmlByNodeType(Map<String, Object> req) throws Exception {
        Result result = new XmlCheckService().queryXmlByNodeType(req);
        return result.getResultset();
    }

    //删除节点
    public Map<String, Object> deleteNode(Map<String, Object> req) throws Exception {
        int i = new XmlCheckService().deleteNode(req);
        Map<String, Object> eq = new HashMap<>();
        if (i == -1) {
            eq.put("result", "fail");
        } else {
            eq.put("result", "success");
        }
        return eq;
    }

    //添加节点信息
    public Map<String, Object> saveNode(Map<String, Object> req) throws Exception {
        XmlCheckService xmlCheckService = new XmlCheckService();
        int save = xmlCheckService.save(req);
        req.clear();
        if (save > 0) {
            req.put("result", "success");
        } else {
            req.put("result", "fail");
        }
        return req;
    }

    //查询校验方法
    public Result queryCheckMethod(Map<String, Object> params) throws Exception {
        Result result = new XmlCheckService().queryCheckMethod(params);
        return result;
    }

    //查询结果提示等级
    public Result queryResultLevel(Map<String, Object> params) throws Exception {
        Result result = new XmlCheckService().queryResultLevel(params);
        return result;
    }

    //修改信息 数据回显
    public Record structure_edit(Map<String, Object> params) throws Exception {
        return new XmlCheckService().get(params);
    }

    public Map<String, Object> updateNode(Map<String, Object> req) throws Exception {
        int update = new XmlCheckService().update(req);
        req.clear();
        if (update > 0) {
            req.put("result", "success");
        } else {
            req.put("result", "fail");
        }
        return req;
    }

    public Map<String, Object> saveXMLType(Map<String, Object> req) throws Exception {
        XmlCheckService xmlCheckService = new XmlCheckService();
        int save = xmlCheckService.saveXMLType(req);
        req.clear();
        if (save > 0) {
            req.put("result", "success");
        } else {
            req.put("result", "fail");
        }
        return req;
    }

    public Map<String, Object> updateXMLType(Map<String, Object> req) throws Exception {
        XmlCheckService xmlCheckService = new XmlCheckService();
        int save = xmlCheckService.updateXMLType(req);
        req.clear();
        if (save > 0) {
            req.put("result", "success");
        } else {
            req.put("result", "fail");
        }
        return req;
    }

}
