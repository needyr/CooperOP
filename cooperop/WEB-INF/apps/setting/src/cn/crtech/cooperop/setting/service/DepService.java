package cn.crtech.cooperop.setting.service;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.ConfigDao;
import cn.crtech.cooperop.setting.dao.DepDao;
import cn.crtech.cooperop.setting.dao.UserDao;
import com.singularsys.jep.functions.Str;
import com.sun.org.apache.regexp.internal.RE;

public class DepService extends BaseService {
    public Result query(Map<String, Object> params) throws Exception {
        try {
            connect();
            DepDao dd = new DepDao();
            if (!CommonFun.isNe(params.get("depSelected"))) {
//				String[] depSelected=(String[]) params.get("depSelected");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("depSelected", params.get("depSelected"));
                if (!CommonFun.isNe(params.get("depHasSelected"))) {
                    map.put("depHasSelected", params.get("depHasSelected"));
                }
                return dd.query(map);
            }
            return dd.query(params);
        } catch (Exception ex) {
            throw ex;
        } finally {
            disconnect();
        }
    }

    public Result querydep(Map<String, Object> params) throws Exception {
        try {
            connect();
            DepDao dd = new DepDao();
            return dd.querydep(params);
        } catch (Exception ex) {
            throw ex;
        } finally {
            disconnect();
        }
    }

    public Record get(Map<String, Object> params) throws Exception {
        try {
            connect();
            DepDao dd = new DepDao();
            return dd.get(params);
        } catch (Exception ex) {
            throw ex;
        } finally {
            disconnect();
        }
    }


    public int save(Map<String, Object> params) throws Exception {
        try {
            connect();
            start();
            DepDao dd = new DepDao();
            Record query_record = new Record();
            query_record.put("code",params.get("code"));
            Result query = dd.queryByCode(query_record);
            if (query != null && query.getResultset().size() > 0){
                return -2;
            }
            //?????????????????????  ???????????????jigid??????????????????
            if ("1".equals(params.get("type"))){
                query_record.clear();
                query_record.put("code",params.get("jigid"));
                Result jg_query = dd.queryByCode(query_record);
                if (jg_query != null && jg_query.getResultset().size() > 0){
                    return -2;
                }
            }
            params.put("state", "1");
            int i = dd.insert(params);
            //????????????????????????????????????????????????????????????????????????????????????system_organization??????
            if (Integer.parseInt(params.get("parent_id").toString()) == 0 && "1".equals(params.get("type"))) {
                Record record = new Record();
                record.put("name", params.get("name"));
                record.put("state", params.get("state"));
                record.put("type", params.get("type"));
                Result result = dd.queryByCondition(record);
                record.clear();
                if (!CommonFun.isNe(result) && result.getResultset().size() > 0) {
                    Object id = result.getResultset().get(0).get("id");
                    record.put("company_id", id);
                }
                record.put("jigname", params.get("name"));
                record.put("jigid", params.get("jigid"));
                record.put("state", params.get("state"));
                i = i + dd.insertOrganization(record);
            }
            commit();
            return i;
        } catch (Exception ex) {
            rollback();
            throw ex;
        } finally {
            disconnect();
        }
    }

    public int update(Map<String, Object> params) throws Exception {
        try {
            connect();
            DepDao dd = new DepDao();
            int i = dd.update(params);
            //????????????????????? ????????????????????????system_organization??????????????????
            if ("1".equals(params.get("type"))){
                Record record = new Record();
                record.put("jigid",params.get("jigid"));
                record.put("jigname",params.get("name"));
                dd.updateOrganization(record);
            }
            return i;
        } catch (Exception ex) {
            rollback();
            throw ex;
        } finally {
            disconnect();
        }
    }

    public int delete(Map<String, Object> params) throws Exception {
        try {
            connect();
            start();
            DepDao dd = new DepDao();
            UserDao ud = new UserDao();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("depid", params.get("id"));
            //?????????????????????????????????????????????
            Result res = ud.queryRoleByDep(map);
            int i = -1;
            if (res != null && res.getResultset().size() > 0) {
                //???????????????????????????
                return i;
            } else {
                //?????????????????????????????????????????????
                //???????????????jigid,??????????????????
                Result query = dd.query(params);
                if ("1".equals(query.getResultset().get(0).get("type"))){  //???????????????????????? ???????????????????????????????????????????????????
                    Record condition = new Record();
                    condition.put("jigid",query.getResultset().get(0).get("jigid"));
                    condition.put("state","-1");
                    i = dd.updateDep(condition);
                }else if ("2".equals(query.getResultset().get(0).get("type"))){  //???????????????????????? ????????????????????????
                    Map<String,Object> param = new HashMap<>();
                    param.put("id", params.get("id"));
                    param.put("state", "-1");
                    i = dd.update(param);
                }

            }
            commit();
            return i;
        } catch (Exception ex) {
            rollback();
            throw ex;
        } finally {
            disconnect();
        }
    }
}
