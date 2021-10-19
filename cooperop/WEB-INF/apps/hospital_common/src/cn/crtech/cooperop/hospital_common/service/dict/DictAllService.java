package cn.crtech.cooperop.hospital_common.service.dict;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.dict.DictAllDao;

public class DictAllService extends BaseService {

    public Result queryis(Map<String, Object> params) throws Exception {
        try {
            connect("hospital_common");
            return new DictAllDao().queryis(params);
        } catch (Exception e) {
            throw e;
        } finally {
            disconnect();
        }
    }

    public Result querynot(Map<String, Object> params) throws Exception {
        try {
            connect("hospital_common");
            return new DictAllDao().querynot(params);
        } catch (Exception e) {
            throw e;
        } finally {
            disconnect();
        }
    }

    public Result querysys(Map<String, Object> params) throws Exception {
        try {
            connect("hospital_common");
            return new DictAllDao().querysys(params);
        } catch (Exception e) {
            throw e;
        } finally {
            disconnect();
        }
    }

    public Result querymap(Map<String, Object> params) throws Exception {
        try {
            connect("hospital_common");
            return new DictAllDao().querymap(params);
        } catch (Exception e) {
            throw e;
        } finally {
            disconnect();
        }
    }

    public int update(Map<String, Object> params) throws Exception {
        try {
            connect("hospital_common");
            int res = new DictAllDao().update(params);
            return res;
        } catch (Exception e) {
            throw e;
        } finally {
            disconnect();
        }
    }

    public Map<String, Object> queryFields(Map<String, Object> params) throws Exception {
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
            connect("hospital_common");
            DictAllDao dao = new DictAllDao();
            //查询匹配表信息
            Record mapInfo = dao.getBytb(params);
            Object histname = mapInfo.get("histname");
            if (!CommonFun.isNe(histname)) {
                //防止跨库查询时无法查询到指定表结构
                if (histname.toString().lastIndexOf(".") != -1) {
                    disconnect();
                    //将表中数据库名称提出来
                    String database = histname.toString().substring(0,histname.toString().lastIndexOf(".")-1);
                    //截取后的histname
                    String subHistname = histname.toString().substring(histname.toString().lastIndexOf(".") + 1);
                    //连接指定数据库
                    connect(database);
                    mapInfo.put("histname", subHistname);
                    rtnMap.put("fields", new DictAllDao().queryFields(mapInfo).getResultset());
                    //将名称还原到初始值,减少后边使用的影响
                    mapInfo.put("histname",histname);
                    rtnMap.putAll(mapInfo);
                    return rtnMap;
                }
            }
            rtnMap.put("fields", new DictAllDao().queryFields(mapInfo).getResultset());
            rtnMap.putAll(mapInfo);
            return rtnMap;
        } catch (Exception e) {
            throw e;
        } finally {
            disconnect();
        }
    }

    public Record edit(Map<String, Object> params) throws Exception {
        try {
            connect("hospital_common");
            return new DictAllDao().edit(params);
        } catch (Exception e) {
            throw e;
        } finally {
            disconnect();
        }
    }

    public void reMapNum(Map<String, Object> params) throws Exception {
        try {
            connect("hospital_common");
            new DictAllDao().reMapNum(params);
        } catch (Exception e) {
            throw e;
        } finally {
            disconnect();
        }
    }
}
