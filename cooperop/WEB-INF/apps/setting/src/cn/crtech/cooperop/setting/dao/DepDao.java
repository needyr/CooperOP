package cn.crtech.cooperop.setting.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DepDao extends BaseDao {
    private final static String TABLE_NAME = "dbo.system_department", SYSTEM_ORGANIZATION = "dbo.system_organization";

    public Result query(Map<String, Object> params) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select f.*,ISNULL(sd.name,'') AS parent_name from dbo.system_department(nolock) f ");
        sql.append(" LEFT JOIN dbo.system_department(nolock) sd ON f.parent_id = sd.id where (f.state!=-1 or f.state is null) ");
        if (!CommonFun.isNe(params.get("filter"))) {
            params.put("key", "%" + params.get("filter") + "%");
        }
        params.put("sort", "order_no");
        setParameter(params, "id", " and f.id=:id ", sql);
        setParameter(params, "cid", " and ','+f.ids +',' like  '%,'+:cid+',%'  ", sql);
        setParameter(params, "filter", " and (f.name like :key or f.input_code like :key) ", sql);
        if (!CommonFun.isNe(params.get("depHasSelected"))) {
            setParameter(params, "depSelected", " and f.id in (:depSelected) ", sql);
            setParameter(params, "depHasSelected", " and f.id not in (:depHasSelected) ", sql);
        } else {
            setParameter(params, "depSelected", " and f.id in (:depSelected) ", sql);
        }
        return executeQuery(sql.toString(), params);
    }

    public Result queryByCode(Record params) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select * from dbo.system_department(nolock) f ");
        sql.append(" where 1 = 1 ");
        if (!CommonFun.isNe(params.get("code"))){
            sql.append(" and code = :code");
        }
        if (!CommonFun.isNe(params.get("jigid"))){
            sql.append(" and jigid = :jigid");
        }
        return executeQuery(sql.toString(), params);
    }

    public Result querydep(Map<String, Object> params) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select f.*,ISNULL(sd.name,'') AS parent_name from system_department(nolock) f ");
        sql.append(" LEFT JOIN dbo.system_department(nolock) sd ON f.parent_id = sd.id where (f.state!=-1 or f.state is null) ");
        if (!CommonFun.isNe(params.get("filter"))) {
            params.put("key", "%" + params.get("filter") + "%");
        }
        params.put("sort", "ids");
        setParameter(params, "filter", " and (f.name like :key or f.input_code like :key) ", sql);
        return executeQueryLimit(sql.toString(), params);
    }

    public Result queryByCondition(Map<String, Object> params) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" select * from system_department(nolock) f");
        sql.append(" where 1 = 1");
        if (!CommonFun.isNe(params.get("name"))) {
            sql.append(" and name = :name");
        }if (!CommonFun.isNe(params.get("type"))) {
            sql.append(" and type = :type");
        }
        return executeQueryLimit(sql.toString(),params);
    }


    public Record get(Map<String, Object> params) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select f.* from dbo.system_department(nolock) f ");
        sql.append(" where 1 = 1 ");
        setParameter(params, "id", " and f.id=:id", sql);
        setParameter(params, "no", " and f.no=:no", sql);
        return executeQuerySingleRecord(sql.toString(), params);
    }

    public int insert(Map<String, Object> params) throws Exception {
        params.remove("id");
        if (CommonFun.isNe(params.get("parent_id"))) {
            params.put("parent_id", 0);
        }
        return executeInsert(TABLE_NAME, params);
    }

    public int update(Map<String, Object> params) throws Exception {
        Record r = new Record();
        r.put("id", params.remove("id"));
        return executeUpdate(TABLE_NAME, params, r);
    }

    public int updateDep(Record params) throws Exception {
        Record r = new Record();
        r.put("jigid", params.remove("jigid"));
        return executeUpdate(TABLE_NAME, params, r);
    }

    public int delete(Map<String, Object> params) throws Exception {
        return executeDelete(TABLE_NAME, params);
    }

    public Record getOrganization(Map<String, Object> params) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select f.* from " + SYSTEM_ORGANIZATION + "f where 1 = 1");
        setParameter(params, "jigid", " and f.jigid = :jigid", sql);
        return executeQuerySingleRecord(sql.toString(), params);
    }

    //添加机构
    public int insertOrganization(Map<String, Object> params) throws Exception {
        params.remove("id");
        return executeInsert(SYSTEM_ORGANIZATION, params);
    }

    public int updateOrganization(Record params) throws Exception {
        Record r = new Record();
        r.put("jigid", params.remove("jigid"));
        return executeUpdate(SYSTEM_ORGANIZATION, params, r);
    }

    public int deleteOrganization(Map<String, Object> params) throws Exception {
        return executeDelete(SYSTEM_ORGANIZATION, params);
    }
}
