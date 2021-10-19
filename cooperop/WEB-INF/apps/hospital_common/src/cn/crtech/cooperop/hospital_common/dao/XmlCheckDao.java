package cn.crtech.cooperop.hospital_common.dao;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

import java.util.Map;

public class XmlCheckDao extends BaseDao {
    public Result query(Record record) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT                                                                        ");
        sql.append(" d.node_name parent_name ,f.*, c.check_name as node_check_method,(             ");
        sql.append(" 	SELECT                                                                     ");
        sql.append(" 		COUNT (1)                                                              ");
        sql.append(" 	FROM                                                                       ");
        sql.append(" 		xml_data_check                                                    ");
        sql.append(" 	WHERE                                                                      ");
        sql.append(" 		parent_node = f.p_key                                                  ");
        sql.append(" ) AS childnums                                                                ");
        sql.append(" from xml_data_check (nolock) f                                           ");
        sql.append(" LEFT JOIN xml_data_check d ON f.parent_node=d.p_key                      ");
        sql.append(" LEFT JOIN check_method  c ON f.check_method=c.check_id                  ");
        sql.append(" where 1 = 1                                                                   ");
        //节点类型
        if (!CommonFun.isNe(record.get("node_type"))) {
            sql.append(" and  f.node_type = :node_type");
        }
        return executeQuery(sql.toString(), record);
    }

    //根据sql和参数得到查询结果
    public Result queryValue(String sql, Record record) throws Exception {
        return executeQuery(sql, record);
    }

    //查询xml节点类型
    public Result queryXmlType(Map<String, Object> req) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" select type_id,type_name,type_descrption from xml_type  where 1 = 1 ");
        if (!CommonFun.isNe(req.get("filter"))) {
            req.put("key", "%" + req.get("filter") + "%");
            sql.append(" and type_name like :key");
        }
        if (!CommonFun.isNe(req.get("node_type"))) {
            sql.append(" and type_id = :node_type");
        }
        if (!CommonFun.isNe(req.get("type_name"))) {
            sql.append(" and type_name = :type_name");
        }
        return executeQuery(sql.toString(), req);
    }

    //删除节点
    public int deleteNode(Map<String, Object> params) throws Exception {
        return executeDelete("xml_data_check", params);
    }

    //查询节点
    public Record get(Map<String, Object> params) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT                                                                        ");
        sql.append(" d.node_name parent_name ,f.*, c.check_name,(                                  ");
        sql.append(" 	SELECT                                                                     ");
        sql.append(" 		COUNT (1)                                                              ");
        sql.append(" 	FROM                                                                       ");
        sql.append(" 		xml_data_check                                                          ");
        sql.append(" 	WHERE                                                                      ");
        sql.append(" 		parent_node = f.p_key                                                  ");
        sql.append(" ) AS childnums                                                                ");
        sql.append(" from xml_data_check (nolock) f                                                 ");
        sql.append(" LEFT JOIN xml_data_check d ON f.parent_node=d.p_key                            ");
        sql.append(" LEFT JOIN check_method  c ON f.check_method=c.check_id                        ");
        sql.append(" where 1 = 1                                                                   ");
        if (!CommonFun.isNe(params.get("p_key"))) {
            sql.append(" and f.p_key = :p_key ");
        }
        if (!CommonFun.isNe(params.get("parent_node"))) {
            sql.append(" and f.parent_node = :parent_node ");
        }
        return executeQuerySingleRecord(sql.toString(), params);
    }

    //修改节点
    public int update(Map<String, Object> params) throws Exception {
        Record r = new Record();
        r.put("p_key", params.remove("p_key"));
        return executeUpdate("xml_data_check", params, r);
    }

    public int updateXMLType(Map<String, Object> params) throws Exception {
        Record r = new Record();
        r.put("type_id", params.remove("type_id"));
        return executeUpdate("xml_type", params, r);

    }

    //保存新节点
    public int save(Map<String, Object> params) throws Exception {
        return executeInsert("xml_data_check", params);
    }

    //保存新模板
    public int saveXMLType(Map<String, Object> params) throws Exception {
        return executeInsert("xml_type", params);
    }


    //查询校验方法
    public Result queryCheckMethod(Map<String, Object> params) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" select check_id, check_name from check_method");
        return executeQuery(sql.toString(), params);
    }

    //查询提示等级
    public Result queryResultLevel(Map<String, Object> params) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" select p_key,levelName from result_level");
        return executeQuery(sql.toString(), params);
    }
}
